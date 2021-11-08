package com.app.aljazierah.ui.HomeScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.LogOut;
import com.app.aljazierah.ui.LoginActivity;
import com.app.aljazierah.ui.SearchProductsActivity;
import com.app.aljazierah.ui.TicketList;
import com.app.aljazierah.ui.fragment.FragmentCategories;
import com.app.aljazierah.ui.fragment.FragmentHome;
import com.app.aljazierah.ui.fragment.FragmentMore;
import com.app.aljazierah.ui.fragment.FragmentOffers;

import com.app.aljazierah.ui.fragment.FragmentOrders;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.CustomViewPager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.ViewPager.ViewPagerAdapter;
import com.roam.appdatabase.DatabaseManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomViewPager viewPager;
    private ConstraintLayout tollview;
    //    private TextView title;
//    public TextView basket_badge;
    private View btn_product, view_categories, btn_offer, btn_order, btn_more;
    //    TextView tvLogout;
//    private RelativeLayout rlbadgeView;
    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
    ProgressDialog mProgressDialog;
    Dialog dialogVersion;
    private ArrayList<Areas> allCityAreas = new ArrayList<>();
    Dialog dialog;
    private static final int REQUEST_FOR_ADDRESS = 11;
    CountDownTimer countDownTimer;
//    private ImageView /*basket_img,*/ /*searchIV,*/ menuBtnHomeScreen;
//    private DrawerLayout drawerLayoutMain;
    private boolean isArabic;
    /*private RecyclerView categoriesNameRV;
    private CategoriesNameAdapter categoriesNameAdapter;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isArabic = AppController.setLocale();
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_homescreen);


        isInternetAccessible(HomeScreenActivity.this);
        /*title = findViewById(R.id.tvToolbarTitleTCA);
        title.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.st_products));*/
    }

    private void setTollView() {
        tollview = findViewById(R.id.toolbar);

//        searchIV = findViewById(R.id.searchIV);
//        menuBtnHomeScreen = findViewById(R.id.menuBtnHomeScreen);
//        drawerLayoutMain = findViewById(R.id.drawerLayoutMain);
//        categoriesNameRV = findViewById(R.id.categoriesNameRV);

//        title.setVisibility(View.GONE);
//        View badge_view = findViewById(R.id.badge_viewFH);

//        basket_badge = findViewById(R.id.basket_badge);
//        textview_location = findViewById(R.id.textview_location);
        setLocation();
//        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
//        ProductsSingleton.getInstance().setBasketview(badge_view);

        btn_product = findViewById(R.id.view_product);
        view_categories = findViewById(R.id.view_categories);
        btn_offer = findViewById(R.id.view_offers);
        btn_order = findViewById(R.id.view_orders);
        btn_more = findViewById(R.id.view_more);
//        tvLogout = findViewById(R.id.tv_logout);
//        tvLogout.setOnClickListener(this);
//        rlbadgeView = findViewById(R.id.badge_viewFH);
//        basket_img = findViewById(R.id.basket_img);
//        imageViewBack = findViewById(R.id.imageViewBackButton);
//        imageViewBack.setVisibility(View.GONE);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setEnableSwipe(true);
        ProductsSingleton.getInstance().setCustomViewPager(viewPager);

        btn_product.setOnClickListener(this);
        view_categories.setOnClickListener(this);
        btn_offer.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        btn_more.setOnClickListener(this);
//        searchIV.setOnClickListener(this);
//        menuBtnHomeScreen.setOnClickListener(this);
//        searchLL.setVisibility(View.VISIBLE);




        /*textview_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    Intent intent = new Intent(HomeScreenActivity.this, ChooseAddressActivity.class);
                    startActivityForResult(intent, REQUEST_FOR_ADDRESS);
                } else {
                    String permission = Manifest.permission.ACCESS_FINE_LOCATION;
                    int res = checkCallingOrSelfPermission(permission);
                    if (res == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent(HomeScreenActivity.this, AddAddressActivity.class);
                        intent.putExtra("fromHome", 1);
                        startActivityForResult(intent, REQUEST_FOR_ADDRESS);
                        overridePendingTransition(R.anim.enter_anim, 0);
                    } else {
                        Toast.makeText(HomeScreenActivity.this, "Enable Location in Settings", Toast.LENGTH_SHORT).show();
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                }
            }
        });*/

        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

        /*badge_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    if (Cart.getTotalItems() < companySetting.minOrder) {
                        Toast.makeText(HomeScreenActivity.this, getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                        overridePendingTransition(R.anim.enter_anim, 0);
                    }
                } else {
                    Constants.fromcart = true;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMCART);
                    overridePendingTransition(R.anim.enter_anim, 0);
                }
            }
        });*/
    }

    private void setLocation() {
        try {
            if (UserSession.getInstance().getSaveAddressObject().equals("")) {

            } else {
                JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
//                textview_location.setText("" + addressObj.getString("details"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentHome(this), getResources().getString(R.string.products));
        adapter.addFrag(new FragmentCategories(this), getResources().getString(R.string.st_category));
        adapter.addFrag(new FragmentOffers(), getResources().getString(R.string.offer));
        adapter.addFrag(new FragmentOrders(), getResources().getString(R.string.order));
        adapter.addFrag(new FragmentMore(), getResources().getString(R.string.settings));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        AppController.getInstance().setViewPager_home(viewPager);

     /*   ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        params1.setMargins(0, -55, 0, 0);
        viewPager.setLayoutParams(params1);*/

        setTabs();

        btn_product.setBackgroundResource(R.color.tab_select);
        view_categories.setBackgroundResource(R.color.colorPrimaryDark);
        btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
        btn_order.setBackgroundResource(R.color.colorPrimaryDark);
        btn_more.setBackgroundResource(R.color.colorPrimaryDark);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setTabs() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
//                        title.setText(getResources().getString(R.string.st_products));
//                        title.setVisibility(View.GONE);
//                        searchIV.setVisibility(View.VISIBLE);
//                        menuBtnHomeScreen.setVisibility(View.VISIBLE);
                        btn_product.setBackgroundResource(R.color.tab_select);
                        view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                        tvLogout.setVisibility(View.GONE);
//                        textview_location.setVisibility(View.VISIBLE);
//                        ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
//                        ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
                        break;
                    case 1:

                        btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                        view_categories.setBackgroundResource(R.color.tab_select);
                        btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_more.setBackgroundResource(R.color.colorPrimaryDark);
                        break;

                    case 2:
//                        title.setText(getResources().getString(R.string.offer));
//                        title.setVisibility(View.VISIBLE);
//                        searchIV.setVisibility(View.GONE);
//                        menuBtnHomeScreen.setVisibility(View.GONE);
                        btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                        view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_offer.setBackgroundResource(R.color.tab_select);
                        btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                        tvLogout.setVisibility(View.GONE);
                        ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                        ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
//                        textview_location.setVisibility(View.GONE);
//
                        break;

                    case 3:
//                        title.setText(getResources().getString(R.string.st_orders));
//                        searchIV.setVisibility(View.GONE);
//                        menuBtnHomeScreen.setVisibility(View.GONE);
                        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                        if (user == null) {
//                            searchIV.setVisibility(View.GONE);
//                            menuBtnHomeScreen.setVisibility(View.GONE);
                            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                            startActivityForResult(intent, ORDERS_SCREEN);
                            overridePendingTransition(R.anim.enter_anim, 0);
//                            textview_location.setVisibility(View.GONE);
                        } else {
//                            searchIV.setVisibility(View.GONE);
//                            menuBtnHomeScreen.setVisibility(View.GONE);
                            btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                            view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                            btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                            btn_order.setBackgroundResource(R.color.tab_select);
                            btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                            tvLogout.setVisibility(View.GONE);
//                            textview_location.setVisibility(View.GONE);
                            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
//                            title.setText(getResources().getString(R.string.st_orders));
//                            title.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 4:
                        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
//                        title.setText(getResources().getString(R.string.more));
//                        searchIV.setVisibility(View.GONE);
//                        menuBtnHomeScreen.setVisibility(View.GONE);
//                        title.setVisibility(View.GONE);
                        btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                        view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_more.setBackgroundResource(R.color.tab_select);
//                        rlbadgeView.setVisibility(View.GONE);
//                        textview_location.setVisibility(View.GONE);
                        if (user != null) {
//                            searchIV.setVisibility(View.GONE);
//                            menuBtnHomeScreen.setVisibility(View.GONE);
//                            tvLogout.setVisibility(View.GONE);
//                            textview_location.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public void getCartNumber() {
        if (Cart.getTotalItems() > 0) {
            ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
        } else {
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
        }
    }

    @SuppressLint({"RtlHardcoded", "WrongConstant"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_product:
                viewPager.setCurrentItem(0);
//                searchIV.setVisibility(View.VISIBLE);
//                menuBtnHomeScreen.setVisibility(View.VISIBLE);
                btn_product.setBackgroundResource(R.color.tab_select);
                view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                tvLogout.setVisibility(View.GONE);
//                title.setVisibility(View.GONE);
//                textview_location.setVisibility(View.VISIBLE);
//                getCartNumber();
                break;
            case R.id.view_categories:
                viewPager.setCurrentItem(1);
                btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                view_categories.setBackgroundResource(R.color.tab_select);
                btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                btn_more.setBackgroundResource(R.color.colorPrimaryDark);
                break;

            case R.id.view_offers:
                viewPager.setCurrentItem(2);
//                searchIV.setVisibility(View.GONE);
//                menuBtnHomeScreen.setVisibility(View.GONE);
//                title.setText(getResources().getString(R.string.offer));
//                title.setVisibility(View.VISIBLE);
                btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                btn_offer.setBackgroundResource(R.color.tab_select);
                btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                tvLogout.setVisibility(View.GONE);
//                textview_location.setVisibility(View.VISIBLE);
                ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
//                textview_location.setVisibility(View.GONE);

                break;

            case R.id.view_orders:
//                title.setText(getResources().getString(R.string.st_orders));
//                searchIV.setVisibility(View.GONE);
//                menuBtnHomeScreen.setVisibility(View.GONE);
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
                if (user == null) {
//                    searchIV.setVisibility(View.GONE);
//                    menuBtnHomeScreen.setVisibility(View.GONE);
                    Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ORDERS_SCREEN);
                    overridePendingTransition(R.anim.enter_anim, 0);
                } else {
//                    searchIV.setVisibility(View.GONE);
//                    menuBtnHomeScreen.setVisibility(View.GONE);
                    viewPager.setCurrentItem(3);
                    btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                    view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                    btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                    btn_order.setBackgroundResource(R.color.tab_select);
                    btn_more.setBackgroundResource(R.color.colorPrimaryDark);
//                    tvLogout.setVisibility(View.GONE);
//                    textview_location.setVisibility(View.GONE);
//                    title.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.view_more:
                viewPager.setCurrentItem(4);
//                searchIV.setVisibility(View.GONE);
//                menuBtnHomeScreen.setVisibility(View.GONE);
                user = DatabaseManager.getInstance().getFirstOfClass(User.class);
//                title.setText(getResources().getString(R.string.more));
//                title.setVisibility(View.GONE);
//                tvLogout.setVisibility(View.GONE);
                btn_product.setBackgroundResource(R.color.colorPrimaryDark);
                view_categories.setBackgroundResource(R.color.colorPrimaryDark);
                btn_offer.setBackgroundResource(R.color.colorPrimaryDark);
                btn_order.setBackgroundResource(R.color.colorPrimaryDark);
                btn_more.setBackgroundResource(R.color.tab_select);
//                rlbadgeView.setVisibility(View.GONE);
//                textview_location.setVisibility(View.GONE);
                if (user != null) {
//                    searchIV.setVisibility(View.GONE);
//                    menuBtnHomeScreen.setVisibility(View.GONE);
//                    textview_location.setVisibility(View.GONE);
//                    title.setVisibility(View.GONE);
//                    tvLogout.setVisibility(View.GONE);
                }
                break;

          /*  case R.id.tv_logout:
                logOut();
                break;*/

            case R.id.searchIV:
                if (ProductsSingleton.getInstance().getProductByLocations().size() > 0)
                    startActivity(new Intent(this, SearchProductsActivity.class));
                break;
           /* case R.id.menuBtnHomeScreen:

                    *//*if (isArabic) {
                        drawerLayoutMain.openDrawer(Gravity.LEFT);
                    } else {*//*
                        drawerLayoutMain.openDrawer(Gravity.END);
//                    }
                break;*/

        }
    }

    private void logOut() {
        User user1 = DatabaseManager.getInstance().getFirstOfClass(User.class);
        LogOut logOut = new LogOut();
        if (user1 != null)
            logOut.setUser_id(user1.userId);

        APIManager.getInstance().setLogOut(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);

                        if (jsonObject.optString("code").equals("200")) {
                            User user1 = DatabaseManager.getInstance().getFirstOfClass(User.class);
                            if (user1.hide_price.equals("1")) {
                                CoreManager.getInstance().removeUserData();
                                Toast.makeText(HomeScreenActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                user = null;
                                UserSession.getInstance().setSaveHomeAddressObject("");
                                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                CoreManager.getInstance().removeUserData();
                                Toast.makeText(HomeScreenActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                user = null;
                                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exceptin", e.toString());
                    }
                } else {
                    Toast.makeText(HomeScreenActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, logOut, HomeScreenActivity.this);
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) {
            clickDone();
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.app_icon)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(getResources().getString(R.string.close_msg))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(HomeScreenActivity.this);
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
    public void onResume() {
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (companySetting != null) {
            setLocation();
//            setTollView();

        }
        super.onResume();
    }

    private BroadcastReceiver promotion_reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("switch") != null) {
                startActivity(new Intent(HomeScreenActivity.this, TicketList.class));
                HomeScreenActivity.this.overridePendingTransition(R.anim.enter_anim, 0);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogVersion != null && dialogVersion.isShowing()) {
            dialogVersion.dismiss();
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        showProgress(false);


        if (user != null) {
            LocalBroadcastManager.getInstance(HomeScreenActivity.this).unregisterReceiver(promotion_reciever);
        }
    }

    private boolean isWifiAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isInternetAccessible(Context context) {
        if (isWifiAvailable()) {
            setTollView();
//        setViews();
            createViewPager();


        } else {
            Toast.makeText(context, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        /*Context context = AlJazeriahContextWrapper.wrap(newBase, new Locale(UserSession.getInstance().getUserLanguage()));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));*/
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}