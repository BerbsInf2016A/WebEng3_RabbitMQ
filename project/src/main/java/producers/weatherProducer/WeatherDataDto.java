package producers.weatherProducer;

import producers.BaseDTO;

/**
 * A data transfer object for weather data.
 */
public class WeatherDataDto extends BaseDTO {
    /**
     * The temperature.
     */
    private final double temperature;
    private final double minTemperature;
    private final double maxTemperature;

    /**
     * Constructor for the WeatherDto class.
     *
     * @param plz The "Postleitzahl" or zip code of the dto.
     * @param temperature The temperature.
     * @param minTemperature The minimum temperature.
     * @param maxTemperature The maximum temperature.
     * @param locationName The name of the location this dto is for.
     */
    public WeatherDataDto(int plz, double temperature, double minTemperature, double maxTemperature, String locationName) {
        super(plz, "weather", locationName);
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    /**
     * Get the temperature.
     *
     * @return The temperature.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Get the minimum temperature.
     *
     * @return The minimum temperature.
     */
    public double getMinTemperature() {
        return minTemperature;
    }

    /**
     * Get the maximum temperature.
     *
     * @return The maximum temperature.
     */
    public double getMaxTemperature() {
        return maxTemperature;
    }
}
