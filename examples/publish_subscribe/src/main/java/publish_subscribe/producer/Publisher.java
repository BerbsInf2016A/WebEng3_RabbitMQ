package publish_subscribe.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import publish_subscribe.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Class for the Publisher.
 */
public class Publisher {

    public static void main(String[] argv) {
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

            String message = getMessage(argv);

            System.out.println("Publishing message: '" + message + "'");
            channel.basicPublish(Configuration.instance.queueName, "", null, message.getBytes("UTF-8"));

            System.out.println("Sent message: '" + message + "'");

            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the Message in a Format to be published.
     * @param strings The Message.
     * @return The Message to publish.
     */
    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "info: Hello World!";
        return String.join(" ", strings);
    }
}