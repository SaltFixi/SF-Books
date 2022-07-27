package com.example.springbootbook02.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootbook02.entity.Book;
import com.example.springbootbook02.entity.Classify;
import com.example.springbootbook02.entity.User;
import com.example.springbootbook02.mapper.BookMapper;
import com.example.springbootbook02.mapper.ClassifyMapper;
import com.example.springbootbook02.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;


    // 多对一
    @Test
    public void test() {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(wrapper);

        for (Classify classify : classifies) {
            classify.setUser(userMapper.selectById(classify.getUId()));
        }

        classifies.forEach(System.out::println);
    }

    @Test
    public void test01() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        // List<Classify> classifies = classifyMapper.selectList(null);
        // classifies.forEach(System.out::println);
    }


    @Test
    public void test02() {
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
        books.forEach(System.out::println);
    }


    @Test
    public void test03() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("u_name", "ad");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }


    @Test
    public void test04() {
        QueryWrapper<Book> bookWrapper = new QueryWrapper<>();
        List<Book> books = bookMapper.selectList(bookWrapper);
        for (Book book : books) {
            book.setClassify(classifyMapper.selectById(book.getCId()));
        }

        books.forEach(System.out::println);
    }

    @Test
    public void test05() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(userWrapper);
        for (User user : users) {
            Classify classify = classifyMapper.selectById(user.getUId());
            List<Classify> classifyList = new ArrayList<>();
            classifyList.add(classify);
            user.setClassifyList(classifyList);
        }

        users.forEach(System.out::println);
    }


    @Test
    public void test06() {
        // 查询Classify
        QueryWrapper<Classify> classifyWrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(classifyWrapper);

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

        classifies.forEach(System.out::println);
    }

    @Test
    public void test16() {
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


        // classifies.forEach(System.out::println);

        users.forEach(System.out::println);

    }


    @Test
    public void test08() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("u_name", "admin");
        userWrapper.eq("u_pwd", "123");
        User user = userMapper.selectOne(userWrapper);
        System.out.println(user);
    }

    @Test
    public void test07() {
        User userToClassify = userMapper.getUserToClassify(1L);

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

        System.out.println(userToClassify);
    }


    @Test
    public void test10() {
        Classify classifyToBook = classifyMapper.getClassifyToBook(1L);
        System.out.println(classifyToBook);
    }


}
