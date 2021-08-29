import java.util.*;

/*
The Stats class creates an object that stores important calculated data, like the r squared, slope, and intercept
for the regression of a specific variable
 */
public class Stats {
    protected Double rsquared;
    protected ArrayList<Double> nums;
    protected Double slope;
    protected Double intercept;
    public Stats(Double slope, Double intercept, Double rsquared, int varNumber, ArrayList<Double> numbers)
    {
        this.slope = slope;
        this.intercept = intercept;
        this.rsquared = rsquared;
        this.nums = numbers;
    }
    public Double getRsquared()
    {
        return rsquared;
    }
    public ArrayList<Double> getNums()
    {
        return nums;
    }
    public Double getSlope()
    {
        return slope;
    }
    public Double getIntercept()
    {
        return intercept;
    }
}
