package com.daniel.JoyRent.Map;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.daniel.JoyRent.R;


public class MapMain extends AppCompatActivity {

    private MapView mMapView = null;
    public LocationClient locationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient=new LocationClient(getApplicationContext());
       // locationClient.registerLocationListener(new my);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //mMapView.onResume();
    }
}
