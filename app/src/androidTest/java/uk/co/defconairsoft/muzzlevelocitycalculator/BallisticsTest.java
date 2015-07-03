/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */
package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.test.InstrumentationTestCase;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.Ballistics;

/**
 * Created by Mark on 01/07/2015.
 */
public class BallisticsTest extends InstrumentationTestCase
{

    /**
     * Data based on Ballistics VS2013 project
     */
    public void testCalculateMuzzleVelocity1(){
        Ballistics target = new Ballistics();
        double actual = target.calculateMuzzleVelocity(1.072D, 1.228D);
        assertEquals(91.094D,actual,0.001D);

    }
}
