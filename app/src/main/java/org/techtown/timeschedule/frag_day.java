package org.techtown.timeschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class frag_day extends Fragment {

    private day_time_adapter time_adapter;
    private day_adapter day_adapter1, day_adapter2;
    private RecyclerView day_recycle_morning, day_recycle_after, time_recycle_morning, time_recycle_after;
    private LinearLayoutManager layoutManager;
    private int[] time_type, time_color;
    private String[] time_category, body;
    private TextView day_t;
    private int[] item_add_day, item_add_week;
    private int cal_day, cal_week;//cal_day:쉐어드 불러온 날짜//cal_week = 1이면 월간에서 불러옴

    public frag_day(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_day,container,false);

        time_type = new int[100];
        time_color = new int[100];
        time_category = new String[100];
        body = new String[100];

        item_add_day = new int[100];
        item_add_week = new int[100];

        time_recycle_morning = view.findViewById(R.id.day_moring_time_recycle);
        time_recycle_after = view.findViewById(R.id.day_afternoon_time_recycle);
        day_recycle_morning = view.findViewById(R.id.day_moring_recycle);
        day_recycle_after = view.findViewById(R.id.day_afternoon_recycle);
        day_t = view.findViewById(R.id.day_day);
        return view;
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
    public int getWeek_int() {
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

    public String getWeek() {
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
        return m_week;
    }

    @Override
    public void onStart() {
        super.onStart();

        //restore(getActivity());
        restore2();
        if(time_type[0] == 0){
            for(int i = 0; i<100; i++){
                time_type[i] = 1;
            }
        }

        day_t.setText(getDate(0)+" "+getWeek());
        Log.d("ㄴㅇㄹ망ㄹ민알","ㅇㄴㅁㄴㅇㄻ");
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        time_recycle_morning.setLayoutManager(layoutManager);

        time_adapter = new day_time_adapter();
        time_adapter.addItem(new WeektimeList("오전"));
        for (int i = 0; i < 12; i++) {
            time_adapter.addItem(new WeektimeList(i + ":00\n~\n" + i + ":30"));
            time_adapter.addItem(new WeektimeList(i + ":30\n~\n" + (i+1) + ":00"));

        }
        time_recycle_morning.setAdapter(time_adapter);
///////////////////////////////////////////////////////////////////////////////
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycle_morning.setLayoutManager(layoutManager);

        day_adapter1 = new day_adapter();//end_time 기준 1:00~1:30을 선택하면 1:00의 시간이다
        day_adapter1.addItem(new dayList("오전",1,0));
        for(int i = 0; i<24; i++){
            if(i+time_type[i]>24){
                day_adapter1.addItem(new dayList(body[i],24-i,time_color[i]));
                i = 30;//탈출
            }else {
                day_adapter1.addItem(new dayList(body[i], time_type[i], time_color[i]));
            }
            i += time_type[i]-1;
        }
        //day_adapter.addItem(new dayList("1번",1,0));
        //day_adapter.addItem(new dayList("2번",2,0));
        //day_adapter.addItem(new dayList("3번",3,0));
        day_recycle_morning.setAdapter(day_adapter1);
        day_adapter1.setOnItemClickListener(new OnlistItemClickListener3() {
            @Override
            public void onItemClick(org.techtown.timeschedule.day_adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), Day_expandActivity.class);
                intent.putExtra("day",getDate(0));//2020.08.11
                intent.putExtra("week",getWeek());//"월","화"
                int time = 0;
                int count = 0;
                for(int i = 0; i<48; i++){
                    time = i;
                    i += time_type[i] - 1;
                    if(count == position-1){
                        i = 100;//탈출
                    }
                    count++;
                }
                intent.putExtra("color",time_color[time]);
                intent.putExtra("category",time_category[time]);
                intent.putExtra("body",body[time]);
                intent.putExtra("type",time_type[time]);
                intent.putExtra("time",time);
                Log.d("타임타임은1",Integer.toString(time));
                if(body[time] != null) {
                    if (body[time].length() > 0) {
                        startActivity(intent);
                    }
                }
            }
        });
        /////////////////////////////////////////////////////////////////////
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        time_recycle_after.setLayoutManager(layoutManager);

        time_adapter = new day_time_adapter();
        time_adapter.addItem(new WeektimeList("오후"));
        for (int i = 0; i < 12; i++) {
            time_adapter.addItem(new WeektimeList(i + ":00\n~\n" + i + ":30"));
            time_adapter.addItem(new WeektimeList(i + ":30\n~\n" + (i+1) + ":00"));

        }
        time_recycle_after.setAdapter(time_adapter);
        ////////////////////////////////////////////////////////////////////////
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycle_after.setLayoutManager(layoutManager);

        day_adapter2 = new day_adapter();//end_time 기준 1:00~1:30을 선택하면 1:00의 시간이다
        day_adapter2.addItem(new dayList("오후",1,0));
        int temp_time = 0;
        int temp_index = 0;
        for(int i = 0; i<24; i++){//오전에서 넘치는값 찾기
            if(i+time_type[i] > 24){
                temp_time = i + time_type[i] -24;
                temp_index = i;
                i = 30;//탈출
            }
        }
        if(temp_time>0) {//오전에서 넘친값 오후 처음에 넣기
            day_adapter2.addItem(new dayList(body[temp_index], temp_time, time_color[temp_index]));
        }

        for(int i = 24+temp_time; i<48; i++){//temp_time 오전에서 넘치면 넘친만큼 총 칸수를 줄여야된다
            if(i+time_type[i]>48){
                day_adapter2.addItem(new dayList(body[i],48-i,time_color[i]));
                i = 60;//탈출
            }else {
                day_adapter2.addItem(new dayList(body[i], time_type[i], time_color[i]));
            }
            i += time_type[i]-1;
        }

        day_recycle_after.setAdapter(day_adapter2);
        day_adapter2.setOnItemClickListener(new OnlistItemClickListener3() {
            @Override
            public void onItemClick(day_adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), Day_expandActivity.class);
                intent.putExtra("day",getDate(0));//2020.08.11
                intent.putExtra("week",getWeek());//"월","화"
                int temp_start_index = 0;
                int temp_time_type1 = 0;
                for(int i = 0; i<24; i++){
                    if(time_type[i] + i > 24){//예 9시(i) 2칸(time_type[i]) 시작시간 10시이면 결과적으로 1칸 초과되서 보임
                        temp_start_index = i;
                        temp_time_type1 = time_type[i] + i - 24;//초과분
                        i = 100;
                    }
                }
                int time = 0;
                int count = 0;
                if(temp_time_type1 != 0){//초과분이있을경우
                    for(int i = temp_start_index; i<48; i++){
                        time = i;
                        i += time_type[i] - 1;
                        if(count == position-1){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }else{
                    for(int i = 24; i<48; i++){
                        time = i;
                        i += time_type[i] - 1;
                        if(count == position-1){
                            i = 100;//탈출
                        }
                        count++;
                    }
                }
                intent.putExtra("color",time_color[time]);
                intent.putExtra("category",time_category[time]);
                intent.putExtra("body",body[time]);
                intent.putExtra("type",time_type[time]);
                intent.putExtra("time",time);
                Log.d("타임타임은2",Integer.toString(time));
                if(body[time] != null) {
                    if (body[time].length() > 0) {
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void restore2(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        for (int i = 0; i < 100; i++) {
            item_add_week[i] = pref.getInt("item_add_week_" + i, 0);//1~7 없으면0
            item_add_day[i] = pref.getInt("item_add_day_" + i, 0);//20200811 없으면0
        }
        if (pref.getInt("day" + getDate(0), 0) == 1) {//getweek(현재요일)-1 = 월요일
            //getDate가 날짜마다 더해져서 getWeek값이 유동이여도 계산이 떨어짐
            //1이면 월간설정 존재 0이면 존재하지않음
            //월간설정이 존재함//월요일검사
            //쉐어드 불러오기 day+날짜+time1_?_i
            cal_day = 1;
            for (int i = 0; i < 100; i++) {
                body[i] = pref.getString("day" + getDate(0) + "time"+getWeek_int()+"_body_" + i, null);
                time_category[i] = pref.getString("day" + getDate(0) + "time"+getWeek_int()+"_category_" + i, null);
                time_color[i] = pref.getInt("day" + getDate(0) + "time"+getWeek_int()+"_color_" + i, 0);
                time_type[i] = pref.getInt("day" + getDate(0) + "time"+getWeek_int()+"_type_" + i, 1);
            }
        } else {
            int temp_index = 0;
            for (int i = 0; i < 100; i++) {
                if (item_add_day[i] != 0) {
                    if (item_add_day[i] > Integer.parseInt(getDate3(0))) {
                        temp_index = i;
                        i = 100;//탈출
                    }
                } else {
                    temp_index = i;
                    if (i == 0) {
                        //단 한개도 존재하지 않을때
                        //time1_??_i = 0 or null
                        for (int k = 0; k < 100; k++) {
                            body[k] = null;
                            time_category[k] = null;
                            time_color[k] = 0;
                            time_type[k] = 1;
                        }
                    }
                    i = 100;
                }
            }
            //////////////////////////////////////////////
            if (temp_index != 0) {
                if (item_add_day[temp_index - 1] == Integer.parseInt(getDate3(0))) {//날짜가 아예 같으면 요일도 같다
                    Log.d("검사검사10번","검사검사10번");
                    cal_week = item_add_day[temp_index-1];
                    for (int k = 0; k < 100; k++) {
                        body[k] = pref.getString(item_add_day[temp_index-1] + "time"+getWeek_int()+"_body_" + k, null);
                        time_category[k] = pref.getString(item_add_day[temp_index-1] + "time"+getWeek_int()+"_category_" + k, null);
                        time_color[k] = pref.getInt(item_add_day[temp_index-1] + "time"+getWeek_int()+"_color_" + k, 0);
                        time_type[k] = pref.getInt(item_add_day[temp_index-1] + "time"+getWeek_int()+"_type_" + k, 1);
                    }
                } else {
                    int restore_count = 0;
                    for (int i = temp_index - 1; i >= 0; i--) {
                        if (item_add_week[i] == 1) {
                            restore_count++;
                            //가장 최근 주간 설정(월요일)
                            //쉐어드에서 불러오기 날짜+time1_?_i
                            cal_week = item_add_day[i];
                            for (int k = 0; k < 100; k++) {
                                body[k] = pref.getString(item_add_day[i] + "time"+getWeek_int()+"_body_" + k, null);
                                time_category[k] = pref.getString(item_add_day[i] + "time"+getWeek_int()+"_category_" + k, null);
                                time_color[k] = pref.getInt(item_add_day[i] + "time"+getWeek_int()+"_color_" + k, 0);
                                time_type[k] = pref.getInt(item_add_day[i] + "time"+getWeek_int()+"_type_" + k, 1);
                            }
                            i = 0;//탈출
                        }
                        if(i == 0){
                            if(restore_count == 0){//날짜는 존재하지만 같은 요일이 없을때
                                for (int k = 0; k < 100; k++) {
                                    body[k] = null;
                                    time_category[k] = null;
                                    time_color[k] = 0;
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
                    body[k] = null;
                    time_category[k] = null;
                    time_color[k] = 0;
                    time_type[k] = 1;
                }
            }
            ////////////////////////////////////////////////////
        }
    }
    public void restore(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        if(pref != null){

            //time_point = pref.getInt("time_point",1);//1:과거 2:현재

            for(int i = 0; i<48; i++){
                //time_type_1[i] = pref.getInt("time_type_1"+i,1);//해당시간 칸수
                //body_1[i] = pref.getString("body_1"+i,null);//내용
                //time_type_2[i] = pref.getInt("time_type_2"+i,1);//해당시간 칸수
                //body_2[i] = pref.getString("body_2"+i,null);//내용
                //time_type_3[i] = pref.getInt("time_type_3"+i,1);//해당시간 칸수
                //body_3[i] = pref.getString("body_3"+i,null);//내용
                time_type[i] = pref.getInt("time_type_4"+i,1);//해당시간 칸수
                body[i] = pref.getString("body_4"+i,null);//내용
                //time_type_5[i] = pref.getInt("time_type_5"+i,1);//해당시간 칸수
                //body_5[i] = pref.getString("body_5"+i,null);//내용
                //time_type_6[i] = pref.getInt("time_type_6"+i,1);//해당시간 칸수
                //body_6[i] = pref.getString("body_6"+i,null);//내용
                //time_type_7[i] = pref.getInt("time_type_7"+i,1);//해당시간 칸수
                //body_7[i] = pref.getString("body_7"+i,null);//내용
                //time_color_1[i] = pref.getInt("time_color_1"+i,0);//카테고리 설정창에서 카테고리를 수정해도 안바뀜// 바뀔려면 add들어가서 다시 저장해야됨
                //time_color_2[i] = pref.getInt("time_color_2"+i,0);
                //time_color_3[i] = pref.getInt("time_color_3"+i,0);
                time_color[i] = pref.getInt("time_color_4"+i,0);
                //time_color_5[i] = pref.getInt("time_color_5"+i,0);
                //time_color_6[i] = pref.getInt("time_color_6"+i,0);
                //time_color_7[i] = pref.getInt("time_color_7"+i,0);
                //time_category_1[i] = pref.getString("time_category_1"+i,null);//리사이클러뷰
                //time_category_2[i] = pref.getString("time_category_2"+i,null);//리사이클러뷰
                //time_category_3[i] = pref.getString("time_category_3"+i,null);//리사이클러뷰
                time_category[i] = pref.getString("time_category_4"+i,null);//리사이클러뷰
                //time_category_5[i] = pref.getString("time_category_5"+i,null);//리사이클러뷰
                //time_category_6[i] = pref.getString("time_category_6"+i,null);//리사이클러뷰
                //time_category_7[i] = pref.getString("time_category_7"+i,null);//리사이클러뷰
            }

        }else{
            for(int i = 0; i<48; i++){
                time_type[i] = 1;
                body[i] = null;
                time_color[i] = 0;
                time_category[i] = null;
            }
        }
    }
}
