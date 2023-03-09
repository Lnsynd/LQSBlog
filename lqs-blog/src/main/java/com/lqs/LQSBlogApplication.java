package com.lqs;

import javafx.application.Application;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.lqs.mapper")
@EnableScheduling // 启动定时任务
@EnableSwagger2
public class LQSBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LQSBlogApplication.class,args);
    }
}
