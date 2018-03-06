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
            System.out.println("Youtube-Producer: Request and publish for " + producer.getPlzMap().size() + " PLZs.");
            for (Map.Entry<Integer, String> mapping : producer.getPlzMap().entrySet()) {
                producer.requestAndPublishYoutubeData(mapping);
            }
            Thread.sleep(Configuration.instance.sendingIntervalInMs);
        }
    }
}
