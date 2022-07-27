package com.example.springbootbook02.service.impl;

import com.example.springbootbook02.entity.User;
import com.example.springbootbook02.mapper.UserMapper;
import com.example.springbootbook02.service.UserService;
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
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
