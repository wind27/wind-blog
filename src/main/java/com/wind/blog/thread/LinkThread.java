package com.wind.blog.thread;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.Blog;
import com.wind.blog.model.Link;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.model.emun.QueueName;
import com.wind.blog.msg.Msg;
import com.wind.blog.rabbitmq.LinkProvider;
import com.wind.blog.service.BlogService;
import com.wind.blog.service.LinkService;
import com.wind.blog.task.AliyunTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(LinkThread.class);

    private LinkProvider linkProvider;

    private int blogSource;

    private String threadName;

    private String url;

    public static volatile int threadNum = 0;

    public static final int THREAD_MAX_SIZE = 10;

    public LinkThread(LinkProvider linkProvider, String url, int blogSource) {
        this.blogSource = blogSource;
        this.linkProvider = linkProvider;
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
            } else if(BlogSource.CSDN.getValue() == blogSource) {

            }

            List<String> blogUrlList = AliyunBlogService.getBlogURLFromPage(url);
            if (!CollectionUtils.isEmpty(blogUrlList)) {
                for (String blogUrl : blogUrlList) {
                    if (StringUtils.isEmpty(blogUrl)) {
                        continue;
                    }

                    boolean exists = false;
                    if(exists) {
                        continue;
                    }
                    Msg msg = new Msg();
                    msg.setMsgType(MsgType.BLOG_ADD);
                    msg.setBody(blogUrl);
                    msg.setQueueName(QueueName.QUEUE_BLOG_URL_PARSE);
                    linkProvider.send(msg);
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
