package producers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * A request to resolve a location name for a plz.
 */
public class LocationNameRequest extends BaseRequest {

    /**
     * Request the location name for a plz.
     *
     * @param plz The plz to request the location name for.
     * @return The name for the location.
     */
    public String requestLocationName(int plz) {
        String locationName = null;
        String adr = Configuration.instance.locationResolvingApiUrlPattern.replace("{plz}", String.valueOf(plz));
        URL url = null;
        try {
            url = new URL(adr);
            String response = this.executeGetRequest(url);

            JSONArray places = new JSONObject(response.toString()).getJSONArray("places");
            for (int i = 0; i < places.length(); i++) {
                JSONObject entry = places.getJSONObject(i);
                String keyValue = entry.getString("place name");
                if (keyValue != null && keyValue.length() > 1) {
                    locationName = keyValue;
                    break;
                }
            }

            if (locationName != null && locationName.length() > 0) {
                return locationName;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
