package com.momeokji.moc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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

    public writeReviewMenuDialogFragment(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
        this.AllMenuList = selectedRestaurant.getMenuList();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, null);
//       LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, (ViewGroup) getActivity().findViewById(android.R.id.content),false);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("메뉴 선택");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        RecyclerView menulist = (RecyclerView) view.findViewById(R.id.menulist);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        menulist.setLayoutManager(linearLayoutManager);


 //       menulist.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data = new ArrayList<>();
        RecyclerViewAdapter_WriteReviewExpandableMenuList adapter = new RecyclerViewAdapter_WriteReviewExpandableMenuList(data);

        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, "대표"));
        for(int i = 0; i < 3; i++) {
            data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, selectedRestaurant.getMainMenus()[i].getName()));
        }

        Set<String> menuCategoryNameSet;
        String menuCategoryName = "";
        ArrayList<Menu> menuNameSet;
        for(int n = 0; n < AllMenuList.size(); n++) {
            menuCategoryNameSet = AllMenuList.get(n).keySet();
            for (String name : menuCategoryNameSet) {
                menuCategoryName = name;
                menuNameSet = (ArrayList<Menu>) AllMenuList.get(n).get(menuCategoryName);
                data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, name));
                for(Menu menu : menuNameSet) {
                    data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, menu.getName()));
                }
            }
        }

        menulist.setAdapter(new RecyclerViewAdapter_WriteReviewExpandableMenuList(data));

        return builder.create();
    }

    @Override
    public void onResume(){
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog_child, null);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);
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


}
