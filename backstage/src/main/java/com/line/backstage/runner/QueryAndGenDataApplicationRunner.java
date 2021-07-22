package com.line.backstage.runner;

import com.line.backstage.service.QueryAndGenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动时刷新数据
 */
@Component
@Order(2)
public class QueryAndGenDataApplicationRunner implements ApplicationRunner {

    @Autowired
    private QueryAndGenDataService queryAndGenDataService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        queryAndGenDataService.queryAndGen();
    }

}
