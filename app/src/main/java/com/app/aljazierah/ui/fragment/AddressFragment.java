package com.app.aljazierah.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.adapter.AddressAdapter;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.ui.AddAddressActivity;
import com.app.aljazierah.utils.recycler.DividerItemDecoration;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.roam.appdatabase.DatabaseManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {


    private View rootView;
    private AddressAdapter addressAdapter;
    private List<Address> addressList;
    private RecyclerView recyclerView;

    public AddressFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        return new AddressFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppController.setLocale();
        rootView = inflater.inflate(R.layout.fragment_address, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.address);
        Button addAddress =rootView.findViewById(R.id.add_address);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {

                    startActivity(new Intent(getActivity(), AddAddressActivity.class)
                    .putExtra("fromadrress",true));
                } else {
                    checkPermission();
                }
            }
        });

        User user  = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user!=null){
            if (user.add_address.equals("0")){
                addAddress.setVisibility(View.GONE);
            }
        }
        setAddressAdapter();
        return rootView;
    }

    private void setAddressAdapter() {
        if (addressList == null) {
            addressList = new ArrayList<>();
            addressList = DatabaseManager.getInstance().getAllOfClass(Address.class);

            Collections.sort(addressList, new Comparator<Address>() {
                @Override
                public int compare(Address lhs, Address rhs) {
                    return rhs.addId.compareTo(lhs.addId);
                }
            });
        }


        if (addressList.isEmpty()){
            int permissionLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getActivity(), AddAddressActivity.class)
                        .putExtra("fromadrress",true));
                getActivity().finish();
            } else {
                checkPermission();
            }

        }
        addressAdapter = new AddressAdapter(addressList, getActivity(),getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addressAdapter);

    }

    public void refreshAddressList() {

        addressList = DatabaseManager.getInstance().getAllOfClass(Address.class);
        Collections.sort(addressList, new Comparator<Address>() {
            @Override
            public int compare(Address lhs, Address rhs) {
                return rhs.addId.compareTo(lhs.addId);
            }
        });
        addressAdapter.setAddressList(addressList);
    }

    public void checkPermission() {
        Dexter.withActivity(getActivity()).withPermission("android.permission.ACCESS_FINE_LOCATION").withListener(new PermissionListener() {
            public void onPermissionGranted(PermissionGrantedResponse response) {
                startActivity(new Intent(getActivity(), AddAddressActivity.class));
            }

            public void onPermissionDenied(PermissionDeniedResponse response) {
                checkPermission();
                Toast.makeText(getActivity(), "Location permission is required", Toast.LENGTH_LONG).show();
            }

            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public void updateAddressList() {
        if (addressAdapter == null)
            return;
        addressList = DatabaseManager.getInstance().getAllOfClass(Address.class);
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        Collections.sort(addressList, new Comparator<Address>() {
            @Override
            public int compare(Address lhs, Address rhs) {
                return rhs.addId.compareTo(lhs.addId);
            }
        });
        addressAdapter.setAddressList(addressList);
    }

    @Override
    public void onResume() {
        super.onResume();
        //setAddressAdapter();
        updateAddressList();
    }

}
