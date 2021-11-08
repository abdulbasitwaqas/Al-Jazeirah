package com.app.aljazierah.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.SubCategoriesModel;
import com.app.aljazierah.utils.ProductsSingleton;

import java.util.List;

public class CategoriesListsAdapter extends RecyclerView.Adapter<CategoriesListsAdapter.MyViewHolder> implements SubCatListAdapter.Clicks{
    private List<Categories> categoriesList;
    private Context context;
    private boolean isArabic = AppController.setLocale();
   SubCatListAdapter.Clicks clicks;

    public CategoriesListsAdapter(List<Categories> categoriesList, Context context, SubCatListAdapter.Clicks clicks) {
        this.categoriesList = categoriesList;
        this.context = context;
       this.clicks = clicks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (isArabic)
            holder.catNameTV.setText(categoriesList.get(position).getName_ar());
        else
            holder.catNameTV.setText(categoriesList.get(position).getName_en());

        if (categoriesList.get(position).getSubCategoriesModelList().size()>0) {
            holder.categoriesRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            SubCatListAdapter subCategoriesNameAdapter = new SubCatListAdapter(categoriesList.get(position).getSubCategoriesModelList(), context, this);
            holder.categoriesRV.setAdapter(subCategoriesNameAdapter);
        }
        else {
            holder.catNameTV.setVisibility(View.GONE);
            holder.categoriesRV.setVisibility(View.GONE);
            holder.lineSCLL.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    @Override
    public void selectSubCategory(SubCategoriesModel subCategoriesModel, int position) {
        clicks.selectSubCategory(subCategoriesModel,position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView catNameTV;
        private LinearLayout subCategoriesNameLL;
        private RecyclerView categoriesRV;
        private View lineSCLL;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catNameTV = itemView.findViewById(R.id.catNameTV);
            subCategoriesNameLL = itemView.findViewById(R.id.subCategoriesNameLL);
            categoriesRV = itemView.findViewById(R.id.subCategoriesRV);
            lineSCLL = itemView.findViewById(R.id.lineSCLL);
        }
    }


}
