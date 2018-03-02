export class WeatherData {
    temperature: number;
    locationName: String;
    creationDate: String;
    plz: number;
    typeIdentifier: String;

    constructor(plz: number, creationDate: String, locationName: String, temperature: number ){
        this.plz = plz;
        this.creationDate = creationDate;
        this.locationName = locationName;
        this.temperature = temperature;
    }
}
