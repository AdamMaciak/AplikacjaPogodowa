package com.example.aplikacjapogodowa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjapogodowa.parser.Data_converter;
import com.example.aplikacjapogodowa.parser.JSONtoWeather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {

    private final String API="&appid=7941ae49f715949eac590f931fe15f15";
    private final String URL_FORECAST_WEATHER ="https://api.openweathermap.org/data/2.5/forecast?";

    private FusedLocationProviderClient fusedLocationClient;

    private String cord;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button search_button;
    private EditText find_city;
    private TextView temperature;
    private ImageView weather_image;
    private TextView city_name;
    private TextView weather_descripition;
    private TextView wind_direction;
    private TextView wind_speed;
    private TextView humidity;
    private TextView pressure;

    private Interface_manager interface_manager;

    private Toast toast;

    DownloadTask task;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_button=findViewById(R.id.search_button);
        find_city=findViewById(R.id.find_city);
        temperature=findViewById(R.id.temperature);
        weather_image=findViewById(R.id.weather_image);
        city_name=findViewById(R.id.city_name);
        weather_descripition=findViewById(R.id.weather_description);
        wind_speed=findViewById(R.id.wind_speed);
        wind_direction=findViewById(R.id.wind_direction);
        humidity=findViewById(R.id.humidity);
        pressure=findViewById(R.id.pressure);

        requestpermision();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        interface_manager=new Interface_manager();


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(search_button.getWindowToken(), 0);
                find_weather(URL_FORECAST_WEATHER+ "q="+find_city.getText() +"&units=metric"+API);
            }
        });
    }



    public class DownloadTask extends AsyncTask <String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            return new Connection_manager().get_json(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList <WeatherData> weather =new JSONtoWeather().getJsonFromData(s);

            //to ponizej to co innego
            ArrayList <WeatherCard> weather_list = new ArrayList<>();


            for(int i=1;i<weather.size();i++) {
                weather_list.add(new WeatherCard(weather.get(i).getCode_icon(),
                        weather.get(i).getTemp(),weather.get(i).getWeather_data(),
                        weather.get(i).getPressure(),
                        Data_converter.degreetoname(weather.get(i).getWind_direction())+" "+weather.get(i).getWind_speed()+"km/h"));
            }

            try {
                interface_manager.setTextView(city_name, weather.get(0).getCity_name());
                interface_manager.setTextView(temperature, Data_converter.round_numbers(weather.get(0).getTemp())+"\u2103");
                interface_manager.setImageView(weather_image, weather.get(0).getCode_icon());
                interface_manager.setTextView(weather_descripition,weather.get(0).getWeather_description());
                interface_manager.setTextView(pressure,Data_converter.round_numbers(weather.get(0).getPressure())+"hPa");
                interface_manager.setTextView(wind_direction,Data_converter.degreetoname(weather.get(0).getWind_direction()));
                interface_manager.setTextView(wind_speed,weather.get(0).getWind_speed()+"km/h");
                interface_manager.setTextView(humidity,weather.get(0).getHumidity()+"%");

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            mAdapter = new RecycleView_Adapter(weather_list);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);


        }
    }

    //------------------------------------------------------------------------------------------------------//

                                                    //Funkcje//

    //------------------------------------------------------------------------------------------------------//
        private void find_weather(String API_URL) {
            try {
                task = new DownloadTask();
                task.execute(API_URL);
            } catch (Exception e) {
                e.printStackTrace();
                toast = Toast.makeText(this, "error", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        private void requestpermision()
        {
            ActivityCompat.requestPermissions( this,new String[]{  android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
            ActivityCompat.requestPermissions( this,new String[]{  Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        private void find_location() throws Exception {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            throw new Exception("no permission");
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
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
}
