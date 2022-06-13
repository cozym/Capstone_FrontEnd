package com.example.practicespace.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getMyBookLogList;
import com.example.practicespace.connection.getUser;
import com.example.practicespace.connection.modNickname;
import com.example.practicespace.connection.myBookList;
import com.example.practicespace.vo.Book;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView nickname = (TextView)findViewById(R.id.nickname);
        final TextView email = (TextView)findViewById(R.id.email);
        TextView name = (TextView)findViewById(R.id.name);
        TextView signed_book = (TextView)findViewById(R.id.signed_book);
        TextView borrowed_book = (TextView)findViewById(R.id.borrowed_book);

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
            Call<myBookList> MY_BOOK = apiInterface.getMyBookList(
                    LoginInfo.getInstance().data.token,null,null);
            MY_BOOK.enqueue(new Callback<myBookList>() {
                @Override
                public void onResponse(Call<myBookList> call, Response<myBookList> response) {
                    myBookList mb = response.body();
                    if(response.code() == 200){
                        Log.d("연결 테스트","성공");
                        signed_book.setText(String.valueOf(mb.data.books.size()));
                    }
                }

                @Override
                public void onFailure(Call<myBookList> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("연결 테스트","실패");
                }
            });

            Call<getMyBookLogList> BORROW_BOOK = apiInterface.getMyBookLogList(LoginInfo.getInstance().data.token);
            BORROW_BOOK.enqueue(new Callback<getMyBookLogList>() {
                @Override
                public void onResponse(Call<getMyBookLogList> call, Response<getMyBookLogList> response) {
                    getMyBookLogList result = response.body();
                    if(response.code()==200){
                        Log.d("내도서로그"," 성공");
                        int count = 0;
                        for(int i=0; i<result.data.bookLogList.size();i++){
                            if(result.data.bookLogList.get(i).getBookLogStatus().equals("BORROW")){
                                count++;
                            }
                        }
                        Log.d("내도서로그"," 성공?? :"+response.code());
                        Log.d("내도서로그",String.valueOf(count));
                        borrowed_book.setText(String.valueOf(count));
                    }else{
                        Log.d("내도서로그"," 실패 :"+response.code());
                    }
                }

                @Override
                public void onFailure(Call<getMyBookLogList> call, Throwable t) {
                t.printStackTrace();
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

        Call<getMyBookLogList> call = apiInterface.getMyBookLogList(LoginInfo.getInstance().data.token);
        call.enqueue(new Callback<getMyBookLogList>() {
            @Override
            public void onResponse(Call<getMyBookLogList> call, Response<getMyBookLogList> response) {
                getMyBookLogList result = response.body();
                if(response.code()==200){
                    TableLayout tableLayout = (TableLayout) findViewById(R.id.mypage_log);
                    Log.d("로그 불러오기", "성공");
                    Log.d("로그 불러오기", String.valueOf(result.data.bookLogList.size()));
                    for(int i=0; i<result.data.bookLogList.size();i++){
                        TableRow tableRow = new TableRow(mypage.this);
                        tableRow.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        TextView book_name = new TextView(mypage.this);
                        book_name.setText(result.data.bookLogList.get(i).getBook().getTitle());
                        book_name.setBackgroundColor(Color.parseColor("#F1F1F1"));
                        book_name.setTextSize(18);
                        book_name.setWidth(changeDP(180));
                        book_name.setGravity(Gravity.CENTER);
                        book_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        book_name.setSelected(true);
                        book_name.setSingleLine(true);
                        book_name.setHeight(changeDP(30));
                        tableRow.addView(book_name);

                        TextView temp1 = new TextView(mypage.this);
                        temp1.setHeight(changeDP(1));
                        temp1.setWidth(changeDP(1));
                        tableRow.addView(temp1);

                        TextView createdTime = new TextView(mypage.this);
                        createdTime.setText(result.data.bookLogList.get(i).getCreatedTime().substring(0,10));
                        createdTime.setBackgroundColor(Color.parseColor("#ffffff"));
                        createdTime.setTextSize(18);
                        createdTime.setWidth(changeDP(24));
                        createdTime.setHeight(changeDP(30));
                        createdTime.setGravity(Gravity.CENTER);
                        tableRow.addView(createdTime);

                        TextView temp2 = new TextView(mypage.this);
                        temp2.setHeight(changeDP(1));
                        temp2.setWidth(changeDP(1));
                        tableRow.addView(temp2);


                        TextView lastModifiedTime = new TextView(mypage.this);
                        if(result.data.bookLogList.get(i).getBookLogStatus().equals("RETURN"))
                            lastModifiedTime.setText(result.data.bookLogList.get(i).getLastModifiedTime().substring(0,10));
                        lastModifiedTime.setBackgroundColor(Color.parseColor("#ffffff"));
                        lastModifiedTime.setTextSize(18);
                        lastModifiedTime.setWidth(changeDP(25));
                        lastModifiedTime.setHeight(changeDP(30));
                        lastModifiedTime.setGravity(Gravity.CENTER);
                        tableRow.addView(lastModifiedTime);
                        tableLayout.addView(tableRow);


                        TableRow tableRow2 = new TableRow(mypage.this);
                        tableRow2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        TextView space = new TextView(mypage.this);
                        space.setHeight(changeDP(1));
                        space.setWidth(changeDP(0));
                        tableRow2.addView(space);
                        tableLayout.addView(tableRow2);
                    }
                }else{
                    Log.d("로그 불러오기", "실패 : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<getMyBookLogList> call, Throwable t) {

            }
        });
    }

    private int changeDP(int i){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(i * displayMetrics.density);
        return dp;
    }
}
