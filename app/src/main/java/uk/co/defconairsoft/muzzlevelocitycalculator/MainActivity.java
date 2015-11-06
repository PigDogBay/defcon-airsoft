/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.Settings;


public class MainActivity extends Activity {

    private MainModel mainModel=null;

    public MainModel getMainModel()
    {
        return mainModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
        ((ImageView)findViewById(R.id.imageView)).setOnClickListener(
                new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         bannerClicked();
                     }
                 });
        if (mainModel==null) {
            mainModel = new MainModel();
        }
    }
    public static void ShowWebPage(Activity currentActivity, String url)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        currentActivity.startActivity(intent);
    }
    private void bannerClicked() {
        try
        {
            ShowWebPage(this, "http://www.defconairsoft.co.uk");
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this, "no browser", Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Settings settings = SettingsActivity.getSettings(this);
        mainModel.applySettings(settings);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainModel.stop();
        SettingsActivity.storeThreshold(this,mainModel.getThreshold());
    }

}
