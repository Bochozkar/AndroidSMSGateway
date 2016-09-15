package com.cclouds.mysmsgateway;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;

import com.cclouds.mysmsgateway.sms.SmsDeliveredReceiver;
import com.cclouds.mysmsgateway.sms.SmsSentReceiver;

import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ONSTART", "ONSTART");
        if((getIntent().getExtras()) != null){
            //do your stuff
            Log.e("GETEXTRAS","SOMETHING");
            Bundle bundle=getIntent().getExtras();
//            if(bundle!=null) {
//                for (String key : bundle.keySet()) {
//                    Object value = bundle.get(key);
//                    Log.e("DATA_SENT", String.format("%s %s (%s)", key,
//                            value.toString(), value.getClass().getName()));
//                }
//            }
            Log.e("GETEXTRAS",""+bundle.getString("your_custom_data_key"));
            sendSMSMessage(bundle.getString("your_custom_data_key"));

        }else{
            //do that you normally do
            Log.e("GETEXTRAS","NORMQL");
        }
    }

    private void sendSMSMessage(String data) {

        try {
            Log.e("Send SMS", "data "+data);
            JSONObject mObj =   new JSONObject(data);


            String phoneNo = mObj.getString("messageTo").toString();
            String message = mObj.getString("message").toString();

            sendSMS(phoneNo,message);

//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNo, null, message, null, null);
//            //Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        Log.e("REACHED", "SEND");
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(this, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(this, SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("ERROR",""+e.getMessage());
            //Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }
}
