package com.momeokji.moc.Adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MyListFragment;
import com.momeokji.moc.R;
import com.momeokji.moc.data.MyListMenu;
public class RecyclerViewAdapter_MyListMenu extends RecyclerView.Adapter<RecyclerViewAdapter_MyListMenu.ItemViewHolder> {

    private MainActivity mainActivity;
    public RecyclerViewAdapter_MyListMenu(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MyListMenu.ItemViewHolder holder, int position){
        holder.onBind(mainActivity.restaurantDATA.MyListMenuList.get(position));
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_MyListMenu.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_mylist_menu_list, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount(){ return mainActivity.restaurantDATA.MyListMenuList.size();  }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView myList_menuName_Txt, myList_menuPrice_Txt, myList_restaurant_Txt, mylist_sumMoneyTxt;
        ImageButton myList_cancel_btn;
        ItemViewHolder(View itemView) {
            super(itemView);
            myList_menuName_Txt = itemView.findViewById(R.id.myList_menuName_Txt);
            myList_menuPrice_Txt = itemView.findViewById(R.id.myList_menuPrice_Txt);
            myList_restaurant_Txt = itemView.findViewById(R.id.myList_restaurant_Txt);
            myList_cancel_btn = itemView.findViewById(R.id.myList_cancel_imgbtn);
            myList_cancel_btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mainActivity.restaurantDATA.MyListMenuList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    // 마이리스트 가격 총 합계
                    MyListFragment.getInstance(mainActivity).calculatePrice();
                }
            });
            mylist_sumMoneyTxt = itemView.findViewById(R.id.mylist_sumMoneyTxt);
        }

        public void onBind(final MyListMenu myListMenu) {
            myList_menuName_Txt.setText(myListMenu.getName());
            myList_menuPrice_Txt.setText(myListMenu.getPrice() + "원");
            myList_restaurant_Txt.setText(myListMenu.getRestaurantName());

        }


    }
}
