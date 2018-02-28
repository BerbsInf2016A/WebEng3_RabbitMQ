package clients;

import clients.ClientOne.ClientOne;
import producers.weatherProducer.WeatherData;
import producers.weatherProducer.WeatherProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException {
        ClientOne clientOne = new ClientOne();
        System.out.println(clientOne.getQueue());
    }
}
