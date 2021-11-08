package com.app.aljazierah.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.huawei.hms.api.HuaweiApiAvailability;

public class HmsGmsUtil {
    private static final String TAG = "HmsGmsUtil";
    public static boolean isHmsAvailable(Context context) {
        boolean isAvailable = false;
        if (null != context) {
            int result = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
            isAvailable = (com.huawei.hms.api.ConnectionResult.SUCCESS == result);
        }
        Log.i(TAG, "isHmsAvailable: " + isAvailable);
        return isAvailable;
    }

    public static boolean isGmsAvailable(Context context) {
        boolean isAvailable = false;
        if (null != context) {
            int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            isAvailable = (com.google.android.gms.common.ConnectionResult.SUCCESS == result);
        }
        Log.i(TAG, "isGmsAvailable: " + isAvailable);
        return isAvailable;
    }


    public static boolean isOnlyHms(Context context) {
        //return isHmsAvailable(context) && !isGmsAvailable(context);
        return isHmsAvailable(context);//only for debugging
    }


}