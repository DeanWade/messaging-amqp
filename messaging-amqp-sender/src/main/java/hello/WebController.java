package hello;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@Autowired
	private Sender sender;

	@GetMapping("/send")
	public String send(){
        sender.send("Hello from RabbitMQ! at " + new Date());
        return "success!";
	}
	
	@GetMapping("/sendAndReceive")
	public String sendAndReceive(){
        String reply = sender.sendAndReceive("Hello from RabbitMQ! at " + new Date());

        
        return handleReply(reply);
	}
	
	private String handleReply(String reply){
        
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return reply;
	}
}
