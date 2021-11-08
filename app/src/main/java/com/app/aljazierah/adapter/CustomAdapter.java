package com.app.aljazierah.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.object.SubCategoriesModel;
import java.util.List;
public class CustomAdapter extends BaseAdapter {
    Context context;
    List<SubCategoriesModel> subCategoriesModelList;
    LayoutInflater inflter;
    boolean isArabic = AppController.setLocale();
    public CustomAdapter(Context applicationContext, List<SubCategoriesModel> subCategoriesModelList) {
        this.context = applicationContext;
        this.subCategoriesModelList = subCategoriesModelList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return subCategoriesModelList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.titleTV);
        if (isArabic)
            names.setText(subCategoriesModelList.get(i).getName_ar());
        else
            names.setText(subCategoriesModelList.get(i).getName_en());
        return view;
    }
}