/**
 * MPD Bailey Technology
 * Copyright 2015
 *
 * www.mpdbailey.co.uk
 */

package uk.co.defconairsoft.muzzlevelocitycalculator.model;

import android.util.Log;

/**
 * Created by Mark on 01/07/2015.
 */
public class Ballistics
{
    public static final double AIR_DENSITY = 1.225D;
    public static final double DISTANCE = 10D;
    public static final double SPEED_OF_SOUND = 330;
    public static final double MASS = 0.25e-3D;
    public static final double DIAMETER = 6e-3D;
    public static final double METERS_TO_FEET = 3.2808399D;
    public static final double BARREL_LENGTH = 0.48D;

    private double airDensity = AIR_DENSITY;
    private double distance = DISTANCE;
    private double speedOfSound = SPEED_OF_SOUND;
    private double mass = MASS;
    private double diameter = DIAMETER;
    private double barrelLength = BARREL_LENGTH;

    public double getBarrelLength() {
        return barrelLength;
    }

    public void setBarrelLength(double barrelLength) {
        this.barrelLength = barrelLength;
    }

    public double getAirDensity() {
        return airDensity;
    }

    public void setAirDensity(double airDensity) {
        this.airDensity = airDensity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeedOfSound() {
        return speedOfSound;
    }

    public void setSpeedOfSound(double speedOfSound) {
        this.speedOfSound = speedOfSound;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public Ballistics(){

    }

    /**
     * Calculates the muzzle velocity using the timings of the sounds when
     * the pellet is fired to the when the pellet hits the target
     *
     * @param startTime time in seconds at which pellet is heard being fired
     * @param endTime time in seconds at which pellet is heard hitting the target
     * @return velocity in meters per second
     */
    public double calculateMuzzleVelocity(double startTime, double endTime){
        double t = calculateFlightTime(startTime,endTime);
        double k = calculateDragCoefficient(getMass(),getDiameter());
        return calculateMuzzleVelocity(t, k, getDistance());
    }

    /**
     * Convert speed from meters per second to feet per second
     * @param metersPerSecond speed in meters per second
     * @return speed in feet per second
     */
    public double convertToFeetPerSecond(double metersPerSecond)
    {
        return metersPerSecond*METERS_TO_FEET;
    }

    /**
     * The equation of motion is complex and so a binary search algorithm is used
     * to guess the velocity of the pellet.
     *
     * @param time of flight of pellet in seconds
     * @param dragCoefficient
     * @param distance distance travelled by the pellet in meters
     * @return
     */
    private double calculateMuzzleVelocity(double time, double dragCoefficient, double distance)
    {
        double high = 10;
        double low = 0;
        double delta = 0;
        double d;
        do
        {
            low = high;
            high = high * 2;
            Log.v("mpdb", String.format("High %f", high));
            d = calculateDistance(time, dragCoefficient, high);

        } while (d<distance);
        do
        {
            Log.v("mpdb", String.format("Low: %f, High: %f", low, high));
            double mid = (low + high) / 2;
            d = calculateDistance(time, dragCoefficient, mid);
            if (d<distance)
            {
                low = mid;
            }
            else
            {
                high = mid;
            }
            delta = high-low;
        } while (delta>0.1D);
        return low;
    }
    /**
     * Takes into account the speed of sound and estimate of time spent in the barrel
     *
     * @param startTime in seconds (pellet leaves muzzle)
     * @param endTime in seconds (pellet hits target + time for sound to travel)
     * @return time in seconds
     */
    private double calculateFlightTime(double startTime, double endTime)
    {
        //30 came from testing
        double timeInBarrel = getBarrelLength() / 30D;
        return endTime - startTime - timeInBarrel - getDistance() / getSpeedOfSound();
    }

    /**
     * See
     * http://en.wikipedia.org/wiki/Airsoft_pellets#Pellet_ballistics
     *
     * Air density will affect the drag coefficient
     *
     * @param mass of pellet in kilograms
     * @param diameter of pellet in meters
     * @return drag coefficient
     */
    private double calculateDragCoefficient(double mass, double diameter)
    {
        return 0.5 * mass * getAirDensity() * 0.47 * Math.PI * diameter * diameter * 0.25;
    }

    /**
     * The equation of motion is complex for the pellet and its hard to refactor the
     * equation to get the velocity.
     *
     * The idea is to estimated the velocity and see how close the calculated distance is to
     * the known distance
     *
     * @param time of travel of the pellet in seconds
     * @param dragCoefficient
     * @param velocity estimated velocity of the pellet
     * @return distance travelled by the pellet in meters
     */
    private double calculateDistance(double time, double dragCoefficient, double velocity)
    {
        double sqrt1 = Math.sqrt(velocity*dragCoefficient/2);
        double sqrt2 = Math.sqrt(2 * velocity / dragCoefficient);
        return Math.atan(sqrt1 * time) * sqrt2;
    }

}
