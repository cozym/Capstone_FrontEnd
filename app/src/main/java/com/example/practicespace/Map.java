package com.example.practicespace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class Map extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;
    private UiSettings uiSettings;
    SlidingUpPanelLayout slidingUpPanelLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.main_frame);

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

                //ui 세팅 추가
                uiSettings = map.getUiSettings();
                uiSettings.setMapToolbarEnabled(false);

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {  //외부 클릭시 정보창 닫기
                        slidingUpPanelLayout.setTouchEnabled(true);
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED); 
                    }
                });
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
                map.setOnMarkerClickListener(markerClickListener);
            } else {
                myLocationMarker.position(curPoint);
            }
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }

    }

    //마커 클릭 이벤트
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            LatLng location = marker.getPosition();

            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            slidingUpPanelLayout.setTouchEnabled(false);
            //외부 영역 클릭 시, 닫기
            return false;
        }
    };

    //뒤로가기 누르면 레이아웃 집어넣기
    @Override
    public void onBackPressed()
    {
        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(R.id.main_frame);

        if(slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
        {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else
        {
            super.onBackPressed();
        }
    }



    public void onResume() {
        super.onResume();

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