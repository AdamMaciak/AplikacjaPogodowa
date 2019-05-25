package com.example.aplikacjapogodowa.parser;

import com.example.aplikacjapogodowa.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONtoWeather {

    public ArrayList<WeatherData> getJsonFromData(String str)
    {
        ArrayList<WeatherData> weather_array=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(str);
            String list=jsonObject.getString("list");
            JSONArray arr_list=new JSONArray(list);

            for(int i=0;i<arr_list.length();i++)
            {
                JSONArray jsonpart_weather_arr=new JSONArray(arr_list.getJSONObject(i).getString("weather"));
                weather_array.add(new WeatherData(
                        arr_list.getJSONObject(i).getJSONObject("main").getString("temp"),
                        arr_list.getJSONObject(i).getJSONObject("main").getString("pressure"),
                        arr_list.getJSONObject(i).getString("dt_txt"),
                        jsonpart_weather_arr.getJSONObject(0).getString("main"),
                        arr_list.getJSONObject(i).getJSONObject("main").getString("humidity"),
                        arr_list.getJSONObject(i).getJSONObject("wind").getString("deg"),
                        arr_list.getJSONObject(i).getJSONObject("wind").getString("speed"),
                        jsonObject.getJSONObject("city").getString("name"),
                        jsonpart_weather_arr.getJSONObject(0).getString("icon")
                        ));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather_array;
    }
}
