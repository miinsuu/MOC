package com.momeokji.moc.data;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;

public class User {
    private static User user = new User();

    private String userUID = null; // 사용자 UID , 중복불가 , 사용자구분 기준 , 서버측에서 사용자들을 구별할 수 있는 identity
    private String nickname; // 리뷰작성시 닉네임
    private String usersMapName; // users Cellection에 저장되는 Map의 무작위 String이름
    private String loginAccount; // 로그인 계정 종류 (email / google / facebook)

    private User(){

    }

    public static User getUser(){
        return user;
    }

    public void putUserInfo(String userUID)
    {
        this.userUID = userUID;
        DatabaseQueryClass.UserInfo.getUserInfoByUserUID(userUID, new DataListener() {
            @Override
            public void getData(Object data, String id) {
                JsonElement element = new JsonParser().parse(data.toString());
                JsonObject jobj = element.getAsJsonObject();
                nickname = jobj.get("nick").getAsString();
                usersMapName = jobj.get("usersMapName").getAsString();
                loginAccount =  jobj.get("account").getAsString();

                Log.e("GOOGLE정보체크", "닉네임: "+nickname + ", 로그인계정: " +loginAccount );
            }
        });
    }

    public boolean isLoggedIn(){
        if(userUID != null)
            return true;
        else
            return false;
    }

    public String getUserUID() {
        return userUID;
    }
    public String getusersMapName(){
        return this.usersMapName;
    }
    public String getNickName(){
        return this.nickname;
    }
    public void clearUser() {
        this.user = new User();
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

}
