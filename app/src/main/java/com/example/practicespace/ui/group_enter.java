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
import android.view.ViewGroup;
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
import com.example.practicespace.connection.bookList;
import com.example.practicespace.connection.deleteBook;
import com.example.practicespace.connection.deleteGroup;
import com.example.practicespace.connection.getGroup;
import com.example.practicespace.vo.Admin;
import com.example.practicespace.vo.Book;
import com.example.practicespace.vo.Group;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class group_enter extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private TextView grouptitle;
    private TextView groupdes;
    private TextView group_isOpen;
    private TextView group_admin;
    private TextView group_mem_num;
    private TextView group_book_num;
    private TextView group_date;
    private String group_thumb;
    private int groupseq;
    private Group group;
    private Admin admin;
    private Intent secondIntent;
    private ImageView groupimage;
    List<Book> books = new ArrayList<Book>();
    private int booknum;
    String TokenPart = LoginInfo.getInstance().data.token.split("\\.")[1];
    String user_seq = new String(android.util.Base64.decode(TokenPart, 0)).split("\"")[7];
    Button btn_enter;


    public void sendImageRequest() {
        String url = group_thumb;

        ListViewAdapter.ImageLoadTask task = new ListViewAdapter.ImageLoadTask(url,groupimage);
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
                    if(oldbitmap != null) {
                        oldbitmap.recycle();
                        oldbitmap = null;
                    }
                }
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                bitmapHash.put(urlStr,bitmap);

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



    class Sync{
        protected void call(){
            Log.d("토큰조각",new String(android.util.Base64.decode(TokenPart, 0)));
            Log.d("토큰조각유저시퀀스",user_seq);
            Log.d("테스트","call");
            //그룹정보 받아오기
            Call<getGroup> call = apiInterface.getGroupSeq(LoginInfo.getInstance().data.token,groupseq);
                    Log.d("토큰 시퀀스",LoginInfo.getInstance().data.token+groupseq);
                    call.enqueue(new Callback<getGroup>() {
                        @Override
                        public void onResponse(Call<getGroup> call, Response<getGroup> response) {
                            getGroup result = response.body();
                            if(response.code() == 200){
                                group = result.data.group;
                                Log.d("test","그룹받아오기"+group.getAdmin().getSeq());
                                System.out.println(group.getAdmin().getNickname());
                            } else{
                                Log.d("연결 테스트", "실패");
                            }
                            group_admin = (TextView)findViewById(R.id.group_admin);
                            group_admin.setText(group.getAdmin().getNickname());
                            Button button2 = findViewById(R.id.group_button2);
                            if(false){ //소속그룹인지 확인부터
                                btn_enter.setText("가입하기");
                               button2.setVisibility(View.GONE);
                                ViewGroup.LayoutParams params = btn_enter.getLayoutParams();
                                params.width = 800;
                               btn_enter.setLayoutParams(params);
                                btn_enter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder dlg = new AlertDialog.Builder(group_enter.this);
                                        dlg.setTitle("가입하시겠습니까?").setMessage(group.getAuthenticationCode());
                                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(group_enter.this,"탈퇴이벤트",Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        });
                                        dlg.setNegativeButton("아니오",null);
                                        dlg.show();
                                    }
                                });
                            } else if(Integer.valueOf(user_seq) == group.getAdmin().getSeq()){ //소속 그룹이면 어드민인지 확인 테스트중
                                button2.setText("코드 보기");
                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
//                                        Call<deleteGroup> call = apiInterface.deleteGroupSeq( LoginInfo.getInstance().data.token,groupseq);
//                                        call.enqueue(new Callback<deleteGroup>() {
//                                            @Override
//                                            public void onResponse(Call<deleteGroup> call, Response<deleteGroup> response) {
//                                                deleteGroup result = response.body();
//                                                if(response.code()==200) {
//                                                    Log.d("test","setgroup성공");
//                                                }
//                                                else {
//                                                    Log.d("test","setgroupt실패");
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call<deleteGroup> call, Throwable t) {
//                                                call.cancel();
//                                            }
//                                        });
//                                        Toast.makeText(group_enter.this,"삭제됐습니다.",Toast.LENGTH_SHORT).show();
//                                        onBackPressed();
                                        AlertDialog.Builder dlg = new AlertDialog.Builder(group_enter.this);
                                        dlg.setTitle("인증코드").setMessage("a3fg5s");
                                        dlg.setPositiveButton("닫기", null);
                                        dlg.show();
                                    }
                                });
                            } else{
                                //탈퇴하기
                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder dlg = new AlertDialog.Builder(group_enter.this);
                                        dlg.setTitle("정말 탈퇴하시겠습니까").setMessage(group.getAuthenticationCode());
                                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(group_enter.this,"탈퇴이벤트",Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        });
                                        dlg.setNegativeButton("아니오",null);
                                        dlg.show();
                                    }
                                });
                            }

                        }
                        @Override
                        public void onFailure(Call<getGroup> call, Throwable t) {
                            call.cancel();
                }
            });

            Call<bookList> call2 = apiInterface.getBookList(
                    LoginInfo.getInstance().data.token,groupseq, 0
            );
            call2.enqueue(new Callback<bookList>() {
                @Override
                public void onResponse(Call<bookList> call, Response<bookList> response) {
                    bookList result = response.body();
                    if(response.code()==200){
                        Log.d("연결 테스트", "코드까지는 성공");
                        books = result.data.books;
                        booknum = books.size();
                        group_book_num = (TextView)findViewById(R.id.group_book_num);
                        group_book_num.setText(String.valueOf(booknum));
                        //유저수추가


                    }
                    else{
                        Log.d("연결 테스트", "실패");
                    }
                }

                @Override
                public void onFailure(Call<bookList> call, Throwable t) {
                    Log.d("연결 테스트", "실패22");
                }
            });

        }
        protected void init(){
            Log.d("테스트","init");
            groupimage=(ImageView) findViewById(R.id.group_image);
            sendImageRequest();
            grouptitle = (TextView)findViewById(R.id.grouptitle);
            grouptitle.setText(secondIntent.getStringExtra("그룹이름"));
            group_isOpen = (TextView) findViewById(R.id.group_isOpen);
            boolean isopen = secondIntent.getBooleanExtra("그룹공개",true);
            if(isopen == true){
                group_isOpen.setText("공개");
                group_isOpen.setBackgroundResource(R.drawable.public_o);
            }else{
                group_isOpen.setText("비공개");
                group_isOpen.setBackgroundResource(R.drawable.public_x);
            }
            groupdes = (TextView)findViewById(R.id.groupdes);
            groupdes.setText(secondIntent.getStringExtra("그룹설명"));

            group_mem_num = (TextView)findViewById(R.id.group_mem_num);
            group_mem_num.setText(String.valueOf(secondIntent.getIntExtra("회원수",2)));
            group_date = (TextView) findViewById(R.id.group_date);
            group_date.setText(secondIntent.getStringExtra("그룹생성일"));

//

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
        setContentView(R.layout.group_enter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        secondIntent = getIntent();

        groupseq = secondIntent.getIntExtra("그룹시퀀스",0);
        group_thumb = secondIntent.getStringExtra("그룹사진");

        //멀티스레드 처리
        Sync sync = new Sync();

        MyThread thread1 = new MyThread(sync,1);
        MyThread thread2 = new MyThread(sync,2);

        thread1.start();
        thread2.start();

        btn_enter = findViewById(R.id.enter);
        btn_enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){

                Intent intent = new Intent(getApplicationContext(), group_main.class);
                intent.putExtra("그룹시퀀스",groupseq);
                intent.putExtra("그룹이름",secondIntent.getStringExtra("그룹이름"));
                startActivity(intent);
            }
        });

    }


}
