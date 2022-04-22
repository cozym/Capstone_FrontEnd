package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity {

    private SignInButton btn_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //처음 로그인 화면


        //로그인 버튼 클릭시 로그인 후 화면 전환 이벤트
        //추후에 웹뷰로 로그인 과정을 추가해야함
        btn_google = findViewById(R.id.btn_google); //버튼 지정
        btn_google.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), list_main_activity.class);
                startActivity(intent);
            }
        });

    }
}