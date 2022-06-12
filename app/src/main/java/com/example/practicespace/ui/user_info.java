package com.example.practicespace.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;

public class user_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    private Intent secondIntent;
    TextView name, admin, rental_num, book_num;
    int userSeq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        secondIntent = getIntent();
        name = (TextView)findViewById(R.id.user_name);
        name.setText(secondIntent.getStringExtra("닉네임"));
        admin = (TextView)findViewById(R.id.user_admin);
        if(secondIntent.getBooleanExtra("유저등급", true)==true){
            admin.setText("관리자");
            admin.setBackgroundResource(R.drawable.public_o);
        }else{
            admin.setText("일반회원");
            admin.setBackgroundResource(R.drawable.rental_o);
        }
        userSeq = secondIntent.getIntExtra("유저시퀀스",0);
    }
}
