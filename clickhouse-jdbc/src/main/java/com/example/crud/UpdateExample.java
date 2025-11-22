package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能：更新
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/21 22:23
 */
public class UpdateExample {
    public static void main(String[] args) {
        String updateSQL = "UPDATE test SET value = ? WHERE entity_id = ?";

        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection();
             PreparedStatement pt = connection.prepareStatement(updateSQL)) {
            pt.setString(1, "女");
            pt.setString(2, "a0");
            pt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
