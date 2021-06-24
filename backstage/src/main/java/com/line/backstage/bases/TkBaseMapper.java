package com.line.backstage.bases;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * TK 泛型
 * @author pc
 * @param <T>
 */
public interface TkBaseMapper<T> extends BaseMapper<T>, ExampleMapper<T>, MySqlMapper<T> {
}
