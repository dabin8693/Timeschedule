package org.techtown.timeschedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class frag3_week extends Fragment {
    private OnFragmentChangedListener listener;
    private View view;
    private TextView textView;
    private RecyclerView week_recycle, day_recycle, time_recycle, monday_recycle, tuesday_recycle, wednesday_recycle;
    private RecyclerView thursday_recycle, friday_recycle, saturday_recycle, sunday_recycle;
    private LinearLayoutManager layoutManager;
    private week_adapter week_adapter;
    private week_day_adapter day_adapter;
    private week_time_adapter time_adapter;
    private week_body_adapter body_adapter_1, body_adapter_2, body_adapter_3, body_adapter_4, body_adapter_5, body_adapter_6, body_adapter_7;
    private Spinner week_spinner, start_time_spinner, end_time_spinner;
    private Button search_button;
    private int[] item_add_day, item_add_week;
    private int[] time_color1, time_color2, time_color3, time_color4, time_color5, time_color6, time_color7;
    private int[] time_type1, time_type2, time_type3, time_type4, time_type5, time_type6, time_type7;
    private String[] time_category1, time_category2, time_category3, time_category4, time_category5, time_category6, time_category7;
    private String[] time_body1, time_body2, time_body3, time_body4, time_body5, time_body6, time_body7;
    private int index;
    private int temp_start_index, temp_time_type1;
    private int cal_day1, cal_day2, cal_day3, cal_day4, cal_day5, cal_day6, cal_day7;//월간시간표에서 가져왔는지 가져왔으면 = 1
    private int cal_week1, cal_week2, cal_week3, cal_week4, cal_week5, cal_week6, cal_week7;//주간시간표에서 가져온 날짜//20200811
    private int start_spinner_position, end_spinner_position, week_position;

    public frag3_week(OnFragmentChangedListener listener, int index){
        this.listener =listener;
        this.index = index;//page position
    }

    public static frag3_week newInstance(OnFragmentChangedListener listener, int index){
        frag3_week frag3_week = new frag3_week(listener, index);
        return frag3_week;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("creat3","loglog");
        view = inflater.inflate(R.layout.frag_week3,container,false);
        //index -= 1500;
        item_add_day = new int[100];//20200811
        item_add_week = new int[100];//1~7
        time_color1 = new int[100];
        time_color2 = new int[100];
        time_color3 = new int[100];
        time_color4 = new int[100];
        time_color5 = new int[100];
        time_color6 = new int[100];
        time_color7 = new int[100];
        time_type1 = new int[100];
        time_type2 = new int[100];
        time_type3 = new int[100];
        time_type4 = new int[100];
        time_type5 = new int[100];
        time_type6 = new int[100];
        time_type7 = new int[100];
        time_category1 = new String[100];
        time_category2 = new String[100];
        time_category3 = new String[100];
        time_category4 = new String[100];
        time_category5 = new String[100];
        time_category6 = new String[100];
        time_category7 = new String[100];
        time_body1 = new String[100];
        time_body2 = new String[100];
        time_body3 = new String[100];
        time_body4 = new String[100];
        time_body5 = new String[100];
        time_body6 = new String[100];
        time_body7 = new String[100];
        cal_day1 = 0;
        cal_day2 = 0;
        cal_day3 = 0;
        cal_day4 = 0;
        cal_day5 = 0;
        cal_day6 = 0;
        cal_day7 = 0;
        cal_week1 = 0;
        cal_week2 = 0;
        cal_week3 = 0;
        cal_week4 = 0;
        cal_week5 = 0;
        cal_week6 = 0;
        cal_week7 = 0;

        week_recycle = view.findViewById(R.id.week_week_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        week_recycle.setLayoutManager(layoutManager);
        week_adapter = new week_adapter();//월~금,월~일

        day_recycle = view.findViewById(R.id.week_day_recycle3);//1~5,1~7
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        day_recycle.setLayoutManager(layoutManager);
        day_adapter = new week_day_adapter();

        time_recycle = view.findViewById(R.id.week_time_recycle3);//1시간단위 시간
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        time_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        time_adapter = new week_time_adapter();

        monday_recycle = view.findViewById(R.id.week_monday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        monday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_1 = new week_body_adapter();

        tuesday_recycle = view.findViewById(R.id.week_tuesday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tuesday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_2 = new week_body_adapter();

        wednesday_recycle = view.findViewById(R.id.week_wednesday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        wednesday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_3 = new week_body_adapter();

        thursday_recycle = view.findViewById(R.id.week_thursday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        thursday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_4 = new week_body_adapter();

        friday_recycle = view.findViewById(R.id.week_friday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        friday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_5 = new week_body_adapter();

        saturday_recycle = view.findViewById(R.id.week_saturday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        saturday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_6 = new week_body_adapter();

        sunday_recycle = view.findViewById(R.id.week_sunday_recycle3);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sunday_recycle.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        body_adapter_7 = new week_body_adapter();

        start_time_spinner = view.findViewById(R.id.week_start_time_spinner3);
        ArrayAdapter<CharSequence> start_time_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.start_time60, android.R.layout.simple_spinner_item);
        start_time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_time_spinner.setAdapter(start_time_adapter);
        //start_time_spinner.setSelection(frag_week.WEEK_START_TIME/2);
        start_time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start_spinner_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        end_time_spinner = view.findViewById(R.id.week_end_time_spinner3);
        ArrayAdapter<CharSequence> end_time_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.end_time60, android.R.layout.simple_spinner_item);
        end_time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_time_spinner.setAdapter(end_time_adapter);
        //end_time_spinner.setSelection(frag_week.WEEK_END_TIME/2);
        end_time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end_spinner_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        week_spinner = view.findViewById(R.id.week_week_spinner3);
        ArrayAdapter<CharSequence> s_week_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.week_setting_week, android.R.layout.simple_spinner_item);
        s_week_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        week_spinner.setAdapter(s_week_adapter);
        //time_spinner.setSelection(0);
        week_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                week_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_button = view.findViewById(R.id.week_search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_spinner_position > end_spinner_position){//다시 선택하세요
                    new AlertDialog.Builder(getActivity()).setTitle("알림").setMessage("시작시간과 종료시간을 다시 설정해주세요.").setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }else{
                    if(week_position == 0){
                        frag_week.WEEK_CALENDER_SIZE = 6;
                    }else{
                        frag_week.WEEK_CALENDER_SIZE = 8;
                    }
                    frag_week.WEEK_START_TIME = start_spinner_position*2;
                    frag_week.WEEK_END_TIME = end_spinner_position*2;

                    SharedPreferences pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("week_calender_size",frag_week.WEEK_CALENDER_SIZE);
                    editor.putInt("week_start_time",frag_week.WEEK_START_TIME);
                    editor.putInt("week_end_time",frag_week.WEEK_END_TIME);
                    editor.commit();

                    listener.onFragmentChanged();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Log.d("start1", "loglog");

        restore();
        start_spinner_position = frag_week.WEEK_START_TIME/2;
        end_spinner_position = frag_week.WEEK_END_TIME/2;
        start_time_spinner.setSelection(frag_week.WEEK_START_TIME/2);
        end_time_spinner.setSelection(frag_week.WEEK_END_TIME/2);
        if(frag_week.WEEK_CALENDER_SIZE == 6){
            week_position = 0;
            week_spinner.setSelection(0);
        }else{
            week_position = 1;
            week_spinner.setSelection(1);
        }
        Log.d("타입타입의 초기값은",Integer.toString(time_type1[0]));
        Log.d("타입타입의 초기값은",Integer.toString(time_type1[1]));
        Log.d("타입타입의 초기값은",Integer.toString(time_type1[2]));
        for(int i = 0; i<10; i++){
            Log.d("날짜아이템검사"+i,Integer.toString(item_add_day[i]));
        }
        week_adapter.resetItem();
        day_adapter.resetItem();
        time_adapter.resetItem();
        body_adapter_1.resetItem();
        body_adapter_2.resetItem();
        body_adapter_3.resetItem();
        body_adapter_4.resetItem();
        body_adapter_5.resetItem();
        body_adapter_6.resetItem();
        body_adapter_7.resetItem();
        if (frag_week.WEEK_CALENDER_SIZE == 6) {
            week_adapter.addItem(new WeekList(""));
            week_adapter.addItem(new WeekList("월"));
            week_adapter.addItem(new WeekList("화"));
            week_adapter.addItem(new WeekList("수"));
            week_adapter.addItem(new WeekList("목"));
            week_adapter.addItem(new WeekList("금"));
        } else if (frag_week.WEEK_CALENDER_SIZE == 8) {
            week_adapter.addItem(new WeekList(""));
            week_adapter.addItem(new WeekList("월"));
            week_adapter.addItem(new WeekList("화"));
            week_adapter.addItem(new WeekList("수"));
            week_adapter.addItem(new WeekList("목"));
            week_adapter.addItem(new WeekList("금"));
            week_adapter.addItem(new WeekList("토"));
            week_adapter.addItem(new WeekList("일"));
        }
        week_recycle.setAdapter(week_adapter);
        if (frag_week.WEEK_CALENDER_SIZE == 6) {
            day_adapter.addItem(new WeekdayList(""));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 1))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 2))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 3))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 4))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 5))));
        } else if (frag_week.WEEK_CALENDER_SIZE == 8) {
            day_adapter.addItem(new WeekdayList(""));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 1))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 2))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 3))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 4))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 5))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 6))));
            day_adapter.addItem(new WeekdayList(getDate2((index - 1500) * 7 - (getWeek() - 7))));
        }
        day_recycle.setAdapter(day_adapter);
        for (int i = frag_week.WEEK_START_TIME; i <= frag_week.WEEK_END_TIME; i++) {
            if (i % 2 == 0) {
                time_adapter.addItem(new WeektimeList(i / 2 + "시"));
            }
            i++;
        }
        time_recycle.setAdapter(time_adapter);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type1[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type1[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_1.addItem(new WeekbodyList(time_body1[temp_start_index], temp_time_type1, time_color1[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type1[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type1[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type1[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_1.addItem(new WeekbodyList(time_body1[i], temp_time_type2, time_color1[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_1.addItem(new WeekbodyList(time_body1[i], time_type1[i], time_color1[i]));
            }
            i += time_type1[i] - 1;
        }
        body_adapter_1.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type1[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type1[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type1[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type1[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day1 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type1[time]);
                    intent.putExtra("body", time_body1[time]);
                    intent.putExtra("week", 1);
                    Log.d("월터치날짜검사", getDate((index - 1500) * 7 - (getWeek() - 1)));
                    Log.d("월터치날짜검사변수", Integer.toString(-(getWeek() - 1)));//0보다 작으면 getDate( 7 - (getWeek() - 1))
                    Log.d("월터치날짜검사수정", getDate(-(getWeek() - 1)));//0보다 크면
                    if (-(getWeek() - 1) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 1)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 1)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 1)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 1)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 1)));//2020.08.11//터치한 위치 날짜
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 1)));//20200811//터치한 위치 날짜
                    intent.putExtra("day3", cal_week1);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day1);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if(cal_day1 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 1)));
                    intent.putExtra("type", time_type1[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body1[time]);
                    intent.putExtra("color",time_color1[time]);
                    intent.putExtra("category",time_category1[time]);
                    startActivity(intent);
                }
            }
        });

        monday_recycle.setAdapter(body_adapter_1);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type2[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type2[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_2.addItem(new WeekbodyList(time_body2[temp_start_index], temp_time_type1, time_color2[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type2[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type2[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type2[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_2.addItem(new WeekbodyList(time_body2[i], temp_time_type2, time_color2[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_2.addItem(new WeekbodyList(time_body2[i], time_type2[i], time_color2[i]));
            }
            i += time_type2[i] - 1;
        }
        body_adapter_2.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type2[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type2[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type2[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type2[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day2 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type2[time]);
                    intent.putExtra("body", time_body2[time]);
                    intent.putExtra("week", 2);
                    if (-(getWeek() - 2) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 2)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 2)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 2)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 2)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 2)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 2)));//20200811
                    intent.putExtra("day3", cal_week2);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day2);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day2 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 2)));
                    intent.putExtra("type", time_type2[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body2[time]);
                    intent.putExtra("color",time_color2[time]);
                    intent.putExtra("category",time_category2[time]);
                    startActivity(intent);
                }
            }
        });

        tuesday_recycle.setAdapter(body_adapter_2);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type3[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type3[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_3.addItem(new WeekbodyList(time_body3[temp_start_index], temp_time_type1, time_color3[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type3[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type3[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type3[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_3.addItem(new WeekbodyList(time_body3[i], temp_time_type2, time_color3[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_3.addItem(new WeekbodyList(time_body3[i], time_type3[i], time_color3[i]));
            }
            i += time_type3[i] - 1;
        }
        body_adapter_3.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type3[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type3[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type3[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type3[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day3 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type3[time]);
                    intent.putExtra("body", time_body3[time]);
                    intent.putExtra("week", 3);
                    if (-(getWeek() - 3) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 3)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 3)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 3)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 3)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 3)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 3)));//20200811
                    intent.putExtra("day3", cal_week3);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day3);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day3 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 3)));
                    intent.putExtra("type", time_type3[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body3[time]);
                    intent.putExtra("color",time_color3[time]);
                    intent.putExtra("category",time_category3[time]);
                    startActivity(intent);
                }
            }
        });

        wednesday_recycle.setAdapter(body_adapter_3);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type4[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type4[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_4.addItem(new WeekbodyList(time_body4[temp_start_index], temp_time_type1, time_color4[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type4[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type4[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type4[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_4.addItem(new WeekbodyList(time_body4[i], temp_time_type2, time_color4[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_4.addItem(new WeekbodyList(time_body4[i], time_type4[i], time_color4[i]));
            }
            i += time_type4[i] - 1;
        }
        body_adapter_4.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type4[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type4[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type4[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type4[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day4 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type4[time]);
                    intent.putExtra("body", time_body4[time]);
                    intent.putExtra("week", 4);
                    if (-(getWeek() - 4) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 4)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 4)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 4)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 4)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 4)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 4)));//20200811
                    intent.putExtra("day3", cal_week4);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day4);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day4 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 4)));
                    intent.putExtra("type", time_type4[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body4[time]);
                    intent.putExtra("color",time_color4[time]);
                    intent.putExtra("category",time_category4[time]);
                    startActivity(intent);
                }
            }
        });

        thursday_recycle.setAdapter(body_adapter_4);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type5[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type5[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_5.addItem(new WeekbodyList(time_body5[temp_start_index], temp_time_type1, time_color5[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type5[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type5[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type5[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_5.addItem(new WeekbodyList(time_body5[i], temp_time_type2, time_color5[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_5.addItem(new WeekbodyList(time_body5[i], time_type5[i], time_color5[i]));
            }
            i += time_type5[i] - 1;
        }
        body_adapter_5.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type5[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type5[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                        Log.d("템프타입값은",Integer.toString(temp_time_type1));
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type5[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type5[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }

                Log.d("템프타입포지션값은",Integer.toString(position));
                Log.d("템프타입타임값은",Integer.toString(time));
                if(cal_day5 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type5[time]);
                    intent.putExtra("body", time_body5[time]);
                    intent.putExtra("week", 5);
                    Log.d("금터치날짜검사", getDate((index - 1500) * 7 - (getWeek() - 5)));
                    Log.d("금터치날짜검사변수", Integer.toString(-(getWeek() - 5)));
                    Log.d("금터치날짜검사수정", getDate(-(getWeek() - 5)));
                    if (-(getWeek() - 5) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 5)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 5)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 5)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 5)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 5)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 5)));//20200811
                    intent.putExtra("day3", cal_week5);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day5);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day5 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 5)));
                    intent.putExtra("type", time_type5[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body5[time]);
                    intent.putExtra("color",time_color5[time]);
                    intent.putExtra("category",time_category5[time]);
                    startActivity(intent);
                }
            }
        });

        friday_recycle.setAdapter(body_adapter_5);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type6[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type6[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_6.addItem(new WeekbodyList(time_body6[temp_start_index], temp_time_type1, time_color6[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type6[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type6[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type6[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_6.addItem(new WeekbodyList(time_body6[i], temp_time_type2, time_color6[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_6.addItem(new WeekbodyList(time_body6[i], time_type6[i], time_color6[i]));
            }
            i += time_type6[i] - 1;
        }
        body_adapter_6.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type6[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type6[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type6[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type6[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day6 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type6[time]);
                    intent.putExtra("body", time_body6[time]);
                    intent.putExtra("week", 6);
                    if (-(getWeek() - 6) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 6)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 6)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 6)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 6)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 6)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 6)));//20200811
                    intent.putExtra("day3", cal_week6);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day6);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day6 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 6)));
                    intent.putExtra("type", time_type6[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body6[time]);
                    intent.putExtra("color",time_color6[time]);
                    intent.putExtra("category",time_category6[time]);
                    startActivity(intent);
                }
            }
        });

        saturday_recycle.setAdapter(body_adapter_6);

        temp_start_index = 0;
        temp_time_type1 = 0;
        for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
            if (time_type7[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                temp_start_index = i;
                temp_time_type1 = time_type7[i] + i - frag_week.WEEK_START_TIME;//초과분
                i = 100;
            }
        }
        if (temp_time_type1 != 0) {//초과분이 있다면
            body_adapter_7.addItem(new WeekbodyList(time_body7[temp_start_index], temp_time_type1, time_color7[temp_start_index]));
        }
        for (int i = frag_week.WEEK_START_TIME + temp_time_type1; i < frag_week.WEEK_END_TIME + 2; i++) {
            if (time_type7[i] + i > frag_week.WEEK_END_TIME + 2) {
                int temp_time_type2 = time_type7[i] + i - (frag_week.WEEK_END_TIME + 2);//초과분
                temp_time_type2 = time_type7[i] - temp_time_type2;//원래간격에서 초과분 간격 빼기 단)1이상이어야된다
                if (temp_time_type2 >= 1) {
                    body_adapter_7.addItem(new WeekbodyList(time_body7[i], temp_time_type2, time_color7[i]));
                }
                i = 98;//탈출 100이 아닌이유 null값 때문
            } else {
                Log.d("loglog추가", "ㅇㄴㄹ");
                body_adapter_7.addItem(new WeekbodyList(time_body7[i], time_type7[i], time_color7[i]));
            }
            i += time_type7[i] - 1;
        }
        body_adapter_7.setOnItemClickListener(new OnlistItemClickListener() {
            @Override
            public void onItemClick(week_body_adapter.ViewHolder holder, View view, int position) {
                temp_start_index = 0;
                temp_time_type1 = 0;
                for (int i = 0; i < frag_week.WEEK_START_TIME; i++) {
                    if (time_type7[i] + i > frag_week.WEEK_START_TIME) {//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type7[i] + i - frag_week.WEEK_START_TIME;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type7[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = frag_week.WEEK_START_TIME; i<48; i++){
                        time = i;
                        i += time_type7[i] - 1;
                        if(count == position){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                if(cal_day7 == 0) {
                    Intent intent = new Intent(getActivity(), week_addActivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("type", time_type7[time]);
                    intent.putExtra("body", time_body7[time]);
                    intent.putExtra("week", 7);
                    if (-(getWeek() - 7) < 0) {
                        intent.putExtra("day", getDate(7 - (getWeek() - 7)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(7 - (getWeek() - 7)));//20200811//터치한 위치 날짜
                    } else {
                        intent.putExtra("day", getDate(-(getWeek() - 7)));//2020.08.11//터치한 위치 날짜
                        intent.putExtra("day2", getDate3(-(getWeek() - 7)));//20200811//터치한 위치 날짜
                    }
                    //intent.putExtra("day", getDate((index-1500) * 7 - (getWeek() - 7)));//2020.08.11
                    intent.putExtra("day22", getDate3((index - 1500) * 7 - (getWeek() - 7)));//20200811
                    intent.putExtra("day3", cal_week7);//데이터를 불러온 날짜
                    intent.putExtra("cal_day", cal_day7);//데이터를 월간달력에서 불러왔는지 아닌지 확인하는 값//이 값이 1이면 수정 불가능하게 설정해야됨
                    startActivity(intent);
                }else if (cal_day7 == 1){
                    Intent intent = new Intent(getActivity(), month_expandActivity.class);
                    intent.putExtra("time",getDate((index - 1500) * 7 - (getWeek() - 7)));
                    intent.putExtra("type", time_type7[time]);
                    intent.putExtra("start_time",time);
                    intent.putExtra("body",time_body7[time]);
                    intent.putExtra("color",time_color7[time]);
                    intent.putExtra("category",time_category7[time]);
                    startActivity(intent);
                }
            }
        });

        sunday_recycle.setAdapter(body_adapter_7);
        super.onStart();
    }

    public void restore() {
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        if (pref != null) {
            frag_week.WEEK_CALENDER_SIZE = pref.getInt("week_calender_size", 6);
            frag_week.WEEK_START_TIME = pref.getInt("week_start_time", 16);
            frag_week.WEEK_END_TIME = pref.getInt("week_end_time", 40);
            for (int i = 0; i < 100; i++) {
                item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
                item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
            }
            //int index = frag_week.PAGE_INDEX - 1500;
            Log.d("주간시간표월간확인3","day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_body_");
            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 1)), 0) == 1) {//getweek(현재요일)-1 = 월요일
                //getDate가 날짜마다 더해져서 getWeek값이 유동이여도 계산이 떨어짐
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//월요일검사
                //쉐어드 불러오기 day+날짜+time1_?_i
                cal_day1 = 1;
                Log.d("주간시간표월간확인","day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_body_");
                for (int i = 0; i < 100; i++) {
                    time_body1[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_body_" + i, null);
                    time_category1[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_category_" + i, null);
                    time_color1[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_color_" + i, 0);
                    time_type1[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 1)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 1)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time1_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body1[k] = null;
                                time_category1[k] = null;
                                time_color1[k] = 0;
                                time_type1[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 1)))) {//날짜가 아예 같으면 요일도 같다
                        Log.d("검사검사10번","검사검사10번");
                        cal_week1 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body1[k] = pref.getString(item_add_day[temp_index-1] + "time1_body_" + k, null);
                            time_category1[k] = pref.getString(item_add_day[temp_index-1] + "time1_category_" + k, null);
                            time_color1[k] = pref.getInt(item_add_day[temp_index-1] + "time1_color_" + k, 0);
                            time_type1[k] = pref.getInt(item_add_day[temp_index-1] + "time1_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 1) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week1 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body1[k] = pref.getString(item_add_day[i] + "time1_body_" + k, null);
                                    time_category1[k] = pref.getString(item_add_day[i] + "time1_category_" + k, null);
                                    time_color1[k] = pref.getInt(item_add_day[i] + "time1_color_" + k, 0);
                                    time_type1[k] = pref.getInt(item_add_day[i] + "time1_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body1[k] = null;
                                        time_category1[k] = null;
                                        time_color1[k] = 0;
                                        time_type1[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body1[k] = null;
                        time_category1[k] = null;
                        time_color1[k] = 0;
                        time_type1[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 2)), 0) == 1) {//getweek(현재요일)-2 = 화요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//화요일검사
                //쉐어드 불러오기 day+날짜+time2_?_i
                cal_day2 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body2[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 2)) + "time_body_" + i, null);
                    time_category2[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 2)) + "time_category_" + i, null);
                    time_color2[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 2)) + "time_color_" + i, 0);
                    time_type2[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 2)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 2)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time2_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body2[k] = null;
                                time_category2[k] = null;
                                time_color2[k] = 0;
                                time_type2[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 2)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week2 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body2[k] = pref.getString(item_add_day[temp_index-1] + "time2_body_" + k, null);
                            time_category2[k] = pref.getString(item_add_day[temp_index-1] + "time2_category_" + k, null);
                            time_color2[k] = pref.getInt(item_add_day[temp_index-1] + "time2_color_" + k, 0);
                            time_type2[k] = pref.getInt(item_add_day[temp_index-1] + "time2_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 2) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week2 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body2[k] = pref.getString(item_add_day[i] + "time2_body_" + k, null);
                                    time_category2[k] = pref.getString(item_add_day[i] + "time2_category_" + k, null);
                                    time_color2[k] = pref.getInt(item_add_day[i] + "time2_color_" + k, 0);
                                    time_type2[k] = pref.getInt(item_add_day[i] + "time2_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body2[k] = null;
                                        time_category2[k] = null;
                                        time_color2[k] = 0;
                                        time_type2[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body2[k] = null;
                        time_category2[k] = null;
                        time_color2[k] = 0;
                        time_type2[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3)), 0) == 1) {//getweek(현재요일)-3 = 수요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//수요일검사
                //쉐어드 불러오기 day+날짜+time3_?_i
                cal_day3 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body3[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 3)) + "time_body_" + i, null);
                    time_category3[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 3)) + "time_category_" + i, null);
                    time_color3[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3)) + "time_color_" + i, 0);
                    time_type3[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 3)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 3)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time3_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body3[k] = null;
                                time_category3[k] = null;
                                time_color3[k] = 0;
                                time_type3[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 3)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week3 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body3[k] = pref.getString(item_add_day[temp_index-1] + "time3_body_" + k, null);
                            time_category3[k] = pref.getString(item_add_day[temp_index-1] + "time3_category_" + k, null);
                            time_color3[k] = pref.getInt(item_add_day[temp_index-1] + "time3_color_" + k, 0);
                            time_type3[k] = pref.getInt(item_add_day[temp_index-1] + "time3_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 3) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week3 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body3[k] = pref.getString(item_add_day[i] + "time3_body_" + k, null);
                                    time_category3[k] = pref.getString(item_add_day[i] + "time3_category_" + k, null);
                                    time_color3[k] = pref.getInt(item_add_day[i] + "time3_color_" + k, 0);
                                    time_type3[k] = pref.getInt(item_add_day[i] + "time3_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body3[k] = null;
                                        time_category3[k] = null;
                                        time_color3[k] = 0;
                                        time_type3[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body3[k] = null;
                        time_category3[k] = null;
                        time_color3[k] = 0;
                        time_type3[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 4)), 0) == 1) {//getweek(현재요일)-4 = 목요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//화요일검사
                //쉐어드 불러오기 day+날짜+time4_?_i
                cal_day4 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body4[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 4)) + "time_body_" + i, null);
                    time_category4[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 4)) + "time_category_" + i, null);
                    time_color4[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 4)) + "time_color_" + i, 0);
                    time_type4[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 4)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 4)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time4_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body4[k] = null;
                                time_category4[k] = null;
                                time_color4[k] = 0;
                                time_type4[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 4)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week4 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body4[k] = pref.getString(item_add_day[temp_index-1] + "time4_body_" + k, null);
                            time_category4[k] = pref.getString(item_add_day[temp_index-1] + "time4_category_" + k, null);
                            time_color4[k] = pref.getInt(item_add_day[temp_index-1] + "time4_color_" + k, 0);
                            time_type4[k] = pref.getInt(item_add_day[temp_index-1] + "time4_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 4) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week4 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body4[k] = pref.getString(item_add_day[i] + "time4_body_" + k, null);
                                    time_category4[k] = pref.getString(item_add_day[i] + "time4_category_" + k, null);
                                    time_color4[k] = pref.getInt(item_add_day[i] + "time4_color_" + k, 0);
                                    time_type4[k] = pref.getInt(item_add_day[i] + "time4_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body4[k] = null;
                                        time_category4[k] = null;
                                        time_color4[k] = 0;
                                        time_type4[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body4[k] = null;
                        time_category4[k] = null;
                        time_color4[k] = 0;
                        time_type4[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 5)), 0) == 1) {//getweek(현재요일)-5 = 금요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//화요일검사
                //쉐어드 불러오기 day+날짜+time1_?_i
                cal_day5 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body5[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 5)) + "time_body_" + i, null);
                    time_category5[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 5)) + "time_category_" + i, null);
                    time_color5[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 5)) + "time_color_" + i, 0);
                    time_type5[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 5)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 5)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time5_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body5[k] = null;
                                time_category5[k] = null;
                                time_color5[k] = 0;
                                time_type5[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 5)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week5 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body5[k] = pref.getString(item_add_day[temp_index-1] + "time5_body_" + k, null);
                            time_category5[k] = pref.getString(item_add_day[temp_index-1] + "time5_category_" + k, null);
                            time_color5[k] = pref.getInt(item_add_day[temp_index-1] + "time5_color_" + k, 0);
                            time_type5[k] = pref.getInt(item_add_day[temp_index-1] + "time5_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 5) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week5 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body5[k] = pref.getString(item_add_day[i] + "time5_body_" + k, null);
                                    time_category5[k] = pref.getString(item_add_day[i] + "time5_category_" + k, null);
                                    time_color5[k] = pref.getInt(item_add_day[i] + "time5_color_" + k, 0);
                                    time_type5[k] = pref.getInt(item_add_day[i] + "time5_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body5[k] = null;
                                        time_category5[k] = null;
                                        time_color5[k] = 0;
                                        time_type5[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body5[k] = null;
                        time_category5[k] = null;
                        time_color5[k] = 0;
                        time_type5[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 6)), 0) == 1) {//getweek(현재요일)-6 = 토요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//화요일검사
                //쉐어드 불러오기 day+날짜+time1_?_i
                cal_day6 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body6[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 6)) + "time_body_" + i, null);
                    time_category6[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 6)) + "time_category_" + i, null);
                    time_color6[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 6)) + "time_color_" + i, 0);
                    time_type6[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 6)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 6)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time6_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body6[k] = null;
                                time_category6[k] = null;
                                time_color6[k] = 0;
                                time_type6[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 6)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week6 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body6[k] = pref.getString(item_add_day[temp_index-1] + "time6_body_" + k, null);
                            time_category6[k] = pref.getString(item_add_day[temp_index-1] + "time6_category_" + k, null);
                            time_color6[k] = pref.getInt(item_add_day[temp_index-1] + "time6_color_" + k, 0);
                            time_type6[k] = pref.getInt(item_add_day[temp_index-1] + "time6_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 6) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week6 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body6[k] = pref.getString(item_add_day[i] + "time6_body_" + k, null);
                                    time_category6[k] = pref.getString(item_add_day[i] + "time6_category_" + k, null);
                                    time_color6[k] = pref.getInt(item_add_day[i] + "time6_color_" + k, 0);
                                    time_type6[k] = pref.getInt(item_add_day[i] + "time6_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body6[k] = null;
                                        time_category6[k] = null;
                                        time_color6[k] = 0;
                                        time_type6[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body6[k] = null;
                        time_category6[k] = null;
                        time_color6[k] = 0;
                        time_type6[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

            if (pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 7)), 0) == 1) {//getweek(현재요일)-7 = 일요일
                //1이면 월간설정 존재 0이면 존재하지않음
                //월간설정이 존재함//화요일검사
                //쉐어드 불러오기 day+날짜+time1_?_i
                cal_day7 = 1;
                for (int i = 0; i < 100; i++) {
                    time_body7[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 7)) + "time_body_" + i, null);
                    time_category7[i] = pref.getString("day" + getDate0((index-1500) * 7 - (getWeek() - 7)) + "time_category_" + i, null);
                    time_color7[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 7)) + "time_color_" + i, 0);
                    time_type7[i] = pref.getInt("day" + getDate0((index-1500) * 7 - (getWeek() - 7)) + "time_type_" + i, 1);
                }
            } else {
                int temp_index = 0;
                for (int i = 0; i < 100; i++) {
                    if (item_add_day[i] != 0) {
                        if (item_add_day[i] > Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 7)))) {
                            temp_index = i;
                            i = 100;//탈출
                        }
                    } else {
                        temp_index = i;
                        if (i == 0) {
                            //단 한개도 존재하지 않을때
                            //time7_??_i = 0 or null
                            for (int k = 0; k < 100; k++) {
                                time_body7[k] = null;
                                time_category7[k] = null;
                                time_color7[k] = 0;
                                time_type7[k] = 1;
                            }
                        }
                        i = 100;
                    }
                }
                //////////////////////////////////////////////
                if (temp_index != 0) {
                    if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3((index-1500) * 7 - (getWeek() - 7)))) {//날짜가 아예 같으면 요일도 같다
                        cal_week7 = item_add_day[temp_index-1];
                        for (int k = 0; k < 100; k++) {
                            time_body7[k] = pref.getString(item_add_day[temp_index-1] + "time7_body_" + k, null);
                            time_category7[k] = pref.getString(item_add_day[temp_index-1] + "time7_category_" + k, null);
                            time_color7[k] = pref.getInt(item_add_day[temp_index-1] + "time7_color_" + k, 0);
                            time_type7[k] = pref.getInt(item_add_day[temp_index-1] + "time7_type_" + k, 1);
                        }
                    } else {
                        int restore_count = 0;
                        for (int i = temp_index - 1; i >= 0; i--) {
                            if (item_add_week[i] == 7) {
                                restore_count++;
                                //가장 최근 주간 설정(월요일)
                                //쉐어드에서 불러오기 날짜+time1_?_i
                                cal_week7 = item_add_day[i];
                                for (int k = 0; k < 100; k++) {
                                    time_body7[k] = pref.getString(item_add_day[i] + "time7_body_" + k, null);
                                    time_category7[k] = pref.getString(item_add_day[i] + "time7_category_" + k, null);
                                    time_color7[k] = pref.getInt(item_add_day[i] + "time7_color_" + k, 0);
                                    time_type7[k] = pref.getInt(item_add_day[i] + "time7_type_" + k, 1);
                                }
                                i = 0;//탈출
                            }
                            if(i == 0){
                                if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                    for (int k = 0; k < 100; k++) {
                                        time_body7[k] = null;
                                        time_category7[k] = null;
                                        time_color7[k] = 0;
                                        time_type7[k] = 1;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    //단 한개도 존재하지 않을때
                    //time1_??_i = 0 or null
                    for (int k = 0; k < 100; k++) {
                        time_body7[k] = null;
                        time_category7[k] = null;
                        time_color7[k] = 0;
                        time_type7[k] = 1;
                    }
                }
                ////////////////////////////////////////////////////
            }

        } else {
            for(int i = 0; i<100; i++){
                time_type1[i] = 1;
                time_type2[i] = 1;
                time_type3[i] = 1;
                time_type4[i] = 1;
                time_type5[i] = 1;
                time_type6[i] = 1;
                time_type7[i] = 1;
            }
            frag_week.WEEK_CALENDER_SIZE = 6;//6,8
            frag_week.WEEK_START_TIME = 16;
            frag_week.WEEK_END_TIME = 40;
        }
        if(time_type1[0] == 0){
            for(int i = 0; i<100; i++){
                time_type1[i] = 1;
            }
        }
        if(time_type2[0] == 0){
            for(int i = 0; i<100; i++){
                time_type2[i] = 1;
            }
        }
        if(time_type3[0] == 0){
            for(int i = 0; i<100; i++){
                time_type3[i] = 1;
            }
        }
        if(time_type4[0] == 0){
            for(int i = 0; i<100; i++){
                time_type4[i] = 1;
            }
        }
        if(time_type5[0] == 0){
            for(int i = 0; i<100; i++){
                time_type5[i] = 1;
            }
        }
        if(time_type6[0] == 0){
            for(int i = 0; i<100; i++){
                time_type6[i] = 1;
            }
        }
        if(time_type7[0] == 0){
            for(int i = 0; i<100; i++){
                time_type7[i] = 1;
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

}
