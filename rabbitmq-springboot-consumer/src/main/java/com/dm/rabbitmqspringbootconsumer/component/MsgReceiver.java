package com.dm.rabbitmqspringbootconsumer.component;

import com.dm.rabbitmqspringbootconsumer.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
  * @className MsgReceiver 
  * @description TODO
  * @author dm
  * @date 2020/8/14
  * @since JDK1.8
  */
@Component
@Slf4j
public class MsgReceiver {

//    @RabbitListener(queues = {"dmBootQueue"})
//    @RabbitHandler
//    public void consumerMsg(Message message, Channel channel) throws IOException {
//
//        log.info("消费消息:{}",message.getPayload());
//        //手工签收
//        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        log.info("接受deliveryTag:{}",deliveryTag);
//        channel.basicAck(deliveryTag,false);
//    }

    @RabbitListener(queues = {"dmBootQueue"})
    @RabbitHandler
    public void consumerOrder(org.springframework.amqp.core.Message message,Channel channel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(message.getBody(), Order.class);
        log.info("order:{}",order.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
