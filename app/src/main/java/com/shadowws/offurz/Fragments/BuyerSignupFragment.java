package com.shadowws.offurz.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
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
import com.shadowws.offurz.HomePage;
import com.shadowws.offurz.MainActivity;
import com.shadowws.offurz.R;
import com.shadowws.offurz.SMSBroadcastReceiver;
import com.shadowws.offurz.web.APIInterface;

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
 * {@link BuyerSignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuyerSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerSignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 1;
    static String TAG = "BuyerSignupFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button signBtn;
    int answer;
    APIInterface apiInterface;
    TextInputLayout buyerNameLay,buyerMobileLay;
    TextInputEditText buyerNameTxt,buyerMobileTxt;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Dialog dialog;

    private OnFragmentInteractionListener mListener;
    private static final int REQUEST_PERMISSION = 1;
    SMSBroadcastReceiver smsBroadcastReceiver;
    ProgressDialog pDialog;
    private String[] PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS

    };

    public BuyerSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyerSignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyerSignupFragment newInstance(String param1, String param2) {
        BuyerSignupFragment fragment = new BuyerSignupFragment();
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
        View buyerSignupView =  inflater.inflate(R.layout.fragment_buyer_signup, container, false);
        Log.d(TAG,"signup");


        buyerNameLay = (TextInputLayout) buyerSignupView.findViewById(R.id.buyer_name_layout);
        buyerMobileLay = (TextInputLayout) buyerSignupView.findViewById(R.id.buyer_mobileNo_layout);
        buyerNameTxt = (TextInputEditText) buyerSignupView.findViewById(R.id.buyer_name);
        buyerMobileTxt = (TextInputEditText) buyerSignupView.findViewById(R.id.buyer_mobileNo);
        signBtn = (Button)buyerSignupView.findViewById(R.id.buyer_signup);
    // verifyStoragePermissions(getActivity());
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buyerNameTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                    buyerNameLay.setError("Please Enter Name");
                }else if(buyerMobileTxt.getText().toString().trim().equalsIgnoreCase(""))
                {
                    buyerMobileLay.setError("Please Enter Mobile Number");
                }else if(buyerMobileTxt.getText().toString().length() < 10 || buyerMobileTxt.getText().toString().length() >10)
                {
                    buyerMobileLay.setError("Please Enter Valid Mobile Number");
                }
                else {


                    BuyerSignupPost("http://offurz.com/buyer_otp.php");

                   // startActivity(new Intent(getContext(), HomePage.class));








                    //BuyerSignupPost("http://logistic.shadowws.in/buyer_otp.php");



                   // sendSMS(buyerMobileTxt.getText().toString(),"Hi");
//                    OTPVerificationFragments otpFragment = new OTPVerificationFragments();
//                    Bundle b = new Bundle();
//                    b.putString("type", "buyer");
//                    otpFragment.setArguments(b);
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.main_container, otpFragment);
//                    transaction.commit();
                }
//                apiInterface = APIClient.getClient().create(APIInterface.class);
//                /**
//                 GET List Resources
//                 **/
//                Call<Login> call = apiInterface.LoginGet();
//                call.enqueue(new Callback<Login>() {
//                    @Override
//                    public void onResponse(Call<Login> call, Response<Login> response) {
//                        Log.d("TAG",response.code()+"");
//                        String displayResponse = "";
//                        Login resource = response.body();
//                        String userName = resource.getUsername();
//                        String Password = resource.getPassword();
//                        displayResponse += userName + " Page\n" + Password + " Total\n";
//                        //responseTxt.setText(displayResponse);
//                        Toast.makeText(getContext(),"Res "+displayResponse,Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onFailure(Call<Login> call, Throwable t) {
//                        call.cancel();
//                    }
//                });

            }
        });
    return buyerSignupView;
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


    public void BuyerSignupPost(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC2D2D")));
        // pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.show();
        Random randomNumber = new Random();
        answer = randomNumber.nextInt(100000) + 1;
        Log.d(TAG,"Random number"+answer);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {

            js.put("b_username", buyerNameTxt.getText().toString());
            js.put("b_mobileNo", buyerMobileTxt.getText().toString());
            js.put("b_otp",answer);
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



                        OTPVerificationFragments otpFragment = new OTPVerificationFragments();
                        Bundle b = new Bundle();
                        b.putString("type", "buyer");
                        b.putString("name",buyerNameTxt.getText().toString());
                        b.putString("mobile",buyerMobileTxt.getText().toString());
                        b.putString("otp", String.valueOf(answer));
                        otpFragment.setArguments(b);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_container, otpFragment).addToBackStack(null);
                        transaction.commit();
                    }
                    else
                    {
                       // Toast.makeText(getContext(), "" + response.getString("success_msg"), Toast.LENGTH_LONG).show();
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
               // Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network connection", Toast.LENGTH_LONG).show();
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
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

  }
