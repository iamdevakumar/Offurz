package com.shadowws.offurz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shadowws.offurz.Pojo.ProductNames;
import com.shadowws.offurz.Pojo.Rating;
import com.shadowws.offurz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo on 3/2/2018.
 */

public class ListAdapter extends BaseAdapter {
   // private final ArrayList<ProductNames> arraylist;

    // Declare Variables
    static String TAG = "ListAdapter";

    Context mContext;
    LayoutInflater inflater;
ArrayList<Rating> ratingList;

    public ListAdapter(Context context, ArrayList<Rating> ratingList) {
        mContext = context;
        this.ratingList = ratingList;
        inflater = LayoutInflater.from(mContext);

    }

    public class ViewHolder {
        TextView reviewName,reviewContent,reviewDate;
    }

    @Override
    public int getCount() {
        Log.d(TAG,"SIze"+ratingList.size());
        return ratingList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int position) {

        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.review_list, null);
            // Locate the TextViews in listview_item.xml
           holder.reviewName = (TextView) view.findViewById(R.id.reviewName);
            holder.reviewContent = (TextView) view.findViewById(R.id.reviewContent);
            holder.reviewDate = (TextView)view.findViewById(R.id.reviewDate);
            holder.reviewDate.setText(ratingList.get(position).getRating_date());
            holder.reviewContent.setText(ratingList.get(position).getReviews());
            holder.reviewName.setText(ratingList.get(position).getBuyer_name());
                view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
       // holder.name.setText(productNamesList.get(position).getName());
        return view;
    }


}