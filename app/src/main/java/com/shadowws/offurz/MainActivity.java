package com.shadowws.offurz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.shadowws.offurz.Activities.FirstPageActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "loginPrefs";
    ImageView image_1;
    ImageView image_2;
    Animation zoom, fade_in;
    static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Log.d(TAG,"MainActivity");
        getSupportActionBar().hide();
       image_1 = (ImageView) findViewById(R.id.homepage_image);
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
       // image_1.setAnimation(zoom);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

       // image_2 = (ImageView) findViewById(R.id.image_2);
      //  image_2.startAnimation(fade_in);

        SharedPreferences sharedPref = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        final int number = sharedPref.getInt("isLogged", 0);
        final String type = sharedPref.getString("type",null);
        Log.d(TAG, "MainActivity");
        Log.d(TAG, "Number"+number);
        Log.d(TAG,"type"+type);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (number == 0) {

                    Intent i = new Intent(getApplicationContext(), FirstPageActivity.class);
                    startActivity(i);
                    finish();
                }else
                {
                   if(type.equals("seller")) {
                       Intent i = new Intent(getApplicationContext(), HomePage.class);
                       startActivity(i);
                       finish();
                   }else if(type.equals("buyer")){
                       Intent i = new Intent(getApplicationContext(), BuyerHomePage.class);
                       startActivity(i);
                       finish();
                   }
                }

            }
        }, 5000);

    }
}
