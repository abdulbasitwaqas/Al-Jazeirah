package com.app.aljazierah.ui;



import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.UpdateProfileWithPass;
import com.app.aljazierah.object.request.VerifyEmailOTP;
import com.app.aljazierah.utils.APIManager;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.aljazierah.utils.Constants.BLOCK_CHARACTERS;


public class EditProfileActivity extends AppCompatActivity {

    private boolean isArabic;
    private User user;
    private ProgressDialog mProgressDialog;
    private EditText editUserName, edit_mobile, edit_email,editTextNewpassword, editTextOldpassword;
    ImageView ivBack;
    TextView tv_email_isverified,tv_name, textview_location;
    LinearLayout layoutUserName,layoutPassword;
    private CheckBox acceptNewsLetterCB;
//    private RelativeLayout badge_viewFH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_edit_profile);
        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (isArabic) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        user = DatabaseManager.getInstance().getFirstOfClass(User.class);

        editUserName = findViewById(R.id.editUserName);
        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
        textview_location.setVisibility(View.INVISIBLE);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        edit_mobile =  findViewById(R.id.edit_mobile);
        edit_email =  findViewById(R.id.edit_email);
        editTextOldpassword = findViewById(R.id.editTextOldpassword);
        editTextNewpassword =  findViewById(R.id.editTextNewpassword);
        layoutUserName = findViewById(R.id.layoutUserName);
        layoutPassword = findViewById(R.id.layoutPassword);
        tv_name = findViewById(R.id.tv_name);
        tv_email_isverified = findViewById(R.id.tv_email_isverified);
        acceptNewsLetterCB = findViewById(R.id.acceptNewsLetterCB);
        if (UserSession.getInstance().getIsSubscribe().equals("1")){
            acceptNewsLetterCB.setChecked(true);
        }
        else {
            acceptNewsLetterCB.setChecked(false);
        }

        View  toolbar=findViewById(R.id.toolbar);
        TextView title_textview=toolbar.findViewById(R.id.tvToolbarTitleTCA);
        title_textview.setText(getResources().getString(R.string.personal_info));
        ImageView iv_backbtn=toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (user != null) {
            if (user.add_address.equals("0")){
                layoutPassword.setVisibility(View.VISIBLE);
                layoutUserName.setVisibility(View.GONE);
                tv_name.setText(""+user.firstName);

            }else {
                layoutPassword.setVisibility(View.GONE);
                layoutUserName.setVisibility(View.VISIBLE);
                editUserName.setText(user.firstName);

                edit_mobile.setText(user.mobile);
                edit_email.setText(user.email);
                if (user.emailActive.equals("0")){
                    tv_email_isverified.setText("("+getResources().getString(R.string._error_email_unverified)+")");
                    tv_email_isverified.setTextColor(getResources().getColor(R.color.red));
                }else {
                    tv_email_isverified.setText("");
                }
            }

        }

        findViewById(R.id.save_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() && user != null) {
                    showProgress(true);
                    UpdateProfileWithPass updateProfile;
                    if (acceptNewsLetterCB.isChecked()) {
                        updateProfile = new UpdateProfileWithPass(user.userId, editUserName.getText().toString(),
                                edit_email.getText().toString(), editTextOldpassword.getText().toString(), editTextNewpassword.getText().toString(), "1");
                    }
                    else {
                        updateProfile = new UpdateProfileWithPass(user.userId, editUserName.getText().toString(),
                                edit_email.getText().toString(), editTextOldpassword.getText().toString(), editTextNewpassword.getText().toString(), "0");

                    }

                    Log.d("updateProfile",new Gson().toJson(updateProfile));

                    APIManager.getInstance().updateProfileWithPass(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            showProgress(false);
                            if (z) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("Code").equals("402")){
                                        Toast.makeText(EditProfileActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }else if (jsonObject.getString("Code").equals("200")) {

                                        if (user.add_address.equals("0")) {
                                            User.saveUserDetail(jsonObject.getJSONObject("rows"));
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_LONG).show();
                                            EditProfileActivity.this.finish();
                                        } else {
                                            User.saveUserDetail(jsonObject.getJSONObject("rows"));
                                            user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                                            if (user.emailActive.equals("0")) {
                                                optVerificationAlertDialog();
                                            } else {
                                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_LONG).show();
                                                EditProfileActivity.this.finish();
                                            }
                                        }


                                        Map<String, Object> eventValue = new HashMap<String, Object>();
                                        eventValue.put("Name",""+""+user.firstName);
                                        eventValue.put("Email",""+user.email);
                                        if (isArabic)
                                            eventValue.put("Language","Arabic");
                                        else
                                            eventValue.put("Language","English");

//                                        CleverTapAPI.getDefaultInstance(EditProfileActivity.this).pushEvent("Update Profile:",eventValue);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, updateProfile);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(Activity.RESULT_OK);
                EditProfileActivity.this.finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validate() {

        if (user.add_address.equals("0")){
            if (editTextOldpassword.getText().toString().equals("")) {
                editTextOldpassword.setError(getResources().getString(R.string.oldpassword));
                return false;
            }
            else
            if (editTextNewpassword.getText().toString().equals("")) {
                editTextNewpassword.setError(getResources().getString(R.string.new_password));
                return false;
            }
        }else {
            if (editUserName.getText().length() == 0) {
                editUserName.setError(getResources().getString(R.string.enter_name));
                return false;
            }

            if (!isValidEmail(edit_email.getText().toString())) {
                edit_email.setError(getResources().getString(R.string.checkout_error_email_invalid));
                return false;
            }
        }
        return true;
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

    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgress(false);
    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(EditProfileActivity.this);
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


    private void optVerificationAlertDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.alert_dialog_otp_verification);
        ImageView imageClose = dialog.findViewById(R.id.imageClose);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvmessage = dialog.findViewById(R.id.tvmessage);
        tvmessage.setText(getResources().getString(R.string._verification_code_sent_to)+"\n"+user.email);

        final EditText edit_opt1 = dialog.findViewById(R.id.edit_opt1);
        final EditText edit_opt2 = dialog.findViewById(R.id.edit_opt2);
        final EditText edit_opt3 = dialog.findViewById(R.id.edit_opt3);
        final EditText edit_opt4 = dialog.findViewById(R.id.edit_opt4);
        final TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);

        edit_opt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(edit_opt1.getText().length()==1)
                    edit_opt2.requestFocus();

                tvSubmit.setVisibility(View.GONE);
                String otp = "" + edit_opt1.getText() + edit_opt2.getText() + edit_opt3.getText()
                        + edit_opt4.getText();

                if (otp.length()>3){
                    tvSubmit.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

        });

        edit_opt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(edit_opt2.getText().length()==1)
                    edit_opt3.requestFocus();

                tvSubmit.setVisibility(View.GONE);
                String otp = "" + edit_opt1.getText() + edit_opt2.getText() + edit_opt3.getText()
                        + edit_opt4.getText();

                if (otp.length()>3){
                    tvSubmit.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

        });

        edit_opt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(edit_opt3.getText().length()==1)
                    edit_opt4.requestFocus();

                tvSubmit.setVisibility(View.GONE);
                String otp = "" + edit_opt1.getText() + edit_opt2.getText() + edit_opt3.getText()
                        + edit_opt4.getText();

                if (otp.length()>3){
                    tvSubmit.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

        });

        edit_opt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(edit_opt4.getText().length()==1)
                {
                    String otp = "" + edit_opt1.getText() + edit_opt2.getText() + edit_opt3.getText()
                            + edit_opt4.getText();

                    if (otp.length()>3){
                        tvSubmit.setVisibility(View.VISIBLE);
                    }

                }else {
                    tvSubmit.setVisibility(View.GONE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = "" + edit_opt1.getText() + edit_opt2.getText() + edit_opt3.getText()
                        + edit_opt4.getText();
                VerifyEmailOTP verifyEmailOTP =new VerifyEmailOTP(user.userId,otp);

                Log.d("requestbody", new Gson().toJson(verifyEmailOTP));

                showProgress(true);
                APIManager.getInstance().verifyEmailedOTP(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        Log.e("OTP Verification","Api Response"+response);
                        showProgress(false);
                        if (z) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optString("email_verified").equals("true")){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_LONG).show();

                                    User.saveUserDetail(jsonObject.getJSONObject("rows"));

                                    if (!jsonObject.optString("message_en").equals("")&&!jsonObject.optString("message_ar").equals(""))
                                    {
                                        if (isArabic)
                                            alertDialogWelcomeMessage(jsonObject.optString("message_ar"));
                                        else
                                            alertDialogWelcomeMessage(jsonObject.optString("message_en"));
                                    } else {
                                        finish();
                                    }

                                    dialog.dismiss();
                                }else {

                                    if (isArabic)
                                        Toast.makeText(EditProfileActivity.this, ""+jsonObject.optString("message_ar"), Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(EditProfileActivity.this, ""+jsonObject.optString("message_en"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(EditProfileActivity.this, "" + getResources().getString(R.string.invalid_otp), Toast.LENGTH_LONG).show();
                        }
                    }
                },verifyEmailOTP);

                //dialog.dismiss();
            }
        });

        dialog.setCancelable(false);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();

            }
        });
        dialog.show();
    }



    private void alertDialogWelcomeMessage(String message) {
        final Dialog confirmDialog = new Dialog(EditProfileActivity.this);
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

