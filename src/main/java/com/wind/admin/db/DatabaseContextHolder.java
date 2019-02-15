package com.wind.admin.db;

import com.wind.admin.emun.DatabaseCatalog;

/**
 * 保存一个线程安全的DatabaseType容器
 *
 * @author qianchun
 * @date 2019/1/29
 **/
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseCatalog> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseCatalog type) {
        contextHolder.set(type);
    }

    public static DatabaseCatalog getDatabaseType() {
        return contextHolder.get();
    }
}
