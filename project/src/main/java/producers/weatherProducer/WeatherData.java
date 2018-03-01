package producers.weatherProducer;

import producers.BaseDTO;

public class WeatherData extends BaseDTO {
    public WeatherData(int plz) {
        super(plz, "weather");
    }
}
