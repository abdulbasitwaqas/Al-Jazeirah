package com.app.aljazierah.utils.recycler;

import android.view.View;

/**
 * Created by Mustanser Iqbal on 6/7/2016.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
