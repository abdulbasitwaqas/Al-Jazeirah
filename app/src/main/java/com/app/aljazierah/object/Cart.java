package com.app.aljazierah.object;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;


@DatabaseTable
public class Cart extends ManagedObject {

    @DatabaseField
    public String productId;

    @DatabaseField
    public String productTitleAr;

    @DatabaseField
    public String productTitleEn;

    @DatabaseField
    public String amount;

    @DatabaseField(defaultValue = "0")
    public int count;

    @DatabaseField
    public String image;

    @DatabaseField
    public String price;


    @DatabaseField
    public String weight;

    @DatabaseField
    public String price_vat;


    private int incrementalBasePromoQtyVal = 0;
    private int incrementalBasePromoFreeGoodsVal = 0;

    public static void addToCart(ProductByLocation product, int count,int t_count) {

        String id = product.getId();
        if (id.isEmpty()) return;

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", id, Cart.class);
        boolean created = result == null;
        if (result == null) {
            result = new Cart();
            result.productId = id;
        }
        result.productTitleAr = product.getNameAr();
        result.productTitleEn = product.getNameEn();


        double t_amount= Double.parseDouble(product.getPrice().replace(",","."))*t_count;

        result.amount = t_amount+"";
        result.price=product.getPrice();
        result.price_vat=product.getPrice_vat();
        result.weight=product.getSpecifications().getWeight();

        Log.e("Price",result.price+"");
        if (count != -1) {
            if (result.count == 0) {
                result.count = t_count;
            } else {
                result.count = result.count + t_count;
            }
        } else {
            result.count = t_count;
        }

        Log.d("adasdas",product.getImg()+"");
        if (product.getImg().size()>0) {
            result.image = product.getImg().get(0);
        }else {
            result.image = "";
        }
        if (created) result.create();
        else result.update();
    }


      public static void addToCartReorder(ProductByLocation product, int count,int t_count,String weight) {

        String id = product.getId();
        if (id.isEmpty()) return;

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", id, Cart.class);
        boolean created = result == null;
        if (result == null) {
            result = new Cart();
            result.productId = id;
        }
        result.productTitleAr = product.getNameAr();
        result.productTitleEn = product.getNameEn();


        double t_amount= Double.parseDouble(product.getPrice().replace(",","."))*t_count;

        result.amount = t_amount+"";
        result.price=product.getPrice();
        result.price_vat=product.getPrice_vat();
        result.weight=weight;

        Log.e("Price",result.price+"");
        if (count != -1) {
            if (result.count == 0) {
                result.count = t_count;
            } else {
                result.count = result.count + t_count;
            }
        } else {
            result.count = t_count;
        }
        result.image = product.getImg().get(0);

        if (created) result.create();
        else result.update();
    }

    public static void addOneItem(String productId) {

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productId, Cart.class);
        if (result != null) {
            result.count = result.count + 1;
            result.update();
        }
        else{
            if (result == null) {
                result = new Cart();
                result.count = result.count + 1;
                result.productId = productId;
                result.create();
            }
        }

    }

    public static void addMultipleItem(String productId,int count,ProductByLocation product) {

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productId, Cart.class);
        if (result != null) {
            result.count =  count;
            result.price = product.getPrice();
            result.price_vat = product.getPrice_vat();
            result.weight=product.getSpecifications().getWeight();
            result.update();
        }
        else{
            if (result == null) {
                result = new Cart();
                result.count = count;
                result.productId = productId;
                result.create();
            }
        }

    }

    public static void removeOneItem(String productId) {

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productId, Cart.class);
        if (result != null) {
            result.count = result.count - 1;
            result.update();
        }
    }
    public static void removeItems(String productId) {

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productId, Cart.class);
        if (result != null) {
            result.count = 0;
            result.update();
        }
    }
    public static void emptyCart() {

        DatabaseManager.getInstance().destroyAllForClass(Cart.class);
    }

    public static boolean deleteProductFromCart(String productId) {

        Cart result = DatabaseManager.getInstance().getFirstMatching("productId", productId, Cart.class);
        if (result != null) {
            result.delete();
            return true;
        } else {
            return false;
        }
    }

    public static String[] getTotalItemsAndPrice() {
        String data[] = new String[2];
        List<Cart> products = DatabaseManager.getInstance().getAllOfClass(Cart.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Cart cart : products) {

                total = total + cart.count;
                float price = (cart.count * (Float.parseFloat(cart.price)));
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
        List<Cart> products = DatabaseManager.getInstance().getAllOfClass(Cart.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Cart cart : products) {
                total = total + cart.count;
                float price = (cart.count * (Float.parseFloat(cart.amount)));
                totalAmount = totalAmount + price;
            }
            return total;
        }
        return 0;
    }
    public static int getTotalWeight() {
        List<Cart> products = DatabaseManager.getInstance().getAllOfClass(Cart.class);
        if (products != null && products.size() > 0) {
            int total = 0;
            for (Cart cart : products) {
                if (cart.weight!=null&&!cart.weight.equals(""))
                 total = total + Integer.parseInt(cart.weight);
            }
            return total;
        }
        return 0;
    }


    public static float getTotalPrice() {
        List<Cart> products = DatabaseManager.getInstance().getAllOfClass(Cart.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Cart cart : products) {
                total = total + cart.count;
                float price = (cart.count * (Float.parseFloat(cart.price)));
                totalAmount = totalAmount + price;
            }
            return totalAmount;
        }
        return 0;
    }

    public static float getTotalPriceVat() {
        List<Cart> products = DatabaseManager.getInstance().getAllOfClass(Cart.class);

        if (products != null && products.size() > 0) {
            int total = 0;
            float totalAmount = 0;
            for (Cart cart : products) {
                total = total + cart.count;
                float price = (cart.count * (Float.parseFloat(cart.price_vat)));
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

    public int getIncrementalBasePromoQtyVal() {
        return incrementalBasePromoQtyVal;
    }

    public void setIncrementalBasePromoQtyVal(int incrementalBasePromoQtyVal) {
        this.incrementalBasePromoQtyVal = incrementalBasePromoQtyVal;
    }

    public int getIncrementalBasePromoFreeGoodsVal() {
        return incrementalBasePromoFreeGoodsVal;
    }

    public void setIncrementalBasePromoFreeGoodsVal(int incrementalBasePromoFreeGoodsVal) {
        this.incrementalBasePromoFreeGoodsVal = incrementalBasePromoFreeGoodsVal;
    }
}
