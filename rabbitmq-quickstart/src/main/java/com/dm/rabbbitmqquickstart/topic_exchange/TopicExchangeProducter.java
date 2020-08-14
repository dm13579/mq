package com.dm.rabbbitmqquickstart.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 主题交换机

public class TopicExchangeProducter {

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

        String exchangeName = "dm-topicexchange";
        String routinkey = "dm.topicexchange.key";
        String routinkey1 = "dm.topicexchange.key1";
        String routinkey2 = "dm.topicexchange.key2";
        String message = "hello";

        channel.basicPublish(exchangeName, routinkey, null, "第一条消息".getBytes());
        channel.basicPublish(exchangeName, routinkey1, null, "第二条消息".getBytes());
        channel.basicPublish(exchangeName, routinkey2, null, "第三条消息".getBytes());
    }
}
