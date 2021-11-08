package com.app.aljazierah.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

@DatabaseTable
public class Cities extends ManagedObject {
    @DatabaseField
    public int cityId;
    @DatabaseField
    public int cityCountryId;
    @DatabaseField
    public String cityTitleEn;
    @DatabaseField
    public String cityTitleAr;
    @DatabaseField
    public String plant;
    @DatabaseField
    public int cityActive;

    public static void fromJson(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;

                json = jsonArray.getJSONObject(i);

                if (json == null) return;

                int id = json.optInt("city_id", 0);
                if (id == 0) return;

                Cities result = DatabaseManager.getInstance().getFirstMatching("cityId", id, Cities.class);
                boolean created = result == null;
                if (result == null) {
                    result = new Cities();
                    result.cityId = id;
                }
                result.cityCountryId = json.optInt("city_country_id");
                result.cityTitleEn = json.optString("city_title_en");
                result.cityTitleAr = json.optString("city_title_ar");
                result.plant = json.optString("plant");
                result.cityActive = json.optInt("city_active");

                if (created) result.create();
                else result.update();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
