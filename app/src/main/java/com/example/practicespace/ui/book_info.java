package com.example.practicespace.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getBook;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class book_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TextView title, author, rental, description,owngroup, ownner, ISBN, category, publisher, publishDate;
    ImageView bookImage;
    private Intent secondIntent;
    class Sync{
        protected void call(){  //seq 변수로 수정해야함
            if(LoginInfo.getInstance().data.token != null){
                Call<getBook> book = apiInterface.getBookSeq(LoginInfo.getInstance().data.token,4);

                book.enqueue(new Callback<getBook>() {
                    @Override
                    public void onResponse(Call<getBook> call, Response<getBook> response) {
                        getBook result = response.body();
                        final TextView title = (TextView)findViewById(R.id.book_title);
                        title.setText(result.data.book.getTitle());
                    }

                    @Override
                    public void onFailure(Call<getBook> call, Throwable t) {

                    }
                });
            }else
                Log.d("연결 테스트","실패");
        }
        public void init(){

        }

        public synchronized void syncRun(int num){
            if(num==1){
                call();
            }else if(num==2){
                init();
            }
        }
    }

    class MyThread extends Thread{
        private Sync sync;
        int num;
        public MyThread(Sync sync, int num){
            this.sync = sync;
            this.num = num;
        }
        public void run(){
            sync.syncRun(num);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        Sync sync = new Sync();
//        MyThread thread1 = new MyThread(sync,1);
//        MyThread thread2 = new MyThread(sync,2);
//
//        thread1.start();
//        thread2.start();
        secondIntent = getIntent();
        bookImage=(ImageView)findViewById(R.id.book_image);
        bookImage.setImageResource(secondIntent.getIntExtra("책사진", 0));
        title = (TextView)findViewById(R.id.book_title);
        title.setText(secondIntent.getStringExtra("책이름"));
        author =(TextView) findViewById(R.id.book_author);
        author.setText(secondIntent.getStringExtra("글쓴이"));
        rental = (TextView) findViewById(R.id.book_rental);
        boolean isrental = secondIntent.getBooleanExtra("대여여부",true);
        if(isrental == true){
            rental.setText("대여가능");
            rental.setBackgroundResource(R.drawable.rental_o);
        }else{
            rental.setText("대여불가");
            rental.setBackgroundResource(R.drawable.rental_x);
        }
        description =(TextView) findViewById(R.id.book_des);
        description.setText(secondIntent.getStringExtra("책설명"));
        owngroup =(TextView) findViewById(R.id.book_owngroup);
        owngroup.setText(secondIntent.getStringExtra("소유그룹"));
        //주인 임시
        ownner =(TextView) findViewById(R.id.ownner);
        ownner.setText(secondIntent.getStringExtra("글쓴이"));
        //
        ISBN =(TextView) findViewById(R.id.book_ISBN);
        ISBN.setText(secondIntent.getStringExtra("ISBN"));
        category =(TextView) findViewById(R.id.book_category);
        category.setText(secondIntent.getStringExtra("카테고리"));
        publisher =(TextView) findViewById(R.id.book_publisher);
        publisher.setText(secondIntent.getStringExtra("출판사"));
        publishDate =(TextView) findViewById(R.id.book_date);
        publishDate.setText(secondIntent.getStringExtra("등록일"));
    }
}
