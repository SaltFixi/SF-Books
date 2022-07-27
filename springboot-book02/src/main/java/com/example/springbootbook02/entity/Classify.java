package com.example.springbootbook02.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author SaltedFixi
 * @since 2022-07-21
 */
@TableName("classify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "c_id", type = IdType.AUTO) // id自增长
    private Long cId;

    private String cCname;

    private Long uId;

    // 一对多
    @TableField(exist = false) // 表示该字段不是数据库中字段但是必须要使用的
    private User user;

    @TableField(exist = false)
    private List<Book> bookList;

}
