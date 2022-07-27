package com.example.springbootbook02.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "b_id", type = IdType.AUTO) // id自增长
    private Integer bId;

    private String bName;

    private String bPublisher;

    private String bIsbn;

    private BigDecimal bPrice;

    private Long cId;

    @TableField(exist = false) // 表示该字段不是数据库中字段但是必须要使用的
    private Classify classify;

}
