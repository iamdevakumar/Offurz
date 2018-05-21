package com.shadowws.offurz.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.shadowws.offurz.HomePage;
import com.shadowws.offurz.MainActivity;
import com.shadowws.offurz.R;
import com.shadowws.offurz.web.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerRegistationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerRegistationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerRegistationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int SELECT_PHOTO3 = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    APIInterface apiInterface;
    ProgressDialog pDialog;
    TextInputEditText sellerNameTxt,sellerMobileNoTxt,sellerPackageTxt,sellerCompanyTct,sellerWebsiteTxt,sellerconfirmPasswordTxt,sellerAddressTxt,sellerEmailTxt,sellerUserNameTxt,sellerPasswordTxt;
    TextInputLayout sellerNameLay,sellerMobileNoLay,sellerPackageLay,sellerCompanyLay,sellerWebsiteLay,sellerAddressLay,sellerEmailLay,sellerUsernameLay,sellerPasswordLay;
    private OnFragmentInteractionListener mListener;
    Spinner Cityspinner,Statespinner;
    ImageView imageView;
    ArrayAdapter<String> adapter,stateadapter;
    String imageName;
    static String TAG = "SellerRegFrag";
    Dialog dialog;
    Bundle bundle;
    ArrayList<String> stateArray;
    ArrayList<String> citArray;
    public SellerRegistationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerRegistationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerRegistationFragment newInstance(String param1, String param2) {
        SellerRegistationFragment fragment = new SellerRegistationFragment();
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
        View sellerView = inflater.inflate(R.layout.fragment_seller_registation, container, false);
        String [] cities =
                {"Select the city","Coimbatore","Madurai","Trichy","Salem","Karur","Erode","Ooty",};
        String [] states =
                {"Select the state","Tamil Nadu","Kerala","Karnataka"};
        Cityspinner = (Spinner) sellerView.findViewById(R.id.spinner_city);
        Statespinner = (Spinner) sellerView.findViewById(R.id.spinner_state);
            sellerNameTxt =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_name);
        sellerMobileNoTxt =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_mobileNo);
        sellerAddressTxt =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_address);
        sellerCompanyTct =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_company);
        sellerPackageTxt =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_package);
        sellerWebsiteTxt =(TextInputEditText)sellerView.findViewById(R.id.sellerReg_website);
        sellerEmailTxt = (TextInputEditText)sellerView.findViewById(R.id.sellerReg_email);
        sellerUserNameTxt = (TextInputEditText)sellerView.findViewById(R.id.sellerReg_username);
        sellerPasswordTxt = (TextInputEditText)sellerView.findViewById(R.id.sellerReg_password);
        sellerconfirmPasswordTxt = (TextInputEditText)sellerView.findViewById(R.id.sellerReg_confirm_password);

        sellerNameLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_name_layout);
        sellerMobileNoLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_phone_layout);
        sellerCompanyLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_company_layout);
        sellerAddressLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_address_layout);
        sellerPackageLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_package_layout);
        sellerWebsiteLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_website_layout);
        sellerEmailLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_email_layout);
        sellerUsernameLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_username_layout);
        sellerPasswordLay = (TextInputLayout)sellerView.findViewById(R.id.sellerReg_password_layout);

        Button sellerImg = (Button)sellerView.findViewById(R.id.seller_profileImage);
        imageView = (ImageView)sellerView.findViewById(R.id.sellerReg_image);
        bundle = this.getArguments();
        if(bundle != null) {
            sellerUserNameTxt.setText(bundle.getString("name"));
            sellerMobileNoTxt.setText(bundle.getString("mobile"));
        }
        GetStateandCity("http://offurz.com/city_state.php");
//        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, cities);
//        stateadapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item,states);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        stateadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        Cityspinner.setAdapter(adapter);
//
//        Statespinner.setAdapter(stateadapter);

        sellerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO3);
            }
        });
        sellerUserNameTxt.setEnabled(false);
        sellerMobileNoTxt.setEnabled(false);
        Button sellerRegSubimitBtn = (Button)sellerView.findViewById(R.id.sellerRegSubmit);
        sellerRegSubimitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(sellerNameTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerNameLay.setError("Please Enter Name");
                }else if(sellerMobileNoTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerMobileNoLay.setError("Please Enter Mobile Number");
                }else if(sellerAddressTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerAddressLay.setError("Please Enter Address");
                }else if(sellerCompanyTct.getText().toString().trim().equalsIgnoreCase("")){
                    sellerCompanyLay.setError("Please Enter Company Name");
                }else if(sellerPackageTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerPackageLay.setError("Please Enter Package");
                }else if(sellerMobileNoTxt.getText().toString().length() < 10){
                    sellerMobileNoLay.setError("Please Valid Mobile Number");
                }else if(sellerUserNameTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerUsernameLay.setError("Please Enter Email");
                }else if(sellerPasswordTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerPasswordLay.setError("Please Enter Password");
                }else if(!sellerPasswordTxt.getText().toString().trim().equalsIgnoreCase(sellerconfirmPasswordTxt.getText().toString().trim())){
                   sellerPasswordLay.setError("Please Enter right password");
               }else
              // SellerRegisterPost("http://logistic.shadowws.in/seller_add.php");
                SellerRegisterPost("http://offurz.com/seller_add.php");

            }
        });



        return sellerView;
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
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        try {
                Log.d("Spinner",Statespinner.getSelectedItem().toString()+" "+Cityspinner.getSelectedItem().toString());
            js.put("name", sellerNameTxt.getText().toString());
            js.put("mobile",sellerMobileNoTxt.getText().toString());
            js.put("address",sellerAddressTxt.getText().toString());
            js.put("package",sellerPackageTxt.getText().toString());
            js.put("website",sellerWebsiteTxt.getText().toString());
            js.put("company",sellerCompanyTct.getText().toString());
            js.put("state",Statespinner.getSelectedItem().toString());
            js.put("city", Cityspinner.getSelectedItem().toString());
            js.put("email",sellerEmailTxt.getText().toString());
            js.put("username",sellerUserNameTxt.getText().toString());
            js.put("password",sellerPasswordTxt.getText().toString());

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bm = drawable.getBitmap();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] b = bao.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("Image encoded",encodedImage.toString());

            js.put("image",encodedImage.toString());
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
                    Log.d("RESPONSE", jsonResponse.toString());
                    boolean message = jsonResponse.getBoolean("success");
                    if(message) {


                        JSONArray jsonObject = jsonResponse.getJSONArray("success_msg");
                        for(int i=0;i<jsonObject.length();i++) {
                            JSONObject jsObj = jsonObject.getJSONObject(i);
                            SharedPreferences pref = getActivity().getSharedPreferences("sellerData", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("user_id", jsObj.getString("id"));
                            editor.putString("name", jsObj.getString("name"));
                            editor.putString("city", jsObj.getString("city"));
                            editor.putString("state", jsObj.getString("state"));
                            editor.putString("mobile", jsObj.getString("mobile"));
                            editor.putString("email", jsObj.getString("email"));
                            editor.putString("company", jsObj.getString("company"));
                            editor.putString("approve", jsObj.getString("approve"));
                            editor.commit();
                            //Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                            if(jsObj.getString("approve").equals("no")){
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.main_container, new ApprovalPendingFragment()).addToBackStack(null);
                                transaction.commit();

                            }else if(jsObj.getString("approve").equals("wait")){
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.main_container, new ApprovalNoFragment()).addToBackStack(null);
                                transaction.commit();

                            }else {
                                Intent homeIntent = new Intent(getContext(), HomePage.class);
                                startActivity(homeIntent);
                            }
                        }
                    }
                    else
                       // Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                    {
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                        dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.error_layout);
                        dialog.show();
                        Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                        TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                        errorTxt.setText("Register Not Successfully");
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
                Log.d("SellerReg",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO3 && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String full_path = pickedImage.getPath();
            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();

            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Log.d("File Path", "" + imagePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
          imageView.setImageBitmap(bitmap);
            Log.d("SELECT IMAGE", bitmap.toString());
            String str = imagePath;
            Log.d("STR", str);
            imageName=bitmap.toString();
            String imgName = str.substring(str.lastIndexOf("/") + 1);
            Log.d("imageName", imgName);
            cursor.close();

        }
    }

    public void GetStateandCity(String url) {
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
                            for (int i = 0; i < jsArray.length(); i++) {
                                JSONObject jsObj = jsArray.getJSONObject(i);

                                stateArray.add(jsObj.getString("state"));
                                citArray.add(jsObj.getString("city"));
                            }
                            Set<String> hs = new HashSet<>();
                            hs.addAll(stateArray);
                            stateArray.clear();
                            stateArray.addAll(hs);
                            if (citArray.size() > 0) {
                                adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, citArray);
                                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                Cityspinner.setAdapter(adapter);
                            }
                            if (stateArray.size() > 0) {
                                stateadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, stateArray);
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
