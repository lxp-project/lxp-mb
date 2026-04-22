package com.jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static final HikariDataSource datasource;

    static {
        try {
            Properties props = new Properties();
            props.load(
                JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties")
            );
            HikariConfig config = new HikariConfig();
            // config.properties에 작성된 내용
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            // hikari 설정
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(2000);
            datasource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public static void close(){
        if (datasource != null){
            datasource.close();
        }
    }
}
