/**
 * PoemDingus -- part of HA RandomArtist
 * example of a complicated Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

public class PoemDingus extends Dingus {

    ArrayList<String> poem = new ArrayList<String>();
    String[] article = {"the", "a", "one", "some", "any"}; // list of words
    String[] noun = {"boy", "girl", "dog", "town", "car"};
    String[] verb = {"drove", "jumped", "ran", "walked", "skipped"};
    String[] preposition = {"to", "from", "over", "under", "on"};
    protected int length, width;
    protected boolean filled; //true: filled, false: outline

    public String generateSentence(){
        int rand1 = (int) Math.floor(Math.random() * 5); // generates random numbers from 0 to 4
        int rand2 = (int) Math.floor(Math.random() * 5); // so we can index into each array (array[0] to array[4])
        int rand3 = (int) Math.floor(Math.random() * 5);
        int rand4 = (int) Math.floor(Math.random() * 5);
        int rand5 = (int) Math.floor(Math.random() * 5);
        int rand6 = (int) Math.floor(Math.random() * 5);
        return Character.toUpperCase(article[rand1].charAt(0)) + article[rand1].substring(1) + " " + noun[rand2] + " " + verb[rand3] + " " + preposition[rand4] + " " + article[rand5] + " " + noun[rand6] + ".\n";
    }

    public void generateStory(){
        for (var i=1; i<=15; i++) {
            poem.add(generateSentence());  // loops 20 times to create 20 sentences upon button click
        }
    }

    public PoemDingus(int maxX, int maxY){
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        int poemType = random.nextInt(4);
        switch(poemType){
            case 0: americanLife();
            break;
            case 1: sorry();
            break;
            case 2: generateStory();
            break;
            case 3: Shakespeare();
            break;
        }
        // initialize randomly the RectangleDingus properties, i.e., radius and filledness
        length = random.nextInt(maxX/2);
        width = random.nextInt(maxY/3);
        filled = random.nextBoolean();
    }

    void americanLife(){
        poem.add("I'm drinkin' a soy latte, I get a double shottie");
        poem.add("It goes right through my body and you know I'm satisfied!");
        poem.add("I drive my mini cooper and I'm feeling super-duper");
        poem.add("Yo, they tell me I'm a trooper and you know I'm satisfied!");
        poem.add("I do yoga and pilates, and the room is full of hotties");
        poem.add("So I'm checking out their bodies, and you know I'm satisfied!");
        poem.add("I'm diggin' on the isotopes, this metaphysics shit is dope");
        poem.add("And if all this can give me hope, you know I'm satisfied!");
        poem.add("I got a lawyer, and a manager, an agent and a chef");
        poem.add("Three nannies, an assistant and a driver and a jet");
        poem.add("A trainer and a butler and a bodyguard or five");
        poem.add("A gardener and a stylist, do you think I'm satisfied?");
    }

    void sorry(){
        poem.add("Je suis désolé");
        poem.add("Lo siento");
        poem.add("Ik ben droevig");
        poem.add("Sono spiacente");
        poem.add("Perdóname");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("I've heard it all before, I've heard it all before");
        poem.add("Gomen nasai");
        poem.add("Mujhe maaf kardo");
        poem.add("Przepraszam");
        poem.add("Slihah");
        poem.add("Forgive me");
    }

    void Shakespeare(){
        poem.add("Two households, both alike in dignity,");
        poem.add("In fair Verona, where we lay our scene,");
        poem.add("From ancient grudge break to new mutiny,");
        poem.add("Where civil blood makes civil hands unclean.");
        poem.add("From forth the fatal loins of these two foes");
        poem.add("A pair of star-cross'd lovers take their life;");
        poem.add("Whose misadventured piteous overthrows");
        poem.add("Do with their death bury their parents' strife.");
        poem.add("The fearful passage of their death-mark'd love,");
        poem.add("And the continuance of their parents' rage,");
        poem.add("Which, but their children's end, nought could remove,");
        poem.add("Is now the two hours' traffic of our stage;");
        poem.add("The which if you with patient ears attend,");
        poem.add("What here shall miss, our toil shall strive to mend.");
    }

    @Override
    void draw(Graphics g){
        g.setColor(color);
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        int index = (int)(Math.random() * (fontNames.length - 1));
        String fontName = fontNames[index];
        int fontSize = random.nextInt(((40 - 28) + 1) + 28);
        Font f = new Font(fontName, Font.PLAIN, fontSize);
        g.setFont(f);
        int lineSpacing = 50;
        
        for (String line : poem){
            g.drawString(line, 25, lineSpacing);
            lineSpacing += 25;   
        }
    }
}
