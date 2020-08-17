package com.dm.mq.controller;

import com.dm.mq.transaction.TransactionListenerImpl;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dm
 * @className ProduceController
 * @description TODO
 * @date 2020/8/17
 * @since JDK1.8
 */
@RestController
@RequestMapping
public class ProduceController {

    /**
     * TX_PGROUP_NAME 必须同 {@link TransactionListenerImpl} 类的注解 txProducerGroup
     */
    private static final String TX_PGROUP_NAME = "myTxProducerGroup";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${dm.rocketmq.transTopic}")
    private String springTransTopic;

    @Value("${dm.rocketmq.topic}")
    private String springTopic;

    /**
     * 发送事务消息
     */
    @GetMapping("testTransaction")
    private void testTransaction() throws MessagingException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = MessageBuilder.withPayload("Hello RocketMQ " + i).setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(TX_PGROUP_NAME, springTransTopic + ":" + tags[i % tags.length], msg, null);
                System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n", msg.getPayload(), sendResult.getSendStatus());
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
