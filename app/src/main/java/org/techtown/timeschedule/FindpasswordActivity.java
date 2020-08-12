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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class FindpasswordActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private LinearLayout container;
    private Button pwfind_send, pwfind_change;
    private LayoutInflater inflater;
    private TextInputEditText pwfind_code, pwfind_pass, pwfind_passcheck, pwfind_email;
    private int pwfind_send_click_check;
    private String checked_email, codeValue;
    private String[] emaillist, passwordlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        emaillist = new String[100];
        passwordlist = new String[100];

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            emaillist = bundle.getStringArray("emaillist");
            passwordlist = bundle.getStringArray("passwordlist");
        }

        toolbar = findViewById(R.id.appbar_pwfind);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        pwfind_email = findViewById(R.id.pwfind_email);
        container = findViewById(R.id.pwfind_container);
        pwfind_send = findViewById(R.id.pwfind_send);
        pwfind_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for(int i = 0; i<100; i++) {
                    if(emaillist[i] == null){
                        i = 100;
                    }else{
                        if(emaillist[i].equals(pwfind_email.getText().toString())){
                            count = 1;
                        }
                    }
                }
                if (count == 1) {//for끝나고 존재하는 회원이면
                    pwfind_send_click_check = 1;
                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.pwfind_code_item, container, true);
                    pwfind_code = container.findViewById(R.id.pwfind_code);
                    pwfind_pass = container.findViewById(R.id.pwfind_pass);
                    pwfind_passcheck = container.findViewById(R.id.pwfind_passcheck);
                    Toast.makeText(FindpasswordActivity.this, "인증번호 발송", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            SendMail mailServer = new SendMail();
                            checked_email = pwfind_email.getText().toString();
                            mailServer.sendSecurityCode(checked_email);//오래걸림
                            codeValue = mailServer.code;
                            Log.d("인증번호 : ", codeValue);

                        }
                    }.start();
                }else {//존재하지않는 회원
                    Toast.makeText(FindpasswordActivity.this, "존재하지않는 회원입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pwfind_change = findViewById(R.id.pwfind_change);
        pwfind_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwfind_send_click_check == 1){//인증번호 보내기 버튼 눌렀을때
                    if(pwfind_email.getText().toString().length() > 0) {
                        if (checked_email.equals(pwfind_email.getText().toString()) == true) {
                            if(pwfind_code.getText().toString().length() > 0){
                                if(codeValue.equals(pwfind_code.getText().toString()) == true){
                                    if (pwfind_pass.getText().toString().length() > 0) {
                                        if (pwfind_passcheck.getText().toString().length() > 0) {
                                            if (pwfind_pass.getText().toString().equals(pwfind_passcheck.getText().toString()) == true) {
                                                for (int i = 0; i < 100; i++) {
                                                    if (emaillist[i] == null) {
                                                        i = 100;
                                                    }else{
                                                        if(emaillist[i].equals(pwfind_email.getText().toString()) == true){
                                                            passwordlist[i] = pwfind_pass.getText().toString();
                                                            i = 100;
                                                        }
                                                    }
                                                }
                                                Intent intent = new Intent(FindpasswordActivity.this, LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                //재활용되는 액티비티는 매니패스트에 android:launchMode="singleTop"넣기
                                                intent.putExtra("emaillist", emaillist);
                                                intent.putExtra("passwordlist", passwordlist);
                                                intent.putExtra("pass_A", 1);
                                                startActivity(intent);

                                            }else {
                                                Toast.makeText(FindpasswordActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(FindpasswordActivity.this, "비밀번호확인란을 입력하세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(FindpasswordActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(FindpasswordActivity.this, "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(FindpasswordActivity.this, "인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FindpasswordActivity.this, "인증번호를 다시 발송하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(FindpasswordActivity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FindpasswordActivity.this, "인증번호를 발송하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if(id == 16908332) {//back
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
