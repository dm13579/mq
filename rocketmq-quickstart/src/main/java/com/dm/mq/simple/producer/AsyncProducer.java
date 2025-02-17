package com.dm.mq.simple.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 異步發送方式
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("async_msg_simple_group");
        producer.setNamesrvAddr("122.51.157.42:9876");
        producer.start();

        //设置发送失败重试机制
        producer.setRetryTimesWhenSendAsyncFailed(5);

        String message = "Hello dm async ";
        int messageCount = 1;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            Message msg = new Message("TopicTest","TagSendOne","OrderID188", (message + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //消息发送成功后，执行回调函数
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);

        producer.shutdown();
    }

}
