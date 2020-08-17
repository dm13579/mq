package com.dm.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
  * @className StringTransactionalConsumer 
  * @description TODO
  * @author dm
  * @date 2020/8/17
  * @since JDK1.8
  */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${dm.rocketmq.transTopic}", consumerGroup = "myTxProducerGroup")
public class StringTransactionalConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringTransactionalConsumer received: %s \n", message);
    }
}
