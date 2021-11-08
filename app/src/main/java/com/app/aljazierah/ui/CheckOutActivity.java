package com.app.aljazierah.ui;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.appsflyer.AFInAppEventParameterName;
//import com.appsflyer.AFInAppEventType;
//import com.appsflyer.AppsFlyerLib;
import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.BanksAdapter;
import com.app.aljazierah.adapter.CartRecyclerAdapter;
import com.app.aljazierah.adapter.PromotionGroupAdapter;
import com.app.aljazierah.adapter.SelectPaymentMethodAdapter;
import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.CartParamResponse.BanksModel;
import com.app.aljazierah.object.CartParamResponse.CartParamResponse;
import com.app.aljazierah.object.CartParamResponse.LatestOrder;
import com.app.aljazierah.object.CartParamResponse.OrderItems;
import com.app.aljazierah.object.CartParamResponse.PaymentMethod;
import com.app.aljazierah.object.Cart_Params;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.PlaceOrder;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.Promotions.Foc_prod;
import com.app.aljazierah.object.YourOrderDetails.OrderDetail;
import com.app.aljazierah.object.YourOrderDetails.YourOrderDetail;
import com.app.aljazierah.object.addressData;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.CheckStcRequest;
import com.app.aljazierah.object.request.OrderRequest;
import com.app.aljazierah.object.request.PromocodeRequest;
import com.app.aljazierah.object.request.TokenRequest;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.app.aljazierah.utils.recycler.DividerItemDecoration;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.app.aljazierah.utils.Constants.AMOUNT;
import static com.app.aljazierah.utils.Constants.validate_promocode;


public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener,
        CartRecyclerAdapter.ICategory, CartRecyclerAdapter.DeleteDialog, CompoundButton.OnCheckedChangeListener, BanksAdapter.Clicks {
    private List<BanksModel> banksModelList = new ArrayList<>();
    public static Activity orderdetailactivity;
    public final int REQUEST_FOR_ADDRESS = 102;
    public boolean isArabic;
    public RecyclerView rvcartProduct, banksRV;
    public String AreaId, cityId = "";
    //    public TextView tvBasketBagde;
//    public View rlBadgeView;
    public TextView textview_addpromocode;
    public final String TAG = "OrderDetailedUpdated";
    public User user;
    double totalprice = 0;
    //private String promocode_valuefromserver="",promocode_quantityfromserver="";
    com.app.aljazierah.object.addressData addressData;
    public CartParamResponse cartParamResponse;
    private String preffered_time_id = "3";
    private TextView textview_locations, textview_location;
    public TextView tvPaymentType, tvChangePaymentMethod, tvChangeAddress, tv_free_group_promotion;
    public EditText et_promocode;
    public RadioButton rbMorning, rbEvening, deliveryTimeView_rbanytime;
    public RadioButton rbNone, rbWeekly, rbMonthly;
    public String paymentype = "";
    public ConstraintLayout rlStepperLayer;
    public View refferalview;
    public boolean getIntentValue;
    public String vat_value_server = "", vat_value = "", min_quantity = "";
    public TextView textview_orderitemsvalue, tvShippingCharges, tvWalletDiscountValue, textview_vatvalue, textview_walletvalue, textview_totalvalue;
    // public AppCompatCheckBox rb_wallet;
    public double promoCode_value = 0.0;
    public double promoCode_valueS = 0.0;
    public double delivery_fee = 0.0;
    public String after_vat = "0";
    public double wallet_discount = 0.0;
    //public int promocode_quantity ;
    public TextView tvPromocodeValueB;
    public TextView tvPromocodeValueA;
    public View layoutPromocodeB;
    public View layoutPromocodeA;
    public View layoutWalletDiscount;
    // public double promo_min_value;
    public JSONArray jsonArray_cart;
    public String recurring = "";
    public Button btn_checkout;
    List<Cart> cartList = new ArrayList<>();
    List<PlaceOrder.Cart> cartListPlaceOrder = new ArrayList<>();
    EditText et_refferal;
    PlaceOrder placeOrder;
    Dialog payment_Dialog, confirmDialog;
    public TextView deliveryAddressView_tvAddress;
    private ProgressDialog mProgressDialog;
    private View promoView_layout, shippingLL;
    private String promocode_type = "";
    private String stc_promo_code = "";
    private ArrayList<String> allowed_products = new ArrayList<>();
    private String discount_level = "", delivery_formula = "";
    public Dialog dialog;
    private boolean edit_order;
    TextView orderdetailUpdated_tvOrderID;
    private View stc_layout;
    private EditText et_otp;
    private TextView textview_addotp;
    private AppCompatCheckBox rb_stc;
    private View view_stcline;
    private TextView textview_stctitle, beforeVATTV, afterVATTV;

    private String stc_tamayouz_min_qty = "0";
    private String address_id = "";
    //private double total_discountitems,total_items_without_discount,total_items_price;
    ArrayList<Foc_prod> foc_prodList = new ArrayList<>();
    int add_on = 0;
    List<PlaceOrder.CartPromotion> cartListPromotion = new ArrayList<>();
    private String promoCode = "";
    private String checkout_time = "";
    private View promoBelowLine, otpBelowLine;
    private CartRecyclerAdapter productAdapter;
    private BanksAdapter banksAdapter;
    private ImageView imageViewBack;

    public AppCompatCheckBox rb_wallet;
    private String bankID = "1";
    private LinearLayout beforeVATLL, afterVATLL;


    final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        isArabic = AppController.setLocale();
        orderdetailactivity = this;
        setContentView(R.layout.activity_checkout);


        getIntentValue = getIntent().getBooleanExtra("order", false);
        edit_order = getIntent().getBooleanExtra("edit_order", false);
        initViews();


        if (edit_order) {
            User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
            getOrderDetails(user.userId, getIntent().getStringExtra("order_id"));
        } else {
            if (user != null) {
                setPromotion();
                getCartParams();
            } else {
                finish();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        checkout_time = "" + currentDateandTime;
        Log.d("cdscdscfsdcfds", currentDateandTime);


    }

    public void showOpenOrdersDialog(String ordersList) {
        dialog = new Dialog(CheckOutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView title = dialog.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.open_orders));
        TextView message = dialog.findViewById(R.id.message);
        String msg = getResources().getString(R.string.open_order_dialog_message);
        msg += "\n";
        msg += getResources().getString(R.string.current_orders);
        if (ordersList.length() > 20) {
            msg += ordersList.substring(0, 20);
            msg += "...";
        } else {
            msg += ordersList;
        }
        message.setText(msg);

        Button ok = dialog.findViewById(R.id.okBtn);
        ok.setText(getResources().getString(R.string.yes));
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        cancel.setText(getResources().getString(R.string.go_to_orders));
        dialog.setCancelable(true);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(3);

                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void initViews() {
        updateCart();
        setToolbar();
        Button btnAddItems = findViewById(R.id.activity_order_detailed_btn_additems);
        Button btnCheckout = findViewById(R.id.activity_order_detailed_btn_checkout);
        rvcartProduct = findViewById(R.id.orderdetailItemsList);
        banksRV = findViewById(R.id.banksRV);
        View locationview = findViewById(R.id.locationview);

        View orderdetail_llOrderID = findViewById(R.id.orderdetail_llOrderID);
//        tvBasketBagde = findViewById(R.id.basket_badge);
//        rlBadgeView = findViewById(R.id.badge_viewFH);


//        couponsTV = findViewById(R.id.couponsTV);
//        couponsSwitch = findViewById(R.id.couponsSwitch);
//        walletSwitch = findViewById(R.id.walletSwitch);
//        walletTV = findViewById(R.id.walletTV);
//        couponsLLMain = findViewById(R.id.couponsLLMain);
//        couponsLL = findViewById(R.id.couponsLL);
//        walletLL = findViewById(R.id.walletLL);
        imageViewBack = findViewById(R.id.imageViewBackButton);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        textview_locations = findViewById(R.id.textview_locations);
        textview_location = findViewById(R.id.textview_location);
        textview_location.setVisibility(View.GONE);
        textview_locations.setVisibility(View.GONE);
        refferalview = findViewById(R.id.refferalview);
        promoBelowLine = findViewById(R.id.promoBelowLine);
        otpBelowLine = findViewById(R.id.otpBelowLine);
        layoutPromocodeB = findViewById(R.id.layoutPromocodeB);
        layoutPromocodeA = findViewById(R.id.layoutPromocodeA);
        layoutWalletDiscount = findViewById(R.id.layoutWalletDiscount);
        layoutPromocodeB.setVisibility(View.GONE);
        layoutPromocodeA.setVisibility(View.GONE);
        layoutWalletDiscount.setVisibility(View.GONE);
        et_promocode = findViewById(R.id.editTextPromocode);
        tvPromocodeValueB = findViewById(R.id.tvPromocodeValueB);
        tvPromocodeValueA = findViewById(R.id.tvPromocodeValueA);
        btn_checkout = findViewById(R.id.activity_order_detailed_btn_checkout);
        textview_addpromocode = findViewById(R.id.tvAddpromoCode);
        deliveryAddressView_tvAddress = findViewById(R.id.tvDeliveryAddress);
        orderdetailUpdated_tvOrderID = findViewById(R.id.tvOrderID);
        et_refferal = findViewById(R.id.et_refferal);
        promoView_layout = findViewById(R.id.promoView_layout);
        tv_free_group_promotion = findViewById(R.id.tv_free_group_promotion);

        textview_stctitle = findViewById(R.id.textview_stctitle);
        view_stcline = findViewById(R.id.view_stcline);
        stc_layout = findViewById(R.id.stc_layout);
        et_otp = findViewById(R.id.et_otp);
        textview_addotp = findViewById(R.id.textview_addotp);

        beforeVATLL = findViewById(R.id.beforeVATLL);
        afterVATLL = findViewById(R.id.afterVATLL);
        beforeVATTV = findViewById(R.id.beforeVATTV);
        afterVATTV = findViewById(R.id.afterVATTV);

        beforeVATLL.setVisibility(View.GONE);
        afterVATLL.setVisibility(View.GONE);


        rb_stc = findViewById(R.id.rb_stc);
        textview_addotp.setOnClickListener(this);
        tv_free_group_promotion.setOnClickListener(this);


//        rlBadgeView.setVisibility(View.GONE);
        stc_layout.setVisibility(View.GONE);
        promoBelowLine.setVisibility(View.GONE);

        view_stcline.setVisibility(View.GONE);
        textview_stctitle.setVisibility(View.GONE);
        View orderdetailcircleview = findViewById(R.id.orderdetailcircleview);
        btnAddItems.setOnClickListener(this);
        btnCheckout.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);
        textview_addpromocode.setOnClickListener(this);
        AppController.getInstance().setActivity(this);
        if (!getIntentValue) {
            locationview.setVisibility(View.GONE);
            orderdetailcircleview.setVisibility(View.GONE);
            orderdetail_llOrderID.setVisibility(View.GONE);
        }
        if (edit_order) {
            orderdetail_llOrderID.setVisibility(View.VISIBLE);
        } else {
            orderdetail_llOrderID.setVisibility(View.GONE);
        }





        tvPaymentType = findViewById(R.id.tvPaymentType);
        tvChangePaymentMethod = findViewById(R.id.paymentOptionView_tvChange);
        tvChangePaymentMethod.setOnClickListener(this);

        tvChangeAddress = findViewById(R.id.deliveryAddressView_tvChangeAddress);
        tv_free_group_promotion = findViewById(R.id.tv_free_group_promotion);

        rbMorning = findViewById(R.id.radioButtonMorning);
        shippingLL = findViewById(R.id.shippingLL);
        rbEvening = findViewById(R.id.radioButtonEvening);
        rbNone = findViewById(R.id.radioButtonScheduleNone);
        rbWeekly = findViewById(R.id.radioButtonScheduleWeekly);
        rbMonthly = findViewById(R.id.radioButtonScheduleEveryMonth);
        deliveryTimeView_rbanytime = findViewById(R.id.radioButtonAnytime);
        rlStepperLayer = findViewById(R.id.orderdetailcircleview);

        textview_orderitemsvalue = findViewById(R.id.textview_orderitemsvalue);
        tvShippingCharges = findViewById(R.id.tvShippingCharges);
        tvWalletDiscountValue = findViewById(R.id.tvWalletDiscountValue);
        textview_vatvalue = findViewById(R.id.textview_vatvalue);
        textview_walletvalue = findViewById(R.id.textview_walletvalue);
        textview_totalvalue = findViewById(R.id.textview_totalvalue);
        rb_wallet = findViewById(R.id.radioButtonWallet);

        rlStepperLayer.setVisibility(View.GONE);
        rbMorning.setOnCheckedChangeListener(this);
        rbEvening.setOnCheckedChangeListener(this);
        deliveryTimeView_rbanytime.setOnCheckedChangeListener(this);
        rbNone.setOnCheckedChangeListener(this);
        rbMonthly.setOnCheckedChangeListener(this);
        rbWeekly.setOnCheckedChangeListener(this);
        rb_wallet.setOnCheckedChangeListener(this);
//        couponsSwitch.setOnCheckedChangeListener(this);
        rb_stc.setOnCheckedChangeListener(this);
        et_refferal.setText("");
        rbMorning.setChecked(false);
        rbEvening.setChecked(false);
        deliveryTimeView_rbanytime.setChecked(true);
        rbNone.setChecked(true);
        rbMonthly.setChecked(false);
        rbWeekly.setChecked(false);
        refferalview.setVisibility(View.GONE);
        otpBelowLine.setVisibility(View.GONE);
        setAddress();
        View addressview = findViewById(R.id.addressview);
        addressview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAddressAlert();

            }
        });

        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.add_address.equals("0")) {
                // tvcreditAmount.setVisibility(View.VISIBLE);
                findViewById(R.id.paymentDetailView).setVisibility(View.GONE);
                tvChangePaymentMethod.setVisibility(View.GONE);

            } else {
                // tvcreditAmount.setVisibility(View.GONE);
                findViewById(R.id.paymentDetailView).setVisibility(View.VISIBLE);
            }
        }

    }

    private void updateCart() {
        List<Cart> cartList;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        if (ProductsSingleton.getInstance().getProductByLocations() != null) {
            for (int j = 0; j < cartList.size(); j++) {
                boolean cartCheck = true;
                Log.d("*size", "" + ProductsSingleton.getInstance().getProductByLocations().size());
                for (int k = 0; k < ProductsSingleton.getInstance().getProductByLocations().size(); k++) {
                    if (ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString().equals(cartList.get(j).productId.toString())) {
                        Cart.addMultipleItem(ProductsSingleton.getInstance().getProductByLocations().get(k).getId(), cartList.get(j).count, ProductsSingleton.getInstance().getProductByLocations().get(k));
                        cartCheck = false;
                    }
                }
                if (cartCheck) {
                    Cart.deleteProductFromCart(cartList.get(j).productId);
                }
            }
        }
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.hide_price.equals("1")) {
                ProductsSingleton.getInstance().getTv_cartamount().setVisibility(View.GONE);
            }
        }
    }

    private void ChangeAddressAlert() {
        dialog = new Dialog(CheckOutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.change_alert_dialog);
        dialog.setCancelable(true);

        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        Button okBtn = dialog.findViewById(R.id.okBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

//        title.setTypeface(Typeface.createFromAsset(getAssets(), "poppins_bold.ttf"), Typeface.NORMAL);
//        message.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//        okBtn.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
//        cancelBtn.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);

        dialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent adressintent = new Intent(CheckOutActivity.this, ChooseAddressActivity.class);
                startActivityForResult(adressintent, REQUEST_FOR_ADDRESS);
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

    private void setPromotion() {
        tv_free_group_promotion.setVisibility(View.GONE);
        foc_prodList = new ArrayList<>();
        int promoGroupCount = 0;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);

        if (cartList.size() == 0)
            finish();

        boolean check = true;
        if (ProductsSingleton.getInstance().getPromotionList() != null &&
                ProductsSingleton.getInstance().getPromotionList().size() > 0) {
            for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().size(); i++) {

                if (ProductsSingleton.getInstance().getPromotionList().get(i).getPromotion_level().equals("group")) {
                    for (int j = 0; j < cartList.size(); j++) {

                        if (ProductsSingleton.getInstance().getPromotionList().get(i).getProduct_id_list().size() == 0) {
                            promoGroupCount = promoGroupCount + cartList.get(j).count;
                        } else {

                            for (int k = 0; k < ProductsSingleton.getInstance().getPromotionList().get(i).getProduct_id_list().size(); k++) {
                                if (ProductsSingleton.getInstance().getPromotionList().get(i).getProduct_id_list().get(k).equals(cartList.get(j).productId)) {
                                    promoGroupCount = promoGroupCount + cartList.get(j).count;
                                }
                            }
                        }
                    }

                    Log.d("promocount", promoGroupCount + "");
                    add_on = Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(i).getAddOn()) * (promoGroupCount / Integer.parseInt(ProductsSingleton.getInstance().getPromotionList().get(i).getQuantity()));

                    if (promoGroupCount >= Double.parseDouble(ProductsSingleton.getInstance().getPromotionList().get(i).getQuantity())) {
                        tv_free_group_promotion.setVisibility(View.VISIBLE);
                        tv_free_group_promotion.setText(getResources().getString(R.string.st_promotion_dialouge_title) + " " + add_on);

                        for (int l = 0; l < ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().size(); l++) {
                            if (l == 0) {
                                Foc_prod foc_prod = new Foc_prod();
                                foc_prod.setCount(add_on);
                                foc_prod.setId(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getId());
                                foc_prod.setName_ar(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getName_ar());
                                foc_prod.setName_en(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getName_en());
                                this.foc_prodList.add(foc_prod);
                            } else {
                                Foc_prod foc_prod = new Foc_prod();
                                foc_prod.setCount(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getCount());
                                foc_prod.setId(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getId());
                                foc_prod.setName_ar(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getName_ar());
                                foc_prod.setName_en(ProductsSingleton.getInstance().getPromotionList().get(i).getFoc_prod().get(l).getName_en());
                                this.foc_prodList.add(foc_prod);
                            }
                        }
                    }
                }

            }
        }

        cartListPromotion.clear();
        for (int i = 0; i < foc_prodList.size(); i++) {
            PlaceOrder.CartPromotion cartPromotion = new PlaceOrder.CartPromotion();
            if (foc_prodList.get(i).getCount() > 0) {
                cartPromotion.setCount(foc_prodList.get(i).getCount());
                cartPromotion.setId(foc_prodList.get(i).getId());
                cartListPromotion.add(cartPromotion);
            }
        }

        InitCart();
    }

    private void getOrderDetails(String clientID, final String orderID) {
        showProgress(true);
        APIManager.getInstance().getOrderDetails(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.e("test", " z = [" + z + "], response = [" + response + "]");
                showProgress(false);
                if (z) {
                    try {
                        YourOrderDetail orderDetails = new Gson().fromJson(response, YourOrderDetail.class);
                        List<OrderDetail> orderDetailList = new ArrayList<>();
                        orderDetailList = orderDetails.getOrder().getOrderDetail();
                        orderdetailUpdated_tvOrderID.setText(getResources().getString(R.string.Order_id) +
                                orderDetails.getOrder().getOrdId());
                        setinfoToViews(orderDetails);
                        Cart.emptyCart();

                        Cart.emptyCart();
                        for (OrderDetail orderDetail : orderDetailList) {
                            ProductByLocation product = new ProductByLocation();
                            product.setId(orderDetail.getDishId());
                            product.setNameAr(orderDetail.getDishTitleAr());
                            product.setNameEn(orderDetail.getDishTitleEn());
                            product.setPrice(orderDetail.getDishPrice());
                            product.setPrice_vat(orderDetail.getPrice_vat());
//                            product.setImg(orderDetail.getDishImage());
                            if (orderDetail.getProd_curr_status().equals("1")
                                    && (orderDetail.getFreeGoods().equals("0") || orderDetail.getFreeGoods().equals(""))
                                    && !orderDetail.getDishPrice().equals("0.00") && !orderDetail.getOrdCount().equals("0"))
                                Cart.addToCart(product, -1, Integer.parseInt(orderDetail.getOrdCount()));
                        }

                        updateCart();
                        setPromotion();
                        getCartParams();


                    } catch (Exception e) {
                        Log.e("OrderDetailexception", e.toString());
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CheckOutActivity.this, "Something went wrong ty again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new OrderRequest(clientID, orderID, ""));

    }

    private void setinfoToViews(YourOrderDetail yourOrderDetail) {
        deliveryAddressView_tvAddress.setText(yourOrderDetail.getOrder().getAddress());
        if (UserSession.getInstance().getUserLanguage().equals("ar")) {
            tvPaymentType.setText(yourOrderDetail.getOrder().getPaymentTypeAr());
        } else {
            tvPaymentType.setText(yourOrderDetail.getOrder().getPaymentTypeEn());
        }

        if (yourOrderDetail.getOrder().getPreferedTime().equals("1")) {
            rbMorning.setChecked(true);
            rbEvening.setChecked(false);
            deliveryTimeView_rbanytime.setChecked(false);

        } else if (yourOrderDetail.getOrder().getPreferedTime().equals("2")) {
            rbMorning.setChecked(false);
            rbEvening.setChecked(true);
            deliveryTimeView_rbanytime.setChecked(false);


        } else {
            rbMorning.setChecked(false);
            rbEvening.setChecked(false);
            deliveryTimeView_rbanytime.setChecked(true);

        }


        if (yourOrderDetail.getOrder().getRecurring().equals("0")) {
            rbNone.setChecked(true);
            rbMonthly.setChecked(false);
            rbWeekly.setChecked(false);
        } else if (yourOrderDetail.getOrder().getRecurring().equals("2")) {
            rbNone.setChecked(false);
            rbMonthly.setChecked(false);
            rbWeekly.setChecked(true);
        } else if (yourOrderDetail.getOrder().getRecurring().equals("3")) {
            rbNone.setChecked(false);
            rbMonthly.setChecked(true);
            rbWeekly.setChecked(false);
        }

    }

    private CartParamResponse getCartParams() {
        showProgress(true);
        setArray();
        Log.d("getcartperameter", new Gson().toJson(new Cart_Params(AreaId, cityId, address_id, Integer.parseInt(user.userId), UserSession.getInstance().getToken(), cartListPlaceOrder)));
        APIManager.getInstance().getCartParemeters(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.d("responseGETCART", "onResult: ApiResponse: " + response);
                if (z) {

                    if (response.contains("Access denied") || response.contains("Invalid Key!")) {
                        CoreManager.getInstance().removeUserData();
                        Toast.makeText(CheckOutActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckOutActivity.this, SplashScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    } else {
                        try {
                            showProgress(false);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("is_valid_order").equals("0")) {
                                if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                                    alertDialogError(jsonObject.getString("Message_ar"));
                                } else {
                                    alertDialogError(jsonObject.getString("Message_en"));
                                }
                            } else {
                                cartParamResponse = new Gson().fromJson(response, CartParamResponse.class);
                                setTokenToServer();
                                setViewsfromCartParams(cartParamResponse);
                                getSumItemsWithCoupons();
                                if (!jsonObject.optString("promo_object").equals("") && !jsonObject.getString("default_promo").equals("")) {
                                    setPromocoderesponse(jsonObject.getJSONObject("promo_object"));
                                    et_promocode.setText("" + jsonObject.getString("default_promo"));
                                    promoCode = et_promocode.getText().toString();
                                } else if (!jsonObject.getString("default_promo").equals("")) {
                                    et_promocode.setText(cartParamResponse.getDefault_promo());
                                    promoCode = et_promocode.getText().toString();
                                    applypromocode();
                                }
                                if (!new JSONObject(response).getString("duplicate_orders").equals(""))
                                    showOpenOrdersDialog(new JSONObject(response).getString("duplicate_orders"));


                                /* if (cartParamResponse.getEnable_wallet().equals("1") *//*&& cartParamResponse.getEnable_Fcoupon().equals("1")*//*) {
//                                    couponsLLMain.setVisibility(View.VISIBLE);
//                                    walletLL.setVisibility(View.VISIBLE);
//                                    couponsLL.setVisibility(View.VISIBLE);

                                } else if (cartParamResponse.getEnable_wallet().equals("1")) {
                                    walletLL.setVisibility(View.VISIBLE);
//                                    couponsLLMain.setVisibility(View.VISIBLE);

                                }*//* else if (cartParamResponse.getEnable_coupon().equals("1")) {
//                                    couponsLL.setVisibility(View.VISIBLE);
//                                    couponsLLMain.setVisibility(View.VISIBLE);
                                } */
                                /*else {
//                                    couponsLLMain.setVisibility(View.GONE);
                                    walletSwitch.setChecked(false);
//                                    couponsSwitch.setChecked(false);

                                }*/

                            }
                        } catch (Exception e) {
                            Log.e("Ex_cartitems", e.toString());
                            e.printStackTrace();
                        }
                    }
                } else {
                    showProgress(false);
                    Toast.makeText(CheckOutActivity.this, "Something went wrong plz try again", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }, new Cart_Params(AreaId, cityId, address_id, Integer.parseInt(user.userId), UserSession.getInstance().getToken(), cartListPlaceOrder));

        return cartParamResponse;
    }

    private void setTokenToServer() {
        DatabaseReference mDatabase;
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUserid(user.userId);
        tokenRequest.setToken(UserSession.getInstance().getToken());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Token");

        myRef.setValue(tokenRequest);
    }

    @SuppressLint("SetTextI18n")
    private void setViewsfromCartParams(CartParamResponse cartParamResponse) {
        //region Check DeliverySlots
        for (int i = 0; i < cartParamResponse.getDeliverySlots().size(); i++) {
            if (cartParamResponse.getDeliverySlots().get(i).getTitleEn().equalsIgnoreCase("Morning") &&
                    cartParamResponse.getDeliverySlots().get(i).getStatus().equals("1")) {
                rbMorning.setVisibility(View.VISIBLE);

            }

            if (cartParamResponse.getDeliverySlots().get(i).getTitleEn().equalsIgnoreCase("Evening") &&
                    cartParamResponse.getDeliverySlots().get(i).getStatus().equals("1")) {
                rbEvening.setVisibility(View.VISIBLE);

            }

            if (cartParamResponse.getDeliverySlots().get(i).getTitleEn().equalsIgnoreCase("Anytime") &&
                    cartParamResponse.getDeliverySlots().get(i).getStatus().equals("1")) {
                deliveryTimeView_rbanytime.setVisibility(View.VISIBLE);
            }
        }


        if (Double.parseDouble(cartParamResponse.getClientWalletBalance()) < 1) {
            rb_wallet.setVisibility(View.GONE);
        } else if (Double.parseDouble(cartParamResponse.getClientWalletBalance()) > 0) {
            rb_wallet.setVisibility(View.VISIBLE);
            rb_wallet.setChecked(true);
        } else if (Cart.getTotalPrice() <= Double.parseDouble(cartParamResponse.getClientWalletBalance())) {
            rb_wallet.setVisibility(View.VISIBLE);
            rb_wallet.setChecked(true);
        }

//        couponsLLMain.setVisibility(View.VISIBLE);
     /*   walletTV.setText(getResources().getString(R.string.st_usewallet) + "  (" + cartParamResponse.getClientWalletBalance() + "" + ")");

        if (Double.parseDouble(cartParamResponse.getClientWalletBalance()) < 1) {
            walletSwitch.setEnabled(false);
            walletSwitch.setAlpha(0.3f);
            walletTV.setAlpha(0.3f);

        } else if (Double.parseDouble(cartParamResponse.getClientWalletBalance()) > 0 && cartParamResponse.getEnable_wallet().equals("1")) {
            walletSwitch.setEnabled(true);
            walletSwitch.setAlpha(1f);
            walletTV.setAlpha(1f);
            walletSwitch.setChecked(true);

        }*/


        if (cartParamResponse.getWallet_discount().equals("") || cartParamResponse.getWallet_discount() == null) {
            layoutWalletDiscount.setVisibility(View.GONE);
        } else if (Double.parseDouble(cartParamResponse.getWallet_discount()) > 0) {
            rb_wallet.setVisibility(View.VISIBLE);
            rb_wallet.setChecked(true);
            layoutWalletDiscount.setVisibility(View.VISIBLE);
            wallet_discount = Double.parseDouble(cartParamResponse.getWallet_discount());
        }


        if (cartParamResponse.getShowReferral().equals("1")) {
            refferalview.setVisibility(View.VISIBLE);
            otpBelowLine.setVisibility(View.VISIBLE);
        } else {
            refferalview.setVisibility(View.GONE);
            otpBelowLine.setVisibility(View.GONE);
        }
        stc_tamayouz_min_qty = cartParamResponse.getStc_tamayouz_min_qty();

        if (Cart.getTotalItems() >= Integer.parseInt(stc_tamayouz_min_qty) && cartParamResponse.getShow_stc_tamayouz().equals("1")) {
            rb_stc.setVisibility(View.VISIBLE);
            //textview_stctitle.setVisibility(View.VISIBLE);
            //view_stcline.setVisibility(View.VISIBLE);
        } else {
            rb_stc.setVisibility(View.GONE);
            textview_stctitle.setVisibility(View.GONE);
            view_stcline.setVisibility(View.GONE);
        }


        for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
            if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().equals("Apple Pay")) {
                cartParamResponse.getPaymentMethods().remove(i);
            }
        }


        if (UserSession.getInstance().getUserLanguage().equals("ar"))
            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleAr());
        else
            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleEn());

        cartParamResponse.getPaymentMethods().get(0).setSelected(true);

        if (!cartParamResponse.getPaymentMethods().get(0).getTitleEn().toLowerCase().equals("cash on delivery"))
            btn_checkout.setText(getResources().getString(R.string.proceed));


//        couponsTV.setText(getResources().getString(R.string.use_coupons) + "  (" + cartParamResponse.getCoupon().getCoupon_qty() + "" + ")");
/*
        if (cartParamResponse.getCoupon().getCoupon_qty().equals("")
                || cartParamResponse.getCoupon().getCoupon_qty() == null
                || cartParamResponse.getCoupon().getCoupon_qty().equals("0")) {
            couponsSwitch.setEnabled(false);
            couponsSwitch.setAlpha(0.3f);
            couponsTV.setAlpha(0.3f);
        } else if (Double.parseDouble(cartParamResponse.getCoupon().getCoupon_qty()) > 0 && cartParamResponse.getEnable_coupon().equals("1")) {
            couponsSwitch.setEnabled(true);
            couponsSwitch.setAlpha(1f);
            couponsSwitch.setChecked(true);
            couponsTV.setAlpha(1f);
        }*/


        setPayment();
        setPaymentServer(tvPaymentType.getText().toString());
        if ( banksModelList.size()>0) {
            banksModelList = cartParamResponse.getBanks();
            banksModelList.get(0).setSelected(true);
            banksAdapter = new BanksAdapter(CheckOutActivity.this, banksModelList, this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(CheckOutActivity.this, 1);
            banksRV.setLayoutManager(gridLayoutManager);
            banksRV.setAdapter(banksAdapter);
        }
    }

    private void openPromotionGroupDialog(final ArrayList<Foc_prod> focProdArrayList, final int add_on) {
        final Dialog dialog = new Dialog(CheckOutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.promotion_group_dialog_with_recyclerview);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.st_promotion_dialouge_title) + " " + add_on);
        RecyclerView promotion_list = dialog.findViewById(R.id.promotion_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        promotion_list.setLayoutManager(manager);
        promotion_list.setHasFixedSize(true);
        final PromotionGroupAdapter adapter = new PromotionGroupAdapter(focProdArrayList, add_on);
        promotion_list.setAdapter(adapter);
        adapter.updateArray(focProdArrayList);
        promotion_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        Button ok = dialog.findViewById(R.id.okBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartListPromotion.clear();
                foc_prodList.clear();
                foc_prodList = adapter.returnFocArray();

                for (int i = 0; i < foc_prodList.size(); i++) {
                    PlaceOrder.CartPromotion cartPromotion = new PlaceOrder.CartPromotion();

                    if (foc_prodList.get(i).getCount() > 0) {
                        cartPromotion.setCount(foc_prodList.get(i).getCount());
                        cartPromotion.setId(foc_prodList.get(i).getId());
                        cartListPromotion.add(cartPromotion);
                    }
                }
                dialog.dismiss();

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

    private void InitCart() {
        vat_value_server = companySetting.vat + "";
        min_quantity = companySetting.minOrder + "";
        productAdapter = new CartRecyclerAdapter(CheckOutActivity.this,
                CheckOutActivity.this, cartList, this, CheckOutActivity.this, CheckOutActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckOutActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvcartProduct.setLayoutManager(linearLayoutManager);
        rvcartProduct.setItemAnimator(new DefaultItemAnimator());
        rvcartProduct.setAdapter(productAdapter);
    }

    private void setToolbar() {
        View toolbar = findViewById(R.id.toolbar);
        TextView title_textview = toolbar.findViewById(R.id.tvToolbarTitleTCA);
        ImageView iv_backbtn = toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(this);
        if (!getIntent().getBooleanExtra("order", false)) {
            if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                title_textview.setText(getResources().getString(R.string.Your_Cart));
            } else {
                title_textview.setText(getResources().getString(R.string.Your_Cart));
            }
        } else {
            if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                title_textview.setText(getResources().getString(R.string.Order_Details));
            } else {
                title_textview.setText(getResources().getString(R.string.Order_Details));
            }
        }


    }

    private void applypromocode() {
        setArray();
        if (et_promocode.getText().toString().equals("")) {
            Toast.makeText(this, "Enter PromoCode", Toast.LENGTH_SHORT).show();
        } else {
//            String isCoupon = "0";

           /* if (couponsSwitch.isChecked()) {
                isCoupon = "1";
            }*/


            Log.d("requestbodyyy", new Gson().toJson(new PromocodeRequest(Integer.parseInt(AreaId),
                    "1", et_promocode.getText().toString(),
                    Cart.getTotalPrice() + "",
                    Integer.parseInt(user.userId), cartListPlaceOrder)));
            showProgress(true);
            APIManager.getInstance().getPromoCode(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {
                    if (z) {
                        try {
                            showProgress(false);
                            JSONObject jsonObject = new JSONObject(response);
                            setPromocoderesponse(jsonObject.getJSONObject("rows"));
                        } catch (Exception e) {
                            showProgress(false);
                            Log.e("Ex_cartitems", e.toString());
                            e.printStackTrace();
                        }
                    } else {
                        showProgress(false);
                    }


                }
            }, new PromocodeRequest(Integer.parseInt(AreaId), "1",
                    et_promocode.getText().toString(),
                    Cart.getTotalPrice() + "",
                    Integer.parseInt(user.userId), cartListPlaceOrder));
        }
    }

    private void setPromocoderesponse(JSONObject promocoderesponse) {
        try {
            if (promocoderesponse.getString("is_promo_valid").equals("1")) {
                textview_addpromocode.setText("" + getResources().getString(R.string.st_remove));

                if (isArabic) {
                    textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_applied_ar));
                } else {
                    textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_applied));
                }


                et_promocode.setEnabled(false);
                validate_promocode = true;
                promoCode = et_promocode.getText().toString();
                stc_layout.setVisibility(View.GONE);
                promoBelowLine.setVisibility(View.GONE);
                et_otp.setText("");
                rb_stc.setVisibility(View.GONE);
                rb_stc.setChecked(false);
                textview_stctitle.setVisibility(View.GONE);
                view_stcline.setVisibility(View.GONE);
                promoView_layout.setVisibility(View.VISIBLE);
                et_promocode.setText(promoCode);

                after_vat = promocoderesponse.optString("after_vat");
                promocode_type = promocoderesponse.getString("type");
                promoCode_valueS = Double.parseDouble(promocoderesponse.getString("value"));

                discount_level = promocoderesponse.getString("discount_level");

                if (discount_level.equals("product")) {
                    JSONArray jsonArray = promocoderesponse.getJSONArray("allowed_products");
                    allowed_products.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        allowed_products.add(jsonArray.getString(i));
                    }
                }
                setPayment();
            } else {
                et_promocode.setEnabled(true);
                et_promocode.setText("");
                textview_addpromocode.setText("" + getResources().getString(R.string.st_apply));
                if (isArabic)
                    textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg_ar));
                else {
                    textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg));
                }
                promoCode_value = 0;
                promoCode_valueS = 0;
                promocode_type = "";
                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                    Toast.makeText(CheckOutActivity.this, "" + promocoderesponse.optString("message_ar"), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CheckOutActivity.this, "" + promocoderesponse.optString("message_en"), Toast.LENGTH_SHORT).show();

            }

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
    }

    private void setOtpresponse(JSONObject jsonObject) {
        try {
            textview_addotp.setText("" + getResources().getString(R.string.st_remove));
            et_otp.setEnabled(false);
            JSONObject promocoderesponse = jsonObject.getJSONObject("rows");
            if (promocoderesponse.getString("is_promo_valid").equals("1")) {
                promocode_type = promocoderesponse.getString("type");
                promoCode_valueS = Double.parseDouble(promocoderesponse.getString("value"));
                stc_promo_code = promocoderesponse.getString("stc_promo_code");
                discount_level = promocoderesponse.getString("discount_level");
                validate_promocode = true;

                if (discount_level.equals("product")) {
                    JSONArray jsonArray = promocoderesponse.getJSONArray("allowed_products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        allowed_products.add(jsonArray.getString(i));
                    }
                }
                setPayment();

            } else {
                et_otp.setEnabled(true);
                et_otp.setText("");
                textview_addotp.setText("" + getResources().getString(R.string.st_apply));
                promoCode_value = 0;
                promoCode_valueS = 0;
                promocode_type = "";
                stc_promo_code = "";
                discount_level = "";

                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                    Toast.makeText(this, promocoderesponse.optString("stc_validation_error_ar"), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, promocoderesponse.optString("stc_validation_error_en"), Toast.LENGTH_SHORT).show();


            }
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
    }

    private void setPlaceOrder() {
        vat_value = "0";
        String stc_otp = "";
        double total_items_price = Double.parseDouble(convertString(String.format("%.2f", (double) (Cart.getTotalPrice()))));
        String check_wallet = "0";

       /* if (walletSwitch.isChecked()) {
            check_wallet = 1;
        } else {
            check_wallet = 0;
        }*/
/*
        if (couponsSwitch.isChecked()) {
            coupons = 1;
        } else {
            coupons = 0;
        }*/


        if (rb_wallet.isChecked()) {
            check_wallet = "1";
        } else {
            check_wallet = "0";
        }

        if (rb_stc.isChecked() && !et_otp.getText().toString().equals("") && validate_promocode) {
            stc_otp = "1";
        } else {
            stc_otp = "0";
        }

        double vat = Double.parseDouble(vat_value_server) * Double.parseDouble(convertString(String.format("%.2f", (double) (Cart.getTotalPrice()))));
        vat = vat / 100;
        vat_value = String.format("%.2f", vat);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        String date = c.get(Calendar.DATE) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR);
        setArray();
        placeOrder = new PlaceOrder();
        vat_value = Utils.convertString(vat_value);
        placeOrder.setAmount_vat(vat_value + "");
        placeOrder.setCheckout_time("" + Utils.convertString(checkout_time));
        placeOrder.setUser_id(user.userId);
        placeOrder.setInclude_wallet(check_wallet + "");
        placeOrder.setIs_bank("0");
//        placeOrder.setCoupons(coupons + "");
        placeOrder.setPayment_type(paymentype);
        placeOrder.setAddressData(addressData);

        if (!et_promocode.getText().toString().trim().equals("") && validate_promocode && promoCode_value != 0) {
            placeOrder.setPromocode(et_promocode.getText().toString());
        } else if (!et_otp.getText().toString().trim().equals("") && validate_promocode && promoCode_value != 0) {
            placeOrder.setPromocode(stc_promo_code);
        } else {
            placeOrder.setPromocode("");
        }

        placeOrder.setPrice_without_vat(total_items_price + "");
        placeOrder.setPrefered_time(preffered_time_id);
        placeOrder.setPrefered_date(date);
        placeOrder.setReferral(et_refferal.getText().toString());
        placeOrder.setRecurring(recurring);
        placeOrder.setStc_otp(stc_otp);
        placeOrder.setCart(cartListPlaceOrder);
        placeOrder.setFoc_items(cartListPromotion);


        Log.d("**placedOrder", "" + placeOrder);

        Log.e("**placeOrderRequest", checkForLatestOrders() + "");

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put("Products", new Gson().toJson(cartListPlaceOrder));
        eventValue.put("Quantity", "" + Cart.getTotalItems());
        eventValue.put("payment method", "" + tvPaymentType.getText().toString());
//        CleverTapAPI.getDefaultInstance(CheckOutActivity.this).pushEvent("Begin checkout:", eventValue);

        if (!checkForLatestOrders()) {
            if (tvPaymentType.getText().toString().contains("تحويل بنكي") ||
                    tvPaymentType.getText().toString().contains("Bank Transfer")) {

                placeOrder.setIs_bank(bankID);
                placeOrder.setIs_bank(bankID);

                Log.d("requestbody", new Gson().toJson(placeOrder));
                Log.d("addresss", new Gson().toJson(addressData));

                Log.d("**bankID", "" + placeOrder.getIs_bank());

                showProgress(true);

                APIManager.getInstance().placeOrder(new APIManager.Callback() {
                                                        @Override
                                                        public void onResult(boolean z, String response) {

                                                            if (z) {

                                                                if (response.contains("Access denied") || response.contains("Invalid Key!") || response.contains("Invalid Key")) {
                                                                    CoreManager.getInstance().removeUserData();
                                                                    Toast.makeText(CheckOutActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(CheckOutActivity.this, SplashScreenActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    finish();
                                                                    startActivity(intent);
                                                                } else {
                                                                    final double amountForTax = (Double.parseDouble(Utils.convertString("" + Cart.getTotalPrice())) * Double.parseDouble(vat_value)) / 100;
                                                                    try {
                                                                        showProgress(false);
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        if (jsonObject.getString("is_valid_order").equals("0")) {
                                                                            if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                                                                                alertDialogError(jsonObject.getString("Message_ar"));
                                                                            } else {
                                                                                alertDialogError(jsonObject.getString("Message_en"));
                                                                            }

                                                                        } else {
                                                                            Constants.validate_promocode = false;
                                                                            Intent intent = new Intent(CheckOutActivity.this, SuccessfullOrder.class);
                                                                            intent.putExtra("address_id", new JSONObject(response).getString("address_id"));

                                                                            intent.putExtra("id", new JSONObject(response).getString("id"));
                                                                            intent.putExtra("cc", false);
                                                                            intent.putExtra(AMOUNT, Cart.getFormattedAmount(Double.parseDouble("" + Cart.getTotalPrice()) + amountForTax));
                                                                            intent.putExtra("amount_Vat", amountForTax + "");
                                                                            intent.putExtra("amount_withoutVat", String.valueOf(Cart.getTotalPrice()));
                                                                            intent.putExtra("cityId", cityId);
                                                                            startActivity(intent);
                                                                            overridePendingTransition(R.anim.enter_anim, 0);
                                                                            saveAddressId(new JSONObject(response).getString("address_id"));

                                                                            Map<String, Object> eventValue = new HashMap<String, Object>();
                                                                            eventValue.put("Products", new Gson().toJson(cartListPlaceOrder));
                                                                            eventValue.put("Total Price", totalprice);
                                                                            eventValue.put("Quantity", Cart.getTotalItems());
                                                                            eventValue.put("payment method", "" + tvPaymentType.getText().toString());
                                                                            eventValue.put("Delivery Preferred Time", "" + preffered_time_id);
                                                                            eventValue.put("Preferred schedule", "" + recurring);
                                                                            eventValue.put("address_lat", "" + addressData.getAdd_latitude());
                                                                            eventValue.put("address_lng", "" + addressData.getAdd_longitude());


                                                                            Areas areas = DatabaseManager.getInstance().getFirstMatching("areaId", addressData.getAdd_area(), Areas.class);
                                                                            Cities cities = DatabaseManager.getInstance().getFirstMatching("cityId", cityId, Cities.class);

                                                                            eventValue.put("address_city", "" + cities.cityTitleEn);
                                                                            eventValue.put("address_area", "" + areas.areaTitleEn);


                                                                            if (promoCode_value != 0)
                                                                                eventValue.put("with a discount:", "Yes:" + promoCode_value);
                                                                            else
                                                                                eventValue.put("with a discount:", "NO");

                                                                            if (!promoCode.equals(""))
                                                                                eventValue.put("with a promo code", "Yes:" + promoCode);
                                                                            else
                                                                                eventValue.put("with a promo code", "No");

//                                        CleverTapAPI.getDefaultInstance(CheckOutActivity.this).pushEvent("Checkout Complete:", eventValue);

                                                                        }

                                                                    } catch (Exception e) {
                                                                        Log.e("Ex_cartitems", e.toString());
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            }


                                                        }
                                                    },
                        placeOrder, CheckOutActivity.this);


            } else if (tvPaymentType.getText().toString().contains("Cash on Delivery")
                    || tvPaymentType.getText().toString().contains("الدفع عند الإستلام")
                    || user.hide_price.equals("1") || tvPaymentType.getText().toString().toLowerCase().contains("wallet")
                    || tvPaymentType.getText().toString().contains("المحفظة النقدية")) {


                Log.d("requestbody", new Gson().toJson(placeOrder));
                Log.d("addresss", new Gson().toJson(addressData));

                Log.d("**bankID", "" + placeOrder.getIs_bank());

                showProgress(true);

                APIManager.getInstance().placeOrder(new APIManager.Callback() {
                                                        @Override
                                                        public void onResult(boolean z, String response) {

                                                            if (z) {

                                                                if (response.contains("Access denied") || response.contains("Invalid Key!") || response.contains("Invalid Key")) {
                                                                    CoreManager.getInstance().removeUserData();
                                                                    Toast.makeText(CheckOutActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(CheckOutActivity.this, SplashScreenActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    finish();
                                                                    startActivity(intent);
                                                                } else {
                                                                    final double amountForTax = (Double.parseDouble(Utils.convertString("" + Cart.getTotalPrice())) * Double.parseDouble(vat_value)) / 100;
                                                                    try {
                                                                        showProgress(false);
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        if (jsonObject.getString("is_valid_order").equals("0")) {
                                                                            if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                                                                                alertDialogError(jsonObject.getString("Message_ar"));
                                                                            } else {
                                                                                alertDialogError(jsonObject.getString("Message_en"));
                                                                            }

                                                                        } else {
                                                                            Constants.validate_promocode = false;
                                                                            Intent intent = new Intent(CheckOutActivity.this, SuccessfullOrder.class);
                                                                            intent.putExtra("address_id", new JSONObject(response).getString("address_id"));

                                                                            intent.putExtra("id", new JSONObject(response).getString("id"));
                                                                            intent.putExtra("cc", false);
                                                                            intent.putExtra(AMOUNT, Cart.getFormattedAmount(Double.parseDouble("" + Cart.getTotalPrice()) + amountForTax));
                                                                            intent.putExtra("amount_Vat", amountForTax + "");
                                                                            intent.putExtra("amount_withoutVat", String.valueOf(Cart.getTotalPrice()));
                                                                            intent.putExtra("cityId", cityId);
                                                                            startActivity(intent);
                                                                            overridePendingTransition(R.anim.enter_anim, 0);
                                                                            saveAddressId(new JSONObject(response).getString("address_id"));

                                                                            Map<String, Object> eventValue = new HashMap<String, Object>();
                                                                            eventValue.put("Products", new Gson().toJson(cartListPlaceOrder));
                                                                            eventValue.put("Total Price", totalprice);
                                                                            eventValue.put("Quantity", Cart.getTotalItems());
                                                                            eventValue.put("payment method", "" + tvPaymentType.getText().toString());
                                                                            eventValue.put("Delivery Preferred Time", "" + preffered_time_id);
                                                                            eventValue.put("Preferred schedule", "" + recurring);
                                                                            eventValue.put("address_lat", "" + addressData.getAdd_latitude());
                                                                            eventValue.put("address_lng", "" + addressData.getAdd_longitude());


                                                                            Areas areas = DatabaseManager.getInstance().getFirstMatching("areaId", addressData.getAdd_area(), Areas.class);
                                                                            Cities cities = DatabaseManager.getInstance().getFirstMatching("cityId", cityId, Cities.class);

                                                                            eventValue.put("address_city", "" + cities.cityTitleEn);
                                                                            eventValue.put("address_area", "" + areas.areaTitleEn);


                                                                            if (promoCode_value != 0)
                                                                                eventValue.put("with a discount:", "Yes:" + promoCode_value);
                                                                            else
                                                                                eventValue.put("with a discount:", "NO");

                                                                            if (!promoCode.equals(""))
                                                                                eventValue.put("with a promo code", "Yes:" + promoCode);
                                                                            else
                                                                                eventValue.put("with a promo code", "No");

//                                        CleverTapAPI.getDefaultInstance(CheckOutActivity.this).pushEvent("Checkout Complete:", eventValue);

                                                                        }

                                                                    } catch (Exception e) {
                                                                        Log.e("Ex_cartitems", e.toString());
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            }


                                                        }
                                                    },
                        placeOrder, CheckOutActivity.this);

            } else {

                Intent intent = new Intent(getApplicationContext(), HyperpayPaymentActivity.class);
                intent.putExtra("price", totalprice + "");
                intent.putExtra("promo", promoCode_value);

                for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                    if (tvPaymentType.getText().toString().trim().toLowerCase().equals(cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase())
                            || tvPaymentType.getText().toString().trim().toLowerCase().equals(cartParamResponse.getPaymentMethods().get(i).getTitleAr().toLowerCase()
                    )) {
                        Log.d("paymentmethod", cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                        intent.putExtra("payment_type", cartParamResponse.getPaymentMethods().get(i).getId());
                        intent.putExtra("payment_method", cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                    }
                }
                intent.putExtra("placeorder", new Gson().toJson(placeOrder));
                intent.putExtra("cityId", cityId);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_anim, 0);

            }
        } else {
            Toast.makeText(CheckOutActivity.this, getResources().getString(R.string.st_adresserror), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAddressId(String address_id) {
        try {
            JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lat", addressObj.getString("lat") + "");
            jsonObject.put("lng", addressObj.getString("lng") + "");
            jsonObject.put("areaId", "" + addressObj.getString("areaId"));
            jsonObject.put("cityId", "" + addressObj.getString("cityId"));
            jsonObject.put("addressName", "Address: " + addressObj.getString("addressName"));
            jsonObject.put("details", "" + addressObj.getString("details"));
            jsonObject.put("address_id", address_id);
            jsonObject.put("add_type", addressObj.getString("add_type"));
            UserSession.getInstance().setSaveHomeAddressObject(jsonObject + "");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    void setArray() {
        try {
            cartListPlaceOrder.clear();
            jsonArray_cart = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).count == 0) {

                } else {
                    PlaceOrder.Cart cart = new PlaceOrder.Cart();
                    cart.setCount(cartList.get(i).count);
                    cart.setDishId(cartList.get(i).productId);
                    cart.setPrice(cartList.get(i).amount);
                    cart.setProduct_price(cartList.get(i).price);
                    cart.setProductTitleEn(cartList.get(i).productTitleEn);
                    cartListPlaceOrder.add(cart);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_order_detailed_btn_checkout:
                if (Cart.getTotalItems() < Double.parseDouble(min_quantity)) {
                    Toast.makeText(CheckOutActivity.this,
                            getResources().getString(R.string.min_order_amount) + " " + min_quantity, Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("credit_limit", "" + totalprice);
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                    if (user != null) {
                        if (user.add_address.equals("0")) {
                            if (totalprice <= Double.parseDouble(cartParamResponse.getCredit_limit())) {
                                setPlaceOrder();
                            } else {
                                Toast.makeText(orderdetailactivity,
                                        "" + getResources().getString(R.string.st_credit_insufficient_message),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!validate_promocode && promoCode_value == 0) {
                                setPlaceOrder();
                            } else if (validate_promocode && (!et_promocode.getText().toString().isEmpty() ||
                                    !et_otp.getText().toString().equals(""))) {
                                setPlaceOrder();
                            } else {
                                ConfirmOrder();
                            }
                        }
                    }
                }
                break;
            case R.id.activity_order_detailed_btn_additems:
                Intent intent = new Intent(CheckOutActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;
            case R.id.imageViewBackButton:
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;
            case R.id.paymentOptionView_tvChange:
                if (totalprice == 0 && rb_wallet.isChecked()) {
                } else selectPaymentMethod();
                break;

            case R.id.tvAddpromoCode:
                if (textview_addpromocode.getText().toString().equals("" + getResources().getString(R.string.st_apply))) {
                    if (!et_promocode.getText().toString().equals("")) {
                        applypromocode();
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_promocode.getWindowToken(), 0);
                } else {
                    et_promocode.setEnabled(true);
                    et_promocode.setText("");
                    textview_addpromocode.setText("" + getResources().getString(R.string.st_apply));
                    if (isArabic)
                        textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg_ar));
                    else {
                        textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg));
                    }
                    promoCode_value = 0;
                    promoCode_valueS = 0;
                    promocode_type = "";
                    setPayment();
                }
                break;
            case R.id.textview_addotp:

                if (textview_addotp.getText().toString().equals("" + getResources().getString(R.string.st_apply))) {
                    if (!et_otp.getText().toString().equals("")) {
                        applyOtp();
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_otp.getWindowToken(), 0);
                } else {
                    reversal_stc_tamayouz_discount();
                    et_otp.setEnabled(true);
                    et_otp.setText("");
                    rb_stc.setChecked(false);
                    textview_addotp.setText("" + getResources().getString(R.string.st_apply));
                    promoCode_value = 0;
                    promoCode_valueS = 0;
                    promocode_type = "";
                    setPayment();
                }
                break;
            case R.id.tv_free_group_promotion:
                openPromotionGroupDialog(foc_prodList, add_on);
                break;

        }

    }


    private double getSumOfAllowedPromoProduct() {

        double total_discountitems = 0;

        for (int i = 0; i < allowed_products.size(); i++) {

            for (int j = 0; j < cartListPlaceOrder.size(); j++) {

                if (allowed_products.get(i).equals(cartListPlaceOrder.get(j).getDishId())) {
                    double amount = cartListPlaceOrder.get(j).getCount() * Double.parseDouble(cartListPlaceOrder.get(j).getProduct_price());
                    total_discountitems = total_discountitems + amount;

                }

            }
        }

        return Double.parseDouble(convertString(String.format("%.2f", total_discountitems)));
    }


    private double getSumItemsWithCoupons() {
        setArray();
        double total_of_items = 0;
//        double total_quantity = Double.parseDouble(cartParamResponse.getCoupon().getCoupon_qty());
//        boolean isCouponsItems = false;
/*
        for (int i = 0; i < cartParamResponse.getCoupon().getProducts().size(); i++) {

            for (int j = 0; j < cartListPlaceOrder.size(); j++) {

                *//*if (cartParamResponse.getCoupon().getProducts().get(i).equals(cartListPlaceOrder.get(j).getDishId())) {
                    if (total_quantity > cartListPlaceOrder.get(j).getCount()) {
                        total_quantity = total_quantity - cartListPlaceOrder.get(j).getCount();
                    } else {

                        double amount = (cartListPlaceOrder.get(j).getCount() - total_quantity) * Double.parseDouble(cartListPlaceOrder.get(j).getProduct_price());
                        total_of_items = total_of_items + amount;
                        total_quantity = 0;
                    }

                    Log.d("asdasdas", "" + cartListPlaceOrder.get(j).getProductTitleEn());
//                    isCouponsItems = true;

                }

                else {*//*
                double amount = cartListPlaceOrder.get(j).getCount() * Double.parseDouble(cartListPlaceOrder.get(j).getProduct_price());
                total_of_items = total_of_items + amount;
//                }

            }
        }*/

     /*   if (!isCouponsItems) {
            couponsLL.setEnabled(false);
            couponsLL.setAlpha(0.3f);
            couponsSwitch.setChecked(false);
        }*/

        return Double.parseDouble(convertString(String.format("%.2f", total_of_items)));
    }


 /*   public void setPayment() {
        try {
            totalprice = 0;
            promoCode_value = 0;
            double vat = 0;
            delivery_fee = Cart.getTotalItems() * Double.parseDouble(cartParamResponse.getDelivery_fee());
            tvChangePaymentMethod.setVisibility(View.VISIBLE);
            if (tvPaymentType.getText().toString().toLowerCase().contains("wallet")
                    || tvPaymentType.getText().toString().contains("المحفظة النقدية")) {
                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                    tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleAr());
                else
                    tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleEn());

                cartParamResponse.getPaymentMethods().get(0).setSelected(true);

                if (!cartParamResponse.getPaymentMethods().get(0).getTitleEn().toLowerCase().equals("cash on delivery"))
                    btn_checkout.setText(getResources().getString(R.string.proceed));
                setPaymentServer(tvPaymentType.getText().toString());
            }

            totalprice = Double.parseDouble(convertString(String.format("%.2f", (double) (Cart.getTotalPrice()))));

            if (discount_level.equals("product")) {

                if (after_vat.equals("1")) {

                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat *totalprice;
                    totalprice = ((double) totalprice + vat);

                    if (promocode_type.equals("percentage")) {
                        double resultVat = getSumOfAllowedPromoProduct() / 100;
                        resultVat = resultVat * Double.parseDouble(vat_value_server);
                        promoCode_value = promoCode_valueS * (getSumOfAllowedPromoProduct() + resultVat) / 100;
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));
                    } else {
                        promoCode_value = promoCode_valueS;
                    }
                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }
                    tvPromocodeValueA.setText("-" + convertString(String.format("%.2f", promoCode_value)) + "  " +
                            getResources().getString(R.string.currency));
                }
                else {

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * getSumOfAllowedPromoProduct() / 100;
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));

                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    totalprice = totalprice + delivery_fee;

                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat *totalprice;
                    totalprice = ((double) totalprice + vat);
                    tvPromocodeValueB.setText("-" + convertString(String.format("%.2f", promoCode_value)) + "  " +
                            getResources().getString(R.string.currency));
                }
            }
            else {

                if (after_vat.equals("1")) {
                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat *totalprice;
                    totalprice = ((double) totalprice + vat);

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * (totalprice / 100);
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));
                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    tvPromocodeValueA.setText("-" + convertString(String.format("%.2f", promoCode_value)) + "  " +
                            getResources().getString(R.string.currency));

                    if (totalprice == 0) {
                        for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                            if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase().equals("wallet")) {
                                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                                    tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                                else
                                    tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                                btn_checkout.setText(getResources().getString(R.string.check_out));
                                cartParamResponse.getPaymentMethods().get(i).setSelected(true);
                            } else {
                                cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                            }
                        }
                        setPaymentServer(tvPaymentType.getText().toString());
                        tvChangePaymentMethod.setVisibility(View.GONE);
                    }

                } else {

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * (totalprice / 100);
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));

                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }
                    totalprice = totalprice + delivery_fee;

                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat *totalprice;
                    totalprice = ((double) totalprice + vat);
                    tvPromocodeValueB.setText("-" + convertString(String.format("%.2f", promoCode_value)) + "  " +
                            getResources().getString(R.string.currency));
                }

            }

            vat = Double.parseDouble(convertString(String.format("%.2f", (double) (vat))));
            layoutWalletDiscount.setVisibility(View.GONE);


    *//*        if (wallet_discount>totalprice&&rb_wallet.isChecked()){
                layoutWalletDiscount.setVisibility(View.VISIBLE);
                tvWalletDiscountValue.setText("-"+convertString(String.format("%.2f", totalprice)+getResources().getString(R.string.currency)));
                totalprice = 0.0;

                for(int i = 0; i< cartParamResponse.getPaymentMethods().size();i++){
                    Log.d("paymentoption",""+cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                    if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase().equals("wallet")){
                        if (isArabic)
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                        else
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                        btn_checkout.setText(getResources().getString(R.string.check_out));
                        cartParamResponse.getPaymentMethods().get(i).setSelected(true);
                    }else {
                        cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                    }
                }

                setPaymentServer(tvPaymentType.getText().toString());

            }else if (rb_wallet.isChecked()){
                totalprice = totalprice-wallet_discount;
                layoutWalletDiscount.setVisibility(View.VISIBLE);
                tvWalletDiscountValue.setText("-"+wallet_discount+getResources().getString(R.string.currency));

                if (wallet_discount==0){
                    layoutWalletDiscount.setVisibility(View.GONE);
                }

            }else {
                layoutWalletDiscount.setVisibility(View.GONE);
            }
*//*

            if (promoCode_value > 0) {
                if (after_vat.equals("1")) {
                    layoutPromocodeA.setVisibility(View.VISIBLE);
                    layoutPromocodeB.setVisibility(View.GONE);
                } else {
                    layoutPromocodeA.setVisibility(View.GONE);
                    layoutPromocodeB.setVisibility(View.VISIBLE);
                }

            } else {
                layoutPromocodeB.setVisibility(View.GONE);
                layoutPromocodeA.setVisibility(View.GONE);
            }



            textview_orderitemsvalue.setText(convertString(String.format("%.2f", Cart.getTotalPrice())) + "  " + getResources().getString(R.string.currency));



            tvShippingCharges.setText(convertString(String.format("%.2f", delivery_fee))+"  "+getResources().getString(R.string.currency));
            if (delivery_fee>0){
                shippingLL.setVisibility(View.VISIBLE);
            }
            else {
                shippingLL.setVisibility(View.GONE);
            }

            textview_vatvalue.setText(convertString(String.format("%.2f", vat)) + "  " + getResources().getString(R.string.currency));
            textview_walletvalue.setText("-" + convertString(String.format("%.2f", Double.parseDouble(cartParamResponse.getClientWalletBalance()))) + "  " + getResources().getString(R.string.currency));
            textview_totalvalue.setText(convertString(String.format("%.2f", totalprice)) + "  " + getResources().getString(R.string.currency));
            View walletview = findViewById(R.id.wallet_view);
            walletview.setVisibility(View.GONE);

//            }


            Log.d("roundedNumber", "TOTAL PRICE:" + (double) (Cart.getTotalPrice()));
            Log.d("roundedNumber", "VAT: " + vat);
            Log.d("roundedNumber", "" + totalprice);

        } catch (Exception ex) {
            Log.e("Exception_Cart", ex.toString());
        }
    }*/


    public void setPayment() {
        try {
            totalprice = 0;
            promoCode_value = 0;
            double vat = 0;

            totalprice = Double.parseDouble(convertString(String.format("%.2f", (double) (Cart.getTotalPrice()))));

            Log.d("weightttt", cartParamResponse.getDelivery_fee() + "");


            if (cartParamResponse.getDelivery_formula().toLowerCase().equals("flat")) {
                delivery_fee = Double.parseDouble(cartParamResponse.getDelivery_fee());

            } else if (cartParamResponse.getDelivery_formula().toLowerCase().equals("item")) {
                delivery_fee = Cart.getTotalItems() * Double.parseDouble(cartParamResponse.getDelivery_fee());
            } else if (cartParamResponse.getDelivery_formula().toLowerCase().equals("weight")) {
                delivery_fee = Cart.getTotalWeight() * Cart.getTotalItems() * Double.parseDouble(cartParamResponse.getDelivery_fee());
            }
            Log.d("weightttt", cartParamResponse.getDelivery_formula().toLowerCase() + "");
            Log.d("weightttt", delivery_fee + "");


            if (discount_level.equals("product")) {

                if (after_vat.equals("1")) {

                    totalprice = totalprice + delivery_fee;
                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat * totalprice;
                    totalprice = ((double) totalprice + vat);
                    if (promocode_type.equals("percentage")) {
                        double resultVat = getSumOfAllowedPromoProduct() / 100;
                        resultVat = resultVat * Double.parseDouble(vat_value_server);
                        promoCode_value = promoCode_valueS * (getSumOfAllowedPromoProduct() + resultVat) / 100;
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));
                    } else {
                        promoCode_value = promoCode_valueS;
                    }
                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    tvPromocodeValueA.setText("-" + convertString(String.format("%.2f", promoCode_value)) +
                            getResources().getString(R.string.currency));

                } else {

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * getSumOfAllowedPromoProduct() / 100;
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));

                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    totalprice = totalprice + delivery_fee;

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat * totalprice;
                    totalprice = ((double) totalprice + vat);
                    tvPromocodeValueB.setText("-" + convertString(String.format("%.2f", promoCode_value)) +
                            getResources().getString(R.string.currency));
                }
            } else {

                if (after_vat.equals("1")) {

                    totalprice = totalprice + delivery_fee;
                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat * totalprice;
                    totalprice = ((double) totalprice + vat);

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * (totalprice / 100);
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));
                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    tvPromocodeValueA.setText("-" + convertString(String.format("%.2f", promoCode_value)) +
                            getResources().getString(R.string.currency));

                } else {

                    if (promocode_type.equals("percentage")) {
                        promoCode_value = promoCode_valueS * (totalprice / 100);
                        promoCode_value = Double.parseDouble(convertString(String.format("%.2f", promoCode_value)));

                    } else {
                        promoCode_value = promoCode_valueS;
                    }

                    totalprice = totalprice + delivery_fee;

                    if (promoCode_value > totalprice) {
                        totalprice = 0.0;
                    } else {
                        totalprice = totalprice - promoCode_value;
                    }

                    vat = Double.parseDouble(vat_value_server) / 100;
                    vat = vat * totalprice;
                    totalprice = ((double) totalprice + vat);
                    tvPromocodeValueB.setText("-" + convertString(String.format("%.2f", promoCode_value)) +
                            getResources().getString(R.string.currency));
                }

            }

            vat = Double.parseDouble(convertString(String.format("%.2f", (double) (vat))));
            layoutWalletDiscount.setVisibility(View.GONE);


    /*        if (wallet_discount>totalprice&&rb_wallet.isChecked()){
                layoutWalletDiscount.setVisibility(View.VISIBLE);
                tvWalletDiscountValue.setText("-"+convertString(String.format("%.2f", totalprice)+getResources().getString(R.string.currency)));
                totalprice = 0.0;

                for(int i = 0; i< cartParamResponse.getPaymentMethods().size();i++){
                     Constants.log("paymentoption",""+cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                    if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase().equals("wallet")){
                        if (isArabic)
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                        else
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                        btn_checkout.setText(getResources().getString(R.string.check_out));
                        cartParamResponse.getPaymentMethods().get(i).setSelected(true);
                    }else {
                        cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                    }
                }

                setPaymentServer(tvPaymentType.getText().toString());

            }else if (rb_wallet.isChecked()){
                totalprice = totalprice-wallet_discount;
                layoutWalletDiscount.setVisibility(View.VISIBLE);
                tvWalletDiscountValue.setText("-"+wallet_discount+getResources().getString(R.string.currency));

                if (wallet_discount==0){
                    layoutWalletDiscount.setVisibility(View.GONE);
                }

            }else {
                layoutWalletDiscount.setVisibility(View.GONE);
            }
*/

            if (promoCode_value > 0) {
                if (after_vat.equals("1")) {
                    layoutPromocodeA.setVisibility(View.VISIBLE);
                    layoutPromocodeB.setVisibility(View.GONE);
                } else {
                    layoutPromocodeA.setVisibility(View.GONE);
                    layoutPromocodeB.setVisibility(View.VISIBLE);
                }

            } else {
                layoutPromocodeB.setVisibility(View.GONE);
                layoutPromocodeA.setVisibility(View.GONE);
            }

            if (rb_wallet.isChecked()) {
                if ((Double.parseDouble(cartParamResponse.getClientWalletBalance())) >= totalprice) {
                    for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                        if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase().equals("wallet")) {
                            if (isArabic)
                                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                            else
                                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());

                            btn_checkout.setText(getResources().getString(R.string.check_out));
                            cartParamResponse.getPaymentMethods().get(i).setSelected(true);
                        } else {
                            cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                        }
                    }
                    setPaymentServer(tvPaymentType.getText().toString());

                    textview_orderitemsvalue.setText(convertString(String.format("%.2f",
                            Cart.getTotalPrice()).replace(",", ".")) +
                            getResources().getString(R.string.currency));
                    textview_vatvalue.setText(convertString(String.format("%.2f", vat)) +
                            getResources().getString(R.string.currency));
                    textview_totalvalue.setText(convertString("0.00") + getResources().getString(R.string.currency));
                    textview_walletvalue.setText("-" + convertString(String.format("%.2f", ((double) totalprice)) +
                            getResources().getString(R.string.currency)));
                    View walletview = findViewById(R.id.wallet_view);
                    if (totalprice > 0)
                        walletview.setVisibility(View.VISIBLE);
                    else
                        walletview.setVisibility(View.GONE);

                    totalprice = 0.00;

                } else {
                    totalprice = totalprice - Double.parseDouble(cartParamResponse.getClientWalletBalance());
                    textview_orderitemsvalue.setText(convertString(String.format("%.2f", Cart.getTotalPrice())) +
                            getResources().getString(R.string.currency));
                    textview_vatvalue.setText(convertString(String.format("%.2f", vat)) +
                            getResources().getString(R.string.currency));
                    textview_walletvalue.setText("-" + convertString(String.format("%.2f",
                            Double.parseDouble(cartParamResponse.getClientWalletBalance())))
                            + getResources().getString(R.string.currency));
                    textview_totalvalue.setText(convertString(String.format("%.2f", totalprice)) +
                            getResources().getString(R.string.currency));

                    View walletview = findViewById(R.id.wallet_view);

                    if (Double.parseDouble(cartParamResponse.getClientWalletBalance()) > 0)
                        walletview.setVisibility(View.VISIBLE);
                    else walletview.setVisibility(View.GONE);

                    if (tvPaymentType.getText().toString().toLowerCase().contains("wallet")
                            || tvPaymentType.getText().toString().contains("المحفظة النقدية")) {
                        if (isArabic)
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleAr());
                        else
                            tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleEn());

                        cartParamResponse.getPaymentMethods().get(0).setSelected(true);

                        if (!cartParamResponse.getPaymentMethods().get(0).getTitleEn().toLowerCase().equals("cash on delivery"))
                            btn_checkout.setText(getResources().getString(R.string.proceed));
                        setPaymentServer(tvPaymentType.getText().toString());
                    }
                }
            } else {
                textview_orderitemsvalue.setText(convertString(String.format("%.2f", Cart.getTotalPrice())) + getResources().getString(R.string.currency));
                textview_vatvalue.setText(convertString(String.format("%.2f", vat)) + getResources().getString(R.string.currency));
                textview_walletvalue.setText("-" + convertString(String.format("%.2f", Double.parseDouble(cartParamResponse.getClientWalletBalance()))) + getResources().getString(R.string.currency));
                textview_totalvalue.setText(convertString(String.format("%.2f", totalprice)) + getResources().getString(R.string.currency));
                View walletview = findViewById(R.id.wallet_view);
                walletview.setVisibility(View.GONE);
            }

            tvShippingCharges.setText(convertString(String.format("%.2f", delivery_fee)) + "  " + getResources().getString(R.string.currency));
            if (delivery_fee > 0) {
                shippingLL.setVisibility(View.VISIBLE);
            } else {
                shippingLL.setVisibility(View.GONE);
            }


        } catch (Exception ex) {
            Log.d("Exception_Cart", ex.toString());
        }
    }


    private String convertString(String number) {

        number = number.replace("١", "1")
                .replace("١", "1")
                .replace("٢", "2").
                        replace("٣", "3").replace("٤", "4")
                .replace("٥", "5")
                .replace("٦", "6")
                .replace("٧", "7")
                .replace("٨", "8")
                .replace("٩", "9")
                .replace("٠", "0")
                .replace("٫", ".")
                .replace(",", ".");
        return number;
    }

    public void selectPaymentMethod() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.alert_dialog_select_payment_method_list);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("" + getResources().getString(R.string._string_select_payment_method));
        ListView listViewChooseTime = dialog.findViewById(R.id.listViewChoosePayment);

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
            if (cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase().equals("wallet") ||
                    cartParamResponse.getPaymentMethods().get(i).getTitleAr().equals("المحفظة النقدية")
                    || cartParamResponse.getPaymentMethods().get(i).getId().toLowerCase().equals("4")) {

            } else {
                paymentMethods.add(cartParamResponse.getPaymentMethods().get(i));
            }
        }

        SelectPaymentMethodAdapter selectTimeListAdapter = new SelectPaymentMethodAdapter(this, paymentMethods);
        listViewChooseTime.setAdapter(selectTimeListAdapter);

        listViewChooseTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                    setPaymentServer(paymentMethods.get(position).getTitleAr());
                else
                    setPaymentServer(paymentMethods.get(position).getTitleEn());

                Log.d("**payment", "" + paymentMethods.get(position).getId());


                if (paymentMethods.get(position).getTitleEn().toString().toLowerCase().equals("cash on delivery")) {
                    btn_checkout.setText(getResources().getString(R.string.check_out));

                } else {
                    btn_checkout.setText(getResources().getString(R.string.proceed));
                }

                for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                    if (i == position) {
                        cartParamResponse.getPaymentMethods().get(i).setSelected(true);
                    } else {
                        cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                    }
                }
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.setCancelable(true);
        //dialog.setCancelable(false);
    }

    private void setPaymentServer(String method_name) {

        if (cartParamResponse != null) {
            for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {

                Log.d("methodName", "" + cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase());
                if (method_name.toLowerCase().contains(cartParamResponse.getPaymentMethods().get(i).getTitleEn().toLowerCase())) {

                    Log.e("String", cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                    if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                        tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                        if (tvPaymentType.getText().toString().contains("تحويل بنكي")) {
                            banksRV.setVisibility(View.VISIBLE);
                        } else {
                            banksRV.setVisibility(View.GONE);
                        }
                    } else {
                        tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                        Log.e("StringE", cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                        if (tvPaymentType.getText().toString().contains("Bank Transfer")) {
                            banksRV.setVisibility(View.VISIBLE);
                        } else {
                            banksRV.setVisibility(View.GONE);
                        }
                    }
                    paymentype = cartParamResponse.getPaymentMethods().get(i).getId();
                    Log.d("StringID", "" + cartParamResponse.getPaymentMethods().get(i).getId());
                    break;
                }
                if (method_name.contains(cartParamResponse.getPaymentMethods().get(i).getTitleAr())) {
                    Log.e("String", cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                    paymentype = cartParamResponse.getPaymentMethods().get(i).getId();
                    Log.d("StringID", "" + cartParamResponse.getPaymentMethods().get(i).getId());
                    if (UserSession.getInstance().getUserLanguage().equals("ar")) {
                        tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleAr());
                        if (tvPaymentType.getText().toString().contains("تحويل بنكي")) {
                            banksRV.setVisibility(View.VISIBLE);
                        } else {
                            banksRV.setVisibility(View.GONE);
                        }

                    } else {
                        tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                        Log.e("StringE", cartParamResponse.getPaymentMethods().get(i).getTitleEn());
                        if (tvPaymentType.getText().toString().contains("Bank Transfer")) {
                            banksRV.setVisibility(View.VISIBLE);
                        } else {
                            banksRV.setVisibility(View.GONE);
                        }
                    }

                    break;
                }

            }
        }
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.USER_SCORE, "Source:" + "Android");
//        eventValue.put(AFInAppEventParameterName.PAYMENT_INFO_AVAILIBLE, "" + method_name);
//        AppsFlyerLib.getInstance().trackEvent(CheckOutActivity.this, AFInAppEventType.ADD_PAYMENT_INFO, eventValue);

    }

    private void alertDialogError(String message) {
        final Dialog confirmDialog = new Dialog(CheckOutActivity.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.setContentView(R.layout.dialog_place_order_error);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        tv_message.setText("" + message);
        TextView btn_ok = confirmDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                finish();
            }
        });

        confirmDialog.setCancelable(false);
        confirmDialog.show();
    }

    @Override
    public void onQuantityChanged() {
        if (!stc_tamayouz_min_qty.equals("")) {
            if (Cart.getTotalItems() >= Integer.parseInt(stc_tamayouz_min_qty) &&
                    cartParamResponse.getShow_stc_tamayouz().equals("1")) {
                rb_stc.setVisibility(View.VISIBLE);
                //textview_stctitle.setVisibility(View.VISIBLE);
                //view_stcline.setVisibility(View.VISIBLE);
            } else {
                rb_stc.setVisibility(View.GONE);
                textview_stctitle.setVisibility(View.GONE);
                view_stcline.setVisibility(View.GONE);
            }
            if (rb_stc.isChecked()) {
                reversal_stc_tamayouz_discount();
            }
        } else {
            rb_stc.setVisibility(View.GONE);
            textview_stctitle.setVisibility(View.GONE);
            view_stcline.setVisibility(View.GONE);
        }

        et_otp.setEnabled(true);
        et_otp.setText("");
        textview_addotp.setText("" + getResources().getString(R.string.st_apply));
        et_promocode.setEnabled(true);
        et_promocode.setText("");
        textview_addpromocode.setText("" + getResources().getString(R.string.st_apply));
        if (isArabic)
            textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg_ar));
        else {
            textview_addpromocode.setBackground(getResources().getDrawable(R.drawable.promo_bg));
        }
        promoCode_value = 0;
        promoCode_valueS = 0;
        discount_level = "";
        promocode_type = "";
        setPayment();
        rb_stc.setChecked(false);
        setPromotion();
//        tvBasketBagde.setText(String.valueOf(size));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        tvBasketBagde.setText(String.valueOf(Cart.getTotalItems()));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.equals(rbMorning)) {
            if (b) {
                setTimeId(rbMorning.getText().toString());
                rbMorning.setChecked(true);
                rbEvening.setChecked(false);
                deliveryTimeView_rbanytime.setChecked(false);
            }
        } else if (compoundButton.equals(rbEvening)) {
            if (b) {
                setTimeId(rbEvening.getText().toString());
                rbMorning.setChecked(false);
                rbEvening.setChecked(true);
                deliveryTimeView_rbanytime.setChecked(false);
            }
        } else if (compoundButton.equals(deliveryTimeView_rbanytime)) {
            if (b) {
                setTimeId(deliveryTimeView_rbanytime.getText().toString());
                rbMorning.setChecked(false);
                rbEvening.setChecked(false);
                deliveryTimeView_rbanytime.setChecked(true);
            }
        }

        if (compoundButton.equals(rbNone)) {
            if (b) {
                recurring = "0";
                rbNone.setChecked(true);
                rbMonthly.setChecked(false);
                rbWeekly.setChecked(false);
            }
        } else if (compoundButton.equals(rbMonthly)) {
            if (b) {
                recurring = "3";
                rbNone.setChecked(false);
                rbMonthly.setChecked(true);
                rbWeekly.setChecked(false);
            }
        } else if (compoundButton.equals(rbWeekly)) {
            if (b) {
                recurring = "2";
                rbNone.setChecked(false);
                rbMonthly.setChecked(false);
                rbWeekly.setChecked(true);
            }
        }
        /*else if (compoundButton.equals(walletSwitch)) {

            if (isArabic)
                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleAr());
            else
                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleEn());

            cartParamResponse.getPaymentMethods().get(0).setSelected(true);

            if (!cartParamResponse.getPaymentMethods().get(0).getTitleEn().toLowerCase().equals("cash on delivery"))
                btn_checkout.setText(getResources().getString(R.string.proceed));

            for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                if (i != 0) {
                    cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                }
            }
            setPaymentServer(tvPaymentType.getText().toString());
            setPayment();

        }*/
        /*else if (compoundButton.equals(couponsSwitch)) {
            setPayment();
        } else */

        else if (compoundButton.equals(rb_wallet)) {
            if (isArabic)
                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleAr());
            else
                tvPaymentType.setText(cartParamResponse.getPaymentMethods().get(0).getTitleEn());

            cartParamResponse.getPaymentMethods().get(0).setSelected(true);

            if (!cartParamResponse.getPaymentMethods().get(0).getTitleEn().toLowerCase().equals("cash on delivery"))
                btn_checkout.setText(getResources().getString(R.string.proceed));

            for (int i = 0; i < cartParamResponse.getPaymentMethods().size(); i++) {
                if (i != 0) {
                    cartParamResponse.getPaymentMethods().get(i).setSelected(false);
                }
            }
            setPaymentServer(tvPaymentType.getText().toString());
            setPayment();

        } else if (compoundButton.equals(rb_stc)) {

            if (b) {
                et_otp.setEnabled(true);
                et_otp.setText("");
                et_promocode.setEnabled(true);
                et_promocode.setText("");
                textview_addotp.setText("" + getResources().getString(R.string.st_apply));
                promoCode_value = 0;
                promoCode_valueS = 0;
                promocode_type = "";
                setPayment();
                applyStc();

            } else {

                promoView_layout.setVisibility(View.VISIBLE);
                stc_layout.setVisibility(View.GONE);
                promoBelowLine.setVisibility(View.GONE);
                et_otp.setEnabled(true);
                et_otp.setText("");
                et_promocode.setEnabled(true);
                et_promocode.setText("");
                textview_addotp.setText("" + getResources().getString(R.string.st_apply));
                promoCode_value = 0;
                promoCode_valueS = 0;
                promocode_type = "";
                setPayment();
            }

        }
    }

    private void setTimeId(String slot) {
        if (cartParamResponse != null) {
            for (int i = 0; i < cartParamResponse.getDeliverySlots().size(); i++) {

            }

            for (int i = 0; i < cartParamResponse.getDeliverySlots().size(); i++) {
                if (slot.equals(cartParamResponse.getDeliverySlots().get(i).getTitleEn())) {
                    preffered_time_id = cartParamResponse.getDeliverySlots().get(i).getId();
                    break;
                }
                if (slot.contains(cartParamResponse.getDeliverySlots().get(i).getTitleAr())) {
                    preffered_time_id = cartParamResponse.getDeliverySlots().get(i).getId();
                    break;
                }

            }
        }
    }

    private void applyStc() {
        showProgress(true);
        CheckStcRequest checkStcRequest;
        if (UserSession.getInstance().getUserLanguage().equals("ar")) {
            checkStcRequest = new CheckStcRequest(user.userId, user.mobile, "1");
        } else {
            checkStcRequest = new CheckStcRequest(user.userId, user.mobile, "2");
        }
        APIManager.getInstance().is_stc_tamayouz_user(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                if (z) {
                    try {
                        showProgress(false);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true")) {
                            if (jsonObject.getString("otp_result").equals("true")) {
                                promoView_layout.setVisibility(View.GONE);
                                stc_layout.setVisibility(View.VISIBLE);
                                promoBelowLine.setVisibility(View.VISIBLE);
                                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                                    Toast.makeText(CheckOutActivity.this, jsonObject.getString("success_ar"), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(CheckOutActivity.this, jsonObject.getString("success_en"), Toast.LENGTH_SHORT).show();
                            } else {
                                rb_stc.setChecked(false);
                                promoView_layout.setVisibility(View.VISIBLE);
                                stc_layout.setVisibility(View.GONE);
                                promoBelowLine.setVisibility(View.GONE);

                                if (UserSession.getInstance().getUserLanguage().equals("ar"))
                                    Toast.makeText(CheckOutActivity.this, jsonObject.getString("error_ar"), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(CheckOutActivity.this, jsonObject.getString("error_en"), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            rb_stc.setChecked(false);
                            promoView_layout.setVisibility(View.VISIBLE);
                            stc_layout.setVisibility(View.GONE);
                            promoBelowLine.setVisibility(View.GONE);
                            if (UserSession.getInstance().getUserLanguage().equals("ar"))
                                Toast.makeText(CheckOutActivity.this, jsonObject.optString("error_ar"), Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(CheckOutActivity.this, jsonObject.optString("error_en"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        showProgress(false);
                        rb_stc.setChecked(false);
                        Log.e("Ex_checkStc", e.toString());
                        e.printStackTrace();
                    }
                } else {
                    showProgress(false);
                    rb_stc.setChecked(false);
                    Toast.makeText(CheckOutActivity.this, "Something went wrong.try again", Toast.LENGTH_SHORT).show();
                    Log.e("error", z + "");
                }
            }
        }, checkStcRequest);

    }

    private void reversal_stc_tamayouz_discount() {
        CheckStcRequest checkStcRequest;
        if (UserSession.getInstance().getUserLanguage().equals("ar")) {
            checkStcRequest = new CheckStcRequest(user.userId, "1");
        } else {
            checkStcRequest = new CheckStcRequest(user.userId, "2");

        }
        APIManager.getInstance().reversal_stc_tamayouz_discount(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                if (z) {
                    try {

                    } catch (Exception e) {
                        showProgress(false);
                        Log.e("Ex_checkStc", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }, checkStcRequest);


    }

    private void applyOtp() {
        setArray();
        showProgress(true);
        String stc_otp = "0";
        if (rb_stc.isChecked()) {
            stc_otp = "1";
        }
/*
        String isCoupon = "0";
        if (couponsSwitch.isChecked()) {
            isCoupon = "1";
        }*/

        Log.d("getstcotp", new Gson().toJson(new PromocodeRequest(Integer.parseInt(AreaId), "1",
                et_otp.getText().toString(),
                Cart.getTotalPrice() + "",
                Integer.parseInt(user.userId), cartListPlaceOrder, stc_otp, user.mobile)));

        APIManager.getInstance().validate_stc_tamayouz_otp(new APIManager.Callback() {

            @Override
            public void onResult(boolean z, String response) {
                if (z) {
                    try {
                        showProgress(false);
                        JSONObject jsonObject = new JSONObject(response);
                        setOtpresponse(jsonObject);

                    } catch (Exception e) {
                        showProgress(false);
                        Log.e("Ex_checkStc", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }, new PromocodeRequest(Integer.parseInt(AreaId), "1",
                et_otp.getText().toString(),
                Cart.getTotalPrice() + "",
                Integer.parseInt(user.userId), cartListPlaceOrder, stc_otp, user.mobile));


    }

    private void setAddress() {
        try {
            JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
            AreaId = "" + Integer.parseInt(addressObj.getString("areaId"));
            address_id = "" + addressObj.getString("address_id");
            cityId = "" + Integer.parseInt(addressObj.getString("cityId"));
            deliveryAddressView_tvAddress.setText("" + addressObj.getString("details"));
            addressData = new addressData();
            addressData.setAdd_area(addressObj.getString("areaId"));
            addressData.setOrd_address_id(addressObj.getString("address_id"));
            addressData.setAdd_name("" + addressObj.getString("addressName"));
            addressData.setAdd_detail(addressObj.getString("details"));
            addressData.setAdd_latitude(addressObj.getString("lat"));
            addressData.setAdd_longitude(addressObj.getString("lng"));
            addressData.setAdd_type(addressObj.getString("add_type"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(CheckOutActivity.this);
            mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }


    private boolean checkForLatestOrders() {
        if (address_id.equals("")) {
            return false;
        }

        if (cartParamResponse.getLatest_orders() != null && cartParamResponse.getLatest_orders().size() > 0) {
            ArrayList<LatestOrder> latestOrders = cartParamResponse.getLatest_orders();
            boolean isIdMatching = false;
            for (LatestOrder latestOrder : latestOrders) {
                if (latestOrder.getAddress_id().equals(address_id)) {

                    ArrayList<OrderItems> orderItems = latestOrder.getOrder_items();

                    if (orderItems != null) {
                        for (OrderItems orderItem : orderItems) {
                            for (Cart cart : cartList) {
                                Log.e(cart.productId, cart.productId);
                                if (cart.productId.equals(orderItem.getProduct_id()) &&
                                        cart.count == Integer.parseInt(orderItem.getQty())) {
                                    return true;
                                }

                            }
                        }
                    }


                }


            }
            return false;

        }


        return false;
    }

    private void ConfirmOrder() {
        confirmDialog = new Dialog(CheckOutActivity.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.setContentView(R.layout.confirm_dialog);
        Button btn_proceed = confirmDialog.findViewById(R.id.btn_proceed);
        Button btn_dismiss = confirmDialog.findViewById(R.id.btn_dismiss);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                setPlaceOrder();
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_promocode.setText("" + promoCode);
                applypromocode();
                confirmDialog.dismiss();
            }
        });

        confirmDialog.setCancelable(false);
        confirmDialog.show();


    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiverOnAddressChange);
        super.onDestroy();
        if (payment_Dialog != null && payment_Dialog.isShowing()) {
            payment_Dialog.dismiss();
        }
        if (confirmDialog != null && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(mReceiverOnAddressChange, new IntentFilter("data_action_add_change"));
        super.onResume();
    }

    private BroadcastReceiver mReceiverOnAddressChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String string = (String) intent.getSerializableExtra("onAddressChange");
                Log.d("changeddd", string + "....");
                if (string.equals("changed")) {
                    setPromotion();
                    setAddress();
                    getCartParams();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void deleteItem(final Cart cart) {
        final Dialog dialog = new Dialog(CheckOutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.deleting_confirm));
        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(getResources().getString(R.string.remove_product_message));

        Button ok = (Button) dialog.findViewById(R.id.okBtn);
        Button cancel = (Button) dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartList.remove(cart);
                cart.delete();
                productAdapter.notifyDataSetChanged();

                if (cartList.size() < 1) {
                    finish();
                }

                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put("Products", "" + "" + cart);
                eventValue.put("Quantity", "" + cart.count);
//                CleverTapAPI.getDefaultInstance(CheckOutActivity.this).pushEvent("Remove from cart:", eventValue);
                setPayment();
                /*Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
                startActivity(intent);
                finish();*/

                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.count < 1) {
                    cart.count = 1;
                    cart.update();
                    productAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void selectBank(BanksModel banksModel, int position) {
        for (int i = 0; i < banksModelList.size(); i++) {
            banksModelList.get(i).setSelected(false);
        }
        banksModelList.get(position).setSelected(true);
        banksAdapter.notifyDataSetChanged();
        bankID = banksModel.getBank_id();
    }
}

