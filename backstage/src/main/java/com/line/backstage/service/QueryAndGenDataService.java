package com.line.backstage.service;

public interface QueryAndGenDataService {

    void queryAndGen();

    /**
     * 每小时生成币种秒线
     */
    void generateCurrencySeconds();

}
