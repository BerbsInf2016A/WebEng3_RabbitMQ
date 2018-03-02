export class Configuration {
    /**
     * The name of the exchange to use when sending messages
     * to the produces.
     */
    static sendExchangeName: String =  'PLZRegisterExchange';
    /**
     * The name of the exchange to use when receiving messages
     * from the producers.
     */
    static receiveExchangeName: String =  'PLZDataExchange';
    /**
     * Address of the RabbitMQ server.
     */
    static rabbitMQServer: String = 'localhost';
}
