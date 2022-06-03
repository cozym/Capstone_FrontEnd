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

    GridView gridView;
    private static GridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_book, container, false);
        gridView = (GridView) view.findViewById(R.id.List_book_Info);

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<GridViewItem> items = new ArrayList<GridViewItem>();

          items.add(new GridViewItem(R.drawable.test_jsp, 4,"쉽게 배우는 JSP 웹 프로그래밍", "송미영",
                  "한빛아카데미", "979-11-5664-338-8","2022-04-08","JSP입문자를 위한 도서",
                  "컴퓨터과학",true));
        items.add(new GridViewItem(R.drawable.test_net, 5,"네트워크 해킹과 보안 개정3판", "양대일",
                "한빛아카데미", "979-11-5664-870-3","2022-05-28","네트워크 공부를 위한 도서",
                "컴퓨터과학",false));
        items.add(new GridViewItem(R.drawable.test_data, 6,"C언어로 쉽게 풀어쓴 자료구조", "천인국",
                "생능출판", "978-89-7050-971-6","2022.04.22","자료구조 공부를 위한 도서",
                "컴퓨터과학",true));
        items.add(new GridViewItem(R.drawable.test_com, 7,"컴퓨터구조론", "송미영",
                "생능출판", "978-89-7050-969-3","2022.05.22","컴퓨터구조 공부를 위한 도서",
                "컴퓨터과학",false));
        items.add(new GridViewItem(R.drawable.test_clang, 8,"누구나 쉽게 즐기는 C언어 콘서트", "천인국",
                "생능출판", " 978-89-7050-493-3","2022-05-23","C언어 쉽게 배워보자",
                "컴퓨터과학",true));
//        items.add(new ListViewItem(R.drawable.menu_book, "OS", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "MongoDB", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "캡스톤", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "컴퓨터 구조", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "네트워크", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "머신러닝", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "파이썬", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "자바", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.menu_book, "코틀린", "#캡스톤"));

        adapter = new GridViewAdapter(items, view.getContext());

        gridView.setAdapter(adapter);

        return view;
    }
}
