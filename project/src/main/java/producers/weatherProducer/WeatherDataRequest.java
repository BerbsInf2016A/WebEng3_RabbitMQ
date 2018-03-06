package producers.weatherProducer;


import com.google.api.services.youtube.model.ResourceId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import producers.Configuration;
import producers.BaseRequest;
import java.io.IOException;

import java.net.URL;

public class WeatherDataRequest extends BaseRequest {
    public WeatherDataDto request(Integer plz, String locationName) {
        String adr = Configuration.instance.openWeatherDataURLPattern
                .replace("{plz}" , String.valueOf(plz))
                .replace("{apikey}", Configuration.instance.openWeatherApiKey);

        URL url = null;
        try {
            url = new URL(adr);
            String response = this.executeRequest(url, "GET");

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
