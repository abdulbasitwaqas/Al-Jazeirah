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
import com.app.aljazierah.object.TicketReply;
import com.app.aljazierah.ui.TicketDetailsActivity;

import java.util.List;


/**
 * Created by Mustanser Iqbal on 6/8/2016.
 */
public class TicketReplyAdapter extends BaseAdapter {

    private Context mContext;
    private List<TicketReply> data;
    public Resources res;
    private LayoutInflater inflater;
    private boolean isArabic;
    TicketDetailsActivity ticketDetailsActivity;


    public TicketReplyAdapter(TicketDetailsActivity ticketDetailsActivity,Context context, List<TicketReply> objects) {

        this.mContext = context;
        this.data = objects;
        this.ticketDetailsActivity=ticketDetailsActivity;

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
            convertView = mInflater.inflate(R.layout.ticketsreply_child, null);
            viewHolder.label_by = convertView.findViewById(R.id.label_by);
            viewHolder.tv_by = convertView.findViewById(R.id.tv_by);
            viewHolder.label_reply = convertView.findViewById(R.id.label_reply);
            viewHolder.tv_reply = convertView.findViewById(R.id.tv_reply);
            viewHolder.label_date = convertView.findViewById(R.id.label_date);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_file = convertView.findViewById(R.id.tv_file);
            viewHolder.label_file = convertView.findViewById(R.id.label_file);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


            viewHolder.tv_by.setText(data.get(position).getClient_name());
            viewHolder.tv_date.setText(data.get(position).getDate());
            viewHolder.tv_reply.setText(data.get(position).getReply_message());
            viewHolder.tv_file.setText(data.get(position).getReply_file_name());

            viewHolder.tv_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketDetailsActivity.ImagePreview(data.get(position).getReply_file_name());

                }
            });
        return convertView;
    }



    class ViewHolder {
        TextView label_by,tv_by,label_reply,tv_reply,label_date,tv_date,tv_file,label_file;


    }

//    define(‘TICKET_STATUS_DELETED’, 0);
//    define(‘TICKET_STATUS_PENDING’, 1);
//    define(‘TICKET_STATUS_INPROCESS’, 2);
//    define(‘TICKET_STATUS_CLOSED’, 3);
//    define(‘TICKET_STATUS_WAITING_FOR_CLIENT’, 4);

}
