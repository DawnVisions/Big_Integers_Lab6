import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

public class N_BasedTests {

    /* define constants */
    static int numberOfTrials = 20;
    private static final int MAXDIGITSIZE = 5;
    static String ResultsFolderPath = "/home/elizabeth/Results/NBased/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        boolean correct = Verification.verify(fibFormulaBig::fibonacci);
        System.out.println("Verification Pass?: " + correct);
        if(correct) {
            // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
            runFullExperiment("fibFormulaBig-Exp1-ThrowAway.txt");
            runFullExperiment("fibFormulaBig-Exp2.txt");
            runFullExperiment("fibFormulaBig-Exp3.txt");
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

        resultsWriter.println("#Digit size of x(n)  AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        BigInteger result;
        long x;

        for (int inputSize = 1; inputSize <= MAXDIGITSIZE; inputSize++) {
            System.out.println("Running test for digit size " + inputSize + " ... ");
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");
                x = randomIntegerOfDigitSize(inputSize);
                System.gc();
                System.out.println("...done.");
                TrialStopwatch.start();
                result = fibFormulaBig.fibonacci(x);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
                System.out.println(x + " fib number is " + result.toString());
            }
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            /*if (lastAverageTime != -1) {
                doublingRatio = averageTimePerTrialInBatch / lastAverageTime;
            }
            lastAverageTime = averageTimePerTrialInBatch;*/

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f\n", inputSize, averageTimePerTrialInBatch);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    //Returns a random long of the specified bit size
    public static long randomIntegerOfDigitSize(int size){
        double minvalue = Math.pow(10,size-1);
        double maxvalue = Math.pow(10,size)-1;
        //System.out.println("Random value between " + minvalue + " and " + maxvalue);
        return (long) (minvalue + Math.random()*(maxvalue-minvalue));
    }

}

