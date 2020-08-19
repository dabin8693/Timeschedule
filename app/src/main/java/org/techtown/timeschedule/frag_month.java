package org.techtown.timeschedule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class frag_month extends Fragment {
    toolbar_callback toolbar_callback;
    private CalendarView calendarView;
    private TextView month_day_t;
    private Calendar cal;
    private FloatingActionButton category_float;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private month_adapter adapter;
    private SimpleDateFormat sdate;
    private Date date;
    private String str_time;//2020.08.16
    private String[] time_body, time_category, time_start, time_end;
    private int[] time_color, time_type;

    public frag_month(toolbar_callback toolbar_callback){
        this.toolbar_callback = toolbar_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_month,container,false);
        calendarView = view.findViewById(R.id.calendar);
        month_day_t = view.findViewById(R.id.month_day_t);

        time_body = new String[100];
        time_category = new String[100];
        time_start = new String[100];
        time_end = new String[100];
        time_color = new int[100];
        time_type = new int[100];

        sdate = new SimpleDateFormat("yyyy.MM.dd");
        date = null;

        cal = Calendar.getInstance();
        str_time = sdate.format(cal.getTime());//초기값
        Log.d("월간 시간 크리에이트",str_time);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //캘린더 위젯 날짜 클릭시 발동
                //SimpleDateFormat sdate = new SimpleDateFormat("yyyy.MM.dd");
                //Date date = null;
                try {
                    date = sdate.parse(year+"."+(month+1)+"."+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(date);
                String dayofweek = week_cal(cal.get(Calendar.DAY_OF_WEEK));
                month_day_t.setText(dayOfMonth+"."+dayofweek);
                //str_time = year+"."+dayOfMonth+"."+dayofweek;
                Log.d("월간 시간 선택",year+"."+(month+1)+"."+dayOfMonth);
                str_time = sdate.format(date);
                toolbar_callback.calenderchange(year, month+1,dayOfMonth);//액티비티를수정

                restore();
                adapter.resetItem();
                for(int i = 0; i<50; i++){
                    if(time_body[i] != null){
                        Log.d("월간 스타트시간",time_start[i]);
                        adapter.addItem(new monthList(time_body[i],time_category[i],time_color[i],time_start[i]+"~"+time_end[i]));
                    }
                }
                recyclerView.setAdapter(adapter);
            }
        });

        recyclerView = view.findViewById(R.id.month_recycle);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new month_adapter();
        adapter.setOnItemClickListener(new OnlistItemClickListener_month() {
            @Override
            public void onItemClick(month_adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), month_insertActivity.class);
                intent.putExtra("time",str_time);
                int touch_count = 0;
                int touch_index = 0;
                for(int i = 0; i<50; i++){
                    if(time_body[i] != null){
                        if(position == touch_count){
                            touch_index = i;
                        }
                        touch_count++;
                    }
                }
                intent.putExtra("start_time",touch_index);
                intent.putExtra("type",time_type[touch_index]);
                intent.putExtra("body",time_body[touch_index]);
                intent.putExtra("color",time_color[touch_index]);
                intent.putExtra("category",time_category[touch_index]);
                startActivity(intent);
            }
        });
        //adapter.addItem(new monthList("제목1","카테고리1",-2236963,"시간1"));
        //adapter.addItem(new monthList("제목2","카테고리2",-2236963,"시간2"));

        //recyclerView.setAdapter(adapter);
        category_float = view.findViewById(R.id.month_float);
        category_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),month_addActivity.class);
                intent.putExtra("time",str_time);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        restore();

        adapter.resetItem();
        for(int i = 0; i<50; i++){
            if(time_body[i] != null){
                Log.d("월간 스타트시간",time_start[i]);
                adapter.addItem(new monthList(time_body[i],time_category[i],time_color[i],time_start[i]+"~"+time_end[i]));
            }
        }
        recyclerView.setAdapter(adapter);
    }

    public void restore(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        time_body = new String[100];
        time_category = new String[100];
        time_start = new String[100];
        time_end = new String[100];
        time_color = new int[100];
        time_type = new int[100];
        Log.d("주간시간표월간확인","day" + str_time + "time_body_");
        if (pref != null) {
            if(pref.getInt("day"+str_time,0) == 1) {
                for (int i = 0; i < 100; i++) {//다이얼로그용
                    time_body[i] = pref.getString("day" + str_time + "time_body_" + i, null);
                    time_category[i] = pref.getString("day" + str_time + "time_category_" + i, null);
                    time_color[i] = pref.getInt("day" + str_time + "time_color_" + i, 0);
                    time_type[i] = pref.getInt("day" + str_time + "time_type_" + i, 1);
                    time_start[i] = pref.getString("day" + str_time + "time_start_" + i, null);
                    time_end[i] = pref.getString("day" + str_time + "time_end_" + i, null);
                }
            }

        }
    }

    public void frag_calender_change(int year, int month, int day){//액티비티에서 프래그먼트를 수정
        //SimpleDateFormat sdate = new SimpleDateFormat("yyyy.MM.dd");
        //Date date = null;
        try {
            date = sdate.parse(year+"."+month+"."+day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        String dayofweek = week_cal(cal.get(Calendar.DAY_OF_WEEK));
        calendarView.setDate(date.getTime());//캘린더 날짜 변경
        month_day_t.setText(day+"."+dayofweek);
        //str_time = year+"."+month+"."+dayofweek;
        str_time = sdate.format(date);

        restore();
        adapter.resetItem();
        for(int i = 0; i<50; i++){
            if(time_body[i] != null){
                Log.d("월간 스타트시간",time_start[i]);
                adapter.addItem(new monthList(time_body[i],time_category[i],time_color[i],time_start[i]+"~"+time_end[i]));
            }
        }
        recyclerView.setAdapter(adapter);
    }

    public String week_cal(int dayofweek){
        switch (dayofweek){
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            case 7:
                return "토";
            default:
                return null;
        }
    }

}
