package com.momeokji.moc;

import androidx.annotation.NonNull;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.momeokji.moc.CustomView.BackPressEditText;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Helper.StringChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final BackPressEditText signup_nickname_edittxt = findViewById(R.id.signup_nickname_edittxt); // 닉네임
        final BackPressEditText signup_email_edittxt = findViewById(R.id.signup_email_edittxt); // 이메일
        final BackPressEditText signup_password_edittxt = findViewById(R.id.signup_password_edittxt); // 비밀번호
        final BackPressEditText signup_password_check_edittxt = findViewById(R.id.signup_password_check_edittxt); // 비밀번호 확인
        Button signup_normal_sign_up_btn = findViewById(R.id.signup_general_sign_up_btn);
        TextView  signup_log_in_txtbtn = findViewById(R.id.signup_log_in_txtbtn);

        firebaseAuth = FirebaseAuth.getInstance();

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
                    SignUpWithMoc(signup_email_edittxt.getText().toString().trim(), signup_password_edittxt.getText().toString().trim(), signup_password_check_edittxt.getText().toString().trim(), signup_nickname_edittxt.getText().toString().trim()); // 일반 회원가입 진행

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
                SignUpWithMoc(signup_email_edittxt.getText().toString().trim(), signup_password_edittxt.getText().toString().trim(), signup_password_check_edittxt.getText().toString().trim(), signup_nickname_edittxt.getText().toString().trim());
            }
        });

    }




    private void SignUpWithMoc(final String email, String password, String passwordCheck, final String nickname) {
        if (StringChecker.CheckEmailForm(email)) {                                                              //이메일 형식 체크
            Toast.makeText(this, R.string.not_valid_email_form, Toast.LENGTH_SHORT).show();
            return ;
        } else if (!password.equals(passwordCheck)) {                                                           // 비밀번호와 비밀번호 확인 이 같은지 체크
            Toast.makeText(this, R.string.do_not_match_password_and_password_check, Toast.LENGTH_SHORT).show();
            return;
        } else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
            Toast.makeText(this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();            //비밀번호 형식 체크
            return;
        } else if (nickname.equals("")) {
            Toast.makeText(this,"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show();            //닉네임 존재 체크
            return;
        } else if (nickname.length() > 10) {
            Toast.makeText(this,"닉네임을 10자 이내로 입력해주세요.",Toast.LENGTH_SHORT).show();            //닉네임 길이 체크
            return;
        }


        // Email 회원가입 구현
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("회원가입CHECK", "EmailSignUp 성공");
                            // 사용자UID
                            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // 사용자UID & 닉네임 DB에 저장
                            DatabaseQueryClass.UserInfo.putUserNickNameToDB(userUID, nickname, "email");
                            // 회원가입 완료 후 로그인 페이지로 이동
                            Toast.makeText(getApplicationContext(),"회원가입 되었습니다.",Toast.LENGTH_SHORT).show();
                            moveLoginActivity();
                        }
                        else {
                            // 회원가입 실패 시
                            Log.e("회원가입CHECK", "EmailSignUp 실패", task.getException());
                            Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    private void moveLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void RemoveFocusFromEditText(BackPressEditText targetEditText) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        targetEditText.clearFocus();
    }
}
