package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能：删除
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/21 22:23
 */
public class DeleteExample {
    public static void main(String[] args) {
        String deleteSQL = "DELETE FROM test WHERE entity_id = ?";

        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection();
             PreparedStatement pt = connection.prepareStatement(deleteSQL)) {
            pt.setString(1, "b1");
            int result = pt.executeUpdate();
            System.out.println("删除结果：" + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
