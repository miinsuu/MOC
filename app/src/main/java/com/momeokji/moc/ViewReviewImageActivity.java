package com.momeokji.moc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ViewReviewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review_image);

        ImageView reviewImage = findViewById(R.id.viewReviewImage_activity_image);

        Intent intent = getIntent();
        Uri imageUri = Uri.parse(intent.getStringExtra("imageUri"));

        Glide.with(this).load(imageUri).into(reviewImage);
    }
}
