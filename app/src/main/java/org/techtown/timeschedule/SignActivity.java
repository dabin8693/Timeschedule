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

public class SignActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private LinearLayout container;
    private Button sign_sign, sign_send;
    private LayoutInflater inflater;
    private TextInputEditText sign_email, sign_pass, sign_passcheck, code_edit;
    private String[] emaillist, passwordlist;
    private String codeValue;
    private String checked_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Log.d("LoS온크리에이트","크리");
        emaillist = new String[100];
        passwordlist = new String[100];

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            emaillist = bundle.getStringArray("emaillist");
            passwordlist = bundle.getStringArray("passwordlist");;
        }

        toolbar = findViewById(R.id.appbar_sign);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        sign_email = findViewById(R.id.sign_email);
        sign_pass = findViewById(R.id.sign_pass);
        sign_passcheck = findViewById(R.id.sign_passcheck);
        container = findViewById(R.id.sign_container);
        sign_send = findViewById(R.id.sign_send);
        sign_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;//0보다 크면 중복
                    if (sign_email.getText().toString().length() > 0) {
                        for (int i = 0; i < 100; i++) {//이메일 db 널 나올때까지 검사
                            if (emaillist[i] != null) {
                                if (sign_email.getText().toString().equals(emaillist[i]) == true) {
                                    //이메일 중복
                                    if (count == 0) {
                                        Toast.makeText(SignActivity.this, "이미 사용중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    count++;
                                }
                            } else {//이메일 db 값없으면 for 탈출
                                i = 100;
                            }
                        }
                        if (count == 0) {//for끝나고 중복없으면
                            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            inflater.inflate(R.layout.sign_code_item, container, true);
                            code_edit = container.findViewById(R.id.sign_code);
                            Toast.makeText(SignActivity.this, "인증번호 발송", Toast.LENGTH_SHORT).show();
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();

                                    SendMail mailServer = new SendMail();
                                    checked_email = sign_email.getText().toString();
                                    mailServer.sendSecurityCode(checked_email);//오래걸림
                                    codeValue = mailServer.code;
                                    Log.d("인증번호 : ", codeValue);

                                }
                            }.start();
                        }
                    } else {//이메일 입력란 비었으면
                        Toast.makeText(SignActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                }
        });

        sign_sign = findViewById(R.id.sign_sign);
        sign_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked_email != null) {
                    if(sign_email.getText().toString().length() > 0) {
                        if (checked_email.equals(sign_email.getText().toString()) == true) {
                            if(code_edit.getText().toString().length() > 0) {
                                if (codeValue.equals(code_edit.getText().toString()) == true) {
                                    if (sign_pass.getText().toString().length() > 0) {
                                        if (sign_passcheck.getText().toString().length() > 0) {
                                            if (sign_pass.getText().toString().equals(sign_passcheck.getText().toString()) == true) {
                                                for (int i = 0; i < 100; i++) {
                                                    if (emaillist[i] == null) {
                                                        emaillist[i] = sign_email.getText().toString();
                                                        passwordlist[i] = sign_pass.getText().toString();
                                                        Log.d("i는",Integer.toString(i));
                                                        Log.d("email:",emaillist[i]);
                                                        Log.d("pass:",passwordlist[i]);
                                                        i = 100;
                                                    }
                                                }
                                                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                //재활용되는 액티비티는 매니패스트에 android:launchMode="singleTop"넣기
                                                intent.putExtra("emaillist", emaillist);
                                                intent.putExtra("passwordlist", passwordlist);
                                                intent.putExtra("sign_A", 1);
                                                startActivity(intent);
                                                Toast.makeText(SignActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(SignActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(SignActivity.this, "비밀번호확인란을 입력하세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(SignActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SignActivity.this, "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(SignActivity.this, "인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignActivity.this, "인증번호를 다시 발송하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignActivity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignActivity.this,"인증번호를 발송하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LoS스타트","스타트");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LoS리줌","리줌");
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
