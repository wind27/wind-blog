package com.wind.admin.service;

import com.wind.admin.dao.BlogExDao;
import com.wind.blog.model.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
@Service
public class BlogService {
    private final static Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogExDao blogExDao;

    /**
     * 根据主键查询
     * 
     * @param id id
     * @return 返回结果
     */
    public Blog getById(Long id) {
        return blogExDao.getByPrimary(id);
    }

    /**
     * 新增
     * 
     * @param blog blog
     * @return 返回结果
     */
    public boolean add(Blog blog) {
        return blogExDao.save(blog) > 0;
    }

    /**
     * 更新
     * 
     * @param blog blog
     * @return 返回结果
     */
    public boolean update(Blog blog) {
        return blog != null && blog.getId() != null && blogExDao.update(blog) > 0;
    }

    /**
     * 删除
     * 
     * @param id id
     * @return 返回结果
     */
    public boolean delete(Long id) {
        return (id != null) && blogExDao.delete(id) > 0;
    }

    /**
     * 分页查询
     * 
     * @param params 参数
     * @return 返回结果
     */
    public List<Blog> getPage(Map<String, Object> params) {
        return blogExDao.getPage(params);
    }
}
