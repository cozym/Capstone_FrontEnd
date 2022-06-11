package com.example.practicespace.ui;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.bookList;
import com.example.practicespace.vo.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment2 extends Fragment {

    GridView gridView;
    private static GridViewAdapter adapter;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Book> books = new ArrayList<Book>();
    private View view;

    class Sync{
        public void getBookList(){
            Log.d("연결 테스트", "코드까지는 성공1111111");
            Call<bookList> call = apiInterface.getBookList(
                    LoginInfo.getInstance().data.token,null,0
            );
            call.enqueue(new Callback<bookList>() {
                @Override
                public void onResponse(Call<bookList> call, Response<bookList> response) {
                    bookList result = response.body();


                    if(response.code()==200){
                        Log.d("연결 테스트", "코드까지는 성공");
                        books = result.data.books;
                        ArrayList<GridViewItem> items = new ArrayList<GridViewItem>();
                        for(int i = 0; i<books.size();i++){
                            items.add(new GridViewItem(books.get(i).getThumbnail(), books.get(i).getSeq(),
                                    books.get(i).getTitle(),books.get(i).getAuthor(),books.get(i).getPublisher()
                                    ,books.get(i).getIsbn(),books.get(i).getPublishDate(),books.get(i).getDescription()
                                    ,books.get(i).getCategory(),books.get(i).getRental()));
                            Log.d("북 리스트 읽어오기", books.get(i).getTitle()+", "+books.get(i).getThumbnail());
                        }
                        adapter = new GridViewAdapter(items, view.getContext());
                        gridView.setAdapter(adapter);

                    }
                    else{
                        Log.d("연결 테스트", "실패");
                    }
                }

                @Override
                public void onFailure(Call<bookList> call, Throwable t) {t.printStackTrace();
                    /*Log.d("연결 테스트", "실패22")*/;
                }
            });
        }
        public synchronized void syncRun(int num){
            if(num==1){
                getBookList();
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
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_book, container, false);
        gridView = (GridView) view.findViewById(R.id.List_book_Info);

        Sync sync = new Sync();
        MyThread thread1 = new MyThread(sync,1);
//        MyThread thread2 = new MyThread(sync,2);
//
        Log.d("연결 테스트", "쓰레드 전까지 성공 까지는 성공");
        try{
            thread1.start();
            thread1.join();
            Log.d("스레드테스트", "2");
        }catch(Exception e){
            e.printStackTrace();
        }


        Log.d("연결 테스트", "코드까지는 성공");
        return view;
    }
}