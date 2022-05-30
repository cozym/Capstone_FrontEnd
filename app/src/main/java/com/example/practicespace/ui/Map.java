package com.example.practicespace.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicespace.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        // 구글 맵 비동기 처리
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("Map", "지도 준비완료");
                map = googleMap;
                map.setMyLocationEnabled(true);
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button = findViewById(R.id.MyLoc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
    }

    // 내 위치 가져오기
    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

            }
            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            Toast.makeText(getApplicationContext(), "나의 위치 요청", Toast.LENGTH_SHORT).show();

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    //내 현재 위치 반환
    class GPSListener implements LocationListener {
        MarkerOptions myLocationMarker;

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            showCurrentLocation(latitude, longitude);
        }

        private void showCurrentLocation(Double latitude, Double longitude) {
            LatLng curPoint = new LatLng(latitude, longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

            showMyLocationMarker(curPoint);
        }
    
        //마커 설정
        private void showMyLocationMarker(LatLng curPoint) {
            if (myLocationMarker == null) {
                myLocationMarker = new MarkerOptions();
                myLocationMarker.position(curPoint);
                myLocationMarker.title("내 위치\n");
                myLocationMarker.snippet("GPS로 확인한 위치");
                //myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
                map.addMarker(myLocationMarker);
            } else {
                myLocationMarker.position(curPoint);
            }
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    public void onResume() {
        super.onResume();
        Log.d("@@@@@@@@", LoginInfo.getInstance().data.token);
        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    public void onPause() {
        super.onPause();

        if (map != null) {
            map.setMyLocationEnabled(false);
        }
    }
}