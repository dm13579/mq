package com.dm.mq.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class FilterProducer {


    /***
     * TAG-FILTER-1000 ---> 布隆过滤器
     * 过滤掉的那些消息。直接就跳过了么。下次就不会继续过滤这些了。是么。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("filter_sample_group");
        producer.setNamesrvAddr("122.51.157.42:9876");
        producer.start();

        for (int i = 0; i < 3; i++) {
            Message msg = new Message("TopicFilter",
                    "TAG-FILTER",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            msg.putUserProperty("a",String.valueOf(i));
            if(i % 2 == 0){
                msg.putUserProperty("b","dm");
            }else{
                msg.putUserProperty("b","dm 666");
            }
            producer.send(msg);
        }

        producer.shutdown();
    }

}
