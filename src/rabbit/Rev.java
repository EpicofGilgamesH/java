package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description TODO
 * @Date 2020/8/10 16:24
 * @Created by wangjie
 */
public class Rev {

	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		//consumer方属于长链接,所以并不需要使用try-catch进行链接释放
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		channel.basicConsume(QUEUE_NAME, true, (s, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		}, tag -> {
		});

		TimeUnit.SECONDS.sleep(1000);
	}
}
