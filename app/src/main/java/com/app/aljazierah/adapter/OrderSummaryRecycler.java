package com.app.aljazierah.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.YourOrderDetails.OrderDetail;


import java.util.List;

public class OrderSummaryRecycler extends RecyclerView.Adapter<OrderSummaryRecycler.OrderSummaryHolder> {
    List<OrderDetail> mData;
    Boolean isArabic;

    public OrderSummaryRecycler(List<OrderDetail> mData) {
        this.mData = mData;
        this.isArabic= AppController.setLocale();

    }

    @NonNull
    @Override
    public OrderSummaryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderSummaryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.si_recycler_order_summary,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryHolder orderSummaryHolder, int i) {
        if (isArabic)
        {
            orderSummaryHolder.tvText.setText(mData.get(orderSummaryHolder.getAdapterPosition()).getOrdCount() + " x ");
        orderSummaryHolder.si_recycler_order_summary_tvText1.setText(mData.get(orderSummaryHolder.getAdapterPosition()).getDishTitleAr());
        orderSummaryHolder.tvText.setGravity(Gravity.RIGHT);
        orderSummaryHolder.tvText.setTextDirection(View.TEXT_DIRECTION_RTL);
    }
        else
            orderSummaryHolder.tvText.setText(mData.get(orderSummaryHolder.getAdapterPosition()).getOrdCount()
                    +" x "+mData.get(orderSummaryHolder.getAdapterPosition()).getDishTitleEn());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OrderSummaryHolder extends RecyclerView.ViewHolder {
        TextView tvText,si_recycler_order_summary_tvText1;
        public OrderSummaryHolder(@NonNull View itemView) {
            super(itemView);
            tvText=itemView.findViewById(R.id.si_recycler_order_summary_tvText);
            si_recycler_order_summary_tvText1=itemView.findViewById(R.id.si_recycler_order_summary_tvText1);

        }
    }
}
