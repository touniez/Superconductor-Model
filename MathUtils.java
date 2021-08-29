import java.util.ArrayList;

/*
This class contains the mathematical methods for the LinearRegressionModel class to use to get slope and intercept of the two datasets
 */
public class MathUtils {
    /*
     * Calculate the covariance of two sets of data
     */
    public static double covariance(ArrayList<Double> x, ArrayList<Double> y) {
        double xmean = mean(x);
        double ymean = mean(y);

        double result = 0;

        for (int i = 0; i < x.size(); i++) {
            result += (x.get(i) - xmean) * (y.get(i) - ymean);
        }

        result /= x.size() - 1;

        return result;
    }

    /*
     * Calculate the mean of a data set
     */
    public static double mean(ArrayList<Double> data) {
        double sum = 0;

        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i);
        }

        return sum / data.size();
    }

    /*
     * Calculate the variance of a data set
     */
    public static double variance(ArrayList<Double> data) {
        // Get the mean of the data set
        double mean = mean(data);

        double sumOfSquaredDeviations = 0;

        // Loop through the data set
        for (int i = 0; i < data.size(); i++) {
            // sum the difference between the data element and the mean squared
            sumOfSquaredDeviations += Math.pow(data.get(i) - mean, 2);
        }

        // Divide the sum by the length of the data set - 1 to get our result
        return sumOfSquaredDeviations / (data.size() - 1);
    }
}