package com.example.springbootbook02;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootbook02.entity.Classify;
import com.example.springbootbook02.entity.User;
import com.example.springbootbook02.mapper.ClassifyMapper;
import com.example.springbootbook02.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SpringbootBook02ApplicationTests {

    @Resource
    private ClassifyMapper classifyMapper;
    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        List<Classify> classifies = classifyMapper.selectList(wrapper);
        for (Classify classify : classifies) {
            classify.setUser(userMapper.selectById(classify.getCId()));
        }
    }

    @Test
    public void test01() {
        List<Classify> classifies = classifyMapper.selectList(null);
        System.out.println(classifies);
    }
}
