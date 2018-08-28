package com.wind.blog.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.model.Blog;
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
        String data = HttpUtil.doGet(url, headers);

        Blog blog = new Blog();
        return blog;
    }

	/**
	 * 解析 blog URL
	 * @param url 当前URL
	 * @return 返回结果
     */
	public static List<String> getBlogURLFromPage(String url) {
		Map<String, String> headers = HttpUtil.getHeader();
		String html = HttpUtil.doGet(url, headers);
		if(StringUtils.isNotEmpty(html)) {
			return filterURL(html);
		}
		return null;
	}

	/**
	 * HTML 解析 URL
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

    public static void main(String[] args) {
		List<String> urlList = new ArrayList<>();
		int pageNum = 1;
		while (true) {
			String url = "https://www.aliyun.com/jiaocheng/java-" + pageNum + ".html";
			List<String> urls = AliyunBlogService.getBlogURLFromPage(url);
			if(CollectionUtils.isEmpty(urls) || pageNum==50) {
				break;
			}
			urlList.addAll(urls);
			System.out.println("当前请求 url = " + url + ", size = " + urls.size());
			pageNum ++;
		}
		System.out.println("当前请求 url = " + JSONObject.toJSON(urlList));

//        service.getUrls(
//                "<div class=\"content0\"data-spm=\"9\"><div class=\"content1\"><div class=\"list\"><a href=\"https://www.aliyun.com\">阿里云&nbsp;&nbsp;</a>&gt;<a href=\"https://www.aliyun.com/jiaocheng\">&nbsp;&nbsp;教程中心&nbsp;&nbsp;</a>&gt;<a href=\"https://www.aliyun.com/jiaocheng/java\">&nbsp;&nbsp;Java教程</a></div><ul class=\"content-ul\"><li><div class=\"title-container\"><span class=\"title\"><a href=\"https://www.aliyun.com/jiaocheng/870657.html\">Java十位大师级人物</a></span></div></li><li><div class=\"title-container\"><span class=\"title\"><a href=\"https://www.aliyun.com/jiaocheng/870658.html\">树之极品——哈夫曼树</a></span></div></li><li><div class=\"title-container\"><span class=\"title\"><a href=\"https://www.aliyun.com/jiaocheng/870659.html\">python学习2----类和对象</a></span></div></li></ul></div></div>");

    }
}
