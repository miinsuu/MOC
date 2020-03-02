package com.momeokji.moc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.momeokji.moc.data.Restaurant;


public class WriteReview extends Fragment {
    private Restaurant selectedRestaurant;


    public WriteReview(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);

        Button reviewPictureAddBtn = view.findViewById(R.id.reviewPictureAddBtn);
        reviewPictureAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               Intent intent = new Intent();
 //               intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
 //               intent.setAction(Intent.ACTION_GET_CONTENT);
 //               startActivityForResult(intent,101);
            }
        });

        return view;
    }


}
