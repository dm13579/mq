package com.dm.rabbbitmqquickstart.quickstartclass;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QuickProducter {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("122.51.157.42");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("dm");
        connectionFactory.setPassword("123456");
        connectionFactory.setConnectionTimeout(100000);

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        // 创建channel
        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            String message = "hello--" + i;
            channel.basicPublish("", "dm-queue-01", null, message.getBytes());
        }

        // 关闭连接
        channel.close();
        connection.close();

    }

}
