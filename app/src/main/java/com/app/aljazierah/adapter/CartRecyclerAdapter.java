package com.app.aljazierah.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.ui.CheckOutActivity;
import com.app.aljazierah.utils.ProductsSingleton;
import com.bumptech.glide.Glide;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.roam.appdatabase.DatabaseManager;

import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartRecyclerHolder> {

    public interface ICategory{
        void onQuantityChanged();
    }

    public interface  DeleteDialog{
        void deleteItem(final Cart cart);
    }



    private List<Cart> mData;
    private Context context;
    public Activity activity;
    private boolean isArabic;
    private final String TAG="CartRecyclerAdapter";
    boolean promotionCheck = true;
    private CheckOutActivity checkOutActivity;
    ICategory iCategory;
    DeleteDialog deleteDialog;
    public class CartRecyclerHolder extends RecyclerView.ViewHolder {
        ImageView ivProducts,ivDelete;
        TextView tvTitle,tvPrice;
//        TextView tvDescription;
        ImageView btnPlus,btnMinus;
        EditText etQty;
        //View mainview;

        View layoutPromotions;
        TextView tvPromotionFreeItems;
        ImageView iv_cart_gift;


        public CartRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            ivProducts=itemView.findViewById(R.id.layout_cart_ivProduct);
            ivDelete=itemView.findViewById(R.id.layout_cart_ivDelete);
            tvTitle=itemView.findViewById(R.id.layout_cart_tvTitle);
//            tvDescription=itemView.findViewById(R.id.layout_cart_tvDescription);
            tvPrice=itemView.findViewById(R.id.layout_cart_tvPrice);
            btnPlus=itemView.findViewById(R.id.btnCounterPlus);
            btnMinus=itemView.findViewById(R.id.btnCounterMinus);
            etQty=itemView.findViewById(R.id.edtCounterQuantity);
            layoutPromotions =itemView.findViewById(R.id.layoutPromotions);
            tvPromotionFreeItems =itemView.findViewById(R.id.tvPromotionFreeItems);
            iv_cart_gift=itemView.findViewById(R.id.iv_cart_gift);
        }
    }

    public CartRecyclerAdapter(Activity activity, CheckOutActivity checkOutActivity, List<Cart> mData ,Context context, ICategory iCategory,DeleteDialog deleteDialog) {
        this.mData = mData;
        Log.d("cartlist",""+mData.size());

        this.activity=activity;
        this.context = context;
        this.checkOutActivity = checkOutActivity;
        this.iCategory=iCategory;
        this.deleteDialog=deleteDialog;
        this.isArabic = AppController.setLocale();

    }

    @NonNull
    @Override
    public CartRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CartRecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_cart_list,viewGroup,false));
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CartRecyclerHolder cartRecyclerHolder, final int i) {

        try {
            final Cart cart = mData.get(i);
               // cartRecyclerHolder.mainview.setVisibility(View.VISIBLE);
                String productTtile;
                if (isArabic) {
                    productTtile = cart.productTitleAr;
                } else {
                    productTtile = cart.productTitleEn;
                }

            if (cart.count>0) {
                cartRecyclerHolder.layoutPromotions.setVisibility(View.GONE);
                promotionCheck =true;
                checkPromotion(cart.count + "", cart.productId, cartRecyclerHolder);
            }
            else {
                cartRecyclerHolder.layoutPromotions.setVisibility(View.GONE);
            }
                cartRecyclerHolder.tvTitle.setText(productTtile);

                if(productTtile.contains("(") && productTtile.contains(")")){
                    String title[] = cartRecyclerHolder.tvTitle.getText().toString().split("\\(");
                    cartRecyclerHolder.tvTitle.setText(title[0]);
                    if(title.length > 1) {
//                        cartRecyclerHolder.tvDescription.setText("(" + title[1]);
                    }
                }
                cartRecyclerHolder.tvPrice.setText(context.getResources().getString(R.string.currency)+ "  " +cart.price_vat  );
                cartRecyclerHolder.etQty.setText("" + cart.count);

                Glide.with(context)
                        .load(IMAGE_BASE_URL + cart.image)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(cartRecyclerHolder.ivProducts);

                cartRecyclerHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart.count = cart.count + 1;
                        cart.update();
                        checkOutActivity.setPayment();
                        notifyDataSetChanged();
                        //updateFooter();
                        iCategory.onQuantityChanged();
                    }
                });

                cartRecyclerHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cart.count <= 1) {
//                            openDeleteDialog(cart);
                            deleteDialog.deleteItem(cart);
                        } else {
                            cart.count = cart.count - 1;
                            checkOutActivity.setPayment();
                            notifyDataSetChanged();
                            //updateFooter();
                            iCategory.onQuantityChanged();
                        }
                    }
                });


                cartRecyclerHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkOutActivity.setPayment();
//                        openDeleteDialog(cart);
                        deleteDialog.deleteItem(cart);
                        iCategory.onQuantityChanged();
                    }
                });

                setProductCount(cartRecyclerHolder.etQty, cartRecyclerHolder.getAdapterPosition(), cart);

                if (mData.size() < 2) {
                    if (mData.get(i).count == 0) {
                        activity.finish();
                    }
                }

            User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
            if (user!=null){
                if (user.hide_price.equals("1")){
                    cartRecyclerHolder.tvPrice.setVisibility(View.GONE);
                }
            }





        }catch (Exception ex){
            Log.e("IEx",ex.toString());
        }
    }



    private void checkPromotion(String count, String productId, CartRecyclerHolder myViewHolder){
        if (ProductsSingleton.getInstance().getPromotionList()!= null&&ProductsSingleton.getInstance().getPromotionList().size() > 0) {
            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().size(); i++) {
                if (promotionCheck) {
                    setPromotion(count, productId, i, myViewHolder);
                }
            }
        }
    }

    private void setPromotion(String count, String productId, int position, CartRecyclerHolder myViewHolder){

        if (ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().size() == 0) {



            if (ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() > 0) {

                for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size(); j++) {

                    Double max = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMax());
                    Double min = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMin());
                    if (Integer.valueOf(count) >= min && Integer.valueOf(count) <= max) {

                         /*   if (j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size()-1){
                                myViewHolder.tvPromotionRemainingItems.setText("0");
                            }else {
                                myViewHolder.tvPromotionRemainingItems.setText("" + (int) (max - Integer.valueOf(count) + 1));
                            }*/

                        myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                        //promotionCheck = false;
                       // break;
                    } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                        myViewHolder.layoutPromotions.setVisibility(View.GONE);
                        Log.d("count......", "....." + (productId));
                        // myViewHolder.tvPromotionRemainingItems.setText("" + (int)(min - Integer.valueOf(count)));
                        myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                       // promotionCheck = false;
                        //break;
                    } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                        //  myViewHolder.tvPromotionRemainingItems.setText("0");
                        myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                       // promotionCheck = false;
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
                    //  myViewHolder.tvPromotionRemainingItems.setText(""+(int)(baseQuanitity-mod));
                    Double freeItemcount = ((counter - mod) / baseQuanitity) * addOn;


                    myViewHolder.tvPromotionFreeItems.setText("x " + (int) Math.round(freeItemcount) + "");
                   if (freeItemcount>0)
                    myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                    Log.d("printsome11111", "" + productId);

                   // promotionCheck = false;
                }
            }

        }else {

            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().size(); i++) {

                if (ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().get(i).equals(productId)) {

                    if (ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() > 0) {

                        for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size(); j++) {

                            Double max = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMax());
                            Double min = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMin());
                            if (Integer.valueOf(count) >= min && Integer.valueOf(count) <= max) {

                         /*   if (j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size()-1){
                                myViewHolder.tvPromotionRemainingItems.setText("0");
                            }else {
                                myViewHolder.tvPromotionRemainingItems.setText("" + (int) (max - Integer.valueOf(count) + 1));
                            }*/

                                myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                promotionCheck = false;
                                break;
                            } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                                myViewHolder.layoutPromotions.setVisibility(View.GONE);
                                Log.d("count......", "....." + (productId));
                                // myViewHolder.tvPromotionRemainingItems.setText("" + (int)(min - Integer.valueOf(count)));
                                myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                                promotionCheck = false;
                                break;
                            } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                                //  myViewHolder.tvPromotionRemainingItems.setText("0");
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
                            //  myViewHolder.tvPromotionRemainingItems.setText(""+(int)(baseQuanitity-mod));
                            Double freeItemcount = ((counter - mod) / baseQuanitity) * addOn;
                            myViewHolder.tvPromotionFreeItems.setText("x " + (int) Math.round(freeItemcount) + "");
                            if (freeItemcount>0)
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




    @Override
    public int getItemCount() {
        return mData.size();
    }


 /*   private void updateFooter() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {
            mData.get(mData.size() - 1).amount = data[1];
            notifyDataSetChanged();

        }
    }*/



    private void ArabicData(CartRecyclerHolder holder, int position){

        holder.tvPrice.setText(mData.get(position).amount);
        String[] name_array=mData.get(position).productTitleAr.trim().split("\\)");



        if(name_array.length>1){

//            holder.tvDescription.setText(name_array[1]+")");
        }
        else{
//            holder.tvDescription.setText(mData.get(position).productTitleAr);

        }

    }

    private void EnglishData(CartRecyclerHolder holder, int position){
        Log.e("NAme",mData.get(position).productTitleEn);
        holder.tvPrice.setText(mData.get(position).amount);
        String[] name_array=mData.get(position).productTitleEn.trim().split("\\(");

        holder.tvTitle.setText(name_array[0]);

        if(name_array.length>1){

//            holder.tvDescription.setText("("+name_array[1]);
        }
        else{
//            holder.tvDescription.setText(mData.get(position).productTitleEn);

        }
    }

    private void openDeleteDialog(final Cart cart) {
/*
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(context.getResources().getString(R.string.deleting_confirm));
        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(context.getResources().getString(R.string.remove_product_message));

        Button ok = (Button) dialog.findViewById(R.id.okBtn);
        Button cancel = (Button) dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(cart);
                cart.delete();
                notifyDataSetChanged();
                iCategory.onQuantityChanged();
                if(mData.size()<1){
                    activity.finish();
                }

                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put("Products",""+""+cart);
                eventValue.put("Quantity",""+cart.count);
                CleverTapAPI.getDefaultInstance(context).pushEvent("Remove from cart:",eventValue);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.count<1){


                    cart.count = 1;
                    cart.update();
                    notifyDataSetChanged();
                    iCategory.onQuantityChanged();
                    dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                }

            }
        });

        dialog.show();*/
    }

    private void setProductCount(final EditText etQty, final int  position, final Cart cart){

        etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etQty.getText().toString().matches("[0-9]+")) {
                    //  productList.get(position).setCount(Integer.parseInt(editText_productcount.getText().toString()));
                }
                else{
                    Toast.makeText(context, "Select only numbers", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(etQty.getText().toString().equals("")){
                        etQty.setText("0");
                    }
                    if(Integer.parseInt(etQty.getText().toString())<1){

                        mData.get(position).count=0;

//                        openDeleteDialog(cart);
                        deleteDialog.deleteItem(cart);


                    }
                    else {
                        cart.count = Integer.parseInt(etQty.getText().toString());
                        cart.update();
                        notifyDataSetChanged();
                        iCategory.onQuantityChanged();
                    }
                }
                return false;
            }
        });


    }


}
