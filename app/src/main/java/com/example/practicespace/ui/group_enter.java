package com.example.practicespace.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.bookList;
import com.example.practicespace.connection.getGroup;
import com.example.practicespace.connection.openGroupList;
import com.example.practicespace.vo.Admin;
import com.example.practicespace.vo.Book;
import com.example.practicespace.vo.Group;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class group_enter extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    private TextView grouptitle, groupdes, group_isOpen, group_admin, group_mem_num, group_book_num, group_date;
    private int groupseq;
    private Group group;
    private Admin admin;
    private Intent secondIntent;
    List<Book> books = new ArrayList<Book>();

    class Sync {
        protected void init() {
            Log.d("테스트", "init");
            grouptitle = (TextView) findViewById(R.id.grouptitle);
            grouptitle.setText(secondIntent.getStringExtra("그룹이름"));
            group_isOpen = (TextView) findViewById(R.id.group_isOpen);
            boolean isopen = secondIntent.getBooleanExtra("그룹공개", true);
            if (isopen == true) {
                group_isOpen.setText("공개");
            } else {
                group_isOpen.setText("비공개");
            }
            groupdes = (TextView) findViewById(R.id.groupdes);
            groupdes.setText(secondIntent.getStringExtra("그룹설명"));
////        group_mem_num = (TextView)findViewById(R.id.group_mem_num);
////        group_mem_num.setText();
////        group_book_num = (TextView)findViewById(R.id.group_book_num);
////        group_book_num.setText(secondIntent.getIntExtra("그룹"));
            group_date = (TextView) findViewById(R.id.group_date);
            group_date.setText(secondIntent.getStringExtra("그룹생성일"));

//


        }

        protected void call() {
            Log.d("테스트", "call");
            //그룹정보 받아오기
            Call<getGroup> call = apiInterface.getGroupSeq(LoginInfo.getInstance().data.token, groupseq);
            Log.d("토큰 시퀀스", LoginInfo.getInstance().data.token + ", " + groupseq);
            call.enqueue(new Callback<getGroup>() {
                @Override
                public void onResponse(Call<getGroup> call, Response<getGroup> response) {
                    getGroup result = response.body();
                    if (response.code() == 200) {
                        group = result.data.group;
                        Log.d("test", "그룹받아오기" + group.getName());
                    } else {
                        Log.d("연결 테스트", "실패");
                    }
                    group_admin = (TextView) findViewById(R.id.group_admin);
                    group_admin.setText(group.getAdmin().getNickname());
                }

                @Override
                public void onFailure(Call<getGroup> call, Throwable t) {
                    call.cancel();
                }
            });

            //북리스트 가져오기
//            Call<bookList> call2 = apiInterface.getBookList(LoginInfo.getInstance().data.token,groupseq);
//            call2.enqueue(new Callback<bookList>() {
//                @Override
//                public void onResponse(Call<bookList> call2, Response<bookList> response) {
//                    bookList result = response.body();
//                    if(response.code() == 200){
//                        books = result.data.books;
//
//                    } else{
//                        Log.d("연결 테스트", "실패");
//                    }
//                }
//                @Override
//                public void onFailure(Call<bookList> call2, Throwable t) {
//                    call2.cancel();
//                }
//            });

        }

        public synchronized void syncRun(int num) {
            if (num == 1) {
                call();
            } else if (num == 2) {
                init();
            }
        }
    }

    class MyThread extends Thread {
        private Sync sync;
        int num;

        public MyThread(Sync sync, int num) {
            this.sync = sync;
            this.num = num;
        }

        public void run() {
            sync.syncRun(num);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_enter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        secondIntent = getIntent();
        groupseq = secondIntent.getIntExtra("그룹시퀀스", 0);


        //멀티스레드 처리
        Sync sync = new Sync();
        MyThread thread1 = new MyThread(sync, 1);
        MyThread thread2 = new MyThread(sync, 2);

        thread1.start();
        thread2.start();
        Log.d("스레드", "3");

        Button btn_enter = findViewById(R.id.enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), group_main.class);
                intent.putExtra("그룹시퀀스", groupseq);
                startActivity(intent);
            }
        });

    }


}
