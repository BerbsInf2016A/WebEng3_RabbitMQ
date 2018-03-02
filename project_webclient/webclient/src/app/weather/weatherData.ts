/** A class containing weather data. */
export class WeatherData {
    /**
     * The current temperature.
     */
    temperature: number;
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
    plz: number;

    /**
     * Constructor for the weather data class.
     *
     * @param plz The "Postleitzahl" or zip code for this data.
     * @param creationDate The creation date of this data.
     * @param locationName The name of the location this data is for.
     * @param temperature The current temperature.
     */
    constructor(plz: number, creationDate: String, locationName: String, temperature: number ) {
        this.plz = plz;
        this.creationDate = creationDate;
        this.locationName = locationName;
        this.temperature = temperature;
    }
}
