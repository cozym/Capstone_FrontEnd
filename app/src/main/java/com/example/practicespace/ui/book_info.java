package com.example.practicespace.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getBook;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class book_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TextView title, author, rental, description,owngroup, ownner, ISBN, category, publisher, publishDate;
    ImageView bookImage;
    String book_thumb;
    private Intent secondIntent;
    class Sync{
        protected void call(){  //책주인 누구인지 알아야함
            if(LoginInfo.getInstance().data.token != null){
                Call<getBook> book = apiInterface.getBookSeq(LoginInfo.getInstance().data.token,4);

                book.enqueue(new Callback<getBook>() {
                    @Override
                    public void onResponse(Call<getBook> call, Response<getBook> response) {
                        getBook result = response.body();

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
        book_thumb = secondIntent.getStringExtra("책사진");
        sendImageRequest();
        title = (TextView)findViewById(R.id.book_title);
        title.setText(secondIntent.getStringExtra("책이름"));
        author =(TextView) findViewById(R.id.book_author);
        author.setText(secondIntent.getStringExtra("글쓴이"));
        rental = (TextView) findViewById(R.id.book_rental);
        boolean isrental = secondIntent.getBooleanExtra("대여여부",true);
        if(isrental == true){
            rental.setText("대여중");
            rental.setBackgroundResource(R.drawable.rental_x);
        }else{
            rental.setText("대여가능");
            rental.setBackgroundResource(R.drawable.rental_o);
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

        Button rent_btn = findViewById(R.id.rent_button);
        //버튼이벤트
        if(true){ //책 주인이면
            if(isrental==true){
                rent_btn.setText("반납받기");
                rent_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                        dlg.setTitle("반납처리 하시겠습니까?");
                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(book_info.this,"반납이벤트",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                        dlg.setNegativeButton("아니오",null);
                        dlg.show();
                    }
                });
            } else{
                rent_btn.setText("대여하기");
                rent_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { //라디오다이얼로그처리해야함(단,본인제외)
                        AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                        dlg.setTitle("대여 하시겠습니까?");
                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(book_info.this,"반납이벤트",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                        dlg.setNegativeButton("아니오",null);
                        dlg.show();
                    }
                });
            }
        } else{
            if(true){//소속그룹인지 확인
                rent_btn.setVisibility(View.GONE);
            } else{
                rent_btn.setText("그룹 바로 가기");
                rent_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                        dlg.setTitle("바로 가시겠습니까?");
                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(getApplicationContext(), group_main.class);
//                                intent.putExtra("그룹시퀀스",groupseq);
//                                startActivity(intent);
                            }
                        });
                        dlg.setNegativeButton("아니오",null);
                        dlg.show();
                    }
                });
            }
        }

    }
    public void sendImageRequest() {
        String url = book_thumb;

        ListViewAdapter.ImageLoadTask task = new ListViewAdapter.ImageLoadTask(url,bookImage);
        task.execute();
    }


    public static class ImageLoadTask extends AsyncTask<Void,Void, Bitmap> {

        private String urlStr;
        private ImageView imageView;
        private HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

        public ImageLoadTask(String urlStr, ImageView imageView) {
            this.urlStr = urlStr;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                if (bitmapHash.containsKey(urlStr)) {
                    Bitmap oldbitmap = bitmapHash.remove(urlStr);
                    if (oldbitmap != null) {
                        oldbitmap.recycle();
                        oldbitmap = null;
                    }
                }
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                bitmapHash.put(urlStr, bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }
    }
}
