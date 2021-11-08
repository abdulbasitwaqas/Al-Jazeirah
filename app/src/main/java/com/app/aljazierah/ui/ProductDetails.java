package com.app.aljazierah.ui;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener{
    private boolean isArabic;
    private ProductByLocation productByLocation;
    EditText editext_productquantity;
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_product_details);
        setViews();

    }
    private void setViews(){
        setToolbar();
        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);
        ImageView iv_productimage=findViewById(R.id.iv_productimage);
        TextView tv_productprice=findViewById(R.id.tv_productprice);
        TextView tv_producttype=findViewById(R.id.tv_producttype);
        TextView tv_productname=findViewById(R.id.tv_productname);
        TextView tv_productcarton=findViewById(R.id.tv_productcarton);
        TextView tv_productpriceview=findViewById(R.id.tv_productpriceview);
        editext_productquantity = findViewById(R.id.edtCounterQuantity);
        Button  minusproduct_button=findViewById(R.id.btnCounterMinus);
        Button  addproduct_button=findViewById(R.id.btnCounterPlus);
        minusproduct_button.setOnClickListener(this);
        addproduct_button.setOnClickListener(this);

        setDataFromIntent(iv_productimage,tv_productprice,tv_producttype,tv_productname,tv_productcarton,
                tv_productpriceview,editext_productquantity);
    }

    private void setToolbar(){
        View toolbar=findViewById(R.id.toolbar);
        TextView title_textview=toolbar.findViewById(R.id.tvToolbarTitleTCA);
        ImageView iv_backbtn=toolbar.findViewById(R.id.imageViewBackButton);
        if(isArabic){
            title_textview.setText("تفاصيل المنتجات");
        }
        else{
            title_textview.setText("Products Details");

        }
        View badge_view=toolbar.findViewById(R.id.badge_viewFH);
        badge_view.setVisibility(View.GONE);
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(this);


    }

    private void setDataFromIntent(ImageView iv_productimage,TextView tv_productprice,TextView tv_producttype,
                                   TextView tv_productname,TextView tv_productcarton,TextView tv_productpriceview,
                                   EditText editext_productquantity){
        productByLocation = new Gson().fromJson(getIntent().getStringExtra("product_object"),
                ProductByLocation.class);
        Glide.with(ProductDetails.this)
                .load(IMAGE_BASE_URL + getIntent().getStringExtra("product_image"))
                .placeholder(R.drawable.loading_spinner)
                .into(iv_productimage);
        if(isArabic){
            String[] name_array=productByLocation.getNameAr().trim().split("\\)");



            if(name_array.length>1){

                tv_productcarton.setText("Bottles Per Carton: "+name_array[1].replace("(",""));
            }
            else{


            }


            tv_productprice.setText(getIntent().getStringExtra("product_price")+" SR");
            tv_producttype.setText(getIntent().getStringExtra("product_cat")+" :"+getResources().getString(R.string.st_productype));
            tv_productname.setText(getResources().getString(R.string.st_product_name)+" :"+name_array[0]);
            tv_productpriceview.setText("SR "+getIntent().getStringExtra("product_price")+" :"+getResources().getString(R.string.st_price));
            editext_productquantity.setText(getIntent().getStringExtra("product_count"));

        }
        else{
            String[] name_array=productByLocation.getNameEn().trim().split("\\(");

            if(name_array.length>1){

                tv_productcarton.setText("Bottles Per Carton: "+name_array[1].replace(")",""));
            }
            else{


            }
            tv_productprice.setText(getIntent().getStringExtra("product_price")+" SR");
            tv_producttype.setText("Product Type:"+getIntent().getStringExtra("product_cat"));
            tv_productname.setText("Product Name: "+name_array[0]);
            tv_productpriceview.setText("Price: "+getIntent().getStringExtra("product_price")+" SR");
            editext_productquantity.setText(getIntent().getStringExtra("product_count"));
        }
        setEditext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCounterPlus:
                addProduct();
            break;
            case R.id.btnCounterMinus:
                removeProduct();
                break;
            case R.id.imageViewBackButton:
                finish();
                overridePendingTransition(0,R.anim.exit_anim);

                break;

        }
    }

    private void addProduct(){


        int count =productByLocation.getCount() + 1;
        productByLocation.setCount(count);
        editext_productquantity.setText(productByLocation.getCount()+"");


        Cart result = DatabaseManager.getInstance().getFirstMatching("productId",productByLocation.getId(),
                Cart.class);
        if(result==null){
            Cart.addToCart( productByLocation, -1,1);
        }
        else{
            Cart.addOneItem(productByLocation.getId());
        }
        new Utils().updateAmount(isArabic);
    }

    private void removeProduct(){
        if (productByLocation.getCount() < 1) {
            int count = 0;
            productByLocation.setCount(count);
           editext_productquantity.setText(productByLocation.getCount()+"");
        }

        else if (productByLocation.getCount() == 1) {
            int count = productByLocation.getCount() - 1;
            productByLocation.setCount(count);
            editext_productquantity.setText(productByLocation.getCount()+"");
            Cart.deleteProductFromCart(productByLocation.getId());
            new Utils().updateAmount(isArabic);
            productByLocation.setAddedToCart(false);
        } else {
            int count = productByLocation.getCount() - 1;
            productByLocation.setCount(count);
            editext_productquantity.setText(productByLocation.getCount()+"");
            Cart.removeOneItem(productByLocation.getId());
            new Utils().updateAmount(isArabic);

        }
    }

    private void setEditext(){
        editext_productquantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    int count = Integer.parseInt(editext_productquantity.getText().toString());
                    Log.e(count+"",editext_productquantity.getText().toString()+"");

                    Cart result = DatabaseManager.getInstance().getFirstMatching("productId",productByLocation.getId(),
                            Cart.class);
                    Cart.removeItems(productByLocation.getId());
                    productByLocation.setCount(count);
                    Log.e(count+"", productByLocation.getCount()+"");
                    if(result==null){
                        Cart.addToCart( productByLocation, -1,count);
                    }
                    else{
                        Cart.addMultipleItem(productByLocation.getId(),count,productByLocation);
                    }
                    new Utils().updateAmount(isArabic);

                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                overridePendingTransition(0,R.anim.exit_anim);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,R.anim.exit_anim);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
