package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.app.aljazierah.object.SingleImageModel;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.AddToWishStockAlertRequest;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.Utils;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;
import static com.app.aljazierah.utils.Constants.LOGINFROMCART;
import static com.app.aljazierah.utils.Constants.ORDERS_SCREEN;

public class SingleProductDetailActivity extends AppCompatActivity {

    private String productID, image, prodDescription, productPrice, prodName;
    private String position;
    private ImageView imageViewBack;
    private TextView productNameTV, productDescriptionTV, tvToolbarTitleTCA, textview_location, basket_badge;
    private ImageView btnCounterMinus, btnCounterPlus, shareProduct, compareIV, imageWhishSPD;
    private EditText edtCounterQuantity;
    private ConstraintLayout cartView;
    private FrameLayout flSlider;
    private ViewPager viewPS;
    private CirclePageIndicator productsCI;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    private TextView dataSheet, productInfo, productSKUTV, productcolorTV, productWeightTV, productlengthTV, productdisplayTV, productHeightTV, productPriceTV, productfilterOneTV, productfilterTwoTV, productfilterThreeTV;
    private LinearLayout filterOneLL, filterTwoLL, filterThreeLL, productSKULL, productColorLL, productWeightLL, productLengthLL, productDisplayLL, productHeightLL;
    private boolean isArabic;
    private ProgressDialog mProgressDialog;
    private Dialog showImageDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isArabic = AppController.setLocale();
        setContentView(R.layout.activity_single_product_detail);

        productID = getIntent().getStringExtra("productID");
        position = getIntent().getStringExtra("position");
        Log.d("*position", productID + "__" + position);
        image = getIntent().getStringExtra("image");
//        List<String> imageList = (List<String>) getIntent().getSerializableExtra("image");

        Log.d("sdfsdfsd", getIntent().getStringExtra("productDetails"));

        prodDescription = getIntent().getStringExtra("prodDescription");
        productPrice = getIntent().getStringExtra("productPrice");
        prodName = getIntent().getStringExtra("prodName");

        initMembers();


        List<ProductByLocation> productByLocation = ProductsSingleton.getInstance().getProductByLocations();

        if (getImagesArray() != null)
            imgSlider(getImagesArray());

    }


    private void setDatainview() {
        List<Cart> cartList;
        cartList = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        int count = 0;
        for (int j = 0; j < cartList.size(); j++) {

            for (int k = 0; k < ProductsSingleton.getInstance().getProductByLocations().size(); k++) {
                if (productID.toString().equals(cartList.get(j).productId.toString())) {
                    count = cartList.get(j).count;
                }
            }
        }
        edtCounterQuantity.setText(count + "");

    }

    private List<String> getImagesArray() {
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().toString().equals(productID)) {
                return ProductsSingleton.getInstance().getProductByLocations().get(i).getImg();
            }
        }
        return null;
    }

    private void OpenUrl(String ulr) {
        try {
            Uri uri = Uri.parse("" + ulr); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
            String data = e.getMessage();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initMembers() {
        viewPS = findViewById(R.id.viewPS);
        flSlider = findViewById(R.id.flSlider);
        productsCI = findViewById(R.id.productsSI);


        productNameTV = findViewById(R.id.productNameTV);

        productPriceTV = findViewById(R.id.productPriceTV);
        productfilterOneTV = findViewById(R.id.productfilterOneTV);
        productfilterTwoTV = findViewById(R.id.productfilterTwoTV);
        productfilterThreeTV = findViewById(R.id.productfilterThreeTV);
        productSKUTV = findViewById(R.id.productSKUTV);
        productcolorTV = findViewById(R.id.productcolorTV);
        productWeightTV = findViewById(R.id.productWeightTV);
        productlengthTV = findViewById(R.id.productlengthTV);
        productdisplayTV = findViewById(R.id.productdisplayTV);
        productHeightTV = findViewById(R.id.productHeightTV);
        productInfo = findViewById(R.id.productInfo);
        dataSheet = findViewById(R.id.dataSheet);
        shareProduct = findViewById(R.id.shareProduct);
        compareIV = findViewById(R.id.compareIV);
        imageWhishSPD = findViewById(R.id.imageWhishSPD);


        filterOneLL = findViewById(R.id.filterOneLL);
        filterTwoLL = findViewById(R.id.filterTwoLL);
        filterThreeLL = findViewById(R.id.filterThreeLL);


        productSKULL = findViewById(R.id.productSKULL);
        productColorLL = findViewById(R.id.productColorLL);
        productWeightLL = findViewById(R.id.productWeightLL);
        productLengthLL = findViewById(R.id.productLengthLL);
        productDisplayLL = findViewById(R.id.productDisplayLL);
        productHeightLL = findViewById(R.id.productHeightLL);

        try {
            JSONObject jsonObject1 = new JSONObject(getIntent().getStringExtra("productDetails"));
            productSKUTV.setText(jsonObject1.optString("material"));
            productcolorTV.setText(jsonObject1.optJSONObject("Specifications").optString("color"));
            productWeightTV.setText(jsonObject1.optJSONObject("Specifications").optString("weight"));
            productdisplayTV.setText(jsonObject1.optJSONObject("Specifications").optString("display"));
            productHeightTV.setText(jsonObject1.optJSONObject("Specifications").optString("height"));
            productlengthTV.setText(jsonObject1.optJSONObject("Specifications").optString("Lenght"));
            productfilterOneTV.setText(jsonObject1.optJSONObject("Specifications").optString("Specification_key_1"));
            productfilterTwoTV.setText(jsonObject1.optJSONObject("Specifications").optString("Specification_key_2"));
            productfilterThreeTV.setText(jsonObject1.optJSONObject("Specifications").optString("Specification_key_3"));

            if (jsonObject1.optJSONObject("Specifications").optString("Specification_key_1").equals("") || jsonObject1.optJSONObject("Specifications").optString("Specification_key_1").equals("null")) {
                filterOneLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("Specification_key_2").equals("") || jsonObject1.optJSONObject("Specifications").optString("Specification_key_2").equals("null")) {
                filterTwoLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("Specification_key_3").equals("") || jsonObject1.optJSONObject("Specifications").optString("Specification_key_3").equals("null")) {
                filterThreeLL.setVisibility(View.GONE);
            }

            if (jsonObject1.optString("material").equals("") || jsonObject1.optString("material").equals("null")) {
                productSKULL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("color").equals("") || jsonObject1.optJSONObject("Specifications").optString("color").equals("null")) {
                productColorLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("weight").equals("") || jsonObject1.optJSONObject("Specifications").optString("weight").equals("null")) {
                productWeightLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("display").equals("") || jsonObject1.optJSONObject("Specifications").optString("display").equals("null")) {
                productDisplayLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("height").equals("") || jsonObject1.optJSONObject("Specifications").optString("height").equals("null")) {
                productHeightLL.setVisibility(View.GONE);
            }
            if (jsonObject1.optJSONObject("Specifications").optString("Lenght").equals("") || jsonObject1.optJSONObject("Specifications").optString("Lenght").equals("null")) {
                productLengthLL.setVisibility(View.GONE);
            }


            if (!jsonObject1.optString("catalogue").equals("")) {
                dataSheet.setVisibility(View.VISIBLE);
            } else {
                dataSheet.setVisibility(View.GONE);
            }

            if (!jsonObject1.optString("info_url").equals("")) {
                productInfo.setVisibility(View.VISIBLE);
            } else {
                productInfo.setVisibility(View.GONE);
            }


            if (!jsonObject1.optString("product_share_link").equals("")) {
                shareProduct.setVisibility(View.VISIBLE);
                compareIV.setVisibility(View.VISIBLE);
            } else {
                shareProduct.setVisibility(View.GONE);
                compareIV.setVisibility(View.GONE);
            }


            dataSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenUrl(jsonObject1.optString("catalogue"));
                }
            });

            productInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenUrl(jsonObject1.optString("info_url"));
                }
            });


            shareProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, jsonObject1.optString("product_share_link"));
                    startActivity(Intent.createChooser(intent, ""));
                }
            });

            for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
                if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals("" + productID)) {
                    if (ProductsSingleton.getInstance().getProductByLocations().get(i).getIs_wish_list_item().equals("1")) {
                        imageWhishSPD.setImageResource(R.drawable.ic_favourite_filled);
                    } else if (ProductsSingleton.getInstance().getProductByLocations().get(i).getIs_wish_list_item().equals("0")) {
                        imageWhishSPD.setImageResource(R.drawable.ic_favourite);
                    }
                    break;
                }

            }

            imageWhishSPD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                    for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
                        if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals("" + productID)) {
                            if (user != null) {
                                addToWishList(ProductsSingleton.getInstance().getProductByLocations().get(i), i);
                            } else {
                                startActivity(new Intent(SingleProductDetailActivity.this, LoginActivity.class));
                            }
                            break;
                        }
                    }
                }
            });


            compareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SingleProductDetailActivity.this, CompareProductsActivity.class);
                    intent.putExtra("productID", "" + productID);
                    intent.putExtra("position", "" + position);


                    if (isArabic) {
                        intent.putExtra("prodDescription", "" + Html.fromHtml("" + prodDescription));
                        intent.putExtra("prodName", "" + productNameTV.getText().toString());
                        intent.putExtra("prodColor", "" + productcolorTV.getText().toString());
                        intent.putExtra("prodWeight", "" + productWeightTV.getText().toString());
                        intent.putExtra("prodLength", "" + productlengthTV.getText().toString());
                        intent.putExtra("prodDisplay", "" + productdisplayTV.getText().toString());
                        intent.putExtra("prodHeight", "" + productHeightTV.getText().toString());
                        intent.putExtra("specOne", "" + productfilterOneTV.getText().toString());
                        intent.putExtra("specTwo", "" + productfilterTwoTV.getText().toString());
                        intent.putExtra("specThree", "" + productfilterThreeTV.getText().toString());
                    } else {
                        intent.putExtra("prodDescription", "" + Html.fromHtml("" + prodDescription));
                        intent.putExtra("prodName", "" + productNameTV.getText().toString());
                        intent.putExtra("prodColor", "" + productcolorTV.getText().toString());
                        intent.putExtra("prodWeight", "" + productWeightTV.getText().toString());
                        intent.putExtra("prodLength", "" + productlengthTV.getText().toString());
                        intent.putExtra("prodDisplay", "" + productdisplayTV.getText().toString());
                        intent.putExtra("prodHeight", "" + productHeightTV.getText().toString());
                        intent.putExtra("specOne", "" + productfilterOneTV.getText().toString());
                        intent.putExtra("specTwo", "" + productfilterTwoTV.getText().toString());
                        intent.putExtra("specThree", "" + productfilterThreeTV.getText().toString());
                    }
                    intent.putExtra("productPrice", "" + productPrice.toString());
                    intent.putExtra("prodImage", "" + image);


                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        productDescriptionTV = findViewById(R.id.productDescriptionTV);
        btnCounterMinus = findViewById(R.id.btnCounterMinus);
        btnCounterPlus = findViewById(R.id.btnCounterPlus);
        edtCounterQuantity = findViewById(R.id.edtCounterQuantity);

//        menuBtn=findViewById(R.id.menuBtn);
        imageViewBack = findViewById(R.id.imageViewBackButton);
        tvToolbarTitleTCA = findViewById(R.id.tvToolbarTitleTCA);
        textview_location = findViewById(R.id.textview_location);
        textview_location.setVisibility(View.GONE);


        cartView = findViewById(R.id.badge_viewFH);
        basket_badge = findViewById(R.id.basket_badge);

        tvToolbarTitleTCA.setText("" + getResources().getString(R.string.st_product));

//        menuBtn.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.VISIBLE);
        tvToolbarTitleTCA.setVisibility(View.VISIBLE);

        SingleImageModel singleImageModel = new SingleImageModel();
        singleImageModel.setImageURL("" + image);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            if (UserSession.getInstance().getSaveAddressObject().equals("")) {
            } else {
                JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
                textview_location.setText("" + addressObj.getString("details"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        productNameTV.setText("" + prodName);
        productPriceTV.setText(getResources().getString(R.string.currency) + " " + productPrice);
        productDescriptionTV.setText(Html.fromHtml("" + prodDescription));
//        productDescriptionTV.setText(""+prodDescription);

        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            Log.d("**productID", "" + ProductsSingleton.getInstance().getProductByLocations().get(i).getId());

            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals("" + Integer.parseInt(productID))) {
                edtCounterQuantity.setText("" + ProductsSingleton.getInstance().getProductByLocations().get(i).getCount());
            }

        }
        setEditext();
        ProductsSingleton.getInstance().setBasket_badge(basket_badge);
        ProductsSingleton.getInstance().setBasketview(cartView);
        
    /*    btnCounterPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i<ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
                    if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals(""+Integer.parseInt(productID))){

                        int count = ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() + 1;
                        ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(count);

                        edtCounterQuantity.setText(ProductsSingleton.getInstance().getProductByLocations().get(i).getCount()+"");
                        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", ProductsSingleton.getInstance().getProductByLocations().get(i).getId(),
                                Cart.class);
                        if(result==null){
                            Cart.addToCart(ProductsSingleton.getInstance().getProductByLocations().get(i), -1,1);
                        }
                        else{
                            Cart.addOneItem(ProductsSingleton.getInstance().getProductByLocations().get(i).getId());
                        }
                        ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
                        updateAmount();
                        ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems()+"");

                        showCartLayout();
                        getCartNumber();

                        Map<String, Object> eventValue = new HashMap<String, Object>();
                        eventValue.put(AFInAppEventParameterName.CONTENT_ID,ProductsSingleton.getInstance().getProductByLocations().get(i).getId());
                        eventValue.put(AFInAppEventParameterName.PRICE,""+ProductsSingleton.getInstance().getProductByLocations().get(i).getPrice());
                        eventValue.put(AFInAppEventParameterName.PARAM_1,"Product Name "+ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn());
                        eventValue.put(AFInAppEventParameterName.PARAM_2,"Quantity"+"1");
                        eventValue.put(AFInAppEventParameterName.PARAM_3,"Stock Availability"+"Yes");
                    }
                }
            }
        });

        btnCounterMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i<ProductsSingleton.getInstance().getProductByLocations().size(); i++){
                    if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals(""+Integer.parseInt(productID))){
                        if (ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() < 1) {
                            int count = 0;
                            ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(count);
                            edtCounterQuantity.setText(ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() + "");
                        }
                        else if (ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() == 1) {
                            int count = ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() - 1;
                            ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(count);
                            edtCounterQuantity.setText(ProductsSingleton.getInstance().getProductByLocations().get(i).getCount()+"");
                            Cart.deleteProductFromCart(ProductsSingleton.getInstance().getProductByLocations().get(i).getId());
                            updateAmount();
                            ProductsSingleton.getInstance().getProductByLocations().get(i).setAddedToCart(false);
                            ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems()+"");

                            getCartNumber();
                        }
                        else {
                            int count = ProductsSingleton.getInstance().getProductByLocations().get(i).getCount() - 1;
                            ProductsSingleton.getInstance().getProductByLocations().get(i).setCount(count);
                            edtCounterQuantity.setText(ProductsSingleton.getInstance().getProductByLocations().get(i).getCount()+"");
                            Cart.removeOneItem(ProductsSingleton.getInstance().getProductByLocations().get(Integer.parseInt(position)).getId());
                            updateAmount();
                            ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems()+"");
                            getCartNumber();
                        }

                    }
                }


            }
        });

*/


        btnCounterPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(edtCounterQuantity.getText().toString()) + 1;
                // productList.get(position).setCount(count);

                edtCounterQuantity.setText(count + "");
                Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productID, Cart.class);
                ProductByLocation productByLocation = getProduct(productID);
                productByLocation.setCount(count);

                if (result == null) {
                    Cart.addToCart(productByLocation, -1, 1);
                } else {
                    Cart.addOneItem(productID);
                }

                ProductsSingleton.getInstance().getBasket_badge().setVisibility(View.VISIBLE);
                updateAmount();
                ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                showCartLayout();
                getCartNumber();

            }
        });

        btnCounterMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtCounterQuantity.getText().toString()) < 1) {
                    int count = 0;
                    edtCounterQuantity.setText(count + "");
                } else if (Integer.parseInt(edtCounterQuantity.getText().toString()) == 1) {
                    int count = Integer.parseInt(edtCounterQuantity.getText().toString()) - 1;
                    edtCounterQuantity.setText(count + "");
                    Cart.deleteProductFromCart(productID);
                    updateAmount();
                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                    getCartNumber();
                } else {

                    int count = Integer.parseInt(edtCounterQuantity.getText().toString()) - 1;
                    edtCounterQuantity.setText(count + "");
                    Cart.removeOneItem(productID);
                    updateAmount();
                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems() + "");
                    getCartNumber();
                }
            }
        });


        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

        cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                if (user != null) {
                    if (Cart.getTotalItems() >= companySetting.minOrder) {
                        Intent intent = new Intent(SingleProductDetailActivity.this, CheckOutActivity.class);
                        intent.putExtra("order", false);
                        startActivityForResult(intent, ORDERS_SCREEN);
                        overridePendingTransition(R.anim.enter_anim, 0);
                    } else {
                        Toast.makeText(SingleProductDetailActivity.this, getResources().getString(R.string.min_order_amount) + " " + companySetting.minOrder, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Constants.fromcart = true;
                    Intent intent = new Intent(SingleProductDetailActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGINFROMCART);
                    overridePendingTransition(R.anim.enter_anim, 0);
                }
            }
        });

        getCartNumber();

    }

    public void updateAmount() {
        String[] data = Cart.getTotalItemsAndPrice();
        if (data != null) {

            String amount = String.format("%.2f", Cart.getTotalPriceVat());
            ProductsSingleton.getInstance().getTv_cartamount().setText(getResources().getString(R.string.currency) + " " + Utils.convertString(amount));

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


    private void setEditext() {

        edtCounterQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (edtCounterQuantity.getText().toString().equals("")) {
                        edtCounterQuantity.setText("0");
                    }

                    int count = Integer.parseInt(edtCounterQuantity.getText().toString());
                    if (count < 1) {
                        Cart.deleteProductFromCart(productID);
                        //productList.get(position).setCount(count);
                        updateAmount();
                    } else {
                        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productID,
                                Cart.class);
                        Cart.removeItems(productID);
                        ProductByLocation productByLocation = getProduct(productID);
                        productByLocation.setCount(count);

                        if (result == null) {
                            Cart.addToCart(productByLocation, -1, count);
                        } else {
                            Cart.addMultipleItem(productID, count, productByLocation);
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

                }
                return false;
            }
        });
        
        
   /*     edtCounterQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    int count = Integer.parseInt(edtCounterQuantity.getText().toString());
                    Log.e(count+"",edtCounterQuantity.getText().toString()+"");

                    Cart result = DatabaseManager.getInstance().getFirstMatching("productId",productID,
                            Cart.class);
                    Cart.removeItems(productID);
                   ProductByLocation productByLocation = getProduct(productID);
                   productByLocation.setCount(count);

                    if(result==null){
                        Cart.addToCart( productByLocation, -1,count);
                    }
                    else{
                        Cart.addMultipleItem(productID,count,productByLocation);
                    }

                    updateAmount();
                    ProductsSingleton.getInstance().getBasket_badge().setText(Cart.getTotalItems()+"");
                    getCartNumber();
                    new Utils().updateAmount(AppController.setLocale());

                }
                return false;
            }
        });*/
    }


    private ProductByLocation getProduct(String productID) {

        ProductByLocation productByLocation = null;


        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().equals(productID)) {

                return ProductsSingleton.getInstance().getProductByLocations().get(i);
            }
        }


        return null;

    }


    @Override
    protected void onResume() {
        super.onResume();
        getCartNumber();
        setDatainview();
    }


    public void imgSlider(List<String> images) {
       /* for (int i=0; i<productByLocations.size();i++){

        }*/
        viewPS.setAdapter(new ImageSliderAdapter(this, images));
        productsCI.setViewPager(viewPS);
        final float density = getResources().getDisplayMetrics().density;
        productsCI.setRadius(3 * density);
        NUM_PAGES = images.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPS.setCurrentItem(currentPage++, true);

            }
        };
        /*Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/


        // Pager listener over productsCI
        productsCI.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        private List<String> imageList;
        private LayoutInflater inflater;

        //
        public ImageSliderAdapter(Context context, List<String> list) {
            mContext = context;
            imageList = list;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.multiple_images_layout, collection, false);

            final ImageView imageView = imageLayout.findViewById(R.id.imageview);
            final ProgressBar progress_bar = imageLayout.findViewById(R.id.progress_bar);
            imageView.setVisibility(View.GONE);
            progress_bar.setVisibility(View.VISIBLE);
            try {
                Picasso.with(mContext).load(IMAGE_BASE_URL + imageList.get(position))
                        .into(imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                imageView.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.GONE);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showImageDialog = new Dialog(SingleProductDetailActivity.this);
                                        showImageDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        showImageDialog.getWindow().setGravity(Gravity.CENTER);
                                        showImageDialog.setCancelable(false);
                                        showImageDialog.setContentView(R.layout.show_pic_layout);

                                        ImageView imageViewSPL = showImageDialog.findViewById(R.id.imageViewSPL);
                                        ImageView cancelDialogIV = showImageDialog.findViewById(R.id.cancelDialogIV);

                                        imageViewSPL.setOnTouchListener(new ImageMatrixTouchHandler(imageViewSPL.getContext()));

                                        Glide.with(SingleProductDetailActivity.this)
                                                .load(IMAGE_BASE_URL + "" + imageList.get(position))
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
                            }

                            @Override
                            public void onError() {
                            }
                        });

            } catch (Exception e) {
                Log.e("IMage SLider Adapter :", e.getMessage());
            }


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


    private void addToWishList(ProductByLocation productByLocation, int position) {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        String action = "0";
        if (productByLocation.getIs_wish_list_item().equals("0")) {
            action = "1";
        }
        showProgress(true);
        Log.d("**favrequestbody", position + new Gson().toJson(new AddToWishStockAlertRequest(user.userId, productID, "wish_list", action)));
        APIManager.getInstance().addTowishListStockAlert(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("code").equals("200")) {
                        if (isArabic)
                            Toast.makeText(SingleProductDetailActivity.this, "" + jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SingleProductDetailActivity.this, "" + jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                        if (productByLocation.getIs_wish_list_item().equals("0")) {
                            //ProductsSingleton.getInstance().getProductByLocations().get(position).setIs_wish_list_item("1");
                            productByLocation.setIs_wish_list_item("1");
                            updateSingletonList(productID, "1");
                        } else {
                            // ProductsSingleton.getInstance().getProductByLocations().get(position).setIs_wish_list_item("0");
                            productByLocation.setIs_wish_list_item("0");
                            updateSingletonList(productID, "0");

                        }
                        initMembers();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new AddToWishStockAlertRequest(user.userId, productID, "wish_list", action));
    }

    public void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(SingleProductDetailActivity.this);
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


    private void updateSingletonList(String id, String action) {
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getId().toString().equals(id)) {
                Log.d("sdfasfas", id);
                ProductsSingleton.getInstance().getProductByLocations().get(i).setIs_wish_list_item(action);
            }
        }
    }
}