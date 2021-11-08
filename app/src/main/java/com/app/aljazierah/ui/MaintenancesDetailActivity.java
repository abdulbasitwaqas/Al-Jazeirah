package com.app.aljazierah.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.aljazierah.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MaintenancesDetailActivity extends AppCompatActivity {
    private ImageView  imageViewBack;
    private TextView tvComplaintNo,tvComplaintId, tvWarrenty, tvCity,
            tvFull_Name, tvproduct_type,tvIssue,tvInvoice_number,
            tvInvoice_date,tvMobile_Number,tvemail,tvRegion,tvTitle,tvProblem_Description,tvToolbarTitleTCA, textview_location, tvServiceSlot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenances_detail);

        tvComplaintNo = findViewById(R.id.tvComplaintNo);
        tvComplaintId = findViewById(R.id.tvComplaintId);
        tvWarrenty = findViewById(R.id.tvWarrenty);
        tvCity = findViewById(R.id.tvCity);
        tvFull_Name = findViewById(R.id.tvFull_Name);
        tvproduct_type = findViewById(R.id.tvproduct_type);
        tvIssue = findViewById(R.id.tvIssue);
        tvInvoice_number = findViewById(R.id.tvInvoice_number);
        tvInvoice_date = findViewById(R.id.tvInvoice_date);
        tvMobile_Number = findViewById(R.id.tvMobile_Number);
        tvemail = findViewById(R.id.tvemail);
        tvRegion = findViewById(R.id.tvRegion);
        tvTitle = findViewById(R.id.tvTitle);
        tvProblem_Description = findViewById(R.id.tvProblem_Description);
        imageViewBack=findViewById(R.id.imageViewBackButton);
        tvToolbarTitleTCA=findViewById(R.id.tvToolbarTitleTCA);
        textview_location=findViewById(R.id.textview_location);
        tvServiceSlot=findViewById(R.id.tvServiceSlot);


        textview_location.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.VISIBLE);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        try {
            JSONObject jsonObject1 =new JSONObject( getIntent().getStringExtra("maintenanceDetails"));

            Log.d("**maintenacneDetail",""+new Gson().toJson(new JSONObject( getIntent().getStringExtra("maintenanceDetails"))));
            tvToolbarTitleTCA.setText(jsonObject1.optString("created_at"));

            tvComplaintNo.setText(getResources().getString(R.string._string_complaint_date)+": " +jsonObject1.optString("created_at"));
            tvComplaintId.setText(getResources().getString(R.string._string_ticket_id)+": "+jsonObject1.optString("ticket_id"));
            tvWarrenty.setText(getResources().getString(R.string.warranty)+": "+jsonObject1.optString("warranty"));
            tvCity.setText(getResources().getString(R.string.city)+": "+jsonObject1.optString("city"));
            tvFull_Name.setText(getResources().getString(R.string.fullname)+": "+jsonObject1.optString("full_name"));
            tvproduct_type.setText(getResources().getString(R.string._string_type)+": "+jsonObject1.optString("product_type"));
            tvIssue.setText(getResources().getString(R.string.issue)+": "+jsonObject1.optString("issue"));
            tvInvoice_number.setText(getResources().getString(R.string._string_invoice_num)+": "+jsonObject1.optString("invoice_number"));
            tvInvoice_date.setText(getResources().getString(R.string.invoice_date)+": "+jsonObject1.optString("invoice_date"));
            tvMobile_Number.setText(getResources().getString(R.string.mobile)+": "+jsonObject1.optString("mobile_number"));
            tvemail.setText(getResources().getString(R.string.email)+": "+jsonObject1.optString("email"));
            tvRegion.setText(getResources().getString(R.string.region)+": "+jsonObject1.optString("district"));
            tvTitle.setText(getResources().getString(R.string._string_title)+": "+jsonObject1.optString("title"));
            tvProblem_Description.setText(getResources().getString(R.string.problem_description)+": "+jsonObject1.optString("body"));


            if (tvInvoice_date.getText().toString().equals("")){
                tvInvoice_date.setVisibility(View.GONE);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }







    }





    @Override
    protected void onResume() {
        super.onResume();
    }

}