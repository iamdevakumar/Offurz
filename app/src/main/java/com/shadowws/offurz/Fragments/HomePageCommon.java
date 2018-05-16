package com.shadowws.offurz.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
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
import com.android.volley.toolbox.Volley;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;
import com.shadowws.offurz.adapter.GridViewAdapter;
import com.shadowws.offurz.adapter.HomeCommonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePageCommon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePageCommon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageCommon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "HomePageCommon";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GridView gridView;
    ProgressDialog pDialog;
    ViewPager viewPager;
    ArrayList<BestSeller> bestSellerArray = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public HomePageCommon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageCommon.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageCommon newInstance(String param1, String param2) {
        HomePageCommon fragment = new HomePageCommon();
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
        View homeCommonView = inflater.inflate(R.layout.fragment_home_page_common, container, false);
        gridView = (GridView) homeCommonView.findViewById(R.id.home_gridview);
        //viewPager = (ViewPager) homeCommonView.findViewById(R.id.pager);
        final Bundle bundle = this.getArguments();
        if(bundle != null) {
            String url = bundle.getString("url");
            HomeCommonGet(url);
        }
//        if (!getActivity().isFinishing() && pDialog != null) {
//            pDialog.dismiss();
//        }
       return homeCommonView;
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

    public void HomeCommonGet(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (pDialog != null){
                    pDialog.dismiss();
                }

                // TODO Auto-generated method stub

               // Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {

                    JSONArray jsarray = new JSONArray(response.toString());
                    Log.d(TAG, "Array value" + response.toString());
                    Log.d(TAG, "Array size" + jsarray.length());

                    for (int i = 0; i < jsarray.length(); i++) {  // **line 2**
                        JSONObject childJSONObject = jsarray.getJSONObject(i);

                        BestSeller bs = new BestSeller();
                        bs.setId(Integer.parseInt(childJSONObject.getString("id")));
                        bs.setProductName(childJSONObject.getString("product_name"));
                        bs.setCompany(childJSONObject.getString("company"));
                        bs.setOfferPrice(childJSONObject.getString("off_price"));
                        bs.setOffPercentage(childJSONObject.getString("off_percentage"));
                        bs.setDescription(childJSONObject.getString("description"));
                        bs.setId(Integer.parseInt(childJSONObject.getString("id")));
                        bs.setCuponCode(childJSONObject.getString("coupon_code"));
                        bs.setMobileNo(childJSONObject.getString("mobile"));
                        bs.setCity(childJSONObject.getString("city"));
                        bs.setState(childJSONObject.getString("state"));
                        bs.setImage("http://offurz.com/uploads/"+childJSONObject.getString("image"));
                        bs.setImage2(childJSONObject.getString("image2"));
                        bestSellerArray.add(bs);
                        //data.add(childJSONObject.getString("product_name"));
                    }
                    Log.d("BestArray","BestArray");
                    Log.d("BestArray",""+bestSellerArray.size());
                    if(bestSellerArray.size() > 0) {

                        final HomeCommonAdapter gridViewAdapter = new HomeCommonAdapter(getContext(), bestSellerArray);
                        gridView.setAdapter(gridViewAdapter);
                    }else
                    {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.buyer_main_container, new EmptyData());
                        transaction.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pDialog.isShowing())
                pDialog.dismiss();
               // Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network connections", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                       // Toast.makeText(getContext(), "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
    @Override
    public void onPause() {
        super.onPause();

        if(pDialog != null)
            pDialog .dismiss();
        pDialog = null;
    }
}
