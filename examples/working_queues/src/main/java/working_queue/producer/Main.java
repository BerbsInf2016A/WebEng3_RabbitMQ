package working_queue.producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import working_queue.Configuration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {

    /**
     * The RabbitMQ connection.
     */
    private static Connection connection;

    public static void main(String[] args) {
        // The channel to send messages to the consumers.
        Channel channel = null;

        // Open connection and channel.
        try {
            System.out.println("Trying to create connection and channel.");
            channel = prepareChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // Get new messages from the user and send them.
        while(true) {
            if (!askUserToSendAnotherMessage()) break;

            String message = getNextMessage();
            try {
                produceMessage(channel, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Close channel and connection.
        System.out.println("Trying to close the connections.");
        try {
            if (channel != null) channel.close();

            if (connection != null) connection.close();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Ask the user if they want to proceed in sending messages.
     *
     * @return boolean - true: send another message - false: send no further messages
     */
    private static boolean askUserToSendAnotherMessage() {
        System.out.println("Do you want to produce a message? y or yes to produce a message, n or no to exit.");
        Scanner input = new Scanner(System.in);
        String response = input.next();

        if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
            return true;
        }
        else return false;
    }

    /**
     * Send a passed message to the passed channel.
     *
     * @param channel   the channel in which the message is to be published
     * @param message   the message to be published as String
     * @throws IOException  the channel can fail to publish the message
     */
    private static void produceMessage(Channel channel, String message) throws IOException {
        /*
         * Publish a message (param4) with no use of a exchange (param 1) to a specified queue (param2),
         * with no headers, further information about content type or timestamp (param3)
         */
        channel.basicPublish("", Configuration.instance.queueName, null, message.getBytes());
        System.out.println("Sent '" + message + "'");
    }

    /**
     * Get the next message the user writes into the command line.
     *
     * @return  the next message to be sent as String
     */
    private static String getNextMessage(){
        System.out.println("Please insert message:");

        Scanner input = new Scanner(System.in);
        String message = input.nextLine();
        return (message.length() < 1)? "test...": message ;
    }

    /**
     * Opens a connection and channel to allow sending messages.
     *
     * @return                  The channel that can be used to send messages.
     * @throws IOException      The connection and channel can fail to open.
     * @throws TimeoutException The connection and channel can fail to open.
     */
    private static Channel prepareChannel() throws IOException, TimeoutException {
        // ConnectionFactory is part of the RabbitMQ library, ...
        ConnectionFactory factory = new ConnectionFactory();
        // it is used to connect to the host of the RabbitMQ server, ...
        factory.setHost(Configuration.instance.host);
        // and open a connection to the server.
        connection = factory.newConnection();
        // The connection can be used to create a channel which is also part of the RabbitMQ library.
        Channel channel = connection.createChannel();
        // The channel can declare a basic queue, that can be accessed via its name.
        channel.queueDeclare(Configuration.instance.queueName, false, false, false, null);
        // The channel can now be used to send messages to its declared queue.
        return channel;
    }
}
