package com.momeokji.moc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.data.Restaurant;
import com.momeokji.moc.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;


public class HomeFragment extends Fragment {
    private static final String TAG_TEXT = "text";
    private static final String TAG_IMAGE = "image";

    List<Map<String, Object>> bestRestaurantDialogItemList; // 밥집 리스트
    List<Map<String, Object>> bestBarDialogItemList; // 술집 리스트
    List<Map<String, Object>> bestCafeDialogItemList; // 카페 리스트

    int image = R.drawable.medal;
    String[] bestRestaurantText = {"탕화쿵푸", "도스마스", "내찜닭", "논두렁", "동경야네"}; // 밥집 리스트
    String[] bestBarText = {"블루힐", "파동추야", "짚동가리 쌩주", "씨밤, 정감, 투다리, 상투골", "젠사이야, JM BAR, 리얼후라이"}; // 술집 리스트
    String[] bestCafeText = {"메머드 커피", "봄봄", "조만식 1층 카페", "미학당", "스타벅스"}; // 카페 리스트

    private MainActivity mainActivity;

    public HomeFragment() {
    }

    public static HomeFragment getInstance() {
            return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = MainActivity.getInstance();

        Log.e("Login확인","사용자UID=>"+User.getUser().getUserUID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);
        RelativeLayout home_searchRestaurants_relativeLayout = view.findViewById(R.id.home_searchRestaurants_relativeLayout);
        home_searchRestaurants_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedFragmentManager.ReplaceFragment(0, SearchRestaurantFragment.getInstance(), Constants.ANIMATION_DIRECT.TO_RIGHT);

            }
        });

        Button moreEvent_btn = view.findViewById(R.id.moreEvent_btn); // 이벤트 배너 더보기 버튼
        final Button home_fragment_bestRestaurantBtn = view.findViewById(R.id.home_fragment_bestRestaurantBtn); // 최고의 밥집 버튼
        final Button home_fragment_bestBarBtn = view.findViewById(R.id.home_fragment_bestBarBtn); // 최고의 술집 버튼
        final Button home_fragment_bestCafeBtn = view.findViewById(R.id.home_fragment_bestCafeBtn); // 최고의 카페 버튼
        TextView home_fragment_anotherSubjectTxt = view.findViewById(R.id.home_fragment_anotherSubjectTxt); // 다른 주제 보기 텍스트뷰

        bestRestaurantDialogItemList = new ArrayList<>();
        bestBarDialogItemList = new ArrayList<>();
        bestCafeDialogItemList = new ArrayList<>();

        // 최고의 밥집 리스트 초기화
        for(int i=0 ; i<bestRestaurantText.length ; i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, image);
            itemMap.put(TAG_TEXT, bestRestaurantText[i]);

            bestRestaurantDialogItemList.add(itemMap);
        }

        // 최고의 술집 리스트 초기화
        for(int i=0 ; i<bestBarText.length ; i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, image);
            itemMap.put(TAG_TEXT, bestBarText[i]);

            bestBarDialogItemList.add(itemMap);
        }

        // 최고의 카페 리스트 초기화
        for(int i=0 ; i<bestCafeText.length ; i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, image);
            itemMap.put(TAG_TEXT, bestCafeText[i]);

            bestCafeDialogItemList.add(itemMap);
        }


        // 이벤트배너 더보기 버튼 클릭 시
        moreEvent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이벤트 및 광고 모아보기 페이지로 이동
                startActivity(new Intent(getContext(), EventPageActivity.class));
            }
        });

        // 최고의 밥집 버튼 다이얼로그 리스너
        home_fragment_bestRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(home_fragment_bestRestaurantBtn.getText().toString(), bestRestaurantDialogItemList);
            }
        });

        // 최고의 술집 버튼 다이얼로그 리스너
        home_fragment_bestBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(home_fragment_bestBarBtn.getText().toString(), bestBarDialogItemList);
            }
        });

        // 최고의 카페 버튼 다이얼로그 리스너
        home_fragment_bestCafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(home_fragment_bestCafeBtn.getText().toString(), bestCafeDialogItemList);
            }
        });

        // 다른 주제 더보기 텍스트뷰 리스너
        home_fragment_anotherSubjectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다른 주제 더보기 액티비티로 이동
                startActivity(new Intent(mainActivity, VariousContentsActivity.class));
            }
        });


        return view;
    }

    // 커스텀 다이얼로그 실행 메소드
    private void showAlertDialog(String subject, List<Map<String, Object>> dialogItemList)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_cardview, null);
        builder.setView(view);

        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        TextView textview_alterdialog_title = view.findViewById(R.id.textview_alterdialog_title);
        final AlertDialog dialog = builder.create();

        textview_alterdialog_title.setText(subject);

        SimpleAdapter simpleAdapter = new SimpleAdapter(mainActivity, dialogItemList,
                R.layout.alert_dialog_row,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.alertDialogItemImageView, R.id.alertDialogItemTextView});

        listview.setAdapter(simpleAdapter);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        //* 카테고리 버튼에 OnClickListener 등록
        //     - onStart에서 해주는 이유 : NavigationBarFragment의 BottomNavigationView를 등록하기 때문에 NavigationBarFragment가 확실히 생성된 후 작업하기 위해*//
        final View view = this.getView();

        Button[] categoryBtns = new Button[Constants.COUNTS.CATEGORY_NUM];
        categoryBtns[0] = view.findViewById(R.id.korean_btn);
        categoryBtns[1] = view.findViewById(R.id.chinese_btn);
        categoryBtns[2] = view.findViewById(R.id.japanese_btn);
        categoryBtns[3] = view.findViewById(R.id.western_btn);
        categoryBtns[4] = view.findViewById(R.id.snack_btn);
        categoryBtns[5] = view.findViewById(R.id.chicken_btn);
        categoryBtns[6] = view.findViewById(R.id.night_btn);
        categoryBtns[7] = view.findViewById(R.id.fast_btn);

        for(int i = 0; i < Constants.COUNTS.CATEGORY_NUM; i++) {
            final int position = i + 1;
            categoryBtns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayedFragmentManager.ReplaceFragment(1, RestaurantListFragment.getInstance(position), Constants.ANIMATION_DIRECT.TO_RIGHT);
                        displayedFragmentManager.bottomNavigationView.getMenu().getItem(Constants.NAVIGATION_ITEM.RESTAURANT_LIST).setChecked(true);
                }
            });
        }


    }
}
