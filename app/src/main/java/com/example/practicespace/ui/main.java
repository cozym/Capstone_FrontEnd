package com.example.practicespace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.google.android.material.navigation.NavigationView;

public class main extends AppCompatActivity{

    private Button btn_toSearch, btn_toMainList, btn_toMyGroup, btn_toMyBook, btn_toGPS;
    private long backKeyPressedTime = 0;
    private Toast toast;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        //Log.d("###########", LoginInfo.getInstance().data.token);

        /*
        Call<setGroup> call = apiInterface.saveGroup(
                LoginInfo.getInstance().data.token,
                "testgroup",
                true,
                "file",
                127.035,
                37.299
        );
        call.enqueue(new Callback<setGroup>() {
            @Override
            public void onResponse(Call<setGroup> call, Response<setGroup> response) {
                setGroup result = response.body();
                if(response.code() == 200) {
                    Log.d("연결 테스트","성공");
                } else {
                    Log.d("연결 테스트","실패");
                }
            }

            @Override
            public void onFailure(Call<setGroup> call, Throwable t) {
                Log.d("연결 테스트","통신 실패");

            }
        });*/


        this.InitializeLayout();

        //검색버튼
        btn_toSearch= findViewById(R.id.to_search); //버튼 지정
        btn_toSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });
        //메인리스트 버튼
        btn_toMainList = findViewById(R.id.to_mainList); //버튼 지정
        btn_toMainList.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainList_activity.class);
                startActivity(intent);
            }
        });
        //내그룹 버튼
        btn_toMyGroup = findViewById(R.id.to_myGroup); //버튼 지정
        btn_toMyGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mygroup.class);
                startActivity(intent);
            }
        });
        //내도서 버튼
        btn_toMyBook = findViewById(R.id.to_myBook); //버튼 지정
        btn_toMyBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mybook.class);
                startActivity(intent);
            }
        });
        //GPS버튼
        btn_toGPS= findViewById(R.id.to_GPS); //버튼 지정
        btn_toGPS.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), book_info.class);
                startActivity(intent);
            }
        });


    }



    //슬라이드 메뉴
    public void InitializeLayout()
    {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigationView);

        navigationView.setItemIconTintList(null); //아이콘 색상이 테마색으로 변경되는거 방지

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed
        );
        drawLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ///// 메뉴택 이벤트
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home:
                        Intent intent1 = new Intent(getApplicationContext(), main.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_group:
                        Intent intent2 = new Intent(getApplicationContext(), mygroup.class);
                        startActivity(intent2);
                        break;
                    case R.id.menu_book:
                        Intent intent3 = new Intent(getApplicationContext(), mybook.class);
                        startActivity(intent3);
                        break;
                    case R.id.mypage:
                        Intent intent4 = new Intent(getApplicationContext(), mypage.class);
                        startActivity(intent4);
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.main_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
    @Override
    public void onBackPressed() {
        //뒤로가기 2번 연속으로 누르면 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }

        //뒤로가기 누르면 메뉴 다시 사라짐
        DrawerLayout drawer = findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
