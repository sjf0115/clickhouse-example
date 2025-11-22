package com.example.datasource.hikaricp;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 功能：HikariCP 连接池
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/19 23:32
 */
public class ClickHouseHikariCPPool {
    private static final String JDBC_URL = "jdbc:ch://localhost:8123/default";
    private static final String USER = "default";
    private static final String PASSWORD = "";
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);

        // 核心参数优化
        config.setMaximumPoolSize(20);          // 根据CPU核心数调整
        config.setMinimumIdle(5);               // 最小空闲连接
        config.setConnectionTimeout(30000);      // 30秒连接超时
        config.setIdleTimeout(30000);          // 30秒空闲超时
        config.setMaxLifetime(60000);         // 1分钟连接生命周期
        config.setValidationTimeout(5000);      // 5秒验证超时
        config.setConnectionTestQuery("SELECT 1"); // 保活查询
        config.setPoolName("HikariPool");

        // ClickHouse专用参数
        config.addDataSourceProperty("socket_timeout", "600000"); // 10分钟Socket超时
        config.addDataSourceProperty("compress", "true");         // 启用压缩

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
