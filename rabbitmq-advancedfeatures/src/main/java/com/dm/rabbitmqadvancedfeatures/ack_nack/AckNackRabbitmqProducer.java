package com.dm.rabbitmqadvancedfeatures.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class AckNackRabbitmqProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("122.51.157.42");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("dm");
        connectionFactory.setUsername("dm");
        connectionFactory.setPassword("123456");
        connectionFactory.setConnectionTimeout(100000);

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        // 创建channel
        Channel channel = connection.createChannel();

        String exchangeName = "dm.ack.direct";
        String routingKey = "dm.ack.key";
        String msgBody = "hello,dm";

        for(int i = 0; i < 5; i++){
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("mark",i);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                    .builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .correlationId(UUID.randomUUID().toString())
                    .headers(infoMap)
                    .build();

            channel.basicPublish(exchangeName,routingKey,basicProperties,(msgBody+i).getBytes());
        }

    }

}
