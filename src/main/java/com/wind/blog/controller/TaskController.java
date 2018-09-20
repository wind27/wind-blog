package com.wind.blog.controller;

import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.thread.LinkParseService;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
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
    private LinkParseService linkParseService;

//    /**
//     * blog link 跑批任务
//     *
//     * @param blogSource blog 来源
//     * @return 返回结果
//     */
//    @ResponseBody
//    @RequestMapping(value = "task/link", method = RequestMethod.GET)
//    public String linkTask(@RequestParam("blogSource") int blogSource) {
//        try {
//            if (!BlogSource.ALIYUN.getValue().equals(blogSource)) {
//                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
//            }
//            linkParseService.start(BlogSource.ALIYUN);
//            return JsonResponseUtil.ok();
//        } catch (Exception e) {
//            logger.error("[LINK任务] 录入link异常, 参数: url={");
//            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
//        }
//    }
}