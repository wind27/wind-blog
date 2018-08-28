package com.wind.blog.thread;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.Blog;
import com.wind.blog.model.Link;
import com.wind.blog.model.emun.BlogFrom;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.msg.Msg;
import com.wind.blog.task.AliyunTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(LinkThread.class);

    private BlogMapperEx blogMapperEx;

    private LinkMapperEx linkMapperEx;

    private String url;

    private BlogFrom blogFrom;

    private String threadName;

    private String aliyunRegx = "https://www.aliyun.com/jiaocheng/[0-9]+.html";

    public LinkThread(BlogMapperEx blogMapperEx, LinkMapperEx linkMapperEx, String url, BlogFrom blogFrom) {
        this.blogMapperEx = blogMapperEx;
        this.linkMapperEx = linkMapperEx;
        this.blogFrom = blogFrom;
        this.url = url;
        this.threadName = Thread.currentThread().getName();
        logger.info("[链接解析线程] 启动 threadName:{}, threadNum={}, url={}", threadName, AliyunTask.threadSize, url);
    }

    @Override
    public void run() {
        try {
            if (StringUtils.isEmpty(url)) {
                logger.error("[链接解析线程] 参数错误, threadName={}, url={}", threadName, url);
                this.close();
                return;
            }
            // 处理URL
            if (BlogFrom.ALIYUN.equals(blogFrom)) {
                url = this.dealUrl(aliyunRegx, url);
            }

            boolean exists = false;
            if (exists) {
                logger.info("[链接解析线程] 已存在, threadName={}, url={}", threadName, url);
                this.close();
                return;
            }

            // 如果URL是 csdn blog 的文章地址, 则爬取文章地址, 并将文章信息录入库中
            Link link = linkMapperEx.findByUrl(url);
            if (link == null) {
                link = new Link();
                link.setSource(BlogFrom.ALIYUN.getValue());
                link.setUrl(url);
                link.setIsParse(Constant.LINK_IS_PARSE.NO);
                linkMapperEx.insert(link);
                Blog blog = AliyunBlogService.parse(url);
                if (blog == null) {
                    logger.info("[链接解析线程] 解析失败, threadName={}, url={}", threadName, url);
                    this.close();
                    return;
                }

                String body = JSONObject.toJSONString(blog);
                Msg msg = new Msg();
                msg.setMsgType(MsgType.BLOG_ADD);
                msg.setBody(body);

                // rabbitmq 发送消息
            }
            logger.info("[链接解析线程] 完成, threadName={}, url={}", threadName, url);
            this.close();
        } catch (Exception e) {
            logger.error("[链接解析线程] 异常, threadName={}, url={}, 异常={}", threadName, url, e);
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

        // article url，解析 article 并录入 article 集合
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
        AliyunTask.threadSize -= 1;
        logger.info("[链接解析线程] 结束 threadName={}, threadNum={}, url:{}", threadName, AliyunTask.threadSize, url);
    }
}
