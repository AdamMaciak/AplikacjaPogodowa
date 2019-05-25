package com.example.aplikacjapogodowa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikacjapogodowa.parser.Data_converter;

import java.util.ArrayList;


public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.ViewHolder> {
    private ArrayList<WeatherCard> ListWeather;
    private Interface_manager interface_manager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView weather_image;
        public TextView temperature;
        public TextView data;
        public TextView pressure;
        public TextView wind;

        public ViewHolder(View itemView) {
            super(itemView);
            weather_image = itemView.findViewById(R.id.weatherimage_cardview);
            temperature = itemView.findViewById(R.id.temperature_cardview);
            data = itemView.findViewById(R.id.data);
            pressure =itemView.findViewById(R.id.pressure_cardview);
            wind =itemView.findViewById(R.id.wind_cardview);
        }
    }

    public RecycleView_Adapter(ArrayList<WeatherCard> exampleList) {
        ListWeather = exampleList;
        interface_manager=new Interface_manager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherCard currentItem = ListWeather.get(position);
        //TODO
        //holder.weather_image.setImageResource(R.drawable.mist);
        interface_manager.setImageView(holder,currentItem.getCode_image());
        holder.temperature.setText(Data_converter.round_numbers(currentItem.getTemperature())+"\u2103");
        holder.data.setText(currentItem.getData());
        holder.pressure.setText(Data_converter.round_numbers(currentItem.getPressure())+"hPa");
        holder.wind.setText(currentItem.getWind());
    }

    @Override
    public int getItemCount() {
        return ListWeather.size();
    }
}
