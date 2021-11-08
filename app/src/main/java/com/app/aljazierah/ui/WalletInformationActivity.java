package com.app.aljazierah.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.TransactionsAdapter;
import com.app.aljazierah.object.TransactionObject;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.WalletRequest;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WalletInformationActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_wallet,wallet_amount,tv_type;
    ProgressDialog mProgressDialog;
    private boolean isArabic;
    RecyclerView rv_transactions;
    private TransactionsAdapter transactionsAdapter;
//    private RelativeLayout badge_viewFH;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.activity_wallet);


        if (UserSession.getInstance().getUserLanguage().equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        setViews();
        getWallet();
    }

    private void setToolbar() {
//        View toolbar = findViewById(R.id.toolbar);
//        View badge_view = toolbar.findViewById(R.id.badge_viewFH);
//        badge_viewFH = toolbar.findViewById(R.id.badge_viewFH);
        TextView textview_location = findViewById(R.id.textview_location);
//        badge_view.setVisibility(View.GONE);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.GONE);
        ImageView iv_backbtn = findViewById(R.id.imageViewBackButton);
        TextView title_textview =findViewById(R.id.tvToolbarTitleTCA);
        title_textview.setText(getResources().getString(R.string.st_wallet));
        iv_backbtn.setVisibility(View.VISIBLE);
        iv_backbtn.setOnClickListener(this);
    }

    private void setViews() {
        setToolbar();
        Button btn_shopnow = findViewById(R.id.btn_shopnow);
        tv_wallet = findViewById(R.id.tv_walletbalance);
        wallet_amount=findViewById(R.id.wallet_amount);
        tv_type=findViewById(R.id.tv_type);
        rv_transactions=findViewById(R.id.rv_transactions);
        btn_shopnow.setOnClickListener(this);
        btn_shopnow.setText(getResources().getString(R.string.st_shopnow));
        transactionsAdapter=new TransactionsAdapter(new ArrayList<TransactionObject>(),getApplicationContext());
        rv_transactions.setLayoutManager(new LinearLayoutManager(this));
        rv_transactions.setAdapter(transactionsAdapter);

    }

    private void getWallet() {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            showProgress(true);
            APIManager.getInstance().get_wallet_details(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {
                    if (z) {

                        try {
                            if (new JSONObject(response).optString("code").equals("200")) {
                                tv_wallet.setText(new JSONObject(response).getString("wallet_balance")
                                        + " " + getResources().getString(R.string.currency));
                                if (new JSONObject(response).getInt("last_transaction") > 0) {

                                    if (new JSONObject(response).getString("last_transaction_type").toLowerCase().equals("")) {
                                        tv_type.setText("");
                                    } else if (new JSONObject(response).getString("last_transaction_type").toLowerCase().equals("debit")) {
                                        tv_type.setText(getResources().getString(R.string.st_debit));
                                    } else {
                                        tv_type.setText(getResources().getString(R.string.st_credit));
                                    }

                                    wallet_amount.setText(new JSONObject(response).getString("last_transaction")
                                            + " " + getResources().getString(R.string.currency));
                                }
                                ArrayList<TransactionObject> transactionObjectArrayList = new ArrayList<>();
                                JSONArray jsonArray = new JSONObject(response).getJSONArray("wallet_transactions");
                                for (int i = 0; i < jsonArray.length(); i++) {


                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("Ex_cartitems", jsonObject.getString("amount") + "");

                                    TransactionObject transactionObject = new TransactionObject();
                                    transactionObject.setOrderId(jsonObject.getString("order_id"));
                                    transactionObject.setAmount(jsonObject.getString("amount"));
                                    transactionObject.setTransactionType(jsonObject.getString("transaction_type"));
                                    transactionObject.setReference(jsonObject.getString("reference"));
                                    transactionObject.setSdt(jsonObject.getString("sdt"));
                                    transactionObjectArrayList.add(transactionObject);

                                }
                                transactionsAdapter.setTransactionList(transactionObjectArrayList);

                            }else {
                                Toast.makeText(WalletInformationActivity.this, ""+new JSONObject(response).optString("error"), Toast.LENGTH_SHORT).show();
                            }

                            showProgress(false);
                        }
                        catch (Exception e) {
                            Log.e("Ex_cartitems", e.toString());
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(WalletInformationActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                    }


                }
            }, new WalletRequest(user.userId));
        }
    }


    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(WalletInformationActivity.this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBackButton:
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;
            case R.id.btn_shopnow:
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
