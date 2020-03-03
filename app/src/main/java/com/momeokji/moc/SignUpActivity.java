package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import static java.security.AccessController.getContext;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final BackPressEditText signup_email_edittxt = findViewById(R.id.signup_email_edittxt);
        final BackPressEditText signup_password_edittxt = findViewById(R.id.signup_password_edittxt);
        final BackPressEditText signup_password_check_edittxt = findViewById(R.id.signup_password_check_edittxt);
        Button signup_sign_up_with_facebook_imgbtn = findViewById(R.id.signup_sign_up_with_facebook_btn);
        Button signup_sign_up_with_google_imgbtn = findViewById(R.id.signup_sign_up_with_google_btn);
        Button signup_normal_sign_up_btn = findViewById(R.id.signup_general_sign_up_btn);
        TextView  signup_log_in_txtbtn = findViewById(R.id.signup_log_in_txtbtn);

        //* EditText에 엔터 클릭시 닫히는 키 리스너 등록
        signup_email_edittxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {  // 이메일 입력란에서 키보드 엔터 누를 때
                    RemoveFocusFromEditText(signup_email_edittxt);                                  // 포커스 해제
                    return true;
                }
                return false;
            }
        });
        signup_password_edittxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {  // 비밀번호 입력란에서 키보드 엔터 누를 떄
                    RemoveFocusFromEditText(signup_password_edittxt);                               // 포커스 해제
                    return true;
                }
                return false;
            }
        });
        signup_password_check_edittxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {  // 비밀번호 확인 입력란에서 키보드 엔터 누를 때
                    RemoveFocusFromEditText(signup_password_check_edittxt);                         // 포커스 해제
                    SignUpWithMoc(signup_email_edittxt.getText().toString(), signup_password_edittxt.getText().toString(), signup_password_check_edittxt.getText().toString()); // 일반 회원가입 진행

                    return true;
                }
                return false;
            }
        });

        //* 로그인 문구 버튼 클릭 시 리스너 등록
        signup_log_in_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);  // 로그인으로 다시 이동
                startActivity(intent);
            }
        });

        //* 일반 회원가입 버튼 클릭 시 리스너 등록
        signup_normal_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpWithMoc(signup_email_edittxt.getText().toString(), signup_password_edittxt.getText().toString(), signup_password_check_edittxt.getText().toString());
            }
        });

        //* 페이스북 회원가입 버튼 클릭 시 리스너 등록
        signup_sign_up_with_facebook_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO : 페이스북 회원가입 구현

            }
        });

        //* 구글 회원가입 버튼 클릭 시 리스너 등록
        signup_sign_up_with_google_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO : 구글 회원가입 구현

            }
        });
    }

    public void SignUpWithMoc(String email, String password, String passwordCheck) {
        if (StringChecker.CheckEmailForm(email)) {                                                              //이메일 형식 체크
            Toast.makeText(this, R.string.not_valid_email_form, Toast.LENGTH_SHORT).show();
            return ;
        } else if (!password.equals(passwordCheck)) {                                                           // 비밀번호와 비밀번호 확인 이 같은지 체크
            Toast.makeText(this, R.string.do_not_match_password_and_password_check, Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO : 일반 회원가입 구현

    }


    public void RemoveFocusFromEditText(BackPressEditText targetEditText) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        targetEditText.clearFocus();
    }
}
