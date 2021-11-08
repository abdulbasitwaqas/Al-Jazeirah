package com.app.aljazierah.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.PlaceOrder;
import com.app.aljazierah.object.hyperpayPayment.GetChectoutId;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.HyperpayKeyResponse;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.gson.Gson;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutStorePaymentDetailsMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

import static com.app.aljazierah.utils.Constants.AMOUNT;
import static com.app.aljazierah.utils.Constants.ORDER_REFERENCE;
import static com.app.aljazierah.utils.Constants.SADAD_SCREEN;


public class HyperpayPaymentActivity extends AppCompatActivity {
    private boolean isArabic;
    private ProgressDialog mProgressDialog;
    PlaceOrder placeOrder;
    String hyperPaymentResponse="";
    String checkoutId="";
    String price="0";
    String cityId="0";
    String payment_method="";
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyper_payment);


        placeOrder=new Gson().fromJson(getIntent().getStringExtra("placeorder"),PlaceOrder.class);
        //Log.e("get",placeOrder.getAddressData().getOrd_address_id());

        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        TextView totalAmount = findViewById(R.id.sub_total);
        price = String.format("%.2f", Double.parseDouble(getIntent().getStringExtra("price")));
        payment_method = getIntent().getStringExtra("payment_method");
        cityId = getIntent().getStringExtra("cityId");
        price = convertString(price);
        totalAmount.setText(""+price+" "+getResources().getString(R.string.currency));


        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);

        if (payment_method.toLowerCase().equals("stc pay")){
            User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
            if (user.mobile.length()==12) {
                String userNumber = "0"+user.mobile.toString().substring(3, 12);
                Log.d("usernumber", userNumber);
                stcNumberAlert(userNumber);
            }
            else {
                stcNumberAlert(user.mobile.toString());
            }
        } else {
            getCheckoutIdRequest("");
            //053,056,055
        }
    }


    private String convertString(String number){
        number=number.replace("١","1")
                .replace("١","1")
                .replace("٢","2").
                        replace("٣","3").replace("٤","4")
                .replace("٥","5")
                .replace("٦","6")
                .replace("٧","7")
                .replace("٨","8")
                .replace("٩","9")
                .replace("٠","0")
                .replace("٫",".")
                .replace(",",".");
        return number;
    }


    private void chectoutSetting(String checkOutId){
        Set<String> paymentBrands = new LinkedHashSet<String>();
        if (payment_method.toLowerCase().equals("credit card")) {
            paymentBrands.add("VISA");
            paymentBrands.add("MASTER");
        }
        else if (payment_method.toLowerCase().equals("mada")){
            paymentBrands.add("MADA");
        } else if (payment_method.toLowerCase().equals("stc pay")){
            paymentBrands.add("STC_PAY");
        }


        // paymentBrands.add("SADAD");
        //            paymentBrands.add("STC_PAY");

        CheckoutSettings checkoutSettings = new CheckoutSettings(""+checkOutId, paymentBrands, Connect.ProviderMode.TEST);
        checkoutSettings.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.PROMPT);
        //checkoutSettings.setTotalAmountRequired(true);

        // Set shopper result URL

        checkoutSettings.setShopperResultUrl("com.app.aljazierah://result");
        /*if(isArabic)
            checkoutSettings.setLocale("ar_AR");
        else
            checkoutSettings.setLocale("en_US");

        checkoutSettings.setLocale("ar_AR");*/



        Intent intent = checkoutSettings.createCheckoutActivityIntent(this);
        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
    }


    private void getCheckoutIdRequest(String mobile_number){
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setUser_id(user.userId);
        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        placeOrder.setPayment_type("" + this.placeOrder.getPayment_type());
        placeOrder.setAmount_vat("" + this.placeOrder.getAmount_vat());
        placeOrder.setPrice_without_vat(this.placeOrder.getPrice_without_vat());
        placeOrder.setReferral(this.placeOrder.getReferral());
        placeOrder.setPayment_type(this.placeOrder.getPayment_type());
        placeOrder.setPrefered_date(this.placeOrder.getPrefered_date());
        placeOrder.setPrefered_time(this.placeOrder.getPrefered_time());
        placeOrder.setInclude_wallet(this.placeOrder.getInclude_wallet());
        placeOrder.setCoupons(this.placeOrder.getCoupons());
        placeOrder.setPromocode(this.placeOrder.getPromocode());
//        placeOrder.setIs_bank(this.placeOrder.getIs_bank());

        placeOrder.setAddressData(this.placeOrder.getAddressData());
        placeOrder.setRecurring(this.placeOrder.getRecurring());
        placeOrder.setHyperResponse(null);
        placeOrder.setTransaction_id(checkoutId);
        placeOrder.setCart(this.placeOrder.getCart());
        placeOrder.setFoc_items(this.placeOrder.getFoc_items());
        placeOrder.setStc_otp(this.placeOrder.getStc_otp());
        placeOrder.setIs_bank(this.placeOrder.getIs_bank());
        placeOrder.setCheckout_time(this.placeOrder.getCheckout_time());
        Log.e("*****Request", new Gson().toJson(new GetChectoutId(""+price,""+user.userId,mobile_number,placeOrder)));

        showProgress(true);
        APIManager.getInstance().getCheckoutId(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.d("onResult: ApiResponse: ", "" + response);
                showProgress(false);
                if (z) {
                    showProgress(false);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.optString("checkout_id").equals("")){
                            String checkout_id = jsonObject.getString("checkout_id");
                            //placeOrderWithHyperPayment(checkout_id,"");
                            chectoutSetting(checkout_id);

                        }else {
                            finish();
                            Toast.makeText(HyperpayPaymentActivity.this, ""+jsonObject.optString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("Ex_cartitems", e.toString());
                        e.printStackTrace();
                    }
                }
                else {
                    showProgress(false);
                    Toast.makeText(HyperpayPaymentActivity.this, ""+getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new GetChectoutId(""+price,""+user.userId,mobile_number,placeOrder),HyperpayPaymentActivity.this);

    }

    private void gethyperPamentStatus(final String checkoutId){

        String url = ""+ Constants.HYPER_PAYMENT_STATUS +checkoutId+"/";

        showProgress(true);
        APIManager.getInstance().gethyperPamentStatus(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.d("ApiResponse: ", "" + response);
                showProgress(false);
                try {
                    if (z) {
                        JSONObject jsonObject = new JSONObject(response);
                        hyperPaymentResponse = response.toString();

                        // if (jsonObject.getJSONObject("result").getString("code").equals("000.100.110")){

                        String mpattern = "^(000\\.000\\.|000\\.100\\.1|000\\.[36])";
                        Matcher m;
                        Pattern r = Pattern.compile(mpattern);
                        m = r.matcher(jsonObject.getJSONObject("result").getString("code"));
                        if (m.find())
                        {
                            placeOrderWithHyperPayment(checkoutId, jsonObject);
                        }
                        else {
                            confirmationAlert("",""+jsonObject.getJSONObject("result").getString("description"));
                            //Toast.makeText(HyperpayPaymentActivity.this, ""+jsonObject.getJSONObject("result").getString("description"), Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {

                        JSONObject jsonObject = new JSONObject(response);
                        confirmationAlert("",""+jsonObject.getJSONObject("result").getString("description"));

                        //Toast.makeText(HyperpayPaymentActivity.this, "Something went wrong plz try again", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                }
                catch (Exception e) {
                    Log.e("Ex_cartitems", e.toString());
                    e.printStackTrace();
                }
            }
        }, url,HyperpayPaymentActivity.this);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                setResult(Activity.RESULT_OK);
                HyperpayPaymentActivity.this.finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            return true;
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            return true;
        }
    }

    private void placeOrderWithHyperPayment(final String checkoutId, final JSONObject paymentResponse) {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        PlaceOrder checkOutWithHyperPay = new PlaceOrder();
        checkOutWithHyperPay.setUser_id(user.userId);
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        checkOutWithHyperPay.setPayment_type("" + placeOrder.getPayment_type());
        checkOutWithHyperPay.setAmount_vat("" + placeOrder.getAmount_vat());
        checkOutWithHyperPay.setPrice_without_vat(placeOrder.getPrice_without_vat());
        checkOutWithHyperPay.setReferral(placeOrder.getReferral());
        checkOutWithHyperPay.setPayment_type(placeOrder.getPayment_type());
        checkOutWithHyperPay.setPrefered_date(placeOrder.getPrefered_date());
        checkOutWithHyperPay.setPrefered_time(placeOrder.getPrefered_time());
        checkOutWithHyperPay.setInclude_wallet(placeOrder.getInclude_wallet());
        checkOutWithHyperPay.setCoupons(placeOrder.getCoupons());
        checkOutWithHyperPay.setPromocode(placeOrder.getPromocode());
        checkOutWithHyperPay.setPromocode(placeOrder.getPromocode());
        checkOutWithHyperPay.setStc_otp(this.placeOrder.getStc_otp());
        checkOutWithHyperPay.setIs_bank(this.placeOrder.getIs_bank());

        checkOutWithHyperPay.setAddressData(placeOrder.getAddressData());
        checkOutWithHyperPay.setRecurring(placeOrder.getRecurring());
        checkOutWithHyperPay.setCart(placeOrder.getCart());
        checkOutWithHyperPay.setFoc_items(placeOrder.getFoc_items());
        checkOutWithHyperPay.setCheckout_time(this.placeOrder.getCheckout_time());

        checkOutWithHyperPay.setHyperResponse("");
        checkOutWithHyperPay.setRegistrationId(paymentResponse.optString("registrationId"));
        final HyperpayKeyResponse hyperpayKeyResponse = new HyperpayKeyResponse();
        hyperpayKeyResponse.setStatusCode(""+paymentResponse.optJSONObject("result").optString("code"));
        hyperpayKeyResponse.setStatusDescription(""+paymentResponse.optJSONObject("result").optString("description"));
        hyperpayKeyResponse.setAmount(""+paymentResponse.optString("amount"));

        if (!paymentResponse.optJSONObject("resultDetails").optString("transaction.receipt").equals("")) {
            hyperpayKeyResponse.setTransactionID(paymentResponse.optJSONObject("resultDetails").optString("transaction.receipt"));
            checkOutWithHyperPay.setTransaction_id(paymentResponse.optJSONObject("resultDetails").optString("transaction.receipt"));
        }else {
            checkOutWithHyperPay.setTransaction_id(paymentResponse.optString("merchantTransactionId"));
        }

        hyperpayKeyResponse.setMerchantID(paymentResponse.optString("merchantTransactionId"));

        hyperpayKeyResponse.setRefundId(paymentResponse.optString("id"));

        checkOutWithHyperPay.setResponse(hyperpayKeyResponse);

        Log.e("Request", new Gson().toJson(checkOutWithHyperPay));

        APIManager.getInstance().placeOrder(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("is_valid_order").equals("0"))
                        {
                            if (isArabic){
                                confirmationAlert("",jsonObject.getString("Message_ar"));
                            }else
                            {
                                confirmationAlert("",jsonObject.getString("Message_en"));
                            }

                        }else {
                            final double amountForTax = (Double.parseDouble("" + Cart.getTotalPrice()) * companySetting.vat) / 100;
                            Intent intent = new Intent(HyperpayPaymentActivity.this, SuccessfullOrder.class);
                            intent.putExtra("address_id", new JSONObject(response).getString("address_id"));
                            intent.putExtra("id", new JSONObject(response).getString("id"));
                            intent.putExtra(AMOUNT, Cart.getFormattedAmount(Double.parseDouble("" + Cart.getTotalPrice()) + amountForTax));
                            saveAddressId(new JSONObject(response).getString("address_id"));

                            if (!paymentResponse.optJSONObject("resultDetails").optString("transaction.receipt").equals(""))
                                intent.putExtra(ORDER_REFERENCE, paymentResponse.optJSONObject("resultDetails").optString("transaction.receipt"));
                            else
                                intent.putExtra(ORDER_REFERENCE, paymentResponse.optString("merchantTransactionId"));

                            intent.putExtra("amount_Vat", amountForTax + "");
                            intent.putExtra("amount_withoutVat", String.valueOf(Cart.getTotalPrice()));
                            intent.putExtra("cc", true);
                            startActivityForResult(intent, SADAD_SCREEN);
                            finish();
                            Map<String, Object> eventValue = new HashMap<String, Object>();
                            eventValue.put("Products",new Gson().toJson(placeOrder.getCart()));
                            eventValue.put("Total Price",""+price);
                            eventValue.put("payment method",""+payment_method);
                            eventValue.put("Quantity",Cart.getTotalItems());

                            eventValue.put("Delivery Preferred Time",""+placeOrder.getPrefered_time());
                            eventValue.put("Preferred schedule",""+placeOrder.getRecurring());
                            eventValue.put("address_lat",""+placeOrder.getAddressData().getAdd_latitude());
                            eventValue.put("address_lng",""+placeOrder.getAddressData().getAdd_longitude());

                            Areas areas = DatabaseManager.getInstance().getFirstMatching("areaId", placeOrder.getAddressData().getAdd_area(), Areas.class);
                            Cities cities = DatabaseManager.getInstance().getFirstMatching("cityId", cityId, Cities.class);

                            eventValue.put("address_city",""+cities.cityTitleEn);
                            eventValue.put("address_area",""+areas.areaTitleEn);

                            if(!placeOrder.getPromocode().equals(""))
                                eventValue.put("with a promo code","Yes"+placeOrder.getPromocode());
                            else
                                eventValue.put("with a promo code","No");

//                            CleverTapAPI.getDefaultInstance(HyperpayPaymentActivity.this).pushEvent("Checkout Complete:",eventValue);


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("response","false")  ;              }
            }
        }, checkOutWithHyperPay, HyperpayPaymentActivity.this);

    }

    private void saveAddressId(String address_id) {
        try {
            JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lat", addressObj.getString("lat") + "");
            jsonObject.put("lng", addressObj.getString("lng") + "");
            jsonObject.put("areaId", "" + addressObj.getString("areaId"));
            jsonObject.put("cityId", "" + addressObj.getString("cityId"));
            jsonObject.put("addressName", "Address: " + addressObj.getString("addressName"));
            jsonObject.put("details", "" + addressObj.getString("details"));
            jsonObject.put("address_id", address_id);
            jsonObject.put("add_type", addressObj.getString("add_type"));
            UserSession.getInstance().setSaveHomeAddressObject(jsonObject + "");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CheckoutActivity.RESULT_OK:
                /* transaction completed */

                Transaction transaction = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                Log.d("checkouttransaction","....1"+transaction.getPaymentParams().getCheckoutId());
                gethyperPamentStatus(transaction.getPaymentParams().getCheckoutId()+"");
                // resource path if needed
                String resourcePath = data.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                Log.d("checkouttransaction","....4"+resourcePath);

                if (transaction.getTransactionType() == TransactionType.SYNC) {

                    //check the result of synchronous transaction
                } else {
                    //wait for the asynchronous transaction callback in the onNewIntent()
                }

                break;
            case CheckoutActivity.RESULT_CANCELED:
                /* shopper canceled the checkout process */
                Log.d("checkouttransaction","....2");
//                Toast.makeText(this, ""+getResources().getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
                finish();

                break;
            case CheckoutActivity.RESULT_ERROR:

                /* error occurred */
                PaymentError error = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR);
                Log.d("checkouttransaction","....1"+error.getErrorMessage());
                //Toast.makeText(this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                confirmationAlert("",error.getErrorMessage());
                // finish();

        }
    }

    private void   confirmationAlert(String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                //.setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle(""+title)
                //set message
                .setMessage(""+message)
                //set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        finish();
                    }
                })
                //set negative button
                /*  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          //set what should happen when negative button is clicked
                          Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                      }
                  })*/
                .show();
        alertDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
        }

    }

    private void stcNumberAlert(String userNumber) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.alert_dialog_stc_number);
        final EditText edit_number = dialog.findViewById(R.id.edit_number);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        ImageView imageClose = dialog.findViewById(R.id.imageClose);
        edit_number.setText(userNumber);
        edit_number.requestFocus();
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_number.getText().length() == 0) {
                    edit_number.setError(getResources().getString(R.string.invalid_mobile));
                }
                else
                if (edit_number.getText().toString().trim().length() != 10) {
                    edit_number.setError(getResources().getString(R.string.invalid_mobile));
                    //053,056,055
                }
                else
                if (edit_number.getText().toString().substring(0, 2).equals("05")) {
                    dialog.dismiss();
                    getCheckoutIdRequest(edit_number.getText().toString());
                }else {
                    edit_number.setError(getResources().getString(R.string.invalid_mobile));
                }
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }


}
