package com.shadowws.offurz.Fragments;

import android.animation.IntArrayEvaluator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
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
import com.bumptech.glide.Glide;
import com.shadowws.offurz.Pojo.Rating;
import com.shadowws.offurz.R;
import com.shadowws.offurz.adapter.ListAdapter;
import com.shadowws.offurz.adapter.ViewImageAdapter;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProductDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "ViewProductFrg";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button getCuouponBtn;
    SharedPreferences pref;
    ListAdapter adapter;
    ViewPager viewPager;
    ArrayList<String> imageUrlArray;
    ViewImageAdapter viewImageAdapter;
    EditText commentEdt;
    TextView smallRating,bigRating;
    ProgressBar progress1,progress2,progress3,progress4,progress5;
    private OnFragmentInteractionListener mListener;
ProgressDialog pDialog;
    String imageUrl;
    int productId;
    Dialog dialog;
    ArrayList<Float> totalRatingArray;
ScrollView viewScrollview;
     Bundle bu;
    RatingBar rb;
    String[] strList = {"1","2","3"};
    ListView list;
    ArrayList<Rating> ratingArray;
    public ViewProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewProductDetailsFragment newInstance(String param1, String param2) {
        ViewProductDetailsFragment fragment = new ViewProductDetailsFragment();
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
        View ViewProduct =  inflater.inflate(R.layout.fragment_view_product_details, container, false);
        TextView pName = (TextView)ViewProduct.findViewById(R.id.view_pname);
        TextView offPrice = (TextView)ViewProduct.findViewById(R.id.view_off_price);
        TextView offPercentage = (TextView)ViewProduct.findViewById(R.id.view_off_percentage);
        TextView description = (TextView)ViewProduct.findViewById(R.id.view_description);
        TextView allDetailsTxt = (TextView)ViewProduct.findViewById(R.id.allDetails);
        viewPager = (ViewPager)ViewProduct.findViewById(R.id.view_pager);
        getCuouponBtn = (Button)ViewProduct.findViewById(R.id.getCuponCode);
        Button rateBtn = (Button)ViewProduct.findViewById(R.id.rateReviewBtn);
        smallRating = (TextView)ViewProduct.findViewById(R.id.small_ratingTxt);
        bigRating = (TextView)ViewProduct.findViewById(R.id.big_ratingTxt);
        progress1 = (ProgressBar)ViewProduct.findViewById(R.id.progressBar1);
        progress2 = (ProgressBar)ViewProduct.findViewById(R.id.progressBar2);
        progress3 = (ProgressBar)ViewProduct.findViewById(R.id.progressBar3);
        progress4 = (ProgressBar)ViewProduct.findViewById(R.id.progressBar4);
        progress5 = (ProgressBar)ViewProduct.findViewById(R.id.progressBar5);
        imageUrlArray = new ArrayList<>();

        final ImageView viewImage = (ImageView)ViewProduct.findViewById(R.id.viewImage);

        list= (ListView)ViewProduct.findViewById( R.id.ratingList );  // List defined in XML ( See Below )
        viewScrollview = (ScrollView)ViewProduct.findViewById(R.id.viviewPageScrollView);
        viewScrollview.fullScroll(ScrollView.FOCUS_UP);
        list.setFocusable(false);
        /**************** Create Custom Adapter *********/

        bu = this.getArguments();
        if(bu !=null)
        {
            pName.setText("Product Name : "+bu.getString("product_name"));
            offPrice.setText("Offer Price : Rs."+bu.getString("Off_price"));
            offPercentage.setText("Offered Percent : "+bu.getString("Off_percentage")+"%");
            description.setText(bu.getString("description"));
            productId = Integer.parseInt(bu.getString("product_id"));
            Log.d("View Product Name",""+bu.getString("product_name"));
            Log.d("View Product ID",""+bu.getString("product_id"));
            if(bu.getString("image2")!= null) {
                Log.d("image 2", bu.getString("image2"));
                String image = bu.getString("image2");
                String[] splitImg = image.split(",");
                imageUrlArray.add(bu.getString("image"));
                for (int i = 0; i < splitImg.length; i++) {
                    imageUrl = "http://offurz.com/uploads/" + splitImg[i];
                    imageUrlArray.add(imageUrl);

                }
                Log.d("ImageUrlArray", "" + imageUrlArray.size());
                viewImageAdapter = new ViewImageAdapter(getContext(), imageUrlArray);
                viewPager.setAdapter(viewImageAdapter);
                CirclePageIndicator indicator = (CirclePageIndicator) ViewProduct.findViewById(R.id.view_indicator);
                indicator.setViewPager(viewPager);
            }


//            Picasso.with(getContext()) // Context
//                    .load(bu.getString("image")) // URL or file
//                    .into(viewImage);
//            Glide.with(getContext())
//                    .load(bu.getString("image"))
//                    .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
//                    .into(viewImage);
//            Log.d(TAG,"Image Url"+bu.getString("image"));



        }
       // RateReviewGet("http://logistic.shadowws.in/rating_json_get_reviews.php");
        RateReviewGet("http://offurz.com/rating_json_get_reviews.php");

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ReviewAndRatingFragment reviewRating = new ReviewAndRatingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("product_id",bu.getString("product_id"));
                bundle.putString("product_name",bu.getString("product_name"));
                bundle.putString("company",bu.getString("company"));
                bundle.putString("mobile",bu.getString("mobile"));
                bundle.putString("image2",bu.getString("image2"));
                reviewRating.setArguments(bundle);

//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.buyer_main_container, reviewRating).addToBackStack(null).commit();
               // fragmentTransaction.commit();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

               dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.rate_review_dialog);
                dialog.show();
                 rb = (RatingBar)dialog.findViewById(R.id.ratingBar);
                Button rateBtn = (Button)dialog.findViewById(R.id.rateBtn);
                commentEdt = (EditText)dialog.findViewById(R.id.commentEdtTxt);
                rateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getRating = String.valueOf(rb.getRating());

                       // Toast.makeText(getContext(),"Rating Star"+getRating+"Commments "+commentEdt.getText().toString(),Toast.LENGTH_SHORT).show();
                      //  RateReviewPost("http://logistic.shadowws.in/rating_json.php");
                        RateReviewPost("http://offurz.com/rating_json.php");
                       // dialog.dismiss();

                    }
                });



            }
        });

        getCuouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  getCouponCode("http://logistic.shadowws.in/add_cart_json.php");
                getCouponCode("http://offurz.com/add_cart_json.php");
            }
        });
        allDetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllDetailsFragment alFr = new AllDetailsFragment();
                Bundle bundle = new Bundle();
                Log.d("All state",bu.getString("state"));
                Log.d("All city",bu.getString("city"));
                bundle.putString("description",bu.getString("description"));
                bundle.putString("company",bu.getString("company"));
                bundle.putString("mobile",bu.getString("mobile"));
                bundle.putString("city",bu.getString("city"));
                bundle.putString("state",bu.getString("state"));
                alFr.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.buyer_main_container, alFr).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return ViewProduct;
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
    public void getCouponCode(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        pref = getContext().getSharedPreferences("buyerData", 0);
//
        JSONObject js = new JSONObject();
        try {

            js.put("fid", String.valueOf(productId));
            js.put("id", pref.getString("user_id",null));
            js.put("name",pref.getString("name",null));
            js.put("city",pref.getString("city",null));
            js.put("mobile",pref.getString("mobile",null));
            js.put("state",pref.getString("state",null));
            js.put("username",pref.getString("username",null));
            js.put("password",pref.getString("password",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                // Toast.makeText(mContext, " Result" + response.toString(), Toast.LENGTH_LONG).show();
                Log.d("GET CUPON",response.toString());
                try {

                    JSONObject jsarray = new JSONObject(response.toString());
                    Log.d("BestSeller", "Array value" + response.toString());
                    Log.d("BestSeller", "Array size" + jsarray.length());
                    boolean message = jsarray.getBoolean("success");
                    if(message){
                        Toast.makeText(getContext(),"Get Coupon Code Successfully",Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(getContext(), "Time Out error", Toast.LENGTH_LONG).show();
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                    dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.error_layout);
                    dialog.show();
                    Button yesBtn = (Button)dialog.findViewById(R.id.errorBtn);
                    TextView errorTxt = (TextView)dialog.findViewById(R.id.error_msg);
                    errorTxt.setText("Check Network Connection");
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
                       // Toast.makeText(getContext(), "Error  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
//

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

    public void RateReviewGet(String url) {

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.d("GET RATING","RATING");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();

        try {
            js.put("add_id",bu.getString("product_id"));
            Log.d(TAG,"ProductId"+bu.getString("product_id"));
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
                    Log.d(TAG, "rating Array value" + response.toString());
                    Log.d(TAG, "rating Array size" + response.length());
                    JSONArray jsArray = jsObject.getJSONArray("items");
                    ratingArray = new ArrayList<>();
                    totalRatingArray = new ArrayList<>();
                    for(int i=0;i< jsArray.length();i++){
                        JSONObject childJsonObj = jsArray.getJSONObject(i);
                        Rating ra = new Rating();
                        ra.setS_id(childJsonObj.getInt("s_id"));
                        ra.setProduct_id(childJsonObj.getString("product_id"));
                        ra.setProduct_name(childJsonObj.getString("product_name"));
                        ra.setRating(childJsonObj.getInt("rating"));
                        ra.setTotal_rating(childJsonObj.getInt("total_rating"));
                        ra.setReviews(childJsonObj.getString("reviews"));
                        ra.setBuyer_name(childJsonObj.getString("buyer_name"));
                        ra.setBuyer_email(childJsonObj.getString("buyer_email"));
                        ra.setRating_date(childJsonObj.getString("rating_date"));
                        totalRatingArray.add(Float.parseFloat(childJsonObj.getString("rating")));
                        ratingArray.add(ra);

                    }
                    if(totalRatingArray.size() > 0){
                    for(int i=0;i<totalRatingArray.size();i++) {
                        float values = totalRatingArray.get(i);
                        Log.d("total_rating", "" + values);
                        Log.d("total Array size",""+totalRatingArray.size());
                        int totlaArraysize = totalRatingArray.size();
                        int sum = 0;
                        for (float j : totalRatingArray)
                            sum += j;
                        Log.d("sum of rating",""+(float)sum/totlaArraysize);
                        DecimalFormat df = new DecimalFormat("#.#");
                        float finalValue = (float)sum/totlaArraysize;
                        smallRating.setText(""+df.format(finalValue)+" *");
                        bigRating.setText(""+df.format(finalValue));
                        int sumValue = 0;
                        if(values == 1) {
                            sumValue += values;
                            Log.d("sumValues",""+sumValue);
                            progress1.setProgress(sumValue);
                            progress1.setMax(100);
                        }
                        if(values == 2) {
                            sumValue += values;
                            Log.d("sumValues 2",""+sumValue);
                            progress2.setProgress(sumValue);
                            progress2.setMax(100);
                        }
                        if(values == 3) {
                            sumValue += values;
                            Log.d("sumValues 3",""+sumValue);
                            progress3.setProgress(sumValue);
                            progress3.setMax(100);
                        }
                        if(values == 4){
                            sumValue += values;
                            Log.d("sumValues 4",""+sumValue);
                            progress4.setProgress(sumValue);
                            progress4.setMax(100);

                        }if(values == 5){
                            sumValue += values;
                            Log.d("sumValues 5",""+sumValue);
                            progress5.setProgress(sumValue);
                            progress5.setMax(100);
                        }

                    }
                    }
                    else
                    {
                        smallRating.setVisibility(View.INVISIBLE);
                        bigRating.setVisibility(View.INVISIBLE);

                    }
                    if(ratingArray.size() > 0) {
                        adapter = new ListAdapter(getContext(), ratingArray);
                        list.setAdapter(adapter);
                    }
                    else
                    {

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
                Log.d("ViewProduct",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check Network connections", Toast.LENGTH_LONG).show();
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


    public void RateReviewPost(String url) {

        if(dialog.isShowing()){
            dialog.dismiss();
        }
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        pref = getContext().getSharedPreferences("buyerData", 0);
        try {
            js.put("buyer_id",pref.getString("user_id",null));
            js.put("buyer_name",pref.getString("name",null));
            js.put("buyer_email",pref.getString("email",null));
            js.put("buyer_mobile",pref.getString("mobile",null));

            Log.d("ProductId Post",bu.getString("product_id"));
            js.put("product_id",bu.getString("product_id"));
            js.put("product_name",bu.getString("product_name"));
            js.put("rating",rb.getRating());
            js.put("company",bu.getString("company"));
            js.put("mobile",bu.getString("mobile"));
            js.put("review",commentEdt.getText().toString());
            Log.d("Rating ",""+rb.getRating());
            Log.d("comments",commentEdt.getText().toString());

            Log.d(TAG,"b_id"+pref.getString("user_id",null));
            Log.d(TAG,"b_mobile"+pref.getString("mobile",null));
            Log.d(TAG,"b_email"+pref.getString("email",null));
            Log.d(TAG,"b_name"+pref.getString("name",null));
            Log.d(TAG,"product_id"+bu.getString("product_id",null));
            Log.d(TAG,"product_name"+bu.getString("product_name",null));
            Log.d(TAG,"company"+bu.getString("company",null));
            Log.d(TAG,"mobile"+bu.getString("mobile",null));


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
                    Log.d(TAG, "Array value" + response.toString());
                    Log.d(TAG, "Array size" + response.length());
                    boolean msg = jsObject.getBoolean("success");
                    if(msg){
                        Toast.makeText(getContext(),"Review and rating added successfully",Toast.LENGTH_SHORT).show();
                        Fragment frg = null;

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.buyer_main_container, new HomePageFragment());
                        transaction.commit();
//                        if(adapter != null)
//                        adapter.notifyDataSetChanged();
//                        else {
//
//
//                        }
//                        frg = getFragmentManager().findFragmentByTag("viewFrg");
//                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(frg);
//                        ft.attach(frg);
//                        ft.commit();
//                        FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.buyer_main_container, new ViewProductDetailsFragment());
//                        transaction.commit();


                        //getFragmentManager().beginTransaction().detach(getContext()).attach(getContext()).commit();
//
//                                ViewProductDetailsFragment vf = new ViewProductDetailsFragment();
//
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
                        ft.replace(R.id.home_container_layout, new ViewProductDetailsFragment());
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
