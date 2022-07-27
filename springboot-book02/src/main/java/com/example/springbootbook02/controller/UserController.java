package com.example.springbootbook02.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootbook02.config.Result;
import com.example.springbootbook02.entity.Book;
import com.example.springbootbook02.entity.Classify;
import com.example.springbootbook02.entity.User;
import com.example.springbootbook02.mapper.BookMapper;
import com.example.springbootbook02.mapper.ClassifyMapper;
import com.example.springbootbook02.mapper.UserMapper;
import com.example.springbootbook02.service.UserService;
import com.example.springbootbook02.service.impl.UserServiceImp;
import com.example.springbootbook02.utils.TokenUtils;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author SaltedFixi
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"*", "null"})
public class UserController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ClassifyMapper classifyMapper;
    @Resource
    private BookMapper bookMapper;

    // 获取用户数据
    @GetMapping()
    public Result<?> getUserList() {
        List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }

    // 这里用的mybatis-plus的方法写的，获取到通过用户查询到对应的分类信息以及分类信息对应的图书信息
    @GetMapping("all")
    public Result<?> getUserClassifyBookList() {
        // 查询Classify
        QueryWrapper<Classify> classifyWrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(classifyWrapper);

        // 查询Book
        QueryWrapper<Book> bookWrapper = new QueryWrapper<>();
        List<Book> books = bookMapper.selectList(bookWrapper);

        // 查询User
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(userWrapper);

        for (User user : users) {
            // new一个Classify集合
            ArrayList<Classify> classifyList = new ArrayList<>();
            // 遍历classifies
            for (Classify classify : classifies) {
                // new一个Book集合
                ArrayList<Book> bookList = new ArrayList<>();
                // 将查询到的books遍历
                for (Book book : books) {
                    // 通过它与classify对应的c_id，将book1集合下的数据添加到bookList列表中
                    if (book.getCId() == classify.getCId()) {
                        Book book1 = bookMapper.selectById(book.getBId());
                        bookList.add(book1);
                    }
                }
                // 设置它的bookList集合
                classify.setBookList(bookList);
                // 通过u_id对应关系将classify添加到classifyList列表中
                if (classify.getUId() == user.getUId()) {
                    classifyList.add(classify);
                }
                // 最后再设置它的classifyList集合
                user.setClassifyList(classifyList);
            }

        }
        return Result.success(users);
    }


    // 用自己写的mapper通过id查询
    @GetMapping("allById")
    public Result<?> getUserClassifyBookList2(@RequestParam(defaultValue = "") Long id) {
        User userToClassify = userMapper.getUserToClassify(id);

        List<Classify> classifyList = userToClassify.getClassifyList();

        List<Classify> arr = new ArrayList();
        for (Classify classify : classifyList) {
            // 通过user的uid与classify的外键uid找到分类对应的图书信息最后将对应的所有图书信息存在arr中
            if (userToClassify.getUId() == classify.getUId()) {
                Classify classifyToBook = classifyMapper.getClassifyToBook(classify.getCId());
                arr.add(classifyToBook);
            }
        }
        userToClassify.setClassifyList(arr);

        return Result.success(userToClassify);
    }

    // 登录
    @PostMapping("login")
    public Result<?> login(@RequestBody User user) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("u_name", user.getUName());
        userWrapper.eq("u_pwd", user.getUPwd());
        User res = userMapper.selectOne(userWrapper);

        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }

        // 调用token工具类自动根据对应的用户生成不同的token
        String token = TokenUtils.sign(res);
        HashMap<String, Object> hs = new HashMap<>();
        hs.put("token", token);
        hs.put("user", res);

        return Result.success(hs);
    }

    // 新增
    @PostMapping("add")
    public Result<?> save(@RequestBody User user) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("u_name", user.getUName());
        User res = userMapper.selectOne(userWrapper);
        if (res != null) {
            return Result.error("-1", "用户名重复");
        }
        userMapper.insert(user);
        return Result.success(user);
    }


    // 删除
    @PostMapping("delete")
    public Result<?> delete(@RequestBody User user) {
        userMapper.deleteById(user);
        return Result.success();
    }

    // 修改
    @PostMapping("update")
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }


    // 分页
    @GetMapping("page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize) {

        Page pageInfo = new Page<>(pageNum, pageSize);

        Page<User> userPage = userMapper.selectPage(pageInfo, null);

        return Result.success(userPage);
    }

    @GetMapping("search")
    public Result<?> search(@RequestParam(defaultValue = "") String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("u_name", name);
        List<User> users = userMapper.selectList(queryWrapper);
        return Result.success(users);
    }


}

