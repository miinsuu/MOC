package com.momeokji.moc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.ExpandedMenuFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListFragment;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;

public class RecyclerViewAdapter_RestaurantList extends RecyclerView.Adapter<RecyclerViewAdapter_RestaurantList.ItemViewHolder>{

    private ArrayList<Restaurant> restaurantList = new ArrayList<>();

    private OnItemClickListener viewHolderListener = null;                                      //
    public static interface OnItemClickListener{                                                       // ViewHolder의 커스텀 클릭 리스너
        void OnItemClick(View v, int position);                                                        // (클릭 리스너를 액티비티에서 구현하기 위해 defualt interface 사용)
    }
    public void setOnItemClickListener(OnItemClickListener viewHolderListener){
        this.viewHolderListener = viewHolderListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_restaurant_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(restaurantList.get(position));
    }

    @Override
    public int getItemCount(){
        return restaurantList.size();
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return this.restaurantList;
    }
    public void setRestaurantList(ArrayList<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView restaurantName_txt, restaurantDescription_txt, menuPriceRange_txt;
        private ImageButton menuExpand_imgbtn;
        ItemViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int targetPos = getAdapterPosition();
                        if(targetPos != RecyclerView.NO_POSITION) {
                            if (viewHolderListener != null)
                                viewHolderListener.OnItemClick(view, targetPos);
                        }
                    }
                });

            restaurantName_txt = itemView.findViewById(R.id.restaurantListItem_restaurantName_txt);
            restaurantDescription_txt = itemView.findViewById(R.id.restaurantListItem_description_txt);
            menuPriceRange_txt = itemView.findViewById(R.id.restaurantListItem_menuPriceRange_txt);
            menuExpand_imgbtn = itemView.findViewById(R.id.restaurantListItem_menuExpand_imgbtn);
        }
        public void onBind(Restaurant restaurant){
            restaurantName_txt.setText((restaurant.getRestaurantName()));
            restaurantDescription_txt.setText((restaurant.getMainMenu()).getName());
            menuPriceRange_txt.setText((restaurant.getMinPrice() + " ~ " + restaurant.getMaxPrice()));
        }

    }


}
