package com.app.aljazierah.ui.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.PagesAdapter;
import com.app.aljazierah.adapter.PagesRequest;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.LogOut;
import com.app.aljazierah.ui.ChooseAddressActivity;
import com.app.aljazierah.ui.EditProfileActivity;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.ui.PagesActivity;
import com.app.aljazierah.ui.ShareActivity;
import com.app.aljazierah.ui.TermsAndConditionsActivity;
import com.app.aljazierah.ui.Ticket;
import com.app.aljazierah.ui.TicketList;
import com.app.aljazierah.ui.WalletInformationActivity;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.CoreManager;
import com.app.aljazierah.utils.ProductsSingleton;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentMore extends Fragment implements View.OnClickListener, PagesAdapter.PageClick {


    private boolean isArabic;
    View view/*,viewWhatsapp,viewMail*/;
    User user;
    LinearLayout /*layotContactInfo*/layoutLanguage, layoutTickets, layoutShareApp /*afterSalesServicesLL,*/ /*ourShowRoomLL*//*,wishListLL, afterSaleServicesLL*/;
    private CardView layoutProfile, layoutLogoutCV, ticketCV;
    TextView tvPrivacyPolicy, versionTV,/*tvReturns,tvmyReturns,tvmaintenances,tvAfterSales,*/
            tvPersonalInfo, tvWallet, tvTicketsList, tvAddTickets, tvAddress, tvContactNumber, tvContactMail, tvContactWhatsapp;
    boolean profileCheck = true;
    boolean afterSSCheck = true;
    boolean ticketCheck = true;
    ImageView imageProfile, imageTickets,/*facebookIV,instagramIV,twitterIV,snapchatIV, */
            arrowASS;
    private TextView facebookIV, instagramIV, twitterIV, snapchatIV, pagesHeaderTV, contactUsTVMF;
    private LinearLayout layoutLogout;

    boolean isVisible = false;
    private RecyclerView pagesRV;
    private Context context;

    private PagesAdapter pagesAdapter;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        layoutProfile = view.findViewById(R.id.layoutProfile);
        ticketCV = view.findViewById(R.id.ticketCV);
        layoutLogoutCV = view.findViewById(R.id.layoutLogoutCV);
        twitterIV = view.findViewById(R.id.twitterTV);
        snapchatIV = view.findViewById(R.id.snapchatTV);
        instagramIV = view.findViewById(R.id.instagramTV);
        facebookIV = view.findViewById(R.id.facebookTV);
        contactUsTVMF = view.findViewById(R.id.contactUsTVMF);
        layoutLogout = view.findViewById(R.id.layoutLogout);
        layoutLanguage = view.findViewById(R.id.layoutLanguage);
        tvWallet = view.findViewById(R.id.tvWallet);
        layoutShareApp = view.findViewById(R.id.layoutShareApp);
        tvPersonalInfo = view.findViewById(R.id.tvPersonalInfo);
        layoutTickets = view.findViewById(R.id.layoutTickets);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvContactNumber = view.findViewById(R.id.tvContactNumber);
        tvContactMail = view.findViewById(R.id.tvContactMail);
        tvContactWhatsapp = view.findViewById(R.id.tvContactWhatsapp);
        tvPrivacyPolicy = view.findViewById(R.id.tvPrivacyPolicy);
        pagesRV = view.findViewById(R.id.pagesRV);
        pagesHeaderTV = view.findViewById(R.id.pagesHeaderTV);

        tvTicketsList = view.findViewById(R.id.tvTicketsList);
        tvAddTickets = view.findViewById(R.id.tvAddTickets);
        imageTickets = view.findViewById(R.id.imageTickets);
        imageProfile = view.findViewById(R.id.imageProfile);
        arrowASS = view.findViewById(R.id.arrowASS);
        versionTV = view.findViewById(R.id.versionTV);
        versionTV.setVisibility(View.GONE);
//        afterSaleServicesLL = view.findViewById(R.id.afterSaleServicesLL);
//        tvReturns.setVisibility(View.GONE);

        pagesRV.setVisibility(View.GONE);
        pagesHeaderTV.setVisibility(View.GONE);


        if (ProductsSingleton.getInstance().getPages().size() > 0) {
            pagesRV.setVisibility(View.VISIBLE);
            pagesHeaderTV.setVisibility(View.VISIBLE);
            pagesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            pagesAdapter = new PagesAdapter(ProductsSingleton.getInstance().getPages(), getContext(), this);
            pagesRV.setAdapter(pagesAdapter);
        }

        layoutProfile.setVisibility(View.GONE);
        ticketCV.setVisibility(View.GONE);
        layoutLogoutCV.setVisibility(View.GONE);


        layoutProfile.setOnClickListener(this);
        ticketCV.setOnClickListener(this);
        layoutLogoutCV.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        tvTicketsList.setOnClickListener(this);
        tvAddTickets.setOnClickListener(this);
        tvWallet.setOnClickListener(this);
        layoutLanguage.setOnClickListener(this);
        layoutShareApp.setOnClickListener(this);
        tvPersonalInfo.setOnClickListener(this);
        layoutTickets.setOnClickListener(this);

        tvAddress.setOnClickListener(this);
        tvContactNumber.setOnClickListener(this);
        tvContactMail.setOnClickListener(this);
        tvContactWhatsapp.setOnClickListener(this);
        tvPrivacyPolicy.setOnClickListener(this);
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (companySetting != null) {
            setViews();
        }
    }

    public void setViews() {
        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(promotion_reciever, new IntentFilter("ticketlist"));
        }
        CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (user != null) {
            layoutProfile.setVisibility(View.VISIBLE);
            ticketCV.setVisibility(View.GONE);
            layoutLogoutCV.setVisibility(View.VISIBLE);
            layoutLanguage.setVisibility(View.VISIBLE);
            layoutLogout.setVisibility(View.VISIBLE);
            layoutShareApp.setVisibility(View.VISIBLE);
            tvPersonalInfo.setVisibility(View.VISIBLE);

            tvAddress.setVisibility(View.VISIBLE);
            tvWallet.setVisibility(View.VISIBLE);

            if (companySetting.is_ticket_enable.equals("1")) {
                layoutTickets.setVisibility(View.VISIBLE);
                tvAddTickets.setVisibility(View.VISIBLE);
                tvTicketsList.setVisibility(View.VISIBLE);
            } else {
                layoutTickets.setVisibility(View.GONE);
                tvAddTickets.setVisibility(View.GONE);
                tvTicketsList.setVisibility(View.GONE);
            }

            if (user.hide_price.equals("1")) {
                tvWallet.setVisibility(View.GONE);
            } else {
                tvWallet.setVisibility(View.VISIBLE);
            }

        } else {
            layoutProfile.setVisibility(View.GONE);
            ticketCV.setVisibility(View.GONE);
            layoutLogoutCV.setVisibility(View.GONE);
            layoutLogout.setVisibility(View.GONE);
            layoutLanguage.setVisibility(View.VISIBLE);
            layoutShareApp.setVisibility(View.VISIBLE);
            tvWallet.setVisibility(View.GONE);
            layoutTickets.setVisibility(View.GONE);
            tvAddTickets.setVisibility(View.GONE);
            tvTicketsList.setVisibility(View.GONE);
        }

        tvPersonalInfo.setVisibility(View.GONE);
        tvAddress.setVisibility(View.GONE);
        tvWallet.setVisibility(View.GONE);

//        tvAfterSales.setVisibility(View.GONE);
//        tvmaintenances.setVisibility(View.GONE);
//        tvmyReturns.setVisibility(View.GONE);

        if (!companySetting.helpLine.equals("") || !companySetting.email.equals("") || !companySetting.whats_app.equals("")) {
            contactUsTVMF.setVisibility(View.VISIBLE);
        } else {
            contactUsTVMF.setVisibility(View.GONE);
        }

        String  input = companySetting.whats_app;
        input = input.replace(" ", "");
        tvContactWhatsapp.setText(input);
        tvContactMail.setText(companySetting.email);
        tvContactNumber.setText(companySetting.helpLine);

        if (companySetting.whats_app.equals("")) {
            tvContactWhatsapp.setVisibility(View.GONE);
//            viewWhatsapp.setVisibility(View.GONE);

        }
        if (companySetting.email.equals("")) {
            tvContactMail.setVisibility(View.GONE);
//            viewMail.setVisibility(View.GONE);

        }
        if (companySetting.helpLine.equals(""))
            tvContactNumber.setVisibility(View.GONE);

        if (!companySetting.tc_ar.equals("") || !companySetting.tc_en.equals("")) {
            tvPrivacyPolicy.setVisibility(View.VISIBLE);
        } else {
            tvPrivacyPolicy.setVisibility(View.GONE);
        }

        if (companySetting.facebook.equals(""))
            facebookIV.setVisibility(View.GONE);

        if (companySetting.instagram.equals(""))
            twitterIV.setVisibility(View.GONE);

        if (companySetting.twitter.equals(""))
            instagramIV.setVisibility(View.GONE);

        if (companySetting.snapchat.equals(""))
            snapchatIV.setVisibility(View.GONE);


/*
      if (isVisible){
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  ProductsSingleton.getInstance().getBasketview().setVisibility(View.INVISIBLE);
              }
          }, 100);
      }*/


        facebookIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUrl(companySetting.facebook);
            }
        });


        twitterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUrl(companySetting.twitter);
            }
        });

        instagramIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUrl(companySetting.instagram);
            }
        });

        snapchatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUrl(companySetting.snapchat);
            }
        });

    }


    private void OpenUrl(String ulr) {
        Uri uri = Uri.parse("" + ulr); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }


    @Override
    public void onResume() {
        final CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        if (companySetting != null) {
            setViews();
        }
        super.onResume();
    }

    private BroadcastReceiver promotion_reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("switch") != null) {
                startActivity(new Intent(getActivity(), TicketList.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
            }
        }
    };

    public FragmentMore() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            user = DatabaseManager.getInstance().getFirstOfClass(User.class);
            isArabic = AppController.setLocale();
            isVisible = true;

            if (isArabic) {
                //prepareDataList_ar();
            } else {
                //prepareDataList_en();
            }
        }
    }


    @Override
    public void onDestroy() {
        isVisible = false;
        super.onDestroy();
        if (user != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(promotion_reciever);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layoutProfile:
                if (profileCheck) {
                    tvPersonalInfo.setVisibility(View.VISIBLE);
                    tvAddress.setVisibility(View.VISIBLE);
                    tvWallet.setVisibility(View.VISIBLE);
                    imageProfile.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    profileCheck = false;
                } else if (!profileCheck) {
                    tvPersonalInfo.setVisibility(View.GONE);
                    tvAddress.setVisibility(View.GONE);
                    tvWallet.setVisibility(View.GONE);
                    imageProfile.setImageResource(R.drawable.ic_right_chevron);
                    profileCheck = true;
                }
                break;
            case R.id.afterSaleServicesLL:
                if (afterSSCheck) {
//                        tvAfterSales.setVisibility(View.VISIBLE);
//                        tvmaintenances.setVisibility(View.VISIBLE);
//                        tvmyReturns.setVisibility(View.VISIBLE);
                    arrowASS.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    afterSSCheck = false;
                } else if (!afterSSCheck) {
//                        tvAfterSales.setVisibility(View.GONE);
//                        tvmaintenances.setVisibility(View.GONE);
//                        tvmyReturns.setVisibility(View.GONE);
                    arrowASS.setImageResource(R.drawable.ic_right_chevron);
                    afterSSCheck = true;
                }
                break;

            case R.id.layoutLogout:
                logOut();
                break;


                /*case R.id.tvAfterSales:
                    Intent intent = new Intent(getActivity(), AfterSalesServicesActivity.class);
                    startActivity(intent);
                break;*/

                    /*case R.id.tvReturns:
                        Intent intent1 = new Intent(getActivity(), AfterSalesServicesMyReturnsActivity.class);
                        startActivity(intent1);
                    break;*/

                  /*  case R.id.tvmyReturns:
                    Intent intent2 = new Intent(getActivity(), ReturnListActivity.class);
                    startActivity(intent2);
                break;*/



             /*   case R.id.ourShowRoomLL:
                    Intent showRoomIntent = new Intent(getActivity(), OurShowRoomsActivity.class);
                    startActivity(showRoomIntent);
                break;*/



               /* case R.id.wishListLL:
                    Intent i = new Intent(getActivity(), MyWishListActivity.class);
                    startActivity(i);
                break;*/

                /*case R.id.tvmaintenances:
                    Intent i1 = new Intent(getActivity(), MaintenanceListActivity.class);
                    startActivity(i1);
                break;*/
            case R.id.layoutTickets:

                if (ticketCheck) {
                    tvTicketsList.setVisibility(View.GONE);
                    tvAddTickets.setVisibility(View.GONE);
                    imageTickets.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

                    ticketCheck = false;
                } else {
                    tvTicketsList.setVisibility(View.VISIBLE);
                    tvAddTickets.setVisibility(View.VISIBLE);
                    imageTickets.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    ticketCheck = true;
                }

                break;

            case R.id.layoutLanguage:
                if (UserSession.getInstance().getUserLanguage().equals(Constants.ENGLISH)) {
                    UserSession.getInstance().setUserLanguage(Constants.ARABIC);
                } else {
                    UserSession.getInstance().setUserLanguage(Constants.ENGLISH);
                }
                getActivity().finish();
                startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;


            case R.id.layoutShareApp:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;


            case R.id.tvPersonalInfo:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);

                break;

            case R.id.tvContactMail:
                contacUsMail();
                break;

            case R.id.tvWallet:
                //contacUsMail();
                startActivity(new Intent(getActivity(), WalletInformationActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;

            case R.id.tvContactNumber:
                  /*   if(isPermissionGranted()){
                            contacUsNumber();
                        }
                    */
                break;

            case R.id.tvContactWhatsapp:
                CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
                openWhatsApp("" + companySetting.whats_app);

                break;

            case R.id.tvAddress:
                Constants.fromore = true;
                startActivity(new Intent(getActivity(), ChooseAddressActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;

            case R.id.tvTicketsList:
                startActivity(new Intent(getActivity(), TicketList.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;

            case R.id.tvAddTickets:
                startActivity(new Intent(getActivity(), Ticket.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;
            case R.id.tvPrivacyPolicy:
                startActivity(new Intent(getActivity(), TermsAndConditionsActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_anim, 0);
                break;

        }
    }

    public void openWhatsApp(String whatsApp) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_VIEW);
        String url = "https://api.whatsapp.com/send?phone=" + whatsApp;
        sendIntent.setData(Uri.parse(url));
        startActivity(sendIntent);
    }

  /*  public void contacUsNumber(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+tvContactNumber.getText() ));
        startActivity(intent);
    }*/

    public void contacUsMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "" + tvContactMail.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    /*
        public  boolean isPermissionGranted() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (getActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG","Permission is granted");
                    return true;
                } else {

                    Log.v("TAG","Permission is revoked");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            android.Manifest.permission.CALL_PHONE}, 1);

                    return false;
                }
            }
            else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG","Permission is granted");
                return true;
            }
        }


        @Override
        public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
            switch (requestCode) {

                case 1: {

                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                        contacUsNumber();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

            }
        }
    */
    private void logOut() {
        User user1 = DatabaseManager.getInstance().getFirstOfClass(User.class);
        LogOut logOut = new LogOut();
        if (user1 != null)
            logOut.setUser_id(user1.userId);
        APIManager.getInstance().setLogOut(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        if (jsonObject.optString("code").equals("200")) {
                            User user1 = DatabaseManager.getInstance().getFirstOfClass(User.class);
                            if (user1.hide_price.equals("1")) {
                                CoreManager.getInstance().removeUserData();
                                Toast.makeText(getActivity(), "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                user = null;
                                UserSession.getInstance().setSaveHomeAddressObject("");
                                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                CoreManager.getInstance().removeUserData();
                                Toast.makeText(getActivity(), "" + getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                                user = null;
                                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exceptin", e.toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, logOut, getActivity());
    }

    @Override
    public void pageClick(String page_id, int position, String pageName) {
        APIManager.getInstance().getPages(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                showProgress(false);
                Constants.checkaddress = false;
                if (z) {
                    if (response.contains("Access denied") || response.contains("Invalid Key!")) {
                        CoreManager.getInstance().removeUserData();
                        Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200")) {

                                String pageAr = jsonObject.getString("page_ar");
                                String pageEn = jsonObject.getString("page_en");

                                Intent intent = new Intent(getContext(), PagesActivity.class);
                                intent.putExtra("pageDetailAr", "" + pageAr);
                                intent.putExtra("pageDetailEn", "" + pageEn);
                                intent.putExtra("pageName", pageName );
                                startActivity(intent);
                            }


                        } catch (Exception e) {
                            Log.e("EXcePRO", e.toString());
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, page_id);
        Log.d("pages_request", new Gson().toJson(new PagesRequest("" + page_id)));
    }


    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(getActivity());
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
}
