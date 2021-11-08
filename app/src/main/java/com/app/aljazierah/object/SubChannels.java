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
public class SubChannels extends ManagedObject {

    @DatabaseField
    public int id;
    @DatabaseField
    public int parentId;
    @DatabaseField
    public String titleAr;
    @DatabaseField
    public String titleEn;
    @DatabaseField
    public int active;
    @DatabaseField
    public int type;
    @DatabaseField
    public String sapId;

    public static void fromJson(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;

                json = jsonArray.getJSONObject(i);

                if (json == null) return;

                int id = json.optInt("id", 0);
                if (id == 0) return;

                SubChannels result = DatabaseManager.getInstance().getFirstMatching("id", id, SubChannels.class);
                boolean created = result == null;
                if (result == null) {
                    result = new SubChannels();
                    result.id = id;
                }
                result.parentId = json.optInt("parent_id");
                result.titleAr = json.optString("title_ar");
                result.titleEn = json.optString("title_en");
                result.sapId = json.optString("sap_id");
                result.active = json.optInt("active");
                result.type = json.optInt("type");

                if (created) result.create();
                else result.update();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
