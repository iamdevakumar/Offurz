package com.shadowws.offurz.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.shadowws.offurz.Pojo.ProductNames;
import com.shadowws.offurz.R;
import com.shadowws.offurz.adapter.ListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchViewFragment extends Fragment implements SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] productNamesList;
    ArrayList<ProductNames> arraylist = new ArrayList<ProductNames>();

    private OnFragmentInteractionListener mListener;

    public SearchViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchViewFragment newInstance(String param1, String param2) {
        SearchViewFragment fragment = new SearchViewFragment();
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

        productNamesList = new String[]{"Clothes", "Shoes", "Watches",
                "Bags", "Mobiles", "AA", "BB", "FF",
                "Electronics","DD","BB"};

        View searchViewFrag = inflater.inflate(R.layout.fragment_search_view, container, false);

        list = (ListView) searchViewFrag.findViewById(R.id.listview_search);

        for (int i = 0; i < productNamesList.length; i++) {
            ProductNames productNames = new ProductNames(productNamesList[i]);
            // Binds all strings into an array
            arraylist.add(productNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) searchViewFrag.findViewById(R.id.search_homepage);
        editsearch.setOnQueryTextListener(this);
        return searchViewFrag;
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
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.filter("");
            list.clearTextFilter();
        } else {
            adapter.filter(newText);
        }
        return true;
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
