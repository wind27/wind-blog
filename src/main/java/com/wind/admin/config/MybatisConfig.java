package com.wind.admin.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.wind.admin.db.DynamicDataSource;
import com.wind.admin.emun.DatabaseCatalog;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * springboot集成mybatis: 1 创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要) 2 创建SqlSessionFactory 3 配置事务管理器，除非需要使用事务，否则不用配置
 *
 * @author qianchun
 * @date 2019/1/29
 **/
@Configuration // 配置
//@PropertySource("classpath:db.properties") // 用来指定配置文件的位置
@MapperScan(basePackages = { "com.wind.admin.dao" })
public class MybatisConfig {
    private final static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    /**
     * 多数据源
     */
    private Map<Object, Object> dataSourceMap = new HashMap<>();

    @Autowired
    private Environment env;

    /**
     * 获取数据源
     */
    private synchronized void createDataSource() {
        String catalogs = null;
        try {
            catalogs = env.getProperty("database.catlog");
            if (catalogs==null || catalogs.isEmpty()) {
                return;
            }

            String[] catalogArray = catalogs.split(",");
            for (String catalog : catalogArray) {
                DatabaseCatalog databaseCatalog = DatabaseCatalog.get(catalog);
                if (databaseCatalog==null || dataSourceMap.get(catalog) != null) {
                    continue;
                }
                String driver = env.getProperty(catalog + ".datasource.driver.name");
                String url = env.getProperty(catalog + ".datasource.url");
                String username = env.getProperty(catalog + ".datasource.username");
                String password = env.getProperty(catalog + ".datasource.password");

                if (StringUtils.isEmpty(driver)) {
                    logger.error("创建数据源catalog={}, driver 配置错误", catalog);
                    continue;
                } else if (StringUtils.isEmpty(url)) {
                    logger.error("创建数据源catalog={}, url 配置错误", catalog);
                    continue;
                } else if (StringUtils.isEmpty(username)) {
                    logger.error("创建数据源catalog={}, username 配置错误", catalog);
                    continue;
                } else if (StringUtils.isEmpty(password)) {
                    logger.error("创建数据源catalog={}, password 配置错误", catalog);
                    continue;
                }

                Properties props = new Properties();
                props.put("driverClassName", driver);
                props.put("url", url);
                props.put("username", username);
                props.put("password", password);
                logger.info("创建数据源catalog={}, 成功", catalog);
                System.out.println("创建数据源catalog=" + catalog + ", 成功");
                DataSource ds = DruidDataSourceFactory.createDataSource(props);
                dataSourceMap.put(databaseCatalog, ds);

            }

        } catch (Exception e) {
            logger.error("创建数据源 catalogs={}, 异常", catalogs, e);
        }
    }

    /**
     * 设置数据源
     *
     * @return 返回结果
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource() {
        this.createDataSource();

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        // 默认的datasource设置
        dynamicDataSource.setDefaultTargetDataSource(null);
        return dynamicDataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     * 
     * @param dataSource 数据源
     * @return 返回结果
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) {
        try {
            // 指定数据源(这个必须有，否则报错)
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            logger.error("创建sqlSessionFactory异常", e);
            return null;
        }

        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        // fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        // fb.setMapperLocations(
        // new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));//
    }

    /**
     * 配置事务管理器
     * 
     * @param dataSource dataSource
     * @return 返回结果
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
        try {
            return new DataSourceTransactionManager(dataSource);
        } catch (Exception e) {
            logger.error("创建 transactionManager 异常", e);
            return null;
        }
    }
}
