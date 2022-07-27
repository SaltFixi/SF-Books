package com.example.springbootbook02.service.impl;

import com.example.springbootbook02.entity.Book;
import com.example.springbootbook02.mapper.BookMapper;
import com.example.springbootbook02.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SaltedFixi
 * @since 2022-07-21
 */
@Service
public class BookServiceImp extends ServiceImpl<BookMapper, Book> implements BookService {

}
