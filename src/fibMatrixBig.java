import java.math.BigInteger;

public class fibMatrixBig {
    public static BigInteger fibonacci(long x)
    {
        if(x<=1)
            return BigInteger.valueOf(x);
        //Create matrix
        // 1  1
        // 1  0
        BigInteger[][] matrix = {{BigInteger.ONE,BigInteger.ONE}, {BigInteger.ONE,BigInteger.ZERO}};

        //Take the matrix to the x+1 th power
        matrix = MatrixPower(matrix,  x+1);
        //The number in the bottom right is the x-th fibonacci number
        return matrix[1][1];
    }

    static BigInteger[][] MatrixPower(BigInteger[][] matrix, long power)
    {
        //If the power is 0, return the identity matrix, which is similar to multiplying an integer by 1, it does not change the matrix.
        //Identity matrix:
        // 1  0
        // 0  1
        if (power == 0) {
            BigInteger[][] identity = {{BigInteger.ONE,BigInteger.ZERO}, {BigInteger.ZERO,BigInteger.ONE}};
            return identity;
        }
        //Odd power - multiply matrix by next lowest even power
        else if (power%2 ==1)
        {
            return MatrixMultiplication(matrix, MatrixPower(matrix, power-1));
        }
        //Even power - multiply the two matrices which are power/2
        else
        {
            BigInteger[][] result = MatrixPower(matrix, power/2);
            return MatrixMultiplication(result,result);
        }
    }

    static BigInteger[][] MatrixMultiplication(BigInteger[][] m1, BigInteger[][] m2)
    {
        BigInteger[][] result = new BigInteger[2][2];
        for(int i = 0; i<2; i++)
        {
            for(int j = 0; j<2; j++)
            {
                result[i][j] = BigInteger.ZERO;
                for(int k = 0; k<2; k++)
                {
                    BigInteger multStep = m1[i][k].multiply(m2[k][j]);
                    result[i][j] = result[i][j].add(multStep);
                }
            }
        }
        return result;
    }

    //Print matrix method for testing and debugging
    static void printMatrix(BigInteger[][] matrix)
    {
        for(int i = 0; i<2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(matrix[i][j].toString() + " ");
            }
            System.out.println();
        }
    }
}
