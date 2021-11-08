package com.app.aljazierah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.SubCategoriesModel;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyViewHolder> {
    private List<SubCategoriesModel> subCategoriesModelList;
    private Context context;
    private FilterAdapterlistener filterAdapterlistener;
    private int checkedPosition = -1;
    private String categoryId = "", subCategoryId;


    public SubCategoriesAdapter(List<SubCategoriesModel> subCategoriesModelList, Context context, FilterAdapterlistener filterAdapterlistener, String categoryId, String subCategoryId) {
        this.subCategoriesModelList = subCategoriesModelList;
        this.context = context;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.filterAdapterlistener = filterAdapterlistener;
    }


    @NonNull
    @Override
    public SubCategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriesAdapter.MyViewHolder holder, int position) {


        AppController.updateResources(context, UserSession.getInstance().getUserLanguage());

        if (UserSession.getInstance().getUserLanguage().equals("ar")) {

            holder.titleTV.setText(subCategoriesModelList.get(position).getName_ar());
        } else if (UserSession.getInstance().getUserLanguage().equals("en")) {
            holder.titleTV.setText(subCategoriesModelList.get(position).getName_en());
        }


        if (!subCategoryId.equals(""))
            if (subCategoriesModelList.get(position).getId().equals(subCategoryId)) {
                if (checkedPosition != position) {
                    checkedPosition = position;
                }
            }


        if (checkedPosition == -1) {
            holder.titleTV.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            holder.removeFilterIV.setVisibility(View.GONE);
        } else {
            if (checkedPosition == position) {
                holder.titleTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                filterAdapterlistener.filter(Integer.parseInt(subCategoriesModelList.get(position).getId()), "");
                holder.removeFilterIV.setVisibility(View.VISIBLE);
            } else {
                holder.titleTV.setTextColor(context.getResources().getColor(R.color.text_color));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                holder.removeFilterIV.setVisibility(View.GONE);
            }
        }


        holder.titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.titleTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.removeFilterIV.setVisibility(View.VISIBLE);
                filterAdapterlistener.filter(Integer.parseInt(subCategoriesModelList.get(position).getId()), "");
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                    subCategoryId ="";
                }


//                    holder.titleTV.setBackground(context.getResources().getDrawable(R.drawable.round_btn));
//                    holder.titleTV.setTextColor(context.getResources().getColor(R.color.white));
//                    filterAdapterlistener.filter( Integer.parseInt(subCategoriesModelList.get(position).getId()));
            }
        });


        holder.removeFilterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.titleTV.setTextColor(context.getResources().getColor(R.color.text_color));
                holder.categoryLine.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                holder.removeFilterIV.setVisibility(View.GONE);
                filterAdapterlistener.filter(000000000, "all");
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                    subCategoryId ="";
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return subCategoriesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV;
        private ImageView removeFilterIV;
        private View categoryLine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.categoryNameTV);
            removeFilterIV = itemView.findViewById(R.id.removeFilterIV);
            categoryLine = itemView.findViewById(R.id.categoryLine);
        }
    }


    public interface FilterAdapterlistener {
        void filter(int id, String check);

    }
}
