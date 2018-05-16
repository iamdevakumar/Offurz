package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.shadowws.offurz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangePasswordBuyer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangePasswordBuyer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordBuyer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog pDialog;
    String user_id;
    String typeOfString;
    EditText oldPassTxt,newPassTxt,retypePassTxt;
    Dialog dialog;

    private OnFragmentInteractionListener mListener;

    public ChangePasswordBuyer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordBuyer.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordBuyer newInstance(String param1, String param2) {
        ChangePasswordBuyer fragment = new ChangePasswordBuyer();
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
        View settingView = inflater.inflate(R.layout.fragment_change_password_buyer, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("login", 0);
        final SharedPreferences sellerPref = getActivity().getSharedPreferences("sellerData",0);
        final SharedPreferences buyerPref = getActivity().getSharedPreferences("buyerData",0);
        oldPassTxt = (EditText)settingView.findViewById(R.id.setting_oldPass);
        newPassTxt = (EditText)settingView.findViewById(R.id.setting_newPass);
        retypePassTxt = (EditText)settingView.findViewById(R.id.setting_reTypePass);
        Button btn = (Button)settingView.findViewById(R.id.setting_btn);

        Log.d("Login Type",pref.getString("type",""));
        typeOfString = pref.getString("type","");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(oldPassTxt.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Enter Old password",Toast.LENGTH_SHORT).show();

                }else if(newPassTxt.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Enter New password",Toast.LENGTH_SHORT).show();

                }else if(retypePassTxt.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Enter Re-Type password",Toast.LENGTH_SHORT).show();

                }else {
                    if (typeOfString.equals("buyer")) {
                        // Toast.makeText(getContext(), " TYPE BUYER", Toast.LENGTH_SHORT).show();
                        user_id = buyerPref.getString("user_id", null);
                        //SettingPost("http://logistic.shadowws.in/buyer_setting_json.php");
                        SettingPost("http://offurz.com/buyer_setting_json.php");
                    } else if (typeOfString.equals("seller")) {
                        // Toast.makeText(getContext(), " TYPE SELLER", Toast.LENGTH_SHORT).show();
                        user_id = sellerPref.getString("user_id", null);
                        //  SettingPost("http://logistic.shadowws.in/seller_setting_json.php");
                        SettingPost("http://offurz.com/seller_setting_json.php");
                    }
                }

            }
        });
        return settingView;
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

    public void SettingPost(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject params = new JSONObject();
        try{
            params.put("user_id", user_id);
            params.put("old_pass",oldPassTxt.getText().toString());
            params.put("pass1",newPassTxt.getText().toString());
            params.put("pass2",retypePassTxt.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,params, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d("RESPONSE", jsonResponse.toString());
                    Boolean message = jsonResponse.getBoolean("success");

                    if(message) {


                        Toast.makeText(getContext(), "Password Update Successfully", Toast.LENGTH_LONG).show();
                        if(typeOfString.equals("buyer")){

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.buyer_main_container, new HomePageFragment());
                            transaction.commit();
                        }else if(typeOfString.equals("seller")){
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_container, new PurchaseBuyerDetailsFragment());
                            transaction.commit();

                        }
//                        Intent homeIntent = new Intent(getContext(), HomePage.class);
//                        startActivity(homeIntent);
                    }
                    else{
                        // Toast.makeText(getContext(), "Falied " , Toast.LENGTH_LONG).show();
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                        dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.error_layout);
                        dialog.show();
                        Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                        TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                        errorTxt.setText("Failed");
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
                Log.d("SellerReg",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network Connections", Toast.LENGTH_LONG).show();
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
//            @Override
//            public Map<String,String> getParams()
//            {
//
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("user_id", user_id);
//                params.put("old_pass",oldPassTxt.getText().toString());
//                params.put("pass1",newPassTxt.getText().toString());
//                params.put("pass2",retypePassTxt.getText().toString());
//
//                return params;
//            }

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
                        ft.replace(R.id.home_container_layout, new ChangePasswordBuyer());
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
