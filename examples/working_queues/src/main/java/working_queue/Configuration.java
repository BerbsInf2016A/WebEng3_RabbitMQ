package working_queue;

/**
 * Contains common configuration parameters.
 */
public enum Configuration {
    instance;

    /**
     * Identifier of the queue used in Producer and Consumer
     */
    public String queueName = "WorkingQueue";

    /**
     * Host of the RabbitMQ server
     */
    public String host = "localhost";
}
