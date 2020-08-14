package com.dm.rabbitmqadvancedfeatures.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QosRabbitmqProducer {

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

        String exchangeName = "dm.qos.direct";
        String routingKey = "dm.qos.key";
        String msgBody = "hello,dm";

        for(int i = 0; i < 500; i++){
            channel.basicPublish(exchangeName,routingKey,null,(msgBody+i).getBytes());
        }

    }

}
