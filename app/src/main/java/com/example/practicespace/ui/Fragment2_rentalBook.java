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
import com.example.practicespace.connection.getBookLogList;
import com.example.practicespace.connection.getMyBookLogList;
import com.example.practicespace.connection.myBookList;
import com.example.practicespace.vo.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//내도서에서 이용
public class Fragment2_rentalBook extends Fragment {

    GridView gridView;
    private static GridViewAdapter adapter;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Book> books = new ArrayList<Book>();
    private View view;

    class Sync{
        public void getBookList(){
            Log.d("연결 테스트", "코드까지는 성공1111111");
            Call<getMyBookLogList> call = apiInterface.getMyBookLogList(LoginInfo.getInstance().data.token);
            call.enqueue(new Callback<getMyBookLogList>() {
                @Override
                public void onResponse(Call<getMyBookLogList> call, Response<getMyBookLogList> response) {
                    getMyBookLogList result = response.body();
                    if(response.code()==200){
                        Log.d("내도서로그"," 성공");
                        ArrayList<GridViewItem> items = new ArrayList<GridViewItem>();
                        for(int i=0; i<result.data.bookLogList.size();i++){
                            if(result.data.bookLogList.get(i).getBookLogStatus().equals("BORROW")){
                                Book book = result.data.bookLogList.get(i).getBook();
                                items.add(new GridViewItem(book.getThumbnail(), book.getSeq(), book.getTitle(),
                                        book.getAuthor(),book.getPublisher(),book.getIsbn(),book.getPublishDate(),
                                        book.getDescription(),book.getCategory(),book.getRental()));
                            }
                        }
                        adapter = new GridViewAdapter(items, view.getContext());
                        gridView.setAdapter(adapter);
                    }else{
                        Log.d("내도서로그"," 실패 :"+response.code());
                    }
                }

                @Override
                public void onFailure(Call<getMyBookLogList> call, Throwable t) {

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
//        try{
//            thread2.start();
//            thread2.join();
//            Log.d("스레드테스트", "3");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        //adapter에 넣을 리스트뷰를 받는 배열
//        ArrayList<GridViewItem> items = new ArrayList<GridViewItem>();
//          items.add(new GridViewItem(R.drawable.test_jsp, 4,"쉽게 배우는 JSP 웹 프로그래밍", "송미영",
//                  "한빛아카데미", "979-11-5664-338-8","2022-04-08","JSP입문자를 위한 도서",
//                  "컴퓨터과학",true));
//        items.add(new GridViewItem(R.drawable.test_net, 5,"네트워크 해킹과 보안 개정3판", "양대일",
//                "한빛아카데미", "979-11-5664-870-3","2022-05-28","네트워크 공부를 위한 도서",
//                "컴퓨터과학",false));
//        items.add(new GridViewItem(R.drawable.test_data, 6,"C언어로 쉽게 풀어쓴 자료구조", "천인국",
//                "생능출판", "978-89-7050-971-6","2022.04.22","자료구조 공부를 위한 도서",
//                "컴퓨터과학",true));
//        items.add(new GridViewItem(R.drawable.test_com, 7,"컴퓨터구조론", "송미영",
//                "생능출판", "978-89-7050-969-3","2022.05.22","컴퓨터구조 공부를 위한 도서",
//                "컴퓨터과학",false));
//        items.add(new GridViewItem(R.drawable.test_clang, 8,"누구나 쉽게 즐기는 C언어 콘서트", "천인국",
//                "생능출판", " 978-89-7050-493-3","2022-05-23","C언어 쉽게 배워보자",
//                "컴퓨터과학",true));

//        adapter = new GridViewAdapter(items, view.getContext());
//        gridView.setAdapter(adapter);
        Log.d("연결 테스트", "코드까지는 성공");
        return view;
    }
}