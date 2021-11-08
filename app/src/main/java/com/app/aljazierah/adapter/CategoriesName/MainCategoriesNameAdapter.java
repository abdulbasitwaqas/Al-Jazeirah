package com.app.aljazierah.adapter.CategoriesName;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Categories;

import java.util.List;

public class MainCategoriesNameAdapter extends RecyclerView.Adapter<MainCategoriesNameAdapter.MyViewHolder> {
    private List<Categories> categoriesList;
    private Context context;
    private boolean isArabic = AppController.setLocale();
    private FilterAdapterlistener filterAdapterlistener;
    private int checkedPosition = -1;

    public MainCategoriesNameAdapter(List<Categories> categoriesList, Context context, FilterAdapterlistener filterAdapterlistener) {
        this.categoriesList = categoriesList;
        this.context = context;
        this. filterAdapterlistener = filterAdapterlistener;
    }
    
    
    @NonNull
    @Override
    public MainCategoriesNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_name_filter_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoriesNameAdapter.MyViewHolder holder, int position) {
        if (isArabic) {
            holder.categoryNameTV.setText(categoriesList.get(position).getName_ar());
        } else {
            holder.categoryNameTV.setText(categoriesList.get(position).getName_en());
        }



        if (checkedPosition == -1) {
            holder.categoryNameTV.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            holder.removeFilterIV.setVisibility(View.GONE);
        }
        else {
            if (checkedPosition == position) {
                holder.categoryNameTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                filterAdapterlistener.filterr( categoriesList.get(position),"checked");
                holder.removeFilterIV.setVisibility(View.VISIBLE);
            }
            else {
                holder.categoryNameTV.setTextColor(context.getResources().getColor(R.color.text_color));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                holder.removeFilterIV.setVisibility(View.GONE);
            }
        }

        holder.categoryNameLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.categoryNameTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                filterAdapterlistener.filterr( categoriesList.get(position),"checked");
                holder.removeFilterIV.setVisibility(View.VISIBLE);
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                }

            }
        });


        holder.removeFilterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.categoryNameTV.setTextColor(context.getResources().getColor(R.color.text_color));
                holder.removeFilterIV.setVisibility(View.GONE);
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                filterAdapterlistener.filterr( categoriesList.get(position),"");
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout categoryNameLL;
        private TextView categoryNameTV;
        private View categoryLine;
        private ImageView removeFilterIV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameLL = itemView.findViewById(R.id.categoryNameLL);
            categoryNameTV = itemView.findViewById(R.id.categoryNameTV);
            categoryLine = itemView.findViewById(R.id.categoryLine);
            removeFilterIV = itemView.findViewById(R.id.removeFilterIV);
        }
    }

    public interface FilterAdapterlistener {
        void filterr(Categories categories,String check);

    }
}
