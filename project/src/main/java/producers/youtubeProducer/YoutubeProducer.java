package producers.youtubeProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import producers.BaseProducer;
import producers.Configuration;
import producers.weatherProducer.WeatherData;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class YoutubeProducer extends BaseProducer {

    public YoutubeProducer() throws IOException, TimeoutException {
        this.prepareConnectionAndChannel();
    }

    public void sendMessage(YoutubeData payload) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payload);
        this.sendChannel.basicPublish(Configuration.instance.exchangeName, payload.getPlz(), null, json.getBytes());
    }
    public void close() throws IOException, TimeoutException {
        this.closeConnection();
    }
}
