package org.techtown.timeschedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class ForegroundService extends Service {
    private ServiceThread thread = null;
    private myServiceHandler handler;
    private int count;
    private long Now_time;
    private NotificationChannel channel;
    private String CHANNEL_ID;
    private String before_time;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        Log.d("온크리","ㅇㅈㅇㅈ");

        foreground();
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void foreground(){
        Log.d("foreground","ㅇㅈㅇㅈ");
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler,getApplicationContext());
        thread.start();
        CharSequence name = "사용시간";
        String description = "휴대폰사용시간";
        int importance = NotificationManager.IMPORTANCE_MIN;//알림음 상태창 설정
        CHANNEL_ID = "channel_id";
        channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        channel.enableLights(false);
        channel.setShowBadge(false);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("휴대폰 사용기록 측정중")
                .setContentText("휴대폰 사용시간:"+format(0,0,0))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setOngoing(true)//노티피케이션이 고정되며 알림이 여러번 안온다
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);
        startForeground(1,builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("온스타트커맨드","ㅇㅈㅇㅈ");

        return START_STICKY;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("온바인드","ㅇㅈㅇㅈ");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("온디스트로이","ㅇㅈㅇㅈ");
        super.onDestroy();
    }

    public String format(int hour, int min, int sec) {//2020.8.11

        StringBuffer sbDate = new StringBuffer();

        if (hour < 10)
            sbDate.append("0");
        sbDate.append(hour);
        sbDate.append(":");
        if (min < 10)
            sbDate.append("0");
        sbDate.append(min);
        sbDate.append(":");
        if (sec < 10)
            sbDate.append("0");
        sbDate.append(sec);


        return sbDate.toString();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    public class myServiceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            count++;
            SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
            Long save_time = pref.getLong("switch_save_time",0);
            Now_time = System.currentTimeMillis();
            //Log.d("냐우타임",Long.toString(Now_time));
            //Log.d("스타트타임",Long.toString(thread.Start_time));
            long time = (Now_time - thread.Start_time + save_time)/1000;
            int int_time = (int)time;
            int hour_time = int_time/3600;
            int min_time = (int_time%3600)/60;
            int sec_time = int_time%60;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("휴대폰 사용기록 측정중")
                        .setContentText("휴대폰 사용시간:"+format(hour_time,min_time,sec_time))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setOngoing(true)//노티피케이션이 고정되며 알림이 여러번 안온다
                        .setOnlyAlertOnce(true)
                        .setAutoCancel(true);

                startForeground(1,builder.build());
                //Log.d("서비스 카운트는",Integer.toString(count));
                if(before_time == null){
                    Calendar temp = Calendar.getInstance();
                    int nYear = temp.get(Calendar.YEAR);
                    int nMonth = temp.get(Calendar.MONTH) + 1;
                    int nDay = temp.get(Calendar.DAY_OF_MONTH);
                    int nhour = temp.get(Calendar.HOUR_OF_DAY);
                    int nmin = temp.get(Calendar.MINUTE);
                    before_time = getDate_h_m(nYear, nMonth, nDay, nhour, nmin);
                }
                if(pref.getInt("screen",1) == 0){//휴대폰이 꺼져있으면
                    Calendar temp = Calendar.getInstance();
                    int nYear = temp.get(Calendar.YEAR);
                    int nMonth = temp.get(Calendar.MONTH) + 1;
                    int nDay = temp.get(Calendar.DAY_OF_MONTH);
                    int nhour = temp.get(Calendar.HOUR_OF_DAY);
                    int nmin = temp.get(Calendar.MINUTE);
                    before_time = getDate_h_m(nYear, nMonth, nDay, nhour, nmin);
                }else {
                    time_add();
                }
            }
        }
    }

    public void time_add(){

        Calendar temp = Calendar.getInstance();
        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);
        int nhour = temp.get(Calendar.HOUR_OF_DAY);//HOUR_OF_DAY=0~24//hour=0~12
        int nmin = temp.get(Calendar.MINUTE);
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int time_count = pref.getInt("day"+getDate0(nYear, nMonth, nDay)+"time_usephone_"+time_index_m(nhour, nmin),0);
        if(before_time != null){
            Long nowtime_l = Long.parseLong(getDate_h_m(nYear, nMonth, nDay, nhour, nmin));
            String nowtime_s = Long.toString(nowtime_l);
            Log.d("비포타임",before_time);
            Log.d("냐우타임",nowtime_s);
            if(Long.parseLong(before_time)+1 == nowtime_l){//전시간보다1분 지났을때
                time_count += 1;
                editor.putInt("day"+getDate0(nYear, nMonth, nDay)+"time_usephone_"+time_index_m(nhour, nmin), time_count);
                //"day+2020.08.24+time_usephone_i(시간) = 횟수(30이 최대)
                editor.commit();
                Log.d("잘저장되어있는지",pref.getInt("day"+getDate0(nYear, nMonth, nDay)+"time_usephone_"+time_index_m(nhour, nmin),0)+"");
                Log.d("잘저장시간",time_index_m(nhour, nmin)+"");
                before_time = nowtime_s;
                Log.d("타임카운터",Integer.toString(time_count));
            }
        }
    }

    public String getDate0(int nYear, int nMonth, int nDay) {//2020.08.11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


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

    public String getDate_h_m(int nYear, int nMonth, int nDay, int nhour, int nmin){

        StringBuffer sbDate = new StringBuffer();


        sbDate.append(nYear);
        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);
        if(nhour<10)
            sbDate.append("0");
        sbDate.append(nhour);
        if(nmin<10)
            sbDate.append("0");
        sbDate.append(nmin);
        //Log.d("시간은??",sbDate.toString());
        return sbDate.toString();
    }

    public int time_index_m(int nHour, int nMin){

        int time_index;
        if(nMin<30){
            time_index = nHour*2;
        }else{
            time_index = (nHour*2)+1;
        }
        Log.d("타임 인덱스",Integer.toString(time_index));
        return time_index;
    }

}
