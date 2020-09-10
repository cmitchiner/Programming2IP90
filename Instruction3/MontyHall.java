import java.util.Random;
public class MontyHall
{
    public static void main(String[] args)
    {
		int switchWins = 0;
		int stayWins = 0;
		Random gen = new Random();
        for (int plays = 0; plays < 40000; plays++ )
        {
			boolean[] doors = {false,false,false}; //0 is a loss, 1 is a win
            doors[gen.nextInt(3)] = true;//put a winner in a random door
			int choice = gen.nextInt(3); //pick a door, any door
			int shown; //the shown door
            do
            {
				shown = gen.nextInt(3);
			//don't show the winner or the choice
			}while(doors[shown] == true || shown == choice);
 
            //if you won by staying, count it
            if (doors[choice])
            {
                stayWins++;
            }
 
            //the switched (last remaining) door is (3 - choice - shown), because 0+1+2=3
            //If the index on the doors array is 0, you loose
            if (doors[3-choice-shown])
            {
                switchWins++;
            }
		}
		System.out.println("Switching wins " + switchWins + " times.");
		System.out.println("Staying wins " + stayWins + " times.");
	}
}