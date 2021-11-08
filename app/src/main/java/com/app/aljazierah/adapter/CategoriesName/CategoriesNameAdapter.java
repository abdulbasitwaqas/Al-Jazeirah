package com.app.aljazierah.adapter.CategoriesName;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.ui.SeeAllActivity;

import java.util.List;

public class CategoriesNameAdapter extends RecyclerView.Adapter<CategoriesNameAdapter.MyViewHolder> {
    private List<Categories> categoriesList;
    private Context context;
    private boolean isArabic = AppController.setLocale();


    public CategoriesNameAdapter(List<Categories> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_name_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (isArabic) {
            holder.categoriesNameTV.setText(categoriesList.get(position).getName_ar());
        } else {
            holder.categoriesNameTV.setText(categoriesList.get(position).getName_en());
        }
        holder.subCategoriesRVCNL.setVisibility(View.GONE);

        holder.linearLayoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoriesList.get(position).getIs_shown()) {
                    categoriesList.get(position).setIs_shown(false);
                    holder.arrowIV.setBackground(context.getResources().getDrawable(R.drawable.ic_arrow_upp));
                    holder.subCategoriesRVCNL.setVisibility(View.VISIBLE);
                } else {
                    categoriesList.get(position).setIs_shown(true);

                    holder.arrowIV.setBackground(context.getResources().getDrawable(R.drawable.ic_right_chevron));
                    holder.subCategoriesRVCNL.setVisibility(View.GONE);
                }
//                notifyDataSetChanged();
            }
        });

        /*if (categoriesList.get(position).getIs_shown()) {
            holder.subCategoriesRVCNL.setVisibility(View.VISIBLE);

        } else {
            holder.subCategoriesRVCNL.setVisibility(View.GONE);

        }*/



        holder.subCategoriesRVCNL.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        SubCategoriesNameAdapter productbyLocationAdapter = new SubCategoriesNameAdapter(categoriesList.get(position).getSubCategoriesModelList(), context);
        holder.subCategoriesRVCNL.setAdapter(productbyLocationAdapter);


        if (categoriesList.get(position).getSubCategoriesModelList().size()<=0){
            holder.arrowIV.setVisibility(View.GONE);

            holder.categoriesNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SeeAllActivity.class);
                    intent.putExtra("categoryId", "" + categoriesList.get(position).getId());
                    intent.putExtra("subCategoryId", "" );
                    intent.putExtra("bestSelletList", "0");
                    if (isArabic)
                        intent.putExtra("catName", "" + categoriesList.get(position).getName_ar());
                    else
                        intent.putExtra("catName", "" + categoriesList.get(position).getName_en());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView categoriesNameTV;
        private ImageView arrowIV;
        private LinearLayout linearLayoutName;
        private RecyclerView subCategoriesRVCNL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesNameTV = itemView.findViewById(R.id.categoriesNameTV);
            arrowIV = itemView.findViewById(R.id.arrowIV);
            linearLayoutName = itemView.findViewById(R.id.linearLayout7);
            subCategoriesRVCNL = itemView.findViewById(R.id.subCategoriesRVCNL);
        }
    }


}
