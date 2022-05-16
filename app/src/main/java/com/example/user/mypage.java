package com.example.user;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class mypage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button rewrite = findViewById(R.id.nick_rewrite);
        rewrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final EditText re_ncik = new EditText(mypage.this);
                AlertDialog.Builder dlg = new AlertDialog.Builder(mypage.this);
                dlg.setTitle("새 닉네임");
                dlg.setView(re_ncik);
                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), re_ncik.getText().toString(), Toast.LENGTH_SHORT).show();
                        //추후 데이터베이스 연동시 변경
                    }
                });
                dlg.show();
            }
        });

    }
}
