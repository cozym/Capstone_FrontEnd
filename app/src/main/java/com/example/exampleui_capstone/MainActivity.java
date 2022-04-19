package com.example.exampleui_capstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayUseLogoEnabled(true);//
        getSupportActionBar().setDisplayShowHomeEnabled(true);//홈으로 돌아가는 버튼을 만든다
        //Appbar에 이렇게 뜬다. <-title

        listview = (ListView)findViewById(R.id.List_crew);

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        items.add(new ListViewItem(R.drawable.note,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source2,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1,"그룹 이름", "#캡스톤"));

        adapter = new ListViewAdapter(items, getApplicationContext());

        listview.setAdapter(adapter);
    }
}
