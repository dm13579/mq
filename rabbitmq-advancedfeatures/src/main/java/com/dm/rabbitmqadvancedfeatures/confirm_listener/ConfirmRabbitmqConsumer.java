package com.dm.rabbitmqadvancedfeatures.confirm_listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmRabbitmqConsumer {

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

        String exchangeName = "dm.confirmlistener.direct";
        String exchangeType = "direct";
        String queueName = "dm-confirmlistener-queue";
        String routinkey = "dm.confirmlistener.key";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routinkey);

        // 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费消息" + new String(delivery.getBody()));
        }

    }
}
