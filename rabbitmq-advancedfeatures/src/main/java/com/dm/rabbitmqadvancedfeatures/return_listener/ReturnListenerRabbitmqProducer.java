package com.dm.rabbitmqadvancedfeatures.return_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ReturnListenerRabbitmqProducer {

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

        String exchangeName = "dm.returnlistener.direct";
        String okroutingKey = "dm.returnlistener.key.ok";
        String errorroutingKey = "dm.returnlistener.key.error";
        String msgBody = "hello,dm";

        channel.addReturnListener(new DmReturnListener());

        Map<String,Object> infoMap = new HashMap<>();
        infoMap.put("user","dm");
        infoMap.put("pasword","123456");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(infoMap)
                .build();

        channel.basicPublish(exchangeName,okroutingKey,true,basicProperties,msgBody.getBytes());

        String errorMsg1 = "mandatory为false,错误消息";
        channel.basicPublish(exchangeName,errorroutingKey,false,basicProperties,errorMsg1.getBytes());

        String errorMsg2 = "mandatory为true,错误消息";
        channel.basicPublish(exchangeName,errorroutingKey,true,basicProperties,errorMsg2.getBytes());
    }

}
