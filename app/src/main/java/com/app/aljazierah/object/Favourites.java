package com.app.aljazierah.object;

import com.j256.ormlite.field.DatabaseField;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Favourites extends ManagedObject {

    @DatabaseField
    public String id;

    @DatabaseField
    public String ord_id;

    @DatabaseField
    public String ord_user_id;

    @DatabaseField
    public String ord_kit_id;

    @DatabaseField
    public String ord_total_price;

    @DatabaseField
    public String ord_note;

    @DatabaseField
    public String ord_delivery_charge;

    @DatabaseField
    public String ord_tax_value;

    @DatabaseField
    public String current_status;

    @DatabaseField
    public String invoice_number;

    @DatabaseField
    public String delv_type;

    @DatabaseField
    public String ord_date;


    @DatabaseField
    public String iscancelable;

    @DatabaseField
    public String iseditable;


    @DatabaseField
    public String isreorderable;

    @DatabaseField
    public int count;

    @DatabaseField
    public String order_status_ar;

    @DatabaseField
    public String order_status_en;

    public static void addOneItem(Favourites favourites) {

        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", favourites.ord_id, Favourites.class);
        if (result != null) {

            result.ord_id=favourites.ord_id;
            result.current_status=favourites.current_status;
            result.delv_type=favourites.delv_type;
            result.id=favourites.id;
            result.invoice_number=favourites.invoice_number;
            result.iscancelable=favourites.iscancelable;
            result.iseditable=favourites.iseditable;
            result.isreorderable=favourites.isreorderable;
            result.ord_date=favourites.ord_date;
            result.ord_delivery_charge=favourites.ord_delivery_charge;
            result.ord_kit_id=favourites.ord_kit_id;
            result.ord_note=favourites.ord_note;
            result.ord_tax_value=favourites.ord_tax_value;
            result.ord_total_price=favourites.ord_total_price;
            result.order_status_ar=favourites.order_status_ar;
            result.order_status_en=favourites.order_status_en;

            result.update();
        }
        else{
            if (result == null) {
                result = new Favourites();

                result.ord_id=favourites.ord_id;
                result.current_status=favourites.current_status;
                result.delv_type=favourites.delv_type;
                result.id=favourites.id;
                result.invoice_number=favourites.invoice_number;
                result.iscancelable=favourites.iscancelable;
                result.iseditable=favourites.iseditable;
                result.isreorderable=favourites.isreorderable;
                result.ord_date=favourites.ord_date;
                result.ord_delivery_charge=favourites.ord_delivery_charge;
                result.ord_kit_id=favourites.ord_kit_id;
                result.ord_note=favourites.ord_note;
                result.ord_tax_value=favourites.ord_tax_value;
                result.ord_total_price=favourites.ord_total_price;
                result.order_status_ar=favourites.order_status_ar;
                result.order_status_en=favourites.order_status_en;
                result.create();
            }
        }

    }

    public static void addMultipleItem(String orderID,int count) {

        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", orderID, Favourites.class);
        if (result != null) {
            result.count = result.count + count;
            result.update();
        }
        else{
            if (result == null) {
                result = new Favourites();
                result.count = result.count + count;
                result.ord_id = orderID;
                result.create();
            }
        }

    }


    public static void removeOneItem(String orderID) {

        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", orderID, Favourites.class);
        if (result != null) {
            result.count = result.count - 1;
            result.update();
        }
    }
    public static void removeItems(String orderID) {

        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", orderID, Favourites.class);
        if (result != null) {
//            result.count = 0;
            result.delete();
        }
    }

    public static void setFavourite(String orderID,int isFavourite){
        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", orderID, Favourites.class);
        if (result!=null){
            result.count=isFavourite;
            result.update();
        }
    }
    public static void emptyCart() {

        DatabaseManager.getInstance().destroyAllForClass(Favourites.class);
    }

    public static boolean deleteProductFromCart(String orderID) {

        Favourites result = DatabaseManager.getInstance().getFirstMatching("ord_id", orderID, Favourites.class);
        if (result != null) {
            result.delete();
            return true;
        } else {
            return false;
        }
    }

    public static String[] getTotalItemsAndPrice() {
        String data[] = new String[2];
        List<Favourites> products = DatabaseManager.getInstance().getAllOfClass(Favourites.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Favourites favourites : products) {

                total = total + favourites.count;
                float price = (favourites.count * (Float.parseFloat(favourites.ord_total_price)));
                totalAmount = totalAmount + price;
            }
            data[0] = "" + total;

            DecimalFormat f = new DecimalFormat("######.00", new DecimalFormatSymbols(Locale.ENGLISH));
            data[1] = f.format(totalAmount);
            return data;
        }
        return null;
    }

    public static int getTotalItems() {
        List<Favourites> products = DatabaseManager.getInstance().getAllOfClass(Favourites.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Favourites favourites : products) {
                total = total + favourites.count;
                float price = (favourites.count * (Float.parseFloat(favourites.ord_total_price)));
                totalAmount = totalAmount + price;
            }
            return total;
        }
        return 0;
    }

    public static float getTotalPrice() {
        List<Favourites> products = DatabaseManager.getInstance().getAllOfClass(Favourites.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Favourites favourites : products) {
                total = total + favourites.count;
                float price = (favourites.count * (Float.parseFloat(favourites.ord_total_price)));
                totalAmount = totalAmount + price;
            }
            return totalAmount;
        }
        return 0;
    }

    public static String getFormattedAmount(double totalAmount) {
        DecimalFormat f = new DecimalFormat("######.00");
        return f.format(totalAmount);
    }


}
