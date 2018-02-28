package producers.weatherProducer;

import producers.BaseDTO;

public class WeatherData extends BaseDTO {
    public WeatherData(String plz) {
        super(plz, "weather");
    }
}
