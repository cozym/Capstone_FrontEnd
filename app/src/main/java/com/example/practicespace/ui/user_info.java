package com.example.practicespace.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.authorizeAdmin;
import com.example.practicespace.connection.blockUser;
import com.example.practicespace.connection.resignGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    private Intent secondIntent;
    TextView name, admin, rental_num, book_num;
    int userSeq, groupSeq;
    private Button admin_button,resign_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        admin_button = (Button)findViewById(R.id.admin_button);
        resign_button = (Button)findViewById(R.id.resign_button);

        secondIntent = getIntent();
        name = (TextView)findViewById(R.id.user_name);
        name.setText(secondIntent.getStringExtra("닉네임"));
        admin = (TextView)findViewById(R.id.user_admin);
        userSeq = secondIntent.getIntExtra("유저시퀀스",0);
        groupSeq = secondIntent.getIntExtra("그룹시퀀스",0);
        if(secondIntent.getBooleanExtra("유저등급", true)==true){
            admin.setText("관리자");
            admin.setBackgroundResource(R.drawable.public_o);

            admin_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"관리자 입니다.",Toast.LENGTH_LONG).show();
                }
            });
            resign_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"관리자 입니다.",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            admin.setText("일반회원");
            admin.setBackgroundResource(R.drawable.rental_o);
            admin_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"관리자로 임명되었습니다.",Toast.LENGTH_LONG).show();
                    Call<authorizeAdmin > call = apiInterface.groupAuthorize(
                            LoginInfo.getInstance().data.token,
                            groupSeq,userSeq
                    );
                    call.enqueue(new Callback<authorizeAdmin>() {
                        @Override
                        public void onResponse(Call<authorizeAdmin> call, Response<authorizeAdmin> response) {
                           authorizeAdmin result = response.body();
                           if(response.code() == 200){
                               Log.d("test","패치 성공");
                           }else{Log.d("test","패치 실패" + response.code());}
                        }

                        @Override
                        public void onFailure(Call<authorizeAdmin> call, Throwable t) {
                            Log.d("test","패치 실패");
                            t.printStackTrace();
                        }
                    });
                }
            });

            resign_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"강퇴 합니다.",Toast.LENGTH_LONG).show();
                    Call<blockUser> call2 = apiInterface.blockGroupUser(
                            LoginInfo.getInstance().data.token,
                            groupSeq,userSeq
                    );
                    call2.enqueue(new Callback<blockUser>() {
                        @Override
                        public void onResponse(Call<blockUser> call, Response<blockUser> response) {
                            blockUser result = response.body();
                            if(response.code() == 200){
                                Log.d("test","패치 성공");
                            }else{Log.d("test","패치 실패" + response.code());}
                        }

                        @Override
                        public void onFailure(Call<blockUser> call, Throwable t) {
                            Log.d("test","패치 실패");
                            t.printStackTrace();
                        }
                    });
                }
            });
        }
    }

}
