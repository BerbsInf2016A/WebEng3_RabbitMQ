package producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class BaseProducer {
    protected Connection connection;
    protected Channel sendChannel;

    public void prepareConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Configuration.instance.rabbitMQServer);

        this.connection = factory.newConnection();
        this.sendChannel = this.connection.createChannel();
        this.sendChannel.exchangeDeclare(Configuration.instance.exchangeName, "direct");
    }

    public void closeConnection() throws IOException, TimeoutException {
        this.sendChannel.close();
        this.connection.close();
    }
}
