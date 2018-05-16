package com.shadowws.offurz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shadowws.offurz.Pojo.ProductName;
import com.shadowws.offurz.Pojo.ProductNames;
import com.shadowws.offurz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class ListViewAdapter extends BaseAdapter {
    private final List<ProductNames> productNamesList;
    private final ArrayList<ProductNames> arraylist;
    static String TAG="ListViewAdapter";
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;


    public ListViewAdapter(Context context, List<ProductNames> productNamesList) {
        mContext = context;
        this.productNamesList = productNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ProductNames>();
        this.arraylist.addAll(productNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return productNamesList.size();
    }

    @Override
    public ProductNames getItem(int position) {
        return productNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item_search, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(productNamesList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productNamesList.clear();
        if (charText.length() == 0) {
            productNamesList.addAll(arraylist);
        } else {
            for (ProductNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
