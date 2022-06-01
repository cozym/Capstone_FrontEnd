package com.example.practicespace.ui;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.openGroupList;
import com.example.practicespace.vo.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment1 extends Fragment {

    ListView listview;
    private static ListViewAdapter adapter;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Group> groups = new ArrayList<Group>();

    @Override
    public void onAttach(Context context) {
        Log.d("123123","123123");

        super.onAttach(context);
        Log.d("321321","3212321");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getGroupList();

        //adapter에 넣을 리스트뷰를 받는 배열
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        for(int i = 0; i <groups.size(); i++){
            items.add(new ListViewItem(R.drawable.profile,groups.get(i).getName(), "#캡스톤", groups.get(i).getSeq()));

        }

//        items.add(new ListViewItem(R.drawable.profile, "5학년 다니면 그만이야", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "영통구 책방", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "경기대 도서관", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "컴공책 동아리", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "논문만 판다", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "공대생 모여라", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "경기도 모여라", "#캡스톤"));
//        items.add(new ListViewItem(R.drawable.profile, "서울도 모여라", "#캡스톤"));

        View view = inflater.inflate(R.layout.frag_group, container, false);
        listview = (ListView) view.findViewById(R.id.List_group_Info);

        adapter = new ListViewAdapter(items, view.getContext());

        listview.setAdapter(adapter);

        return view;
    }

    public void getGroupList(){
        Call<openGroupList> call = apiInterface.getGroupList();

        call.enqueue(new Callback<openGroupList>() {
            @Override
            public void onResponse(Call<openGroupList> call, Response<openGroupList> response) {
                openGroupList result = response.body();
                if(response.code() == 200){
                    groups = result.data.groups;

                } else{
                    Log.d("연결 테스트", "실패");
                }
            }

            @Override
            public void onFailure(Call<openGroupList> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
