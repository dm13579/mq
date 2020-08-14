package com.dm.rabbitmqadvancedfeatures.confirm_listener;

import com.rabbitmq.client.*;

import java.io.IOException;

public class DmConfirmListener implements ConfirmListener {

    /**
     * @param deliveryTag 消息唯一id
     * @param multiple    批量模式  true代表着当前已发送批量的消息,小于deliveryTag的消息都被处理
     * @throws IOException
     */
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("handleAck:" + deliveryTag + ",,multiple" + multiple);
    }

    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("handleNack:" + deliveryTag + ",,multiple" + multiple);
    }
}
