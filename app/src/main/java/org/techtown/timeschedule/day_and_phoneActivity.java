package org.techtown.timeschedule;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;

public class day_and_phoneActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView body_recycle, phone_recycle, time_recycle;
    private LinearLayoutManager layoutManager;
    private timetime_adapter timetime_adapter;
    private bodybody_adapter bodybody_adapter;
    private phonephone_adapter phonephone_adapter;
    private int[] item_add_week, item_add_day, time_type;
    private String[] time_body, time_time;
    private int year_a, month_a, day_a;
    private String ymd_s, ymd_ss;
    private int ymd_i;
    private TextView day_t;
    private Button find_b;
    private StringBuffer sbDate;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_and_phone);

        item_add_day = new int[100];
        item_add_week = new int[100];
        time_body = new String[100];
        time_time = new String[100];
        time_type = new int[100];

        toolbar = findViewById(R.id.appbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //recylee 시간 width = (MainActivity.width_size_main-80)*1/7;
        //recycle width = (MainActivity.width_size_main-80)*3/7;
        //adapter 3개 array 3개
        Calendar cal = Calendar.getInstance();
        year_a = cal.get(Calendar.YEAR);
        month_a = cal.get(Calendar.MONTH) + 1;
        day_a = cal.get(Calendar.DAY_OF_MONTH);

        sbDate = new StringBuffer();
        sbDate.append(year_a);
        sbDate.append(".");
        if (month_a < 10)
            sbDate.append("0");
        sbDate.append(month_a);
        if (day_a < 10)
            sbDate.append("0");
        sbDate.append(".");
        sbDate.append(day_a);
        ymd_s = sbDate.toString();//2020.08.25

        sbDate = new StringBuffer();
        sbDate.append(year_a);
        if (month_a < 10)
            sbDate.append("0");
        sbDate.append(month_a);
        if (day_a < 10)
            sbDate.append("0");
        sbDate.append(day_a);

        ymd_ss = sbDate.toString();//20200825
        ymd_i = Integer.parseInt(ymd_ss);
        Log.d("ymd_s",ymd_s);
        Log.d("ymd_ss",ymd_ss);
        Log.d("ymd_i",Integer.toString(ymd_i));

        day_t = findViewById(R.id.dayday);
        day_t.setText(ymd_s);
        find_b = findViewById(R.id.findfind);
        find_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_DatePicker();

            }
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d("핸들러시작","ㅇㅇ");
                sbDate = new StringBuffer();
                sbDate.append(year_a);
                sbDate.append(".");
                if (month_a < 10)
                    sbDate.append("0");
                sbDate.append(month_a);
                if (day_a < 10)
                    sbDate.append("0");
                sbDate.append(".");
                sbDate.append(day_a);
                ymd_s = sbDate.toString();//2020.08.25

                sbDate = new StringBuffer();
                sbDate.append(year_a);
                if (month_a < 10)
                    sbDate.append("0");
                sbDate.append(month_a);
                if (day_a < 10)
                    sbDate.append("0");
                sbDate.append(day_a);

                ymd_ss = sbDate.toString();//20200825
                ymd_i = Integer.parseInt(ymd_ss);
                time_type = new int[100];
                time_body = new String[100];
                time_time = new String[100];
                restore();
                bodybody_adapter.resetItem();
                phonephone_adapter.resetItem();
                for(int i = 0; i<48; i++){
                    bodybody_adapter.addItem(new bodybodyList(time_body[i], time_type[i]));
                    i += time_type[i] - 1;
                }
                body_recycle.setAdapter(bodybody_adapter);

                SharedPreferences pref = getSharedPreferences("pref", day_and_phoneActivity.MODE_PRIVATE);
                phonephone_adapter = new phonephone_adapter();
                for(int i = 0; i<48; i++){
                    int timecount = 0;
                    for(int k = 0; k<time_type[i]; k++) {
                        if(pref.getInt("day" + ymd_s + "time_usephone_" + (i+k), 0) >30){
                            timecount += 30;
                        }else{
                            timecount += pref.getInt("day" + ymd_s + "time_usephone_" + (i+k), 0);
                        }
                        Log.d("i,k",i+"/"+k);
                        Log.d("timecount",timecount+"");
                        Log.d("iymd_s",ymd_s+"");
                    }
                    time_time[i] = Integer.toString(timecount);
                    phonephone_adapter.addItem(new phonephoneList(time_time[i]+"분 사용",time_type[i]));
                    i += time_type[i] - 1;
                }
                phone_recycle.setAdapter(phonephone_adapter);

                day_t.setText(ymd_s);
                Log.d("핸들러끝","ㅇㅇ");
                return false;
            }
        });
        restore();

        time_recycle = findViewById(R.id.timetime_recycle);
        body_recycle = findViewById(R.id.dayday_recycle);
        phone_recycle = findViewById(R.id.phonephone_recycle);
        layoutManager = new LinearLayoutManager(day_and_phoneActivity.this, LinearLayoutManager.VERTICAL, false);
        time_recycle.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(day_and_phoneActivity.this, LinearLayoutManager.VERTICAL, false);
        body_recycle.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(day_and_phoneActivity.this, LinearLayoutManager.VERTICAL, false);
        phone_recycle.setLayoutManager(layoutManager);

        timetime_adapter = new timetime_adapter();
        for (int i = 0; i < 24; i++) {
            timetime_adapter.addItem(new timetimeList(i + ":00\n~\n" + i + ":30"));
            timetime_adapter.addItem(new timetimeList(i + ":30\n~\n" + (i+1) + ":00"));

        }
        time_recycle.setAdapter(timetime_adapter);

        bodybody_adapter = new bodybody_adapter();
        for(int i = 0; i<48; i++){
            bodybody_adapter.addItem(new bodybodyList(time_body[i], time_type[i]));
            i += time_type[i] - 1;
        }
        body_recycle.setAdapter(bodybody_adapter);

        SharedPreferences pref = getSharedPreferences("pref", day_and_phoneActivity.MODE_PRIVATE);
        phonephone_adapter = new phonephone_adapter();
        for(int i = 0; i<48; i++){
            int timecount = 0;
            for(int k = 0; k<time_type[i]; k++) {
                if(pref.getInt("day" + ymd_s + "time_usephone_" + (i+k), 0) >30){
                    timecount += 30;
                }else{
                    timecount += pref.getInt("day" + ymd_s + "time_usephone_" + (i+k), 0);
                }
                Log.d("i,k",i+"/"+k);
                Log.d("timecount",timecount+"");
                Log.d("iymd_s",ymd_s+"");
            }
            time_time[i] = Integer.toString(timecount);
            phonephone_adapter.addItem(new phonephoneList(time_time[i]+"분 사용",time_type[i]));
            i += time_type[i] - 1;
        }
        phone_recycle.setAdapter(phonephone_adapter);


    }

    private void Dialog_DatePicker() {
        Calendar c = Calendar.getInstance();
        int c_year = c.get(Calendar.YEAR);
        int c_month = c.get(Calendar.MONTH);
        int c_day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String dateStr = year+"년 "+ (month+1) + "월 " + dayOfMonth + "일";
                year_a = year;
                month_a = month+1;
                day_a = dayOfMonth;
                Toast.makeText(day_and_phoneActivity.this,"선택한 날짜는"+dateStr,Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(0);
            }
        };

        DatePickerDialog alert = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, c_year, c_month, c_day);

        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    public int getWeek(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        String m_week = null;
        if (day_of_week == 1) {
            m_week = "일요일";
            day_of_week += 6;
        }
        else if (day_of_week == 2) {
            m_week = "월요일";
            day_of_week -= 1;
        }
        else if (day_of_week == 3) {
            m_week = "화요일";
            day_of_week -= 1;
        }
        else if (day_of_week == 4){
            m_week = "수요일";
            day_of_week -= 1;
        }
        else if (day_of_week == 5){
            m_week = "목요일";
            day_of_week -= 1;
        }
        else if (day_of_week == 6){
            m_week = "금요일";
            day_of_week -= 1;
        }
        else if (day_of_week == 7){
            m_week = "토요일";
            day_of_week -= 1;
        }
        return day_of_week;
    }

    public void restore(){
        SharedPreferences pref = getSharedPreferences("pref", day_and_phoneActivity.MODE_PRIVATE);
        for (int i = 0; i < 100; i++) {
            item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
            item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
        }
        if (pref.getInt("day" + ymd_s, 0) == 1) {//getweek(현재요일)-1 = 월요일
            //getDate가 날짜마다 더해져서 getWeek값이 유동이여도 계산이 떨어짐
            //1이면 월간설정 존재 0이면 존재하지않음
            //월간설정이 존재함//월요일검사
            //쉐어드 불러오기 day+날짜+time1_?_i
            for (int i = 0; i < 100; i++) {
                time_body[i] = pref.getString("day" + ymd_s + "time_body_" + i, null);
                time_type[i] = pref.getInt("day" + ymd_s + "time_type_" + i, 1);
            }
        } else {
            int temp_index = 0;
            for (int i = 0; i < 100; i++) {
                if (item_add_day[i] != 0) {
                    if (item_add_day[i] > ymd_i) {
                        temp_index = i;
                        i = 100;//탈출
                    }
                } else {
                    temp_index = i;
                    if (i == 0) {
                        //단 한개도 존재하지 않을때
                        //time1_??_i = 0 or null
                        for (int k = 0; k < 100; k++) {
                            time_body[k] = null;
                            time_type[k] = 1;
                        }
                    }
                    i = 100;
                }
            }
            //////////////////////////////////////////////
            if (temp_index != 0) {
                if (item_add_day[temp_index - 1] == ymd_i) {//날짜가 아예 같으면 요일도 같다
                    Log.d("검사검사10번","검사검사10번");
                    //cal_week = item_add_day[temp_index-1];
                    for (int k = 0; k < 100; k++) {
                        time_body[k] = pref.getString(item_add_day[temp_index-1] + "time"+getWeek(year_a,month_a,day_a)+"_body_" + k, null);
                        time_type[k] = pref.getInt(item_add_day[temp_index-1] + "time"+getWeek(year_a,month_a,day_a)+"_type_" + k, 1);
                    }
                } else {
                    int restore_count = 0;
                    for (int i = temp_index - 1; i >= 0; i--) {
                        if (item_add_week[i] == getWeek(year_a,month_a,day_a)) {
                            restore_count++;
                            //가장 최근 주간 설정(월요일)
                            //쉐어드에서 불러오기 날짜+time1_?_i
                            //cal_week = item_add_day[i];
                            for (int k = 0; k < 100; k++) {
                                time_body[k] = pref.getString(item_add_day[i] + "time"+getWeek(year_a,month_a,day_a)+"_body_" + k, null);
                                time_type[k] = pref.getInt(item_add_day[i] + "time"+getWeek(year_a,month_a,day_a)+"_type_" + k, 1);
                            }
                            i = 0;//탈출
                        }
                        if(i == 0){
                            if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                for (int k = 0; k < 100; k++) {
                                    time_body[k] = null;
                                    time_type[k] = 1;
                                }
                            }
                        }

                    }
                }
            } else {
                //단 한개도 존재하지 않을때
                //time1_??_i = 0 or null
                for (int k = 0; k < 100; k++) {
                    time_body[k] = null;
                    time_type[k] = 1;
                }
            }
            ////////////////////////////////////////////////////
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == 16908332){
            finish();
        }
        Log.d("id : ",Integer.toString(id));
        return super.onOptionsItemSelected(item);
    }
}
