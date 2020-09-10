import java.util.*;
public class Product 
{
    Scanner sc = new Scanner(System.in);
    int number;
    int product;

    void doProduct()
    {
        product = 1;
        while (sc.hasNextInt())
        {
            number = sc.nextInt();
            if (number != 0)
            {
                product *= number;
            }
            else
            {
                System.out.println("The product is " + product);
                return;
            }
        }


    }

    public static void main(String[] args)
    {
        Product p = new Product();
        p.doProduct();;
    }
}
