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

        String exchangeType = "direct";
        String routinkey = "dm.dlx.key";

        // 声明正常队列
        String exchangeName = "dm.normaldlx.direct";
        String queueName = "dm-normaldlx-queue";

        // 声明死信队列
        String dlxExchangeName = "dlx.direct";
        String dlxQueueName = "dm-dlx-queue";

        // 正常队列绑定
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        Map<String,Object> queueArgs = new HashMap<>();
        // 当这个队列里的消息变为死信后投递到dlxExchangeName这个死信队列中去
        queueArgs.put("x-dead-letter-exchange",dlxExchangeName);
        // 消息最大长度是4
        queueArgs.put("x-max-length",4);
        channel.queueDeclare(queueName, true, false, false, queueArgs);
        channel.queueBind(queueName, exchangeName, routinkey);

        // 声明死信队列
        channel.exchangeDeclare(dlxExchangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExchangeName,routinkey);


        // TODO 效果：生产者发送了100条消息,正常队列最大接收4条消息，所以96条消息会直接进死信，
        //  然后消息处理这边睡了1000ms,之后拒绝这4条消息，然后这4条消息又会进入死信
        channel.basicConsume(queueName, false, new DlxConsumer(channel));

    }
}
