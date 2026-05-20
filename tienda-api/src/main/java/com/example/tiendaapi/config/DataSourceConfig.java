package com.example.tiendaapi.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties(DataSourceProperties properties) {
        String url = properties.getUrl();
        if (url != null) {
            if (url.contains("?")) {
                if (!url.contains("useSSL=")) {
                    url += "&useSSL=true&trustServerCertificate=true";
                }
            } else {
                url += "?useSSL=true&trustServerCertificate=true";
            }
            properties.setUrl(url);
        }
        return properties;
    }
}
