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
import com.example.practicespace.connection.SearchGroup;
import com.example.practicespace.connection.bookList;
import com.example.practicespace.vo.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_searchgroup extends Fragment {

    ListView listview;
    private static ListViewAdapter adapter;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Group> groups = new ArrayList<Group>();
    private View view;
    int booknum;
    int i;

    //멀티스레드 작성시작
    class Sync{
        public void getGroupList(){
            String searchStr = getSearchStr();
            Call<SearchGroup> call = apiInterface.searchGroup(
                    LoginInfo.getInstance().data.token,
                    searchStr
            );
            call.enqueue(new Callback<SearchGroup>() {
                @Override
                public void onResponse(Call<SearchGroup> call, Response<SearchGroup> response) {
                    SearchGroup result = response.body();
                    Log.d("책 테스트", String.valueOf(response.body()));
                    if(response.code() == 200){
                        groups = result.data.groups;
                        Log.d("test","getgroup전");
                        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
                        for(i = 0; i <groups.size(); i++){
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
                                        items.add(new ListViewItem(temp.getThumbnail(),temp.getName(), temp.getDescription()
                                                ,temp.getSeq(),temp.getOpen(),temp.getCreatedDate(),booknum));
                                    }
                                    else{
                                        Log.d("연결 테스트", "실패");
                                    }
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
                        adapter = new ListViewAdapter(items, view.getContext());
                        listview.setAdapter(adapter);
                    } else{
                        Log.d("연결 테스트", "실패");
                    }

                }

                @Override
                public void onFailure(Call<SearchGroup> call, Throwable t) {
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
    public String getSearchStr(){
        return getArguments().getString("searchstr");
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
