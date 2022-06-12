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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.CheckUserIsSigned;
import com.example.practicespace.connection.borrowBook;
import com.example.practicespace.connection.deleteBook;
import com.example.practicespace.connection.deleteGroup;
import com.example.practicespace.connection.getBook;
import com.example.practicespace.connection.getUserList;
import com.example.practicespace.connection.setBookLog;
import com.example.practicespace.vo.User;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class book_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TextView title, author, rental, description,owngroup, ownner, ISBN, category, publisher, publishDate;
    ImageView bookImage;
    String book_thumb;
    String TokenPart = LoginInfo.getInstance().data.token.split("\\.")[1];
    String user_seq = new String(android.util.Base64.decode(TokenPart, 0)).split("\"")[7];
    List<User> Users = new ArrayList<User>();
    String[] userlist = new String[] {};
    int which;
    int book_seq;
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
        book_seq = secondIntent.getIntExtra("책시퀀스",0);
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
        Button delete_btn = findViewById(R.id.delete_button);
        //버튼이벤트


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                dlg.setTitle("삭제하시겠습니까?");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<deleteBook> call = apiInterface.deleteBookSeq( LoginInfo.getInstance().data.token,book_seq);
                        Log.d("시퀀스", String.valueOf(book_seq));
                        call.enqueue(new Callback<deleteBook>() {
                            @Override
                            public void onResponse(Call<deleteBook> call, Response<deleteBook> response) {
                                deleteBook result = response.body();
                                if(response.code()==200) {
                                    Log.d("test","setgroup성공");
                                }
                                else {
                                    Log.d("test","setgroupt실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<deleteBook> call, Throwable t) {
                                call.cancel();
                            }
                        });
                        Toast.makeText(book_info.this,"삭제됐습니다.",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                dlg.setNegativeButton("아니오",null);
                dlg.show();
            }
        });

        Call<getBook> call = apiInterface.getBookSeq(LoginInfo.getInstance().data.token, book_seq);
        call.enqueue(new Callback<getBook>() {
            @Override
            public void onResponse(Call<getBook> call, Response<getBook> response) {
                getBook result = response.body();
                if (response.code() == 200) {
                    Log.d("책정보 가져오기", "성공");
                    ownner.setText(result.data.book.getUser().getNickname());
                    owngroup.setText(result.data.book.getGroup().getName());

                    if(result.data.book.getUser().getUserSeq()==Integer.valueOf(user_seq)){ //책 주인이면
                        if(isrental==true){   // 테스트할때 true로 바꾸면 대여중으로 뜸 -> 반납하기 영상
                            rental.setText("대여중");
                            rental.setBackgroundResource(R.drawable.rental_x);
                            rent_btn.setText("반납받기");
                            ViewGroup.LayoutParams params = rent_btn.getLayoutParams();
                            params.width = 800;
                            rent_btn.setLayoutParams(params);
                            rent_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                                    dlg.setTitle("반납처리 하시겠습니까?");
                                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            rental.setText("대여가능");
                                            rental.setBackgroundResource(R.drawable.rental_o);
                                            Toast.makeText(book_info.this,"반납이벤트",Toast.LENGTH_SHORT).show();
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                    dlg.setNegativeButton("아니오",null);
                                    dlg.show();
                                }
                            });
                            delete_btn.setVisibility(View.GONE);

                        } else{
                            rent_btn.setText("대여하기");
                            rent_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) { //라디오다이얼로그처리해야함(단,본인제외)
                                    Call<getUserList> call = apiInterface.getUserList(LoginInfo.getInstance().data.token,result.data.book.getGroup().getSeq());
                                    call.enqueue(new Callback<getUserList>() {
                                        @Override
                                        public void onResponse(Call<getUserList> call, Response<getUserList> response) {
                                            getUserList result2 = response.body();
                                            Log.d("책 테스트", String.valueOf(response.body()));
                                            if(response.code() == 200){
                                                Users = result2.data.userList;
                                                List<String> names = new ArrayList<String>();
                                                for(int i=0; i<Users.size();i++){
                                                    //책주인은 안뜨게
                                                    names.add(Users.get(i).getNickname());
                                                }
                                                String[] radiolist = names.toArray(new String[0]);
                                                AlertDialog.Builder dlg = new AlertDialog.Builder(book_info.this);
                                                dlg.setSingleChoiceItems(radiolist, -1, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        which=i;
                                                    }
                                                });
                                                dlg.setTitle("누구한테 대여 하시겠습니까?");
                                                dlg.setPositiveButton("대여하기", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Toast.makeText(book_info.this,Users.get(which).getNickname(),Toast.LENGTH_SHORT).show();
                                                        Call<borrowBook> call = apiInterface.borrow(LoginInfo.getInstance().data.token, Users.get(i).getUserSeq(),result.data.book.getSeq());
                                                        call.enqueue(new Callback<borrowBook>() {
                                                            @Override
                                                            public void onResponse(Call<borrowBook> call, Response<borrowBook> response) {
                                                                borrowBook result3 = response.body();
                                                                if(response.code()==200){
                                                                    Log.d("버로우이벤트","성공");
                                                                    Call<setBookLog> call2 = apiInterface.addBookLog(LoginInfo.getInstance().data.token,"BORROW",result.data.book.getSeq(),Users.get(which).getUserSeq());
                                                                    call2.enqueue(new Callback<setBookLog>() {
                                                                        @Override
                                                                        public void onResponse(Call<setBookLog> call, Response<setBookLog> response) {
                                                                            setBookLog result4 = response.body();
                                                                            if(response.code()==200){
                                                                                Log.d("로그이벤트","성공");
                                                                            }else{
                                                                                Log.d("로그이벤트","실패");
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<setBookLog> call, Throwable t) {

                                                                        }
                                                                    });
                                                                }else{
                                                                    Log.d("버로우 이벤트", "실패");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<borrowBook> call, Throwable t) {

                                                            }
                                                        });
                                                    }
                                                });
                                                dlg.setNegativeButton("취소",null);
                                                dlg.show();
                                            } else{
                                                Log.d("연결 테스트", "실패");
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<getUserList> call, Throwable t) {
                                            call.cancel();
                                        }
                                    });
                                }
                            });
                        }
                    } else{
                        delete_btn.setVisibility(View.GONE);

                        Call<CheckUserIsSigned> call0 = apiInterface.signedGroup(LoginInfo.getInstance().data.token,result.data.book.getGroup().getSeq());
                        call0.enqueue(new Callback<CheckUserIsSigned>() {
                            @Override
                            public void onResponse(Call<CheckUserIsSigned> call, Response<CheckUserIsSigned> response) {
                                CheckUserIsSigned result0 = response.body();
                                if(response.code() == 200){
                                    boolean UserInGroup = result0.data.result;
                                    if(UserInGroup){//소속그룹인지 확인
                                        ViewGroup.LayoutParams params = rent_btn.getLayoutParams();
                                        params.width = 800;
                                        rent_btn.setLayoutParams(params);
                                        rent_btn.setText("소속 그룹입니다.");
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
                                                        Intent intent = new Intent(getApplicationContext(), group_enter.class);
                                                        intent.putExtra("그룹시퀀스",result.data.book.getGroup().getSeq());
                                                        intent.putExtra("그룹사진",result.data.book.getGroup().getThumbnail());
                                                        intent.putExtra("그룹이름",result.data.book.getGroup().getName());
                                                        intent.putExtra("그룹설명",result.data.book.getGroup().getDescription());
                                                        intent.putExtra("그룹공개",result.data.book.getGroup().getOpen());
                                                        intent.putExtra("그룹생성일" ,result.data.book.getGroup().getCreatedDate());
                                                        startActivity(intent);
                                                    }
                                                });
                                                dlg.setNegativeButton("아니오",null);
                                                dlg.show();
                                            }
                                        });
                                    }

                                }else{Log.d("연결 테스트", "실패");}
                            }

                            @Override
                            public void onFailure(Call<CheckUserIsSigned> call, Throwable t) {
                                call.cancel();
                            }
                        });

                    }

                } else {
                    Log.d("책정보", "실패");
                }
            }

            @Override
            public void onFailure(Call<getBook> call, Throwable t) {

            }
        });



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
