package mq.simple.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wangjie
 * @version v1.0
 * @description 简单的发送消息
 * @date 2020/4/16 10:29
 */
public class SimpleSend {

    //We'll call our message publisher (sender) Send and our message consumer (receiver) Recv.
    //The publisher will connect to RabbitMQ, send a single message, then exit.
    //我们将声明消息发布者(发送方)和消息消费者(接收方),发布者将连接到RabbitMQ,发送一个简单的消息,然后退出。

    //声明一个队列名称
    private final static String QUEUE_NAME = "lizzy";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置host
        factory.setHost("localhost");
        //创建连接
        try (Connection connection = factory.newConnection()) {
            //创建Channel 通道
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world";
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("[x] sent '" + message + "'");
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
