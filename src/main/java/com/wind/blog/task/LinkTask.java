package com.wind.blog.task;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.common.Constant;
import com.wind.blog.common.RabbitMqConfig;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.model.emun.QueueName;
import com.wind.blog.msg.Msg;
import com.wind.blog.redis.RedisService;
import com.wind.blog.thread.LinkThread;
import com.wind.blog.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

/**
 * BlogService
 *
 * @author qianchun 2018/9/3
 **/
public class LinkTask {
    private final static Logger logger = LoggerFactory.getLogger(LinkTask.class);

    private RabbitTemplate rabbitTemplate;

    private RedisService redisService;

    public static volatile Integer threadNum = 0;


    public LinkTask(RabbitTemplate rabbitTemplate, RedisService redisService) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisService = redisService;
    }

    /**
     * 解析blogURL, 并发送rabbitmq
     * 
     * @param blogSource blogSource
     */
    public void parseBlogUrl(BlogSource blogSource) {
        List<String> catalogList = AliyunBlogService.catalogList;
        for (String catalog : catalogList) {
            if (StringUtils.isEmpty(catalog)) {
                continue;
            }
            parseBlogUrlByCatalog(catalog, blogSource);
        }
    }

    /**
     * 根据类型解析blogURL
     * 
     * @param blogSource blogSource
     * @param catalog 分类
     */
    private void parseBlogUrlByCatalog(String catalog, BlogSource blogSource) {
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
                    Thread linkTaskThread = new Thread(new LinkThread(this, url, blogSource.getValue()));
                    linkTaskThread.setName("[LinkTask-" + LinkTask.threadNum + "]");
                    linkTaskThread.start();
                }
            } catch (Exception e) {
                logger.error("[LINK任务] link 解析异常, 参数: url={}", url);
            }
        }
        logger.error("[LINK任务] 解析完成, 参数: catalog={}", catalog);
    }

    /**
     * send msg
     *
     * @param blogUrl blogUrl
     */
    public void send(String blogUrl) {

        if (StringUtils.isEmpty(blogUrl)) {
            return;
        }
        if (redisService.get(blogUrl) != null) {
            return;
        }
        redisService.set(blogUrl, blogUrl);
        Msg msg = new Msg();
        msg.setMsgType(MsgType.BLOG_ADD);
        msg.setBody(blogUrl);
        msg.setQueueName(QueueName.QUEUE_BLOG_URL_PARSE);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT_BLOGLINKPARSE,
                RabbitMqConfig.ROUTING_BLOGLINKPARSE, JSONObject.toJSON(msg));
        logger.info("[RABBITMQ] send msg: {}", JSONObject.toJSON(msg));
    }
}
