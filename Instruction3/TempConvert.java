import java.util.*;
public class TempConvert 
{

    public void calcTemp()
    {
        String choice = " ";
        Double originalTemp = 0.0;
        Double newTemp = 0.0;
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Give a temperature in Celsius or Fahrenheit or type q to quit");
            System.out.print("> ");
            if (!sc.hasNextDouble())
            {
                return;
            }
            originalTemp = sc.nextDouble();
            choice = sc.nextLine();
            if (choice.contains("F"))
            {
                newTemp = (originalTemp - 32)/1.8;
                System.out.println(originalTemp + " degrees Fahrenheit = " + newTemp + " degrees Celsius\n");
            }
            else if (choice.contains("C"))
            {
                newTemp = (originalTemp * 1.8) + 32;
                System.out.println(originalTemp + " degrees Celsius = " + newTemp + " degrees Fahrenehit\n");
            }
            else
            {
                System.out.println("Error");
            }
        }
        // commentasdf

    }
    public static void main(String[] args)
    {
        TempConvert t = new TempConvert();
        t.calcTemp();
    }
}
