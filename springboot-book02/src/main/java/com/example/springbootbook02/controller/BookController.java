package com.example.springbootbook02.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootbook02.config.Result;
import com.example.springbootbook02.entity.Book;
import com.example.springbootbook02.entity.Classify;
import com.example.springbootbook02.mapper.BookMapper;
import com.example.springbootbook02.mapper.ClassifyMapper;
import com.example.springbootbook02.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/book")
@CrossOrigin(origins = {"*", "null"})
public class BookController {

    @Resource
    private UserMapper userMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private ClassifyMapper classifyMapper;

    @GetMapping()
    public Result<?> getBookList() {
        List<Book> books = bookMapper.selectList(null);
        return Result.success(books);
    }

    // 获取图书分类及对应的用户数据
    @GetMapping("all")
    public Result<?> getBookToClassifyToUserList() {
        // 通过book查询classify
        QueryWrapper<Book> bookWrapper = new QueryWrapper<>();
        List<Book> books = bookMapper.selectList(bookWrapper);
        // 通过classify查询user
        QueryWrapper<Classify> classifyWrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(classifyWrapper);
        for (Book book : books) {
            for (Classify classify : classifies) {
                classify.setUser(userMapper.selectById(classify.getUId()));
                // 最后将classify加入到book中
                if (classify.getCId() == book.getCId()) {
                    book.setClassify(classify);
                }
            }
        }

        return Result.success(books);
    }


    // 新增
    @PostMapping("add")
    public Result<?> save(@RequestBody Book book) {
        bookMapper.insert(book);
        return Result.success();
    }


    // 删除
    @PostMapping("delete")
    public Result<?> delete(@RequestBody Book book) {
        bookMapper.deleteById(book);
        return Result.success();
    }

    // 修改
    @PostMapping("update")
    public Result<?> update(@RequestBody Book book) {
        bookMapper.updateById(book);
        return Result.success();
    }


    // 分页
    @GetMapping("page")
    public Result<?> findPage(@RequestParam(defaultValue = "") Long id,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize) {

        Page pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("b_id", id);
        Page<Book> bookPage = bookMapper.selectPage(pageInfo, wrapper);

        return Result.success(bookPage);
    }

    @GetMapping("search")
    public Result<?> search(@RequestParam(defaultValue = "") Long id,
                            @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();

        wrapper.eq("b_id", id);
        wrapper.like("b_name", name);
        List<Book> books = bookMapper.selectList(wrapper);
        return Result.success(books);
    }

}

