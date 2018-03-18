package helloworld;

/**
 * Contains the configuration for this example.
 */
public enum Configuration {
    instance;

    /**
     * The name of the queue to use.
     */
    public final static String queueName = "HelloWorldQueue";

    /**
     * The server which is used for the communication.
     */
    public final static String rabbitMQServerHost = "localhost";
}
