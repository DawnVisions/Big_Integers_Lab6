import java.util.ArrayList;

public class BigIntegerTesting {
    public static void main(String[] args) {
        MyBigIntegers a = new MyBigIntegers("423");
        MyBigIntegers b = new MyBigIntegers("1118");
        System.out.println("a+b = " + a.Plus(b).toString());
    }
}
