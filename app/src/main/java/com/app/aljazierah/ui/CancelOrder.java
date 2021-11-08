package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.CancelReasonsAdapter;
import com.app.aljazierah.object.CancelReasons;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.CancelOrderRequest;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.recycler.DividerItemDecoration;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.aljazierah.utils.Constants.isOrderCancel;

public class CancelOrder extends AppCompatActivity {
    private TextView tvToolbarTitleTCA, textview_location, textwhy;
    private ImageView imageViewBack;
//    private RelativeLayout badge_viewFH;
    private RecyclerView options_list;
    private RadioGroup radioRefundGroup;
    private Button cancelOrder_btnSubmit,cancelOrderBtnCA;
    private RadioButton radio_refund_to_wallet,radio_refund_to_bank;
    private ProgressDialog mProgressDialog;
    private  String id, orderID, orderCancelAble, orderStatusEng;
    private boolean isArabic;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_cancel_order);


        tvToolbarTitleTCA=findViewById(R.id.tvToolbarTitleTCA);
        imageViewBack=findViewById(R.id.imageViewBackButton);
        textview_location=findViewById(R.id.textview_location);
//        badge_viewFH=findViewById(R.id.badge_viewFH);
        options_list=findViewById(R.id.options_list);
        radioRefundGroup=findViewById(R.id.radioRefundGroup);
        cancelOrderBtnCA=findViewById(R.id.cancelOrderBtnCA);
        textwhy=findViewById(R.id.textwhy);
        cancelOrder_btnSubmit=findViewById(R.id.cancelOrder_btnSubmit);
        radio_refund_to_wallet=findViewById(R.id.radio_refund_to_wallet);
        radio_refund_to_bank=findViewById(R.id.radio_refund_to_bank);
        Intent intent = getIntent();

//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);
        imageViewBack.setVisibility(View.VISIBLE);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelOrderBtnCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvToolbarTitleTCA.setText(getResources().getString(R.string.cancel_order));
        tvToolbarTitleTCA.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        id = intent.getStringExtra("id");
        orderID = intent.getStringExtra("orderID");
        orderCancelAble = intent.getStringExtra("orderCancelAble");
        orderStatusEng = intent.getStringExtra("orderStatusEng");

        textwhy.setText(getResources().getText(R.string.cancel_reason)+"  #"+ orderID);

        getCancelOrderReasons(id);

    }

    private void getCancelOrderReasons(final String orders) {
        showProgress(true);
        APIManager.getInstance().getCanceOrderReasons(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);

                if (z) {
                    Log.e(""+z,response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<CancelReasons>>() {

                        }.getType();
                        ArrayList<CancelReasons> cancelReasons = gson.fromJson(jsonObject.getJSONArray("result").toString(), listType);
                        openCancelOrderReasonsDialog(cancelReasons,orders);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openCancelOrderReasonsDialog(ArrayList<CancelReasons> cancelReasons, final String orders) {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(CancelOrder.this);
        options_list.setLayoutManager(manager);
        options_list.setHasFixedSize(true);

        final CancelReasonsAdapter adapter = new CancelReasonsAdapter(cancelReasons);
        options_list.setAdapter(adapter);
        options_list.addItemDecoration(new DividerItemDecoration(CancelOrder.this, DividerItemDecoration.VERTICAL_LIST));


        if (orderCancelAble.equals("3")) {
            radioRefundGroup.setVisibility(View.VISIBLE);
            radio_refund_to_wallet.setVisibility(View.VISIBLE);
            radio_refund_to_bank.setVisibility(View.GONE);
            radio_refund_to_wallet.setChecked(true);
        }else if (orderCancelAble.equals("4")){
            radioRefundGroup.setVisibility(View.VISIBLE);
            radio_refund_to_wallet.setVisibility(View.GONE);
            radio_refund_to_bank.setVisibility(View.VISIBLE);
            radio_refund_to_bank.setChecked(true);

        }else if (orderCancelAble.equals("5")){
            radioRefundGroup.setVisibility(View.VISIBLE);
        }else {
            radioRefundGroup.setVisibility(View.GONE);
        }

        cancelOrder_btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refund_to_wallet = "";
                String refund_to_bank = "";
                if (orderCancelAble.equals("3")||
                        orderCancelAble.equals("4")||
                        orderCancelAble.equals("5")) {
                    RadioButton radioButton;
                    // get selected radio button from radioGroup
                    int selectedId = radioRefundGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);
                    if (radioButton.getText().toString().toLowerCase().equals(getResources().getString(R.string.st_refund_to_wallet).toLowerCase())) {
                        refund_to_wallet = "1";
                    } else {
                        refund_to_bank = "1";
                    }

                    if (adapter.getCheckedCancelReason() != null) {
                        postCancelOrderReason(adapter.getCheckedCancelReason().getId(), id, refund_to_wallet, refund_to_bank);


                    }

                }else {
                    if (adapter.getCheckedCancelReason() != null) {
                        postCancelOrderReason(adapter.getCheckedCancelReason().getId(),id,refund_to_wallet,refund_to_bank);
                        Map<String, Object> eventValue = new HashMap<String, Object>();
                        eventValue.put("Order status",""+""+orderStatusEng);
                        eventValue.put("reason of cancellation",""+adapter.getCheckedCancelReason().getReasonEn());
                        eventValue.put("canceled by","user");
//                        CleverTapAPI.getDefaultInstance(CancelOrder.this).pushEvent("Cancel Order:",eventValue);

                    }
                }

            }
        });
    }

    private void postCancelOrderReason(String reasonId,final String id, String refund_to_wallet, String refund_to_bank) {
        showProgress(true);
        User user= DatabaseManager.getInstance().getFirstOfClass(User.class);
        APIManager.getInstance().postCancelOrderReason(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        switch (jsonObject.getString("result")) {
                            case "true":
                                Intent intent=new Intent(CancelOrder.this, CanceledOrder.class);
                                intent.putExtra("orderID", ""+orderID);
                                startActivity(intent);
                                finish();
                                break;
                            case "false":
                                showAlertDialog(getString(R.string.cancel_order_failed_title),getString(R.string.cancel_order_failed_status));
                                break;
                            case "pending":
                                showAlertDialog(getString(R.string.cancel_order_pending_title),getString(R.string.cancel_order_pending_status));
                                break;
                        }

                        isOrderCancel = true;
                        /*broadcastData("");*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new CancelOrderRequest(user.userId, id, reasonId,refund_to_wallet,refund_to_bank));

        Log.d("cancelOrderParms",new Gson().toJson(new CancelOrderRequest(user.userId, id, reasonId,refund_to_wallet,refund_to_bank))+"");
    }

    private void showAlertDialog(final String title, String msg) {
        final Dialog dialog = new Dialog(CancelOrder.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView txtTitle = dialog.findViewById(R.id.title);
        TextView message = (TextView) dialog.findViewById(R.id.message);
        txtTitle.setText(title);
        message.setText(msg);
        Button ok = dialog.findViewById(R.id.okBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        cancel.setVisibility(View.GONE);
        dialog.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals(getString(R.string.cancel_order_success_title))) {

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(CancelOrder.this);
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
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);

    }
}
