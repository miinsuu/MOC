package com.momeokji.moc.Database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.momeokji.moc.data.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseQueryClass {
    private static DatabaseQueryClass databaseQuery = new DatabaseQueryClass();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private  DatabaseQueryClass(){

    }


    public static class ShopFromDB {

        public static void getKoreanShopList(final DataListener dataListener){
                Log.e("ShopFromDB", "getKoreanShopList");
                CollectionReference shopRef = db.collection("korean");
                shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Shop", document.getId() + " => " + document.getData());
                                dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                            }
                        } else {
                            Log.d("Shop", "Error getting documents: ", task.getException());
                        }
                    }
                });
        }

        public static void getChineseShopList(final DataListener dataListener){
            Log.e("ShopFromDB", "getChineseShopList");
            CollectionReference shopRef = db.collection("chinese");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getJapaneseShopList(final DataListener dataListener){
            Log.e("ShopFromDB", "getJapaneseShopList");
            CollectionReference shopRef = db.collection("japanese");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getChickenShopList(final DataListener dataListener){
            CollectionReference shopRef = db.collection("chicken");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getSnackShopList(final DataListener dataListener){
            CollectionReference shopRef = db.collection("snack");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getWesternShopList(final DataListener dataListener){
            CollectionReference shopRef = db.collection("western");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getFastShopList(final DataListener dataListener){
            CollectionReference shopRef = db.collection("fast");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getNightShopList(final DataListener dataListener){
            CollectionReference shopRef = db.collection("night");
            shopRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Shop", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Shop", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

    }



    public static class UserInfo {

        // Email주소를 이용해서 nickname을 DB에 저장하는 메소드
        public static void putUserNickNameToDB(String userUID, final String nickname){
            Map<String, Object> user = new HashMap<>();
            user.put("userUID", userUID);
            user.put("nick", nickname);

            // users Collection에 [userUID:key, nickname:value]로 사용자 정보 저장
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // 저장한 문서 ID
                            Log.e("putUserNickNameToDB", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("putUserNickNameToDB", "Error adding document", e);
                        }
                    });
        }

        // 이메일로 DB에 저장된 닉네임, 사용자ID를 가져옴
        public static void getUserInfoByUserUID(final String userUID, final DataListener dataListener) {
            db.collection("users")
                    .whereEqualTo("userUID", userUID)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        String data = null;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String json = new Gson().toJson(document.getData());
                            JsonElement element = new JsonParser().parse(json);
                            JsonObject jobj = element.getAsJsonObject();
                            jobj.addProperty("usersMapName", document.getId());

                            data = new Gson().toJson(jobj);
                        }
                        if (data != null)
                            dataListener.getData(data, null);
                    } else {
                        Log.d("user", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        // 로그인시 DB에 있는 users Collection에 유저정보가 저장되어있는지 userUID기준으로 체크한 뒤, 없으면 저장
        public static void checkUserDuplication(final String userUID, final String nickname){
            db.collection("users")
                    .whereEqualTo("userUID", userUID)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(task.getResult().isEmpty()) {
                            Log.e("LoginCheck", "신규로그인");
                            DatabaseQueryClass.UserInfo.putUserNickNameToDB(userUID, nickname);
                        } else {
                            Log.d("LoginCheck","ReLogin");
                        }
                    } else {

                    }
                }
            });
        }

        // 유저 닉네임 업데이트
        public static void updateNickname(String nickname, String usersMapName){
            Log.d("updateNickname", nickname+" , "+usersMapName);

            DocumentReference updateRef = db.collection("users").document(usersMapName);
            updateRef
                    .update("nick", nickname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("닉네임업데이트", "성공");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("닉네임업데이트", "Error updating document", e);
                        }
                    });
        }




    }


    // DB에 리뷰 CRUD 작업
    public static class ReviewDB {

        // 리뷰 쓰기
        public static void createReview(final String reviewMenuName,
                                      final String reviewText,
                                      final String reviewImageUrl,
                                      final String reviewShopName, final MyOnSuccessListener myOnSuccessListener)
        {
            Map<String, Object> review  = new HashMap<>();
            review.put("menu", reviewMenuName);
            review.put("content",reviewText );
            review.put("img", reviewImageUrl);
            review.put("shopName", reviewShopName);
            review.put("date",  (new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date())));
            review.put("nick", User.getUser().getNickName());
            review.put("userUID", User.getUser().getUserUID());
            review.put("createdAt",  Long.parseLong( new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) );
            Log.e("리뷰upload", review.toString());

            db.collection("reviews")
                    .add(review)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("uploadd", "create post upload success " + documentReference.getId());
                            myOnSuccessListener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("uploadd", "Error adding document", e);
                        }
                    });
        }

        // 가게마다 리뷰 가져오기
        public static void getReviewsByShop(String shopName, final DataListener dataListener){
            CollectionReference reviewRef = db.collection("reviews");
            Query query = reviewRef.whereEqualTo("shopName", shopName).orderBy("createdAt", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Review", document.getId() + " => " + document.getData());
                            dataListener.getData( new Gson().toJson(document.getData()), document.getId());
                        }
                    } else {
                        Log.d("Post", "Error getting documents: ", task.getException());
                    }
                }
            });
        }


        // 유저UID로 내가 쓴 리뷰 가져오기
        public static void getMyReviewsByUserUID(final String userUID, final DataListener dataListener){
            CollectionReference postRef = db.collection("reviews");
            Query query = postRef.whereEqualTo("userUID", userUID).orderBy("createdAt", Query.Direction.DESCENDING);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("MyReview", document.getId() + " => " + document.getData());
                            String json = new Gson().toJson(document.getData());
                            dataListener.getData(json, document.getId());
                        }
                    } else {
                        Log.d("Post", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        // 내가 쓴 리뷰에서 리뷰ID로 리뷰 삭제하기
        public static void deleteReview(String reviewId, final MyOnSuccessListener myOnSuccessListener){
            db.collection("reviews").document(reviewId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("리뷰삭제", "성공");
                            myOnSuccessListener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("del", "Error deleting document", e);
                        }
                    });
        }

    }
}


