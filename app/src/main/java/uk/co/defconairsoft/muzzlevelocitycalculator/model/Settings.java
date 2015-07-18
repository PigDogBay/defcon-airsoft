package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import android.util.Log;

/**
 * Created by Mark on 18/07/2015.
 */
public class Settings {
    public double targetDistance;
    public double barrelLength;
    public double correction;
    public double pelletMass;
    public double pelletDiameter;
    public int threshold;

    public void log(String tag){
        Log.v(tag, "Diameter " + Double.toString(pelletDiameter));
        Log.v(tag,"Mass "+Double.toString(pelletMass));
        Log.v(tag,"Distance "+Double.toString(targetDistance));
        Log.v(tag,"Barrel "+Double.toString(barrelLength));
        Log.v(tag,"Correction "+Double.toString(correction));
        Log.v(tag,"Threshold "+Integer.toString(threshold));
    }
}
