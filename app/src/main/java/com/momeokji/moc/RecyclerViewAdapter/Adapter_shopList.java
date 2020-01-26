package com.momeokji.moc.RecyclerViewAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.Shop;

import java.util.ArrayList;

public class Adapter_shopList extends RecyclerView.Adapter<Adapter_shopList.ItemViewHolder>{
    private ArrayList<Shop> shopList = new ArrayList<>();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_shoplist, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(shopList.get(position));
    }

    @Override
    public int getItemCount(){
        return shopList.size();
    }

    public ArrayList<Shop> getShopList(){
        return this.shopList;
    }
    public void setShopList(ArrayList<Shop> shopList){
        this.shopList = shopList;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView shopName_Txt, mainMenu_Txt, minmaxPrice_Txt;
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

            shopName_Txt = itemView.findViewById(R.id.shopName_Txt);
            mainMenu_Txt = itemView.findViewById(R.id.mainMenu_Txt);
            minmaxPrice_Txt = itemView.findViewById(R.id.minmaxPrice_Txt);
        }
        public void onBind(Shop shop){
            shopName_Txt.setText((shop.getShopName()));
            mainMenu_Txt.setText((shop.getMainMenu()).getName());
            minmaxPrice_Txt.setText((shop.getMinPrice() + " ~ " + shop.getMaxPrice()));
        }

    }


}
