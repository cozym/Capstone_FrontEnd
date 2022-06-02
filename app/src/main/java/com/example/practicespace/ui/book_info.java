package com.example.practicespace.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getBook;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class book_info extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


    class Sync{
        protected void call(){

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

        if(LoginInfo.getInstance().data.token != null){
            Call<getBook> book = apiInterface.getBookSeq(LoginInfo.getInstance().data.token,4);
            book.enqueue(new Callback<getBook>() {
                @Override
                public void onResponse(Call<getBook> call, Response<getBook> response) {
                    getBook result = response.body();
                    final TextView title = (TextView)findViewById(R.id.booktitle);
                    title.setText(result.data.book.getTitle());
                }

                @Override
                public void onFailure(Call<getBook> call, Throwable t) {

                }
            });
        }else
            Log.d("연결 테스트","실패");
        TextView title = (TextView)findViewById(R.id.booktitle);

    }
}
