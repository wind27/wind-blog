package com.wind.blog.controller;

import com.wind.blog.es.BlogESVO;
import com.wind.blog.model.Blog;
import com.wind.blog.service.RestHighLevelClientService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BlogController Tester.
 * 
 * @author <Authors name>
 * @since
 * 
 *        <pre>
 * 十一月 26, 2018
 *        </pre>
 * 
 * @version 1.0
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "javax.management.*", "javax.security.*", "javax.net.ssl.*" })
public class BlogControllerTest {

    private  String INDEX = "blog";
    private  String TYPE = Blog.class.getName();

    private MockMvc mockMvc;

    @InjectMocks
    private BlogController blogController;

    @Mock
    private RestHighLevelClientService restHighLevelClientService;

    @Mock
    private List mockList;

    @Before
    public void before() throws Exception {
//        blogController = new BlogController();

        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();



//        Whitebox.setInternalState(blogController, "restHighLevelClientService", restHighLevelClientService);


    }

    @After
    public void after() throws Exception {
    }

    private void initRequestParams(MockHttpServletRequestBuilder requestBuilder, Map<String, String> params) {
        // 设置编码格式
        requestBuilder.characterEncoding("utf-8");
        if (params != null) {
            Set<Map.Entry<String, String>> sets = params.entrySet();
            StringBuilder paramBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : sets) {
                paramBuilder.append('&');
                paramBuilder.append(entry.getKey());
                paramBuilder.append('=');
                paramBuilder.append(entry.getValue());
            }
            String bodyData = paramBuilder.deleteCharAt(0).toString();
            System.out.println(bodyData);
            requestBuilder.content(bodyData);// 这里是重点
            requestBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED);// 这里是重点

            for (Map.Entry<String, String> entry : sets) {
                requestBuilder.param(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Method: detail(@RequestParam("id") Long id)
     */
    @Test
    public void testDetail() throws Exception {
        String url = "/blog/detail";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        Map<String, String> params = new HashMap<>();
        params.put("id", "2");
        initRequestParams(requestBuilder, params);

        BlogESVO vo = new BlogESVO();
        vo.setId(2L);
        vo.setTitle("test");
        when(restHighLevelClientService.findById(INDEX, TYPE, "2")).thenReturn(vo);
        BlogESVO blog = restHighLevelClientService.findById(INDEX, TYPE, "2");

        ResultActions actions = mockMvc.perform(requestBuilder);
        String result = actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

        System.out.println(result);


    }

    /**
     * Method: exists(@RequestParam("id") Long id)
     */
    @Test
    public void testExists() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: delete(@RequestParam("id") Long id)
     */
    @Test
    public void testDelete() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: add(@RequestParam("id") Long id)
     */
    @Test
    public void testAdd() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: bulkAadd(@RequestParam("ids") String ids)
     */
    @Test
    public void testBulkAadd() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: list(@RequestParam("title") String title, @RequestParam("content") String
     * content, @RequestParam("keyword") String keyword, @RequestParam("id") int start, @RequestParam("limit") int
     * limit)
     */
    @Test
    public void testList() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        // TODO: Test goes here...
    }

}
