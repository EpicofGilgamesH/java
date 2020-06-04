package mq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangjie
 * @version v1.0
 * @description workqueue send
 * @date 2020/4/16 15:27
 */
public class WorkQueueSend {
    private final static String QUEUE_NAME = "lizzy-task";

    public static void main(String[] args) throws IOException, TimeoutException {
        String[] argv = new String[]{"a", ".", "b", ".", "c"};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String msg = String.join("", argv);
            //queue won't be lost even if RabbitMQ restarts
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN
                    , msg.getBytes("UTF-8"));
            System.out.println("[x] sent '" + msg + "'");
        }
    }
}
