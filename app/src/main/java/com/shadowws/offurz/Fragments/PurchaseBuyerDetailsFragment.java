package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
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
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;
import com.shadowws.offurz.adapter.PurchaseBuyerDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurchaseBuyerDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchaseBuyerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseBuyerDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView purchaseBuyerDtlList;
    String[] prdoctName = {"Shoes","Watches","Clothes"};
    String[] buyerName = {"AAA ","BBB","CCC"};
    int[] mobileNo = {235612286,258896225,45586366};
    SharedPreferences pref;
    ProgressDialog pDialog;
    ArrayList<PurchaseDetails> purchaseDetails;

    public PurchaseBuyerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseBuyerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseBuyerDetailsFragment newInstance(String param1, String param2) {
        PurchaseBuyerDetailsFragment fragment = new PurchaseBuyerDetailsFragment();
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
        View purchaseBuyerDetails = inflater.inflate(R.layout.fragment_purchase_buyer_details, container, false);
        purchaseBuyerDtlList = (ListView)purchaseBuyerDetails.findViewById(R.id.purchase_buyer_details_listview);

      //  PurchaseBuyerDetailsGet("http://logistic.shadowws.in/get_pur_buyer_details.php");
        PurchaseBuyerDetailsGet("http://offurz.com/get_pur_buyer_details.php");

        return purchaseBuyerDetails;
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

    public void PurchaseBuyerDetailsGet(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        pref = getContext().getSharedPreferences("sellerData", 0);
//        Log.d("purchaseDetailsShare",pref.getString("user_id",null));
//        Log.d("purchaseDetailsShare",pref.getString("mobile",null));
//        Log.d("purchaseDetailsShare",pref.getString("company",null));
        JSONObject js = new JSONObject();
        try {

            Log.d("Mobile",pref.getString("mobile",null));
            js.put("s_id",pref.getString("user_id",null));
            js.put("com_mobile",pref.getString("mobile",null));
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
                    Log.d("purchaseBuyerDetails", "Array value" + response.toString());
                    Log.d("purchaseBuyerDetails", "Array size" + response.length());
                    JSONArray jsArray = jsObject.getJSONArray("items");
                    Log.d("Json Array",""+jsArray.toString());
                    Log.d("Json Array",""+jsArray.length());
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
                    if(purchaseDetails.size() > 0) {
                        PurchaseBuyerDetailsAdapter purchaseBuyerDetailsAd = new PurchaseBuyerDetailsAdapter(getContext(), purchaseDetails, getFragmentManager());
                        purchaseBuyerDtlList.setAdapter(purchaseBuyerDetailsAd);
                    }
                    else
                    {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new EmptyDataSeller()).addToBackStack(null);
                        fragmentTransaction.commit();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
              //  Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("purchaseDetails",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network Connections", Toast.LENGTH_LONG).show();
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
                        transaction.replace(R.id.main_container, new SellerHomePagePurchaseBuyer());
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
                        ft.replace(R.id.main_container, new PurchaseBuyerDetailsFragment());
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

//
//    @Override
//    public void onResume() {
//
//        super.onResume();
//            Log.d("ON RESUME","RESUME");
//
//
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
////                    moveTaskToBack(true);
////                    finish();
//                    final Dialog dialog = new Dialog(getActivity());
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.custom_dialog);
//                    Button btnSave = (Button) dialog.findViewById(R.id.save);
//                    Button cancel = (Button) dialog.findViewById(R.id.cancel);
//                    btnSave.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //                           moveTaskToBack(true);
////                                    finish();
////                                    System.exit(0);
//                            SharedPreferences exit = getActivity().getSharedPreferences("exit", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor exit1 = exit.edit();
//                            exit1.putString("exit", "splash");
//                            exit1.commit();
//                            getActivity().moveTaskToBack(true);
//                            getActivity().finish();
//                            System.exit(0);
//
//
//                        }
//                    });
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                                                  @Override
//                                                  public void onClick(View view) {
//                                                      dialog.dismiss();
//                                                  }
//                                              }
//                    );
//                    dialog.show();
//                    return true;
//                }
//
//
//                return false;
//            }
//
//        });
//
//    }
//
//    @Override
//    public void onPause() {
//        Log.e("DEBUG", "OnPause of HomeFragment");
//        super.onPause();
//    }



//
//    private FragmentManager.OnBackStackChangedListener getListener()
//    {
//        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
//        {
//            public void onBackStackChanged()
//            {
//                FragmentManager manager = getFragmentManager();
//
//                if (manager != null)
//                {
//                    if(manager.getBackStackEntryCount() >= 1){
//                        String topOnStack = manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName();
//                        Log.i("TOP ON BACK STACK",topOnStack);
//                    }
//                }
//            }
//        };
//
//        return result;
//    }

}
