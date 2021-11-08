package com.app.aljazierah.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.aljazierah.R;
import com.app.aljazierah.adapter.OffersRecyclerAdapter;
import com.app.aljazierah.object.Promotions.Promotion;
import com.app.aljazierah.utils.ProductsSingleton;
import com.app.aljazierah.utils.recycler.ClickListener;
import com.app.aljazierah.utils.recycler.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOffers extends Fragment {
    RecyclerView rvOffers;
    ImageView iv_no_offer;
//    List<String>offerList=new ArrayList<>();


    public FragmentOffers() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if(ProductsSingleton.getInstance().getPromotionList()!=null) {
                if (isVisibleToUser) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Promotion> mData= new ArrayList<>();
                            for(int i=0;i<ProductsSingleton.getInstance().getPromotionList().size();i++){
                                if(ProductsSingleton.getInstance().getPromotionList().get(i).getIs_offer().equals("1")){
                                    mData.add(ProductsSingleton.getInstance().getPromotionList().get(i));
                                }
                            }
                            rvOffers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            rvOffers.setAdapter(new OffersRecyclerAdapter(getActivity(),getActivity(), mData));

                            if(mData.size()>0){
                                rvOffers.setVisibility(View.VISIBLE);
                                iv_no_offer.setVisibility(View.GONE);
                            }
                            else{
                                rvOffers.setVisibility(View.GONE);
                                iv_no_offer.setVisibility(View.VISIBLE);
                            }

                        }
                    }, 500);
                }
            }
        }catch (Exception ex){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offers, container, false);

        rvOffers=view.findViewById(R.id.offers_rvOffers);
        iv_no_offer=view.findViewById(R.id.iv_no_offer);
        rvOffers.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvOffers, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ProductsSingleton.getInstance().getCustomViewPager().setCurrentItem(0);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
//        for (int i=0;i<7;i++)
//        {
//            offerList.add("dummy");
//        }

        return view;
    }

}
