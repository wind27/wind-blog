package com.wind.admin.controller;

import com.wind.form.model.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@Controller
@RequestMapping("/form")
public class FormController {
    private final static Logger logger = LoggerFactory.getLogger(FormController.class);

    /**
     * index 页面
     *
     * @param request request
     * @return 返回结果
     */
    @RequestMapping(value = "/field/list", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {

        List<Field> fields = new ArrayList<>();

        Field field = new Field();
        field.setId(1L);
        field.setFormId(1L);
        field.setName("用户");
        field.setIsShow(1);
        field.setCssClass("user_css");
        field.setIsMust(String.valueOf(1));
        field.setCreateTime(new Date());
        field.setUpdateTime(new Date());
        fields.add(field);

        field = new Field();
        field.setId(2L);
        field.setFormId(2L);
        field.setName("角色");
        field.setIsShow(1);
        field.setCssClass("role_css");
        field.setIsMust(String.valueOf(1));
        field.setCreateTime(new Date());
        field.setUpdateTime(new Date());
        fields.add(field);

        ModelAndView model = new ModelAndView();
        model.setViewName("field/list");
        model.addObject("fields", fields);
        model.addObject("test", "helo");
        return model;
    }


    @RequestMapping(value = "/field/add", method = RequestMethod.GET)
    public ModelAndView fieldAdd(HttpServletRequest request, Field field) {
        ModelAndView model = new ModelAndView();
        model.setViewName("field/add");
        return model;
    }
}