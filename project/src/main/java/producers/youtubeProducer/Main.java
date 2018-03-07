package producers.youtubeProducer;

import producers.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Youtube-Producer started");
        YoutubeProducer producer = new YoutubeProducer();
        while (true) {
            try {
                System.out.println("Youtube-Producer: Request and publish for " + producer.getPlzMap().size() + " PLZs.");
                for (Map.Entry<String, String> mapping : producer.getPlzMap().entrySet()) {
                    producer.requestAndPublishYoutubeData(mapping);
                }
                Thread.sleep(Configuration.instance.sendingIntervalInMs);
            } catch (Exception e) {
                //Catch only Exceptions to prevent Shutdown of producer.
                e.printStackTrace();
            }
        }
    }
}
