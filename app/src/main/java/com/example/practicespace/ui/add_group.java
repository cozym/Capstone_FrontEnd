package com.example.practicespace.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.SomeResponse;
import com.example.practicespace.connection.setGroup;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_group extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private final int GET_GALLERY_IMAGE=200;
    private ImageView imageview;
    private EditText group_Name;
    private EditText group_Location;
    private ImageButton address;
    private ImageButton myLocation;
    private EditText group_Description;
    private EditText group_HashTag;
    private boolean is_open = true;
    private Button submit_group;
    private String uri;
    List<Address> list = null;
    LatLng position = null;
    Intent intent;

    public String getPath(Uri uri){
        int column=0;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection,null,null,null);
        if(cursor.moveToFirst()){
            column=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Geocoder geocoder = new Geocoder(this);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        imageview = (ImageView) findViewById(R.id.group_thumbnail);
        group_Name = (EditText) findViewById(R.id.group_Name);
        group_Location = (EditText) findViewById(R.id.group_location);
        address = (ImageButton) findViewById(R.id.address);
        myLocation = (ImageButton) findViewById(R.id.setMyLocation);
        group_Description = (EditText) findViewById(R.id.group_Description);
//        group_HashTag = (EditText) findViewById(R.id.group_HashTag);
        submit_group = (Button) findViewById(R.id.submit_group);


        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

            }
        });

        //주소 입력을 통한 그룹 위도경도 설정
        address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final EditText re_ncik = new EditText(add_group.this);
                AlertDialog.Builder dlg = new AlertDialog.Builder(add_group.this);
                dlg.setTitle("주소입력");
                dlg.setView(re_ncik);
                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(), re_ncik.getText().toString(), Toast.LENGTH_SHORT).show();
                        try {
                            list = geocoder.getFromLocationName(re_ncik.getText().toString(),10);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (list != null) {
                            String city = "";
                            String country = "";
                            if (list.size() == 0) {
                                Toast.makeText(getApplicationContext(), "올바른 주소를 입력하세요", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Address address = list.get(0);
                                group_Location.setText(re_ncik.getText().toString());
                                position = new LatLng(address.getLatitude(),address.getLongitude());
                                double lat = address.getLatitude();
                                double lon = address.getLongitude();
                                System.out.println(lat);
                                System.out.println(lon);
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "올바른 주소를 입력하세요", Toast.LENGTH_SHORT).show();

                    }
                });
                dlg.show();
            }
        });


        // 내 위치를 통한 그룹 위도경도 설정
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng curPoint = new LatLng(latitude, longitude);
                        System.out.println(latitude);
                        System.out.println(longitude);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "내 위치 불러오기 실패", Toast.LENGTH_SHORT).show();

                    //GPSListener gpsListener = new GPSListener();
                    long minTime = 10000;
                    float minDistance = 0;

                    //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
                    //Toast.makeText(getApplicationContext(), "나의 위치 요청", Toast.LENGTH_SHORT).show();

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });


//        Log.d("image test",serveruri);
        submit_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serveruri = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEyMTBfMjQz%2FMDAxNjM5MDkzMTI0Mjk2.EEPStsR0cJekbLGH7jSqkvSU9E03cKJGnUezv-ZW6_cg.guvIYPL-NDL6vDJA5SdDkJ2mVExWM-GIrnt4xMK98Owg.PNG.designerjuni%2F%25BC%25BA%25B1%25D5%25B0%25FC%25B4%25EB%25C7%25D0%25B1%25B3%25B7%25CE%25B0%25ED.png&type=a340";//여기에다가 사진 주소(10메가이하)
                Call<setGroup> call = apiInterface.saveGroup(
                        LoginInfo.getInstance().data.token,
                        group_Name.getText().toString(),
                        is_open,
                        serveruri,
                        group_Description.getText().toString(),
                        position.longitude,
                        position.latitude
                );
                call.enqueue(new Callback<setGroup>() {
                    @Override
                    public void onResponse(Call<setGroup> call, Response<setGroup> response) {
                        setGroup result = response.body();
                        if (response.code() == 200) {
                            Log.d("test", "setgroup성공");
                        } else {
                            Log.d("test", "setgroupt실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<setGroup> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            imageview.setImageURI(selectedImageUri);

//            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//            File file = new File(getPath(selectedImageUri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), getPath(selectedImageUri));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","jpg",requestBody);
            Log.d("image test","말좀 들어라32211");
            Call<SomeResponse> call = apiInterface.saveImage(
                    LoginInfo.getInstance().data.token,
                    filePart
            );
            call.enqueue(new Callback<SomeResponse>() {
                @Override
                public void onResponse(Call<SomeResponse> call, Response<SomeResponse> response) {
                    SomeResponse result = response.body();
                    if(response.code() == 200){
                        uri = result.data.URL;
                        Log.d("image test",uri);
                    }else{
                        Log.d("image test","말좀 들어라33333");
                    }
                }

                @Override
                public void onFailure(Call<SomeResponse> call, Throwable t) {
                    Log.d("image test","말좀 들어라3344567");
                }
            });

            Log.d("image test","말좀 들어라1");
//            Call<okhttp3.Response> call = apiInterface.saveImage(LoginInfo.getInstance().data.token,requestBody);
//            call.enqueue(new Callback<okhttp3.Response>() {
//                @Override
//                public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response) {
//                    okhttp3.Response result = response.body();
//                    if (response.code() ==200 ){
//                        uri = result.body().toString();
//                    }else{
//                        Log.d("image test","말좀 들어라2");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<okhttp3.Response> call, Throwable t) {
//                    call.cancel();
//                }
//            });
//===================================================================================================================================
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);

//            RequestBody requestBody = new MultipartBody.Part.createFormData("file"
//            ,getPath(selectedImageUri),requestBody);

//            RequestBody requestBody = new MultipartBody.Builder().
//                    setType(MultipartBody.FORM)
//                    .addFormDataPart("file", "THUMBNAIL",
//                            RequestBody.create(MediaType.parse("image/*"), new File(getPath(selectedImageUri))))
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url("http://5gradekgucapstone.xyz:8080/")
//                    .post(requestBody)
//                    .build();
//
////            Call<okhttp3.Response> call = apiInterface.saveImage(LoginInfo.getInstance().data.token,requestBody);
//            Call call = client.newCall(request);
//            Response response = null;
//            try{
//                response = call.execute();
//            }


//
//            try {
//                uri = client.newCall(request).execute().body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.group_isOpen:
                is_open = true;
                break;
            case R.id.group_isClose:
                if (checked)
                    is_open = false;
                break;
        }
    }

//    public static Boolean uploadFile(/*String serverURL*/, File file,ImageView imageview) {
//        try {
//
//
//        }
//        catch (Exception ex) {
//            // Handle the error
//        }
//    }
}
