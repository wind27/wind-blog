package com.wind.blog.task;

import com.wind.blog.common.Constant;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import com.wind.blog.model.emun.BlogSource;
import com.wind.blog.thread.LinkThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LinkMain
 *
 * @author qianchun 2018/8/28
 **/
public class AliyunTask {
    private final static Logger logger = LoggerFactory.getLogger(AliyunTask.class);

    @Autowired
    private BlogMapperEx blogMapperEx;

    @Autowired
    private LinkMapperEx linkMapperEx;

    private String linuxUrl = "https://www.aliyun.com/jiaocheng/linux";

    private String javaUrl = "https://www.aliyun.com/jiaocheng/java";

    ExecutorService pool = Executors.newFixedThreadPool(Constant.LINK_MAX_THREAD);

//    public static Integer threadSize = 0;

    public static Set<String> allUrls = new HashSet<>();

    public void init() {
        int start = 0;
        int limit = 20;

        int pageNum = 1;
        String url = "https://www.aliyun.com/jiaocheng/java-" + pageNum + ".html";

    }


    /**
     * 启动线程池
     *
     * @param url 链接
     */
    public void startThreadPool(String url) {
//        Thread t = new Thread(new LinkThread(blogMapperEx, linkMapperEx, url, BlogSource.ALIYUN));
//        pool.execute(t);
//        AliyunTask.threadSize += 1;
    }

    /**
     * 睡眠
     * 
     * @param millisecond 毫秒
     */
    public void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
