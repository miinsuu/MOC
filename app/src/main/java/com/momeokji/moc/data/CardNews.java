package com.momeokji.moc.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardNews implements Parcelable {
    private String cardNewsTitle; // 제목
    ArrayList<Map<String, Object>> cardNewsImageUri; // 카드뉴스 사진 Uri
    private int cardNewsIndex; // 카드뉴스 index

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://mocfirebaseproject-28e15.appspot.com");
    StorageReference storageRef = storage.getReference();

    public CardNews(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.cardNewsTitle = obj.get("title").getAsString();
        this.cardNewsIndex = obj.get("index").getAsInt();
        this.cardNewsImageUri = new ArrayList<>();

        JsonArray jsonArray = obj.get("imgUriArray").getAsJsonArray();

        for(int i = 0; i < jsonArray.size(); i++) {
            // 사진 순서와 Uri 저장할 Map
            final Map<String, Object> cardNewsImage  = new HashMap<>();

            // DB에서 가져온 uriLastPath로 Storage Uri 받아오기
            String uriLastPath = jsonArray.get(i).getAsString();
            StorageReference imageRef = storageRef.child("cardnews/" + uriLastPath);
            final int index = i;
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Uri 추가
                    cardNewsImage.put("index", index);
                    cardNewsImage.put("imageUri", uri.toString());
                    cardNewsImageUri.add(cardNewsImage);
                    Log.e("Uri다운 성공", uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("Uri다운 실패", "");
                }
            });
        }


    }


    protected CardNews(Parcel in) {
        cardNewsTitle = in.readString();
        cardNewsIndex = in.readInt();
        cardNewsImageUri = in.readArrayList(CardNews.class.getClassLoader());
    }

    public static final Creator<CardNews> CREATOR = new Creator<CardNews>() {
        @Override
        public CardNews createFromParcel(Parcel in) {
            return new CardNews(in);
        }

        @Override
        public CardNews[] newArray(int size) {
            return new CardNews[size];
        }
    };

    public String getCardNewsTitle() {
        return cardNewsTitle;
    }

    public void setCardNewsTitle(String cardNewsTitle) {
        this.cardNewsTitle = cardNewsTitle;
    }

    public ArrayList<Map<String, Object>> getCardNewsImageUri() {
        return cardNewsImageUri;
    }

    public void setCardNewsImageUri(ArrayList<Map<String, Object>> cardNewsImageUri) {
        this.cardNewsImageUri = cardNewsImageUri;
    }

    public int getCardNewsIndex() {
        return cardNewsIndex;
    }

    public void setCardNewsIndex(int cardNewsIndex) {
        this.cardNewsIndex = cardNewsIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardNewsTitle);
        parcel.writeInt(cardNewsIndex);
        parcel.writeList(cardNewsImageUri);
    }

}
