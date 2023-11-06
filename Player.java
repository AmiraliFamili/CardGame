import java.util.ArrayList;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;


public class Player {

    final private int playerNumber;
    static ArrayList<Player> players = new ArrayList<>();
    boolean firstT;

    
    //to get player number
    
    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        players.add(this);
        this.firstT = true;
    }

    //more work needs to be done between line 23-50
    public void initalHand(int playerNumber) {
    }
    

     public void getHands(int playerNumber) {

    }

    public void drawCard(int playerNumber) {
    
        // Implementation here need to be added so code undertsand that a player has drawn card to listen to card.java
    }
 
    public void discardCard(int cardDiscarded) {
       // same here i dont unsetrdan how be simple code need to be added to it knows when a player has discarded a cad from the
       //main card game
    }

   
 
    public boolean hasWon() {
        return false;
        // Implementation here
    }

    

//this section is where the code will use to create logs for output/logs folder
    public void logAction(String action) {
        try {
            String directoryPath = "output/logs/";
            BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath + "player" + playerNumber + "_output.txt", true));
            writer.append(action);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* 
        try {
            String directoryPath = "output/logs";
            File directory = new File(directoryPath);
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath + "player" + playerNumber + "_output.txt", true));
            writer.append(action);
            writer.newLine();
            writer.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
*/
        
    }

    public void startGame() {
        //start of the game requires this (got the "dealHands" from cardGame.java)
        logAction("Player " + playerNumber + " innital hand is ");

        //this logs the what cards player has drawn (needs to find correct function name fpr cardDrawn)
        logAction("Player " + playerNumber + " has drawn a ");
        //this logs the what cards player discarded (got "getCard" from card.java)
        logAction("Player " + playerNumber + " has discarded a " );
        //this logs the what cards player currently has (got "getHands" form cardGAme.java)
        logAction("Player " + playerNumber + "'s is " );
        //action taken when there is a winner
        if (hasWon()){
            logAction("Player " + playerNumber + " wins");
            logAction("Player" + playerNumber + " exits");
            logAction("Player " + playerNumber + " final hand " );
            }
    }



}



/*
 * EXTRA NOTES FOR IMPLMENTAION 
 * implements the Callable interface, which is part of Java's concurrency API and is often used in conjunction with threads.
    interface similar to Runnable interface, but it allows for a return value and can throw exceptions. 
    In the Player class, the call() method is implemented, which is the method that will be called when a Player object is used with an executor service, 
    such as ExecutorService.submit(Callable<T> task)
 */
