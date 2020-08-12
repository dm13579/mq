package com.dm.rabbitmqadvancedfeatures.confirm_listener;

import com.rabbitmq.client.*;

import java.io.IOException;

public class DmConfirmListener implements ConfirmListener {

    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("handleAck:"+deliveryTag);
    }

    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("handleNack:"+deliveryTag);
    }
}
