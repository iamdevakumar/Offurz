package com.shadowws.offurz.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shadowws.offurz.R;
import com.shadowws.offurz.SMSBroadcastReceiver;
import com.shadowws.offurz.interfaces.SmsListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.shadowws.offurz.Activities.FirstPageActivity.fragname;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OTPVerificationFragments.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OTPVerificationFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTPVerificationFragments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG = "OTPVerification";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText otpEdt;
    private Button verifyBtn;
    String[] splitMsg;
    private String typeOfString;
    private static final int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 1;
    private static final int REQUEST_PERMISSION = 1;
    private String[] PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS

    };
    private OnFragmentInteractionListener mListener;
    SMSBroadcastReceiver smsBroadcastReceiver;
    Bundle bundle;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public OTPVerificationFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OTPVerificationFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static OTPVerificationFragments newInstance(String param1, String param2) {
        OTPVerificationFragments fragment = new OTPVerificationFragments();
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
        View otpView = inflater.inflate(R.layout.fragment_otpverification_fragments, container, false);
         otpEdt = (EditText)otpView.findViewById(R.id.editText_otp);
        bundle = this.getArguments();
         typeOfString = bundle.getString("type");
        final String otpStr = bundle.getString("otp");
       // checkAndRequestPermissions();
                verifyBtn = (Button)otpView.findViewById(R.id.button_verify);


            // carry on the normal flow, as the case of  permissions  granted.
            smsBroadcastReceiver.bindListener(new SmsListener() {

                @Override
                public void messageReceived(String messageText) {
                    Log.d("Text", messageText);
                   // Toast.makeText(getActivity(),"Message: "+typeOfString,Toast.LENGTH_LONG).show();
                     splitMsg = messageText.split(" ");
                     otpEdt.setText(splitMsg[0]);
                    if(otpEdt.getText().toString().trim().equals(otpStr)) {

                        if (typeOfString.equals("seller")) {
//                            Intent homeIntent = new Intent(getContext(), HomePage.class);
//                            startActivity(homeIntent);
                            Log.d("Seller",typeOfString);
                            SellerRegistationFragment sellReg = new SellerRegistationFragment();
                            Bundle b = new Bundle();
                            b.putString("name",bundle.getString("name"));
                            b.putString("mobile",bundle.getString("mobile"));
                            sellReg.setArguments(b);
                           // Fragment otpfragment = getFragmentManager().findFragmentByTag(TAG);
                            fragname= TAG;
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_container, sellReg).addToBackStack(null);
                           // transaction.remove(otpfragment);
                            transaction.commit();


                        } else if (typeOfString.equals("buyer")) {
                            Log.d("Buyer",typeOfString);
                            fragname= TAG;
                            BuyerRegisterFragment buyerReg = new BuyerRegisterFragment();
                            Bundle b = new Bundle();
                            b.putString("name",bundle.getString("name"));
                            b.putString("mobile",bundle.getString("mobile"));
                            buyerReg.setArguments(b);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_container, buyerReg).addToBackStack(null);
                            transaction.commit();

                        }
                    }
                }
            });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otpEdt.getText().toString().isEmpty()) {
                    if (otpEdt.getText().toString().trim().equals(otpStr)) {
                        if (bundle != null) {
                            if (typeOfString.equals("seller")) {
//                            Intent homeIntent = new Intent(getContext(), HomePage.class);
//                            startActivity(homeIntent);
                                SellerRegistationFragment sellReg = new SellerRegistationFragment();
                                Bundle b = new Bundle();
                                b.putString("name", bundle.getString("name"));
                                b.putString("mobile", bundle.getString("mobile"));
                                sellReg.setArguments(b);

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.main_container, sellReg).addToBackStack(null);
                                transaction.commit();

                            } else if (typeOfString.equals("buyer")) {
//                            Intent homeIntent = new Intent(getContext(), BuyerHomePage.class);
//                            startActivity(homeIntent);
                                BuyerRegisterFragment buyerReg = new BuyerRegisterFragment();
                                Bundle b = new Bundle();
                                b.putString("name", bundle.getString("name"));
                                b.putString("mobile", bundle.getString("mobile"));
                                buyerReg.setArguments(b);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.main_container, buyerReg).addToBackStack(null);
                                transaction.commit();

                            }
                        }
                    }
                    else
                        Toast.makeText(getContext(),"OTP is incorrect. Please Check it",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return otpView;
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
}
