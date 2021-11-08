package com.app.aljazierah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.R;
import com.app.aljazierah.object.SingleImageModel;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class SingleImageAdapter extends RecyclerView.Adapter<SingleImageAdapter.MyViewHolder>{
    private List<SingleImageModel> singleImageModelList;
    private Context context;

    public SingleImageAdapter(List<SingleImageModel> singleImageModelList, Context context ) {
        this.singleImageModelList = singleImageModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleImageAdapter.MyViewHolder holder, int position) {

        Glide.with(context)
                .load(IMAGE_BASE_URL + singleImageModelList.get(position).getImageURL())
                .placeholder(R.drawable.ic_place_holder)
                .into(holder.singleIV);
        holder.singleIV.setOnTouchListener(new ImageMatrixTouchHandler(holder.singleIV.getContext()));

    }

    @Override
    public int getItemCount() {
        return singleImageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView singleIV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singleIV=itemView.findViewById(R.id.singleIV);
        }
    }
}
