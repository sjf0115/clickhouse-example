package com.example.crud;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 功能：创建表
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/20 09:49
 */
public class CreateTableExample {
    public static void main(String[] args) {
        String createSQL = "CREATE TABLE default.test (\n" +
                "              `entity_id` String,\n" +
                "              `value` String\n" +
                "          ) ENGINE = MergeTree\n" +
                "          PRIMARY KEY entity_id\n" +
                "          ORDER BY entity_id SETTINGS index_granularity = 8192";

        String url = "jdbc:ch:http://localhost:8123/default";
        Properties properties = new Properties();
        properties.setProperty("user", "default");
        properties.setProperty("password", "");

        try (Connection connection = new ClickHouseDataSource(url, properties).getConnection()) {
            Statement st = connection.createStatement();
            st.execute(createSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
