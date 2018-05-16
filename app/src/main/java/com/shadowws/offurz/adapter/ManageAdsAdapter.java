package com.shadowws.offurz.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowws.offurz.Fragments.ManageAdsEditFragment;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/15/2018.
 */

public class ManageAdsAdapter extends BaseAdapter {
    static String TAG = "ManageAdsAdapter";
    Context context;
    String productName[];
    int cuponCode[];
    String descriptions[];
    LayoutInflater inflter;
    FragmentManager fm;
    TextView productNameTxt,cuponCodeTxt,despTxt;
    ArrayList<BestSeller> bestSellerArrayList;

    public ManageAdsAdapter(Context applicationContext, ArrayList<BestSeller> bestSellerArrayList,FragmentManager fm) {
        this.context = applicationContext;
      this.bestSellerArrayList = bestSellerArrayList;
        this.fm =fm;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return bestSellerArrayList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.manage_ads_list_items, null);
        productNameTxt = (TextView) view.findViewById(R.id.product_name_result);
        cuponCodeTxt = (TextView) view.findViewById(R.id.cupon_code_result);
         despTxt = (TextView) view.findViewById(R.id.description_result);
        ImageView pencil = (ImageView) view.findViewById(R.id.edit_pencil_id);
       // TextView manageAdView = (TextView) view.findViewById(R.id.manage_ads_view_id);

            //BestSeller bs = bestSellerArrayList.get(j);
        Log.d(TAG,"Pname "+bestSellerArrayList.get(i).getProductName());
            productNameTxt.setText(bestSellerArrayList.get(i).getProductName());
            cuponCodeTxt.setText(bestSellerArrayList.get(i).getCuponCode());
            despTxt.setText(bestSellerArrayList.get(i).getDescription());

        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentTransaction fr =
                ManageAdsEditFragment fragmentB = new ManageAdsEditFragment ();
                Bundle args = new Bundle();
               // Toast.makeText(context,"String"+bestSellerArrayList.get(i).getProductName(),Toast.LENGTH_SHORT).show();
                args.putInt("id",bestSellerArrayList.get(i).getId());
                args.putString("productName",bestSellerArrayList.get(i).getProductName());
                args.putString("description",bestSellerArrayList.get(i).getDescription());
                args.putString("cuponCode",bestSellerArrayList.get(i).getCuponCode());
                args.putString("a_price",""+bestSellerArrayList.get(i).getA_price());
                args.putString("off_percentage",bestSellerArrayList.get(i).getOffPercentage());
                args.putString("off_price",bestSellerArrayList.get(i).getOfferPrice());
                args.putString("count",""+bestSellerArrayList.get(i).getCount());
                args.putString("categories",bestSellerArrayList.get(i).getCategories());
                args.putString("image",bestSellerArrayList.get(i).getImage());
                args.putString("image2",bestSellerArrayList.get(i).getImage2());
                fragmentB .setArguments(args);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, fragmentB).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}