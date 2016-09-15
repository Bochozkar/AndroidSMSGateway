package com.cclouds.mysmsgateway.firebase;

/**
 * Created by anooj on 14/09/16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.cclouds.mysmsgateway.HomeActivity;
import com.cclouds.mysmsgateway.R;
import com.cclouds.mysmsgateway.sms.SmsDeliveredReceiver;
import com.cclouds.mysmsgateway.sms.SmsSentReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;


public class MyFirebaseMesagingService extends FirebaseMessagingService{

    private static final String TAG = "***FirebaseMsg***";



    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: VERIFY HIT");
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData().get("your_custom_data_key"));
            sendSMSMessage(remoteMessage.getData().get("your_custom_data_key"));
            sendNotification(remoteMessage.getNotification().getBody());
            //sendSMSMessage(remoteMessage.getData().get("your_custom_data_key"), remoteMessage.getData().get("body"));

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "MessageBody: " + remoteMessage.getNotification().getBody());
            //sendSMSMessage(remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {



        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());




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
        Log.e("REACHED","SEND");
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
