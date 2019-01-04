package com.uic.cs478.sylvesterraj.project3_a3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SanFranMainActivity extends AppCompatActivity implements PlacesFragment.ListSelectionListener{

    private final WebDisplayFragment mWebDisplayFragment = new WebDisplayFragment();
    public static String[] mPlaces;  //Array for storing the place names.
    public static String[] mPlacesURL;//Array for storing the urls for all the places.
    private FragmentManager mFragmentManager;
    private FrameLayout mPlacesFrameLayout, mDisplayFrameLayout; //Getting the frame layouts
    PlacesFragment mPF;
    WebDisplayFragment mWF;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String PLACES_FRAGMENT_TAG ="PF";
    private static final String WEBDISPLAY_FRAGMENT_TAG ="WF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_fran_main);
        ActionBar actionBar = getSupportActionBar();    //set title and icon to action bar
        actionBar.setTitle("PROJECT3 APP 3");
        actionBar.setLogo(R.drawable.ic_launcher_foreground);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mPlaces = getResources().getStringArray(R.array.Places);
        mPlacesURL = getResources().getStringArray(R.array.PlacesURL);

        // Get references to the PlacesFragment and to the WebDisplayFragment
        mPlacesFrameLayout = (FrameLayout) findViewById(R.id.fragment_main);
        mDisplayFrameLayout = (FrameLayout) findViewById(R.id.fragment_display);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        //Get the retained fragments:
        mPF = (PlacesFragment) mFragmentManager.findFragmentByTag(PLACES_FRAGMENT_TAG);
        mWF = (WebDisplayFragment) mFragmentManager.findFragmentByTag(WEBDISPLAY_FRAGMENT_TAG);

        if (mPF == null) {
            fragmentTransaction.replace(R.id.fragment_main,
                    new PlacesFragment(), PLACES_FRAGMENT_TAG);
            // Commit the FragmentTransaction
            fragmentTransaction.commit();
        }
        else {

            fragmentTransaction.replace(R.id.fragment_main,
                    mPF, PLACES_FRAGMENT_TAG);
            if (mWF != null) {
                fragmentTransaction.replace(R.id.fragment_display,
                        mWF, WEBDISPLAY_FRAGMENT_TAG);
            } else {
                mWF = new WebDisplayFragment();

                fragmentTransaction.replace(R.id.fragment_display,
                        mWF, WEBDISPLAY_FRAGMENT_TAG);
            }
            // Commit the FragmentTransaction
            fragmentTransaction.commit();
        }
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {

            if (!mWebDisplayFragment.isAdded()) {

                // Make the PlacesFragment occupy the entire layout
                mPlacesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mDisplayFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            }
            else  {
                // Make the WebDisplayFragment occupy the entire layout
                mPlacesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));


                mDisplayFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        }
        else
        {
            if (!mWebDisplayFragment.isAdded()) {
                // Make the PlacesFragment occupy the entire layout
                mPlacesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mDisplayFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                // Make the PlacesFragment take 1/3 of the layout's width
                mPlacesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the WebDisplayFragment take 2/3's of the layout's width
                mDisplayFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }

        }
    }

    // Called when the user selects an item in the PlacesFragment
    @Override
    public void onListSelection(int index) {

        // If the WebDisplayFragment has not been added, add it now
        if (!mWebDisplayFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the WebDisplayFragment to the layout
            fragmentTransaction.replace(R.id.fragment_display,
                    mWebDisplayFragment,WEBDISPLAY_FRAGMENT_TAG);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mWebDisplayFragment.getShownIndex() != index) {

            // Tell the WebDisplayFragment to show the url string at position index
            mWebDisplayFragment.showURLAtIndex(index);

        }
    }

}
