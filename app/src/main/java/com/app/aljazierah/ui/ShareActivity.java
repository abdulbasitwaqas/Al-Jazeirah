package com.app.aljazierah.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;

public class ShareActivity extends AppCompatActivity {

    private boolean isArabic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        setContentView(R.layout.share_activity);
    }

    public void closeActivity(View v) {
        finish();
    }

    public void shareContent(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.delicious_text));
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share with"));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }
}
