package com.dm.rabbitmqadvancedfeatures.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckNackRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

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

        String exchangeName = "dm.ack.direct";
        String exchangeType = "direct";
        String queueName = "dm-ack-queue";
        String routinkey = "dm.ack.key";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routinkey);

        // 创建消费者
        // 关闭消息自动签收
        channel.basicConsume(queueName, false, new AckConsumer(channel));

    }
}
