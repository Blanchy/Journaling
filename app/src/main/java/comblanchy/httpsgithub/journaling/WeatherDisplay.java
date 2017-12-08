package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherDisplay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherDisplay extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private OnFragmentInteractionListener mListener;
    private ImageView weatherIcon;
    private int code;
    private TextView temp;
    private double temperature;
    private int PERMISSION_LOCATION_FINE;

    Handler handler;
    public WeatherDisplay() {
        handler = new Handler();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WeatherDisplay.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherDisplay newInstance(String param1, String param2) {
        WeatherDisplay fragment = new WeatherDisplay();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION_FINE);
        }
        else {
            updateWeatherData("Sydney"); //TODO: implement locationmanager
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather_display, container, false);
        weatherIcon = (ImageView) rootView.findViewById(R.id.weathericon);
        temp = (TextView) rootView.findViewById(R.id.temptext);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_LOCATION_FINE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted.
                updateWeatherData("Sydney");
            } else {
                // Permission request was denied.
                updateWeatherData("Manila");
            }
        }
    }

    /******************************************************************************************
     * Weather
     * https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587
     * https://thenounproject.com/ricardovscardoso/collection/meteorology-pack/
     ******************************************************************************************/

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json;
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String locationProvider = locationManager.getBestProvider(criteria, true);

                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.v("Location","no permission");

                    json = RemoteFetch.buildURLFromCity(getActivity(), city);
                }
                else
                {
                    if (locationProvider != null) {
                        Location location = locationManager.getLastKnownLocation(locationProvider); //TODO: request location
                        if (location != null) {
                            json = RemoteFetch.buildURLFromCoord(getActivity(), location.getLatitude(), location.getLongitude());
                        }
                        else {
                            json = RemoteFetch.buildURLFromCity(getActivity(), city);
                        }
                    }
                    else {
                        json = RemoteFetch.buildURLFromCity(getActivity(), city);
                        Log.v("Location", "null provider");
                    }
                }

                //TODO: if location is null, get city from preferences


                if(json == null){
                    Log.v("Weather Handler", "null json");
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    "Place not found",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (json != null){
                    Log.v("Weather Handler", "json retrieved");
                    handler.post(new Runnable(){
                        public void run(){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                renderWeather(json);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");

            temperature = main.getDouble("temp");

            temp.setText(
                    String.format("%.2f", temperature)+ " ℃");

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        code = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                //icon = getActivity().getString(R.string.weather_sunny);
            } else {
                //icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(code) {
                case 2 : icon = "thunder";
                    break;
                case 3 : icon = "drizzle";
                    break;
                case 7 : icon = "foggy";
                    break;
                case 8 : icon = "cloudy";
                    break;
                case 6 : icon = "snowy";
                    break;
                case 5 : icon = "rainy";
                    break;
            }
        }
        //weathericon.setText(icon);
    }

    private void setWeatherFromIntent(int icon, double temp) {
        this.temp.setText(String.valueOf(temp));
        switch(icon) {
            case 2 : ; // thunder
                break;
            case 3 : ; // drizzle
                break;
            case 7 : ; // foggy
                break;
            case 8 : ; // cloudy
                break;
            case 6 : ; // snowy
                break;
            case 5 : ; // rainy
                break;
        }
        //weatherIcon.setImageResource();
    }

    public int getCode() {
        return code;
    }

    public double getTemp() {
        return temperature;
    }
}
