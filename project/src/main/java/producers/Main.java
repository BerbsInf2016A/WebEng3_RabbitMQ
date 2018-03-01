package producers;

import producers.weatherProducer.WeatherData;
import producers.weatherProducer.WeatherProducer;
import producers.youtubeProducer.YoutubeData;
import producers.youtubeProducer.YoutubeProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        WeatherProducer weatherProducer = new WeatherProducer();
        YoutubeProducer youtubeProducer = new YoutubeProducer();
        try {

            while (true) {
                for (int plz : weatherProducer.getPlzSet()) {
                    weatherProducer.sendMessage(new WeatherData(plz));
                }
                for (int plz : youtubeProducer.getPlzSet()) {
                    youtubeProducer.sendMessage(new YoutubeData(plz));
                }
                Thread.sleep(Configuration.instance.sendingDelay);
            }
        } finally {
            weatherProducer.close();
        }



    }
}
