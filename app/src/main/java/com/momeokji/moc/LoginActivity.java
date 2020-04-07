package com.momeokji.moc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.momeokji.moc.CustomView.BackPressEditText;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Helper.StringChecker;
import com.momeokji.moc.data.User;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private LoginActivity loginActivity;
    // 페이스북 콜백 매니저
    //private CallbackManager callbackManager;

    // 구글로그인 result 상수
    private static final int RC_SIGN_IN = 900;
    // 구글api클라이언트
    private GoogleSignInClient googleSignInClient;

    private BackPressEditText login_email_edittxt;

    public LoginActivity() {
        this.loginActivity = this;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 만약 이미 로그인 했다면 메인액티비티로 이동
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (!User.getUser().isLoggedIn()) { // 자동로그인 시 유저정보 가져오기
                String userUID = user.getUid();
                User.getUser().putUserInfo(userUID);
                startActivity(new Intent(loginActivity, MainActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            } else {
                startActivity(new Intent(loginActivity, MainActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getAppKeyHash();

         login_email_edittxt = findViewById(R.id.login_email_edittxt);
        final BackPressEditText login_password_edittxt = findViewById(R.id.login_password_edittxt);
        TextView login_find_password_txtbtn = findViewById(R.id.login_find_password_txtbtn);
        Button login_log_in_btn = findViewById(R.id.login_log_in_btn);
        TextView login_sign_up_txtbtn = findViewById(R.id.login_sign_up_txtbtn);
        View login_with_facebook_imgbtn = findViewById(R.id.login_facebook_btn);
        com.google.android.gms.common.SignInButton login_with_google_imgbtn = findViewById(R.id.login_google_btn);

        firebaseAuth = FirebaseAuth.getInstance();

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

        // 비밀번호 찾기(재설정 이메일 보내기)
        //* 비밀번호 찾기 문구 버튼 클릭 시 리스너 등록
        login_find_password_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edittext = new EditText(loginActivity);

                // 비밀번호초기화 메일을 보내기 위해 사용자이메일을 입력받을 다이얼로그
                final AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity);
                builder.setTitle("비밀번호 재설정");
                builder.setMessage("계정 이메일을 입력해 주십시오.");
                builder.setView(edittext);
                builder.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {

                                String email = edittext.getText().toString();
                                if(email.equals(""))
                                    dialog.dismiss(); // 창 닫음
                                else {
                                    // 비밀번호 찾기(재설정 이메일 보내기)
                                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "비밀번호 재설정 메일을 전송했습니다", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss(); // 창 닫음
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "등록된 이메일 계정이 아닙니다.", Toast.LENGTH_LONG).show();
                                                Log.e("비밀번호재설정오류", task.getException().toString());
                                            }
                                        }
                                    });
                                }

                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // 창 닫음
                            }
                        });
                builder.show();
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
                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });


        //* 페이스북 로그인 버튼 클릭 시 리스너 등록
        // 페이스북 콜백 등록
        login_with_facebook_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(loginActivity, "신종 코로나바이러스로 인해\n페이스북 본사 측 앱 검수가\n지연 중입니다 ㅠㅠ", Toast.LENGTH_LONG).show();
            }
        });

        // 코로나 바이러스 끝나면 주석해제
//        callbackManager = CallbackManager.Factory.create();
//        login_with_facebook_imgbtn.setReadPermissions("email", "public_profile");
//        login_with_facebook_imgbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d("FACEBOOK", "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("FACEBOOK", "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e("FACEBOOK",  error.toString());
//                // ...
//            }
//        });// ...


        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //* 구글 로그인 버튼 클릭 시 리스너 등록
        login_with_google_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }
    ////// OnCreate() 끝 ////////////////

    // 앱의 키 해시 값을 알려주는 메소드
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 페이스북 콜백 등록
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }


    // 페이스북 로그인 이벤트
    // 사용자가 정상적으로 로그인한 후 페이스북 로그인 버튼의 onSuccess 콜백 메소드에서 로그인한 사용자의
    // 액세스 토큰을 가져와서 Firebase 사용자 인증 정보로 교환하고,
    // Firebase 사용자 인증 정보를 사용해 Firebase에 인증.
    private void handleFacebookAccessToken(final AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공

                            //페이스북 사용자 정보 추출 (사용자이름=닉네임)
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String nickname = null;
                            String userUID = user.getUid();
                            if (user != null) {
                                for (UserInfo profile : user.getProviderData()) {
                                    nickname = profile.getDisplayName();
                                    break;
                                }
                                // 페이스북계정으로 첫 로그인인지 DB에 저장된 유저정보 중복체크후 저장
                                DatabaseQueryClass.UserInfo.checkUserDuplication(userUID, nickname, "facebook");
                                // 파이어베이스 userUID로 DB에 있는 닉네임, 유저uid 정보 불러오기
                                User.getUser().putUserInfo(userUID);

                                new Handler().postDelayed(new Runnable() {
                                    @Override public void run() {
                                        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        User.getUser().putUserInfo(userUID);
                                    }
                                }, 2000); // 2초 딜레이

                                Log.e("페이스북체크!","account:"+User.getUser().getLoginAccount()+", nickname:"+User.getUser().getNickName()+", getUserUID():"+User.getUser().getUserUID());

                                // 로그인 성공 메시지
                                Toast.makeText(loginActivity, "로그인", Toast.LENGTH_SHORT).show();
                                // 메인액티비티로 이동
                                startActivity(new Intent(loginActivity, MainActivity.class));
                                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
                            }


                        } else {
                            // 로그인 실패
                            Toast.makeText(loginActivity, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // 사용자가 정상적으로 로그인한 후에 GoogleSignInAccount 개체에서 ID 토큰을 가져와서
    // Firebase 사용자 인증 정보로 교환하고 Firebase 사용자 인증 정보를 사용해 Firebase에 인증
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공

                            //Google 사용자 정보 추출 (사용자이름=닉네임)
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String nickname = null;
                            String userUID = user.getUid();
                            if (user != null) {
                                for (UserInfo profile : user.getProviderData()) {
                                    nickname = profile.getDisplayName();
                                    break;
                                }
                                // Google계정으로 첫 로그인인지 DB에 저장된 유저정보 중복체크후 저장
                                DatabaseQueryClass.UserInfo.checkUserDuplication(userUID, nickname, "google");
                                // 파이어베이스 userUID로 DB에 있는 닉네임, 유저uid 정보 불러오기
                                User.getUser().putUserInfo(userUID);
                                Log.e("구글체크!","account:"+User.getUser().getLoginAccount()+", nickname:"+User.getUser().getNickName()+", getUserUID():"+User.getUser().getUserUID());

                                new Handler().postDelayed(new Runnable() {
                                    @Override public void run() {
                                        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        User.getUser().putUserInfo(userUID);
                                    }
                                }, 2000); // 2초 딜레이

                                Log.e("구글체크!","account:"+User.getUser().getLoginAccount()+", nickname:"+User.getUser().getNickName()+", getUserUID():"+User.getUser().getUserUID());


                                // 로그인 성공 메시지
                                Toast.makeText(loginActivity, "로그인", Toast.LENGTH_SHORT).show();
                                // 메인액티비티로 이동
                                startActivity(new Intent(loginActivity, MainActivity.class));
                                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
                            }


                        } else {
                            // 로그인 실패
                            Toast.makeText(loginActivity, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    //* 이메일과 비밀번호를 확인하고 계정이 확인되면 MainActivity로 넘어가는 함수
    public void VerifyLoginInfo(final String email, String password) {

        if (email.isEmpty()) {
            Toast.makeText(loginActivity, "이메일을 입력해주십시오.", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(loginActivity, "비밀번호를 입력해주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인정보 인증 성공 시
                            Log.e("로그인CHECK", "Email로그인성공");
                            // 파이어베이스 userUID로 DB에 있는 닉네임, 유저UID 정보 불러오기
                            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User.getUser().putUserInfo(userUID);
                            // 메인액티비티로 이동
                            Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loginActivity, MainActivity.class));
                            overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
                        } else {
                            // 로그인 사용자인증 실패 시
                            Log.e("로그인CHECK", "Email로그인실패", task.getException());
                            Toast.makeText(loginActivity, R.string.incorrect_emile_or_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onStop() {
        super.onStop();

        String userUID = null;
        // 유저정보 갱신
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userUID != null)
            User.getUser().putUserInfo(userUID);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String userUID = null;
        // 유저정보 갱신
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userUID != null)
            User.getUser().putUserInfo(userUID);
    }

    public void RemoveFocusFromEditText(BackPressEditText targetEditText) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        targetEditText.clearFocus();
    }

    // 뒤로가기 애니메이션
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }
}
