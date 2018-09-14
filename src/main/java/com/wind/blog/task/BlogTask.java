package com.wind.blog.task;

import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.service.BlogService;
import com.wind.blog.service.LinkService;
import com.wind.blog.thread.BlogParseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class BlogTask {
    private final static Logger logger = LoggerFactory.getLogger(BlogTask.class);

//    @Async
//    public void blogTask(LinkService linkService, BlogService blogService, String url, BlogSource blogSource) {
//        try {
//            if (StringUtils.isEmpty(url)) {
//                return ;
//            }
//            synchronized (this) {
//                if(threadNum < THREAD_MAX_SIZE) {
//                    boolean flag = true;
//                    while (flag) {
//                        if(blogSource == BlogSource.ALIYUN) {
//                            BlogParseService blogT = new BlogParseService(blogService, linkService, url, BlogSource.ALIYUN);
//                            Thread blogThread = new Thread(blogT);
//                            blogThread.setName("[BlogTask-"+threadNum+"]");
//                            blogThread.start();
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("[BLOG任务] 录入blog异常, 参数: url={}", url);
//        }
//        logger.info("[BLOG任务] 操作完毕, url={}", url);
//    }
}
