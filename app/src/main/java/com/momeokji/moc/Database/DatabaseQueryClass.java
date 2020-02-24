package com.momeokji.moc.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

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

        public static void getJapaneseShopList(final DataListener dataListener){
            Log.e("ShopFromDB", "getJapaneseShopList");
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

    }
}


