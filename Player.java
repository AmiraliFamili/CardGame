import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;





public class Player{

//instance attributes

    private int playerNumber;
    private LinkedList<Card> hand = new LinkedList<Card>();    //used prevously: static LinkedList<Integer> hand = new LinkedList<Integer>();
    private Card pickupLeft;   //left  was: discardBottom
    private Card discardRight;      //right  was insertTop
    private CardGame game;       //might not be "game" but something else
   


//constructor:
//   -------------------------------------------------------------------------



    Player(int playerNumber, Card discard, Card pickup, CardGame game, ArrayList<Card> hand) throws IOException{
        this.playerNumber = playerNumber;
        this.discardRight = discard;
        this.pickupLeft = pickup;
        this.game = game;

       
    
        
    }



//setters and getters:
// -----------------------------------------------------------------------------

    

//METHODS:
//   ---------------------------------------------------------------------------

   // ------------------main methods for writting to the output --------------------
/*
 * 
 * we need to have a meeting on my code to help get some implemnation with your code as i found it hard to understand
 */


    //method to create files in the correct location

    public void SetupOutputFile(){        //look into then name of the method if that matters
        try{
            File newFile = new File("player" + playerNumber + "_output.txt"); // use this to create code
            newFile.createNewFile();
            System.out.println("File created: " + newFile.getName());
        } catch (IOException e) {
            System.out.println("error occurred while trying to create file");
            e.printStackTrace();
        }
    }
    

    //method to write into new file the inital hand
    public void initialHand(){
        String initialHand = "";
        for (Card card:hand) {
            initialHand += " " + card.getHand();//work on this
        }

        try{
            FileWriter writer = new FileWriter("player" + playerNumber + "_output.txt", true);
            writer.write("player " + playerNumber + " initial hand " + initialHand + "\n");
            writer.close();
            //add part of function to gte correct hand -- need more work 

        } catch (IOException e) {
            System.out.println("error occurred in initalhand method");
            e.printStackTrace();
        }
    }

    public void discardAndPickUp(){
        Card card = new Card(decks, players);
        int selectedCard = game.getCard(hand);
        hand.add(pickupLeft);
        hand.remove(selectedCard);
        
        try {
            
            FileWriter writer = new FileWriter("player" + playerNumber + "output.txt", true);
            writer.write("player " + playerNumber + " draws a " + hand.add(pickupLeft.getCard()) + " from deck \n" /*+ leftCardDeck.getDeckNumber()*/ );
            writer.write("\nplayer " + playerNumber + " discards a " + hand.remove(selectedCard) + " to deck \n" /*+ rightCardDeck.getDeckNumber() */);   // look at sorting this part and get rifd and think of implmentation of deck
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred during writting of draw/discard card");
            e.printStackTrace();
        }
    
    }


    public void currentHand(){
        String currentHand = "";
        for (Card card:hand) {
            currentHand += " " + card.getCard();//work on this
        }

        try {
            FileWriter writer = new FileWriter("player" + playerNumber + "output.txt", true);
            writer.write("player " + playerNumber + " currents hand " + currentHand +"\n");   // look at sorting this part and get rifd and think of implmentation of deck
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred during writting of draw/discard card");
            e.printStackTrace();
        }
    }



    public void winner(){
        try{
        FileWriter writer = new FileWriter("player"+ playerNumber +"_output.txt", true);
            writer.write("\nplayer " + playerNumber + " wins\n");
            writer.write("player " + playerNumber + " exits\n");
            writer.close();


        } catch (IOException e) {
            System.out.println("An error occurred writing this player's win to a file.");
            e.printStackTrace();

        }
    }



    public void finalhand(){
        String finalHand = "";
        for (Card card:hand) {
            finalHand += " " + card.getHand();//work on this
        }
        try {
            FileWriter writer = new FileWriter("player"+ playerNumber +"_output.txt", true);
            writer.write("player " + playerNumber + " final hand " + finalHand + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred writing players final hand");
            e.printStackTrace();
        }
    }



            // ------------------  other  methods  ---------------------
    
    
        
        
               

    







}

    

/*for later use
 * 
 *  // creates a string of the starting hand
        String initialHand = "player " + id + " initial hand";
        for (Card c:hand) {
            initialHand += " " + card.getValue();
        }
 */























/*
 * EXTRA NOTES FOR IMPLMENTAION 
 * implements the Callable interface, which is part of Java's concurrency API and is often used in conjunction with threads.
    interface similar to Runnable interface, but it allows for a return value and can throw exceptions. 
    In the Player class, the call() method is implemented, which is the method that will be called when a Player object is used with an executor service, 
    such as ExecutorService.submit(Callable<T> task)
 */





 /*
  * to get the player output
  need output file for player an card deck

  for each one you need an attribute which is an output file for the objects

  in the constructor need to inistilise the outoutfile

  where card 
  */