package com.app.aljazierah.utils;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.util.Log;

import com.app.aljazierah.adapter.PagesRequest;
import com.app.aljazierah.object.Cart_Params;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.PlaceOrder;
import com.app.aljazierah.object.RemoveReceiptModel;
import com.app.aljazierah.object.SearchWord;
import com.app.aljazierah.object.ServiceSlotsModel;
import com.app.aljazierah.object.hyperpayPayment.GetChectoutId;
import com.app.aljazierah.object.request.AddComplaint;
import com.app.aljazierah.object.request.AddToWishStockAlertRequest;
import com.app.aljazierah.object.request.AfterSalesServicesMyReturnReq;
import com.app.aljazierah.object.request.AfterSalesServicesReq;
import com.app.aljazierah.object.request.Area;
import com.app.aljazierah.object.request.CancelOrderRequest;
import com.app.aljazierah.object.request.Channel;
import com.app.aljazierah.object.request.Check;
import com.app.aljazierah.object.request.CheckOpenOrder;
import com.app.aljazierah.object.request.CheckOut;
import com.app.aljazierah.object.request.CheckOutWithPayment;
import com.app.aljazierah.object.request.CheckOutWithSadadID;
import com.app.aljazierah.object.request.CheckStcRequest;
import com.app.aljazierah.object.request.CheckUserOTP;
import com.app.aljazierah.object.request.CompanySettings;
import com.app.aljazierah.object.request.ComplaintReply;
import com.app.aljazierah.object.request.DeleteAddress;
import com.app.aljazierah.object.request.FavouriteRequest;
import com.app.aljazierah.object.request.ForgetPass;
import com.app.aljazierah.object.request.GetComplaintDetails;
import com.app.aljazierah.object.request.Language;
import com.app.aljazierah.object.request.LogOut;
import com.app.aljazierah.object.request.Login;
import com.app.aljazierah.object.request.LoginRegister;
import com.app.aljazierah.object.request.Maintenences;
import com.app.aljazierah.object.request.OrderRequest;
import com.app.aljazierah.object.request.ProductsRequest;
import com.app.aljazierah.object.request.PromocodeRequest;
import com.app.aljazierah.object.request.RegAddress;
import com.app.aljazierah.object.request.Register;
import com.app.aljazierah.object.RetingRequest.RetingRequest;
import com.app.aljazierah.object.request.TimeSlots;
import com.app.aljazierah.object.request.UpdateProfile;
import com.app.aljazierah.object.request.UpdateProfileWithPass;
import com.app.aljazierah.object.request.UpdateUsername;
import com.app.aljazierah.object.request.VerifyEmailOTP;
import com.app.aljazierah.object.request.WalletRequest;
import com.roam.appdatabase.DatabaseManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.aljazierah.utils.Constants.BASE_URL;

public class APIManager {

    private static final String TAG = "APIManager";
    private static final APIManager instance = new APIManager();
    private Retrofit retrofit;


    public interface Callback {
        void onResult(boolean z, String response);
    }

    public static APIManager getInstance() {
        return instance;
    }

    private APIManager() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient.addNetworkInterceptor(new AddHeaderInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }


    public void refreshToken(final Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.refreshToken(new Check());
        sendRequest(callback, result);
    }

    public void login(final Callback callback, Login login) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.login(login);
        sendRequest(callback, result);
    }

    public void loginRegister(final Callback callback, LoginRegister loginRegister) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.loginRegister(loginRegister);
        sendRequest(callback, result);
    }

    public void afterSalesServices(final Callback callback, AfterSalesServicesReq afterSalesServicesReq) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.afterSaleServicesReq(afterSalesServicesReq);
        sendRequest(callback, result);
    }


    public void afterSalesServicesMyReturn(final Callback callback, AfterSalesServicesMyReturnReq afterSalesServicesReq) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.afterSaleServicesMyReturnReq(afterSalesServicesReq);
        sendRequest(callback, result);
    }

    public void checkUserOTP(final Callback callback, CheckUserOTP checkUserOTP) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkUserOTP(checkUserOTP);
        sendRequest(callback, result);
    }

    public void verifyEmailedOTP(final Callback callback, VerifyEmailOTP verifyEmailOTP) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.verifyEmailOTP(verifyEmailOTP);
        sendRequest(callback, result);
    }

    public void updateUsername(final Callback callback, UpdateUsername updateUsername) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.updateUsername(updateUsername);
        sendRequest(callback, result);
    }

    public void signUp(final Callback callback, Register register, String lang) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.signUp(register, lang);
        sendRequest(callback, result);
    }
    public void makeFavourite(final Callback callback, FavouriteRequest favouriteRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.makeOrderFavourite(favouriteRequest);
        sendRequest(callback, result);
    }
    public void addTowishListStockAlert(final Callback callback, AddToWishStockAlertRequest addToWishStockAlertRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.addTowishListStockAlert(addToWishStockAlertRequest);
        sendRequest(callback, result);
    }

    public void saveOrderRating(final Callback callback, RetingRequest retingRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.saveOrderRating(retingRequest);
        sendRequest(callback, result);
    }

    public void getTips(Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getTips();
        sendRequest(callback, result);

    }

    public void getCompanySettings(final Callback callback, CompanySettings copmanySettings, Activity activity) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getCompanySetting(copmanySettings);
        sendRequest(callback, result);
    }

   public void setLogOut(final Callback callback, LogOut logOut, Activity activity) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.setLogOut(logOut);
        sendRequest(callback, result);
    }

    public void getPromoCode(final Callback callback, PromocodeRequest promocodeRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getPromoCode(promocodeRequest);
        sendRequest(callback, result);
    }

    public void is_stc_tamayouz_user(final Callback callback, CheckStcRequest promocodeRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.is_stc_tamayouz_user(promocodeRequest);
        sendRequest(callback, result);
    }

    public void validate_stc_tamayouz_otp(final Callback callback, PromocodeRequest promocodeRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.validate_stc_tamayouz_otp(promocodeRequest);
        sendRequest(callback, result);
    }
    public void reversal_stc_tamayouz_discount (final Callback callback, CheckStcRequest promocodeRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.reversal_stc_tamayouz_discount(promocodeRequest);
        sendRequest(callback, result);
    }
    public void get_wallet_details (final Callback callback, WalletRequest placeOrder) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.get_wallet_details (placeOrder);
        sendRequest(callback, result);
    }

    public void get_list_complaints(final Callback callback, WalletRequest placeOrder) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.get_list_complaints (placeOrder);
        sendRequest(callback, result);
    }

    public void save_temp_order_payment(final Callback callback, CheckOutWithSadadID placeOrder) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.save_temp_order_payment (placeOrder);
        sendRequest(callback, result);
    }

    public void load_complaint_data(final Callback callback, WalletRequest placeOrder) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.load_complaint_data (placeOrder);
        sendRequest(callback, result);
    }

    public void add_complaint_reply(final Callback callback, ComplaintReply placeOrder) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.add_complaint_reply (placeOrder);
        sendRequest(callback, result);
    }

    public void add_complaint(final Callback callback, AddComplaint addComplaint) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.add_complaint (addComplaint);
        sendRequest(callback, result);
    }
    public void complaint_detail(final Callback callback, GetComplaintDetails addComplaint) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.complaint_detail (addComplaint);
        sendRequest(callback, result);
    }

    public void placeOrder(final Callback callback, PlaceOrder placeOrder,Activity activity) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.placeorder(placeOrder);
        sendRequest(callback, result);

    }
    public void getPopupPromo(Callback callback, Language lang) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getPopupPromo(lang);
        sendRequest(callback, result);
    }

    public void getOrders(final Callback callback, OrderRequest orderRequest) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getOrders(orderRequest);
        sendRequest(callback, result);
    }

    public void getOrderDetails(final Callback callback, OrderRequest orderRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getOrdersDetail(orderRequest);
        sendRequest(callback, result);
    }
    public void getCartParemeters(final Callback callback, Cart_Params userID){
        APIService service=retrofit.create(APIService.class);
        Call<ResponseBody> result=service.getCartParameters(userID);
        sendRequest(callback,result);

    }
    public void getCities(final Callback callback) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getCities();
        sendRequest(callback, result);
    }

    public void getSubChannels(final Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Channel channel = new Channel();
        channel.setTypeId("1");
        Call<ResponseBody> result = service.getSubChannels(channel);
        sendRequest(callback, result);
    }

    public void getChannels(final Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Channel channel = new Channel();
        channel.setTypeId("1");
        Call<ResponseBody> result = service.getChannels(channel);
        sendRequest(callback, result);
    }

    public void getCityArea(final Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Area area = new Area();
        Call<ResponseBody> result = service.getCityArea();
        sendRequest(callback, result);
    }
    public void getProducts(final Callback callback,int area_id,int city_id,String custome_id,String add_type) {

        APIService service = retrofit.create(APIService.class);
        ProductsRequest product = new ProductsRequest(area_id,city_id, custome_id,add_type);
        Call<ResponseBody> result = service.getProducts(product);
        sendRequest(callback, result);
    }
    public void getPages(final Callback callback, String page_id) {

        APIService service = retrofit.create(APIService.class);
        PagesRequest pagesRequest = new PagesRequest(page_id);
        Call<ResponseBody> result = service.getPages(pagesRequest);
        sendRequest(callback, result);
    }
    public void addAddress(final Callback callback, RegAddress regAddress) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.addAddress(regAddress);
        sendRequest(callback, result);
    }

    public void updateProfile(final Callback callback, UpdateProfile updateProfile) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.updateProfile(updateProfile);
        sendRequest(callback, result);
    }

    public void updateProfileWithPass(final Callback callback, UpdateProfileWithPass updateProfile) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.updateProfileWithPass(updateProfile);
        sendRequest(callback, result);
    }

    public void resendCode(final Callback callback, String userId) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.resend_code(userId);
        sendRequest(callback, result);
    }

    public void verifyCode(final Callback callback, String userId, String code, String language) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.verifyAccount(userId, code, language);
        sendRequest(callback, result);
    }

    public void deleteAddress(final Callback callback, DeleteAddress deleteAddress) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.deleteAddress(deleteAddress);
        sendRequest(callback, result);
    }

    public void sendForgetPasswordEmail(final Callback callback, ForgetPass forgetPass) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.sendForgetPassEmail(forgetPass);
        sendRequest(callback, result);
    }

    public void verifyCodeForForgetPassword(final Callback callback, ForgetPass forgetPass) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.verifyCodeForgetPassEmail(forgetPass);
        sendRequest(callback, result);
    }

    public void resetPassword(final Callback callback, ForgetPass forgetPass) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.resetPassword(forgetPass);
        sendRequest(callback, result);
    }

    public void placeOrderByHand(final Callback callback, CheckOut checkOut) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkOut(checkOut);
        sendRequest(callback, result);
    }


    public void placeOrderByCreditCard(Callback callback, CheckOutWithPayment checkOut) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkOutWithCard(checkOut);
        sendRequest(callback, result);
    }

    public void placeOrderBySadadId(Callback callback, CheckOutWithSadadID checkOut) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkOutWithSadadId(checkOut);
        sendRequest(callback, result);
    }

    public void getPaymentStatus(final Callback callback, CheckOutWithPayment checkOut) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkOutWithCard(checkOut);
        sendRequest(callback, result);
    }

    public void getCanceOrderReasons(final Callback callback) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getCancelOrderReason();
        sendRequest(callback, result);
    }

    public void getStoresRequest(final Callback callback) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getStores();
        sendRequest(callback, result);
    }

    public void postCancelOrderReason(final Callback callback, CancelOrderRequest cancelOrderRequest) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.postCancelOrderReason(cancelOrderRequest);
        sendRequest(callback, result);
    }

    public void getTimeSlots(final Callback callback, TimeSlots timeSlots) {

        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.getTimeSlots(timeSlots);
        sendRequest(callback, result);
    }

    public void checkOpenOrders(Callback callback, CheckOpenOrder checkOpenOrder) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.checkOpenOrders(checkOpenOrder);
        sendRequest(callback, result);
    }

    public void getAddressByLatLng(Callback callback, String location,String language ,Activity activity) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        APIService service = retro.create(APIService.class);
        Call<ResponseBody> result = service.getAddressFromGoogle(location,true, language, Constants.GOOGLE_API_Key);
        sendRequest(callback, result);
    }



    public void getCheckoutId(final Callback callback, GetChectoutId getChectoutId, Activity activity){
        APIService service=retrofit.create(APIService.class);
        Call<ResponseBody> result=service.getCheckoutId(getChectoutId);
        sendRequest(callback,result);
    }


    public void gethyperPamentStatus(Callback callback,String url, Activity activity) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(""+url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIService service = retro.create(APIService.class);
        Call<ResponseBody> result = service.getPaymentStatus();
        sendRequest(callback, result);
    }


    public void getUserPublicIp(Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        String ipUrl = "";
        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (companySetting.sadadDelivery.equals("0")){
            ipUrl = "https://api.ipify.org/";
        }
        else if (companySetting.sadadDelivery.equals("1")){
            ipUrl = "https://checkip.amazonaws.com/";
        }else {
            ipUrl = "https://icanhazip.com/";
        }


        Retrofit retro = new Retrofit.Builder()
                .baseUrl(""+ipUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIService service = retro.create(APIService.class);
        Call<ResponseBody> result = service.getPublicIP();
        sendRequest(callback, result);
    }

    public void word_search(final Callback callback, SearchWord searchWord) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.word_search(searchWord);
        sendRequest(callback, result);
    }

    public void getMaintenencesRequest(final Callback callback, Maintenences searchWord) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.maintenences(searchWord);
        sendRequest(callback, result);
    }

    public void getReturnDataRequest(final Callback callback, Maintenences searchWord) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.returnData(searchWord);
        sendRequest(callback, result);
    }

    public void getRemoveReceiptReq(final Callback callback, RemoveReceiptModel removeReceiptModel) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.remove_receipt(removeReceiptModel);
        sendRequest(callback, result);
    }

    public void getServiceSlotsReq (final Callback callback, ServiceSlotsModel serviceSlotsModel) {
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> result = service.service_slotsReq(serviceSlotsModel);
        sendRequest(callback, result);
    }

    private void sendRequest(final Callback callback, Call<ResponseBody> result) {

        result.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                Log.e("response :", "...." + response.toString());
                Log.e("apiurl :", "...." + response.raw().request().url());

                if (response.body() != null) {
                    try {
                        if (callback != null) {
                            String serverResponse = response.body().string();
                            logLargeString(serverResponse);
                            callback.onResult(true, "" + serverResponse);
                        }
                    } catch (IOException e) {
                        Log.e("Error", "" +e.toString());

                        e.printStackTrace();
                        if (callback != null)
                            callback.onResult(false, "Error");
                    }
                } else {
                    if (callback != null)
                        callback.onResult(false, "" + response.errorBody().toString());
                    Log.e("Error", "" + response.errorBody().contentType().toString().toString());

                           try {
                    Log.e("Response", "...." + response.errorBody().string());
                } catch (IOException e) {

                    e.printStackTrace();
                }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                t.fillInStackTrace();
                if (callback != null) {
                    callback.onResult(false, "" + t.getMessage());
                }
                Log.e("response :", "...." + t.toString());

            }

        });


    }



    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.i("Response", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.i("Response", ""+str); // continuation
        }
    }


}