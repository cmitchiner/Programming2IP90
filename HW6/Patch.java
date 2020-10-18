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
        } 
        return false;
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
    void setPreviousStrategy(boolean passedStrategy) {
        previousStrategy = passedStrategy;
    }
    //This will be used in the buildCells method to check if the past strategy equals the next
    boolean getPreviousStrategy(){
        return previousStrategy;
    }

    void chooseStrategyBasedOnNeighborsAlternative() {
        Double maximum = score;
        Patch maximumPatch = null;
        boolean foundAnother = false;
        ArrayList<Patch> list = new ArrayList<Patch>();

        //Find the maximum score, if this patch has the highest then maximumPatch will be null
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getScore() > maximum){
                maximum = neighbors.get(i).getScore();
                maximumPatch = neighbors.get(i);
            }
        }
        if (maximumPatch == null) { //If this patch has the highestScore, using the alternitive rule we keep it the same
            this.setCooperating(this.isCooperating());
        } else { //If this patch doesnt have the highest score
            for (int i = 0; i < neighbors.size(); i++) {//Check if any other neighbors share that same high score
                if (neighbors.get(i).getScore() == maximum && neighbors.get(i) != maximumPatch) { 
                    list.add(neighbors.get(i)); //If they do add them to a list
                    foundAnother = true;
                }
            }
            if (foundAnother) { 
                list.add(maximumPatch); //Make sure the original max is in the list
                int pickRandom = rand.nextInt(list.size()); //Then pick a random index on that list
                setCooperating(list.get(pickRandom).isCooperating());
            } else {
                setCooperating(maximumPatch.isCooperating()); //Otherwise maximumPatch has the highest score
            }
        }
    }
   
    void chooseStrategyBasedOnNeighbors() {
        Double maximum = score;
        Patch maximumPatch = null;
        boolean foundAnother = false;
        ArrayList<Patch> list = new ArrayList<Patch>();

        //Find the maximum score, if this patch has the highest then maximumPatch will be null
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getScore() > maximum) {
                maximum = neighbors.get(i).getScore();
                maximumPatch = neighbors.get(i);
            }
        }
        if (maximumPatch == null) { //If this patch has the highestScore, use the alternitive rule and keep it the same
            for (int i = 0; i < neighbors.size(); i++) {//Check if any other neighbors share that same high score
                if (neighbors.get(i).getScore() == maximum) { 
                    list.add(neighbors.get(i)); //If they do add them to a list
                    foundAnother = true;
                }
            }
            if (foundAnother) {
                list.add(this); //Make sure not to forget this patch in the list
                int pickRandom = rand.nextInt(list.size()); //Pick randomly 
                setCooperating(list.get(pickRandom).isCooperating());
            }
        } else {//If this patch doesnt have the highest score
            for (int i = 0; i < neighbors.size(); i++) //Check if any other neighbors share that same high score
            {
                if (neighbors.get(i).getScore() == maximum && neighbors.get(i) != maximumPatch) { 
                    list.add(neighbors.get(i)); //If they do add them to a list
                    foundAnother = true;
                }
            }
            if (foundAnother) {//Same as above
                list.add(maximumPatch);
                int pickRandom = rand.nextInt(list.size());
                setCooperating(list.get(pickRandom).isCooperating());
            } else {
                setCooperating(maximumPatch.isCooperating());
            }
        }
    }
}
