package com.uic.cs478.sylvesterraj.project3_a1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1 ;
    private Button mButton2 ;
    private boolean permissonGranted = false ;  //denotes whether permission granted by user
    private static final String SF_INTENT =
            "edu.uic.cs478.BroadcastReceiverPro3.sanFransisco"; //San Francisco Intent Action string
    private static final String NY_INTENT =
            "edu.uic.cs478.BroadcastReceiverPro3.newYork";  //New York Intent Action string
    private static final String PRO3_PERMISSION =
            "edu.uic.cs478.f18.project3" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (Button) findViewById(R.id.sf_button) ;

        mButton1.setOnClickListener(this);

        mButton2 = (Button) findViewById(R.id.ny_button) ;

        mButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        checkPermission();
        switch (view.getId()) {
            case R.id.sf_button:
                if(permissonGranted){
                    Intent sfIntent = new Intent(SF_INTENT) ;   //action set to San Francisco
                    sendOrderedBroadcast(sfIntent, PRO3_PERMISSION) ;
                }
                break;

            case R.id.ny_button:
                if(permissonGranted){
                    Intent nyIntent = new Intent(NY_INTENT) ;   //action set to New York
                    sendOrderedBroadcast(nyIntent, PRO3_PERMISSION) ;
                }
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, PRO3_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            permissonGranted = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{PRO3_PERMISSION}, 0) ;
        }

    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                permissonGranted = true;
            }
            else {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
