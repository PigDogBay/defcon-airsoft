package uk.co.defconairsoft.muzzlevelocitycalculator.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Structure to hold a X,Y data point
 */
public class DPoint
{
	public final double X;
	public final double Y;

	public DPoint(double X, double Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	@Override
	public String toString()
	{
		return String.format("(%f,%f)", X,Y);
	}

    public static List<DPoint> createList(int[] data)
    {
        ArrayList<DPoint> list = new ArrayList<>(data.length);
        for (int i = 0; i < data.length; i++)
        {
            list.add(new DPoint((double)i,(double)data[i]));
        }
        return list;
    }

    public static void sortByX(List<DPoint> points){
        Collections.sort(points, new Comparator<DPoint>() {
            @Override
            public int compare(DPoint lhs, DPoint rhs) {
                return Double.compare(lhs.X, rhs.X);
            }
        });
    }

    public static DPoint getMinY(List<DPoint> points){
        DPoint min = points.get(0);
        for (DPoint point : points)
        {
            if (point.Y < min.Y){
                min = point;
            }
        }
        return min;
    }
    public static DPoint getMaxY(List<DPoint> points){
        DPoint max = points.get(0);
        for (DPoint point : points)
        {
            if (point.Y > max.Y){
                max = point;
            }
        }
        return max;
    }
}
