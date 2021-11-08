
package com.app.aljazierah.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.Categories;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.ui.SeeAllActivity;
import com.app.aljazierah.ui.fragment.FragmentHome;
import com.app.aljazierah.utils.ProductsSingleton;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private List<Categories> categoriesList;
    private Context context;
    private FragmentHome mProductsFragment;
    private Activity activity;
    private boolean isArabic = AppController.setLocale();

    public CategoriesAdapter(List<Categories> categoriesList, Context context, FragmentHome mProductsFragment, Activity activity) {
        this.categoriesList = categoriesList;
        this.context = context;
        this.activity = activity;
        this.mProductsFragment = mProductsFragment;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_layout, null);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.MyViewHolder holder, final int position) {

        Log.d("**categoriesSize", "size:  " + getProducts(categoriesList.get(position)).size());
        if (getProducts(categoriesList.get(position)).size() > 0) {
            if (isArabic && !categoriesList.get(position).getName_ar().equals("")) {
                holder.categoriesTitleTV.setText(categoriesList.get(position).getName_ar());
            } else {
                holder.categoriesTitleTV.setText(categoriesList.get(position).getName_en());
            }

            ProductsSingleton.getInstance().setSubCategoriesModel(ProductsSingleton.getInstance().getCategoriesList().get(position).getSubCategoriesModelList());

            holder.seeAllTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SeeAllActivity.class);
                    intent.putExtra("categoryId", "" + categoriesList.get(position).getId());
                    intent.putExtra("subCategoryId", "" );
                    intent.putExtra("bestSelletList", "0");

                    if (isArabic)
                        intent.putExtra("catName", "" + categoriesList.get(position).getName_ar());
                    else
                        intent.putExtra("catName", "" + categoriesList.get(position).getName_en());
                    context.startActivity(intent);
                }
            });

            holder.categoriesRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            ProductbyLocationAdapter productbyLocationAdapter = new ProductbyLocationAdapter(context, getProducts(categoriesList.get(position)), mProductsFragment, activity);
            holder.categoriesRV.setAdapter(productbyLocationAdapter);
        }
        else {
            holder.categoriesLL.setVisibility(View.GONE);
        }

    }


    private ArrayList<ProductByLocation> getProducts(Categories categories) {

        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
        if (categories.getSubCategoriesModelList().size() > 0) {
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {

                for (int i = 0; i < categories.getSubCategoriesModelList().size(); i++) {

                    if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                            equals(categories.getSubCategoriesModelList().get(i).getId().toString())
                            ||
                            ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                                    equals(categories.getId().toString()))

                    {
                        if (ProductsSingleton.getInstance().getProductByLocations().get(j).getShow_index().equals("1")) {
                            productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
                            break;
                        }
                    }
                }
            }

        } else {
            for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
                if (ProductsSingleton.getInstance().getProductByLocations().get(j).getCategory_id().toString().
                        equals(categories.getId().toString())) {
                    if (ProductsSingleton.getInstance().getProductByLocations().get(j).getShow_index().equals("1")) {
                        productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
                    }
                }
            }
        }

        return productByLocations;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView categoriesTitleTV;
        private RecyclerView categoriesRV;
        private LinearLayout categoriesLL;
        private TextView seeAllTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesTitleTV = itemView.findViewById(R.id.categoriesTitleTV);
            categoriesRV = itemView.findViewById(R.id.categoriesRV);
            categoriesLL = itemView.findViewById(R.id.categoriesLL);
            seeAllTV = itemView.findViewById(R.id.seeAllTV);
        }
    }



}
