package com.app.aljazierah.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.Store;
import com.app.aljazierah.utils.HmsGmsUtil;
import com.app.aljazierah.utils.gpsLocation.GetLocationByGoogleClientApi;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.maps.HuaweiMap;

import java.util.ArrayList;
import java.util.List;

public class OurShowRoomsMapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, com.huawei.hms.maps.OnMapReadyCallback {
    private FrameLayout mapGroupOSA;
    private GoogleMap mMap;
    private HuaweiMap hMap;
    private LatLng userCurrentLatlng = null;
    boolean isAreaSlected = false;
    private Gson gson;
    private String mapCity = "";
    private String mapArea = "";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequestH;
    private com.huawei.hms.location.SettingsClient settingsClientH;
    com.huawei.hms.location.LocationCallback mLocationCallbackH;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000;
    GetLocationByGoogleClientApi getLocationByGoogleClientApi;
    private ProgressDialog mProgressDialog;
    Marker gMarker;
    private ImageView imageViewBackButton;
    com.huawei.hms.maps.model.Marker hMarker;
    List<Store> storeList = new ArrayList<>();
    private  String titlee;
    private boolean showAn;
    private int position=0;
    private Dialog dialog;
    private TextView textview_location;
    private String intentLat, intentLng, name;
    private LatLng latLng;
    private com.huawei.hms.maps.model.LatLng hLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_show_rooms);
        intentLat = getIntent().getStringExtra("lat");
        intentLng = getIntent().getStringExtra("lng");
        name = getIntent().getStringExtra("name");
        Log.d("*latlngaa",intentLat+","+intentLng);
        createGson();
        initMembers();


    }


  /*  private void getStores() {
        showProgress(true);
        APIManager.getInstance().getStoresRequest(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);

                if (z) {
                    Log.e("" + z, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Gson gson = new Gson();

                        Type listType = new TypeToken<List<Store>>() {
                        }.getType();
                        storeList = gson.fromJson(jsonObject.getString("stores"), listType);
                        setDataOnMap();


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }*/

    private void setDataOnMap() {
//        Log.d("**latlng", intentLat+ "   :LAT    , oooo     LNG:     " + intentLng);
//        for (int i = 0; i < storeList.size(); i++) {

            Log.d("**latlng", intentLat+ "   :LAT    ,      LNG:     " + intentLng);


            if (HmsGmsUtil.isOnlyHms(this)){
                hMarker = hMap.addMarker(new com.huawei.hms.maps.model.MarkerOptions()

                        .position(new com.huawei.hms.maps.model.LatLng(Double.parseDouble(intentLat), Double.parseDouble(intentLng)))
                        .icon(com.huawei.hms.maps.model.BitmapDescriptorFactory.fromBitmap(getScaledBitmap(R.drawable.marker, 100,100))));
//                        hMarker.setTitle(storeList.get(i).getStoreName());

                hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.huawei.hms.maps.model.Marker marker) {
                        hMap.clear();
                        showroomsDialog();
                        return false;
                    }
                });

                hLatlng = new com.huawei.hms.maps.model.LatLng(Double.parseDouble(intentLat), Double.parseDouble(intentLng));
                com.huawei.hms.maps.CameraUpdate cameraUpdate = com.huawei.hms.maps.CameraUpdateFactory.newLatLngZoom(hLatlng, 17);
                hMap.animateCamera(cameraUpdate);

                        
            }
            else {
                gMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(intentLat), Double.parseDouble(intentLng)))
                        .icon(BitmapDescriptorFactory.fromBitmap(getScaledBitmap(R.drawable.marker, 100, 100))));
//                gMarker.setTitle(storeList.get(i).getStoreName());


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        mMap.clear();
                        showroomsDialog();
                        return true;
                    }
                });
                latLng = new LatLng(Double.parseDouble(intentLat), Double.parseDouble(intentLng));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                mMap.animateCamera(cameraUpdate);
            }
//        }






    }

    public void showroomsDialog() {
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.show_rooms_dialog);

        TextView name = dialog.findViewById(R.id.name);
        TextView location = dialog.findViewById(R.id.location);
        TextView phoneNoTV = dialog.findViewById(R.id.phoneNoTV);
        TextView timingTV = dialog.findViewById(R.id.timingTV);
        ImageView cancelDialogBtn = dialog.findViewById(R.id.cancelDialogBtn);

        for (int i = 0; i < storeList.size(); i++) {
            if (HmsGmsUtil.isOnlyHms(this)){
                hMarker = hMap.addMarker(new com.huawei.hms.maps.model.MarkerOptions()
                        .position(new com.huawei.hms.maps.model.LatLng(Double.parseDouble(storeList.get(i).getLatitude()), Double.parseDouble(storeList.get(i).getLongitude())))
                        .icon(com.huawei.hms.maps.model.BitmapDescriptorFactory.fromBitmap(getScaledBitmap(R.drawable.marker, 100,100))));
//                hMarker.setTitle(storeList.get(i).getStoreName());
                hMarker.setTag(position);



                name.setText(storeList.get(position).getNameEn());
                location.setText(storeList.get(position).getStoreMeta());
                phoneNoTV.setText(storeList.get(position).getStoreNumber());
                timingTV.setText(storeList.get(position).getMorningOpentime()+" - "+storeList.get(position).getMorningClosetime());
            }
            else {
                gMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(storeList.get(i).getLatitude()), Double.parseDouble(storeList.get(i).getLongitude())))
                        .icon(BitmapDescriptorFactory.fromBitmap(getScaledBitmap(R.drawable.marker, 100, 100))));
//                gMarker.setTitle(storeList.get(i).getStoreName());
                gMarker.setTag(position);


                name.setText(storeList.get(position).getNameEn());
                location.setText(storeList.get(position).getStoreMeta());
                phoneNoTV.setText(storeList.get(position).getStoreNumber());
                timingTV.setText(storeList.get(position).getMorningOpentime()+" - "+storeList.get(position).getMorningClosetime());
            }
        }





        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.show();
    }

    private Bitmap getScaledBitmap(int id, int height, int width) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(id);
        Bitmap b = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(b, width, height, false);
    }


    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void initMembers() {
        mapGroupOSA = findViewById(R.id.mapGroupOSA);
        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        textview_location = findViewById(R.id.textview_location);
        imageViewBackButton.setVisibility(View.VISIBLE);
        textview_location.setVisibility(View.GONE);

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (HmsGmsUtil.isOnlyHms(this)) {
            createAsH();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        } else {
            createAsG();
            getLocationByGoogleClientApi = new GetLocationByGoogleClientApi(OurShowRoomsMapActivity.this);
        }
    }

    private void createAsH() {
        FrameLayout mapGroup = (FrameLayout) findViewById(R.id.mapGroup);
        View child = getLayoutInflater().inflate(R.layout.hmap, mapGroup, false);
        mapGroup.addView(child);

        com.huawei.hms.maps.SupportMapFragment mapFragmentHuawei = (com.huawei.hms.maps.SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragmentHuawei.getMapAsync(this);
    }

    private void createAsG() {
        FrameLayout mapGroup = (FrameLayout) findViewById(R.id.mapGroupOSA);
        View child = getLayoutInflater().inflate(R.layout.gmap, mapGroup, false);
        mapGroup.addView(child);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;
        hMap.setMyLocationEnabled(false);
//        getStores();
        setDataOnMap();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        getStores();
        setDataOnMap();
    }

    private void moveToLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.clear();
        // getAddressRequest(currentLocation.latitude,currentLocation.longitude);

    }

    private void moveToLocationH(com.huawei.hms.maps.model.LatLng currentLocation) {
        hMap.moveCamera(com.huawei.hms.maps.CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        hMap.animateCamera(com.huawei.hms.maps.CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        hMap.animateCamera(com.huawei.hms.maps.CameraUpdateFactory.zoomTo(15), 2000, null);
        hMap.clear();
        // getAddressRequest(currentLocation.latitude,currentLocation.longitude);

    }

    private void showProgress(boolean show) {
        try {
            if (show) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            } else {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (Exception ex) {

        }
    }

}