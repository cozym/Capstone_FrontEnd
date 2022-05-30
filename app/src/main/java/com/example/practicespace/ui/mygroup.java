package com.example.practicespace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;
import com.google.android.material.navigation.NavigationView;

public class mygroup extends AppCompatActivity{

    Fragment fragment0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroup);


        this.InitializeLayout();





        fragment0 = new Fragment1();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();


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
                        Intent intent2 = new Intent(getApplicationContext(), group_info.class);
                        startActivity(intent2);
                        break;
                    case R.id.menu_book:
                        Intent intent3 = new Intent(getApplicationContext(), book_info.class);
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
     //뒤로가기 누르면 메뉴 다시 사라짐
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
