

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class fibFormulaBig {
    BigDecimal sqrt5;
    BigDecimal phi;
    BigDecimal negPhi;

    public BigInteger fibonacci(long x)
    {

        //Phi^x
        BigDecimal exp = phi.pow((int)x);
        //-Phi^x
        BigDecimal exp2 = negPhi.pow((int)x);
        // (exp - exp2) / sqrt(5)
        exp = exp.subtract(exp2);
        exp = exp.divide(sqrt5,RoundingMode.FLOOR);
        //Round up and convert to BigInteger
        BigInteger fib = exp.setScale(0, RoundingMode.FLOOR).toBigInteger();
        return fib;
    }

    public fibFormulaBig() {
        //Accuracy of fibonacci formula depends on the initial precision of sqrt(5)
        //With precision of 100, can accurately get fib(472) until we start seeing variation
        //With precision of 200, can get fib(944)
        sqrt5 = ArbitraryPrecisionSqrt.sqrt(5, 200);
        //phi = (1 + sqrt(5)) / 2
        phi = sqrt5;
        phi = phi.add(BigDecimal.ONE);
        phi = phi.divide(BigDecimal.valueOf(2));
        //-phi = (1 - sqrt(5)) / 2
        negPhi = sqrt5;
        negPhi = BigDecimal.ONE.subtract(negPhi);
        negPhi = negPhi.divide(BigDecimal.valueOf(2));

    }
}
