package org.techtown.timeschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

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

        setting_switch = findViewById(R.id.setting_switch);
        //초기값 설정
        setting_switch.setChecked(help_alarm);
        setting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                temp_help_alarm = isChecked;
            }
        });
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
        radioGroup.check(start_frag_position);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//id 1 = 한시간 id 2 = 30분
                temp_start_frag_position = checkedId;
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

        if(login == 1){
            container = findViewById(R.id.setting_container);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.setting_item, container, true);
            setting_logout = container.findViewById(R.id.setting_logout);
            setting_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//다이얼로그
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("setting_A_logout",1);
                    intent.putExtra("login", 0);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.wekk_setting_app_bar, menu);
        //MenuItem check_btn = menu.findItem(R.id.appbar_week_check);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if(id == 2131296339) {//check
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("setting_A",1);
            intent.putExtra("temp_start_frag_position",temp_start_frag_position);
            intent.putExtra("temp_help_alarm", temp_help_alarm);
            Log.d("설설정 액티비티안에서 끝",Integer.toString(temp_start_frag_position));
            startActivity(intent);
        }else if(id == 16908332){//back
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
