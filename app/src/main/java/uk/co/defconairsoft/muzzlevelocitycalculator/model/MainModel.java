package uk.co.defconairsoft.muzzlevelocitycalculator.model;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainModel
{
    public static final double MIN_TIME = 0.05D;
    private AudioMonitor audioMonitor;
    private Ballistics ballistics;
    private int count;
    private int miss;
    private double speed;
    private double average;
    private double timeBetweenPeaks;
    private double[] previous = new double[3];
    private boolean isStarted = false;

    public int getMiss(){return miss;}
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public double getSpeed() {
        return speed;
    }
    public double getAverage() {
        return average;
    }
    public void setAverage(double average) {
        this.average = average;
    }
    public double[] getPrevious() {
        return previous;
    }
    public Ballistics getBallistics(){
        return ballistics;
    }
    public void setListener(LiveAnalysis.IAnalysisListener listener){
        audioMonitor.getLiveAnalysis().setListener(listener);
    }
    public double getTimeBetweenPeaks() {
        return timeBetweenPeaks;
    }
    public void setThreshold(int threshold){
        audioMonitor.getLiveAnalysis().setThreshold((short)threshold);
    }
    public int getThreshold(){
        return (int)audioMonitor.getLiveAnalysis().getThreshold();
    }


    public MainModel(){
        audioMonitor = new AudioMonitor();
        ballistics = new Ballistics();
    }

    public void start(){
        if (!isStarted) {
            audioMonitor.start();
            isStarted = true;
        }

    }

    public void stop() {
        try {
            isStarted=false;
            audioMonitor.stop();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void calculateSpeed(double timeBetweenPeaks){

        this.timeBetweenPeaks = timeBetweenPeaks;
        if (timeBetweenPeaks< MIN_TIME) {
            //a miss
            miss++;
            return;
        }
        count++;
        previous[2]=previous[1];
        previous[1]=previous[0];
        previous[0]=speed;
        speed = 0d;
        speed = getBallistics().calculateMuzzleVelocity(0D, timeBetweenPeaks);
        speed = getBallistics().convertToFeetPerSecond(speed);
        speed = getBallistics().applyCorrection(speed);

        average = speed+previous[0]+previous[1]+previous[2];
        average = average/4;

    }

    public void applySettings(Settings settings){
        setThreshold(settings.threshold);
        ballistics.setBarrelLength(settings.barrelLength);
        ballistics.setDiameter(settings.pelletDiameter);
        ballistics.setMass(settings.pelletMass);
        ballistics.setDistance(settings.targetDistance);
        ballistics.setCorrection(settings.correction);
    }


}
