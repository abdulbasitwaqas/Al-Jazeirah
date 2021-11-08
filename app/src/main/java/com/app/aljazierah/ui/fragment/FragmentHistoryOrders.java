package com.app.aljazierah.ui.fragment;


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
import com.app.aljazierah.utils.ProductsSingleton;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistoryOrders extends Fragment {

    private List<Orders> ordersList;
    private OrdersRecyclerViewAdapter ordersAdapter;
    RecyclerView recyclerView;
    public FragmentHistoryOrders() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setOrdersList();
                }
            },200);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_orders_list, container, false);
         recyclerView =  view.findViewById(R.id.orders_list);
        //setOrdersList();
        return view;
    }

    private void setOrdersList(){
        ordersList = new ArrayList<>();
        for (int i = 0; i< ProductsSingleton.getInstance().getUserOrdersList().size(); i++) {
            Orders order = ProductsSingleton.getInstance().getUserOrdersList().get(i);
            if (order.getCurrentStatus().equals("4")
                    || order.getCurrentStatus().equals("6")
            )


                ordersList.add(order);
        }

        ordersAdapter = new OrdersRecyclerViewAdapter(getActivity(), getActivity(), ordersList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ordersAdapter);
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
                    setOrdersList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
