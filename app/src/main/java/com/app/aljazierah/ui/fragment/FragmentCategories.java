package com.app.aljazierah.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.aljazierah.R;
import com.app.aljazierah.adapter.CategoriesName.CategoriesNameAdapter;
import com.app.aljazierah.object.ProductByLocation;
import com.app.aljazierah.ui.SeeAllActivity;
import com.app.aljazierah.utils.ProductsSingleton;

import java.util.ArrayList;


public class FragmentCategories extends Fragment {
    private Context context;
    private RecyclerView categoriesRV;
    private CategoriesNameAdapter categoriesNameAdapter;
    private LinearLayout bestSellerLLFC;
    private View lineFC;


    public FragmentCategories(Context context) {
        this.context = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isNetworkConnected()) {
                        initMembers();
                    } else
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }, 500);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        categoriesRV = view.findViewById(R.id.categoriesRV);
        bestSellerLLFC = view.findViewById(R.id.bestSellerLLFC);
        lineFC = view.findViewById(R.id.lineFC);
        bestSellerLLFC.setVisibility(View.GONE);
        lineFC.setVisibility(View.GONE);
//        initMembers();
        return view;
    }

    private void initMembers() {
        if (ProductsSingleton.getInstance().getCategoriesList().size() > 0) {
            categoriesNameAdapter = new CategoriesNameAdapter(ProductsSingleton.getInstance().getCategoriesList(), getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            categoriesRV.setLayoutManager(gridLayoutManager);
            categoriesRV.setAdapter(categoriesNameAdapter);
            bestSellerLLFC.setVisibility(View.VISIBLE);
            lineFC.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.no_products_to_show), Toast.LENGTH_SHORT).show();
        }



        bestSellerLLFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeAllActivity.class);
                intent.putExtra("categoryId", "");
                intent.putExtra("subCategoryId", "");
                intent.putExtra("bestSelletList", "1");
                intent.putExtra("catName", "" + getResources().getString(R.string.best_seller));
                context.startActivity(intent);
            }
        });
    }


    private ArrayList<ProductByLocation> getBestSellingProd() {

        ArrayList<ProductByLocation> productByLocations = new ArrayList<>();
        for (int j = 0; j < ProductsSingleton.getInstance().getProductByLocations().size(); j++) {
            if (ProductsSingleton.getInstance().getProductByLocations().get(j).getBest_selling().toString().equals("1")) {
                productByLocations.add(ProductsSingleton.getInstance().getProductByLocations().get(j));
            }
        }
        return productByLocations;
    }


}