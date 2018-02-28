package producers;

import producers.weatherProducer.WeatherData;
import producers.weatherProducer.WeatherProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException {
        WeatherProducer weatherProducer = new WeatherProducer();
        weatherProducer.sendMessage(new WeatherData("74706"));
        weatherProducer.sendMessage(new WeatherData("74704"));
        weatherProducer.close();
    }
}
