package com.app.aljazierah.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.AddToWishStockAlertRequest;
import com.app.aljazierah.ui.LoginActivity;
import com.app.aljazierah.ui.SingleProductDetailActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.MyViewHolder> {
    private List<ProductByLocation> productList;
    private Context mContext;
    private boolean isArabic;
    boolean promotionCheck = true;
    public ProgressDialog mProgressDialog;

    public SeeAllAdapter(Context context, ArrayList<ProductByLocation> productList) {
        this.productList = productList;
        this.mContext = context;
        this.isArabic = AppController.setLocale();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_products, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (productList.get(position).getCount() > 0) {
            holder.layoutPromotions.setVisibility(View.GONE);
            promotionCheck = true;
            checkPromotion(productList.get(position).getCount() + "", productList.get(position).getId(), holder);
        }
        else {
            holder.layoutPromotions.setVisibility(View.GONE);
        }



        if (isArabic) {
            holder.si_home_Products_tvTitle.setText(productList.get(position).getNameAr());
        } else {

            holder.si_home_Products_tvTitle.setText(productList.get(position).getNameEn());
        }
        holder.si_home_Products_tvPrice.setText(Utils.convertString(productList.get(position).getPrice_vat()));


        holder.editext_productquantity.setText(productList.get(position).getCount()+"");

        if (productList.get(position).getProd_rating().toString().equals("")||productList.get(position).getProd_rating().toString().equals("0")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
        }
        else if (productList.get(position).getProd_rating().toString().equals("1")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
        }
        else if (productList.get(position).getProd_rating().toString().equals("2")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
        }
        else if (productList.get(position).getProd_rating().toString().equals("3")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
        }
        else if (productList.get(position).getProd_rating().toString().equals("4")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_unselected));
        }
        else if (productList.get(position).getProd_rating().toString().equals("5")){
            holder.oneStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.twoStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.threeStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.fourStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
            holder.fiveStarIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_selected));
        }




        if (productList.get(position).getIs_wish_list_item().equals("1")){
            holder.imageWhish.setImageResource(R.drawable.ic_favourite_filled);
        }else {
            holder.imageWhish.setImageResource(R.drawable.ic_favourite);
        }

        if (productList.get(position).getStock().equals("false")){
            holder.addToCartBtn.setVisibility(View.GONE);
            holder.layout_counter.setVisibility(View.GONE);
            holder.outOfStockNotification.setVisibility(View.VISIBLE);
        }else {
            holder.outOfStockNotification.setVisibility(View.GONE);
            if (holder.editext_productquantity.getText().toString().trim().equals("0")){
                holder.addToCartBtn.setVisibility(View.VISIBLE);
                holder.layout_counter.setVisibility(View.GONE);
            }
            else {
                holder.addToCartBtn.setVisibility(View.GONE);
                holder.layout_counter.setVisibility(View.VISIBLE);

            }
        }

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addToCartBtn.setVisibility(View.GONE);
                holder.addToCartBtn.setVisibility(View.GONE);
                holder.layout_counter.setVisibility(View.VISIBLE);
                int count = productList.get(position).getCount() + 1;
                productList.get(position).setCount(count);

                holder.editext_productquantity.setText(productList.get(position).getCount() + "");
                Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productList.get(position).getId(),
                        Cart.class);
                if (result == null) {
                    Cart.addToCart(productList.get(position), -1, 1);
                } else {
                    Cart.addOneItem(productList.get(position).getId());
                }
                ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
                updateAmount();
                ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                showCartLayout();
                getCartNumber();
                notifyDataSetChanged();
            }
        });

        holder.addproduct_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = productList.get(position).getCount() + 1;
                productList.get(position).setCount(count);

                holder.editext_productquantity.setText(productList.get(position).getCount() + "");
                Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productList.get(position).getId(),
                        Cart.class);

                if (result == null) {
                    Cart.addToCart(productList.get(position), -1, 1);
                } else {
                    Cart.addOneItem(productList.get(position).getId());
                }

                ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
                updateAmount();
                ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                showCartLayout();
                getCartNumber();
                notifyDataSetChanged();

            }
        });

        holder.minusproduct_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productList.get(position).getCount() < 1) {
                    int count = 0;
                    productList.get(position).setCount(count);
                    holder.editext_productquantity.setText(productList.get(position).getCount() + "");
                }
                else if (productList.get(position).getCount() == 1) {
                    int count = productList.get(position).getCount() - 1;
                    productList.get(position).setCount(count);
                    holder.editext_productquantity.setText(productList.get(position).getCount() + "");
                    Cart.deleteProductFromCart(productList.get(position).getId());
                    updateAmount();
                    productList.get(position).setAddedToCart(false);
                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");

                  getCartNumber();
                    notifyDataSetChanged();
                }
                else {
                    holder.layout_counter.setVisibility(View.VISIBLE);
                    holder.addToCartBtn.setVisibility(View.GONE);
                    holder.addToCartBtn.setVisibility(View.GONE);
                    int count = productList.get(position).getCount() - 1;
                    productList.get(position).setCount(count);
                    holder.editext_productquantity.setText(productList.get(position).getCount() + "");
                    Cart.removeOneItem(productList.get(position).getId());
                    updateAmount();
                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                   getCartNumber();
                    notifyDataSetChanged();
                }
            }
        });

        holder.productsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SingleProductDetailActivity.class);
                intent.putExtra("productID", "" + productList.get(position).getId());
                intent.putExtra("position", "" + position);
                intent.putExtra("productDetails", "" +  new Gson().toJson(productList.get(position)));
                Log.d("*producyByLocPos", "" + productList.get(position));
                intent.putExtra("image", "" + productList.get(position).getImg());
                if (isArabic) {
                    intent.putExtra("prodDescription", "" + productList.get(position).getDescriptionAr());
                    intent.putExtra("prodName", "" + productList.get(position).getNameAr());
                } else {
                    intent.putExtra("prodDescription", "" + productList.get(position).getDescriptionEn());
                    intent.putExtra("prodName", "" + productList.get(position).getNameEn());
                }
                intent.putExtra("productPrice", "" + productList.get(position).getPrice_vat());

                mContext.startActivity(intent);
            }
        });        holder.productsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SingleProductDetailActivity.class);
                intent.putExtra("productID", "" + productList.get(position).getId());
                intent.putExtra("position", "" + position);
                intent.putExtra("productDetails", "" +  new Gson().toJson(productList.get(position)));
                Log.d("*producyByLocPos", "" + productList.get(position));
                intent.putExtra("image", "" + productList.get(position).getImg());
                if (isArabic) {
                    intent.putExtra("prodDescription", "" + productList.get(position).getDescriptionAr());
                    intent.putExtra("prodName", "" + productList.get(position).getNameAr());
                } else {
                    intent.putExtra("prodDescription", "" + productList.get(position).getDescriptionEn());
                    intent.putExtra("prodName", "" + productList.get(position).getNameEn());
                }
                intent.putExtra("productPrice", "" + productList.get(position).getPrice_vat());

                mContext.startActivity(intent);
            }
        });



        holder.imageWhish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                if (user!=null){
                    addToWishList(productList.get(position),position);
                }else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));

                }

            }
        });
        holder.outOfStockNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user!=null){
                    stockNotificationAlert(productList.get(position));
                }else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }

            }
        });


        if (productList.get(position).getBefore_discount() == null ||
                productList.get(position).getBefore_discount().equals("") ||
                productList.get(position).getBefore_discount().equals("0")) {
            holder.tv_before_sr.setVisibility(View.GONE);
            holder.tvBeforePrice.setVisibility(View.GONE);
        } else {
            holder.tv_before_sr.setVisibility(View.VISIBLE);
            holder.tvBeforePrice.setVisibility(View.VISIBLE);
            holder.tvBeforePrice.setText(productList.get(position).getBefore_discount());
            holder.tvBeforePrice.setPaintFlags(holder.tvBeforePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }



        if (productList.get(position).getImg().size()>0) {
            Glide.with(mContext)
                    .load(IMAGE_BASE_URL + productList.get(position).getImg().get(0))
                    .placeholder(R.drawable.ic_place_holder)
                    .into(holder.productsImage);
        }else {
            Glide.with(mContext)
                    .load(IMAGE_BASE_URL + "")
                    .placeholder(R.drawable.ic_place_holder)
                    .into(holder.productsImage);
        }



        holder.tv_sr.setText(mContext.getResources().getString(R.string.currency));
//        setProductCount(holder.editext_productquantity, position);
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.hide_price.equals("1")) {
                holder.si_home_Products_tvPrice.setVisibility(View.GONE);
                holder.tv_sr.setVisibility(View.GONE);
                holder.priceview.setVisibility(View.GONE);
            }
        }

        setProductCount(holder.editext_productquantity, position);
    }


    public void updateAmount() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {

            String amount = String.format("%.2f", Cart.getTotalPriceVat());
            ProductsSingleton.getInstance().getTv_cartamount().setText(mContext.getResources().getString(R.string.currency) + " " + Utils.convertString(amount));

        } else {
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);


        }

        if (Cart.getTotalItems() < 1) {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.INVISIBLE);
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
        } else {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
        }


    }

    public void showCartLayout() {
        ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
        ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
    }

    public void getCartNumber() {
        if (Cart.getTotalItems() > 0) {
            ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
        } else {
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBeforePrice, tv_before_sr, si_home_Products_tvPrice, tv_sr, si_home_Products_tvTitle;
        private ImageView productsImage, addToCartBtn,imageWhish,outOfStockNotification, oneStarIV, twoStarIV, threeStarIV, fourStarIV, fiveStarIV;
        private LinearLayout addMoreLayout;
        private EditText editext_productquantity;
        private ImageView addproduct_button, minusproduct_button;
        private View priceview;
        View layoutPromotions;
        TextView tvPromotionFreeItems;
        TextView tvPromotionRemainingItems;
        private View layout_counter;
        private CardView mainRL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainRL = itemView.findViewById(R.id.cardView);
            si_home_Products_tvPrice = itemView.findViewById(R.id.tvProductPrice);
            si_home_Products_tvTitle = itemView.findViewById(R.id.tvProductTitle);
            //si_home_Products_tvDetails = itemView.findViewById(R.id.tvProductDetails);
            productsImage = itemView.findViewById(R.id.imageProductThumb);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            editext_productquantity = itemView.findViewById(R.id.edtCounterQuantity);
            addproduct_button = itemView.findViewById(R.id.btnCounterPlus);
            minusproduct_button = itemView.findViewById(R.id.btnCounterMinus);
            tv_sr = itemView.findViewById(R.id.tv_sr);
            priceview = itemView.findViewById(R.id.priceview);
            layoutPromotions = itemView.findViewById(R.id.layoutPromotions);
            tvPromotionFreeItems = itemView.findViewById(R.id.tvPromotionFreeItems);
            tvPromotionRemainingItems = itemView.findViewById(R.id.tvPromotionRemainingItems);
            tvBeforePrice = itemView.findViewById(R.id.tvBeforePrice);
            tv_before_sr = itemView.findViewById(R.id.tv_before_sr);
            layout_counter = itemView.findViewById(R.id.layout_addMinus);
            imageWhish = itemView.findViewById(R.id.imageWhish);
            outOfStockNotification = itemView.findViewById(R.id.outOfStockNotification);

            oneStarIV = itemView.findViewById(R.id.oneStarIV);
            twoStarIV = itemView.findViewById(R.id.twoStarIV);
            threeStarIV = itemView.findViewById(R.id.threeStarIV);
            fourStarIV = itemView.findViewById(R.id.fourStarIV);
            fiveStarIV = itemView.findViewById(R.id.fiveStarIV);
        }
    }



    private void setProductCount(final EditText editText_productcount, final int position) {

        editText_productcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText_productcount.getText().toString().matches("[0-9]+")) {
                    //  productList.get(position).setCount(Integer.parseInt(editText_productcount.getText().toString()));
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText_productcount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (editText_productcount.getText().toString().equals("")) {
                        editText_productcount.setText("0");
                    }

                    int count = Integer.parseInt(editText_productcount.getText().toString());
                    if (count < 1) {
                        Cart.deleteProductFromCart(productList.get(position).getId());
                        productList.get(position).setCount(count);
                        updateAmount();
                    } else {
                        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productList.get(position).getId(),
                                Cart.class);
                        Cart.removeItems(productList.get(position).getId());
                        productList.get(position).setCount(count);
                        if (result == null) {
                            Cart.addToCart(productList.get(position), -1, count);
                        } else {
                            Cart.addMultipleItem(productList.get(position).getId(), count, productList.get(position));
                        }
                    }


                    updateAmount();

                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");

                    if (Cart.getTotalItems() > 0) {
                        ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
                        ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
                        ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
                    } else {

                        ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);
                        ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                        ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);

                    }
                    notifyDataSetChanged();
                }
                return false;
            }
        });


    }


    private void ArabicData(MyViewHolder holder, int position) {
        holder.si_home_Products_tvPrice.setText(Utils.convertString(productList.get(position).getPrice_vat()));
        String name = productList.get(position).getNameAr().replace(")", "");
        String replacename = name.replaceAll("[\\(\\(]", "%");
        String[] name_array = replacename.split("%");
        holder.si_home_Products_tvTitle.setText(name_array[0]);

    }

    private void EnglishData(MyViewHolder holder, int position) {
        holder.si_home_Products_tvPrice.setText(productList.get(position).getPrice_vat());
        String[] name_array = productList.get(position).getNameEn().trim().split("\\(");
        holder.si_home_Products_tvTitle.setText(name_array[0]);
    }

    private void checkPromotion(String count, String productId, MyViewHolder myViewHolder) {
        if (ProductsSingleton.getInstance().getPromotionList() != null && ProductsSingleton.getInstance().getPromotionList().size() > 0) {
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
                            myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + "0");
                        } else {
                            myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (max - Integer.valueOf(count) + 1));
                        }

                        myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                        // promotionCheck = false;
                        //break;
                    } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                        myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                        Log.d("count......", "....." + (productId));
                        myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (min - Integer.valueOf(count)));
                        myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                        // promotionCheck = false;
                        // break;
                    } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                        myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + "0");
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
                    myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (baseQuanitity - mod));
                    Double freeItemcount = ((counter - mod) / baseQuanitity) * addOn;
                    myViewHolder.tvPromotionFreeItems.setText("x " + (int) Math.round(freeItemcount) + "");
                    myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                    Log.d("printsome11111", "" + productId);
                    //promotionCheck = false;
                }
            }

        } else {

            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().size(); i++) {

                if (ProductsSingleton.getInstance().getPromotionList().get(position).getProduct_id_list().get(i).equals(productId)) {

                    if (ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() > 0) {

                        for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size(); j++) {

                            Double max = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMax());
                            Double min = Double.valueOf(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getMin());
                            if (Integer.valueOf(count) >= min && Integer.valueOf(count) <= max) {

                                if (j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) {
                                    myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + "0");
                                } else {
                                    myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (max - Integer.valueOf(count) + 1));
                                }

                                myViewHolder.tvPromotionFreeItems.setText("x " + Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().get(j).getAdd_on()));
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                promotionCheck = false;
                                break;
                            } else if (Integer.valueOf(count) > 0 && Integer.valueOf(count) < min && j == 0) {
                                myViewHolder.layoutPromotions.setVisibility(View.VISIBLE);
                                Log.d("count......", "....." + (productId));
                                myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (min - Integer.valueOf(count)));
                                myViewHolder.tvPromotionFreeItems.setText("x " + 0);
                                promotionCheck = false;
                                break;
                            } else if ((j == ProductsSingleton.getInstance().getPromotionList().get(position).getRange_limit().size() - 1) && Integer.valueOf(count) >= max) {
                                myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + "0");
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
                            myViewHolder.tvPromotionRemainingItems.setText("" + mContext.getResources().getString(R.string._string_Remaining) + (int) (baseQuanitity - mod));
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

    public void setProductList(List<ProductByLocation> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    private void stockNotificationAlert(ProductByLocation productByLocation) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.change_alert_dialog);
        dialog.setCancelable(true);
        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        Button okBtn = dialog.findViewById(R.id.okBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        title.setText(""+mContext.getResources().getString(R.string._string_notificatio));
        message.setText(""+mContext.getResources().getString(R.string._string_out_of_stock_message));
        dialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockNotification(productByLocation);
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void stockNotification(ProductByLocation productByLocation){
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        String action = "9";
        showProgress(true);
        APIManager.getInstance().addTowishListStockAlert(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("code").equals("200")){
                        if (isArabic)
                            Toast.makeText(mContext, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                        else Toast.makeText(mContext, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new AddToWishStockAlertRequest(user.userId,productByLocation.getId(),"stock_alert",action));
    }


    private void addToWishList(ProductByLocation productByLocation,int position){
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        String action = "0";
        if (productByLocation.getIs_wish_list_item().equals("0")){
            action = "1";
        }
        showProgress(true);
        APIManager.getInstance().addTowishListStockAlert(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("code").equals("200")){
                        if (isArabic)
                            Toast.makeText(mContext, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                        else Toast.makeText(mContext, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();
                        if (productByLocation.getIs_wish_list_item().equals("0")){
                            productList.get(position).setIs_wish_list_item("1");
                            updateSingletonList(productByLocation.getId(),"1");
                        }else {
                            productList.get(position).setIs_wish_list_item("0");
                            updateSingletonList(productByLocation.getId(),"0");

                        }
                        notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new AddToWishStockAlertRequest(user.userId,productByLocation.getId(),"wish_list",action));
    }


    private void updateSingletonList(String id, String action){
        for (int i = 0; i< ProductsSingleton.getInstance().getProductByLocations().size();i++){
            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().toString().equals(id)){
                ProductsSingleton.getInstance().getProductByLocations().get(i).setIs_wish_list_item(action);
            }
        }
    }

    public void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getResources().getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

}
