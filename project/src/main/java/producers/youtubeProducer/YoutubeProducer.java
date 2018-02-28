package producers.youtubeProducer;

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
        String YoutubeDataJson = "";
        this.sendChannel.basicPublish(Configuration.instance.exchangeName, payload.getYoutubeLink(), null, YoutubeDataJson.getBytes());
    }
    public void close() throws IOException, TimeoutException {
        this.closeConnection();
    }
}
