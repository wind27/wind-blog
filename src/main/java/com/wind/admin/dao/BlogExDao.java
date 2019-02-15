package com.wind.admin.dao;

import com.wind.annotation.DAO;
import com.wind.blog.dao.BlogDao;
import com.wind.blog.model.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 *
 * ${table.daoName2}
 * blog信息表
 */
@DAO(catalog = "blog")
public interface BlogExDao extends BlogDao {


    @Select(SELECT_SQL + " where url = #{url} limit 0, 1")
    Blog getByUrl(String url);


    /**
     * 分页列表查询
     *
     * @param param 参数
     * @return 返回结果
     */
    @SelectProvider(type=BlogProvider.class, method="findPage")
    List<Blog> getPage(Map<String, Object> param);

}