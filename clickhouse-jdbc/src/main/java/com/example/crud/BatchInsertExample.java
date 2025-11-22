package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能：批量插入操作
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/20 09:49
 */
public class BatchInsertExample {
    public static void main(String[] args) {
        String insertSQL = "INSERT INTO test VALUES (?, ?)";

        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection();
             PreparedStatement pt = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < 10; i++) {
                pt.setString(1, "a"+i);
                pt.setString(2, "男");
                pt.addBatch();
            }
            pt.executeBatch(); // 执行批量操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
