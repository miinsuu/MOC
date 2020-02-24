package com.momeokji.moc;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_MenuClassificationTab;
import com.momeokji.moc.data.Menu;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuClassificationTab extends Fragment {

    private ArrayList<Menu> menuArrayList;


    public MenuClassificationTab() {
        // Required empty public constructor
    }
    public MenuClassificationTab(ArrayList<Menu> arrayList) {
        this.menuArrayList = arrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_menu_classification_tab, container, false);

        RecyclerView menuList_recyclerView = view.findViewById(R.id.menuList_recyclerView);
        menuList_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuList_recyclerView.setHasFixedSize(true);

        RecyclerViewAdapter_MenuClassificationTab adapter = new RecyclerViewAdapter_MenuClassificationTab(menuArrayList);
        menuList_recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        //for Test
//        ArrayList<Menu> mSearchData = new ArrayList<>();
//        mSearchData.add(new Menu("asd", "5050"));
//        mSearchData.add(new Menu("sf", "9090"));
    }

}
