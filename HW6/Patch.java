/*
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part Patch
 * 
 * @author Charles Mitchiner (1574531)
 * @author Samir Saidi (1548735)
 * assignment group 97
 * 
 * assignment copyright Kees Huizing
 */
import java.util.Random;
import java.util.ArrayList;

class Patch {

    private Random rand = new Random();
    private boolean strategy;
    private double score;
    private ArrayList<Patch> neighbors; 

    public Patch(){
        strategy = rand.nextBoolean();
        score = 0.0;
        neighbors = new ArrayList<Patch>();
    }
    
    // returns true if and only if patch is cooperating
    boolean isCooperating() {
        if (strategy)
        {
            return true;
        } else {
            return false;
        }
    }
    
    // set strategy to C if isC is true and to D if false
    void setCooperating(boolean isC) {
        if (isC)
        {
            strategy = true;
        } else {
            strategy = false;
        }
    }
    
    // change strategy from C to D and vice versa
    void toggleStrategy() {
        if (strategy)
        {
            strategy = false;
        } else {
            strategy = true;
        }
    }
     
    // return score of this patch in current round
    double getScore() {
        return score;
    }
    // sets score to the passed paramater
    void setScore(double score) {
        this.score = score;
    }
    //Add a patch to neighbors array
    void addNeighbor(Patch p){
        neighbors.add(p);
    }
    //Clear neighbors array
    void clearNeighbors() {
        neighbors.clear();
    }
    //Get a specific neighbor at a paramter index
    Patch getNeighbor(int index) {
        if (index < neighbors.size()) {
            return neighbors.get(index);
        }
        return null;
    }
    //Get size of Neighbors Array
    int getNeighborsArraySize() {
      return neighbors.size();  
    }
   
    void chooseStrategyBasedOnNeighbors() {
        Double maximum = score;
        Patch maximumPatch = null;
        ArrayList<Patch> list = new ArrayList<Patch>();

        //Find the maximum score, if this patch has the highest then maximumPatch will be null
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getScore() > maximum){
                maximum = neighbors.get(i).getScore();
                maximumPatch = neighbors.get(i);
            }
        }
        
        for (int i = 0; i < neighbors.size(); i++) {
            if (maximum == score && maximumPatch != null) { //If maxium equals score and maxiumPatch isnt null, they have the same score
                list.add(maximumPatch);
                list.add(this);
            }
            if (neighbors.get(i).getScore() == maximum && neighbors.get(i) != maximumPatch) { //Check all the neighbors besides the maximumPatch to see if they are also equal
                list.add(neighbors.get(i));
            }
        }

        if (list.isEmpty() && maximumPatch != null){ //If the list is empty and maximumPatch isnt null then we only have one maximum
            setCooperating(maximumPatch.isCooperating());
        } else if (!list.isEmpty()) {               //If the list isnt empty, then we have multiple maxiums, and we need to pick a random one
            int whichOne = rand.nextInt(list.size());
            setCooperating(list.get(whichOne).isCooperating());
            
        } else {//Otherwise this patch's score is the highest
            setCooperating(isCooperating());
        }   
    }
}
