package com.dm.rabbitmqadvancedfeatures.confirm_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ConfirmRabbitmqProducer {

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
        // 开启消息投递模式（确认模式）
        channel.confirmSelect();

        String exchangeName = "dm.confirmlistener.direct";
        String routingKey = "dm.confirmlistener.key";
        String msgBody = "hello,dm";

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("user", "dm");
        infoMap.put("pasword", "123456");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(infoMap)
                .build();

        // 消息确认监听
        channel.addConfirmListener(new DmConfirmListener());

        // 批量确认
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(exchangeName, routingKey, basicProperties, msgBody.getBytes());
        }

    }

}
