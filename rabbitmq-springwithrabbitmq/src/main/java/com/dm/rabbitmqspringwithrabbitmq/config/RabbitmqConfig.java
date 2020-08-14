package com.dm.rabbitmqspringwithrabbitmq.config;

import com.dm.rabbitmqspringwithrabbitmq.conveter.MyImageConverter;
import com.dm.rabbitmqspringwithrabbitmq.conveter.MyWordConverter;
import com.dm.rabbitmqspringwithrabbitmq.messagedelegate.MessgaeDelegate;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitmqConfig
 * @Description rabbitmq 配置类
 * @Author dm
 * @Date 2019/11/3 11:04
 * @Version 1.0
 **/
@Configuration
public class RabbitmqConfig {

    /**
     * Description: 创建连接工厂
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:07
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses("122.51.157.42:5672");
        cachingConnectionFactory.setVirtualHost("test");
        cachingConnectionFactory.setUsername("dm");
        cachingConnectionFactory.setPassword("123456");
        cachingConnectionFactory.setConnectionTimeout(10000);
        cachingConnectionFactory.setCloseTimeout(10000);
        return cachingConnectionFactory;
    }

    /**
     * Description: 配置RabbitAdmin
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:14
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // spring容器自动加载
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    // ======================交换机========================
    /**
     * Description: 声明主题交换机
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:19
     */
    @Bean
    public TopicExchange topicExchange(){
        TopicExchange topicExchange = new TopicExchange("dm.topic.exchange",true,false);
        return topicExchange;
    }

    /**
     * Description: 声明直接交换机
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:19
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange("dm.direct.exchange",true,false);
        return directExchange;
    }

    /**
     * Description: 声明交换机
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:19
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange("dm.fanout.exchange",true,false);
        return fanoutExchange;
    }

    // ===============================队列=========================
    /**
     * Description: 声明队列
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:23
     */
    @Bean
    public Queue testTopicQueue(){
        Queue queue = new Queue("testTopicQueue",true,false,false,null);
        return queue;
    }

    /**
     * Description: 声明队列
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:23
     */
    @Bean
    public Queue testTopicQueue2(){
        Queue queue = new Queue("testTopicQueue2",true,false,false,null);
        return queue;
    }

    /**
     * Description: 声明队列
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:23
     */
    @Bean
    public Queue testDirectQueue(){
        Queue queue = new Queue("testDirectQueue",true,false,false,null);
        return queue;
    }

    /**
     * Description: 声明队列
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 11:23
     */
    @Bean
    public Queue testFanoutQueue(){
        Queue queue = new Queue("testFanoutQueue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue orderQueue(){
        Queue queue = new Queue("orderQueue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue addressQueue(){
        Queue queue = new Queue("addressQueue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue fileQueue(){
        Queue queue = new Queue("fileQueue",true,false,false,null);
        return queue;
    }



    //=================================声明绑定============================
    @Bean
    public Binding topicBinding(){
        return BindingBuilder.bind(testTopicQueue()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(testTopicQueue2()).to(topicExchange()).with("topic.key.#");
    }

    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(testDirectQueue()).to(directExchange()).with("direct.key");
    }

    @Bean
    public Binding fanoutBinding(){
        return BindingBuilder.bind(testFanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(orderQueue()).to(directExchange()).with("rabbitmq.order");
    }

    @Bean
    public Binding addressQueueBinding() {
        return BindingBuilder.bind(addressQueue()).to(directExchange()).with("rabbitmq.address");
    }

    @Bean
    public Binding fileQueueBinding() {
        return BindingBuilder.bind(fileQueue()).to(directExchange()).with("rabbitmq.file");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReceiveTimeout(50000);
        return rabbitTemplate;
    }

    /**
     * Description: 消息监听容器
     *
     * @param:
     * @return:
     * @auther: dm
     * @date: 2019/11/3 15:25
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        // 监听队列
        simpleMessageListenerContainer.setQueues(testTopicQueue(),testTopicQueue2(),testDirectQueue(),testFanoutQueue(),orderQueue(),addressQueue(),fileQueue());
        // 消费者数量
        simpleMessageListenerContainer.setConcurrentConsumers(5);
        // 最大消费者数量
        simpleMessageListenerContainer.setMaxConcurrentConsumers(10);
        // 签收模式
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 设置拒绝重回队列
        simpleMessageListenerContainer.setDefaultRequeueRejected(false);


        // 使用默认监听方法
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        // 指定消费方法
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
//        messageListenerAdapter.setDefaultListenerMethod("consumerMsg");
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        // 指定队列和消费方法绑定
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
//        Map<String,String> queueMaps = new HashMap<>();
//        queueMaps.put("testTopicQueue","consumerTopicQueue");
//        queueMaps.put("testTopicQueue2","consumerTopicQueue2");
//        messageListenerAdapter.setQueueOrTagToMethodName(queueMaps);
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        // 处理json
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
//        messageListenerAdapter.setDefaultListenerMethod("consumerJsonMessage");
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        // 处理javaObject
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
//
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//
//        messageListenerAdapter.setDefaultListenerMethod("consumerJavaObjMessage");
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        javaTypeMapper.setTrustedPackages("com.dm.rabbitmqspringwithrabbitmq.entity");
//        // 设置java转json
//        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        // 处理文件
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessgaeDelegate());
        messageListenerAdapter.setDefaultListenerMethod("consumerFileMessage");

        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
        messageConverter.addDelegate("img/png",new MyImageConverter());
        messageConverter.addDelegate("img/jpg",new MyImageConverter());
        messageConverter.addDelegate("application/word",new MyWordConverter());
        messageConverter.addDelegate("word",new MyWordConverter());

        messageListenerAdapter.setMessageConverter(messageConverter);
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
        return simpleMessageListenerContainer;
    }
}
