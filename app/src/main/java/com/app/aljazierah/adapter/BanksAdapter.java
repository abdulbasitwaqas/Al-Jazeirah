package com.app.aljazierah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.CartParamResponse.BanksModel;

import java.util.List;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.MyViewHolder> {
    private List<BanksModel> banksModelList;
    private Context mContext;
    private boolean isArabic;
    public Clicks clicks;
//    private int selectedPos = 0;


    public BanksAdapter(Context context, List<BanksModel> banksModelList, Clicks clicks) {
        this.banksModelList = banksModelList;
        this.mContext = context;
        this.clicks = clicks;
        this.isArabic = AppController.setLocale();
//        this.banksModelList.get(0).setSelected(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banks_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bankNameTV.setText(banksModelList.get(position).getName());
        holder.accountTitleTV.setText("" + mContext.getResources().getString(R.string.account_title) + " " + banksModelList.get(position).getAccount_title());
        holder.accountNumTV.setText("" + mContext.getResources().getString(R.string.account_number) + " " + banksModelList.get(position).getAccount_no());
        holder.ibanTV.setText("" + mContext.getResources().getString(R.string.iban) + " " + banksModelList.get(position).getIban());
//        holder.itemView.setSelected(selectedPos == position);

        if (banksModelList.get(position).isSelected()){
            holder.bankCL.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }
        else {
            holder.bankCL.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }


        holder.bankCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks.selectBank(banksModelList.get(position) , position);

            }
        });

       /* holder.bankCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    holder.itemView.setSelected(selectedPos == position);
                    Toast.makeText(mContext, "" + banksModelList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    UserSession.getInstance().setBankID("" + banksModelList.get(position).getBank_id());

                } else {
                    holder.itemView.setSelected(selectedPos == position);
                    Toast.makeText(mContext, "" + banksModelList.get(position).getName(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(mContext, "" + banksModelList.get(position).getName(), Toast.LENGTH_SHORT).show();
//                    UserSession.getInstance().setBankID("");
                }
            }
        });*/
    }






    @Override
    public int getItemCount() {
        return banksModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView bankNameTV, accountTitleTV, accountNumTV, ibanTV;
        private ConstraintLayout bankCL;
        private CheckBox bankCB;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bankCL=itemView.findViewById(R.id.bankCL);
            bankCB=itemView.findViewById(R.id.bankCB);

            bankNameTV=itemView.findViewById(R.id.bankNameTV);
            accountTitleTV=itemView.findViewById(R.id.accountTitleTV);
            accountNumTV=itemView.findViewById(R.id.accountNumTV);
            ibanTV=itemView.findViewById(R.id.ibanTV);
        }
    }
    public interface Clicks {
        void selectBank (BanksModel banksModel, int position);


    }
}
