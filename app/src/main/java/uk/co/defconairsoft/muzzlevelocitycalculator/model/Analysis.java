package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import java.util.List;

import uk.co.defconairsoft.muzzlevelocitycalculator.maths.DPoint;
import uk.co.defconairsoft.muzzlevelocitycalculator.maths.Differentiator;
import uk.co.defconairsoft.muzzlevelocitycalculator.maths.StationaryPoints;

/**
 * Created by Mark on 03/07/2015.
 */
public class Analysis
{
    private static final int CHUNK_LENGTH = 100;
    private double startTime = 0D, endTime=0D;

    public double getStartTime()
    {
        return startTime;
    }
    public double getEndTime()
    {
        return endTime;
    }

    public void analyze(WavData wavData)
    {
        int[] compressedData = compress(wavData.getSamples(), CHUNK_LENGTH);
        List<DPoint> points = DPoint.createList(compressedData);
        Differentiator diff = new Differentiator(points);
        List<DPoint> differentials = diff.Differentiate();
        DPoint max = DPoint.getMaxY(differentials);
        this.startTime = getTime(wavData, max);
        int endIndex = getFinalPeak(differentials);
        this.endTime=startTime;
        if (endIndex>0) {
            this.endTime = getTime(wavData, differentials.get(endIndex));
        }
    }

    private int getFinalPeak(List<DPoint> data)
    {
        StationaryPoints statPoints = new StationaryPoints(data);
        statPoints.setThreshold(0.05D);
        List<StationaryPoints.TurningPoint> turningPoints = statPoints.CalculateTurningPoints();
        for (int i = turningPoints.size() - 1; i > 0; i--)
        {
            if (turningPoints.get(i).isPeak())
            {
                return turningPoints.get(i).getIndex();
            }
        }
        return -1;

    }

    private double getTime(WavData wavData, DPoint point)
    {
        double timePerIndex = ((double)CHUNK_LENGTH) / ((double)wavData.getRate());
        return point.X * timePerIndex;
    }

    private int[] compress(int[] samples, int chunkLength)
    {
        int len = samples.length / chunkLength;
        int[] averaged = new int[len];
        for (int i = 0; i < len; i++)
        {
            int total = 0;
            int offset = i * chunkLength;
            for (int j = 0; j < chunkLength; j++)
            {
                int d = samples[offset + j];
                if (d < 0)
                {
                    d = -d;
                }
                total += d;
            }
            averaged[i] = total/chunkLength;
        }
        return averaged;
    }
}
