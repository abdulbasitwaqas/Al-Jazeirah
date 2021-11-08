package com.app.aljazierah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.R;
import com.app.aljazierah.object.MaintenencesData;
import com.app.aljazierah.ui.MaintenancesDetailActivity;
import com.google.gson.Gson;

import java.util.List;

public class AfterSaleseDataAdapter extends RecyclerView.Adapter<AfterSaleseDataAdapter.MyViewHolder> {

    private List<MaintenencesData> maintenencesDataList;
    private Context context;

    public AfterSaleseDataAdapter(List<MaintenencesData> storeList, Context context) {
        this.maintenencesDataList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public AfterSaleseDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_after_sales_list, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AfterSaleseDataAdapter.MyViewHolder holder, int position) {
        holder.tvComplaintId.setText(""+context.getResources().getString(R.string._string_ticket_id)+": "+
                maintenencesDataList.get(position).getTicketId());
        holder.tvWarrenty.setText(context.getResources().getString(R.string.inside_warranty)+": "+
                maintenencesDataList.get(position).getWarranty());
        holder.tvComplaintNo.setText(context.getResources().getString(R.string._string_complaint_date)+": "
                +maintenencesDataList.get(position).getCreatedAt());
        holder.tvCity.setText(context.getResources().getString(R.string.city)+": "+maintenencesDataList.get(position).getCity());

        holder.viewAftersales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MaintenancesDetailActivity.class);
                intent.putExtra("maintenanceDetails", "" +  new Gson().toJson(maintenencesDataList.get(position)));
                intent.putExtra("service_slot", "" +  new Gson().toJson(maintenencesDataList.get(position).getServiceSlot()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return maintenencesDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvComplaintNo, tvWarrenty, tvComplaintId,tvCity;
        View viewAftersales;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComplaintId = itemView.findViewById(R.id.tvComplaintId);
            tvComplaintNo = itemView.findViewById(R.id.tvComplaintNo);
            tvWarrenty = itemView.findViewById(R.id.tvWarrenty);
            tvCity = itemView.findViewById(R.id.tvCity);
            viewAftersales = itemView.findViewById(R.id.viewAftersales);
        }
    }
}
