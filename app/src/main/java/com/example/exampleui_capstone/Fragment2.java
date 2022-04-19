package com.example.exampleui_capstone;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    ListView listview;
    private static ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);
        listview = (ListView) view.findViewById(R.id.List_crew_Book);

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        items.add(new ListViewItem(R.drawable.note, "android Studio", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source2, "OS", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "MongoDB", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "캡스톤", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "컴퓨터 구조", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "네트워크", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "머신러닝", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "파이썬", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "자바", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "코틀린", "#캡스톤"));

        adapter = new ListViewAdapter(items, view.getContext());

        listview.setAdapter(adapter);

        return view;
    }
}
