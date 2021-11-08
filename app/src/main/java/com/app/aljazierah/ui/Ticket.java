package com.app.aljazierah.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.CustomerServiceAdapter;
import com.app.aljazierah.adapter.SelectCategoryAdapter;
import com.app.aljazierah.adapter.SelectPriceAdapter;
import com.app.aljazierah.adapter.SkuAdapter;
import com.app.aljazierah.object.CallcenterAgent;
import com.app.aljazierah.object.ComplaintObject;
import com.app.aljazierah.object.OrderInformation;
import com.app.aljazierah.object.ProductList;
import com.app.aljazierah.object.TicketCategory;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.AddComplaint;
import com.app.aljazierah.object.request.WalletRequest;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.IConverter;
import com.app.aljazierah.utils.ImageConverterTask;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static com.app.aljazierah.utils.Utils.getRealPathFromURI_API11to19;
import static com.app.aljazierah.utils.Utils.getRealPathFromURI_BelowAPI11;

public class Ticket extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IConverter {
    private boolean isArabic;
    ProgressDialog mProgressDialog;
    private Spinner spinnercategory, spinnercustomer, spinnerordericon, spinnersku;
    private TextView label_customer, label_icon, label_sku, tv_choosefile, image_name, tv_image_cancel;
    private View viewspinnercustomer, viewspinnerordericon, viewspinnersku;
    Uri picUri = null;
    private final static int CAMERA_RQ = 6969;
    private EditText et_batches, et_quantity, et_complaint;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    ProductList productList = new ProductList();
    ArrayList<OrderInformation> orderInformationArrayList = new ArrayList<>();
    ArrayList<CallcenterAgent> callcenterAgentArrayList = new ArrayList<>();
    ArrayList<TicketCategory> ticketCategoryArrayList = new ArrayList<>();
    ArrayList<ProductList> productListArrayList = new ArrayList<>();
    private File file;
    private ImageView iv_batch;
    private boolean fromgallery;
    private Button btn_submit;

    private String category_id="";
    private String order_code="";
    private String order_id="";
    private String agent_id="";
    private String callcenter_user="";
    private String sku_in="";
    private TextView textview_location;
//    private RelativeLayout badge_viewFH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());

        setContentView(R.layout.activity_ticket);
        setViews();
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        loadComplaintData();

    }

    private void setViews() {
        setHeader();
        isArabic = AppController.setLocale();
        spinnercategory = findViewById(R.id.spinnercategory);
        label_customer = findViewById(R.id.label_customer);
        viewspinnercustomer = findViewById(R.id.viewspinnercustomer);
        spinnercustomer = findViewById(R.id.spinnercustomer);
        label_icon = findViewById(R.id.label_icon);
        viewspinnerordericon = findViewById(R.id.viewspinnerordericon);
        spinnerordericon = findViewById(R.id.spinnerordericon);

        textview_location = findViewById(R.id.textview_location);
//        badge_viewFH = findViewById(R.id.badge_viewFH);
//        badge_viewFH.setVisibility(View.INVISIBLE);
        textview_location.setVisibility(View.INVISIBLE);


        label_sku = findViewById(R.id.label_sku);
        viewspinnersku = findViewById(R.id.viewspinnersku);
        spinnersku = findViewById(R.id.spinnersku);
        et_batches = findViewById(R.id.et_batches);
        et_quantity = findViewById(R.id.et_quantity);
        et_complaint = findViewById(R.id.et_complaint);
        tv_choosefile = findViewById(R.id.tv_choosefile);
        image_name = findViewById(R.id.image_name);
        tv_image_cancel = findViewById(R.id.tv_image_cancel);
        iv_batch=findViewById(R.id.iv_batch);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        tv_image_cancel.setOnClickListener(this);
        initListeners();

        if (isArabic) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            et_complaint.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            et_complaint.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    private void initListeners(){

        tv_choosefile.setOnClickListener(this);
        spinnersku.setOnItemSelectedListener(this);
        spinnerordericon.setOnItemSelectedListener(this);
        spinnercustomer.setOnItemSelectedListener(this);
        spinnercategory.setOnItemSelectedListener(this);

            viewspinnercustomer.setVisibility(View.GONE);
            label_customer.setVisibility(View.GONE);
            viewspinnersku.setVisibility(View.GONE);
            label_sku.setVisibility(View.GONE);
        iv_batch.setVisibility(View.GONE);
            et_batches.setVisibility(View.GONE);
        et_quantity.setVisibility(View.GONE);
        image_name.setText("");

    }



    private void setHeader() {
        View toolbar = findViewById(R.id.toolbar);
        TextView title_textview = toolbar.findViewById(R.id.tvToolbarTitleTCA);
        ImageView iv_backbtn = toolbar.findViewById(R.id.imageViewBackButton);
        iv_backbtn.setVisibility(View.VISIBLE);
        title_textview.setText(getResources().getString(R.string.st_tickets));
        iv_backbtn.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }


    private void loadComplaintData() {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        if (user != null) {
            Log.e("Ex_cartitems", user.authToken);
            showProgress(true);
            APIManager.getInstance().load_complaint_data(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {

                    if (z) {
                        try {
                            ComplaintObject complaintObject = new ComplaintObject();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray ticket_categories_array = jsonObject.getJSONArray("ticket_categories");
                            JSONArray ticket_order_information = jsonObject.getJSONArray("order_information");
                            JSONArray ticket_callcenter_agents = jsonObject.getJSONArray("callcenter_agents");
                            JSONArray ticket_product_list = jsonObject.getJSONArray("product_list");
                            ArrayList<TicketCategory> ticketCategories = new ArrayList<>();
                            ArrayList<OrderInformation> orderInformations = new ArrayList<>();
                            ArrayList<CallcenterAgent> callcenterAgents = new ArrayList<>();
                            ArrayList<ProductList> productLists = new ArrayList<>();
                            String batch_url=jsonObject.getString("batch_url");
                            Picasso.with(Ticket.this).load(batch_url).into(iv_batch);

                            for (int i = 0; i < ticket_categories_array.length(); i++) {
                                JSONObject jsonObject1 = ticket_categories_array.getJSONObject(i);
                                TicketCategory ticketCategory = new TicketCategory();
                                ticketCategory.setId(jsonObject1.getString("id"));
                                ticketCategory.setTitleEnglish(jsonObject1.getString("title_english"));
                                ticketCategory.setTitleArabic(jsonObject1.getString("title_arabic"));
                                ticketCategories.add(ticketCategory);

                            }

                            for (int i = 0; i < ticket_order_information.length(); i++) {
                                JSONObject jsonObject1 = ticket_order_information.getJSONObject(i);
                                OrderInformation orderInformation = new OrderInformation();
                                orderInformation.setId(jsonObject1.getString("id"));
                                orderInformation.setTotal_price(jsonObject1.getString("total_price"));
                                orderInformation.setOrder_code(jsonObject1.getString("order_code"));
                                orderInformation.setOrder_created_date(jsonObject1.getString("order_created_date"));

                                orderInformations.add(orderInformation);

                            }

                            for (int i = 0; i < ticket_callcenter_agents.length(); i++) {
                                JSONObject jsonObject1 = ticket_callcenter_agents.getJSONObject(i);
                                CallcenterAgent callcenterAgent = new CallcenterAgent();
                                callcenterAgent.setId(jsonObject1.getString("id"));
                                callcenterAgent.setFullname(jsonObject1.getString("fullname"));

                                callcenterAgents.add(callcenterAgent);

                            }

                            for (int i = 0; i < ticket_product_list.length(); i++) {
                                JSONObject jsonObject1 = ticket_product_list.getJSONObject(i);
                                ProductList productList = new ProductList();
                                productList.setId(jsonObject1.getString("id"));
                                productList.setNameAr(jsonObject1.getString("name_ar"));
                                productList.setNameEn(jsonObject1.getString("name_en"));
                                productLists.add(productList);

                            }
                            complaintObject.setCallcenterAgents(callcenterAgents);
                            complaintObject.setOrderInformation(orderInformations);
                            complaintObject.setProductList(productLists);
                            complaintObject.setTicketCategories(ticketCategories);
                            setCatgorySpinner(ticketCategories);
                            setCallCenterSpinner(callcenterAgents);
                            setOrderSpinner(orderInformations);
                            setSkuSpinner(productLists);
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

    private void addComplaintData() {
        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);

        if (user != null) {
          if(category_id.equals("1") || category_id.equals("4") || category_id.equals("2")) {
              if(order_id==null)
                  order_id="no";
              if(order_id.equals("no") || order_id.equals("")){
                  Toast.makeText(Ticket.this, "Select Order", Toast.LENGTH_SHORT).show();
              }
              else if(et_complaint.getText().toString().equals("")){
                  Toast.makeText(Ticket.this, "Enter message", Toast.LENGTH_SHORT).show();
              }
              else {

                  AddComplaint addComplaint =new AddComplaint(user.userId,order_code,"",
                          category_id,et_complaint.getText().toString(),"","",
                          imagetoBase64(),"");
                  SubmitData(addComplaint);
              }
          }
          else if(category_id.equals("5")) {
              if(agent_id==null)
                  agent_id="no";

              if(agent_id.equals("no") || agent_id.equals("")){
                  Toast.makeText(Ticket.this, "Select Employee", Toast.LENGTH_SHORT).show();
              }
              else if(et_complaint.getText().toString().equals("")){
                  Toast.makeText(Ticket.this, "Enter message", Toast.LENGTH_SHORT).show();
              }
              else {

                  AddComplaint addComplaint =new AddComplaint(user.userId,"",agent_id,
                          category_id,et_complaint.getText().toString(),"","",
                          imagetoBase64(),"");
                  SubmitData(addComplaint);
              }
          }
          else if(category_id.equals("3")) {

              if(sku_in==null)
                  sku_in="no";

              if(order_id==null)
                  order_id="no";

              if(sku_in.equals("no") || sku_in.equals("")){
                  Toast.makeText(Ticket.this, "Select SKU", Toast.LENGTH_SHORT).show();
              }
              else  if(order_code.equals("no") || order_code.equals("")){
                  Toast.makeText(Ticket.this, "Select Order", Toast.LENGTH_SHORT).show();
              }
              else if(et_quantity.getText().toString().equals("")){
                  Toast.makeText(Ticket.this, "Enter quantity", Toast.LENGTH_SHORT).show();
              }
              else if(et_batches.getText().toString().equals("")){
                  Toast.makeText(Ticket.this, "Enter batches", Toast.LENGTH_SHORT).show();
              }
              else if(et_complaint.getText().toString().equals("")){
                  Toast.makeText(Ticket.this, "Enter message", Toast.LENGTH_SHORT).show();
              }
              else {

                  AddComplaint addComplaint =new AddComplaint(user.userId,order_code,"",
                          category_id,et_complaint.getText().toString(),et_batches.getText().toString(),
                          et_quantity.getText().toString(),
                          imagetoBase64(),sku_in);
                  SubmitData(addComplaint);
              }
          }

        }
    }

    public void SubmitData(AddComplaint add_complaint){

        String response=new Gson().toJson(add_complaint);
        Log.e("test",response);

                    showProgress(true);
            APIManager.getInstance().add_complaint(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {

                    if (z) {
                        try {
                            showProgress(false);
                            if(new JSONObject(response).getString("status").equals("1")){
                              new Handler().postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      Intent i = new Intent("ticketlist");
                                      i.putExtra("switch", "1");



                                      LocalBroadcastManager.getInstance(Ticket.this).sendBroadcast(i);
                                      finish();
                                  }
                              },500);
                            }
                            else{
                                Toast.makeText(Ticket.this, new JSONObject(response).getString("Message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.e("Ex_cartitems", e.toString());
                            e.printStackTrace();
                        }

                    } else {
                    }


                }
            }, add_complaint);
    }



    private void setCatgorySpinner(ArrayList<TicketCategory> ticketCategories) {


        TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setId("no");
        ticketCategory.setTitleArabic(getResources().getString(R.string.st_selecticket));
        ticketCategory.setTitleEnglish(getResources().getString(R.string.st_selecticket));
        ticketCategoryArrayList.add(ticketCategory);
        ticketCategoryArrayList.addAll(ticketCategories);
        SelectCategoryAdapter selectCategoryAdapter = new SelectCategoryAdapter(this, ticketCategoryArrayList);
        spinnercategory.setAdapter(selectCategoryAdapter);

    }

    private void setCallCenterSpinner(ArrayList<CallcenterAgent> ticketCategories) {

        CallcenterAgent ticketCategory = new CallcenterAgent();
        ticketCategory.setId("no");
        ticketCategory.setFullname(getResources().getString(R.string.st_selectservice));
        callcenterAgentArrayList.add(ticketCategory);
        callcenterAgentArrayList.addAll(ticketCategories);
        CustomerServiceAdapter selectCategoryAdapter = new CustomerServiceAdapter(this, callcenterAgentArrayList);
        spinnercustomer.setAdapter(selectCategoryAdapter);
    }


    private void setOrderSpinner(ArrayList<OrderInformation> ticketCategories) {

        OrderInformation orderInformation = new OrderInformation();
        orderInformation.setId("no");
        orderInformation.setTotal_price(getResources().getString(R.string.st_selectorder));
        orderInformationArrayList.add(orderInformation);
        orderInformationArrayList.addAll(ticketCategories);
        SelectPriceAdapter selectCategoryAdapter = new SelectPriceAdapter(this, orderInformationArrayList);
        spinnerordericon.setAdapter(selectCategoryAdapter);
    }

    private void setSkuSpinner(ArrayList<ProductList> ticketCategories) {


        productList.setId("no");
        if (isArabic) {
            productList.setNameAr(getResources().getString(R.string.st_chooses));
        } else {
            productList.setNameEn(getResources().getString(R.string.st_chooses));
        }

        productListArrayList.add(productList);
        productListArrayList.addAll(ticketCategories);
        SkuAdapter selectCategoryAdapter = new SkuAdapter(this, productListArrayList);
        spinnersku.setAdapter(selectCategoryAdapter);
    }

    private void showProgress(boolean show) {
        if (show) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(Ticket.this);
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void addVideo() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Ticket.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_video_pic, null);
        Button   take_pic =  dialogView.findViewById(R.id.take_pic);
        Button  gallery =  dialogView.findViewById(R.id.gallery);

        dialogBuilder.setView(dialogView);

        TextView editText = (TextView) dialogView.findViewById(R.id.label_field);


        editText.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
        take_pic.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);
        gallery.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_regular.ttf"), Typeface.NORMAL);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBackButton:
                finish();
                overridePendingTransition(0, R.anim.exit_anim);
                break;
            case R.id.tv_choosefile:
                if (Build.VERSION.SDK_INT >= 23) {


                if (!hasPermissions(this, PERMISSIONS)) {
                    Toast.makeText(Ticket.this, "Enable Permissions in Settings", Toast.LENGTH_SHORT).show();
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
            case R.id.btn_submit:
                addComplaintData();
                break;




        }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnercategory:
                setCategorySpinnerSelection(position);
                break;
            case R.id.spinnerordericon:
                order_code=orderInformationArrayList.get(position).getOrder_code();
                order_id=orderInformationArrayList.get(position).getId();
                break;
            case R.id.spinnercustomer:
                callcenter_user=callcenterAgentArrayList.get(position).getFullname();
                agent_id=callcenterAgentArrayList.get(position).getId();
                break;
            case R.id.spinnersku:
                    sku_in=productListArrayList.get(position).getId();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void setCategorySpinnerSelection(int position){
        category_id=ticketCategoryArrayList.get(position).getId();
        if(ticketCategoryArrayList.get(position).getId().equals("3")){
            viewspinnercustomer.setVisibility(View.GONE);
            label_customer.setVisibility(View.GONE);
            viewspinnersku.setVisibility(View.VISIBLE);
            label_sku.setVisibility(View.VISIBLE);
            et_quantity.setVisibility(View.VISIBLE);
            et_batches.setVisibility(View.VISIBLE);
            spinnerordericon.setVisibility(View.VISIBLE);
            label_icon.setVisibility(View.VISIBLE);
            iv_batch.setVisibility(View.VISIBLE);

        }
        else if(ticketCategoryArrayList.get(position).getId().equals("5")){
            viewspinnercustomer.setVisibility(View.VISIBLE);
            label_customer.setVisibility(View.VISIBLE);
            viewspinnersku.setVisibility(View.GONE);
            label_sku.setVisibility(View.GONE);
            et_quantity.setVisibility(View.GONE);
            et_batches.setVisibility(View.GONE);
            spinnerordericon.setVisibility(View.GONE);
            label_icon.setVisibility(View.GONE);
            iv_batch.setVisibility(View.GONE);
        }
        else{
            viewspinnercustomer.setVisibility(View.GONE);
            label_customer.setVisibility(View.GONE);
            viewspinnersku.setVisibility(View.GONE);
            label_sku.setVisibility(View.GONE);
            et_quantity.setVisibility(View.GONE);
            et_batches.setVisibility(View.GONE);
            spinnerordericon.setVisibility(View.VISIBLE);
            label_icon.setVisibility(View.VISIBLE);
            iv_batch.setVisibility(View.GONE);

        }

        Log.e(ticketCategoryArrayList.get(position).getId(),ticketCategoryArrayList.get(position).getTitleEnglish());


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == CAMERA_RQ) {

                if (resultCode == RESULT_OK) {


                    showProgress(true);
                    fromgallery=false;
                    new ImageConverterTask(Ticket.this,
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

    @Override
    public void getConvertedFile(File file) {
        try {
            this.file=file;
            image_name.setText(file.getName());
            Log.e("EXc",imagetoBase64());

            showProgress(false);
        } catch (Exception e) {
            Log.e("EXc",e.toString());
            Toast.makeText(Ticket.this, "Image not supported", Toast.LENGTH_SHORT).show();
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
}
