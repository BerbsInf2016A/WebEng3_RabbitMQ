package publish_subscribe.producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import publish_subscribe.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {


    private static Connection connection;
    private static Channel channel;

    public static void main(String[] args) {
	    boolean abort = false;
        Channel channel = null;
        try {
            System.out.println("Trying to create connection and channel.");
            channel = prepareChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        while(true) {
            //abort = !askUserToSendAnotherMessage();
            abort = false;
            if (abort) break;

            try {
                produceMessage(channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Trying to close the connections.");
        try {
            if (channel != null){
                channel.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean askUserToSendAnotherMessage() {
        System.out.println("Do you want to produce a message? y or yes to produce a message, n or no to exit.");
        Scanner input = new Scanner(System.in);
        String response = input.next();

        if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
            return true;
        }
        if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
            return false;
        }
        return false;
    }

    private static void produceMessage(Channel channel) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);

        String message = "Hello World! " + strDate;
        channel.basicPublish("", Configuration.instance.queueName, null, message.getBytes());
        System.out.println("Sent '" + message + "'");
    }

    private static Channel prepareChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(Configuration.instance.queueName, false, false, false, null);
        return channel;
    }
}
