package com.wind.admin.aspect;

import com.wind.admin.db.DatabaseContextHolder;
import com.wind.admin.emun.DatabaseCatalog;
import com.wind.annotation.DAO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 面向切面 DataSource 多数据源切换
 *
 * @author qianchun
 * @date 2019/1/29
 **/
@Aspect
@Component
public class DataSourceAspect {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution( * com.wind.*.dao.*.*(..))")
    private void adaptDB(){
        System.out.println("------------------");
    }


    @Before("adaptDB()")
    public void adaptDBByDAOCataLog(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DAO dao = AnnotationUtils.findAnnotation(method.getDeclaringClass(), DAO.class);

        if (dao == null) {
            logger.error("获取Dao注解失败, 请检查配置! class={}", method.getDeclaringClass().getName());
            return;
        }
        String catalog = dao.catalog();

        DatabaseCatalog databaseType = DatabaseCatalog.get(catalog);
        if (databaseType != null) {
            DatabaseContextHolder.setDatabaseType(databaseType);
        }
    }
}
