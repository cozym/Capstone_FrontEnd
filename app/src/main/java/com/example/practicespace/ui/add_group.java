package com.example.practicespace.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.SomeResponse;
import com.example.practicespace.connection.setGroup;

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
    private EditText group_Description;
    private EditText group_HashTag;
    private boolean is_open;
    private Button submit_group;
    private String uri;
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


        imageview = (ImageView) findViewById(R.id.group_thumbnail);
        group_Name = (EditText) findViewById(R.id.group_Name);
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


//        Log.d("image test",serveruri);
        submit_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serveruri = "https://www.ajou.ac.kr/_res/ajou/kr/img/intro/img-symbol.png";
                Call<setGroup> call = apiInterface.saveGroup(
                        LoginInfo.getInstance().data.token,
                        group_Name.getText().toString(),
                        is_open,
                        serveruri,
                        group_Description.getText().toString(),
                        127.0312,
                        37.2970
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
