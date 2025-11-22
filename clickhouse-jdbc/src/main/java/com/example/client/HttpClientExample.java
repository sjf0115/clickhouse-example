package com.example.client;

import com.clickhouse.client.api.Client;
import com.clickhouse.client.api.data_formats.ClickHouseBinaryFormatReader;
import com.clickhouse.client.api.enums.Protocol;
import com.clickhouse.client.api.query.QueryResponse;

import java.util.concurrent.TimeUnit;

/**
 * 功能：Http Client 交互模式示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/11/22 15:58
 */
public class HttpClientExample {
    public static void main(String[] args) {
        try (Client client = new Client.Builder().addEndpoint(Protocol.HTTP, "localhost", 8123, false)
                .setUsername("default")
                .setPassword("")
                .build()) {

            final String sql = "select * from test";
            try (QueryResponse response = client.query(sql).get(3, TimeUnit.SECONDS);) {
                ClickHouseBinaryFormatReader reader = client.newBinaryFormatReader(response);
                while (reader.hasNext()) {
                    reader.next();
                    String id = reader.getString("entity_id");
                    String value = reader.getString("value");
                    System.out.println("ID: " + id + "Value: " + value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
