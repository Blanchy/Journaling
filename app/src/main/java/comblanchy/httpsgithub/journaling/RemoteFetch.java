package comblanchy.httpsgithub.journaling;

/**
 * Taken from https://github.com/tutsplus/AndroidWeatherApp
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial";
    private static final String OPEN_WEATHER_MAP_COORDINATES =
            "api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s";

    public static JSONObject buildURLFromCity(Context context, String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            return getJSON(context, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject buildURLFromCoord(Context context, double lat, double lon) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_COORDINATES, lat, lon));
            return getJSON(context, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSON(Context context, URL url){

        try {

            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}