package com.shadowws.offurz;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shadowws.offurz.Activities.FirstPageActivity;
import com.shadowws.offurz.Fragments.AboutUsBuyer;
import com.shadowws.offurz.Fragments.AboutUsFragment;
import com.shadowws.offurz.Fragments.BestSellerFragment;
import com.shadowws.offurz.Fragments.ChangePasswordBuyer;
import com.shadowws.offurz.Fragments.EnquiryFragment;
import com.shadowws.offurz.Fragments.HomePageFragment;
import com.shadowws.offurz.Fragments.HotDealsFragment;
import com.shadowws.offurz.Fragments.NewLaunchProducts;
import com.shadowws.offurz.Fragments.OurSellerPriceFragment;
import com.shadowws.offurz.Fragments.PurchaseDetailsFragment;
import com.shadowws.offurz.Fragments.SettingsFragment;
import com.shadowws.offurz.Fragments.TermsAndConditionsFragment;
import com.shadowws.offurz.Fragments.TermsandConditionBuyer;
import com.shadowws.offurz.Fragments.TrendingOffersFragment;

public class BuyerHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        static String TAG = "BuyerHomePage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences loginPref = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = loginPref.edit();
        prefEditor.putInt("isLogged", 1);
        prefEditor.putString("type","buyer");
        Log.d(TAG, "buyer_isLogged" + 1);
        prefEditor.commit();

//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                } else {
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                }
//            }
//        });


       // getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
      //  shouldDisplayHomeUp();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.buyer_main_container, new HomePageFragment());
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences pref = getSharedPreferences("buyerData",0);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
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


        }
//            if (getFragmentManager().getBackStackEntryCount() > 0) {
//                getFragmentManager().popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            int count = getFragmentManager().getBackStackEntryCount();
//            Log.d("Back Press","count"+count);
//            if (count == 0)
//                super.onBackPressed();
//        }

//            int count = getFragmentManager().getBackStackEntryCount();
//
//            if (count == 0) {
//                super.onBackPressed();
//                //additional code
//            } else {
//                getFragmentManager().popBackStack();
//            }
           // super.onBackPressed();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.buyer_main_container, new HomePageFragment());
//            transaction.commit();



//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buyer_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_home){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.buyer_main_container, new HomePageFragment());
            transaction.commit();
            return true;
        }
        if (id == R.id.action_logout) {
            SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            SharedPreferences profilePref = getSharedPreferences("buyerData", Context.MODE_PRIVATE);
            SharedPreferences.Editor profileEdit = profilePref.edit();
            profileEdit.clear();
            profileEdit.commit();
            Intent intent = new Intent(this, FirstPageActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
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
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomePageFragment();

        } else if (id == R.id.nav_trendingOffers) {
            fragment = new TrendingOffersFragment();

        } else if (id == R.id.nav_changePassword) {
            fragment = new ChangePasswordBuyer();

        } else if (id == R.id.nav_purchaseDetails) {
            fragment = new PurchaseDetailsFragment();

//        } else if (id == R.id.nav_enquiry) {
//            fragment = new EnquiryFragment();

        } else if (id == R.id.nav_ourSellerPrice) {
            fragment = new OurSellerPriceFragment();

        }
        else if (id == R.id.nav_bestSeller) {
            fragment = new BestSellerFragment();
        }
        else if (id == R.id.nav_newLaunchProducts) {
            fragment = new NewLaunchProducts();

        } else if (id == R.id.nav_hotDeals) {
            fragment = new HotDealsFragment();
        }else if (id == R.id.nav_AboutUs) {
            fragment = new AboutUsBuyer();
        }else if (id == R.id.nav_TermsConditions) {
            fragment = new TermsandConditionBuyer();
        }else if(id == R.id.nav_logout){
          //  Hotline.clearUserData(getApplicationContext());
            Log.d(TAG,"LogOUT");
            SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
//            SharedPreferences walletPreference = getSharedPreferences("sellerData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editorWallet = walletPreference.edit();
//            editorWallet.clear();
//            editorWallet.commit();
            SharedPreferences profilePref = getSharedPreferences("buyerData", Context.MODE_PRIVATE);
            SharedPreferences.Editor profileEdit = profilePref.edit();
            profileEdit.clear();
            profileEdit.commit();
            Intent intent = new Intent(this, FirstPageActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.buyer_main_container, fragment);
            fragmentTransaction.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





//    @Override
//    public void onBackStackChanged() {
//        shouldDisplayHomeUp();
//    }
//    public void shouldDisplayHomeUp(){
//        //Enable Up button only  if there are entries in the back stack
//        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        //This method is called when the up button is pressed. Just the pop back stack.
//        getSupportFragmentManager().popBackStack();
//        return true;
//    }

}
