///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.dm.mq.filter;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.MixAll;
//import org.apache.rocketmq.common.message.MessageExt;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class Consumer {
//
//    public static void main(String[] args) throws InterruptedException, MQClientException, IOException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupNamecc4");
//        consumer.setNamesrvAddr("122.51.157.42:9876");
//
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        File classFile = new File(classLoader.getResource("MessageFilterImpl.java").getFile());
//
//        String filterCode = MixAll.file2String(classFile);
//        consumer.subscribe("TopicFilter7", "org.apache.rocketmq.example.filter.MessageFilterImpl",
//            filterCode);
//
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//
//        consumer.start();
//
//        System.out.printf("Consumer Started.%n");
//    }
//}
