package com.wind.blog.service;

import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.model.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class BlogService {
    private final static Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogMapperEx blogMapperEx;

    public Blog findById(String url) {
        return blogMapperEx.findById(url);
    }

    public boolean add(Blog blog) {
        return blogMapperEx.insert(blog) > 0;
    }
}
