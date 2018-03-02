package producers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * An abstract base class for producers.
 */
public abstract class BaseProducer {
    /**
     * The RabbitMQ connection.
     */
    protected Connection connection;
    /**
     * The channel to send messages to the clients.
     */
    protected Channel sendChannel;
    /**
     * The channel to receive messages from the clients.
     */
    protected Channel receiveChannel;
    /**
     * A set of PLZs ("Postleitzahlen" or zip codes).
     */
    Set<Integer> plzSet;

    /**
     * Constructor for the BaseProducer class.
     */
    public BaseProducer() {
        this.plzSet = new HashSet<Integer>();
    }

    /**
     * Getter for the "Postleitzahlen" or zip codes.
     *
     * @return A set of integers, representing the zip codes.
     */
    public Set<Integer> getPlzSet() {
        return plzSet;
    }

    /**
     * Connect and create the channels.
     *
     * @throws IOException      The RabbitMQ client can throw this is exception.
     * @throws TimeoutException The RabbitMQ client can throw this is exception.
     */
    public void prepareConnectionAndChannel() throws IOException, TimeoutException {
        System.out.println("send message");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Configuration.instance.rabbitMQServer);

        this.connection = factory.newConnection();
        this.sendChannel = this.connection.createChannel();
        this.sendChannel.exchangeDeclare(Configuration.instance.sendExchangeName, "direct", true);

        this.receiveChannel = this.connection.createChannel();
        this.receiveChannel.exchangeDeclare(Configuration.instance.receiveExchangeName, "fanout", true);
        String queueName = this.receiveChannel.queueDeclare().getQueue();
        this.receiveChannel.queueBind(queueName, Configuration.instance.receiveExchangeName, "");

        // Save the received in the set of plzs.
        Consumer consumer = new DefaultConsumer(this.receiveChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                plzSet.add(Integer.parseInt(message));
            }
        };
        this.receiveChannel.basicConsume(queueName, true, consumer);

    }

    /**
     * Close all connections and channels.
     *
     * @throws IOException      The RabbitMQ client can throw this is exception.
     * @throws TimeoutException The RabbitMQ client can throw this is exception.
     */
    public void closeConnection() throws IOException, TimeoutException {
        this.sendChannel.close();
        this.receiveChannel.close();
        this.connection.close();
    }
}
