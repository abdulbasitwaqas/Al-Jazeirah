package com.app.aljazierah.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by Mustanser Iqbal on 12/25/2017.
 */

public class TypefaceUtil {

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *  @param context                    to work with assets
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String customFontFileNameInAssets) {

        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField("SERIF");
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.d("font matters : ", "Can not set custom font " + customFontFileNameInAssets + " instead of SERIF");
        }

        /*final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("serif", customFontTypeface);
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final Field defaultFontTypefaceField = Typeface.class.getDeclaredField("SERIF");
                defaultFontTypefaceField.setAccessible(true);
                defaultFontTypefaceField.set(null, customFontTypeface);
            } catch (Exception e) {
                Log.e(TypefaceUtil.class.getSimpleName(), "Can not set custom font " + customFontFileNameInAssets + " instead of " + "SERIF");
            }
        }*/
    }
}