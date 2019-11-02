import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

//Reference: https://introcs.cs.princeton.edu/java/92symbolic/ArbitraryPrecisionSqrt.java
public class ArbitraryPrecisionSqrt {
    private static final BigDecimal TWO = new BigDecimal(2);

    // Reference: https://en.wikipedia.org/wiki/Methods_of_computing_square_roots
    public static BigDecimal sqrt(int x, int n) {
        if (x <= 0) throw new IllegalArgumentException("x must be a positive integer: " + x);
        if (n <= 0) throw new IllegalArgumentException("n must be a positive integer: " + n);

        // initial estimate (find smallest power of 2 >= sqrt(x))
        int initial = 1;
        while (initial * initial < x) {
            initial *= 2;
        }

        // 1 + lg(n) iterations of Newton's method
        // (1 iteration needed to get 1 decimal digit of precision; each subsequent
        //  iteration at least doubles the number of significant digits)
        BigDecimal t = new BigDecimal(initial);
        BigDecimal c = new BigDecimal(x);
        for (int precision = 1; precision <= 2 * n; precision *= 2) {
            MathContext context = new MathContext(1 + 2 * precision, RoundingMode.HALF_EVEN);
            t = c.divide(t, context).add(t).divide(TWO, context);
        }
        return t.round(new MathContext(n, RoundingMode.HALF_EVEN));
    }
}