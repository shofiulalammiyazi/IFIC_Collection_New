package com.unisoft.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class ClientDbServerConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties primaryDbSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.configuration")
    public DataSource primaryDbSource() {
        return primaryDbSourceProperties().
                initializeDataSourceBuilder().
                type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "client.db.server1.datasource")
    public DataSourceProperties clientDbServer1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "clientDbServer1DataSource")
    @ConfigurationProperties(prefix = "client.db.server1.datasource")
    public DataSource clientDbServer1DataSource() {
        return clientDbServer1DataSourceProperties().
                initializeDataSourceBuilder().
                type(HikariDataSource.class).
                build();
    }

    @Bean
    @ConfigurationProperties(prefix = "client.db.server2.datasource")
    public DataSourceProperties clientDbServer2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "clientDbServer2DataSource")
    @ConfigurationProperties(prefix = "client.db.server2.datasource")
    public DataSource clientDbServer2DataSource() {
        return clientDbServer2DataSourceProperties().
                initializeDataSourceBuilder().
                type(HikariDataSource.class).
                build();
    }

    @Bean
    @ConfigurationProperties(prefix = "client.db.server4.datasource")
    public DataSourceProperties ificClientDBDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "clientDbServerIfic")
    @ConfigurationProperties(prefix = "client.db.server4.datasource")
    public DataSource ificClientDBDataSource() {
        return ificClientDBDatasourceProperties().
                initializeDataSourceBuilder().
                type(HikariDataSource.class).
                build();
    }


}
