package hello;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	public void handleMessage(String message){
        System.out.println("handleMessage : <" + message + ">");
	}

	@RabbitListener(queues=ReceiverConfig.sendQueueName)
    public void receive(String message) {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
        System.out.println("Received: <" + message + ">");
    }
	
	@RabbitListener(queues=ReceiverConfig.sendReceiveQueueName)
    public String receiveAndReply(String message) {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
        System.out.println("Received and Reply: <" + message + ">");
        return message.toUpperCase();
    }

}
