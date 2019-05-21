package com.example.aplikacjapogodowa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Find_weather extends Fragment {

    private final String URL_CURRENT = "https://api.openweathermap.org/data/2.5/weather?";
    static final String API = "&appid=7941ae49f715949eac590f931fe15f15";

    private FusedLocationProviderClient fusedLocationClient;

    private String FULL_URL;

    private String cord;

    //do wyszukiwania pogody
    EditText search_city;
    Button search;
    Button search_local;

    //layout glowny na obecna pogode

    TextView city_name;
    TextView temp_text_1;
    ImageView weather_image_1;
    TextView name_day;

    // layout z opisem pogody

    TextView description_text;
    TextView pressure_text;

    DownloadTask task;

    Date now;
    SimpleDateFormat simpleDateFormat;
    Toast toast;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        city_name = getActivity().findViewById(R.id.city);
        temp_text_1 = getActivity().findViewById(R.id.temp_1);
        weather_image_1 = getActivity().findViewById(R.id.weather_image_1);
        search_city = getActivity().findViewById(R.id.search_city);
        pressure_text = getActivity().findViewById(R.id.pressure);
        description_text = getActivity().findViewById(R.id.description);
        name_day = getActivity().findViewById(R.id.name_day);
        search = getActivity().findViewById(R.id.search_button);
        search_local = getActivity().findViewById(R.id.geolocal_button);

        task = new DownloadTask();

        now = new Date();
        simpleDateFormat = new SimpleDateFormat("EEEE");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FULL_URL=URL_CURRENT +"q=" +search_city.getText().toString() + API;
                find_weather(FULL_URL);
            }
        });

        search_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    find_location();
                } catch (Exception e) {
                    toast = Toast.makeText(getActivity(), "no permission", Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                FULL_URL=URL_CURRENT+cord+API;
                find_weather(FULL_URL);
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_weather, container, false);
    }

    public static Find_weather newInstance() {
        return new Find_weather();
    }

    private void find_weather(String API_URL) {
        try {
            task = new DownloadTask();
            //task.execute(URL_CURRENT +"q=" search_city.getText().toString() + API); //7941ae49f715949eac590f931fe15f15
            task.execute(API_URL);
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(search_city.getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
            toast = Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void find_location() throws Exception {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            throw new Exception("no permission");
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location){

                if (location != null) {
                    cord="lat="+String.format("%.2f",location.getLatitude())+"&lon="+String.format("%.2f",location.getLongitude());
                    cord=cord.replace(",",".");
                    Log.i("cord",cord);
                }
            }
        });
    }


    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            return new Connection_manager().get_json(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");
                //String mainInfo=jsonObject.getString("main");

                Log.i("Weather content", weatherInfo);
                JSONArray arrWeather = new JSONArray(weatherInfo);
                String message ="";
                String code_image="";

                for (int i=0; i < arrWeather.length(); i++) {

                    JSONObject jsonPart = arrWeather.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    code_image=jsonPart.getString("icon");

                    if (!main.equals("") && !description.equals("")) {
                        message += main + ": " + description + "\r\n";
                    }
                }

                String temp=jsonObject.getJSONObject("main").getString("temp");
                String pressure =jsonObject.getJSONObject("main").getString("pressure");
                String name=jsonObject.getString("name");

                double tempdouble=Double.parseDouble(temp);
                tempdouble=tempdouble-273;
                int castint=(int)Math.round(tempdouble);
                temp=Integer.toString(castint);

                setImage(code_image);

                if (!message.equals("")) {
                    temp_text_1.setText(temp+"C");
                    description_text.setText(message);
                    pressure_text.setText("Pressure: "+pressure+"hPa");
                    city_name.setText(name);
                }

                name_day.setText(simpleDateFormat.format(now));

            } catch (Exception e) {

                toast=Toast.makeText(getActivity(),"didnt find the weather",Toast.LENGTH_LONG);
                toast.show();
                e.printStackTrace();
            }
        }

        private void setImage(String code_image)
        {
            if(code_image.equals("01d")||code_image.equals("01n")) {
                weather_image_1.setImageResource(R.drawable.sun);
            }
            else if(code_image.equals("02d")||code_image.equals("02n")) {
                weather_image_1.setImageResource(R.drawable.cloud);
            }
            else if(code_image.equals("03d")||code_image.equals("03n")) {
                weather_image_1.setImageResource(R.drawable.cloudy);
            }
            else if(code_image.equals("04d")||code_image.equals("04n")) {
                weather_image_1.setImageResource(R.drawable.darkcloud);
            }
            else if(code_image.equals("09d")||code_image.equals("09n")) {
                weather_image_1.setImageResource(R.drawable.raining);
            }
            else if(code_image.equals("10d")||code_image.equals("10n")) {
                weather_image_1.setImageResource(R.drawable.rain);
            }
            else if(code_image.equals("11d")||code_image.equals("11n")) {
                weather_image_1.setImageResource(R.drawable.lightning);
            }
            else if(code_image.equals("13d")||code_image.equals("13n")) {
                weather_image_1.setImageResource(R.drawable.snowfall);
            }
            else if(code_image.equals("50d")||code_image.equals("50n")) {
                weather_image_1.setImageResource(R.drawable.mist);
            }
        }
    }
}
