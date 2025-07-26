package com.kr.moo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DBConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.instance1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

}
