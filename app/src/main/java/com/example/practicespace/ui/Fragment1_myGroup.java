package com.example.practicespace.ui;



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
import com.example.practicespace.connection.getGroup;
import com.example.practicespace.connection.openGroupList;
import com.example.practicespace.vo.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment1_myGroup extends Fragment {

    ListView listview;
    private static ListViewAdapter adapter;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Group> groups = new ArrayList<Group>();
    private Group group;
    private View view;

    //멀티스레드 작성시작
    class Sync{
        public void getGroupList(){
            Log.d("test","getgroup");
            Call<openGroupList> call1 = apiInterface.getGroupList();
            call1.enqueue(new Callback<openGroupList>() {
                @Override
                public void onResponse(Call<openGroupList> call, Response<openGroupList> response) {
                    openGroupList result = response.body();
                    if(response.code() == 200){
                        groups = result.data.groups;
//                        Log.d("test","getgroup전");
                        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
                        for(int i = 0; i <groups.size(); i++){
                            Call<getGroup> call2 = apiInterface.getGroupSeq(
                                    LoginInfo.getInstance().data.token,
                                    groups.get(i).getSeq()
                            );
                            call2.enqueue(new Callback<getGroup>() {
                                @Override
                                public void onResponse(Call<getGroup> call, Response<getGroup> response) {
                                   getGroup result2 = response.body();
                                   if(response.code() == 200){
                                       group = result2.data.group;
                                       items.add(new ListViewItem(R.drawable.profile,group.getName(), group.getDescription()
                                               ,group.getSeq(),group.getOpen(),group.getCreatedDate(), 0));  // booknum 나중에 수정
                                   }
                                   else{
                                       Log.d("연결 테스트2", "실패");
                                   }
                                }

                                @Override
                                public void onFailure(Call<getGroup> call, Throwable t) {
                                    call2.cancel();
                                }
                            });

                        }

                        adapter = new ListViewAdapter(items, view.getContext());
                        listview.setAdapter(adapter);
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
        public synchronized void syncRun(int num){
            if(num==1){
                getGroupList();
            }
        }

    }
    class MyThread extends Thread{
        private Sync sync;
        int num;

        public MyThread(Sync sync, int num){
            this.sync = sync;
            this.num = num;
        }
        public void run(){
            sync.syncRun(num);
        }
    }
    //멀티스레드 작성끝

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_group, container, false);
        listview = (ListView) view.findViewById(R.id.List_group_Info);

        Sync sync = new Sync();
        MyThread thread1 = new MyThread(sync,1);
        MyThread thread2 = new MyThread(sync,2);

        try{
            thread1.start();
            thread1.join();
            Log.d("스레드테스트", "2");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            thread2.start();
            thread2.join();
            Log.d("스레드테스트", "3");
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("스레드테스트", "5");
        return view;

    }



}
