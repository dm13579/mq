package com.dm.mq.simple.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PullConsumer {

    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("async_msg_simple_group");
        consumer.setNamesrvAddr("122.51.157.42:9876");
        consumer.start();

        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicSync");
        for (MessageQueue mq : mqs) {
            System.err.println("Consume from the queue: " + mq);
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.println(pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());

                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                            for (MessageExt m : messageExtList) {
                                System.out.println(new String(m.getBody()));
                            }
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        consumer.shutdown();
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }
}
