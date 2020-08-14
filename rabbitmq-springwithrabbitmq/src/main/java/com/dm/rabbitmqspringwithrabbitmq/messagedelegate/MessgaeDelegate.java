package com.dm.rabbitmqspringwithrabbitmq.messagedelegate;

import com.dm.rabbitmqspringwithrabbitmq.entity.Order;

import java.io.File;
import java.util.Map;

/**
 * @ClassName MessgaeDelegate
 * @Description TODO
 * @Author dm
 * @Date 2019/11/3 15:32
 * @Version 1.0
 **/
public class MessgaeDelegate {

   public void handleMessage(String msgBody){
       System.out.println("MsgDelegete,handleMessage;======"+msgBody);
   }

    public void consumerMsg(String msgBody){
       System.out.println("MsgDelegete,consumerMsg;======"+msgBody);
    }

    public void consumerTopicQueue(String msgBody) {
        System.out.println("MsgDelegete,consumerTopicQueue"+msgBody);
    }

    public void consumerTopicQueue2(String msgBody) {
        System.out.println("MsgDelegete,consumerTopicQueue2"+msgBody);
    }

    /**
     * 处理json
     * @param jsonMap
     */
    public void consumerJsonMessage(Map jsonMap) {
        System.out.println("MsgDelegete,处理json"+jsonMap);
    }

    /**
     * 处理order对象
     * @param order
     */
    public void consumerJavaObjMessage(Order order) {
        System.out.println("MsgDelegete,处理java对象"+order.toString());
    }

    public void consumerFileMessage(File file) {
        System.out.println("MsgDelegete,处理文件"+file.getName());
    }
}
