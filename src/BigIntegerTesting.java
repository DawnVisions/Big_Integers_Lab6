import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class BigIntegerTesting {

    /* define constants */
    static int numberOfTrials = 20;
    private static final int MAXDIGITSIZE = (int) Math.pow(2,16);
    static String ResultsFolderPath = "/home/elizabeth/Results/MyBigIntegers/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        boolean correct = BigIntegerVerification.Verification();
        System.out.println("Verification Pass?: " + correct);
        if (correct) {
            // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
            runFullExperiment("TimesFasterBaseCase-Exp1-ThrowAway.txt");
            runFullExperiment("TimesFasterBaseCase-Exp2.txt");
            runFullExperiment("TimesFasterBaseCase-Exp3.txt");
        }
    }

    private static void runFullExperiment(String resultsFileName) {
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial
        double lastAverageTime = -1;
        double doublingRatio = 0;
        resultsWriter.println("#Digit size of x(n)  AverageTime    Doubling Ratio"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        for (int inputSize = 1; inputSize <= MAXDIGITSIZE; inputSize*= 2) {
            System.out.println("Running test for digit size " + inputSize + " ... ");
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");
                MyBigIntegers a = new MyBigIntegers(randomNumberOfDigitSize(inputSize));
                MyBigIntegers b = new MyBigIntegers(randomNumberOfDigitSize(inputSize));
                System.gc();
                System.out.println("...done.");
                TrialStopwatch.start();
                a.TimesFaster(b);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
            }
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            doublingRatio = averageTimePerTrialInBatch / lastAverageTime;
            lastAverageTime = averageTimePerTrialInBatch;
            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f  %14f\n", inputSize, averageTimePerTrialInBatch, doublingRatio);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    //Returns a random long of the specified bit size
    public static String randomNumberOfDigitSize(int size){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(int i = 1; i<= size; i++)
        {
            int digit = rand.nextInt(10);
            if(i == 1)
            {
                while(digit == 0)
                {
                    digit = rand.nextInt(10);
                }
            }
            sb.append(digit);
        }
        return sb.toString();
    }
}
