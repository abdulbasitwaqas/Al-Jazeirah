package com.app.aljazierah.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.SelectionListAdapter;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.AfterSalesServicesMyReturnReq;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.APIService;
import com.app.aljazierah.utils.AddHeaderInterceptorForImg;
import com.app.aljazierah.utils.IConverter;
import com.app.aljazierah.utils.ImageConverterTask;
import com.app.aljazierah.utils.RequestPermissionHandler;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.aljazierah.utils.Constants.BASE_URL;
import static com.app.aljazierah.utils.Utils.getRealPathFromURI_API11to19;
import static com.app.aljazierah.utils.Utils.getRealPathFromURI_BelowAPI11;

public class AfterSalesServicesMyReturnsActivity extends AppCompatActivity implements IConverter {
    private TextView textview_location, tvToolbarTitleTCA, tv_logout, showImageIVASS;
    private ImageView imageViewBackButton;
    private ProgressDialog mProgressDialog;
    private TextInputEditText fullNameET, invoiceNoET, invoiceDateET, mobileNumET, emailET, cityET, regionET, probDescriptionET;
    private CheckBox termsAndConditionCheckBox;
    private Button submitBtn;
    private RadioGroup warrantyTypeRBG;
    private String warrenty = "";
    private CardView fullNameCV, invoiceNumCV, invoiceDateCV, phoneNoCV, emailCV, cityCV, regionCV, problemDesCV;
    final Calendar myCalendar = Calendar.getInstance();
    private String orderID = "", orderNumber = "", invoiceNumber = "", invoiceDate = "";
    private User user;

    private String filePath;
    private File finalFile;
    private Uri picUri = null;
    private final static int CAMERA_RQ = 102;
    private boolean permissonCheck = false;
    private RequestPermissionHandler mRequestPermissionHandler;
    private APIService service;
    private Retrofit retrofit;
    private boolean fromgallery;
    private String currentPhotoPath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppController.updateResources(this, UserSession.getInstance().getUserLanguage());
        mRequestPermissionHandler = new RequestPermissionHandler();
        setContentView(R.layout.activity_after_sales_services_my_return);

        warrenty = ""+getResources().getString(R.string.inside_warranty);
        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        orderID = getIntent().getStringExtra("orderID");
        orderNumber = getIntent().getStringExtra("orderNumber");
        invoiceNumber = getIntent().getStringExtra("invoiceNumber");
        invoiceDate = getIntent().getStringExtra("invoiceDate");
        initMembers();


    }

    private void initMembers() {
        textview_location = findViewById(R.id.textview_location);
        tvToolbarTitleTCA = findViewById(R.id.tvToolbarTitleTCA);
        tv_logout = findViewById(R.id.tv_logout);

        problemDesCV = findViewById(R.id.problemDesCV);
        regionCV = findViewById(R.id.regionCV);
        cityCV = findViewById(R.id.cityCV);
        emailCV = findViewById(R.id.emailCV);
        phoneNoCV = findViewById(R.id.phoneNoCV);
        invoiceDateCV = findViewById(R.id.invoiceDateCV);
        invoiceNumCV = findViewById(R.id.invoiceNumCV);
        fullNameCV = findViewById(R.id.fullNameCV);


        fullNameET = findViewById(R.id.fullNameET);
        invoiceNoET = findViewById(R.id.invoiceNoET);
        invoiceDateET = findViewById(R.id.invoiceDateET);
        mobileNumET = findViewById(R.id.mobileNumET);
        emailET = findViewById(R.id.emailET);
        cityET = findViewById(R.id.cityET);
        regionET = findViewById(R.id.regionET);
        probDescriptionET = findViewById(R.id.probDescriptionET);
        termsAndConditionCheckBox = findViewById(R.id.termsAndConditionCheckBox);

//        selectImageIVASS = findViewById(R.id.selectImageIVASS);
//        showImageIVASS = findViewById(R.id.showImageIVASS);
        submitBtn = findViewById(R.id.submitBtn);

        invoiceNoET.setText(invoiceNumber);
        invoiceDateET.setText(invoiceDate);


        if (user != null) {
            fullNameET.setText(user.firstName + " " + user.lastName);
            String phoneNum = user.phone.substring(3);
            mobileNumET.setText(phoneNum);
            emailET.setText(user.email);
        }


        okhttp3.OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient.addNetworkInterceptor(new AddHeaderInterceptorForImg());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        service = retrofit.create(APIService.class);

       /* selectImageIVASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissonCheck) {
                    addImage();
                } else {
                    handlePermissions();
                }
            }
        });*/

        cityET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySetting companySetting = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);

                List<String> citiesList = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(companySetting.after_sale_cities);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        citiesList.add(jsonArray.get(i).toString());
                    }

                    alertChooseAreas(citiesList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        warrantyTypeRBG = findViewById(R.id.warrantyTypeRBG);
        termsAndConditionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!termsAndConditionCheckBox.isChecked()) {
                    submitBtn.setEnabled(false);
                    submitBtn.setBackground(getResources().getDrawable(R.drawable.unselect_btn));
                } else {
                    submitBtn.setEnabled(true);
                    submitBtn.setBackground(getResources().getDrawable(R.drawable.select_btn));
                }
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullNameET.getText().toString().equals("")) {
                    fullNameET.setError(getResources().getString(R.string.enter_your_name));
                } else if (invoiceNoET.getText().toString().equals("")) {
                    invoiceNoET.setError(getResources().getString(R.string.enter_invoice_num));
                } else if (invoiceDateET.getText().toString().equals("")) {
                    invoiceDateET.setError(getResources().getString(R.string.enter_invoice_date));
                } else if (mobileNumET.getText().toString().equals("")) {
                    mobileNumET.setError(getResources().getString(R.string.enter_phone_number));
                } else if (!mobileNumET.getText().toString().substring(0, 1).equals("5")) {
                    mobileNumET.setError(getResources().getString(R.string.invalid_mobile));
                } else if (!isValidEmail(emailET.getText().toString())) {
                    emailET.setError(getResources().getString(R.string.enter_email));
                } else if (cityET.getText().toString().equals("")) {
                    cityET.setError(getResources().getString(R.string.enter_city));
                } else if (regionET.getText().toString().equals("")) {
                    regionET.setError(getResources().getString(R.string.enter_region));
                } else if (probDescriptionET.getText().toString().equals("")) {
                    probDescriptionET.setError(getResources().getString(R.string.enter_problem));
                }else {
                    afterSalesServicesReq();

                }

                Log.d("dsfasafa1","sfasfsaf");

            }
        });


        tv_logout.setVisibility(View.GONE);
        textview_location.setVisibility(View.GONE);
        tvToolbarTitleTCA.setVisibility(View.VISIBLE);

        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        imageViewBackButton.setVisibility(View.VISIBLE);


        tvToolbarTitleTCA.setText(getResources().getString(R.string._string_aftersales_returns));

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (UserSession.getInstance().getUserLanguage().equals("en")) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                } else {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            }
        };

        invoiceDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserSession.getInstance().getUserLanguage().equals("en")) {

                    // TODO Auto-generated method stub
                    DatePickerDialog dialog = new DatePickerDialog(AfterSalesServicesMyReturnsActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                } else {
                    // TODO Auto-generated method stub
                    DatePickerDialog dialog = new DatePickerDialog(AfterSalesServicesMyReturnsActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                }
            }
        });
    }

    private void addImage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AfterSalesServicesMyReturnsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_video_pic, null);
        Button take_pic = dialogView.findViewById(R.id.take_pic);
        Button gallery = dialogView.findViewById(R.id.gallery);

        dialogBuilder.setView(dialogView);


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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            finalFile = createImageFile();
            // Continue only if the File was successfully created
            if (finalFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        finalFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_RQ);
            }
        }

        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, CAMERA_RQ);*/
    }

    private File createImageFile() {
        //Create an image file name
        try {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = 200;
        int targetH = 200;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Log.e("bitmapfromacamrapath",currentPhotoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        Log.e("bitmapfromacamra",bitmap.toString());
//        previewImageView.setImageBitmap(bitmap);
        filePath = currentPhotoPath;

        Log.d("**fileName", "" + finalFile);
        Log.d("**fileName", "" + filePath);
        showImageIVASS.setText(finalFile.getName());
    }

    public void GalleryPic() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 101);
    }

    public void handlePermissions() {
        mRequestPermissionHandler.requestPermission(this, new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                permissonCheck = true;
            }

            @Override
            public void onFailed() {
            }
        });
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        invoiceDateET.setText(sdf.format(myCalendar.getTime()));
    }


    public void alertChooseAreas(List<String> chooseItems) {

        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setContentView(R.layout.alert_dialog_select_city_list);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText editSearch = dialog.findViewById(R.id.editSearch);
        tvTitle.setText("" + getResources().getString(R.string.choose_area));
        ListView listViewChooseItems = dialog.findViewById(R.id.listViewChooseItems);
        final SelectionListAdapter selectCityListAdapter = new SelectionListAdapter(this, chooseItems);
        listViewChooseItems.setAdapter(selectCityListAdapter);

        editSearch.setVisibility(View.GONE);
        listViewChooseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppController.setLocale()) {
                    cityET.setText("" + chooseItems.get(position));
                } else {
                    cityET.setText("" + chooseItems.get(position));
                }
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }


    private void afterSalesServicesReq() {
        showProgress(true);

        User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
  /*
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("name", fullNameET.getText().toString());
        builder.addFormDataPart("warranty", warrenty);
        builder.addFormDataPart("invoice_number", invoiceNoET.getText().toString());
        builder.addFormDataPart("invoice_date", invoiceDateET.getText().toString());
        builder.addFormDataPart("customer_mobile", mobileNumET.getText().toString());
        builder.addFormDataPart("email", emailET.getText().toString());
        builder.addFormDataPart("city", cityET.getText().toString());
        builder.addFormDataPart("region", regionET.getText().toString());
        builder.addFormDataPart("problem_description", probDescriptionET.getText().toString());
        builder.addFormDataPart("user_id", user.userId);
        builder.addFormDataPart("orderid", orderID);
        builder.addFormDataPart("order_number", orderNumber);


        Bitmap bmp = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);

        builder.addFormDataPart("image", finalFile.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));

//        builder.addFormDataPart("image", finalFile.getName(), RequestBody.create(MultipartBody.FORM, finalFile));


        RequestBody requestBody = builder.build();


            Call<AfterSalesServicesMyReturnReq> call = service.afterSaleServicesMyReturnReq(requestBody);
            call.enqueue(new Callback<AfterSalesServicesMyReturnReq>() {
                @Override
                public void onResponse(Call<AfterSalesServicesMyReturnReq> call, Response<AfterSalesServicesMyReturnReq> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            showProgress(false);

                            Log.d("**response", "" + response.raw());
                            Log.d("**responseError", "" + response.errorBody());

                        }
                    } else {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), "problem image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AfterSalesServicesMyReturnReq> call, Throwable t) {
                    showProgress(false);
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(getApplicationContext(), "problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

*/

        Log.d("**afterSales", new Gson().toJson(new AfterSalesServicesMyReturnReq("" + fullNameET.getText().toString(),
                "" + warrenty, "" + invoiceNoET.getText().toString(), "" +
                invoiceDateET.getText().toString(),
                "" + mobileNumET.getText().toString(), "" + emailET.getText().toString(),
                "" + cityET.getText().toString(), "" + regionET.getText().toString(),
                "" + probDescriptionET.getText().toString(), user.userId, "" + orderID, "" + orderNumber)));


        APIManager.getInstance().afterSalesServicesMyReturn(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {
                Log.e("afterSaleResponse: ", "Response" + response);
                showProgress(false);
                if (z) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.optString("Code").equals("200")) {
                            finish();
                            Toast.makeText(AfterSalesServicesMyReturnsActivity.this, "" + getResources().getString(R.string.submit), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new AfterSalesServicesMyReturnReq("" + fullNameET.getText().toString(), "" + warrenty, "" + invoiceNoET.getText().toString(), "" + invoiceDateET.getText().toString(),
                "" + mobileNumET.getText().toString(), "" + emailET.getText().toString(),
                "" + cityET.getText().toString(), "" + regionET.getText().toString(),
                "" + probDescriptionET.getText().toString(), user.userId, "" + orderID, "" + orderNumber));
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showProgress(boolean show) {
        if (show) {
            mProgressDialog = new ProgressDialog(this);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (resultCode == RESULT_OK) {
                if (requestCode == 101) {
                    picUri = data.getData();
                    filePath = getPath(picUri);
                    showImageIVASS.setText(picUri.getPath());
                    finalFile = new File(getRealPathFromURI(picUri));
                } else if (requestCode == CAMERA_RQ) {
                    setPic();
                }
            }
        } catch (Exception ex) {
            Log.e("Ex_OnActivityResult", ex.toString());
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    @Override
    public void getConvertedFile(File file) {
        try {
            this.finalFile = file;
            if (file.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                showImageIVASS.setText(file.getAbsolutePath());

            }
            Log.e("EXc", imagetoBase64());

            showProgress(false);
        } catch (Exception e) {
            Log.e("EXc", e.toString());
            Toast.makeText(AfterSalesServicesMyReturnsActivity.this, "Image not supported", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String imagetoBase64(){
        if(finalFile!=null){
            Bitmap bm = BitmapFactory.decodeFile(finalFile.getPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 9, baos);
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            Log.d("ffff",encodedImage);
            logLargeString(encodedImage);
            return encodedImage;
        }
        return "";

    }


    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.e("**filePath", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e("**filePath", ""+str);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

}