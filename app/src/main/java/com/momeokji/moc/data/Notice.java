package com.momeokji.moc.data;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;

public class Notice implements Serializable {
    private String noticeTitle; // 제목
    private String noticeCreateAt; // 쓴 날짜
    private String noticeContent; // 내용
    private int noticeIndex; // 공지사항 index

    public Notice(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.noticeTitle = obj.get("title").getAsString();
        this.noticeCreateAt = obj.get("createAt").getAsString();
        this.noticeContent = obj.get("content").getAsString();
        this.noticeIndex = obj.get("index").getAsInt();
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }


    public String getNoticeCreateAt() {
        return noticeCreateAt;
    }

    public void setNoticeCreateAt(String noticeCreateAt) {
        this.noticeCreateAt = noticeCreateAt;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public int getNoticeIndex() {
        return noticeIndex;
    }

    public void setNoticeIndex(int noticeIndex) {
        this.noticeIndex = noticeIndex;
    }
}
