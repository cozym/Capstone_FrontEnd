package com.example.practicespace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicespace.ui.main;

public class LocationActivity extends AppCompatActivity {
    TextView text_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        text_location = findViewById(R.id.MyLocation);

        Button check_location = findViewById(R.id.LocationRequest);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        check_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationService();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), main.class);
        startActivity(intent);
        Log.d("uuuuuuuuuuu", "111111111111");
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            text_location.setText("test3");
            if(location != null) {
                text_location.setText("test4");
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "<최근 위치> \n위도 : " + latitude + "\n경도 : " + longitude;

                text_location.setText(message);
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
    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            String message = "<내 위치>\n 위도 : " + latitude + "\n경도 : " + longitude;
            text_location.setText(message);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }
}

