package com.shadowws.offurz.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shadowws.offurz.R;
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;
import com.shadowws.offurz.adapter.CustomPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OurSellerPriceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OurSellerPriceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OurSellerPriceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
ViewPager mViewPager;
    private OnFragmentInteractionListener mListener;
    CustomPagerAdapter mCustomPagerAdapter;

    public OurSellerPriceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OurSellerPriceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OurSellerPriceFragment newInstance(String param1, String param2) {
        OurSellerPriceFragment fragment = new OurSellerPriceFragment();
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
        View oursellerpriceView = inflater.inflate(R.layout.fragment_our_seller_price, container, false);

        mCustomPagerAdapter = new CustomPagerAdapter(getContext());

        mViewPager = (ViewPager) oursellerpriceView.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
//        TitlePageIndicator indicator = (TitlePageIndicator) oursellerpriceView.findViewById(R.id.indicator);
//        indicator.setViewPager(mViewPager);
        CirclePageIndicator indicator =(CirclePageIndicator) oursellerpriceView.findViewById(R.id.indicator);
        indicator.setFillColor(R.color.redToolbar);
        indicator.setViewPager(mViewPager);

        //indicator.setSelectedItem(int, true);
//        TabLayout tabLayout = (TabLayout) oursellerpriceView.findViewById(R.id.tabDots);
//        tabLayout.setupWithViewPager(mViewPager, true);
       // mRecyclerView = (RecyclerView) oursellerpriceView.findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
        return oursellerpriceView;
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
                        transaction.replace(R.id.main_container, new SellerHomePagePurchaseBuyer());
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
                        ft.replace(R.id.main_container, new OurSellerPriceFragment());
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
