package com.wind.blog.task;

import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.rabbitmq.LinkProvider;
import com.wind.blog.service.BlogService;
import com.wind.blog.service.LinkService;
import com.wind.blog.thread.BlogThread;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.SynchronousQueue;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class BlogTask {
    private final static Logger logger = LoggerFactory.getLogger(BlogTask.class);

    public static volatile Integer threadNum = 0;

    public static final int THREAD_MAX_SIZE = 10;

    @Async
    public void blogTask(LinkService linkService, BlogService blogService, String url, BlogSource blogSource) {
        try {
            if (StringUtils.isEmpty(url)) {
                return ;
            }
            synchronized (this) {
                if(threadNum < THREAD_MAX_SIZE) {
                    boolean flag = true;
                    while (flag) {
                        if(blogSource == BlogSource.ALIYUN) {
                            BlogThread blogT = new BlogThread(blogService, linkService, url, BlogSource.ALIYUN);
                            Thread blogThread = new Thread(blogT);
                            blogThread.setName("[BlogTask-"+threadNum+"]");
                            blogThread.start();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("[BLOG任务] 录入blog异常, 参数: url={}", url);
        }
        logger.info("[BLOG任务] 操作完毕, url={}", url);
    }
}
