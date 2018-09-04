package com.wind.blog.task;

import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.rabbitmq.LinkProvider;
import com.wind.blog.redis.RedisService;
import com.wind.blog.service.BlogService;
import com.wind.blog.service.LinkService;
import com.wind.blog.thread.LinkThread;
import com.wind.blog.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class LinkTask {
    private final static Logger logger = LoggerFactory.getLogger(LinkTask.class);

    public static volatile Integer threadNum = 0;

    /**
     * 解析blogURL, 并发送rabbitmq
     * 
     * @param linkProvider linkProvider
     */
    public void parseBlogUrl(RedisService redisService, LinkProvider linkProvider, BlogSource blogSource) {
        List<String> catalogList = AliyunBlogService.catalogList;
        for(String catalog: catalogList) {
            if(StringUtils.isEmpty(catalog)) {
                continue;
            }
            parseBlogUrlByCatalog(redisService, linkProvider, catalog, blogSource);
        }
    }

    /**
     * 根据类型解析blogURL
     * 
     * @param linkProvider linkProvider
     * @param catalog 分类
     */
    private void parseBlogUrlByCatalog(RedisService redisService, LinkProvider linkProvider, String catalog, BlogSource blogSource) {
        if (blogSource == null || (blogSource != BlogSource.ALIYUN) && blogSource != BlogSource.CSDN) {
            logger.error("[LINK任务] blogSource 不正确, 参数: blogSource={}", blogSource);
            return;
        }

        if (!AliyunBlogService.catalogList.contains(catalog)) {
            logger.error("[LINK任务] catalog 不正确, 参数: catalog={}", catalog);
            return;
        }

        String url = null;
        boolean flag = true;
        int num = 0;
        while (flag) {
            try {
                synchronized (LinkTask.class) {
                    if (LinkThread.threadNum >= Constant.THREAD_MAX_SIZE) {
                        Thread.sleep(100);
                    }
                    if (blogSource == BlogSource.ALIYUN) {
                        url = AliyunBlogService.getUrl(catalog, ++num);
                    }
                    if (!HttpUtil.checkUrlEnable(url)) {
                        break;
                    }
                    Thread linkTaskThread = new Thread(new LinkThread(redisService, linkProvider, url, blogSource.getValue()));
                    linkTaskThread.setName("[LinkTask-" + LinkTask.threadNum + "]");
                    linkTaskThread.start();
                }
            } catch (Exception e) {
                logger.error("[LINK任务] link 解析异常, 参数: url={}", url);
            }
        }
        logger.error("[LINK任务] 解析完成, 参数: catalog={}", catalog);
    }
}
