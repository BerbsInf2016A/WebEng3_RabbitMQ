package producers.weatherProducer;

import producers.BaseDTO;

public class WeatherData extends BaseDTO {
    public int getTemperature() {
        return temperature;
    }



    private final int temperature;

    public WeatherData(int plz, int temperature, String locationName) {
        super(plz, "weather", locationName);
        this.temperature = temperature;
    }
}
