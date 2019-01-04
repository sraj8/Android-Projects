package com.uic.cs478.sylvesterraj.project3_cars;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent used to start this Activity
        final Intent intent = getIntent();

        // Make a new ImageView
        ImageView imageView = new ImageView(getApplicationContext());

        // Get the ID of the image to display and set it as the image for this ImageView
        imageView.setImageResource(intent.getIntExtra(MainActivity.EXTRA_RES_ID, 0));

        // Display official website when clicken anywhere on the image
        imageView.setOnClickListener((view)->{
            Intent browse=new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra(MainActivity.EXTRA_URL)));
            browse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browse.setPackage("com.android.chrome");
            try {
                getApplicationContext().startActivity(browse);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                browse.setPackage(null);
                getApplicationContext().startActivity(browse);
            }
        });

        setContentView(imageView);
    }

}
