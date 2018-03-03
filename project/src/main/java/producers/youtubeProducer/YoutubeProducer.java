package producers.youtubeProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import producers.BaseProducer;
import producers.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * A producer for youtube data.
 */
public class YoutubeProducer extends BaseProducer {

    /**
     * Constructor for the YoutubeProducer class.
     *
     * @throws IOException      The RabbitMQ client can throw this is exception.
     * @throws TimeoutException The RabbitMQ client can throw this is exception.
     */
    public YoutubeProducer() throws IOException, TimeoutException {
        this.prepareConnectionAndChannel();
    }

    /**
     * Send a message to the clients.
     *
     * @param payload The payload to send.
     * @throws IOException The RabbitMQ client can throw this is exception.
     */
    private void sendMessage(YoutubeDataDto payload) throws IOException {
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
     * Request and publish the youtube data.
     *
     * @param plzNameMapping The plz and name to query data for.
     * @throws IOException Youtube client can throw this exception.
     */
    public void requestAndPublishYoutubeData(Map.Entry<Integer, String> plzNameMapping) throws IOException {
        YoutubeRequest request = new YoutubeRequest();
        List<YoutubeDataDto> results = request.request(plzNameMapping.getValue(), Configuration.instance.numberOfRequestedVideos, plzNameMapping.getKey());
        for (YoutubeDataDto dto : results) {
            this.sendMessage(dto);
        }
    }
}
