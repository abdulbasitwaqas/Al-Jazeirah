package com.app.aljazierah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.PagesModel;

import java.util.List;

public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.MyViewHolder> {
    private List<PagesModel> pagesModelList;
    private Context context;
    private boolean isArabic = AppController.setLocale();
    private PageClick pageClick;


    public PagesAdapter(List<PagesModel> pagesModelList, Context context, PageClick pageClick) {
        this.pagesModelList = pagesModelList;
        this.context = context;
        this.pageClick = pageClick;
    }

    @NonNull
    @Override
    public PagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pages_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PagesAdapter.MyViewHolder holder, int position) {

        if (!isArabic)
            holder.pagesTV.setText(pagesModelList.get(position).getEn());
        else
            holder.pagesTV.setText(pagesModelList.get(position).getAr());


        holder.pagesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArabic)
                    pageClick.pageClick(pagesModelList.get(position).getPage_id(), position, pagesModelList.get(position).getAr());
                else
                    pageClick.pageClick(pagesModelList.get(position).getPage_id(), position, pagesModelList.get(position).getEn());
               /* String url = Constants.pages_url +""+pagesModelList.get(position).getPage_id();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return pagesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pagesTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pagesTV = itemView.findViewById(R.id.pagesTV);
        }
    }


    public interface PageClick {
        void pageClick(String page_id, int position, String pageName);


    }
}
