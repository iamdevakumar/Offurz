package com.shadowws.offurz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowws.offurz.Fragments.ViewProductDetailsFragment;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lenovo on 2/22/2018.
 */

public class GridViewAdapter extends BaseAdapter implements Filterable,View.OnClickListener {
    static String TAG = "GridViewAdapter";
      ArrayList<BestSeller> bestSeller,filterList;
    Context mContext;
    ViewProductDetailsFragment vf;
    Bundle bundle;

    TextView textView;
    CustomFilter filter;

    public GridViewAdapter(Context c, ArrayList<BestSeller> bestSellerArray) {
        mContext = c;
        this.bestSeller = bestSellerArray;
        this.filterList = bestSellerArray;

    }

    @Override
    public int getCount() {
        return bestSeller.size();
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
        View grid;
        final int finalI = i;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.bestseller_gridview_item, null);
            textView = (TextView) grid.findViewById(R.id.bestSeller_productName);
           // TextView textView1 = (TextView) grid.findViewById(R.id.bestSeller_companyName);
            TextView imageView = (TextView) grid.findViewById(R.id.bestSeller_offerPercentage);
            TextView offPrice = (TextView)grid.findViewById(R.id.bestSeller_offerPrice);
            final ImageView image = (ImageView)grid.findViewById(R.id.grid_image);
            for (int j=i;i<bestSeller.size();i++) {
                textView.setText(bestSeller.get(j).getProductName());
                imageView.setText(bestSeller.get(j).getDescription());
               // textView1.setText(bestSeller.get(j).getOffPercentage());
                offPrice.setText("Rs. "+bestSeller.get(j).getOfferPrice());
                Picasso.with(mContext) // Context
                        .load(bestSeller.get(j).getImage()) // URL or file
                        .into(image);
                            }
        } else {
            grid = (View) view;
        }

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext,"Click "+ finalI,Toast.LENGTH_SHORT).show();
                 vf = new ViewProductDetailsFragment();
                bundle= new Bundle();
                bundle.putString("product_name",bestSeller.get(finalI).getProductName());
                bundle.putString("Off_price",bestSeller.get(finalI).getOfferPrice());
                bundle.putString("Desc",bestSeller.get(finalI).getDescription());
                bundle.putString("Off_percentage",bestSeller.get(finalI).getOffPercentage());
                bundle.putString("image",bestSeller.get(finalI).getImage());
                bundle.putString("company",bestSeller.get(finalI).getCompany());
                bundle.putString("mobile",bestSeller.get(finalI).getMobileNo());
                bundle.putString("city",bestSeller.get(finalI).getCity());
                bundle.putString("state",bestSeller.get(finalI).getState());
                bundle.putString("product_id",String.valueOf(bestSeller.get(finalI).getId()));
                bundle.putString("image2",bestSeller.get(finalI).getImage2());
                vf.setArguments(bundle);
                Log.d(TAG,"product_name"+bestSeller.get(finalI).getProductName());

                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.buyer_main_container, vf).addToBackStack(null);
                transaction.commit();
            }
        });
        return grid;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }

    @Override
    public void onClick(View view) {
        view.setOnClickListener(this);
    }

    public static class CustomFilter extends Filter {

        GridViewAdapter adapter;
        ArrayList<BestSeller> filterList;


        public CustomFilter(ArrayList<BestSeller> filterList, GridViewAdapter adapter) {
            this.adapter = adapter;
            this.filterList = filterList;

        }

        //FILTERING OCURS
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            //CHECK CONSTRAINT VALIDITY
            if (constraint != null && constraint.length() > 0) {
                //CHANGE TO UPPER
                constraint = constraint.toString().toUpperCase();
                //STORE OUR FILTERED PLAYERS
                ArrayList<BestSeller> filteredPlayers = new ArrayList<>();

                for (int i = 0; i < filterList.size(); i++) {
                    //CHECK
                    if (filterList.get(i).getProductName().toUpperCase().contains(constraint)) {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredPlayers.add(filterList.get(i));
                    }else if(filterList.get(i).getOffPercentage().toUpperCase().contains(constraint)){
                        filteredPlayers.add(filterList.get(i));
                    }else if(filterList.get(i).getCompany().toUpperCase().contains(constraint)){
                        filteredPlayers.add(filterList.get(i));
                    }else if(filterList.get(i).getOfferPrice().toUpperCase().contains(constraint)){
                        filteredPlayers.add(filterList.get(i));
                    }else if(filterList.get(i).getDescription().toUpperCase().contains(constraint)){
                        filteredPlayers.add(filterList.get(i));
                    }
                }

                results.count = filteredPlayers.size();
                results.values = filteredPlayers;
            } else {
                results.count = filterList.size();
                results.values = filterList;

            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            adapter.bestSeller = (ArrayList<BestSeller>) results.values;

            //REFRESH
            adapter.notifyDataSetChanged();
        }
    }
}
