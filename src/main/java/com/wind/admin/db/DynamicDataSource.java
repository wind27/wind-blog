package com.wind.admin.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（继承AbstractRoutingDataSource） 使用DatabaseContextHolder获取当前线程的DatabaseType
 *
 * @author qianchun
 * @date 2019/1/29
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDatabaseType();
    }
}
