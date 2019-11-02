

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class fibFormulaBig {
    public static BigInteger fibonacci(long x)
    {
        //phi = (1 + sqrt(5)) / 2
        BigDecimal phi = new BigDecimal(Math.sqrt(5));
        phi = phi.add(BigDecimal.ONE);
        phi = phi.divide(BigDecimal.valueOf(2));
        //-phi = (1 - sqrt(5)) / 2
        BigDecimal negPhi = new BigDecimal(Math.sqrt(5));
        negPhi = BigDecimal.ONE.subtract(negPhi);
        negPhi = negPhi.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        //Phi^x
        BigDecimal exp = phi.pow((int)x);
        //-Phi^x
        BigDecimal exp2 = negPhi.pow((int)x);
        // (exp - exp2) / 5
        exp = exp.subtract(exp2);
        exp = exp.divide(BigDecimal.valueOf(Math.sqrt(5)),RoundingMode.HALF_UP);
        //Round up and convert to BigInteger
        return exp.setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }
}
