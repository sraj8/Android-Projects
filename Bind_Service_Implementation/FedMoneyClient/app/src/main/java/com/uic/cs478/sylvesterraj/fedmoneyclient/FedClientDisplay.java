package com.uic.cs478.sylvesterraj.fedmoneyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FedClientDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fed_client_display);

        //Initialize list view to be displayed
        ListView resultList = (ListView) findViewById(R.id.display);

        final Intent intent = getIntent();
        String results = intent.getStringExtra(MainActivity.RESULT);
        Log.i("res", results);
        List<String>displayList = new ArrayList(Arrays.asList(results.split("\n")));

        //Display list view with results
        resultList.setAdapter(new ArrayAdapter<>(this,R.layout.display_results,displayList));
    }
}



