package com.momeokji.moc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;
import com.momeokji.moc.data.User;

import java.util.regex.Pattern;

public class UpdateNicknameActivity extends AppCompatActivity {
    // 닉네임만 업데이트하는 액티비티가 아니고, 내 정보 수정 액티비티! (닉네임변경, 비밀번호변경, 로그아웃, 회원탈퇴)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickname);

        final TextView updateNickname_acticity_newNicknameTxt = findViewById(R.id.updateNickname_acticity_newNicknameTxt); // 바꿀 닉네임텍스트뷰
        Button updateNickname_acticity_updateBtn = findViewById(R.id.updateNickname_acticity_updateBtn); // 닉네임변경버튼
        TextView myPage_activity_logoutBtn = findViewById(R.id.myPage_activity_logoutBtn); // 로그아웃 버튼
        TextView myPage_activity_userDeleteBtn = findViewById(R.id.myPage_activity_userDeleteBtn); // 회원탈퇴 버튼
        View passwordChange_linearLayout = findViewById(R.id.passwordChange_linearLayout); // 비밀번호 변경 레이아웃 (이메일사용자에게만 보임)
        final TextView updatePassword_acticity_newPasswordTxt = findViewById(R.id.updatePassword_acticity_newPasswordTxt); // 새 비밀번호 텍스트뷰
        final TextView updatePassword_acticity_newPasswordCheckTxt = findViewById(R.id.updatePassword_acticity_newPasswordCheckTxt); // 새 비밀번호 확인 텍스트뷰
        Button updatePassword_acticity_updateBtn = findViewById(R.id.updatePassword_acticity_updateBtn); // 비밀번호 변경 버튼
        Button updateNickname_back_btn = findViewById(R.id.updateNickname_back_btn); // 백 버튼

        // 뒤로가기 버튼
        updateNickname_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                finish();
            }
        });

        // 닉네임변경 버튼 눌렀을 때 DB에 있는 정보 업데이트
        updateNickname_acticity_updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateNickname_acticity_newNicknameTxt.getText().toString().equals("") || updateNickname_acticity_newNicknameTxt.getText().toString().trim().equals("")) {
                    Toast.makeText(UpdateNicknameActivity.this, "변경할 닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (updateNickname_acticity_newNicknameTxt.getText().toString().length() > 10) {
                    Toast.makeText(UpdateNicknameActivity.this, "10자 이내로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String newNickname = updateNickname_acticity_newNicknameTxt.getText().toString();
                    String usersMapName = User.getUser().getusersMapName();
                    // DB에 업데이트 작업
                    DatabaseQueryClass.UserInfo.updateNickname(newNickname, usersMapName);

                    // DB에 업데이트된 닉네임을 앱으로 가져오기
                    String userUID = FirebaseAuth.getInstance().getUid();
                    User.getUser().putUserInfo(userUID);
                    finish(); // 업데이트 액티비티 종료
                    Toast.makeText(UpdateNicknameActivity.this, "닉네임 업데이트", Toast.LENGTH_LONG).show();

                    // 홈 화면으로 이동
                    //MainActivity.getInstance().finish();
                    startActivity(new Intent(UpdateNicknameActivity.this, MainActivity.class));
                }

            }
        });


        // 로그아웃 리스너
        myPage_activity_logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 파이어베이스 로그아웃
                FirebaseAuth.getInstance().signOut();
                // 페이스북 로그아웃
                LoginManager.getInstance().logOut();
                // 유저정보 삭제
                User.getUser().clearUser();
                Toast.makeText(UpdateNicknameActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                // 로그인 화면으로 이동
                ActivityCompat.finishAffinity(UpdateNicknameActivity.this);
                startActivity(new Intent(UpdateNicknameActivity.this, LoginActivity.class));
            }
        });


        // 회원탈퇴 리스너
        myPage_activity_userDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원 탈퇴를 다시 한번 물어보는 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateNicknameActivity.this);
                builder.setTitle("정말로 회원 탈퇴하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // DB에서 users 정보 삭제
                        DatabaseQueryClass.UserInfo.deleteUser(User.getUser().getusersMapName(), new MyOnSuccessListener() {
                            @Override
                            public void onSuccess() {
                                // 계정 삭제 (탈퇴)
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("계정탈퇴", "성공");
                                                // User클래스 유저정보 삭제
                                                User.getUser().clearUser();
                                                Toast.makeText(UpdateNicknameActivity.this, "계정이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                                // 로그인 화면으로 이동
                                                ActivityCompat.finishAffinity(UpdateNicknameActivity.this);
                                                startActivity(new Intent(UpdateNicknameActivity.this, LoginActivity.class));
                                            }
                                        }
                                    });

                                }
                        });


                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //Log.e("getLoginAccount()", User.getUser().getLoginAccount());
        // 만약 사용자가 이메일 계정이 아니라면(구글/페북 사용자라면) 비밀번호 변경 레이아웃이 보이지 않음
        if(User.getUser().getLoginAccount().equals("email"))
            passwordChange_linearLayout.setVisibility(View.VISIBLE);
        else
            passwordChange_linearLayout.setVisibility(View.GONE);


        // 비밀번호 변경 버튼 리스너
        updatePassword_acticity_updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = updatePassword_acticity_newPasswordTxt.getText().toString();
                String passwordCheck = updatePassword_acticity_newPasswordCheckTxt.getText().toString();

                if(password.equals("") || passwordCheck.equals("")) {
                    // 비었는지 체크
                    Toast.makeText(UpdateNicknameActivity.this, "비밀번호를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(passwordCheck)) {
                    // 비밀번호와 비밀번호 확인 이 같은지 체크
                    Toast.makeText(UpdateNicknameActivity.this, R.string.do_not_match_password_and_password_check, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
                    //비밀번호 형식 체크
                    Toast.makeText(UpdateNicknameActivity.this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 변경
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.e("비밀번호 변경", "성공");
                                    Toast.makeText(UpdateNicknameActivity.this,"비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                                    // 홈 화면으로 이동
                                    finish();
                                    MainActivity.getInstance().finish();
                                    startActivity(new Intent(UpdateNicknameActivity.this, MainActivity.class));
                                }
                            }
                        });

            }
        });


    }

    // 뒤로가기 애니메이션
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }

    // 뒤로가기 애니메이션
    @Override
    public void finishAffinity() {
        super.finishAffinity();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }
}
