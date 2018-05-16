package com.shadowws.offurz.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shadowws.offurz.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 3/12/2018.
 */

public class ViewImageAdapter extends PagerAdapter {
    static String TAG = "ViewImageAdapter";
    Context mContext;
    ArrayList<String> image2;
    LayoutInflater mLayoutInflater;


    public ViewImageAdapter(Context context, ArrayList<String> image2) {
        mContext = context;
        this.image2 = image2;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return image2.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_image, container, false);
        ImageView img = (ImageView)itemView.findViewById(R.id.viewImage);
        Log.d("Image View",image2.get(position));
        Glide.with(mContext)
                    .load(image2.get(position))
                .override(600,200)
                    .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
                    .into(img);
            Log.d(TAG,"view Image Url"+image2.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}