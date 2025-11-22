package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能：连接
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/20 09:13
 */
public class ConnectionExample {

    public static void main(String[] args) {
        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection()) {
            String catalog = connection.getCatalog();
            System.out.println("Connected to ClickHouse " + catalog + " Successful !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
