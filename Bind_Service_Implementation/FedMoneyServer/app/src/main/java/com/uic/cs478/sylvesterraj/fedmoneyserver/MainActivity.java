package com.uic.cs478.sylvesterraj.fedmoneyserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView serviceStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView displays the status of service
        serviceStatus = findViewById(R.id.service_status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (FedTreasuryService.serviceStatus){
            case 0: serviceStatus.setText("not yet started");
                    startService(new Intent(this,FedTreasuryService.class));
            case 1:serviceStatus.setText("started, but not bound to a client"); break;
            case 2:serviceStatus.setText("started and bound to one or more clients"); break;
            case 3:serviceStatus.setText("bound but not started"); break;
            case 4:serviceStatus.setText("destroyed"); break;
        }
    }
}
