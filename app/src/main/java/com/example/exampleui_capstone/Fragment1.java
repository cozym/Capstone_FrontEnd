package com.example.exampleui_capstone;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment1,container,false);
        //레이아웃을 인플레이트(inflate)하는 곳이다.
        //onCreateView의 매개변수로 전달되는 container가 Activity의 ViewGroup이며, 여기에 Fragment가 위치하게 된다.
        //또 다른 매개변수인 savedInstanceState는 Bundle 객체로 Fragment가 재개되는 경우 이전 상태에 대한 데이터를 제공한다.

    }
}
