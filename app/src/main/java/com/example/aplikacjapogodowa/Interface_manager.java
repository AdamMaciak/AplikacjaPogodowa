package com.example.aplikacjapogodowa;


import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

public class Interface_manager {


    public void setImageView(ImageView image, String code_image)
    {

            if(code_image.equals("01d")||code_image.equals("01n")) {
                image.setImageResource(R.drawable.sun);
            }
            else if(code_image.equals("02d")||code_image.equals("02n")) {
                image.setImageResource(R.drawable.cloud);
            }
            else if(code_image.equals("03d")||code_image.equals("03n")) {
                image.setImageResource(R.drawable.cloudy);
            }
            else if(code_image.equals("04d")||code_image.equals("04n")) {
                image.setImageResource(R.drawable.darkcloud);
            }
            else if(code_image.equals("09d")||code_image.equals("09n")) {
                image.setImageResource(R.drawable.raining);
            }
            else if(code_image.equals("10d")||code_image.equals("10n")) {
                image.setImageResource(R.drawable.rain);
            }
            else if(code_image.equals("11d")||code_image.equals("11n")) {
                image.setImageResource(R.drawable.lightning);
            }
            else if(code_image.equals("13d")||code_image.equals("13n")) {
                image.setImageResource(R.drawable.snowfall);
            }
            else if(code_image.equals("50d")||code_image.equals("50n")) {
                image.setImageResource(R.drawable.mist);
            }

    }
    public void setImageView(RecycleView_Adapter.ViewHolder image, String code_image)
    {
            if(code_image.equals("01d")||code_image.equals("01n")) {
            image.weather_image.setBackgroundResource(R.drawable.mist);
            //setImageResource(R.drawable.sun);
            }
            else if(code_image.equals("02d")||code_image.equals("02n")) {
                image.weather_image.setBackgroundResource(R.drawable.cloud);
            }
            else if(code_image.equals("03d")||code_image.equals("03n")) {
                image.weather_image.setBackgroundResource(R.drawable.cloudy);
            }
            else if(code_image.equals("04d")||code_image.equals("04n")) {
                image.weather_image.setBackgroundResource(R.drawable.darkcloud);
            }
            else if(code_image.equals("09d")||code_image.equals("09n")) {
                image.weather_image.setBackgroundResource(R.drawable.raining);
            }
            else if(code_image.equals("10d")||code_image.equals("10n")) {
                image.weather_image.setBackgroundResource(R.drawable.rain);
            }
            else if(code_image.equals("11d")||code_image.equals("11n")) {
                image.weather_image.setBackgroundResource(R.drawable.lightning);
            }
            else if(code_image.equals("13d")||code_image.equals("13n")) {
                image.weather_image.setBackgroundResource(R.drawable.snowfall);
            }
            else if(code_image.equals("50d")||code_image.equals("50n")) {
                image.weather_image.setBackgroundResource(R.drawable.mist);
            }
    }

    public void setTextView(TextView textView,String text)
    {
        textView.setText(text);
    }
}
