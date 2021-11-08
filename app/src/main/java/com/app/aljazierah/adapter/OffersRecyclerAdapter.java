package com.app.aljazierah.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;

import com.app.aljazierah.object.Promotions.Promotion;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.app.aljazierah.utils.Constants.PROMO_BASE_URL;


public class OffersRecyclerAdapter extends RecyclerView.Adapter<OffersRecyclerAdapter.OfferHolder> {
    Context context;
    private List<Promotion> mData;
    Boolean isArabic;
    Activity activity;
    public OffersRecyclerAdapter(Activity activity,Context context, List<Promotion> mData) {
        this.context = context;
        this.mData = mData;
        this.isArabic = AppController.setLocale();
            this.activity=activity;
    }

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OfferHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.si_offers,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OfferHolder offerHolder, int i) {
        offerHolder.imageView.setVisibility(View.INVISIBLE);
        offerHolder.dateview.setVisibility(View.GONE);
        offerHolder.progress_bar.setVisibility(View.VISIBLE);
        if (isArabic) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            activity. getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        if(mData.get(i).getImageMobileAr().equals("") &&
                mData.get(i).getImageMobileEn().equals("")){
            offerHolder.view_offers.setVisibility(View.GONE);
        }

        Log.d("urlddd",""+PROMO_BASE_URL + mData.get(i).getImageMobileEn());
        if (isArabic) {


            Picasso.with(context).load(PROMO_BASE_URL + mData.get(i).getImageMobileAr())

                    .into(offerHolder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            offerHolder.imageView.setVisibility(View.VISIBLE);
                            offerHolder.progress_bar.setVisibility(View.GONE);
                            offerHolder.dateview.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }else {
            Picasso.with(context).load(PROMO_BASE_URL + mData.get(i).getImageMobileEn())

                    .into(offerHolder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            offerHolder.imageView.setVisibility(View.VISIBLE);
                            offerHolder.progress_bar.setVisibility(View.GONE);
                            offerHolder.dateview.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        offerHolder.tvExDate.setText(context.getResources().getString(R.string.st_validtill)+" "+mData.get(i).getEnd_date());
        offerHolder.tvExDate.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OfferHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvExDate;
        View view_offers,dateview;
        ProgressBar progress_bar;




        public OfferHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.si_offers_image);
            tvExDate=itemView.findViewById(R.id.si_offers_tvExDate);
            view_offers=itemView.findViewById(R.id.view_offers);
            progress_bar=itemView.findViewById(R.id.progress_bar);
            dateview=itemView.findViewById(R.id.dateview);

        }
    }
}
