package com.app.aljazierah.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.CheckUserOTP;
import com.app.aljazierah.object.request.LoginRegister;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ArabicToEnglishNumberConverter;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.app.aljazierah.utils.Constants.BLOCK_CHARACTERS;
import static com.app.aljazierah.utils.Constants.special_promotion;

public class OTPVerificationActivity extends Activity implements View.OnClickListener {


    private static final int REQUEST_USERNAME_ACTIVITY = 1002;
    private ProgressDialog mProgressDialog;
    private EditText mOTP;
    private Button mVerifyButton;
    TextView tv_resendsms;
    private boolean isArabic;
    CountDownTimer countDownTimer;
//    ImageView ivClose;
    TextView tvPhone,tvTimer;
    String otp="";
    EditText etDigit1,etDigit2,etDigit3,etDigit4,etDigit5,etDigit6;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        this.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        this.getWindow().setBackgroundDrawableResource(R.drawable.bg);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        initViews();

        startTimer();
        tv_resendsms=findViewById(R.id.tv_resendsms);
        tv_resendsms.setVisibility(View.GONE);
        tv_resendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                APIManager.getInstance().loginRegister(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        Log.e("Login Activity","Response"+response);
                        showProgress(false);
                        if (z) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean isExisitngUser = false;
                                if (jsonObject.getString("result").trim().toLowerCase().equals("existing")) {
                                    isExisitngUser = true;
                                } else if (jsonObject.getString("result").trim().toLowerCase().equals("new")) {
                                    isExisitngUser = false;
                                }
                                startTimer();
                                tv_resendsms.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(OTPVerificationActivity.this, "" + getResources().getString(R.string.invalid_mobile), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new LoginRegister(ArabicToEnglishNumberConverter.
                        arabicToDecimal(getIntent().getStringExtra("number")),"",""));

            }
        });

    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(getResources().getString(R.string.please_wait_0_57)+"   " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                        tv_resendsms.setVisibility(View.VISIBLE);
                    }
                });
            }

        }.start();
    }

    private void initViews() {
        etDigit1=findViewById(R.id.otpVerification_etDigit1);
        etDigit2=findViewById(R.id.otpVerification_etDigit2);
        etDigit3=findViewById(R.id.otpVerification_etDigit3);
        etDigit4=findViewById(R.id.otpVerification_etDigit4);
        etDigit5=findViewById(R.id.otpVerification_etDigit5);
        etDigit6=findViewById(R.id.otpVerification_etDigit6);

//        ivClose=findViewById(R.id.otpVerification_ivClose);
//        ivClose.setOnClickListener(this);
        tvPhone=findViewById(R.id.otpVerification_tvPhone);

        if (isArabic) {
            tvPhone.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            tvPhone.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        tvPhone.setText("+966 "+getIntent().getStringExtra("mobile"));
        tvTimer=findViewById(R.id.otpVerification_tvTimer);
        View view_code=findViewById(R.id.view_code);
        view_code.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        if(isArabic){
//            tvTimer.setTypeface(Typeface.createFromAsset(getAssets(), "neo_sans_arabic.ttf"), Typeface.NORMAL);

        }
        else{
            //tvTimer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf"), Typeface.NORMAL);
        }



        etDigit1.addTextChangedListener(new GenericTextWatcher(etDigit1));
        etDigit2.addTextChangedListener(new GenericTextWatcher(etDigit2));
        etDigit3.addTextChangedListener(new GenericTextWatcher(etDigit3));
        etDigit4.addTextChangedListener(new GenericTextWatcher(etDigit4));
        etDigit5.addTextChangedListener(new GenericTextWatcher(etDigit5));
        etDigit6.addTextChangedListener(new GenericTextWatcher(etDigit6));




    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }


    private boolean validate() {

        if (mOTP.getText().length() == 0) {
            mOTP.setError(getResources().getString(R.string.otp_message));
            return false;
        }

        if (mOTP.getText().toString().trim().length() != 4) {
            mOTP.setError(getResources().getString(R.string.otp_message));
            return false;
        }
        return true;
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
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);

        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && BLOCK_CHARACTERS.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_USERNAME_ACTIVITY && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    private void callVerificationApi(String OTP)
    {
        this.otp=OTP;
        showProgress(true);

        APIManager.getInstance().checkUserOTP(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.e("OTP Verification","Api Response"+response);
                showProgress(false);
                if (z) {
                    try {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                        }
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("Code").equals("200")) {
                            User.saveUserDetail(jsonObject.getJSONArray("user").getJSONObject(0));
                            Address.saveAddresses(jsonObject.getJSONArray("address"));

                            UserSession.getInstance().setIsSubscribe(jsonObject.getString("is_subscribe"));

                            User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                            Map<String, Object> eventValue = new HashMap<String, Object>();
                            eventValue.put("Method Used","Phone:"+"Android");
                            eventValue.put("Status","Logged in success");
                            eventValue.put("Phone Number",user.phone);
//                            CleverTapAPI.getDefaultInstance(OTPVerificationActivity.this).pushEvent("Login:",eventValue);



                            // each of the below mentioned fields are optional
                            // if set, these populate demographic information in the Dashboard
                            HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                            profileUpdate.put("Name", user.firstName);                  // String
                            profileUpdate.put("Method Used","Phone:"+"Android");
                            profileUpdate.put("Status","Logged in success");
                            profileUpdate.put("Phone","+"+user.phone);                    // String or number
                            profileUpdate.put("Identity", ""+getIntent().getStringExtra("mobile"));                    // String or number
                            profileUpdate.put("Email", user.email);               // Email address of the user
                            // Enable WhatsApp notifications
//                            CleverTapAPI.getDefaultInstance(getApplicationContext()).onUserLogin(profileUpdate);
                            if (!getIntent().getBooleanExtra("is_existing_user", false)) {
                                startActivityForResult(new Intent(OTPVerificationActivity.this, UpdateUserNameActivity.class).putExtra("user_id", user.userId), REQUEST_USERNAME_ACTIVITY);
                            } else {
                                setResult(RESULT_OK);
                                Constants.fromproduct = true;
                                Constants.login = true;
                                if (!Constants.fromcart) {
                                    ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(3);
                                }else {

                                    if (jsonObject.optString("special_promotion").equals("1")){
                                        special_promotion = true;
                                    }
                                }

                                if (!jsonObject.getString("welcome_message_en").equals("")&&!jsonObject.getString("welcome_message_ar").equals(""))
                                {
                                    if (isArabic)
                                    alertDialogWelcomeMessage(jsonObject.getString("welcome_message_ar"));
                                    else
                                        alertDialogWelcomeMessage(jsonObject.getString("welcome_message_en"));
                                } else {
                                    finish();
                                }

                            }
                        }else if (jsonObject.getString("Code").equals("403")){
                            if (isArabic)
                            Toast.makeText(OTPVerificationActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                            else
                            Toast.makeText(OTPVerificationActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                            Map<String, Object> eventValue = new HashMap<String, Object>();
                            eventValue.put("Method Used","Phone:"+"Android");
                            eventValue.put("Failure reason",""+jsonObject.optString("message_en"));
                            eventValue.put("Phone Number",ArabicToEnglishNumberConverter.
                                    arabicToDecimal(getIntent().getStringExtra("number")));
//                            CleverTapAPI.getDefaultInstance(OTPVerificationActivity.this).pushEvent("Login:",eventValue);

                        }
                        else {
                            if (isArabic)
                            Toast.makeText(OTPVerificationActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(OTPVerificationActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(OTPVerificationActivity.this, "" + getResources().getString(R.string.invalid_otp), Toast.LENGTH_LONG).show();
                }
            }
        },new CheckUserOTP(getIntent().getStringExtra("mobile"),ArabicToEnglishNumberConverter.arabicToDecimal(OTP)));

        Log.d("**otpRequestBody", new Gson().toJson(new CheckUserOTP(getIntent().getStringExtra("mobile"),
                ArabicToEnglishNumberConverter.arabicToDecimal(OTP))));


//        new CheckUserOTP(getIntent().getStringExtra("mobile"), ArabicToEnglishNumberConverter.arabicToDecimal(mOTP.getText().toString())))

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case R.id.otpVerification_ivClose:
                finish();
                break;*/
        }

    }

    private class GenericTextWatcher implements TextWatcher{

        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.otpVerification_etDigit1:
                    if(text.length()==1)
                        etDigit2.requestFocus();
                    break;
                case R.id.otpVerification_etDigit2:
                    if(text.length()==1)
                        etDigit3.requestFocus();
                    break;
                case R.id.otpVerification_etDigit3:
                    if(text.length()==1)
                        etDigit4.requestFocus();
                    break;
                case R.id.otpVerification_etDigit4:
                    if(text.length()==1) {
//                        etDigit5.requestFocus();
                        String otp = "" + etDigit1.getText() + etDigit2.getText() + etDigit3.getText()
                                + etDigit4.getText() + etDigit5.getText() + etDigit6.getText();
                        callVerificationApi(otp);
                    }
                    break;
                case R.id.otpVerification_etDigit5:
                    if(text.length()==1)
                        etDigit6.requestFocus();
                    break;
                case R.id.otpVerification_etDigit6:
                    if(text.length()==1) {

                    }
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }


    private void alertDialogWelcomeMessage(String message) {
        final Dialog confirmDialog = new Dialog(OTPVerificationActivity.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.setContentView(R.layout.dialog_place_order_error);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        ImageView image_alert = confirmDialog.findViewById(R.id.image_alert);
        image_alert.setVisibility(View.GONE);
        tv_message.setText(""+message);
        TextView btn_ok=confirmDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                finish();
            }
        });

        confirmDialog.setCancelable(false);
        confirmDialog.show();
    }
}
