package hello;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class ReceiverConfig {
	
	final static String exchangeName = "spring-boot-exchange";
	
    final static String sendQueueName = "spring-boot-send";
    
    final static String sendReceiveQueueName = "spring-boot-send-receive";
    
    final static String replyQueueName = "spring-boot-reply";

	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private MessageConverter messageConverter;

    @Bean
    Queue sendQueue() {
        return new Queue(sendQueueName, false);
    }
    
    @Bean
    Queue sendReceiveQueue() {
        return new Queue(sendReceiveQueueName, false);
    }

    
    @Bean
    SimpleMessageConverter simpleMessageConverter(){
    	return new SimpleMessageConverter();
    }

    
    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    /**
    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(MessageListenerAdapter messageListenerAdapter) {
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    	container.setMessageConverter(messageConverter);
    	container.setConnectionFactory(connectionFactory);
      container.setQueues(sendQueue(),sendReceiveQueue());
    	container.setMessageListener(messageListenerAdapter);
    	return container;
    }
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "handleMessage");
    }
    */
}
