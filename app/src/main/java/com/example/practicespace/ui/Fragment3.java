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
import com.example.practicespace.connection.getUserList;
import com.example.practicespace.connection.openGroupList;
import com.example.practicespace.vo.Group;
import com.example.practicespace.vo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment {

    ListView listview;
    private static ListViewAdapter2 adapter;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<User> Users = new ArrayList<User>();
    private View view;
    int i;

    //멀티스레드 작성시작
    class Sync{
        public void getUserList(){
            int groupseq = getSeq();
            Log.d("test","getgroup");
            Call<getUserList> call = apiInterface.getUserList(LoginInfo.getInstance().data.token,groupseq);
            call.enqueue(new Callback<getUserList>() {
                @Override
                public void onResponse(Call<getUserList> call, Response<getUserList> response) {
                    getUserList result = response.body();
                    Log.d("책 테스트", String.valueOf(response.body()));
                    if(response.code() == 200){
                        Users = result.data.userList;
                        Log.d("test","getgroup전");
                        ArrayList<ListViewItem2> items = new ArrayList<ListViewItem2>();
                        for(i = 0; i <Users.size(); i++){
                            boolean admin = false;

                            if(Users.get(i).getNickname().equals(getAdminName())){
                                admin=true;
                            }
                            items.add(new ListViewItem2(Users.get(i).getEmails(), Users.get(i).getNickname(),admin,Users.get(i).getUserSeq()));
                        }
                        adapter = new ListViewAdapter2(items, view.getContext());
                        listview.setAdapter(adapter);
                    } else{
                        Log.d("연결 테스트", "실패");
                    }

                }

                @Override
                public void onFailure(Call<getUserList> call, Throwable t) {
                    call.cancel();
                }
            });



        }
        public synchronized void syncRun(int num){
            if(num==1){
                getUserList();
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
    public int getSeq(){
        return getArguments().getInt("groupseq");
    }
    public String getAdminName(){
        return getArguments().getString("adminName");
    }


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
//        items.add(new ListViewItem(R.drawable.test_1, "경기대", "경기대 학생들 들어오세요.", 4,
//                false,"2022-04-11TT11:11:11",1,1));
//        items.add(new ListViewItem(R.drawable.test_2, "졸업만 모여요", "졸업반 분들 같이 책 읽어요!",5
//                ,true,"2022-05-05TT11:11:11",4,1));
//        items.add(new ListViewItem(R.drawable.test_3, "서울 책방", "서울 사람들 모여라",6
//                ,true,"2022-05-11TT11:11:11",3,3));
//        items.add(new ListViewItem(R.drawable.test_4, "영통구 책방", "영통구 사람들을 위한 모임",7
//                ,true,"2022-05-23TT11:11:11",3,2));
//        items.add(new ListViewItem(R.drawable.test_5, "공대 전공도서", "전공도서 서로 공유하실분!",8
//                ,false,"2022-06-01TT11:11:11",5,1));
//        items.add(new ListViewItem(R.drawable.test_1, "대학생", "대학생 분들 오세요!",9
//                ,false,"2022-06-02TT11:11:11",5,1));
        /////
//        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
//        adapter = new ListViewAdapter(items, view.getContext());
//        listview.setAdapter(adapter);

        /////
        Log.d("스레드테스트", "5");
        return view;

    }



}
