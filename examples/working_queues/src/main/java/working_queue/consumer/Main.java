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


    static int nextConsumer = 0;

    public static void main(String[] args) {
        try {
            System.out.println("Trying to create connection and channel.");
            final Channel channel = prepareChannel();

            try {
                channel.basicQos(1); // accept only one unack-ed message at a time (see below)
            } catch (IOException e) {
                e.printStackTrace();
            }
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received '" + message + "'");

                    try {
                        doWork(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        System.out.println(" - DONE");
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            boolean autoAck = false;
            channel.basicConsume(Configuration.instance.queueName, autoAck, "MyConsumer"+nextConsumer, consumer);
            nextConsumer++;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static Channel prepareChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Configuration.instance.queueName, false, false, false, null);
        System.out.println("Waiting for messages. To exit press CTRL+C");
        return channel;
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}