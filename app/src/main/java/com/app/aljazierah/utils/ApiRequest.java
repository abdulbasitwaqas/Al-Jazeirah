package com.app.aljazierah.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.aljazierah.BuildConfig;
import com.app.aljazierah.Interfaces.IResult;
import com.app.aljazierah.object.ParmsModel;
import com.app.aljazierah.object.login.User;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.facebook.FacebookSdk.getApplicationContext;


public class ApiRequest {
    private IResult iResult = null;
    private Context context;

    public ApiRequest(IResult resultCallback, Context context) {
        iResult = resultCallback;
        this.context = context;
    }




    public void apiRequestPostMethodWithJsonObject(String url, final JSONObject jsonObject, final String requestMessage) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (iResult != null)
                                iResult.notifySuccess(response.toString(), requestMessage);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("jsonreqesssst", "..............");
                    if (iResult != null)
                        iResult.notifyError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                    headers.put("AppVersion", ""+ BuildConfig.VERSION_NAME);
                    if (HmsGmsUtil.isOnlyHms(getApplicationContext())){
                        headers.put("DeviceType", "HMS");
                    }
                    else {
                        headers.put("DeviceType", "Google");
                    }

                    headers.put("Content-Type", "application/json");
                    if (user != null) {
                        headers.put("Authorization", user.authToken);
                        Log.d("**authorization",user.authToken);
                    }
                    else {
                        headers.put("Authorization", Constants.KEY);
                        Log.d("**authorization", Constants.KEY);
                    }

                    return headers;
                }
            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    120000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjReq);


        } catch (Exception e) {
        }
    }

    public void apiRequestPostMethod(String url, final List<ParmsModel> parmsModelList, final String requestMessage) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest jsonObj = new StringRequest(Request.Method.POST,   url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (iResult != null)
                        iResult.notifySuccess(response,requestMessage);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (iResult != null)
                        iResult.notifyError(error);
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                    headers.put("AppVersion", ""+ BuildConfig.VERSION_NAME);
                    if (HmsGmsUtil.isOnlyHms(getApplicationContext())){
                        headers.put("DeviceType", "HMS");
                    }
                    else {
                        headers.put("DeviceType", "Google");
                    }
                    if (user != null) {
                        headers.put("Authorization", user.authToken);
                        Log.d("**authorization",user.authToken);
                    }
                    else {
                        headers.put("Authorization", Constants.KEY);
                        Log.d("**authorization", Constants.KEY);
                    }



                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    for (int i = 0; i< parmsModelList.size(); i++){
                        params.put(parmsModelList.get(i).getParmsTag(), parmsModelList.get(i).getParmsValue());
                        Log.d("****params", parmsModelList.get(i).getParmsTag()+",,"+parmsModelList.get(i).getParmsValue());
                    }
                    return params;
                }

            };
            jsonObj.setRetryPolicy(new DefaultRetryPolicy(
                    120000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }


}
