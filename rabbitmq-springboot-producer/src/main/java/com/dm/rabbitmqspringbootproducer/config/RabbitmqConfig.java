package com.dm.rabbitmqspringbootproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitmqConfig
 * @Description TODO
 * @Author dm
 * @Date 2019/11/3 17:59
 * @Version 1.0
 **/
@Configuration
public class RabbitmqConfig {

    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange("springboot.direct.exchange",true,false);
        return directExchange;
    }
    @Bean
    public CustomExchange delayExchange(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange("delayExchange","x-delayed-message",true,false,args);
    }

    @Bean
    public Queue dmBootQueue() {
        Queue queue = new Queue("dmBootQueue",true,false,false);
        return queue;
    }

    @Bean
    public Queue dmClusterQueue() {
        Queue queue = new Queue("dmClusterQueue",true,false,false);
        return queue;
    }

    @Bean
    public Queue dmBootDelayQueue() {
        Queue queue = new Queue("dmBootDelayQueue",true,false,false);
        return queue;
    }

    @Bean
    public Binding dmBootBinder() {
        return BindingBuilder.bind(dmBootQueue()).to(directExchange()).with("springboot.key");
    }

    @Bean
    public Binding dmClusterBinder() {
        return BindingBuilder.bind(dmClusterQueue()).to(directExchange()).with("rabbitmq.cluster.key");
    }

    @Bean
    public Binding delayBinder() {
        return BindingBuilder.bind(dmBootDelayQueue()).to(delayExchange()).with("springboot.delay.key").noargs();
    }
}
