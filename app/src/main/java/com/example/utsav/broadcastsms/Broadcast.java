package com.example.utsav.broadcastsms;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Broadcast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 333);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                String message = "";
                if(bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    for(int i=0; i<msgs.length;i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        message = msgs[i].getMessageBody();
                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, intentFilter);
    }
}
