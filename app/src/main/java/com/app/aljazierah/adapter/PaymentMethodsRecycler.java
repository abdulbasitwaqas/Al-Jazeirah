package com.app.aljazierah.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.app.aljazierah.R;
import com.app.aljazierah.object.CartParamResponse.PaymentMethod;

import java.util.List;

public class PaymentMethodsRecycler extends RecyclerView.Adapter<PaymentMethodsRecycler.PaymentMethodHolder> {
    public interface IPaymentMethodRecycler{
        void OnPaymentMethodSelected(String PaymentType);
    }
    List<PaymentMethod> mData;
    private int lastCheckedPosition;
    IPaymentMethodRecycler iPaymentMethodRecycler;


    public PaymentMethodsRecycler(List<PaymentMethod> mData) {
        this.mData = mData;
    }

    public PaymentMethodsRecycler(List<PaymentMethod> mData, IPaymentMethodRecycler iPaymentMethodRecycler) {
        this.mData = mData;
        this.iPaymentMethodRecycler = iPaymentMethodRecycler;
    }

    @NonNull
    @Override
    public PaymentMethodHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PaymentMethodHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.si_payment_methods,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodHolder paymentMethodHolder, int i) {
        if (mData.get(i).getStatus().equals("1"))
            paymentMethodHolder.rbPaymentMethod.setChecked(i == lastCheckedPosition);
            paymentMethodHolder.rbPaymentMethod.setText(mData.get(i).getTitleEn());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PaymentMethodHolder extends RecyclerView.ViewHolder {
        RadioButton rbPaymentMethod;
        public PaymentMethodHolder(@NonNull View itemView) {
            super(itemView);
            rbPaymentMethod=itemView.findViewById(R.id.si_paymentMethods_rbPaymentMethods);

            rbPaymentMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                    //because of this blinking problem occurs so
                    //i have a suggestion to add notifyDataSetChanged();
                    //   notifyItemRangeChanged(0, list.length);//blink list problem
                    iPaymentMethodRecycler.OnPaymentMethodSelected(rbPaymentMethod.getText().toString());
                    notifyDataSetChanged();

                }
            });
        }
    }
}
