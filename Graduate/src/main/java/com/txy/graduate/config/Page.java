package com.txy.graduate.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义分类查询信息类
 */
@Data
@NoArgsConstructor
public class Page<T> {
    private int currentPage;
    private int pageSize;

    public Page(int currentPage, int pageSize) {
        if (currentPage <= 0)
            currentPage = 1;
        if (pageSize <= 0)
            pageSize = 1;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        //计算出查询的起始索引
        this.start = (currentPage - 1) * pageSize;
    }

    /**
     * 查询的结果封装
     */
    private List<T> objects;

    /**
     * 查询的起始索引
     */
    private int start;

    /**
     * 查询到数据量
     */
    private int total;

    /**
     * 表中的总数
     */
    private int count;


}

