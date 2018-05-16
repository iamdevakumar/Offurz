package com.shadowws.offurz.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.shadowws.offurz.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "AllDetailsFargment";
    TabHost host;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String city,state;
    Bundle b;

    private OnFragmentInteractionListener mListener;

    public AllDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllDetailsFragment newInstance(String param1, String param2) {
        AllDetailsFragment fragment = new AllDetailsFragment();
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
        Log.d(TAG,"AllDetailsFragment");
        // Inflate the layout for this fragment
        View alldetailsView = inflater.inflate(R.layout.fragment_all_details, container, false);

       host = (TabHost)alldetailsView.findViewById(R.id.tabhost);
//        TextView tv = (TextView) host.findViewById(android.R.id.title);
//        tv.setTextColor(Color.parseColor("#ffffff"));

        host.setup();
//        TextView title = (TextView)host.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
//        title.setTextColor(Color.parseColor("#ffffff"));
        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(20);
            tv.setTextColor(Color.WHITE);
        }
        b = this.getArguments();
        TextView des = (TextView)host.findViewById(R.id.descAns);

        TextView companyName = (TextView)host.findViewById(R.id.companyNameAns);
        TextView contact = (TextView)host.findViewById(R.id.contactNoAns);
        TextView address = (TextView)host.findViewById(R.id.addressAns);
        if(b != null){
            city = b.getString("city");
            state =b.getString("state");
            des.setText(b.getString("description"));
            companyName.setText(b.getString("company"));
            contact.setText(b.getString("mobile"));
            address.setText(city+","+state);
        }
       // host.setBackground();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Description");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Description");

        host.addTab(spec);



        //Tab 2
        spec = host.newTabSpec("Shop Details");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Shop Details");
        host.addTab(spec);




//        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//
//            @Override
//            public void onTabChanged(String tabId) {
//
//                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
//                    //host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
//                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
//                    tv.setTextColor(Color.parseColor("#ffffff"));
//                }
//
//                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
//                TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
//                tv.setTextColor(Color.parseColor("#000000"));
//
//            }
//        });
        return alldetailsView;
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
    public void onResume(){

        Log.d("All resume","all resume");
        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#000000"));
        }
        super.onResume();

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
