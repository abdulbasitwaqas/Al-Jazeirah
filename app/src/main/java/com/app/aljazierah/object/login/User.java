
package com.app.aljazierah.object.login;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONObject;

@DatabaseTable
public class User extends ManagedObject {

    @DatabaseField
    public String userId;
    @DatabaseField
    public String email;
    @DatabaseField
    public String pass;
    @DatabaseField
    public String firstName;
    @DatabaseField
    public String lastName;
    @DatabaseField
    public String mobile;
    @DatabaseField
    public String image;
    @DatabaseField
    public String cityId;
    @DatabaseField
    public String uniqueId;
    @DatabaseField
    public String toicArn;
    @DatabaseField
    public String endPointArn;
    @DatabaseField
    public String loginId;
    @DatabaseField
    public String notification;
    @DatabaseField
    public String birthDate;
    @DatabaseField
    public String gender;
    @DatabaseField
    public String mobileActive;
    @DatabaseField
    public String emailActive;
    @DatabaseField
    public String mobileActiveCode;
    @DatabaseField
    public String activeStatus;
    @DatabaseField
    public String phone;
    @DatabaseField
    public String random;
    @DatabaseField
    public String identityNum;
    @DatabaseField
    public String clientType;
    @DatabaseField
    public String clientSubType;
    @DatabaseField
    public String companyName;
    @DatabaseField
    public String sapUserId;
    @DatabaseField
    public String locationCode;
    @DatabaseField
    public String registerDate;
    @DatabaseField
    public String smsRand;
    @DatabaseField
    public String authToken;
    @DatabaseField
    public String hide_price;
    @DatabaseField
    public String add_address;

    public static void saveUserDetail(JSONObject json) {
        try {

            if (json == null) return;

            String id = json.optString("user_id", "");
            if (id.isEmpty()) return;

            User result = DatabaseManager.getInstance().getFirstMatching("userId", id, User.class);
            boolean created = result == null;
            if (result == null) {
                result = new User();
                result.userId = id;
            }

            result.email = json.optString("email");
            result.pass = json.optString("pass");
            result.firstName = json.optString("first_name");
            result.lastName = json.optString("last_name");
            result.mobile = json.optString("mobile");
            result.image = json.optString("image");
            result.cityId = json.optString("city_id");
            result.toicArn = json.optString("toicArn");
            result.endPointArn = json.optString("endPointArn");
            result.loginId = json.optString("login_id");
            result.notification = json.optString("notification");
            result.birthDate = json.optString("birth_date");
            result.gender = json.optString("gender");
            result.mobileActive = json.optString("mobile_active");
            result.emailActive = json.optString("email_active");
            result.mobileActiveCode = json.optString("mobile_active_code");
            result.activeStatus = json.optString("activeStatus");
            result.phone = json.optString("phone");
            result.identityNum = json.optString("identity_num");
            result.clientType = json.optString("client_type");
            result.clientSubType = json.optString("client_sub_type");
            result.companyName = json.optString("company_name");
            result.sapUserId = json.optString("sap_userId");
            result.locationCode = json.optString("location_code");
            result.registerDate = json.optString("register_date");
            result.smsRand = json.optString("sms_rand");
            result.authToken = json.optString("auth_key");
            result.hide_price = json.optString("hide_price");
            result.add_address = json.optString("add_address");

            if (created) result.create();
            else result.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
