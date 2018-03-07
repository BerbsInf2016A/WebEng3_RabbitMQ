package producers.weatherProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import producers.BaseProducer;
import producers.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

/**
 * A producer for weather data.
 */
public class WeatherProducer extends BaseProducer {
    /**
     * The weather data request.
     */
    private WeatherDataRequest weatherDataRequest;

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

    /**
     * Request the weather data from the api and publish the received data to the clients.
     *
     * @param plzNameMapping Mapping for the plz and location. Key = plz and value = locationName.
     * @throws IOException Requesting and sending data can throw an IOException.
     */
    public void requestAndPublishWeatherData(Map.Entry<String, String> plzNameMapping) throws IOException {
        if (this.weatherDataRequest == null)
            this.weatherDataRequest = new WeatherDataRequest();
        WeatherDataDto result = this.weatherDataRequest.execute(plzNameMapping.getKey(), plzNameMapping.getValue());
        if (result != null)
            this.sendMessage(result);
    }
}
