package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;

public class PersonalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_personal_info);
    }
}
