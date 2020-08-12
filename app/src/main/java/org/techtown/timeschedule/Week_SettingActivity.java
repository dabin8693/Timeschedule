package org.techtown.timeschedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Week_SettingActivity extends AppCompatActivity {
    private RadioGroup radioGroup_time, radioGroup_before_after;
    private MaterialToolbar toolbar;
    private RadioButton radio_30, radio_60, radio_after, radio_before;
    private int id_30, id_60, before_after_initial, temp_before_after_initial, week_time_initial, temp_week_time_initial;
    private int id_after, id_before;
    private int start_time, end_time, week_calender_size, time_interval;
    private int temp_start_time, temp_end_time, temp_week_size, temp_interval, time_point;
    private TextView start_t, end_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_setting);

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {//인텐트 안씀
            Bundle bundle = intent.getExtras();
            before_after_initial = bundle.getInt("week_time_initial");
            week_time_initial = bundle.getInt("week_time_initial");
        }
        temp_before_after_initial = before_after_initial;
        temp_week_time_initial = week_time_initial;

        toolbar = findViewById(R.id.appbar_week_setting);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        restore();
        temp_interval = time_interval;

        Spinner week_spinner = (Spinner) findViewById(R.id.week_setting_week);
        ArrayAdapter<CharSequence> week_adapter = ArrayAdapter.createFromResource(this,
                R.array.week_setting_week, android.R.layout.simple_spinner_item);
        week_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        week_spinner.setAdapter(week_adapter);
        if (week_calender_size == 6) {
            week_spinner.setSelection(0);
        } else if (week_calender_size == 8) {
            week_spinner.setSelection(1);
        }
        week_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    week_calender_size = 6;
                }else if(position == 1){
                    week_calender_size = 8;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start_t = findViewById(R.id.week_setting_start);
        if (temp_interval == 1) {
            if (start_time % 2 == 0) {
                start_t.setText((start_time / 2) + ":00~" + (start_time / 2) + ":30");
            } else if (start_time % 2 == 1) {
                start_t.setText((start_time / 2) + ":30~" + (start_time / 2 + 1) + ":00");
            }
        } else if (temp_interval == 2) {
            start_t.setText((start_time / 2) + ":00~" + (start_time / 2 + 1) + ":00");
        }
        final String[] item_30 = getResources().getStringArray(R.array.setting_start_time_30);
        final String[] item_60 = getResources().getStringArray(R.array.setting_start_time_60);
        start_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp_interval == 1) {//30
                    new AlertDialog.Builder(Week_SettingActivity.this).setTitle("선택").setItems(item_30, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(Week_SettingActivity.this,"1번위치"+which,Toast.LENGTH_SHORT).show();
                            start_time = which;
                            Log.d("스타트타임",Integer.toString(start_time));
                            if (temp_interval == 1) {
                                if (start_time % 2 == 0) {
                                    start_t.setText((start_time / 2) + ":00~" + (start_time / 2) + ":30");
                                } else if (start_time % 2 == 1) {
                                    start_t.setText((start_time / 2) + ":30~" + (start_time / 2 + 1) + ":00");
                                }
                            } else if (temp_interval == 2) {
                                start_t.setText((start_time / 2) + ":00~" + (start_time / 2 + 1) + ":00");
                            }
                        }
                    }).setNegativeButton("닫기",null).show();

                } else if (temp_interval == 2) {//60
                    new AlertDialog.Builder(Week_SettingActivity.this).setTitle("선택").setItems(item_60, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(Week_SettingActivity.this,"2번위치"+which,Toast.LENGTH_SHORT).show();
                            start_time = which*2;
                            Log.d("스타트타임",Integer.toString(start_time));
                            if (temp_interval == 1) {
                                if (start_time % 2 == 0) {
                                    start_t.setText((start_time / 2) + ":00~" + (start_time / 2) + ":30");
                                } else if (start_time % 2 == 1) {
                                    start_t.setText((start_time / 2) + ":30~" + (start_time / 2 + 1) + ":00");
                                }
                            } else if (temp_interval == 2) {
                                start_t.setText((start_time / 2) + ":00~" + (start_time / 2 + 1) + ":00");
                            }
                        }
                    }).setNegativeButton("닫기",null).show();
                }
            }
        });

        end_t = findViewById(R.id.week_setting_end);
        if (temp_interval == 1) {
            if (end_time % 2 == 0) {
                end_t.setText((end_time / 2) + ":00~" + (end_time / 2) + ":30");
            } else if (end_time % 2 == 1) {
                end_t.setText((end_time / 2) + ":30~" + (end_time / 2 + 1) + ":00");
            }
        } else if (temp_interval == 2) {
            end_t.setText((end_time / 2) + ":00~" + (end_time / 2 + 1) + ":00");
        }

        end_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp_interval == 1) {//30
                    new AlertDialog.Builder(Week_SettingActivity.this).setTitle("선택").setItems(item_30, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(Week_SettingActivity.this,"1번위치"+which,Toast.LENGTH_SHORT).show();
                            end_time = which;
                            if (temp_interval == 1) {
                                if (end_time % 2 == 0) {
                                    end_t.setText((end_time / 2) + ":00~" + (end_time / 2) + ":30");
                                } else if (end_time % 2 == 1) {
                                    end_t.setText((end_time / 2) + ":30~" + (end_time / 2 + 1) + ":00");
                                }
                            } else if (temp_interval == 2) {
                                end_t.setText((end_time / 2) + ":00~" + (end_time / 2 + 1) + ":00");
                            }
                        }
                    }).setNegativeButton("닫기",null).show();

                } else if (temp_interval == 2) {//60
                    new AlertDialog.Builder(Week_SettingActivity.this).setTitle("선택").setItems(item_60, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(Week_SettingActivity.this,"2번위치"+which,Toast.LENGTH_SHORT).show();
                            end_time = which*2;
                            if (temp_interval == 1) {
                                if (end_time % 2 == 0) {
                                    end_t.setText((end_time / 2) + ":00~" + (end_time / 2) + ":30");
                                } else if (end_time % 2 == 1) {
                                    end_t.setText((end_time / 2) + ":30~" + (end_time / 2 + 1) + ":00");
                                }
                            } else if (temp_interval == 2) {
                                end_t.setText((end_time / 2) + ":00~" + (end_time / 2 + 1) + ":00");
                            }
                        }
                    }).setNegativeButton("닫기",null).show();
                }
            }
        });


        radioGroup_before_after = findViewById(R.id.week_group_0);
        radio_after = findViewById(R.id.week_after);
        radio_before = findViewById(R.id.week_before);
        id_after = 2;
        id_before = 1;
        //라디오 버튼 id설정
        radio_after.setId(id_after);
        radio_before.setId(id_before);
        //초기값 설정
        radioGroup_before_after.check(time_point);
        radioGroup_before_after.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//id 1 = 과거 id 2 = 미래
                temp_before_after_initial = checkedId;
                time_point = checkedId;//1:과거 2:현재
                Toast.makeText(Week_SettingActivity.this, "선택:" + checkedId, Toast.LENGTH_SHORT).show();
            }
        });

        radioGroup_time = findViewById(R.id.week_group);
        radio_30 = findViewById(R.id.week_30);
        radio_60 = findViewById(R.id.week_60);
        id_30 = 1;
        id_60 = 2;
        //라디오 버튼 id설정
        radio_30.setId(id_30);
        radio_60.setId(id_60);
        //초기값 설정
        //radioGroup_time.check(week_time_initial);
        radioGroup_time.check(time_interval);
        radioGroup_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//id 2 = 한시간 id 1 = 30분
                //temp_week_time_initial = checkedId;
                //Log.d("주간 세팅 설정값",Integer.toString(temp_week_time_initial));
                //Log.d("주간 세팅 설정값 원본",Integer.toString(checkedId));

                temp_interval = checkedId;
                Log.d("스타트타임",Integer.toString(start_time));
                if (temp_interval == 1) {
                    if (start_time % 2 == 0) {
                        start_t.setText((start_time / 2) + ":00~" + (start_time / 2) + ":30");
                    } else if (start_time % 2 == 1) {
                        start_t.setText((start_time / 2) + ":30~" + (start_time / 2 + 1) + ":00");
                    }
                } else if (temp_interval == 2) {
                    start_t.setText((start_time / 2) + ":00~" + (start_time / 2 + 1) + ":00");
                }

                if (temp_interval == 1) {
                    if (end_time % 2 == 0) {
                        end_t.setText((end_time / 2) + ":00~" + (end_time / 2) + ":30");
                    } else if (end_time % 2 == 1) {
                        end_t.setText((end_time / 2) + ":30~" + (end_time / 2 + 1) + ":00");
                    }
                } else if (temp_interval == 2) {
                    end_t.setText((end_time / 2) + ":00~" + (end_time / 2 + 1) + ":00");
                }

                if(temp_interval == 2){//30분에서 1시간으로 바꼈을때 1:30 -> 1:00 로 바뀌면 end_time 위치값이 바껴 예외처리
                    if(end_time%2 == 1){
                        end_time -= 1;
                    }
                }
                Toast.makeText(Week_SettingActivity.this, "선택:" + checkedId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void save() {
        time_interval = temp_interval;
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("start_time", start_time);
        editor.putInt("end_time", end_time);
        editor.putInt("time_type", time_interval);
        editor.putInt("week_calender_size", week_calender_size);
        editor.putInt("time_point",time_point);
        editor.commit();
    }

    public void restore() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {
            time_point = pref.getInt("time_point",1);//없으면 과거로 초기값
            for (int i = 0; i < 48; i++) {
                start_time = pref.getInt("start_time", 16);//0시0분 = 1, 0시30분 = 2;
                end_time = pref.getInt("end_time", 42);//0시0분 = 1, 0시30분 = 2;
                time_interval = pref.getInt("time_type", 2);//30분 = 1, 1시간 = 2;
                week_calender_size = pref.getInt("week_calender_size", 6);//6 = 월~금, 8 = 월~일
            }
        } else {//저장된게 없으면 초기값
            start_time = 16;
            end_time = 42;
            time_interval = 2;
            week_calender_size = 6;
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
        if (id == 2131296339) {//check2131296339
            if (start_time > end_time) {
                Toast.makeText(Week_SettingActivity.this, "시간을 다시설정해주세요", Toast.LENGTH_SHORT).show();
            } else {
                save();
                finish();
            }
            /*
            Log.d("주간 설정창 인텐트시작","ㅇㄴㄹ");
            Intent intent = new Intent(Week_SettingActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("week_setting_A",1);
            intent.putExtra("temp_week_time_initial",temp_week_time_initial);
            intent.putExtra("temp_before_after_initial",temp_before_after_initial);
            Log.d("주간 세팅 설정창 나갈떄 값",Integer.toString(temp_week_time_initial));
            startActivity(intent);

             */
        } else if (id == 16908332) {//back
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
