package com.example.springbootbook02;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 代码生成器
@SpringBootTest
public class GeneratorServiceEntity {
    // 路径设置
    public String PROJECT_DIR = "D:\\RouteFile\\Java.study\\project\\springboot-book02\\";    // 项目路径
    public String PACKAGE = "com.example";      // 设置父包名  全名 com.xx.jumper
    public String START_PACKAGE = "springbootbook02"; // 设置父包模块名（即启动类所在包的包名）
    // 数据库设置
    public String URL = "jdbc:mysql://localhost:3306/books?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
    public String USERNAME = "root";
    public String PASSWORD = "123456";
    // 要生产的表
    public List<String> TABLES = Arrays.asList("user","book","classify");
    // 相对路径（无需改动）
    public String JAVA_DIR = PROJECT_DIR  + "src\\main\\java\\";
    public String MAPPER_XML_DIR = PROJECT_DIR + "src\\main\\resources\\mapper\\";

    @Test
    public void contextLoads() {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("SaltedFixi") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
//                            .disableOpenDir()   // 不打开目录
                            .outputDir(JAVA_DIR); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(PACKAGE) // 设置父包名
                            .moduleName(START_PACKAGE) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, MAPPER_XML_DIR)); // 设置mapperXml生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            // Entity策略配置
                            .entityBuilder()
                            .enableLombok() // 开启 lombok 模型
//                            .logicDeleteColumnName("")    //  逻辑删除字段名(数据库)
//                            .logicDeletePropertyName("")  //  逻辑删除属性名(实体)
//                            .enableTableFieldAnnotation() //  开启生成实体时生成字段注解
                            // Controller策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启生成 @RestController 控制器
                            // Service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            // Mapper策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation()   // 开启 @Mapper 注解
                    ;
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}

