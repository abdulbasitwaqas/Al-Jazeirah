package com.app.aljazierah.ui.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.app.aljazierah.AppController;
import com.app.aljazierah.object.Orders;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.OrderRequest;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.CustomViewPager;
import com.app.aljazierah.utils.ProductsSingleton;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.R;
import com.app.aljazierah.utils.ViewPager.ViewPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.app.aljazierah.utils.Constants.isOrderCancel;


public class FragmentOrders extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private ProgressDialog mProgressDialog;
    Gson gson;
    public boolean isUpdated = false;
    SwipeRefreshLayout swipeRefresh;
    Date startTime;
    private CustomViewPager viewPager;
    private TextView activeOrdersTVOF,historyOrdersTVOF,favOrdersTVOF;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                    if (user != null) {

                      if (isNetworkConnected())  {
                            getOrdersRequest();
                        }
                      else
                          Toast.makeText(getActivity(), ""+getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    } else {
                        ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
                    }

                }
            }, 500);
        }
        createGson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_your_orders, container, false);

        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        activeOrdersTVOF = rootView.findViewById(R.id.activeOrdersTVOF);
        historyOrdersTVOF = rootView.findViewById(R.id.historyOrdersTVOF);
        favOrdersTVOF = rootView.findViewById(R.id.favOrdersTVOF);
        
        viewPager = rootView.findViewById(R.id.yourOrders_viewPagerFrag);


        swipeRefresh.setOnRefreshListener(this);
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
       /* if (user!=null)
        getOrdersRequest();*/
//        createViewPager();




        activeOrdersTVOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(0);

                activeOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimary));
                historyOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                favOrdersTVOF.setTextColor(getResources().getColor(R.color.white));

                activeOrdersTVOF.setBackgroundResource(R.color.white);
                historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);

            }
        });

        historyOrdersTVOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(1);
                activeOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                historyOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                favOrdersTVOF.setTextColor(getResources().getColor(R.color.white));

                activeOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                historyOrdersTVOF.setBackgroundResource(R.color.white);
                favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);

            }
        });

        favOrdersTVOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                activeOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                historyOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                favOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                activeOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                favOrdersTVOF.setBackgroundResource(R.color.white);
            }
        });

        return rootView;
    }

    private void createViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setOffscreenPageLimit(2);

        adapter.addFrag(new FragmentActiveOrders(), getResources().getString(R.string.st_active));
        adapter.addFrag(new FragmentHistoryOrders(), getResources().getString(R.string.st_history));
        adapter.addFrag(new FragmentFavouritesOrders(), getResources().getString(R.string.st_favourite));
        viewPager.setAdapter(adapter);
        AppController.getInstance().setViewPager_home(viewPager);

        activeOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimary));
        historyOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
        favOrdersTVOF.setTextColor(getResources().getColor(R.color.white));

        activeOrdersTVOF.setBackgroundResource(R.color.white);
        historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
        favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);

        setTabs();
        activeOrdersTVOF.setBackgroundResource(R.color.white);
        historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
        favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
    }

    private void setTabs() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        viewPager.setCurrentItem(0);

                        activeOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimary));
                        historyOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                        favOrdersTVOF.setTextColor(getResources().getColor(R.color.white));

                        activeOrdersTVOF.setBackgroundResource(R.color.white);
                        historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                        favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);


                        break;


                    case 1:
                        viewPager.setCurrentItem(1);
                        activeOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                        historyOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        favOrdersTVOF.setTextColor(getResources().getColor(R.color.white));

                        activeOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                        historyOrdersTVOF.setBackgroundResource(R.color.white);
                        favOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);

                        break;

                    case 2:
                        viewPager.setCurrentItem(2);
                        activeOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                        historyOrdersTVOF.setTextColor(getResources().getColor(R.color.white));
                        favOrdersTVOF.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                        activeOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                        historyOrdersTVOF.setBackgroundResource(R.color.colorPrimaryDark);
                        favOrdersTVOF.setBackgroundResource(R.color.white);

                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void getOrdersRequest() {
        startTime = Calendar.getInstance().getTime();
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        //showProgress(true);
        swipeRefresh.setRefreshing(true);
        OrderRequest orderRequest = new OrderRequest(user.userId, 0);
        APIManager.getInstance().getOrders(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                //showProgress(false);
                swipeRefresh.setRefreshing(false);
                if (z) {
                    try {
                        if (response.contains("Invalid Key!") || response.contains("Access denied!")) {
                            CoreManager.getInstance().removeUserData();
                            Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            JSONObject jsonObject = new JSONObject(response);
                            Type listType = new TypeToken<List<Orders>>() {
                            }.getType();
                            List<Orders> userOrdersList = gson.fromJson(jsonObject.getString("rows"), listType);
                            ProductsSingleton.getInstance().setUserOrdersList(userOrdersList);

                            if (isUpdated)
                                broadcastData();
                            else {
                                createViewPager();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, orderRequest);
    }


    private void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(getActivity());
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
    public void onResume() {
        super.onResume();
        if (isOrderCancel){
            User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
            if (user != null) {

                if (isNetworkConnected())  {
                    getOrdersRequest();
                }
                else
                    Toast.makeText(getActivity(), ""+getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
            }
            isOrderCancel = false;
        }




        getActivity().registerReceiver(mReceiverOnAddressChange, new IntentFilter("data_action_order_change"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiverOnAddressChange);
    }

    private BroadcastReceiver mReceiverOnAddressChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String string = (String) intent.getSerializableExtra("onOrderChange");
                String orderId = (String) intent.getSerializableExtra("orderId");
                String favType = (String) intent.getSerializableExtra("favType");

                Log.d("changeddd", string + "....");
                if (string.equals("changedFav")) {
                    for (int i = 0; i < ProductsSingleton.getInstance().getUserOrdersList().size(); i++) {
                        if (orderId.equals(ProductsSingleton.getInstance().getUserOrdersList().get(i).getOrdId())) {
                            if (favType.equals("fav")) {
                                ProductsSingleton.getInstance().getUserOrdersList().get(i).setIsFavorite("1");
                            } else {
                                ProductsSingleton.getInstance().getUserOrdersList().get(i).setIsFavorite("2");
                            }
                            broadcastData();
                            break;
                        }
                    }

                } else {
                    getOrdersRequest();
                    isUpdated = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void broadcastData() {
        Intent intent = new Intent("data_action_update_list");
        intent.putExtra("onupdatelist", "" + "updated");
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onRefresh() {
        // startTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Date currentTime = Calendar.getInstance().getTime();
        long difference = currentTime.getTime() - startTime.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        Log.i("======= Hours", " :: " + min);

        if (min > 1) {
            getOrdersRequest();
        } else {
            swipeRefresh.setRefreshing(false);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
