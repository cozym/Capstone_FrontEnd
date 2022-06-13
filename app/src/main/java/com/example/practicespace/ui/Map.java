package com.example.practicespace.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.SearchGroup;
import com.example.practicespace.vo.Group;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    SupportMapFragment mapFragment;
    GoogleMap map;
    private UiSettings uiSettings;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LatLng myPosition = null;
    List<Group> groups = null;
    List<Marker> markers = new ArrayList<Marker>();
    private TextView searchedGroupName;
    private TextView searchedGroupDescription;
    private Button toGroupInfo;
    int groupPointer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchedGroupName = (TextView) findViewById(R.id.searchedGroupName);
        searchedGroupDescription = (TextView) findViewById(R.id.searchedGroupDescription);
        toGroupInfo = (Button) findViewById(R.id.to_groupinfo);

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.main_frame);
        EditText eText2 = (EditText) findViewById(R.id.MapSearch);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        eText2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    groups = null;
                    groups = new ArrayList<Group>();
                    String keyword = eText2.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(eText2.getWindowToken(), 0);
                    Log.d("검색키워드",eText2.getText().toString());
                    for(int m = 0; m < markers.size(); m++) {
                        markers.get(m).remove();
                    }
                    markers.clear();
                    if(!keyword.equals(""))
                        getGroupWithBook(keyword);
                    startLocationService();
                    return true;
                }
                return false;
            }
        });


        // 구글 맵 비동기 처리
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
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
                try {
                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        myPosition = new LatLng(latitude, longitude);
                        Log.d("2222","2222");
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                    }

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                //startLocationService();
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //그룹 바로가기
        toGroupInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), group_enter.class);
                intent.putExtra("그룹시퀀스",groups.get(groupPointer).getSeq());
                intent.putExtra("그룹사진",groups.get(groupPointer).getThumbnail());
                intent.putExtra("그룹이름",groups.get(groupPointer).getName());
                intent.putExtra("그룹공개",groups.get(groupPointer).getOpen());
                intent.putExtra("그룹설명",groups.get(groupPointer).getDescription());
                //intent.putExtra("회원수",groups.get(i).);
                intent.putExtra("그룹생성일",groups.get(groupPointer).getCreatedDate().substring(0,10));
                startActivity(intent);
            }
        });

    }

    // 검색한 책을 보유한 그룹들 가져오기
    public void getGroupWithBook(String keyword) {
        //int distance = 500;
        Log.d("3333","3333");
        Log.d("위도경도",String.valueOf(myPosition.longitude)+String.valueOf(myPosition.latitude)+keyword);
        // 위치기반 책 검색 api
        Call<SearchGroup> call = apiInterface.searchGroupByKeywordAndLocation(
                LoginInfo.getInstance().data.token,
                keyword,
                myPosition.longitude,
                myPosition.latitude
        );
        Log.d("4444","4444");
        call.enqueue(new Callback<SearchGroup>() {
            @Override
            public void onResponse(Call<SearchGroup> call, Response<SearchGroup> response) {
                SearchGroup result = response.body();
                System.out.println(result);
                Log.d("리스폰스코드", String.valueOf(response.code()));

                if(response.code()==200){
                    groups = result.data.groups;
                    Log.d("연결 테스트", "코드까지는 성공");
                    Log.d("5555", String.valueOf(groups.size()));

                }
                else{
                    Log.d("연결 테스트", "실패");
                }
            }

            @Override
            public void onFailure(Call<SearchGroup> call, Throwable t) {t.printStackTrace();
                /*Log.d("연결 테스트", "실패22")*/;
            }
        });
    }

    // 내 위치 가져오기
    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            //Toast.makeText(getApplicationContext(), "나의 위치 요청", Toast.LENGTH_SHORT).show();

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
            List<Marker> markers = null;
            // 검색한 그룹들 마커로 표시
            if(groups.size() != 0) {
                for (int i = 0; i < groups.size(); i++) {
                    Log.d("sssss", groups.get(i).getName());
                    showMyLocationMarker(new LatLng(groups.get(i).getLatitude(), groups.get(i).getLongitude()));
                }
            }
//            showMyLocationMarker(new LatLng(37.2970, 127.0312));
//            showMyLocationMarker(new LatLng(37.3012, 127.0388));
//            showMyLocationMarker(new LatLng(37.3033, 127.0346));
        }

        //마커 설정
        private void showMyLocationMarker(LatLng curPoint) {
            //if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);
            myLocationMarker.title("Seleted");
            Marker marker = map.addMarker(myLocationMarker);
            markers.add(marker);
            map.setOnMarkerClickListener(markerClickListener);
            //} else {
            //    myLocationMarker.position(curPoint);
            //}
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    //마커 클릭 이벤트
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            List<Marker> markers = null;
            LatLng location = marker.getPosition();
            //int i = Character.getNumericValue(markerId.charAt(1));
            Log.d("마커 위치", String.valueOf(marker.getPosition()));

            for(int j = 0; j < groups.size(); j++) {
                if(groups.get(j).getLatitude()==location.latitude && groups.get(j).getLongitude()==location.longitude) {
                    searchedGroupName.setText(groups.get(j).getName());
                    searchedGroupDescription.setText(groups.get(j).getDescription());
                    groupPointer = j;
                }
            }

            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            slidingUpPanelLayout.setTouchEnabled(false);
            //외부 영역 클릭 시, 닫기
            return false;
        }
    };

    //뒤로가기 누르면 레이아웃 집어넣기
    @Override
    public void onBackPressed() {
        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(R.id.main_frame);

        if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
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