package com.uic.cs478.sylvesterraj.project3_a2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiverActivity extends AppCompatActivity {

    private Button mButton;


    public static final String PRO3_PERMISSION =
            "edu.uic.cs478.f18.project3" ;
    public static final String SF_INTENT =
            "edu.uic.cs478.BroadcastReceiverPro3.sanFransisco";
    public static final String NY_INTENT =
            "edu.uic.cs478.BroadcastReceiverPro3.newYork";

    BroadcastReceiver mSFReceiver = new BroadReceiver() ; //Broadcast receiver objects for SF
    IntentFilter mFilterSF = new IntentFilter(SF_INTENT) ;  //San Francisco Intent Filter
    BroadcastReceiver mNYReceiver = new BroadReceiver() ; //Broadcast receiver objects for NY
    IntentFilter mFilterNY = new IntentFilter(NY_INTENT) ;  //New York Intent Filter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        mButton = (Button) findViewById(R.id.click_button) ;

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermission();  //on click of button check permission
            }
        }) ;
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, PRO3_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            registerBroadReceiver();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{PRO3_PERMISSION}, 0) ;
        }

    }

    private void registerBroadReceiver() {
        mFilterSF.setPriority(100);
        //if permission granted register both receivers
        registerReceiver(mSFReceiver, mFilterSF);
        mFilterNY.setPriority(100);
        registerReceiver(mNYReceiver, mFilterNY);
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                registerBroadReceiver();
            }
            else {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
