package uk.co.defconairsoft.muzzlevelocitycalculator.maths;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mark on 03/07/2015.
 */
public class DPointTest extends InstrumentationTestCase
{
    public void testCreateList1()
    {
        List<DPoint> target = DPoint.createList(new int[]{42,9,8,40});
        assertEquals(4,target.size());
        assertEquals(0D,target.get(0).X);
        assertEquals(2D,target.get(2).X);
        assertEquals(8D,target.get(2).Y);
    }

    public void testSortByX1()
    {
        DPoint dpoint1 = new DPoint(42D,5D);
        DPoint dpoint2 = new DPoint(37D,9D);
        DPoint dpoint3 = new DPoint(38D,10D);

        List<DPoint> list = new ArrayList<>();
        list.add(dpoint1);
        list.add(dpoint2);
        list.add(dpoint3);
        DPoint.sortByX(list);
        assertEquals(dpoint2,list.get(0));
        assertEquals(dpoint3,list.get(1));
        assertEquals(dpoint1,list.get(2));

    }

    public void testGetMinY()
    {
        List<DPoint> target = DPoint.createList(new int[]{42,9,8,40});
        DPoint actual = DPoint.getMinY(target);
        assertEquals(8D,actual.Y);
    }
    public void testGetMaxY()
    {
        List<DPoint> target = DPoint.createList(new int[]{42,49,8,40});
        DPoint actual = DPoint.getMaxY(target);
        assertEquals(49D,actual.Y);
    }
}
