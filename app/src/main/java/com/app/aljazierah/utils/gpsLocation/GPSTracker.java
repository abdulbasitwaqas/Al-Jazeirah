package com.app.aljazierah.utils.gpsLocation;

/**
 * Created by Lenovo on 11/20/2017.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by techintegrity on 08/07/16.
 */
public class GPSTracker extends Service implements LocationListener {
    private final Activity mContext;
    // flag for GPS status
    boolean isGPSEnabled = false;

    public boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Activity context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.d("isNetworkEnabled",""+isNetworkEnabled);
            Log.d("isGPSEnabled",""+isGPSEnabled);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.d("notnetwork",""+isGPSEnabled);
            }
            else {
                this.canGetLocation = true;
                // First get location from Network Provider
                checkLocationPermission();


                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("networkstatus", ""+locationManager);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Log.d("locationstatus", ""+location);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("netwrklatitude", ""+latitude);
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    Log.d("GPSStatus", "GPS Enabled");
                    if (location != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPSStatus", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d("gpslatitude", ""+latitude);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            checkLocationPermission();
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        //alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("Please enable GPS to get locations.");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void gpsModeConfirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Use location?");
        builder.setMessage(""+"For effective location selection, please select the following option:\n\n " +
                "\t\t \u233E Use GPS, Wi-Fi and mobile \n\t\t\tnetworks for location.")
                .setCancelable(false)
                .setPositiveButton(""+"Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(""+"Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Locationfdsafasdfa",location.toString());

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
              //  showGPSDisabledAlertToUser();
            }
            return true;
        }
    }

    public void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public boolean getGPSStatus()
    {
        return isGPSEnabled;
    }
}
