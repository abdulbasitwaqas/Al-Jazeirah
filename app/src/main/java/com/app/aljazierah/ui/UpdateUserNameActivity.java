package com.app.aljazierah.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.UpdateUsername;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.app.aljazierah.utils.Constants.BLOCK_CHARACTERS;

public class UpdateUserNameActivity extends Activity {


    private ProgressDialog mProgressDialog;
    private EditText username;
    private Button btnSubmit;

    private boolean isArabic;
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        this.getWindow().setBackgroundDrawableResource(R.drawable.bg);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);

        btnSubmit = (Button) findViewById(R.id.btn_submit);


        View toolbar=findViewById(R.id.toolbar);
        TextView title_textview=toolbar.findViewById(R.id.tvToolbarTitleTCA);
        title_textview.setFilters(new InputFilter[]{filter});

        ImageView iv_backbtn=toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setImageResource(R.drawable.ic_close_black_24dp);
        View badge_view=toolbar.findViewById(R.id.badge_viewFH);
        badge_view.setVisibility(View.GONE);
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgress(true);

                    APIManager.getInstance().updateUsername(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            showProgress(false);
                            if (z) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("result").equals("true")) {
                                        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                                        user.firstName = username.getText().toString();
                                        user.update();
                                        setResult(RESULT_OK);
                                        Intent data = new Intent();
                                        data.putExtra("login", 1);
                                        setResult(RESULT_OK, data);
                                        Constants.fromproduct=true;
                                        finish();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(UpdateUserNameActivity.this, "" + getResources().getString(R.string.invalid_input), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new UpdateUsername(getIntent().getStringExtra("user_id"), username.getText().toString()));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }


    private boolean validate() {

        if (username.getText().length() == 0) {
            username.setError(getResources().getString(R.string.invalid_input));
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
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
