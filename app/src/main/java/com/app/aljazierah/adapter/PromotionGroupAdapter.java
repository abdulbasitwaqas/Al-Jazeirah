package com.app.aljazierah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Promotions.Foc_prod;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PromotionGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Foc_prod> focProdArrayList;
    private boolean isArabic;
    int add_on;

    private boolean firsttime = true ;

    public PromotionGroupAdapter(ArrayList<Foc_prod> foc_prods, int add_on) {
        this.focProdArrayList = foc_prods;
        this.add_on = add_on;
        isArabic = AppController.setLocale();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_promotion_group_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder hldr = (ListViewHolder) holder;
            if (isArabic) {
                hldr.tv_title.setText(focProdArrayList.get(position).getName_ar());
            } else {
                hldr.tv_title.setText(focProdArrayList.get(position).getName_en());
            }
           hldr.tv_count.setText(""+focProdArrayList.get(position).getCount());

            hldr.addproduct_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int coutTotal=0;
                    for(int i = 0; i<focProdArrayList.size();i++){
                        coutTotal = coutTotal+focProdArrayList.get(i).getCount();
                    }
                    if (coutTotal<add_on) {
                        int count = Integer.parseInt(hldr.tv_count.getText().toString()) + 1;
                        updateCount(count, position);
                    }

                }
            });


            hldr.minusproduct_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(hldr.tv_count.getText().toString())>0) {
                        int count = Integer.parseInt(hldr.tv_count.getText().toString()) - 1;
                        updateCount(count, position);
                    }
                }
            });

        }
    }

    public ArrayList<Foc_prod> returnFocArray(){
        return this.focProdArrayList;
    }

    public void updateCount(int count, int position){
        focProdArrayList.get(position).setCount(count);
        updateArray(focProdArrayList);
    }

    public void updateArray(ArrayList<Foc_prod> focProdArrayList ){
        this.focProdArrayList=new ArrayList<>();
        for (int i =0; i<focProdArrayList.size();i++){
                Foc_prod foc_prod =new Foc_prod();
                foc_prod.setCount(focProdArrayList.get(i).getCount());
                foc_prod.setId(focProdArrayList.get(i).getId());
                foc_prod.setName_ar(focProdArrayList.get(i).getName_ar());
                foc_prod.setName_en(focProdArrayList.get(i).getName_en());
                this.focProdArrayList.add(foc_prod);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return focProdArrayList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_count,addproduct_button,minusproduct_button;
        public ListViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_count = itemView.findViewById(R.id.tv_count);
            addproduct_button = itemView.findViewById(R.id.addproduct_button);
            minusproduct_button = itemView.findViewById(R.id.minusproduct_button);

        }
    }




}
