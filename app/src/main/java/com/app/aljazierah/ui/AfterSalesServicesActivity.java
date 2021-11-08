package com.app.aljazierah.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.app.aljazierah.AppController;
import com.app.aljazierah.Interfaces.IPresenter;
import com.app.aljazierah.Interfaces.RequestViews;
import com.app.aljazierah.Presenter.Presenter;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.adapter.CustomAdapter;
import com.app.aljazierah.adapter.SelectionListAdapter;
import com.app.aljazierah.adapter.SubCatListAdapter;
import com.app.aljazierah.adapter.CategoriesListsAdapter;
import com.app.aljazierah.object.AddReceiptModel;
import com.app.aljazierah.object.CompanySetting;
import com.app.aljazierah.object.ServiceSlotsModel;
import com.app.aljazierah.object.SubCategoriesModel;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.AfterSalesServicesReq;
import com.app.aljazierah.utils.APIManager;
import com.app.aljazierah.utils.APIService;
import com.app.aljazierah.utils.AddHeaderInterceptorForImg;
import com.app.aljazierah.utils.Constants;
import com.app.aljazierah.utils.ProductsSingleton;
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

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.aljazierah.utils.Constants.AFTERSALESERVICES;
import static com.app.aljazierah.utils.Constants.BASE_URL;

public class AfterSalesServicesActivity extends AppCompatActivity implements SubCatListAdapter.Clicks, RequestViews, IPresenter {
    private TextView textview_location, tvToolbarTitleTCA, tv_logout;
    private ImageView imageViewBackButton/*, arrowsIVASS*/;
    private ProgressDialog mProgressDialog;
    private TextInputEditText fullNameET, productTypeET, invoiceNoET, invoiceDateET, mobileNumET, emailET, titleET, issueET, cityET, regionET,
            addressET, probDescriptionET;
    private CheckBox termsAndConditionCheckBox;
    private Button submitBtn;
    private RadioGroup warrantyTypeRBG;
    private String warrenty = "";
    private boolean isArabic;
    private CardView fullNameCV, productTypeCV, invoiceNumCV, invoiceDateCV, phoneNoCV, emailCV, titleCV, issueCV, cityCV, regionCV,
            addressCV, problemDesCV, slotsCV;
    final Calendar myCalendar = Calendar.getInstance();
    private User user;
    private RecyclerView categoriesRVASS;
    //    private TextView categoriesNameTVAFS;
    private CategoriesListsAdapter subCategoriesListAdapter;
    private ArrayList<String> idsStringArray = new ArrayList<>();
    private RadioButton insideWarrantyRB;
    private boolean afterSSCheck = true;
    private TextView showImageIVASS;
    private boolean permissonCheck = false;
    private RequestPermissionHandler mRequestPermissionHandler;
    private String filePath;
    private File finalFile;
    private Uri picUri = null;
    private final static int CAMERA_RQ = 102;
    private String currentPhotoPath = "";
    private Spinner slotsSpinner;
    private Spinner subCategoriesSpinner;
    CustomAdapter customAdapter, slotsAdapter;
    ArrayAdapter arrayAdapter;
    List<String> list;
    private int modelPosition = -1;
    private String st_sub_category = "";
    private int st_slots;
    List<SubCategoriesModel> subCategoriesModels = new ArrayList<>();
    String encodedImage;
    private APIService service;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isArabic = AppController.setLocale();
        mRequestPermissionHandler = new RequestPermissionHandler();
        setContentView(R.layout.activity_after_sales_services);

        warrenty = "" + getResources().getString(R.string.inside_warranty);
        user = DatabaseManager.getInstance().getFirstOfClass(User.class);
        initMembers();
    }

    private void initMembers() {
        textview_location = findViewById(R.id.textview_location);
        tvToolbarTitleTCA = findViewById(R.id.tvToolbarTitleTCA);


        tv_logout = findViewById(R.id.tv_logout);
        categoriesRVASS = findViewById(R.id.categoriesRVASS);
        subCategoriesSpinner = findViewById(R.id.subCategoriesSpinner);
//        categoriesNameTVAFS = findViewById(R.id.categoriesNameTVAFS);


        problemDesCV = findViewById(R.id.problemDesCV);
        addressCV = findViewById(R.id.addressCV);
        regionCV = findViewById(R.id.regionCV);
        cityCV = findViewById(R.id.cityCV);
        issueCV = findViewById(R.id.issueCV);
        titleCV = findViewById(R.id.titleCV);
        emailCV = findViewById(R.id.emailCV);
        phoneNoCV = findViewById(R.id.phoneNoCV);
        invoiceDateCV = findViewById(R.id.invoiceDateCV);
        invoiceNumCV = findViewById(R.id.invoiceNumCV);
        productTypeCV = findViewById(R.id.productTypeCV);
        fullNameCV = findViewById(R.id.fullNameCV);


        fullNameET = findViewById(R.id.fullNameET);
        productTypeET = findViewById(R.id.productTypeET);
        invoiceNoET = findViewById(R.id.invoiceNoET);
        invoiceDateET = findViewById(R.id.invoiceDateET);
        mobileNumET = findViewById(R.id.mobileNumET);
        emailET = findViewById(R.id.emailET);
        titleET = findViewById(R.id.titleET);
        issueET = findViewById(R.id.issueET);
        cityET = findViewById(R.id.cityET);
        regionET = findViewById(R.id.regionET);
        addressET = findViewById(R.id.addressET);
        probDescriptionET = findViewById(R.id.probDescriptionET);
        showImageIVASS = findViewById(R.id.showImageIVASS);
//        arrowsIVASS = findViewById(R.id.arrowsIVASS);
        slotsCV = findViewById(R.id.slotsCV);
        slotsSpinner = findViewById(R.id.slotsSpinner);


        slotsCV.setVisibility(View.GONE);


        termsAndConditionCheckBox = findViewById(R.id.termsAndConditionCheckBox);

        submitBtn = findViewById(R.id.submitBtn);

        warrantyTypeRBG = findViewById(R.id.warrantyTypeRBG);

        categoriesRVASS.setVisibility(View.GONE);


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

     /*   categoriesNameTVAFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (afterSSCheck) {

                    arrowsIVASS.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    categoriesRVASS.setVisibility(View.VISIBLE);
                    afterSSCheck = false;

                } else if (!afterSSCheck) {

                    arrowsIVASS.setBackground(getResources().getDrawable(R.drawable.ic_right_chevron));
                    afterSSCheck = true;
                    categoriesRVASS.setVisibility(View.GONE);
                }

            }
        });*/


        showImageIVASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissonCheck) {
                    addImage();
                } else {
                    handlePermissions();
                }

            }
        });


        if (ProductsSingleton.getInstance().getCategoriesList().size() > 0) {

            /*subCategoriesListAdapter = new CategoriesListsAdapter(ProductsSingleton.getInstance().getCategoriesList(),
                    AfterSalesServicesActivity.this,this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(AfterSalesServicesActivity.this, 1);
            categoriesRVASS.setLayoutManager(gridLayoutManager);
            categoriesRVASS.setAdapter(subCategoriesListAdapter);*/


            for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().size(); i++) {
                for (int j = 0; j < ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().size(); j++) {
               /*     if (isArabic){
                        list= Collections.singletonList(ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().get(j).getName_en());

                        Log.d("**list",""+list);
                        subCatAdapter = new ArrayAdapter<>(AfterSalesServicesActivity.this, R.layout.main_spinner_layout, list);
                    }*/
                    subCategoriesModels.add(ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().get(j));
                }
            }

//            subCatAdapter = new ArrayAdapter<>(AfterSalesServicesActivity.this, R.layout.main_spinner_layout, subCategoriesModels);

            st_sub_category = subCategoriesModels.get(0).getId();
            customAdapter = new CustomAdapter(getApplicationContext(), subCategoriesModels);
            subCategoriesSpinner.setAdapter(customAdapter);

        } else {
            Toast.makeText(this, "" + getResources().getString(R.string.no_products_to_show), Toast.LENGTH_SHORT).show();
        }

        if (user != null) {
            fullNameET.setText(user.firstName + " " + user.lastName);
            String phoneNum = user.phone.substring(3);
            mobileNumET.setText(phoneNum);
            emailET.setText(user.email);
        }

        subCategoriesSpinner.setAdapter(customAdapter);
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

        subCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Log.d("**subCatName", "" + subCategoriesModels.get(position).getName_en().toString());
                st_sub_category = subCategoriesModels.get(position).getId();

                if (!cityET.getText().toString().equals("")) {
                    showProgress(true);
                    serviceSlotsReq();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


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

        insideWarrantyRB = findViewById(R.id.insideWarrantyRB);

        insideWarrantyRB.setChecked(true);
        warrantyTypeRBG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.outsideWarrantyRB:
                        if (isArabic) {
                            warrenty = "خارج الضمان";
                        } else {
                            warrenty = getResources().getString(R.string.outside_warranty);
                        }

                        fullNameCV.setVisibility(View.VISIBLE);
                        productTypeCV.setVisibility(View.VISIBLE);
                        invoiceDateCV.setVisibility(View.GONE);
                        invoiceNumCV.setVisibility(View.GONE);
                        phoneNoCV.setVisibility(View.VISIBLE);
                        emailCV.setVisibility(View.VISIBLE);
                        titleCV.setVisibility(View.VISIBLE);
                        issueCV.setVisibility(View.VISIBLE);
                        cityCV.setVisibility(View.VISIBLE);
                        regionCV.setVisibility(View.VISIBLE);
                        addressCV.setVisibility(View.VISIBLE);
                        problemDesCV.setVisibility(View.VISIBLE);
                        termsAndConditionCheckBox.setVisibility(View.VISIBLE);

                        if (!termsAndConditionCheckBox.isChecked()) {
                            submitBtn.setEnabled(false);
                            submitBtn.setBackground(getResources().getDrawable(R.drawable.unselect_btn));
                        } else {
                            submitBtn.setEnabled(true);
                            submitBtn.setBackground(getResources().getDrawable(R.drawable.select_btn));
                        }

                        break;
                    case R.id.insideWarrantyRB:

                        if (isArabic) {
                            warrenty = "داخل الضمان";
                        } else {
                            warrenty = getResources().getString(R.string.inside_warranty);

                        }
                        fullNameCV.setVisibility(View.VISIBLE);
                        productTypeCV.setVisibility(View.VISIBLE);
                        invoiceDateCV.setVisibility(View.VISIBLE);
                        invoiceNumCV.setVisibility(View.VISIBLE);
                        phoneNoCV.setVisibility(View.VISIBLE);
                        emailCV.setVisibility(View.VISIBLE);
                        titleCV.setVisibility(View.VISIBLE);
                        issueCV.setVisibility(View.VISIBLE);
                        cityCV.setVisibility(View.VISIBLE);
                        regionCV.setVisibility(View.VISIBLE);
                        addressCV.setVisibility(View.GONE);
                        problemDesCV.setVisibility(View.VISIBLE);
                        termsAndConditionCheckBox.setVisibility(View.GONE);
                        submitBtn.setBackground(getResources().getDrawable(R.drawable.select_btn));

                        break;
                }
            }
        });


        slotsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Log.d("**slotsTime", "" + slotsSpinner.getSelectedItem().toString());
                st_slots = position;
                modelPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warrenty.equals("" + getResources().getString(R.string.inside_warranty))) {

                    if (fullNameET.getText().toString().equals("")) {
                        fullNameET.setError(getResources().getString(R.string.enter_your_name));
                    } else if (productTypeET.getText().toString().equals("")) {
                        productTypeET.setError(getResources().getString(R.string.enter_product_type));
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
                    } else if (titleET.getText().toString().equals("")) {
                        titleET.setError(getResources().getString(R.string.enter_title));
                    } else if (issueET.getText().toString().equals("")) {
                        issueET.setError(getResources().getString(R.string.enter_issue));
                    } else if (cityET.getText().toString().equals("")) {
                        cityET.setError(getResources().getString(R.string.enter_city));
                    } else if (regionET.getText().toString().equals("")) {
                        regionET.setError(getResources().getString(R.string.enter_region));
                    } else if (probDescriptionET.getText().toString().equals("")) {
                        probDescriptionET.setError(getResources().getString(R.string.enter_problem));
                    }
                    /*else if (showImageIVASS.getText().toString().equals("")) {
                        showImageIVASS.setError(getResources().getString(R.string.please_select_img));
                    }*/
                    else {
                        afterSalesServicesReq();
                    }
                } else {
                    if (fullNameET.getText().toString().equals("")) {
                        fullNameET.setError(getResources().getString(R.string.enter_your_name));
                    } else if (productTypeET.getText().toString().equals("")) {
                        productTypeET.setError(getResources().getString(R.string.enter_product_type));
                    } else if (mobileNumET.getText().length() == 0) {
                        mobileNumET.setError(getResources().getString(R.string.enter_phone_number));
                    } else if (mobileNumET.getText().toString().trim().length() != 9) {
                        mobileNumET.setError(getResources().getString(R.string.enter_phone_number));
                    } else if (!mobileNumET.getText().toString().substring(0, 1).equals("5")) {
                        mobileNumET.setError(getResources().getString(R.string.enter_phone_number));
                    } else if (!isValidEmail(emailET.getText().toString())) {
                        emailET.setError(getResources().getString(R.string.enter_email));
                    } else if (titleET.getText().toString().equals("")) {
                        titleET.setError(getResources().getString(R.string.enter_title));
                    } else if (issueET.getText().toString().equals("")) {
                        issueET.setError(getResources().getString(R.string.enter_issue));
                    } else if (cityET.getText().toString().equals("")) {
                        cityET.setError(getResources().getString(R.string.enter_city));
                    } else if (regionET.getText().toString().equals("")) {
                        regionET.setError(getResources().getString(R.string.enter_region));
                    } else if (addressET.getText().toString().equals("")) {
                        addressET.setError(getResources().getString(R.string.enter_address));
                    } else if (probDescriptionET.getText().toString().equals("")) {
                        probDescriptionET.setError(getResources().getString(R.string.enter_problem));
                    } else if (warrantyTypeRBG.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(AfterSalesServicesActivity.this, "" + getResources().getString(R.string.select_warrenty_type), Toast.LENGTH_SHORT).show();
                    } else {
                        afterSalesServicesReq();
                    }
                }
            }
        });

        tv_logout.setVisibility(View.GONE);
        textview_location.setVisibility(View.GONE);
        tvToolbarTitleTCA.setVisibility(View.VISIBLE);

        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        imageViewBackButton.setVisibility(View.VISIBLE);

        tvToolbarTitleTCA.setText(getResources().getString(R.string.after_sales));


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
                    DatePickerDialog dialog = new DatePickerDialog(AfterSalesServicesActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                } else {
                    // TODO Auto-generated method stub
                    DatePickerDialog dialog = new DatePickerDialog(AfterSalesServicesActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                }
            }
        });
    }

    private void addImage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AfterSalesServicesActivity.this);
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
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Log.e("bitmapfromacamrapath", currentPhotoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        Log.e("bitmapfromacamra", bitmap.toString());
//        previewImageView.setImageBitmap(bitmap);
        filePath = currentPhotoPath;

        Log.d("**fileName", "" + finalFile);
        Log.d("**fileName", "" + filePath);
        showImageIVASS.setText(finalFile.getName());

        Bitmap bm = BitmapFactory.decodeFile(finalFile.getName());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
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

    /*
        private void updateLabel() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            invoiceDateET.setText(sdf.format(myCalendar.getTime()));
        }
    */

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
        tvTitle.setText("" + getResources().getString(R.string.choose_city));
        ListView listViewChooseItems = dialog.findViewById(R.id.listViewChooseItems);
        final SelectionListAdapter selectCityListAdapter = new SelectionListAdapter(this, chooseItems);
        listViewChooseItems.setAdapter(selectCityListAdapter);

        editSearch.setVisibility(View.GONE);

        listViewChooseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppController.setLocale()) {
                    cityET.setText("" + chooseItems.get(position));
                    showProgress(true);
                    serviceSlotsReq();
                } else {
                    cityET.setText("" + chooseItems.get(position));
                    showProgress(true);
                    serviceSlotsReq();
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

        for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().size(); i++) {
            for (int j = 0; j < ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().size(); j++) {
                if (!ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().get(j).getisSelected()) {
                    idsStringArray.add(ProductsSingleton.getInstance().getCategoriesList().get(i).getSubCategoriesModelList().get(j).getId());
                }
            }
        }


          /*  Log.d("**afterSales", new Gson().toJson(new AfterSalesServicesReq("" +
                    user.userId, "" + fullNameET.getText().toString(),
                    "" + productTypeET.getText().toString(), "" + warrenty,
                    "" + invoiceNoET.getText().toString(), "" + invoiceDateET.getText().toString(),
                    "" + mobileNumET.getText().toString(), "" + emailET.getText().toString(),
                    "" + titleET.getText().toString(),
                    "" + issueET.getText().toString(), "" + cityET.getText().toString(),
                    "" + regionET.getText().toString(), "" + addressET.getText().toString(),
                    "" + probDescriptionET.getText().toString(), "1",
                    st_sub_category,""+st_slots, ""+finalFile.getName())));*/


        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("user_id", "" + user.userId);
        builder.addFormDataPart("name", "" + fullNameET.getText().toString());
        builder.addFormDataPart("product_type", "" + productTypeET.getText().toString());
        builder.addFormDataPart("warranty", "" + warrenty);
        builder.addFormDataPart("invoice_number", "" + invoiceNoET.getText().toString());
        builder.addFormDataPart("invoice_date", "" + invoiceDateET.getText().toString());
        builder.addFormDataPart("customer_mobile", "" + mobileNumET.getText().toString());
        builder.addFormDataPart("email", "" + emailET.getText().toString());
        builder.addFormDataPart("title", "" + titleET.getText().toString().trim());
        builder.addFormDataPart("product_issue", "" + issueET.getText().toString().trim());
        builder.addFormDataPart("city", "" + cityET.getText().toString().trim());
        builder.addFormDataPart("region", "" + regionET.getText().toString().trim());
        builder.addFormDataPart("address", "" + addressET.getText().toString().trim());
        builder.addFormDataPart("problem_description", "" + probDescriptionET.getText().toString().trim());
        builder.addFormDataPart("warranty_agreement", "1");
        builder.addFormDataPart("product_category", "" + st_sub_category);
        builder.addFormDataPart("service_slots", "" + st_slots);
//        builder.addFormDataPart("", "" + imagetoBase64());


        Bitmap bmp = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);

        builder.addFormDataPart("invoice_pic", finalFile.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));

        Log.d("**invoiceImg", ""+finalFile.getName());
//        builder.addFormDataPart("invoice_pic", finalFile.getName(), RequestBody.create(MultipartBody.FORM, finalFile));


        RequestBody requestBody = builder.build();


        Call<AfterSalesServicesReq> call = service.send_after_sale(requestBody);
        call.enqueue(new Callback<AfterSalesServicesReq>() {
            @Override
            public void onResponse(Call<AfterSalesServicesReq> call, Response<AfterSalesServicesReq> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        showProgress(false);

                        Log.d("**response", "" + response.raw());
                        Log.d("**responseError", "" + response.errorBody());
                        Toast.makeText(AfterSalesServicesActivity.this, "" + getResources().getString(R.string.request_submitted_successfully), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "error" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AfterSalesServicesReq> call, Throwable t) {
                showProgress(false);
                Log.v("Response gotten is", t.getMessage());
                Toast.makeText(getApplicationContext(), "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });



/*
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", "" + user.userId);
            jsonObject.put("name", "" + fullNameET.getText().toString());
            jsonObject.put("product_type", "" + productTypeET.getText().toString());
            jsonObject.put("warranty", "" + warrenty);
            jsonObject.put("invoice_number", "" + invoiceNoET.getText().toString());
            jsonObject.put("invoice_date", "" + invoiceDateET.getText().toString());
            jsonObject.put("customer_mobile", "" + mobileNumET.getText().toString());
            jsonObject.put("email", "" + emailET.getText().toString());
            jsonObject.put("title", "" + titleET.getText().toString().trim());
            jsonObject.put("product_issue", "" + issueET.getText().toString().trim());
            jsonObject.put("city", "" + cityET.getText().toString().trim());
            jsonObject.put("region", "" + regionET.getText().toString().trim());
            jsonObject.put("address", "" + addressET.getText().toString().trim());
            jsonObject.put("problem_description", "" + probDescriptionET.getText().toString().trim());
            jsonObject.put("warranty_agreement", "1");
            jsonObject.put("product_category", "" + st_sub_category);
            jsonObject.put("service_slots", "" + st_slots);
            jsonObject.put("invoive_pic", "" + imagetoBase64());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Presenter presenter = new Presenter(this, this, this);
        presenter.setPostMethodJsonObject(Constants.BASE_URL+""+AFTERSALESERVICES, jsonObject, "aftersaleReq");
        Log.d("**resp",""+new Gson().toJson(jsonObject));*/

/*
            APIManager.getInstance().afterSalesServices(new APIManager.Callback() {
                @Override
                public void onResult(boolean z, String response) {
                    Log.e("afterSaleResponse: ", "Response" + response);
                    showProgress(false);
                    if (z) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optString("Code").equals("200")) {
                                finish();
                                Toast.makeText(AfterSalesServicesActivity.this, "" + getResources().getString(R.string.submit), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new AfterSalesServicesReq("" + user.userId, "" + fullNameET.getText().toString(), "" + productTypeET.getText().toString(), "" + warrenty, "" + invoiceNoET.getText().toString(), "" + invoiceDateET.getText().toString(),
                    "" + mobileNumET.getText().toString(), "" + emailET.getText().toString(), "" + titleET.getText().toString(),
                    "" + issueET.getText().toString(), "" + cityET.getText().toString(), "" + regionET.getText().toString(), "" + addressET.getText().toString(),
                    "" + probDescriptionET.getText().toString(), "1", st_sub_category,""+st_slots,""+ imagetoBase64()));

*/

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


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String profile_image = "";
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteFormat = stream.toByteArray();
            profile_image = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        } catch (Exception ex) {
            Log.e("base64 exception", ex.toString());
        }
        return profile_image;
    }


    private String imagetoBase64() {
        if (finalFile != null) {
            /*Bitmap bm = BitmapFactory.decodeFile(finalFile.getPath());

             */
            /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.NO_WRAP);*/

            /*
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//            logLargeString(encodedImage);*/


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            logLargeString(imageString);
            return imageString;

//            return encodedImage;
        } else {
            return "";
        }

    }


    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    private String getBase64String() {

        // give your image file url in mCurrentPhotoPath
        Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getPath());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);

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

    @Override
    public void selectSubCategory(SubCategoriesModel subCategoriesModel, int position) {
        for (int j = 0; j < ProductsSingleton.getInstance().getCategoriesList().size(); j++) {
            for (int i = 0; i < ProductsSingleton.getInstance().getCategoriesList().get(j).getSubCategoriesModelList().size(); i++) {
                if (ProductsSingleton.getInstance().getCategoriesList().get(j).getSubCategoriesModelList().get(i).getId().equals(subCategoriesModel.getId())) {
                    ProductsSingleton.getInstance().getCategoriesList().get(j).getSubCategoriesModelList().get(i).setSelected(true);
                } else {
                    ProductsSingleton.getInstance().getCategoriesList().get(j).getSubCategoriesModelList().get(i).setSelected(false);
                }
            }
        }

        subCategoriesListAdapter.notifyDataSetChanged();
    }

    private void serviceSlotsReq() {

        Log.d("**reqBodyServiceSlots", "" + new Gson().toJson(new ServiceSlotsModel("" + cityET.getText().toString(), "" + st_sub_category))
        );


        APIManager.getInstance().getServiceSlotsReq(new APIManager.Callback() {
            @Override
            public void onResult(boolean z, String response) {

                if (z) {
                    Log.d("*responseSlots" + z, response);
                    Log.d("*responseSlots" + z, response);
                    try {
                        showProgress(false);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("code").equals("200")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("slots");
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                list.add(jsonArray.getString(i));
                            }
                            slotsCV.setVisibility(View.VISIBLE);
                            arrayAdapter = new ArrayAdapter<>(AfterSalesServicesActivity.this, R.layout.main_spinner_layout, list);
                            slotsSpinner.setAdapter(arrayAdapter);
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new ServiceSlotsModel("" + cityET.getText().toString(), "" + st_sub_category));
        Log.d("**reqBodyServiceSlots", "" + new Gson().toJson(new ServiceSlotsModel("" + cityET.getText().toString(), "" + st_sub_category)));
    }


    public void logLargeString(String str) {
        if (str.length() > 200000) {
            Log.e("***invoiceIMG", str.substring(0, 200000));
            logLargeString(str.substring(200000));
        } else {
            Log.e("***invoiceIMG", "" + str); // continuation
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        AppController.updateResources(newBase, UserSession.getInstance().getUserLanguage());
        super.attachBaseContext(newBase);
    }

    @Override
    public void getResponse(String response, String requestMessage) {
        Log.d("**afterSales", requestMessage);
        Log.d("**afterSalesRes", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (requestMessage) {
                case "aftersaleReq":
                    if (jsonObject.getString("Code").equals("200")) {
                        finish();
                        Toast.makeText(AfterSalesServicesActivity.this, "" + getResources().getString(R.string.submit), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AfterSalesServicesActivity.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void getError(VolleyError error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getSuccessNetwork(NetworkResponse response, String requestMessage) {
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }
}