package com.line.backstage;

<<<<<<< HEAD
import org.springframework.scheduling.annotation.EnableAsync;
=======
>>>>>>> master
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jack
 */
@SpringBootApplication
@MapperScan("com.line.backstage.dao.mapper")
<<<<<<< HEAD
// 开启定时任务
@EnableScheduling
// 开启异步
@EnableAsync
=======
@EnableScheduling
>>>>>>> master
public class BackstageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class, args);
    }
}
