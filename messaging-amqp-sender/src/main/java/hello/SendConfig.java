package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendConfig {
	
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
    Queue replyQueue() {
        return new Queue(replyQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding bindingSendQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendQueue()).to(exchange).with(sendQueueName);
    }
    
    @Bean
    Binding bindingSendReceiveQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendReceiveQueue()).to(exchange).with(sendReceiveQueueName);
    }

    @Bean
    SimpleMessageConverter simpleMessageConverter(){
    	return new SimpleMessageConverter();
    }

    @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchange().getName());
        rabbitTemplate.setReplyAddress(replyQueueName);
        rabbitTemplate.setReplyTimeout(5000);
        return rabbitTemplate;
    }
    
    @Bean
    SimpleMessageListenerContainer replyListenerContainer(RabbitTemplate amqpTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(replyQueueName);
        container.setMessageListener(amqpTemplate);
        return container;
    }
}
