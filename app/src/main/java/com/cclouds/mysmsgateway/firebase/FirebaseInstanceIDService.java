package com.cclouds.mysmsgateway.firebase;

/**
 * Created by anooj on 14/09/16.
 */

import android.content.Intent;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.cclouds.mysmsgateway.App;
import com.cclouds.mysmsgateway.HomeActivity;
import com.cclouds.mysmsgateway.utils.Config;
import com.cclouds.mysmsgateway.utils.Contracts;
import com.cclouds.mysmsgateway.utils.SharedPreferenceSingleton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public final static String MY_ACTION = "MY_ACTION";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


        SharedPreferenceSingleton.getInstance().init(this);
        String token    =   SharedPreferenceSingleton.getInstance().getStringPreference(Contracts.TOKEN);

        //if(token == null)
            sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.


        try {

            JSONObject mObject  =   new JSONObject();
            mObject.put("serverKey",Config.API_KEY);
            mObject.put("senderId",Config.SENDER_ID);
            mObject.put("phoneToken",token);

            AndroidNetworking.post(Config.BASE_ENDPOINT).addJSONObjectBody(mObject)
                    .setTag("TOKEN_UPDATE")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("RESPONSE",""+response);
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("RESPONSE","FAILED");
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
