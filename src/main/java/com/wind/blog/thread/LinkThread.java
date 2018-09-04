package com.wind.blog.thread;

import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.model.emun.QueueName;
import com.wind.blog.msg.Msg;
import com.wind.blog.rabbitmq.RabbitmqConfig;
import com.wind.blog.redis.RedisService;
import com.wind.blog.task.LinkTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(LinkThread.class);

    // private RedisService redisService;

    // private RabbitmqConfig linkProvider;
    private LinkTask linkTask;

    private int blogSource;

    private String threadName;

    private String url;

    public static volatile int threadNum = 0;

    public static final int THREAD_MAX_SIZE = 10;

    public LinkThread(LinkTask linkTask, String url, int blogSource) {
        this.linkTask = linkTask;
        this.blogSource = blogSource;
        this.url = url;
        this.threadName = Thread.currentThread().getName();
        logger.info("[LINK解析线程] 启动 threadName:{}, threadNum={}, url={}", threadName, ++threadNum, url);
    }

    @Override
    public void run() {
        try {
            if (StringUtils.isEmpty(url)) {
                logger.error("[LINK解析线程] 参数错误, threadName={}, url={}", threadName, url);
                this.close();
                return;
            }

            // 处理URL
            if (BlogSource.ALIYUN.getValue() == blogSource) {
                url = this.dealUrl(Constant.ALIYUN_REGEX, url);
            } else if (BlogSource.CSDN.getValue() == blogSource) {

            }

            List<String> blogUrlList = AliyunBlogService.getBlogURLFromPage(url);
            if (!CollectionUtils.isEmpty(blogUrlList)) {
                for (String blogUrl : blogUrlList) {
                    linkTask.send(blogUrl);
                }
            }
            logger.info("[LINK解析线程] 完成, threadName={}, url={}", threadName, url);
            this.close();
        } catch (Exception e) {
            logger.error("[LINK解析线程] 异常, threadName={}, url={}, 异常={}", threadName, url, e);
            this.close();
        }
    }

    /**
     * 正则处理链接URL
     *
     * @param urlRegx 链接正则
     * @param url 链接URL
     * @return 返回结果
     */
    private String dealUrl(String urlRegx, String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        url = url.trim();
        Pattern pattern = Pattern.compile(urlRegx);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            url = url.substring(matcher.start(), matcher.end());
            return url;
        } else {
            return null;
        }
    }

    /**
     * 线程结束
     */
    private void close() {
        logger.info("[LINK解析线程] 结束 threadName={}, threadNum={}, url:{}", threadName, --threadNum, url);
    }
}
