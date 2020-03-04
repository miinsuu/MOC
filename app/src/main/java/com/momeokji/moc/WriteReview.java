package com.momeokji.moc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.data.Restaurant;

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
        LinearLayout ll = view.findViewById(R.id.ll);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        LinearLayout ll3 = view.findViewById(R.id.ll3);
        final EditText writeReview_editText = view.findViewById(R.id.writeReview_editText);

        writetReview_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(writeReview_editText.getWindowToken(),0);
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm2 = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(writeReview_editText.getWindowToken(),0);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm3 = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(writeReview_editText.getWindowToken(),0);
            }
        });
        writeReview_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        writeReview_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        //사진앱연결
        reviewPictureAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 /*               Intent intent = new Intent();
                //기본갤러리앱
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //구글앱
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
 //               intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,1);
  */
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

                startActivityForResult(intent,1);
            }
        });


        return view;
    }

    //사진 화면에 보이기
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
 /*       if(requestCode == 1 && resultCode == RESULT_OK) {
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

  */
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            reviewPicture_imageView.setImageURI(selectedImageUri);
            reviewPicture_imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            reviewPicture_imageView.setAdjustViewBounds(true);
        }
        else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(),"취소",Toast.LENGTH_SHORT).show();
        }
    }




}
