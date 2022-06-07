package com.example.practicespace.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.practicespace.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<ListViewItem>{
    //각 리스트에 들어갈 아이템들을 가지고 있는 리스트 배열
    private ArrayList<ListViewItem> listViewItemList;
    Context mContext;
    //position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴, : 필수 구현
    //view는 넣을 곳 즉, viewgroup에 들어가 view 생성
    //viewgroup은 내가 보고 있는 화면 즉, list

    //View lookup cache
    private static class ViewHolder{
        ImageView ICon;
        TextView text_Group_Name;
        TextView text_Hash_Tag;
        TextView isopen;
        TextView booknum;
    }

    public ListViewAdapter(ArrayList<ListViewItem> list, Context context){
        super(context, R.layout.listview_crew_item, list);
        this.listViewItemList = list;
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //position에 위치시키기 위해 listViewItem 하나 생성
        ListViewItem listViewItem = getItem(position);
        //cache 사용
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

//사용항 inflater의 객체 얻는 3가지 방법
//LayoutInflater inflater =
//(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//LayoutInflater inflater = Activity.getLayoutInflater();
//LayoutInflater inflater = LayoutInflater.from(Context context);
            convertView = inflater.inflate(R.layout.listview_crew_item,parent,false);//resourceId는 R.id.listView_crew_item이다.
            viewHolder.ICon = (ImageView) convertView.findViewById(R.id.group_icon);
//            Glide.with(convertView).load(listViewItem.getIcon()).into(viewHolder.ICon);
            viewHolder.text_Group_Name = (TextView)convertView.findViewById(R.id.group_name);
            viewHolder.text_Hash_Tag = (TextView)convertView.findViewById(R.id.group_des);
            viewHolder.isopen = (TextView)convertView.findViewById(R.id.gorup_list_isopen);
            viewHolder.booknum = (TextView)convertView.findViewById(R.id.group_list_book);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //아이템 내 각 위젯에 대한 데이터 반영
        //위젯에 내가 만들어 놓은 부분 적용
        viewHolder.ICon.setImageResource(R.drawable.test_1);
        viewHolder.text_Group_Name.setText(listViewItem.getGroupName());
        if(listViewItem.getIsOpen()==true){
            viewHolder.isopen.setText("공개");
            viewHolder.isopen.setBackgroundResource(R.drawable.public_o);
        }else {
            viewHolder.isopen.setText("비공개");
            viewHolder.isopen.setBackgroundResource(R.drawable.public_x);
        }
        viewHolder.booknum.setText("도서 수: "+listViewItem.getBooknum());
        String group_date = listViewItem.getCreatedDate().substring(0,10);
        //클릭이벤트
        LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.group_click);
        Intent intent = new Intent(getContext(), group_enter.class);
        intent.putExtra("그룹사진",listViewItem.getIcon());
        intent.putExtra("그룹이름",viewHolder.text_Group_Name.getText());
        intent.putExtra("그룹설명",listViewItem.getGroupDes());
        intent.putExtra("그룹공개",listViewItem.getIsOpen());
//        intent.putExtra("회원수",listViewItem.getPeonum());
//        intent.putExtra("도서수",listViewItem.getBooknum());
//        intent.putExtra("관리자", listViewItem.getAdmin().getNickname());
        intent.putExtra("그룹시퀀스",listViewItem.getGroupSeq());
        intent.putExtra("그룹생성일" ,group_date);
        cmdArea.setOnClickListener(v -> getContext().startActivity(intent));

        return convertView;
    }
}
