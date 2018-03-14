package publish_subscribe.consumer;

import com.rabbitmq.client.*;
import publish_subscribe.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Class for the Subscriber.
 */
public class Subscriber {

    public static void main(String[] argv){
        // Creating the Connection Factory.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;

        try {
            System.out.println("Creating Connection and Channel.");
            connection = factory.newConnection();
            System.out.println("Connection created.");
            Channel channel = connection.createChannel();
            System.out.println("Channel created.");

            channel.exchangeDeclare(Configuration.instance.queueName, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, Configuration.instance.queueName, "");

            System.out.println("Waiting for messages. To exit press CTRL+C");

            // Creating the Consumer.
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received message: '" + message + "'");
                }
            };

            channel.basicConsume(queueName, true, consumer);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}