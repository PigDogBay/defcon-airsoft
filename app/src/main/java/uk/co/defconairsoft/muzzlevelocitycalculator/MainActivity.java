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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.Settings;
import uk.co.defconairsoft.muzzlevelocitycalculator.utils.ActivityUtils;


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
        if (mainModel==null) {
            mainModel = new MainModel();
        }
        WebView webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.defconairsoft.co.uk/AppBanner.html");
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
        else if (id == R.id.action_help){
            showWebPage(R.string.code_help_url);
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
        SettingsActivity.storeThreshold(this, mainModel.getThreshold());
    }
    public void showWebPage(int urlId)
    {
        try
        {
            ActivityUtils.ShowWebPage(this,this.getString(urlId));
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this,this.getString(R.string.help_web_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

}
