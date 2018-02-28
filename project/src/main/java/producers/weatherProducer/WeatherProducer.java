package producers.weatherProducer;

import producers.BaseProducer;
import producers.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WeatherProducer extends BaseProducer{

    public WeatherProducer() throws IOException, TimeoutException {
        this.prepareConnectionAndChannel();
    }

    public void sendMessage(WeatherData payload) throws IOException {
        String weatherDataJson = "Test";
        this.sendChannel.basicPublish(Configuration.instance.exchangeName, payload.getPLZ(), null, weatherDataJson.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        this.closeConnection();
    }
}
