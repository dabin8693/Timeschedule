package org.techtown.timeschedule;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frag_month extends Fragment implements LocationListener {
    toolbar_callback toolbar_callback;
    private CalendarView calendarView;
    private TextView month_day_t, month_weather_temp;
    private ImageView month_weather;
    private Calendar cal, test_cal;
    private FloatingActionButton category_float;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private month_adapter adapter;
    private SimpleDateFormat sdate;
    private Date date;
    private String str_time;//2020.08.16
    private String[] time_body, time_category, time_start, time_end;
    private int[] time_color, time_type;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private String today_icon;
    private int today_temp;
    private Handler handler;
    private String choice_day;


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
        choice_day = getDate(0);

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
                Log.d("현재날짜",getDate(0));//2020.8.11
                Log.d("변경날짜",year+"."+(month+1)+"."+dayOfMonth);//2020.8.11
                choice_day = year+"."+(month+1)+"."+dayOfMonth;
                if(getDate(0).equals(year+"."+(month+1)+"."+dayOfMonth) == false){
                    month_weather.setImageBitmap(null);
                    month_weather_temp.setText("");
                }else{
                    SharedPreferences pref = getActivity().getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                    today_icon = pref.getString("weather"+getDate0(0),null);
                    today_temp = pref.getInt("weathertemp"+getDate0(0),100);
                    if(today_icon != null){
                        int lid = getActivity().getResources().getIdentifier(today_icon,"drawable",getActivity().getPackageName());
                        month_weather.setImageResource(lid);
                    }else{
                        Log.d("널임","ㅇㅇ");
                    }
                    if(today_temp != 100){
                        month_weather_temp.setText(Integer.toString(today_temp)+"°");
                    }else{
                        Log.d("널임1","ㅇㅇ");
                    }
                }
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

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d("핸들러","ㅇㅇ");
                if(choice_day.equals(getDate(0))){
                    Log.d("핸들러1","ㅇㅇ");
                    if(today_icon != null){
                        int lid = getActivity().getResources().getIdentifier(today_icon,"drawable",getActivity().getPackageName());
                        month_weather.setImageResource(lid);
                    }else{
                        Log.d("널임","ㅇㅇ");
                    }
                    if(today_temp != 100){
                        month_weather_temp.setText(Integer.toString(today_temp)+"°");
                    }else{
                        Log.d("널임1","ㅇㅇ");
                    }
                }
                return false;
            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        month_weather = view.findViewById(R.id.month_weather);
        month_weather_temp = view.findViewById(R.id.month_weather_temp);
        requestLocation();
        SharedPreferences pref = getActivity().getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        today_icon = pref.getString("weather"+getDate0(0),null);
        today_temp = pref.getInt("weathertemp"+getDate0(0),100);
        Log.d("불러온값",pref.getString("weather"+getDate0(0),""));
        if(today_icon != null){
            int lid = this.getResources().getIdentifier(today_icon,"drawable",getActivity().getPackageName());
            month_weather.setImageResource(lid);
        }else{
            Log.d("널임","ㅇㅇ");
        }
        if(today_temp != 100){
            month_weather_temp.setText(Integer.toString(today_temp)+"°");
        }else{
            Log.d("널임1","ㅇㅇ");
        }
        //test_cal = Calendar.getInstance();
        //Date test_date = new Date();
        //test_date.setTime(1598331600);
        //test_cal.setTime(test_date);
        //test_cal.getTime();
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

    private void requestLocation() {
        //사용자로 부터 위치정보 권한체크
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

        }
    }


    private void getWeather(double latitude, double longitude) {
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        final ApiService apiService = retrofit.create(ApiService.class);
        int int_lat = (int)latitude;
        int int_lon = (int)longitude;
        Log.d("int_lat",Integer.toString(int_lat));
        Log.d("int_lon",Integer.toString(int_lon));

        Call<JsonObject> call = apiService.getCurrentWeatherData(Integer.toString(int_lat), Integer.toString(int_lon), "9e40023c77b305ca158da11f5fe58017");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(">>??","dsfsf");
                Log.d(">>??",response.message());
                Log.d(">>??","dsfsf");
                if (response.isSuccessful()){
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    Log.d("날씨정보","널임");
                    if (object != null) {
                        //데이터가 null 이 아니라면 날씨 데이터를 텍스트뷰로 보여주기
                        //month_weather.setText(object.toString());
                        Log.d("날씨정보",object.toString());
                        JsonArray jsonArrayicon = (JsonArray)object.get("weather");
                        JsonObject jsonObjecticon = (JsonObject)jsonArrayicon.get(0);

                        JsonObject jsonObjecttemp = (JsonObject) object.get("main");
                        String temp = jsonObjecttemp.get("temp")+"";
                        today_temp = (int)(Float.parseFloat(temp) - 273.15);

                        Log.d("날씨는:",object.get("weather")+"");
                        Log.d("날씨의메인은:",jsonObjecticon.get("icon")+"");
                        SharedPreferences pref = getActivity().getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        today_icon = ""+jsonObjecticon.get("icon");
                        today_icon = today_icon.substring(1,4);
                        today_icon = "a"+today_icon;
                        Log.d("today아이콘",today_icon);
                        Log.d("절대온도",temp);
                        Log.d("온도",Integer.toString(today_temp));
                        editor.putInt("weathertemp"+getDate0(0),today_temp);
                        editor.putString("weather"+getDate0(0),today_icon);
                        editor.commit();
                        handler.sendEmptyMessage(0);
                        Log.d("들어간값",pref.getString("weather"+getDate0(0),""));

                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                //t.getMessage();
                Log.d("래트로핏실패",t.getMessage());
            }
        });
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        Log.d("현재날짜1",getDate(0));
        Log.d("변경날짜1",year+"."+month+"."+day);
        choice_day = year+"."+month+"."+day;
        if(getDate(0).equals(year+"."+month+"."+day) == false){
            month_weather.setImageBitmap(null);
            month_weather_temp.setText("");
        }else{
            SharedPreferences pref = getActivity().getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
            today_icon = pref.getString("weather"+getDate0(0),null);
            today_temp = pref.getInt("weathertemp"+getDate0(0),100);
            if(today_icon != null){
                int lid = getActivity().getResources().getIdentifier(today_icon,"drawable",getActivity().getPackageName());
                month_weather.setImageResource(lid);
            }else{
                Log.d("널임","ㅇㅇ");
            }
            if(today_temp != 100){
                month_weather_temp.setText(Integer.toString(today_temp)+"°");
            }else{
                Log.d("널임1","ㅇㅇ");
            }
        }
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

    @Override
    public void onLocationChanged(Location location) {
/*현재 위치에서 위도경도 값을 받아온뒤 우리는 지속해서 위도 경도를 읽어올것이 아니니
        날씨 api에 위도경도 값을 넘겨주고 위치 정보 모니터링을 제거한다.*/
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //위도 경도 텍스트뷰에 보여주기
        //latText.setText(String.valueOf(latitude));
        //lonText.setText(String.valueOf(longitude));
        Log.d("latitudeㅇㅇ",String.valueOf(latitude));
        Log.d("longitudeㅇㅇ",String.valueOf(longitude));
        //날씨 가져오기 통신
        getWeather(latitude, longitude);
        //위치정보 모니터링 제거
        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
