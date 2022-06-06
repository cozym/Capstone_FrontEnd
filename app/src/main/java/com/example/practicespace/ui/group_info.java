package com.example.practicespace.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.practicespace.R;
import com.example.practicespace.connection.modNickname;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class group_info extends AppCompatActivity {
    private Button button_join_group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        button_join_group = findViewById(R.id.button_join_group);
//
//        button_join_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final EditText authentic_code = new EditText(group_info.this);
//                AlertDialog.Builder dlg = new AlertDialog.Builder(group_info.this);
//                dlg.setTitle("인증 코드");
//                dlg.setView(authentic_code);
//                dlg.setPositiveButton("입력", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(), authentic_code.getText().toString(), Toast.LENGTH_SHORT).show();
//                        //추후 데이터베이스 연동시 변경
//
//                        Intent intent = getIntent();
//                        finish();
//                        startActivity(intent);
//                    }
//                });
//
//            }
//        });

    }
}
