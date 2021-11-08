package com.app.aljazierah.utils.gpsLocation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class GetLocationByGoogleClientApi implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final Context mContext;
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    boolean isBroadcast = false;
    public GetLocationByGoogleClientApi(Context activity){
        this.mContext = activity;
        setUpGClient();
        Log.e("GoogleClientApi", ".........................");
    }
    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((FragmentActivity) mContext, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("locationnnnn","............");

        broadcastData();


    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    private void broadcastData(){
        if (mylocation!=null)
        isBroadcast = true;
        Intent in = new Intent("data_action");
        in.putExtra("onConnectedGoogleApi", "connected");
        mContext.sendBroadcast(in);
        Log.e("onConnectedGoogleApi", ".........................");

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            Double latitude=mylocation.getLatitude();
            Double longitude=mylocation.getLongitude();
            //Log.d("latlonggggg","Latitude : "+latitude+","+longitude);
            //Or Do whatever you want with your location
        }
    }

    public Location getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(mContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(mContext,
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                        if (!isBroadcast){
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    broadcastData();
                                                }
                                            }, 1000);

                                        }

                                    }
                                    break;

                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult((Activity) mContext,
                                                00);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }


        return mylocation;
    }


    public void removeGoogleClient(){
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage((FragmentActivity) mContext);
            googleApiClient.disconnect();
        }
    }
}
