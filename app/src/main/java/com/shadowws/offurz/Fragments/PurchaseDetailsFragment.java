package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
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
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.Pojo.PurchaseDetails;
import com.shadowws.offurz.R;
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;
import com.shadowws.offurz.adapter.ManageAdsAdapter;
import com.shadowws.offurz.adapter.PurchaseDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurchaseDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchaseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "PurchaseDetails";
     RecyclerView mRecyclerView;
     RecyclerView.Adapter mAdapter;
     RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog pDialog;
    SharedPreferences pref;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<PurchaseDetails> purchaseDetails;

    private OnFragmentInteractionListener mListener;

    public PurchaseDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseDetailsFragment newInstance(String param1, String param2) {
        PurchaseDetailsFragment fragment = new PurchaseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View purchaseDetails = inflater.inflate(R.layout.fragment_purchase_details, container, false);
        mRecyclerView = (RecyclerView) purchaseDetails.findViewById(R.id.purchaseDetails_recylerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //PurchaseDetailsGet("http://logistic.shadowws.in/get_buyer_pur_de_an.php");
        PurchaseDetailsGet("http://offurz.com/get_buyer_pur_de_an.php");
       // mRecyclerView.setHasFixedSize(true);


        return purchaseDetails;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void PurchaseDetailsGet(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        pref = getContext().getSharedPreferences("buyerData", 0);
//        Log.d("purchaseDetailsShare",pref.getString("user_id",null));
  //      Log.d("purchaseDetailsShare",pref.getString("name",null));
    //    Log.d("purchaseDetailsShare",pref.getString("email",null));
//        Log.d("details",pref.getString("username",null));
//        Log.d("details",pref.getString("password",null));
        JSONObject js = new JSONObject();
        try {


            js.put("b_id",pref.getString("user_id",null));
            js.put("mobile",pref.getString("mobile",null));
            js.put("name",pref.getString("name",null));
            js.put("email",pref.getString("email",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                // Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsObject = new JSONObject(response.toString());
                    Log.d("purchaseDetails", "Array value" + response.toString());
                    Log.d("purchaseDetails", "Array size" + response.length());
                    JSONArray jsArray = jsObject.getJSONArray("items");
                    Log.d(TAG,"Json Array"+jsArray.toString());
                    Log.d(TAG,"Json Array"+jsArray.length());
                    purchaseDetails = new ArrayList<>();
                    for(int i=0; i<jsArray.length();i++) {

                        JSONObject childJSONObject = jsArray.getJSONObject(i);
                        PurchaseDetails pd = new PurchaseDetails();
                        pd.setId(Integer.parseInt(childJSONObject.getString("id")));
                        pd.setProduct_name(childJSONObject.getString("product_name"));
                        pd.setPurchase_date(childJSONObject.getString("purchase_date"));
                        pd.setCom_mobile(childJSONObject.getString("com_mobile"));
                        pd.setOff_percentage(childJSONObject.getString("off_percentage"));
                        pd.setConfirm_order(childJSONObject.getString("confirm_order"));
                        pd.setCoupon_code(childJSONObject.getString("coupon_code"));
                        pd.setEmail(childJSONObject.getString("email"));
                        pd.setCity(childJSONObject.getString("city"));
                        pd.setState(childJSONObject.getString("state"));
                        pd.setExpire_ads(childJSONObject.getString("expire_ads"));
                        pd.setMobile(childJSONObject.getString("mobile"));
                        pd.setState(childJSONObject.getString("state"));
                        pd.setEnter_ads(childJSONObject.getString("enter_ads"));
                        pd.setName(childJSONObject.getString("name"));
                        pd.setAprice(childJSONObject.getString("aprice"));
                        pd.setBprice(childJSONObject.getString("bprice"));
                        pd.setAdd_id(Integer.parseInt(childJSONObject.getString("add_id")));
                        purchaseDetails.add(pd);
                    }

                    if(purchaseDetails.size() >0) {


                        mAdapter = new PurchaseDetailsAdapterinside(getContext(), purchaseDetails);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                    else
                    {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.buyer_main_container, new EmptyData()).addToBackStack(null);
                        fragmentTransaction.commit();
                    }

//                    ManageAdsAdapter manageAdapter = new ManageAdsAdapter(getContext(),bestSellerArray,getFragmentManager());
//                    manageAdList.setAdapter(manageAdapter);
//                        bs.setImage("http://offurz.shadowws.in/uploads/"+childJSONObject.getString("image"));
//
//
//                        bestSellerArray.add(bs);
//                    }
//




//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                //Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("purchaseDetails",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network connections", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        //Toast.makeText(getContext(), "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
    public class PurchaseDetailsAdapterinside extends RecyclerView.Adapter<PurchaseDetailsAdapterinside.MyViewHolder> {
         String TAG = "PurchaseAdapter";

        private Context mContext;

        ArrayList<PurchaseDetails> purchaseDetails;
        ProgressDialog pDialog;
        Dialog dialog;
         int order_id;
        FragmentManager fm;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView headertext, count;
            TextView productNameTxt, couponCodeTxt, offPrecentageTxt, priceTxt;
            public ImageView thumbnail, overflow;
            ImageView deleteImg;


            public MyViewHolder(View view) {
                super(view);
                priceTxt = (TextView) view.findViewById(R.id.purchase_price_ans);
                offPrecentageTxt = (TextView) view.findViewById(R.id.purchase_offerPercentage_ans);
                couponCodeTxt = (TextView) view.findViewById(R.id.purchase_cuponCode_ans);
                productNameTxt = (TextView) view.findViewById(R.id.purchase_productDetailsName_ans);
                deleteImg = (ImageView) itemView.findViewById(R.id.delete_icon);

                //   headertext = (TextView) view.findViewById(R.id.header);
                // count = (TextView) view.findViewById(R.id.count);
                //thumbnail = (ImageView) view.findViewById(R.id.type);
                //overflow = (ImageView) view.findViewById(R.id.overflow);
            }
        }

        public PurchaseDetailsAdapterinside(Context mContext, ArrayList<PurchaseDetails> purchaseDetails) {
            this.mContext = mContext;
            this.purchaseDetails = purchaseDetails;
            //this.data = data;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.purchase_details_cardview, parent, false);
            // dashboardAdapter = new DashboardAdapter(mContext);
                Log.d("Recyler call","Call");

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.productNameTxt.setText(purchaseDetails.get(position).getProduct_name());
            holder.couponCodeTxt.setText(purchaseDetails.get(position).getCoupon_code());
            holder.priceTxt.setText("Rs." + purchaseDetails.get(position).getAprice());
            holder.offPrecentageTxt.setText(purchaseDetails.get(position).getOff_percentage() + "%");
            //notifyDataSetChanged();
            Log.d("orderId first", "" + purchaseDetails.get(position).getAdd_id());
            order_id = purchaseDetails.get(position).getAdd_id();
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.delete_dialog_box);
                    dialog.show();
                    Button yesBtn = (Button) dialog.findViewById(R.id.delete_yesBtn);
                    Button noBtn = (Button) dialog.findViewById(R.id.delete_noBtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("orderId second", "" + purchaseDetails.get(position).getAdd_id());
                            // PurchaseBuyerEditDetailsPost("http://logistic.shadowws.in/order_delete_json.php",purchaseDetails.get(position).getAdd_id());
                            PurchaseBuyerEditDetailsPost("http://offurz.com/order_delete_json.php", purchaseDetails.get(position).getAdd_id());
//                            mAdapter = new PurchaseDetailsAdapterinside(getContext(), purchaseDetails);
//                            mRecyclerView.setAdapter(mAdapter);
//                            mAdapter.notifyDataSetChanged();
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

                js.put("add_id", "" + order_id);
                Log.d("orderId third", "" + order_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, js, new com.android.volley.Response.Listener<JSONObject>() {
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
                        if (msg) {
                            Toast.makeText(mContext, jsObject.getString("success_msg"), Toast.LENGTH_SHORT).show();
                           Log.d("Prdouct Details","Delete");
                           Log.d("Products details",""+purchaseDetails.size());
                           Log.d("RecylerVew","RecylerView");
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                            transaction.replace(R.id.buyer_main_container, new HomePageFragment());
//                            transaction.commit();

//                            mLayoutManager = new LinearLayoutManager(getContext());
//                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter = new PurchaseDetailsAdapterinside(getContext(), purchaseDetails);
                            mRecyclerView.setAdapter(mAdapter);
//                            mAdapter.notifyDataSetChanged();
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
                    Log.d("purchaseDetails", error.toString());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext, "Check Network Connection", Toast.LENGTH_LONG).show();
                        Log.d("purchaseDetails", error.toString());
                    } else if (error instanceof AuthFailureError) {
                        //TODO
                    } else if (error instanceof ServerError) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            //Toast.makeText(mContext, "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
                            Log.d("purchaseDetails", error.toString());
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




    @Override
    public void onResume() {

        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    SharedPreferences back=getActivity().getSharedPreferences("back", Context.MODE_PRIVATE);
                    if(back.getInt("back",0)!=1) {
                        Log.d("back", String.valueOf(back.getInt("back",0)));
                        Log.d("kk","kk");
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.buyer_main_container, new HomePageFragment());
                        transaction.commit();
                    }
                    else
                    {
                        Log.d("back", String.valueOf(back.getInt("back",0)));
                        Log.d("kk123","kk");
                        SharedPreferences back1=getActivity().getSharedPreferences("back", Context.MODE_PRIVATE);
                        SharedPreferences.Editor bac=back1.edit();
                        bac.putInt("back",0);
                        bac.commit();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.home_container_layout, new PurchaseDetailsFragment());
                        ft.commit();
                    }
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.home_page_container,new HomePageFragment());
//                ft.commit();
                }
                return true;
            }
        });


    }
}
