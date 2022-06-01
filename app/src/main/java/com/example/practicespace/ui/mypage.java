package com.example.practicespace.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getUser;
import com.example.practicespace.connection.modNickname;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mypage extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView nickname = (TextView)findViewById(R.id.nickname);
        final TextView email = (TextView)findViewById(R.id.email);
        TextView name = (TextView)findViewById(R.id.name);

        Button rewrite = findViewById(R.id.nick_rewrite);

        if(LoginInfo.getInstance().data.token != null){
            Call<getUser> user = apiInterface.getUserInfo(LoginInfo.getInstance().data.token);
            user.enqueue(new Callback<getUser>() {
                @Override
                public void onResponse(Call<getUser> call, Response<getUser> response) {
                    getUser result = response.body();
                    nickname.setText(result.data.nickname);
                    email.setText(result.data.emails.get(0));
                    name.setText(result.data.nickname);
                }

                @Override
                public void onFailure(Call<getUser> call, Throwable t) {

                }
            });
        }else
            Log.d("연결 테스트","실패");

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
                        if(LoginInfo.getInstance().data.token != null){
                            Call<modNickname> user = apiInterface.modifyNickname(LoginInfo.getInstance().data.token,re_ncik.getText().toString());
                            user.enqueue(new Callback<modNickname>() {
                                @Override
                                public void onResponse(Call<modNickname> call, Response<modNickname> response) {
                                    modNickname result = response.body();
                                }
                                @Override
                                public void onFailure(Call<modNickname> call, Throwable t) {

                                }
                            });
                        }else
                            Log.d("연결 테스트","실패");
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                dlg.show();
            }
        });

    }
}
