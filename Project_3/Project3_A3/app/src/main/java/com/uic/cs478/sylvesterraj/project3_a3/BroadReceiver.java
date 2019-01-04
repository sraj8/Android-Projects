package com.uic.cs478.sylvesterraj.project3_a3;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BroadReceiver extends BroadcastReceiver {

    public static final String SF_INTENT =
            "edu.uic.cs478.BroadcastReceiverPro3.sanFransisco";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SF_INTENT)){
            Intent sf = new Intent(context.getApplicationContext(), SanFranMainActivity.class);
            context.startActivity(sf);
        }
        else
            Toast.makeText(context, "New York information is under construction ",
                    Toast.LENGTH_LONG).show();
    }
}
