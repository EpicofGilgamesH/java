package mq.simple.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wangjie
 * @version v1.0
 * @description 简单接收方
 * @date 2020/4/16 10:48
 */
public class SimpleRecv {
    private final static String QUEUE_NAME = "lizzy";

    //在接收方(消费者,订阅方),要注意try-with-resource的使用方式;其使用完成后即close的特性
    //采用语法糖的形式,执行完try住的片段,即在finally中调用close方法。
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x]received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, s -> {
        });

        TimeUnit.MINUTES.sleep(60);
    }
}
