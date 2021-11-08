package com.app.aljazierah.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.CartParamResponse.BanksModel;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.SubCategoriesModel;

import java.util.List;

public class SubCatListAdapter extends RecyclerView.Adapter<SubCatListAdapter.MyViewHolder> {
    private List<SubCategoriesModel> subCategoriesModelList;
    private Context context;
    private boolean isArabic = AppController.setLocale();
    public Clicks clicks;

    public SubCatListAdapter(List<SubCategoriesModel> subCategoriesModelList, Context context, Clicks clicks) {
        this.subCategoriesModelList = subCategoriesModelList;
        this.context = context;
        this.clicks = clicks;
    }

    public void setUpdatedData(List<SubCategoriesModel> subCategoriesModelList){
        this.subCategoriesModelList = subCategoriesModelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_list, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (isArabic)
            holder.catNameTV.setText(subCategoriesModelList.get(position).getName_ar());
        else
            holder.catNameTV.setText(subCategoriesModelList.get(position).getName_en());


        Log.d("**statusBD",""+subCategoriesModelList.get(position).getisSelected());

        if (subCategoriesModelList.get(position).getisSelected()){
            holder.tickSCL.setVisibility(View.VISIBLE);
        }
        else {
            holder.tickSCL.setVisibility(View.GONE);
        }

        holder.subCategoriesNameLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks.selectSubCategory(subCategoriesModelList.get(position), position);
                /*if (subCategoriesModelList.get(position).getisSelected()) {
                    holder.tickSCL.setVisibility(View.VISIBLE);
                    subCategoriesModelList.get(position).setSelected(false);
                } else if (!subCategoriesModelList.get(position).getisSelected()){
                    holder.tickSCL.setVisibility(View.GONE);
                    subCategoriesModelList.get(position).setSelected(true);
                }*/

                Log.d("**status",""+subCategoriesModelList.get(position).getisSelected());
            }
        });
    }


    @Override
    public int getItemCount() {
        return subCategoriesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView catNameTV;
        private LinearLayout subCategoriesNameLL;
        private ImageView tickSCL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catNameTV = itemView.findViewById(R.id.catNameTV);
            tickSCL = itemView.findViewById(R.id.tickSCL);
            subCategoriesNameLL = itemView.findViewById(R.id.subCategoriesNameLL);
        }
    }

    public interface Clicks {
        void selectSubCategory (SubCategoriesModel subCategoriesModel, int position);

    }

}
