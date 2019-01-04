package com.uic.cs478.sylvesterraj.project3_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

class BroadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ReceiverActivity.SF_INTENT))  //user clicked on San Francisco button
                Toast.makeText(context, "San Francisco was selected by the broadcaster! ",
                        Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "New York was selected by the broadcaster! ",
                        Toast.LENGTH_LONG).show();
    }
}
