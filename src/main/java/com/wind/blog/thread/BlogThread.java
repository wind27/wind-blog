package com.wind.blog.thread;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.Blog;
import com.wind.blog.model.Link;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.msg.Msg;
import com.wind.blog.task.AliyunTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BlogThread.class);

    private BlogMapperEx blogMapperEx;

    private LinkMapperEx linkMapperEx;

    private String url;

    private BlogSource blogFrom;

    private String threadName;

    private String ALIYUN_BLOG_URL_REGX = "https://www.aliyun.com/jiaocheng/[0-9]+.html";

    public BlogThread(BlogMapperEx blogMapperEx, LinkMapperEx linkMapperEx, String url, BlogSource blogFrom) {
        this.blogMapperEx = blogMapperEx;
        this.linkMapperEx = linkMapperEx;
        this.blogFrom = blogFrom;
        this.url = url;
        this.threadName = Thread.currentThread().getName();
        logger.info("[BLOG解析线程] 启动 threadName:{}, threadNum={}, url={}", threadName, AliyunTask.threadSize, url);
    }

    @Override
    public void run() {
        try {
            if (StringUtils.isEmpty(url)) {
                logger.error("[BLOG解析线程] 参数错误, threadName={}, url={}", threadName, url);
                this.close();
                return;
            }

            boolean exists = false;
            if (exists) {
                logger.info("[BLOG解析线程] 已存在, threadName={}, url={}", threadName, url);
                this.close();
                return;
            }

            // 如果URL是 csdn blog 的文章地址, 则爬取文章地址, 并将文章信息录入库中
            Link link = linkMapperEx.findByUrl(url);
            if (link == null) {

                link = new Link();
                link.setSource(BlogSource.ALIYUN.getValue());
                link.setUrl(url);
                link.setIsParse(Constant.LINK_IS_PARSE.YES);

                Blog blog = AliyunBlogService.getBlogFromUrl(url);
                if (blog == null) {
                    logger.info("[BLOG解析线程] 解析失败, threadName={}, url={}", threadName, url);
                    this.close();
                    return;
                }
                blogMapperEx.insert(blog);
                linkMapperEx.insert(link);
            }
            logger.info("[BLOG解析线程] 完成, threadName={}, url={}", threadName, url);
            this.close();
        } catch (Exception e) {
            logger.error("[BLOG解析线程] 异常, threadName={}, url={}, 异常={}", threadName, url, e);
            this.close();
        }
    }

    /**
     * 线程结束
     */
    private void close() {
        AliyunTask.threadSize -= 1;
        logger.info("[BLOG解析线程] 结束 threadName={}, threadNum={}, url:{}", threadName, AliyunTask.threadSize, url);
    }
}
