package org.techtown.timeschedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ForegroundService extends Service {
    private ServiceThread thread = null;
    private myServiceHandler handler;
    private int count;
    private long Now_time;
    @Override
    public void onCreate() {
        Log.d("온크리","ㅇㅈㅇㅈ");
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        if(pref.getBoolean("switch",false) == true){
            MainActivity.ON = 0;
        }else{
            MainActivity.ON = 1;
        }
        myServiceHandler handler = new myServiceHandler();
        thread = null;
        thread = new ServiceThread(handler,getApplicationContext());
        thread.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("온스타트커맨드","ㅇㅈㅇㅈ");
        //if(thread == null) {
            Log.d("온스타트커맨드","null");
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        if(pref.getBoolean("switch",false) == true){
            MainActivity.ON = 0;

        }else{
            MainActivity.ON = 1;
        }
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler, getApplicationContext());
        thread.start();
        //}
        return START_STICKY;
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
        //if(thread == null) {
            Log.d("온디스트로이","null");
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        if(pref.getBoolean("switch",false) == true){
            MainActivity.ON = 0;

        }else{
            MainActivity.ON = 1;
        }
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler, getApplicationContext());
        thread.start();
        //}
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
            Log.d("냐우타임",Long.toString(Now_time));
            Log.d("스타트타임",Long.toString(thread.Start_time));
            long time = (Now_time - thread.Start_time + save_time)/1000;
            int int_time = (int)time;
            int hour_time = int_time/3600;
            int min_time = (int_time%3600)/60;
            int sec_time = int_time%60;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "name";
                String description = "description";
                int importance = NotificationManager.IMPORTANCE_LOW;//알림음 상태창 설정
                String CHANNEL_ID = "channel_id";
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
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
                        .setContentText(hour_time+":"+min_time+":"+sec_time)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(getApplicationContext());

                // notificationId is a unique int for each notification that you must define
                notificationManager1.notify(1, builder.build());
                Log.d("서비스 카운트는",Integer.toString(count));
            }
        }
    }
}
