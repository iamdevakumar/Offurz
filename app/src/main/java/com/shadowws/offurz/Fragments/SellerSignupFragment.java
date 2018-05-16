package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.shadowws.offurz.MainActivity;
import com.shadowws.offurz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerSignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerSignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "sellerSignUp";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private Button signBtn;
    private TextInputEditText sellerNameTxt,sellerMobileTxt;
    private TextInputLayout sellerNameLay,sellerPMobileLay;
    private OnFragmentInteractionListener mListener;
    ProgressDialog pDialog;
    Dialog dialog;
    int answer;

    public SellerSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerSignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerSignupFragment newInstance(String param1, String param2) {
        SellerSignupFragment fragment = new SellerSignupFragment();
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
        View seller_signupView = inflater.inflate(R.layout.fragment_seller_signup, container, false);
        Log.d("Seller Signup","signup");
        sellerNameLay = (TextInputLayout) seller_signupView.findViewById(R.id.seller_name_layout);
        sellerPMobileLay = (TextInputLayout) seller_signupView.findViewById(R.id.seller_mobileNo_layout);
        sellerNameTxt = (TextInputEditText) seller_signupView.findViewById(R.id.seller_name);
        sellerMobileTxt = (TextInputEditText) seller_signupView.findViewById(R.id.seller_mobileNo);
        signBtn = (Button)seller_signupView.findViewById(R.id.seller_sign_btn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sellerNameTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                    sellerNameLay.setError("Please Enter Name");
                }else if(sellerMobileTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                        sellerPMobileLay.setError("Please Enter Mobile Number");
                }else if(sellerMobileTxt.getText().toString().length() < 10 || sellerMobileTxt.getText().toString().length() >10)
                {
                    sellerPMobileLay.setError("Please Enter Valid Mobile Number");
                }
                else {
                  //  SellerSignupPost("http://logistic.shadowws.in/seller_otp.php");
                    SellerSignupPost("http://offurz.com/seller_otp.php");

                }
            }
        });

    return  seller_signupView;
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


    public void SellerSignupPost(String url) {
        pDialog = new ProgressDialog(getContext());
      // pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC2D2D")));
        // pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.show();
        Random randomNumber = new Random();
         answer = randomNumber.nextInt(100000) + 1;
        Log.d("Random number"," "+answer);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {

            js.put("s_username", sellerNameTxt.getText().toString());
            js.put("s_mobileNo", sellerMobileTxt.getText().toString());
            js.put("s_otp",answer);
            //js.put("email", "test@gmail.com");
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
                    if(message) {

                        Log.d("Inside","Inside If");
                        OTPVerificationFragments otpFragment = new OTPVerificationFragments();
                        Bundle b = new Bundle();
                        b.putString("type", "seller");
                        b.putString("name",sellerNameTxt.getText().toString());
                        b.putString("mobile",sellerMobileTxt.getText().toString());
                        b.putString("otp", String.valueOf(answer));
                        otpFragment.setArguments(b);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_container, otpFragment);
                        transaction.commit();
                    }else {
                        Log.d("Inside","Inside else");
                       // Toast.makeText(getContext(), "" + response.getString("success_msg"), Toast.LENGTH_LONG).show();
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
                Log.d("SellerReg",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check your network connection", Toast.LENGTH_LONG).show();
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
