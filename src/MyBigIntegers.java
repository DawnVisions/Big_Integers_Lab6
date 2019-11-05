import javax.swing.*;
import java.net.Inet4Address;
import java.sql.Time;
import java.util.ArrayList;

public class MyBigIntegers {
    String value;
    boolean isPositive;

    public String Plus(MyBigIntegers number)
    {
        String a = this.value;
        String b = number.value;
        StringBuilder answer = new StringBuilder();
        int carry = 0;

        //a + (-b)
        if(this.isPositive && !number.isPositive)
        {
            return this.Minus(number);
        }

        //(-a) + b
        if(!this.isPositive && number.isPositive)
        {
            this.isPositive = true;
            return number.Minus(this);
        }

        //Ensures a is longer than b
        if(b.length() > a.length())
        {
            String temp = b;
            b = a;
            a = temp;
        }
        //Looping from right to left of the strings
        for(int i =1; i<= a.length(); i++)
        {
            //Adding next right index from a and b
            if(i <= b.length()) {
                int adigit = a.charAt(a.length()-i) -'0';
                int bdigit = b.charAt(b.length()-i) -'0';
                int digit = adigit + bdigit + carry;
                answer.insert(0, digit % 10 );
                if (digit > 9)
                    carry = 1;
                else
                    carry = 0;
            }
            //Else if we ran out of numbers to add in b
            else
            {
                answer.insert(0, a.charAt(a.length()-i)-'0'+carry);
                carry = 0;
            }
        }
        //Adds leading 1 if there is still a carry
        if (carry > 0 )
        {
            answer.insert(0, "1");
        }

        //Add negative sign to the answer if both numbers are negative
        if(!this.isPositive && !number.isPositive)
        {
            answer.insert(0, '-');
        }
        return answer.toString();
    }

    public String Times(MyBigIntegers number) {
        String a = this.value;
        String b = number.value;

        //a or b is empty
        if(a.isEmpty() || b.isEmpty())
            return "0";
        if(a.charAt(0) == '0' || b.charAt(0) == '0')
            return "0";

        //array of digits in reverse order
        int[] digit = new int[a.length()+b.length()];
        int a_position = 0;
        int b_position = 0;

        // Go from right to left in a
        for (int i = a.length() - 1; i >= 0; i--)
        {
            int carry = 0;
            int n1 = a.charAt(i) - '0';
            b_position = 0;
            // Go from right to left in num2
            for (int j = b.length() - 1; j >= 0; j--)
            {
                // Take current digit of second number
                int n2 = b.charAt(j) - '0';

                // Multiply two digits and add previously stored digit and carry
                int sum = n1 * n2 + digit[a_position + b_position] + carry;
                carry = sum / 10;
                digit[a_position + b_position] = sum % 10;
                b_position++;
            }

            // store carry in next cell
            if (carry > 0)
                digit[a_position + b_position] += carry;

            // To shift position to left after every
            // multipliccharAtion of a digit in num1.
            a_position++;
        }

        StringBuilder answer = new StringBuilder();
        for(int k = digit.length -1; k>= 0; k--)
        {
            if(answer.length() == 0 && digit[k] == 0) {//Don't add leading 0
            }
            else
                answer.append(digit[k]);
        }

        //Make the answer negative if a xor b is negative
        if(this.isPositive ^ number.isPositive)
        {
            answer.insert(0, '-');
        }
        return answer.toString();
    }

   public static String TimesFaster(String ab, String cd) {
        System.out.println("Testing "+ ab + " times " + cd);
        //Base case
        if(ab.length() == 1 || cd.length() == 1)
        {
            int answer = Integer.parseInt(ab)*Integer.parseInt(cd);
            return Integer.toString(answer);
        }

        //Find the middle of the smaller string
        int mid = Integer.min(ab.length(), cd.length());
        mid = Math.floorDiv(mid,2);

        //Split strings in the middle
        MyBigIntegers a = new MyBigIntegers(ab.substring(0,mid));
        MyBigIntegers b = new MyBigIntegers(ab.substring(mid));
        MyBigIntegers c = new MyBigIntegers(cd.substring(0, mid));
        MyBigIntegers d = new MyBigIntegers(cd.substring(mid));

        String z0 = TimesFaster(b.ToString(),d.ToString());
        String z1 = TimesFaster(a.Plus(b), c.Plus(d));
        String z2 = TimesFaster(a.ToString(), c.ToString());

        long zero = Long.parseLong(z0);
        long one = Long.parseLong(z1);
        long two = Long.parseLong(z2);
        System.out.println("z0 is " + zero + "  z1 is " + one + "  z2 is " + two);
        long result = two*(long)Math.pow(10, mid*2)+(one-two-zero)*(long)Math.pow(10,mid)+zero;

        System.out.println("Returning " + result);
        return Long.toString(result);
    }

    public String Minus(MyBigIntegers number) {

        String a = this.value;
        String b = number.value;
        StringBuilder answer = new StringBuilder();
        boolean negativeAnswer = false;
        int carry = 0;

        // a - (-b)  =  a + b
        if(this.isPositive && !number.isPositive) {
            number.isPositive = true;
            return this.Plus(number);
        }

        //-a - b   =  -(a + b)
        if(!this.isPositive && number.isPositive) {
            number.isPositive = false;
            return this.Plus(number);
        }

        // -a - (-b)   =  (-a) + b
        if(!this.isPositive && !number.isPositive) {
            number.isPositive = true;
            return this.Plus(number);
        }

        // Check to see if a is smaller than b
        if (isSmaller(a, b))
        {
            String tmp = a;
            a = b;
            b = tmp;
            negativeAnswer = true;
        }

        int diff = a.length() - b.length();

        // Traverse from end of both strings
        for (int i = b.length() - 1; i >= 0; i--)
        {
            // Subtract digits and carry
            int digit = (((int)a.charAt(i + diff) - (int)'0') -
                        ((int)b.charAt(i) - (int)'0') - carry);
            if (digit < 0)
            {
                digit = digit+10;
                carry = 1;
            }
            else {
                carry = 0;
            }
            answer.insert(0, digit);
        }

        for (int i = a.length() - b.length() - 1; i >= 0; i--)
        {
            if (a.charAt(i) == '0' && carry > 0)
            {
                answer.insert(0, "9");
                continue;
            }
            int digit = (((int)a.charAt(i) - (int)'0') - carry);
            if (i > 0 || digit > 0) // remove preceding 0's
                answer.insert(0, digit);
            carry = 0;
        }
        if(negativeAnswer)
            answer.insert(0, '-');

        return answer.toString();
    }

    //Helper function for Minus
    // a-b, tells us if the value of a is smaller than b
    static boolean isSmaller(String a, String b)
    {
        // Calculate lengths of both string
        int n1 = a.length(), n2 = b.length();

        if (n1 < n2)
            return true;
        if (n1 > n2)
            return false;

        for (int i = 0; i < n1; i++)
        {
            if (a.charAt(i) < b.charAt(i))
                return true;
            else if (a.charAt(i) > b.charAt(i))
                return false;
        }
        return false;
    }

    public MyBigIntegers() {
        value = "0";
        isPositive = true;
    }

    public MyBigIntegers(String number) {
        isPositive = true;
        if(number.charAt(0) == '-')
        {
            isPositive = false;
            number = number.substring(1);
        }
        value = number;
    }

    public String ToString(){
        if(!isPositive)
        {
            return "-".concat(value);
        }
        return value;
    }
}

