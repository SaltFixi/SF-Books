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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@RequestMapping("/classify")
@CrossOrigin(origins = {"*", "null"})
public class ClassifyController {
    @Resource
    private ClassifyMapper classifyMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private BookMapper bookMapper;

    @GetMapping()
    public Result<?> getClassifyList() {
        List<Classify> classifies = classifyMapper.selectList(null);
        return Result.success(classifies);
    }

    // 获取图书分类及对应的用户数据
    @GetMapping("all")
    public Result<?> getClassifyToUserList() {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(wrapper);

        for (Classify classify : classifies) {
            classify.setUser(userMapper.selectById(classify.getUId()));
        }
        return Result.success(classifies);
    }


    // 新增
    @PostMapping("add")
    public Result<?> save(@RequestBody Classify classify) {
        QueryWrapper<Classify> classifyWrapper = new QueryWrapper<>();
        classifyWrapper.eq("u_id", classify.getUId());
        classifyWrapper.eq("c_cname", classify.getCCname());
        Classify res = classifyMapper.selectOne(classifyWrapper);
        if (res != null) {
            return Result.error("-1", "图书分类名称重复");
        }
        classifyMapper.insert(classify);
        return Result.success();
    }

    // 删除
    @PostMapping("delete")
    public Result<?> delete(@RequestBody Classify classify) {
        classifyMapper.deleteById(classify);
        return Result.success();
    }

    // 修改
    @PostMapping("update")
    public Result<?> update(@RequestBody Classify classify) {
        classifyMapper.updateById(classify);
        return Result.success();
    }


    // 获取图书分类及对应的用户数据
    private List<Classify> getClassifyToBookList(List<Classify> classifies) {
        // 查询Book
        QueryWrapper<Book> bookWrapper = new QueryWrapper<>();
        List<Book> books = bookMapper.selectList(bookWrapper);

        for (Classify classify : classifies) {  // 遍历classifies
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
        }

        return classifies;
    }


    // 查询图书分类对应的图书信息
    @GetMapping("page")
    public Result<?> getClassifyToBookListPage(@RequestParam(defaultValue = "") Long id,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "5") Integer pageSize) {

        // 查询Classify
        QueryWrapper<Classify> classifyWrapper = new QueryWrapper<>();
        classifyWrapper.eq("u_id", id);

        // 分页
        Page pageInfo = new Page<>(pageNum, pageSize);
        Page classifyPage = classifyMapper.selectPage(pageInfo, classifyWrapper);

        List<Classify> records = classifyPage.getRecords();
        getClassifyToBookList(records);

        return Result.success(classifyPage);
    }


    @GetMapping("search")
    public Result<?> search(@RequestParam(defaultValue = "") Long id,
                            @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id", id);
        wrapper.like("c_cname", name);
        List<Classify> classifies = classifyMapper.selectList(wrapper);
        return Result.success(classifies);
    }

}

