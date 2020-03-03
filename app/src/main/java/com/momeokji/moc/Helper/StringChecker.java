package com.momeokji.moc.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {

    //* 이메일 형식을 체크하는 함수
    public static boolean CheckEmailForm(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@([a-zA-Z0-9]+.[a-zA-Z]{3}|[a-zA-Z0-9]+.[a-zA-Z]{2}.[a-zA-Z]{2})"); //aaa00@bbb11.ccc 또는 aaa00@bbb11.cc.dd  형식 체크
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
