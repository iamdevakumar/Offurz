package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.shadowws.offurz.HomePage;
import com.shadowws.offurz.MainActivity;
import com.shadowws.offurz.Pojo.SellerRegister;
import com.shadowws.offurz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginSellerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginSellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginSellerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText usernameTxt,passwordTxt;
    TextInputLayout til,pass;
    ProgressDialog pDialog;
    Dialog dialog;
    TextView newSignUTextView,forgotPassword;
    static String TAG = "LoginSeller";

    private OnFragmentInteractionListener mListener;

    public LoginSellerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.c
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginSellerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginSellerFragment newInstance(String param1, String param2) {
        LoginSellerFragment fragment = new LoginSellerFragment();
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
        View loginsellerView = inflater.inflate(R.layout.fragment_login_seller, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("sellerLogin", 0);
        final int number = sharedPref.getInt("isLogged", 0);




        usernameTxt = (TextInputEditText)loginsellerView.findViewById(R.id.seller_login_Username);
        passwordTxt = (TextInputEditText)loginsellerView.findViewById(R.id.seller_login_password);
        newSignUTextView = (TextView)loginsellerView.findViewById(R.id.loginPage_signup);
        Button loginBtn = (Button)loginsellerView.findViewById(R.id.login_submit);
        til = (TextInputLayout) loginsellerView.findViewById(R.id.text_input_layout);
        pass = (TextInputLayout)loginsellerView.findViewById(R.id.text2_input_layout);

        forgotPassword = (TextView)loginsellerView.findViewById(R.id.seller_forgotPass);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                    til.setError("Enter Username");
                }else if(passwordTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                    pass.setError("Enter Password");
                }else
                   // SellerRegisterPost("http://logistic.shadowws.in/seller_log_check.php");
                SellerRegisterPost("http://offurz.com/seller_log_check.php");
            }
        });
        newSignUTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, new SignUpFragment());
                transaction.commit();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.forgot_password);
                dialog.show();
                Button okBtn = (Button)dialog.findViewById(R.id.forgot_btn_ok);
                final EditText editTxt = (EditText)dialog.findViewById(R.id.forgot_editText);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(editTxt.getText().toString().trim().equalsIgnoreCase(""))
                            Toast.makeText(getContext(),"Enter Number in the field",Toast.LENGTH_SHORT).show();
                        else {
                          //  Toast.makeText(getContext(), editTxt.getText().toString(), Toast.LENGTH_SHORT).show();
                            SellerForgotPassword("http://offurz.com/seller_forgot.php",editTxt.getText().toString());

                        }

                    }
                });

            }
        });
        return loginsellerView;
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
    public void SellerRegisterPost(String url) {
         ArrayList<String> objArray = new ArrayList<String>();
        pDialog = new ProgressDialog(getContext());
     //   pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC2D2D")));
        // pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {

            js.put("username", usernameTxt.getText().toString());
            js.put("password", passwordTxt.getText().toString());
            //js.put("email", "test@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, js,new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d("RESPONSE", jsonResponse.toString());
                    boolean message = jsonResponse.getBoolean("success");

                if(message) {

                    SellerRegister sr = new SellerRegister();
   // Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                    SharedPreferences pref = getActivity().getSharedPreferences("sellerData", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_id",jsonResponse.getString("id"));
                    editor.putString("name",jsonResponse.getString("name"));
                    editor.putString("city",jsonResponse.getString("city"));
                    editor.putString("state",jsonResponse.getString("state"));
                    editor.putString("mobile",jsonResponse.getString("mobile"));
                    editor.putString("email",jsonResponse.getString("email"));
                    editor.putString("company",jsonResponse.getString("company"));
                    editor.putString("approve",jsonResponse.getString("approve"));
                    editor.commit();
                    if(jsonResponse.getString("approve").equals("no")){
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, new ApprovalPendingFragment());
                        transaction.commit();

                    }else if(jsonResponse.getString("approve").equals("wait")){
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, new ApprovalNoFragment());
                        transaction.commit();

                    }else {

                        startActivity(new Intent(getContext(), HomePage.class));
                    }
                }
            else{
                    String error_msg = jsonResponse.getString("success_msg");
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                    dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.error_layout);
                    dialog.show();
                    Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                    TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                    if(error_msg !=null)
                    errorTxt.setText(error_msg);
                    else
                        errorTxt.setText("Error Something");
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

                }


               // Toast.makeText(getContext(), "" + response.getString("success_msg"), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), "Check your network connection", Toast.LENGTH_LONG).show();
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
    public void SellerForgotPassword(String url, String mobileNo) {
        if(dialog.isShowing())
            dialog.dismiss();

        pDialog = new ProgressDialog(getContext());
        // pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC2D2D")));
        pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {
            Log.d("forgot Mobile",mobileNo);
            js.put("mob", mobileNo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, js,new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d("RESPONSE", jsonResponse.toString());
                    boolean message = jsonResponse.getBoolean("success");
                    JSONArray msgStatus = jsonResponse.getJSONArray("success_msg");
                     final int otpForgot = jsonResponse.getInt("otp");
                    if(message) {
                        //Toast.makeText(getContext(), msgStatus, Toast.LENGTH_LONG).show();
                            for(int i = 0; i<msgStatus.length();i++) {
                                JSONObject jsobject = msgStatus.getJSONObject(0);
                               // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                                SharedPreferences pref = getActivity().getSharedPreferences("sellerData", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("user_id", jsobject.getString("id"));
                                editor.putString("name", jsobject.getString("name"));
                                editor.putString("city", jsobject.getString("city"));
                                editor.putString("address", jsobject.getString("address"));
                                editor.putString("mobile", jsobject.getString("mobile"));
                                editor.putString("email", jsobject.getString("email"));
                                editor.putString("state", jsobject.getString("state"));
                                // editor.putString("approve",jsonResponse.getString("approve"));
                                editor.putString("username", jsobject.getString("username"));
                                editor.putString("password", jsobject.getString("password"));
                                editor.commit();
                            }
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                        dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.otp_forgot_verification);
                        dialog.show();
                        Button yesBtn = (Button)dialog.findViewById(R.id.otp_forgotBtn);
                        final TextView otpVerify = (TextView)dialog.findViewById(R.id.otp_forgotEdit);

                        yesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // dialog.dismiss();
                                Log.d("otpVerify",""+otpVerify);
                                Log.d("otpFogot",""+otpForgot);
                                if(otpVerify.getText().toString().trim().equalsIgnoreCase(String.valueOf(otpForgot))){
                                    dialog.dismiss();
                                    ForgotPasswordChangeFragment frPass = new ForgotPasswordChangeFragment();
                                    Bundle bu = new Bundle();
                                    bu.putString("type","seller");
                                    frPass.setArguments(bu);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.main_container, frPass);
                                    transaction.commit();
                                }else

                                    Toast.makeText(getContext(), "Verification Number is wrong", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                    else {

                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();

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
                Log.d("forgot",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check your network connection", Toast.LENGTH_LONG).show();

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
                        transaction.replace(R.id.main_container, new LoginPageFragment());
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
                        ft.replace(R.id.login_container_layout, new LoginSellerFragment());
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
