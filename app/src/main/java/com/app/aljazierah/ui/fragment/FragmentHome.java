package com.app.aljazierah.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.aljazierah.adapter.CategoriesAdapter;
import com.app.aljazierah.adapter.CategoriesName.CategoriesNameAdapter;
import com.app.aljazierah.adapter.CategoriesName.MainCategoriesNameAdapter;
import com.app.aljazierah.adapter.ProductbyLocationAdapter;
import com.app.aljazierah.adapter.SeeAllAdapter;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.SubCategoriesModel;
import com.app.aljazierah.object.request.ProductsRequest;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.Promotions.Promotion;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.ui.AddAddressActivity;
import com.app.aljazierah.ui.AfterSalesServicesActivity;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.ui.LoginActivity;
import com.app.aljazierah.ui.CheckOutActivity;
import com.app.aljazierah.ui.MaintenanceListActivity;
import com.app.aljazierah.ui.MyWishListActivity;
import com.app.aljazierah.ui.OurShowRoomsActivity;
import com.app.aljazierah.ui.ReturnListActivity;
import com.app.aljazierah.ui.SearchProductsActivity;
import com.app.aljazierah.ui.SeeAllActivity;
import com.app.aljazierah.ui.SplashScreenActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.ProductsSingleton;

import com.app.aljazierah.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.aljazierah.utils.Constants.LOGINFROMAFTERSALES;
import static com.app.aljazierah.utils.Constants.LOGINFROMCART;
import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;
import static com.app.aljazierah.utils.Constants.PROMO_BASE_URL;
import static com.app.aljazierah.utils.Constants.fromcart;
import static com.app.aljazierah.utils.Constants.special_promotion;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentHome extends Fragment implements View.OnClickListener, MainCategoriesNameAdapter.FilterAdapterlistener {
    private View rootView;
    private boolean isArabic;

    //private TabLayout tabs;
    //private CustomViewPager viewpager_products;
    int currentPage = 0;
    int NUM_PAGES = 0;
    private Context context;
    private ConstraintLayout cartview;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ProductsSingleton.getInstance().getProductByLocations() != null) {
                        updateListing();
                    }
                    user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                    if (user != null) {
                        afterSalesServicesLL.setVisibility(View.VISIBLE);
                        afterSaleServicesLL.setVisibility(View.VISIBLE);
                        ourShowRoomLL.setVisibility(View.VISIBLE);
                        wishListLL.setVisibility(View.VISIBLE);
                        tvAfterSales.setVisibility(View.GONE);
                        tvmaintenances.setVisibility(View.GONE);
                        tvmyReturns.setVisibility(View.GONE);
                    } else {
                        afterSaleServicesLL.setVisibility(View.GONE);
                        afterSalesServicesLL.setVisibility(View.GONE);
                        ourShowRoomLL.setVisibility(View.VISIBLE);
                        wishListLL.setVisibility(View.GONE);
                        wishListLL.setVisibility(View.GONE);
                    }

                    if (isNetworkConnected()) {}
                    else
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }, 100);
        }
        createGson();
    }


    public FragmentHome(Context context) {
        this.context = context;
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    FrameLayout flSlider;
    ViewPager viewPagerSlider;
    CirclePageIndicator indicator;

    private ProgressDialog mProgressDialog;
    public TextView tv_show_vat, tvCatGlass, tvCatPlastic, bestSellerTV;
    public LinearLayout layoutCategories;
    RecyclerView rvHomeProducts, bestSellingRV;
    private static final int REQUEST_FOR_ADDRESS = 11;
    CountDownTimer countDownTimer;
    Dialog dialog;
    Gson gson;
    private ProductbyLocationAdapter productbyLocationAdapter;
    private CategoriesAdapter categoriesAdapter;
    ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
    private View lineCL;

    private ConstraintLayout rlbadgeView;
    private ImageView basket_img, searchIV, menuBtnHomeScreen, arrowASS, addNewReqIV;
    public TextView basket_badge, tvAfterSales, tvmaintenances, tvmyReturns;
    private DrawerLayout drawerLayoutMain;
    private RecyclerView categoriesNameRV;
    private CategoriesNameAdapter categoriesNameAdapter;
    private RecyclerView mainCategoriesRV;
    private MainCategoriesNameAdapter mainCategoriesNameAdapter;
    private LinearLayout ourShowRoomLL, afterSalesServicesLL, wishListLL, afterSaleServicesLL, bestSEllerLLL, bestSellerLLFH;
    private boolean afterSSCheck = true;
    private User user;
    private RecyclerView filteredRVFH;
    private SeeAllAdapter seeAllAdapter;
    private CoordinatorLayout coordinatorLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isArabic = AppController.setLocale();
        rootView = getLayoutInflater().inflate(R.layout.fragment_homes, container, false);
        initMembers(rootView);
        return rootView;
    }

    private void initMembers(View rootView) {

        if (isArabic) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        createGson();
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);


        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        rlbadgeView = rootView.findViewById(R.id.badge_viewFH);
//        rlbadgeView.setVisibility(View.GONE);
        basket_img = rootView.findViewById(R.id.basket_img);
//        basket_img.setVisibility(View.GONE);
//        View badge_view = rootView.findViewById(R.id.badge_viewFH);
        searchIV = rootView.findViewById(R.id.searchIV);
        menuBtnHomeScreen = rootView.findViewById(R.id.menuBtnHomeScreen);
        drawerLayoutMain = rootView.findViewById(R.id.drawerLayoutMain);
        categoriesNameRV = rootView.findViewById(R.id.categoriesNameRV);
        mainCategoriesRV = rootView.findViewById(R.id.mainCategoriesRV);
        mainCategoriesRV.setVisibility(View.VISIBLE);
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);

        basket_badge = rootView.findViewById(R.id.basket_badge);

        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
        ProductsSingleton.getInstance().setBasketview(basket_img);

        ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);


        ourShowRoomLL = rootView.findViewById(R.id.ourShowRoomLL);
        bestSellerLLFH = rootView.findViewById(R.id.bestSellerLLFH);
        bestSellerLLFH.setVisibility(View.GONE);
        bestSEllerLLL = rootView.findViewById(R.id.bestSEllerLLL);
        afterSalesServicesLL = rootView.findViewById(R.id.afterSalesServicesLL);
        wishListLL = rootView.findViewById(R.id.wishListLL);
        afterSaleServicesLL = rootView.findViewById(R.id.afterSaleServicesLL);
        arrowASS = rootView.findViewById(R.id.arrowASS);
        tvAfterSales = rootView.findViewById(R.id.tvAfterSales);
        addNewReqIV = rootView.findViewById(R.id.addNewReqIV);
        tvmaintenances = rootView.findViewById(R.id.tvmaintenances);
        tvmyReturns = rootView.findViewById(R.id.tvmyReturns);
        filteredRVFH = rootView.findViewById(R.id.filteredRVFH);
        filteredRVFH.setVisibility(View.GONE);

        if (companySetting != null) {
            setViews();
        } else {
            getActivity().finish();
            startActivity(new Intent(getActivity(), SplashScreenActivity.class));
            getActivity().overridePendingTransition(R.anim.enter_anim, 0);
        }


        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductsSingleton.getInstance().getProductByLocations().size() > 0)
                    startActivity(new Intent(getContext(), SearchProductsActivity.class));
            }
        });
        bestSellerLLFH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SeeAllActivity.class);
                intent.putExtra("categoryId", "");
                intent.putExtra("subCategoryId", "");
                intent.putExtra("bestSelletList", "1");
                intent.putExtra("catName", "" + getResources().getString(R.string.best_seller));
                context.startActivity(intent);

            }
        });

        menuBtnHomeScreen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (isArabic) {
                    drawerLayoutMain.openDrawer(Gravity.RIGHT);
                } else {
                    drawerLayoutMain.openDrawer(Gravity.LEFT);
                }

            }
        });


        ourShowRoomLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showRoomIntent = new Intent(getActivity(), OurShowRoomsActivity.class);
                startActivity(showRoomIntent);
            }
        });

        wishListLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyWishListActivity.class);
                startActivity(i);
            }
        });

        tvmaintenances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), MaintenanceListActivity.class);
                startActivity(i1);
            }
        });


        addNewReqIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    Intent intent = new Intent(getActivity(), AfterSalesServicesActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMAFTERSALES);
                }
            }
        });


        tvmyReturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ReturnListActivity.class);
                startActivity(intent2);
            }
        });

        tvAfterSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AfterSalesServicesActivity.class);
                startActivity(intent);
            }
        });

        afterSaleServicesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (afterSSCheck) {
                    tvAfterSales.setVisibility(View.VISIBLE);
                    tvmaintenances.setVisibility(View.VISIBLE);
                    tvmyReturns.setVisibility(View.VISIBLE);
                    arrowASS.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    afterSSCheck = false;
                } else if (!afterSSCheck) {
                    tvAfterSales.setVisibility(View.GONE);
                    tvmaintenances.setVisibility(View.GONE);
                    tvmyReturns.setVisibility(View.GONE);
                    arrowASS.setImageResource(R.drawable.ic_right_chevron);
                    afterSSCheck = true;
                }
            }
        });


        rlbadgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    if (Cart.getTotalItems() < companySetting.minOrder) {
                        Toast.makeText(getContext(), getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
//                        this.overridePendingTransition(R.anim.enter_anim, 0);
                    }
                } else {
                    Constants.fromcart = true;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMCART);
//                    this.overridePendingTransition(R.anim.enter_anim, 0);
                }
            }
        });
    }


    private void setViews() {
        viewPagerSlider = rootView.findViewById(R.id.productsNew_viewPagerSlider);
        //tabs = rootView.findViewById(R.id.tabs);
        //viewpager_products = rootView.findViewById(R.id.viewpager_products);
//        textview_location = rootView.findViewById(R.id.textview_location);
        rvHomeProducts = rootView.findViewById(R.id.rvHomeProducts);
        bestSellingRV = rootView.findViewById(R.id.bestSellingRV);
        bestSellerTV = rootView.findViewById(R.id.bestSellerTV);
        lineCL = rootView.findViewById(R.id.lineFH);


        layoutCategories = rootView.findViewById(R.id.layoutCategories);
        tvCatPlastic = rootView.findViewById(R.id.tvCatPlastic);
        tvCatGlass = rootView.findViewById(R.id.tvCatGlass);
        tv_show_vat = rootView.findViewById(R.id.tv_show_vat);
        cartview = rootView.findViewById(R.id.cartview);
        cartview.setVisibility(View.GONE);
        flSlider = rootView.findViewById(R.id.fragmentHome_flSlider);
        TextView tv_cartamount = rootView.findViewById(R.id.tv_cartamount);
        indicator = rootView.findViewById(R.id.productsNew_sliderIndicator);
        ProductsSingleton.getInstance().setCartView(cartview);
        ProductsSingleton.getInstance().setTv_cartamount(tv_cartamount);

        ProductsSingleton.getInstance().setSlider(flSlider);
//        textview_location.setOnClickListener(this);
        tvCatGlass.setOnClickListener(this);
        tvCatPlastic.setOnClickListener(this);


        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

        if (companySetting.show_vat.equals("1")) {
            tv_show_vat.setVisibility(View.VISIBLE);
        } else {
            tv_show_vat.setVisibility(View.GONE);

        }

        if (companySetting.show_category.equals("1")) {
            layoutCategories.setVisibility(View.VISIBLE);
            tvCatPlastic.setBackground(getContext().getResources().getDrawable(R.drawable.plastic_selected_btn_bg));
        } else {
            layoutCategories.setVisibility(View.GONE);
        }

        bestSellerTV.setVisibility(View.GONE);
        menuBtnHomeScreen.setVisibility(View.GONE);
        addNewReqIV.setVisibility(View.GONE);
        searchIV.setVisibility(View.GONE);
        rlbadgeView.setVisibility(View.GONE);

        lineCL.setVisibility(View.GONE);
        ProductsSingleton.getInstance().getCartView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (Cart.getTotalItems() < companySetting.minOrder) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                } else {
                    if (user != null) {
                        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                        getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                    } else {
                        fromcart = true;
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, LOGINFROMCART);
                        getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                    }
                }
            }
        });


        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.hide_price.equals("1")) {
                ProductsSingleton.getInstance().getTv_cartamount().setVisibility(View.GONE);
            }
        }


    }

    private void initSlider(List<Promotion> sliderList) {

        viewPagerSlider.setAdapter(new ImageSliderAdapter(getActivity(), sliderList));
        indicator.setViewPager(viewPagerSlider);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3 * density);
        NUM_PAGES = sliderList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPagerSlider.setCurrentItem(currentPage++, true);

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProductByLocation();
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void getProductByLocation() {
        try {
            if (UserSession.getInstance().getSaveAddressObject().equals("")) {
                startActivity(new Intent(getActivity(), AddAddressActivity.class));
            } else {
                JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
                getProducts(Integer.parseInt(addressObj.getString("areaId")), Integer.parseInt(addressObj.getString("cityId")), addressObj.getString("add_type"));
                Log.d("addresstype", addressObj.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            if (user.hide_price.equals("1")) {
                ProductsSingleton.getInstance().getTv_cartamount().setVisibility(View.GONE);
            }
        }

    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(getActivity());
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

    public void getProducts(final int area_id, int city_id, String add_type) {
        showProgress(true);
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        ProductsSingleton.getInstance().setProductByLocations(null);

        String userId = "";
        if (user != null)
            userId = user.userId;

        Log.e("pBLRequest", new Gson().toJson(new ProductsRequest(area_id, city_id, userId, add_type)));
        APIManager.getInstance().getProducts(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                Constants.checkaddress = false;
                if (z) {
                    if (response.contains("Access denied") || response.contains("Invalid Key!")) {
                        CoreManager.getInstance().removeUserData();
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        layoutCategories.setVisibility(View.GONE);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Type listType = new TypeToken<List<Promotion>>() {
                            }.getType();
                            Type categoriesType = new TypeToken<List<Categories>>() {
                            }.getType();
                            Type subCateType = new TypeToken<List<SubCategoriesModel>>() {
                            }.getType();

                            List<Promotion> promotionbyLocation = gson.fromJson(jsonObject.getString("promotions"), listType);
                            List<Categories> categories = gson.fromJson(jsonObject.getString("categories"), categoriesType);

                            ProductsSingleton.getInstance().setCategoriesList(categories);
                            ProductsSingleton.getInstance().setPromotionList(promotionbyLocation);


                            List<Promotion> sliderList = new ArrayList<>();
                            if (isArabic) {
                                for (int i = 0; i < promotionbyLocation.size(); i++) {
                                    if (!promotionbyLocation.get(i).getImageMobileAr().equals("") && promotionbyLocation.get(i).getCategory_id_list().size() == 0) {

                                        sliderList.add(promotionbyLocation.get(i));

                                    }
                                }
                            } else {
                                for (int i = 0; i < promotionbyLocation.size(); i++) {
                                    if (!promotionbyLocation.get(i).getImageMobileEn().equals("") && promotionbyLocation.get(i).getCategory_id_list().size() == 0) {
                                        sliderList.add(promotionbyLocation.get(i));
                                    }
                                }
                            }

                            if (promotionbyLocation.size() > 0) {
                                flSlider.setVisibility(View.VISIBLE);

                                initSlider(sliderList);
                                if (sliderList.size() > 0) {
                                    flSlider.setVisibility(View.VISIBLE);
                                } else {
                                    flSlider.setVisibility(View.GONE);
                                }

                            } else {
                                flSlider.setVisibility(View.GONE);
                            }

                            ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("rows");

                            Type TypeRows = new TypeToken<List<ProductByLocation>>() {
                            }.getType();

                            productByLocations = gson.fromJson(jsonObject.getString("rows"), TypeRows);

                            ProductsSingleton.getInstance().setProductByLocations(productByLocations);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateCart();
                                    updateListing();
                                    updateCategories();


                                    categoriesNameAdapter = new CategoriesNameAdapter(ProductsSingleton.getInstance().getCategoriesList(), getActivity());
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                                    categoriesNameRV.setLayoutManager(gridLayoutManager);
                                    categoriesNameRV.setAdapter(categoriesNameAdapter);

                                    bestSellerLLFH.setVisibility(View.VISIBLE);

                                    mainCategoriesRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                    mainCategoriesNameAdapter = new MainCategoriesNameAdapter(ProductsSingleton.getInstance().getCategoriesList(), getActivity(), FragmentHome.this);
                                    mainCategoriesRV.setAdapter(mainCategoriesNameAdapter);


                                    if (ProductsSingleton.getInstance().getProductByLocations().size() > 0) {

                                        seeAllAdapter = new SeeAllAdapter(getContext(), ProductsSingleton.getInstance().getProductByLocations());
                                        GridLayoutManager gridLayoutManagerr = new GridLayoutManager(getApplicationContext(), 2);
                                        filteredRVFH.setLayoutManager(gridLayoutManagerr);
                                        filteredRVFH.setAdapter(seeAllAdapter);
                                    }

                                }
                            });

                            if (jsonArray.length() > 0) {
                            /*    for (int i = 0; i < jsonArray.length(); i++) {
                                    productByLocations.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ProductByLocation.class));
                                }*/
                            } else {
                                Toast.makeText(getActivity(), "" + jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                                Cart.emptyCart();
                                ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
                                ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
                                ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);
                                rvHomeProducts.setVisibility(View.GONE);
                                bestSellingRV.setVisibility(View.GONE);
                                bestSellerTV.setVisibility(View.GONE);
                                menuBtnHomeScreen.setVisibility(View.GONE);
                                searchIV.setVisibility(View.GONE);
                                addNewReqIV.setVisibility(View.GONE);
                                rlbadgeView.setVisibility(View.GONE);
                                lineCL.setVisibility(View.GONE);
                                layoutCategories.setVisibility(View.GONE);
                            }

                            if (fromcart) {
                                fromcart = false;
                                if (Constants.fromproduct) {
                                    Constants.fromproduct = false;
                                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                                    if (Cart.getTotalItems() > 0) {
                                        if (user != null) {
                                            Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                                            intent.putExtra("order", false);
                                            startActivityForResult(intent, ORDERS_SCREEN);
                                            getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e("EXcePRO", e.toString());
                            e.printStackTrace();
                        }
                    }

                    showProgress(false);
                }
            }
        }, area_id, city_id, userId, add_type);
    }

    private void updateCart() {
        List<Cart> cartList;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        for (int j = 0; j < cartList.size(); j++) {
            boolean cartCheck = true;
            for (int k = 0; k < ProductsSingleton.getInstance().getProductByLocations().size(); k++) {
//                Log.d("cartitemcount", "" + ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString() + "," + cartList.get(j).productId.toString());

                if (ProductsSingleton.getInstance().getProductByLocations().get(k).getId().toString().equals(cartList.get(j).productId.toString())) {
//                    Log.d("cartitemcount", cartList.get(j).count + "");
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


    public class ImageSliderAdapter extends PagerAdapter {
        private Context mContext;
        private List<Promotion> imageList;
        private LayoutInflater inflater;

        //
        public ImageSliderAdapter(Context context, List<Promotion> list) {
            mContext = context;
            imageList = list;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.si_slidingimage_layout, collection, false);

            final ImageView imageView = imageLayout.findViewById(R.id.imageview);
            final ProgressBar progress_bar = imageLayout.findViewById(R.id.progress_bar);
            imageView.setVisibility(View.GONE);
            progress_bar.setVisibility(View.VISIBLE);

            try {

                if (!isArabic) {
                    Picasso.with(getActivity()).load(PROMO_BASE_URL + imageList.get(position).getImageMobileEn())

                            .into(imageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    imageView.setVisibility(View.VISIBLE);
                                    progress_bar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });


                } else {

                    Picasso.with(getActivity()).load(PROMO_BASE_URL + imageList.get(position).getImageMobileAr())

                            .into(imageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    imageView.setVisibility(View.VISIBLE);
                                    progress_bar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });


                }
            } catch (Exception e) {
                Log.e("IMage SLider Adapter :", e.getMessage());

            }

            // imageView.setImage(ImageSource.uri( Common.PhotoURl+imageList.get(position).getPhotoSource()));

            collection.addView(imageLayout);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            //return CustomPagerEnum.values().length;
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return imageList.get(position).toString();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.textview_location:
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    Intent intent = new Intent(getActivity(), ChooseAddressActivity.class);
                    startActivityForResult(intent, REQUEST_FOR_ADDRESS);
                } else {
                    String permission = Manifest.permission.ACCESS_FINE_LOCATION;
                    int res = getContext().checkCallingOrSelfPermission(permission);
                    if (res == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
                        intent.putExtra("fromHome",1);
                        startActivityForResult(intent, REQUEST_FOR_ADDRESS);
                        getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                    } else {
                        Toast.makeText(getActivity(), "Enable Location in Settings", Toast.LENGTH_SHORT).show();
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                }

                break;*/

             /*   case R.id.tvCatPlastic:
                    tvCatGlass.setBackground(null);
                    tvCatPlastic.setBackground(getContext().getResources().getDrawable(R.drawable.plastic_selected_btn_bg));
                    //setCategories("1");
                break;

                case R.id.tvCatGlass:
                    tvCatGlass.setBackground(getContext().getResources().getDrawable(R.drawable.glass_selected_btn_bg));
                    tvCatPlastic.setBackground(null);
                    //setCategories("2");
                break;*/
        }
    }

/*
    private void setCategories(final String categories) {

        productByLocations=new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ProductsSingleton.getInstance().getProductByLocations() != null) {
                    productByLocations.clear();
                    for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {

                        if (ProductsSingleton.getInstance().getProductByLocations().get(i).getCategory_id().equals(categories)) {
                            Cart cart = DatabaseManager.getInstance().getFirstMatching("productId",
                                    ProductsSingleton.getInstance().getProductByLocations().
                                            get(i).getId(), Cart.class);
                            if (cart != null) {
                                ProductsSingleton.getInstance().getProductByLocations().get(i).setAddedToCart(true);
                                ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(cart.count);
                            }
                            productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));

                        } else if(categories.equals("all")) {
                            final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
                            if (companySetting.show_category.equals("1")){
                                if (ProductsSingleton.getInstance().getProductByLocations().get(i).getCategory_id().equals("1")) {
                                    Cart cart = DatabaseManager.getInstance().getFirstMatching("productId",
                                            ProductsSingleton.getInstance().getProductByLocations().
                                                    get(i).getId(), Cart.class);
                                    if (cart != null) {
                                        ProductsSingleton.getInstance().getProductByLocations().get(i).setAddedToCart(true);
                                        ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(cart.count);
                                    }
                                    productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
                                }
                            }else {
                                Cart cart = DatabaseManager.getInstance().getFirstMatching("productId",
                                        ProductsSingleton.getInstance().getProductByLocations().
                                                get(i).getId(), Cart.class);
                                if (cart != null) {
                                    ProductsSingleton.getInstance().getProductByLocations().get(i).setAddedToCart(true);
                                    ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(cart.count);
                                }
                                productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
                            }

                        }
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateListing();
                        }
                    });
                }
            }
        }).start();

    }
*/


    private void updateCategories() {
        categoriesAdapter = new CategoriesAdapter(ProductsSingleton.getInstance().getCategoriesList(), getActivity(), this, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rvHomeProducts.setLayoutManager(gridLayoutManager);
        rvHomeProducts.setAdapter(categoriesAdapter);
//        rvHomeProducts.setAdapter(categoriesAdapter);

        if (getBestSellingProd().size() > 0) {
            bestSellerTV.setVisibility(View.VISIBLE);
            menuBtnHomeScreen.setVisibility(View.VISIBLE);
            addNewReqIV.setVisibility(View.VISIBLE);

            searchIV.setVisibility(View.VISIBLE);
            rlbadgeView.setVisibility(View.VISIBLE);
            lineCL.setVisibility(View.VISIBLE);
            bestSellingRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            productbyLocationAdapter = new ProductbyLocationAdapter(getActivity(), getBestSellingProd(), this, getActivity());
            bestSellingRV.setAdapter(productbyLocationAdapter);
        } else {
            bestSellerTV.setVisibility(View.GONE);
            menuBtnHomeScreen.setVisibility(View.GONE);
            addNewReqIV.setVisibility(View.GONE);

            searchIV.setVisibility(View.GONE);
            rlbadgeView.setVisibility(View.GONE);
            lineCL.setVisibility(View.GONE);
            bestSellingRV.setVisibility(View.GONE);
        }
    }

    private ArrayList<ProductByLocation> getBestSellingProd() {
        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
        for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
            if (ProductsSingleton.getInstance().getProductByLocations().get(j).getBest_selling().toString().equals("1")) {
                productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
            }
        }
        return productByLocations;
    }

    private void updateListing() {
        boolean haveItemInCart = false;
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            ProductByLocation product = ProductsSingleton.getInstance().getProductByLocations().get(i);
            Cart cart = DatabaseManager.getInstance().getFirstMatching("productId", product.getId(), Cart.class);
            if (cart != null) {
                haveItemInCart = true;
                product.setAddedToCart(true);
                product.setCount(cart.count);
            } else {
                product.setAddedToCart(false);
                product.setCount(0);
            }
        }
        updateCategories();
        getCartNumber();
        if (haveItemInCart) {
            ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
            updateAmount();
        } else {
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);
        }

    }

    public void updateAmount() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {

            String amount = String.format("%.2f", Cart.getTotalPriceVat());
            ProductsSingleton.getInstance().getTv_cartamount().setText(getResources().getString(R.string.currency) + " " + Utils.convertString(amount));

        } else {
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.GONE);


        }

        if (Cart.getTotalItems() > 0) {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
        } else {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);

        }

    }

    public void showCartLayout() {
        ProductsSingleton.getInstance().getCartView().setVisibility(View.VISIBLE);
        ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);
        ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
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
    public void onResume() {
        super.onResume();
//        getProductByLocation();

        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
        ProductsSingleton.getInstance().setBasketview(basket_img);
        getCartNumber();
        if (Constants.checkaddress) {
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
            getActivity().overridePendingTransition(R.anim.enter_anim, 0);
            getActivity().finish();
            Constants.checkaddress = false;
        }
        getActivity().registerReceiver(mReceiverOnAddressChange, new IntentFilter("data_action_add_change"));
        if (ProductsSingleton.getInstance().getProductByLocations() != null && ProductsSingleton.getInstance().getCustomViewPager().getCurrentItem() == 0) {

            updateListing();
            seeAllAdapter = new SeeAllAdapter(getContext(), ProductsSingleton.getInstance().getProductByLocations());
            GridLayoutManager gridLayoutManagerr = new GridLayoutManager(getApplicationContext(), 2);
            filteredRVFH.setLayoutManager(gridLayoutManagerr);
            filteredRVFH.setAdapter(seeAllAdapter);
            Log.d("asdasdas","asdasda");
        }







        if (special_promotion) {
            getProductByLocation();
            special_promotion = false;
        } else if (fromcart) {
            fromcart = false;
            if (Constants.fromproduct) {
                Constants.fromproduct = false;
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (Cart.getTotalItems() > 0) {
                    if (user != null) {
                        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                        getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                    }
                }
            }

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiverOnAddressChange);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    private BroadcastReceiver mReceiverOnAddressChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String string = (String) intent.getSerializableExtra("onAddressChange");
                Log.d("changeddd", string + "....");
                if (string.equals("changed")) {
//                    getProductByLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    @Override
    public void filterr(Categories categories, String check) {


        Log.d("filterID", "" + categories.getId());
        if (check.equals("checked")) {
            filteredRVFH.setVisibility(View.VISIBLE);
            coordinatorLayout.setVisibility(View.GONE);

        } else {
            filteredRVFH.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }


        seeAllAdapter.setProductList(getProducts(categories));


    }

/*    private ArrayList<ProductByLocation> getProducts(Categories categories) {
        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
                if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                        equals(categories.getParent_id().toString())) {
                    Log.d("**ids",ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id()+"           parentID:"+categories.getParent_id().toString());
                    productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
                }
        }

        return productByLocations;
    }*/


    private ArrayList<ProductByLocation> getProducts(Categories categories) {

        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
        if (categories.getSubCategoriesModelList().size() > 0) {
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {

                for (int i = 0; i < categories.getSubCategoriesModelList().size(); i++) {

                    if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                            equals(categories.getSubCategoriesModelList().get(i).getId().toString())
                            ||
                            ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                                    equals(categories.getId().toString())) {
                        productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
                        break;

                    }
                }
            }

        } else {
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
                if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                        equals(categories.getId().toString())) {
                    productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));

                }
            }
        }

        return productByLocations;
    }


}
