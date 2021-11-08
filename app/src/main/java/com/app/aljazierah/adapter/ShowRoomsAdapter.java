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

public class ShowRoomsAdapter extends RecyclerView.Adapter<ShowRoomsAdapter.MyViewHolder> {

    private List<Store> storeList;
    private Context context;

    public ShowRoomsAdapter(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowRoomsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showrooms_list, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRoomsAdapter.MyViewHolder holder, int position) {


        if (AppController.setLocale()) {
            holder.showRoomNameTV.setText(storeList.get(position).getNameAr());
        } else {
            holder.showRoomNameTV.setText(storeList.get(position).getNameEn());
        }

        if (!storeList.get(position).getStoreNumber().equals("")) {
            holder.phoneSRTV.setText("+" + storeList.get(position).getStoreNumber());
        } else {
            holder.phoneSRTV.setVisibility(View.GONE);
        }


        if (storeList.get(position).getMorningOpentime().equals("") && storeList.get(position).getEveningClosetime().equals(""))
            holder.storeTimingsTV.setVisibility(View.GONE);
        else
            holder.storeTimingsTV.setText(context.getResources().getString(R.string.open) + " " + context.getResources().getString(R.string.st_morning) + "(" + storeList.get(position).getMorningOpentime() + " - " + storeList.get(position).getMorningClosetime() + ")\n"
                    + context.getResources().getString(R.string.open) + " " + context.getResources().getString(R.string.st_evening) + "(" + storeList.get(position).getEveningOpentime() + " - " + storeList.get(position).getEveningClosetime() + ")");
//            holder.showRoomStatusTV.setText(context.getResources().getString(R.string.open));


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
        private TextView showRoomNameTV, phoneSRTV, storeTimingsTV, showRoomStatusTV;
        private ImageView showRoomDirectionIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            showRoomDirectionIV = itemView.findViewById(R.id.showRoomDirectionIV);
            showRoomNameTV = itemView.findViewById(R.id.showRoomNameTV);
            phoneSRTV = itemView.findViewById(R.id.phoneSRTV);
            storeTimingsTV = itemView.findViewById(R.id.storeTimingsTV);
            showRoomStatusTV = itemView.findViewById(R.id.showRoomStatusTV);

        }
    }
}
