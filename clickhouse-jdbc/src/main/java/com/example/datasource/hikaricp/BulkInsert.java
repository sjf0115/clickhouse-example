package com.example.datasource.hikaricp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：连接池并行批量插入
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/4/19 23:36
 */
public class BulkInsert {
    private final static Integer INSERT_BATCH_SIZE = 10000;
    private final static Integer INSERT_BATCH_NUM = 10;

    /**
     * 批量插入
     * @param conn
     * @throws SQLException
     */
    public static void batchInsert(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO test VALUES (?, ?, ?, ?)";
        PreparedStatement pt = conn.prepareStatement(insertSQL);

        String[] genders = {"男", "女"};
        Random random = new Random();
        for (int i = 0; i < INSERT_BATCH_NUM; i++) {
            long insertStartTime = System.currentTimeMillis();
            for (int j = 0; j < INSERT_BATCH_SIZE; j++) {
                int id = i * 1000000 + j;
                pt.setString(1, "user_" + id);
                pt.setString(2, genders[random.nextInt(genders.length)]);
                pt.addBatch();
            }
            pt.executeBatch();

            System.out.printf("[%d] insert batch [%d/%d] success, cost %d ms\n",
                    Thread.currentThread().getId(), i + 1, INSERT_BATCH_NUM, System.currentTimeMillis() - insertStartTime);
        }
    }

    /**
     * 条数
     * @param conn
     * @throws Exception
     */
    public static void count(Connection conn) throws Exception {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT count(*) as cnt FROM test");
        if (resultSet.next()) {
            System.out.printf("table `test` has %d rows\n", resultSet.getInt("cnt"));
        }
    }

    public static void main(String[] args) {
        try(Connection connection = ClickHouseHikariCPPool.getConnection()) {
            // 并发插入数据
            int concurrentNum = 5;
            //开启5个线程
            CountDownLatch countDownLatch = new CountDownLatch(concurrentNum);
            ExecutorService executorService = Executors.newFixedThreadPool(concurrentNum);
            for (int i = 0; i < concurrentNum; i++) {
                executorService.submit(() -> {
                    System.out.printf("[%d] Thread start inserting\n", Thread.currentThread().getId());
                    try {
                        //插入数据
                        batchInsert(connection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.printf("[%d] Thread stop inserting\n", Thread.currentThread().getId());
                        countDownLatch.countDown();
                    }
                });
            }
            // 等待每个线程完成数据插入
            countDownLatch.await();
            // 插入后查看结果
            count(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
