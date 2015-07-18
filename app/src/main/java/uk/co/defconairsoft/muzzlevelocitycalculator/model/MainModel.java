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
    private double speed;
    private double average;
    private double timeBetweenPeaks;
    private double[] previous = new double[3];

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


    public MainModel(){
        audioMonitor = new AudioMonitor();
        ballistics = new Ballistics();
    }

    public void start(){
        audioMonitor.start();

    }

    public void stop() {
        try {
            audioMonitor.stop();
            audioMonitor.release();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void calculateSpeed(double timeBetweenPeaks){
        this.timeBetweenPeaks = timeBetweenPeaks;
        count++;
        previous[2]=previous[1];
        previous[1]=previous[0];
        previous[0]=speed;
        speed = 0d;
        if (timeBetweenPeaks> MIN_TIME) {
            speed = getBallistics().calculateMuzzleVelocity(0D, timeBetweenPeaks);
            speed = getBallistics().convertToFeetPerSecond(speed);
        }

        average = speed+previous[0]+previous[1]+previous[2];
        average = average/4;

    }


}
