package com.wind.blog.controller;

import com.wind.blog.aliyun.AliyunBlogService;
import com.wind.blog.mapper.BlogMapperEx;
import com.wind.blog.mapper.LinkMapperEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@RestController(value = "link")
public class LinkController {
    private String linuxUrl = "https://www.aliyun.com/jiaocheng/linux";

    private String javaUrl = "https://www.aliyun.com/jiaocheng/java";

    @ResponseBody
    @RequestMapping(value = "task", method = RequestMethod.GET)
    public void main() {
        int start = 0;
        int limit = 20;

//        int pageNum = 1;
//
//
//        while (true) {
//            String url = "https://www.aliyun.com/jiaocheng/java-" + pageNum + ".html";
//            List<String> urls = AliyunBlogService.getBlogURLFromPage(url);
//            if(CollectionUtils.isEmpty(urls)) {
//                break;
//            }
//            System.out.println("当前请求 url = " + url + ", size = " + urls.size());
//            pageNum ++;
//        }
//
//        while (true) {
//            // 分页处理
//            List<Link> links = null;
//            while (links == null || links.size() == 0) {
//                sleep(1000);
//
//                Map<String, Object> params = new HashMap<>();
//                params.put("is_parse", Constant.LINK_IS_PARSE.NO);
//                links = linkService.findByMap(params, 0, limit);
//                logger.info("**************************** start:{}, limit:{}, size:{} ****************************",
//                        start, limit, links.size());
//            }
//
//            if (links != null && links.size() > 0) {
//                for (Link link : links)
//                    if (link != null) {
//                        while (threadSize >= Constant.LINK_MAX_THREAD) {
//                            sleep(1000);
//                            continue;
//                        }
//                        startThreadPool(link);
//                    }
//            }
//        }
    }

}
