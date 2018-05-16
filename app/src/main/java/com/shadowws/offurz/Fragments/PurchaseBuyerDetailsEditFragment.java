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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;
import com.shadowws.offurz.adapter.PurchaseBuyerDetailsAdapter;

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
 * {@link PurchaseBuyerDetailsEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchaseBuyerDetailsEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseBuyerDetailsEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "PurchaseBuyerEdit";
    ProgressDialog pDialog;
    Dialog dialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int order_id;
    Bundle bundle;
    TextView pbdBuyerNameTxt,pbdMobileNo,pbdproductName,pbdCuponCodeTxt,pbdPriceTxt,pbdOffPercentageTxt,pbdOffPriceTxt,pbdPurchaseDateTxt;
    private OnFragmentInteractionListener mListener;
    String buyerName,mobileNo,productName;
//    public static MasterFragment newInstance() {
//        return new MasterFragment();
//    }
    public PurchaseBuyerDetailsEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseBuyerDetailsEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseBuyerDetailsEditFragment newInstance(String param1, String param2) {
        PurchaseBuyerDetailsEditFragment fragment = new PurchaseBuyerDetailsEditFragment();
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
        View pbdView = inflater.inflate(R.layout.fragment_purchase_buyer_details_edit, container, false);
         bundle = this.getArguments();
        pbdBuyerNameTxt = (TextView)pbdView.findViewById(R.id.pbd_buyerName);
        pbdMobileNo = (TextView)pbdView.findViewById(R.id.pbd_mobile);
        pbdproductName = (TextView)pbdView.findViewById(R.id.pbd_edit_productName);
        pbdCuponCodeTxt =(TextView)pbdView.findViewById(R.id.pbd_cuponCode);
        pbdOffPercentageTxt = (TextView)pbdView.findViewById(R.id.pbd_offPercentage);
        pbdOffPriceTxt = (TextView)pbdView.findViewById(R.id.pbd_OffPrice);
        pbdPurchaseDateTxt = (TextView)pbdView.findViewById(R.id.pbd_PurchaseDate);
        pbdPriceTxt = (TextView)pbdView.findViewById(R.id.pbd_price);

        Button editBtn = (Button)pbdView.findViewById(R.id.pbd_edit_confirm);
        Button cancelBtn = (Button)pbdView.findViewById(R.id.pbd_edit_cancel);
       // Toast.makeText(getContext(),"bundle"+bundle.getString("coupon_code"),Toast.LENGTH_SHORT).show();
        pbdproductName.setEnabled(false);
        pbdBuyerNameTxt.setEnabled(false);
        pbdMobileNo.setEnabled(false);
        pbdCuponCodeTxt.setEnabled(false);
        pbdOffPercentageTxt.setEnabled(false);
        pbdOffPriceTxt.setEnabled(false);
        pbdPurchaseDateTxt.setEnabled(false);
        pbdPriceTxt.setEnabled(false);

        if(bundle != null) {
            productName = bundle.getString("productName");
            buyerName = bundle.getString("buyerName");
            mobileNo = bundle.getString("mobileNumber");
            order_id = Integer.parseInt(bundle.getString("id"));
            Log.d("PrId",""+bundle.getInt("add_id"));
            Log.d(TAG,"orderId "+order_id);
            //Toast.makeText(getContext(), "bundle inside" + mobileNo, Toast.LENGTH_SHORT).show();
            pbdproductName.setText(productName);
            pbdMobileNo.setText(mobileNo);
            pbdBuyerNameTxt.setText(buyerName);
            pbdPriceTxt.setText(bundle.getString("price"));
            //pbdPurchaseDateTxt.setText(bundle.getString("purchaseDate"));
            pbdOffPercentageTxt.setText(bundle.getString("off_percentage"));
            pbdOffPriceTxt.setText(bundle.getString("off_price"));
            pbdCuponCodeTxt.setText(bundle.getString("coupon_code"));

if(bundle.getString("purchaseDate")!= null) {
    String Date = bundle.getString("purchaseDate");
    String[] splitDate = Date.split("-");
    if (splitDate.length > 2)
        pbdPurchaseDateTxt.setText(splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0]);
    else
        pbdPurchaseDateTxt.setText(bundle.getString("purchaseDate"));
}
            byte [] strAsByteArray = bundle.getString("purchaseDate").getBytes();

            byte [] result =
                    new byte [strAsByteArray.length];

            // Store result in reverse order into the
            // result byte[]
            for (int i = 0; i<strAsByteArray.length; i++)
                result[i] =
                        strAsByteArray[strAsByteArray.length-i-1];

          //  pbdPurchaseDateTxt.setText();


        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                 dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_dialog_confirm);
                dialog.show();
                Button okBtn = (Button)dialog.findViewById(R.id.yesBtn);
                Button noBtn = (Button)dialog.findViewById(R.id.noBtn);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PurchaseBuyerEditDetailsPost("http://offurz.com/order_confirm_json.php");

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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, new PurchaseBuyerDetailsFragment());
                transaction.commit();

            }
        });
        return pbdView;
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
    public void PurchaseBuyerEditDetailsPost(String url) {
        dialog.dismiss();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {

            js.put("add_id",bundle.getInt("add_id"));
            Log.d("add_id",""+bundle.getInt("add_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                //Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsObject = new JSONObject(response.toString());
                    Log.d("purchaseBuyerDetails", "Array value" + response.toString());
                    Log.d("purchaseBuyerDetails", "Array size" + response.length());
                    boolean msg = jsObject.getBoolean("success");
                    if(msg){
                        Toast.makeText(getContext(),jsObject.getString("message"),Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, new PurchaseBuyerDetailsFragment());
                        transaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(),jsObject.getString("message"),Toast.LENGTH_SHORT).show();
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
               // Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("purchaseDetails",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Time Out error", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                      //  Toast.makeText(getContext(), "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
                        ft.replace(R.id.home_container_layout, new PurchaseBuyerDetailsFragment());
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
