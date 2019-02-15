package com.wind.admin.emun;

import org.apache.commons.lang3.StringUtils;

/**
 * DatabaseCatalog
 * 
 * @author qianchun
 * @date 2019/1/29
 */
public enum DatabaseCatalog {

    auth, blog, form;

    public static DatabaseCatalog get(String catalog) {
        if (StringUtils.isEmpty(catalog)) {
            return null;
        } else if (catalog.equals(auth.name())) {
            return auth;
        } else if (catalog.equals(blog.name())) {
            return blog;
        } else if (catalog.equals(form.name())) {
            return blog;
        } else {
            return null;
        }
    }
}
