package com.wind.admin.common;

//import com.wind.admin.config.DynamicDataSource;
//import com.wind.admin.config.MybatisConfig;
//import com.wind.annotation.DAO;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;

/**
 * 获取Spring上下文对象
 *
 * @author qianchun
 * @date 2019/1/29
 **/
//@Component
public class ApplicationContextProvider /*implements ApplicationContextAware*/ {
    // private final static Logger logger = LoggerFactory.getLogger(ApplicationContextProvider.class);

//    /**
//     * 上下文对象实例
//     */
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        if (this.applicationContext == null) {
//            this.applicationContext = applicationContext;
//            MybatisConfig mybatisConfig = applicationContext.getBean(MybatisConfig.class);
//
//            // 获取工程中所有的DAO注解类
//            Set<String> beanNameSet = applicationContext.getBeansWithAnnotation(DAO.class).keySet();
//            if (CollectionUtils.isNotEmpty(beanNameSet)) {
//                for (String beanName : beanNameSet) {
//                    Object bean = applicationContext.getBean(beanName);
//                    DAO dao = AnnotationUtils.findAnnotation(bean.getClass(), DAO.class);
//                    if (dao != null) {
//                        mybatisConfig.createDataSource(dao.catalog());
//                    }
//                }
//            }
//
//            DynamicDataSource dynamicDataSource = mybatisConfig.dataSource();
//            SqlSessionFactory sqlSessionFactory = mybatisConfig.sqlSessionFactory(dynamicDataSource);
//            DataSourceTransactionManager transactionManager = mybatisConfig.transactionManager(dynamicDataSource);
//
//
//            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
//                    .getParentBeanFactory();
//
//            // 根据obj的类型、创建一个新的bean、添加到Spring容器中，
//            // 注意BeanDefinition有不同的实现类，注意不同实现类应用的场景
//            BeanDefinition beanDefinition = new GenericBeanDefinition();
//            beanDefinition.setBeanClassName(dynamicDataSource.getClass().getName());
//            beanFactory.registerBeanDefinition(dynamicDataSource.getClass().getName(), beanDefinition);
//
//            beanDefinition = new GenericBeanDefinition();
//            beanDefinition.setBeanClassName(sqlSessionFactory.getClass().getName());
//            beanFactory.registerBeanDefinition(sqlSessionFactory.getClass().getName(), beanDefinition);
//
//            beanDefinition = new GenericBeanDefinition();
//            beanDefinition.setBeanClassName(transactionManager.getClass().getName());
//            beanFactory.registerBeanDefinition(transactionManager.getClass().getName(), beanDefinition);
//        }
//    }
}
