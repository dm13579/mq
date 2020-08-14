package com.dm.rabbitmqspringbootproducer;

import com.dm.rabbitmqspringbootproducer.compent.MyMsgSender;
import com.dm.rabbitmqspringbootproducer.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MyMsgSender myMsgSender;

	@Test
	public void testMsgSender() throws JsonProcessingException {
		Map<String,Object> msgProp = new HashMap<>();
		msgProp.put("company","My");
		msgProp.put("name","dm");
/*		String msgBody = "hello My ";*/
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("dm");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());
		ObjectMapper objectMapper = new ObjectMapper();
        myMsgSender.sendMsg(objectMapper.writeValueAsString(order),msgProp);
	}

	@Test
	public void testSenderOrder() throws Exception {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("dm");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());

        myMsgSender.sendOrderMsg(order);
	}

	@Test
	public void testSenderDelay() {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		order.setUserName("dm");
		order.setPayMoney(10000.00);
		order.setCreateDt(new Date());

        myMsgSender.sendDelayMessage(order);
	}
}
