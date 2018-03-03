package producers;

/**
 * Contains common configuration parameters.
 */
public enum Configuration {
    instance;

    /**
     * The name of the exchange to send data from the producers to the clients.
     */
    public String sendExchangeName = "PLZDataExchange";
    /**
     * The name of the exchange to send data from the clients to the producers.
     */
    public String receiveExchangeName = "PLZRegisterExchange";
    /**
     * Address of the RabbitMQ Server.
     */
    public String rabbitMQServer = "localhost";
    /**
     * The interval to send data from the producers to the clients.
     * In milliseconds.
     */
    public int sendingIntervalInMs = 20000;

    /**
     * The url with a placeholder to resolve the location name for a plz.
     */
    public String locationResolvingApiUrlPattern = "http://api.zippopotam.us/de/{plz}";
    /**
     * The user agent to use.
     */
    public String userAgent = "Mozilla/5.0";

    /**
     * The api key which is used to query youtube data.
     */
    public String youtubeApiKey = "ENTER YOUR API KEY HERE";

    /**
     * Prefix to generate the url to the youtube videos.
     */
    public String youtubeVideoURLPrefix = "https://www.youtube.com/watch?v=";

    /**
     * The number of requested videos per PLZ.
     */
    public long numberOfRequestedVideos = 5L;
}
