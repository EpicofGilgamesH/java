package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @Description Rabbetmq hello world
 * @Date 2020/8/10 13:43
 * @Created by wangjie
 */
public class Send {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) {

		ExecutorService pool = Executors.newFixedThreadPool(6);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
		     Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "hello world";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("[send] Send '" + message + "'");
			while (true) {
				pool.submit(() -> {
					try {
						channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
						System.out.println("[send] Send '" + message + "'");
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
