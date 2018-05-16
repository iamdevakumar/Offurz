package com.shadowws.offurz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.shadowws.offurz.Activities.FirstPageActivity;
import com.shadowws.offurz.interfaces.SmsListener;

/**
 * Created by AD Technologies.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    static int count = 0;
    private static SmsListener mListener;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
       // Toast.makeText(context,"Received SMS"+intent.getAction().toString(),Toast.LENGTH_SHORT).show();
//        if (intent.getAction().equals("")) {
//            final String message = intent.getStringExtra("message");
//            Toast.makeText(context,"Received SMS"+message,Toast.LENGTH_SHORT).show();
//        }
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                //Toast.makeText(context,"SMS"+pdusObj.toString(),Toast.LENGTH_SHORT).show();
                Log.d("SMS"," "+pdusObj.toString());
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(TAG, message );
                    Log.d(TAG, message);
                    try {
                        if(message.contains("your Offurz")){
                            mListener.messageReceived(message);
                            Log.d(TAG,"Inside if");

                        }

                        else{
                            //Toast.makeText(context, "Msg Received ", Toast.LENGTH_LONG).show();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (Exception e) {

        }

    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}

