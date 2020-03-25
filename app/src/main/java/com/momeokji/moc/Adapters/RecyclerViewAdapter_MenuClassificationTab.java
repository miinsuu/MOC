package com.momeokji.moc.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.data.Menu;
import com.momeokji.moc.data.MyListMenu;

import java.util.ArrayList;

public class RecyclerViewAdapter_MenuClassificationTab extends RecyclerView.Adapter<RecyclerViewAdapter_MenuClassificationTab.ItemViewHolder> {
    private ArrayList<Menu> mlist ;
    private ImageButton myListAddBtn;

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
        ItemViewHolder(View ItemView){
            super(ItemView);
            menuName = ItemView.findViewById(R.id.menuClassificationTab_menuName);
            menuPrice = ItemView.findViewById(R.id.menuClassificationTab_menuPrice);
            myListAddBtn = ItemView.findViewById(R.id.MyListAddBtn);
        }

        public void onBind(final Menu menu){
            menuName.setText((menu.getName()));
            menuPrice.setText((menu.getPrice()) + "원");
            myListAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.getInstance() != null) {
                        if(MainActivity.getInstance().restaurantDATA.MyListMenuList.contains(new MyListMenu(menu, MainActivity.getInstance().restaurantDATA.selectedRestaurant.getRestaurantName()))) {
                            Toast.makeText(MainActivity.getInstance(), "이미 등록된 메뉴입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            MainActivity.getInstance().restaurantDATA.MyListMenuList.add(new MyListMenu(menu, MainActivity.getInstance().restaurantDATA.selectedRestaurant.getRestaurantName()));
                            Toast.makeText(MainActivity.getInstance(), "메뉴가 마이리스트에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    };

    public ArrayList<Menu> getMenuList(){
        return this.mlist;
    }
    public void setMenuList(ArrayList<Menu> menuList){
        this.mlist = mlist;
    }
}
