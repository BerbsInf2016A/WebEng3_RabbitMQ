package producers.weatherProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import producers.BaseProducer;
import producers.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class WeatherProducer extends BaseProducer{

    public WeatherProducer() throws IOException, TimeoutException {
        this.prepareConnectionAndChannel();
    }

    public void sendMessage(WeatherData payload) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payload);
        this.sendChannel.basicPublish(Configuration.instance.sendExchangeName, payload.getPlzString(), null, json.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        this.closeConnection();
    }
}
