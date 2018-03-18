package working_queue.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import working_queue.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    /**
     * static variable to give each consumer an own id
     */
    private static int nextConsumer = 0;

    public static void main(String[] args) {
        try {
            // Open connection and channel.
            System.out.println("Trying to create connection and channel.");
            final Channel channel = prepareChannel();

            // Accept only one message at a time that has to be manually acknowledged (see below).
            channel.basicQos(1);

            // Build a consumer for the opened channel. The method handelDelivery is adjusted to fulfill our needs.
            Consumer consumer = new DefaultConsumer(channel) {
                /**
                 * The method that is called when a message is received.
                 *
                 * @params              Several parameters, of which one is the delivered message.
                 * @throws IOException  The method can throw a IOException.
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    // The message is transferred as a byte[] and re-concatenated to a String
                    String message = new String(body, "UTF-8");
                    System.out.println("Received '" + message + "'");

                    try {
                        // The consumer pretends to work for a couple of seconds to allow testing the behavior
                        // if one consumer works faster than the other or one is interrupted.
                        doWork(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        System.out.println(" - DONE");
                        // The acknowledgement is sent as soon as the task is done. Would the consumer be
                        // interrupted, the message would be delivered to the next consumer if there is one.
                        // param2 indicates that one message is acknowledged, not multiple.
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            boolean noAck = false;
            // The built consumer (param4) is started on the opened channel and a specified queue (param1).
            // Acknowledgement is enabled (param2).
            // The consumer is named MyConsumer[count] (param3), which can be inspected on the web-monitoring-tool.
            channel.basicConsume(Configuration.instance.queueName, noAck, "MyConsumer"+nextConsumer, consumer);
            nextConsumer++;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a connection and channel to allow receiving messages.
     *
     * @return                  The channel that can be used to receive messages.
     * @throws IOException      The connection and channel can fail to open.
     * @throws TimeoutException The connection and channel can fail to open.
     */
    private static Channel prepareChannel() throws IOException, TimeoutException {
        // ConnectionFactory is part of the RabbitMQ library, ...
        ConnectionFactory factory = new ConnectionFactory();
        // it is used to connect to the host of the RabbitMQ server, ...
        factory.setHost(Configuration.instance.host);
        // and open a connection to the server.
        Connection connection = factory.newConnection();
        // The connection can be used to create a channel which is also part of the RabbitMQ library.
        Channel channel = connection.createChannel();
        // The channel can declare a basic queue, that can be accessed via its name.
        channel.queueDeclare(Configuration.instance.queueName, false, false, false, null);
        System.out.println("Waiting for messages. To exit press CTRL+C");
        // The channel can now be used to receive messages from its declared queue.
        return channel;
    }

    /**
     * Wait for each '.' in the passed task String one second to pretend work.
     *
     * @param task                  String which contains the delivered message of the producer.
     * @throws InterruptedException Thread.sleep cannot be interrupted.
     */
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}