package org.techtown.timeschedule;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Day_expandActivity extends AppCompatActivity {
    private TextView start_t, end_t, category_t, body_t, week_t;
    private ImageView category_img;
    private String day_s, week_s, category_s, body_s;
    private int color, type, time;
    private GradientDrawable drawable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_expand);

        final Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            day_s = bundle.getString("day","");
            week_s = bundle.getString("week","");
            category_s = bundle.getString("category","");
            body_s = bundle.getString("body","");
            color = bundle.getInt("color",0);
            type = bundle.getInt("type",0);
            time = bundle.getInt("time",0);
        }

        start_t = findViewById(R.id.start_time_t);
        if(time%2 == 0){
            start_t.setText(time/2+":00");
        }else{
            start_t.setText(time/2+":30");
        }
        end_t = findViewById(R.id.end_time_t);
        if(time%2 == 0){
            end_t.setText((time+type)/2+":30");
        }else{
            end_t.setText((time+type)/2+":00");
        }
        category_t = findViewById(R.id.category_t);
        category_t.setText(category_s);
        body_t = findViewById(R.id.body_t);
        body_t.setText(body_s);
        week_t = findViewById(R.id.week_t);
        week_t.setText(week_s);
        category_img = findViewById(R.id.category_circle);
        //category_img.setBackgroundColor(color);
        drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circle_style);
        drawable.setColor(color);
        category_img.setImageDrawable(drawable);//초기값
    }
}
