package com.wind.blog.controller;

import com.wind.blog.common.Constant;
import com.wind.blog.es.BlogESVO;
import com.wind.blog.model.Blog;
import com.wind.blog.service.RestHighLevelClientService;
import com.wind.commons.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * LinkController
 *
 * @author qianchun 2018/8/28
 **/
@RestController
public class BlogController {
    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);

    private static final String INDEX = "blog";
    private static final String TYPE = Blog.class.getName();

    @Autowired
    private RestHighLevelClientService restHighLevelClientService;

    /**
     * blog 详情
     * 
     * @param id 主键
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "blog/detail", method = RequestMethod.GET)
    public String detail(@RequestParam("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Map<String, Object> data = new HashMap<>();
            BlogESVO blog = restHighLevelClientService.findById(INDEX, TYPE, id.toString());
            data.put("blog", blog);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("BLOG detail 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "blog/exists", method = RequestMethod.GET)
    public String exists(@RequestParam("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Map<String, Object> data = new HashMap<>();

            boolean flag = restHighLevelClientService.existsById(INDEX, TYPE, id.toString());
            if (flag) {
                return JsonResponseUtil.ok();
            }
            return JsonResponseUtil.fail(ErrorCode.NOT_EXISTS);
        } catch (Exception e) {
            logger.error("BLOG exists 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "blog/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            boolean flag = restHighLevelClientService.deleteById(INDEX, TYPE, id.toString());
            if(flag){
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG delete 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "blog/add", method = RequestMethod.GET)
    public String add(@RequestParam("id") Long id) {
        try {
            if (id == null) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            boolean flag = restHighLevelClientService.existsById(INDEX, TYPE, id.toString());
            if(flag) {
                return JsonResponseUtil.fail(ErrorCode.EXISTS);
            }
            Date date = new Date();
            Blog blog = new Blog();
            blog.setId(id);
            blog.setTags("tags");
            blog.setTitle("title");
            blog.setSummary("summary");
            blog.setContent("content");
            blog.setCreateTime(date);
            blog.setUpdateTime(date);
            blog.setStatus(1);
            flag = restHighLevelClientService.add(INDEX, TYPE, blog);
            if(flag){
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG add 异常, 参数: id={}", id, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "blog/bulk/add", method = RequestMethod.GET)
    public String bulkAadd(@RequestParam("ids") String ids) {
        try {
            if (StringUtils.isEmpty(ids)) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            Date date = new Date();
            List<Blog> list = new ArrayList<>();
            List<String> idList = Arrays.asList(ids.split(","));
            for(String id : idList) {
                Blog blog = new Blog();
                blog.setId(Long.parseLong(id));
                blog.setTags("tags"+id);
                blog.setTitle("title"+id);
                blog.setSummary("summary"+id);
                blog.setContent("content"+id);
                blog.setCreateTime(date);
                blog.setUpdateTime(date);
                blog.setStatus(1);
                list.add(blog);
            }

            boolean flag = restHighLevelClientService.bulkAdd(INDEX, TYPE, list);
            if(flag){
                return JsonResponseUtil.ok();
            } else {
                return JsonResponseUtil.fail();
            }
        } catch (Exception e) {
            logger.error("BLOG add 异常, 参数: id={}", ids, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "blog/delete", method = RequestMethod.GET)
//    public String deleteMult(@RequestParam("keyword") String keyword) {
//        try {
//            if (StringUtils.isEmpty(keyword)) {
//                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
//            }
//
//            Map<String, Object> data = new HashMap<>();
//
//
//            BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(restClient)
//                    .filter(QueryBuilders.matchQuery("gender", "male")).source("persons").get();
//
//            return JsonResponseUtil.ok();
//        } catch (Exception e) {
//            logger.error("BLOG detail 异常, 参数: id={}", id, e);
//            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
//        }
//    }

    //
    // @Autowired
    // private BlogRepository blogRepository;
    //
    // /**
    // * blog 详情
    // *
    // * @param id 主键
    // * @return 返回结果
    // */
    // @ResponseBody
    // @RequestMapping(value = "detail", method = RequestMethod.GET)
    // public String detail(@RequestParam("id") long id) {
    // try {
    // if (id <= 0) {
    // return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
    // }
    //
    // BlogESVO blogESVO = blogRepository.findOne(id);
    // if (blogESVO == null) {
    // return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
    // }
    //
    // return JsonResponseUtil.ok(blogESVO);
    // } catch (Exception e) {
    // logger.error("[LINK任务] 录入link异常, 参数: url={");
    // return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
    // }
    // }
    //
    /**
     * 列表
     * 
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
            Map<String, Object> data = new HashMap<>();
            List<BlogESVO> list = new ArrayList<>();
            if (start < 0) {
                start = 0;
            }
            if (limit < 0) {
                limit = Constant.LIMIT;
            }

            // 设置分页
            // Pageable pageable = new PageRequest(start, limit);
            // SearchQuery searchQuery = new
            // NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).build();
            // Page<BlogESVO> page = blogRepository.search(searchQuery);
            // List<BlogESVO> list = page.getContent();
            // return JsonResponseUtil.ok(list);

            data.put("list", list);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("[LINK任务] 录入link异常, 参数: url={");
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    // ------------------------------------------------------------------------------------------------------------------

    // routing value
    // request.routing("routing");

    // parent value
    // request.parent("parent");

    // preference value
    // request.preference("preference");

    // set realtime flag to false (true by default)
    // request.realtime(false);

    // perform a refresh before retrieving the document (false by default)
    // request.refresh(true);

    // version
    // request.version(2);

    // set store fileds
    // get.storedFields("message");

    // 设置source include and exclude fileds
    // get.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
    // String[] includes = new String[]{"message", "*Date"};
    // String[] excludes = Strings.EMPTY_ARRAY;
    // FetchSourceContext fetchSourceContext =
    // new FetchSourceContext(true, includes, excludes);
    // get.fetchSourceContext(fetchSourceContext);

    // Synchronous Executionedit
    // GetResponse getResponse = esConfig.getRestHighLevelClient().get(request, RequestOptions.DEFAULT);

    // Asynchronous Executionedit
    // esConfig.getRestHighLevelClient().getAsync(request, RequestOptions.DEFAULT,
    // new ActionListener<GetResponse>() {
    // @Override
    // public void onResponse(GetResponse getResponse) {
    //
    // }
    //
    // @Override
    // public void onFailure(Exception e) {
    //
    // }
    // });


    public static void main(String[] args) {
        System.out.println(TYPE);
    }
}