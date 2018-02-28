package clients.ClientOne;

import clients.BaseClient;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ClientOne extends BaseClient{

    public String getQueue() throws IOException {
        return recieveChannel.queueDeclare().getQueue();
    }
    public void closeConnection() throws IOException, TimeoutException {
        this.recieveChannel.close();
        this.connection.close();
    }
}
