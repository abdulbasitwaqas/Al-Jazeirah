package com.app.aljazierah.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.SelectAreaListAdapter;
import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.GeoCodeApi.ModelGeoCodeApi;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.RegAddress;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.HmsGmsUtil;
import com.app.aljazierah.utils.gpsLocation.GetLocationByGoogleClientApi;
//import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.maps.HuaweiMap;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AddAddressActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, com.huawei.hms.maps.OnMapReadyCallback {
    Button tvSaveAddress;
    TextView tvAddress;
    TextView tv_availableCities;
    TextView tvAreas;
    TextView tvnoServices;
    EditText editAddressTitle;
    ImageView imageViewSearch, onMapSearch;
    private GoogleMap mMap;
    private HuaweiMap hMap;
    Gson gson;
    GetLocationByGoogleClientApi getLocationByGoogleClientApi;
    private LatLng userCurrentLatlng = null;
    List<Areas> areaFilteredList;
    private int cityId = 0;
    private int areaId = 0;
    private String mapCity = "";
    private String mapArea = "";
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private ProgressDialog mProgressDialog;
    boolean isProceed = false;
    boolean isAreaSlected = false;
    boolean isConnected = true;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequestH;
    private com.huawei.hms.location.SettingsClient settingsClientH;
    com.huawei.hms.location.LocationCallback mLocationCallbackH;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000;
    public static final int REQUEST_CODE = 0X01;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        // getSupportActionBar().hide();
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_add_address);
        createGson();
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        if (HmsGmsUtil.isOnlyHms(this)) {
            createAsH();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        } else {
            createAsG();
            getLocationByGoogleClientApi = new GetLocationByGoogleClientApi(AddAddressActivity.this);
        }


        tvSaveAddress = findViewById(R.id.tvSaveAddress);
        tv_availableCities = findViewById(R.id.tv_availableCities);
        tvAddress = findViewById(R.id.tvAddress);
        tvAreas = findViewById(R.id.tvAreas);
        tvnoServices = findViewById(R.id.tvnoServices);
        imageViewSearch = findViewById(R.id.imageViewSearch);
        onMapSearch = findViewById(R.id.onMapSearch);
        editAddressTitle = findViewById(R.id.editAddressTitle);
        imageViewSearch.setOnClickListener(this);
        onMapSearch.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvSaveAddress.setOnClickListener(this);
        tvAreas.setOnClickListener(this);
        tv_availableCities.setOnClickListener(this);
        setAvailableCities();
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
        FrameLayout mapGroup = (FrameLayout) findViewById(R.id.mapGroup);
        View child = getLayoutInflater().inflate(R.layout.gmap, mapGroup, false);
        mapGroup.addView(child);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setAvailableCities() {
        List<Cities> cities = DatabaseManager.getInstance().getAllOfClass(Cities.class);
        String availableCities = "";
        if (AppController.setLocale()) {
            //tv_availableCities.append("خدماتنا متوفرة فقط في مدينة"+"\n");
            availableCities = "خدماتنا متوفرة فقط في مدينة" + "\n";
        } else {
            //tv_availableCities.append("Our services are only available in these cities"+"\n");
            availableCities = "Our services are only available in these cities" + "\n";
        }

        for (int j = 0; j < cities.size(); j++) {
            if (AppController.setLocale()) {
                // tv_availableCities.append(cities.get(j).cityTitleAr+",");
                availableCities = availableCities + cities.get(j).cityTitleAr + ",";
            } else {
                //tv_availableCities.append(cities.get(j).cityTitleEn+",");
                availableCities = availableCities + cities.get(j).cityTitleEn + ",";
            }


        }
        if (availableCities.length() > 80) {
            availableCities = availableCities.substring(0, 80) + "...";
        }
        tv_availableCities.setText(availableCities);

    }

    private void openAutoCompleteIntent() {
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "" + getResources().getString(R.string.google_maps_key));
        }

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setCountry("SA")
                .build(AddAddressActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void getUserCurrentLatLong() {
        Location location = getLocationByGoogleClientApi.getMyLocation();
        if (location != null) {
            LatLng currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
            userCurrentLatlng = currentLatLong;
            moveToLocation(userCurrentLatlng);
        }
    }

    private void createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void getAddressRequest(double lat, double lng) {
        userCurrentLatlng = new LatLng(lat, lng);

        showProgress(true);
        APIManager.getInstance().getAddressByLatLng(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                //Log.d("onResult: ApiResponse: ", "" + response);
                showProgress(false);
                if (z) {
                    try {
                        isAreaSlected = false;
                        boolean flag = false;
                        boolean areaflag = false;
                        mapCity = "";
                        mapArea = "";
                        //tvSaveAddress.setVisibility(View.GONE);
                        tvnoServices.setVisibility(View.GONE);

                        JSONObject jsonObject = new JSONObject(response);
                        ModelGeoCodeApi geoCodeApi = gson.fromJson(jsonObject.toString(), ModelGeoCodeApi.class);
                        if (geoCodeApi.getStatus().equals("ZERO_RESULTS")) {
                            tvAddress.setText("Location not found");
                            tvAreas.setVisibility(View.GONE);
                        } else if (geoCodeApi.getStatus().equals("OVER_QUERY_LIMIT")) {
                            tvAddress.setText("Location not found");
                            tvAreas.setVisibility(View.GONE);
                        } else {
                            tvAddress.setText("" + geoCodeApi.getResults().get(0).getFormattedAddress());
                            editAddressTitle.setText("" + geoCodeApi.getResults().get(0).getFormattedAddress());

                            for (int i = 0; i < geoCodeApi.getResults().get(0).getAddressComponents().size(); i++) {

                                for (int k = 0; k < geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().size(); k++) {

                                    if (geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().get(k).equals("locality")) {
                                        mapCity = geoCodeApi.getResults().get(0).getAddressComponents().get(i).getLongName();
                                        Log.d("localitymapcity", "..." + mapCity);
                                    }

                                    if (geoCodeApi.getResults().get(0).getAddressComponents().get(i).getTypes().get(k).equals("sublocality")) {
                                        mapArea = geoCodeApi.getResults().get(0).getAddressComponents().get(i).getLongName();
                                        Log.d("localitymaparea", "..." + mapArea);
                                    }
                                }

                            }
                            Log.d("localitymapcity", "..." + mapCity);

                            if (!mapCity.equals("")){
                                List<Cities> cities = DatabaseManager.getInstance().getAllOfClass(Cities.class);
                            for (int j = 0; j < cities.size(); j++) {

                                if (cities.get(j).cityTitleEn.toLowerCase().equals(mapCity.toLowerCase())
                                        || cities.get(j).cityTitleAr.equals(mapCity)) {
                                    flag = true;
                                    cityId = cities.get(j).cityId;
                                }

                            }


                            if (flag) {
                                //tv_availableCities.setVisibility(View.GONE);

                                if (mapArea.equals("")) {
                                    mapArea = "unknown";
                                }
                                if (!isAreaMatch()) {
                                    mapArea = "other";
                                }

                                if (cityId != 0) {
                                    //List<Areas> areas = DatabaseManager.getInstance().getAllOfClass(Areas.class);
                                    List<Areas> areas = getCityArea(cityId);
                                    for (int j = 0; j < areas.size(); j++) {
                                        Log.d("localityArea", "..." + mapArea+","+areas.get(j).areaTitleEn+","+areas.get(j).areaTitleAr);
                                        if (areas.get(j).areaTitleEn.toLowerCase().equals(mapArea.toLowerCase()) ||
                                                areas.get(j).areaTitleAr.toLowerCase().equals(mapArea.toLowerCase())) {
                                            areaflag = true;
                                            areaId = areas.get(j).areaId;
                                        }

                                    }
                                    isAreaSlected = true;
                                }

                                if (areaflag) {
                                    tvAreas.setVisibility(View.GONE);
                                    //tvSaveAddress.setBackgroundResource(R.drawable.);
                                    //tvSaveAddress.setVisibility(View.VISIBLE);
                                    //tvSaveAddress.setEnabled(true);
                                    tvAreas.setText("");

                                    if (isProceed) {
                                        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                                        if (user != null) {
                                            addAddressRequest();
                                        } else {
                                            UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject("") + "");
                                            broadcastData();
                                            finish();
                                        }
                                    }

                                } else {
                                    tvAreas.setVisibility(View.GONE);
                                    // tvSaveAddress.setVisibility(View.VISIBLE);
                                    // tvSaveAddress.setEnabled(true);
                                    areaId = 0;
                                    tvAreas.setText("");


                                    if (isProceed) {
                                        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

                                        if (user != null) {
                                            addAddressRequest();
                                        } else {
                                            UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject("") + "");
                                            broadcastData();
                                            finish();
                                        }
                                    }



                                    //Toast.makeText(AddAddressActivity.this, "" + getResources().getString(R.string._string_please_select_area), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                tvAreas.setVisibility(View.GONE);
                                // tvSaveAddress.setVisibility(View.GONE);
                                //tvnoServices.setVisibility(View.VISIBLE);
                                // tvSaveAddress.setEnabled(false);
                                // tv_availableCities.setVisibility(View.VISIBLE);
                                // Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_service_in_area), Toast.LENGTH_LONG).show();
                                alertDialogMessage(getResources().getString(R.string.no_service_in_area1));
                            }
                        }  else {
                                tvAreas.setVisibility(View.GONE);
                                // tvSaveAddress.setVisibility(View.GONE);
                                //tvnoServices.setVisibility(View.VISIBLE);
                                // tvSaveAddress.setEnabled(false);
                                // tv_availableCities.setVisibility(View.VISIBLE);
                                // Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_service_in_area), Toast.LENGTH_LONG).show();
                                alertDialogMessage(getResources().getString(R.string.no_service_in_area1));
                            }

                        }

                    } catch (Exception e) {
                        Log.e("Ex_cartitems", e.toString());
                        e.printStackTrace();
                    }
                } else {
                    showProgress(false);
                    finish();
                }
            }
        }, lat + "," + lng, "ar", AddAddressActivity.this);
    }

    private boolean isAreaMatch(){
        List<Areas> areas = getCityArea(cityId);
        for (int j = 0; j < areas.size(); j++) {
            Log.d("localityArea", areas.get(j).areaTitleEn+","+areas.get(j).areaTitleAr+"..." + mapArea);
            if (areas.get(j).areaTitleEn.toLowerCase().equals(mapArea.toLowerCase()) ||
                    areas.get(j).areaTitleAr.equals(mapArea)) {
                return true;
            }
        }
        return false;
    }

    private List getCityArea(int cityId) {
        List<Areas> cityAreaList = new ArrayList<>();
        List<Areas> areasAll = DatabaseManager.getInstance().getAllOfClass(Areas.class);
        for (Areas area : areasAll) {
            if (cityId == area.areaCityId) {
                cityAreaList.add(area);
            }
        }


        return cityAreaList;
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

    @Override
    public void onResume() {
        registerReceiver(mReceiverLocation, new IntentFilter("data_action"));
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiverLocation);
        super.onPause();
    }

    private BroadcastReceiver mReceiverLocation = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String string = (String) intent.getSerializableExtra("onConnectedGoogleApi");
                Log.e("onConnectedGoogleApi", string);
                if (string.equals("connected") && isConnected) {
                    getUserCurrentLatLong();
                    isConnected = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Determine whether to turn on GPS
     */
    private boolean isGPSOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;
        hMap.setMyLocationEnabled(false);
        if (isGPSOpen(this)) {
            hMap.setMyLocationEnabled(true);
            hMap.getUiSettings().setMyLocationButtonEnabled(true);
            getMyLastLocationAnimation();
        } else {
            hMap.setMyLocationEnabled(false);
            hMap.getUiSettings().setMyLocationButtonEnabled(false);
            handleLocation();
        }


        if (userCurrentLatlng != null) {
            moveToLocationH(new com.huawei.hms.maps.model.LatLng(userCurrentLatlng.latitude, userCurrentLatlng.longitude));
        }


        hMap.setOnCameraIdleListener(new HuaweiMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                tvAreas.setVisibility(View.GONE);
                isAreaSlected = false;
            }

        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        if (userCurrentLatlng != null) {
            moveToLocation(userCurrentLatlng);
        }
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
            public boolean onMyLocationButtonClick()
            {
                //TODO: Any custom actions
                Location location = getLocationByGoogleClientApi.getMyLocation();
                if (location != null) {
                    LatLng currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                    userCurrentLatlng = currentLatLong;
                    moveToLocation(userCurrentLatlng);
                }
                return false;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                tvAreas.setVisibility(View.GONE);
                isAreaSlected = false;
            }

        });


    }

    private void moveToLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.clear();
        getAddressRequest(currentLocation.latitude,currentLocation.longitude);

    }

    private void moveToLocationH(com.huawei.hms.maps.model.LatLng currentLocation) {
        hMap.moveCamera(com.huawei.hms.maps.CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        hMap.animateCamera(com.huawei.hms.maps.CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        hMap.animateCamera(com.huawei.hms.maps.CameraUpdateFactory.zoomTo(15), 2000, null);
        hMap.clear();
        getAddressRequest(currentLocation.latitude,currentLocation.longitude);

    }

    private void setTvgetMarkerLocation(){

        if (mMap!=null) {
            double CameraLat = mMap.getCameraPosition().target.latitude;
            double CameraLong = mMap.getCameraPosition().target.longitude;
            Log.i("lattlong", String.valueOf(CameraLat + "," + CameraLong));
            if (CameraLat != 0.0) {
                getAddressRequest(CameraLat, CameraLong);
            }

        }
    }

    private void setTvgetMarkerLocationH(){

        if (hMap!=null) {
            double CameraLat = hMap.getCameraPosition().target.latitude;
            double CameraLong = hMap.getCameraPosition().target.longitude;
            Log.i("lattlong", String.valueOf(CameraLat + "," + CameraLong));
            if (CameraLat != 0.0) {
                getAddressRequest(CameraLat, CameraLong);
            }

        }
    }


    private void addAddressRequest(){
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        ArrayList<Address> addressList = (ArrayList<Address>) DatabaseManager.getInstance().getAllOfClass(Address.class);
        boolean addresscheck = false;

        if (addressList != null && addressList.size() > 0) {
                for (int i = 0; i < addressList.size(); i++) {
                    if (editAddressTitle.getText().toString().equals(addressList.get(i).addDetail)) {
                        addresscheck = true;
                        Address.changeDefaultAddress(addressList.get(i).addId);
                        UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject("")+"");
                        broadcastData();
                        finish();
                        break;
                    }
                }
            }

            if (!addresscheck || addressList.size()<1){
                RegAddress regAddress = null;

                if(HmsGmsUtil.isOnlyHms(this)){
                    regAddress = new RegAddress(user.userId,
                            "" + userCurrentLatlng.latitude,
                            "" + userCurrentLatlng.longitude,
                            "" + areaId,
                            "" + cityId,
                            "" + DateFormat.format("dd_MMM_yyyy", new Date()),
                            editAddressTitle.getText().toString(), "1", "0", "0");
                }else {
                    regAddress = new RegAddress(user.userId,
                            "" + userCurrentLatlng.latitude,
                            "" + userCurrentLatlng.longitude,
                            "" + areaId,
                            "" + cityId,
                            "" + DateFormat.format("dd_MMM_yyyy", new Date()),
                            editAddressTitle.getText().toString(), "1", "0", "0");

                }

                Log.e("Requestadddddddd", new Gson().toJson(regAddress));

                showProgress(true);
                APIManager.getInstance().addAddress(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        if (z) {
                            showProgress(false);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                CoreManager.getInstance().removeUserAddresses();
                                if (Address.saveAddresses(jsonObject.getJSONArray("rows"))) {

                                    ArrayList<Address> addressList = (ArrayList<Address>) DatabaseManager.getInstance().getAllOfClass(Address.class);

                                    if (addressList != null && addressList.size() > 0) {
                                        for (int i = 0; i < addressList.size(); i++) {
                                            if (editAddressTitle.getText().toString().equals(addressList.get(i).addDetail)) {
                                               // Address.changeDefaultAddress(addressList.get(i).addId);
                                                Log.d("adddddddid",addressList.get(i).addId+"");
                                                UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject(addressList.get(i).addId)+"");
                                                break;
                                            }
                                        }
                                    }

                                    Map<String, Object> eventValue = new HashMap<String, Object>();
                                    eventValue.put("Address Details",""+""+creatJsonObject(""));
//                                    CleverTapAPI.getDefaultInstance(AddAddressActivity.this).pushEvent("Add Address:",eventValue);

                                }
                                broadcastData();
                                finish();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, regAddress);
            }
    }


    private JSONObject creatJsonObject(String addId) {
        JSONObject jsonObject = new JSONObject();

        try {
           /* if (HmsGmsUtil.isOnlyHms(this)&&userCurrentLatlngH!=null){
                jsonObject.put("lat", userCurrentLatlngH.latitude+"");
                jsonObject.put("lng", userCurrentLatlngH.longitude+"");
            }else {
                if (userCurrentLatlng!=null){
                jsonObject.put("lat", userCurrentLatlng.latitude+"");
                jsonObject.put("lng", userCurrentLatlng.longitude+"");
                }else {
                    jsonObject.put("lat", "0.00");
                    jsonObject.put("lng", "0.00");
                }
            }*/

            if (userCurrentLatlng!=null){
                    jsonObject.put("lat", userCurrentLatlng.latitude+"");
                    jsonObject.put("lng", userCurrentLatlng.longitude+"");
                }else {
                    jsonObject.put("lat", "0.00");
                    jsonObject.put("lng", "0.00");
                }



            jsonObject.put("areaId", ""+areaId);
            jsonObject.put("cityId", ""+cityId);
            jsonObject.put("addressName", "Address: " +  DateFormat.format("dd_MMM_yyyy", new Date()));
            jsonObject.put("details", ""+tvAddress.getText().toString().trim());
            jsonObject.put("address_id", addId);
            jsonObject.put("add_type", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void broadcastData() {
        Constants.checkaddress = true;
        Intent intent = new Intent("data_action_add_change");
        intent.putExtra("onAddressChange", "" + "changed");
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvSaveAddress:
                if (isAreaSlected) {
                    User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                    if (user!=null){
                        addAddressRequest();
                    }else {
                        UserSession.getInstance().setSaveHomeAddressObject(creatJsonObject("") + "");
                        broadcastData();
                        finish();
                    }
                }else{
                    isProceed = true;
                    if (HmsGmsUtil.isOnlyHms(this)){
                        setTvgetMarkerLocationH();
                    }else {
                        setTvgetMarkerLocation();
                    }

                }
                break;

            case R.id.imageViewSearch:
                openAutoCompleteIntent();
                break;
                case R.id.tvAddress:
                openAutoCompleteIntent();
                break;

                case R.id.onMapSearch:
                openAutoCompleteIntent();
                break;
            case R.id.tvAreas:
                if (cityId != 0) {
                    List<Areas> areas = getCityArea(cityId);
                    alertChooseAreas(areas);
                } else {
                    Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show();
                }
                break;

                case R.id.tv_availableCities:
                    alertAvailableCities();
                break;
        }
    }

    public void alertChooseAreas(List<Areas> chooseItems) {
        areaFilteredList = new ArrayList<>();
        areaFilteredList = chooseItems;
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setContentView(R.layout.alert_dialog_select_city_list);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText editSearch = dialog.findViewById(R.id.editSearch);
        tvTitle.setText("" + getResources().getString(R.string.choose_area));
        ListView listViewChooseItems = dialog.findViewById(R.id.listViewChooseItems);
        final SelectAreaListAdapter selectCityListAdapter = new SelectAreaListAdapter(this, areaFilteredList);
        listViewChooseItems.setAdapter(selectCityListAdapter);

        listViewChooseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppController.setLocale()){
                tvAreas.setText("" + areaFilteredList.get(position).areaTitleAr);
                }else {
                    tvAreas.setText("" + areaFilteredList.get(position).areaTitleEn);
                }
                areaId = areaFilteredList.get(position).areaId;
                isAreaSlected = true;
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                areaFilteredList = new ArrayList<>();
                areaFilteredList = selectCityListAdapter.filter(editSearch.getText().toString().toLowerCase(Locale.getDefault()));
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }

    public void alertAvailableCities() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setContentView(R.layout.alert_dialog_available_cities);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tv_availableCities = dialog.findViewById(R.id.tv_availableCities);
        tv_availableCities.setMovementMethod(new ScrollingMovementMethod());

        List<Cities> cities = DatabaseManager.getInstance().getAllOfClass(Cities.class);
        if(AppController.setLocale())
        {
            tvTitle.setText("خدماتنا متوفرة فقط في مدينة"+"\n");
        }else {
            tvTitle.setText("Our services are only available in these cities"+"\n");
        }

        for (int j = 0; j < cities.size(); j++) {
            if(AppController.setLocale())
            {
                tv_availableCities.append(cities.get(j).cityTitleAr+",\n");
            }else {
                tv_availableCities.append(cities.get(j).cityTitleEn+",\n");
            }
        }


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("Tag", "Place: " + place.getName() + ", " + place.getId());
                //getAddressRequest(place.getLatLng());

                if (HmsGmsUtil.isOnlyHms(this)) {
                    userCurrentLatlng = place.getLatLng();
                    moveToLocationH(new com.huawei.hms.maps.model.LatLng(place.getLatLng().latitude, place.getLatLng().longitude));
                } else {
                    moveToLocation(place.getLatLng());
                    userCurrentLatlng = place.getLatLng();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (HmsGmsUtil.isOnlyHms(this)) {
                handleLocation();
            } else {
                getUserCurrentLatLong();

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (UserSession.getInstance().getSaveAddressObject().equals("")){
            Toast.makeText(AddAddressActivity.this, ""+getResources().getString(R.string._string_enter_address_to_load), Toast.LENGTH_SHORT).show();

        }else {
            super.onBackPressed();
        }


    }

    private void alertDialogMessage(String message) {
        final Dialog confirmDialog = new Dialog(AddAddressActivity.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.setContentView(R.layout.dialog_place_order_error);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        tv_message.setText(""+message);
        TextView btn_ok=confirmDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                //finish();
            }
        });

        confirmDialog.setCancelable(false);
        confirmDialog.show();
    }


    public void handleLocation() {
        settingsClientH = LocationServices.getSettingsClient(this);
        locationRequestH = new LocationRequest();
        locationRequestH.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequestH.setInterval(UPDATE_INTERVAL);
        locationRequestH.setFastestInterval(FASTEST_INTERVAL);

        if (mLocationCallbackH == null) {
            mLocationCallbackH = new com.huawei.hms.location.LocationCallback() {
                @Override
                public void onLocationResult(com.huawei.hms.location.LocationResult locationResult) {
                    if (locationResult != null) {
                        List<Location> locations = locationResult.getLocations();
                        if (!locations.isEmpty()) {
                            hMap.setMyLocationEnabled(true);
                            hMap.getUiSettings().setMyLocationButtonEnabled(true);
                            for (Location location : locations) {
                                userCurrentLatlng = new LatLng(location.getLatitude(),location.getLongitude());
                                Log.i("TAG", "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.getLongitude() + "," + location.getLatitude() + "," + location.getAccuracy());
                            }
                        }
                    }
                }

                @Override
                public void onLocationAvailability(com.huawei.hms.location.LocationAvailability locationAvailability) {
                    if (locationAvailability != null) {
                        boolean flag = locationAvailability.isLocationAvailable();
                        Log.i("TAG", "onLocationAvailability isLocationAvailable:" + flag);
                    }
                }
            };
        }

        updateLocation();
        fusedLocationClient.requestLocationUpdates(locationRequestH, mLocationCallbackH, Looper.getMainLooper());
    }

    public void updateLocation() {
        try {
            com.huawei.hms.location.LocationSettingsRequest.Builder builder = new com.huawei.hms.location.LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequestH);
            com.huawei.hms.location.LocationSettingsRequest locationSettingsRequest = builder.build();
            // check devices settings before request location updates.
            settingsClientH.checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(new OnSuccessListener<com.huawei.hms.location.LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(com.huawei.hms.location.LocationSettingsResponse locationSettingsResponse) {
                            Log.i("TAG", "check location settings success");
                            getMyLastLocationAnimation();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.e("TAG", "checkLocationSetting onFailure:" + e.getMessage());
                            int statusCode = ((com.huawei.hms.common.ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        com.huawei.hms.common.ResolvableApiException rae = (com.huawei.hms.common.ResolvableApiException) e;
                                        rae.startResolutionForResult(AddAddressActivity.this, 0);
                                    } catch (IntentSender.SendIntentException sie) {
                                        sie.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    });

        } catch (Exception e) {
            Log.e("TAG", "requestLocationUpdatesWithCallback exception:" + e.getMessage());
        }


//        fusedLocationClient
//                .requestLocationUpdates(locationRequestH, mLocationCallbackH, Looper.getMainLooper())
//                .addOnSuccessListener(new com.huawei.hmf.tasks.OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.i(TAG, "onActivityResult requestLocationUpdatesWithCallback onSuccess");
//                        getMyLastLocationAnimation();
//                    }
//                })
//                .addOnFailureListener(new com.huawei.hmf.tasks.OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        Log.e(TAG,
//                                "onActivityResult requestLocationUpdatesWithCallback onFailure:" + e.getMessage());
//                    }
//                })
//        ;
    }

    public void getMyLastLocationAnimation() {

        Task<Location> task = fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            // Logic to handle location object
                            Log.i("TAG", "location is null");
                            //request location updates
                            handleLocation();
                        } else {
                            Log.i("TAG", "location found");
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                              userCurrentLatlng = latLng;
                              if (userCurrentLatlng!=null)
                            moveToLocationH(new com.huawei.hms.maps.model.LatLng(latLng.latitude,latLng.longitude));
                        }
                        //Processing logic of the Location object.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //Exception handling logic.
                    }
                });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onDestroy() {
        if (HmsGmsUtil.isOnlyHms(this)) {
            fusedLocationClient.removeLocationUpdates(mLocationCallbackH).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i("TAG", "removeLocationUpdatesWithCallbackH onSuccess");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e("TAG", "removeLocationUpdatesWithCallback onFailure:" + e.getMessage());
                }
            });
        }
        super.onDestroy();
    }

}