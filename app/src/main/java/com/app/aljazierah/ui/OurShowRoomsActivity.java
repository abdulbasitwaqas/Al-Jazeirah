package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.R;
import com.app.aljazierah.adapter.ShowRoomsAdapter;
import com.app.aljazierah.object.Store;
import com.app.aljazierah.utils.APIManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OurShowRoomsActivity extends AppCompatActivity {
    private RecyclerView showroomsRV;
    private ProgressDialog mProgressDialog;
    private ShowRoomsAdapter showRoomsAdapter;
    List<Store> storeList = new ArrayList<>();
    private ImageView imageViewBackButton;
    private TextView textview_location,tvToolbarTitleTCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_show_rooms2);

        initMembers();
    }

    private void initMembers() {
        showroomsRV=findViewById(R.id.showroomsRV);
        imageViewBackButton=findViewById(R.id.imageViewBackButton);
        textview_location=findViewById(R.id.textview_location);
        tvToolbarTitleTCA=findViewById(R.id.tvToolbarTitleTCA);

        imageViewBackButton.setVisibility(View.VISIBLE);
        textview_location.setVisibility(View.GONE);

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getStores();
        tvToolbarTitleTCA.setText(getResources().getString(R.string.our_show_rooms));

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


                        showRoomsAdapter = new ShowRoomsAdapter(storeList, OurShowRoomsActivity.this);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(OurShowRoomsActivity.this, 1);
                        showroomsRV.setLayoutManager(gridLayoutManager);
                        showroomsRV.setAdapter(showRoomsAdapter);

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
}