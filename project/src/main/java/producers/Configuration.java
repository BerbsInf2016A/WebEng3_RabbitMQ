package producers;

public enum Configuration {
    instance;

    public static String sendExchangeName =  "PLZDataExchange";
    public static String receiveExchangeName =  "PLZRegisterExchange";
    public static String rabbitMQServer = "localhost";
    public int sendingDelay = 20000;
}
