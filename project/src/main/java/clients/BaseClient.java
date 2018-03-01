package clients;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import producers.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class BaseClient {

    protected Connection connection;
    protected Channel recieveChannel;

    public void prepareConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Configuration.instance.rabbitMQServer);
        this.connection = factory.newConnection();
        this.recieveChannel = connection.createChannel();
        this.recieveChannel.exchangeDeclare(Configuration.instance.sendExchangeName,"direct");
    }

}
