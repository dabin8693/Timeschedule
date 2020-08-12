package org.techtown.timeschedule;

import android.content.Intent;
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

    public frag_month(toolbar_callback toolbar_callback){
        this.toolbar_callback = toolbar_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_month,container,false);
        calendarView = view.findViewById(R.id.calendar);
        month_day_t = view.findViewById(R.id.month_day_t);
        cal = Calendar.getInstance();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //캘린더 위젯 날짜 클릭시 발동
                SimpleDateFormat sdate = new SimpleDateFormat("yyyy.MM.dd");
                Date date = null;
                try {
                    date = sdate.parse(year+"."+month+1+"."+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(date);
                String dayofweek = week_cal(cal.get(Calendar.DAY_OF_WEEK));
                month_day_t.setText(dayOfMonth+"."+dayofweek);
                toolbar_callback.calenderchange(year, month+1,dayOfMonth);//액티비티를수정
            }
        });

        recyclerView = view.findViewById(R.id.month_recycle);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new month_adapter();
        adapter.addItem(new monthList("제목1","카테고리1",-2236963,"시간1"));
        adapter.addItem(new monthList("제목2","카테고리2",-2236963,"시간2"));
        adapter.addItem(new monthList("제목3","카테고리3",-2236963,"시간3"));
        adapter.addItem(new monthList("제목1","카테고리1",-2236963,"시간1"));
        adapter.addItem(new monthList("제목2","카테고리2",-2236963,"시간2"));
        adapter.addItem(new monthList("제목3","카테고리3",-2236963,"시간3"));
        adapter.addItem(new monthList("제목1","카테고리1",-2236963,"시간1"));
        adapter.addItem(new monthList("제목2","카테고리2",-2236963,"시간2"));
        adapter.addItem(new monthList("제목3","카테고리3",-2236963,"시간3"));

        recyclerView.setAdapter(adapter);
        category_float = view.findViewById(R.id.month_float);
        category_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(),Category_addActivity.class);
                //intent.putExtra("plus",1);
                //startActivity(intent);
            }
        });

        return view;
    }

    public void frag_calender_change(int year, int month, int day){//액티비티에서 프래그먼트를 수정
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = sdate.parse(year+"."+month+"."+day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        String dayofweek = week_cal(cal.get(Calendar.DAY_OF_WEEK));
        calendarView.setDate(date.getTime());//캘린더 날짜 변경
        month_day_t.setText(day+"."+dayofweek);

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
