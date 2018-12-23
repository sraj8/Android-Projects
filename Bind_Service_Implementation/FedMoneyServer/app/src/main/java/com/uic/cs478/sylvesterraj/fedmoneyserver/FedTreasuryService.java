package com.uic.cs478.sylvesterraj.fedmoneyserver;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.uic.cs478.sylvesterraj.fedmoneycommon.FedMoney;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FedTreasuryService extends Service {

    public static int serviceStatus = 0;
    public final String URL = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=";
    private static List<Integer> monthlyAvg, dailyCash;
    private HttpURLConnection httpUrlConnection = null;


    private final FedMoney.Stub mBinder = new FedMoney.Stub() {
        @Override
        public List monthlyAvgCash(final int aYear) {
            //started and bound to one or more clients
            FedTreasuryService.serviceStatus = 2;
            FedTreasuryService.monthlyAvg = new ArrayList<>();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String query;
                    try {
                        //query as per - https://github.com/csvsoundsystem/federal-treasury-api/blob/master/documentation/treasuryio_data_dictionary.pdf?raw=true
                        query = URLEncoder.encode(" select distinct open_mo as monthly_avg from t1 where is_total = 1 and year = " + aYear, "utf-8");
                        String url = URL + query;
                        Log.i("URL", url);
                        httpUrlConnection = (HttpURLConnection) new URL(url)
                                .openConnection();
                    }
                    catch (UnsupportedEncodingException e) {
                        Log.e("Unsupported", "UnsupportedEncodingException");
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        Log.e("IOE", "IOException");
                        e.printStackTrace();
                    }
                    finally {
                        if (null != httpUrlConnection)
                            httpUrlConnection.disconnect();
                    }
                }
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Log.e("InterruptedException", "InterruptedException");
                e.printStackTrace();
            }
            // Acquire lock to ensure exclusive access to httpUrlConnection
            synchronized (httpUrlConnection) {
                InputStream in = null;
                try {
                    in = new BufferedInputStream(
                            httpUrlConnection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String response = readStreamData(in);
                monthlyAvg = getFormattedData(response, "monthly_avg");
            }
            return monthlyAvg;
        }

        @Override
        public List dailyCash(final int aYear, final int aMonth, final int aDay, final int aNumber) {
            //started and bound to one or more clients
            serviceStatus = 2;
            dailyCash = new ArrayList<>();
            String response;
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //query as per - https://github.com/csvsoundsystem/federal-treasury-api/blob/master/documentation/treasuryio_data_dictionary.pdf?raw=true
                        String url = URL + "select open_today as daily_cash from t1 where is_total = 1 and date >= '" + aYear+ "-" +aMonth+ "-" +aDay + "' order by date limit "+ aNumber;
                        Log.i("URL", url);
                        httpUrlConnection = (HttpURLConnection) new URL(url)
                                .openConnection();
                    }
                    catch (UnsupportedEncodingException e) {
                        Log.e("Unsupported", "UnsupportedEncodingException");
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        Log.e("IOE", "IOException");
                        e.printStackTrace();
                    }
                    finally {
                        if (null != httpUrlConnection)
                            httpUrlConnection.disconnect();
                    }
                }
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Log.e("InterruptedException", "InterruptedException");
                e.printStackTrace();
            }
            // Acquire lock to ensure exclusive access to httpUrlConnection
            synchronized (httpUrlConnection) {
                InputStream in = null;
                try {
                    in = new BufferedInputStream(
                            httpUrlConnection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response = readStreamData(in);
                dailyCash = getFormattedData(response, "daily_cash");
            }
            Log.i("year, month, day", "year: "+ aYear + " month: "+ aMonth + " day: " + aDay + " working: "+ aNumber);
            Log.i("daily_cash", response);
            return dailyCash;
        }
    };

    private String readStreamData(InputStream in) {
        BufferedReader reader = null;
        // StringBuffer is a thread-safe String that can also be changed
        StringBuffer data = new StringBuffer("");
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            Log.e("IOE", "IOException");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    private List<Integer> getFormattedData(String response, String column) {
        List<Integer> responseList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0;i < jsonArray.length();i++) {
                responseList.add(jsonArray.getJSONObject(i).getInt(column));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseList;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //bound but not started
        serviceStatus = 3;
        return this.mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //started, but not bound to a client
        serviceStatus = 1;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //destroyed
        serviceStatus = 4;
        super.onDestroy();
    }


}
