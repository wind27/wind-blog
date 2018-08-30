package com.wind.blog.controller;

import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.thread.BlogThread;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@RestController
public class TaskController {
    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private BlogMapperEx blogMapperEx;

    @Autowired
    private LinkMapperEx linkMapperEx;


    @ResponseBody
    @RequestMapping(value = "task/blog", method = RequestMethod.GET)
    public String blogTask() {

        String url = "https://www.aliyun.com/jiaocheng/870657.html";
        try {

            if(StringUtils.isEmpty(url)) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            BlogThread blogT = new BlogThread(blogMapperEx, linkMapperEx, url, BlogSource.ALIYUN);
            Thread blogThread = new Thread(blogT);
            blogThread.start();

            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("[BLOG任务] 录入blog异常, 参数: url={}", url);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

}
