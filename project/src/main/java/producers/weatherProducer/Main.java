package producers.weatherProducer;

import producers.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Weather-Producer started");
        WeatherProducer producer = new WeatherProducer();
        while (true) {
            System.out.println("Weather-Producer: Request and publish for " + producer.getPlzMap().size() + " PLZs.");
            for (Map.Entry<Integer, String> mapping : producer.getPlzMap().entrySet()) {
                producer.requestAndPublishWeatherData(mapping);
            }
            Thread.sleep(Configuration.instance.sendingIntervalInMs);
        }
    }
}
