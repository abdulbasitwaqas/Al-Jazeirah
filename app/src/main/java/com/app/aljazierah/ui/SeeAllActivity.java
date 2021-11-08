package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.app.aljazierah.adapter.CategoriesAdapter;
import com.app.aljazierah.adapter.ProductbyLocationAdapter;
import com.app.aljazierah.adapter.SeeAllAdapter;
import com.app.aljazierah.adapter.SubCategoriesAdapter;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.Promotions.Promotion;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.roam.appdatabase.DatabaseManager;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.aljazierah.utils.Constants.LOGINFROMCART;
import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;
import static com.app.aljazierah.utils.Constants.PROMO_BASE_URL;
import static com.app.aljazierah.utils.Constants.fromcart;
import static com.app.aljazierah.utils.Constants.special_promotion;

public class SeeAllActivity extends AppCompatActivity implements SubCategoriesAdapter.FilterAdapterlistener {
    private RecyclerView productDetailRV, subCategoriesRV;
    //    private ImageView imageViewBack;
    private TextView basket_badge;
    private SeeAllAdapter seeAllAdapter;
    private String categoryId, catName, subCategoryId;
    private ConstraintLayout cartView;
    private FrameLayout seeAllFLSlider;
    private ViewPager viewPagerSlider;
    private CirclePageIndicator indicator;
    private boolean isArabic;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    private LinearLayout bannerLL;
    private ImageView backBtn, searchIV, addNewReqIV, menuBtnHomeScreen;
    private User user;
    private String bestSellerArrayList;
    private ArrayList<ProductByLocation> bestSellingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        this.isArabic = AppController.setLocale();
        setContentView(R.layout.activity_see_all);


        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        categoryId = getIntent().getStringExtra("categoryId");
        subCategoryId = getIntent().getStringExtra("subCategoryId");
        catName = getIntent().getStringExtra("catName");
        bestSellerArrayList = getIntent().getStringExtra("bestSelletList");


//        bestSellerArrayList = (ArrayList<ProductByLocation>)getIntent().getSerializableExtra("bestSelletList");
        Log.d("*categoryId", "" + categoryId);
        initMembers();


    }

    private void initMembers() {

        productDetailRV = findViewById(R.id.productDetailRV);
        subCategoriesRV = findViewById(R.id.subCategoriesRV);
//        tvToolbarTitleTCA=findViewById(R.id.tvToolbarTitleTCA);
//        textview_location=findViewById(R.id.textview_location);
         bannerLL = findViewById(R.id.bannerLL);
//        textview_location.setVisibility(View.GONE);
//        tvToolbarTitleTCA.setVisibility(View.VISIBLE);
//        tvToolbarTitleTCA.setText(""+catName);


        cartView = findViewById(R.id.badge_viewFH);
        basket_badge = findViewById(R.id.basket_badge);

        indicator = findViewById(R.id.sliderCPI);
        viewPagerSlider = findViewById(R.id.viewPagerSlider);
        seeAllFLSlider = findViewById(R.id.seeAllFLSlider);
        menuBtnHomeScreen = findViewById(R.id.menuBtnHomeScreen);
        backBtn = findViewById(R.id.backBtn);
        searchIV = findViewById(R.id.searchIV);
        addNewReqIV = findViewById(R.id.addNewReqIV);


        menuBtnHomeScreen.setVisibility(View.GONE);
//        addNewReqIV.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);

        if (user != null) {
            addNewReqIV.setVisibility(View.VISIBLE);
        } else {
            addNewReqIV.setVisibility(View.VISIBLE);

        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addNewReqIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeAllActivity.this, AfterSalesServicesActivity.class);
                startActivity(intent);
            }
        });

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductsSingleton.getInstance().getProductByLocations().size() > 0)
                    startActivity(new Intent(SeeAllActivity.this, SearchProductsActivity.class));
            }
        });


        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
        ProductsSingleton.getInstance().setBasketview(cartView);
        List<Promotion> sliderList = new ArrayList<>();


        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    if (Cart.getTotalItems() < companySetting.minOrder) {
                        Toast.makeText(SeeAllActivity.this, getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                    }
                } else {
                    Constants.fromcart = true;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMCART);
                }
            }
        });

        for (int i = 0; i < ProductsSingleton.getInstance().getPromotionList().size(); i++) {
            for (int j = 0; j < ProductsSingleton.getInstance().getPromotionList().get(i).getCategory_id_list().size(); j++) {
                if (ProductsSingleton.getInstance().getPromotionList().get(i).getCategory_id_list().get(j).equals(categoryId)) {
                    sliderList.add(ProductsSingleton.getInstance().getPromotionList().get(i));
                }
            }
        }
        Log.d("**promotions", "" + sliderList.size());

        if (sliderList.size() > 0) {
            initSlider(sliderList);
        } else {
            bannerLL.setVisibility(View.GONE);
        }
        getCartNumber();
        try {
            if (UserSession.getInstance().getSaveAddressObject().equals("")) {

            } else {
                JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
//                textview_location.setText("" + addressObj.getString("details"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    /*    imageViewBack=findViewById(R.id.imageViewBackButton);

        imageViewBack.setVisibility(View.VISIBLE);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        Log.d("*bestSellerArrayList", "bestSellerArrayList: " + bestSellerArrayList);

        if (bestSellerArrayList.equals("1")) {
            Log.d("*catID", "" + bestSellerArrayList);
            bestSellingList = new ArrayList<>();
            for (ProductByLocation productByLocation : ProductsSingleton.getInstance().getProductByLocations()) {
                if (productByLocation.getBest_selling().equals("1")) {
                    bestSellingList.add(productByLocation);
                }
            }

            seeAllAdapter = new SeeAllAdapter(SeeAllActivity.this, bestSellingList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            productDetailRV.setLayoutManager(gridLayoutManager);
            productDetailRV.setAdapter(seeAllAdapter);
            subCategoriesRV.setVisibility(View.GONE);
        } else {
            Categories categories = null;
            for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().size(); i++) {
                if (ProductsSingleton.getInstance().getCategoriesList().get(i).getId().toString().equals(categoryId)) {
                    categories = ProductsSingleton.getInstance().getCategoriesList().get(i);
                }
            }
//            Log.d("*catID", "" + getProducts(categories).size());
            if (categories != null) {
                seeAllAdapter = new SeeAllAdapter(SeeAllActivity.this, getProducts(categories));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                productDetailRV.setLayoutManager(gridLayoutManager);
                productDetailRV.setAdapter(seeAllAdapter);
            }


            subCategoriesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            SubCategoriesAdapter productbyLocationAdapter = new SubCategoriesAdapter(categories.getSubCategoriesModelList(), this, this, categoryId, "");
            subCategoriesRV.setAdapter(productbyLocationAdapter);


            if (!subCategoryId.equals("")) {
                filter(Integer.parseInt(subCategoryId), "");
                subCategoriesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                productbyLocationAdapter = new SubCategoriesAdapter(categories.getSubCategoriesModelList(), this, this, categoryId, "" + subCategoryId);
                subCategoriesRV.setAdapter(productbyLocationAdapter);
                Log.d("subCate", "" + subCategoryId);
            }

        }

    }


    @Override
    public void filter(int id, String check) {
        Log.d("filterID", "" + id);

        if (id == 000000000) {
            Categories categories = null;
            for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().size(); i++) {
                if (ProductsSingleton.getInstance().getCategoriesList().get(i).getId().toString().equals(categoryId)) {
                    categories = ProductsSingleton.getInstance().getCategoriesList().get(i);
                }
            }
            if (categories != null) {
                seeAllAdapter.setProductList(getProducts(categories));
            } else {
                Toast.makeText(this, "" + getResources().getString(R.string.no_products_available), Toast.LENGTH_SHORT).show();
            }

        } else {
            List<ProductByLocation> productByLocations = new ArrayList<>();
            for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
                if (ProductsSingleton.getInstance().getProductByLocations().get(i).getCategory_id().toString().equals("" + id)) {
                    productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
                }
            }
            seeAllAdapter.setProductList(productByLocations);
            if (productByLocations.size() == 0)
                Toast.makeText(this, "" + getResources().getString(R.string.no_products_available), Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<ProductByLocation> getProducts(Categories categories) {
        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
        if (categories.getSubCategoriesModelList().size() > 0) {
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
                for (int i = 0; i < categories.getSubCategoriesModelList().size(); i++) {
                    if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                            equals(categories.getSubCategoriesModelList().get(i).getId().toString()) ||
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


    public void updateAmount() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {

            String amount = String.format("%.2f", Cart.getTotalPriceVat());
            ProductsSingleton.getInstance().getTv_cartamount().setText(getResources().getString(R.string.currency) + " " + Utils.convertString(amount));

        } else {
            ProductsSingleton.getInstance().getCartView().setVisibility(View.GONE);
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.GONE);


        }

        if (Cart.getTotalItems() < 1) {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.INVISIBLE);
        } else {
            ProductsSingleton.getInstance().getBasketview().setVisibility(View.VISIBLE);

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


    private void initSlider(List<Promotion> sliderList) {

        viewPagerSlider.setAdapter(new ImageSliderAdapter(SeeAllActivity.this, sliderList));
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
                    Picasso.with(SeeAllActivity.this).load(PROMO_BASE_URL + imageList.get(position).getImageMobileEn())

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

                    Picasso.with(SeeAllActivity.this).load(PROMO_BASE_URL + imageList.get(position).getImageMobileAr())

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
    public void onResume() {
        super.onResume();
//        getProductByLocation();

        getCartNumber();
        if (ProductsSingleton.getInstance().getProductByLocations() != null) {
            updateListing();
            seeAllAdapter = new SeeAllAdapter(SeeAllActivity.this, bestSellingList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            productDetailRV.setLayoutManager(gridLayoutManager);
            productDetailRV.setAdapter(seeAllAdapter);
            subCategoriesRV.setVisibility(View.GONE);
        }

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

    private void updateCategories() {
        Categories categories = null;
        for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().size(); i++) {
            if (ProductsSingleton.getInstance().getCategoriesList().get(i).getId().toString().equals(categoryId)) {
                categories = ProductsSingleton.getInstance().getCategoriesList().get(i);
            }
        }
        if (categories != null) {
            seeAllAdapter = new SeeAllAdapter(SeeAllActivity.this, getProducts(categories));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            productDetailRV.setLayoutManager(gridLayoutManager);
            productDetailRV.setAdapter(seeAllAdapter);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}