package com.momeokji.moc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.data.Restaurant;

import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class WriteReview extends Fragment {
    private Restaurant selectedRestaurant;
    private ImageView reviewPicture_imageView;


    public WriteReview(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);


        Button writeReview_back_btn = view.findViewById(R.id.writeReview_back_btn);
        Button writeReview_done_btn = view.findViewById(R.id.writeReview_done_btn);
        Button reviewPictureAddBtn = view.findViewById(R.id.reviewPictureAddBtn);
        TextView writetReview_restaurantName_txt = view.findViewById(R.id.writetReview_restaurantName_txt);
        TextView minMaxPrice = view.findViewById(R.id.writeReview_restaurantRangePrice_txt);
        MarqueeTextView preview = view.findViewById(R.id.writeReview_restaurantPreview_txt);
        reviewPicture_imageView = view.findViewById(R.id.reviewPicture_imageView);

        writetReview_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);

        writeReview_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        writeReview_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //사진앱연결
        reviewPictureAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //기본갤러리앱
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //구글앱
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
 //               intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,1);
            }
        });


        return view;
    }

    //사진 화면에 보이기
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                reviewPicture_imageView.setImageBitmap(bm);
                reviewPicture_imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                reviewPicture_imageView.setAdjustViewBounds(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(),"취소",Toast.LENGTH_SHORT).show();
        }
    }




}
