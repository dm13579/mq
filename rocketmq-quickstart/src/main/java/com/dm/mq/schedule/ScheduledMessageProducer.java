package com.dm.mq.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ExampleConsumer");

        producer.setNamesrvAddr("122.51.157.42:9876");

        producer.start();
        int totalMessagesToSend = 3;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            //延时消费
            message.setDelayTimeLevel(6);
            // Send the message
            producer.send(message);
        }

        System.out.printf("message send is completed .%n");
        producer.shutdown();
    }
}
