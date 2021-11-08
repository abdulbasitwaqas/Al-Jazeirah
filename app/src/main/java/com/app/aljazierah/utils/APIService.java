package com.app.aljazierah.utils;

import com.app.aljazierah.adapter.PagesRequest;
import com.app.aljazierah.object.AddReceiptModel;
import com.app.aljazierah.object.Cart_Params;
import com.app.aljazierah.object.PlaceOrder;
import com.app.aljazierah.object.RemoveReceiptModel;
import com.app.aljazierah.object.SearchWord;
import com.app.aljazierah.object.ServiceSlotsModel;
import com.app.aljazierah.object.hyperpayPayment.GetChectoutId;
import com.app.aljazierah.object.request.AddComplaint;
import com.app.aljazierah.object.request.AddToWishStockAlertRequest;
import com.app.aljazierah.object.request.AfterSalesServicesMyReturnReq;
import com.app.aljazierah.object.request.AfterSalesServicesReq;
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
import com.app.aljazierah.object.request.PaymentStatus;
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

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.app.aljazierah.utils.Constants.ADD_ORDERS_WITH_PAYMENT;
import static com.app.aljazierah.utils.Constants.AFTERSALESERVICES;
import static com.app.aljazierah.utils.Constants.AFTERSALESERVICES_RETURN;
import static com.app.aljazierah.utils.Constants.Add_To_Wish_Stock_alert;
import static com.app.aljazierah.utils.Constants.CANCEL_ORDER;
import static com.app.aljazierah.utils.Constants.CANCEL_ORDER_REASON;
import static com.app.aljazierah.utils.Constants.CHANNELS;
import static com.app.aljazierah.utils.Constants.CHECKUSEROTP;
import static com.app.aljazierah.utils.Constants.CHECK_OPEN_ORDER;
import static com.app.aljazierah.utils.Constants.CITIES;
import static com.app.aljazierah.utils.Constants.CITY_AREA;
import static com.app.aljazierah.utils.Constants.COMPANY_SETTINGS;
import static com.app.aljazierah.utils.Constants.DELETE_ADDRESS;
import static com.app.aljazierah.utils.Constants.FORGET_PASSWORD;
import static com.app.aljazierah.utils.Constants.FORGET_PASSWORD_VERIFY_CODE;
import static com.app.aljazierah.utils.Constants.GET_ADDRESS_GOOGLE;
import static com.app.aljazierah.utils.Constants.GET_CART_PARAMETERS;
import static com.app.aljazierah.utils.Constants.GET_STORES;
import static com.app.aljazierah.utils.Constants.LOGIN;
import static com.app.aljazierah.utils.Constants.LOGINREGISTER;
import static com.app.aljazierah.utils.Constants.LOGOUT;
import static com.app.aljazierah.utils.Constants.MAKE_ORDER_FAV;
import static com.app.aljazierah.utils.Constants.NEW_ADDRESS;
import static com.app.aljazierah.utils.Constants.ORDERS;
import static com.app.aljazierah.utils.Constants.ORDERS_DETAIL;
import static com.app.aljazierah.utils.Constants.PAGES_URL;
import static com.app.aljazierah.utils.Constants.PAYMENT;
import static com.app.aljazierah.utils.Constants.PAYMENT_STATUS;
import static com.app.aljazierah.utils.Constants.POPUP_PROMO;
import static com.app.aljazierah.utils.Constants.PROCESS_PY;
import static com.app.aljazierah.utils.Constants.PRODUCTSBYLOCATION;
import static com.app.aljazierah.utils.Constants.REFRESH_TOKEN;
import static com.app.aljazierah.utils.Constants.RESEND_CODE;
import static com.app.aljazierah.utils.Constants.RESET_PASSWORD;
import static com.app.aljazierah.utils.Constants.SAVE_ORDER_RATING;
import static com.app.aljazierah.utils.Constants.SIGNUP;
import static com.app.aljazierah.utils.Constants.SUB_CHANNELS;
import static com.app.aljazierah.utils.Constants.TIME_SLOTS;
import static com.app.aljazierah.utils.Constants.TIPS;
import static com.app.aljazierah.utils.Constants.UPDATEUSERNAME;
import static com.app.aljazierah.utils.Constants.UPDATE_PROFILE;
import static com.app.aljazierah.utils.Constants.VERIFY_ACCOUNT;

import static com.app.aljazierah.utils.Constants.VerifyEmail;
import static com.app.aljazierah.utils.Constants.add_complaint;
import static com.app.aljazierah.utils.Constants.add_complaint_reply;
import static com.app.aljazierah.utils.Constants.add_receipt;
import static com.app.aljazierah.utils.Constants.after_sale_2;
import static com.app.aljazierah.utils.Constants.complaint_detail;
import static com.app.aljazierah.utils.Constants.get_promo_discount;
import static com.app.aljazierah.utils.Constants.get_wallet_details;

import static com.app.aljazierah.utils.Constants.is_stc_tamayouz_user;
import static com.app.aljazierah.utils.Constants.list_complaints;
import static com.app.aljazierah.utils.Constants.load_complaint_data;
import static com.app.aljazierah.utils.Constants.maintenences;
import static com.app.aljazierah.utils.Constants.remove_receipt;
import static com.app.aljazierah.utils.Constants.returnData;
import static com.app.aljazierah.utils.Constants.reversal_stc_tamayouz_discount;
import static com.app.aljazierah.utils.Constants.save_order_new;
import static com.app.aljazierah.utils.Constants.save_temp_order_payment;
import static com.app.aljazierah.utils.Constants.service_slots;
import static com.app.aljazierah.utils.Constants.validate_stc_tamayouz_otp;
import static com.app.aljazierah.utils.Constants.word_search;

/**
 * Created by Mustanser Iqbal on 11/24/2017.
 */

public interface APIService {

    @POST(LOGIN)
//    @POST("")
    Call<ResponseBody> login(@Body Login login);

    @POST(LOGINREGISTER)
    Call<ResponseBody> loginRegister(@Body LoginRegister loginRegister);



    @POST(AFTERSALESERVICES)
    Call<ResponseBody> afterSaleServicesReq(@Body AfterSalesServicesReq afterSalesServicesReq);

    @POST(AFTERSALESERVICES_RETURN)
    Call<ResponseBody> afterSaleServicesMyReturnReq(@Body AfterSalesServicesMyReturnReq afterSalesServicesReq);

    @POST(CHECKUSEROTP)
    Call<ResponseBody> checkUserOTP(@Body CheckUserOTP checkUserOTP);

    @POST(VerifyEmail)
    Call<ResponseBody> verifyEmailOTP(@Body VerifyEmailOTP verifyEmailOTP);

    @POST(UPDATEUSERNAME)
    Call<ResponseBody> updateUsername(@Body UpdateUsername updateUsername);

    @POST(FORGET_PASSWORD)
    Call<ResponseBody> sendForgetPassEmail(@Body ForgetPass login);

    @POST(FORGET_PASSWORD_VERIFY_CODE)
    Call<ResponseBody> verifyCodeForgetPassEmail(@Body ForgetPass login);

    /*@POST(FORGET_PASSWORD)
    Call<ResponseBody> sendForgetPassEmail(@Body Login login);*/

    @POST(CITIES)
    Call<ResponseBody> getCities();

    @POST(CITY_AREA)
    Call<ResponseBody> getCityArea();

    @POST(CHANNELS)
    Call<ResponseBody> getChannels(@Body Channel channel);

    @POST(SUB_CHANNELS)
    Call<ResponseBody> getSubChannels(@Body Channel channel);

    @POST(SIGNUP + "/{language}")
    Call<ResponseBody> signUp(@Body Register register, @Path("language") String lang);

    @POST(TIPS)
    Call<ResponseBody> getTips();

    @POST(COMPANY_SETTINGS)
    Call<ResponseBody> getCompanySetting(@Body CompanySettings channel);
   @POST(LOGOUT)
    Call<ResponseBody> setLogOut(@Body LogOut logOut);


    @POST(get_promo_discount)
    Call<ResponseBody> getPromoCode(@Body PromocodeRequest channel);

    @POST(is_stc_tamayouz_user)
    Call<ResponseBody> is_stc_tamayouz_user(@Body CheckStcRequest channel);

    @POST(reversal_stc_tamayouz_discount)
    Call<ResponseBody> reversal_stc_tamayouz_discount(@Body CheckStcRequest channel);

    @POST(validate_stc_tamayouz_otp)
    Call<ResponseBody> validate_stc_tamayouz_otp(@Body PromocodeRequest channel);


    @POST(save_order_new)
    Call<ResponseBody> placeorder(@Body PlaceOrder placeOrder);

    @POST(get_wallet_details )
    Call<ResponseBody> get_wallet_details (@Body WalletRequest placeOrder);

    @POST(list_complaints)
    Call<ResponseBody> get_list_complaints(@Body WalletRequest placeOrder);

    @POST(save_temp_order_payment)
    Call<ResponseBody> save_temp_order_payment(@Body CheckOutWithSadadID placeOrder);

    @POST(load_complaint_data)
    Call<ResponseBody> load_complaint_data(@Body WalletRequest placeOrder);

    @POST(add_complaint)
    Call<ResponseBody> add_complaint(@Body AddComplaint addcomplaint);
    @POST(complaint_detail)
    Call<ResponseBody> complaint_detail(@Body GetComplaintDetails getComplaintDetails);

    @POST(add_complaint_reply)
    Call<ResponseBody> add_complaint_reply(@Body ComplaintReply complaintReply);

    @POST(PRODUCTSBYLOCATION)
    Call<ResponseBody> getProducts(@Body ProductsRequest product);

    @POST(PAGES_URL)
    Call<ResponseBody> getPages(@Body PagesRequest pagesRequest);

    @POST(ORDERS)
    Call<ResponseBody> getOrders(@Body OrderRequest request);

    @POST(NEW_ADDRESS)
    Call<ResponseBody> addAddress(@Body RegAddress request);

    @POST(DELETE_ADDRESS)
    Call<ResponseBody> deleteAddress(@Body DeleteAddress deleteAddress);

    @POST(VERIFY_ACCOUNT + "/{id}" + "/{code}" + "/{language}")
    Call<ResponseBody> verifyAccount(@Path("id") String user_id, @Path("code") String code, @Path("language") String language);

    @POST(RESEND_CODE + "{email}")
    Call<ResponseBody> resend_code(@Path("email") String user_id);

    @POST(ORDERS_DETAIL)
    Call<ResponseBody> getOrdersDetail(@Body OrderRequest request);

    @POST(UPDATE_PROFILE)
    Call<ResponseBody> updateProfile(@Body UpdateProfile updateProfile);

    @POST(UPDATE_PROFILE)
    Call<ResponseBody> updateProfileWithPass(@Body UpdateProfileWithPass updateProfile);
    @POST(MAKE_ORDER_FAV)
    Call<ResponseBody> makeOrderFavourite(@Body FavouriteRequest request);

    @POST(Add_To_Wish_Stock_alert)
    Call<ResponseBody> addTowishListStockAlert(@Body AddToWishStockAlertRequest request);

    @POST(SAVE_ORDER_RATING)
    Call<ResponseBody> saveOrderRating(@Body RetingRequest request);
    //    @POST(ADD_ORDERS)
    @POST(ADD_ORDERS_WITH_PAYMENT)
    Call<ResponseBody> checkOut(@Body CheckOut checkOut);

    @POST(save_order_new)
    Call<ResponseBody> checkOutWithCard(@Body CheckOutWithPayment checkOut);

    @POST(save_order_new)
    Call<ResponseBody> checkOutWithSadadId(@Body CheckOutWithSadadID checkOut);

    @POST(PAYMENT_STATUS)
    Call<ResponseBody> getPaymentStatus(@Body PaymentStatus paymentStatus);

    @POST(REFRESH_TOKEN)
//    @POST("1igmzpq1")
    Call<ResponseBody> refreshToken(@Body Check Check);

    @GET("/")
    Call<ResponseBody> getPublicIP();

    @GET(GET_ADDRESS_GOOGLE)
    Call<ResponseBody> getAddressFromGoogle(@Query("latlng") String latlang,
                                            @Query("sensor") boolean sensor, @Query("language") String language, @Query("key") String apiKey);

    @POST(RESET_PASSWORD)
    Call<ResponseBody> resetPassword(@Body ForgetPass login);

    @GET(CANCEL_ORDER_REASON)
    Call<ResponseBody> getCancelOrderReason();

    @GET(GET_STORES)
    Call<ResponseBody> getStores();

    @POST(CANCEL_ORDER)
    Call<ResponseBody> postCancelOrderReason(@Body CancelOrderRequest cancelOrderRequest);

    @POST(TIME_SLOTS)
    Call<ResponseBody> getTimeSlots(@Body TimeSlots timeSlots);

    @POST(CHECK_OPEN_ORDER)
    Call<ResponseBody> checkOpenOrders(@Body CheckOpenOrder checkOpenOrder);



    @POST(POPUP_PROMO)
    Call<ResponseBody> getPopupPromo(@Body Language lang);

    @POST(GET_CART_PARAMETERS)
    Call<ResponseBody> getCartParameters(@Body Cart_Params params);

    @POST(PROCESS_PY)
    Call<ResponseBody> getCheckoutId(@Body GetChectoutId params);
    @GET(PAYMENT)
    Call<ResponseBody> getPaymentStatus();

    @POST(word_search)
    Call<ResponseBody> word_search(@Body SearchWord searchWord);

    @POST(maintenences)
    Call<ResponseBody> maintenences(@Body Maintenences searchWord);

    @POST(returnData)
    Call<ResponseBody> returnData(@Body Maintenences searchWord);



    @POST(remove_receipt)
    Call<ResponseBody> remove_receipt(@Body RemoveReceiptModel removeReceiptModel);



    @POST(service_slots)
    Call<ResponseBody> service_slotsReq(@Body ServiceSlotsModel serviceSlotsModel);


    @POST(add_receipt)
    Call<AddReceiptModel> send_receipt(@Body RequestBody image);


    @POST(after_sale_2)
    Call<AfterSalesServicesReq> send_after_sale(@Body RequestBody invoice_pic);


   /* @POST(AFTERSALESERVICES_RETURN)
    Call<AfterSalesServicesMyReturnReq> afterSaleServicesMyReturnReq(@Body RequestBody image);
*/


/*  @Multipart
  @POST(add_receipt)
  Call<AddReceiptModel> send_receipt(@Part MultipartBody.Part filePart, @Part("order_id") String order_id,
                                     @Part("user_id") String user_id);*/

}
