package com.example.springbootbook02.mapper;

import com.example.springbootbook02.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserMapper extends BaseMapper<User> {
    // 一对多
    User getUserToClassify(@Param("id") Long id);

}
