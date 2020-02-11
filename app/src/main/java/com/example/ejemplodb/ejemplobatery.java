package com.example.ejemplodb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ejemplobatery extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {

            Toast.makeText(context, "Battery low!!", Toast.LENGTH_LONG).show();
            Log.d("test","Entra a onReceive desde ejemplos batery" + intent.getAction());
        }

    }
}
