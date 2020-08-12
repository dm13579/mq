package com.dm.rabbitmqadvancedfeatures.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QosRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

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

        String exchangeName = "dm.qos.direct";
        String exchangeType = "direct";
        String queueName = "dm-qos-queue";
        String routinkey = "dm.qos.key";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routinkey);


        /**
         * 限流设置
         * pretetchSize:每条消息大小的设置
         * prefetchCount:每次推送多少条消息
         * global:false 表示channel级别 true表示消费者级别
         */
        channel.basicQos(0,1,false);

        // 自动签收关闭
        channel.basicConsume(queueName, false, new QosConsumer(channel));

    }
}
