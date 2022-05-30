package com.example.practicespace.ui;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    GridView listview;
    private static GridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_book, container, false);
        listview = (GridView) view.findViewById(R.id.List_book_Info);

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        items.add(new ListViewItem(R.drawable.menu_book, "android Studio", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "OS", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "MongoDB", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "캡스톤", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "컴퓨터 구조", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "네트워크", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "머신러닝", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "파이썬", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "자바", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.menu_book, "코틀린", "#캡스톤"));

        adapter = new GridViewAdapter(items, view.getContext());

        listview.setAdapter(adapter);

        return view;
    }
}
