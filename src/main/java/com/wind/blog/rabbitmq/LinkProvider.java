package com.wind.blog.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.common.RabbitMqConfig;
import com.wind.blog.msg.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * Provider
 *
 * @author qianchun 2018/8/13
 **/
@Service
@Configuration
public class LinkProvider implements RabbitTemplate.ConfirmCallback {
    private static Logger logger = LoggerFactory.getLogger(LinkProvider.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public LinkProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);

    }

    /**
     * 回调方法
     * 
     * @param correlationData correlationData
     * @param ack ack
     * @param cause cause
     */
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("[RABBITMQ] confirm 消息成功消费");
        } else {
            logger.info("[RABBITMQ] confirm 消息消费失败:" + cause);
        }
    }

    /**
     * send msg
     * 
     * @param msg 消息
     */
    public void send(Msg msg) {
        if (msg == null) {
            return;
        }
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT_BLOGLINKPARSE,
                RabbitMqConfig.ROUTING_BLOGLINKPARSE, JSONObject.toJSON(msg));
        logger.info("[RABBITMQ] send msg: {}", JSONObject.toJSON(msg));
    }
}
