package org.techtown.timeschedule;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class Phone_record extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextView textView, textView2;
    private Handler handler;
    private boolean bool;
    private BroadcastReceiver scrOnReceiver;
    private BroadcastReceiver scrOffReceiver;
    private IntentFilter scrOnFilter;
    private IntentFilter scrOffFilter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_record);
        //MainActivity.ON = 1;
        toolbar = findViewById(R.id.appbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);

        textView = findViewById(R.id.service);
        textView2 = findViewById(R.id.service2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d("핸들러메세지","ㅇㅇ");
                SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                Long Start_time = pref.getLong("switch_start_time",0);
                Long save_time = pref.getLong("switch_save_time",0);
                Long First_start_time = pref.getLong("first_switch_start_time",0);
                if(MainActivity.ON == 0) {
                    Long Now_time = System.currentTimeMillis();
                    long time = (Now_time - Start_time + save_time)/1000;
                    long time2 = (Now_time - First_start_time)/1000;
                    Log.d("나우타임",Long.toString(Now_time));
                    Log.d("스타트타임",Long.toString(Start_time));
                    Log.d("세이브타임",Long.toString(save_time));
                    Log.d("냐우타임-스타트",Long.toString(Now_time-Start_time));
                    Log.d("냐우타임-스타트+세이브",Long.toString(Now_time-Start_time+save_time));

                    int int_time = (int)time;
                    int hour_time = int_time/3600;
                    int min_time = (int_time%3600)/60;
                    int sec_time = int_time%60;
                    textView.setText(hour_time+":"+min_time+":"+sec_time);
                    int int_time2 = (int)time2;
                    int hour_time2 = int_time2/3600;
                    int min_time2 = (int_time2%3600)/60;
                    int sec_time2 = int_time2%60;
                    textView2.setText(hour_time2+":"+min_time2+":"+sec_time2);
                }else{
                    textView.setText("측정x");
                    textView2.setText("측정x");
                }

                return false;
            }
        });

        bool = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (bool) {
                    if(MainActivity.ON == 1){
                        bool = false;
                    }
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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

    @Override
    protected void onPause() {
        super.onPause();
        bool = false;
    }
}
