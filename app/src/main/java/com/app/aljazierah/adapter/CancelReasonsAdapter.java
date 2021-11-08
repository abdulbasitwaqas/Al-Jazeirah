package com.app.aljazierah.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.CancelReasons;

import java.util.ArrayList;

public class CancelReasonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CancelReasons> cancelReasons;
    private boolean isArabic;
    private int lastCheckedPosition = -1;

    public CancelReasonsAdapter(ArrayList<CancelReasons> cancelReasons) {
        this.cancelReasons = cancelReasons;
        isArabic = AppController.setLocale();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_select_item_adapter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            ListViewHolder hldr = (ListViewHolder) holder;
            if (isArabic) {
                hldr.singleItem.setText(cancelReasons.get(position).getReasonAr());
            } else {
                hldr.singleItem.setText(cancelReasons.get(position).getReasonEn());
            }

            hldr.singleItem.setChecked(position == lastCheckedPosition);
        }
    }

    @Override
    public int getItemCount() {
        return cancelReasons.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RadioButton singleItem;
        private LinearLayout clickable;

        public ListViewHolder(View itemView) {
            super(itemView);
            singleItem = itemView.findViewById(R.id.single_item);
            clickable = itemView.findViewById(R.id.clickable);
            singleItem.setOnClickListener(this);
            clickable.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.single_item || view.getId() == R.id.clickable) {
                lastCheckedPosition = getAdapterPosition();
                notifyDataSetChanged();
//                notifyItemRangeChanged(0, cancelReasons.size());
            }
        }
    }

    public CancelReasons getCheckedCancelReason() {
        if (lastCheckedPosition >= 0) {
            return cancelReasons.get(lastCheckedPosition);
        }
        return null;
    }
}
