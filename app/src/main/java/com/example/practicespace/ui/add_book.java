package com.example.practicespace.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicespace.R;
import com.example.practicespace.connection.APIClient;
import com.example.practicespace.connection.APIInterface;
import com.example.practicespace.connection.SomeResponse;
import com.example.practicespace.connection.setBook;
import com.example.practicespace.connection.setGroup;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import android.widget.TextView;

public class add_book extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE=200;
    private ImageView imageview;
    private ImageButton ISBNCamera;
    private EditText ISBNInput;
    private EditText addbook_title;
    private EditText addbook_author;
    private EditText addbook_publisher;
    private EditText addbook_description;
    private Button addbook_category;
    private String genreString;
    private Button addbook_button;
    private Intent secondIntent;
    private int groupseq;
    private String uri;
    Intent intent;


    final String[] category = new String[] {"컴퓨터 과학", "철학", "종교", "사회과학", "언어", "과학", "기술", "예술", "문학", "역사"};
    int categorynum ;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        secondIntent=getIntent();
        groupseq=secondIntent.getIntExtra("그룹시퀀스",0);

        imageview = (ImageView)findViewById(R.id.addbook_thumbnail);
        addbook_title=(EditText)findViewById(R.id.addbook_title);
        addbook_author=(EditText)findViewById(R.id.addbook_author);
        addbook_publisher=(EditText)findViewById(R.id.addbook_publisher);
        addbook_description=(EditText)findViewById(R.id.addbook_description);
        addbook_category=(Button)findViewById(R.id.addbook_category);
        addbook_button=(Button)findViewById(R.id.addbook_button);
        ISBNInput = (EditText)findViewById(R.id.addbook_ISBN);

        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        addbook_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String serveruri = "https://image.yes24.com/goods/96706474/XL";  // 책 이미지 uri 직접추가해야함, publishDate도 같이
                Call<setBook> call=apiInterface.saveBook(
                        LoginInfo.getInstance().data.token,
                        addbook_title.getText().toString(),
                        addbook_author.getText().toString(),
                        addbook_publisher.getText().toString(),
                        ISBNInput.getText().toString(),
                        serveruri,
                        "2022-05-16",
                        addbook_description.getText().toString(),
                        genreString,
                        groupseq
                );
                call.enqueue(new Callback<setBook>() {
                    @Override
                    public void onResponse(Call<setBook> call, Response<setBook> response) {
                        setBook result= response.body();
                        if(response.code()==200) {
                            Log.d("test","setgroup성공");
                        }
                        else {
                            Log.d("test","setgroupt실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<setBook> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        //ISBN Scanner를 가져오는 button 및 클릭 리스너
        ISBNCamera = (ImageButton) findViewById(R.id.ISBNCamera);
        ISBNCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

    }

    public void DialogClick(View view) {
        new AlertDialog.Builder(this).setTitle("선택").setSingleChoiceItems(category, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categorynum = which;
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int select) {
                Button genre = (Button)findViewById(R.id.addbook_category);
                switch (categorynum){
                    case 0:
                        genre.setText("컴퓨터과학");
                        genreString="컴퓨터과학";
                        break;
                    case 1:
                        genre.setText("철학");
                        genreString="철학";
                        break;
                    case 2:
                        genre.setText("종교");
                        genreString="종교";
                        break;
                    case 3:
                        genre.setText("사회과학");
                        genreString="사회과학";
                        break;
                    case 4:
                        genre.setText("언어");
                        genreString="언어";
                        break;
                    case 5:
                        genre.setText("과학");
                        genreString="과학";
                        break;
                    case 6:
                        genre.setText("기술");
                        genreString="기술";
                        break;
                    case 7:
                        genre.setText("예술");
                        genreString="예술";
                        break;
                    case 8:
                        genre.setText("문학");
                        genreString="문학";
                        break;
                    case 9:
                        genre.setText("역사");
                        genreString="역사";
                        break;
                }
            }
        }).setNegativeButton("취소", null).show();
    }

    private void scanCode() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), getPath(selectedImageUri));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","jpg",requestBody);
            Log.d("image test","말좀 들어라32211");
            Call<SomeResponse> call = apiInterface.saveImage(
                    LoginInfo.getInstance().data.token,
                    filePart
            );
            call.enqueue(new Callback<SomeResponse>() {
                @Override
                public void onResponse(Call<SomeResponse> call, Response<SomeResponse> response) {
                    SomeResponse result = response.body();
                    if(response.code() == 200){
                        uri = result.data.URL;
                        Log.d("image test",uri);
                    }else{
                        Log.d("image test","말좀 들어라33333");
                    }
                }

                @Override
                public void onFailure(Call<SomeResponse> call, Throwable t) {
                    Log.d("image test","말좀 들어라3344567");
                }
            });

            Log.d("image test","말좀 들어라1");
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(result.getContents())
                        .setTitle("Scanning Result")
                        .setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                scanCode();
                            }
                        }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ISBNInput = (EditText)findViewById(R.id.addbook_ISBN);
                                ISBNInput.setText(result.getContents());
                                //finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                Toast.makeText(this,"No Results", Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String getPath(Uri uri){
        int column=0;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection,null,null,null);
        if(cursor.moveToFirst()){
            column=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column);
    }
}
