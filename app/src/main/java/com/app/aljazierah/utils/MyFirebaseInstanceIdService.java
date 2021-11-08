package com.app.aljazierah.utils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MYFireBaseToken";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedtoken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Refreshed Token",refreshedtoken);
        System.out.println("Refresh Token is"+refreshedtoken);
//        storeToken(refreshedtoken);
    }

}
