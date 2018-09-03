package com.wind.blog.service;

import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class LinkService {
    private final static Logger logger = LoggerFactory.getLogger(LinkService.class);

    @Autowired
    private LinkMapperEx linkMapperEx;

    public Link findByUrl(String url) {
        return linkMapperEx.findByUrl(url);
    }

    public boolean add(Link link) {
        return linkMapperEx.insert(link) > 0;
    }

}
