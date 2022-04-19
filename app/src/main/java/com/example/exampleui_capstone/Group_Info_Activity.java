package com.example.exampleui_capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Group_Info_Activity extends AppCompatActivity {


    private Button btn_crew;
    private Button btn_book;
    private Button btn_crew2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        btn_crew = (Button) findViewById(R.id.btn_crew);
        btn_book = (Button) findViewById(R.id.btn_book);

        btn_crew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_book.setBackgroundResource(R.drawable.btn_source1);
                btn_crew.setBackgroundResource(R.drawable.btn_source2);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //transaction은 화면을 변환할 때 사용하는 객체이다.
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.frame_group_Info, fragment1);
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
                transaction.replace(R.id.frame_group_Info, fragment2);
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}
