package com.wind.admin.controller;

import com.wind.auth.model.User;
import com.wind.admin.service.UserService;
import com.wind.common.Page;
import com.wind.common.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        try {
            if (pageNum < 1) {
                pageNum = 1;
            }

            Page pageModel = new Page(pageNum, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("page", pageModel);

            List<User> userList = userService.findPage(params);
            return JsonResponseUtil.ok(userList);
        } catch (Exception e) {
            logger.error("用户列表, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 用户详情
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.USER_NOT_EXISTS);
        }
        return JsonResponseUtil.ok(user);
    }

    /**
     * 用户新增
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add(User param) {
        if (StringUtils.isEmpty(param.getMobile())) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findByMobile(param.getMobile());
        if (user != null) {
            return JsonResponseUtil.fail(ErrorCode.USER_MOBILE_HAS_REGIST);
        }
        user = new User();
        user.setMobile(param.getMobile());
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean flag = userService.saveOrUpdate(user);
        if (flag) {
            return JsonResponseUtil.ok(user);
        } else {
            return JsonResponseUtil.fail(ErrorCode.FAIL);
        }
    }

    /**
     * 用户删除
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        if(id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean flag = userService.delete(id);
        if(flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail();
        }
    }

    /**
     * 用户启用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public Object enable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.USER_NOT_EXISTS);
        }
        user.setStatus(1);
        user.setUpdateTime(new Date());
        userService.saveOrUpdate(user);
        return JsonResponseUtil.ok(user);
    }

    /**
     * 用户停用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public Object disable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.USER_NOT_EXISTS);
        }
        user.setStatus(0);
        user.setUpdateTime(new Date());
        userService.saveOrUpdate(user);
        return JsonResponseUtil.ok(user);
    }

    /**
     * 用户统计
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count(User user) {

        Map<String, Object> param = new HashMap<>();

        int count = userService.count(param);
        return JsonResponseUtil.ok(count);
    }

}