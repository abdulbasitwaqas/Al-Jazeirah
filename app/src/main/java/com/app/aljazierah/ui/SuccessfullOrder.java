package com.app.aljazierah.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.appsflyer.AppsFlyerLib;
import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.utils.Constants;

import static com.app.aljazierah.ui.CheckOutActivity.orderdetailactivity;
import static com.app.aljazierah.utils.Constants.ORDER_REFERENCE;

public class SuccessfullOrder extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_ordercreated, textview_location;
    private TextView tv_transactioncreated;
    private Button browsProductsBtn;
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_successfull_order);
        setViews();
    }

    private void setViews() {
        Constants.validate_promocode = false;
        Button gotomyorder = findViewById(R.id.successfulOrder_btnMyOrders);
        gotomyorder.setOnClickListener(this);
        tv_ordercreated = findViewById(R.id.tv_ordercreated);
        browsProductsBtn = findViewById(R.id.browsProductsBtn);
        tv_transactioncreated = findViewById(R.id.tv_transactioncreated);
        textview_location = findViewById(R.id.textview_location);
        imageViewBack = findViewById(R.id.imageViewBackButton);
        textview_location.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.VISIBLE);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setDataFromIntent();

        Cart.emptyCart();
        orderdetailactivity.finish();


        browsProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().getViewPager_home().setCurrentItem(0);
                finish();
            }
        });

//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.PAYMENT_INFO_AVAILIBLE,"TransictionId:"+getIntent().getStringExtra("id"));
//        eventValue.put(AFInAppEventParameterName.CURRENCY,"SAR");
//        eventValue.put(AFInAppEventParameterName.REVENUE,""+Double.parseDouble(getIntent().getStringExtra("amount_withoutVat")));
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext() , AFInAppEventType.PURCHASE , eventValue);

    }

    private void setDataFromIntent() {
        tv_ordercreated.setText(getResources().getString(R.string.st_order_created) + "  " + getIntent().getStringExtra("id"));
//        tv_transactioncreated.setVisibility(View.GONE);

        if (getIntent().getBooleanExtra("cc", true)) {
            tv_transactioncreated.setText(getResources().getString(R.string.st_transaction_created) + "  " + getIntent().getStringExtra(ORDER_REFERENCE));
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.successfulOrder_btnMyOrders:
                if (AppController.getInstance().getViewPager_home() != null) {
                    AppController.getInstance().getViewPager_home().setCurrentItem(3);
                    AppController.getInstance().getActivity().finish();
                }
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
