package com.app.aljazierah.adapter.CategoriesName;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.SubCategoriesModel;
import com.app.aljazierah.ui.SeeAllActivity;

import java.util.List;

public class SubCategoriesNameAdapter extends RecyclerView.Adapter<SubCategoriesNameAdapter.MyViewHolder> {
    private List<SubCategoriesModel> subCategoriesModelList;
    private Context context;
    private boolean isArabic = AppController.setLocale();

    public SubCategoriesNameAdapter(List<SubCategoriesModel> subCategoriesModelList, Context context) {
        this.subCategoriesModelList = subCategoriesModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategories_name_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull SubCategoriesNameAdapter.MyViewHolder holder, int position) {
        if (isArabic)
            holder.subCatNameTV.setText(subCategoriesModelList.get(position).getName_ar());
        else
            holder.subCatNameTV.setText(subCategoriesModelList.get(position).getName_en());

        holder.subCategoriesNameLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeAllActivity.class);
                intent.putExtra("categoryId", "" + subCategoriesModelList.get(position).getParent_id());
                intent.putExtra("subCategoryId", ""+subCategoriesModelList.get(position).getId());
                intent.putExtra("bestSelletList", "0");

                if (isArabic)
                    intent.putExtra("catName", "" + subCategoriesModelList.get(position).getName_ar());
                else
                    intent.putExtra("catName", "" + subCategoriesModelList.get(position).getName_en());
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return subCategoriesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView subCatNameTV;
        private LinearLayout subCategoriesNameLL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subCatNameTV = itemView.findViewById(R.id.subCatNameTV);
            subCategoriesNameLL = itemView.findViewById(R.id.subCategoriesNameLL);
        }
    }
}
