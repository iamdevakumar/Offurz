package com.shadowws.offurz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shadowws.offurz.Activities.FirstPageActivity;
import com.shadowws.offurz.Fragments.AboutUsFragment;
import com.shadowws.offurz.Fragments.AdsRegisterLimitationFragment;
import com.shadowws.offurz.Fragments.EnquiryFragment;
import com.shadowws.offurz.Fragments.HomePageFragment;
import com.shadowws.offurz.Fragments.LoginPageFragment;
import com.shadowws.offurz.Fragments.ManageAdsFragment;
import com.shadowws.offurz.Fragments.OurSellerPriceFragment;
import com.shadowws.offurz.Fragments.PurchaseBuyerDetailsFragment;
import com.shadowws.offurz.Fragments.SellerAdsRegisterationFragment;
import com.shadowws.offurz.Fragments.SellerRegistationFragment;
import com.shadowws.offurz.Fragments.SettingsFragment;
import com.shadowws.offurz.Fragments.SignUpFragment;
import com.shadowws.offurz.Fragments.TermsAndConditionsFragment;
import com.shadowws.offurz.Fragments.ViewProductDetailsFragment;
import com.shadowws.offurz.Others.BadgeDrawable;
import com.shadowws.offurz.Pojo.BestSeller;
import com.shadowws.offurz.Pojo.ProductName;
import com.shadowws.offurz.adapter.BestSellerAdapter;
import com.shadowws.searchlibrary.iterfaces.interfaces.onSearchListener;
import com.shadowws.searchlibrary.iterfaces.interfaces.onSimpleSearchActionsListener;
import com.shadowws.searchlibrary.iterfaces.widgets.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, onSimpleSearchActionsListener, onSearchListener ,EnquiryFragment.OnFragmentInteractionListener{
    private TabLayout tabLayout;
    private String[] prodcutList;
    static String TAG = "HomePage";
    public static ArrayList<ProductName> productNameArrayList = new ArrayList<ProductName>();
    //This is our viewPager
    private ViewPager viewPager;
    SearchView editsearch;
    LayerDrawable icon;
    public static boolean isChangeMenu = false;
    public static Menu menu;
    private MaterialSearchView mSearchView;
    private WindowManager mWindowManager;
    Toolbar mToolbar;
    private boolean mSearchViewAdded = false;
    private MenuItem searchItem;
    private boolean searchActive = false;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        SharedPreferences loginPref =getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = loginPref.edit();
        prefEditor.putInt("isLogged", 1);
        prefEditor.putString("type","seller");
        Log.d(TAG, "sellerRegLog" + 1);
        prefEditor.commit();

//        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        mSearchView = new MaterialSearchView(this);
//        mSearchView.setOnSearchListener(this);
//        mSearchView.setSearchResultsListener(this);
//        mSearchView.setHintText("Search");
//        if (mToolbar != null) {
//            // Delay adding SearchView until Toolbar has finished loading
//            mToolbar.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (!mSearchViewAdded && mWindowManager != null) {
//                        mWindowManager.addView(mSearchView,
//                                MaterialSearchView.getSearchViewLayoutParams(HomePage.this));
//                        mSearchViewAdded = true;
//                    }
//                }
//            });
//        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, new SellerHomePagePurchaseBuyer());
        transaction.commit();

        SharedPreferences pref = getSharedPreferences("sellerData",0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

//        View header=navigationView. findViewById(R.id.header);
        TextView name = (TextView)hView.findViewById(R.id.username);
        TextView email = (TextView)hView.findViewById(R.id.email);
        name.setText(pref.getString("name",null));
        email.setText(pref.getString("email",null));

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            int count = getFragmentManager().getBackStackEntryCount();
            Log.d("Count",""+count);
            if (count == 0) {
                super.onBackPressed();
                Log.d("Back Press","Back Press");
                // finish();
            }

//            if(getFragmentManager().findFragmentByTag("home")){
//
//            }


//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.main_container, new HomePageFragment());
//            transaction.commit();

        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("inside menu","iside Menu");
        if (isChangeMenu) {
//            MenuItem itemCart = menu.findItem(R.id.action_adcart);
//            icon = (LayerDrawable) itemCart.getIcon();
           // setBadgeCount(this, icon, "9");
//            menu.findItem(R.id.action_adcart).setIcon(R.drawable.ic_action_cart_white);
//        } else {
//            menu.findItem(R.id.action_adcart).setIcon(R.drawable.ic_action_cart_white);
//        }
        }

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        this.menu = menu;
        getMenuInflater().inflate(R.menu.home_page, menu);
//        searchItem = menu.findItem(R.id.search);
//        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                mSearchView.display();
//                openKeyboard();
//                return true;
//            }
//        });
//        if(searchActive)
//            mSearchView.display();


        return true;
    }
    private void openKeyboard(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 200);
    }

    public void setBadgeCount(Context context,LayerDrawable icon, String s) {
        BadgeDrawable badge;

        // Reuse drawable if possible
        Log.d("inside badge","inside badge");
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            Log.d("inside reuse","inside reuse");
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(s);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
//        startActivity(getIntent());
//        finish();
        // onRestart();
       // recreate();
       // startActivity(new Intent(this,HomePage.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new SellerHomePagePurchaseBuyer());
            transaction.commit();
            return true;
        }
        if(id == R.id.action_logout){
            //Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            SharedPreferences walletPreference = getSharedPreferences("sellerData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorWallet = walletPreference.edit();
            editorWallet.clear();
            editorWallet.commit();
            Intent intent = new Intent(this, FirstPageActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.action_signup){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new SignUpFragment());
            transaction.commit();
           // Toast.makeText(getApplicationContext(),"SignUp",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new PurchaseBuyerDetailsFragment();
        } else if (id == R.id.nav_registerAds) {
            AdsRegstireLimitation("http://offurz.com/reg_check_an.php");

          // fragment = new SellerAdsRegisterationFragment();

        } else if (id == R.id.nav_manageAds) {
          fragment =  new ManageAdsFragment();

        } else if (id == R.id.nav_purchaseBuyerDetails) {
            fragment = new PurchaseBuyerDetailsFragment();

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();

//        }else if (id == R.id.nav_enquiry) {
//           fragment = new EnquiryFragment();
        } else if (id == R.id.nav_logout) {

            SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            SharedPreferences walletPreference = getSharedPreferences("sellerData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorWallet = walletPreference.edit();
            editorWallet.clear();
            editorWallet.commit();
//            SharedPreferences profilePref = getSharedPreferences("buyerData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor profileEdit = profilePref.edit();
//            profileEdit.clear();
//            profileEdit.commit();
            Intent intent = new Intent(this, FirstPageActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_AboutUs) {

            fragment =  new AboutUsFragment();

        }else if (id == R.id.nav_TermsConditions) {

           fragment =  new TermsAndConditionsFragment();

        }else if (id == R.id.nav_ourSellerPrice) {
            fragment = new OurSellerPriceFragment();

        }

        if (fragment != null) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment).addToBackStack(null);
        fragmentTransaction.commit();

    }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearch(String query) {
        Log.d("Query",""+query);

    }

    @Override
    public void searchViewOpened() {
        Toast.makeText(HomePage.this,"Search View Opened",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void searchViewClosed() {
       // Util.showSnackBarMessage(fab,"Search View Closed");
        Toast.makeText(HomePage.this,  " search view closed",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCancelSearch() {
        searchActive = false;
        mSearchView.hide();

    }

    @Override
    public void onItemClicked(String item) {
        Toast.makeText(HomePage.this,item + " clicked",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onScroll() {

    }

    @Override
    public void error(String localizedMessage) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void AdsRegstireLimitation(String url) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences pref = getSharedPreferences("sellerData",0);
JSONObject js = new JSONObject();
        try {
            js.put("user_id",pref.getString("user_id",null));
            js.put("name", pref.getString("name",null));
            js.put("company",pref.getString("company",null));
            js.put("mobile", pref.getString("mobile",null));
            Log.d("user_id",pref.getString("user_id",null));
            Log.d("name",pref.getString("name",null));
            Log.d("company",pref.getString("company",null));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,js, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // TODO Auto-generated method stub

               // Toast.makeText(getApplicationContext(), " Result" + response.toString(), Toast.LENGTH_LONG).show();
                Log.d("Response",response.toString());
                try {
                    JSONObject jsObj = new JSONObject(response.toString());

                    boolean msg = jsObj.getBoolean("success");
                    if(msg){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AdsRegisterLimitationFragment()).addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new SellerAdsRegisterationFragment()).addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                try {
//                   // JSONArray jsarray = new JSONArray(response.toString());
//
//                    Log.d("BestSeller", "Array value" + response.toString());
//                //    Log.d("BestSeller", "Array size" + jsarray.length());
//
//
////                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                //Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("HomePage",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Time Out error", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        //Toast.makeText(getApplicationContext(), "Err  " + networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
