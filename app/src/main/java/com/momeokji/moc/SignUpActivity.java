package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.momeokji.moc.Helper.StringChecker;

public class SignUpActivity extends AppCompatActivity {
    public final static String NOT_VALID_EMAIL_FORM = "올바른 Email 형식을 입력해 주세요.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText signup_email_edittxt = findViewById(R.id.signup_email_edittxt);
        final EditText signup_password_edittxt = findViewById(R.id.signup_password_edittxt);
        final EditText signup_password_check_edittxt = findViewById(R.id.signup_password_check_edittxt);
        Button signup_normal_sign_up_btn = findViewById(R.id.signup_normal_sign_up_btn);
        TextView  signup_log_in_txtbtn = findViewById(R.id.signup_log_in_txtbtn);
        ImageButton signup_sign_up_with_facebook_imgbtn = findViewById(R.id.signup_sign_up_with_facebook_imgbtn);
        ImageButton signup_sign_up_with_google_imgbtn = findViewById(R.id.signup_sign_up_with_google_imgbtn);


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
            Toast.makeText(this, NOT_VALID_EMAIL_FORM, Toast.LENGTH_SHORT).show();
            return ;
        } else if (!password.equals(passwordCheck)) {                                                           // 비밀번호와 비밀번호 확인 이 같은지 체크
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO : 일반 회원가입 구현

    }
}
