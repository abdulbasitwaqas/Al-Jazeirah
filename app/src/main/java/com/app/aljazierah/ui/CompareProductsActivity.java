package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.utils.ProductsSingleton;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.app.aljazierah.utils.Constants.IMAGE_BASE_URL;

public class CompareProductsActivity extends AppCompatActivity {
    private ImageView backBtnCPA;
    private Spinner compareProdSpinnerL;
    private ArrayAdapter<String> adapter;
    private String productName = "";
    private int modelPosition = -1;
    private boolean isArabic;
    private TextView productNameTVOP, productcolorTVFP, productWeightTVFP, productlengthTVFP, productdisplayTVFP, productHeightTVFP, productfilterOneTVFP,
            productfilterTwoTVFP, productfilterThreeTVFP, productPriceTVFP, descriptionTVOP, descriptionTVSP;

    private LinearLayout secondProdLL, productColorLL, productWeightLL, productLengthLL, productDisplayLL, productHeightLL, filterOneLL, filterTwoLL, filterThreeLL, priceLL;
    private String productID = "", position = "", productDetails = "", prodDescription = "", prodName = "", prodColor = "", prodWeight = "", prodLength = "", prodDisplay = "", prodHeight = "",
            specOne = "", specTwo = "", specThree = "", productPrice = "", prodImage="", prodDesOP, prodDesSP;


    private TextView productNameTVRP, productcolorTVSP, productWeightTVSP, productlengthTVSP, productdisplayTVSP, productHeightTVSP, productfilterOneTVSP,
            productfilterTwoTVSP, productfilterThreeTVSP, productPriceTVSP;
    private ImageView productIVFP,productIVtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isArabic = AppController.setLocale();
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_compare_products);

        initMembers();

    }

    private void initMembers() {

        productID = getIntent().getStringExtra("productID");
        position = "" + getIntent().getStringExtra("position");
        Log.d("*position", productID + "__" + position);
        prodDescription = getIntent().getStringExtra("prodDescription");
        productPrice = getIntent().getStringExtra("productPrice");
        prodImage = getIntent().getStringExtra("prodImage");
        prodName = getIntent().getStringExtra("prodName");
        prodColor = getIntent().getStringExtra("prodColor");
        prodWeight = getIntent().getStringExtra("prodWeight");
        prodLength = getIntent().getStringExtra("prodLength");
        prodDisplay = getIntent().getStringExtra("prodDisplay");
        prodHeight = getIntent().getStringExtra("prodHeight");
        specOne = getIntent().getStringExtra("specOne");
        specTwo = getIntent().getStringExtra("specTwo");
        specThree = getIntent().getStringExtra("specThree");




        backBtnCPA = findViewById(R.id.backBtnCPA);
        compareProdSpinnerL = findViewById(R.id.compareProdSpinnerL);
        productcolorTVFP = findViewById(R.id.productcolorTVFP);

        productNameTVOP = findViewById(R.id.productNameTVOP);
        descriptionTVOP = findViewById(R.id.descriptionTVOP);
        productWeightTVFP = findViewById(R.id.productWeightTVFP);
        productlengthTVFP = findViewById(R.id.productlengthTVFP);
        productdisplayTVFP = findViewById(R.id.productdisplayTVFP);
        productHeightTVFP = findViewById(R.id.productHeightTVFP);
        productfilterOneTVFP = findViewById(R.id.productfilterOneTVFP);
        productfilterTwoTVFP = findViewById(R.id.productfilterTwoTVFP);
        productfilterThreeTVFP = findViewById(R.id.productfilterThreeTVFP);
        productPriceTVFP = findViewById(R.id.productPriceTVFP);

        productIVFP = findViewById(R.id.productIVFP);
        productIVtwo = findViewById(R.id.productIVtwo);


        productNameTVRP = findViewById(R.id.productNameTVRP);
        descriptionTVSP = findViewById(R.id.descriptionTVSP);
        productcolorTVSP = findViewById(R.id.productcolorTVSP);
        productWeightTVSP = findViewById(R.id.productWeightTVSP);
        productlengthTVSP = findViewById(R.id.productlengthTVSP);
        productdisplayTVSP = findViewById(R.id.productdisplayTVSP);
        productHeightTVSP = findViewById(R.id.productHeightTVSP);
        productfilterOneTVSP = findViewById(R.id.productfilterOneTVSP);
        productfilterTwoTVSP = findViewById(R.id.productfilterTwoTVSP);
        productfilterThreeTVSP = findViewById(R.id.productfilterThreeTVSP);
        productPriceTVSP = findViewById(R.id.productPriceTVSP);


        productColorLL = findViewById(R.id.productColorLL);
        secondProdLL = findViewById(R.id.secondProdLL);
        productWeightLL = findViewById(R.id.productWeightLL);
        productLengthLL = findViewById(R.id.productLengthLL);
        productDisplayLL = findViewById(R.id.productDisplayLL);
        productHeightLL = findViewById(R.id.productHeightLL);
        filterOneLL = findViewById(R.id.filterOneLL);
        filterTwoLL = findViewById(R.id.filterTwoLL);
        filterThreeLL = findViewById(R.id.filterThreeLL);
        priceLL = findViewById(R.id.priceLL);

        List<String> nameList = new ArrayList<>();

        
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            if (isArabic) {
                nameList.add(ProductsSingleton.getInstance().getProductByLocations().get(i).getNameAr());
            } else {
                nameList.add(ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn());
            }
        }

        Log.d("ssdfsfsfsd",ProductsSingleton.getInstance().getProductByLocations().size()+"");

        productcolorTVFP.setText(prodColor.toString().trim());
        productWeightTVFP.setText(prodWeight.toString().trim());
        productdisplayTVFP.setText(prodDisplay.toString().trim());
        productHeightTVFP.setText(prodHeight.toString().trim());
        productlengthTVFP.setText(prodLength.toString().trim());
        productfilterOneTVFP.setText(specOne.toString().trim());
        productfilterTwoTVFP.setText(specTwo.toString().trim());
        productfilterThreeTVFP.setText(specThree.toString().trim());
        productPriceTVFP.setText(productPrice.toString().trim());
        productNameTVOP.setText(prodName.toString().trim());
        descriptionTVOP.setText(prodDescription.toString().trim());
        descriptionTVOP.setMovementMethod(new ScrollingMovementMethod());
        if (specOne.equals("") || specOne.equals("null")) {
            filterOneLL.setVisibility(View.GONE);
        }
        if (specTwo.equals("") || specTwo.equals("null")) {
            filterTwoLL.setVisibility(View.GONE);
        }
        if (specThree.equals("") || specThree.equals("null")) {
            filterThreeLL.setVisibility(View.GONE);
        }
        if (prodDescription.equals("") || prodDescription.equals("null")) {
            descriptionTVOP.setText("-");
        }


        if (prodColor.equals("") || prodColor.equals("null")) {
            productColorLL.setVisibility(View.GONE);
        }
        if (prodWeight.equals("") || prodWeight.equals("null")) {
            productWeightLL.setVisibility(View.GONE);
        }
        if (prodDisplay.equals("") || prodDisplay.equals("null")) {
            productDisplayLL.setVisibility(View.GONE);
        }
        if (prodHeight.equals("") || prodHeight.equals("null")) {
            productHeightLL.setVisibility(View.GONE);
        }
        if (prodLength.equals("") || prodLength.equals("null")) {
            productLengthLL.setVisibility(View.GONE);
        }
        prodImage = prodImage.replaceAll("[\\[\\]\\(\\)]", "");

        Glide.with(CompareProductsActivity.this)
                .load(IMAGE_BASE_URL+""+prodImage )
                .placeholder(R.drawable.loading_spinner)
                .into(productIVFP);



        if (productcolorTVFP.getText().toString().equals("") &&  productcolorTVSP.getText().toString().equals("")){
            productColorLL.setVisibility(View.GONE);
            productcolorTVSP.setVisibility(View.GONE);
        }
        if (productWeightTVFP.getText().toString().equals("") &&  productWeightTVSP.getText().toString().equals("")){
            productWeightLL.setVisibility(View.GONE);
            productWeightTVSP.setVisibility(View.GONE);
        }
        if (productlengthTVFP.getText().toString().equals("") &&  productlengthTVSP.getText().toString().equals("")){
            productLengthLL.setVisibility(View.GONE);
            productlengthTVSP.setVisibility(View.GONE);
        }
        if (productdisplayTVFP.getText().toString().equals("") &&  productdisplayTVSP.getText().toString().equals("")){
            productDisplayLL.setVisibility(View.GONE);
            productdisplayTVSP.setVisibility(View.GONE);
        }
        if (productHeightTVFP.getText().toString().equals("") &&  productHeightTVSP.getText().toString().equals("")){
            productHeightLL.setVisibility(View.GONE);
            productHeightTVSP.setVisibility(View.GONE);
        }
        if (productfilterOneTVFP.getText().toString().equals("") &&  productfilterOneTVSP.getText().toString().equals("")){
            filterOneLL.setVisibility(View.GONE);
            productfilterOneTVSP.setVisibility(View.GONE);
        }
        if (productfilterTwoTVFP.getText().toString().equals("") &&  productfilterTwoTVSP.getText().toString().equals("")){
            filterTwoLL.setVisibility(View.GONE);
            productfilterTwoTVSP.setVisibility(View.GONE);
        }
        if (productfilterThreeTVFP.getText().toString().equals("") &&  productfilterThreeTVSP.getText().toString().equals("")){
            filterThreeLL.setVisibility(View.GONE);
            productfilterThreeTVSP.setVisibility(View.GONE);
        }
        if (productPriceTVFP.getText().toString().equals("") &&  productPriceTVSP.getText().toString().equals("")){
            priceLL.setVisibility(View.GONE);
            productPriceTVSP.setVisibility(View.GONE);
        }



        Log.d("**image",IMAGE_BASE_URL+""+prodImage);
        adapter = new ArrayAdapter<>(CompareProductsActivity.this, R.layout.main_spinner_layout, nameList);
        Log.d("**prodName", "" + nameList.toString());

        compareProdSpinnerL.setAdapter(adapter);

        backBtnCPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        compareProdSpinnerL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Log.d("**selectedProd", "" + compareProdSpinnerL.getSelectedItem().toString());
                productName = compareProdSpinnerL.getSelectedItem().toString();
                modelPosition = position;
                filter(""+compareProdSpinnerL.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void filter(String searchingText) {
        ProductByLocation productByLocation = null;
        for (int i = 0; i < ProductsSingleton.getInstance().getProductByLocations().size(); i++) {
            Log.d("**selectedProd11", searchingText+"," + ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn());

            if (ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn().toLowerCase().toString().equals(searchingText.toLowerCase().trim())
                        ||ProductsSingleton.getInstance().getProductByLocations().get(i).getNameAr().toString().equals(searchingText)) {
                Log.d("**selectedProd22", "" + ProductsSingleton.getInstance().getProductByLocations().get(i).getNameEn());

                productByLocation = ProductsSingleton.getInstance().getProductByLocations().get(i);

                break;
                }
            }
        


        if (productByLocation !=null) {
            if (isArabic) {
                productNameTVRP.setText(productByLocation.getNameAr());
                descriptionTVSP.setText(Html.fromHtml(""+productByLocation.getDescriptionAr()));
            }
            productNameTVRP.setText(productByLocation.getNameEn());
            descriptionTVSP.setText(Html.fromHtml(""+productByLocation.getDescriptionEn()));

            descriptionTVSP.setMovementMethod(new ScrollingMovementMethod());

            productcolorTVSP.setText(productByLocation.getSpecifications().getColor());
            productWeightTVSP.setText(productByLocation.getSpecifications().getWeight());
            productlengthTVSP.setText(productByLocation.getSpecifications().getLenght());
            productdisplayTVSP.setText(productByLocation.getSpecifications().getDisplay());
            productHeightTVSP.setText(productByLocation.getSpecifications().getHeight());
            productfilterOneTVSP.setText(productByLocation.getSpecifications().getSpecificationKey1());
            productfilterTwoTVSP.setText(productByLocation.getSpecifications().getSpecificationKey2());
            productfilterThreeTVSP.setText(productByLocation.getSpecifications().getSpecificationKey3());
            productPriceTVSP.setText(productByLocation.getPrice());


            if (productByLocation.getImg().size()>0){
                Glide.with(CompareProductsActivity.this)
                        .load(IMAGE_BASE_URL + productByLocation.getImg().get(0))
                        .placeholder(R.drawable.loading_spinner)
                        .into(productIVtwo);
            }
            else {
                Glide.with(CompareProductsActivity.this)
                        .load(R.drawable.loading_spinner)
                        .placeholder(R.drawable.loading_spinner)
                        .into(productIVtwo);
            }




            if (productByLocation.getSpecifications().getColor().equals("")){
//                productcolorTVSP.setVisibility(View.GONE);
                productcolorTVSP.setText("-");
//                productColorLL.setVisibility(View.GONE);
            }
            if (productByLocation.getDescriptionAr().equals("") ||productByLocation.getDescriptionEn().equals("")){
//                productcolorTVSP.setVisibility(View.GONE);
                descriptionTVSP.setText("-");
//                productColorLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getWeight().equals("")){
//                productWeightTVSP.setVisibility(View.GONE);
                productWeightTVSP.setText("-");
//                productWeightLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getLenght().equals("")){
//                productlengthTVSP.setVisibility(View.GONE);
                productlengthTVSP.setText("-");
//                productLengthLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getDisplay().equals("")){
//                productdisplayTVSP.setVisibility(View.GONE);
                productdisplayTVSP.setText("-");
//                productDisplayLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getHeight().equals("")){
//                productHeightTVSP.setVisibility(View.GONE);
                productHeightTVSP.setText("-");
//                productHeightLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getSpecificationKey1().equals("")){
//                productfilterOneTVSP.setVisibility(View.GONE);
                productfilterOneTVSP.setText("-");
//                filterOneLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getSpecificationKey2().equals("")){
//                productfilterTwoTVSP.setVisibility(View.GONE);
                productfilterTwoTVSP.setText("-");
//                filterTwoLL.setVisibility(View.GONE);
            }
            if (productByLocation.getSpecifications().getSpecificationKey3().equals("")){
//                productfilterThreeTVSP.setVisibility(View.GONE);
                productfilterThreeTVSP.setText("-");
//                filterThreeLL.setVisibility(View.GONE);
            }
            if (productByLocation.getPrice().equals("")){
//                productPriceTVSP.setVisibility(View.GONE);
                productPriceTVSP.setText("-");
//                priceLL.setVisibility(View.GONE);
            }

        } else {
            secondProdLL.setVisibility(View.GONE);
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}