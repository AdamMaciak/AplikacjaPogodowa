package com.example.aplikacjapogodowa;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView resultTextView;
    Button button;
    DownloadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.city);
        resultTextView = findViewById(R.id.info);
        button=findViewById(R.id.search);
        task = new DownloadTask();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    task = new DownloadTask();
                    task.execute(); //7941ae49f715949eac590f931fe15f15
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();

                    resultTextView.setText("nie moze znalezc");
                }
            }
        });
    }


    public class DownloadTask extends AsyncTask<String,Void,String> {

        private final String API_URL="https://api.openweathermap.org/data/2.5/weather?q=" + editText.getText().toString() + "&appid=7941ae49f715949eac590f931fe15f15";

        @Override
        protected String doInBackground(String... urls)
        {
            URLConnection url;
            String json="";

            try
            {
                url = new URL(API_URL).openConnection();
                InputStream is = url.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;

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
                e.printStackTrace();
            }
            return json;
        }

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

                for (int i=0; i < arrWeather.length(); i++) {

                    JSONObject jsonPart = arrWeather.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if (!main.equals("") && !description.equals("")) {
                        message += main + ": " + description + "\r\n";
                    }
                }

                String temp=jsonObject.getJSONObject("main").getString("temp");
                String pressure =jsonObject.getJSONObject("main").getString("pressure");
                String humidity=jsonObject.getJSONObject("main").getString("humidity");

                double tempdouble=Double.parseDouble(temp);
                tempdouble=tempdouble-273;
                //Math.round(tempdouble);

                temp=Double.toString(tempdouble);
                Log.i("pokonwersji",temp);


                message += "Temp: " + temp + "C" + "\r\n" + "Pressure: " + pressure +" hPa" + "\r\n" + "Humidity: " + humidity + "%" + "\r\n";


                if (!message.equals("")) {
                    resultTextView.setText(message);
                } else {

                    resultTextView.setText("didnt find the weather");
                }

            } catch (Exception e) {


                resultTextView.setText("can't parse JSON");
                e.printStackTrace();
            }

        }
    }
}
