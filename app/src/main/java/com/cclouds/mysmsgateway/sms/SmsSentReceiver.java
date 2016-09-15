package com.cclouds.mysmsgateway.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by anooj on 14/09/16.
 */
public class SmsSentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Log.e("SMS_STAT","SMS SENT");
                //Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();

                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Log.e("SMS_STAT","SMS GENERIC FAILURE");
                //Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT)
                       // .show();

                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Log.e("SMS_STAT","SMS NO SERVICE");
                //Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT)
                //        .show();

                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Log.e("SMS_STAT","SMS NUL PDU");
                //Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Log.e("SMS_STAT","SMS RADIO OFF");
                //Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
