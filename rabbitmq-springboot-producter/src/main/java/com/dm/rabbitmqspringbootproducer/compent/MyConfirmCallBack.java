//package com.dm.rabbitmqspringbootproducer.compent;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//@Slf4j
//public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        log.info("correlationData:========>{}",correlationData.getId());
//        log.info("ack:================{}",ack);
//        if(ack) {
//            log.info("mq生产端消息已经成功投递到了broker,更新我们消息日志表");
//        }else {
//            log.warn("mq生产端没有被broker ack,原因:{}",cause);
//        }
//    }
//}
