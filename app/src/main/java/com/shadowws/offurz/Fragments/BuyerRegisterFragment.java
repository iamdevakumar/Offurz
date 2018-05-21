package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.shadowws.offurz.BuyerHomePage;
import com.shadowws.offurz.HomePage;
import com.shadowws.offurz.MainActivity;
import com.shadowws.offurz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuyerRegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuyerRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerRegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "BuyerRegisterFragment";
    Dialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog pDialog;
    ArrayAdapter<String> adapter,stateadapter;
    private OnFragmentInteractionListener mListener;
    ArrayList<String> stateArray;
    ArrayList<String> citArray;
    Spinner Cityspinner,Statespinner;
    Bundle bundle;
    TextInputEditText buyerNameTxt,buyerEmailTxt,buyerAddressTxt,buyerMobileTxt,buyerUserNameTxt,buyerPasswordTxt,buyerconfirmPasswordTxt;
    TextInputLayout buyerNameLay,buyerEmailLay,buyerAddressLay,buyerMobileLay,buyerUserNameLay,buyerPasswordLay;

    public BuyerRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyerRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyerRegisterFragment newInstance(String param1, String param2) {
        BuyerRegisterFragment fragment = new BuyerRegisterFragment();
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
        Log.d(TAG,"BuyerRegister");
        String [] cities =
                {"Select the city","Coimbatore","Madurai","Trichy","Salem","Karur","Erode","Ooty",};
        String [] states =
                {"Select the state","Tamil Nadu","Kerala","Karnataka"};
        View buyerView = inflater.inflate(R.layout.fragment_buyer_register, container, false);

         Cityspinner = (Spinner) buyerView.findViewById(R.id.spinner_city);
         Statespinner = (Spinner) buyerView.findViewById(R.id.spinner_state);





       GetSateandCity("http://offurz.com/city_state.php");




        buyerNameTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_name);
        buyerEmailTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_name);
        buyerMobileTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_mobileNo);
        buyerAddressTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_address);
        buyerUserNameTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_userName);
        buyerPasswordTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_password);
        buyerconfirmPasswordTxt = (TextInputEditText)buyerView.findViewById(R.id.buyerReg_confirm_password);

        buyerNameLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_name_layout);
        buyerEmailLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_email_layout);
        buyerMobileLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_mobileNo_layout);
        buyerAddressLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_address_layout);
        buyerUserNameLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_useName_layout);
        buyerPasswordLay = (TextInputLayout)buyerView.findViewById(R.id.buyer_password_layout);
         bundle = this.getArguments();
        if(bundle != null) {
           // buyerNameTxt.setText(bundle.getString("name"));
            buyerUserNameTxt.setText(bundle.getString("name"));
            buyerMobileTxt.setText(bundle.getString("mobile"));
        }

        Button buyerRegBut = (Button)buyerView.findViewById(R.id.buyerRegSubmit);
        buyerRegBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent homeIntent = new Intent(getContext(), BuyerHomePage.class);
//                startActivity(homeIntent);

                buyerUserNameLay.setEnabled(false);
                buyerMobileTxt.setEnabled(false);
                if (buyerNameTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerNameLay.setError("Please Enter Name");
                } else if (buyerMobileTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerMobileLay.setError("Please Enter Name");
                } else if (buyerAddressTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerAddressLay.setError("Please Enter Name");
                } else if (buyerEmailTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerEmailLay.setError("Please Enter Name");
                } else if (buyerMobileTxt.getText().toString().length() < 10) {
                    buyerMobileLay.setError("Please Enter Name");
                } else if (buyerUserNameTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerUserNameLay.setError("Please Enter Username");
                } else if (buyerPasswordTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    buyerPasswordLay.setError("Please Enter Password");
                } else if (buyerPasswordTxt.getText().toString().trim().equalsIgnoreCase(buyerconfirmPasswordTxt.getText().toString().trim())) {
                    buyerPasswordLay.setError("Please Enter Correct Password");
                } else
                   // BuyerRegistrationPost("http://logistic.shadowws.in/buyer_add.php");
                BuyerRegistrationPost("http://offurz.com/buyer_add.php");
            }
        });

        return buyerView;
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

    public void BuyerRegistrationPost(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {
            Log.d("Spinner",Statespinner.getSelectedItem().toString()+" "+Cityspinner.getSelectedItem().toString());
            js.put("name", buyerNameTxt.getText().toString());
            js.put("mobile",buyerMobileTxt.getText().toString());
            js.put("address",buyerAddressTxt.getText().toString());
            js.put("email",buyerEmailTxt.getText().toString());
            js.put("state",Statespinner.getSelectedItem().toString());
            js.put("city", Cityspinner.getSelectedItem().toString());
            js.put("username",buyerUserNameTxt.getText().toString());
            js.put("password",buyerPasswordTxt.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d(TAG, jsonResponse.toString());
                   boolean message = jsonResponse.getBoolean("success");
                    if (message) {



                            JSONArray jsonObject = jsonResponse.getJSONArray("items");
                        for(int i=0;i<jsonObject.length();i++) {
                            JSONObject jsObj = jsonObject.getJSONObject(i);
                            SharedPreferences pref = getActivity().getSharedPreferences("buyerData", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("user_id", jsObj.getString("id"));
                            editor.putString("name", jsObj.getString("name"));
                            editor.putString("city", jsObj.getString("city"));
                            //editor.putString("state",jsonResponse.getString("state"));
                            editor.putString("mobile", jsObj.getString("mobile"));
                            editor.putString("email", jsObj.getString("email"));
                            editor.commit();
                           // Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                            Intent homeIntent = new Intent(getContext(), BuyerHomePage.class);
                            startActivity(homeIntent);
                        }
                    }
                    else {
                       // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                        dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.error_layout);
                        dialog.show();
                        Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                        TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                        errorTxt.setText(response.getString("success_msg"));
                        yesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                //Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check  Network Connections", Toast.LENGTH_LONG).show();
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



    public void GetSateandCity(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d(TAG, jsonResponse.toString());
                    boolean message = jsonResponse.getBoolean("success");
                    JSONArray jsArray = jsonResponse.getJSONArray("success_msg");
                    citArray = new ArrayList<>();
                    stateArray = new ArrayList<>();
                    if (message) {
                        //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsArray.length();i++){
                            JSONObject jsObj = jsArray.getJSONObject(i);

                            stateArray.add(jsObj.getString("state"));
                            citArray.add(jsObj.getString("city"));
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(stateArray);
                        stateArray.clear();
                        stateArray.addAll(hs);
                        if(citArray.size()>0){
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, citArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            Cityspinner.setAdapter(adapter);
                        }
                        if(stateArray.size() > 0){
                            stateadapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,stateArray);
                            stateadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            Statespinner.setAdapter(stateadapter);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                //Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check  Network Connections", Toast.LENGTH_LONG).show();
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
}

