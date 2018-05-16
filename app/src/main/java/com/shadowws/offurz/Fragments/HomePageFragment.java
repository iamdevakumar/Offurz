package com.shadowws.offurz.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shadowws.offurz.Activities.FirstPageActivity;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.R;
import com.shadowws.offurz.adapter.HomePageAdapter;
import com.shadowws.offurz.web.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<BestSeller> bestSellerArray;
    APIInterface apiInterface;
    private static final int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 1;
    private static final int REQUEST_PERMISSION = 1;
    private String[] PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS

    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog pDialog;
    SearchView sv;

    private OnFragmentInteractionListener mListener;
    int[] images;
    TabLayout tabLayout;
    String[] fruits = {"Shoes", "Watches","Cloths","Mobile","Laptop", "Electronic", "Gadgets", "AA","Sss", "WW", "CC", "LL"};
    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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

      //  verifyStoragePermissions(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        int[] myDataset = {1,2,3,4};
         View homePageView = inflater.inflate(R.layout.fragment_home_page, container, false);
        tabLayout = (TabLayout) homePageView.findViewById(R.id.home_tabLayout);
       // sv = (SearchView)homePageView.findViewById(R.id.search_product);
        tabLayout.addTab(tabLayout.newTab().setText("New Updations"));
        tabLayout.addTab(tabLayout.newTab().setText("Trending Offers"));
        tabLayout.addTab(tabLayout.newTab().setText("Best Seller"));
        tabLayout.addTab(tabLayout.newTab().setText("Hot Deals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        HomePageCommon hpCommon = new HomePageCommon();
        Bundle bu = new Bundle();
       // bu.putString("url","http://logistic.shadowws.in/newupdation_an.php");
        bu.putString("url","http://offurz.com/newupdation_an.php");
        hpCommon.setArguments(bu);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container_layout, hpCommon);
        transaction.commit();
        tabLayout.setOnTabSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, fruits);

       // getAllItems("http://logistic.shadowws.in/off_welcome_json.php");
        //Getting the instance of AutoCompleteTextView
//        final AutoCompleteTextView actv = (AutoCompleteTextView) homePageView.findViewById(R.id.autoCompleteTextView);
//        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//    actv.setOnClickListener(new AdapterView.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Toast.makeText(getContext(),"Selected"+actv.getText().toString(),Toast.LENGTH_SHORT).show();
//            if(Arrays.asList(fruits).contains(actv.getText().toString()))
//            {
//                Toast.makeText(getContext(),"Selected 2"+actv.getText().toString(),Toast.LENGTH_SHORT).show();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_container, new SignUpFragment());
//                transaction.commit();
//            }
//

//
//        }
//
//    });

       // mRecyclerView = (RecyclerView) homePageView.findViewById(R.id.home_recyler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new HomePageAdapter(getContext(),myDataset);
//        mRecyclerView.setAdapter(mAdapter);


//        SearchView searchViewHome = (SearchView)homePageView.findViewById(R.id.search_product);
//        searchViewHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_container, new SearchViewFragment());
//                transaction.commit();
//            }
//        });
      //  getAllItems("http://logistic.shadowws.in/all_an.php");
        return homePageView;
    }

    private void getAllItems(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final List<JSONArray> arrayList = new ArrayList<>();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsarray =new JSONArray(response.toString());
                    Log.d("BestSeller", "Array value" + response.toString());
                    Log.d("BestSeller", "Array size" + jsarray.length());

                    for(int i=0;i<jsarray.length();i++){
                        JSONObject jsObj = jsarray.getJSONObject(i);
                        String id = jsObj.getString("id");
                        String count = jsObj.getString("count");
                      //  arrayList.add(jsObj);

                                           }

                  //  }
                        Log.d("ARRAYLIST",""+arrayList.size());
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new HomePageAdapter(getContext(),arrayList);
                    mRecyclerView.setAdapter(mAdapter);
//                    }


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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //viewPager.setCurrentItem(tab.getPosition());
        Log.d("Get Tab",""+tab.getPosition());
        Log.d("Get tab",""+tab.getText());
        if(tab.getPosition() == 0){


            HomePageCommon hpCommon = new HomePageCommon();
            Bundle bu = new Bundle();
            //bu.putString("url","http://logistic.shadowws.in/newupdation_an.php");
            bu.putString("url","http://offurz.com/newupdation_an.php");
            hpCommon.setArguments(bu);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_container_layout, hpCommon);
            transaction.commit();
        }
        else if(tab.getPosition() == 1){
            HomePageCommon hpCommon = new HomePageCommon();
            Bundle bu = new Bundle();
            //bu.putString("url","http://logistic.shadowws.in/trending_an.php");
            bu.putString("url","http://offurz.com/trending_an.php");
            hpCommon.setArguments(bu);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_container_layout, hpCommon);
            transaction.commit();
        }else if(tab.getPosition() ==2){
            HomePageCommon hpCommon = new HomePageCommon();
            Bundle bu = new Bundle();
            //bu.putString("url","http://logistic.shadowws.in/bestseller_an.php");
            bu.putString("url","http://offurz.com/bestseller_an.php");
            hpCommon.setArguments(bu);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_container_layout, hpCommon);
            transaction.commit();

        }else if(tab.getPosition() ==3){
            HomePageCommon hpCommon = new HomePageCommon();
            Bundle bu = new Bundle();
            //bu.putString("url","http://logistic.shadowws.in/hotdeal_an.php");
            bu.putString("url","http://offurz.com/hotdeal_an.php");
            hpCommon.setArguments(bu);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_container_layout, hpCommon);
            transaction.commit();


        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    moveTaskToBack(true);
//                    finish();
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog);
                    Button btnSave = (Button) dialog.findViewById(R.id.save);
                    Button cancel = (Button) dialog.findViewById(R.id.cancel);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //                           moveTaskToBack(true);
//                                    finish();
//                                    System.exit(0);
                            SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();
                            SharedPreferences walletPreference = getActivity().getSharedPreferences("buyerData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorWallet = walletPreference.edit();
                            editorWallet.clear();
                            editorWallet.commit();


                            Intent intent = new Intent(getContext(), FirstPageActivity.class);
                            intent.putExtra("finish", true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);
                            getActivity().finish();
//            SharedPreferences profilePref = getSharedPreferences("buyerData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor profileEdit = profilePref.edit();
//            profileEdit.clear();
//            profileEdit.commit();

//                            SharedPreferences exit = getActivity().getSharedPreferences("exit", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor exit1 = exit.edit();
//                            exit1.putString("exit", "splash");
//                            exit1.commit();
//                            getActivity().moveTaskToBack(true);
//                            getActivity().finish();
//                            System.exit(0);


                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      dialog.dismiss();
                                                  }
                                              }
                    );
                    dialog.show();
                    return true;
                }


                return false;
            }

        });

    }

//
//    @Override
//    public void onResume() {
//
//        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    SharedPreferences back=getActivity().getSharedPreferences("back", Context.MODE_PRIVATE);
//                    if(back.getInt("back",0)!=1) {
//                        Log.d("back", String.valueOf(back.getInt("back",0)));
//                        Log.d("kk","kk");
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.replace(R.id.home_container_layout, new HomePageFragment());
//                        ft.commit();
//                    }
//                    else
//                    {
//                        Log.d("back", String.valueOf(back.getInt("back",0)));
//                        Log.d("kk123","kk");
//                        SharedPreferences back1=getActivity().getSharedPreferences("back", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor bac=back1.edit();
//                        bac.putInt("back",0);
//                        bac.commit();
//
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.replace(R.id.home_container_layout, new LoginBuyerFragment());
//                        ft.commit();
//                    }
////                FragmentTransaction ft = getFragmentManager().beginTransaction();
////                ft.replace(R.id.home_page_container,new HomePageFragment());
////                ft.commit();
//                }
//                return true;
//            }
//        });


//}
}
