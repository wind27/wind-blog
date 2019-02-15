package com.wind.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.wind.admin.emun.BlogStatus;
import com.wind.admin.service.BlogService;
import com.wind.blog.model.Blog;
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

/**
 * BlogController
 *
 * @author qianchun
 * @date 2018/8/28
 **/
@Controller
@RequestMapping("/blog")
public class BlogController {
    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    /**
     * 列表
     *
     * @param pageNum 分页起始
     * @param pageSize 分页大小
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize) {
        try {
            Map<String, Object> param = new HashMap<>();

            Page page = new Page(pageNum, pageSize);
            param.put("page", page);

            // 设置分页
            Map<String, Object> data = new HashMap<>();
            List<Blog> list = blogService.getPage(param);
            data.put("list", list);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 详情
     *
     * @param id 主键
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Map<String, Object> data = new HashMap<>();
            Blog blog = blogService.getById(id);
            data.put("blog", blog);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("BLOG detail 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 新增
     *
     * @param blog 待新增
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Blog blog) {
        try {
            if (blog == null || blog.getId() == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Date date = new Date();
            blog.setCreateTime(date);
            blog.setUpdateTime(date);
            blog.setStatus(BlogStatus.PUBLISH.getValue());
            boolean flag = blogService.add(blog);
            if (flag) {
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG add 异常, 参数: blog={}", JSONObject.toJSONString(blog), e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 更新
     *
     * @param blog 待更新
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(Blog blog) {
        try {
            if (blog == null || blog.getId() == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Blog currentBlog = blogService.getById(blog.getId());
            if (currentBlog == null) {
                return JsonResponseUtil.fail(ErrorCode.NOT_EXISTS);
            }

            if (StringUtils.isNotEmpty(blog.getTags())) {
                currentBlog.setTags(blog.getTags());
            }
            if (StringUtils.isNotEmpty(blog.getTitle())) {
                currentBlog.setTitle(blog.getTitle());
            }
            if (StringUtils.isNotEmpty(blog.getSummary())) {
                currentBlog.setSummary(blog.getSummary());
            }
            if (StringUtils.isNotEmpty(blog.getContent())) {
                currentBlog.setContent(blog.getContent());
            }

            currentBlog.setUpdateTime(new Date());
            currentBlog.setStatus(BlogStatus.PUBLISH.getValue());
            boolean flag = blogService.update(blog);
            if (flag) {
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG update 异常, 参数: blog={}", JSONObject.toJSONString(blog), e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteMult(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Blog blog = blogService.getById(id);
            if(blog==null) {
                return JsonResponseUtil.ok();
            }
            boolean flag = blogService.delete(id);
            if(flag) {
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG delete 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 是否存在
     *
     * @param id 主键
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/exists/{id}", method = RequestMethod.GET)
    public String exists(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            boolean flag = blogService.getById(id)!=null;
            if (flag) {
                return JsonResponseUtil.ok();
            }
            return JsonResponseUtil.fail(ErrorCode.NOT_EXISTS);
        } catch (Exception e) {
            logger.error("BLOG exists 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }



}