package com.shadowws.offurz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowws.offurz.Fragments.PurchaseBuyerDetailsEditFragment;
import com.shadowws.offurz.Pojo.PurchaseDetails;
import com.shadowws.offurz.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/16/2018.
 */

public class PurchaseBuyerDetailsAdapter extends BaseAdapter {
    static String TAG = "PurchaseBuyerAdapter";
ArrayList<PurchaseDetails> purchaseDetails;
    Context context;
    String productName[];
    int mobileNo[];
    String buyerName[];
    LayoutInflater inflter;
    FragmentManager fm;
    TextView buyerNameTxt,mobileNoTxt,productNameTxt;

    public PurchaseBuyerDetailsAdapter(Context applicationContext, ArrayList<PurchaseDetails> purchaseDetails, FragmentManager fm) {
        this.context = applicationContext;
        this.purchaseDetails = purchaseDetails;
        this.fm =fm;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return purchaseDetails.size();
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
        view = inflter.inflate(R.layout.purchase_buyer_details_list_item, null);
        buyerNameTxt = (TextView) view.findViewById(R.id.buyer_name_result);
        mobileNoTxt = (TextView) view.findViewById(R.id.mobile_no_result);
        productNameTxt = (TextView) view.findViewById(R.id.product_name_result);
        ImageView pencil = (ImageView) view.findViewById(R.id.edit_pencil_id);
        // TextView manageAdView = (TextView) view.findViewById(R.id.manage_ads_view_id);
        productNameTxt.setText(purchaseDetails.get(i).getProduct_name());
        mobileNoTxt.setText(purchaseDetails.get(i).getMobile());
        buyerNameTxt.setText(purchaseDetails.get(i).getName());
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentTransaction fr =
                PurchaseBuyerDetailsEditFragment pbdFragment = new PurchaseBuyerDetailsEditFragment ();
                Bundle args = new Bundle();
               // Toast.makeText(context,"String"+productNameTxt.getText(),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"order_id"+purchaseDetails.get(i).getId());
                args.putString("id",""+purchaseDetails.get(i).getId());
                args.putInt("add_id",purchaseDetails.get(i).getAdd_id());
                args.putString("productName",purchaseDetails.get(i).getProduct_name());
                args.putString("buyerName",purchaseDetails.get(i).getName());
                args.putString("mobileNumber",purchaseDetails.get(i).getMobile());
                args.putString("coupon_code",purchaseDetails.get(i).getCoupon_code());
                args.putString("price",purchaseDetails.get(i).getAprice());
                args.putString("off_percentage",purchaseDetails.get(i).getOff_percentage());
                args.putString("off_price",purchaseDetails.get(i).getBprice());
                args.putString("purchaseDate",purchaseDetails.get(i).getPurchase_date());
                pbdFragment .setArguments(args);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, pbdFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}