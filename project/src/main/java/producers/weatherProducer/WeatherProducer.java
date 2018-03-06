package producers.weatherProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import producers.BaseProducer;
import producers.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * A producer for weather data.
 */
public class WeatherProducer extends BaseProducer {

    /**
     * Constructor for the WeatherProducer class.
     * Will create the connection and channels.
     *
     * @throws IOException      The RabbitMQ client can throw this is exception.
     * @throws TimeoutException The RabbitMQ client can throw this is exception.
     */
    public WeatherProducer() throws IOException, TimeoutException {
        this.prepareConnectionAndChannel();
    }

    /**
     * Send a message to the clients.
     *
     * @param payload The payload to send.
     * @throws IOException The RabbitMQ client can throw this is exception.
     */
    public void sendMessage(WeatherDataDto payload) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payload);
        this.sendChannel.basicPublish(Configuration.instance.sendExchangeName, payload.getPlzString(), null, json.getBytes());
    }

    /**
     * Close all connections and channels.
     *
     * @throws IOException      The RabbitMQ client can throw this is exception.
     * @throws TimeoutException The RabbitMQ client can throw this is exception.
     */
    public void close() throws IOException, TimeoutException {
        this.closeConnection();
    }

    public void requestAndPublishWeatherData(Map.Entry<Integer, String> plzNameMapping) throws IOException {
        WeatherDataRequest request = new WeatherDataRequest();
        WeatherDataDto result = request.request(plzNameMapping.getKey(), plzNameMapping.getValue());
        if (result != null)
            this.sendMessage(result);
    }
}
