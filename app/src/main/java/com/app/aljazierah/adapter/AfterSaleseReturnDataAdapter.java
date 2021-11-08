package com.app.aljazierah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.R;
import com.app.aljazierah.object.MaintenencesData;
import com.app.aljazierah.ui.ReturnsDetailActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;
import static com.app.aljazierah.utils.Constants.SERVER_URL;

public class AfterSaleseReturnDataAdapter extends RecyclerView.Adapter<AfterSaleseReturnDataAdapter.MyViewHolder> {

    private List<MaintenencesData> maintenencesDataList;
    private Context context;

    public AfterSaleseReturnDataAdapter(List<MaintenencesData> storeList, Context context) {
        this.maintenencesDataList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public AfterSaleseReturnDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_after_sales_return_list, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AfterSaleseReturnDataAdapter.MyViewHolder holder, int position) {
        holder.tvComplaintId.setText(""+context.getResources().getString(R.string._string_ticket_id)+": "+
                maintenencesDataList.get(position).getTicketId());
        holder.tvWarrenty.setText(context.getResources().getString(R.string.warranty)+": "+
                maintenencesDataList.get(position).getWarranty());
        holder.tvComplaintNo.setText(context.getResources().getString(R.string._string_complaint_date)+": "
                +maintenencesDataList.get(position).getCreatedAt());
        holder.tvCity.setText(context.getResources().getString(R.string.city)+": "+maintenencesDataList.get(position).getCity());

        holder.viewAftersales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReturnsDetailActivity.class);
                intent.putExtra("maintenanceDetails", "" +  new Gson().toJson(maintenencesDataList.get(position)));
                context.startActivity(intent);
            }
        });

//        if (!maintenencesDataList.get(position).getInvoice_pic().equals("")) {
            holder.invoiceIV.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(SERVER_URL + maintenencesDataList.get(position).getInvoice_pic())
                    .placeholder(R.drawable.ic_place_holder)
                    .into(holder.invoiceIV);
     /*   }
        else {
            holder.invoiceIV.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        return maintenencesDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvComplaintNo, tvWarrenty, tvComplaintId,tvCity;
        View viewAftersales;
        private ImageView invoiceIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComplaintId = itemView.findViewById(R.id.tvComplaintId);
            tvComplaintNo = itemView.findViewById(R.id.tvComplaintNo);
            tvWarrenty = itemView.findViewById(R.id.tvWarrenty);
            tvCity = itemView.findViewById(R.id.tvCity);
            invoiceIV = itemView.findViewById(R.id.invoiceIV);
            viewAftersales = itemView.findViewById(R.id.viewAftersales);
        }
    }
}
