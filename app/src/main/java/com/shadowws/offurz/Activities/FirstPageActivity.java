package com.shadowws.offurz.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.shadowws.offurz.Fragments.LoginPageFragment;
import com.shadowws.offurz.R;
import com.shadowws.offurz.Fragments.SignUpFragment;

public class FirstPageActivity extends AppCompatActivity {
    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS


    };
    public static String fragname="";
    private static final int REQUEST_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        verifyStoragePermissions(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, new LoginPageFragment());
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.firstpage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_login) {
            //Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new LoginPageFragment()).addToBackStack(null);
            transaction.commit();
            return true;
        }
        if (id == R.id.action_signup) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new SignUpFragment()).addToBackStack(null);
            transaction.commit();
            //Toast.makeText(getApplicationContext(), "SignUp", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void verifyStoragePermissions(Activity activity) {
       // int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int readSMSPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        int receivePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            Log.d("FIRSTACTIVITY", "Permission not allowed");
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );
        }
        if (readSMSPermission != PackageManager.PERMISSION_DENIED && receivePermission != PackageManager.PERMISSION_DENIED) {
            Log.d("FIRSTACTIVITY", "Permission Allowed");
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );
        }
//        if (permission != PackageManager.PERMISSION_DENIED && readPermission != PackageManager.PERMISSION_DENIED) {
//            Log.d("FIRSTACTIVITY", "Permission Allowed");
//            ActivityCompat.requestPermissions(
//                    activity,
//                    PERMISSIONS,
//                    REQUEST_PERMISSION
//            );
//        }
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragname);
        if (count == 0) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            int fragcount = getFragmentManager().getBackStackEntryCount();
            Log.d("Count",""+fragcount);
            if (fragcount == 0) {
                final Dialog dialog = new Dialog(this);
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
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        SharedPreferences walletPreference = getApplicationContext().getSharedPreferences("buyerData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorWallet = walletPreference.edit();
                        editorWallet.clear();
                        editorWallet.commit();


                        Intent intent = new Intent(getApplicationContext(), FirstPageActivity.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();
                        //additional code
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            }
        else{
            getFragmentManager().popBackStack();
        }

    }
}