package com.dm.rabbbitmqquickstart.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 直接交换机
public class DirectExchangeProducter {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.186.134");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("dm");
        connectionFactory.setUsername("dm");
        connectionFactory.setPassword("123456");
        connectionFactory.setConnectionTimeout(100000);

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        // 创建channel
        Channel channel = connection.createChannel();

        String exchangeName = "dm-directexchange";
        String routinkey = "dm-directexchange-key";
        String message = "hello";

        channel.basicPublish(exchangeName, routinkey, null, message.getBytes());

        // 关闭连接
        channel.close();
        connection.close();

    }
}
