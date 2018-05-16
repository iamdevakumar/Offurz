package com.shadowws.offurz.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.shadowws.offurz.Fragments.ViewProductDetailsFragment;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 3/1/2018.
 */

public class HomeCommonAdapter extends BaseAdapter {
    static String TAG = "HomeCommonAdapter";
    private final ArrayList<BestSeller> bestSeller;
    Context mContext;
    TextView textView;
     int ProductId;
    ProgressDialog progressDialog,pDialog;
    SharedPreferences pref;
    public HomeCommonAdapter(Context c, ArrayList<BestSeller> bestSellerArray) {
        mContext = c;
        this.bestSeller = bestSellerArray;

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
            grid = inflater.inflate(R.layout.homecommon_gridview_item, null);
            textView = (TextView) grid.findViewById(R.id.bestSeller_productName);
            TextView imageView = (TextView) grid.findViewById(R.id.bestSeller_offerPercentage);
            TextView offPrice = (TextView)grid.findViewById(R.id.bestSeller_offerPrice);
            final ImageView image = (ImageView)grid.findViewById(R.id.grid_image);
            Button getCupon = (Button)grid.findViewById(R.id.getCuponCode);
            for (int j=i;i<bestSeller.size();i++) {
                textView.setText(bestSeller.get(j).getProductName());
                imageView.setText(bestSeller.get(j).getOffPercentage()+"%");
                //textView1.setText(bestSeller.get(j).getOffPercentage());
                offPrice.setText("Rs."+bestSeller.get(j).getOfferPrice());
                Log.d(TAG,"Product Id"+bestSeller.get(j).getId());

//                Picasso.with(mContext) // Context
//                        .load(bestSeller.get(j).getImage()) // URL or file
//                        .into(image);

                Glide.with(mContext)
                        .load(bestSeller.get(j).getImage())
                        .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
                        .into(image);
                Log.d(TAG,"Image Url"+bestSeller.get(j).getImage());


                progressDialog =new ProgressDialog(mContext);
                progressDialog.setMessage("Image Loading.....");

                getCupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Button Id",""+bestSeller.get(finalI).getId());
                        ProductId = bestSeller.get(finalI).getId();
                       // HomeCommonGet("http://logistic.shadowws.in/add_cart_json.php");
                        HomeCommonGet("http://offurz.com/add_cart_json.php");


                    }
                });
                grid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(mContext,"Click product "+bestSeller.get(finalI).getId(),Toast.LENGTH_SHORT).show();
                      ViewProductDetailsFragment  vf = new ViewProductDetailsFragment();
                      Bundle  bundle= new Bundle();
                        bundle.putString("product_id",""+ bestSeller.get(finalI).getId());
                        Log.d(TAG,"ProductId"+bestSeller.get(finalI).getId());
                        bundle.putString("product_name",bestSeller.get(finalI).getProductName());
                        bundle.putString("Off_price",bestSeller.get(finalI).getOfferPrice());
                        bundle.putString("description",bestSeller.get(finalI).getDescription());
                        bundle.putString("Off_percentage",bestSeller.get(finalI).getOffPercentage());
                        bundle.putString("image",bestSeller.get(finalI).getImage());
                        bundle.putString("city",bestSeller.get(finalI).getCity());
                        bundle.putString("state",bestSeller.get(finalI).getState());
                        bundle.putString("company",bestSeller.get(finalI).getCompany());
                        bundle.putString("mobile",bestSeller.get(finalI).getMobileNo());
                        bundle.putString("image2",bestSeller.get(finalI).getImage2());
                        vf.setArguments(bundle);
                        FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.buyer_main_container, vf).addToBackStack(null);
                        transaction.commit();
                    }
                });

            }
        } else {
            grid = (View) view;
        }

        return grid;
}
    public void HomeCommonGet(String url) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
         pref = mContext.getSharedPreferences("buyerData", 0);
        JSONObject js = new JSONObject();
        try {

            js.put("fid", String.valueOf(ProductId));
            js.put("id", pref.getString("user_id",null));
            js.put("name",pref.getString("name",null));
            js.put("city",pref.getString("city",null));
            js.put("mobile",pref.getString("mobile",null));
            js.put("state",pref.getString("state",null));
            js.put("username",pref.getString("username",null));
            js.put("password",pref.getString("password",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

               // Toast.makeText(mContext, " Result" + response.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,response.toString());
                try {

                    JSONObject jsarray = new JSONObject(response.toString());
                    Log.d(TAG, "Array value" + response.toString());
                    Log.d(TAG, "Array size" + jsarray.length());
                    boolean message = jsarray.getBoolean("success");
                        if(message){
                            Toast.makeText(mContext,"Get Coupon Code Successfully",Toast.LENGTH_SHORT).show();
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
               // Toast.makeText(mContext, "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(mContext, "Time Out error", Toast.LENGTH_LONG).show();
                    Log.d(TAG,error.toString());
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        Log.d(TAG,error.toString());
                       // Toast.makeText(mContext, "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
                    }
                    switch (networkResponse.statusCode) {
                    }
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    Log.d(TAG,error.toString());
                    //TODO
                }
                // TODO Auto-generated method stub
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Application", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }
}
