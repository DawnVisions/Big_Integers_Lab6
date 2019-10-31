public class fibMatrixBig {
    public static long fibonacci(long x)
    {
        if(x<=1)
            return x;
        //Create matrix
        // 1  1
        // 1  0
        long[][] matrix = {{1,1}, {1,0}};

        //Take the matrix to the x+1 th power
        matrix = MatrixPower(matrix, (int) x+1);
        //The number in the bottom right is the x-th fibonacci number
        return matrix[1][1];
    }

    static long[][] MatrixPower(long[][] matrix, int power)
    {
        //If the power is 0, return the identity matrix, which is similar to multiplying an integer by 1, it does not change the matrix.
        //Identity matrix:
        // 1  0
        // 0  1
        if (power == 0) {
            long[][] identity = {{1,0}, {0,1}};
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
            long[][] result = MatrixPower(matrix, power/2);
            return MatrixMultiplication(result,result);
        }
    }

    static long[][] MatrixMultiplication(long[][] m1, long[][] m2)
    {
        long[][] result = new long[2][2];
        for(int i = 0; i<2; i++)
        {
            for(int j = 0; j<2; j++)
            {
                result[i][j] = 0;
                for(int k = 0; k<2; k++)
                {
                    result[i][j] += m1[i][k]*m2[k][j];
                }
            }
        }
        return result;
    }

    //Print matrix method for testing and debugging
    static void printMatrix(long[][] matrix)
    {
        for(int i = 0; i<2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
