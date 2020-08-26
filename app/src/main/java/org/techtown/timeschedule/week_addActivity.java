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

import java.util.Calendar;

public class week_addActivity extends AppCompatActivity {

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
    private String day;//2020.8.11 터치한 위치 날짜
    private String day2;//20200811 터치한 위치 날짜//수정할때 이 날짜로 해야된다//저장하기전에 먼저day3저장값을 덮어쓰고 수정해야된다.
    private int day3, day4;//20200811 쉐어드에서 데이터를 불러온 날짜 (단:아예없으면 0이다)//저장할때 키값 날짜가 이 규격이다(주간저장만)
    private int cal_day;//1이면 월간에서 불러온거 0이면 주간에서 불러오거나 데이터가 아예 없을때
    private int[] item_add_day, item_add_week;
    private String[] time_body;
    private String day22;
    //private TextView repeat_t;
    //private CheckBox checkBox;
    private int color;
    private String category;
    private boolean checking;
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_add);

        count_category = 0;
        time_use = new int[100];
        time_type = new int[100];
        time_stack_type = new int[100];
        time_color = new int[100];
        time_category = new String[100];
        time_body = new String[100];

        color_array = new int[100];
        category_array = new String[100];

        item_add_day = new int[100];//20200811
        item_add_week = new int[100];//1~7

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            time = bundle.getInt("time");
            type = bundle.getInt("type");
            week = bundle.getInt("week");
            body = bundle.getString("body");
            day = bundle.getString("day");//2020.08.11 터치한 위치 날짜
            day2 = bundle.getString("day2");//20200811 터치한 위치 날짜//수정할때 이 날짜로 해야된다//저장하기전에 먼저day3저장값을 덮어쓰고 수정해야된다.
            day3 = bundle.getInt("day3");//20200811 쉐어드에서 데이터를 불러온 날짜 (단:아예없으면 0이다)//저장할때 키값 날짜가 이 규격이다(주간저장만)
            day4 = bundle.getInt("day4");//2020.08.11 쉐어드에서 데이터를 불러온 날짜
            cal_day = bundle.getInt("cal_day");//1이면 월간에서 불러온거 0이면 주간에서 불러오거나 데이터가 아예 없을때
            day22 = bundle.getString("day22");
            color = bundle.getInt("color");
            category = bundle.getString("category");
            index = bundle.getInt("index");
        }
        Log.d("검사time", Integer.toString(time));
        Log.d("검사type", Integer.toString(type));
        Log.d("검사week", Integer.toString(week));
        Log.d("dayday", day);
        Log.d("dayday2", day2);
        Log.d("dayday3", Integer.toString(day3));

        //restore();
        restore2();
        //restore_new();
        /*
        if(day3 == Integer.parseInt(day2)){
            checkBox.setChecked(false);
            checking = false;
        }else{
            checkBox.setChecked(true);
            checking = true;
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking = checkBox.isChecked();
            }
        });

         */
        for (int i = 0; i < 100; i++) {
            if (category_array[i] == null) {
                category_array_d = new String[i];
                i = 100;
            }
        }
        for (int i = 0; i < 100; i++) {
            if (category_array[i] == null) {
                i = 100;
            } else {
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
        if (body != null) {
            input_body.setText(body);
        }

        week_t = findViewById(R.id.add_week_t);
        if (week == 1) {
            week_t.setText("월");
        } else if (week == 2) {
            week_t.setText("화");
        } else if (week == 3) {
            week_t.setText("수");
        } else if (week == 4) {
            week_t.setText("목");
        } else if (week == 5) {
            week_t.setText("금");
        } else if (week == 6) {
            week_t.setText("토");
        } else if (week == 7) {
            week_t.setText("일");
        }

        circle = findViewById(R.id.week_add_circle);
        category_t = findViewById(R.id.week_add_category);
        drawable = (GradientDrawable) ContextCompat.getDrawable(week_addActivity.this, R.drawable.circle_style);
        //여기 다음 조건문 categort,color 전부 인텐트로 받아오기

        if (time_category[time] == null) {//초기값 설정
            count_category = 1;//1이면 확인버튼 못누름
            drawable.setColor(-2236963);
        } else {
            dialog_position = time;//초기값
            color_array[dialog_position] = time_color[dialog_position];//??
            category_array[dialog_position] = time_category[dialog_position];//??
            //time_?? : 시간 _array : 카테고리 인덱스
            category_t.setText(time_category[time]);
            drawable.setColor(time_color[time]);
        }

/*
        dialog_position = time;//초기값
        color_array[dialog_position] = color;
        category_array[dialog_position] = category;
        category_t.setText(category);
        drawable.setColor(color);


 */


        circle.setImageDrawable(drawable);
        category_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(week_addActivity.this).setTitle("선택").setItems(category_array_d, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count_category = 0;
                        dialog_position = which;
                        category_t.setText(category_array[which]);
                        drawable.setColor(color_array[which]);
                        circle.setImageDrawable(drawable);
                    }
                }).setNegativeButton("닫기", null).show();
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
        MenuItem check_btn = menu.findItem(R.id.appbar_week_add_check);
        MenuItem delect_btn = menu.findItem(R.id.appbar_week_add_delect);
        if (cal_day == 1) {//월간달력 일때
            check_btn.setVisible(false);//확인(수정) 버튼 숨기기
            delect_btn.setVisible(false);
        }
        Log.d("day2/현재", day22 + "/" + getDate3(0));

        if (Integer.parseInt(day22) < Integer.parseInt(getDate3(0))) {//터치한 날짜가 과거일때
            check_btn.setVisible(false);//확인(수정) 버튼 숨기기
            delect_btn.setVisible(false);
        }


        if (body == null) {//작성되지않은 시간표일때
            delect_btn.setVisible(false);//삭제버튼 숨기기
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 2131296336) {//check2131296336

            if (count_category == 0) {
                if (input_body.getText().toString() != null) {
                    if (input_body.getText().toString().length() > 0) {
                        if (choice_start_time > choice_end_time) {
                            Toast.makeText(week_addActivity.this, "시간을 다시설정해주세요", Toast.LENGTH_SHORT).show();
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
                                new AlertDialog.Builder(week_addActivity.this).setTitle("알림").setMessage("저장하시겠습니까").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("피니시", "ㅇㄴㄹㄴㅇㄹ");
                                        //save();
                                        Log.d("데이2데이3", day2 + "ㅇ" + day3);
                                        if (Integer.parseInt(day2) == day3) {
                                            save2();
                                        } else {//day3가 과거거나 아예0일경우
                                            save3();
                                        }
                                        finish();
                                    }
                                }).show();
                            } else {
                                Log.d("토스트", "ㅇㄴㄹㄴㅇㄹ");
                                Toast.makeText(week_addActivity.this, "중복", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(week_addActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(week_addActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(week_addActivity.this, "카테고리를 선택하세요", Toast.LENGTH_SHORT).show();
            }


            /*
            if(checking == false){//반복안할때
                if(day3 == Integer.parseInt(day2)){
                    save_today_main();//반복의 주체일때
                }else{
                    save_today_sub();//반복의 주체가 아닐때
                }
            }else{//반복할때
                //다이얼로그 1.전체반복일정수정 2.이후 일정 모두 수정
                save_repeat();//이후 일정 모두 수정
                //save_repeat_origin();//전체 반복 일정 수정
            }

             */
        } else if (id == 16908332) {//back16908332
            finish();
        } else if (id == 2131296337) {//삭제2131296337
            //delect();
            new AlertDialog.Builder(week_addActivity.this).setTitle("알림").setMessage("삭제하시겠습니까").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delect2();
                    finish();
                }
            }).show();

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
            editor.putString("time_category_" + week + i, null);
            editor.putInt("time_color_" + week + i, 0);
        }
        editor.commit();
    }

    public void delect2() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            //Log.d("초이스1i"+i,Integer.toString(i));
            //use타입 말고는 time번째 제외하고는 값이 비어있지만 혹시 모르니 모든 변수 초기화
            editor.putInt(day3 + "time" + week + "_use_" + i, 0);
            editor.putInt(day3 + "time" + week + "_type_" + i, 1);
            editor.putString(day3 + "time" + week + "_body_" + i, null);
            editor.putString(day3 + "time" + week + "_category_" + i, null);
            editor.putInt(day3 + "time" + week + "_color_" + i, 0);
        }
        editor.commit();
    }

    public void restore2() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (cal_day == 0) {//월간에서 안불렀을때
            if (day3 == 0) {//(프래그먼트)쉐어드에서 데이터를 불러오지않았을 경우
                //다이얼로그 아이템만 불러온다
                for (int i = 0; i < 100; i++) {
                    color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                    category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템
                    time_type[i] = 1;//얘만 초기값이1이라서 넣어줌
                    ////
                    item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
                    item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
                }
            } else {//day3 : 쉐어드에서 불러온 날짜(프래그먼트) 예)20200811
                for (int i = 0; i < 48; i++) {
                    time_type[i] = pref.getInt(day3 + "time" + week + "_type_" + i, 1);//해당시간 칸수
                    time_use[i] = pref.getInt(day3 + "time" + week + "_use_" + i, 0);
                    time_color[i] = pref.getInt(day3 + "time" + week + "_color_" + i, 0);//array를 불러와 time에 저장//조건 또는 초기값으로 쓰인다
                    time_category[i] = pref.getString(day3 + "time" + week + "_category_" + i, null);//array를 불러와 time에 저장
                    time_body[i] = pref.getString(day3 + "time" + week + "_body_" + i, null);
                }
                for (int i = 0; i < 100; i++) {//다이얼로그용
                    color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                    category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템
                    ////
                    item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
                    item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
                }
                for (int i = 0; i < 10; i++) {
                    Log.d("검사 정렬불러오는지점" + i, Integer.toString(item_add_day[i]));
                }
            }
        } else {//월간에서 불러오기

        }
    }

    public void save1() {//불러온날이 없을때 //이 경우면 무조건 item_add_day 첫항부터 0이어야 되는데 혹시 모르니
        //안씀//save3로 대체가능
        for (int i = 0; i < 100; i++) {
            if (item_add_day[i] == 0) {
                Log.d("0검사" + i, "무조건0이어야 정상임");
                i = 100;
            }
        }
        //item_add_day[0] = Integer.parseInt(day2);//save3에서 추가함
        //item_add_week[0] = week;//save3에서 추가함
        save3();
    }

    public void save2() {//불러온날짜랑 수정하는 날짜가 같을때
        Log.d("같은날저장", "ㄴㄹㄴ");
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int tp_type = choice_end_time - choice_start_time;
        //쉐어드에서 불러온 날짜에 그대로 다시 저장
        for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            editor.putInt(day3 + "time" + week + "_use_" + i, 0);
            editor.putInt(day3 + "time" + week + "_type_" + i, 1);
            editor.putString(day3 + "time" + week + "_body_" + i, null);
            editor.putInt(day3 + "time" + week + "_color_" + i, 0);
            editor.putString(day3 + "time" + week + "_category_" + i, null);
            Log.d("같은날저장초기화" + i, "ㄴㄹㄴ");
        }
        time_type[choice_start_time] = tp_type + 1;//간격 설정
        editor.putInt(day3 + "time" + week + "_type_" + choice_start_time, tp_type + 1);//간격 설정
        editor.putString(day3 + "time" + week + "_body_" + choice_start_time, input_body.getText().toString());//내용 넣기
        editor.putInt(day3 + "time" + week + "_color_" + choice_start_time, color_array[dialog_position]);
        editor.putString(day3 + "time" + week + "_category_" + choice_start_time, category_array[dialog_position]);
        for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
            editor.putInt(day3 + "time" + week + "_use_" + i, 1);//사용중이다
        }
        editor.commit();
    }

    public void save3() {//불러온날짜랑 수정하는 날짜가 다를때//다른 이유 현재 선택한 날짜가 저장이 안되어있기 때문 그래서 초기화 없이 덮어씌워도 문제없음
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for (int i = 0; i < 48; i++) {//쉐어드에서 불러온 날짜의 데이터를 새로운 날짜로 저장
            editor.putInt(day2 + "time" + week + "_use_" + i, time_use[i]);
            editor.putInt(day2 + "time" + week + "_type_" + i, time_type[i]);
            editor.putString(day2 + "time" + week + "_body_" + i, time_body[i]);
            editor.putInt(day2 + "time" + week + "_color_" + i, time_color[i]);
            editor.putString(day2 + "time" + week + "_category_" + i, time_category[i]);
        }
        int temp_save_index = 0;
        for (int i = 0; i < 10; i++) {
            Log.d("검사 정렬넣기전" + i, Integer.toString(item_add_day[i]));
        }
        int check_count = 0;
        for (int i = 0; i < 100; i++) {
            if (item_add_day[i] == Integer.parseInt(day2)) {
                check_count++;
                i = 100;
            }
        }
        if (check_count == 0) {//동일한 날짜가 들어가있으면 저장을 안함
            for (int i = 0; i < 100; i++) {
                if (item_add_day[i] == 0) {
                    item_add_day[i] = Integer.parseInt(day2);
                    item_add_week[i] = week;
                    temp_save_index = i;
                    i = 100;
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Log.d("검사 정렬전" + i, Integer.toString(item_add_day[i]));
        }
        for (int i = 0; i < temp_save_index; i++) {//작은순으로정렬
            for (int k = i; k < temp_save_index; k++) {
                if (item_add_day[k] > item_add_day[k + 1]) {
                    int s_temp1 = item_add_day[k];
                    int s_temp2 = item_add_week[k];
                    item_add_day[k] = item_add_day[k + 1];
                    item_add_week[k] = item_add_week[k + 1];
                    item_add_day[k + 1] = s_temp1;
                    item_add_week[k + 1] = s_temp2;
                    Log.d("검사 정렬중" + i, Integer.toString(item_add_day[i]));
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Log.d("검사 정렬후" + i, Integer.toString(item_add_day[i]));
        }
        for (int i = 0; i < 100; i++) {
            //Log.d("검사 정렬잘됐는지"+i,Integer.toString(item_add_day[i]));
            editor.putInt("item_add_day_" + i, item_add_day[i]);
            editor.putInt("item_add_week_" + i, item_add_week[i]);
        }
        editor.commit();
        save3_next();
    }
//getDate0(0) <- 이거 선택한 날짜로 난중에 수정 getDate(i*7)도 포함
    public void restore_new(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        //if(pref.getInt("day"+getDate0(0)+time,0) == 1){
        //}
        for (int i = 0; i < 100; i++) {//다이얼로그용
            color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
            category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템

        }
    }
    public void save_today_sub() {//만약반복상태이면 반복의 주체가 아닐때(만약 반복의 주체면 save_today_main메소드 끝나고 나와야된다)//당일만 수정//이후 반복x//day터치day4쉐어드
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("day" + day+choice_start_time, 1);//불러올때 1이면 당일저장값 확인
        int tp_type = choice_end_time - choice_start_time;
        for (int i = time; i < time + type; i++) {//기존 쉐어드 초기화
            editor.putInt("day" + day + "time_use_" + i, 0);
            editor.putInt("day" + day + "time_type_" + i, 1);
            editor.putInt("day" + day + "time_color_" + i, 0);
            editor.putString("day" + day + "time_body_" + i, null);
            editor.putString("day" + day + "time_category_" + i, null);
        }
        for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
            editor.putInt("day" + day + "time_use_" + i, 1);
        }
        editor.putInt("day" + day + "time_type_" + choice_start_time, tp_type + 1);
        editor.putInt("day" + day + "time_color_" + choice_start_time, color_array[dialog_position]);
        editor.putString("day" + day + "time_body_" + choice_start_time, input_body.getText().toString());
        editor.putString("day" + day + "time_category_" + choice_start_time, category_array[dialog_position]);
        editor.commit();
    }

    /////////////불러온날짜가 선택한날짜냐 아니냐로 위 아래 선택(반복상태일때만)
    public void save_today_main() {//반복상태일때//당일만 수정//단 반복의 주체일때//결과 주체가 사라지거나 다음주로 밀린다//day터치day4쉐어드
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int tp_type = choice_end_time - choice_start_time;
        int[] repeat_type = new int[100];
        int[] repeat_day = new int[100];
        for (int i = 0; i < 100; i++) {
            repeat_day[i] = pref.getInt("day" + week + "repeat_day_" + time + "_" + i, 0);
            repeat_type[i] = pref.getInt("day" + week + "repeat_type_" + time + "_" + i, 1);
        }
        int index_start_day = 0;
        for (int i = 0; i < 100; i++) {
            if (repeat_day[i] == Integer.parseInt(day)) {
                index_start_day = i;
                i = 100;
            }
        }
        int index_end_day = 0;
        for (int i = 0; i < 100; i++) {
            if (repeat_day[index_start_day + 1] <= Integer.parseInt(getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i))) {//다음 저장 주체를 넘었을때//Integer.parseInt(getDate0(7 * i)
                index_end_day = i;
                i = 100;
            }
        }
        int save_check = 0;
        for (int i = 1; i < index_end_day; i++) {//getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) = getDate0(7*i)일 경우만
            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i), 0) == 0) {//7*i일후 시간표가 저장되지 않았을때//오늘 시간표를 다음주에 저장 그리고 다음주를 주체로 변경한다
                save_check++;
                editor.putInt("day" + day+time, 0);//이전껄 다음주로 옮기는 작업이니 start_time이 아닌 time을 써야된다.
                editor.putInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i)+time, 1);//불러올때 1이면 당일저장값 확인
                editor.putInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) + "time_type_" + time, pref.getInt("day" + getDate0(0) + "time_type_" + time, 1));
                editor.putInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) + "time_color_" + time, pref.getInt("day" + getDate0(7 * i) + "time_color_" + time, 0));
                editor.putString("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) + "time_body_" + time, pref.getString("day" + getDate0(7 * i) + "time_body_" + time, null));
                editor.putString("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) + "time_category_" + time, pref.getString("day" + getDate0(7 * i) + "time_category_" + time, null));
                for (int k = time; k < time + type; k++) {
                    editor.putInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i) + "time_use_" + k, 1);
                }

                int index_range = 0;
                for (int k = 0; k < 100; k++) {
                    if (repeat_day[k] == 0) {//새로운 위치에 저장
                        repeat_day[k] = Integer.parseInt(getDate0((index-1500) * 7 - (getWeek() - 3) + 7 * i));//빈 자리에 새로운 날짜 저장
                        repeat_type[k] = type;
                        index_range = k;
                        k = 100;
                    }
                }
                for (int k = 0; k < 100; k++) {
                    if (repeat_day[k] == Integer.parseInt(day)) {//현재 저장날짜 찾아 초기화
                        repeat_day[k] = 0;
                        repeat_type[k] = 1;
                        k = 100;
                    }
                }
                for (int t = 0; t < index_range; t++) {//작은순으로정렬
                    for (int k = t; k < index_range; k++) {
                        if (repeat_day[k] > repeat_day[k + 1]) {
                            int s_temp1 = repeat_day[k];
                            int s_temp2 = repeat_type[k];
                            repeat_day[k] = repeat_day[k + 1];
                            repeat_type[k] = repeat_type[k + 1];
                            repeat_day[k + 1] = s_temp1;
                            repeat_type[k + 1] = s_temp2;
                            Log.d("검사 정렬중" + t, Integer.toString(repeat_day[t]));
                        }
                    }
                }
            }
            //반복설정 삭제후 반복 주체를 다음주로 옮김
            i = 100;

        }
        if (save_check == 0) {//주체를 더이상 옮기때까없어 반복설정이 사라짐
            editor.putInt("day" + day+time, 1);//불러올때 1이면 당일저장값 확인
            int index_range = 0;
            for (int k = 0; k < 100; k++) {
                if (repeat_day[k] == 0) {
                    index_range = k;
                    k = 100;
                }
            }
            for (int k = 0; k < 100; k++) {
                if (repeat_day[k] == Integer.parseInt(day)) {
                    repeat_day[k] = 0;
                    repeat_type[k] = 1;
                    k = 100;
                }
            }
            for (int t = 0; t < index_range; t++) {//작은순으로정렬
                for (int k = t; k < index_range; k++) {
                    if (repeat_day[k] > repeat_day[k + 1]) {
                        int s_temp1 = repeat_day[k];
                        int s_temp2 = repeat_type[k];
                        repeat_day[k] = repeat_day[k + 1];
                        repeat_type[k] = repeat_type[k + 1];
                        repeat_day[k + 1] = s_temp1;
                        repeat_type[k + 1] = s_temp2;
                        Log.d("검사 정렬중1" + t, Integer.toString(repeat_day[t]));
                    }
                }
            }
        }
        editor.commit();
        //반복설정 삭제후 다음주로 옮기거나 말거나//이전작업
        ////////////////////////////////////////////////
        //현재날짜 저장//저장작업
        save_today_sub();//save_repeat()안쓰는 이유: 반복의 주체인데 반복설정 자체가 필요없다
    }

    public void save_repeat() {//반복 설정하기//현재 반복상태여부는 상관없음//day터치day4쉐어드
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("day" + day +time, 0);
        editor.putInt("day" + day +choice_start_time, 1);//불러올때 1이면 당일저장값 확인
        int tp_type = choice_end_time - choice_start_time;
        for (int i = time; i < time + type; i++) {//쉐어드에서 불러온 날짜의 데이터를 새로운 날짜로 저장
            editor.putInt("day" + day + "time_use_" + i, 0);
            editor.putInt("day" + day + "time_type_" + i, 1);
            editor.putInt("day" + day + "time_color_" + i, 0);
            editor.putString("day" + day + "time_body_" + i, null);
            editor.putString("day" + day + "time_category_" + i, null);
        }
        for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
            editor.putInt("day" + day + "time_use_" + i, 1);
        }
        editor.putInt("day" + day + "time_type_" + choice_start_time, tp_type + 1);
        editor.putInt("day" + day + "time_color_" + choice_start_time, color_array[dialog_position]);
        editor.putString("day" + day + "time_body_" + choice_start_time, input_body.getText().toString());
        editor.putString("day" + day + "time_category_" + choice_start_time, category_array[dialog_position]);
        int[] repeat_type = new int[100];
        int[] repeat_day = new int[100];
        for (int i = 0; i < 100; i++) {
            repeat_day[i] = pref.getInt("day" + week + "repeat_day_" + time + "_" + i, 0);
            repeat_type[i] = pref.getInt("day" + week + "repeat_type_" + time + "_" + i, 1);
        }
        int index_range = 0;
        for (int k = 0; k < 100; k++) {
            if((repeat_day[k] == Integer.parseInt(day)) == false) {
                if (repeat_day[k] == 0) {//새로운 위치에 저장
                    repeat_day[k] = Integer.parseInt(getDate0(0));//빈 자리에 새로운 날짜 저장
                    repeat_type[k] = tp_type + 1;
                    index_range = k;
                    k = 100;
                }
            }else{//반복의 주체일경우 저장도 안하고 정렬도 안한다

            }
        }
        for (int t = 0; t < index_range; t++) {//작은순으로정렬
            for (int k = t; k < index_range; k++) {
                if (repeat_day[k] > repeat_day[k + 1]) {
                    int s_temp1 = repeat_day[k];
                    int s_temp2 = repeat_type[k];
                    repeat_day[k] = repeat_day[k + 1];
                    repeat_type[k] = repeat_type[k + 1];
                    repeat_day[k + 1] = s_temp1;
                    repeat_type[k + 1] = s_temp2;
                    Log.d("검사 정렬중" + t, Integer.toString(repeat_day[t]));
                }
            }
        }
        editor.commit();
    }

    public void save_repeat_origin() {//반복 설정하기//원본수정//day터치day4쉐어드
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("day" + day4 +time, 0);
        editor.putInt("day" + day4 +choice_start_time, 1);//불러올때 1이면 당일저장값 확인
        int tp_type = choice_end_time - choice_start_time;
        for (int i = time; i < time + type; i++) {//쉐어드에서 불러온 날짜의 데이터를 새로운 날짜로 저장
            editor.putInt("day" + day4 + "time_use_" + i, 0);
            editor.putInt("day" + day4 + "time_type_" + i, 1);
            editor.putInt("day" + day4 + "time_color_" + i, 0);
            editor.putString("day" + day4 + "time_body_" + i, null);
            editor.putString("day" + day4 + "time_category_" + i, null);
        }
        for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
            editor.putInt("day" + day4 + "time_use_" + i, 1);
        }
        editor.putInt("day" + day4 + "time_type_" + choice_start_time, tp_type + 1);
        editor.putInt("day" + day4 + "time_color_" + choice_start_time, color_array[dialog_position]);
        editor.putString("day" + day4 + "time_body_" + choice_start_time, input_body.getText().toString());
        editor.putString("day" + day4 + "time_category_" + choice_start_time, category_array[dialog_position]);
        int[] repeat_type = new int[100];
        int[] repeat_day = new int[100];
        for (int i = 0; i < 100; i++) {
            repeat_day[i] = pref.getInt("day" + week + "repeat_day_" + time + "_" + i, 0);
            repeat_type[i] = pref.getInt("day" + week + "repeat_type_" + time + "_" + i, 1);
        }
        int index_range = 0;
        for (int k = 0; k < 100; k++) {
            if((repeat_day[k] == day4) == false) {
                if (repeat_day[k] == 0) {//새로운 위치에 저장
                    repeat_day[k] = day4;//빈 자리에 새로운 날짜 저장
                    repeat_type[k] = tp_type + 1;
                    index_range = k;
                    k = 100;
                }
            }else{//반복의 주체일경우 저장도 안하고 정렬도 안한다

            }
        }
        for (int t = 0; t < index_range; t++) {//작은순으로정렬
            for (int k = t; k < index_range; k++) {
                if (repeat_day[k] > repeat_day[k + 1]) {
                    int s_temp1 = repeat_day[k];
                    int s_temp2 = repeat_type[k];
                    repeat_day[k] = repeat_day[k + 1];
                    repeat_type[k] = repeat_type[k + 1];
                    repeat_day[k + 1] = s_temp1;
                    repeat_type[k + 1] = s_temp2;
                    Log.d("검사 정렬중" + t, Integer.toString(repeat_day[t]));
                }
            }
        }
        editor.commit();
    }

    public void save3_next() {//새로운 날짜에 수정
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int tp_type = choice_end_time - choice_start_time;
        //쉐어드에서 불러온 날짜에 그대로 다시 저장
        for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            editor.putInt(day2 + "time" + week + "_use_" + i, 0);
            editor.putInt(day2 + "time" + week + "_type_" + i, 1);
            editor.putString(day2 + "time" + week + "_body_" + i, null);
            editor.putInt(day2 + "time" + week + "_color_" + i, 0);
            editor.putString(day2 + "time" + week + "_category_" + i, null);
        }
        time_type[choice_start_time] = tp_type + 1;//간격 설정
        editor.putInt(day2 + "time" + week + "_type_" + choice_start_time, tp_type + 1);//간격 설정
        editor.putString(day2 + "time" + week + "_body_" + choice_start_time, input_body.getText().toString());//내용 넣기
        editor.putInt(day2 + "time" + week + "_color_" + choice_start_time, color_array[dialog_position]);
        editor.putString(day2 + "time" + week + "_category_" + choice_start_time, category_array[dialog_position]);
        for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
            editor.putInt(day2 + "time" + week + "_use_" + i, 1);//사용중이다
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
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
            }
            time_type[choice_start_time] = tp_type + 1;//간격 설정
            editor.putInt("time_type_" + week + choice_start_time, tp_type + 1);//간격 설정
            editor.putString("body_" + week + choice_start_time, input_body.getText().toString());//내용 넣기
            editor.putInt("time_color_" + week + choice_start_time, color_array[dialog_position]);
            editor.putString("time_category_" + week + choice_start_time, category_array[dialog_position]);
            for (int i = choice_start_time; i < choice_start_time + tp_type + 1; i++) {//use초기화후 넣기
                editor.putInt("time_use_" + week + i, 1);//사용중이다
            }
        } else if (week == 2) {
            editor.putInt("first_restore", 1);//초기값 변경
            int tp_type = choice_end_time - choice_start_time;

            for (int i = time; i < time + type; i++) {//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
                editor.putInt("time_use_" + week + i, 0);
                editor.putInt("time_type_" + week + i, 1);
                editor.putString("body_" + week + i, null);
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                editor.putInt("time_color_" + week + i, 0);
                editor.putString("time_category_" + week + i, null);
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
                time_color[i] = pref.getInt("time_color_" + week + i, 0);//array를 불러와 time에 저장//조건 또는 초기값으로 쓰인다
                time_category[i] = pref.getString("time_category_" + week + i, null);//array를 불러와 time에 저장
            }
            for (int i = 0; i < 100; i++) {//다이얼로그용
                color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                category_array[i] = pref.getString("category_array" + i, null);//time저장용도, 다이얼로그 아이템

            }
        }
    }

    public String getDate(int iDay) {//2020.8.11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);


        sbDate.append(nYear);
        sbDate.append(".");
        //if (nMonth < 10)
        //sbDate.append("0");
        sbDate.append(nMonth);
        //if (nDay < 10)
        //sbDate.append("0");
        sbDate.append(".");
        sbDate.append(nDay);


        return sbDate.toString();
    }

    public String getDate2(int iDay) {//08/11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);


        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        sbDate.append("/");
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);


        return sbDate.toString();
    }

    public String getDate3(int iDay) {//20200811
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);


        sbDate.append(nYear);
        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);


        return sbDate.toString();
    }

    public int getWeek() {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        String m_week = null;
        if (day_of_week == 1) {
            m_week = "일요일";
            day_of_week += 6;
        } else if (day_of_week == 2) {
            m_week = "월요일";
            day_of_week -= 1;
        } else if (day_of_week == 3) {
            m_week = "화요일";
            day_of_week -= 1;
        } else if (day_of_week == 4) {
            m_week = "수요일";
            day_of_week -= 1;
        } else if (day_of_week == 5) {
            m_week = "목요일";
            day_of_week -= 1;
        } else if (day_of_week == 6) {
            m_week = "금요일";
            day_of_week -= 1;
        } else if (day_of_week == 7) {
            m_week = "토요일";
            day_of_week -= 1;
        }
        return day_of_week;
    }

    public String getDate0(int iDay) {//2020.08.11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);


        sbDate.append(nYear);
        sbDate.append(".");
        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(".");
        sbDate.append(nDay);


        return sbDate.toString();
    }

}
