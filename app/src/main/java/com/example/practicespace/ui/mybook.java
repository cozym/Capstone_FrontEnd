package com.example.practicespace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.getUser;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mybook extends AppCompatActivity{

    Fragment fragment0,fragment1;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybook);
        NavigationView navigationView = (NavigationView)findViewById(R.id.main_navigationView);
        View nav_header_view = navigationView.getHeaderView(0);
        TextView nickname = (TextView)nav_header_view.findViewById(R.id.main_nickname);
        TextView email = (TextView)nav_header_view.findViewById(R.id.main_email);

        if(LoginInfo.getInstance().data.token != null){
            Call<getUser> user = apiInterface.getUserInfo(LoginInfo.getInstance().data.token);
            user.enqueue(new Callback<getUser>() {
                @Override
                public void onResponse(Call<getUser> call, Response<getUser> response) {
                    getUser result = response.body();
                    nickname.setText(result.data.nickname);
                    email.setText(result.data.emails.get(0));
                }

                @Override
                public void onFailure(Call<getUser> call, Throwable t) {

                }
            });
        }else
            Log.d("연결 테스트","실패");


        this.InitializeLayout();





//        fragment0 = new Fragment2_myBook();
       fragment0 = new Fragment2_rentalBook();
        fragment1 = new Fragment2_myBook();

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
