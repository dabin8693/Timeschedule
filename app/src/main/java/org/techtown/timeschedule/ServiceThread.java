package org.techtown.timeschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

public class ServiceThread extends Thread {
    ForegroundService.myServiceHandler handler;
    boolean isRun = true;
    long Start_time, restart_time;
    Context context;
    private BroadcastReceiver scrOnReceiver;
    private BroadcastReceiver scrOffReceiver;
    private IntentFilter scrOnFilter;
    private IntentFilter scrOffFilter;
    private int Oncount, Offcount;

    public ServiceThread(ForegroundService.myServiceHandler handler, Context context) {
        this.handler = handler;
        this.context = context;
        SharedPreferences pref = context.getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("offcount",0);
        editor.putInt("oncount",0);
        editor.commit();
        scrOnReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Offcount = 0;
                SharedPreferences pref = context.getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("offcount",0);
                editor.commit();
                if(pref.getBoolean("switch",false) == false){

                }else{
                    if(pref.getInt("oncount",1) == 0) {
                        //Oncount++;
                        restart_time = System.currentTimeMillis();
                        editor.putLong("switch_start_time", restart_time);
                        editor.putInt("oncount",1);
                        editor.commit();
                        Log.d("리시버e", "SCREEN ON");
                    }
                }
                Log.d("리시버", "SCREEN ON" );
            }
        };
        scrOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Oncount = 0;
                SharedPreferences pref = context.getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("oncount",0);
                editor.commit();
                if(pref.getBoolean("switch",false) == false){

                }else{
                    if(pref.getInt("offcount",1) == 0) {
                        //Offcount++;
                        Long before_time = pref.getLong("switch_start_time", 0);//스위치 처음켰을때일수도 있고 다음에 화면을 껏다가 켰을때 시간일수도 있다.
                        Long save_time = pref.getLong("switch_save_time", 0);
                        Long time = save_time + System.currentTimeMillis() - before_time;
                        Log.d("ㅈㅈ세이브타임", Long.toString(save_time));
                        Log.d("ㅈㅈbefore타임", Long.toString(before_time));
                        editor.putLong("switch_save_time", time);
                        editor.putInt("offcount",1);
                        editor.commit();
                        Log.d("리시버e", "SCREEN OFF");
                    }
                }
                Log.d("리시버", "SCREEN OFF");
            }
        };
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {

        scrOnFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        scrOffFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

        context.registerReceiver(scrOnReceiver, scrOnFilter);
        context.registerReceiver(scrOffReceiver, scrOffFilter);
        SharedPreferences pref = context.getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        while (isRun) {
            Start_time = pref.getLong("switch_start_time",0);
            if(pref.getBoolean("switch",false) == false){
                isRun = false;
            }
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (Exception e){

            }
        }
        scrOffFilter = null;
        scrOnFilter = null;
        scrOffReceiver = null;
        scrOnReceiver = null;
    }
}
