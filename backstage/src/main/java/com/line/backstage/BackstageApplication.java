package com.line.backstage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author jack
 */
@SpringBootApplication
@MapperScan("com.line.backstage.dao.mapper")
// 开启定时任务
@EnableScheduling
// 开启异步
@EnableAsync
public class BackstageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class, args);
    }
}
