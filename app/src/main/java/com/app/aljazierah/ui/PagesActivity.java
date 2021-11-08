package com.app.aljazierah.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;

public class PagesActivity extends AppCompatActivity {
    private TextView pageDescriptionTV, pageTitleTV;
    private ImageView backBtnPA;
    private String pageDetailAr="", pageDetailEn="", pageName="";
    private boolean isArabic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isArabic = AppController.setLocale();
        setContentView(R.layout.activity_pages);

        pageDetailAr= getIntent().getExtras().getString("pageDetailAr");
        pageDetailEn= getIntent().getExtras().getString("pageDetailEn");
        pageName=     getIntent().getExtras().getString("pageName");

        initMembers();


    }

    private void initMembers() {
        backBtnPA=findViewById(R.id.backBtnPA);
        pageDescriptionTV=findViewById(R.id.pageDescriptionTV);
        pageTitleTV=findViewById(R.id.pageTitleTV);



        if (isArabic) {
            pageTitleTV.setText(pageName);
            pageDescriptionTV.setText(Html.fromHtml(pageDetailAr));
        }
        else {
            pageTitleTV.setText(pageName);
            pageDescriptionTV.setText(Html.fromHtml(pageDetailEn));
        }



        backBtnPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}