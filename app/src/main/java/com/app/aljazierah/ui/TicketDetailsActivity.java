package com.app.aljazierah.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.TicketReplyAdapter;
import com.app.aljazierah.object.TicketDetails;
import com.app.aljazierah.object.TicketReply;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.ComplaintReply;
import com.app.aljazierah.object.request.GetComplaintDetails;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.IConverter;
import com.app.aljazierah.utils.ImageConverterTask;
import com.roam.appdatabase.DatabaseManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static com.app.aljazierah.ui.Ticket.hasPermissions;
import static com.app.aljazierah.utils.Constants.load_tickets;
import static com.app.aljazierah.utils.Utils.getRealPathFromURI_API11to19;
import static com.app.aljazierah.utils.Utils.getRealPathFromURI_BelowAPI11;

public class TicketDetailsActivity extends AppCompatActivity implements View.OnClickListener, IConverter {
    private boolean isArabic;


    private TextView tv_no, tv_status, tv_category, tv_body, tv_replies, tv_ordernumber, tv_created,
            tv_lupdated, tv_file,tv_choosefile, image_name, tv_image_cancel;
    private EditText et_complain;
    private ListView list_reply;
    ProgressDialog mProgressDialog;
    Dialog Preview_Dialog;
    TicketDetails getComplaintDetails;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    Uri picUri = null;
    private File file;
    private boolean fromgallery;
    private final static int CAMERA_RQ = 6969;
    private View view_listreply;
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketdetailscreen);


        isArabic = AppController.setLocale();
        setViews();
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        getTicketDetails();
    }

    private void setViews() {
        setHeader();
        TextView label_no = findViewById(R.id.label_no);
        TextView label_status = findViewById(R.id.label_status);

        TextView label_body = findViewById(R.id.label_body);
        TextView label_replies = findViewById(R.id.label_replies);
        TextView label_created = findViewById(R.id.label_created);
        TextView label_lupdated = findViewById(R.id.label_lupdated);
        TextView label_file = findViewById(R.id.label_file);
        TextView label_ordernumber=findViewById(R.id.label_ordernumber);
        Button btn_reply = findViewById(R.id.btn_reply);
        tv_no = findViewById(R.id.tv_no);
        tv_ordernumber = findViewById(R.id.tv_ordernumber);
        tv_status = findViewById(R.id.tv_status);
        tv_category = findViewById(R.id.tv_category);
        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);
        tv_body = findViewById(R.id.tv_body);
        tv_replies = findViewById(R.id.tv_replies);
        tv_lupdated = findViewById(R.id.tv_lupdated);
        tv_created = findViewById(R.id.tv_created);
        tv_file = findViewById(R.id.tv_file);
        tv_choosefile = findViewById(R.id.tv_choosefile);
        image_name = findViewById(R.id.image_name);
        tv_image_cancel = findViewById(R.id.tv_image_cancel);
        View view_reply = findViewById(R.id.viewreply);
        view_listreply=findViewById(R.id.view_listreply);

        label_no.setText(getResources().getString(R.string.st_no)+" :"+" ");
        label_status.setText(getResources().getString(R.string.st_status)+" :"+" ");
        label_replies.setText(getResources().getString(R.string.st_replies)+" :"+" ");
        label_created.setText(getResources().getString(R.string.st_created)+" :"+" ");
        label_lupdated.setText(getResources().getString(R.string.st_lupdated)+" :"+" ");
        label_body.setText(getResources().getString(R.string.st_complaints)+" :"+" ");

        label_ordernumber.setText(getResources().getString(R.string.st_ordernumber)+" :"+" ");
        label_file.setText(getResources().getString(R.string.st_files)+" :");

        if(getIntent().getStringExtra("status").equals("0") ||
                getIntent().getStringExtra("status").equals("3")){
            view_reply.setVisibility(View.GONE);
        }
        else{
            view_reply.setVisibility(View.VISIBLE);
        }

        et_complain = findViewById(R.id.et_complain);
        list_reply = findViewById(R.id.list_reply);
        btn_reply.setOnClickListener(this);

        tv_file.setOnClickListener(this);
        tv_image_cancel.setOnClickListener(this);
        tv_choosefile.setOnClickListener(this);


    }
    private void setHeader(){
        View toolbar=findViewById(R.id.toolbar);
        TextView title_textview=toolbar.findViewById(R.id.tvToolbarTitleTCA);
        ImageView iv_backbtn=toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        title_textview.setText(getResources().getString(R.string.st_ticketdetail));
        iv_backbtn.setOnClickListener(this);
    }



    public void getTicketDetails() {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        showProgress(true);
        APIManager.getInstance().complaint_detail(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                if (z) {
                    try {
                        showProgress(false);
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject ticket=jsonObject.getJSONObject("ticket");



                        getComplaintDetails = new TicketDetails();

                        getComplaintDetails.setTicketStatus(ticket.getString("status"));
                        getComplaintDetails.setCategoryId(ticket.getString("category_id"));
                        getComplaintDetails.setTicketBody(ticket.getString("ticket_body"));
                        getComplaintDetails.setReplies(ticket.getString("replies"));
                        getComplaintDetails.setTicketOrderCode(ticket.getString("ticket_order_code"));
                        getComplaintDetails.setTicketFileAttachment(ticket.getString("file_attachment"));
                        getComplaintDetails.setTicketSdt(ticket.getString("ticket_sdt"));
                        getComplaintDetails.setTicketUdt(ticket.getString("ticket_udt"));
                        getComplaintDetails.setCategoryTitleArabic(ticket.getString("category_title_arabic"));
                        getComplaintDetails.setCategoryTitleEnglish(ticket.getString("category_title_english"));
                        ArrayList<TicketReply> ticketReplyArrayList=new ArrayList<>();
                        JSONArray jsonArray=ticket.getJSONArray("reply");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            TicketReply ticketReply=new TicketReply();
                            ticketReply.setClient_name(jsonObject1.getString("client_name"));
                            ticketReply.setReply_person_id(jsonObject1.getString("reply_person_id"));
                            ticketReply.setReply_message(jsonObject1.getString("reply_message"));
                            ticketReply.setReply_file_name(jsonObject1.getString("reply_file_name"));
                            ticketReply.setDate(jsonObject1.getString("date"));
                            ticketReplyArrayList.add(ticketReply);

                        }
                        getComplaintDetails.setReply(ticketReplyArrayList);
                        if(getComplaintDetails.getReply().size()<1){
                            view_listreply.setVisibility(View.GONE);
                        }
                        else{
                            view_listreply.setVisibility(View.VISIBLE);
                        }
                        setDetails(getComplaintDetails);
                        setReplyList();


                    } catch (Exception e) {
                        Log.e("Ex_ticketdetails", e.toString());
                        e.printStackTrace();
                    }

                } else {
                    showProgress(false);
                    Toast.makeText(TicketDetailsActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }


            }
        }, new GetComplaintDetails(user.userId, getIntent().getStringExtra("id")));
    }

    public void SubmitTicketReply() {

        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        ComplaintReply complaintReply=new ComplaintReply(user.userId,getIntent().getStringExtra("id"),et_complain.getText().toString(),
                                                        imagetoBase64());
        showProgress(true);

        APIManager.getInstance().add_complaint_reply(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                if (z) {
                    try {
                        showProgress(false);
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getString("status").equals("1")){
                           JSONObject inserted_row =jsonObject.getJSONObject("inserted_row");

                           TicketReply ticketReply=new TicketReply();
                           ticketReply.setDate(inserted_row.getString("date"));
                            ticketReply.setClient_name(inserted_row.getString("client_name"));
                            ticketReply.setReply_message(inserted_row.getString("reply_message"));
                            ticketReply.setReply_file_name(inserted_row.getString("reply_file_name"));
                            ArrayList<TicketReply> ticketReplyArrayList=new ArrayList<>();
                            et_complain.setText("");
                            ticketReplyArrayList.add(ticketReply);
                            if(getComplaintDetails.getReply().size()>0){
                                ticketReplyArrayList.addAll(getComplaintDetails.getReply());

                            }
                            else{
                                getComplaintDetails.getReply().addAll(ticketReplyArrayList);
                            }
                            if(getComplaintDetails.getReply().size()<1){
                                view_listreply.setVisibility(View.GONE);
                            }
                            else{
                                view_listreply.setVisibility(View.VISIBLE);
                            }

                            TicketReplyAdapter ticketReplyAdapter=new TicketReplyAdapter(TicketDetailsActivity.this,
                                    TicketDetailsActivity.this,ticketReplyArrayList);
                            list_reply.setAdapter(ticketReplyAdapter);
                            setListViewHeightBasedOnChildren(list_reply);
                            InputMethodManager imm = (InputMethodManager) TicketDetailsActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            load_tickets=true;
                        }



                    } catch (Exception e) {
                        Log.e("Ex_ticketdetailsreply", e.toString());
                        e.printStackTrace();
                    }

                } else {
                    showProgress(false);
                    Toast.makeText(TicketDetailsActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }


            }
        }, complaintReply);
    }

    private void setDetails(TicketDetails getComplaintDetails) {
        tv_no.setText(getIntent().getStringExtra("id"));



        if (isArabic) {
            tv_category.setText(getComplaintDetails.getCategoryTitleArabic());
        } else {
            tv_category.setText(getComplaintDetails.getCategoryTitleEnglish());
        }

        tv_body.setText(getComplaintDetails.getTicketBody());
        tv_replies.setText(getComplaintDetails.getReplies());
        tv_ordernumber.setText(getComplaintDetails.getTicketOrderCode());
        tv_created.setText(getComplaintDetails.getTicketSdt());
        tv_lupdated.setText(getComplaintDetails.getTicketUdt());
        tv_file.setText(getComplaintDetails.getTicketFileAttachment());
        if (!getComplaintDetails.getTicketFileAttachment().contains("pdf")) {
          //  ImagePreview(getComplaintDetails.getTicketFileAttachment());
        }

        if (getComplaintDetails.getTicketStatus().equals("1")) {
            tv_status.setText(getResources().getString(R.string.st_pending));

        } else if (getComplaintDetails.getTicketStatus().equals("2")) {
            tv_status.setText(getResources().getString(R.string.st_open));

        } else if (getComplaintDetails.getTicketStatus().equals("3")) {
            tv_status.setText(getResources().getString(R.string.st_complete));

        } else if (getComplaintDetails.getTicketStatus().equals("4")) {
            tv_status.setText(getResources().getString(R.string.st_client));


        } else if (getComplaintDetails.getTicketStatus().equals("0")) {
            tv_status.setText(getResources().getString(R.string.st_delete));


        }

        if(getComplaintDetails.getTicketFileAttachment().equals("")){
            findViewById(R.id.label_file).setVisibility(View.GONE);
            tv_file.setVisibility(View.GONE);
        }

        if(getComplaintDetails.getTicketOrderCode().equals("")){
            findViewById(R.id.label_ordernumber).setVisibility(View.GONE);
            tv_ordernumber.setVisibility(View.GONE);
        }
        try {
            if (getComplaintDetails.getTicketOrderCode() == null) {
                findViewById(R.id.label_ordernumber).setVisibility(View.GONE);
                tv_ordernumber.setVisibility(View.GONE);
            }

        }catch (Exception ex){

        }
    }

    public void ImagePreview(String file) {
        Preview_Dialog = new Dialog(TicketDetailsActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        Preview_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Preview_Dialog.setCancelable(true);
        Preview_Dialog.setContentView(R.layout.preview_image);
        ImageView ivPreview = Preview_Dialog.findViewById(R.id.iv_preview_image);

        Picasso.with(TicketDetailsActivity.this).load("https://berain.com.sa/oms/cms_attachments/ticket_files/"+file).into(ivPreview);

        Preview_Dialog.show();

    }

    private void setReplyList(){
        TicketReplyAdapter ticketReplyAdapter=new TicketReplyAdapter(TicketDetailsActivity.this,
                TicketDetailsActivity.this,getComplaintDetails.getReply());
            list_reply.setAdapter(ticketReplyAdapter);
        setListViewHeightBasedOnChildren(list_reply);
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(TicketDetailsActivity.this);
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
        case R.id.tv_file:
            ImagePreview(getComplaintDetails.getTicketFileAttachment());
            break;
        case R.id.tv_choosefile:
            if (Build.VERSION.SDK_INT >= 23) {


                if (!hasPermissions(this, PERMISSIONS)) {
                    Toast.makeText(TicketDetailsActivity.this, "Enable Permissions in Settings", Toast.LENGTH_SHORT).show();
                }
                else{
                    addVideo();
                }
            }
            else{
                addVideo();
            }
            break;
        case R.id.tv_image_cancel:
            image_name.setText("");
            if(file!=null){
                if(!fromgallery){
                    file.delete();
                }
            }
            break;

        case R.id.btn_reply:
            if(!et_complain.getText().equals("")) {
                SubmitTicketReply();
            }
            else{
                Toast.makeText(TicketDetailsActivity.this, "Submit Message First", Toast.LENGTH_SHORT).show();
            }
            break;
    }
    }


    private void addVideo() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TicketDetailsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_video_pic, null);
        Button   take_pic =  dialogView.findViewById(R.id.take_pic);
        Button  gallery =  dialogView.findViewById(R.id.gallery);

        dialogBuilder.setView(dialogView);

        TextView editText = (TextView) dialogView.findViewById(R.id.label_field);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                TakeAPic();


            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                GalleryPic();


            }
        });


    }
    public void TakeAPic() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        picUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_RQ);


    }
    public void GalleryPic() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 101);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgress(false);
        if (Preview_Dialog != null && Preview_Dialog.isShowing()) {
            Preview_Dialog.dismiss();
        }
    }

    @Override
    public void getConvertedFile(File file) {
        try {
            this.file=file;
            image_name.setText(file.getName());
            Log.e("EXc",imagetoBase64());

            showProgress(false);
        } catch (Exception e) {
            Log.e("EXc",e.toString());
            Toast.makeText(TicketDetailsActivity.this, "Image not supported", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String imagetoBase64(){
        if(file!=null){
            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            return encodedImage;
        }
        return "";

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == CAMERA_RQ) {

                if (resultCode == RESULT_OK) {


                    showProgress(true);
                    fromgallery=false;
                    new ImageConverterTask(TicketDetailsActivity.this,
                            picUri, false, this).execute();

                }
            }

            if (requestCode == 101) {


                if (data != null) {


                    picUri = data.getData();
                    if (picUri != null) {
                        if (Build.VERSION.SDK_INT < 11) {
                            file = new File(getRealPathFromURI_BelowAPI11(getApplicationContext(), picUri));
                        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 19) {
                            file = new File(getRealPathFromURI_API11to19(getApplicationContext(), picUri));
                        } else if (Build.VERSION.SDK_INT > 19) {
                            file = new File(getRealPathFromURI_API11to19(getApplicationContext(), picUri));
                        }

                        picUri = Uri.fromFile(file);
                        image_name.setText(file.getName());
                        fromgallery=true;



                    }

                }
            }
        } catch (Exception ex) {
            Log.e("Ex_OnActivityResult", ex.toString());
        }
    }
}

