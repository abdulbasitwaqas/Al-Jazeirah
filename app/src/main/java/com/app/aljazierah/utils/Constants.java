package com.app.aljazierah.utils;
 
import java.text.SimpleDateFormat;
import java.util.Locale;

public class    Constants {

    //live
//    public static final String SERVER_URL = "http://oms.aljazierah.com/";
    public static final String SERVER_URL = "http://94.74.86.219/Al-Jazierah-web/";  //stagging
//     public static final String SERVER_URL = "http://oms-alb-1668609292.me-south-1.elb.amazonaws.com/oms";    //live
//     public static final String SERVER_URL = "http://fgstg.berain.com.sa/oms";    //Berain staggin
     public static String BASE_URL = SERVER_URL+"api/v1/";

     public static final String IMAGE_BASE_URL = SERVER_URL;    // stagging image url
     public static final String PROMO_BASE_URL = IMAGE_BASE_URL;
    //public static  String BASE_URL = "https://berain.com.sa/oms/api8/index.php/";
    public static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/";
    public static final String PAYMENT_MFU = "https://berain.com.sa/oms/index.php/Order/payment_response_app";
    public static final String HYPER_PAYMENT_STATUS = "https://test.oppwa.com/v1/checkouts/";

    //APIs
    public static final String COMPANY_SETTINGS = "companysetting";
    public static final String GET_STORES = "get-stores";
    public static final String PRODUCTSBYLOCATION = "get_products_byloc";
    public static final String GET_CART_PARAMETERS = "get_cart_parameters";
    public static final String get_promo_discount = "get_promo_discount";
    public static final String is_stc_tamayouz_user = "is_stc_tamayouz_user";
    public static final String validate_stc_tamayouz_otp = "validate_stc_tamayouz_otp";
    public static final String reversal_stc_tamayouz_discount  = "reversal_stc_tamayouz_discount";

    public static final String save_order_new = "save_order_new";
    public static final String PROCESS_PY = "payment-callback";

    public static final String word_search = "word_search";
    public static final String maintenences = "aftersale_data";
    public static final String returnData = "aftersale_return_data";

    public static final String LOGOUT = "logout";
    public static final String LOGINREGISTER = "LoginRegister";
    public static final String AFTERSALESERVICES = "after-sale";
    public static final String AFTERSALESERVICES_RETURN = "after-sale-return";
    public static final String GETSTORES = "get-stores";
    public static final String CHECKUSEROTP = "CheckUserOtp";
    public static final String ORDERS = "getorders";
    public static final String ORDERS_DETAIL = "getorder_detail";
    public static final String CANCEL_ORDER_REASON = "CancelReasons";
    public static final String CANCEL_ORDER = "CancelOrder";
    public static final String MAKE_ORDER_FAV = "make_order_fav";
    public static final String Add_To_Wish_Stock_alert = "wishlist_stock";
    public static final String SAVE_ORDER_RATING = "save_order_rating";
    public static final String UPDATE_PROFILE = "updateprofile";
    public static final String VerifyEmail = "verify_email";
    public static final String get_wallet_details  = "get_wallet_details";
    public static final String NEW_ADDRESS = "newaddress";
    public static final String DELETE_ADDRESS = "delete_address";

    public static final String LOGIN = "checkuser";
    public static final String UPDATEUSERNAME = "UpdateUsername";
    //public static final String FORGET_PASSWORD = "SendMail";
    public static final String FORGET_PASSWORD = "checkuser";
    public static final String FORGET_PASSWORD_VERIFY_CODE = "checkuser";
    public static final String CHANNELS = "get_client_type_detail";
    public static final String SUB_CHANNELS = "get_sub_client_type";
    public static final String CITIES = "get_all_cities";
    public static final String CITY_AREA = "getarea";
    public static final String SIGNUP = "signup";
    public static final String TIPS = "get_tips";

    public static final String list_complaints  = "list_complaints";
    public static final String save_temp_order_payment  = "save_temp_order_payment";
    public static final String load_complaint_data  = "load_complaint_data";
    public static final String add_complaint  = "add_complaint";
    public static final String POPUP_PROMO = "PopupPromo";
    public static final String complaint_detail  = "complaint_detail";
    public static final String add_complaint_reply = "add_complaint_reply";
    public static final String PRODUCTS = "get_dishes";
    public static final String PAYMENT = "payment";
    public static final String ADD_ORDERS = "addorders";
    public static final String ADD_ORDERS_WITH_PAYMENT = "addordersnew";
    public static final String GET_ADDRESS_GOOGLE = "json";
    public static final String RESEND_CODE = "resend_code";
    public static final String VERIFY_ACCOUNT = "activeAccountVerify";
    public static final String PAYMENT_STATUS = "payment_status";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String RESET_PASSWORD = "resetpassword";
    public static final String TIME_SLOTS = "TimeSlots";
    public static final String CHECK_OPEN_ORDER = "CheckOpenOrder";
    public static final String PAGES_URL = "page";
    public static final String add_receipt = "bank_image_upload";
    public static final String remove_receipt = "bank_image_remove";
    public static final String service_slots = "service-slots";
    public static final String after_sale_2 = "after-sale-2";




    // keys and codes
    public static final String GOOGLE_API_Key = "AIzaSyA6mQGJkrpqkjynEjkD1uwCcDfS71YfnlQ";

    public static final String ARABIC = "ar";
    public static final String ENGLISH = "en";
    public static boolean validate_promocode=false;


    public static String dateFormat = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());


    //////////////// Intents //////////////////////////////

    public static String IS_LOGIN = "is_login";
    public static boolean isOrderCancel = false;
    public static String EMAIL = "user_email";
    public static String USER_ID = "user_id";
    public static String FINISH = "finish";
    public static String OPEN_ORDERS = "open_orders";
    public static String FROM_CHECK_OUT = "from_checkOut";
    public static boolean fromcart = false;
    public static boolean special_promotion = false;
    public static boolean checkaddress = false;

    //////////////////// EXTRAS ////////////////////////
    public static String DATE_EXTRA = "date_extra";
    public static String TIME_EXTRA = "date_extra";
    public static String ORDER_NUMBER = "order_num";
    public static String ORDER_REFERENCE = "order_ref";
    public static String AMOUNT = "amount";
    public static String PAYMENT_METHOD = "payment_method";
    public static String ADDRESS_ID = "address_id";
    public static String KEY = "060fac9a80afec9b95eb292ad884c5f5";


    /////////////// Codes /////////////////////////


    public static int THANKS = 101;
    public static int CHOOSE_ADDRESS = 102;
    public static int CART_SCREEN = 103;
    public static final int ORDERS_SCREEN = 104;
    public static final int ORDER_DETAIL = 105;
    public static final int SADAD_SCREEN = 106;
    public static int OPEN_ORDER_INTENT = 107;
    public static final int LOGINFROMCART = 108;
    public static final int LOGINFROMAFTERSALES = 777;
    public static boolean fromproduct = false;
    public static boolean fromore = false;
    public static boolean login = false;
    public static boolean load_tickets = false;

    public static String BLOCK_CHARACTERS = "[\'\"*{<>>]`℅™®©~#";

}
