package com.wind.blog.controller;

import com.wind.blog.common.Constant;
import com.wind.blog.es.BlogESVO;
import com.wind.blog.es.BlogRepository;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@RestController
@Controller("blog")
public class BlogController {
    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogRepository blogRepository;

    /**
     * blog 详情
     *
     * @param id 主键
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(@RequestParam("id") long id) {
        try {
            if (id <= 0) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }

            BlogESVO blogESVO = blogRepository.findOne(id);
            if (blogESVO == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }

            return JsonResponseUtil.ok(blogESVO);
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 列表
     * @param title 标题
     * @param content 内容
     * @param keyword 关键字
     * @param start 分页起始
     * @param limit 分页大小
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@RequestParam("title") String title, @RequestParam("content") String content,
            @RequestParam("keyword") String keyword, @RequestParam("id") int start, @RequestParam("limit") int limit) {
        try {
            if (start < 0) {
                start = 0;
            }
            if (limit < 0) {
                limit = Constant.LIMIT_20;
            }
            FunctionScoreQueryBuilder builder = QueryBuilders.functionScoreQuery();

            if (StringUtils.isNotEmpty(title)) {
                builder.add(QueryBuilders.matchPhraseQuery("title", title),
                        ScoreFunctionBuilders.weightFactorFunction(100));
            }

            if (StringUtils.isNotEmpty(content)) {
                builder = builder.add(QueryBuilders.matchPhraseQuery("content", content),
                        ScoreFunctionBuilders.weightFactorFunction(100));
            }

            if (StringUtils.isNotEmpty(keyword)) {
                builder = builder.add(QueryBuilders.matchPhraseQuery("keyword", keyword),
                        ScoreFunctionBuilders.weightFactorFunction(100));
            }
            // 设置权重分 求和模式
            builder.scoreMode("sum");
            // 设置权重分最低分
            builder.setMinScore(10);

            // 设置分页
            Pageable pageable = new PageRequest(start, limit);
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).build();
            Page<BlogESVO> page = blogRepository.search(searchQuery);
            List<BlogESVO> list = page.getContent();
            return JsonResponseUtil.ok(list);
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }
}