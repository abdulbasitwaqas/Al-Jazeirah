package com.app.aljazierah.utils;

import android.content.Context;

import com.app.aljazierah.object.Areas;
import com.app.aljazierah.object.Cart;
import com.app.aljazierah.object.Channels;
import com.app.aljazierah.object.Cities;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.Favourites;
import com.app.aljazierah.object.PopupPromo;
import com.app.aljazierah.object.SubChannels;
import com.app.aljazierah.object.Tips;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.roam.appdatabase.DatabaseManager;

public class CoreManager {
    private static final String TAG = "CoreManager";
    private static final CoreManager instance = new CoreManager();
    public Context context;

    public static synchronized CoreManager getInstance() {
        CoreManager coreManager;
        synchronized (CoreManager.class) {
            coreManager = instance;
        }
        return coreManager;
    }

    public void setContext(Context context) {
        if (this.context == null) {
            this.context = context.getApplicationContext();
            initDatabase(context);
        }
    }

    private void initDatabase(Context context) {
        DatabaseManager.beginSetup(context)
                .setDBName("fayhaDB")
                .setDBVersion(2)
                .wipeOnVersionChange()
                .registerClass(User.class)
                .registerClass(Address.class)
                .registerClass(Channels.class)
                .registerClass(SubChannels.class)
                .registerClass(Cities.class)
                .registerClass(Tips.class)
                .registerClass(Cart.class)
                .registerClass(CompanySetting.class)
                .registerClass(PopupPromo.class)
                .registerClass(Areas.class)
                .registerClass(Favourites.class)
                .save();
    }

    public void removeLocaleData() {
        DatabaseManager.getInstance().destroyAllForClass(User.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Address.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Channels.class, true);
        DatabaseManager.getInstance().destroyAllForClass(SubChannels.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Cities.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Tips.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Cart.class, true);
        DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Areas.class, true);

    }

    public void removeUserData() {
        DatabaseManager.getInstance().destroyAllForClass(User.class, false);
        // DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class, false);
        DatabaseManager.getInstance().destroyAllForClass(Address.class, false);
        DatabaseManager.getInstance().destroyAllForClass(Cart.class, false);
        DatabaseManager.getInstance().destroyAllForClass(Favourites.class, false);
        // DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class, false);
    }

    public void removeCityAreaData(){
        DatabaseManager.getInstance().destroyAllForClass(Cities.class, true);
        DatabaseManager.getInstance().destroyAllForClass(Areas.class, true);
    }
    public void removeUserAddresses() {
        // DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class, false);
        DatabaseManager.getInstance().destroyAllForClass(Address.class, false);

        // DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class, false);
    }
}
