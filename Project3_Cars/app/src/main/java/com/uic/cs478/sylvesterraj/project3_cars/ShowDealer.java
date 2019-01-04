package com.uic.cs478.sylvesterraj.project3_cars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowDealer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dealer);
        //Initialize list view to be displayed
        ListView dealerList = (ListView) findViewById(R.id.dealers);

        final Intent intent = getIntent();

        //Display list view with dealer details of a particular car selected by the user
        dealerList.setAdapter(new ArrayAdapter<>(this,R.layout.dealer_details,getResources().getStringArray(intent.getIntExtra(MainActivity.EXTRA_DEALER,0))));
    }
}
