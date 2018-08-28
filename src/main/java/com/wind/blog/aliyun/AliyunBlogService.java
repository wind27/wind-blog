package com.wind.blog.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.model.Blog;
import com.wind.blog.model.emun.BlogFrom;
import com.wind.blog.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ObjectTag;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AliyunBlogService
 *
 * @author qianchun 2018/8/28
 **/
public class AliyunBlogService {
    private final static Logger logger = LoggerFactory.getLogger(AliyunBlogService.class);

    /**
     * url 解析 blog
     * 
     * @param url 链接
     * @return 返回结果
     */
    public static Blog parse(String url) {
        Map<String, String> headers = HttpUtil.getHeader();
        String html = HttpUtil.doGet(url, headers);

        if (StringUtils.isNotEmpty(html)) {
            return filterBlog(html);
        }
        Blog blog = new Blog();
        return blog;
    }

    /**
     * 解析 blog URL
     * 
     * @param url 当前URL
     * @return 返回结果
     */
    public static List<String> getBlogURLFromPage(String url) {
        Map<String, String> headers = HttpUtil.getHeader();
        String html = HttpUtil.doGet(url, headers);
        if (StringUtils.isNotEmpty(html)) {
            return filterURL(html);
        }
        return null;
    }

    /**
     * 解析 blog
     * 
     * @param url URL
     * @return 返回结果
     */
    public static Blog getBlogFromPage(String url) {
        Map<String, String> headers = HttpUtil.getHeader();
        String html = HttpUtil.doGet(url, headers);
        if (StringUtils.isNotEmpty(html)) {
            return filterBlog(html);
        }
        return null;
    }

    /**
     * HTML 解析 URL
     * 
     * @param html HTML
     * @return 返回结果
     */
    private static List<String> filterURL(String html) {
        List<String> urlList = new ArrayList<>();
        try {
            Parser parser = new Parser(html);
            CssSelectorNodeFilter filter = new CssSelectorNodeFilter("ul[class='content-ul'] li div span a");
            NodeList liList = parser.parse(filter);
            if (liList == null) {
                return urlList;
            }
            for (int i = 0; i < liList.size(); i++) {
                Tag link = (Tag) liList.elementAt(i);
                if (link instanceof LinkTag) {
                    String url = link.getAttribute("href");
                    urlList.add(url);
                }
            }
            return urlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HTML 解析 blog
     * 
     * @param html HTML
     * @return 返回结果
     */
    private static Blog filterBlog(String html) {
        Blog blog = new Blog();
        try {
            Parser parser = new Parser(html);
            CssSelectorNodeFilter titleFilter = new CssSelectorNodeFilter("ul[class='article'] li[class='zhinan']");// 标题
            NodeList titleNode = parser.parse(titleFilter);
            Tag titleTag = (Tag) titleNode.elementAt(0);
            if (titleTag != null) {
                String title = titleTag.getText();
                blog.setTitle(title);
            }

            CssSelectorNodeFilter summaryFilter = new CssSelectorNodeFilter("ul[class='article'] li[class='li3'] a");// 摘要
            NodeList summaryNode = parser.parse(summaryFilter);
            Tag summaryTag = (Tag) summaryNode.elementAt(0);
            if (summaryTag != null) {
                String summary = summaryTag.getText();
                blog.setSummary(summary);
            }

            CssSelectorNodeFilter contentFilter = new CssSelectorNodeFilter(
                    "ul[class='article'] li[class='article-content']");// 内容
            NodeList contentNode = parser.parse(contentFilter);
            Tag contentTag = (Tag) contentNode.elementAt(0);
            if (contentTag != null) {
                String content = contentTag.getText();
                blog.setContent(content);
            }

            blog.setCreateTime(new Date());
            blog.setPublishTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setSource(BlogFrom.ALIYUN.getValue());

            return blog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<String> urlList = new ArrayList<>();
        int pageNum = 1;
        while (true) {
            String url = "https://www.aliyun.com/jiaocheng/java-" + pageNum + ".html";
            List<String> urls = AliyunBlogService.getBlogURLFromPage(url);
            if (CollectionUtils.isEmpty(urls)) {
                break;
            }
            for (String tmp : urls) {
                Blog blog = getBlogFromPage(tmp);
                if (blog != null) {
                    System.out.println("当前请求 url = " + tmp + ", size = " + JSONObject.toJSON(blog));
                }
            }

			if(pageNum == 1) {
				break;
			}
            urlList.addAll(urls);
            pageNum++;
        }
        System.out.println("当前请求 url = " + JSONObject.toJSON(urlList));
    }
}
