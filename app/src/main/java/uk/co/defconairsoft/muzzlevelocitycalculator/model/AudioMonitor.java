package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Mark on 04/07/2015.
 */
public class AudioMonitor implements AudioRecord.OnRecordPositionUpdateListener {
    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLES_PER_FRAME = SAMPLE_RATE/10;
    private static final int BYTES_PER_SAMPLE = 2;

    private AudioRecord audioRecord = null;
    private LiveAnalysis liveAnalysis;
    private short[] buffer;


    public LiveAnalysis getLiveAnalysis() {
        return liveAnalysis;
    }



    public AudioMonitor()
    {
        buffer = new short[SAMPLES_PER_FRAME];
        liveAnalysis = new LiveAnalysis(SAMPLES_PER_FRAME,SAMPLE_RATE);
    }

    @Override
    public void onMarkerReached(AudioRecord recorder) {

    }

    @Override
    public void onPeriodicNotification(AudioRecord recorder) {
        recorder.read(buffer, 0, buffer.length);
        liveAnalysis.update(buffer);
    }

    public void start(){
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                SAMPLES_PER_FRAME*BYTES_PER_SAMPLE*4);
        audioRecord.setRecordPositionUpdateListener(this);
        audioRecord.setPositionNotificationPeriod(SAMPLES_PER_FRAME);
        audioRecord.startRecording();
    }
    public void stop(){
        audioRecord.setRecordPositionUpdateListener(null);
        if (AudioRecord.STATE_UNINITIALIZED!=audioRecord.getState()) {
            audioRecord.stop();
        }
        audioRecord.release();
        audioRecord=null;
    }
}
