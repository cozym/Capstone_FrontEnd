package com.example.practicespace.ui;

import android.graphics.Color;
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

public class add_group extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

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

    }
}
