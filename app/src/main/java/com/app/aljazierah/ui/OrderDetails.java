package com.app.aljazierah.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.OrderSummaryRecycler;
import com.app.aljazierah.adapter.YourOrderDetailRecycler;
import com.app.aljazierah.adapter.YourOrderDetailRecyclerRating;
import com.app.aljazierah.object.AddReceiptModel;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.PlaceOrder;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.RemoveReceiptModel;
import com.app.aljazierah.object.RetingRequest.Items;
import com.app.aljazierah.object.YourOrderDetails.OrderDetail;
import com.app.aljazierah.object.YourOrderDetails.YourOrderDetail;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.OrderRequest;
import com.app.aljazierah.object.RetingRequest.RetingRequest;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.APIService;
import com.app.aljazierah.utils.AddHeaderInterceptorForImg;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.RequestPermissionHandler;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.aljazierah.utils.Constants.BASE_URL;
import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;
import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;
import static com.app.aljazierah.utils.Utils.convertString;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {
    private String order_id;
    private String order_number;
    private static final String TAG = "Order Detail Activity";
    private ProgressDialog mProgressDialog;
    private boolean isArabic;
    LinearLayout llOrderShipped, llOrderVerified, llDelivered;
    LinearLayout rlPayementDetailView;
    LinearLayout orderdetailLayoutmain, layoutQitaf;
    RelativeLayout layoutNoDataFound;
    private View toolbar;
    private ConstraintLayout orderdetailcircleview;
    //Recycler
    RecyclerView orderdetailItemsList;
    YourOrderDetailRecycler orderAdapter;
    YourOrderDetailRecyclerRating orderAdapterRatting;
    List<OrderDetail> orderDetailList = new ArrayList<>();
    private ImageView imageViewBackButton;
    TextView tvToolbarTitle, walletTV;
    TextView tvOrderID, tvReOrder;
    private Button uploadPaymentReceiptTV, selectImageTV;
    //Top Layer steps
    // LinearLayout llStepper;
    ImageView imageviewPlaced, imageviewVerified, imageViewShipped, imageViewDelivered;
    TextView tvOrderPlaced, tvVerified, tvShipped, tvDelivered, tv_qitaf_rewardpoints;
    View layoutWalletDiscount, viewStatus;
    private TextView textview_addpromocode, tvUseWallet;
    private Dialog showImageDialog;
//    ;
    //below Recycler

    public TextView tvPaymentType, textview_location, tvChangePaymentMethod, tvDeliveryAddress, tvChangeAddress, tvTransactionIdApplePay, tvTransactionId;
    public EditText editTextPromocode;
    public RadioButton radioButtonMorning, radioButtonEvening, radioButtonAnytime;
    public RadioButton radioButtonScheduleNone, radioButtonScheduleWeekly, radioButtonScheduleEveryMonth;
    RelativeLayout rlPromoView;
    //bottom Payment Details
    private TextView tvOrderItems, tvReturns, tv_please_rate, tvShippingCharges, tvWalletDiscountValue, tvVAT, tvPromocodeValueA, tvPromocodeValueB, tvWallet, tvGrandTotal, tvDevliveryMan, tvDevliveryTime, tvSubmitRating;

    RecyclerView orderSummaryRecyclerView;
    TextView tvOrderSummery, tvOrderSummeryOD, tvOrderStatus,
            orderSummarytvPaymentType, tvSummaryAddress,
            tvSummaryDeliveryTime, tvSummarySchedule, editUserRemarks, viewInvoice, orderTracking;

    private View ratingview;
    LinearLayout layoutProductQuality, wallet_view, imageLL;
    RatingBar ProductQuality, DeliveryTime, retingDeliveryMan;
    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
    View orderDetaiMain, layoutPromocodeB, layoutPromocodeA;
    boolean isViewSummery = false;

    private ConstraintLayout placedLayout, verifiedLayout, shippedLayout, deliveredLayout;
    private ImageView placedCheckMarkIV, verifiedCheckMarkIV, shippedCheckMarkIV, deliveredCheckMarkIV, uploadedIV, removeIV;
    private View placedView, verifiedView, shippedView, shippingLL;

    private Button activity_order_detailed_btn_checkout, browseProductsBtn;
    private LinearLayout afterFeedBackLL, rattingView, addPromoLL, layoutScheduleOrder;

    RecyclerView OrderItemsRatingRecyclerView;

//    private SwitchCompat  walletSwitch;
//    private LinearLayout  walletLL;

    List<PlaceOrder.Cart> cartListPlaceOrder = new ArrayList<>();
    private RequestPermissionHandler mRequestPermissionHandler;
    private boolean permissonCheck = false;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    private final static int CAMERA_RQ = 6969;
    private APIService service;
    private Retrofit retrofit;
    private String filePath;
    private File finalFile;
    private Uri picUri = null;
    private ConstraintLayout imageCL;
    private Gson gson;
    private YourOrderDetail orderDetail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        mRequestPermissionHandler = new RequestPermissionHandler();
        setContentView(R.layout.activity_order_detail);


        initMembers();


    }

    private void initMembers() {
        isArabic = AppController.setLocale();
        order_id = getIntent().getStringExtra("order_id");
        order_number = getIntent().getStringExtra("order_number");

        TextView tv_items = findViewById(R.id.tv_items);

        rattingView = findViewById(R.id.rattingView);
        afterFeedBackLL = findViewById(R.id.afterFeedBackLL);
        layoutScheduleOrder = findViewById(R.id.layoutScheduleOrder);
        orderTracking = findViewById(R.id.orderTracking);
        viewInvoice = findViewById(R.id.viewInvoice);


        afterFeedBackLL.setVisibility(View.GONE);
        layoutScheduleOrder.setVisibility(View.GONE);
        rattingView.setVisibility(View.VISIBLE);


        okhttp3.OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient.addNetworkInterceptor(new AddHeaderInterceptorForImg());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        service = retrofit.create(APIService.class);

        TextView tv_paymentoptions = findViewById(R.id.tv_paymentoptions);

        TextView tv_da = findViewById(R.id.tv_da);
        TextView tv_dt = findViewById(R.id.tv_dt);
        TextView tv_so = findViewById(R.id.tv_so);


        //TODO change hardcoded client ID
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        getOrderDetails(user.userId, order_id);

        Log.d("orderDetailReq", new Gson().toJson(new OrderRequest(user.userId, order_id)));
        initViews();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (isArabic) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        createGson();
    }

    private void initViews() {
        orderdetailItemsList = findViewById(R.id.orderdetailItemsList);
        OrderItemsRatingRecyclerView = findViewById(R.id.OrderItemsRatingRecyclerView);

        //Toolbar

        toolbar = findViewById(R.id.toolbar);
        toolbar.setEnabled(true);
        imageViewBackButton = toolbar.findViewById(R.id.imageViewBackButton);
        imageViewBackButton.setVisibility(View.VISIBLE);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitleTCA);
        layoutPromocodeB = findViewById(R.id.layoutPromocodeB);
        layoutPromocodeA = findViewById(R.id.layoutPromocodeA);
        tvToolbarTitle.setText(getResources().getString(R.string.Order_Details));

        ratingview = findViewById(R.id.ratingview);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvReOrder = findViewById(R.id.tvReOrder);
        uploadPaymentReceiptTV = findViewById(R.id.uploadPaymentReceiptTV);
        uploadPaymentReceiptTV.setVisibility(View.GONE);
        selectImageTV = findViewById(R.id.selectImageTV);
        imageLL = findViewById(R.id.imageLL);

        placedLayout = findViewById(R.id.placedLayout);
        verifiedLayout = findViewById(R.id.verifiedLayout);
        shippedLayout = findViewById(R.id.shippedLayout);
        deliveredLayout = findViewById(R.id.deliveredLayout);


        placedCheckMarkIV = findViewById(R.id.placedCheckMarkIV);
        verifiedCheckMarkIV = findViewById(R.id.verifiedCheckMarkIV);
        shippedCheckMarkIV = findViewById(R.id.shippedCheckMarkIV);
        deliveredCheckMarkIV = findViewById(R.id.deliveredCheckMarkIV);


        placedView = findViewById(R.id.placedView);
        verifiedView = findViewById(R.id.verifiedView);
        shippedView = findViewById(R.id.shippedView);
        shippingLL = findViewById(R.id.shippingLL);
        textview_location = findViewById(R.id.textview_location);

        imageCL = findViewById(R.id.imageCL);
        uploadedIV = findViewById(R.id.uploadedIV);
        removeIV = findViewById(R.id.removeIV);


        activity_order_detailed_btn_checkout = findViewById(R.id.activity_order_detailed_btn_checkout);
        browseProductsBtn = findViewById(R.id.browseProductsBtn);
        activity_order_detailed_btn_checkout.setVisibility(View.GONE);
        textview_location.setVisibility(View.GONE);

        browseProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        wallet_view = findViewById(R.id.wallet_view);
        radioButtonMorning = findViewById(R.id.radioButtonMorning);
        radioButtonEvening = findViewById(R.id.radioButtonEvening);
        radioButtonAnytime = findViewById(R.id.radioButtonAnytime);
        llOrderVerified = findViewById(R.id.layoutOrderVerified);
        llOrderShipped = findViewById(R.id.layoutShipped);
        rlPayementDetailView = findViewById(R.id.paymentDetailView);
        llDelivered = findViewById(R.id.layoutDelivered);
        tvTransactionIdApplePay = findViewById(R.id.tvTransactionIdApplePay);
        tvTransactionId = findViewById(R.id.tvTransactionId);
        //llStepper=findViewById(R.id.orderdetailUpdated_llStepper);
        imageviewPlaced = findViewById(R.id.imageviewPlaced);
        imageviewVerified = findViewById(R.id.imageviewVerified);
        imageViewShipped = findViewById(R.id.imageViewShipped);
        imageViewDelivered = findViewById(R.id.imageViewDelivered);
        tvWalletDiscountValue = findViewById(R.id.tvWalletDiscountValue);

        tvOrderPlaced = findViewById(R.id.tvOrderPlaced);
        tvVerified = findViewById(R.id.tvVerified);
        tvShipped = findViewById(R.id.tvShipped);
        tvDelivered = findViewById(R.id.tvDelivered);

//        viewOrderPlaced=findViewById(R.id.view_orderplaced);
//        viewVerified=findViewById(R.id.view_verified);
//        viewShipped=findViewById(R.id.view_shipped);

        textview_addpromocode = findViewById(R.id.tvAddpromoCode);
        addPromoLL = findViewById(R.id.addPromoLL);
        textview_addpromocode.setEnabled(false);
        textview_addpromocode.setVisibility(View.GONE);
        addPromoLL.setEnabled(false);
        addPromoLL.setAlpha(0.3f);
        //below RecyclerView

        tvPaymentType = findViewById(R.id.tvPaymentType);
        tvChangePaymentMethod = findViewById(R.id.paymentOptionView_tvChange);
        tvChangePaymentMethod.setOnClickListener(this);
        tvChangePaymentMethod.setVisibility(View.GONE);
        tvDeliveryAddress = findViewById(R.id.tvDeliveryAddress);
        tvChangeAddress = findViewById(R.id.deliveryAddressView_tvChangeAddress);
        tvChangeAddress.setOnClickListener(this);
        tvChangeAddress.setVisibility(View.GONE);

        radioButtonScheduleNone = findViewById(R.id.radioButtonScheduleNone);
        radioButtonScheduleWeekly = findViewById(R.id.radioButtonScheduleWeekly);
        radioButtonScheduleEveryMonth = findViewById(R.id.radioButtonScheduleEveryMonth);
        rlPromoView = findViewById(R.id.promoView_layout);
        editTextPromocode = findViewById(R.id.editTextPromocode);

        //bottom Payment Details
        tvOrderItems = findViewById(R.id.textview_orderitemsvalue);
        tvReturns = findViewById(R.id.tvReturns);
        tvShippingCharges = findViewById(R.id.tvShippingCharges);
        tv_please_rate = findViewById(R.id.tv_please_rate);
        tvVAT = findViewById(R.id.textview_vatvalue);
        tvPromocodeValueB = findViewById(R.id.tvPromocodeValueB);
        tvPromocodeValueA = findViewById(R.id.tvPromocodeValueA);
        tvWallet = findViewById(R.id.textview_walletvalue);
        tvGrandTotal = findViewById(R.id.textview_totalvalue);
        layoutWalletDiscount = findViewById(R.id.layoutWalletDiscount);

        //Order Summary
        orderSummaryRecyclerView = findViewById(R.id.orderSummaryRecyclerView);
        orderSummarytvPaymentType = findViewById(R.id.orderSummarytvPaymentType);
        tvOrderSummery = findViewById(R.id.tvOrderSummery);
        tvOrderSummeryOD = findViewById(R.id.tvOrderSummeryOD);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        viewStatus = findViewById(R.id.viewStatus);
        tvUseWallet = findViewById(R.id.layout_order_summary_tvUseWallet);
        tvSummaryAddress = findViewById(R.id.layout_order_summary_tvAddress);
        tvSummaryDeliveryTime = findViewById(R.id.layout_order_summary_tvDeliveryTime);
        tvSummarySchedule = findViewById(R.id.layout_order_summary_tvScheduleOrder);
        editUserRemarks = findViewById(R.id.editUserRemarks);

        orderdetailLayoutmain = findViewById(R.id.orderdetailLayoutmain);
        layoutQitaf = findViewById(R.id.layoutQitaf);
        tv_qitaf_rewardpoints = findViewById(R.id.tv_qitaf_rewardpoints);
        layoutNoDataFound = findViewById(R.id.layoutNoDataFound);
        orderdetailcircleview = findViewById(R.id.orderdetailcircleview);
        tvDevliveryTime = findViewById(R.id.tvDevliveryTime);
        tvDevliveryMan = findViewById(R.id.tvDevliveryMan);
        ProductQuality = findViewById(R.id.layout_order_delivered_retingProductQuality);
        DeliveryTime = findViewById(R.id.layout_order_delivered_retingDeliveryTime);
        retingDeliveryMan = findViewById(R.id.retingDeliveryMan);
        layoutProductQuality = findViewById(R.id.layoutProductQuality);
        tvSubmitRating = findViewById(R.id.tvSubmitRating);
        orderDetaiMain = findViewById(R.id.order_detai_rlmain);
        //orderDetaiMain.setVisibility(View.GONE);
        orderdetailLayoutmain.setVisibility(View.GONE);
        tvOrderSummery.setOnClickListener(this);
        tvOrderSummeryOD.setOnClickListener(this);
        tvReturns.setOnClickListener(this);
        tvReturns.setVisibility(View.GONE);


//        couponsTV = findViewById(R.id.couponsTV);
//        couponsSwitch = findViewById(R.id.couponsSwitch);
//        walletSwitch = findViewById(R.id.walletSwitch);
//        walletTV = findViewById(R.id.walletTV);
////        couponsLLMain = findViewById(R.id.couponsLLMain);
////        couponsLL = findViewById(R.id.couponsLL);
//        walletLL = findViewById(R.id.walletLL);
//
//
////        couponsLLMain.setEnabled(false);
////
////        couponsSwitch.setEnabled(false);
//        walletSwitch.setEnabled(false);


        tvReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Log.d("**orderWeight", "weight:  " + orderDetail.getWeight());
//                    product.getSpecifications().setWeight(orderDetail.getWeight());
                    if (orderDetail.getProd_curr_status().equals("1")
                            && (orderDetail.getFreeGoods().equals("0") || orderDetail.getFreeGoods().equals(""))
                            && !orderDetail.getDishPrice().equals("0.00") && !orderDetail.getOrdCount().equals("0"))
                        Cart.addToCartReorder(product, -1, Integer.parseInt(orderDetail.getOrdCount()), orderDetail.getWeight());
                }
                Intent intent = new Intent(OrderDetails.this, CheckOutActivity.class);
                startActivityForResult(intent, ORDERS_SCREEN);
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                if (ProductsSingleton.getInstance().getProductByLocations() != null) {
                    updateCart();
                }
                finish();

            }
        });

        if (user != null) {
            if (user.hide_price.equals("1")) {
                ((LinearLayout) findViewById(R.id.paymentDetailView)).setVisibility(View.GONE);
            }
        }
    }

    private void updateCart() {
        List<Cart> cartList;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        for (int j = 0; j < cartList.size(); j++) {
            boolean cartCheck = true;
            for (int k = 0; k < ProductsSingleton.getInstance().getProductByLocations().size(); k++) {
                Log.d("cartitemcount", "" + ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString() + "," + cartList.get(j).productId.toString());

                if (ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString().equals(cartList.get(j).productId.toString())) {
                    Log.d("cartitemcount", cartList.get(j).count + "");
                    Cart.addMultipleItem(ProductsSingleton.getInstance().getProductByLocations().get(k).getId(), cartList.get(j).count, ProductsSingleton.getInstance().getProductByLocations().get(k));
                    cartCheck = false;
                }
            }
            if (cartCheck) {
                Log.d("cartitemcount", "....." + cartCheck);
                Cart.deleteProductFromCart(cartList.get(j).productId);
            }
        }


        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.hide_price.equals("1")) {
                ProductsSingleton.getInstance().getTv_cartamount().setVisibility(View.GONE);
            }
        }
    }

    private void getOrderDetails(String clientID, final String orderID) {
        showProgress(true);
        APIManager.getInstance().getOrderDetails(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    try {
                        if (new JSONObject(response).getString("order").equals("false") || new JSONObject(response).optString("Code").equals("403")) {
                            orderdetailLayoutmain.setVisibility(View.GONE);
                            layoutNoDataFound.setVisibility(View.VISIBLE);
                        } else {
                            orderDetail = new Gson().fromJson(response, YourOrderDetail.class);
                            orderDetailList = orderDetail.getOrder().getOrderDetail();
                            setOrderDetailsViews(orderDetail);
                            saveOrderRating(orderDetail.getOrder().getOrdId());
                            orderdetailLayoutmain.setVisibility(View.VISIBLE);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResult: " + e.getMessage());
                        orderdetailLayoutmain.setVisibility(View.GONE);
                        layoutNoDataFound.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(OrderDetails.this, "No Detail Found", Toast.LENGTH_SHORT).show();
                }
            }
        }, new OrderRequest(clientID, orderID, ""));
    }

    @SuppressLint("SetTextI18n")
    private void setOrderDetailsViews(YourOrderDetail orderDetail) {
        if (orderDetail.getOrder().getCurrentStatus().equals("5") || orderDetail.getOrder().getCurrentStatus().equals("2")) {
            llOrderVerified.setVisibility(View.VISIBLE);
            llOrderShipped.setVisibility(View.GONE);
            llDelivered.setVisibility(View.GONE);
            tvOrderID.setTextColor(getResources().getColor(R.color.tab_selected));
            tvReOrder.setTextColor(getResources().getColor(R.color.tab_selected));
            tvTransactionId.setTextColor(getResources().getColor(R.color.tab_selected));
            tvTransactionIdApplePay.setTextColor(getResources().getColor(R.color.tab_selected));
            orderDetaiMain.setBackgroundColor(getResources().getColor(R.color.grey));

             /*imageviewPlaced.setImageResource(R.drawable.ic_placed);
             tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimary));
//             viewOrderPlaced.setBackgroundColor(getResources().getColor(R.color.tab_selected));
             placedLayout.setAlpha(1.0f);
             verifiedLayout.setAlpha(1.0f);
             shippedLayout.setAlpha(0.4f);
             deliveredLayout.setAlpha(0.4f);

             placedView.setVisibility(View.GONE);
             verifiedView.setVisibility(View.VISIBLE);
             shippedView.setVisibility(View.GONE);

             verifiedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
             placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
             shippedCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);
             deliveredCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);*/

            if (orderDetail.getOrder().getCurrentStatus().equals("5")) {
                imageviewVerified.setImageResource(R.drawable.ic_verified);
                tvVerified.setTextColor(getResources().getColor(R.color.colorPrimary));


                placedLayout.setAlpha(1.0f);
                verifiedLayout.setAlpha(0.4f);
                shippedLayout.setAlpha(0.4f);
                deliveredLayout.setAlpha(0.4f);

                placedView.setVisibility(View.VISIBLE);
                verifiedView.setVisibility(View.GONE);
                shippedView.setVisibility(View.GONE);

                placedLayout.setBackground(getResources().getDrawable(R.drawable.placed_white_bg_en));

                placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
                verifiedCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);
                shippedCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);
                deliveredCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);


                if (!orderDetail.getOrder().getTracking_link().equals("") || orderDetail.getOrder().getTracking_link().equals("null")) {
                    orderTracking.setVisibility(View.VISIBLE);
                } else {
                    orderTracking.setVisibility(View.GONE);
                }
            } else if (orderDetail.getOrder().getCurrentStatus().equals("2")) {
                imageviewVerified.setImageResource(R.drawable.ic_verified);
                tvVerified.setTextColor(getResources().getColor(R.color.colorPrimary));
                placedLayout.setAlpha(1.0f);
                verifiedLayout.setAlpha(1.0f);
                shippedLayout.setAlpha(0.4f);
                deliveredLayout.setAlpha(0.4f);

                placedView.setVisibility(View.GONE);
                verifiedView.setVisibility(View.VISIBLE);
                shippedView.setVisibility(View.GONE);

                placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
                verifiedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
                shippedCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);
                deliveredCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);


                if (!orderDetail.getOrder().getTracking_link().equals("") || orderDetail.getOrder().getTracking_link().equals("null")) {
                    orderTracking.setVisibility(View.VISIBLE);
                } else {
                    orderTracking.setVisibility(View.GONE);

                }
            }

           /*  imageViewShipped.setImageResource(R.drawable.ic_shipped);
             imageViewDelivered.setImageResource(R.drawable.ic_delivered);
             tvShipped.setTextColor(getResources().getColor(R.color.colorPrimary));
             tvDelivered.setTextColor(getResources().getColor(R.color.colorPrimary));

             placedLayout.setAlpha(1.0f);
             verifiedLayout.setAlpha(1.0f);
             shippedLayout.setAlpha(0.4f);
             deliveredLayout.setAlpha(0.4f);

             verifiedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
             placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
             shippedCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);
             deliveredCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);


             placedView.setVisibility(View.GONE);
             verifiedView.setVisibility(View.VISIBLE);
             shippedView.setVisibility(View.GONE);*/

        }

        if (orderDetail.getOrder().getCurrentStatus().equals("3")) {
            llOrderVerified.setVisibility(View.GONE);
            llOrderShipped.setVisibility(View.VISIBLE);
            orderDetaiMain.setBackgroundColor(getResources().getColor(R.color.grey));
            llDelivered.setVisibility(View.GONE);
            tvOrderID.setTextColor(getResources().getColor(R.color.black));
            tvReOrder.setTextColor(getResources().getColor(R.color.black));
            tvTransactionId.setTextColor(getResources().getColor(R.color.black));
            tvTransactionIdApplePay.setTextColor(getResources().getColor(R.color.black));
//            orderDetaiMain.setBackground(getResources().getDrawable(R.drawable.jazeriah_bg));
            imageviewPlaced.setImageResource(R.drawable.ic_placed);
            imageviewVerified.setImageResource(R.drawable.ic_verified);
            imageViewShipped.setImageResource(R.drawable.ic_shipped);
            imageViewDelivered.setImageResource(R.drawable.ic_delivered);

            tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvVerified.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvShipped.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvDelivered.setTextColor(getResources().getColor(R.color.colorPrimary));

            placedLayout.setAlpha(1.0f);
            verifiedLayout.setAlpha(1.0f);
            shippedLayout.setAlpha(1.0f);
            deliveredLayout.setAlpha(0.4f);


            placedView.setVisibility(View.GONE);
            verifiedView.setVisibility(View.GONE);
            shippedView.setVisibility(View.VISIBLE);


            placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            verifiedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            shippedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            deliveredCheckMarkIV.setImageResource(R.drawable.ic_uncheck_circle);

            if (orderDetail.getOrder().getTracking_link().contains("") || orderDetail.getOrder().getTracking_link().contains("null")) {
                orderTracking.setVisibility(View.GONE);
            } else {
                orderTracking.setVisibility(View.VISIBLE);
            }

        } else if (orderDetail.getOrder().getCurrentStatus().equals("4")) {
            llOrderVerified.setVisibility(View.GONE);
            llOrderShipped.setVisibility(View.GONE);
            llDelivered.setVisibility(View.VISIBLE);

            if (orderDetail.getOrder().getEnable_return_sales().equals("1")) {
                tvReturns.setVisibility(View.VISIBLE);
            } else {
                tvReturns.setVisibility(View.GONE);
            }
            tvOrderID.setTextColor(getResources().getColor(R.color.black));
            tvReOrder.setTextColor(getResources().getColor(R.color.black));
            tvTransactionId.setTextColor(getResources().getColor(R.color.black));
            tvTransactionIdApplePay.setTextColor(getResources().getColor(R.color.black));
            rlPayementDetailView.setVisibility(View.GONE);
            imageviewPlaced.setImageResource(R.drawable.ic_placed);
            imageviewVerified.setImageResource(R.drawable.ic_verified);
            imageViewShipped.setImageResource(R.drawable.ic_shipped);
            imageViewDelivered.setImageResource(R.drawable.ic_delivered);

            tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvVerified.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvShipped.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvDelivered.setTextColor(getResources().getColor(R.color.colorPrimary));

            placedLayout.setAlpha(1.0f);
            verifiedLayout.setAlpha(1.0f);
            shippedLayout.setAlpha(1.0f);
            deliveredLayout.setAlpha(1.0f);

            placedView.setVisibility(View.GONE);
            verifiedView.setVisibility(View.GONE);
            shippedView.setVisibility(View.GONE);

            verifiedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            placedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            shippedCheckMarkIV.setImageResource(R.drawable.ic_check_circle);
            deliveredCheckMarkIV.setImageResource(R.drawable.ic_check_circle);


            orderTracking.setVisibility(View.GONE);

            if (orderDetail.getOrder().getQitaf_rewardpoints().equals("0") || orderDetail.getOrder().getQitaf_rewardpoints().equals("")) {
                layoutQitaf.setVisibility(View.GONE);
            } else {

                tv_qitaf_rewardpoints.setText(orderDetail.getOrder().getQitaf_rewardpoints() + " " + getResources().getString(R.string.st_qitaf_rewardpoints_points));

            }
/*
             tvOrderSummery.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });*/


        }

        if (orderDetail.getOrder().getCurrentStatus().equals("3") || orderDetail.getOrder().getCurrentStatus().equals("2") ||
                orderDetail.getOrder().getCurrentStatus().equals("5") || orderDetail.getOrder().getCurrentStatus().equals("4")) {
            orderdetailcircleview.setVisibility(View.VISIBLE);
            tvOrderStatus.setVisibility(View.GONE);
            selectImageTV.setVisibility(View.GONE);
            tvTransactionId.setVisibility(View.GONE);
            imageLL.setVisibility(View.GONE);
            viewStatus.setVisibility(View.GONE);
            Log.d("**image","ImaR");

        }
        else {
            orderdetailcircleview.setVisibility(View.GONE);
            tvOrderStatus.setVisibility(View.VISIBLE);
            selectImageTV.setVisibility(View.VISIBLE);
            tvTransactionId.setVisibility(View.VISIBLE);
            imageLL.setVisibility(View.VISIBLE);
            viewStatus.setVisibility(View.VISIBLE);
            if (isArabic) {
                tvOrderStatus.setText(orderDetail.getOrder().getOrderStatusAr());
                if (orderDetail.getOrder().getPaymentTypeAr().equals("تحويل بنكي")) {
                    if (orderDetail.getOrder().getCurrentStatus().equals("6")) {
                        selectImageTV.setVisibility(View.GONE);
                        uploadPaymentReceiptTV.setVisibility(View.GONE);
                        imageLL.setVisibility(View.GONE);
                        removeIV.setVisibility(View.GONE);
                    } else {
                        if (orderDetail.getOrder().getBank_receipt().equals("")) {
                            if (orderDetail.getOrder().getOrderStatusAr().toLowerCase().toString().equals("" + getResources().getString(R.string.st_payment_pending))) {
                                selectImageTV.setVisibility(View.VISIBLE);
                                removeIV.setVisibility(View.VISIBLE);
                            }

                            imageLL.setVisibility(View.VISIBLE);
                            removeIV.setVisibility(View.GONE);
                            tvTransactionId.setVisibility(View.VISIBLE);

                        }

                    }
                } else {
                    imageLL.setVisibility(View.GONE);
                }

            }
            else {
                tvOrderStatus.setText(orderDetail.getOrder().getOrderStatusEn());
                if (orderDetail.getOrder().getPaymentTypeEn().equals("Bank Transfer")) {
                    if (orderDetail.getOrder().getCurrentStatus().equals("6")) {
                        selectImageTV.setVisibility(View.GONE);
                        uploadPaymentReceiptTV.setVisibility(View.GONE);
                        imageLL.setVisibility(View.GONE);
                        removeIV.setVisibility(View.GONE);
                    } else {
                        if (orderDetail.getOrder().getBank_receipt().equals("")) {
                            if (orderDetail.getOrder().getOrderStatusAr().toLowerCase().toString().equals("" + getResources().getString(R.string.st_payment_pending))) {
                                selectImageTV.setVisibility(View.VISIBLE);
                                removeIV.setVisibility(View.VISIBLE);
                            }

                            imageLL.setVisibility(View.VISIBLE);
                            removeIV.setVisibility(View.GONE);
                            tvTransactionId.setVisibility(View.VISIBLE);

                        }

                    }
                } else {
                    imageLL.setVisibility(View.GONE);
                }
            }



            if (isArabic) {
                if (orderDetail.getOrder().getPaymentTypeAr().equals("تحويل بنكي")) {
                    if (!orderDetail.getOrder().getBank_receipt().equals("")) {
                        uploadedIV.setVisibility(View.VISIBLE);
                        removeIV.setVisibility(View.GONE);
                    }
                }
            }
            else {
                if (orderDetail.getOrder().getPaymentTypeEn().equals("Bank Transfer")) {
                    if (!orderDetail.getOrder().getBank_receipt().equals("")) {
                        uploadedIV.setVisibility(View.VISIBLE);
                        removeIV.setVisibility(View.GONE);
                    }
                }
            }
        }




        tvOrderID.setText(getResources().getString(R.string.st_ordernumber) + orderDetail.getOrder().getOrdId());

        if (orderDetail.getOrder().getTracking_link().contains("") || orderDetail.getOrder().getTracking_link().contains("null")) {
            orderTracking.setVisibility(View.GONE);
        } else {
            orderTracking.setVisibility(View.VISIBLE);
        }


        if (orderDetail.getOrder().getIsreorderable().equals("1")) {
            tvReOrder.setVisibility(View.VISIBLE);

        }
        if (!orderDetail.getOrder().getBank_receipt().equals("")) {
            uploadedIV.setVisibility(View.VISIBLE);
            imageCL.setVisibility(View.VISIBLE);
            removeIV.setVisibility(View.VISIBLE);
            uploadedIV.setVisibility(View.VISIBLE);
            selectImageTV.setVisibility(View.GONE);
            uploadPaymentReceiptTV.setVisibility(View.GONE);
            Glide.with(this)
                    .load(IMAGE_BASE_URL + "" + orderDetail.getOrder().getBank_receipt())
                    .placeholder(R.drawable.app_icon)
                    .into(uploadedIV);
        } else {
            imageCL.setVisibility(View.GONE);
        }

        if (isArabic) {
            if (orderDetail.getOrder().getPaymentTypeAr().equals("تحويل بنكي")) {
                if (!orderDetail.getOrder().getBank_receipt().equals("")) {
                    Glide.with(this)
                            .load(IMAGE_BASE_URL + "" + orderDetail.getOrder().getBank_receipt())
                            .placeholder(R.drawable.app_icon)
                            .into(uploadedIV);
                    uploadedIV.setVisibility(View.VISIBLE);

                    imageCL.setVisibility(View.VISIBLE);
                    imageLL.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.GONE);
                    Log.d("**image","ImageAR");

                }
            }
        }
        else {
            if (orderDetail.getOrder().getPaymentTypeEn().equals("Bank Transfer")) {
                if (!orderDetail.getOrder().getBank_receipt().equals("")) {
                    Glide.with(this)
                            .load(IMAGE_BASE_URL + "" + orderDetail.getOrder().getBank_receipt())
                            .placeholder(R.drawable.app_icon)
                            .into(uploadedIV);
                    uploadedIV.setVisibility(View.VISIBLE);

                    imageCL.setVisibility(View.VISIBLE);
                    imageLL.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.GONE);
                    Log.d("**image","ImageEN");
                }
            }
        }


        uploadedIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog = new Dialog(OrderDetails.this);
                showImageDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                showImageDialog.getWindow().setGravity(Gravity.CENTER);
                showImageDialog.setCancelable(false);
                showImageDialog.setContentView(R.layout.show_pic_layout);

                ImageView imageViewSPL = showImageDialog.findViewById(R.id.imageViewSPL);
                ImageView cancelDialogIV = showImageDialog.findViewById(R.id.cancelDialogIV);

                imageViewSPL.setOnTouchListener(new ImageMatrixTouchHandler(imageViewSPL.getContext()));

                Glide.with(OrderDetails.this)
                        .load(IMAGE_BASE_URL + "" + orderDetail.getOrder().getBank_receipt())
                        .placeholder(R.drawable.app_icon)
                        .into(imageViewSPL);
                imageViewSPL.setOnTouchListener(new ImageMatrixTouchHandler(imageViewSPL.getContext()));


                cancelDialogIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showImageDialog.dismiss();
                    }
                });
                showImageDialog.show();
            }
        });

        if (!orderDetail.getOrder().getPaymentTransaction().equals("")) {
            tvTransactionId.setText("" + getResources().getString(R.string.st_transaction_created) + ":" + orderDetail.getOrder().getPaymentTransaction());
            tvTransactionId.setVisibility(View.VISIBLE);
        }

      /*   if (!orderDetail.getOrder().getApple_pay_trans().equals("")) {
             tvTransactionIdApplePay.setText("Apple Pay "+getResources().getString(R.string.st_transaction_created)+":\n" + orderDetail.getOrder().getApple_pay_trans());
             tvTransactionIdApplePay.setVisibility(View.VISIBLE);
         }*/

        orderdetailItemsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderAdapter = new YourOrderDetailRecycler(this, orderDetailList);
        orderdetailItemsList.setAdapter(orderAdapter);


        if (isArabic) {
            tvPaymentType.setText(orderDetail.getOrder().getPaymentTypeAr());
        } else {
            tvPaymentType.setText(orderDetail.getOrder().getPaymentTypeEn());
        }

//        walletSwitch.setEnabled(false);

/*
        if (Double.parseDouble(orderDetail.getOrder().getWalletAmount()) > 0) {
            walletSwitch.setChecked(true);

        } else {
            walletSwitch.setChecked(false);
            walletSwitch.setEnabled(false);
        }*/


//        couponsLLMain.setVisibility(View.VISIBLE);
//        couponsTV.setText(getResources().getString(R.string.use_coupons) + "  (" + orderDetail.getOrder().getCoupon_qty() + "" + ")");
//        walletTV.setText(getResources().getString(R.string.st_usewallet) + "  (" + orderDetail.getOrder().getWalletAmount() + "" + ")");

       /* if (orderDetail.getOrder().getCoupon_qty() == null || orderDetail.getOrder().getCoupon_qty().equals("NULL") ||
                Double.parseDouble(orderDetail.getOrder().getCoupon_qty()) == 0 || orderDetail.getOrder().getCoupon_qty().equals("")) {
            couponsSwitch.setChecked(false);
            couponsSwitch.setEnabled(false);
            couponsTV.setText(getResources().getString(R.string.use_coupons) + "  (" +"0" + ")");
        } else if (Double.parseDouble(orderDetail.getOrder().getCoupon_qty()) > 0) {

            couponsSwitch.setChecked(true);
        }*/

        tvDeliveryAddress.setText(orderDetail.getOrder().getAddress());
        radioButtonMorning.setEnabled(false);
        radioButtonEvening.setEnabled(false);
        radioButtonAnytime.setEnabled(false);
        radioButtonScheduleNone.setEnabled(false);
        radioButtonScheduleWeekly.setEnabled(false);
        radioButtonScheduleEveryMonth.setEnabled(false);
        if (orderDetail.getOrder().getPreferedTime().equals("1")) {
            radioButtonMorning.setChecked(true);
            radioButtonMorning.setVisibility(View.VISIBLE);
            radioButtonEvening.setVisibility(View.GONE);
            radioButtonAnytime.setVisibility(View.GONE);
        } else if (orderDetail.getOrder().getPreferedTime().equals("2")) {
            radioButtonEvening.setChecked(true);
            radioButtonMorning.setVisibility(View.GONE);
            radioButtonEvening.setVisibility(View.VISIBLE);
            radioButtonAnytime.setVisibility(View.GONE);

        } else if (orderDetail.getOrder().getPreferedTime().equals("3")) {
            radioButtonAnytime.setChecked(true);
            radioButtonMorning.setVisibility(View.GONE);
            radioButtonEvening.setVisibility(View.GONE);
            radioButtonAnytime.setVisibility(View.VISIBLE);
        }

        if (orderDetail.getOrder().getRecurring().equals("0")) {
            radioButtonScheduleNone.setChecked(true);
            radioButtonScheduleNone.setVisibility(View.VISIBLE);
            radioButtonScheduleWeekly.setVisibility(View.GONE);
            radioButtonScheduleEveryMonth.setVisibility(View.GONE);
        } else if (orderDetail.getOrder().getRecurring().equals("2")) {
            radioButtonScheduleWeekly.setChecked(true);
            radioButtonScheduleNone.setVisibility(View.GONE);
            radioButtonScheduleWeekly.setVisibility(View.VISIBLE);
            radioButtonScheduleEveryMonth.setVisibility(View.GONE);
        } else if (orderDetail.getOrder().getRecurring().equals("3")) {
            radioButtonScheduleEveryMonth.setChecked(true);
            radioButtonScheduleNone.setVisibility(View.GONE);
            radioButtonScheduleWeekly.setVisibility(View.GONE);
            radioButtonScheduleEveryMonth.setVisibility(View.VISIBLE);
        }

        if (orderDetail.getOrder().getPromocode() != null) {
            editTextPromocode.setText(orderDetail.getOrder().getPromocode());
            editTextPromocode.setEnabled(false);
        } else {
            rlPromoView.setVisibility(View.GONE);
        }

        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderSummaryRecyclerView.setAdapter(new OrderSummaryRecycler(orderDetailList));

        if (isArabic) {
            orderSummarytvPaymentType.setText(orderDetail.getOrder().getPaymentTypeAr());
            tvUseWallet.setVisibility(View.GONE);
            tvSummaryAddress.setText(orderDetail.getOrder().getAddress());
            tvSummaryDeliveryTime.setText(orderDetail.getOrder().getDeliveryTimeAr());
            tvSummarySchedule.setText(getResources().getString(R.string.st_none));
            if (orderDetail.getOrder().getPaymentTypeAr().equals("تحويل بنكي")) {
                if (orderDetail.getOrder().getBank_receipt().equals("")) {
                    if (orderDetail.getOrder().getOrderStatusAr().toLowerCase().toString().equals("" + getResources().getString(R.string.st_payment_pending))) {
                        selectImageTV.setVisibility(View.VISIBLE);
                        removeIV.setVisibility(View.VISIBLE);
                    }

                    imageLL.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.GONE);
                    tvTransactionId.setVisibility(View.VISIBLE);

                }
            }

        } else {
            orderSummarytvPaymentType.setText(orderDetail.getOrder().getPaymentTypeEn());
            tvUseWallet.setVisibility(View.GONE);
            tvSummaryAddress.setText(orderDetail.getOrder().getAddress());
            tvSummaryDeliveryTime.setText(orderDetail.getOrder().getDeliveryTimeEn());
            tvSummarySchedule.setText(getResources().getString(R.string.st_none));
            if (orderDetail.getOrder().getPaymentTypeEn().equals("Bank Transfer")) {
                if (orderDetail.getOrder().getBank_receipt().equals("")) {
                    if (orderDetail.getOrder().getOrderStatusAr().toLowerCase().toString().equals("" + getResources().getString(R.string.st_payment_pending))) {
                        selectImageTV.setVisibility(View.VISIBLE);
                        removeIV.setVisibility(View.VISIBLE);
                    }
                    removeIV.setVisibility(View.GONE);
                    imageLL.setVisibility(View.VISIBLE);
                    tvTransactionId.setVisibility(View.VISIBLE);
                }
            }
        }


        selectImageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissonCheck) {
                    addImage();

                } else {
                    handlePermissions();
                }

               /* if (!hasPermissions(OrderDetails.this, PERMISSIONS)) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + getPackageName()));
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.allow_all_permissions), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addImage();
                }*/

//                uploadReceipt();
            }
        });
        uploadPaymentReceiptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                uploadReceipt();
            }
        });

        removeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                removeReceipt();
            }
        });


        if (orderDetail.getOrder().getQitaf_rewardpoints().equals("0") || orderDetail.getOrder().getQitaf_rewardpoints().equals("")) {
            layoutQitaf.setVisibility(View.GONE);
        } else {
            tv_qitaf_rewardpoints.setText(orderDetail.getOrder().getQitaf_rewardpoints() + " " + getResources().getString(R.string.st_qitaf_rewardpoints_points));
        }

        double deliveryCharges = Double.parseDouble(orderDetail.getOrder().getOrdDeliveryCharge());

        if (deliveryCharges > 0) {
            shippingLL.setVisibility(View.VISIBLE);
        } else {
            shippingLL.setVisibility(View.GONE);
        }
        if (isArabic) {
            tvOrderItems.setText(convertString(orderDetail.getOrder().getPriceWithoutVat()) + " " + getResources().getString(R.string.currency));
            tvShippingCharges.setText(convertString(orderDetail.getOrder().getOrdDeliveryCharge()) + " " + getResources().getString(R.string.currency));
            tvVAT.setText(convertString(orderDetail.getOrder().getAmountVat()) + " " + getResources().getString(R.string.currency));
            tvWalletDiscountValue.setText("-" + convertString(orderDetail.getOrder().getWallet_discount()) + " " + getResources().getString(R.string.currency));
            tvWallet.setText("-" + convertString(orderDetail.getOrder().getWalletAmount()) + " " + getResources().getString(R.string.currency));
            tvGrandTotal.setText(convertString(orderDetail.getOrder().getGrandTotal()) + " " + getResources().getString(R.string.currency));
            if (orderDetail.getOrder().getAfter_vat().equals("1"))
                tvPromocodeValueA.setText("-" + convertString(orderDetail.getOrder().getDiscountAmount()) + " " + getResources().getString(R.string.currency));
            else
                tvPromocodeValueB.setText("-" + convertString(orderDetail.getOrder().getDiscountAmount()) + " " + getResources().getString(R.string.currency));

        } else {
            tvOrderItems.setText(orderDetail.getOrder().getPriceWithoutVat() + " " + getResources().getString(R.string.currency));
            tvShippingCharges.setText(convertString(orderDetail.getOrder().getOrdDeliveryCharge()) + " " + getResources().getString(R.string.currency));
            tvVAT.setText(orderDetail.getOrder().getAmountVat() + " " + getResources().getString(R.string.currency));
            tvWalletDiscountValue.setText("-" + orderDetail.getOrder().getWallet_discount() + " " + getResources().getString(R.string.currency));
            tvWallet.setText("-" + orderDetail.getOrder().getWalletAmount() + " " + getResources().getString(R.string.currency));
            tvGrandTotal.setText(orderDetail.getOrder().getGrandTotal() + " " + getResources().getString(R.string.currency));
            if (orderDetail.getOrder().getAfter_vat().equals("1"))
                tvPromocodeValueA.setText("-" + orderDetail.getOrder().getDiscountAmount() + " " + getResources().getString(R.string.currency));
            else
                tvPromocodeValueB.setText("-" + orderDetail.getOrder().getDiscountAmount() + " " + getResources().getString(R.string.currency));

        }

        if (!orderDetail.getOrder().getPromocode().equals("")) {
            if (orderDetail.getOrder().getAfter_vat().equals("1"))
                layoutPromocodeA.setVisibility(View.VISIBLE);
            else
                layoutPromocodeB.setVisibility(View.VISIBLE);
        } else {
            layoutPromocodeB.setVisibility(View.GONE);
            layoutPromocodeA.setVisibility(View.GONE);
        }

        if (Double.parseDouble(orderDetail.getOrder().getWalletAmount()) > 0) {
            wallet_view.setVisibility(View.VISIBLE);
        } else {
            wallet_view.setVisibility(View.GONE);
        }

        if (Double.parseDouble(orderDetail.getOrder().getWallet_discount()) > 0) {
            layoutWalletDiscount.setVisibility(View.VISIBLE);
        } else {
            layoutWalletDiscount.setVisibility(View.GONE);
        }


        OrderItemsRatingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderAdapterRatting = new YourOrderDetailRecyclerRating(this, orderDetailList);
        OrderItemsRatingRecyclerView.setAdapter(orderAdapterRatting);

        if (!isRating(orderDetailList)) {
            tvSubmitRating.setVisibility(View.VISIBLE);
        } else {
            tvSubmitRating.setVisibility(View.GONE);
        }


        if (orderDetail.getOrder().getInvoice().equals("")) {
            viewInvoice.setVisibility(View.GONE);
        } else {
            viewInvoice.setVisibility(View.VISIBLE);
        }


        orderTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OpenUrl(orderDetail.getOrder().getTracking_link());
                Uri uri = Uri.parse(orderDetail.getOrder().getTracking_link());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUrl(orderDetail.getOrder().getInvoice());
            }
        });
    }

    private void removeReceipt() {

        APIManager.getInstance().getRemoveReceiptReq(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    Log.e("" + z, response);
                    Log.e("" + z, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("Code").equals("200")) {
                            getOrderDetails(user.userId, order_id);
                            Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.image_removed), Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.image_not_removed), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }, new RemoveReceiptModel("" + order_id, "" + user.userId));

        Log.d("**reqBodyRemove", "" + new Gson().toJson(new RemoveReceiptModel("" + order_id, "" + user.userId)));
    }

    private void addImage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderDetails.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_video_pic, null);
        Button take_pic = dialogView.findViewById(R.id.take_pic);
        Button gallery = dialogView.findViewById(R.id.gallery);

        dialogBuilder.setView(dialogView);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                TakeAPic();


            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                GalleryPic();


            }
        });
    }

    public void TakeAPic() {

        /*ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        picUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_RQ);*/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, CAMERA_RQ);


    }

    public void GalleryPic() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 101);
    }


    public void handlePermissions() {
        mRequestPermissionHandler.requestPermission(this, new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                permissonCheck = true;
            }

            @Override
            public void onFailed() {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }


    private void saveOrderRating(final String productID) {

        tvSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRating(orderAdapterRatting.getOrderItemsList())) {
                    List<Items> items = new ArrayList<>();
                    for (int i = 0; i < orderAdapterRatting.getOrderItemsList().size(); i++) {
                        Items items1 = new Items(orderAdapterRatting.getOrderItemsList().get(i).getDishId(),
                                orderAdapterRatting.getOrderItemsList().get(i).getRating(),
                                orderAdapterRatting.getOrderItemsList().get(i).getComments());
                        items.add(items1);

                    }

                    Log.d("RatingRequest", new Gson().toJson(new RetingRequest(user.userId, order_id, items, "0")));

                    APIManager.getInstance().saveOrderRating(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            try {
                                Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.st_rating_submitted), Toast.LENGTH_SHORT).show();
                                getOrderDetails(user.userId, order_id);
                            } catch (Exception ex) {

                            }
                        }
                    }, new RetingRequest(user.userId, productID, items, "0"));

                } else {
                    Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.st_rateorder), Toast.LENGTH_SHORT).show();
                }

            }
        });


        /*  DeliveryTime.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {

               if (rating>0 && retingDeliveryMan.getRating()>0){
                    tvSubmitRating.setVisibility(View.VISIBLE);
                }else {
                    tvSubmitRating.setVisibility(View.GONE);
                }
            }
        });*/
        /*    retingDeliveryMan.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {


                if (rating>0 && DeliveryTime.getRating()>0){
                    tvSubmitRating.setVisibility(View.VISIBLE);
                }else {
                    tvSubmitRating.setVisibility(View.GONE);
                }

            }
        });
*/

/*
        DeliveryTime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Log.d("rating", "" + rating);
                if (rating > 0 && retingDeliveryMan.getRating() > 0) {
                    tvSubmitRating.setVisibility(View.VISIBLE);
                } else {
                    tvSubmitRating.setVisibility(View.GONE);
                }

            }
        });
*/
/*

        retingDeliveryMan.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                if (rating > 0 && DeliveryTime.getRating() > 0) {
                    tvSubmitRating.setVisibility(View.VISIBLE);
                } else {
                    tvSubmitRating.setVisibility(View.GONE);
                }

            }
        });
*/

/*

        tvSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIManager.getInstance().saveOrderRating(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        try {
                            Toast.makeText(OrderDetails.this, "" + getResources().getString(R.string.st_rating_submitted), Toast.LENGTH_SHORT).show();
                            tvSubmitRating.setVisibility(View.GONE);
                            editUserRemarks.setEnabled(false);
                            DeliveryTime.setIsIndicator(true);
                            retingDeliveryMan.setIsIndicator(true);

                            afterFeedBackLL.setVisibility(View.VISIBLE);
                            rattingView.setVisibility(View.GONE);


                        } catch (Exception ex) {

                        }
                    }
                }, new RetingRequest(user.userId, order_id, retingDeliveryMan.getRating() + "", "" + DeliveryTime.getRating(), editUserRemarks.getText().toString().trim(), "0"));

            }
        });
*/

    }


    private boolean isRating(List<OrderDetail> orderDetails) {
        boolean israting = true;
        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getRating().equals("") || orderDetails.get(i).getRating().equals("0") ||
                    orderDetails.get(i).getRating().equals("0.00")) {
                israting = false;
            }
        }

        return israting;
    }


    private void OpenUrl(String ulr) {
        Uri uri = Uri.parse(Constants.SERVER_URL + "" + ulr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    private void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOrderSummery:
                llOrderShipped.setVisibility(View.VISIBLE);
                orderDetaiMain.setBackgroundColor(getResources().getColor(R.color.grey));
                llDelivered.setVisibility(View.GONE);
                tvOrderID.setTextColor(getResources().getColor(R.color.black));
                tvReOrder.setTextColor(getResources().getColor(R.color.black));
                tvTransactionId.setTextColor(getResources().getColor(R.color.black));
                tvTransactionIdApplePay.setTextColor(getResources().getColor(R.color.black));
                if (user.hide_price.equals("1")) {
                    rlPayementDetailView.setVisibility(View.GONE);

                } else {
                    rlPayementDetailView.setVisibility(View.VISIBLE);
                }
                isViewSummery = true;
                break;
            case R.id.tvOrderSummeryOD:
                llOrderShipped.setVisibility(View.VISIBLE);
                orderDetaiMain.setBackgroundColor(getResources().getColor(R.color.grey));
                llDelivered.setVisibility(View.GONE);
                tvOrderID.setTextColor(getResources().getColor(R.color.black));
                tvReOrder.setTextColor(getResources().getColor(R.color.black));
                tvTransactionId.setTextColor(getResources().getColor(R.color.black));
                tvTransactionIdApplePay.setTextColor(getResources().getColor(R.color.black));
                if (user.hide_price.equals("1")) {
                    rlPayementDetailView.setVisibility(View.GONE);

                } else {
                    rlPayementDetailView.setVisibility(View.VISIBLE);
                }
                isViewSummery = true;
                break;

            case R.id.tvReturns:
                Intent intent1 = new Intent(OrderDetails.this, AfterSalesServicesMyReturnsActivity.class);
                intent1.putExtra("orderID", "" + order_id);
                intent1.putExtra("orderNumber", "" + order_number);
                intent1.putExtra("invoiceNumber", "" + orderDetail.getOrder().getOrdId());

                intent1.putExtra("invoiceDate", "" + orderDetail.getOrder().getOrdDate());
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (isViewSummery) {
            llOrderShipped.setVisibility(View.GONE);
            llDelivered.setVisibility(View.VISIBLE);
            tvOrderID.setTextColor(getResources().getColor(R.color.black));
            tvReOrder.setTextColor(getResources().getColor(R.color.black));
            tvTransactionId.setTextColor(getResources().getColor(R.color.black));
            tvTransactionIdApplePay.setLinkTextColor(getResources().getColor(R.color.black));

//            orderDetaiMain.setBackground(getResources().getDrawable(R.drawable.jazeriah_bg));
            rlPayementDetailView.setVisibility(View.GONE);
            isViewSummery = false;
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            if (resultCode == RESULT_OK) {
                if (requestCode == 101) {


                    picUri = data.getData();
                    filePath = getPath(picUri);

                    uploadedIV.setImageURI(picUri);
                    finalFile = new File(getRealPathFromURI(picUri));

                    uploadedIV.setImageURI(picUri);
                    imageCL.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.GONE);

                    if (picUri != null) {
                        uploadPaymentReceiptTV.setVisibility(View.VISIBLE);
                        selectImageTV.setVisibility(View.GONE);
                        removeIV.setVisibility(View.GONE);
                    }
//                    uploadReceipt();
                    Log.d("**fileName", "" + finalFile);
                    Log.d("**fileName", "" + filePath);

                } else if (requestCode == CAMERA_RQ) {

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    ivWatch.setImageBitmap(photo);

                    picUri = getImageUri(getApplicationContext(), photo);
                    filePath = getPath(picUri);

                    finalFile = new File(getRealPathFromURI(picUri));

                    uploadedIV.setImageURI(picUri);
                    imageCL.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.VISIBLE);
                    removeIV.setVisibility(View.GONE);
                    if (picUri != null) {
                        uploadPaymentReceiptTV.setVisibility(View.VISIBLE);
                        selectImageTV.setVisibility(View.GONE);
                        removeIV.setVisibility(View.GONE);
                    }
//                    uploadReceipt();
                    Log.d("**fileName", "" + finalFile);
                    Log.d("**fileName", "" + filePath);

                }
            }

        } catch (Exception ex) {
            Log.e("Ex_OnActivityResult", ex.toString());
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderDetails.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_upload_dialog, null);
        Button yesUpload = dialogView.findViewById(R.id.yesUpload);
        Button noUpload = dialogView.findViewById(R.id.noUpload);

        dialogBuilder.setView(dialogView);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        yesUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                uploadReceipt();


            }
        });


        noUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();


            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void uploadReceipt() {

        //will be deleted if not work
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("user_id", user.userId);
        builder.addFormDataPart("order_id", order_id);

        Bitmap bmp = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);

        builder.addFormDataPart("image", finalFile.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));

//        builder.addFormDataPart("image", finalFile.getName(), RequestBody.create(MultipartBody.FORM, finalFile));


        RequestBody requestBody = builder.build();


        Call<AddReceiptModel> call = service.send_receipt(requestBody);
        call.enqueue(new Callback<AddReceiptModel>() {
            @Override
            public void onResponse(Call<AddReceiptModel> call, Response<AddReceiptModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        showProgress(false);

                        Log.d("**response", "" + response.raw());
                        Log.d("**responseError", "" + response.errorBody());
//                        initViews();
                        getOrderDetails(user.userId, order_id);

                    }
                } else {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "problem image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddReceiptModel> call, Throwable t) {
                showProgress(false);
                Log.v("Response gotten is", t.getMessage());
                Toast.makeText(getApplicationContext(), "problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
