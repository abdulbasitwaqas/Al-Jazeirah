package com.app.aljazierah.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.User;
import com.roam.appdatabase.DatabaseManager;

public class TermsAndConditionsActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private boolean isArabic;
    private User user;
    TextView tvPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isArabic = AppController.setLocale();
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_terms_and_coditions);


        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy);
            TextView title_textview=findViewById(R.id.tvToolbarTitleTCA);
            title_textview.setText(getResources().getString(R.string._string_privacy_policy));
            ImageView iv_backbtn=findViewById(R.id.imageViewBackButton);
        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setPrivacyPolicy();
    }
    private void setPrivacyPolicy(){
        String s = "";
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

            if (isArabic)
            s = companySetting.tc_ar;
            else
                s = companySetting.tc_en;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            tvPrivacyPolicy.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvPrivacyPolicy.setText(Html.fromHtml(s));
        }


            /* APIManager.getInstance().getPrivacyPolicy(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
               showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString("Code").equals("200"))
                        {
                            String privacyPolicy = jsonObject.getString("privacy_policy");
                            tvPrivacyPolicy.setText(""+privacyPolicy);

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                tvPrivacyPolicy.setText(Html.fromHtml(privacyPolicy, Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                tvPrivacyPolicy.setText(Html.fromHtml(privacyPolicy));
                            }
                            
                        }else {
                            Toast.makeText(PrivacyPolicyActivity.this, ""+jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.e("Exceptin",e.toString());
                    }
                }
                else{

                    Toast.makeText(PrivacyPolicyActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        },privacyPolicy,PrivacyPolicyActivity.this);*/
    }

    private void showProgress(boolean show) {
        if (show) {
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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                setResult(Activity.RESULT_OK);
                TermsAndConditionsActivity.this.finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
