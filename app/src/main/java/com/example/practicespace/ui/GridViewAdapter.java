package com.example.practicespace.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.practicespace.R;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<GridViewItem>{
    //각 리스트에 들어갈 아이템들을 가지고 있는 리스트 배열
    private ArrayList<GridViewItem> gridViewItemList;
    Context mContext;
    //position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴, : 필수 구현
    //view는 넣을 곳 즉, viewgroup에 들어가 view 생성
    //viewgroup은 내가 보고 있는 화면 즉, list

    //View lookup cache
    private static class ViewHolder{
        ImageView ICon;
        TextView text_Group_Name;
        TextView text_author;
        TextView category;
        TextView rental;
    }

    public GridViewAdapter(ArrayList<GridViewItem> list, Context context){
        super(context, R.layout.listview_crew_item, list);
        this.gridViewItemList = list;
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //position에 위치시키기 위해 listViewItem 하나 생성
        GridViewItem gridViewItem = getItem(position);
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
            convertView = inflater.inflate(R.layout.grid_crew_item,parent,false);//resourceId는 R.id.listView_crew_item이다.
            viewHolder.ICon = (ImageView) convertView.findViewById(R.id.group_icon);
            viewHolder.text_Group_Name = (TextView)convertView.findViewById(R.id.group_name);
            viewHolder.text_author = (TextView)convertView.findViewById(R.id.list_author);
            viewHolder.category = (TextView)convertView.findViewById(R.id.list_category);
            viewHolder.rental = (TextView)convertView.findViewById(R.id.list_rental);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //아이템 내 각 위젯에 대한 데이터 반영
        //위젯에 내가 만들어 놓은 부분 적용
        viewHolder.ICon.setImageResource(gridViewItem.getIcon());
        viewHolder.text_Group_Name.setText(gridViewItem.getTitle());
        viewHolder.text_author.setText("저자: "+gridViewItem.getAuthor());
        viewHolder.category.setText("카테고리: "+gridViewItem.getCategory());
        if(gridViewItem.getRental()==true){
            viewHolder.rental.setText("대여가능");
            viewHolder.rental.setBackgroundResource(R.drawable.rental_o);
        }else{
            viewHolder.rental.setText("대여불가");
            viewHolder.rental.setBackgroundResource(R.drawable.rental_x);
        }


        //클릭이벤트
        LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.book_clcik);
        Intent intent = new Intent(getContext(), book_info.class);
        intent.putExtra("책사진",gridViewItem.getIcon());
        intent.putExtra("책이름",gridViewItem.getTitle());
        intent.putExtra("글쓴이",gridViewItem.getAuthor());
        intent.putExtra("대여여부",gridViewItem.getRental());
        intent.putExtra("책설명",gridViewItem.getDescription());
        intent.putExtra("소유그룹","공대 전공도서");
        intent.putExtra("책주인","narak");
        intent.putExtra("ISBN",gridViewItem.getIsbn());
        intent.putExtra("카테고리",gridViewItem.getCategory());
        intent.putExtra("출판사",gridViewItem.getPublisher());
        intent.putExtra("등록일",gridViewItem.getPublishDate());
        cmdArea.setOnClickListener(v -> getContext().startActivity(intent));
        return convertView;
    }
}

