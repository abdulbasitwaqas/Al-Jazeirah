package com.app.aljazierah.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.appsflyer.AFInAppEventParameterName;
//import com.appsflyer.AFInAppEventType;
//import com.appsflyer.AppsFlyerLib;
import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.LoginRegister;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ArabicToEnglishNumberConverter;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.app.aljazierah.utils.Constants.BLOCK_CHARACTERS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private EditText edtPhoneNumber, editUserName,editUserPassword;
    private LinearLayout layoutCorporateUser,layoutHomeUser;
    TextView tvLoginasCorporateUser,tvLoginasHomeUser;
    private Button buttonVerify,buttonLogin;

    LinearLayout layoutQitaf;
    TextView tv_stc_qitaf_msg1,tv_stc_qitaf_msg2,tvHomeUser;
    private boolean isArabic;
    private static final int REQUEST_OTP_ACTIVITY = 1001;
//    ImageView ivClose;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        super.onCreate(savedInstanceState);
     /*   if (isArabic) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }*/
        setContentView(R.layout.activity_login);


        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        tv_stc_qitaf_msg1 = findViewById(R.id.tv_stc_qitaf_msg1);
        tv_stc_qitaf_msg2 = findViewById(R.id.tv_stc_qitaf_msg2);
        tvHomeUser = findViewById(R.id.tvHomeUser);
        layoutQitaf = findViewById(R.id.layoutQitaf);
        buttonVerify = (Button) findViewById(R.id.buttonVerify);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
//        ivClose=findViewById(R.id.login_ivClose);
        LinearLayout loginview=findViewById(R.id.loginview);



        editUserName =findViewById(R.id.editUserName);
        editUserPassword=findViewById(R.id.editUserPassword);
        layoutCorporateUser=findViewById(R.id.layoutCorporateUser);
        layoutHomeUser=findViewById(R.id.layoutHomeUser);
        tvLoginasHomeUser=findViewById(R.id.tvLoginasHomeUser);
        tvLoginasCorporateUser=findViewById(R.id.tvLoginasCorporateUser);
        tvLoginasHomeUser.setOnClickListener(this);
        tvLoginasCorporateUser.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);

        /*        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgress(true);
                    Log.e("test", edtPhoneNumber.getText().toString());
                    APIManager.getInstance().loginRegister(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            Log.e("Login Activity","Response"+response);
                            showProgress(false);
                            if (z) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean isExisitngUser = false;
                                    if (jsonObject.getString("Code").equals("200")) {
                                        if (jsonObject.getString("result").trim().toLowerCase().equals("existing")) {
                                            isExisitngUser = true;
                                        } else if (jsonObject.getString("result").trim().toLowerCase().equals("new")) {
                                            isExisitngUser = false;
                                        }
                                        String signup = jsonObject.getString("signup");

                                        startActivityForResult(new Intent(LoginActivity.this,
                                                        OTPVerificationActivity.class).putExtra("is_existing_user", isExisitngUser).
                                                        putExtra("mobile", ArabicToEnglishNumberConverter.arabicToDecimal(edtPhoneNumber.getText().toString()))
                                                        .putExtra("number", edtPhoneNumber.getText().toString())
                                                        .putExtra("signup", signup)
                                                , REQUEST_OTP_ACTIVITY);


                                            Map<String, Object> eventValue = new HashMap<String, Object>();
                                            eventValue.put(AFInAppEventParameterName.USER_SCORE,"Source:"+"Android");
                                            eventValue.put(AFInAppEventParameterName.PARAM_1,"Phone Number:"+ edtPhoneNumber.getText().toString());
                                            AppsFlyerLib.getInstance().trackEvent(LoginActivity.this , AFInAppEventType.LOGIN , eventValue);


                                    }else if (jsonObject.getString("Code").equals("403")){
                                        if (isArabic)
                                        Toast.makeText(LoginActivity.this, ""+jsonObject.getString("message_ar"), Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(LoginActivity.this, ""+jsonObject.getString("message_en"), Toast.LENGTH_SHORT).show();

                                    Date startDate =  Calendar.getInstance().getTime();
                                    UserSession.getInstance().setStartDate(jsonObject.optString("time")+"@@"+startDate);

                                    }
                                    else {
                                        if (isArabic)
                                            Toast.makeText(LoginActivity.this, ""+jsonObject.optString("messaage_ar"), Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(LoginActivity.this, ""+jsonObject.optString("messaage_en"), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "" + getResources().getString(R.string.invalid_mobile), Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new LoginRegister(ArabicToEnglishNumberConverter.
                            arabicToDecimal(edtPhoneNumber.getText().toString())));
                }
            }
        });*/

        //        ivClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (!companySetting.stc_qitaf_msg_en.equals("")||!companySetting.stc_qitaf_msg_ar.equals("")) {

            if (isArabic) {
                String[] separated = companySetting.stc_qitaf_msg_ar.split("_qitaf_logo_");
                if (separated.length>0) {
                    tv_stc_qitaf_msg1.setText(separated[0]);
                    tv_stc_qitaf_msg2.setText(separated[1]);
                }
            } else {
                String[] separated = companySetting.stc_qitaf_msg_en.split("_qitaf_logo_");
                if (separated.length>0) {
                    tv_stc_qitaf_msg1.setText(separated[0]);
                    tv_stc_qitaf_msg2.setText(separated[1]);
                }
            }
            layoutQitaf.setVisibility(View.VISIBLE);
        }

        if (companySetting.enable_other_channels.equals("1")){
            tvLoginasCorporateUser.setVisibility(View.VISIBLE);
            tvHomeUser.setVisibility(View.VISIBLE);

        }else {
            tvLoginasCorporateUser.setVisibility(View.GONE);
            tvHomeUser.setVisibility(View.GONE);

        }


    }

    private void loginUser(){
        showProgress(true);
        APIManager.getInstance().loginRegister(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.e("Login Activity", "Response" + response);
                showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isExisitngUser = false;
                        if (jsonObject.getString("Code").equals("5")) {
                            if (isArabic)
                            Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                            else
                            Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                        }
                        else if (jsonObject.getString("Code").equals("7")) {


                            if (isArabic)
                                Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                        }
                        else if (jsonObject.getString("Code").equals("6")) {

                            if (isArabic)
                                Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(LoginActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                        }
                        else {

                            if (jsonObject.getString("user_type").trim().toLowerCase().equals("home")) {

                                if (jsonObject.getString("result").trim().toLowerCase().equals("existing")) {
                                    isExisitngUser = true;
                                } else if (jsonObject.getString("result").trim().toLowerCase().equals("new")) {
                                    isExisitngUser = false;
                                }
                                String signup = jsonObject.getString("signup");
                                startActivityForResult(new Intent(LoginActivity.this,
                                                OTPVerificationActivity.class).putExtra("is_existing_user", isExisitngUser).
                                                putExtra("mobile", ArabicToEnglishNumberConverter.arabicToDecimal(edtPhoneNumber.getText().toString()))
                                                .putExtra("number", edtPhoneNumber.getText().toString())
                                                .putExtra("signup", signup)
                                        , REQUEST_OTP_ACTIVITY);


//                                Map<String, Object> eventValue = new HashMap<String, Object>();
//                                eventValue.put(AFInAppEventParameterName.USER_SCORE,"Source:"+"Android");
//                                eventValue.put(AFInAppEventParameterName.PARAM_1,"Phone Number:"+ edtPhoneNumber.getText().toString());
//                                AppsFlyerLib.getInstance().trackEvent(LoginActivity.this , AFInAppEventType.LOGIN , eventValue);

                            }

                            else if (jsonObject.getString("user_type").trim().toLowerCase().equals("corporate"))
                            {

                                User.saveUserDetail(jsonObject.getJSONArray("user").getJSONObject(0));
                                Address.saveAddresses(jsonObject.getJSONArray("address"));
                                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                                List<Address> addressList = DatabaseManager.getInstance().getAllOfClass(Address.class);

                                if (user!=null){
                                    if (addressList.size()>0) {
                                        JSONObject jsonObjectaddress = new JSONObject();
                                        try {
                                            jsonObjectaddress.put("lat", addressList.get(0).addLatitude+"");
                                            jsonObjectaddress.put("lng", addressList.get(0).addLongitude+"");
                                            jsonObjectaddress.put("areaId", ""+addressList.get(0).addArea);
                                            jsonObjectaddress.put("cityId", ""+addressList.get(0).addCity);
                                            jsonObjectaddress.put("addressName", addressList.get(0).addName );
                                            jsonObjectaddress.put("details", ""+addressList.get(0).addDetail);
                                            jsonObjectaddress.put("address_id", addressList.get(0).addId);
                                            jsonObjectaddress.put("add_type", addressList.get(0).add_type);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        UserSession.getInstance().setSaveHomeAddressObject(jsonObjectaddress+"");
                                        broadcastData();

                                    }

                                }
                                finish();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new LoginRegister(ArabicToEnglishNumberConverter.
                arabicToDecimal(edtPhoneNumber.getText().toString()),
                editUserName.getText().toString(),editUserPassword.getText().toString()));
    }

    private void broadcastData() {
        Intent intent = new Intent("data_action_add_change");
        intent.putExtra("onAddressChange", "" + "changed");
        sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OTP_ACTIVITY && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private boolean validate() {

        if (edtPhoneNumber.getText().length() == 0) {
            edtPhoneNumber.setError(getResources().getString(R.string.invalid_mobile));
            return false;
        }

        if (edtPhoneNumber.getText().toString().trim().length() != 9) {
            edtPhoneNumber.setError(getResources().getString(R.string.invalid_mobile));
            return false;
        }

        if (!edtPhoneNumber.getText().toString().substring(0, 1).equals("5")) {
            edtPhoneNumber.setError(getResources().getString(R.string.invalid_mobile));
            return false;
        }


        if (!UserSession.getInstance().getStartDate().equals("")) {
            try {

            String[] separated = UserSession.getInstance().getStartDate().split("@@");
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);;
            Date startTime  = format.parse(separated[1]);
            Date currentTime =  format.parse(Calendar.getInstance().getTime()+"");
            long difference = currentTime.getTime() - startTime.getTime();
            int days = (int) (difference / (1000*60*60*24));
            int  hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            int  min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            hours = (hours < 0 ? -hours : hours);
            Log.i("======= Hours"," :: "+min);

            if (min<Integer.parseInt(separated[0]))
            {
                Log.i("======= Hours"," :: "+Integer.parseInt(separated[0]));
                Toast.makeText(this, "Your account is blocked. Try again after "+(Integer.parseInt(separated[0])- min)+" minutes", Toast.LENGTH_SHORT).show();
            return false;
            }else {
                return true;
            }

            }catch (Exception e)
            {
                Log.d("Exception",e+"");
            }

        }

        return true;
    }

    private boolean corporateValid(){

        if (editUserName.getText().toString().trim().length()==0) {
            editUserName.setError(getResources().getString(R.string._string_error_username));
            return false;
        }

        if (editUserPassword.getText().length() == 0) {
            editUserPassword.setError(getResources().getString(R.string._string_error_password));
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
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoginasCorporateUser:
                layoutHomeUser.setVisibility(View.GONE);
                layoutCorporateUser.setVisibility(View.VISIBLE);
                break;
            case R.id.tvLoginasHomeUser:
                layoutHomeUser.setVisibility(View.VISIBLE);
                layoutCorporateUser.setVisibility(View.GONE);
                break;
            case R.id.buttonLogin:
                if (corporateValid()){
                    loginUser();
                }

                break;

                case R.id.buttonVerify:
                if (validate()){
                    loginUser();
                }

                break;

        }
    }
}
