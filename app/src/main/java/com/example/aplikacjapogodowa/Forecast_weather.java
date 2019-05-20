package com.example.aplikacjapogodowa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Forecast_weather extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.forecast_weather, container, false);
        return v;
    }

    public static Forecast_weather newInstance() {
        Forecast_weather f =new Forecast_weather();
        return f;
    }
}
