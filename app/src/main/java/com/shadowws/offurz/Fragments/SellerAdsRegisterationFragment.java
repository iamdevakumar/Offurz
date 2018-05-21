package com.shadowws.offurz.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.shadowws.offurz.R;
import com.shadowws.offurz.SellerHomePagePurchaseBuyer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerAdsRegisterationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerAdsRegisterationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerAdsRegisterationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int SELECT_PHOTO = 12345;
    private final static int SELECT_PHOTO2 = 3;
    private final static int SELECT_PHOTO3 = 2;
    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,


    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView chooseImgTxt;
    private final static int PICK_IMAGE_MULTIPLE = 1;

    static String TAG = "sellerAdsReg";
    List<String> imagesEncodedList;
    TextInputEditText  sellerPNameTxt,sellerApriceTxt,sellerCountTxt,sellerOffPecentageTxt,sellerOffPriceTxt,sellerCcodeTxt,
            sellerDescpTxt;
    TextInputLayout sellerPNameLay,sellerApriceLay,sellerCountLay,sellerOffPecentageLay,sellerOffPriceLay,sellerCcodeLay,
            sellerDescpLay;
List<String> catArrayList;
    private OnFragmentInteractionListener mListener;
    ImageButton img1,img2,img3;
    ArrayList<String> imageArray = new ArrayList<>();
    ProgressDialog pDialog;
    Spinner Catspinner;
    ArrayAdapter adapter;
    String a_price,offPrec;
    String imageStrfirst;
    String encodedString;
    String imageURI;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    public SellerAdsRegisterationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerAdsRegisterationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerAdsRegisterationFragment newInstance(String param1, String param2) {
        SellerAdsRegisterationFragment fragment = new SellerAdsRegisterationFragment();
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
        String [] categories =
                {"Select the Categories","All products","Men","Women"};
        View sellerAdsView = inflater.inflate(R.layout.fragment_seller_ads_registeration, container, false);

//          String id = bundle.getString("user_id");
//           Log.d("user_id",id);


        sellerPNameTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_productName);
        sellerApriceTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_aPrice);
        sellerCcodeTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_cuponCode);
        sellerDescpTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_description);
        sellerCountTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_count);
        sellerOffPecentageTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_offPercentage);
        sellerOffPriceTxt = (TextInputEditText)sellerAdsView.findViewById(R.id.sellerAdsReg_offPrice);





//        SellerRegister sr = new SellerRegister();
//        Log.d("name",sr.getSellerName());
        sellerPNameLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_productName_layout);
        sellerApriceLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_layout_aPrice);
        sellerCcodeLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_layout_cuponCode);
        sellerDescpLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_layout_description);
        sellerCountLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_count_layout);
        sellerOffPecentageLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_offeredpercentage_layout);
        sellerOffPriceLay = (TextInputLayout)sellerAdsView.findViewById(R.id.sellerAdsReg_layout_offeredPrice);
        Catspinner = (Spinner) sellerAdsView.findViewById(R.id.cat_spinner);


       // sellerOffPriceTxt.setEnabled(false);
        GetCategories("http://offurz.com/view_category.php");


        Button choose_img = (Button)sellerAdsView.findViewById(R.id.choose_image);
     Button submitSellerAdsReg = (Button)sellerAdsView.findViewById(R.id.sellerAdsRegSubmit);
        chooseImgTxt =(TextView)sellerAdsView.findViewById(R.id.choose_image_text);
        choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        img1 = (ImageButton) sellerAdsView.findViewById(R.id.imageButton1);
        img2 = (ImageButton) sellerAdsView.findViewById(R.id.imageButton2);
        img3 = (ImageButton) sellerAdsView.findViewById(R.id.imageButton3);
        encodedImageList =new ArrayList<>();
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
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
        //sellerOffPriceTxt.setEnabled(false);
//        sellerOffPriceTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        SharedPreferences pref = getActivity().getSharedPreferences("sellerData", 0);
        String cmpName = pref.getString("company",null);
        if(cmpName !=null) {
            String cuponCode = cmpName.substring(0, 3);
            Random randomNumber = new Random();
            int answer = randomNumber.nextInt(100000) + 1;
            sellerCcodeTxt.setText(cuponCode + "-" + answer);
        }
        else
        {
            Toast.makeText(getContext(),"Not set Cupon code please enter",Toast.LENGTH_SHORT).show();
            sellerCcodeTxt.setEnabled(true);
        }
        sellerCcodeTxt.setEnabled(false);
        sellerOffPriceTxt.setEnabled(false);

sellerCountTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {
            Log.d("SellerAds","setOnFocusChangeListener");
        if(sellerApriceTxt.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getContext(),"Enter Actual price value",Toast.LENGTH_SHORT).show();
            sellerApriceTxt.requestFocus();
            }else if(sellerOffPecentageTxt.getText().toString().trim().equalsIgnoreCase("")){
                Toast.makeText(getContext(),"Enter Offered percentage value",Toast.LENGTH_SHORT).show();
            sellerOffPecentageTxt.requestFocus();
        }else
        {

            String aPrice = sellerApriceTxt.getText().toString();
            String offPrec = sellerOffPecentageTxt.getText().toString();
            int result = Integer.parseInt(aPrice) * Integer.parseInt(offPrec) / 100;
            int resultSecond = Integer.parseInt(aPrice) - result;
            Log.d("result", "result");
            Log.d("result", "" + resultSecond);
            sellerOffPriceTxt.setText("" + resultSecond);

        }

    }

});


        submitSellerAdsReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sellerPNameTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerPNameLay.setError("Enter Product Name");
                }else if(sellerApriceTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerApriceLay.setError("Enter Actual Price");


                }else if(sellerCcodeTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerCcodeLay.setError("Enter Coupon Code");
                }else if(sellerOffPriceTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerOffPriceLay.setError("Enter Offer Price");
                }else if(sellerOffPecentageTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerOffPriceLay.setError("Enter Offer Percentage");
                }else if(sellerCountTxt.getText().toString().trim().equalsIgnoreCase("")){
                    sellerCountLay.setError("Enter Count");
                }else if(sellerDescpTxt.getText().toString().trim().equalsIgnoreCase("")) {
                    sellerDescpTxt.setError("Enter Description");
                }else if(chooseImgTxt.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Select the Image",Toast.LENGTH_SHORT).show();

                }else if(encodedImageList.size() <=  3) {
                    Toast.makeText(getContext(),"Select Image",Toast.LENGTH_SHORT).show();
                    Log.d("EncodedList",""+encodedImageList.size());
                }
                else {
                    a_price = sellerApriceTxt.getText().toString();
                    offPrec = sellerOffPecentageTxt.getText().toString();
                    Log.d("EncodedList else",""+encodedImageList.size());

//                    int offprice = Integer.parseInt(a_price) * Integer.parseInt(offPrec);
//                    int offprec = offprice/100;
//                    Log.d("Off Price",""+offprec);
//                    sellerOffPriceTxt.setText(""+offprec);
                        SellerAdsRegisterPost("http://offurz.com/ads_reg.php");
                  //  SellerAdsRegisterPost("http://logistic.shadowws.in/ads_reg.php");
                 //   SellerAdsRegisterPost("http://logistic.shadowws.in/img_uploads.php");
                    //SellerAdsRegisterPost("http://192.168.43.84/php/ads_reg.php");
                }

            }
        });
//        choose_multi_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setType("image/*");
////                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_container, new SelectMultipleImageFragment());
//                transaction.commit();
//
//            }
//        });
        return sellerAdsView;
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

   // @SuppressLint("NewApi")
    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch(requestCode) {
            case SELECT_PHOTO:
                if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
                    // Let's read picked image data - its URI

                    imagesUriList = new ArrayList<Uri>();
                    encodedImageList.clear();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    if (data.getData() != null) {
                        Uri pickedImage = data.getData();
                        Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                       if(cursor !=null) {
                           cursor.moveToFirst();
                           int columnIndex = cursor.getColumnIndex(filePath[0]);
                           imageURI = cursor.getString(columnIndex);
                       }
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        encodedImageList.add(encodedImage);
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

                   if(cursor != null) {
                       cursor.moveToFirst();

                       int columnIndex = cursor.getColumnIndex(filePath[0]);
                       imageURI = cursor.getString(columnIndex);
                   }
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inSampleSize = 5;
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
                   if(cursor != null) {
                       cursor.moveToFirst();
                       int columnIndex = cursor.getColumnIndex(filePath[0]);
                       imageURI = cursor.getString(columnIndex);
                   }
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inSampleSize = 5;
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
                   if(cursor != null) {
                       cursor.moveToFirst();
                       int columnIndex = cursor.getColumnIndex(filePath[0]);
                       imageURI = cursor.getString(columnIndex);
                   }
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                    Log.d("encodedImageList",""+encodedImageList.size());

                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.d("File Path", "" + imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inSampleSize = 5;
                    Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath, options);
                    img3.setImageBitmap(bitmap2);

                    cursor.close();
                }

            }
    }
    public void SellerAdsRegisterPost(String url) {
        pDialog = new ProgressDialog(getContext());
     //   pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FC2D2D")));
        // pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
       // pDialog.setProgressStyle();
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();
        JSONArray jsArr = new JSONArray();

        try {
            SharedPreferences pref = getActivity().getSharedPreferences("sellerData", 0);
            Log.d("Shared",pref.getString("user_id",""));
//            for(int i = 0 ; i<imageArray.size();i++) {
//                Log.d("inside for",imageArray.get(i).toString());
//                String bm = imageArray.get(0);
//                BitmapFactory.Options options = null;
//                options = new BitmapFactory.Options();
//                options.inSampleSize = 3;
//              Bitmap  bitmap = BitmapFactory.decodeFile(bm,options);
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
//                byte[] byte_arr = stream.toByteArray();
//               encodedString = Base64.encodeToString(byte_arr, 0);

//               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), bm);
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//                encodedImageList.add(encodedImage);
//                Log.d("encodedImageList",""+encodedImageList.size());
//                Log.d("encodeImaage",""+encodedImage);
//               // Bitmap bm = BitmapFactory.decodeFile(imageArray.get(i));
//                ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//                byte[] b = bao.toByteArray();
//                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//                Log.d("Bitmap Image",encodedImage.toString());
//            }
            if (encodedImageList.isEmpty()) {
                Toast.makeText(getContext(), "Please select some images first.", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("Inside Volley",""+encodedImageList.size());
            for(String encode : encodedImageList){
                    jsArr.put(encode);
                    Log.d("encode",""+encode.length());
                }

            //js.put("image1","");
            js.put("product_name", sellerPNameTxt.getText().toString());
            js.put("coupon_code", sellerCcodeTxt.getText().toString());
            Log.d("JSONARRAY",""+jsArr.length());
            Log.d("Image Drawable",""+img1.getDrawable());
            js.put("description",sellerDescpTxt.getText().toString());
            js.put("a_price",sellerApriceTxt.getText().toString());
            js.put("off_percentage",sellerOffPecentageTxt.getText().toString());
            js.put("off_price",sellerOffPriceTxt.getText().toString());
            js.put("count",sellerCountTxt.getText().toString());
            js.put("user_id",pref.getString("user_id",""));
            js.put("s_name",pref.getString("name",""));
            js.put("company",pref.getString("company",""));
            js.put("mobile",pref.getString("mobile",""));
            js.put("categories",Catspinner.getSelectedItem().toString());
            js.put("city",pref.getString("city",""));
            js.put("state",pref.getString("state",""));
            js.put("approve",pref.getString("approve",""));
            Log.d("seller approve",pref.getString("approve",null));
               js.put("image",encodedImageList.get(0));
                js.put("name",sellerPNameTxt.getText().toString()+"_image_0");
                js.put("image1",encodedImageList.get(1));
                js.put("name1",sellerPNameTxt.getText().toString()+"_image_1");
                js.put("image2",encodedImageList.get(2));
                js.put("name2",sellerPNameTxt.getText().toString()+"_image_2");
                js.put("image3",encodedImageList.get(3));
                js.put("name3",sellerPNameTxt.getText().toString()+"_image_3");



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

                        Log.d("Inside","Inside If");
                        //Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, new ManageAdsFragment());
                        transaction.commit();

                    }else {
                        Log.d("Inside","Inside else");
                        Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
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


    public static List<String> removeDuplicateElements(List<String> catArrayList, int n){
        if (n==0 || n==1){
            return catArrayList;
        }

//        List<List<String>> tmpList = Arrays.asList(catArrayList);
//        //create a treeset with the list, which eliminates duplicates
//        TreeSet<String> unique = new TreeSet<String>((Comparator<? super String>) tmpList);
      //  Log.d("Unique",""+unique);
//       ArrayList<String> tempCat = new ArrayList<>();
//        ArrayList<String> tempCatsec = new ArrayList<>();
//        int j = 0;
//        for (int i=0; i<n-1; i++){
//            tempCat.add(catArrayList.get(i));
//            if (tempCat.get(i) == catArrayList.get(i)){
//               tempCat.add(catArrayList.get(i));
//                Log.d("CatArray duplicate",tempCat.get(i));
//            }
//        }
//        temp[j++] = arr[n-1];
//        // Changing original array
//        for (int i=0; i<j; i++){
//            arr[i] = temp[i];
//        }
        return catArrayList;
    }

}
