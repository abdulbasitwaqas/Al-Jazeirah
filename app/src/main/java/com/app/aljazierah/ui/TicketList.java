package com.app.aljazierah.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.TicketsAdapter;
import com.app.aljazierah.object.Tickets;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.WalletRequest;
import com.app.aljazierah.utils.APIManager;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.app.aljazierah.utils.Constants.load_tickets;

public class TicketList extends AppCompatActivity implements View.OnClickListener{
    ProgressDialog mProgressDialog;
    ListView tickets_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_ticketlist);
        setViews();
        getTicketList();

    }

    private void setViews(){
        setHeader();
        tickets_listview=findViewById(R.id.tickets_listview);
        if (UserSession.getInstance().getUserLanguage().equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
    }


    private void setHeader(){
        View toolbar=findViewById(R.id.toolbar);
        TextView title_textview=toolbar.findViewById(R.id.tvToolbarTitleTCA);
        ImageView iv_backbtn=toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        title_textview.setText(getResources().getString(R.string.st_tickets));
        iv_backbtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(load_tickets){
            load_tickets=false;
            getTicketList();
        }
    }

    private void getTicketList() {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            showProgress(true);
            APIManager.getInstance().get_list_complaints(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {

                    if (z) {
                        try {
                            ArrayList<Tickets> ticketsArrayList=new ArrayList<>();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("result");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json=jsonArray.getJSONObject(i);
                                Tickets tickets=new Tickets();
                                tickets.setTicketId(json.getString("ticket_id"));
                                tickets.setTicketBody(json.getString("ticket_body"));
                                tickets.setTicketStatus(json.getString("ticket_status"));
                                tickets.setTicketDateGenerated(json.getString("ticket_date_generated"));
                                tickets.setCategoryTitleEnglish(json.getString("category_title_english"));
                                tickets.setCategoryTitleArabic(json.getString("category_title_arabic"));
                                tickets.setReplies(json.getString("replies"));
                                tickets.setPendingFirstName(json.getString("pending_first_name"));
                                tickets.setPendingLastName(json.getString("pending_last_name"));
                                ticketsArrayList.add(tickets);
                            }
                            Collections.sort(ticketsArrayList, new Comparator<Tickets>() {
                                @Override
                                public int compare(Tickets z1, Tickets z2) {
                                    if (Integer.parseInt(z1.getTicketId()) < Integer.parseInt(z2.getTicketId()))
                                        return 1;
                                    if (Integer.parseInt(z1.getTicketId()) > Integer.parseInt(z2.getTicketId()))
                                        return -1;
                                    return 0;
                                }
                            });
                            ArrayList<Tickets> ticketslist=new ArrayList<>();
                            ArrayList<Tickets> tickets=new ArrayList<>();
                            for(int i=0;i<ticketsArrayList.size();i++){
                                if(ticketsArrayList.get(i).getTicketStatus().equals("1")){
                                    ticketslist.add(ticketsArrayList.get(i));

                                }
                                else{
                                    tickets.add(ticketsArrayList.get(i));
                                }
                            }


                            ticketslist.addAll(tickets);
                            TicketsAdapter ticketsAdapter=new TicketsAdapter(TicketList.this,ticketslist);
                            tickets_listview.setAdapter(ticketsAdapter);

                            showProgress(false);

                        } catch (Exception e) {
                            Log.e("Ex_cartitems", e.toString());
                            e.printStackTrace();
                        }

                    } else {
                    }


                }
            }, new WalletRequest(user.userId));
        }
    }
    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(TicketList.this);
            mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewBackButton:
                finish();
                overridePendingTransition(0,R.anim.exit_anim);
                break;

        }
    }
}
