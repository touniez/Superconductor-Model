import java.util.ArrayList;

public abstract class RegressionModel {

    // The X values of the data set points
    protected ArrayList<Double> xValues;

    // The X values of the data set points
    protected ArrayList<Double> yValues;

    // Have the unknown parameters been calculated yet?
    protected boolean computed;

    /*
     * Construct a new RegressionModel object with the specified data points
     * based on X and Y inputs
     */
    public RegressionModel(ArrayList<Double> x, ArrayList<Double> y) {
        this.xValues = x;
        this.yValues = y;
        computed = false;
    }

    public abstract double[] getCoefficients();

    /*
     * Find the best fit values of the unknown parameters in this regression model
     */
    public abstract void compute();


}