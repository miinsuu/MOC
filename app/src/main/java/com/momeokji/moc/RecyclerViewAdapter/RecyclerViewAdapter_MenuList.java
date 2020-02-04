package com.momeokji.moc.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.Menu;

import java.util.ArrayList;

public class RecyclerViewAdapter_MenuList extends RecyclerView.Adapter<RecyclerViewAdapter_MenuList.ItemViewHolder>{
    private ArrayList<Menu> menuList = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_menu_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(menuList.get(position));
    }

    @Override
    public int getItemCount(){
        return menuList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView menuName_Txt, menuPrice_Txt;
        ItemViewHolder(View ItemView){
            super(ItemView);
            menuName_Txt = ItemView.findViewById(R.id.menuName_Txt);
            menuPrice_Txt = ItemView.findViewById(R.id.menuPrice_Txt);
        }

        public void onBind(Menu menu){
            menuName_Txt.setText((menu.getName()));
            menuPrice_Txt.setText((menu.getPrice()) + "원");
        }
    };

    public ArrayList<Menu> getMenuList(){
        return this.menuList;
    }
    public void setMenuList(ArrayList<Menu> menuList){
        this.menuList = menuList;
    }

}
