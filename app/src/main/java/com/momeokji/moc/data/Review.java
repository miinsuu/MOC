package com.momeokji.moc.data;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {
    private String reviewRestaurantName;
    private String reviewMenuName;
    private String reviewNickName;
    private String reviewTime;
    private Uri reviewImageUri;
    private String reviewText;
    private String reviewUserUID;
    private String reviewId; // 저장되는 MAP 고유ID

    public Review(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        Log.d("Review", obj.toString());
        Log.e("가게이름확인", obj.get("shopName").getAsString());

        this.reviewRestaurantName = obj.get("shopName").getAsString();
        this.reviewMenuName = obj.get("menu").getAsString();
        this.reviewNickName = obj.get("nick").getAsString();
        this.reviewTime = obj.get("date").getAsString();
        String uri = obj.get("img").getAsString();
        if(uri.equals(""))
            this.reviewImageUri = null;
        else
            this.reviewImageUri = Uri.parse(uri);
        this.reviewText = obj.get("content").getAsString();
        this.reviewUserUID = obj.get("userUID").getAsString();
        this.reviewId = id;

    }



    public String getReviewMenuName() {
        return reviewMenuName;
    }

    public void setReviewMenuName(String reviewMenuName) {
        this.reviewMenuName = reviewMenuName;
    }

    public String getReviewNickName() {
        return reviewNickName;
    }

    public void setReviewNickName(String reviewNickName) {
        this.reviewNickName = reviewNickName;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Uri getReviewImageUri() {
        return reviewImageUri;
    }

    public void setReviewImage(Uri reviewImage) {
        this.reviewImageUri = reviewImage;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewUserUID() {
        return reviewUserUID;
    }

    public void setReviewUserUID(String reviewUserUID) {
        this.reviewUserUID = reviewUserUID;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewRestaurantName() {
        return reviewRestaurantName;
    }

    public void setReviewRestaurantName(String reviewRestaurantName) {
        this.reviewRestaurantName = reviewRestaurantName;
    }
}
