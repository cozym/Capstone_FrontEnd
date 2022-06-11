package com.example.practicespace.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.SomeResponse;
import com.example.practicespace.connection.setGroup;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;


public class add_group extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private final int GET_GALLERY_IMAGE=200;
    private ImageView imageview;
    private EditText group_Name;
    private EditText group_Description;
    private EditText group_HashTag;
    private boolean is_open;
    private Button submit_group;
    private String uri;
    Intent intent;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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


        imageview = (ImageView) findViewById(R.id.group_thumbnail);
        group_Name = (EditText) findViewById(R.id.group_Name);
        group_Description = (EditText) findViewById(R.id.group_Description);
//        group_HashTag = (EditText) findViewById(R.id.group_HashTag);
        submit_group = (Button) findViewById(R.id.submit_group);

        verifyStoragePermissions(add_group.this);
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
                AlertDialog.Builder dlg = new AlertDialog.Builder(add_group.this);
                dlg.setTitle("등록 하시겠습니까?");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String serveruri = "http://5gradekgucapstone.xyz:8080".concat(uri);
//                                "https://t1.daumcdn.net/cfile/tistory/25203F4A5764A82608";//여기에다가 사진 주소(10메가이하)
                        Call<setGroup> call = apiInterface.saveGroup(
                                LoginInfo.getInstance().data.token,
                                group_Name.getText().toString(),
                                true,
                                serveruri,
                                group_Description.getText().toString(),
                                127.0312,
                                37.2970
                        );
                        call.enqueue(new Callback<setGroup>() {
                            @Override
                            public void onResponse(Call<setGroup> call, Response<setGroup> response) {
                                setGroup result = response.body();
                                if (response.code() == 200) {
                                    Log.d("test", "setgroup성공");
                                } else {
                                    Log.d("test", "setgroupt실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<setGroup> call, Throwable t) {
                                call.cancel();
                            }
                        });
                        Toast.makeText(add_group.this,"등록됐습니다.",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                dlg.setNegativeButton("아니오",null);
                dlg.show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            File file = new File(getPath(selectedImageUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse(/*"image/*"*/
                    getContentResolver().getType(selectedImageUri) /*"multipart/form-data"*/), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestFile);
            Log.d("image test","말좀 들어라32211");
            Log.d("image test",requestFile.toString());
            Call<SomeResponse> call = apiInterface.saveImage(
                    LoginInfo.getInstance().data.token,
                    body
            );
            call.enqueue(new Callback<SomeResponse>() {
                @Override
                public void onResponse(Call<SomeResponse> call, Response<SomeResponse> response) {
                    SomeResponse result = response.body();
                    if(response.code() == 200){
                        uri = result.data.url;
                        Log.d("image test",uri);
                    }else{
                        Log.d("image test","말좀 들어라33333");
                    }
                }

                @Override
                public void onFailure(Call<SomeResponse> call, Throwable t) {
                    Log.d("image test","말좀 들어라3344567");
                    t.printStackTrace();
                }
            });
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
