package com.dm.mq.simple.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OnewayProducer {

    public static void main(String[] args) throws Exception{

        DefaultMQProducer producer = new DefaultMQProducer("oneway_msg_simple_group");
        producer.setNamesrvAddr("122.51.157.42:9876");

        producer.start();
        String message = "Hello dm oneway ";
        for (int i = 0; i < 1; i++) {
            Message msg = new Message("TopicTest","TagSendOne","OrderID188", (message + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(msg);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
