package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.SearchAdapter;
import com.app.aljazierah.adapter.ShowRoomsAdapterSPA;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.SearchWord;
import com.app.aljazierah.object.Store;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchProductsActivity extends AppCompatActivity {
    private EditText searchETSPA;
    private ImageView backBtnSPA;
    private RecyclerView recyclerViewSPA;
    private SearchAdapter searchAdapter;
    private String et_search;
    private Button searchDoneBtn;
    private RecyclerView showRoomsRvSPA;
    private ShowRoomsAdapterSPA showRoomsAdapterSPA;
    private ProgressDialog mProgressDialog;
    private ImageView imageSerarch;
    List<Store> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_search_products);
        initMembers();
    }

    private void initMembers() {
        searchETSPA = findViewById(R.id.searchETSPA);
        backBtnSPA = findViewById(R.id.backBtnSPA);
        recyclerViewSPA = findViewById(R.id.recyclerViewSPA);
        showRoomsRvSPA = findViewById(R.id.showRoomsRvSPA);
        searchDoneBtn = findViewById(R.id.searchDoneBtn);
        imageSerarch = findViewById(R.id.imageSerarch);


        backBtnSPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchETSPA.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            /*    if (s.length() == 0) {
                    searchAdapter.setProductList(ProductsSingleton.getInstance().getProductByLocations());
                }*/
            }
        });


        searchDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search = searchETSPA.getText().toString().toLowerCase();

                if (!et_search.equals("")) {

                    filter(et_search);
                    word_search(et_search);
                    hideKeyboard(SearchProductsActivity.this);
                }
            }
        });

        searchAdapter = new SearchAdapter(this, new ArrayList<ProductByLocation>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewSPA.setLayoutManager(gridLayoutManager);
        recyclerViewSPA.setAdapter(searchAdapter);

        imageSerarch.setVisibility(View.VISIBLE);
        recyclerViewSPA.setVisibility(View.GONE);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void filter(String searchingText) {

        String[] splitedWords = searchingText.split("\\s+");
        List<ProductByLocation> productByLocations = new ArrayList<>();

        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            for (int j = 0; j < splitedWords.length; j++) {
                Log.d("*name", "" + splitedWords[j]);
                if (ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn().toLowerCase().contains(splitedWords[j].toLowerCase()) ||
                        ProductsSingleton.getInstance().getProductByLocations().get(i).getNameAr().toLowerCase().contains(splitedWords[j].toLowerCase()) ||
                        ProductsSingleton.getInstance().getProductByLocations().get(i).getDescriptionEn().toLowerCase().contains(splitedWords[j].toLowerCase()) ||
                        ProductsSingleton.getInstance().getProductByLocations().get(i).getDescriptionAr().toLowerCase().contains(splitedWords[j].toLowerCase())) {

                    productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
                    break;
                }
            }
        }



                if (productByLocations.size()>0) {
                    searchAdapter.setProductList(productByLocations);
                    imageSerarch.setVisibility(View.GONE);
                    recyclerViewSPA.setVisibility(View.VISIBLE);
                }else {
                    imageSerarch.setVisibility(View.VISIBLE);
                    recyclerViewSPA.setVisibility(View.GONE);
                }

        /*    Log.d("*name", "" + searchingText);

        List<ProductByLocation> productByLocations = new ArrayList<>();
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            if (searchingText.toLowerCase().contains(ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn().toLowerCase().toLowerCase()) ||
                    searchingText.toLowerCase().contains(ProductsSingleton.getInstance().getProductByLocations().get(i).getNameAr()) ||
                    ProductsSingleton.getInstance().getProductByLocations().get(i).getDescriptionEn().toLowerCase().toLowerCase().contains(searchingText) ||
                    ProductsSingleton.getInstance().getProductByLocations().get(i).getDescriptionAr().contains(searchingText)) {

                productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(i));
                Log.d("*nameaaasd", "" + searchingText);
            }
        }
        searchAdapter.setProductList(productByLocations);
//        recyclerViewSPA.setVisibility(View.VISIBLE);*/


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


    private void word_search(String s) {
        String user_id="";
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null)
            user_id = user.userId;
        APIManager.getInstance().word_search(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.d("SPshowRooms", "Response: " + response);
                if (z) {
                    Log.e("" + z, response);
                    Log.e("" + z, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();

                        Type listType = new TypeToken<List<Store>>() {
                        }.getType();
                        storeList = gson.fromJson(jsonObject.getString("stores"), listType);

                        if (storeList.size()==0){
                            showRoomsRvSPA.setVisibility(View.GONE);
                        }
                        else {
                            showRoomsRvSPA.setVisibility(View.VISIBLE);
                            showRoomsAdapterSPA = new ShowRoomsAdapterSPA(storeList, SearchProductsActivity.this);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchProductsActivity.this, 1);
                            showRoomsRvSPA.setLayoutManager(gridLayoutManager);
                            showRoomsRvSPA.setAdapter(showRoomsAdapterSPA);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }, new SearchWord(s,user_id));
    }


    private void getStores() {
        showProgress(true);
        APIManager.getInstance().getStoresRequest(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);

                if (z) {
                    Log.e("" + z, response);
                    Log.e("" + z, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();

                        Type listType = new TypeToken<List<Store>>() {
                        }.getType();
                        storeList = gson.fromJson(jsonObject.getString("stores"), listType);


                        showRoomsAdapterSPA = new ShowRoomsAdapterSPA(storeList, SearchProductsActivity.this);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchProductsActivity.this, 1);
                        recyclerViewSPA.setLayoutManager(gridLayoutManager);
                        recyclerViewSPA.setAdapter(showRoomsAdapterSPA);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void showProgress(boolean show) {
        try {
            if (show) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
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
        } catch (Exception ex) {

        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}