import java.util.*;



public class LinearRegressionModel extends RegressionModel {

    // The y intercept of the straight line
    private double a;

    // The slope of the line
    private double b;

    /*
     * Construct a new LinearRegressionModel with the supplied data set
     * Two arraylists of x and y data are the inputs
     */
    public LinearRegressionModel(ArrayList<Double> x, ArrayList<Double> y) {
        super(x, y);
        a = b = 0;
    }


    /*
     * This method returns the slope and intercept in an array
     */
    public double[] getCoefficients() {
        if (!computed)
            throw new IllegalStateException("Model has not yet computed");
        //System.out.println("intercept: "+ a + "  slope: " + b);
        return new double[] { a, b };
    }

    /*
     * This method computes the slope and intercept of the regression of x and y data with covariance and variance
     */
    public void compute() {

        // throws exception if regression can not be performed
        if (xValues.size() < 2 | yValues.size() < 2) {
            throw new IllegalArgumentException("Must have more than two values");
        }

        // get the value of the gradient using the formula b = cov[x,y] / var[x]
        b = MathUtils.covariance(xValues, yValues) / MathUtils.variance(xValues);

        // get the value of the y-intercept using the formula a = ybar + b \* xbar
        a = MathUtils.mean(yValues) - b * MathUtils.mean(xValues);

        // set the computed flag to true after we have calculated the coefficients
        computed = true;
    }


}