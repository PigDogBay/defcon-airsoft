/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.WavData;

/**
 * Created by Mark on 12/06/2015.
 */
public class FileUtils
{
    public static String CopyAssetToFile(Context context, String assetName, String path)
    {
        File f = new File(path);
        return CopyAssetToFile(context,assetName,f);
    }
    public static String CopyAssetToFile(Context context, String assetName, File destFile)
    {
        if (!destFile.exists()) try {

            InputStream is = context.getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) { throw new RuntimeException(e); }
        return destFile.getPath();
    }
    public static String CopyAssetToFile(Context context, String assetName, String destName, String publicDir)
    {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(publicDir),
                destName);
        return CopyAssetToFile(context, assetName, file);

    }

    public static WavData openWavAsset(Context context, String assetName, String destFile)
    {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                destFile);
        FileUtils.CopyAssetToFile(context, assetName, file);

        WavData wavData = new WavData();
        try {
            wavData.load(file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wavData;
    }
}
