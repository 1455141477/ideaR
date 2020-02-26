package cn.itcast.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
/*@PropertySource("classpath:application.properties")*/
@EnableConfigurationProperties(JdbcProperties.class)
public class JdbcConfig {
    /*//方式一
    @Value("${jdbc.driverClassName}")
    String driverClassName;
    @Value("${jdbc.url}")
    String url;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;*/
    //方式二
    /*@Autowired
    private JdbcProperties prop;
*//*    public JdbcConfig(JdbcProperties jdbcProperties){
        this.prop=jdbcProperties;
    }*//*
    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(prop.getDriverClassName());
        dataSource.setUrl(prop.getUrl());
        dataSource.setUsername(prop.getUsername());
        dataSource.setPassword(prop.getPassword());
        return dataSource;
    }*/
    //方式三
    @Bean
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource getDataSource() {
        return new DruidDataSource();
    }
}
