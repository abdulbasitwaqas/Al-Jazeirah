package com.app.aljazierah.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.ui.SeeAllActivity;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.bumptech.glide.Glide;
import com.roam.appdatabase.DatabaseManager;

import java.util.ArrayList;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewHolder> {
    private ArrayList<ProductByLocation> productList;
    private Context context;
    private boolean isArabic;
    public Activity activity;
    private boolean promotionCheck = true;



    public ProductDetailsAdapter(ArrayList<ProductByLocation> productByLocations, Context context, SeeAllActivity activity) {
        this.productList = productList;
        this.context = context;
        this.activity = activity;
        this.isArabic = AppController.setLocale();
    }

    @NonNull
    @Override
    public ProductDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(isArabic){
            ArabicData(holder,position);
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        else{
            EnglishData(holder,position);
            activity. getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }


        if (productList.get(position).getCount()>0) {
            holder.layoutPromotions.setVisibility(View.GONE);
            promotionCheck =true;
            checkPromotion(productList.get(position).getCount() + "", productList.get(position).getId(), holder);
        }
        else {
            holder.layoutPromotions.setVisibility(View.GONE);
        }
        if (productList.get(position).getBefore_discount()==null ||
                productList.get(position).getBefore_discount().equals("")||
                productList.get(position).getBefore_discount().equals("0")){
            holder.tv_before_sr.setVisibility(View.GONE);
            holder.tvBeforePrice.setVisibility(View.GONE);
        }else {
            holder.tv_before_sr.setVisibility(View.VISIBLE);
            holder.tvBeforePrice.setVisibility(View.VISIBLE);
            holder.tvBeforePrice.setText(productList.get(position).getBefore_discount());
            holder.tvBeforePrice.setPaintFlags( holder.tvBeforePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        holder.tv_sr.setText(context.getResources().getString(R.string.currency));

        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null){
            if (user.hide_price.equals("1")){
                holder.tvProductPrice.setVisibility(View.GONE);
                holder.tv_sr.setVisibility(View.GONE);
                holder.priceview.setVisibility(View.GONE);            }
        }


        Glide.with(context)
                .load(IMAGE_BASE_URL + productList.get(position).getImg())
                .placeholder(R.drawable.ic_place_holder)
                .into(holder.productIV);

    }

    private void checkPromotion(String count, String productId, MyViewHolder myViewHolder){
        if (ProductsSingleton.getInstance().getPromotionList()!= null&&ProductsSingleton.getInstance().getPromotionList().size() > 0) {
            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().size(); i++) {
                if (promotionCheck) {
                    setPromotion(count, productId, i, myViewHolder);
                }
            }
        }
    }

    private void setPromotion(String count, String productId, int position, MyViewHolder myViewHolder) {
        if (ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().size() == 0) {

            if (ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() > 0) {

                for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size(); j++) {
                    Double max = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMax());
                    Double min = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMin());
                    if (Integer.valueOf(count) >= min && Integer.valueOf(count) <= max) {
                        if (j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) {
                            myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + "0");
                        } else {
                            myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (max - Integer.valueOf(count) + 1));
                        }
                        myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                    } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                        Log.d("count......", "....." + (productId));
                        myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (min - Integer.valueOf(count)));
                        myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                        // promotionCheck = false;
                        // break;
                    } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                        myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + "0");
                        myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                        //promotionCheck = false;
                        // break;
                    }
                }
            } else if (ProductsSingleton.getInstance().getPromotionList().get(position).getPromotion_level().equals("product")) {
                if (Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getQuantity()) > 0 &&
                        Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getAddOn()) > 0) {
                    Log.d("printsome", "" + productId);
                    Double baseQuanitity = Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getQuantity());
                    Double addOn = Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getAddOn());
                    Double counter = Double.parseDouble(count);
                    Double mod = counter % baseQuanitity;
                    myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (baseQuanitity - mod));
                    Double freeItemcount = ((counter - mod) / baseQuanitity) * addOn;
                    myViewHolder.tvPromotionFreeItems.setText("x " + (int) Math.round(freeItemcount) + "");
                    myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                    Log.d("printsome11111", "" + productId);
                    //promotionCheck = false;
                }
            }

        }
        else {

            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().size(); i++) {

                if (ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().get(i).equals(productId)) {

                    if (ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() > 0) {

                        for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size(); j++) {

                            Double max = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMax());
                            Double min = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMin());
                            if (Integer.valueOf(count) >= min && Integer.valueOf(count) <= max) {

                                if (j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) {
                                    myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + "0");
                                } else {
                                    myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (max - Integer.valueOf(count) + 1));
                                }

                                myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                promotionCheck = false;
                                break;
                            } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                Log.d("count......", "....." + (productId));
                                myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (min - Integer.valueOf(count)));
                                myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                                promotionCheck = false;
                                break;
                            } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                                myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + "0");
                                myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                promotionCheck = false;
                                break;
                            }

                        }
                    } else if (ProductsSingleton.getInstance().getPromotionList().get(position).getPromotion_level().equals("product")) {
                        if (Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getQuantity()) > 0 &&
                                Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getAddOn()) > 0) {
                            Log.d("printsome", "" + productId);
                            Double baseQuanitity = Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getQuantity());
                            Double addOn = Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(position).getAddOn());
                            Double counter = Double.parseDouble(count);
                            Double mod = counter % baseQuanitity;
                            myViewHolder.tvPromotionRemainingItems.setText("" + context.getResources().getString(R.string._string_Remaining) + (int) (baseQuanitity - mod));
                            Double freeItemcount = ((counter - mod) / baseQuanitity) * addOn;
                            myViewHolder.tvPromotionFreeItems.setText("x " + (int) Math.round(freeItemcount) + "");
                            myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                            Log.d("printsome11111", "" + productId);
                            promotionCheck = false;
                        }
                    }

                    break;
                } else {
                    myViewHolder.layoutPromotions.setVisibility(View.GONE);
                }

            }

        }

    }


    private void ArabicData(ProductDetailsAdapter.MyViewHolder holder, int position){
        
        holder.tvProductPrice.setText(Utils.convertString(productList.get(position).getPrice_vat()));


        String name=productList.get(position).getNameAr().replace(")","");

        String replacename=name.replaceAll("[\\(\\(]","%");

        String[] name_array=replacename.split("%");
        holder.tvProductTitle.setText(name_array[0]);

        if(name_array.length>1){

            holder.si_home_Products_tvDetails.setText("("+name_array[1]+")");
        }
        else{


        }

    }

    private void EnglishData(ProductDetailsAdapter.MyViewHolder holder, int position){
        
        holder.tvProductPrice.setText(productList.get(position).getPrice_vat());
        String[] name_array=productList.get(position).getNameEn().trim().split("\\(");

        holder.tvProductTitle.setText(name_array[0]);

        if(name_array.length>1){

            holder.si_home_Products_tvDetails.setText("("+name_array[1]);
        }
        else{
            holder.si_home_Products_tvDetails.setText(productList.get(position).getNameEn());

        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout productListCL;
        private ImageView productIV;
        private TextView tvProductTitle, tv_before_sr,tvBeforePrice,tv_sr, tvProductPrice, si_home_Products_tvDetails, tvPromotionRemainingItems, tvPromotionFreeItems;
        private LinearLayout llProductList;
        private View layoutPromotions, priceview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productListCL=itemView.findViewById(R.id.productListCL);
            productIV=itemView.findViewById(R.id.productIV);
            tvProductPrice=itemView.findViewById(R.id.tvProductPrice);
            llProductList=itemView.findViewById(R.id.llProductList);
            si_home_Products_tvDetails=itemView.findViewById(R.id.tvProductDetails);
            layoutPromotions =itemView.findViewById(R.id.layoutPromotions);
            tvPromotionRemainingItems =itemView.findViewById(R.id.tvPromotionRemainingItems);
            tvPromotionFreeItems =itemView.findViewById(R.id.tvPromotionFreeItems);
            priceview =itemView.findViewById(R.id.tvPromotionFreeItems);
        }
    }


}
