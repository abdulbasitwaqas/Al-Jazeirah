package com.app.aljazierah.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.SeeAllAdapter;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.aljazierah.utils.Constants.LOGINFROMCART;
import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;

public class MyWishListActivity extends AppCompatActivity{
    private RecyclerView productDetailRV;
    private ImageView imageViewBack;
    private TextView tvToolbarTitleTCA, textview_location, basket_badge;
    private SeeAllAdapter seeAllAdapter;
    private ConstraintLayout cartView;
    ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_my_wish_list);

        initMembers();
    }

    private void initMembers() {

        productDetailRV=findViewById(R.id.productDetailRV);
        tvToolbarTitleTCA=findViewById(R.id.tvToolbarTitleTCA);
        textview_location=findViewById(R.id.textview_location);
        textview_location.setVisibility(View.GONE);
        tvToolbarTitleTCA.setVisibility(View.VISIBLE);
        tvToolbarTitleTCA.setText(""+getResources().getString(R.string.my_wishlist));
        cartView =findViewById(R.id.badge_viewFH);
        basket_badge =findViewById(R.id.basket_badge);

        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
        ProductsSingleton.getInstance().setBasketview(cartView);

        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

        cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                if (user != null) {
                    if (Cart.getTotalItems() < companySetting.minOrder) {
                        Toast.makeText(MyWishListActivity.this, getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                        overridePendingTransition(R.anim.enter_anim, 0);
                    }
                }

                else {
                    Constants.fromcart = true;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMCART);
                    overridePendingTransition(R.anim.enter_anim, 0);
                }


            }

        });

        getCartNumber();
        try {
            if (UserSession.getInstance().getSaveAddressObject().equals("")) {

            } else {
                JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
                textview_location.setText("" + addressObj.getString("details"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        imageViewBack=findViewById(R.id.imageViewBackButton);

        imageViewBack.setVisibility(View.VISIBLE);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setWishlistData();
    }

    public void setWishlistData(){
        productByLocations = new ArrayList<>();
        for (int i = 0; i<ProductsSingleton.getInstance().getProductByLocations().size();i++){

            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getIs_wish_list_item().toString().equals("1"))
            {
                productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
            }
        }
        seeAllAdapter = new SeeAllAdapter(this, productByLocations);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        productDetailRV.setLayoutManager(gridLayoutManager);
        productDetailRV.setAdapter(seeAllAdapter);
    }


    public void updateAmount() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {

            String amount = String.format("%.2f", Cart.getTotalPriceVat());
            ProductsSingleton.getInstance().getTv_cartamount().setText(  getResources().getString(R.string.currency)+ " " + Utils.convertString(amount));

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


    @Override
    public void onResume() {
        super.onResume();
//        getProductByLocation();

        getCartNumber();
        if (ProductsSingleton.getInstance().getProductByLocations() != null) {
            updateListing();
            Log.d("asdasdas","asdasda");
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
        setWishlistData();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}