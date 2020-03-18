package com.momeokji.moc.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.Menu;

import java.util.ArrayList;

public class RecyclerViewAdapter_MenuClassificationTab extends RecyclerView.Adapter<RecyclerViewAdapter_MenuClassificationTab.ItemViewHolder> {
    private ArrayList<Menu> mlist ;

    public RecyclerViewAdapter_MenuClassificationTab(ArrayList<Menu> mlist) {
        this.mlist = mlist;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_menu_classification_tab, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView menuName, menuPrice;
        private ImageButton MyListAddBtn;

        ItemViewHolder(View ItemView){
            super(ItemView);
            menuName = ItemView.findViewById(R.id.menuClassificationTab_menuName);
            menuPrice = ItemView.findViewById(R.id.menuClassificationTab_menuPrice);
            MyListAddBtn = ItemView.findViewById(R.id.MyListAddBtn);

        }

        public void onBind(Menu menu){
            menuName.setText((menu.getName()));
            menuPrice.setText((menu.getPrice()) + "원");
        }
    };

    public ArrayList<Menu> getMenuList(){
        return this.mlist;
    }
    public void setMenuList(ArrayList<Menu> menuList){
        this.mlist = mlist;
    }
}
