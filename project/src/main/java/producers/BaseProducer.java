package producers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

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
     * A map of PLZs ("Postleitzahlen" or zip codes) and their location name.
     */
    HashMap<String, String> plzMap;

    /**
     * Constructor for the BaseProducer class.
     */
    public BaseProducer() {
        this.plzMap = new HashMap();
    }

    /**
     * Getter for the "Postleitzahlen" or zip codes.
     *
     * @return A set of integers, representing the zip codes.
     */
    public HashMap<String, String> getPlzMap() {
        return plzMap;
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
                System.out.println(" [x] Received PLZ '" + message + "'");
                upsertPLZ(message);

            }
        };
        this.receiveChannel.basicConsume(queueName, true, consumer);

    }

    /**
     * Insert a plz to the collection if it is not already a part of the inner collection.
     *
     * @param plzString The plz.
     */
    private void upsertPLZ(String plzString) {
        if (Pattern.matches("\\d{4}", plzString))
                plzString = "0" + plzString;
        if (Pattern.matches("\\d{5}", plzString)) {
            if (!this.plzMap.containsKey(plzString)) {
                LocationNameRequest request = new LocationNameRequest();
                String locationName = request.requestLocationName(plzString);
                if (locationName != null) {
                    System.out.println("Adding " + plzString + " " + locationName + " to the source list.");
                    this.plzMap.put(plzString, locationName);
                } else {
                    System.out.print("Could not resolve location name for " + plzString + ".");
                }
            }
        } else {
            System.out.print("Invalid PLZ. Must be 5 digits.");
        }

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
