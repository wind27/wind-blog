package com.wind.blog.service.base;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.common.RabbitMqConfig;
import com.wind.blog.model.emun.MsgType;
import com.wind.blog.model.emun.QueueName;
import com.wind.blog.model.rabbitmq.Msg;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisService
 *
 * @author qianchun 2018/9/4
 **/
@Service
public class RabbitmqService {
    private final static Logger logger = LoggerFactory.getLogger(RabbitmqService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void send(Msg msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT_BLOGLINKPARSE,
                RabbitMqConfig.ROUTING_BLOGLINKPARSE, JSONObject.toJSON(msg));
        logger.info("[RABBITMQ] send msg: {}", JSONObject.toJSON(msg));
    }

}
