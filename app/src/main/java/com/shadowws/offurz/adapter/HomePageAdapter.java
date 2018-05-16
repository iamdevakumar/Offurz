package com.shadowws.offurz.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.Pojo.OurSellerPrice;
import com.shadowws.offurz.R;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lenovo on 2/13/2018.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

    private final List<JSONArray> bestsellersArray;
    static String TAG = "HomePageAdapter";
    private Context mContext;
    DashboardAdapter dashboardAdapter;
    ViewPager viewPager;
    private int[] data;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    Handler handler = null;
    Runnable Update;
   // private List<OurSellerPrice> albumList;
   GridView grid;

    String[] header = {
            "New Updates",
            "Trending Offers",
            "Hot deals",
            "Best Seller"
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView headertext, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
           headertext = (TextView) view.findViewById(R.id.header);
           // count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.type);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public HomePageAdapter(Context mContext, List<JSONArray> bestSellersArray) {
        this.mContext = mContext;
        this.bestsellersArray = bestSellersArray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_cardview, parent, false);
        dashboardAdapter = new DashboardAdapter(mContext,bestsellersArray);

        viewPager = (ViewPager) itemView.findViewById(R.id.pager);
        viewPager.setAdapter(dashboardAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)
               itemView.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == 16) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);


//        CustomGrid adapter = new CustomGrid(mContext, web, imageId, rate);
//        grid = (GridView) itemView.findViewById(R.id.grid);
//        grid.setAdapter(adapter);
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(mContext, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {



       // OurSellerPrice oursellerprice = albumList.get(position);
       holder.headertext.setText(header[position]);
       // holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        //Glide.with(mContext).load(oursellerprice.getCupons()).into(holder.thumbnail);
//
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
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
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
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
        //Log.d("arraSize",""+header.length);
        return header.length;

    }

}
