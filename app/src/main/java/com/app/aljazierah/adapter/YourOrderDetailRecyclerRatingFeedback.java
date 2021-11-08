package com.app.aljazierah.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.RetingRequest.FeedbackProducts;

import java.util.ArrayList;
import java.util.List;

public class YourOrderDetailRecyclerRatingFeedback extends RecyclerView.Adapter<YourOrderDetailRecyclerRatingFeedback.YourOrderDetailHolder> {
    Context context;
    List<FeedbackProducts> mData=new ArrayList<>();
    Boolean isArabic;

    public YourOrderDetailRecyclerRatingFeedback(Context context, List<FeedbackProducts> mData) {
        this.context = context;
        this.mData = mData;
        this.isArabic = AppController.setLocale();
    }

    @NonNull
    @Override
    public YourOrderDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new YourOrderDetailHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_cart_list_rating,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull YourOrderDetailHolder yourOrderDetailHolder, int positon) {
       // OrderDetail detail=mData.get(positon);
        String ProductName;
        if (isArabic) {
            ProductName = mData.get(positon).getNameAr();
        }
        else{
            ProductName=mData.get(positon).getNameEn();
        }
        yourOrderDetailHolder.tvTitle.setText(ProductName);

        if (!mData.get(positon).getComments().equals("")){
            yourOrderDetailHolder.editUserRemarks.setText(""+mData.get(positon).getComments());
            yourOrderDetailHolder.editUserRemarks.setEnabled(false);
        }

        if (!mData.get(positon).getRating().equals("")){
            yourOrderDetailHolder.itemRatting.setRating(Float.parseFloat(mData.get(positon).getRating()));
            yourOrderDetailHolder.itemRatting.setIsIndicator(true);
        }


        yourOrderDetailHolder.itemRatting.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                mData.get(positon).setRating(rating+"");

            }
        });



        yourOrderDetailHolder.editUserRemarks.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    mData.get(positon).setComments(s.toString());
            }
        });

    }


    public List<FeedbackProducts> getOrderItemsList(){
        return mData;
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class YourOrderDetailHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        RatingBar itemRatting;
        EditText editUserRemarks;
        public YourOrderDetailHolder(@NonNull View itemView) {
            super(itemView);
            itemRatting=itemView.findViewById(R.id.itemRatting);
            tvTitle=itemView.findViewById(R.id.layout_cart_tvTitle);
            editUserRemarks=itemView.findViewById(R.id.editUserRemarks);
        }
    }
}
