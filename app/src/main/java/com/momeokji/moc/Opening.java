package com.momeokji.moc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Opening extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
    ImageView mainImage;
    final String[] mainImageUrl = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        View start = findViewById(R.id.opening);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opening.this, MainActivity.class);
                startActivity(intent);
            }
        });

        db.collection("category")
                .orderBy("priority", Query.Direction.ASCENDING)
                //.whereEqualTo("priority", 5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // mainImageUrl[0] = document.getData().get("image").toString();
                                Log.e("Firebase확인", document.getId() + " => " + document.getData());
                                //Log.e("Firebase확인", mainImageUrl[0]);
                            }
                        } else {
                            Log.e("Firebase확인", "Error getting documents.", task.getException());
                        }
                    }
                });

        //connectDB();






//        Uri uri = Uri.parse("gs://mocfirebaseproject-28e15.appspot.com/"+mainImageUrl[0]);
//        mainImage = findViewById(R.id.mainImage);
//        Glide.with(this).load(uri).into(mainImage);

//        FirebaseStorage fs = FirebaseStorage.getInstance();
//        StorageReference imagesRef = fs.getReference().child("ramen.png");
//        Glide.with(this)
//                .load(imagesRef)
//                .into(mainImage);

}

    public void connectDB() {

        mainImage = findViewById(R.id.mainImage);
        //StorageReference ref = FirebaseStorage.getInstance().getReference(mainImageUrl[0]);
        Glide.with(this)
                .load("gs://mocfirebaseproject-28e15.appspot.com/"+mainImageUrl[0])
                .into(mainImage);

    }
}
