package com.app.aljazierah.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.ui.fragment.AddressFragment;
import com.roam.appdatabase.DatabaseManager;

import static com.app.aljazierah.utils.Constants.FINISH;
import static com.app.aljazierah.utils.Constants.THANKS;
import static com.app.aljazierah.utils.Constants.fromore;

public class ChooseAddressActivity extends AppCompatActivity {

    private boolean isArabic;
    private Fragment mCurrentFragment;
    public Address address;
//    private View toolbar;
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_choose_addressctivity);

        textview_location=findViewById(R.id.textview_location);
//        badge_viewFH=findViewById(R.id.badge_viewFH);
//
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);

        setToolbar();
//        initToolBar();
        if (getIntent().hasExtra("addId")) {
            Address address = DatabaseManager.getInstance().getFirstMatching("addId", getIntent().getStringExtra("addId"), Address.class);
            setAddress(address);
        } else {
            setAddressFragment();
        }
    }


    private void setToolbar() {
//        toolbar = findViewById(R.id.toolbar);
//        View badge_view = toolbar.findViewById(R.id.badge_viewFH);
//        badge_view.setVisibility(View.GONE);
        TextView title_textview = findViewById(R.id.tvToolbarTitleTCA);
        title_textview.setText(getResources().getString(R.string.choose_address));
        ImageView iv_backbtn = findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setAddressFragment() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction t = manager.beginTransaction();
            t.replace(R.id.container, mCurrentFragment = new AddressFragment()).commit();
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == THANKS) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(FINISH)) {
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentFragment instanceof AddressFragment) {
            ((AddressFragment) mCurrentFragment).updateAddressList();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                ChooseAddressActivity.this.finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        /*Context context = AlJazeriahContextWrapper.wrap(newBase, new Locale(UserSession.getInstance().getUserLanguage()));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));*/

        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromore=false;
    }


}
