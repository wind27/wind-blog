package com.wind.blog.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * BlogRepository
 *
 * @author qianchun 2018/9/21
 **/
@Repository
public interface BlogRepository extends ElasticsearchRepository<BlogESVO, Long> {


}
