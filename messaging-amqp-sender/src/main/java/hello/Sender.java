package hello;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String message){
        rabbitTemplate.convertAndSend(SendConfig.sendQueueName, message);
        System.out.println("Sent <" + message + ">");
        return;
	}
	
	public String sendAndReceive(String message){
        Object repliedMessage = rabbitTemplate.convertSendAndReceive(SendConfig.sendReceiveQueueName, message);
        System.out.println("sendAndReceive <" + message + ">");
        return repliedMessage.toString();
	}
	
}
