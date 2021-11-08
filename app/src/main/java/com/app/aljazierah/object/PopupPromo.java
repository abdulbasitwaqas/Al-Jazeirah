package com.app.aljazierah.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONObject;

@DatabaseTable
public class PopupPromo extends ManagedObject {
    @DatabaseField(defaultValue = "false")
    public boolean showPopup;

    @DatabaseField(defaultValue = "")
    public String title;

    @DatabaseField(defaultValue = "")
    public String popupImage;

    @DatabaseField(defaultValue = "0")
    public int promoBaseQty;

    @DatabaseField(defaultValue = "0")
    public int promoFreeGoods;

    public static void fromJson(JSONObject jsonObject) {

        DatabaseManager.getInstance().destroyAllForClass(PopupPromo.class);


        if (jsonObject == null) {
            return;
        }

        PopupPromo result = DatabaseManager.getInstance().getFirstOfClass(PopupPromo.class);
        boolean created = result == null;
        if (result == null) {
            result = new PopupPromo();
        }
        result.showPopup = jsonObject.optBoolean("showpopup");
        result.title = jsonObject.optString("title");
        result.popupImage = jsonObject.optString("popupimage");
        result.promoBaseQty = jsonObject.optInt("promobaseqty");
        result.promoFreeGoods = jsonObject.optInt("promofreegoods");
        if (created) {
            result.create();
        } else {
            result.update();
        }

    }
}
