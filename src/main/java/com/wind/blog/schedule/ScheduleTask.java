package com.wind.blog.schedule;

import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.service.base.RabbitmqService;
import com.wind.blog.task.LinkTask;
import com.wind.blog.thread.BlogParseService;
import com.wind.blog.thread.LinkParseService;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

//    @Scheduled(initialDelay = 100, fixedDelay = 60000)
//    private void linkTask() {
//        while (true) {
//            String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
//            logger.info("[SCHEDULE] LINK start date:" + date);
//            linkParseService.start(BlogSource.ALIYUN);
//            date = DateUtils.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
//            logger.info("[SCHEDULE] LINK date:" + date);
//        }
//    }



//    @Scheduled(initialDelay = 100, fixedDelay = 60000)
//    private void blogTask() {
//        while (true) {
//
//
//            String url = "";
//            String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
//            logger.info("[SCHEDULE] BLOG start date:" + date);
//            blogParseService.start(url, BlogSource.ALIYUN);
//            date = DateUtils.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
//            logger.info("[SCHEDULE] BLOG end date:" + date);
//        }
//    }
}
