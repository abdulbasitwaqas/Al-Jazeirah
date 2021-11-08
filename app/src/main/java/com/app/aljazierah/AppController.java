package com.app.aljazierah;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.LocaleList;
import androidx.multidex.MultiDex;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

//import com.appsflyer.AppsFlyerConversionListener;
//import com.appsflyer.AppsFlyerLib;
import com.app.aljazierah.utils.CoreManager;
//import com.clevertap.android.sdk.ActivityLifecycleCallback;
//import com.clevertap.android.sdk.CleverTapAPI;
//import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.ActivityLifecycleCallback;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

import static com.app.aljazierah.utils.Constants.ARABIC;


@ReportsCrashes(mailTo = "smtayyeb@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private Context mContext;
    private static final String AF_DEV_KEY = "n4QzPHhpEBvJhdru24Ys3h";
    public ViewPager viewPager_home;
    public Activity activity;
    public int[] colors = new int[3];
    private static AppController mInstance;

    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        mInstance = this;
        mContext = this;
        CoreManager.getInstance().setContext(this);
        UserSession.getInstance().setContext(this);

        if (UserSession.getInstance().getUserLanguage().equals(ARABIC)) {

            ViewPump.init(ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build());

        } else {
        }
        colors = new int[]{ContextCompat.getColor(this, R.color.slider1),
                ContextCompat.getColor(this, R.color.slider2),
                ContextCompat.getColor(this, R.color.slider3)};
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static boolean setLocale() {
        String lang = UserSession.getInstance().getUserLanguage();
        Locale current = AppController.getInstance().getResources().getConfiguration().locale;
        Locale myLocale = new Locale(lang);
        Resources res = AppController.getInstance().getResources();
        Configuration conf = res.getConfiguration();
        if (lang.equals(ARABIC)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                conf.setLocale(myLocale);
                LocaleList localeList = new LocaleList(myLocale);
                LocaleList.setDefault(localeList);
                conf.setLocales(localeList);
                AppController.getInstance().createConfigurationContext(conf);

            } else {
                conf.locale = myLocale;
            }
            return true;
        } else {
            return false;
        }

    }

    public ViewPager getViewPager_home() {
        return viewPager_home;
    }

    public void setViewPager_home(ViewPager viewPager_home) {
        this.viewPager_home = viewPager_home;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AppController.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }




    public static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        context.createConfigurationContext(configuration);
    }


}