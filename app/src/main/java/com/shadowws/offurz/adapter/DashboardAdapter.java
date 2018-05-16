package com.shadowws.offurz.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shadowws.offurz.HomePage;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2/14/2018.
 */

public class DashboardAdapter extends PagerAdapter {
    static String TAG = "DashboardAdapter";

    private final List<JSONArray> bestsellerArray;
    Context mContext;
    LayoutInflater mLayoutInflater;

//    int[] imageId = {
//            R.drawable.bags_first,
//            R.drawable.clothes_first,
//            R.drawable.shoes_first,
//            R.drawable.watches_first,
//            R.drawable.clothes_second,
//            R.drawable.bags_second,
//            R.drawable.shoes_second,
//            R.drawable.watches_second,
//            R.drawable.bags_third,
//            R.drawable.clothes_third,
//            R.drawable.watches_third,
//            R.drawable.shoes_third,
//            R.drawable.bags_fourth,
//            R.drawable.clothes_first,
//            R.drawable.shoes_fourth,
//            R.drawable.watches_fourth,
//
//    };

    public DashboardAdapter(Context context, List<JSONArray> bestsellersArray) {
        mContext = context;
        this.bestsellerArray = bestsellersArray;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bestsellerArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.grid_items, container, false);
//        RelativeLayout rl = (RelativeLayout) itemView.findViewById(R.id.bglayout);
//        rl.setBackgroundResource(mlayoutResource[position]);
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.type);
//
//        imageView.setImageResource(mResources[position]);
//        TextView amount = (TextView) itemView.findViewById(R.id.amount);
//        amount.setText("Rs." + amountarray[position] + " / Month");
//        TextView type = (TextView) itemView.findViewById(R.id.type);
//        type.setText(typearray[position]);
//        TextView cupons = (TextView) itemView.findViewById(R.id.cupons);
//        cupons.setText("Unlimited Cupons");
//        TextView deals = (TextView) itemView.findViewById(R.id.deals);
//        deals.setText("Unlimited Deals / Promos");
//        TextView submitUnlimet = (TextView) itemView.findViewById(R.id.submitUnlimted);
//        submitUnlimet.setText("Submit unlimited cupons and store");
//
//        container.addView(itemView);
//        if (itemView == null) {

//            grid = new View(mContext);
//            grid = inflater.inflate(R.layout.grid_items, null);
            TextView textView = (TextView) itemView.findViewById(R.id.grid_text);
            TextView textView1 = (TextView)itemView.findViewById(R.id.grid_text_sec);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.grid_image);

//                JSONArray jsArr = bestsellerArray.get(position);
//        for(int i=0;i<bestsellerArray.size();i++) {
//            try {
//                JSONObject jsonLineItem = jsArr.getJSONObject(i);
//                Log.d("OBJECT ID", jsonLineItem.getString("id"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

            //textView.setText(web[position]);
         //   imageView.setImageResource(imageId[position]);
       // textView1.setText(rate[position]);
//        } else {
//            itemView = (View) itemView;
//        }

//        Button addcartBtn = (Button)itemView.findViewById(R.id.home_addcart_btn);
//        addcartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                HomePage hp = new HomePage();
////                hp.setBadgeCount(mContext,"1");
//                HomePage.isChangeMenu = true;
//
//                if (mContext instanceof HomePage) {
//                    ((HomePage) mContext).onPrepareOptionsMenu(HomePage.menu);
//                }
//
//            }
//        });
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}