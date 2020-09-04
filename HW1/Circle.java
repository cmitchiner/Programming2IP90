/**
 * Circle
 * by Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 */
import java.util.Scanner;
import java.lang.Math;

public class Circle { //Can you see this

    /**
    * This method first assigns various variables through user input,
    * used for calculating the distance from a user defined Cartesian coordinate
    * point to one of the circles, also user defined. It will check to see if the distance is
    * smaller than, borders (lies inside) both of these circles, and will accordingly
    * print the result, whether it lies in circle 1, or circle 2, both, or neither.
    * If the user has ill defined a circle through inputting a negative value,
    * the program returns an input error. 
    *
    * The reason this works is because circles have a constant radius, if we are 
    * able to calculate the distance from the center of the circle to a given point. We can then compare
    * that distance to the radius, if the distance is greater than the radius we know its not in the 
    * circle, and likewise if it is less than or equal to distance its on the border or inside the circle.
    */

    public void run() {

        //Assign Variables
        float midpointX1, midpointY1, circleRadius1, midpointX2, midpointY2, circleRadius2, pointX, pointY, distance1, distance2; 
        boolean inCircle1, inCircle2;
        Scanner sc = new Scanner(System.in);

        //Fill variables with user input
        midpointX1 = sc.nextFloat(); // Circle 1 vertex X coordinate

        midpointY1 = sc.nextFloat(); // Circle 1 vertex Y coordinate

        circleRadius1 = sc.nextFloat(); // Circle 1 radius
        midpointX2 = sc.nextFloat(); // Circle 2 vertex X coordinate
        midpointY2 = sc.nextFloat(); // Circle 2 vertex Y coordinate

        circleRadius2 = sc.nextFloat(); // Circle 2 radius

        pointX = sc.nextFloat(); // X Coordinate for Point

        pointY = sc.nextFloat(); // Y Coordinate for point

        sc.close(); // Prevent wasted resources

        if (circleRadius1 < 0 || circleRadius2 < 0 ) { // Make sure circle isn't ill defined
            System.out.println("input error");
            return;
        }
        
        // Check if point is inside, outside, or on the border of circle 1
        distance1 = (float) Math.sqrt(Math.pow((midpointX1 - pointX), 2) + Math.pow((midpointY1 - pointY), 2));

        // Check if point is inside, outside, or on the border of circle 2
        distance2 = (float) Math.sqrt(Math.pow((midpointX2 - pointX), 2) + Math.pow((midpointY2 - pointY), 2));
        
        if (distance1 <= circleRadius1) { // The point is inside circle 1 or on the border of circle 1
            inCircle1 = true;
        } else { // The point is outside of circle 1
            inCircle1 = false;
        }
        
        if (distance2 <= circleRadius2) { // The point is inside circle 2 or on the border of circle 2
            inCircle2 = true;
        } else { // The point is outside of circle 2
            inCircle2 = false;
        }

        // Finally, print out results
        if (inCircle1 && inCircle2) { // In both circles
            System.out.println("\nThe point lies in both circles.");
        } else if (inCircle2 && !inCircle1) { // In only circle 2
            System.out.println("\nThe point lies in the second circle.");
        } else if (inCircle1 && !inCircle2) { // In only circle 1
            System.out.println("\nThe point lies in the first circle.");
        } else { // Not in either circle
            System.out.println("\nThe point does not lie in either circle.");
        }

    }

    public static void main(String[] args) {
        (new Circle()).run();
    }
}