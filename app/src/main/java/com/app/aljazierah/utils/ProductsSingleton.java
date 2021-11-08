package com.app.aljazierah.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.Orders;
import com.app.aljazierah.object.PagesModel;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.object.Promotions.Promotion;
import com.app.aljazierah.object.Store;
import com.app.aljazierah.object.SubCategoriesModel;

import java.util.ArrayList;
import java.util.List;

public class ProductsSingleton {

    private static final ProductsSingleton ourInstance = new ProductsSingleton();

    public ArrayList<ProductByLocation> productByLocations;
    public List<PagesModel> pagesModelArrayList;
    public List<SubCategoriesModel> subCategoriesModelList;
    public View cartView,basketview;
    public TextView tv_cartamount,basket_badge;
    public ArrayList<Areas> cityAreas;
    public List<Store> stores;
    public List<Cities> cities;
    private List<Categories> categoriesList = new ArrayList<>();
    private CustomViewPager customViewPager;
    private List<Promotion> PromotionList;
    public View slider;
    public ImageView cartImage;

    public  List<Orders> userOrdersList;

    public List<Orders> getUserOrdersList() {
        return userOrdersList;
    }

    public void setUserOrdersList(List<Orders> userOrdersList) {
        this.userOrdersList = userOrdersList;
    }

    public static ProductsSingleton getInstance() {
        return ourInstance;
    }

    private ProductsSingleton() {
    }

    public CustomViewPager getCustomViewPager() {
        return customViewPager;
    }

    public void setCustomViewPager(CustomViewPager customViewPager) {
        this.customViewPager = customViewPager;
    }

    public List<Promotion> getPromotionList() {
        return PromotionList;
    }

    public void setPromotionList(List<Promotion> promotionList) {
        PromotionList = promotionList;
    }

    public ArrayList<ProductByLocation> getProductByLocations() {
        return productByLocations;
    }

    public void setProductByLocations(ArrayList<ProductByLocation> productByLocations) {
        this.productByLocations = productByLocations;
    }
    public List<SubCategoriesModel> getSubCategoriesModel() {
        return subCategoriesModelList;
    }

    public void setSubCategoriesModel(List<SubCategoriesModel> subCategoriesModelList) {
        this.subCategoriesModelList = subCategoriesModelList;
    }


    public View getCartView() {
        return cartView;
    }

    public void setCartView(View cartView) {
        this.cartView = cartView;
    }


    public TextView getTv_cartamount() {
        return tv_cartamount;
    }

    public void setTv_cartamount(TextView tv_cartamount) {
        this.tv_cartamount = tv_cartamount;
    }

    public TextView getBasket_badge() {
        return basket_badge;
    }

    public void setBasket_badge(TextView basket_badge) {
        this.basket_badge = basket_badge;
    }

    public View getBasketview() {
        return basketview;
    }

    public void setBasketview(View basketview) {
        this.basketview = basketview;
    }

    public ImageView getBasketImg() {
        return cartImage;
    }

    public void setBasketImg(ImageView basketview) {
        this.cartImage = cartImage;
    }

    public ArrayList<Areas> getCityAreas() {
        return cityAreas;
    }

    public void setCityAreas(ArrayList<Areas> cityAreas) {
        this.cityAreas = cityAreas;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }



    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }


    public List<PagesModel> getPages() {
        return pagesModelArrayList;
    }

    public void setPages(List<PagesModel> pagesModelArrayList) {
        this.pagesModelArrayList = pagesModelArrayList;
    }




    public View getSlider() {
        return slider;
    }

    public void setSlider(View slider) {
        this.slider = slider;
    }



}
