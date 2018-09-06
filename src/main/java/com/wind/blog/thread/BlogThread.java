package com.wind.blog.thread;

import com.wind.blog.source.aliyun.AliyunParser;
import com.wind.blog.common.Constant;
import com.wind.blog.model.Blog;
import com.wind.blog.model.Link;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.service.BlogService;
import com.wind.blog.service.LinkService;
import com.wind.blog.task.BlogTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlogThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BlogThread.class);

    private BlogService blogService;

    private LinkService linkService;

    private String url;

    private BlogSource blogSource;

    private String threadName;

    /**
     * 构造方法
     * @param blogService blogService
     * @param linkService linkService
     * @param url 解析 URL
     * @param blogSource 来源
     */
    public BlogThread(BlogService blogService, LinkService linkService, String url, BlogSource blogSource) {
        this.blogService = blogService;
        this.linkService = linkService;
        this.blogSource = blogSource;
        this.url = url;
        this.threadName = Thread.currentThread().getName();
        logger.info("[BLOG解析线程] 启动 threadName:{}, threadNum={}, url={}", threadName, ++BlogTask.threadNum, url);
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
            Link link = linkService.findByUrl(url);
            if (link == null) {
                link = new Link();
                link.setSource(blogSource.getValue());
                link.setUrl(url);
                link.setIsParse(Constant.LINK_IS_PARSE.YES);


                Blog blog = null;
                if(blogSource == BlogSource.ALIYUN) {
                    blog = AliyunParser.getBlogFromUrl(url);
                } else if(blogSource == BlogSource.CSDN) {

                }


                if (blog == null) {
                    logger.info("[BLOG解析线程] 解析失败, threadName={}, url={}", threadName, url);
                    this.close();
                    return;
                }
                blogService.add(blog);
                link.setBlogId(blog.getId());
                linkService.add(link);
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
        logger.info("[BLOG解析线程] 结束 threadName={}, threadNum={}, url:{}", threadName, --BlogTask.threadNum, url);
    }
}
