package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能：查询
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/21 22:23
 */
public class SelectExample {
    public static void main(String[] args) {
        String selectSQL = "SELECT value, count(*) AS num\n" +
                "FROM test\n" +
                "GROUP BY value";

        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection();
             PreparedStatement pt = connection.prepareStatement(selectSQL)) {
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                String value = rs.getString("value");
                int num = rs.getInt("num");
                System.out.printf("性别 %s: %d 名%n", value, num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
