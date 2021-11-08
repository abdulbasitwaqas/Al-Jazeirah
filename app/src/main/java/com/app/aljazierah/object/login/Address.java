
package com.app.aljazierah.object.login;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.fetch.Predicate;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable
public class Address extends ManagedObject {

    @DatabaseField
    public String addId;
    @DatabaseField
    public String sapId;
    @DatabaseField
    public String userId;
    @DatabaseField
    public String addArea;
    @DatabaseField
    public String addName;
    @DatabaseField
    public String addDetail;
    @DatabaseField
    public String addStreetName;
    @DatabaseField
    public String addLatitude;
    @DatabaseField
    public String addLongitude;
    @DatabaseField
    public String addBlock;
    @DatabaseField
    public String addFloor;
    @DatabaseField
    public String addGoogleAddress;
    @DatabaseField
    public String addCity;
    @DatabaseField
    public String addCountry;
    @DatabaseField
    public boolean isDefault;
    @DatabaseField
    public String add_type;

    public static boolean saveAddresses(JSONArray addresses) {
        try {
            for (int size = 0; size < addresses.length(); size++) {
                JSONObject json = null;

                json = addresses.getJSONObject(size);

                if (json == null) return false;

                String id = json.optString("add_id", "");
                if (id.isEmpty()) return false;

                Address result = DatabaseManager.getInstance().getFirstMatching("addId", id, Address.class);
                boolean created = result == null;
                if (result == null) {
                    result = new Address();
                    result.addId = id;
                    if (size == 0) {
                        result.isDefault = true;
                    } else {
                        result.isDefault = false;
                    }
                }

                result.sapId = json.optString("sap_id");
                result.userId = json.optString("user_id");
                result.addArea = json.optString("add_area");
                result.addName = json.optString("add_name");
                result.addDetail = json.optString("add_detail");
                result.addStreetName = json.optString("add_street_name");
                result.addLatitude = json.optString("add_latitude");
                result.addLongitude = json.optString("add_longitude");
                result.addBlock = json.optString("add_block");
                result.addFloor = json.optString("add_floor");
                result.addGoogleAddress = json.optString("add_google_address");
                result.addCity = json.optString("add_city");
                result.addCountry = json.optString("add_country");
                result.add_type = json.optString("add_type");
                if (created) result.create();
                else result.update();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void changeDefaultAddress(final String addId) {

        List<Address> addresses = DatabaseManager.getInstance().runQueryWithPredicate(Address.class, new Predicate<Address>() {
            @Override
            public void with(QueryBuilder<Address, Integer> queryBuilder) throws SQLException {
                queryBuilder.where().ne("addId", addId).query();
            }
        });
        if (addresses != null && addresses.size() > 0) {
            for (Address address : addresses) {
                address.isDefault = false;
                address.update();
            }
        }

        Address result = DatabaseManager.getInstance().getFirstMatching("addId", addId, Address.class);
        if (result != null) {
            result.isDefault = true;
            result.update();
        }
    }
}
