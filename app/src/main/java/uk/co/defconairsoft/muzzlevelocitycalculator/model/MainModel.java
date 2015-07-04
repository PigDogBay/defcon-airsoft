package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import java.util.Date;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainModel
{
    private boolean isRecording=false;
    private String results="";
    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean isRecording) {
        this.isRecording = isRecording;
    }

    public String getResults() {
        return results;
    }

    public void toggleRecord(){
        isRecording=!isRecording;
        if (!isRecording)
        {
            Date date = new Date();
            results = results+date.toString()+"\n";
        }
    }

    public void playBack() {

    }

    public void stop() {

    }
}
