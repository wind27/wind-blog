package com.wind.blog.schedule;

import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.service.base.RabbitmqService;
import com.wind.blog.task.LinkTask;
import com.wind.blog.thread.BlogParseService;
import com.wind.blog.thread.LinkParseService;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 *
 */
@Component
public class ScheduleTask {
    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private LinkParseService linkParseService;

    @Autowired
    private BlogParseService blogParseService;

    @Autowired
    private RabbitmqService rabbitmqService;

    /**
     * blog link 跑批任务
     */
    @Scheduled(initialDelay = 100, fixedDelay = 60000)
    private void blogTask() {
        try {
            int blogSource = BlogSource.ALIYUN.getValue();
            if (!BlogSource.ALIYUN.getValue().equals(blogSource)) {
                return ;
            }
            linkParseService.start(BlogSource.ALIYUN);
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
        }
    }
}
