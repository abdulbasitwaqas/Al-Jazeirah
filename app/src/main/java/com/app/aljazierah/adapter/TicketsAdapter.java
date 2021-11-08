package com.app.aljazierah.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Tickets;
import com.app.aljazierah.ui.TicketDetailsActivity;

import java.util.List;


/**
 * Created by Mustanser Iqbal on 6/8/2016.
 */
public class TicketsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tickets> data;
    public Resources res;
    private LayoutInflater inflater;
    private boolean isArabic;

    /*************
     * CustomAdapter Constructor
     *****************/
    public TicketsAdapter(Context context, List<Tickets> objects) {

        this.mContext = context;
        this.data = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isArabic = AppController.setLocale();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.tickets_child, null);
            viewHolder.tv_complaint = convertView.findViewById(R.id.tv_complaint);
            viewHolder.tv_status = convertView.findViewById(R.id.tv_status);
            viewHolder.tv_replies = convertView.findViewById(R.id.tv_replies);
            viewHolder.tv_category = convertView.findViewById(R.id.tv_category);
            viewHolder.tv_description = convertView.findViewById(R.id.tv_description);
            viewHolder.iv_status = convertView.findViewById(R.id.iv_status);
            viewHolder.label_complaint = convertView.findViewById(R.id.label_complaint);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setFonts(viewHolder);
        viewHolder.tv_description.setText(data.get(position).getTicketBody());

        if (data.get(position).getTicketStatus().equals("1")) {
            viewHolder.tv_status.setText(mContext.getResources().getString(R.string.st_pending));
            if (isArabic) {
                viewHolder.iv_status.setBackgroundResource(R.drawable.pending_filled_ar);
            } else {
                viewHolder.iv_status.setBackgroundResource(R.drawable.pending_filled);
            }

        } else if (data.get(position).getTicketStatus().equals("2")) {
            viewHolder.tv_status.setText(mContext.getResources().getString(R.string.st_open));
            if (isArabic) {
                viewHolder.iv_status.setBackgroundResource(R.drawable.open_filled_ar);
            } else {
                viewHolder.iv_status.setBackgroundResource(R.drawable.open_filled);
            }


        } else if (data.get(position).getTicketStatus().equals("3")) {
            viewHolder.tv_status.setText(mContext.getResources().getString(R.string.st_complete));
            if (isArabic) {
                viewHolder.iv_status.setBackgroundResource(R.drawable.complete_filled_ar);
            } else {
                viewHolder.iv_status.setBackgroundResource(R.drawable.complete_filled);
            }

        } else if (data.get(position).getTicketStatus().equals("4")) {
            viewHolder.tv_status.setText(mContext.getResources().getString(R.string.st_client));
            if (isArabic) {
                viewHolder.iv_status.setBackgroundResource(R.drawable.client_filled_ar);
            } else {
                viewHolder.iv_status.setBackgroundResource(R.drawable.client_filled);
            }

        } else if (data.get(position).getTicketStatus().equals("0")) {
            viewHolder.tv_status.setText(mContext.getResources().getString(R.string.st_delete));
            if (isArabic) {
                viewHolder.iv_status.setBackgroundResource(R.drawable.delete_filled_ar);
            } else {
                viewHolder.iv_status.setBackgroundResource(R.drawable.delete_filled);
            }

        }
        if (isArabic) {
            viewHolder.tv_category.setText(data.get(position).getCategoryTitleArabic());
            viewHolder.tv_complaint.setText("  " + data.get(position).getTicketId());

            viewHolder.tv_replies.setText("  " + data.get(position).getReplies());

        } else {
            viewHolder.tv_category.setText(data.get(position).getCategoryTitleEnglish());
            viewHolder.tv_complaint.setText(" " + data.get(position).getTicketId());

            viewHolder.tv_replies.setText(" " + data.get(position).getReplies());

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TicketDetailsActivity.class);
                intent.putExtra("id",data.get(position).getTicketId());
                intent.putExtra("status",data.get(position).getTicketStatus());
                mContext.startActivity(intent);
            }
        });



        return convertView;
    }


    private void setFonts(ViewHolder viewHolder){
        if(!isArabic){
//            viewHolder.tv_replies.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//            viewHolder.tv_status.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "poppins_bold.ttf"), Typeface.NORMAL);
//            viewHolder.tv_replies.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//            viewHolder.tv_description.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//            viewHolder.tv_category.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//            viewHolder.tv_complaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "poppins_bold.ttf"), Typeface.NORMAL);
//            viewHolder.label_complaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "poppins_bold.ttf"), Typeface.NORMAL);

        }
    }


    class ViewHolder {
        TextView tv_complaint,tv_status,tv_category,tv_replies,tv_description,label_complaint;
        ImageView iv_status;

    }

//    define(‘TICKET_STATUS_DELETED’, 0);
//    define(‘TICKET_STATUS_PENDING’, 1);
//    define(‘TICKET_STATUS_INPROCESS’, 2);
//    define(‘TICKET_STATUS_CLOSED’, 3);
//    define(‘TICKET_STATUS_WAITING_FOR_CLIENT’, 4);

}
