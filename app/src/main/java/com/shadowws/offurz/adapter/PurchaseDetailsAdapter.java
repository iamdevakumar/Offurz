package com.shadowws.offurz.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shadowws.offurz.Pojo.PurchaseDetails;
import com.shadowws.offurz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Lenovo on 2/15/2018.
 */

public class PurchaseDetailsAdapter extends RecyclerView.Adapter<PurchaseDetailsAdapter.MyViewHolder> {
    static String TAG  = "PurchaseAdapter";

    private Context mContext;

    ArrayList<PurchaseDetails> purchaseDetails;
    ProgressDialog pDialog;
    Dialog dialog;
   static int order_id;
    FragmentManager fm;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView headertext, count;
        TextView productNameTxt,couponCodeTxt,offPrecentageTxt,priceTxt;
        public ImageView thumbnail, overflow;
        ImageView deleteImg;


        public MyViewHolder(View view) {
            super(view);
            priceTxt = (TextView)view.findViewById(R.id.purchase_price_ans);
            offPrecentageTxt = (TextView)view.findViewById(R.id.purchase_offerPercentage_ans);
            couponCodeTxt = (TextView)view.findViewById(R.id.purchase_cuponCode_ans);
            productNameTxt = (TextView)view.findViewById(R.id.purchase_productDetailsName_ans);
            deleteImg = (ImageView)itemView.findViewById(R.id.delete_icon);

         //   headertext = (TextView) view.findViewById(R.id.header);
            // count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.type);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public PurchaseDetailsAdapter(Context mContext, ArrayList<PurchaseDetails> purchaseDetails) {
        this.mContext = mContext;
        this.purchaseDetails = purchaseDetails;
        //this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_details_cardview, parent, false);
       // dashboardAdapter = new DashboardAdapter(mContext);








        return new PurchaseDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.productNameTxt.setText(purchaseDetails.get(position).getProduct_name());
        holder.couponCodeTxt.setText(purchaseDetails.get(position).getCoupon_code());
        holder.priceTxt.setText("Rs."+purchaseDetails.get(position).getAprice());
        holder.offPrecentageTxt.setText(purchaseDetails.get(position).getOff_percentage()+"%");
        Log.d("orderId first",""+purchaseDetails.get(position).getAdd_id());
        order_id = purchaseDetails.get(position).getAdd_id();
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.delete_dialog_box);
                dialog.show();
                Button yesBtn = (Button)dialog.findViewById(R.id.delete_yesBtn);
                Button noBtn = (Button)dialog.findViewById(R.id.delete_noBtn);
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("orderId second",""+purchaseDetails.get(position).getAdd_id());
                       // PurchaseBuyerEditDetailsPost("http://logistic.shadowws.in/order_delete_json.php",purchaseDetails.get(position).getAdd_id());
                            PurchaseBuyerEditDetailsPost("http://offurz.com/order_delete_json.php",purchaseDetails.get(position).getAdd_id());
                    }
                });
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

            }
        });


    }





    @Override
    public int getItemCount() {
        return purchaseDetails.size();
    }
    public void PurchaseBuyerEditDetailsPost(String url, int order_id) {
        dialog.dismiss();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject js = new JSONObject();
        try {

            js.put("add_id",""+order_id);
            Log.d("orderId third",""+order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

            //    Toast.makeText(mContext, " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsObject = new JSONObject(response.toString());
                    Log.d("purchaseBuyerDetails", "Array value" + response.toString());
                    Log.d("purchaseBuyerDetails", "Array size" + response.length());
                    boolean msg = jsObject.getBoolean("success");
                    if(msg) {
                        Toast.makeText(mContext,jsObject.getString("success_msg"),Toast.LENGTH_SHORT).show();
                    }

//                    JSONArray jsArray = jsObject.getJSONArray("items");
//                    Log.d("Json Array",""+jsArray.toString());
//                    Log.d("Json Array",""+jsArray.length());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                //Toast.makeText(mContext, "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("purchaseDetails",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(mContext, "Check Network Connection", Toast.LENGTH_LONG).show();
                    Log.d("purchaseDetails",error.toString());
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        //Toast.makeText(mContext, "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
                        Log.d("purchaseDetails",error.toString());
                    }
                    switch (networkResponse.statusCode) {
                    }
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
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
