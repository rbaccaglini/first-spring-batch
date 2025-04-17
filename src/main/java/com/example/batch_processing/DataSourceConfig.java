package com.example.batch_processing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springDS() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource applicationDS() {
        return DataSourceBuilder.create().build();
    }

    @Qualifier("applicationJdbcTemplate")
    @Bean
    public JdbcTemplate applicationJdbcTemplate(@Qualifier("applicationDS") DataSource applicationDS) {
        return new JdbcTemplate(applicationDS);
    }

}
