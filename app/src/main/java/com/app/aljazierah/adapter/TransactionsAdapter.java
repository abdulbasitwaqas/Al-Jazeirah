package com.app.aljazierah.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.TransactionObject;

import java.util.List;


public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    private List<TransactionObject> transactionList;
    private Context mContext;
    private boolean isArabic;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_amount, tv_type, tv_orderid, tv_date;

        public MyViewHolder(View view) {
            super(view);
            tv_amount = view.findViewById(R.id.tv_amount);
            tv_type =  view.findViewById(R.id.tv_type);
            tv_orderid = view.findViewById(R.id.tv_orderid);
            tv_date = view.findViewById(R.id.tv_date);
        }
    }


    public TransactionsAdapter(List<TransactionObject> ordersList, Context context) {
        this.transactionList = ordersList;
        this.mContext = context;
        this.isArabic = AppController.setLocale();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_child_transaction, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TransactionObject transactionObject = transactionList.get(position);

        holder.tv_orderid.setText(mContext.getResources().getString(R.string.st_ordernumber)+""+transactionObject.getOrderId());
        holder.tv_date.setText(transactionObject.getReference());

            if(transactionObject.getTransactionType().toLowerCase().equals("debit")){
                if(isArabic){
                    holder.tv_type.setText("آخر استخدام");
                }
                else{
                    holder.tv_type.setText("Debit");
                }
            }
            else if(transactionObject.getTransactionType().toLowerCase().equals("credit")){
                if(isArabic){
                    holder.tv_type.setText("آخر إضافة");
                }
                else{
                    holder.tv_type.setText("Credit");
                }

            }

            if (isArabic){
                holder.tv_amount.setText(""+transactionObject.getAmount()+"ر.س ");
            }
            else {
                holder.tv_amount.setText(""+transactionObject.getAmount()+" SAR");
            }




    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setTransactionList(List<TransactionObject> transactionList) {
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }
}
