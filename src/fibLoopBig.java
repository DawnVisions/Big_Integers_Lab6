import java.math.BigInteger;

public class fibLoopBig{

    public static BigInteger fibonacci(long x)
    {
        //Base case, return 0 or 1
        if (x<=1)
        {
            return BigInteger.valueOf(x);
        }
        //Keeps track of x-2 and x-1 to calculate x
        else {
            //Initializing variables
            BigInteger secondToLast = BigInteger.ZERO;
            BigInteger last = BigInteger.ONE;
            BigInteger current = BigInteger.ONE;
            //Loop until we reach the desired x value
            for (int i = 1; i < x; i++) {
                current = secondToLast.add(last);
                secondToLast = last;
                last = current;
            }
            //Return the x fibonacci number
            return current;
        }
    }
}
