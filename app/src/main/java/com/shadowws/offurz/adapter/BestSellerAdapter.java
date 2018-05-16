package com.shadowws.offurz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shadowws.offurz.Fragments.ViewProductDetailsFragment;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.Pojo.ProductNames;
import com.shadowws.offurz.R;
import com.shadowws.offurz.interfaces.ItemClickListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lenovo on 2/19/2018.
 */

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> implements Filterable {

    static String TAG = "BestSellerAdapter";
    private Context mContext;
    ViewPager viewPager;
    ArrayList<BestSeller> data,filterList;
    ViewProductDetailsFragment vf;
    Bundle bundle;
    CustomFilter filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView headertext, descTxt, countTxt, prdnameTxt, companyTxt, offerPecTxt, offerPriceTxt;
        public ImageView viewImage, overflow;

        public MyViewHolder(View view) {
            super(view);
            // headertext = (TextView) view.findViewById(R.id.header);
            prdnameTxt = (TextView) view.findViewById(R.id.bestSeller_productName);
            companyTxt = (TextView) view.findViewById(R.id.bestSeller_companyName);
            offerPecTxt = (TextView) view.findViewById(R.id.bestSeller_offerPercentage);
            offerPriceTxt = (TextView) view.findViewById(R.id.bestSeller_offerPrice);
            descTxt = (TextView)view.findViewById(R.id.bestSeller_fulldetails);
            viewImage = (ImageView) view.findViewById(R.id.grid_image);
            countTxt = (TextView) view.findViewById(R.id.countTxt);

            // count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.type);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }

        @Override
        public void onClick(View view) {
            view.setOnClickListener(this);

        }
    }


    public BestSellerAdapter(Context mContext, ArrayList<BestSeller> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.best_seller_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(itemView);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.prdnameTxt.setText(data.get(position).getProductName());
        holder.companyTxt.setText(data.get(position).getCompany());
        holder.offerPecTxt.setText(data.get(position).getOffPercentage() + " %");
        holder.offerPriceTxt.setText("Rs." + data.get(position).getOfferPrice());
        Log.d("BSA PD Name",data.get(position).getProductName());
        holder.countTxt.setText("Count:"+data.get(position).getCount());
//                  Picasso.with(mContext) // Context
//                    .load(data.get(position).getImage()).memoryPolicy(MemoryPolicy.NO_CACHE )
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .error(R.drawable.gift_100)
//                    .into(holder.viewImage);
        Glide.with(mContext)
                .load(data.get(position).getImage())
                .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
                .into(holder.viewImage);
                Log.d(TAG,"Image Url"+data.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"Click"+position,Toast.LENGTH_SHORT).show();
                vf = new ViewProductDetailsFragment();
                bundle= new Bundle();
                bundle.putString("product_id",""+ data.get(position).getId());
                Log.d(TAG,"productId"+data.get(position).getId());
                bundle.putString("product_name",data.get(position).getProductName());
                bundle.putString("Off_price",data.get(position).getOfferPrice());
                bundle.putString("Desc",data.get(position).getDescription());
                bundle.putString("Off_percentage",data.get(position).getOffPercentage());
                bundle.putString("image",data.get(position).getImage());
                bundle.putString("city",data.get(position).getCity());
                bundle.putString("state",data.get(position).getState());
                bundle.putString("mobile",data.get(position).getMobileNo());
                bundle.putString("company",data.get(position).getCompany());
                Log.d(TAG,"company"+data.get(position).getCompany());
                Log.d(TAG,"mobile"+data.get(position).getMobileNo());
                Log.d(TAG,"product_name"+data.get(position).getProductName());
                bundle.putString("price",String.valueOf(data.get(position).getAmount()));
                bundle.putString("image2",data.get(position).getImage2());
                vf.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.buyer_main_container, vf).addToBackStack(null);
                transaction.commit();
            }
        });


        // OurSellerPrice oursellerprice = albumList.get(position);
        // holder.headertext.setText(header[position]);
        // holder.count.setText(album.getNumOfSongs() + " songs");

//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        // inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new BestSellerAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"Size"+data.size());
        return data.size();
    }

    public static class CustomFilter extends Filter {

        BestSellerAdapter adapter;
        ArrayList<BestSeller> filterList;


        public CustomFilter(ArrayList<BestSeller> filterList, BestSellerAdapter adapter) {
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

            adapter.data = (ArrayList<BestSeller>) results.values;

            //REFRESH
            adapter.notifyDataSetChanged();
        }
    }
}