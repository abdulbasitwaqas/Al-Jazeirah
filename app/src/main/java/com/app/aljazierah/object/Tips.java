package com.app.aljazierah.object;

import com.app.aljazierah.AppController;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

@DatabaseTable
public class Tips extends ManagedObject {
    @DatabaseField
    public int id;
    @DatabaseField (defaultValue = "")
    public String detailAr;
    @DatabaseField (defaultValue = "")
    public String detailEn;
    @DatabaseField
    public String date;
    @DatabaseField
    public int color;
    @DatabaseField
    public int active;

    public static boolean fromJson(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;

                json = jsonArray.getJSONObject(i);

                if (json == null) return false;

                int id = json.optInt("id", 0);
                if (id == 0) return false;

                Tips result = DatabaseManager.getInstance().getFirstMatching("id", id, Tips.class);
                boolean created = result == null;
                if (result == null) {
                    result = new Tips();
                    result.id = id;
                }
                if (i < 3) {
                    result.color = AppController.getInstance().colors[i];
                } else {
                    Random r = new Random();
                    int Low = 0;
                    int High = 3;
                    result.color = AppController.getInstance().colors[r.nextInt(High - Low) + Low];
                }
                result.detailAr = json.optString("detail_ar");
                result.detailEn = json.optString("detail_en");
                result.date = json.optString("date");
                result.active = json.optInt("active");

                if (created) result.create();
                else result.update();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
