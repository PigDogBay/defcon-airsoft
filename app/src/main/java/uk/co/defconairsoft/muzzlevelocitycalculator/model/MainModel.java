package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import android.util.Log;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainModel
{
    private String results="";
    private AudioMonitor audioMonitor;
    private Ballistics ballistics;


    public String getResults() {
        return results;
    }
    public Ballistics getBallistics(){
        return ballistics;
    }

    public void setListener(LiveAnalysis.IAnalysisListener listener){
        audioMonitor.getLiveAnalysis().setListener(listener);
    }


    public MainModel(){
        audioMonitor = new AudioMonitor();
        ballistics = new Ballistics();
    }

    public void start(){
        audioMonitor.start();

    }

    public void stop() {
        try {
            audioMonitor.stop();
            audioMonitor.release();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
