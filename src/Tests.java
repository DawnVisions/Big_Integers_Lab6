import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Tests {

    /* define constants */
    static int numberOfTrials = 50;
    private static final int MAXDIGITSIZE = 7;
    static String ResultsFolderPath = "/home/elizabeth/IdeaProjects/Results/NBased/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        boolean correct = Verification.verify(fibFormulaBig::fibonacci);
        System.out.println("Verification Pass?: " + correct);
        if(correct) {
            // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
            //runFullExperiment("fibMatrixBig-Exp1-ThrowAway.txt");
            //runFullExperiment("fibMatrixBig-Exp2.txt");
            //runFullExperiment("fibMatrixBig-Exp3.txt");
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

        resultsWriter.println("#Digit size of x(n)  AverageTime   Doubling Ratio"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        for (int inputSize = 1; inputSize <= MAXDIGITSIZE; inputSize*=2) {
            System.out.println("Running test for digit size " + inputSize + " ... ");
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");
                long x = randomIntegerOfDigitSize(inputSize);
                System.gc();
                System.out.println("...done.");
                TrialStopwatch.start();
                BigInteger result = fibMatrixBig.fibonacci(x);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
                System.out.println(x + " fib number is " + result.toString());
            }
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            if (lastAverageTime != -1) {
                doublingRatio = averageTimePerTrialInBatch / lastAverageTime;
            }
            lastAverageTime = averageTimePerTrialInBatch;

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f %10.2f\n", inputSize, averageTimePerTrialInBatch, doublingRatio);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    //Returns a random long of the specified bit size
    public static long randomIntegerOfDigitSize(int size){
        double minvalue = Math.pow(10,size-1);
        double maxvalue = Math.pow(10,size)-1;
        System.out.println("Random value between " + minvalue + " and " + maxvalue);
        return (long) (minvalue + Math.random()*(maxvalue-minvalue));
    }

    public static int sizeInBits(long x)
    {
        return (int) Math.floor(Math.log(x)/Math.log(2)+1);
    }
}

