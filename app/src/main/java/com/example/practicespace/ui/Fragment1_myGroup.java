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
import com.example.practicespace.connection.getUser;
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
    private String User_nickname;
    private View view;

    //멀티스레드 작성시작
    class Sync {
        public void getMyUser() {
            Log.d("test 중 입니다.", "getmyUser");
            Call<getUser> call0 = apiInterface.getUserInfo(LoginInfo.getInstance().data.token);
            call0.enqueue(new Callback<getUser>() {
                @Override
                public void onResponse(Call<getUser> call, Response<getUser> response) {
                    getUser result0 = response.body();
                    if (response.code() == 200) {

                        User_nickname = result0.data.nickname;
                        Log.d("User 중 입니다.", User_nickname);
                    } else {
                        Log.d("User 중 입니다.", "User실패");
                    }

                }
                @Override
                public void onFailure(Call<getUser> call, Throwable t) {
                    call0.cancel();
                }
            });
        }

        public void getGroupList() {
            Log.d("test", "getgroup");
            Call<openGroupList> call1 = apiInterface.getGroupList();
            call1.enqueue(new Callback<openGroupList>() {
                @Override
                public void onResponse(Call<openGroupList> call, Response<openGroupList> response) {
                    openGroupList result = response.body();
                    if (response.code() == 200) {
                        groups = result.data.groups;
                        Log.d("test", String.valueOf(groups.size()));

                        Log.d("test 중 입니다.", "getmyGroup");
                        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
                        for (int i = 0; i < groups.size(); i++) {
                            Group group = groups.get(i);
                            if (group.getAdmin().getNickname().equals(User_nickname)) {
                                            Log.d("test 중 입니다.", "실패인듯 한데 성공");
                                            items.add(new ListViewItem(R.drawable.test_1, group.getName(), group.getDescription()
                                                    , group.getSeq(), group.getOpen(), group.getCreatedDate(), 0));  // bo oknum 나중에 수정
                                            System.out.println(items);

                                        }

                        }
                        adapter = new ListViewAdapter(items, view.getContext());
                        listview.setAdapter(adapter);
                        Log.d("listview", "넣기 성공");



                    }else{
                        Log.d("test 중 입니다.","실패");
                    }

                }



                @Override
                public void onFailure(Call<openGroupList> call, Throwable t) {
                    call.cancel();
                }
            });
        }

        public synchronized void syncRun(int num) {
            if (num == 1) {
                getGroupList();

            }
            else if (num == 2) {
                getMyUser();
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
        MyThread thread2 = new MyThread(sync,2);
        MyThread thread1 = new MyThread(sync,1);


        try{
            thread2.start();
            thread2.join();
            Log.d("스레드테스트", "1");
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            thread1.start();
            thread1.join();
            Log.d("스레드테스트", "2");

        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("스레드테스트", "5");
        return view;
    }
}
