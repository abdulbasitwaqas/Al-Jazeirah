package com.app.aljazierah.adapter;

/**
 * Created by Lenovo on 1/4/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;

import java.util.List;

/**
 * Created by
 * on 8/16/2016.
 */
public class SelectionListAdapter extends BaseAdapter {
    AppCompatActivity activity;
    private static LayoutInflater inflater=null;
    List<String> titles;

    public SelectionListAdapter(AppCompatActivity activity, List<String> titles) {
// TODO Auto-generated constructor stub
        this.titles = titles;
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return titles.size();
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
        TextView tv_title;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.model_choose_city_area, null);
        holder.tv_title =  view.findViewById(R.id.tv_title);
        if (AppController.setLocale()){
        holder.tv_title.setText(titles.get(position));}else {
            holder.tv_title.setText(titles.get(position));
        }
        return view;
    }
}