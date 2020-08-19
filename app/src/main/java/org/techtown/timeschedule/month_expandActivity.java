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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class month_expandActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private String time;
    private int choice_start_time, choice_end_time, choice_week;
    private String str_time;
    private String body;
    private ArrayAdapter<CharSequence> week_adapter;
    private TextInputEditText input_body;
    private TextView day_t, category_t;
    private int[] color_array, time_color, time_use;
    private String[] category_array, category_array_d, time_category;
    private ImageView circle;
    private GradientDrawable drawable;
    private int count_category, dialog_position, type, start_time;
    private int color;
    private String category;
    private TextView start_t, end_t, body_t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_expand);

        color_array = new int[100];
        category_array = new String[100];
        time_color = new int[100];
        time_use = new int[100];

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            time = bundle.getString("time");//2020.08.16
            type = bundle.getInt("type");
            start_time = bundle.getInt("start_time");
            body = bundle.getString("body");
            color = bundle.getInt("color");
            category = bundle.getString("category");
        }
        Log.d("time", time);
        Log.d("달칼라",Integer.toString(color));

        day_t = findViewById(R.id.add_month_day);
        day_t.setText(time);

        for(int i = 0; i<100; i++){
            if(category_array[i] == null){
                category_array_d = new String[i];
                i = 100;
            }
        }
        for(int i = 0; i<100; i++){
            if(category_array[i] == null){
                i = 100;
            }else{
                category_array_d[i] = category_array[i];//null없는 배열 다이얼로그에 쓰기위해 필요함
            }
        }
        toolbar = findViewById(R.id.appbar_week_add_layout);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        body_t = findViewById(R.id.add_body);
        body_t.setText(body);

        circle = findViewById(R.id.week_add_circle);
        category_t = findViewById(R.id.week_add_category);
        drawable = (GradientDrawable) ContextCompat.getDrawable(month_expandActivity.this, R.drawable.circle_style);
        category_t.setText(category);
        drawable.setColor(color);
        circle.setImageDrawable(drawable);
        //count_category = 1;//1이면 확인버튼 못누름
        //drawable.setColor(-2236963);
        //circle.setImageDrawable(drawable);


        start_t = findViewById(R.id.add_start_time);
        if(start_time<20) {
            if (start_time % 2 == 0) {
                start_t.setText("0" + start_time/2 + ":00");
            }else{
                start_t.setText("0" + start_time/2 + ":30");
            }
        }else{
            if (start_time % 2 == 0) {
                start_t.setText(start_time/2 + ":00");
            }else{
                start_t.setText(start_time/2 + ":30");
            }
        }

        end_t = findViewById(R.id.add_end_time);
        Log.d("스타트타임",Integer.toString(start_time));
        Log.d("스타트타입",Integer.toString(type));
        if(start_time+type<20) {
            if ((start_time+type) % 2 == 0) {
                end_t.setText("0" + ((start_time+type)/2 + 1) + ":00");
            }else{
                end_t.setText("0" + (start_time+type)/2 + ":30");
            }
        }else{
            if ((start_time+type) % 2 == 0) {
                end_t.setText(((start_time+type)/2 + 1) + ":00");
            }else{
                end_t.setText((start_time+type)/2 + ":30");
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 16908332) {//back
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}
