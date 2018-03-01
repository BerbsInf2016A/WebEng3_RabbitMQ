package producers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public abstract class BaseProducer {
    protected Connection connection;
    protected Channel sendChannel;
    protected Channel receiveChannel;
    Set<Integer> plzSet;

    public Set<Integer> getPlzSet() {
        return plzSet;
    }

    public BaseProducer() {
        this.plzSet = new HashSet<Integer>();
    }

    public void prepareConnectionAndChannel() throws IOException, TimeoutException {
        System.out.println("send message");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Configuration.instance.rabbitMQServer);

        this.connection = factory.newConnection();
        this.sendChannel = this.connection.createChannel();
        this.sendChannel.exchangeDeclare(Configuration.instance.sendExchangeName, "direct");

        this.receiveChannel = this.connection.createChannel();
        this.receiveChannel.exchangeDeclare(Configuration.instance.receiveExchangeName, "fanout");
        String queueName = this.receiveChannel.queueDeclare().getQueue();
        this.receiveChannel.queueBind(queueName, Configuration.instance.receiveExchangeName, "");

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

    public void closeConnection() throws IOException, TimeoutException {
        this.sendChannel.close();
        this.receiveChannel.close();
        this.connection.close();
    }
}
