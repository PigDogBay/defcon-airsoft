package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.os.Environment;
import android.test.InstrumentationTestCase;

import java.io.IOException;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.WavData;
import uk.co.defconairsoft.muzzlevelocitycalculator.utils.FileUtils;

/**
 * Created by Mark on 01/07/2015.
 */
public class WavDataTest extends InstrumentationTestCase
{
    public void xtestLoad1() throws IOException {
        String filename = FileUtils.CopyAssetToFile(getInstrumentation().getContext(),"test.wav","test.wav", Environment.DIRECTORY_MUSIC);
        WavData target = new WavData();
        target.load(filename);
        assertTrue(target.isValid());
        assertEquals(643568,target.getDataSize());
        target.log("test");
    }
}
