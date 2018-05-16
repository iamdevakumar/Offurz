package com.shadowws.offurz.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shadowws.offurz.R;

/**
 * Created by Lenovo on 2/13/2018.
 */

public class CustomPagerAdapter extends PagerAdapter {
    static String TAG = "CustomPageAdapter";
    Context mContext;
    LayoutInflater mLayoutInflater;

    int[] mlayoutResource = {
            R.drawable.sliver_bg,R.drawable.gold_bg,R.drawable.plat_bg

    };
    String[] typearray={
            "Sliver","Gold","Platinum"
    };
    String[] cuponsPerStore = {
            "Submit 5 Coupons Per Store","Submit 30 Coupons Per Store","Submit Unlimited Coupons and Unlimited Stores"
    };
    String[] noOfCoupons = {
            "5 Coupons","30 Coupons","Unlimited Coupons"
    };
    String[] dealsArray={
            "5 Deals Promos","30 Deals Promos","Unlimited Deals / Promos"
    };
    int[] amountarray = {300,500,1000};
    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mlayoutResource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.homepage_cardview, container, false);
        RelativeLayout rl=(RelativeLayout)itemView.findViewById(R.id.bglayout);
        rl.setBackgroundResource(mlayoutResource[position]);
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.type);
//
//        imageView.setImageResource(mResources[position]);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        amount.setText("Rs."+amountarray[position]+" / Month");
        TextView type = (TextView) itemView.findViewById(R.id.type);
        type.setText(typearray[position]);
        TextView cupons = (TextView) itemView.findViewById(R.id.cupons);
        cupons.setText(noOfCoupons[position]);
        TextView deals = (TextView) itemView.findViewById(R.id.deals);
        deals.setText(dealsArray[position]);
        TextView submitUnlimet = (TextView) itemView.findViewById(R.id.submitUnlimted);
        submitUnlimet.setText(cuponsPerStore[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
