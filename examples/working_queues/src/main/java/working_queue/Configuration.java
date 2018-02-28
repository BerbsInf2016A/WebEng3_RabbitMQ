package working_queue;


public enum Configuration {
    instance;

    public final static String queueName = "WorkingQueue";
    public int nextConsumer = 0;
}
