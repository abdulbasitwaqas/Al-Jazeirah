package com.app.aljazierah.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.aljazierah.BuildConfig;
import com.app.aljazierah.object.login.User;
import com.roam.appdatabase.DatabaseManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AddHeaderInterceptorForImg implements Interceptor {


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        Request.Builder builder = chain.request().newBuilder();
        builder.header("AppVersion", ""+BuildConfig.VERSION_NAME);
        if (HmsGmsUtil.isOnlyHms(getApplicationContext())){
            builder.header("DeviceType", "HMS");
        }
        else {
            builder.header("DeviceType", "Google");
        }

//        builder.header("Content-Type", "application/json");

        if (user != null) {
            builder.header("Authorization", user.authToken);
        }
        else {
            builder.header("Authorization", Constants.KEY);
            Log.d("**authorization", Constants.KEY);

        }

        return chain.proceed(builder.build());
    }
}