package com.app.aljazierah.ui;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.utils.ProductsSingleton;

public class CanceledOrder extends AppCompatActivity {
    private TextView textview_location, canceledOrder_tvOrderPlaced;
//    private RelativeLayout badge_viewFH;
    private Button browseProductsBtn, myOrdersBtn;
    private String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_canceled_order);

//        badge_viewFH=findViewById(R.id.badge_viewFH);
        textview_location=findViewById(R.id.textview_location);
        canceledOrder_tvOrderPlaced=findViewById(R.id.canceledOrder_tvOrderPlaced);
        browseProductsBtn=findViewById(R.id.browseProductsBtn);
        myOrdersBtn=findViewById(R.id.myOrdersBtn);
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

        textview_location.setVisibility(View.INVISIBLE);
//        badge_viewFH.setVisibility(View.INVISIBLE);

        canceledOrder_tvOrderPlaced.setText(getResources().getText(R.string.your_order)+"  #"+orderID +" "+ getResources().getText(R.string.has_been_cancelled));

        Button canceledOrder_btnMyOrders=findViewById(R.id.canceledOrder_btnMyOrders);
        canceledOrder_btnMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                finish();
                overridePendingTransition(R.anim.enter_anim,0);
            }
        });

        browseProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                finish();
                overridePendingTransition(R.anim.enter_anim,0);

            }
        });

        myOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(2);
                finish();
                overridePendingTransition(R.anim.enter_anim,0);
            }
        });




    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
