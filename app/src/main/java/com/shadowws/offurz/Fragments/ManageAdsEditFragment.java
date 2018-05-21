package com.shadowws.offurz.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.bumptech.glide.Glide;
import com.shadowws.offurz.R;
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;
import static com.shadowws.offurz.R.color.lightGrey;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageAdsEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageAdsEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageAdsEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String TAG = "ManageAdsEdit";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String productName,cuponCode,description,off_price,off_precentage,a_price,count,categoriesStr;
    EditText editPrName,editcuponCode,editdescription,editApriceTxt,editOffPrecentageTxt,editOffPriceTxt,editCountTxt;

    private OnFragmentInteractionListener mListener;
    ProgressDialog pDialog;
    TextView imageTextview;
    Bundle bundle;
    Spinner Catspinner;
    ArrayAdapter adapter;
SharedPreferences pref;
    String imageStrfirst;
Button editChooseImg;
    ImageButton img1,img2,img3;
    String imageURI;
    String cat;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    ArrayList<String> encodedImageListSecond;
    TextView chooseImgTxt;
    ArrayList<String> catArrayList;
    private final static int SELECT_PHOTO = 12345;
    private final static int SELECT_PHOTO2 = 3;
    private final static int SELECT_PHOTO3 = 2;
    private final static int PICK_IMAGE_MULTIPLE = 1;
    public ManageAdsEditFragment() {
        // Required empty public constructor
    }

     // TODO: Rename and change types and number of parameters
    public static ManageAdsEditFragment newInstance(String param1, String param2) {
        ManageAdsEditFragment fragment = new ManageAdsEditFragment();
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
        String [] categories =
                {"Select the Categories","All products","Men","Women","means wear"};
        View manageeditView = inflater.inflate(R.layout.fragment_manage_ads_edit, container, false);
         bundle = this.getArguments();

        encodedImageList =new ArrayList<>();
        encodedImageListSecond = new ArrayList<>();
         editPrName = (EditText)manageeditView.findViewById(R.id.editAdsReg_productName);
         editcuponCode = (EditText)manageeditView.findViewById(R.id.editAdsReg_cuponCode);
         editdescription = (EditText)manageeditView.findViewById(R.id.editAdsReg_description);
        editApriceTxt = (EditText) manageeditView.findViewById(R.id.editAdsReg_aPrice);
        editCountTxt = (EditText) manageeditView.findViewById(R.id.editAdsReg_count);
        editOffPrecentageTxt = (EditText) manageeditView.findViewById(R.id.editAdsReg_offPercentage);
        editOffPriceTxt = (EditText)manageeditView.findViewById(R.id.editAdsReg_offPrice);
        Catspinner = (Spinner) manageeditView.findViewById(R.id.cat_spinner);
       // adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, categories);
        imageTextview = (TextView)manageeditView.findViewById(R.id.edit_choose_image_text);
       // Catspinner.setAdapter(adapter);

        Button editBtn = (Button)manageeditView.findViewById(R.id.editAdsRegEdit);
        Button submitBtn = (Button)manageeditView.findViewById(R.id.editAdsRegSubmit);
        Button cancelBtn = (Button)manageeditView.findViewById(R.id.editAdsRegCancel);
        editChooseImg = (Button)manageeditView.findViewById(R.id.editchoose_image);
         img1 = (ImageButton)manageeditView.findViewById(R.id.edit_imageButton1);
        img2 = (ImageButton)manageeditView.findViewById(R.id.edit_imageButton2);
        img3 = (ImageButton)manageeditView.findViewById(R.id.edit_imageButton3);
        chooseImgTxt = (TextView)manageeditView.findViewById(R.id.edit_choose_image_text);


        editChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

            }
        });

       // Toast.makeText(getContext(),"bundle"+bundle.getString("cuponCode"),Toast.LENGTH_SHORT).show();

        if(bundle != null) {
            productName = bundle.getString("productName");
            cuponCode = bundle.getString("cuponCode");
            description = bundle.getString("description");
            count = bundle.getString("count");
            cuponCode = bundle.getString("cuponCode");
            off_precentage = bundle.getString("off_percentage");
            off_price = bundle.getString("off_price");
            categoriesStr = bundle.getString("categories");
            a_price = bundle.getString("a_price");
           // Toast.makeText(getContext(),"bundle inside"+cuponCode,Toast.LENGTH_SHORT).show();

        }
        editPrName.setText(productName);
        editcuponCode.setText(cuponCode);
        editdescription.setText(description);
        Log.d(TAG,"conut "+bundle.getString("count")+"a_price "+bundle.getString("a_price"));
        editApriceTxt.setText(bundle.getString("a_price"));
        editCountTxt.setText(bundle.getString("count"));
        editOffPrecentageTxt.setText(bundle.getString("off_percentage"));
        editOffPriceTxt.setText(bundle.getString("off_price"));
        cat = bundle.getString("categories");
      //  Toast.makeText(getContext(),"cat"+cat,Toast.LENGTH_SHORT).show();

      //  imageTextview.setText(bundle.getString("image"));
        final String imageFirst = "http://offurz.com/uploads/"+bundle.getString("image");
        Log.d("URL",imageFirst);
        GetCategories("http://offurz.com/view_category.php");

        final Bitmap[] imageBitMap = new Bitmap[1];
        new AsyncTask<Void, Void, Void>() {
            Bitmap bmp;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(imageFirst).openStream();
                    if(in != null)
                        bmp = BitmapFactory.decodeStream(in);
                    else {
                        bmp = BitmapFactory.decodeResource(getContext().getResources(),
                                R.drawable.gift_100);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null) {
                    imageBitMap[0] = bmp;
                    // profileImageView.setImageBitmap(bmp);
                    chooseImgTxt.setText(bundle.getString("image"));
                    Log.d("Image first",bmp.toString());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    Log.d("encodedImageListSecond","1");
                    encodedImageList.add(encodedImage);
                }
            }

        }.execute();


        String imgae2Spilt = bundle.getString("image2");


        String[] splitImg = imgae2Spilt.split(",");

        if(splitImg.length > 0) {

            final String imageSecond_1 = "http://offurz.com/uploads/" + splitImg[0];
            final Bitmap[] imageBitMapSecond = new Bitmap[1];
            new AsyncTask<Void, Void, Void>() {
                Bitmap bmp;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(imageSecond_1).openStream();
                        if(in != null) {
                            bmp = BitmapFactory.decodeStream(in);
                            Log.d("BMP",bmp.toString());
                        }
                        else {
                            bmp = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.gift_100);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null) {
                        imageBitMapSecond[0] = bmp;
                         img1.setImageBitmap(bmp);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        Log.d("encodedImageListSecond","2");
                        encodedImageList.add(encodedImage);
                    }
                }

            }.execute();

        }
        if(splitImg.length > 1) {

            final String imageSecond_2 = "http://offurz.com/uploads/" + splitImg[1];
            final Bitmap[] imageBitMapThird = new Bitmap[1];
            new AsyncTask<Void, Void, Void>() {
                Bitmap bmp;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(imageSecond_2).openStream();
                        if(in != null)
                            bmp = BitmapFactory.decodeStream(in);
                        else {
                            bmp = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.gift_100);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null) {
                        imageBitMapThird[0] = bmp;
                        img2.setImageBitmap(bmp);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        Log.d("encodedImageListSecond","3");
                        encodedImageList.add(encodedImage);
                    }
                }

            }.execute();

        }
        if(splitImg.length > 2) {

            final String imageSecond_3 = "http://offurz.com/uploads/" + splitImg[2];
            final Bitmap[] imageBitMapFourth = new Bitmap[1];
            new AsyncTask<Void, Void, Void>() {
                Bitmap bmp;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(imageSecond_3).openStream();
                        if(in != null)
                            bmp = BitmapFactory.decodeStream(in);
                        else {
                            bmp = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.gift_100);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null) {
                        imageBitMapFourth[0] = bmp;
                         img3.setImageBitmap(bmp);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        Log.d("encodedImageListSecond","4");
                        encodedImageList.add(encodedImage);
                    }
                }

            }.execute();

        }


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//        encodedImageList.add(encodedImage);



//        Log.d("Image 2",bundle.getString("image2"));
//        String imgaeSpilt = bundle.getString("image2");
//        String[] splitImg2 = imgaeSpilt.split(",");
//        if(splitImg2.length > 0) {
//            Glide.with(getContext())
//                    .load("http://logistic.shadowws.in/uploads/" + splitImg[0])
//                    .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
//                    .into(img1);
//            Log.d(TAG, "Image Url" + splitImg[0]);
//
//            BitmapDrawable drawable = (BitmapDrawable) img1.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//            encodedImageList.add(encodedImage);
//            if(splitImg.length > 1){
//                Glide.with(getContext())
//                        .load("http://logistic.shadowws.in/uploads/" + splitImg[1])
//                        .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
//                        .into(img2);
//                BitmapDrawable drawable2 = (BitmapDrawable) img2.getDrawable();
//                Bitmap bitmap2 = drawable2.getBitmap();
//                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
//                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
//                String encodedImage2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
//                encodedImageList.add(encodedImage2);
//                Log.d(TAG, "Image Url" + splitImg[1]);
//            }
//            else
//            {
//                img3.setVisibility(View.INVISIBLE);
//            }
//            if(splitImg.length > 2){
//
//                Glide.with(getContext())
//                        .load("http://logistic.shadowws.in/uploads/" + splitImg[2])
//                        .error(R.drawable.gift_100).placeholder(R.drawable.gift_100)
//                        .into(img3);
//                BitmapDrawable drawable3 = (BitmapDrawable) img3.getDrawable();
//                Bitmap bitmap3 = drawable3.getBitmap();
//                ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
//                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
//                String encodedImage3 = Base64.encodeToString(byteArrayOutputStream3.toByteArray(), Base64.DEFAULT);
//                encodedImageList.add(encodedImage3);
//
//                Log.d(TAG, "Image Url" + splitImg[2]);
//            }
//        }


       // Catspinner.setPrompt(cat);


        editPrName.setEnabled(false);
        //editPrName.setBackgroundColor(lightGrey);
        editcuponCode.setEnabled(false);
        editdescription.setEnabled(false);
        editApriceTxt.setEnabled(false);
        editCountTxt.setEnabled(false);
        editOffPrecentageTxt.setEnabled(false);
        editOffPriceTxt.setEnabled(false);
        imageTextview.setEnabled((false));
        Catspinner.setEnabled(false);
        editChooseImg.setEnabled(false);
        img1.setEnabled(false);
        img2.setEnabled(false);
        img3.setEnabled(false);



        editcuponCode.setEnabled(false);
        editOffPriceTxt.setEnabled(false);

        editCountTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                  @Override
                                                  public void onFocusChange(View view, boolean b) {
                                                      Log.d("SellerAds", "setOnFocusChangeListener");
                                                      if (editApriceTxt.getText().toString().trim().equalsIgnoreCase("")) {
                                                          Toast.makeText(getContext(), "Enter Actual price value", Toast.LENGTH_SHORT).show();
                                                          editApriceTxt.requestFocus();
                                                      } else if (editOffPrecentageTxt.getText().toString().trim().equalsIgnoreCase("")) {
                                                          Toast.makeText(getContext(), "Enter Offered percentage value", Toast.LENGTH_SHORT).show();
                                                          editOffPriceTxt.requestFocus();
                                                      } else {

                                                          String aPrice = editApriceTxt.getText().toString();
                                                          String offPrec = editOffPrecentageTxt.getText().toString();
                                                          int result = Integer.parseInt(aPrice) * Integer.parseInt(offPrec) / 100;
                                                          int resultSecond = Integer.parseInt(aPrice) - result;
                                                          Log.d("result", "result");
                                                          Log.d("result", "" + resultSecond);
                                                          editOffPriceTxt.setText("" + resultSecond);

                                                      }
                                                  }
                                              });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Allow to Edit the Value",Toast.LENGTH_SHORT).show();
                editPrName.setEnabled(true);
                editdescription.setEnabled(true);
                editApriceTxt.setEnabled(true);
                editCountTxt.setEnabled(true);
                imageTextview.setEnabled(true);
                editOffPrecentageTxt.setEnabled(true);
                editChooseImg.setEnabled(true);
                Catspinner.setEnabled(true);
                img1.setEnabled(true);
                img2.setEnabled(true);
                img3.setEnabled(true);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, new ManageAdsFragment());
                transaction.commit();

            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_MULTIPLE);
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
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editPrName.getText().toString().trim().equalsIgnoreCase("")){
                  //  editPrNameLa.setError("Enter Product Name");
                    Toast.makeText(getContext(),"Product Name empty",Toast.LENGTH_SHORT).show();
                }else if(editApriceTxt.getText().toString().trim().equalsIgnoreCase("")){
                   // sellerApriceLay.setError("Enter Actual Price");
                    Toast.makeText(getContext(),"Actual price empty",Toast.LENGTH_SHORT).show();
                }else if(editOffPrecentageTxt.getText().toString().trim().equalsIgnoreCase("")){
                   // sellerOffPriceLay.setError("Enter Offer Price");
                    Toast.makeText(getContext(),"Offered Percentage empty",Toast.LENGTH_SHORT).show();
                }else if(editCountTxt.getText().toString().trim().equalsIgnoreCase("")){
                   // sellerCountLay.setError("Enter Count");
                    Toast.makeText(getContext(),"Count empty",Toast.LENGTH_SHORT).show();
                }else if(editdescription.getText().toString().trim().equalsIgnoreCase("")) {
                   // sellerDescpTxt.setError("Enter Description");
                    Toast.makeText(getContext(),"Description empty",Toast.LENGTH_SHORT).show();
                }else if(chooseImgTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Select the Image", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d("EncodedList else", "" + encodedImageList.size());
                   // ManageAdsEditPost("http://logistic.shadowws.in/update_pur_details_an.php");
                    ManageAdsEditPost("http://offurz.com/update_pur_details_an.php");
                }
            }
        });
        // Inflate the layout for this fragment
        return manageeditView;
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
    public void ManageAdsEditPost(String url) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        pref = getContext().getSharedPreferences("sellerData", 0);
        Log.d(TAG,pref.getString("user_id",null));
        Log.d(TAG,pref.getString("company",null));
        Log.d(TAG,pref.getString("mobile",null));
        Log.d(TAG,productName);
        Log.d(TAG,cuponCode);
        Log.d(TAG,description);
        JSONObject js = new JSONObject();

        Log.d("Encoded first",""+encodedImageList.size());
        Log.d("Encoded Second",""+encodedImageListSecond.size());
        try {


            Log.d(TAG,""+Catspinner.getSelectedItem());
            Log.d(TAG,""+editPrName.getText().toString());
            Log.d(TAG,""+editdescription.getText().toString());
           js.put("ads_id",bundle.getInt("id"));//product_id
            //js.put("product_name","ks test");
            js.put("product_name",editPrName.getText().toString());
            js.put("coupon_code",editcuponCode.getText().toString());
            js.put("a_price",editApriceTxt.getText().toString());
            js.put("off_percentage",editOffPrecentageTxt.getText().toString());
            js.put("off_price",editOffPriceTxt.getText().toString());
            js.put("count",editCountTxt.getText().toString());
            js.put("description",editdescription.getText().toString());
            js.put("categories",Catspinner.getSelectedItem());
                js.put("image",encodedImageList.get(0));
                js.put("name",editPrName.getText().toString()+"_image_0");
                Log.d("Image First",encodedImageList.get(0));
                js.put("image1",encodedImageList.get(1));
                js.put("name1",editPrName.getText().toString()+"_image_1");
                Log.d("Image Second",encodedImageList.get(1));
                js.put("image2",encodedImageList.get(2));
                js.put("name2",editPrName.getText().toString()+"_image_2");
                Log.d("Image Third",encodedImageList.get(2));
                js.put("image3",encodedImageList.get(3));
                js.put("name3",editPrName.getText().toString()+"_image_3");
                Log.d("Image Fourth",encodedImageList.get(3));

//            else if(encodedImageListSecond.size() >0){
//                js.put("image",encodedImageListSecond.get(0));
//                js.put("name",editPrName.getText().toString()+"_image_0");
//                Log.d("Image First edit",encodedImageListSecond.get(0));
//                js.put("image1",encodedImageListSecond.get(1));
//                js.put("name1",editPrName.getText().toString()+"_image_1");
//                Log.d("Image Second edit",encodedImageListSecond.get(1));
//                js.put("image2",encodedImageListSecond.get(2));
//                js.put("name2",editPrName.getText().toString()+"_image_2");
//                Log.d("Image Third edit",encodedImageListSecond.get(2));
//                js.put("image3",encodedImageListSecond.get(3));
//                js.put("name3",editPrName.getText().toString()+"_image_3");
//                Log.d("Image Fourth edit",encodedImageListSecond.get(3));
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

                // Toast.makeText(getContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG,response.toString());
                try {

                    //JSONObject jsarray = new JSONObject(response.toString());
                    Log.d(TAG, "Array value" + response.toString());
                   // Log.d("ManageAdsEdit", "Array size" + jsarray.length());
                    boolean message = response.getBoolean("success");
                    if(message){
                        Toast.makeText(getContext(),"ManageAds added Successfully",Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, new ManageAdsFragment());
                        transaction.commit();
                    }
                    else {
                        Toast.makeText(getContext(),"ManageAds Not added",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
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

    public void setSpinText(Spinner spin, String text)
    {
        Log.d("Spin Text",text);
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
                    // Let's read picked image data - its URI

                    imagesUriList = new ArrayList<Uri>();
                   // encodedImageList.clear();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    if (data.getData() != null) {
                        Uri pickedImage = data.getData();
                        Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePath[0]);
                        imageURI = cursor.getString(columnIndex);
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        if(encodedImageList.size() <= 3){
                            encodedImageList.add(encodedImage);
                        }else
                        encodedImageList.set(0,encodedImage);
                        Log.d("encodedImageList",""+encodedImageList.size());
                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                        Log.d("File Path", "" + imagePath);
                        String str = imagePath;
//                    //imageArray.add(imagePath);
                        Log.d("STR", str);
                        String imgName = str.substring(str.lastIndexOf("/") + 1);
                        Log.d("imageName", imgName);
                        chooseImgTxt.setText(imgName);
                        cursor.close();
                    }
                    else {
                        Toast.makeText(getContext(),"IMAGE ELSE",Toast.LENGTH_SHORT).show();
                    }
                }
            case PICK_IMAGE_MULTIPLE:
                if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
                    // Let's read picked image data - its URI
                    Uri pickedImage = data.getData();
                    // Let's read picked image path using content resolver
                    String full_path = pickedImage.getPath();
                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    imageURI = cursor.getString(columnIndex);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    if(encodedImageList.size() <= 3){
                        encodedImageList.add(encodedImage);
                    }else
                    encodedImageList.set(1,encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath, options);
                    img1.setImageBitmap(bitmap2);
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
                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    imageURI = cursor.getString(columnIndex);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    if(encodedImageList.size() <= 3){
                        encodedImageList.add(encodedImage);
                    }else
                    encodedImageList.set(2,encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath, options);
                    img2.setImageBitmap(bitmap2);
//                    String str = imagePath;
//                    //imageArray.add(imagePath);
//                    Log.d("STR", str);
//                    String imgName = str.substring(str.lastIndexOf("/") + 1);
//                    Log.d("imageName", imgName);
                    cursor.close();
                }
            case SELECT_PHOTO3:
                if (requestCode == SELECT_PHOTO3 && resultCode == RESULT_OK && data != null) {
                    Uri pickedImage = data.getData();
                    String full_path = pickedImage.getPath();
                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    imageURI = cursor.getString(columnIndex);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    if(encodedImageList.size() <= 3){
                        encodedImageList.add(encodedImage);
                    }else
                    encodedImageList.set(3,encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath, options);
                    img3.setImageBitmap(bitmap2);

                    cursor.close();
                }

        }
    }
    public void GetCategories(String url) {
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
                    catArrayList = new ArrayList<>();
                    if (message) {
                        //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < jsArray.length(); i++) {
                            JSONObject jsObj = jsArray.getJSONObject(i);

                            catArrayList.add(jsObj.getString("category"));




                        }

// add elements to al, including duplicates
                        Set<String> hs = new HashSet<>();
                        hs.addAll(catArrayList);
                        catArrayList.clear();
                        catArrayList.addAll(hs);
                        if(catArrayList.size() >0) {
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, catArrayList);
                            Catspinner.setAdapter(adapter);

                          setSpinText(Catspinner,cat);
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
