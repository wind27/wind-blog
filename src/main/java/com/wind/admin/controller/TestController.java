package com.wind.admin.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Value("${dubbo.registry.address}")
    private String dubboRegistryAddress;

    @Value("${redis.hostName}")
    private String redisHostName;

    @Value("${auth.datasource.driver.name}")
    private String driverName;


    @ResponseBody
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String home() {

        Map<String, Object> map = new HashMap<>();
        map.put("dubboRegistryAddress", dubboRegistryAddress);
        map.put("redisHostName", redisHostName);
        map.put("driverName", driverName);
        return JSONObject.toJSON(map).toString();
    }
}