package org.techtown.timeschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private Button login_btn, sign_btn;
    private TextView pwfind_btn;
    private TextInputEditText login_email, login_pass;
    private String[] emaillist, passwordlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillist = new String[100];
        passwordlist = new String[100];

        final Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            emaillist = bundle.getStringArray("emaillist");
            passwordlist = bundle.getStringArray("passwordlist");;
        }

        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_email.getText().toString().length() > 0) {
                    if(login_pass.getText().toString().length() > 0) {
                        int count = 0;
                        for (int i = 0; i < 100; i++) {
                            if (emaillist[i] == null) {
                                i = 100;
                            } else {
                                if (emaillist[i].equals(login_email.getText().toString()) == true) {
                                    if (passwordlist[i].equals(login_pass.getText().toString()) == true) {
                                        count++;
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.putExtra("emaillist",emaillist);
                                        intent.putExtra("passwordlist",passwordlist);
                                        intent.putExtra("nowemail",login_email.getText().toString());
                                        intent.putExtra("login", 1);
                                        intent.putExtra("login_A",1);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                        if(count == 0){
                            Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 잘못입력했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sign_btn = findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                intent.putExtra("emaillist",emaillist);
                intent.putExtra("passwordlist",passwordlist);
                startActivity(intent);
            }
        });

        pwfind_btn = findViewById(R.id.pwfind_btn);//텍스트뷰
        pwfind_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindpasswordActivity.class);
                intent.putExtra("emaillist",emaillist);
                intent.putExtra("passwordlist",passwordlist);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Lo뉴인텐트","뉴인텐트");
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if(bundle.getInt("sign_A") == 1) {//회원가입창에서부터 왔다
                //회원가입창에서 데이터가 최신화된것을 받는다
                emaillist = bundle.getStringArray("emaillist");
                passwordlist = bundle.getStringArray("passwordlist");
            }else if(bundle.getInt("pass_A") == 1){
                passwordlist = bundle.getStringArray("passwordlist");
            }
            setIntent(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lo리스타트","리스타트");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lo스타트","스타트");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lo리줌","리줌");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lo포즈","포즈");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lo스탑","스탑");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lo디스트로이","디스트로이");
    }
}
