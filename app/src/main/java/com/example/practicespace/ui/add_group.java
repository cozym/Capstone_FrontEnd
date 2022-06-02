package com.example.practicespace.ui;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.setGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class add_group extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private final int GET_GALLERY_IMAGE=200;
    private ImageView imageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Call<setGroup> call = apiInterface.saveGroup(
                LoginInfo.getInstance().data.token,
                "testgroup",
                true,
                "file",
                "my name is",
                127.035,
                37.299
        );
        call.enqueue(new Callback<setGroup>() {
            @Override
            public void onResponse(Call<setGroup> call, Response<setGroup> response) {
                setGroup result = response.body();
                if(response.code() == 200) {
                    Log.d("연결 테스트","성공");
                } else {
                    Log.d("연결 테스트","실패");
                }
            }

            @Override
            public void onFailure(Call<setGroup> call, Throwable t) {
                Log.d("연결 테스트","통신 실패");

            }
        });

        imageview = (ImageView)findViewById(R.id.group_thumbnail);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
        }
    }
}
