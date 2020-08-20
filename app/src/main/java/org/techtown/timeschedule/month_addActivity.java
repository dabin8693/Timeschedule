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

public class month_addActivity extends AppCompatActivity {

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
    private int count_category, dialog_position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_add);

        color_array = new int[100];
        category_array = new String[100];
        time_color = new int[100];
        time_use = new int[100];

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            time = bundle.getString("time");//2020.08.16

        }
        Log.d("time", time);

        day_t = findViewById(R.id.add_month_day);
        day_t.setText(time);

        restore();
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

        input_body = findViewById(R.id.add_body);

        circle = findViewById(R.id.week_add_circle);
        category_t = findViewById(R.id.week_add_category);
        drawable = (GradientDrawable) ContextCompat.getDrawable(month_addActivity.this, R.drawable.circle_style);

        count_category = 1;//1이면 확인버튼 못누름
        drawable.setColor(-2236963);
        circle.setImageDrawable(drawable);
        category_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(month_addActivity.this).setTitle("선택").setItems(category_array_d, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count_category = 0;
                        dialog_position = which;
                        category_t.setText(category_array[which]);
                        drawable.setColor(color_array[which]);
                        circle.setImageDrawable(drawable);
                    }
                }).setNegativeButton("닫기",null).show();
            }
        });

        Spinner time_spinner = (Spinner) findViewById(R.id.add_start_time);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.month_spinner_start, android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(time_adapter);
        time_spinner.setSelection(10);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                choice_start_time = position;//0시0분부터 센다
                //Log.d("선택한 시간은",Integer.toString(choice_start_time));
                //Log.d("넘버:",Integer.toString(money));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner time_spinner2 = (Spinner) findViewById(R.id.add_end_time);
        ArrayAdapter<CharSequence> time_adapter2 = ArrayAdapter.createFromResource(this,
                R.array.month_spinner_end, android.R.layout.simple_spinner_item);
        time_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner2.setAdapter(time_adapter2);
        time_spinner2.setSelection(12);
        time_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                choice_end_time = position;
                //Log.d("선택한 시간은",Integer.toString(choice_end_time));
                //Log.d("넘버:",Integer.toString(money));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.wekk_setting_app_bar, menu);
        //MenuItem check_btn = menu.findItem(R.id.appbar_week_check);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 2131296339) {//check
            if(choice_start_time<=choice_end_time) {
                if(input_body.getText() != null) {
                    if(input_body.getText().toString().length()>0) {
                        if(count_category == 0) {
                            save();
                        }
                    }
                }
            }else{
                Toast.makeText(month_addActivity.this,"시간을 다시 설정하세요.",Toast.LENGTH_SHORT).show();
            }
        } else if (id == 16908332) {//back
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public void restore() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {

            for (int i = 0; i < 48; i++) {
                time_use[i] = pref.getInt("day"+time+"time_use_" + i, 0);

            }
            for (int i = 0; i < 100; i++) {//다이얼로그용
                color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템

            }
        }
    }

    public void save(){
        final int tp_type = choice_end_time - choice_start_time;
        int save_count = 0;
        for(int i = choice_start_time; i< choice_end_time+1; i++){
            if(time_use[i] == 1){
                save_count++;
            }
        }
        if(save_count == 0){
            new AlertDialog.Builder(month_addActivity.this).setTitle("알림").setMessage("추가하시겠습니까").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("day"+time, 1);//월간설정이 있다
                    editor.putInt("day"+time+"time_type_" + choice_start_time, tp_type + 1);//간격 설정
                    editor.putString("day"+time+"time_body_" + choice_start_time, input_body.getText().toString());//내용 넣기
                    editor.putInt("day"+time+"time_color_" + choice_start_time, color_array[dialog_position]);
                    editor.putString("day"+time+"time_category_" + choice_start_time, category_array[dialog_position]);
                    if(choice_start_time<20) {
                        if (choice_start_time % 2 == 0) {
                            editor.putString("day" + time + "time_start_" + choice_start_time, "0" + choice_start_time/2 + ":00");
                        }else{
                            editor.putString("day" + time + "time_start_" + choice_start_time, "0" + choice_start_time/2 + ":30");
                        }
                    }else{
                        if (choice_start_time % 2 == 0) {
                            editor.putString("day" + time + "time_start_" + choice_start_time, choice_start_time/2 + ":00");
                        }else{
                            editor.putString("day" + time + "time_start_" + choice_start_time, choice_start_time/2 + ":30");
                        }
                    }
                    if(choice_end_time<19) {
                        if (choice_end_time % 2 == 0) {
                            editor.putString("day" + time + "time_end_" + choice_start_time, "0" + choice_end_time/2 + ":30");
                        }else{
                            editor.putString("day" + time + "time_end_" + choice_start_time, "0" + (choice_end_time/2 + 1) + ":00");
                        }
                    }else{
                        if (choice_end_time % 2 == 0) {
                            editor.putString("day" + time + "time_end_" + choice_start_time, choice_end_time/2 + ":30");
                        }else{
                            editor.putString("day" + time + "time_end_" + choice_start_time, (choice_end_time/2 + 1) + ":00");
                        }
                    }
                    for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기//14:00 /14:30 이면 tp_type = 0이다
                        editor.putInt("day"+time+"time_use_" + i, 1);//사용중이다
                    }
                    editor.commit();
                    finish();
                }
            }).show();

        }else{
            //사용중인 시간이 있습니다
            Toast.makeText(month_addActivity.this,"중복",Toast.LENGTH_SHORT).show();
        }
    }
}
