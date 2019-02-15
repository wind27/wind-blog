package com.wind.admin.dao;

import com.wind.annotation.DAO;
import com.wind.auth.dao.UserDao;
import com.wind.auth.model.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@DAO(catalog = "auth")
public interface UserExDao extends UserDao {

    /**
     * 分页列表查询
     * 
     * @param param 参数
     * @return 返回结果
     */
    @SelectProvider(type = UserProvider.class, method = "findPage")
    public List<User> findPage(Map<String, Object> param);

    @Select(SELECT_SQL + " WHERE mobile = #{mobile}")
    public User findByMobile(String mobile);

}
