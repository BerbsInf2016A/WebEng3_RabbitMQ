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
                System.out.println("Publish messages interval reached");
                for (int plz : weatherProducer.getPlzSet()) {
                    weatherProducer.sendMessage(new WeatherData(plz, 10, "Testname_" + plz));
                }
                for (int plz : youtubeProducer.getPlzSet()) {
                    youtubeProducer.sendMessage(new YoutubeData(plz, "Testname_" + plz,"Mosbach , Fahr mal hin (SWR 2002)",
                            "https://www.youtube.com/watch?v=PCMRGjlSGxg", "https://i.ytimg.com/vi/PCMRGjlSGxg/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLBC3FLhBraxuS-r-zoaB3E5OEey8A"));
                    youtubeProducer.sendMessage(new YoutubeData(plz, "Testname_" + plz,"Vlog #1 in Mosbach Teil 1",
                            "https://www.youtube.com/watch?v=lPfQGbmdTFM", "https://i.ytimg.com/vi/lPfQGbmdTFM/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLBpTQ3h0rbG-Fr5CINLTnIpUe-vag"));
                }
                Thread.sleep(Configuration.instance.sendingDelay);
            }
        } finally {
            weatherProducer.close();
        }



    }
}
