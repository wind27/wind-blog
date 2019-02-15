package com.wind.admin.service;

import com.wind.auth.model.User;
import com.wind.admin.dao.UserExDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * UserService
 *
 * @author qianchun 2019/1/10
 **/
@Service
public class UserService {

    @Autowired
    private UserExDao userDao;

    /**
     * 主键id查询
     * 
     * @param id id
     * @return 返回结果
     */
    public User findById(Long id) {
        if (id == 0) {
            return null;
        }
        return userDao.getByPrimary(id);
    }

    /**
     * 手机号查询
     * 
     * @param mobile 手机号
     * @return 返回结果
     */
    public User findByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        return userDao.findByMobile(mobile);
    }

    /**
     * 新增或修改
     * 
     * @param user user
     * @return 返回结果
     */
    public boolean saveOrUpdate(User user) {
        if (user == null) {
            return false;
        }
        if (user.getId() != null) {
            return userDao.update(user) > 0;
        } else {
            return userDao.save(user) > 0;
        }
    }

    /**
     * 删除
     * 
     * @param id id
     * @return 返回结果
     */
    public boolean delete(Long id) {
        if (id == null) {
            return true;
        }
        if (userDao.getByPrimary(id) == null) {
            return true;
        }
        return userDao.delete(id) > 0;
    }

    /**
     * 统计
     * 
     * @param params 参数
     * @return 返回结果
     */
    public int count(Map<String, Object> params) {
        return userDao.count(params);
    }

    /**
     * 分页
     * 
     * @param params 参数
     * @return 返回结果
     */
    public List<User> findPage(Map<String, Object> params) {
        return userDao.findPage(params);
    }
}
