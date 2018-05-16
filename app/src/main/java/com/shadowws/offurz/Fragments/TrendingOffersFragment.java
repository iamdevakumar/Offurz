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
import android.widget.SearchView;
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
import com.android.volley.toolbox.Volley;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;
import com.shadowws.offurz.adapter.BestSellerAdapter;

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
 * {@link TrendingOffersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrendingOffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingOffersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog pDialog;
    ArrayList<BestSeller> bestSellerArray;
    Dialog dialog;
    SearchView sv;

    private OnFragmentInteractionListener mListener;

    public TrendingOffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrendingOffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendingOffersFragment newInstance(String param1, String param2) {
        TrendingOffersFragment fragment = new TrendingOffersFragment();
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
        View trendingOfferView = inflater.inflate(R.layout.fragment_trending_offers, container, false);

       // TrendingOffersGet("http://logistic.shadowws.in/trending_an.php");
        TrendingOffersGet("http://offurz.com/trending_an.php");

        mRecyclerView = (RecyclerView) trendingOfferView.findViewById(R.id.trendingOffer_recyler);
        sv = (SearchView)trendingOfferView.findViewById(R.id.search_product);
        return trendingOfferView;
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

    public void TrendingOffersGet(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                //Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsarray = new JSONArray(response.toString());
                    Log.d("BestSeller", "Array value" + response.toString());
                    Log.d("BestSeller", "Array size" + jsarray.length());
                    bestSellerArray = new ArrayList<>();
                    for (int i = 0; i < jsarray.length(); i++) {  // **line 2**
                        JSONObject childJSONObject = jsarray.getJSONObject(i);
                        Log.d("inside Trending",""+jsarray.getJSONObject(i));
                        BestSeller bs = new BestSeller();
                        bs.setProductName(childJSONObject.getString("product_name"));
                        Log.d("TD PD Name",childJSONObject.getString("product_name"));
                        bs.setCompany(childJSONObject.getString("company"));
                        bs.setOfferPrice(childJSONObject.getString("off_price"));
                        bs.setOffPercentage(childJSONObject.getString("off_percentage"));
                        bs.setDescription(childJSONObject.getString("description"));
                        bs.setId(Integer.parseInt(childJSONObject.getString("id")));
                        bs.setState(childJSONObject.getString("state"));
                        bs.setMobileNo(childJSONObject.getString("mobile"));
                        bs.setCity(childJSONObject.getString("city"));
                        bs.setCount(childJSONObject.getInt("count"));
                        Log.d("trending",childJSONObject.getString("city"));
                        bs.setAmount(Integer.parseInt(childJSONObject.getString("a_price")));
                        bs.setImage2(childJSONObject.getString("image2"));
                        bs.setImage("http://offurz.com/uploads/"+childJSONObject.getString("image"));
                        Log.d("Trending","http://offurz.com/uploads/"+childJSONObject.getString("image"));
                        bestSellerArray.add(bs);
                    }
                if(bestSellerArray.size() > 0) {
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    final BestSellerAdapter mAdapter = new BestSellerAdapter(getContext(), bestSellerArray);
                    mRecyclerView.setAdapter(mAdapter);
                    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            mAdapter.getFilter().filter(s);
                            return false;
                        }
                    });

                }
                else
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.buyer_main_container, new EmptyData());
                    fragmentTransaction.commit();
                }
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
               // Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("SellerReg",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                   // Toast.makeText(getContext(), "Time Out error", Toast.LENGTH_LONG).show();
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                    dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.error_layout);
                    dialog.show();
                    Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                    TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                    errorTxt.setText("Time Out Error");
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });
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
                        ft.replace(R.id.home_container_layout, new TrendingOffersFragment());
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
