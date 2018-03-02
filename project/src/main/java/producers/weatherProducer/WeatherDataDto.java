package producers.weatherProducer;

import producers.BaseDTO;

/**
 * A data transfer object for weather data.
 */
public class WeatherDataDto extends BaseDTO {
    /**
     * The temperature.
     */
    private final int temperature;

    /**
     * Constructor for the WeatherDto class.
     *
     * @param plz          The "Postleitzahl" or zip code of the dto.
     * @param temperature  The temperature.
     * @param locationName The name of the location this dto is for.
     */
    public WeatherDataDto(int plz, int temperature, String locationName) {
        super(plz, "weather", locationName);
        this.temperature = temperature;
    }

    /**
     * Get the temperature.
     *
     * @return The temperature.
     */
    public int getTemperature() {
        return temperature;
    }
}
