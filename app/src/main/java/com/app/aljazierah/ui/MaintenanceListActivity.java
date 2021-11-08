package com.app.aljazierah.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.R;
import com.app.aljazierah.adapter.AfterSaleseReturnDataAdapter;
import com.app.aljazierah.object.MaintenencesData;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.Maintenences;
import com.app.aljazierah.utils.APIManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceListActivity extends AppCompatActivity {
    private RecyclerView showroomsRV;
    private ProgressDialog mProgressDialog;
    private AfterSaleseReturnDataAdapter showRoomsAdapter;
    List<MaintenencesData> maintenencesData = new ArrayList<>();
    private ImageView imageViewBackButton;
    private TextView textview_location,tvToolbarTitleTCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenence_list);

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
        getAftersalesData();
        tvToolbarTitleTCA.setText(getResources().getString(R.string._string_maintinences));

    }


    private void getAftersalesData() {
        showProgress(true);

        User user1= DatabaseManager.getInstance().getFirstOfClass(User.class);
        String userId = "";
        if (user1 != null)
            userId = user1.userId;

        APIManager.getInstance().getMaintenencesRequest(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    Log.e("" + z, response);
                    Log.e("" + z, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();
                        Log.d("maintenanceResp",""+response);

                        Type listType = new TypeToken<List<MaintenencesData>>() {
                        }.getType();
                        maintenencesData = gson.fromJson(jsonObject.getString("data"), listType);
                        showRoomsAdapter = new AfterSaleseReturnDataAdapter(maintenencesData, MaintenanceListActivity.this);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MaintenanceListActivity.this, 1);
                        showroomsRV.setLayoutManager(gridLayoutManager);
                        showroomsRV.setAdapter(showRoomsAdapter);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        },new Maintenences(userId));

        Log.d("**bodyMaintenance",""+new Gson().toJson(new Maintenences(userId)));
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