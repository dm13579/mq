package com.dm.rabbitmqadvancedfeatures.return_listener;

import com.rabbitmq.client.*;

import java.io.IOException;

public class DmReturnListener implements ReturnListener {

    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("replyCode:"+replyCode);
        System.out.println("replyText:"+replyText);
        System.out.println("exchange:"+exchange);
        System.out.println("routingKey:"+routingKey);
        System.out.println("properties:"+properties);
        System.out.println("body:"+new String(body));
    }
}
