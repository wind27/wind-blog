package com.wind.admin.dao;

import com.wind.annotation.DAO;
import com.wind.blog.dao.BlogDao;
import com.wind.blog.model.Blog;
import com.wind.form.dao.FormDao;
import com.wind.form.model.Form;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 *
 * ${table.daoName2}
 * blog信息表
 */
@DAO(catalog = "form")
public interface FormExDao extends FormDao {


    @Select(SELECT_SQL + " where id = #{id} limit 0, 1")
    Form getById(Long id);


    /**
     * 分页列表查询
     *
     * @param param 参数
     * @return 返回结果
     */
    @SelectProvider(type=FormProvider.class, method="findPage")
    List<Form> getPage(Map<String, Object> param);

}