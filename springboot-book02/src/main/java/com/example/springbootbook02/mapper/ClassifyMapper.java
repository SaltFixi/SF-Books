package com.example.springbootbook02.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootbook02.entity.Classify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootbook02.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SaltedFixi
 * @since 2022-07-21
 */
@Mapper
public interface ClassifyMapper extends BaseMapper<Classify> {
    // 一对多
    Classify getClassifyToBook(@Param("id") Long id);
}
