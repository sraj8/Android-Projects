package com.uic.cs478.sylvesterraj.project3_a3;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReceiverActivity extends AppCompatActivity {

    public static final String PRO3_PERMISSION =
            "edu.uic.cs478.f18.project3" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        checkPermission(); // check whether user has granted permission
        ActionBar actionBar = getSupportActionBar();    //set title and icon to action bar
        actionBar.setTitle("PROJECT3 APP 3");
        actionBar.setLogo(R.drawable.ic_launcher_foreground);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, PRO3_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{PRO3_PERMISSION}, 0) ;
        }

    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
