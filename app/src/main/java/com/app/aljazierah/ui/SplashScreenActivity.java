package com.app.aljazierah.ui;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.adapter.YourOrderDetailRecyclerRatingFeedback;
import com.app.aljazierah.object.PagesModel;
import com.app.aljazierah.object.RetingRequest.FeedbackProducts;
import com.app.aljazierah.object.RetingRequest.Items;
import com.app.aljazierah.utils.ProductsSingleton;
import com.appsflyer.AFInAppEventParameterName;
import com.app.aljazierah.AppController;
import com.app.aljazierah.BuildConfig;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.GeoCodeApi.ModelGeoCodeApi;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.CompanySettings;
import com.app.aljazierah.object.RetingRequest.RetingRequest;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.HmsGmsUtil;
import com.app.aljazierah.utils.RequestPermissionHandler;
import com.app.aljazierah.utils.gpsLocation.GPSTracker;
import com.app.aljazierah.utils.gpsLocation.GetLocationByGoogleClientApi;
import com.bumptech.glide.Glide;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import static com.app.aljazierah.utils.Constants.PROMO_BASE_URL;


public class SplashScreenActivity extends AppCompatActivity {
    ImageView appIconIVSA;
    private TextView chooseDefaultLanguageHeaderTV;
    private RadioGroup languageRG;
    //    View progress_bar;
    private Button btnGetStart;
    private boolean isArabic;
    private View clSA;
    private RequestPermissionHandler mRequestPermissionHandler;
    private boolean permissonCheck = false;
    GetLocationByGoogleClientApi getLocationByGoogleClientApi;
    private int cityId = 0;
    private int areaId = 0;
    private String mapCity = "";
    private String mapArea = "";
    Gson gson;
    boolean goHome = true;
    boolean isQuestioner = false;
    ProgressDialog mProgressDialog;
    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
    boolean isRating = false;
    boolean isPopup = false;
    String reference_id = "";
    public String key = "";

    private SpinKitView spinKitSplash;

    private RadioButton englishRB, arabicRB;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        getLocationByGoogleClientApi = new GetLocationByGoogleClientApi(SplashScreenActivity.this);
        createGson();

        HmsGmsUtil.isOnlyHms(this);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Objects.<ActionBar>requireNonNull(getSupportActionBar()).hide();
        isArabic = AppController.setLocale();
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        mRequestPermissionHandler = new RequestPermissionHandler();
        setContentView(R.layout.splash_screen);

        isInternetAccessible(SplashScreenActivity.this);
        appIconIVSA = findViewById(R.id.splashIcon);
        clSA = findViewById(R.id.clSA);
        languageRG = findViewById(R.id.languageRG);
        spinKitSplash = findViewById(R.id.spinKitSplash);
        spinKitSplash.setVisibility(View.GONE);
        englishRB = findViewById(R.id.englishRB);
        arabicRB = findViewById(R.id.arabicRG);
        chooseDefaultLanguageHeaderTV = findViewById(R.id.chooseDefaultLanguageHeaderTV);

        btnGetStart = findViewById(R.id.btnGetStart);
        btnGetStart.animate().alpha(0.0f);
        appIconIVSA.animate().alpha(0.0f);
        btnGetStart.animate().setDuration(0);
        appIconIVSA.animate().setDuration(0);
        languageRG.animate().alpha(0.0f);
        languageRG.animate().setDuration(0);
        chooseDefaultLanguageHeaderTV.animate().alpha(0.0f);
        chooseDefaultLanguageHeaderTV.animate().setDuration(0);


        if (isArabic) {
            arabicRB.setChecked(true);
        } else {
            englishRB.setChecked(true);
        }


        if (HmsGmsUtil.isOnlyHms(SplashScreenActivity.this)) {
            getHMSToken();
        }
        else {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashScreenActivity.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Log.e("newToken", newToken);
                    UserSession.getInstance().setToken(newToken);
                }
            });
        }





        if (!UserSession.getInstance().getToken().equals("")) {
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(AFInAppEventParameterName.PARAM_1, "" + "FCM Android" + "" + UserSession.getInstance().getToken());
//            CleverTapAPI.getDefaultInstance(this).pushEvent("FCM Android Token:", eventValue);
//            CleverTapAPI.getDefaultInstance(this).pushFcmRegistrationId(UserSession.getInstance().getToken(), true);
        }


        languageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.englishRB:
                        UserSession.getInstance().setUserLanguage(Constants.ENGLISH);
                        break;
                    case R.id.arabicRG:
                        UserSession.getInstance().setUserLanguage(Constants.ARABIC);
                        break;
                }
            }
        });


        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);

                if (key.equals("key")) {
                    this.key = value.toString();
                    Log.d("Keysssss", this.key + "........111111");
                }
                if (key.equals("reference_id")) {
                    reference_id = value.toString();
                }
                Log.d("TAG", "Key: " + key + " Value: " + value);
            }
        }
        if (!reference_id.equals("") || !key.equals("")) {
            if (key.equals("order_detail") && user != null) {
                Intent intent = new Intent(SplashScreenActivity.this, OrderDetails.class);
                intent.putExtra("order_id", reference_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            } else {
                setCompanySettings();
            }
        }
        else {
            setCompanySettings();
        }
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUserCurrentLatLong() && UserSession.getInstance().getSaveAddressObject().equals("")) {
                    getAddressRequest();
                } else {
                    goToHomeScreenActivity();
                }
            }
        });
        handlePermissions();

        Log.d("**fcmToken","fcm token:   "+UserSession.getInstance().getToken());

    }

    private void getHMSToken() {
        // get token
        new Thread() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(getApplicationContext()).getString("client/app_id");
                    String pushtoken = HmsInstanceId.getInstance(getApplicationContext()).getToken(appId, "HCM");
                    if (!TextUtils.isEmpty(pushtoken)) {
                        //showToken(pushtoken);
                        Log.i("TAG", "getToken: " + pushtoken);

                        UserSession.getInstance().setToken(pushtoken);
                    }
                } catch (Exception e) {
                    Log.i("TAG", "getToken failed, " + e);
                    Log.i("TAG", "getToken failed, " + e);

                }
            }
        }.start();
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void setCompanySettings() {
//        progress_bar.setVisibility(View.VISIBLE);

//        spinKitSplash.setVisibility(View.VISIBLE);
        showProgress(true);
        if (isWifiAvailable()) {
            CompanySettings companySettings = new CompanySettings();
            if (user != null) {
                companySettings.setUser_id(user.userId);
                Log.d("useriddddd", user.authToken);
            } else
                companySettings.setUser_id("");

            companySettings.setLocation_date(UserSession.getInstance().getLastAreaUpdatedDate());

            Log.d("lastareadate", UserSession.getInstance().getLastAreaUpdatedDate());


     /*       Log.d("**companySettings", new Gson().toJson(new CompanySettings(""+UserSession.getInstance().getLastAreaUpdatedDate(),
                    ""+user.userId)));

*/
            APIManager.getInstance().getCompanySettings(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {
//                progress_bar.setVisibility(View.GONE);
//                    spinKitSplash.setVisibility(View.GONE);

                    if (z) {
                        if (response.contains("Access denied") || response.contains("Invalid Key!") || response.contains("Invalid Key")) {
                            CoreManager.getInstance().removeUserData();
                            Toast.makeText(SplashScreenActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashScreenActivity.this, SplashScreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                        } else {
                            try {
                                boolean gotoHome = true;

                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObjectRows = jsonObject.getJSONObject("rows");
                                JSONArray arrayCities = jsonObjectRows.getJSONArray("cities");
                                JSONArray arrayAreas = jsonObjectRows.getJSONArray("areas");


                                CompanySetting.fromJson(jsonObjectRows);
                                CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
                                int versionfromserver = Integer.parseInt(jsonObjectRows.getString("cur_android_ver").replace(".", ""));
                                int versionapp = Integer.parseInt(BuildConfig.VERSION_NAME.replace(".", ""));

                                if (versionfromserver > versionapp) {
                                    openVersionDialog();

                                } else {
                                    if (jsonObjectRows.getString("delete_addresses").equals("1") && user != null) {
                                        UserSession.getInstance().setSaveHomeAddressObject("");
                                        CoreManager.getInstance().removeUserData();
                                        Toast.makeText(SplashScreenActivity.this, "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                        if (getUserCurrentLatLong() && UserSession.getInstance().getSaveAddressObject().equals("")) {
                                            getAddressRequest();
                                        }
                                    }

                                    if (arrayAreas.length() > 0 && arrayCities.length() > 0) {
                                        CoreManager.getInstance().removeCityAreaData();
                                        Areas.fromJson(arrayAreas);
                                        Cities.fromJson(arrayCities);
                                        splashThread();
                                        gotoHome = false;

                                        UserSession.getInstance().setLastAreaUpdatedDate(jsonObjectRows.getString("last_area_updated_date"));
                                    }

                                    if (!jsonObjectRows.getString("feedback_order").equals("") && user != null) {
                                        Type listType = new TypeToken<List<FeedbackProducts>>() {
                                        }.getType();
                                        List<FeedbackProducts> feedbackProductsList = gson.fromJson(jsonObjectRows.getString("feedback_products"), listType);
                                        rattingAlertDialog(jsonObjectRows.getString("feedback_order"), jsonObjectRows.optString("feedback_order_number"),feedbackProductsList);
                                        gotoHome = false;
                                    }

                                /*  Type listType = new TypeToken<List<FeedbackProducts>>() {
                                    }.getType();
                                    String feedback = "[\n" +
                                            "            {\n" +
                                            "                \"product_id\": 1,\n" +
                                            "                \"name_en\": \"Berain 200 ml (1 Carton-48 Plastic Bottles)\",\n" +
                                            "                \"name_ar\": \"بيرين 200 مل (1 كرتون-48 عبوة بلاستيك)\"\n" +
                                            "            },\n" +
                                            "            {\n" +
                                            "                \"product_id\": 2,\n" +
                                            "                \"name_en\": \"Berain 330 ml (1 Carton-40 Plastic Bottles)\",\n" +
                                            "                \"name_ar\": \"بيرين 330 مل (1 كرتون-40 عبوة بلاستيك)\"\n" +
                                            "            }\n" +
                                            "        ]\n" +
                                            "";
                                    List<FeedbackProducts> feedbackProductsList = gson.fromJson(feedback, listType);
                                    rattingAlertDialog("114", "17052",feedbackProductsList);
                                    gotoHome = false;*/


                                //commenting just for testing 7/7/2021 11:34AM

                                   /* if (!jsonObjectRows.optJSONObject("questionnaire").equals("")&&!jsonObjectRows.optJSONObject("questionnaire").optString("url").equals("")) {
                                        if (isArabic)
                                            stockNotificationAlert(jsonObjectRows.optJSONObject("questionnaire").optString("url"), jsonObjectRows.optJSONObject("questionnaire").optString("title_ar"));
                                        else
                                            stockNotificationAlert(jsonObjectRows.optJSONObject("questionnaire").optString("url"), jsonObjectRows.optJSONObject("questionnaire").optString("title_en"));
                                        gotoHome = false;
                                    }*/

                                    Type pagesType = new TypeToken<List<PagesModel>>() {}.getType();
                                    List<PagesModel> pagesModelList = gson.fromJson(jsonObjectRows.getString("pages"), pagesType);
                                    ProductsSingleton.getInstance().setPages(pagesModelList);


                                    if (!jsonObjectRows.getString("full_page_promo_en").equals("") ||
                                            !jsonObjectRows.getString("full_page_promo_ar").equals("")) {

                                        if (isArabic)
                                            showPromoPopupAlert(jsonObjectRows.getString("full_page_promo_ar"));
                                        else
                                            showPromoPopupAlert(jsonObjectRows.getString("full_page_promo_en"));
                                        gotoHome = false;
                                    }

                                    if (!jsonObjectRows.getString("base_api_url").equals("")) {
                                        Constants.BASE_URL = jsonObjectRows.getString("base_api_url");
                                    }

                                    if (gotoHome){
                                        goToHomeScreenActivity();
                                    }
                                    showProgress(false);
                                }
                            } catch (Exception e) {
                                Log.e("Exceptin", e.toString());
                            }
                        }
                    } else {
                        Toast.makeText(SplashScreenActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }, companySettings, SplashScreenActivity.this);
        } else {
            Toast.makeText(SplashScreenActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void rattingAlertDialog(final String orderNumber, String feedback_order_number,List<FeedbackProducts> feedbackProductsList) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.ratting_alert_dialog);
        ImageView imageClose = dialog.findViewById(R.id.imageClose);
        TextView tvOrderTitle = dialog.findViewById(R.id.tvOrderTitle);
        final TextView tvSubmitRating = dialog.findViewById(R.id.tvSubmitRating);
        //final RatingBar ratingYourSatisfaction = dialog.findViewById(R.id.ratingYourSatisfaction);
        //final RatingBar ratingOverallEcperience = dialog.findViewById(R.id.ratingOverallEcperience);
        isRating = true;
        tvOrderTitle.setText(getResources().getString(R.string.st_orderarrived_feedback) + ": " +
                feedback_order_number + "\n" + getResources().getString(R.string.st_rateorder_feedback));
        RecyclerView OrderItemsRatingRecyclerView = dialog.findViewById(R.id.OrderItemsRatingRecyclerView);
        OrderItemsRatingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        YourOrderDetailRecyclerRatingFeedback orderAdapterRatting = new YourOrderDetailRecyclerRatingFeedback(this, feedbackProductsList);
        OrderItemsRatingRecyclerView.setAdapter(orderAdapterRatting);

        tvSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRating(orderAdapterRatting.getOrderItemsList())) {
                    List<Items> items=new ArrayList<>();
                    for (int i = 0; i<orderAdapterRatting.getOrderItemsList().size();i++){
                        Items items1 = new Items(orderAdapterRatting.getOrderItemsList().get(i).getProductId(),
                                orderAdapterRatting.getOrderItemsList().get(i).getRating(),
                                orderAdapterRatting.getOrderItemsList().get(i).getComments());
                        items.add(items1);

                    }
                    Log.d("RatingRequest",new Gson().toJson(new RetingRequest(user.userId, orderNumber,items, "0")));
                    APIManager.getInstance().saveOrderRating(new APIManager.Callback() {
                        @Override
                        public void onResult(boolean z, String response) {
                            try {
                                Toast.makeText(SplashScreenActivity.this, "" + getResources().getString(R.string.st_rating_submitted), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                goToHomeScreenActivity();
                            } catch (Exception ex) {

                            }
                        }
                    }, new RetingRequest(user.userId, feedback_order_number,items, "0"));

                }else {
                    Toast.makeText(SplashScreenActivity.this, ""+getResources().getString(R.string.st_rateorder), Toast.LENGTH_SHORT).show();
                }
            }
        });


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                APIManager.getInstance().saveOrderRating(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        showProgress(false);
                        try {
                            // Toast.makeText(HomeScreenActivity.this, new JSONObject(response).getString("status"), Toast.LENGTH_SHORT).show();
                            goToHomeScreenActivity();
//                            splashThread();
                        } catch (Exception ex) {

                        }
                    }
                }, new RetingRequest(user.userId, orderNumber, null, "1"));

                dialog.dismiss();
                isRating = false;
            }
        });
        dialog.show();
    }

    private boolean isRating(List<FeedbackProducts> orderDetails){
        boolean israting = true;
        for (int i = 0; i< orderDetails.size();i++){
            if (orderDetails.get(i).getRating().equals("")||orderDetails.get(i).getRating().equals("0")||
                    orderDetails.get(i).getRating().equals("0.00")){
                israting = false;
            }
        }

        return israting;
    }

    private void showPromoPopupAlert(String promoUrl) {
        final Dialog dialog = new Dialog(SplashScreenActivity.this, R.style.Theme_AppCompat_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        isPopup = true;
        dialog.setContentView(R.layout.promo_popup_layout);
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        ImageView popupImg = dialog.findViewById(R.id.popupImg);
  /*      TextView txtPopupTitle = dialog.findViewById(R.id.txtPopupTitle);
        if (promo.title.trim().length() > 0) {
            txtPopupTitle.setText(promo.title);
            txtPopupTitle.setVisibility(View.VISIBLE);
        } else {
            txtPopupTitle.setVisibility(View.GONE);
        }*/

        CircularProgressDrawable cd = new CircularProgressDrawable(this);
        cd.setStrokeWidth(10f);
        cd.setCenterRadius(100f);
        cd.start();
        Glide.with(this)
                .load(PROMO_BASE_URL + promoUrl)
//                    .placeholder(R.drawable.loading_spinner)
                .placeholder(cd)
//                    .override(100, 100)
                .into(popupImg);
        popupImg.setVisibility(View.VISIBLE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //   drawer.removeView(popupPromoContainer);
                if (!isRating)
                    goToHomeScreenActivity();
//                    splashThread();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private void stockNotificationAlert(String url,String messages ) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.change_alert_dialog);
//        dialog.setCancelable(true);
        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        Button okBtn = dialog.findViewById(R.id.okBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        title.setVisibility(View.GONE);
        message.setText(""+messages);
        dialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQuestioner = true;
                OpenUrl(url);
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!isRating)
                    goToHomeScreenActivity();
            }
        });
    }

    private void OpenUrl(String ulr){
        Uri uri = Uri.parse(""+ulr); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(SplashScreenActivity.this);
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

    private void openVersionDialog() {
        Dialog dialogVersion;
        dialogVersion = new Dialog(this);
        dialogVersion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogVersion.setContentView(R.layout.version_dialog);
        TextView title = (TextView) dialogVersion.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.update_version));
        TextView message = (TextView) dialogVersion.findViewById(R.id.message);
        message.setText(getResources().getString(R.string.update_version_msg));

        Button ok = (Button) dialogVersion.findViewById(R.id.okBtn);
        Button cancel = (Button) dialogVersion.findViewById(R.id.cancelBtn);
        dialogVersion.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });

        dialogVersion.show();
    }

    public void goToHomeScreenActivity() {
        appIconIVSA.animate().alpha(1.0f);
        appIconIVSA.animate().setDuration(2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (permissonCheck) {
                        if (goHome) {
                            goHome = false;
                            Log.d("**gotoHome", "SPlashINTENT");
                            startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                            overridePendingTransition(R.anim.enter_anim, 0);
                            finish();
                        }
                    } else {
                        handlePermissions();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);


    }

    private void splashThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("**thread", "SplashThreadRunning");
                    handlePermissions();
                    btnGetStart.animate().alpha(1.0f);
                    appIconIVSA.animate().alpha(1.0f);
                    appIconIVSA.animate().setDuration(900);
                    btnGetStart.animate().setDuration(900);

                    languageRG.animate().alpha(1.0f);
                    languageRG.animate().setDuration(900);
                    chooseDefaultLanguageHeaderTV.animate().alpha(1.0f);
                    chooseDefaultLanguageHeaderTV.animate().setDuration(900);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    public void handlePermissions() {
        mRequestPermissionHandler.requestPermission(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                permissonCheck = true;
            }

            @Override
            public void onFailed() {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    private void getAddressRequest() {
        String language = "en";
        if (AppController.setLocale()) {
            language = "ar";
        }

        showProgress(true);

        APIManager.getInstance().getAddressByLatLng(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        boolean flag = false;
                        boolean areaflag = false;
                        ModelGeoCodeApi geoCodeApi = gson.fromJson(jsonObject.toString(), ModelGeoCodeApi.class);
                        if (geoCodeApi.getStatus().equals("ZERO_RESULTS")) {
                            Toast.makeText(SplashScreenActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                        } else if (geoCodeApi.getStatus().equals("OVER_QUERY_LIMIT")) {
                            Toast.makeText(SplashScreenActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < geoCodeApi.getResults().get(0).getAddressComponents().size(); i++) {
                                for (int k = 0; k < geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().size(); k++) {
                                    if (geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().get(k).equals("locality")) {
                                        mapCity = geoCodeApi.getResults().get(0).getAddressComponents().get(i).getLongName();
                                        Log.d("localitymapcity", "..." + mapCity);
                                    }

                                    if (geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().get(k).equals("sublocality")) {
                                        mapArea = geoCodeApi.getResults().get(0).getAddressComponents().get(i).getLongName();
                                        Log.d("localitymaparea", "..." + mapArea);
                                    }
                                }

                            }

                            List<Cities> cities = DatabaseManager.getInstance().getAllOfClass(Cities.class);

                            for (int j = 0; j < cities.size(); j++) {
                                if (cities.get(j).cityTitleEn.toLowerCase().equals(mapCity.toLowerCase()) || cities.get(j).cityTitleAr.toLowerCase().equals(mapCity.toLowerCase())) {
                                    flag = true;
                                    cityId = cities.get(j).cityId;
                                    Log.d("localitymapcity", "..." + cityId);
                                }
                            }

                            if (flag) {
                                if (cityId != 0) {
                                    //List<Areas> areas = DatabaseManager.getInstance().getAllOfClass(Areas.class);
                                    List<Areas> areas = getCityArea(cityId);

                                    for (int j = 0; j < areas.size(); j++) {
                                        if (areas.get(j).areaTitleEn.toLowerCase().equals(mapArea.toLowerCase()) || areas.get(j).areaTitleAr.toLowerCase().equals(mapArea.toLowerCase())) {
                                            areaflag = true;
                                            areaId = areas.get(j).areaId;
                                            Log.d("localitymapcity", "..." + areaId);
                                        }

                                    }
                                }

                                if (areaflag) {
                                    UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject(latitude + "", longitude + "", geoCodeApi.getResults().get(0).getFormattedAddress()) + "");
                                }
                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                goToHomeScreenActivity();
//                splashThread();
            }
        }, latitude + "," + longitude, language, SplashScreenActivity.this);
    }

    private List getCityArea(int cityId) {
        List<Areas> cityAreaList = new ArrayList<>();
        List<Areas> areasAll = DatabaseManager.getInstance().getAllOfClass(Areas.class);
        for (Areas area : areasAll) {
            if (cityId == area.areaCityId) {
                cityAreaList.add(area);
            }
        }

        return cityAreaList;
    }

    private JSONObject creatJsonObject(String lat, String lng, String addDetail) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", lat + "");
            jsonObject.put("lng", lng + "");
            jsonObject.put("areaId", "" + areaId);
            jsonObject.put("cityId", "" + cityId);
            jsonObject.put("addressName", "Address: " + addDetail);
            jsonObject.put("details", "" + addDetail);
            jsonObject.put("address_id", "");
            jsonObject.put("add_type", "1");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onResume() {
        registerReceiver(mReceiverLocation, new IntentFilter("data_action"));
        if (isQuestioner)
            goToHomeScreenActivity();
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiverLocation);
        super.onPause();
    }

    private BroadcastReceiver mReceiverLocation = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String string = (String) intent.getSerializableExtra("onConnectedGoogleApi");
                Log.e("onConnectedGoogleApi", string);
                if (string.equals("connected"))
                    getUserCurrentLatLong();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private boolean getUserCurrentLatLong() {
        boolean isLocation = false;
        if (HmsGmsUtil.isOnlyHms(this)) {
            GPSTracker tracker = new GPSTracker(this);
            Location location = tracker.getLocation();

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                isLocation = true;
            }
        } else {
            Location location = getLocationByGoogleClientApi.getMyLocation();
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                //LatLng currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
//                CleverTapAPI.getDefaultInstance(this).setLocation(location); //android.location.Location
                isLocation = true;
            }
        }

        return isLocation;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }


    private boolean isWifiAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isInternetAccessible(Context context) {
        if (isWifiAvailable()) {
            return true;
        } else {
            Toast.makeText(context, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}

