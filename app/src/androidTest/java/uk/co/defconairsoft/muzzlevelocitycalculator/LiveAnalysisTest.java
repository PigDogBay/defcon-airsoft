package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.os.Environment;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.IOException;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.LiveAnalysis;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.WavData;

/**
 * Created by Mark on 12/07/2015.
 */
public class LiveAnalysisTest extends InstrumentationTestCase {
    double duration = 0d;
    boolean isListenerCalled = false;
    private LiveAnalysis create()
    {
        LiveAnalysis liveAnalysis = new LiveAnalysis(4410,44100);
        liveAnalysis.setThreshold((short) 5000);
        liveAnalysis.setListener(new LiveAnalysis.IAnalysisListener() {
            @Override
            public void onPelletFired(double durationOfFlight) {
                LiveAnalysisTest.this.duration = durationOfFlight;
                isListenerCalled=true;
            }
        });
        return liveAnalysis;
    }

    /**
     * Do nothing
     * send quiet samples
     * @throws InterruptedException
     */
    public void test1() throws InterruptedException {
        LiveAnalysis liveAnalysis = create();
        short[] samples = new short[4410];
        isListenerCalled=false;
        for (int i=0;i<100;i++){
            liveAnalysis.update(samples);
        }
        Thread.sleep(50);
        assertFalse(isListenerCalled);
    }

    /**
     * Simulated data
     * send quiet samples
     * @throws InterruptedException
     */
    public void test2() throws InterruptedException {
        LiveAnalysis liveAnalysis = create();
        short[] emptySamples = new short[4410];
        short[] startSamples = new short[4410];
        short[] fullSamples = new short[4410];
        short[] pingSamples = new short[4410];
        for (int i=3000;i<startSamples.length;i=i+2){
            startSamples[i] = 10000;
            startSamples[i+1] = -10000;
        }
        for(int i=0;i<fullSamples.length;i=i+2){
            fullSamples[i]=5000;
            fullSamples[i+1]=-5000;
        }
        for(int i=1000;i<1200;i=i+2){
            pingSamples[i]=2000;
            pingSamples[i+1]=-2000;
        }
        isListenerCalled=false;
        liveAnalysis.update(startSamples);
        liveAnalysis.update(fullSamples);
        liveAnalysis.update(emptySamples);
        liveAnalysis.update(pingSamples);
        for (int i=0;i<10;i++){
            liveAnalysis.update(emptySamples);
        }
        double estimatedDuration = (1410D+4410D+4410D+1000D)/44100D;
        Thread.sleep(50);
        assertTrue(isListenerCalled);
        assertEquals(estimatedDuration,duration,0.01D);
    }
    /**
     * Real data
     * @throws InterruptedException
     */
    public void test3() throws InterruptedException, IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),"test.wav");
        WavData wavData = WavData.create(file);
        int[] rawData = wavData.getSamples();
        LiveAnalysis liveAnalysis = create();
        short[] samples = new short[4410];
        isListenerCalled=false;
        for (int i=0;i<(rawData.length-4410);i=i+4410){
            for (int j=0;j<samples.length;j++){
                samples[j]=(short)rawData[i+j];
            }
            liveAnalysis.update(samples);
        }
        Thread.sleep(50);
        assertTrue(isListenerCalled);
        assertEquals(4.51927d-4.35374d,duration,0.001D);
    }

}
