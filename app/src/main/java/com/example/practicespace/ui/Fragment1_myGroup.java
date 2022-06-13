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
import com.example.practicespace.connection.bookList;
import com.example.practicespace.connection.getGroup;
import com.example.practicespace.connection.getUser;
import com.example.practicespace.connection.myGroupList;
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
    int booknum;

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

        public void getGroupList(){
            Log.d("test","getgroup");
            Call<myGroupList> call = apiInterface.getMyGroupList(LoginInfo.getInstance().data.token);
            call.enqueue(new Callback<myGroupList>() {
                @Override
                public void onResponse(Call<myGroupList> call, Response<myGroupList> response) {
                    myGroupList result = response.body();
                    Log.d("책 테스트", String.valueOf(response.body()));
                    if(response.code() == 200){
                        groups = result.data.groups;
                        Log.d("test","getgroup전");
                        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
                        for(int i = 0; i <groups.size(); i++){
                            Call<bookList> call2 = apiInterface.getBookList(
                                    LoginInfo.getInstance().data.token,groups.get(i).getSeq(), 0
                            );
                            Group temp =groups.get(i);
                            call2.enqueue(new Callback<bookList>() {
                                @Override
                                public void onResponse(Call<bookList> call, Response<bookList> response) {
                                    bookList result2 = response.body();
                                    Log.d("책 리스트","잘 가져옴") ;
                                    if(response.code()==200){
                                        Log.d("연결 테스트", "책불러오기");
                                        booknum = result2.data.books.size();
                                        Log.d("책수", String.valueOf(booknum));
                                            items.add(new ListViewItem(temp.getThumbnail(), temp.getName(), temp.getDescription()
                                                    , temp.getSeq(), temp.getOpen(), temp.getCreatedDate(), booknum));
                                    }
                                    else{
                                        Log.d("연결 테스트", "실패");
                                    }
                                    //어차피 계속 반복하므로 마지막으로 남은 adapter
                                    // 즉, items가 전부 추가된 adapter만 남게된다.
                                    adapter = new ListViewAdapter(items, view.getContext());
                                    listview.setAdapter(adapter);
                                }
                                @Override
                                public void onFailure(Call<bookList> call, Throwable t) {
                                    Log.d("연결 테스트", "실패22");
                                }
                            });
                            ///도서수 계산
                        }
                    } else{
                        Log.d("연결 테스트", "실패"+response.code());
                    }

                }

                @Override
                public void onFailure(Call<myGroupList> call, Throwable t) {
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
