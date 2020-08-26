package org.techtown.timeschedule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;

public class Stat_category extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private int[] time_type, item_add_week, item_add_day, time_phoneuse;
    private String[] time_category, category_array;
    private int[][] cate_alltime_usetime;//[z][k] z=arraycategory 인덱스 k = 0 카테고리 모든시간 k = 1 카테고리 모든 사용시간
    private RecyclerView stat_recycle;
    private LinearLayoutManager layoutManager;
    private stat_adapter adapter;
    //어뎁터
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_category);

        time_type = new int[100];
        time_category = new String[100];
        time_phoneuse = new int[100];
        item_add_day = new int[100];
        item_add_week = new int[100];
        category_array = new String[100];
        cate_alltime_usetime = new int[100][2];

        restore();

        toolbar = findViewById(R.id.appbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);

        stat_recycle = findViewById(R.id.stat_recycle);
        layoutManager = new LinearLayoutManager(Stat_category.this, LinearLayoutManager.VERTICAL, false);
        stat_recycle.setLayoutManager(layoutManager);
        adapter = new stat_adapter();
        for(int i = 0; i<100; i++){
            if(category_array[i] != null){
                if(cate_alltime_usetime[i][0] != 0){
                    int percent = (cate_alltime_usetime[i][1] * 100) / cate_alltime_usetime[i][0];
                    adapter.addItem(new statList(category_array[i], percent));
                    Log.d("점검","점검");
                }else {
                    adapter.addItem(new statList(category_array[i], 0));
                    Log.d("점검","점검");
                }
            }else{
                //i = 100;
            }
        }
        //adapter.addItem(new statList("dsfdsf",10));
        stat_recycle.setAdapter(adapter);
        /*
        for(int i = 0; i<10; i++){
            if(cate_alltime_usetime[i][0] != 0) {//0으로 나누면 오류있음
                Log.d("etet퍼센트" + i, (cate_alltime_usetime[i][1] * 100) / cate_alltime_usetime[i][0]+ "%");
                Log.d("etet총시간" + i, cate_alltime_usetime[i][0] + "");
                Log.d("etet사용시간" + i, cate_alltime_usetime[i][1] + "");
            }
        }
         */
    }

    public void restore(){
        SharedPreferences pref = getSharedPreferences("pref", Stat_category.MODE_PRIVATE);
        for (int i = 0; i < 100; i++) {
            item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
            item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
            category_array[i] = pref.getString("category_array" + i, null);//카테고리 목록
        }
        for(int t = -15; t<1; t++) {
            Log.d("statㅇ월간설정여부",Integer.toString(pref.getInt("day" + getDate0(t), 0)));
            Log.d("statㅇ조회하는날짜",getDate0(t));
            Log.d("statㅇ조회하는요일",Integer.toString(getWeek(getDate_y(t),getDate_m(t),getDate_d(t))));
            if (pref.getInt("day" + getDate0(t), 0) == 1) {//getweek(현재요일)-1 = 월요일
                //getDate가 날짜마다 더해져서 getWeek값이 유동이여도 계산이 떨어짐
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//월요일검사
                //쉐어드 불러오기 day+날짜+time1_?_i
                for (int i = 0; i < 100; i++) {
                    time_category[i] = pref.getString("day" + getDate0(t) + "time_category_" + i, null);
                    time_type[i] = pref.getInt("day" + getDate0(t) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3(t))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time1_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_category[k] = null;
                                time_type[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3(t))) {//날짜가 아예 같으면 요일도 같다
                        //Log.d("검사검사10번", "검사검사10번");
                        //cal_week1 = item_add_day[temp_index - 1];
                        for (int k = 0; k < 100; k++) {//1.getWeek(getDate_y(t),getDate_m(t),getDate_d(t)) 2.item_add_week[] 중에 선택
                            time_category[k] = pref.getString(item_add_day[temp_index - 1] + "time" + getWeek(getDate_y(t),getDate_m(t),getDate_d(t)) + "_category_" + k, null);
                            time_type[k] = pref.getInt(item_add_day[temp_index - 1] + "time" + getWeek(getDate_y(t),getDate_m(t),getDate_d(t)) +"_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == getWeek(getDate_y(t),getDate_m(t),getDate_d(t))) {
                                restore_count++;
                                //가장 최근 주간 설정(??요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                //cal_week1 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_category[k] = pref.getString(item_add_day[i] + "time" + getWeek(getDate_y(t),getDate_m(t),getDate_d(t)) + "_category_" + k, null);
                                    time_type[k] = pref.getInt(item_add_day[i] + "time" + getWeek(getDate_y(t),getDate_m(t),getDate_d(t)) + "_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if (i == 0) {
                                if (restore_count == 0) {//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_category[k] = null;
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
                        time_category[k] = null;
                        time_type[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            for(int k = 0; k<50; k++){//검사용
                if(time_category[k] != null){//널탈출하면안됨
                    Log.d("statㅇ카테고리"+k+"/"+t,time_category[k]);
                    for(int z = 0; z<100; z++) {
                        if(category_array[z] != null){
                            if (time_category[k].equals(category_array[z])){
                                Log.d("etetz의카테고리는"+z+"/"+t,time_category[k]);
                                Log.d("etetz의타입은"+z+"/"+t,time_type[k]*30+"");
                                if(t != 0) {//오늘이 아닐때
                                    cate_alltime_usetime[z][0] += time_type[k] * 30;
                                }else{//오늘일때
                                    Calendar cal = Calendar.getInstance();
                                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                                    hour = hour*2;
                                    int min = cal.get(Calendar.MINUTE);
                                    if(min >= 30){
                                        hour += 1;
                                    }
                                    Log.d("시시시간", Integer.toString(hour));
                                    Log.d("시시시간k", Integer.toString(k));
                                    if(hour>=k){//k는 조회하는 시간//현재시간을 지나지 않았을때
                                        Log.d("시시시간kk", Integer.toString(k));
                                        if(time_type[k]+k > hour+1){
                                            Log.d("시시시간kkk", Integer.toString(k));
                                            cate_alltime_usetime[z][0] += (hour+1-k) * 30;
                                        }else{
                                            Log.d("시시시간kkkk", Integer.toString(k));
                                            cate_alltime_usetime[z][0] += time_type[k] * 30;
                                        }
                                    }
                                }
                                for(int w = 0; w<time_type[k]; w++){
                                    cate_alltime_usetime[z][1] += pref.getInt("day"+getDate0(t)+"time_usephone_"+(k+w),0);//타입크기만큼 조사
                                    Log.d("etetz의사용시간은"+z+"/"+t,cate_alltime_usetime[z][1]+"/"+(k+w));
                                }
                            }
                        }else{
                            z = 100;
                        }
                    }
                }
            }
        }
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

    public int getDate_y(int iDay) {//2020
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);

        return nYear;
    }

    public int getDate_m(int iDay) {//8
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nMonth = temp.get(Calendar.MONTH) + 1;

        return nMonth;
    }

    public int getDate_d(int iDay) {//11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nDay = temp.get(Calendar.DAY_OF_MONTH);

        return nDay;
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
