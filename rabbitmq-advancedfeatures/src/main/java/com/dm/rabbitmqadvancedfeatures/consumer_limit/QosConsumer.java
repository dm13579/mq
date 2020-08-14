package com.dm.rabbitmqadvancedfeatures.consumer_limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class QosConsumer extends DefaultConsumer {

    private Channel channel;

    public QosConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("consumerTag:"+consumerTag);
        System.out.println("envelope:"+envelope);
        System.out.println("properties:"+properties);
        System.out.println("body:"+new String(body));

        //multiple:false 不批量签收
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
