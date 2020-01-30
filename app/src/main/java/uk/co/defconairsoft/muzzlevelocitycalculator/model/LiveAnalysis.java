package uk.co.defconairsoft.muzzlevelocitycalculator.model;

/*
 * Created by Mark on 12/07/2015.
 */
public class LiveAnalysis
{
    public interface IAnalysisListener{
        void onPelletFired(double durationOfFlight);
    }

    private enum State{
        Listening,
        Recording,
        Processing
    }

    private static final int NUMBER_OF_SAMPLES_TO_RECORD = 10;
    private short threshold = 5000;
    private IAnalysisListener listener;
    private State state;
    private short[] recordedSampleData;
    private int sampleRate;
    private int sampleCount;

    public IAnalysisListener getListener() {
        return listener;
    }

    public void setListener(IAnalysisListener listener) {
        this.listener = listener;
    }

    synchronized public short getThreshold() {
        return threshold;
    }

    synchronized public void setThreshold(short threshold) {
        this.threshold = threshold;
    }
    public LiveAnalysis(int sampleSize, int sampleRate){
        this.sampleRate = sampleRate;
        recordedSampleData = new short[sampleSize*NUMBER_OF_SAMPLES_TO_RECORD];
        state = State.Listening;
    }

    public void update(short[] samples){
        switch(state){
            case Listening:
                listenState(samples);
                break;
            case Recording:
                recordingState(samples);
                break;
            case Processing:
                //do nothing, runs on a worker thread
                break;
        }
    }

    private void listenState(short[] samples){
        if (hasCrossedThreshold(samples)) {
            state = State.Recording;
            sampleCount = 0;
            recordingState(samples);
        }
    }
    private void recordingState(short[] samples) {
        int position = samples.length*sampleCount;
        System.arraycopy(samples,0,recordedSampleData,position,samples.length);
        sampleCount++;
        if (sampleCount==NUMBER_OF_SAMPLES_TO_RECORD){
            startProcessingThread();
        }

    }
    private void startProcessingThread()
    {
        state = State.Processing;
        Thread t = new Thread(() -> {
            LiveAnalysis.this.onPelletFiredEvent();
            state = State.Listening;
        });
        t.start();
    }

    private boolean hasCrossedThreshold(short[] samples){
        int count=0;
        int thresholdLocal = getThreshold();
        //start halfway in,
        for (int i = samples.length/2; i< samples.length; i++)
        {
            short s = samples[i];
            if (s>thresholdLocal){
                count++;
                if (count>3)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void onPelletFiredEvent(){
        if (getListener()!=null){

            WavData wavData = new WavData(recordedSampleData, sampleRate);
            Analysis analysis = new Analysis();
            analysis.analyze(wavData);
            double duration = analysis.getEndTime()-analysis.getStartTime();
            listener.onPelletFired(duration);
        }
    }


}
