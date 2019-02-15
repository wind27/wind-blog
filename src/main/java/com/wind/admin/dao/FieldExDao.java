package com.wind.admin.dao;

import com.wind.annotation.DAO;
import com.wind.form.dao.FieldDao;
import com.wind.form.model.Field;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 表单属性管理
 * @author qianchun
 * @date 2019/02/01
 */
@DAO(catalog = "form")
public interface FieldExDao extends FieldDao {


    @Select(SELECT_SQL + " where id = #{id} limit 0, 1")
    Field getById(Long id);


    /**
     * 分页列表查询
     *
     * @param param 参数
     * @return 返回结果
     */
    @SelectProvider(type=FieldProvider.class, method="findPage")
    List<Field> getPage(Map<String, Object> param);

}