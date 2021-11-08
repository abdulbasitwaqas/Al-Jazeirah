package com.app.aljazierah.Presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.app.aljazierah.Interfaces.IPresenter;
import com.app.aljazierah.Interfaces.IResult;
import com.app.aljazierah.Interfaces.RequestViews;
import com.app.aljazierah.object.ParmsModel;
import com.app.aljazierah.utils.ApiRequest;

import org.json.JSONObject;

import java.util.List;

public class Presenter implements IResult {
    ApiRequest apiRequests;
    private RequestViews requestViews;
    IPresenter iPresenter;
    Context context;
    private String requestMessage;
    private String LOG_TAG = "vooolllyerror";

    public Presenter(RequestViews requestViews, Context context, IPresenter iPresenter){
        this.requestViews = requestViews;
        this.context=context;
        this.iPresenter=iPresenter;
        this.apiRequests = new ApiRequest(this,context);
    }




    public void setPostMethod(String url, List<ParmsModel> parmsList, String requestMessage){
        requestViews.showProgress();
        this.requestMessage = requestMessage;
        apiRequests.apiRequestPostMethod(url,parmsList,requestMessage);
    }



    public void setPostMethodJsonObject(String url, JSONObject jsonObject, String requestMessage) {
        requestViews.showProgress();
        this.requestMessage = requestMessage;
        apiRequests.apiRequestPostMethodWithJsonObject(url, jsonObject, requestMessage);
    }


    @Override
    public void notifySuccess(String response, String requestMessage) {
        logLargeString(response);
        requestViews.hideProgress();
        iPresenter.getResponse(response,requestMessage);
    }

    @Override
    public void notifySuccessNetwork(NetworkResponse response, String requestMessage) {
        Log.d("notifySuccessNetwork", String.valueOf(response));
        requestViews.hideProgress();
        iPresenter.getSuccessNetwork(response,requestMessage);
    }
    @Override
    public void notifyError(VolleyError error) {
        iPresenter.getError(error);
        requestViews.hideProgress();
        if (error instanceof NetworkError) {
            //errorCode = NETWORK_ERROR;
            Log.i(LOG_TAG, "NetworkError" + error);
        } else if (error instanceof ServerError) {
            Log.i(LOG_TAG, "ServerError" + error);
        } else if (error instanceof AuthFailureError) {
            Log.i(LOG_TAG, "AuthFailureError" + error);
        } else if (error instanceof ParseError) {
            Log.i(LOG_TAG, "ParseError" + error);
        } else if (error instanceof NoConnectionError) {
            Log.i(LOG_TAG, "NoConnectionError" + error);
        } else if (error instanceof TimeoutError) {
            Log.i(LOG_TAG, "TimeoutError" + error);
        } else {
            Log.i(LOG_TAG, "TimeoutError" + error);
        }
    }


    public void logLargeString(String str) {
        if(str.length() > 10000) {
            Log.i("getRequestresponse", str.substring(0, 10000));
            logLargeString(str.substring(10000));
        } else {
            Log.i("getRequestresponse", ""+str);
        }
    }
}
