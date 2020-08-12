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
    private int time, type, week;
    private int choice_start_time, choice_end_time, choice_week;
    private String str_time;
    private String body;
    private ArrayAdapter<CharSequence> week_adapter;
    private TextInputEditText input_body;
    private TextView week_t, category_t;
    private int[] time_type_1, time_type_2, time_type_3, time_type_4, time_type_5, time_type_6, time_type_7, time_type;
    private int[] time_use_1, time_use_2, time_use_3, time_use_4, time_use_5, time_use_6, time_use_7, time_use;
    private int[] time_stack_type_1, time_stack_type_2, time_stack_type_3, time_stack_type_4, time_stack_type_5, time_stack_type_6, time_stack_type_7, time_stack_type;
    private int[] color_array, time_color;
    private String[] category_array, category_array_d, time_category;
    private ImageView circle;
    private GradientDrawable drawable;
    private int count_category, dialog_position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_add);

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            time = bundle.getInt("time");

        }
        Log.d("time", Integer.toString(time));


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

        circle = findViewById(R.id.week_add_circle);
        category_t = findViewById(R.id.week_add_category);
        drawable = (GradientDrawable) ContextCompat.getDrawable(month_addActivity.this, R.drawable.circle_style);
        if(time_category[time] == null){//초기값 설정
            count_category = 1;//1이면 확인버튼 못누름
            drawable.setColor(-2236963);
        }else{
            dialog_position = time;//초기값
            color_array[dialog_position] = time_color[dialog_position];//??
            category_array[dialog_position] = time_category[dialog_position];//??
            //time_?? : 시간 _array : 카테고리 인덱스
            category_t.setText(time_category[time]);
            drawable.setColor(time_color[time]);
        }
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
                R.array.time, android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(time_adapter);
        time_spinner.setSelection(time);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                str_time = (String) parent.getItemAtPosition(position);//해당 포지션 총벌칙금 선택
                String[] temp_str_time = str_time.split(":");//예) 20,000 -> [0]=20 [1]=000
                choice_time = Integer.parseInt(temp_str_time[0].substring(0,1));
                if(choice_time == 0) {//맨앞자리가0이면 자른다
                    temp_str_time[0] = temp_str_time[0].substring(1, 2);
                    choice_time = Integer.parseInt(temp_str_time[0]);
                }else{
                    choice_time = Integer.parseInt(temp_str_time[0]);
                }

                 */
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
                R.array.time, android.R.layout.simple_spinner_item);
        time_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner2.setAdapter(time_adapter2);
        time_spinner2.setSelection(time + type - 1);
        time_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                str_time = (String) parent.getItemAtPosition(position);//해당 포지션 총벌칙금 선택
                String[] temp_str_time = str_time.split(":");//예) 20,000 -> [0]=20 [1]=000
                choice_time = Integer.parseInt(temp_str_time[0].substring(0,1));
                if(choice_time == 0) {//맨앞자리가0이면 자른다
                    temp_str_time[0] = temp_str_time[0].substring(1, 2);
                    choice_time = Integer.parseInt(temp_str_time[0]);
                }else{
                    choice_time = Integer.parseInt(temp_str_time[0]);
                }

                 */
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
        menuInflater.inflate(R.menu.week_add_app_bar, menu);
        //MenuItem check_btn = menu.findItem(R.id.appbar_week_check);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 2131296335) {//check
            if(count_category == 0) {
                if (input_body.getText().toString() != null) {
                    if (input_body.getText().toString().length() > 0) {
                        if (choice_start_time > choice_end_time) {
                            Toast.makeText(month_addActivity.this, "시간을 다시설정해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            int count = 0;
                            for (int i = choice_start_time; i < choice_end_time + 1; i++) {//지정한 시간내에 작성된 시간이 있는가 확인
                                Log.d("검사시0작" + i, "ㅇㄴㄹㄴㅇㄹ");
                                if (i >= time && i < time + type) {//기존 설정값 제외하고 검사
                                    Log.d("검사시1작" + i, "ㅇㄴㄹㄴㅇㄹ");
                                } else {
                                    Log.d("검사시2작" + i, "ㅇㄴㄹㄴㅇㄹ");
                                    if (time_use[i] == 1) {
                                        Log.d("검사시3작" + i, "ㅇㄴㄹㄴㅇㄹ");
                                        count++;
                                    }
                                }
                            }
                            if (count == 0) {
                                Log.d("피니시", "ㅇㄴㄹㄴㅇㄹ");
                                save();
                                finish();
                            } else {
                                Log.d("토스트", "ㅇㄴㄹㄴㅇㄹ");
                                Toast.makeText(month_addActivity.this, "중복", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(month_addActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(month_addActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(month_addActivity.this, "카테고리를 선택하세요", Toast.LENGTH_SHORT).show();
            }
        } else if (id == 16908332) {//back
            finish();
        } else if (id == 2131296336) {//삭제
            delect();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delect() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            //Log.d("초이스1i"+i,Integer.toString(i));
            //use타입 말고는 time번째 제외하고는 값이 비어있지만 혹시 모르니 모든 변수 초기화
            editor.putInt("time_use_" + week + i, 0);
            editor.putInt("time_type_" + week + i, 1);
            editor.putString("body_" + week + i, null);
            editor.putString("time_category_"+week+i,null);
            editor.putInt("time_color_"+week+i,0);
        }
        editor.commit();
    }

    public void save() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (week == 1) {//굳이 조건문을 할필요는 없는것 같지만 만일을 위해서
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                //Log.d("초이스1i"+i,Integer.toString(i));
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            //time_type_week[choice_start_time]
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            //Log.d("body_"+week+"초이스"+choice_start_time,input_body.getText().toString());
            //Log.d("엔드타임",Integer.toString(choice_end_time));
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                //Log.d("초이스2i"+i,Integer.toString(i));
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
            /*
            for(int i = 0; i<48; i++){//총간격 다시 설정
                if(time_type[i] == 0){//초기값설정
                    time_type[i] = 1;
                }
                if(i>0){
                    time_stack_type[i] += time_type[i] + time_stack_type[i-1];
                }else {
                    time_stack_type[i] += time_type[i];
                }
                editor.putInt("time_stack_type"+week+i,time_stack_type[i]);
            }

             */
        } else if (week == 2) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            //초기화
            ////////////////////////////////
            //넣기
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 3) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 4) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 5) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 6) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 7) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_"+week+i,0);
                editor.putString("time_category_"+week+i,null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        }
        editor.commit();
    }

    public void restore() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {

            for (int i = 0; i < 48; i++) {
                time_type[i] = pref.getInt("time_type_" + week + i, 1);//해당시간 칸수
                time_use[i] = pref.getInt("time_use_" + week + i, 0);
                time_color[i] = pref.getInt("time_color_"+week+i,0);//array를 불러와 time에 저장//조건 또는 초기값으로 쓰인다
                time_category[i] = pref.getString("time_category_"+week+i,null);//array를 불러와 time에 저장
            }
            for (int i = 0; i < 100; i++) {//다이얼로그용
                color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템

            }
        }
    }
}
