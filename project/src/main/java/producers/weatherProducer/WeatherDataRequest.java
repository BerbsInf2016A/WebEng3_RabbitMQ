package producers.weatherProducer;


import org.json.JSONException;
import org.json.JSONObject;
import producers.BaseRequest;
import producers.Configuration;

import java.io.IOException;
import java.net.URL;

/**
 * A request to query weather data.
 */
public class WeatherDataRequest extends BaseRequest {

    /**
     * Execute the weather data request.
     *
     * @param plz The "Postleitzahl" or zip code.
     * @param locationName The name of the location.
     * @return Null, if no weather could be received. The weather dto if successful.
     */
    public WeatherDataDto execute(Integer plz, String locationName) {
        String adr = Configuration.instance.openWeatherDataURLPattern
                .replace("{plz}", String.valueOf(plz))
                .replace("{apikey}", Configuration.instance.openWeatherApiKey);

        URL url = null;
        try {
            url = new URL(adr);
            String response = this.executeGetRequest(url);

            // Parse the response.
            JSONObject responseObject = new JSONObject(response);
            JSONObject weather = responseObject.getJSONObject("main");
            double curTemp = weather.getDouble("temp");
            double tempMin = weather.getDouble("temp_min");
            double tempMax = weather.getDouble("temp_max");
            return new WeatherDataDto(plz, curTemp, tempMin, tempMax, locationName);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
