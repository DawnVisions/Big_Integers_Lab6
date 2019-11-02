import java.math.BigInteger;

public class fibFormula {
    public static long fibonacci(long x)
    {
        double phi = (Math.sqrt(5)-1)/2;
        double fib = (Math.pow(phi, x)-Math.pow((phi*-1.0),x))/5;
        return Math.round(fib);
    }
}
