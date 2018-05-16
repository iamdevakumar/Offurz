package com.shadowws.offurz.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shadowws.offurz.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectMultipleImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectMultipleImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectMultipleImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout relative4;
    ImageButton img1,img2,img3;
    private final static int SELECT_PHOTO = 12345;
    private final static int SELECT_PHOTO2 = 1;
    private final static int SELECT_PHOTO3 = 2;
    private OnFragmentInteractionListener mListener;

    public SelectMultipleImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectMultipleImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectMultipleImageFragment newInstance(String param1, String param2) {
        SelectMultipleImageFragment fragment = new SelectMultipleImageFragment();
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
        View selectMultiView = inflater.inflate(R.layout.fragment_select_multiple_image, container, false);

        img1 = (ImageButton) selectMultiView.findViewById(R.id.imageButton1);
        img2 = (ImageButton) selectMultiView.findViewById(R.id.imageButton2);
       img3 = (ImageButton) selectMultiView.findViewById(R.id.imageButton3);
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                    img2.setVisibility(view.VISIBLE);

                }
            });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO2);
                img3.setVisibility(view.VISIBLE);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO3);
               // img3.setVisibility(view.VISIBLE);
            }
        });



//        relative4 = (LinearLayout) selectMultiView.findViewById(R.id.relative4);
//
//        Drawable d = getResources().getDrawable(R.drawable.ic_action_cart_white);
//        final int w = d.getIntrinsicWidth();
//        final int mScreenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        for (int i = 0; i < 4; i++) {
//            ImageView imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new LinearLayout.LayoutParams
//                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            imageView.setBackgroundResource(R.drawable.ic_action_eye);
//            relative4.addView(imageView);
//        }
        return selectMultiView;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
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
                    img1.setImageBitmap(bitmap);
                    String str = imagePath;
                    Log.d("STR", str);
                    String imgName = str.substring(str.lastIndexOf("/") + 1);
                    Log.d("imageName", imgName);
                    //  chooseImgTxt.setText(str.substring(str.lastIndexOf("/") + 1));
                    cursor.close();
                }
            case SELECT_PHOTO2:
                if (requestCode == SELECT_PHOTO2 && resultCode == RESULT_OK && data != null) {
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
                    img2.setImageBitmap(bitmap);
                    String str = imagePath;
                    Log.d("STR", str);
                    String imgName = str.substring(str.lastIndexOf("/") + 1);
                    Log.d("imageName", imgName);
                    //  chooseImgTxt.setText(str.substring(str.lastIndexOf("/") + 1));
                    cursor.close();
                }
            case SELECT_PHOTO3:
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
                    img3.setImageBitmap(bitmap);
                    String str = imagePath;
                    Log.d("STR", str);
                    String imgName = str.substring(str.lastIndexOf("/") + 1);
                    Log.d("imageName", imgName);
                    //  chooseImgTxt.setText(str.substring(str.lastIndexOf("/") + 1));
                    cursor.close();
                }
        }
    }

}
