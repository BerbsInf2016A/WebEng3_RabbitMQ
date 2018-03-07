/** A class containing weather data. */
export class WeatherData {
    /**
     * The current temperature.
     */
    temperature: number;

    /**
     * The minimum temperature.
     */
    minTemperature: number;

    /**
     * The maximum temperature.
     */
    maxTemperature: number;

    /**
     * The name of the location this data is for.
     */
    locationName: String;
    /**
     * The creation date of this data.
     */
    creationDate: String;
    /**
     * The "Postleitzahl" or zip code for this data.
     */
    plz: String;

    /**
     * Constructor for the weather data class.
     *
     * @param plz The "Postleitzahl" or zip code for this data.
     * @param creationDate The creation date of this data.
     * @param locationName The name of the location this data is for.
     * @param temperature The current temperature.
     * @param minTemperature The minimum temperature.
     * @param maxTemperature The maximum temperature.
     */
    constructor(plz: String, creationDate: String, locationName: String, temperature: number,
            minTemperature: number, maxTemperature: number ) {
        this.plz = plz;
        this.creationDate = creationDate;
        this.locationName = locationName;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }
}
