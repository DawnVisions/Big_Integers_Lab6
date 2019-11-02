import java.math.BigInteger;
import java.util.Arrays;

public class Verification {
    public static boolean verify(Fibonacci fibMethod)
    {
        boolean correct = true;
        long[] compareAgainst = {0,1,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765};

        //Checking the first 20 fib numbers
        //Creates a list of fiboacci numbers with the selected method up to 20
        for(int i = 0; i<= 20; i++)
        {
            if(fibMethod.fibonacci(i).intValue() != compareAgainst[i])
            {
                correct = false;
                System.out.println("Fib number " + fibMethod.fibonacci(i).toString() + " does not equal " + compareAgainst[i]);
            }
        }

        //Checking other larger fib numbers
        if(fibMethod.fibonacci(40).toString().compareTo("102334155") != 0)
            correct = false;
        if(fibMethod.fibonacci(50).toString().compareTo("12586269025") != 0)
            correct = false;
        if(fibMethod.fibonacci(55).toString().compareTo("139583862445") != 0)
            correct = false;
        if(fibMethod.fibonacci(60).toString().compareTo("1548008755920") != 0)
            correct = false;
        if(fibMethod.fibonacci(70).toString().compareTo("190392490709135") != 0)
            correct = false;

        //Checking random large x against the fibLoopBig version
        int minvalue = 60;
        int maxvalue = 300;
        long x = (long)(minvalue+Math.random()*(maxvalue-minvalue));
        String numToCheck = fibMethod.fibonacci(x).toString();
        if(numToCheck.compareTo(fibLoopBig.fibonacci(x).toString()) != 0) {
            correct = false;
            System.out.println(numToCheck + " does not equal " + fibLoopBig.fibonacci(x).toString());
        }
        else
            System.out.println("Test passed: fib(" + x + ") =  " + numToCheck);
        return correct;
    }
}
