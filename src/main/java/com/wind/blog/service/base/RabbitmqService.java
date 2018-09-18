package com.wind.blog.service.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.wind.blog.config.RabbitMqConfig;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.model.rabbitmq.Msg;
import com.wind.blog.thread.BlogParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private BlogParseService blogParseService;

    @Autowired
    public Queue userQueue;

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void send(Msg msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT_BLOGLINKPARSE,
                RabbitMqConfig.ROUTING_BLOGLINKPARSE, JSONObject.toJSONString(msg));
        logger.info("[RABBITMQ] send msg: {}", msg);
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(userQueue);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                if(body == null) {
                    return ;
                }
                String content = new String(body);
                logger.info("receive msg : " + content);
                JSONObject json = JSONObject.parseObject(content);
                Msg msg = JSONObject.toJavaObject(json, Msg.class);
                if (msg != null) {
                    Thread.sleep(1000);
                    blogParseService.start(msg.getBody(), BlogSource.ALIYUN);
                }
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
            }
        });
        return container;
    }
}
