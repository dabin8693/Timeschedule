package org.techtown.timeschedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class Category_insertActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private MaterialToolbar toolbar;
    private ImageView img_color;
    private TextInputEditText category_t;
    private GradientDrawable drawable;
    private int[] color_array;
    private String[] category_array;
    private int change_color, first_color;
    private String first_head;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_insert);

        color_array = new int[100];
        category_array = new String[100];

        final Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            position = bundle.getInt("position");
            first_color = bundle.getInt("color");
            first_head = bundle.getString("head");
        }
        restore();
        change_color = first_color;//초기값 설정

        toolbar = findViewById(R.id.appbar_category);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);

        category_t = findViewById(R.id.category_add_body);
        category_t.setText(first_head);
        //Paint paint = new Paint();
        //paint.setColor(Color.parseColor("#ffdddddd"));
        //Log.d("칼라는ㅇㄴㄹㄴㅇㄹ:",Integer.toString(paint.getColor()));//기본색-2236963
        img_color = findViewById(R.id.category_add_color);
        drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circle_style);
        drawable.setColor(first_color);
        img_color.setImageDrawable(drawable);//초기값
        img_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.newBuilder().setDialogType(ColorPickerDialog.TYPE_PRESETS).show(Category_insertActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.week_add_app_bar, menu);
        MenuItem delectItem = menu.findItem(R.id.appbar_week_add_delect);

        //MenuItem check_btn = menu.findItem(R.id.appbar_week_check);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 2131296336) {//check2131296336
            if(category_t.getText().toString() != null) {
                if(category_t.getText().toString().length() > 0) {
                    int count = 0;
                    for(int i = 0; i<100; i++){//제목 같은게 있는지
                        if(category_array[i] != null){
                            if(category_array[i].equals(category_t.getText().toString().trim()) == true){
                                if(i == position){
                                 //현재 글을 조회하면 제목을 수정안해도 저장이 되야되서 예외처리
                                }else {
                                    count++;
                                }
                            }
                        }else{
                            i = 100;
                        }
                    }
                    if(count == 0) {
                        new AlertDialog.Builder(Category_insertActivity.this).setTitle("알림").setMessage("수정하시겠습니까").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                category_array[position] = category_t.getText().toString();
                                color_array[position] = change_color;
                                save();
                                finish();
                            }
                        }).show();
                    }else{
                        Toast.makeText(Category_insertActivity.this, "이미 존재하는 카테고리 입니다", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Category_insertActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Category_insertActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
            }

        } else if (id == 16908332) {//back

            finish();
        } else if(id == 2131296337){//삭제2131296337
            new AlertDialog.Builder(Category_insertActivity.this).setTitle("알림").setMessage("삭제하시겠습니까").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i = position; i<99; i++){
                        category_array[i] = category_array[i+1];
                        color_array[i] = color_array[i+1];
                    }
                    category_array[99] = null;
                    color_array[99] = 0;
                    save();
                    finish();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delect(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int i = 0; i<100; i++){//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            //Log.d("초이스1i"+i,Integer.toString(i));
            editor.putInt("color_array"+i, 0);
            editor.putString("category_array"+i, null);
            editor.putInt("color_array"+i, color_array[i]);
            editor.putString("category_array"+i, category_array[i]);
        }
        editor.commit();
    }

    public void save(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int i = 0; i<100; i++){//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            //Log.d("초이스1i"+i,Integer.toString(i));
            editor.putInt("color_array"+i, 0);
            editor.putString("category_array"+i, null);
            editor.putInt("color_array"+i, color_array[i]);
            editor.putString("category_array"+i, category_array[i]);
        }
        editor.commit();
    }

    public void restore(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {

            for (int i = 0; i < 100; i++) {
                color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                category_array[i] = pref.getString("category_array" + i, null);

            }
        }
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        Log.d("칼라",Integer.toString(color));
        drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circle_style);
        drawable.setColor(color);
        change_color = color;//저장할때 포지션위치에 저장하면 됨
        img_color.setImageDrawable(drawable);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
