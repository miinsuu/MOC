package com.momeokji.moc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_WriteReviewExpandableMenuList;
import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;
import com.momeokji.moc.data.Restaurant;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class WriteReview extends Fragment {
    private Restaurant selectedRestaurant;
    private ImageView reviewPicture_imageView;
    private EditText writeReview_editText;
    private static TextView menuChoiceText;
    private writeReviewMenuDialogFragment menuChoice; // 메뉴선택 다이얼로그
    private Uri selectedImageUri = null; // 선택한 사진 Uri
    private static final int DIALOG_REQUEST_CODE = 1234;
    Context context;
    private List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data;

    private static String choiceMenu = ""; // 선택한 메뉴이름
    private String reivewContent; // 작성한 리뷰 글
    private String restaurantName; // 가게이름

    public WriteReview(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);


        Button writeReview_back_btn = view.findViewById(R.id.writeReview_back_btn);
        Button writeReview_done_btn = view.findViewById(R.id.writeReview_done_btn);
        final TextView writetReview_restaurantName_txt = view.findViewById(R.id.writetReview_restaurantName_txt);
        TextView minMaxPrice = view.findViewById(R.id.writeReview_restaurantRangePrice_txt);
        MarqueeTextView preview = view.findViewById(R.id.writeReview_restaurantPreview_txt);

        Button writeReviewMenuBtn = view.findViewById(R.id.writeReviewMenuBtn);
        menuChoiceText = view.findViewById(R.id.menuChoiceText);
        Button reviewPictureAddBtn = view.findViewById(R.id.reviewPictureAddBtn);
        reviewPicture_imageView = view.findViewById(R.id.reviewPicture_imageView);
        LinearLayout ll = view.findViewById(R.id.ll);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        LinearLayout ll3 = view.findViewById(R.id.ll3);
        writeReview_editText = view.findViewById(R.id.writeReview_editText);

        writetReview_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        restaurantName = selectedRestaurant.getRestaurantName();
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);

        //******* writeReviewMenuDialogFragment 에서 선택한 메뉴이름 string 보여주기 *************
        menuChoice = new writeReviewMenuDialogFragment(selectedRestaurant);

        // 메뉴선택 다이얼로그 버튼
        writeReviewMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu();
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        writeReview_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        // DB에 리뷰게시글 올리기
        writeReview_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리뷰글이 없으면 토스트메시지
                if(writeReview_editText.getText().toString().equals("")) {
                    Toast.makeText(((MainActivity)getActivity()), "리뷰를 작성해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 리뷰메뉴가 없으면 토스트메시지
                if(choiceMenu.equals("")) {
                    Toast.makeText(((MainActivity)getActivity()), "리뷰할 메뉴를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                reivewContent = writeReview_editText.getText().toString();

                // 업로드 할 사진이 있다면
                if(selectedImageUri != null) {
                    // 리뷰사진을 Strorage에 올리기
                    FirebaseStorage storage = FirebaseStorage.getInstance("gs://mocfirebaseproject-28e15.appspot.com");
                    final StorageReference storageRef = storage.getReference();
                    StorageReference riverRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());
                    UploadTask uploadTask = riverRef.putFile(selectedImageUri);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("uploadd", e.toString());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("uploadd", taskSnapshot.getMetadata().toString());
                            StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());

                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("uploadd", uri.toString());
                                    // 사진 업로드가 성공적으로 끝나면 나머지 데이터를 DB에 올리기
                                    DatabaseQueryClass.ReviewDB.createReview(choiceMenu, reivewContent, uri.toString(), restaurantName, new MyOnSuccessListener() {
                                        @Override
                                        public void onSuccess() {
                                            //Toast.makeText(((MainActivity)getActivity()), "리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                                            Log.e("업로드 완료","=========");

                                            // 업로드하고 뒤로가기
                                           // backReviewPage();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });
                        }
                    });

                } else {
                    // 업로드할 사진이 없다면
                    DatabaseQueryClass.ReviewDB.createReview(choiceMenu, reivewContent, "", restaurantName, new MyOnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            //Toast.makeText(((MainActivity)getActivity()), "리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("업로드 완료","=========");

                            // 업로드하고 뒤로가기
                            //backReviewPage();
                        }
                    });
                }
                // 성공메시지
                Toast.makeText(((MainActivity)getActivity()), "\""+restaurantName+"\" 리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                // 업로드하고 홈화면으로 이동
                goToHomePage();

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
            selectedImageUri = data.getData();
            reviewPicture_imageView.setImageURI(selectedImageUri);
            reviewPicture_imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            reviewPicture_imageView.setAdjustViewBounds(true);
        }
        else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(),"사진등록 취소",Toast.LENGTH_SHORT).show();
        }
    }

    void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(writeReview_editText.getWindowToken(),0);
    }

    void selectMenu(){
        //writeReviewMenuDialogFragment dialogFragment = new writeReviewMenuDialogFragment(selectedRestaurant);
        menuChoice.show(getFragmentManager(),"dialog");
        menuChoice.setTargetFragment(this,DIALOG_REQUEST_CODE);

    }

    // 선택한 메뉴이름을 다이얼로그가 닫힐 때, textview에 입력되도록
    public static void sendReviewMenuName(String menuChoiceName) {
        menuChoiceText.setText(menuChoiceName);
        choiceMenu = menuChoiceName;
    }

    public void goToHomePage() {
        // 리뷰 작성 후 홈화면으로 이동
        //startActivity(new Intent((MainActivity)getActivity(), MainActivity.class));
        ((MainActivity)getActivity()).onBackPressed();
        //displayedFragmentManager.ReplaceFragment(0, new RestaurantInfoFragment(selectedRestaurant), Constants.ANIMATION_DIRECT.TO_RIGHT);
        // 위 코드 처럼 가게 첫페이지로 이동가능하면 수정하기
    }

}





