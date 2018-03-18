package helloworld.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import helloworld.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {
        Channel channel = null;
        try {
            System.out.println("Trying to create connection and channel.");
            channel = prepareChannel();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received '" + message + "'");
                }
            };
            channel.basicConsume(Configuration.instance.queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepare the channel.
     *
     * @return The channel created channel.
     * @throws IOException Can be thrown by the RabbitMQ client.
     * @throws TimeoutException Can be thrown by the RabbitMQ client.
     */
    private static Channel prepareChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Configuration.rabbitMQServerHost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Configuration.instance.queueName, false, false, false, null);
        System.out.println("Waiting for messages. To exit press CTRL+C");
        return channel;
    }
}
