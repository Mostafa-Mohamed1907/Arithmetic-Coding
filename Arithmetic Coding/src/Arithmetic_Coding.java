import java.io.*;
import java.lang.*;
import java.util.*;

public class Arithmetic_Coding {

    static Map<Character, LowerAndUpper> Cumulative = new HashMap<>();
    static Map<Character, Double> Probability = new HashMap<>();

    public static double Compress(String data) {

        double prob;
        double counter;

        for (int i = 0; i < data.length(); i++)
        {
            counter = 1;
            for (int j = i+1; j < data.length(); j++)
            {
                if (data.charAt(i) == data.charAt(j))
                {
                    counter++;
                }
            }
            prob = (counter/data.length());

            //Handling: Same Key -- ex: a a , value 0.2 not 0.1
            if (Probability.containsKey(data.charAt(i)) == false)
                Probability.put(data.charAt(i), prob);

            counter = 0;
            prob = 0;
        }
        for (Character var : Probability.keySet())
        {
            System.out.println(var + " " + Probability.get(var));
        }
        System.out.println("====================================");

        double temp = 0;
        for(Character var : Probability.keySet())
        {
            LowerAndUpper lowerAndUpper = new LowerAndUpper();
            lowerAndUpper.Lower = temp;
            lowerAndUpper.Upper = temp + Probability.get(var);
            System.out.println(var + "   "+ lowerAndUpper.Lower + "   " + lowerAndUpper.Upper);
            Cumulative.put(var, lowerAndUpper);
            temp += Probability.get(var);
        }
        System.out.println("====================================");

        double lower = 0;
        double upper = 1;
        double range = 1;
        double Code;
        for (int i = 0; i < data.length(); i++)
        {
            for(Character var : Probability.keySet())
            {
                {
                    upper = lower + range * Cumulative.get(data.charAt(i)).Upper;
                    lower = lower + range * Cumulative.get(data.charAt(i)).Lower;
                    range = upper - lower;
                    break;
                }
            }
        }
        Code = ((lower + upper) / 2);
        System.out.println(Code);
        System.out.println("====================================");

        return Code;
    }

    public static void DeCompress(int len, double code) {

        String Decompressed_Stream = "";
        double range = 1;
        double upper = 1;
        double lower = 0;
        double Expanded_Code = code;
        for (int i = 0; i < len; i++)
        {
            for(Character var : Cumulative.keySet())
            {
                if (Expanded_Code >= Cumulative.get(var).Lower && Expanded_Code <= Cumulative.get(var).Upper) {
                    Decompressed_Stream += var + "";
                    lower = Cumulative.get(var).Lower;
                    upper = Cumulative.get(var).Upper;
                    break;
                }
            }
            range = upper - lower;
            Expanded_Code = (Expanded_Code - lower) / range;
        }
        System.out.println(Decompressed_Stream);
    }

    public static void main(String[] args) {

        Scanner var = new Scanner(System.in);
        String symb = var.nextLine();
        double code = 0;
        code = Compress(symb);
        DeCompress(symb.length(), code);

    }
}
