package com.example.exampleui_capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_crew;
    private Button btn_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayUseLogoEnabled(true);//
        getSupportActionBar().setDisplayShowHomeEnabled(true);//홈으로 돌아가는 버튼을 만든다
        //Appbar에 이렇게 뜬다. <-title

        btn_crew = (Button)findViewById(R.id.btn_crew);
        btn_book = (Button)findViewById(R.id.btn_book);

        btn_crew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_book.setBackgroundResource(R.drawable.btn_source1);
                btn_crew.setBackgroundResource(R.drawable.btn_source2);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //transaction은 화면을 변환할 때 사용하는 객체이다.
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.frame,fragment1);
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_crew.setBackgroundResource(R.drawable.btn_source1);
                btn_book.setBackgroundResource(R.drawable.btn_source2);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //transaction은 화면을 변환할 때 사용하는 객체이다.
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.frame,fragment2);
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //MenuItem item를 인자로 받은 item 즉, 내가 클릭한 버튼의 id를 받아오는 것이다.

        if(id == R.id.pencil){
            Toast.makeText(getApplicationContext(),"작성 아이콘",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}