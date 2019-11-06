public class BigIntegerVerification {

    public static boolean Verification() {

        boolean testPass = true;
        MyBigIntegers a = new MyBigIntegers("1234");
        MyBigIntegers b = new MyBigIntegers("5678");

        String result;
        String expectedResult;

        result = a.Times(b).ToString();
        expectedResult = Long.toString(1234*5678);
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        result = a.TimesFaster(b).ToString();
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        a.value = "123123123123123";
        b.value = "456456456456456";
        result = a.Plus(b).ToString();
        expectedResult = "579579579579579";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        a.value = "9999999999999999999999999999999999";
        b.value = "11111111";
        result = a.Minus(b).ToString();
        expectedResult = "9999999999999999999999999988888888";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        result = a.Plus(b).ToString();
        expectedResult = "10000000000000000000000000011111110";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        a = new MyBigIntegers("-1223334444555556666667777777");
        b = new MyBigIntegers("99999999988888888");
        result = a.Minus(b).ToString();
        expectedResult = "-1223334444655556666656666665";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        result = a.Plus(b).ToString();
        expectedResult = "-1223334444455556666678888889";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        result = a.Times(b).ToString();
        expectedResult = "-122333444441963060639863197354307654328641976";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        result = a.TimesFaster(b).ToString();
        expectedResult = "-122333444441963060639863197354307654328641976";
        if(result.compareTo(expectedResult) != 0)
            testPass = false;

        //Testing Times() and TimesFaster() against integer multiplication for random numbers less than 31 bits
        for(int i =0; i< 20; i++)
        {
            long aRand = randomIntegerLessThan31Bits();
            long bRand = randomIntegerLessThan31Bits();
            a = new MyBigIntegers(Long.toString(aRand));
            b = new MyBigIntegers(Long.toString(bRand));
            expectedResult = Long.toString(aRand*bRand);

            result = a.Times(b).ToString();
            if(result.compareTo(expectedResult) != 0)
                testPass = false;

            result = a.TimesFaster(b).ToString();
            if(result.compareTo(expectedResult) != 0)
                testPass = false;
        }


        return testPass;
    }

    public static long randomIntegerLessThan31Bits(){
        double minvalue = 0;
        double maxvalue = Math.pow(2, 31);
        return (long) (minvalue + Math.random()*(maxvalue-minvalue));
    }
}
