package org.techtown.timeschedule;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements toolbar_callback {

    private DrawerLayout main_drawer_main;
    private View main_drawer_menu;
    static int ON;
    private boolean checked;
    private Long Start_time;
    private MaterialToolbar toolbar;
    private String t_month, t_year;
    private LayoutInflater inflater, inflater_login, inflater_month;
    private View title_text_v, month_v, title_text_v2;
    private TextView title, side_month, side_week, side_day, title2, side_phone, side_category;
    private CalendarView calendarView;
    private LinearLayout container, main_linear;
    private ImageView setting, my;
    private CoordinatorLayout main_coor;
    private frag_month frag_month;
    private frag_week frag_week;
    private frag_day frag_day;
    private int login;
    private final int SELECT_IMAGE = 1;
    private final int SELECT_CARMER = 2;
    private final int CROP_FROM_CAMERA = 3;
    private String imageFilePath;
    private Uri photoUri;
    private InputStream inputStream;
    private String[] emaillist, passwordlist;
    private int start_frag_position, week_time_initial, before_after_initial;
    private boolean help_alarm;
    private String nowemail;
    private TextView sideemail;
    private int onnewintent_check;
    private Bitmap image_bitmap;
    private int bitmap_reset;
    static int width_size_main;
    private int color_check;
    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ON = 1;
        checked = false;
        emaillist = new String[100];
        passwordlist = new String[100];

        final Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            login = bundle.getInt("login");
            emaillist = bundle.getStringArray("emaillist");
            passwordlist = bundle.getStringArray("passwordlist");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        width_size_main = size.x;

        //reset();
/*
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
*/

        //첫화면 월간 달력 프래그먼트
        //프래그먼트 뷰 수정시 inflate하고 해야됨
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag_month = new frag_month(this);
        transaction.replace(R.id.main_frame, frag_month);
        //transaction.addToBackStack(null);//백버튼 눌렀을때
        transaction.commit();
        ///////////////////////////////////////
        //툴바 코드 시작
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        t_month = monthFormat.format(currentTime);
        t_year = yearFormat.format(currentTime);
        int month_int = Integer.parseInt(t_month.substring(0,1));
        if(month_int == 0) {//1~9월
            t_month = t_month.substring(1, 2);
        }else{
            //month 그대로
        }
        toolbar = findViewById(R.id.appbar);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        getSupportActionBar().setDisplayShowCustomEnabled(true);//커스텀 허용
        getSupportActionBar().setDisplayShowTitleEnabled(false);//타이틀 가리기
        inflater = LayoutInflater.from(this);
        //월간 타이틀뷰
        title_text_v = inflater.inflate(R.layout.titleview,null);
        title = title_text_v.findViewById(R.id.title);
        title.setText(t_month+"월 "+t_year+"년");
        getSupportActionBar().setCustomView(title_text_v);//가려진 타이틀에 커스텀뷰(제목) 장착
        title_text_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//날짜
                Dialog_DatePicker();
                //Log.d("캘린더:",Long.toString(calendarView.getDate()));
            }
        });
        //주,일간 타이틀 뷰
        title_text_v2 = inflater.inflate(R.layout.titleview2,null);
        title2 = title_text_v2.findViewById(R.id.title2);
        //툴바 코드 종료
        ////////////////////////////////////////
        //month inflate 시작 해당 프래그먼트 페이지에서만 코드가 된다
        inflater_month = LayoutInflater.from(this);
        month_v = inflater_month.inflate(R.layout.frag_month,null);
        calendarView = month_v.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
        //드로어 레이아웃 사이드메뉴 로그인,로그아웃
        //네비게이션바 뷰 코드 시작
        container = findViewById(R.id.menu_container);
        if(login == 0) {
            container.removeAllViewsInLayout();
            inflater_login = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater_login.inflate(R.layout.logout_item, container, true);
            Button login = container.findViewById(R.id.sidelogin);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("emaillist",emaillist);
                    intent.putExtra("passwordlist",passwordlist);
                    startActivity(intent);
                }
            });
        }else{
            container.removeAllViewsInLayout();
            inflater_login = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater_login.inflate(R.layout.login_item, container, true);
            sideemail = container.findViewById(R.id.sideemail);
            sideemail.setText(nowemail);
            my = container.findViewById(R.id.sideinform);
            my.setBackground(new ShapeDrawable(new OvalShape()));
            my.setClipToOutline(true);
            if(image_bitmap == null) {
                my.setImageResource(R.drawable.ic_launcher_foreground);
            }else{
                if(bitmap_reset == 0) {
                    my.setImageBitmap(image_bitmap);
                }else{
                    bitmap_reset = 0;
                }
            }
            my.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String str[] = {"카메라","앨범","기본이미지"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("선택")
                            .setNegativeButton("취소",null)
                            .setItems(str, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which == 0){
                                        doSelectCarmer();
                                    }else if(which == 1){
                                        doSelectImage();
                                    }else if(which == 2){
                                        bitmap_reset = 1;
                                        my.setImageResource(R.drawable.ic_launcher_foreground);
                                    }
                                    Toast.makeText(getApplicationContext(),"선택 "+str[which]+"위치 "+Integer.toString(which),Toast.LENGTH_LONG).show();
                                }
                            });
                    //builder.show();
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
        main_drawer_main = findViewById(R.id.main_drawer_main);
        main_drawer_menu = findViewById(R.id.main_drawer_menu);

        side_month = findViewById(R.id.side_month);
        side_week = findViewById(R.id.side_week);
        side_day = findViewById(R.id.side_day);

        main_linear = findViewById(R.id.main_linear);
        main_coor = findViewById(R.id.main_coor);
        side_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_text_v2.setVisibility(View.INVISIBLE);
                title_text_v.setVisibility(View.VISIBLE);
                side_month.setTextColor(Color.parseColor("#FF000000"));
                side_phone.setTextColor(Color.parseColor("#60000000"));
                side_day.setTextColor(Color.parseColor("#60000000"));
                side_week.setTextColor(Color.parseColor("#60000000"));
                side_category.setTextColor(Color.parseColor("#60000000"));
                color_check = 0;
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                t_month = monthFormat.format(currentTime);
                t_year = yearFormat.format(currentTime);
                int month_int = Integer.parseInt(t_month.substring(0,1));
                if(month_int == 0) {//1~9월
                    t_month = t_month.substring(1, 2);
                }else{
                    //month 그대로
                }
                title.setText(t_month+"월 "+t_year+"년");
                getSupportActionBar().setCustomView(title_text_v);//가려진 타이틀에 커스텀뷰(제목) 장착
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag_month = new frag_month(MainActivity.this);
                transaction.replace(R.id.main_frame, frag_month);
                //transaction.addToBackStack(null);//백버튼 눌렀을때
                transaction.commit();

                main_drawer_main.closeDrawers();
            }
        });
        side_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressDialog = new ProgressDialog(MainActivity.this);
                //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //progressDialog.setMessage("잠시 기다려 주세요.");
                //progressDialog.show();
                title_text_v.setVisibility(View.INVISIBLE);
                title_text_v2.setVisibility(View.VISIBLE);
                title2.setText("주간시간표");
                color_check = 1;
                side_week.setTextColor(Color.parseColor("#FF000000"));
                side_phone.setTextColor(Color.parseColor("#60000000"));
                side_day.setTextColor(Color.parseColor("#60000000"));
                side_month.setTextColor(Color.parseColor("#60000000"));
                side_category.setTextColor(Color.parseColor("#60000000"));
                getSupportActionBar().setCustomView(title_text_v2);//가려진 타이틀에 커스텀뷰(제목) 장착
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag_week = new frag_week(progressDialog);
                transaction.replace(R.id.main_frame, frag_week);
                //transaction.addToBackStack(null);//백버튼 눌렀을때
                transaction.commit();

                main_drawer_main.closeDrawers();
            }
        });
        side_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_text_v.setVisibility(View.INVISIBLE);
                title_text_v2.setVisibility(View.VISIBLE);
                title2.setText("일간시간표");
                color_check = 2;
                side_day.setTextColor(Color.parseColor("#FF000000"));
                side_phone.setTextColor(Color.parseColor("#60000000"));
                side_week.setTextColor(Color.parseColor("#60000000"));
                side_month.setTextColor(Color.parseColor("#60000000"));
                side_category.setTextColor(Color.parseColor("#60000000"));
                getSupportActionBar().setCustomView(title_text_v2);//가려진 타이틀에 커스텀뷰(제목) 장착
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag_day = new frag_day();
                transaction.replace(R.id.main_frame, frag_day);
                //transaction.addToBackStack(null);//백버튼 눌렀을때
                transaction.commit();

                main_drawer_main.closeDrawers();
            }
        });
        setting = findViewById(R.id.side_set);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //설정창
                Log.d("설설정 메인 인텐트 시작",Integer.toString(start_frag_position));
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("start_frag_position",start_frag_position);
                intent.putExtra("help_alarm",help_alarm);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
        side_category = findViewById(R.id.side_category);
        side_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_check = 3;
                side_category.setTextColor(Color.parseColor("#FF000000"));
                side_day.setTextColor(Color.parseColor("#60000000"));
                side_week.setTextColor(Color.parseColor("#60000000"));
                side_phone.setTextColor(Color.parseColor("#60000000"));
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        side_phone = findViewById(R.id.side_phone);
        side_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_check = 4;
                side_phone.setTextColor(Color.parseColor("#FF000000"));
                side_day.setTextColor(Color.parseColor("#60000000"));
                side_week.setTextColor(Color.parseColor("#60000000"));
                side_category.setTextColor(Color.parseColor("#60000000"));
                Intent intent = new Intent(MainActivity.this, Phone_record.class);
                startActivity(intent);
            }
        });
        //네비게이션바 뷰 코드 종료
        ////////////////////////////////////////
        color_check = 0;
        if(color_check == 0){
            side_month.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 1){
            side_week.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 2){
            side_day.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 3){
            side_category.setTextColor(Color.parseColor("#FF000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 4){
            side_phone.setTextColor(Color.parseColor("#FF000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //툴바 메뉴 inflate
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_app_bar, menu);
        MenuItem switchOnOffItem = menu.findItem(R.id.switchOnOffItem);
        switchOnOffItem.setActionView(R.layout.switch_layout);

        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        boolean start_switch = pref.getBoolean("switch",false);
        checked = start_switch;
        Switch switchOnOff = switchOnOffItem.getActionView().findViewById(R.id.switchOnOff);
        switchOnOff.setChecked(start_switch);
        if(start_switch == false){
            ON = 1;
        }else{
            Intent intent = new Intent(MainActivity.this, ForegroundService.class);
            startService(intent);
        }
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//스위치
                SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("switch",isChecked);
                editor.commit();
                checked = isChecked;
                if(isChecked == true){
                    Start_time = System.currentTimeMillis();
                    Date date = new Date(Start_time);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
                    String formatDate = sdfNow.format(date);
                    editor.putString("first_switch_start_time_string",formatDate);
                    editor.putLong("switch_start_time",Start_time);
                    editor.putLong("first_switch_start_time",Start_time);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, ForegroundService.class);
                    startService(intent);
                }else{
                    editor.putString("first_switch_start_time_string",null);
                    editor.putLong("switch_save_time",0);
                    editor.putLong("first_switch_start_time",0);
                    editor.commit();
                    ON = 1;
                }
                Log.d("체크:",Boolean.toString(isChecked));
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //툴바 아이템 클릭시 실행되는 메소드 클릭부분 id를 알 수 있다
        int id = item.getItemId();
        if(id == 16908332){//메뉴바
            main_drawer_main.openDrawer(main_drawer_menu);
        }else if(id == 0){
            //intent
            Intent intent = new Intent(this,Phone_record.class);
            startActivity(intent);
        }
        Log.d("id : ",Integer.toString(id));
        return super.onOptionsItemSelected(item);
    }

    private void Dialog_DatePicker() {
        Calendar c = Calendar.getInstance();
        int c_year = c.get(Calendar.YEAR);
        int c_month = c.get(Calendar.MONTH);
        int c_day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                t_month = Integer.toString(month+1);
                t_year = Integer.toString(year);
                SimpleDateFormat sdate = new SimpleDateFormat("yyyy.MM.dd");
                Date date = null;
                try {
                    date = sdate.parse(year+"."+(month+1)+"."+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("gettime 날짜:",Long.toString(date.getTime()));
                Log.d("getdate 날짜:",Long.toString(date.getDate()));
                Log.d("1번 날짜:",Long.toString(calendarView.getDate()));
                calendarView.setDate(date.getTime());
                Log.d("2번 날짜:",Long.toString(calendarView.getDate()));
                //frag_month.calenderchange(year,month+1,dayOfMonth);
                frag_month.frag_calender_change(year,month+1,dayOfMonth);
                title.setText(t_month+"월 "+t_year+"년");
                getSupportActionBar().setCustomView(title_text_v);
                String dateStr = year+"년 "+ (month+1) + "월 " + dayOfMonth + "일";
                Toast.makeText(MainActivity.this,"선택한 날짜는"+dateStr,Toast.LENGTH_SHORT).show();
            }
        };

        DatePickerDialog alert = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, c_year, c_month, c_day);

        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    @Override
    public void calenderchange(int year, int month, int day) {//인터페이스
        title.setText(month+"월 "+year+"년");
        getSupportActionBar().setCustomView(title_text_v);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras() != null) {
            onnewintent_check = 1;
            Bundle bundle = intent.getExtras();
            if(bundle.getInt("login_A") == 1) {//회원가입창에서부터 왔다
                login = bundle.getInt("login");
                emaillist = bundle.getStringArray("emaillist");
                passwordlist = bundle.getStringArray("passwordlist");
                nowemail = bundle.getString("nowemail");
            }
            if(bundle.getInt("setting_A") == 1){//설정창
                start_frag_position = bundle.getInt("temp_start_frag_position");
                help_alarm = bundle.getBoolean("temp_help_alarm");
                Log.d("설설정 메인에서 받음",Integer.toString(start_frag_position));
            }
            if(bundle.getInt("setting_A_logout") == 1){
                login = bundle.getInt("login");
            }
            if(bundle.getInt("week_setting_A") == 1){
                Log.d("메인 주간 설정창 인텐트 받음","ㅇㄴㄹ");
                week_time_initial = bundle.getInt("temp_week_time_initial");
                before_after_initial = bundle.getInt("temp_before_after_initial");
                Log.d("주간 세팅 설정값 메인에서",Integer.toString(week_time_initial));

            }
            setIntent(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        if(onnewintent_check == 1){
            onnewintent_check = 0;//초기화
            if(login == 0) {
                container.removeAllViewsInLayout();
                inflater_login = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater_login.inflate(R.layout.logout_item, container, true);
                Button login = container.findViewById(R.id.sidelogin);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("emaillist",emaillist);
                        intent.putExtra("passwordlist",passwordlist);
                        startActivity(intent);
                    }
                });
            }else{
                container.removeAllViewsInLayout();
                inflater_login = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater_login.inflate(R.layout.login_item, container, true);
                sideemail = container.findViewById(R.id.sideemail);
                sideemail.setText(nowemail);
                my = container.findViewById(R.id.sideinform);
                my.setBackground(new ShapeDrawable(new OvalShape()));
                my.setClipToOutline(true);
                if(image_bitmap == null) {
                    my.setImageResource(R.drawable.ic_launcher_foreground);
                }else{
                    if(bitmap_reset == 0) {
                        my.setImageBitmap(image_bitmap);
                    }else{
                        bitmap_reset = 0;
                    }
                }
                my.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String str[] = {"카메라","앨범","기본이미지"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("선택")
                                .setNegativeButton("취소",null)
                                .setItems(str, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0){
                                            doSelectCarmer();
                                        }else if(which == 1){
                                            doSelectImage();
                                        }else if(which == 2){
                                            bitmap_reset = 1;
                                            my.setImageResource(R.drawable.ic_launcher_foreground);
                                        }
                                        Toast.makeText(getApplicationContext(),"선택 "+str[which]+"위치 "+Integer.toString(which),Toast.LENGTH_LONG).show();
                                    }
                                });
                        //builder.show();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

            }
        }
        if(color_check == 0){
            side_month.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 1){
            side_week.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 2){
            side_day.setTextColor(Color.parseColor("#FF000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 3){
            side_category.setTextColor(Color.parseColor("#FF000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_phone.setTextColor(Color.parseColor("#60000000"));
        }else if(color_check == 4){
            side_phone.setTextColor(Color.parseColor("#FF000000"));
            side_week.setTextColor(Color.parseColor("#60000000"));
            side_day.setTextColor(Color.parseColor("#60000000"));
            side_month.setTextColor(Color.parseColor("#60000000"));
            side_category.setTextColor(Color.parseColor("#60000000"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        Log.d("onresult","ㅇㅇㅇ");
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("ㄴㅇㄹㄴㄹㅇ ", "ㅇㅇ");

        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_IMAGE)//앨범에서 사진 선택후
            {//uri
                for(int i = 0; i<100; i++) {
                    Log.d("카메라ㄴㄴ ", "ㅇㅇ");
                }
                Uri uri = intent.getData();
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                    Bitmap img2 = BitmapFactory.decodeStream(inputStream);
                    //BitmapFactory.Options options = new BitmapFactory.Options();
                    //options.inSampleSize = 4;
                    //Bitmap small_img = BitmapFactory.decodeStream()
                    image_bitmap = img2;
                    my.setImageBitmap(img2);

                    //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    //img2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    //byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    //비트맵형식을 string으로 바꿔 저장
                    //profile = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                    //Log.d("프로파일 : ",profile);
                    //imageSave();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else if (requestCode == SELECT_CARMER)
            {
                cropImage();
            }
            else if (requestCode == CROP_FROM_CAMERA)
            {//string
                Log.d("이미지파일:",imageFilePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                ExifInterface exif = null;
                try{
                    exif = new ExifInterface(imageFilePath);
                } catch (IOException e){
                    e.printStackTrace();
                }

                int exifOrientation;
                int exifDegree;

                if(exif != null){
                    exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    exifDegree = exifOrientationToDegress(exifOrientation);
                }else{
                    exifDegree = 0;
                }
                image_bitmap = rotate(bitmap,exifDegree);
                my.setImageBitmap(rotate(bitmap,exifDegree));

                //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                //rotate(bitmap,exifDegree).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                //byte[] imageBytes = byteArrayOutputStream.toByteArray();
                //비트맵형식을 string으로 바꿔 저장
                //profile = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                //imageSave();
                //Log.d("프로파일 : ",profile);

            }

        }
    }

    public void cropImage(){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(photoUri , "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("output", photoUri);
        /**
         * getUriforFile()이 return한 content URI에 대한 접근권한을 승인하려면 grantUriPermission을 호출한다.
         * mode_flags 파라미터의 값에 따라. 지정한 패키지에 대해 content URI를 위한 임시 접근을 승인한다.
         * 권한은 기기가 리부팅 되거나 revokeUriPermission()을 호출하여 취소할때까지 유지.
         *
         */
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        grantUriPermission( getPackageName(), photoUri , Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(intent, CROP_FROM_CAMERA);

    }

    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }

    private int exifOrientationToDegress(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    public void doSelectCarmer(){
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("1번","ㅇㄴㄹ");
        if(i.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            Log.d("2번","ㅇㄴㄹ");
            try{
                photoFile = createImageFile();
                Log.d("3번","ㅇㄴㄹ");
            }catch (IOException e){

            }
            if(photoFile != null){
                Log.d("4번","ㅇㄴㄹ");
                photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(),photoFile);
                Log.d("4ㄴㄹㄴㄹ",MediaStore.EXTRA_OUTPUT);
                i.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(i, SELECT_CARMER);
            }
            Log.d("5번","ㅇㄴㄹ");
        }
        //Uri probiderURI = FileProvider.getUriForFile(informActivity.this, getPackageName(),photoFile);
        //i.putExtra(MediaStore.EXTRA_OUTPUT,probiderURI);
        //i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
        //Uri uri = FileProvider.getUriForFile(getBaseContext(), "com.onedelay.chap7.fileprovider", file);
        //i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }

    public void doSelectImage(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try
        {
            startActivityForResult(i, SELECT_IMAGE);
        } catch (android.content.ActivityNotFoundException e)
        {
            e.printStackTrace();
        }



    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show();

        }
    };

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storgeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storgeDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


    public void reset(){
        SharedPreferences pref = getSharedPreferences("pref",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
