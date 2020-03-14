package com.momeokji.moc.data;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {
    private String reviewMenuName;
    private String reviewNickName;
    private String reviewTime;
    private Uri reviewImage;
    private String reviewText;

    public Review(String reviewMenuName, String reviewNickName, String reviewTime, String reviewText) {
        this.reviewMenuName = reviewMenuName;
        this.reviewNickName = reviewNickName;
        this.reviewTime = reviewTime;
        this.reviewImage = null;
        this.reviewText = reviewText;
    }

    public Review(String reviewMenuName, String reviewNickName, String reviewTime, Uri reviewImage, String reviewText) {
        this.reviewMenuName = reviewMenuName;
        this.reviewNickName = reviewNickName;
        this.reviewTime = reviewTime;
        this.reviewImage = reviewImage;
        this.reviewText = reviewText;
    }

    public static ArrayList<Review> createContactsList(int numContacts) {
        ArrayList<Review> contacts = new ArrayList<Review>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Review("떡볶이","aa","20200202","reviewtext"));
        }

        return contacts;

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

    public Uri getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(Uri reviewImage) {
        this.reviewImage = reviewImage;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
