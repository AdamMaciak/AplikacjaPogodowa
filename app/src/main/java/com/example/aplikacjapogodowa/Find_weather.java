package com.example.aplikacjapogodowa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Find_weather extends Fragment {

    private final String URL_CURRENT="https://api.openweathermap.org/data/2.5/weather?q=";
   // private final String URL_FORECAST="https://api.openweathermap.org/data/2.5/forecast?q=";
    private final String API= "&appid=7941ae49f715949eac590f931fe15f15";


    //do wyszukiwania pogody
    EditText search_city;
    Button search;

    //layout glowny na obecna pogode
    TextView city_name;
    TextView temp_text_1;
    ImageView weather_image_1;
    TextView name_day;

    // layout z opisem pogody
    TextView description_text;
    TextView pressure_text;

    DownloadTask task;

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

        task = new DownloadTask();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    task = new DownloadTask();
                    task.execute( URL_CURRENT+ search_city.getText().toString() + API); //7941ae49f715949eac590f931fe15f15
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(search_city.getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                    pressure_text.setText("error");
                }
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.find_weather, container, false);
        return v;
    }

    public static Find_weather newInstance() {
        Find_weather f = new Find_weather();
        return f;
    }



    public class DownloadTask extends AsyncTask<String,Void,String> {

//        private final String API_URL="https://api.openweathermap.org/data/2.5/weather?q=" + search_city.getText().toString() + "&appid=7941ae49f715949eac590f931fe15f15";

///////////////////////////////////////////////////
        @Override
        protected String doInBackground(String... urls)
        {
            URLConnection url;
            String json="";
            try
            {
                url = new URL(urls[0]).openConnection();
                InputStream is = url.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;

                while ((line = reader.readLine()) != null) // read line by line
                {
                    json+=line;
                    Log.i("text",line);
                }
                is.close();
                return json;
            }
            catch (Exception e)
            {
                Log.i("doinback","blad");
                e.printStackTrace();
            }
            return json;
        }
////////////////////////////////////////////////////


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ONPOSTEXECUTE",s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");
                //String mainInfo=jsonObject.getString("main");

                Log.i("Weather content", weatherInfo);
                JSONArray arrWeather = new JSONArray(weatherInfo);
                String message = "";
                String code_image=null;
                for (int i=0; i < arrWeather.length(); i++) {

                    JSONObject jsonPart = arrWeather.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    code_image=jsonPart.getString("icon");
                    Log.i("ikona",code_image);
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

/////////////////////////////////////////////////////////////////////////////////////
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
                    weather_image_1.setImageResource(R.drawable.darkclou);
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

                if (!message.equals("")) {
                    temp_text_1.setText(temp+"C");
                    description_text.setText(message);
                    pressure_text.setText(pressure+"hPa");
                    city_name.setText(name);

                } else {

                    description_text.setText("didnt find the weather");
                }

            } catch (Exception e) {


                description_text.setText("can't parse JSON");
                e.printStackTrace();
            }

        }
    }

}
