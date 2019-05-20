//package com.example.aplikacjapogodowa;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.os.Bundle;
//
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.util.concurrent.Executor;
//
//
//public class Geolocal_weather extends Fragment{
//
//    TextView text1;
//    private FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        text1=(TextView) getActivity().findViewById(R.id.pressure);
//        text1.setText("mordeczkomordunio");
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            text1.setText("brak perm");
//            return ;
//        }
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                text1.setText("jest perm");
//                if (location != null) {
//
//                    text1.setText("long:"+Double.toString(location.getLongitude()));
//
//                }
//                else
//                {
//                    text1.setText("null");
//                }
//            }
//        });
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.geolocal_weather, container, false);
//        return v;
//    }
//
//    public static Geolocal_weather newInstance() {
//
//        Geolocal_weather f = new Geolocal_weather();
//        return f;
//    }
//}