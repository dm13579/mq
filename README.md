# 消息队列

**RabbitMQ**
1. RabbitMQ快速开始(rabbitmq-quickstart)
	- 主题交换机测试  
	- 直接交换机测试
	- 扇形交换机测试
2. RabbitMQ高级特性(rabbitmq-advancedfeatures)
	- 消息的确认机制  confirm_listener包
	- 消费端的ack_nack处理  ack_nack包
	- 不可达消息处理机制 return_listener包（mandatory为true,调用生产端的returnListener处理，false直接丢掉） 
	- 消费端的限流  consumer_limit包
	- 死信队列  dlx包
3. RabbitMQ集成Spring(rabbitmq-springwithrabbitmq)
4. RabbitMQ集成SpringBoot(rabbitmq-springboot-producter,rabbitmq-springboot-consumer)