package com.uic.cs478.sylvesterraj.fedmoneyclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uic.cs478.sylvesterraj.fedmoneycommon.FedMoney;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner optionSpinner, yearSpinner;
    private boolean isBound = false;
    private TextView enterYear;
    private Button getData, unbind;
    private EditText adate, amonth, anumber;
    private FedMoney mFedMoney;
    public static final String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing
        optionSpinner = (Spinner) findViewById(R.id.option_spinner);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        enterYear = (TextView) findViewById(R.id.year) ;
        adate = (EditText) findViewById(R.id.aday);
        amonth = (EditText) findViewById(R.id.amonth);
        anumber = (EditText) findViewById(R.id.anumber);
        getData = (Button) findViewById(R.id.get_data);
        unbind = (Button) findViewById(R.id.unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindService();
            }
        });
        optionSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_options,getResources().getStringArray(R.array.options)));
        yearSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_options,getResources().getStringArray(R.array.years)));
        optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //based on selection enable particular fields
                switch (position){
                    case 0: adate.setVisibility(View.GONE);
                    amonth.setVisibility(View.GONE);
                    anumber.setVisibility(View.GONE);
                    yearSpinner.setVisibility(View.GONE);
                    enterYear.setVisibility(View.GONE);
                    getData.setVisibility(View.GONE);
                    break;

                    case 1: adate.setVisibility(View.GONE);
                        amonth.setVisibility(View.GONE);
                        anumber.setVisibility(View.GONE);
                        yearSpinner.setSelection(0);
                        yearSpinner.setVisibility(View.VISIBLE);
                        enterYear.setVisibility(View.VISIBLE);
                        getData.setVisibility(View.VISIBLE);
                        break;

                    case 2: adate.setVisibility(View.VISIBLE);
                        amonth.setVisibility(View.VISIBLE);
                        anumber.setVisibility(View.VISIBLE);
                        yearSpinner.setSelection(0);
                        yearSpinner.setVisibility(View.VISIBLE);
                        enterYear.setVisibility(View.VISIBLE);
                        getData.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnClick on getData button check selection and call appropriate api
                int selection = optionSpinner.getSelectedItemPosition();
                Log.i("selection", selection+"");
                if(!isBound) bindToFedService();
                switch(selection){
                    case 1: if(isBound){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                                List<Integer>  monthlyData = null;
                                try {
                                    monthlyData = mFedMoney.monthlyAvgCash(year);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                //Format the result arraylist into string
                                final String formattedData = formatResult(monthlyData);
                                //start a new activity that displays the result
                                Intent intent = new Intent(MainActivity.this, FedClientDisplay.class);
                                intent.putExtra(RESULT, formattedData);
                                startActivity(intent);
                            }
                        }).start();
                    }
                    break;

                    case 2: if(isBound){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                                int month = Integer.parseInt(amonth.getText().toString());
                                int date = Integer.parseInt(adate.getText().toString());
                                int workingDate = Integer.parseInt(anumber.getText().toString());
                                List<Integer>  dailyCash = null;
                                try {
                                    dailyCash = mFedMoney.dailyCash(year, month, date, workingDate);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                //Format the result arraylist into string
                                final String formattedData = formatResult(dailyCash);
                                //start a new activity that displays the result
                                Intent intent = new Intent(MainActivity.this, FedClientDisplay.class);
                                intent.putExtra(RESULT, formattedData);
                                startActivity(intent);
                            }
                        }).start();
                    }
                    break;
                }
            }
        });
    }

    private final ServiceConnection mServiceConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mFedMoney = FedMoney.Stub.asInterface(service);
            isBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {
            mFedMoney = null;
            isBound = false;

        }
    };

    private void bindToFedService() {
        if(!isBound){
            //if not bound, bind to FedTreasuryService
            Intent intent = new Intent(FedMoney.class.getName());
            ResolveInfo info = getPackageManager().resolveService(intent, PackageManager.MATCH_ALL);
            intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            boolean flag = bindService(intent, this.mServiceConn, Context.BIND_AUTO_CREATE);

            if(flag){
                Log.i("FedClient","Service successfully bound");
                isBound = true;
            }
            else{
                Log.i("FedClient","Service not bound");
            }
        }
    }

    private void unBindService() {
        //Unbind from FedTreasuryService
        if(isBound){
            unbindService(MainActivity.this.mServiceConn);
            Toast.makeText(this, "Unbind successful", Toast.LENGTH_LONG).show();
            isBound = false;
        }else{
            Toast.makeText(this,"Service already unbounded",Toast.LENGTH_LONG).show();
        }
    }

    private String formatResult(List<Integer> res) {
        StringBuffer sb = new StringBuffer();
        for(Integer i : res){
            sb.append(i+"\n");
        }
        return sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isBound){
            bindToFedService();
        }
    }

    @Override
    protected void onDestroy() {
        //added to prevent ServiceConnectionLeaked
        super.onDestroy();
        if(isBound)
            unBindService();
    }
}
