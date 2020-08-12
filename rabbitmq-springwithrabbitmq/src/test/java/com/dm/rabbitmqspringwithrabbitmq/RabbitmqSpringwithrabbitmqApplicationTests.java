package com.dm.rabbitmqspringwithrabbitmq;

import com.dm.rabbitmqspringwithrabbitmq.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringwithrabbitmqApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRabbitmqTemplate(){
        rabbitTemplate.convertAndSend("dm.direct.exchange","direct.key","dm");
    }

    @Test
    public void messageListenerAdaperQueueOrTagToMethodName(){
        rabbitTemplate.convertAndSend("dm.topic.exchange","topic.key.dm","dm");
        rabbitTemplate.convertAndSend("dm.topic.exchange","topic.dm","dm");
    }

    @Test
    public void sendJson() throws JsonProcessingException {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setCreateDt(new Date());
        order.setPayMoney(10000.00);
        order.setUserName("dm");

        ObjectMapper objectMapper = new ObjectMapper();
        String orderjson = objectMapper.writeValueAsString(order);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message orderMsg = new Message(orderjson.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("dm.direct.exchange","rabbitmq.order",orderMsg);
    }

    @Test
    public void sendJavaObj() throws JsonProcessingException{

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setCreateDt(new Date());
        order.setPayMoney(10000.00);
        order.setUserName("dm");

        ObjectMapper objectMapper = new ObjectMapper();
        String orderjson = objectMapper.writeValueAsString(order);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","com.dm.rabbitmqspringwithrabbitmq.entity.Order");
        Message orderMsg = new Message(orderjson.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("dm.direct.exchange","rabbitmq.order",orderMsg);
    }

    @Test
    public void sendImage() throws IOException {
        byte[] imgBody = Files.readAllBytes(Paths.get("D:/smlz/file01","smlz.png"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("img/png");
        Message message = new Message(imgBody, messageProperties);
        rabbitTemplate.send("tuling.direct.exchange","rabbitmq.file",message);

    }

    @Test
    public void sendWord() throws IOException {
        byte[] imgBody = Files.readAllBytes(Paths.get("D:/smlz/file01","spring.docx"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/word");
        Message message = new Message(imgBody, messageProperties);
        rabbitTemplate.send("tuling.direct.exchange","rabbitmq.file",message);

    }
}
