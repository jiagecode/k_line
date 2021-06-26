package com.line.backstage.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author pc
 */
@Data
public class PageWrapper<T> implements Serializable {

    private List<T> list;
    private int total;

    public PageWrapper(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = (int) pageInfo.getTotal();
    }
}
