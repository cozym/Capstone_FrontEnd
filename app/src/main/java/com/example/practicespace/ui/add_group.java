package com.example.practicespace.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.setGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class add_group extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private final int GET_GALLERY_IMAGE=200;
    private ImageView imageview;
    private EditText group_Name;
    private EditText group_Description;
    private EditText group_HashTag;
    private boolean is_open;
    private Button submit_group;
    Intent intent;

    public String getPath(Uri uri){
        int column=0;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection,null,null,null);
        if(cursor.moveToFirst()){
            column=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imageview = (ImageView)findViewById(R.id.group_thumbnail);
        group_Name =(EditText)findViewById(R.id.group_Name);
        group_Description = (EditText)findViewById(R.id.group_Description);
        submit_group = (Button)findViewById(R.id.submit_group);



        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

            }
        });

        submit_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Call<setGroup> call= apiInterface.saveGroup(
//                        LoginInfo.getInstance().data.token,
//                        group_Name.getText().toString(),
//                        is_open,
//                        imageview.toString(),
//                        group_Description.getText().toString(),
//                        10.15,
//                        9.12
//                );
//                call.enqueue(new Callback<setGroup>() {
//                    @Override
//                    public void onResponse(Call<setGroup> call, Response<setGroup> response) {
//                    setGroup result = response.body();
//                    if(response.code() == 200){
//                        Log.d("test","setgroup성공");
//                    }
//                    else {
//                        Log.d("test","setgroupt실패");
//                    }
//                    }
//
//                    @Override
//                    public void onFailure(Call<setGroup> call, Throwable t) {
//                        call.cancel();
//                    }
//                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Log.d("테스트",getPath(selectedImageUri));
            imageview.setImageURI(selectedImageUri);
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.group_isOpen:
                is_open = true;
                break;
            case R.id.group_isClose:
                if (checked)
                    is_open = false;
                break;
        }
    }
}
