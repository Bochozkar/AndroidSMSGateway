package com.cclouds.mysmsgateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cclouds.mysmsgateway.firebase.FirebaseInstanceIDService;

public class HomeActivity extends AppCompatActivity {


    //MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);






    }



    @Override
    protected void onStart() {

//        myReceiver = new MyReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(FirebaseInstanceIDService.MY_ACTION);
//        registerReceiver(myReceiver, intentFilter);
//
//        Intent intent = new Intent(HomeActivity.this,
//                FirebaseInstanceIDService.class);
//        startService(intent);
        super.onStart();


    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //unregisterReceiver(myReceiver);
        super.onStop();
    }

//    private class MyReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
//            // TODO Auto-generated method stub
//            Log.e("SERVICE","RECAHED");
//            String datapassed = arg1.getStringExtra("DATAPASSED");
//
//            Toast.makeText(HomeActivity.this,
//                    "Triggered by Service!\n"
//                            + "Data passed: " + datapassed,
//                    Toast.LENGTH_LONG).show();
//
//        }
//
//    }
}
