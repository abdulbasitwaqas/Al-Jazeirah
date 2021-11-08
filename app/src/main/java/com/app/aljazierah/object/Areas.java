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
public class Areas extends ManagedObject {
    @DatabaseField
    public int areaId;
    @DatabaseField
    public int areaCityId;

    @DatabaseField
    public String areaTitleAr;
    @DatabaseField
    public String areaTitleEn;

/*    @DatabaseField
    public int areaCountryId;



    @DatabaseField
    public int areaActive;


    @DatabaseField
    public String googleAreaEn;
    @DatabaseField
    public String googleAreaAr;*/

    public static void fromJson(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;

                json = jsonArray.getJSONObject(i);

                if (json == null) return;

                int id = json.optInt("area_id", 0);
                if (id == 0) return;

                Areas result = DatabaseManager.getInstance().getFirstMatching("areaId", id, Areas.class);
                boolean created = result == null;
                if (result == null) {
                    result = new Areas();
                    result.areaId = id;
                }
               // result.areaCountryId = json.optInt("area_country_id");
                result.areaCityId = json.optInt("area_city_id");
                result.areaTitleEn = json.optString("area_title_en");
                result.areaTitleAr = json.optString("area_title_ar");
               // result.googleAreaAr = json.optString("google_area_ar");
               // result.googleAreaEn = json.optString("google_area_en");
               // result.areaActive = json.optInt("area_active");

                if (created) result.create();
                else result.update();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
