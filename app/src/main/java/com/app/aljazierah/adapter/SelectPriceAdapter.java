package com.app.aljazierah.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.OrderInformation;

import java.util.List;


/**
 * Created by Mustanser Iqbal on 6/8/2016.
 */
public class SelectPriceAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderInformation> data;
    public Resources res;
    private LayoutInflater inflater;
    private boolean isArabic;

    /*************
     * CustomAdapter Constructor
     *****************/
    public SelectPriceAdapter(Context context, List<OrderInformation> objects) {

        this.mContext = context;
        this.data = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isArabic = AppController.setLocale();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_item, parent, false);

        TextView label = (TextView) row.findViewById(R.id.title);
        if (isArabic){
            if(data.get(position).getId().equals("no")){
                label.setText(mContext.getResources().getString(R.string.st_selectorder));
            }
            else{
                label.setText(data.get(position).getOrder_code()+","+data.get(position).getOrder_created_date());
            }

        }else {
            if(data.get(position).getId().equals("no")){
                label.setText(mContext.getResources().getString(R.string.st_selectorder));
            }
            else{
                label.setText(data.get(position).getOrder_code()+","+data.get(position).getOrder_created_date());
            }

        }

        return row;
    }

    public void setData(List<OrderInformation> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
