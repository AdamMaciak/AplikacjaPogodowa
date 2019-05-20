package com.example.aplikacjapogodowa;


import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private void requestpermision()
    {
        ActivityCompat.requestPermissions( this,new String[]{  android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
        ActivityCompat.requestPermissions( this,new String[]{  Manifest.permission.ACCESS_FINE_LOCATION},1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = findViewById(R.id.viewpager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        requestpermision();
    }
}
