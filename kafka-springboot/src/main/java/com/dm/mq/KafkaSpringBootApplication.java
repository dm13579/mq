package com.dm.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
  * @className KafkaSpringBootApplication 
  * @description 启动类
  * @author dm
  * @date 2020/8/17
  * @since JDK1.8
  */
@SpringBootApplication
public class KafkaSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringBootApplication.class,args);
    }
}
