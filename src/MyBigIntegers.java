import java.util.ArrayList;

public class MyBigIntegers {
    ArrayList<Integer> value;
    boolean positive = true;

    public ArrayList Plus(MyBigIntegers number)
    {
        ArrayList<Integer> a = this.value;
        ArrayList<Integer> b = number.value;
        ArrayList<Integer> answer = new ArrayList<Integer>();
        int carry = 0;

        //Ensures a is longer than b
        if(b.size() > a.size())
        {
            ArrayList<Integer> temp = b;
            b = a;
            a = temp;
        }
        //Looping from right to left of the array
        for(int i =1; i<= a.size(); i++)
        {
            //Adding next right index from a and b
            if(i <= b.size()) {
                int digit = a.get(a.size()-i) + b.get(b.size()-i) + carry;
                answer.add(0, digit % 10);
                if (digit > 9)
                    carry = 1;
                else
                    carry = 0;
            }
            //Else if we ran out of numbers to add in b
            else
            {
                answer.add(0, a.get(a.size()-i)+carry);
                carry = 0;
            }
        }
        //Adds leading 1 if there is still a carry
        if (carry > 0 )
        {
            answer.add(0, 1);
        }
        return answer;
    }

    public MyBigIntegers() {
        value = new ArrayList<Integer>();
        value.add(0);
    }

    public MyBigIntegers(String number) {
        if (number.charAt(0) == '-')
        {
            positive = false;
            number = number.substring(1);
        }
        for(int i = 0; i<number.length(); i++)
        {
            value.add((int)number.charAt(i));
        }
    }

    public MyBigIntegers(ArrayList<Integer> value, boolean positive) {
        this.value = value;
        this.positive = positive;
    }

    public String ToString(){
        return value.toString();
    }
}

