package com.example.practicespace.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;

public class group_enter extends AppCompatActivity {

    private TextView grouptitle;
    private int groupseq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_enter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent secondIntent = getIntent();
        grouptitle = (TextView)findViewById(R.id.grouptitle);
        grouptitle.setText(secondIntent.getStringExtra("그룹이름"));
        groupseq = secondIntent.getIntExtra("그룹시퀀스",0);




        Button btn_enter = findViewById(R.id.enter);
        btn_enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), group_main.class);
                intent.putExtra("그룹시퀀스",groupseq);

                startActivity(intent);
            }
        });

    }
}
