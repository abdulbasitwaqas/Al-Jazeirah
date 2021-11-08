package com.app.aljazierah.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.CancelReasons;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.Favourites;
import com.app.aljazierah.object.Orders;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.YourOrderDetails.OrderDetail;
import com.app.aljazierah.object.YourOrderDetails.YourOrderDetail;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.CancelOrderRequest;
import com.app.aljazierah.object.request.FavouriteRequest;

import com.app.aljazierah.object.request.OrderRequest;
import com.app.aljazierah.ui.CancelOrder;
import com.app.aljazierah.ui.CanceledOrder;
import com.app.aljazierah.ui.CheckOutActivity;
import com.app.aljazierah.ui.OrderDetails;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.recycler.DividerItemDecoration;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.ActiveOrderHolder> {
    private List<Orders>mData;
    public Context mContext;
    List<Favourites> favouritesList;
    String userID;
    boolean isArabic;
    private final String TAG="ActiveOrdrReclr";
    public ProgressDialog mProgressDialog;
    public Dialog dialog;
    Activity activity;
    public OrdersRecyclerViewAdapter(Activity activity, Context mContext, List<Orders> mData)
    {
        this.activity=activity;
        this.mData = mData;
        this.mContext=mContext;
        favouritesList= DatabaseManager.getInstance().getAllOfClass(Favourites.class);
        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null)
        userID=user.userId;
        isArabic= AppController.setLocale();
    }

    @NonNull
    @Override
    public ActiveOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_orders_list, parent, false);
        return new ActiveOrderHolder(itemView,mData);
    }

    @Override

    public void onBindViewHolder(@NonNull final ActiveOrderHolder activeOrderHolder, final int i) {
        final Orders orders=mData.get(i);
//        activeOrderHolder.tvPrice.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/poppins_bold.ttf"), Typeface.NORMAL);
        //Check if Favourite



        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null){
            if (user.hide_price.equals("1")){
                activeOrderHolder.tvPrice.setVisibility(View.GONE);
            }
        }


        if (orders.getQitafRewardpoints().equals("0")||orders.getQitafRewardpoints().equals("")){
            activeOrderHolder.tv_stc_qitaf_points.setVisibility(View.GONE);
        }else {
            activeOrderHolder.tv_stc_qitaf_points.setText(""+orders.getQitafRewardpoints());
        }

        if(orders.getIsreorderable().equals("1")){
            activeOrderHolder.imageViewReorder.setVisibility(View.VISIBLE);
            activeOrderHolder.reOrderView.setVisibility(View.VISIBLE);

        }
        else{
            activeOrderHolder.imageViewReorder.setVisibility(View.GONE);
            activeOrderHolder.reOrderView.setVisibility(View.GONE);
        }


        if(orders.getIsFavorite().equals("1")){
            activeOrderHolder.imageViewFavourite.setImageResource(R.drawable.ic_favourite_filled);
        }
        else{
            activeOrderHolder.imageViewReorder.setVisibility(View.GONE);
            activeOrderHolder.reOrderView.setVisibility(View.GONE);
            activeOrderHolder.imageViewFavourite.setImageResource(R.drawable.ic_favourite);
        }

        activeOrderHolder.imageViewReorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrderDetails(userID,orders.getId());
            }
        });

        if (orders.getIscancelable().equals("1")||orders.getIscancelable().equals("3")||
                orders.getIscancelable().equals("4")||orders.getIscancelable().equals("5"))
        {
            activeOrderHolder.imageViewCancel.setVisibility(View.VISIBLE);
        }else if (orders.getIscancelable().equals("0")){
            activeOrderHolder.imageViewCancel.setVisibility(View.VISIBLE);
            activeOrderHolder.imageViewCancel.setAlpha(0.5f);
        }else {
            activeOrderHolder.imageViewCancel.setVisibility(View.GONE);
          }

        if (orders.getIseditable().equals("1")){
            activeOrderHolder.imageViewEdit.setVisibility(View.VISIBLE);
            activeOrderHolder.cancelView.setVisibility(View.VISIBLE);
        }else {
            activeOrderHolder.imageViewEdit.setVisibility(View.GONE);
            activeOrderHolder.cancelView.setVisibility(View.GONE);
        }

        SpannableString span2 = new SpannableString(" "+mContext.getResources().getString(R.string.currency));
        span2.setSpan(new RelativeSizeSpan(0.2f), 0,1, 0);

        activeOrderHolder.tvOrderID.setText(String.format("%s%s", mContext.getResources().getString(R.string.Order_id), orders.getOrdId()));
        activeOrderHolder.tvPrice.setText(String.format("%s%s", orders.getOrdTotalPrice(), span2));
        activeOrderHolder.tvDate.setText(orders.getOrdDate());

//        activeOrderHolder.imageView2.setBackground(o);

       // activeOrderHolder.itemView.setOnClickListener(onClickListener);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderDetails.class);
                intent.putExtra("order_id", orders.getId());
                intent.putExtra("order_number", orders.getOrdId());
                mContext.startActivity(intent);
            }
        };
        activeOrderHolder.tvStatus.setOnClickListener(onClickListener);
        activeOrderHolder.tvPrice.setOnClickListener(onClickListener);
        activeOrderHolder.tvDate.setOnClickListener(onClickListener);
        activeOrderHolder.tvOrderID.setOnClickListener(onClickListener);
        activeOrderHolder.mainOLL.setOnClickListener(onClickListener);
        activeOrderHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CheckOutActivity.class);
                intent.putExtra("order_id", orders.getOrdId());
                intent.putExtra("edit_order", true);
                intent.putExtra("order_server_id",orders.getId());
                intent.putExtra("is_cancelable", orders.getIscancelable());
                intent.putExtra("is_editable", orders.getIseditable());
                intent.putExtra("is_reorderable", orders.getIsreorderable());
                mContext.startActivity(intent);
            }
        });

        if (orders.getCurrentStatus().equals("4")){
            activeOrderHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.delivered_order_bg));
            activeOrderHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        else if (orders.getCurrentStatus().equals("6")){
            activeOrderHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.cancelled_order_bg));
            activeOrderHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.white));

        }

            if(isArabic){
                activeOrderHolder.tvStatus.setText(orders.getOrderStatusAr());
            }
            else{
                activeOrderHolder.tvStatus.setText(orders.getOrderStatusEn());
            }


        activeOrderHolder.imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orders.getIsFavorite().equals("1")) {
                    activeOrderHolder.imageViewFavourite.setImageResource(R.drawable.ic_favourite);
                    if (userID!=null){
                        showProgress(true);
                        Log.e("Favourite",userID+"  "+orders.getOrdId());
                        APIManager.getInstance().makeFavourite(new APIManager.Callback() {
                            @Override
                            public void onResult(boolean z, String response) {
                                showProgress(false);
                                Log.e(TAG, "onResult() called with: z = [" + z + "], response = [" + response + "]");
                                broadcastData(orders.getOrdId(),"unfav");
                           }
                        },new FavouriteRequest(userID,orders.getOrdId(),"0"));
                    }
                }else {
                    activeOrderHolder.imageViewFavourite.setImageResource(R.drawable.ic_favourite_filled);
                    if (userID!=null){
                        showProgress(true);
                    APIManager.getInstance().makeFavourite(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            showProgress(false);
                            broadcastData(orders.getOrdId(),"fav");
                            Map<String, Object> eventValue = new HashMap<String, Object>();
                            eventValue.put("Products",""+""+orders.getOrdId());
                            eventValue.put("Order status",""+orders.getOrderStatusEn());
//                            CleverTapAPI.getDefaultInstance(mContext).pushEvent("Add order to favourite:",eventValue);
                        }
                    },new FavouriteRequest(userID,orders.getOrdId(),"1"));
                }
                }
            }
        });

        activeOrderHolder.imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orders.getIscancelable().equals("1")||orders.getIscancelable().equals("3")||
                        orders.getIscancelable().equals("4")
                        ||orders.getIscancelable().equals("5")){
//                    getCancelOrderReasons(orders);
                    Intent intent = new Intent(mContext, CancelOrder.class);
                    intent.putExtra("id", ""+orders.getId());
                    intent.putExtra("orderID", ""+orders.getOrdId());
                    intent.putExtra("orderCancelAble", ""+orders.getIscancelable());
                    intent.putExtra("orderStatusEng", ""+orders.getOrderStatusEn());
                    mContext.startActivity(intent);
                }
                else{
                    final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
                    if (companySetting!=null){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.st_assistance) +" "+ companySetting.helpLine+" "+
                                mContext.getResources().getString(R.string.st_assistance1), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void broadcastData(String id,String fav) {
        Intent intent = new Intent("data_action_order_change");
        intent.putExtra("onOrderChange", "" + "changedFav");
        intent.putExtra("orderId",""+id);
        intent.putExtra("favType",""+fav);
        mContext.sendBroadcast(intent);
    }

    private void getCancelOrderReasons(final Orders orders) {
        showProgress(true);
        APIManager.getInstance().getCanceOrderReasons(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);

                if (z) {
                    Log.e(""+z,response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<CancelReasons>>() {

                        }.getType();
                        ArrayList<CancelReasons> cancelReasons = gson.fromJson(jsonObject.getJSONArray("result").toString(), listType);
                        openCancelOrderReasonsDialog(cancelReasons,orders);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.mData = ordersList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ActiveOrderHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCancel, imageViewEdit, imageViewFavourite,imageViewReorder, imageView2;
        TextView tvOrderID,tvStatus,tvDate,tvPrice,tv_stc_qitaf_points;
        private View reOrderView,editView, cancelView;
        private ConstraintLayout mainOLL;


        public ActiveOrderHolder(@NonNull View itemView, List<Orders> mData) {
            super(itemView);
            imageViewCancel =itemView.findViewById(R.id.imageViewCancel);
            imageView2 =itemView.findViewById(R.id.imageView2);
            tv_stc_qitaf_points=itemView.findViewById(R.id.tv_stc_qitaf_points);
            imageViewEdit =itemView.findViewById(R.id.imageView);
            imageViewFavourite =itemView.findViewById(R.id.imageViewFavourite);
            tvOrderID=itemView.findViewById(R.id.layout_orders_tvOrderID);
            mainOLL=itemView.findViewById(R.id.mainOLL);
            tvStatus=itemView.findViewById(R.id.layout_orders_tvStatus);
            tvDate=itemView.findViewById(R.id.layout_orders_tvDate);
            tvPrice=itemView.findViewById(R.id.layout_orders_tv_price);
            imageViewReorder =itemView.findViewById(R.id.imageViewReorder);


            cancelView =itemView.findViewById(R.id.cancelView);
            editView =itemView.findViewById(R.id.editView);
            reOrderView =itemView.findViewById(R.id.reOrderView);
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

    private void openCancelOrderReasonsDialog(ArrayList<CancelReasons> cancelReasons,final Orders orders) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_with_recyclerview);


        TextView title = (TextView) dialog.findViewById(R.id.title);
        RadioButton radio_refund_to_wallet = dialog.findViewById(R.id.radio_refund_to_wallet);
        RadioButton radio_refund_to_bank = dialog.findViewById(R.id.radio_refund_to_bank);
        title.setText(mContext.getResources().getString(R.string.order_cancellation_reasons));
        RecyclerView optionsList = dialog.findViewById(R.id.options_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        optionsList.setLayoutManager(manager);
        optionsList.setHasFixedSize(true);

        final CancelReasonsAdapter adapter = new CancelReasonsAdapter(cancelReasons);
        optionsList.setAdapter(adapter);
        optionsList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        Button ok = dialog.findViewById(R.id.okBtn);
        final RadioGroup radioRefundGroup = (RadioGroup) dialog.findViewById(R.id.radioRefundGroup);
        Button cancel = dialog.findViewById(R.id.cancelBtn);


        dialog.setCancelable(false);

        if (orders.getIscancelable().equals("3")) {
            radioRefundGroup.setVisibility(View.VISIBLE);
            radio_refund_to_wallet.setVisibility(View.VISIBLE);
            radio_refund_to_bank.setVisibility(View.GONE);
            radio_refund_to_wallet.setChecked(true);
        }else if (orders.getIscancelable().equals("4")){
            radioRefundGroup.setVisibility(View.VISIBLE);
            radio_refund_to_wallet.setVisibility(View.GONE);
            radio_refund_to_bank.setVisibility(View.VISIBLE);
            radio_refund_to_bank.setChecked(true);

        }else if (orders.getIscancelable().equals("5")){
            radioRefundGroup.setVisibility(View.VISIBLE);
        }else {
            radioRefundGroup.setVisibility(View.GONE);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refund_to_wallet = "";
                String refund_to_bank = "";
                if (orders.getIscancelable().equals("3")||
                        orders.getIscancelable().equals("4")||
                        orders.getIscancelable().equals("5")) {
                    RadioButton radioButton;
                    // get selected radio button from radioGroup
                    int selectedId = radioRefundGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioButton = (RadioButton) dialog.findViewById(selectedId);
                    if (radioButton.getText().toString().toLowerCase().equals(mContext.getResources().getString(R.string.st_refund_to_wallet).toLowerCase())) {
                        refund_to_wallet = "1";
                    } else {
                        refund_to_bank = "1";
                    }

                    if (adapter.getCheckedCancelReason() != null) {
                        postCancelOrderReason(adapter.getCheckedCancelReason().getId(), orders.getId(), refund_to_wallet, refund_to_bank);
                        dialog.dismiss();
                    }

                }else {
                if (adapter.getCheckedCancelReason() != null) {
                    postCancelOrderReason(adapter.getCheckedCancelReason().getId(),orders.getId(),refund_to_wallet,refund_to_bank);
                    Map<String, Object> eventValue = new HashMap<String, Object>();
                    eventValue.put("Order status",""+""+orders.getOrderStatusEn());
                    eventValue.put("reason of cancellation",""+adapter.getCheckedCancelReason().getReasonEn());
                    eventValue.put("canceled by","user");
//                    CleverTapAPI.getDefaultInstance(mContext).pushEvent("Cancel Order:",eventValue);
                    dialog.dismiss();
                }
              }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void postCancelOrderReason(String reasonId,final String id, String refund_to_wallet, String refund_to_bank) {
        showProgress(true);
        User user=DatabaseManager.getInstance().getFirstOfClass(User.class);
        APIManager.getInstance().postCancelOrderReason(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        switch (jsonObject.getString("result")) {
                            case "true":
                              Intent intent=new Intent(mContext, CanceledOrder.class);
                              mContext.startActivity(intent);
                                break;
                            case "false":
                                showAlertDialog(mContext.getString(R.string.cancel_order_failed_title),mContext.getString(R.string.cancel_order_failed_status));
                                break;
                            case "pending":
                                showAlertDialog(mContext.getString(R.string.cancel_order_pending_title),mContext.getString(R.string.cancel_order_pending_status));
                                break;
                        }
                        //broadcastData("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new CancelOrderRequest(user.userId, id, reasonId,refund_to_wallet,refund_to_bank));
    }

    private void showAlertDialog(final String title, String msg) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView txtTitle = dialog.findViewById(R.id.title);
        TextView message = (TextView) dialog.findViewById(R.id.message);
        txtTitle.setText(title);
        message.setText(msg);
        Button ok = dialog.findViewById(R.id.okBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        cancel.setVisibility(View.GONE);
        dialog.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals(mContext.getString(R.string.cancel_order_success_title))) {

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getOrderDetails(String clientID, final String orderID){

        showProgress(true);
        APIManager.getInstance().getOrderDetails(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.e("test", " z = [" + z + "], response = [" + response + "]");
                showProgress(false);
                if (z) {
                    try {
                        YourOrderDetail orderDetails = new Gson().fromJson(response, YourOrderDetail.class);
                    List<OrderDetail> orderDetailList = orderDetails.getOrder().getOrderDetail();
                        Cart.emptyCart();

                        List<String> images = new ArrayList<>();
                        for (OrderDetail orderDetail : orderDetailList) {
                            ProductByLocation product = new ProductByLocation();
                            product.setId(orderDetail.getDishId());
                            product.setNameAr(orderDetail.getDishTitleAr());
                            product.setNameEn(orderDetail.getDishTitleEn());
                            product.setPrice(orderDetail.getDishPrice());
                            product.setPrice_vat(orderDetail.getPrice_vat());
                            images.add(orderDetail.getDishImage());
                            product.setImg(images);
                            if (orderDetail.getProd_curr_status().equals("1")
                                    && (orderDetail.getFreeGoods().equals("0") || orderDetail.getFreeGoods().equals(""))
                                    && !orderDetail.getDishPrice().equals("0.00") && !orderDetail.getOrdCount().equals("0"))
                                Cart.addToCartReorder(product, -1, Integer.parseInt(orderDetail.getOrdCount()),orderDetail.getWeight());
                        }
                        Intent intent = new Intent(mContext, CheckOutActivity.class);
                        activity.startActivityForResult(intent, ORDERS_SCREEN);
                        ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                        if (ProductsSingleton.getInstance().getProductByLocations()!=null){
                            updateCart();
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }, new OrderRequest(clientID,orderID,""));

    }

    private void updateCart(){
        List<Cart> cartList;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        for(int j=0;j<cartList.size();j++){
            boolean cartCheck = true;
            for (int k = 0; k < ProductsSingleton.getInstance().getProductByLocations().size(); k++) {
                Log.d("cartitemcount",  ""+ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString()+","+cartList.get(j).productId.toString());

                if (ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString().equals(cartList.get(j).productId.toString())) {
                    Log.d("cartitemcount", cartList.get(j).count + "");
                    Cart.addMultipleItem(ProductsSingleton.getInstance().getProductByLocations().get(k).getId(),cartList.get(j).count,ProductsSingleton.getInstance().getProductByLocations().get(k));
                    cartCheck = false;
                }
            }
            if (cartCheck)
            {
                Log.d("cartitemcount",  "....."+cartCheck);
                Cart.deleteProductFromCart(cartList.get(j).productId);
            }
        }


        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null){
            if (user.hide_price.equals("1")){
                ProductsSingleton.getInstance().getTv_cartamount().setVisibility(View.GONE);
            }
        }
    }

}
