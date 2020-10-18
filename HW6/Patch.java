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
    private boolean previousStrategy;

    public Patch(){
        strategy = rand.nextBoolean();
        score = 0.0;
        neighbors = new ArrayList<Patch>();
        previousStrategy = strategy;
    }
    
    // returns true if and only if patch is cooperating
    boolean isCooperating() {
        if (strategy) {
            return true;
        } else {
            return false;
        }
    }
    
    // set strategy to C if isC is true and to D if false
    void setCooperating(boolean isC) {
        if (isC) {
            strategy = true;
        } else {
            strategy = false;
        }
    }
    
    // change strategy from C to D and vice versa
    void toggleStrategy() {
        if (strategy) {
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
    //This will be used in the step method, to set the previous strategy before calling the chooseStrategy method
    void setPreviousStrategy(boolean passedStrategy)
    {
        previousStrategy = passedStrategy;
    }
    //This will be used in the buildCells method to check if the past strategy equals the next
    boolean getPreviousStrategy(){
        return previousStrategy;
    }

    void chooseStrategyBasedOnNeighborsAlternative()
    {
        Double maximum = score;
        Patch maximumPatch = null;
        ArrayList<Patch> list = new ArrayList<Patch>();
        previousStrategy = isCooperating();

        //Find the maximum score, if this patch has the highest then maximumPatch will be null
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getScore() >= maximum){
                maximum = neighbors.get(i).getScore();
                maximumPatch = neighbors.get(i);
            }
        }
        
        for (int i = 0; i < neighbors.size(); i++) {
            if (maximum == score && maximumPatch != null) { //If this if statement gets hit, it means this patches score is a max and one of its neighbors has the same max
                setCooperating(isCooperating());            //Using the alternative rule we should just use this patches score, so we set it and return
                return;
            } else if (maximum != score && neighbors.get(i).getScore() == maximum && neighbors.get(i) != maximumPatch) { //Check all the neighbors besides the maximumPatch to see if they are also equal
                list.add(neighbors.get(i));
            }
        }

        if (list.isEmpty() && maximumPatch != null) { //If the list is empty and maximumPatch isnt null then we only have one maximum
            setCooperating(maximumPatch.isCooperating());
        } else if (!list.isEmpty()) {   //If the list isnt empty, then we have multiple maxiums, however this patch is not one of them
            int whichOne = rand.nextInt(list.size());
            setCooperating(list.get(whichOne).isCooperating());
        } else if (maximumPatch == null) { //This patch has a unique higher score than all of its neighbors
            setCooperating(isCooperating());
        }
    }
   
    void chooseStrategyBasedOnNeighbors() {
        Double maximum = score;
        Patch maximumPatch = null;
        ArrayList<Patch> list = new ArrayList<Patch>();
        previousStrategy = isCooperating();

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
        } else if (!list.isEmpty()) {               //If the list isnt empty, then we have multiple maximums, and we need to pick a random one
            int whichOne = rand.nextInt(list.size());
            setCooperating(list.get(whichOne).isCooperating());
            
        } else {//Otherwise this patch's score is the highest
            setCooperating(isCooperating());
        }   
    }
}
