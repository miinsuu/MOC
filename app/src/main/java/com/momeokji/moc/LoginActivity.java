package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.momeokji.moc.CustomView.BackPressEditText;
import com.momeokji.moc.Helper.StringChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    public final static String NOT_VALID_EMAIL_FORM = "올바른 Email 형식을 입력해 주세요.";

    private LoginActivity loginActivity;

    public LoginActivity() {
        this.loginActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final BackPressEditText login_email_edittxt = findViewById(R.id.login_email_edittxt);
        final BackPressEditText login_password_edittxt = findViewById(R.id.login_password_edittxt);
        TextView login_find_password_txtbtn = findViewById(R.id.login_find_password_txtbtn);
        Button login_log_in_btn = findViewById(R.id.login_log_in_btn);
        TextView login_sign_up_txtbtn = findViewById(R.id.login_sign_up_txtbtn);
        ImageButton login_log_in_with_facebook_imgbtn = findViewById(R.id.login_log_in_with_facebook_imgbtn);
        ImageButton login_log_in_with_google_imgbtn = findViewById(R.id.login_log_in_with_google_imgbtn);


        //* EditText에 엔터 클릭시 닫히는 키 리스너 등록
        login_email_edittxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {  // 이메일 입력란에서 엔터 누를 때
                    RemoveFocusFromEditText(login_email_edittxt);                                   // 포커스 해제
                    return true;
                }
                return false;
            }
        });
        login_password_edittxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {  // 비밀번호 입력란에서 엔터 누를 떄
                    RemoveFocusFromEditText(login_password_edittxt);                                // 포커스 해제
                    VerifyLoginInfo(login_email_edittxt.getText().toString(), login_password_edittxt.getText().toString());     // 로그인 절차 실행
                    return true;
                }
                return false;
            }
        });

        //* 비밀번호 찾기 문구 버튼 클릭 시 리스너 등록
        login_find_password_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //* 로그인 버튼 클릭 시 리스너 등록
        login_log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyLoginInfo(login_email_edittxt.getText().toString(), login_password_edittxt.getText().toString());     // 로그인 절차 실행
            }
        });

        //* 회원가입 문구 버튼 클릭 시 리스너 등록
        login_sign_up_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);  // 메인액티비티로 이동
                startActivity(intent);
            }
        });

        //* 페이스북 로그인 버튼 클릭 시 리스너 등록
        login_log_in_with_facebook_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : 페이스북 로그인
            }
        });

        //* 구글 로그인 버튼 클릭 시 리스너 등록
        login_log_in_with_google_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : 구글 로그인
            }
        });
    }

    //* 입력된 이메일과 비밀번호가 유효한지 검사하는 함수
    public boolean CheckLoginInfo(String email, String password) {
        if (!StringChecker.CheckEmailForm(email))                                               // 이메일 형식인지 체크
            Toast.makeText(this, NOT_VALID_EMAIL_FORM, Toast.LENGTH_LONG).show();

        //TODO : 입력한 아이디, 비밀번호가 맞는지 체크

        return true;
    }

    //* 이메일과 비밀번호를 확인하고 계정이 확인되면 MainActivity로 넘어가는 함수
    public void VerifyLoginInfo(String email, String password) {
        if (CheckLoginInfo(email, password)) {                                                          // 적힌 이메일과 비밀번호가 유효하다면
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);         // 메인액티비티로 이동
            startActivity(intent);
        } else {
            Toast.makeText(loginActivity, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
        }
    }


    public void RemoveFocusFromEditText(BackPressEditText targetEditText) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        targetEditText.clearFocus();
    }
}
