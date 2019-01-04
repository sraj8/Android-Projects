package com.uic.cs478.sylvesterraj.project3_cars;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    protected static final String EXTRA_RES_ID = "POS";
    protected static final String EXTRA_URL ="URL";
    protected static final String EXTRA_DEALER ="DEALER";

    //Image ids of the original resolution images stored in List
    private ArrayList<Integer> imgIdCars = new ArrayList<>(
            Arrays.asList(R.drawable.image1, R.drawable.image2,
                    R.drawable.image3, R.drawable.image4, R.drawable.image5,
                    R.drawable.image6, R.drawable.image7, R.drawable.image8,
                    R.drawable.image9, R.drawable.image10, R.drawable.image11,
                    R.drawable.image12));

    //Ids of the thumbnails stored in List
    private ArrayList<Integer> thumbIdsCars = new ArrayList<>(
            Arrays.asList(R.drawable.image1_tn, R.drawable.image2_tn,
                    R.drawable.image3_tn, R.drawable.image4_tn, R.drawable.image5_tn,
                    R.drawable.image6_tn, R.drawable.image7_tn, R.drawable.image8_tn,
                    R.drawable.image9_tn, R.drawable.image10_tn, R.drawable.image11_tn,
                    R.drawable.image12_tn));

    //Ids of array with dealers info stored in List
    private ArrayList<Integer> dealers = new ArrayList<>(
            Arrays.asList(R.array.merc_dealers, R.array.lexus_dealers,
                    R.array.mustang_dealers, R.array.isuzu_dealers, R.array.mazda_dealers,
                    R.array.toyota_dealers, R.array.renault_dealers, R.array.honda_dealers,
                    R.array.ford_dealers, R.array.jaguar_dealers, R.array.audi_dealers,
                    R.array.bmw_dealers));

    //Car names stored in list
    private ArrayList<Integer> carNames = new ArrayList<>(
            Arrays.asList(R.string.merc_cls, R.string.lexus, R.string.mustang, R.string.isuzu,
                    R.string.mazda, R.string.toyota, R.string.renault, R.string.honda,
                    R.string.ford, R.string.jaguar, R.string.audi, R.string.bmw));

    //Urls stored in list
    private ArrayList<String> urls = new ArrayList<>(Arrays.asList(
            "https://www.mercedes-benz.com/","https://www.lexus.com/","http://www.ford.com",
            "http://www.isuzu.com/", "https://www.mazdausa.com/", "https://www.toyota.com/",
            "https://www.renault.co.in", "http://www.honda.com", "http://www.ford.com",
            "https://www.jaguarusa.com","http://www.audi.com", "http://www.bmw.com"
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.grid);
        registerForContextMenu(gridview);

        // Create a new GridViewAdapter and set it as the Adapter for this GridView
        gridview.setAdapter(new GridViewAdapter(this, thumbIdsCars, imgIdCars, carNames, urls, dealers));

        // Set an setOnItemClickListener on the GridView
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Create an Intent to start the ImageViewActivity
                Intent intent = new Intent(MainActivity.this,
                        ImageViewActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                intent.putExtra(EXTRA_RES_ID, (int) id);
                intent.putExtra(EXTRA_URL, urls.get(position));


                // Start the ImageViewActivity
                startActivity(intent);
            }
        });

    }

    //
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    //On long press displays context menu with three options
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.exp_pic:
                //Expand the picture to show original full resolution image
                Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
                intent.putExtra(EXTRA_RES_ID, (int)info.id);
                intent.putExtra(EXTRA_URL, urls.get(info.position));
                startActivity(intent);
                return true;
            case R.id.browse:
                //Show official website of the car selected
                Intent browse=new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(info.position)));
                browse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                browse.setPackage("com.android.chrome");
                try {
                    getApplicationContext().startActivity(browse);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    browse.setPackage(null);
                    getApplicationContext().startActivity(browse);
                }
                return true;
            case R.id.dealer:
                //Show dealers and addresses
                Intent showDealers = new Intent(MainActivity.this, ShowDealer.class);
                showDealers.putExtra(EXTRA_DEALER, dealers.get(info.position));
                startActivity(showDealers);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
