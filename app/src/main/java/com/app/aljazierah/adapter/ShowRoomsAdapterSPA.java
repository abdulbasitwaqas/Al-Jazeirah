package com.app.aljazierah.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Store;

import java.util.List;

public class ShowRoomsAdapterSPA extends RecyclerView.Adapter<ShowRoomsAdapterSPA.MyViewHolder> {

    private List<Store> storeList;
    private Context context;

    public ShowRoomsAdapterSPA(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowRoomsAdapterSPA.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showrooms_list_search, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRoomsAdapterSPA.MyViewHolder holder, int position) {

        if (storeList.get(position).getStatus().toString().equals("1")) {
            if (AppController.setLocale())
            holder.showRoomNameTV.setText(storeList.get(position).getNameAr()+"       ("+context.getResources().getString(R.string.open)+")");
            else holder.showRoomNameTV.setText(storeList.get(position).getNameEn()+"       ("+context.getResources().getString(R.string.open)+")");
        } else {
            if (AppController.setLocale())
            holder.showRoomNameTV.setText(storeList.get(position).getNameAr()+"       ("+context.getResources().getString(R.string.close)+")");
            else holder.showRoomNameTV.setText(storeList.get(position).getNameEn()+"       ("+context.getResources().getString(R.string.close)+")");
        }

        holder.showRoomDirectionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + "&daddr=" + storeList.get(position).getLatitude() + "," + storeList.get(position).getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView showRoomNameTV;
        private ImageView showRoomDirectionIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            showRoomDirectionIV = itemView.findViewById(R.id.showRoomDirectionIV);
            showRoomNameTV = itemView.findViewById(R.id.showRoomNameTV);

        }
    }
}
