import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.util.Scanner;

public class Run {
    //The Statistics arraylist holds all of the calculated values with their respective data
    protected static ArrayList<Stats> statistics = new ArrayList<Stats>();

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<ArrayList<Double>> inputfiles = processData("/Users/anthonyzheng/IdeaProjects/FInalProject/src/trainingdata.csv");
        runregregression(inputfiles);
        System.out.println("Testing Success Rate: " + test());
    }

    /*
     * First, we need to process the large csv file by splitting it into each compound and its properties
     * Use ArrayList of ArrayLists, first arraylist holds all compounds, second one holds all properties
     */
    public static ArrayList<ArrayList<Double>> processData(String filename) throws FileNotFoundException
    {
        ArrayList<ArrayList<Double>> ans = new ArrayList<ArrayList<Double>>();
        File text = new File(filename);
        Scanner in = new Scanner(text);
        while(in.hasNext())
        {
            ArrayList<Double> element = new ArrayList<Double>();
            String[] elementArray = in.next().split(",");
            for(int i = 0; i < elementArray.length; i++)
            {
                element.add(Double.parseDouble(elementArray[i]));
            }
            ans.add(element);
        }
        return ans;

    }


    /*
        Next, we need to run the regression for each of the properties vs the critical temperature of the material
        Find slope, intercept, and r squared
         */
    public static void runregregression(ArrayList<ArrayList<Double>> inputdata) throws FileNotFoundException
    {
        ArrayList<Double> actual = getList("/Users/anthonyzheng/IdeaProjects/FInalProject/src/critical_temps.csv");
        File text = new File("/Users/anthonyzheng/IdeaProjects/FInalProject/src/varnames.csv");
        Scanner in2 = new Scanner(text);
        String[] names = in2.next().split(",");
        for(int i = 0; i < inputdata.get(1).size(); i++)
        {
            ArrayList<Double> x = new ArrayList<Double>();
            for(int j = 0; j < inputdata.size(); j++)
            {
                x.add(inputdata.get(j).get(i));
            }
            RegressionModel model = new LinearRegressionModel(x, actual);
            model.compute();
            double[] coefficients = model.getCoefficients();
            double rsquared = calc(x, actual, coefficients[0], coefficients[1]);
            Stats result = new Stats(coefficients[1], coefficients[0], rsquared, i, x);
            System.out.println("variable: " + names[i] + "    rsquared: "+ rsquared + "\n     linear regression: " + coefficients[1] + " + " + coefficients[0] + "x");
            statistics.add(result);
        }


        //System.out.println(statistics.get(0).getNums());
        //System.out.println(statistics.get(1).getNums());
    }

    /*
    Converts the CSV into an ArrayList
     */
    public static ArrayList<Double> getList(String path) throws FileNotFoundException
    {
        File text = new File(path);
        Scanner in = new Scanner(text);
        ArrayList<Double> ans = new ArrayList<Double>();
        String next = in.next().substring(1);
        ans.add(Double.parseDouble(next.trim()));
        while(in.hasNext())
        {
            String nex = in.next();
            ans.add(Double.parseDouble(nex.trim()));
        }
        return ans;
    }

    /*
    Calculates the r squared based on the slope, intercept, and x and y data
     */
    public static double calc(ArrayList<Double> expected, ArrayList<Double> actual, Double intercept, Double slope)
    {
        //System.out.println(intercept + " " + slope);
        Double residuals = 0.0;
        Double totaly = 0.0;
        for(int i = 0; i < expected.size(); i++) {
            residuals += Math.pow(actual.get(i) - (slope * expected.get(i) + intercept), 2);
            totaly += actual.get(i);
        }
        Double meany = totaly/expected.size();
        Double meancalc = 0.0;
        for(int i = 0; i < expected.size(); i++) {
            meancalc += Math.pow(actual.get(i)-meany, 2);
        }
        return 1-(residuals/meancalc);
    }

        /*
        The model uses the slope, intercept, and r squared values to predict the critical temperature based on a combination of properties
        The testing method will test the model for 2000 materials and return a success rate (within +- 5)
         */
    public static Double test() throws FileNotFoundException
    {
        File test = new File("/Users/anthonyzheng/IdeaProjects/FInalProject/src/testingdata.csv");
        Scanner in = new Scanner(test);
        int successes = 0;
        int totalTrials = 0;
        while(in.hasNext())
        {
            Double predict = 0.0;
            Double div = 0.0;
            String[] elementData = in.next().split(",");
            for(int i = 1; i < elementData.length-1; i++)
            {

                Double current = Double.parseDouble(elementData[i]);
                Double currentR = statistics.get(i).getRsquared();
                if(currentR < 0.35)
                    continue;
                Double currentVal = current * statistics.get(i).getSlope() + statistics.get(i).getIntercept();
                predict += Math.pow(currentVal*currentR,2);
                div += Math.sqrt(currentR);
            }
            Double ans = Math.sqrt(predict)/div;
            Double actualans = Double.parseDouble(elementData[elementData.length-1]);
            //System.out.println(ans + " " + actualans);
            totalTrials ++;
            if(ans > actualans -5 &&  ans < actualans +5)
                successes ++;

        }
        return ((double)successes)/totalTrials;
    }
}
