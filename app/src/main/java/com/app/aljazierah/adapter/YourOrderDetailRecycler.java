package com.app.aljazierah.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.YourOrderDetails.OrderDetail;
import com.app.aljazierah.object.login.User;
import com.bumptech.glide.Glide;
import com.roam.appdatabase.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class YourOrderDetailRecycler extends RecyclerView.Adapter<YourOrderDetailRecycler.YourOrderDetailHolder> {
    Context context;
    List<OrderDetail> mData=new ArrayList<>();
    Boolean isArabic;

    public YourOrderDetailRecycler(Context context, List<OrderDetail> mData) {
        this.context = context;
        this.mData = mData;
        this.isArabic = AppController.setLocale();
    }

    @NonNull
    @Override
    public YourOrderDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new YourOrderDetailHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_cart_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull YourOrderDetailHolder yourOrderDetailHolder, int i) {
        OrderDetail detail=mData.get(yourOrderDetailHolder.getAdapterPosition());
        String ProductName;
        if (isArabic) {
            ProductName = detail.getDishTitleAr();
//            yourOrderDetailHolder.tvTitle.
//                    setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/neo_sans_bold.ttf"), Typeface.BOLD);
//            yourOrderDetailHolder.tvDescription.setText("("+detail.getDishDetailAr());
        }
        else{
            ProductName=detail.getDishTitleEn();
//            yourOrderDetailHolder.tvTitle.
//                    setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/poppins_bold.ttf"), Typeface.BOLD);
//            yourOrderDetailHolder.tvDescription.setText("("+detail.getDishDetailEn());
        }

        yourOrderDetailHolder.tvTitle.setText(ProductName);
        String title[]=yourOrderDetailHolder.tvTitle.getText().toString().split("\\(");
        yourOrderDetailHolder.tvTitle.setText(title[0]);
//        yourOrderDetailHolder.tvDescription.setText("("+title[1]);
//        yourOrderDetailHolder.tvDescription.setText("("+detail.getDishDetailEn());
        yourOrderDetailHolder.tvPrice.setText(detail.getDishPrice() + " " + context.getResources().getString(R.string.currency));
        yourOrderDetailHolder.etQty.setText("" + detail.getOrdCount());

        if (detail.getFreeGoods()!=null) {
            if (detail.getFreeGoods().equals("0")) {
                yourOrderDetailHolder.layoutPromotions.setVisibility(View.GONE);
            } else {
                yourOrderDetailHolder.layoutPromotions.setVisibility(View.VISIBLE);
                if(isArabic)
                yourOrderDetailHolder.tvPromotionFreeItems.setText( detail.getFreeGoods()+" X ");
                else
                    yourOrderDetailHolder.tvPromotionFreeItems.setText("X " + detail.getFreeGoods());

            }
        }else {
            yourOrderDetailHolder.layoutPromotions.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(IMAGE_BASE_URL + detail.getDishImage())
                .placeholder(R.drawable.ic_place_holder)
                .into(yourOrderDetailHolder.ivProduct);

        yourOrderDetailHolder.btnPlus.setEnabled(false);
        yourOrderDetailHolder.btnMinus.setEnabled(false);
        yourOrderDetailHolder.etQty.setEnabled(false);
        yourOrderDetailHolder.btnPlus.setVisibility(View.GONE);
        yourOrderDetailHolder.btnMinus.setVisibility(View.GONE);
        yourOrderDetailHolder.ivDelete.setEnabled(false);
        yourOrderDetailHolder.ivDelete.setVisibility(View.GONE);

        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null){
            if (user.hide_price.equals("1")){
                yourOrderDetailHolder.tvPrice.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class YourOrderDetailHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct,ivDelete;
        TextView tvTitle,tvPrice;
        ImageView btnPlus,btnMinus;
        EditText etQty;
        View layoutPromotions;
        TextView tvPromotionFreeItems;

        public YourOrderDetailHolder(@NonNull View itemView) {
            super(itemView);
            //llcartUpdatedOffers=itemView.findViewById(R.id.si_cart_updated_llcartOfferProducts);
            ivProduct=itemView.findViewById(R.id.layout_cart_ivProduct);
            tvTitle=itemView.findViewById(R.id.layout_cart_tvTitle);
//            tvDescription=itemView.findViewById(R.id.layout_cart_tvDescription);
            tvPrice=itemView.findViewById(R.id.layout_cart_tvPrice);
            ivDelete=itemView.findViewById(R.id.layout_cart_ivDelete);
            btnPlus=itemView.findViewById(R.id.btnCounterPlus);
            btnMinus=itemView.findViewById(R.id.btnCounterMinus);
            etQty=itemView.findViewById(R.id.edtCounterQuantity);
            layoutPromotions =itemView.findViewById(R.id.layoutPromotions);
            tvPromotionFreeItems =itemView.findViewById(R.id.tvPromotionFreeItems);
        }
    }
}
