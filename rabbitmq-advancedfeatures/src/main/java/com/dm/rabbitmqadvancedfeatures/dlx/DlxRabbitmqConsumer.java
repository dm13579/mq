package com.dm.rabbitmqadvancedfeatures.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DlxRabbitmqConsumer {

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

        String exchangeType = "direct";
        String routinkey = "dm.dlx.key";

        // 声明正常队列
        String exchangeName = "dm.normaldlx.direct";
        String queueName = "dm-normaldlx-queue";

        // 声明死信队列
        String dlxExchangeName = "dlx.direct";
        String dlxQueueName = "dm-dlx-queue";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        Map<String,Object> queueArgs = new HashMap<>();
        queueArgs.put("x-dead-letter-exchange",dlxExchangeName);
        queueArgs.put("x-max-length",4);
        channel.queueDeclare(queueName, true, false, false, queueArgs);
        channel.queueBind(queueName, exchangeName, routinkey);

        // 声明死信队列
        channel.exchangeDeclare(dlxExchangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExchangeName,routinkey);

        channel.basicConsume(queueName, false, new DlxConsumer(channel));

    }
}
