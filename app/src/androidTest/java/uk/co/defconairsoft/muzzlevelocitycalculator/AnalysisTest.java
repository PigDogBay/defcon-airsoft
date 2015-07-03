package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.os.Environment;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.IOException;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.Analysis;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.WavData;

/**
 * Created by Mark on 03/07/2015.
 */
public class AnalysisTest extends InstrumentationTestCase
{
    public void testAnalyze1() throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),"test.wav");
        WavData wavData = WavData.create(file);
        Analysis target = new Analysis();
        target.analyze(wavData);

        assertEquals(4.35374d,target.getStartTime(),0.00001d);
        assertEquals(4.51927d,target.getEndTime(),0.00001d);
    }
}
