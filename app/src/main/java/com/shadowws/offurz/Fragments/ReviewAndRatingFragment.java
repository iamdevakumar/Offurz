package com.shadowws.offurz.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.shadowws.offurz.adapter.ListAdapter;
import com.shadowws.offurz.adapter.PurchaseDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewAndRatingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewAndRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewAndRatingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "ReviewRating";
    RatingBar rateBar;
    EditText editText;
    Button submitBtn;
    SharedPreferences pref;
    ProgressDialog pDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView list;
Bundle bundle;
    private OnFragmentInteractionListener mListener;

    public ReviewAndRatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewAndRatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewAndRatingFragment newInstance(String param1, String param2) {
        ReviewAndRatingFragment fragment = new ReviewAndRatingFragment();
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

        // Inflate the layout for this fragment
        View ratingView = inflater.inflate(R.layout.fragment_review_and_rating, container, false);
  submitBtn = (Button)ratingView.findViewById(R.id.reviewSubmitBtn);
        editText = (EditText)ratingView.findViewById(R.id.rating_editText);
        rateBar = (RatingBar)ratingView.findViewById(R.id.ratingBar);
        pref = getContext().getSharedPreferences("buyerData",0);
        bundle = this.getArguments();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,editText.getText().toString());
                Log.d(TAG,""+rateBar.getRating());

                RateReviewPost("http://offurz.com/rating_json.php");
            }
        });

        return ratingView;
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

    public void RateReviewPost(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
//
//        pref = getContext().getSharedPreferences("buyerData", 0);
//        Log.d("purchaseDetailsShare",pref.getString("user_id",null));
//        Log.d("purchaseDetailsShare",pref.getString("name",null));
//        Log.d("purchaseDetailsShare",pref.getString("email",null));
//        Log.d("details",pref.getString("username",null));
//        Log.d("details",pref.getString("password",null));
        JSONObject js = new JSONObject();
        try {
            js.put("buyer_id",pref.getString("user_id",null));
            js.put("buyer_name",pref.getString("name",null));
            js.put("buyer_email",pref.getString("email",null));
            js.put("buyer_mobile",pref.getString("mobile",null));
            js.put("product_id",bundle.getString("product_id"));
            js.put("product_name",bundle.getString("product_name"));
            js.put("rating",rateBar.getRating());
            js.put("company",bundle.getString("company"));
            js.put("mobile",bundle.getString("mobile"));
            js.put("review",editText.getText().toString());

            Log.d(TAG,"b_id"+pref.getString("user_id",null));
            Log.d(TAG,"b_mobile"+pref.getString("mobile",null));
            Log.d(TAG,"b_email"+pref.getString("email",null));
            Log.d(TAG,"b_name"+pref.getString("name",null));
            Log.d(TAG,"product_id"+bundle.getString("product_id",null));
            Log.d(TAG,"product_name"+bundle.getString("product_name",null));
            Log.d(TAG,"company"+bundle.getString("company",null));
            Log.d(TAG,"mobile"+bundle.getString("mobile",null));


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
                    Log.d(TAG, "Array value" + response.toString());
                    Log.d(TAG, "Array size" + response.length());
                    boolean msg = jsObject.getBoolean("success");
                    if(msg){
                            Toast.makeText(getContext(),"Review and rating added successfully",Toast.LENGTH_SHORT).show();
//
//                                ViewProductDetailsFragment vf = new ViewProductDetailsFragment();
//                        Bundle b = new Bundle();
//                        b.putString("product_id",bundle.getString("product_id",null));
//                        vf.setArguments(b);
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.buyer_main_container, vf);
//                        transaction.commit();
                    }
//                    purchaseDetails = new ArrayList<>();
//                    for(int i=0; i<jsArray.length();i++) {
//
//                        JSONObject childJSONObject = jsArray.getJSONObject(i);
//                        PurchaseDetails pd = new PurchaseDetails();
//                        pd.setId(Integer.parseInt(childJSONObject.getString("id")));
//                        pd.setProduct_name(childJSONObject.getString("product_name"));
//                        pd.setPurchase_date(childJSONObject.getString("purchase_date"));
//                        pd.setCom_mobile(childJSONObject.getString("com_mobile"));
//                        pd.setOff_percentage(childJSONObject.getString("off_percentage"));
//                        pd.setConfirm_order(childJSONObject.getString("confirm_order"));
//                        pd.setCoupon_code(childJSONObject.getString("coupon_code"));
//                        pd.setEmail(childJSONObject.getString("email"));
//                        pd.setCity(childJSONObject.getString("city"));
//                        pd.setState(childJSONObject.getString("state"));
//                        pd.setExpire_ads(childJSONObject.getString("expire_ads"));
//                        pd.setMobile(childJSONObject.getString("mobile"));
//                        pd.setState(childJSONObject.getString("state"));
//                        pd.setEnter_ads(childJSONObject.getString("enter_ads"));
//                        pd.setName(childJSONObject.getString("name"));
//                        pd.setAprice(childJSONObject.getString("aprice"));
//                        pd.setBprice(childJSONObject.getString("bprice"));
//                        purchaseDetails.add(pd);
//                    }
//
//
//                    mRecyclerView.setLayoutManager(mLayoutManager);
//                    mAdapter = new PurchaseDetailsAdapter(getContext(),purchaseDetails);
//                    mRecyclerView.setAdapter(mAdapter);
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
                Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("purchaseDetails",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Time Out error", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        Toast.makeText(getContext(), "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
