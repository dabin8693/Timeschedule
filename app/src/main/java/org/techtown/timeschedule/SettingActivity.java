package org.techtown.timeschedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private MaterialToolbar toolbar;
    private RadioButton radio_month, radio_week, radio_day;
    private int id_month, id_week, id_day;
    private ImageView setting_category, setting_logout;
    private String[] emaillist, passwordlist;
    private int login;
    private LayoutInflater inflater;
    private LinearLayout container;
    private int start_frag_position, temp_start_frag_position;
    private boolean help_alarm, temp_help_alarm;
    private Switch setting_switch;
    private int start_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            login = bundle.getInt("login");
            start_frag_position = bundle.getInt("start_frag_position");
            help_alarm = bundle.getBoolean("help_alarm");
            Log.d("설설정 액티비티안에서 처음",Integer.toString(temp_start_frag_position));
        }
        temp_start_frag_position = start_frag_position;
        temp_help_alarm = help_alarm;

        toolbar = findViewById(R.id.appbar_setting);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        restore();

        radioGroup = findViewById(R.id.setting_group);
        radio_month = findViewById(R.id.setting_month);
        radio_week = findViewById(R.id.setting_week);
        radio_day = findViewById(R.id.setting_day);
        id_month = 1;
        id_week = 2;
        id_day = 3;
        //라디오 버튼 id설정
        radio_month.setId(id_month);
        radio_week.setId(id_week);
        radio_day.setId(id_day);
        //초기값 설정
        radioGroup.check(start_position);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//id 1 = 한시간 id 2 = 30분
                temp_start_frag_position = checkedId;
                start_position = checkedId;
                save();
                Log.d("설설정 액티비티안에서 체인지",Integer.toString(temp_start_frag_position));
                Log.d("설설정 액티비티안에서 체인지 원본",Integer.toString(checkedId));
                Toast.makeText(SettingActivity.this,"선택:"+checkedId,Toast.LENGTH_SHORT).show();
            }
        });

        setting_category = findViewById(R.id.setting_category);
        setting_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });


    }

    public void save(){
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("start_position",start_position);
        editor.commit();
    }
    public void restore(){
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        start_position = pref.getInt("start_position",1);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if(id == 2131296339) {//check

        }else if(id == 16908332){//back
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
