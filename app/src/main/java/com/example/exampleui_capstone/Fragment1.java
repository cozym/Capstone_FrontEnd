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

public class Fragment1 extends Fragment {

    ListView listview;
    private static ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);
        listview = (ListView) view.findViewById(R.id.List_crew_Info);

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        items.add(new ListViewItem(R.drawable.note, "박우현", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source2, "안영민", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "김경민", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "이대현", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "이원우", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "임성환", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "윤교수님", "#캡스톤"));
        items.add(new ListViewItem(R.drawable.btn_source1, "캡스톤 수 123", "#캡스톤"));

        adapter = new ListViewAdapter(items, view.getContext());

        listview.setAdapter(adapter);

        return view;
    }
}
