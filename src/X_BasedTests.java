import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

public class X_BasedTests {

    private static final int MAXIMUM = 400;
    /* define constants */
    static int numberOfTrials = 50;
    static String ResultsFolderPath = "/home/elizabeth/IdeaProjects/Results/XBased/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        boolean correct = Verification.verify(fibMatrixBig::fibonacci);
        System.out.println("Verification Pass?: " + correct);
        if(correct) {
            // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
            runFullExperiment("fibMatrixBig-Exp1-ThrowAway.txt");
            runFullExperiment("fibMatrixBig-Exp2.txt");
            runFullExperiment("fibMatrixBig-Exp3.txt");
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

        resultsWriter.println("#Fib number x  AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        for (int i = 1; i<= MAXIMUM; i++) {
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");
                System.gc();
                TrialStopwatch.start();
                BigInteger result = fibMatrixBig.fibonacci(i);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
                System.out.println(i + " fib number is " + result.toString());
            }
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f\n", i, averageTimePerTrialInBatch);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }
}

