package com.app.aljazierah.ui.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.aljazierah.R;
import com.app.aljazierah.adapter.OrdersRecyclerViewAdapter;
import com.app.aljazierah.object.Orders;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.utils.ProductsSingleton;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavouritesOrders extends Fragment {
    private ProgressDialog mProgressDialog;
    private User mUser;
    private List<Orders> ordersList=new ArrayList<>();
    public int page = 0;
    RecyclerView orders_list;
    private OrdersRecyclerViewAdapter ordersAdapter;
    public FragmentFavouritesOrders() {
        // Required empty public constructor
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFavourites();
                }
            },500);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders_list, container, false);
        orders_list =view.findViewById(R.id.orders_list);

        return view;
    }

    public void getFavourites() {
        ordersList = new ArrayList<>();
        for (int i = 0; i< ProductsSingleton.getInstance().getUserOrdersList().size(); i++) {
            Orders order = ProductsSingleton.getInstance().getUserOrdersList().get(i);
            if (order.getIsFavorite().equals("1"))
                ordersList.add(order);
        }
        ordersAdapter=new OrdersRecyclerViewAdapter(getActivity(),getActivity(),ordersList);
        orders_list.setHasFixedSize(true);
        orders_list.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        orders_list.setAdapter(ordersAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mReceiverOnAddressChange, new IntentFilter("data_action_update_list"));
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
                String string = (String) intent.getSerializableExtra("onupdatelist");
                Log.d("updated", string + "....");
                if (string.equals("updated")) {
                    getFavourites();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
