/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator.utils;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mark on 05/06/2015.
 */
public class ThorByteArrayOutputStream extends ByteArrayOutputStream{

    public ThorByteArrayOutputStream(int i) {
        super(i);
    }

    public byte[] getInternalByteArray()
    {
        return this.buf;
    }
}
