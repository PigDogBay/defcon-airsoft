/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;


public class MainActivity extends Activity {

    private MainModel mainModel;

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
        mainModel = new MainModel();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainModel.stop();
    }
}
