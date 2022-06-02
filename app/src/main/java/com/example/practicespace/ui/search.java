package com.example.practicespace.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity{

    Fragment fragment0, fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //콤보박스1
        final String[] category = {"전체", "과학", "역사"};
        final String[] category2 = {"제목", "저자", "내용"};
        Spinner spiner = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter3 = new ArrayAdapter(this,R.layout.spinner_item, category2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);

        //카테고리 리스트추가 추후에 데이터베이스에서 받아서 for문으로 입력
        List<String> categories = new ArrayList<String>();
        categories.add("전체");
        categories.add("과학");
        categories.add("역사");

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //콤보박스2
        final String[] sort = {"이름순", "날짜순", "좋아요순"};
        Spinner spiner2 = (Spinner) findViewById(R.id.spinner_sort);
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_item, sort);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(adapter2);

        //카테고리 리스트추가 추후에 데이터베이스에서 받아서 for문으로 입력
        List<String> sorts = new ArrayList<String>();
        categories.add("이름순");
        categories.add("날짜순");
        categories.add("좋아요순");

        spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        fragment0 = new Fragment1();
        fragment1 = new Fragment2();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = fragment0;
                    spiner.setAdapter(adapter);
                    categories.clear();
                    categories.add("전체");
                    categories.add("과학");
                    categories.add("역사");

                }else if (position == 1){

                    selected = fragment1;
                    spiner.setAdapter(adapter3);
                    categories.clear();
                    categories.add("제목");
                    categories.add("저자");
                    categories.add("내용");

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }






}
