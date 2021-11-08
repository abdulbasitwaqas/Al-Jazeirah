package com.app.aljazierah.adapter;


import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.TimeSlot;

import java.util.List;


/**
 * Created by Mustanser Iqbal on 6/8/2016.
 */
public class TimeSlotSpinnerAdapter extends BaseAdapter {


    private List<TimeSlot> data;
    public Resources res;
    private boolean isArabic;

    public TimeSlotSpinnerAdapter(List<TimeSlot> objects) {

        this.data = objects;
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
                .inflate(R.layout.timeslot_spinner_item, parent, false);

        TextView label = row.findViewById(R.id.title);
        if (isArabic) {
            label.setText(data.get(position).getTitleAr());
        } else {
            label.setText(data.get(position).getTitleEn());
        }

        return row;
    }


}
