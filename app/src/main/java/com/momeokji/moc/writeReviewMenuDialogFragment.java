package com.momeokji.moc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_WriteReviewExpandableMenuList;
import com.momeokji.moc.data.Menu;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class writeReviewMenuDialogFragment extends DialogFragment {

    private Restaurant selectedRestaurant;
    private ArrayList<Map> AllMenuList;
    private ArrayList<Menu> menuNameSet;
    private View childDialog;
    private Set<String> menuCategoryNameSet;
    private String menuCategoryName = "";
//    List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data = new ArrayList<>();
    private Context context;
    private RecyclerView menulist;
    private RecyclerView.Adapter adapter;
    public static String reviewMenuName = "";

    public writeReviewMenuDialogFragment(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
        this.AllMenuList = selectedRestaurant.getMenuList();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        context = requireActivity();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, null);
//       LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, (ViewGroup) getActivity().findViewById(android.R.id.content),false);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("메뉴 선택");
/*        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reviewMenuName = "";
                RecyclerViewAdapter_WriteReviewExpandableMenuList.ListChildViewHolder childHolder = ((RecyclerViewAdapter_WriteReviewExpandableMenuList.ListChildViewHolder) holder;
                childDialog = getLayoutInflater().inflate(R.layout.fragment_write_review_menu_dialog_child, null,false);
                CheckBox checkBoxMenu = childDialog.findViewById(R.id.checkBoxMenu);

                for(int j = 0; j <data.size(); j++) {
                    if (checkBoxMenu.isChecked()) {
                        reviewMenuName += checkBoxMenu.getText();
                        reviewMenuName += "aa";
                    }
                }
  /*


               // Toast.makeText(getContext(),reviewMenuName,Toast.LENGTH_SHORT).show();


            }
        });

 */
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reviewMenuName= "";

            }
        });


        menulist = (RecyclerView) view.findViewById(R.id.menulist);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        menulist.setLayoutManager(linearLayoutManager);

        List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data = new ArrayList<>();
        adapter = new RecyclerViewAdapter_WriteReviewExpandableMenuList(context,data);

        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, "대표"));
        for(int i = 0; i < 3; i++) {
            data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, selectedRestaurant.getMainMenus()[i].getName(),false));
        }

 //       Set<String> menuCategoryNameSet;
 //       String menuCategoryName = "";
 //       ArrayList<Menu> menuNameSet;
        for(int n = 0; n < AllMenuList.size(); n++) {
            menuCategoryNameSet = AllMenuList.get(n).keySet();
            for (String name : menuCategoryNameSet) {
                menuCategoryName = name;
                menuNameSet = (ArrayList<Menu>) AllMenuList.get(n).get(menuCategoryName);
                data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, name));
                for(Menu menu : menuNameSet) {
                    data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, menu.getName(),false));
                }
            }
        }

///
        builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String reviewMenuName = "";
                List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data = ((RecyclerViewAdapter_WriteReviewExpandableMenuList)adapter).getData();

//                childDialog = getLayoutInflater().inflate(R.layout.fragment_write_review_menu_dialog_child, null,false);
//                CheckBox checkBoxMenu = childDialog.findViewById(R.id.checkBoxMenu);

                for(int j = 0; j <data.size(); j++) {
                    RecyclerViewAdapter_WriteReviewExpandableMenuList.Item itemMenu = data.get(j);
                    if (itemMenu.isSelected == true) {
                        reviewMenuName += itemMenu.getText();
                        reviewMenuName += ", ";
                    }
                }
                if(reviewMenuName.length() > 2)
                    reviewMenuName = reviewMenuName.substring(0, reviewMenuName.length() - 2);

                Log.e("메뉴선택",reviewMenuName);
                // 리뷰할 메뉴 리스트보내주기
                WriteReview.sendReviewMenuName(reviewMenuName);
                // 리스트 리셋
                reviewMenuName = "";
            }
        });

        menulist.setItemViewCacheSize(data.size());
        menulist.setAdapter(adapter);

        return builder.create();
    }



    /*
    @Override
    public void onResume(){
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog_child, null);
        final CheckBox checkBox = view.findViewById(R.id.checkBoxMenu);
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button negativeBtn = (Button) d.getButton(Dialog.BUTTON_NEGATIVE);
            negativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(false);
                    Boolean wantToCloseDialog = false;
                    if(wantToCloseDialog)
                        d.dismiss();
                }
            });
        }
    }

 */


}
