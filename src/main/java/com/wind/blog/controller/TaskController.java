package com.wind.blog.controller;

import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.config.RabbitmqConfig;
import com.wind.blog.service.base.RedisService;
import com.wind.blog.service.LinkService;
import com.wind.blog.task.LinkTask;
import com.wind.blog.thread.LinkParseService;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RedisService redisService;

    @Autowired
    private LinkParseService linkParseService;


    // @Autowired
    // private AliyunBlogService aliyunBlogService;

    @ResponseBody
    @RequestMapping(value = "task/blog", method = RequestMethod.GET)
    public String blogTask() {

        // String url = "https://www.aliyun.com/jiaocheng/870657.html";
        // try {
        //
        // if (StringUtils.isEmpty(url)) {
        // return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        // }
        // BlogThread blogT = new BlogThread(blogMapperEx, linkMapperEx, url, BlogSource.ALIYUN);
        // Thread blogThread = new Thread(blogT);
        // blogThread.start();
        //
        // return JsonResponseUtil.ok();
        // } catch (Exception e) {
        // logger.error("[BLOG任务] 录入blog异常, 参数: url={}", url);
        // return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        // }
        return null;
    }

    /**
     * blog link 跑批任务
     * 
     * @param blogSource blog 来源
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "task/link", method = RequestMethod.GET)
    public String linkTask(@RequestParam("blogSource") int blogSource) {
        try {
            if (!BlogSource.ALIYUN.getValue().equals(blogSource)) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            linkParseService.start(BlogSource.ALIYUN);
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "redis", method = RequestMethod.GET)
    public String redis(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            Object v = redisService.get(key);
            if (v == null) {
                redisService.set(key, value);
            }
            value = (String) redisService.get(key);
            logger.info("[LINK任务] key={}, value={}", key, value);
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("[LINK任务] 异常, e={}", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

}
