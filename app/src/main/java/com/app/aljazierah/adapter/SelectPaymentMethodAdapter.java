package com.app.aljazierah.adapter;

/**
 * Created by Lenovo on 1/4/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.CartParamResponse.PaymentMethod;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class SelectPaymentMethodAdapter extends BaseAdapter {
    AppCompatActivity activity;
    private static LayoutInflater inflater=null;
    List<PaymentMethod> paymentMethodList;


    public SelectPaymentMethodAdapter(AppCompatActivity activity, List<PaymentMethod> paymentMethodList) {
// TODO Auto-generated constructor stub
        this.paymentMethodList = paymentMethodList;
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return paymentMethodList.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        RadioButton radio_item_title;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.model_choose_payment_method, null);
        holder.radio_item_title =  view.findViewById(R.id.radio_item_title);

        if (paymentMethodList.get(position).getTitleEn().toLowerCase().equals("wallet")||
                paymentMethodList.get(position).getTitleAr().equals("المحفظة النقدية"))
        {
            paymentMethodList.remove(position);
            notifyDataSetChanged();
        }else {
            if (AppController.setLocale())
                holder.radio_item_title.setText(paymentMethodList.get(position).getTitleAr());
            else
                holder.radio_item_title.setText(paymentMethodList.get(position).getTitleEn());

            holder.radio_item_title.setChecked(paymentMethodList.get(position).isSelected());
        }
        return view;
    }

}