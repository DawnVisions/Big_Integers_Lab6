import java.util.ArrayList;

public class Addition {
    public static ArrayList add( int[] a, int[] b)
    {
        ArrayList answer = new ArrayList();
        int carry = 0;

        //Ensures a is longer than b
        if(b.length > a.length)
        {
            int[] temp = b;
            b = a;
            a = temp;
        }

        //Looping from right to left of the array
        for(int i =1; i<= a.length; i++)
        {
            //Adding next right index from a and b
            if(i <= b.length) {
                int digit = a[a.length-i] + b[b.length-i] + carry;
                answer.add(0, digit % 10);
                if (digit > 9)
                    carry = 1;
                else
                    carry = 0;
            }
            //Else if we ran out of numbers to add in b
            else
            {
                answer.add(0, a[a.length-i]+carry);
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

    static void printAnswer(ArrayList list)
    {
        list.forEach((n) -> System.out.print(n));
        System.out.println();
    }

    public static void main(String[] args) {
        int a[] = {4,2,3};
        int b[] = {1,1,1,8};
        ArrayList answer;
        answer = Addition.add(a, b);
        Addition.printAnswer(answer);
    }
}

