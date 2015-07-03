/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import uk.co.defconairsoft.muzzlevelocitycalculator.utils.WindowsBinaryFileIO;

/**
 * Created by Mark on 01/07/2015.
 */

public class WavData
{
    public String getRIFF() {
        return RIFF;
    }

    public void setRIFF(String RIFF) {
        this.RIFF = RIFF;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getWAVE() {
        return WAVE;
    }

    public void setWAVE(String WAVE) {
        this.WAVE = WAVE;
    }

    public String getFmt() {
        return fmt;
    }

    public void setFmt(String fmt) {
        this.fmt = fmt;
    }

    public int getFormatSize() {
        return formatSize;
    }

    public void setFormatSize(int formatSize) {
        this.formatSize = formatSize;
    }

    public int getFormatType() {
        return formatType;
    }

    public void setFormatType(int formatType) {
        this.formatType = formatType;
    }

    public int getNumberOfChannels() {
        return numberOfChannels;
    }

    public void setNumberOfChannels(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCalc1() {
        return calc1;
    }

    public void setCalc1(int calc1) {
        this.calc1 = calc1;
    }

    public int getCalc2() {
        return calc2;
    }

    public void setCalc2(int calc2) {
        this.calc2 = calc2;
    }

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int[] getSamples() {
        return samples;
    }

    public void setSamples(int[] samples) {
        this.samples = samples;
    }

    private String RIFF="";
    private int fileSize;
    private String WAVE="";
    private String fmt="";
    private int formatSize;
    private int formatType;
    private int numberOfChannels;
    private int rate;
    private int calc1;
    private int calc2;
    private int bitsPerSample;
    private String data;
    private int dataSize;
    private int[] samples;


    public WavData(){

    }

    public boolean isValid()
    {
        return "RIFF".equals(RIFF) && "WAVE".equals(WAVE) && fmt!=null && fmt.startsWith("fmt");
    }

    public void load(String filename) throws IOException {
        WindowsBinaryFileIO binaryReader = new WindowsBinaryFileIO(filename);
        this.RIFF = new String(binaryReader.ReadChars(4));
        this.fileSize = binaryReader.ReadInt32();
        this.WAVE = new String(binaryReader.ReadChars(4));
        this.fmt = new String(binaryReader.ReadChars(4));
        this.formatSize = binaryReader.ReadInt32();
        this.formatType = (int)binaryReader.ReadInt16();
        this.numberOfChannels = (int)binaryReader.ReadInt16();
        this.rate = binaryReader.ReadInt32();
        this.calc1 = binaryReader.ReadInt32();
        this.calc2 = (int)binaryReader.ReadInt16();
        this.bitsPerSample = (int)binaryReader.ReadInt16();
        this.data = new String(binaryReader.ReadChars(4));
        this.dataSize = binaryReader.ReadInt32();
        int sampleCount = this.dataSize / 2;
        this.samples = new int[sampleCount];
        for (int i = 0; i < sampleCount; i++)
        {
            this.samples[i] = (int)binaryReader.ReadInt16();
        }
        binaryReader.Close();
    }

    public void log(String tag){
        Log.i(tag, RIFF);
        Log.i(tag, String.format("File size = %d",this.fileSize));
        Log.i(tag, WAVE);
        Log.i(tag, fmt);
        Log.i(tag, String.format("Fmt size = %d", this.formatSize));
        Log.i(tag, String.format("Fmt type = %d", this.formatType));
        Log.i(tag, String.format("Fmt channels = %d", this.numberOfChannels));
        Log.i(tag, String.format("Rate = %d", this.rate));
        Log.i(tag, String.format("Calc1 = %d", this.calc1));
        Log.i(tag, String.format("Calc2 = %d", this.calc2));
        Log.i(tag, String.format("Bits per sample = %d", this.bitsPerSample));
        Log.i(tag, this.data);
        Log.i(tag, String.format("File size = %d", this.dataSize));
    }

    public static WavData create(File file) throws IOException {
        WavData wavData = new WavData();
        wavData.load(file.getPath());
        return wavData;
    }
}
