package com.example.aplikacjapogodowa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return Find_weather.newInstance();
            default: return Forecast_weather.newInstance();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}