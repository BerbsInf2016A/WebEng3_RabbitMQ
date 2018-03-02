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
}
