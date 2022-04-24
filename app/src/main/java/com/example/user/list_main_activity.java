package com.example.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class list_main_activity extends AppCompatActivity implements View.OnClickListener{

    Fragment fragment0, fragment1;
    private Button btn_crew;
    private Button btn_book;
    private Button btn_crew2;
    DrawerLayout drawLayout;
    NavigationView navigationView;

    //추가 버튼 관련
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);


        this.InitializeLayout();


        fragment0 = new Fragment1();
        fragment1 = new Fragment2();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){

                    selected = fragment0;

                }else if (position == 1){

                    selected = fragment1;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        //추가 버튼관련 애니메이션
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

    }


    ////우측 상단 검색 메뉴 추가
    public boolean onCreateOptionsMenu(Menu menu) {   
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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
                        Intent intent1 = new Intent(getApplicationContext(), list_main_activity.class);
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
                        Intent intent4 = new Intent(getApplicationContext(), user_info.class);
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

    //클릭 이벤트
    @Override
    public void onClick(View view) {
        //추가 버튼 이벤트
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent1 = new Intent(getApplicationContext(), add_group.class);
                startActivity(intent1);
                break;
            case R.id.fab2:
                anim();
                Intent intent2 = new Intent(getApplicationContext(), add_book.class);
                startActivity(intent2);
                break;
            default:
                anim();
                break;
        }
    }
    
    //추가 버튼 애니메이션
    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}
