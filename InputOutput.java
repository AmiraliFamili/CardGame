import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class InputOutput {

    public int getPlayerNumber() {
        Scanner scan = new Scanner(System.in);

        System.out.print("\nPlease enter the number of players : ");
        String playerNum = scan.nextLine();

        try {
            int num = Integer.parseInt(playerNum);
            if (num < 1) {
                System.out.println("Please enter a positive Integer as player number: \n");
                return getPlayerNumber();
            } else {
                return num;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number as number of players : \n");
            return getPlayerNumber();
        }
    }

    public LinkedList<Integer> getPackFilePath() {
        LinkedList<Integer> pack = new LinkedList<Integer>();
        System.out.print("\nPlease enter location of pack to load: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        Path path = Paths.get(filePath);

        if (Files.exists(path)) {
            pack = readInputPack(filePath);
        } else {
            System.out.println("Entered location is Not Valid !");
            getPackFilePath();
        }
        return pack;
    }

    public LinkedList<Integer> readInputPack(String filePath) {

        LinkedList<Integer> pack = new LinkedList<Integer>();

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                // Parse each line as an integer and add it to the list
                int value = Integer.parseInt(line.trim());
                pack.add(value);
            }
        } catch (Exception e) {
            System.out.println("Pack file does not have the right format please enter another file name");
            getPackFilePath();
        }
        return pack;
    }

    // instance attributes

    private int playerNumber;
    private LinkedList<Card> hand = new LinkedList<Card>(); // used prevously: static LinkedList<Integer> hand = new
                                                            // LinkedList<Integer>();
    private Card pickupLeft; // left was: discardBottom
    private Card discardRight; // right was insertTop
    private CardGame game; // might not be "game" but something else

    // constructor:
    // -------------------------------------------------------------------------

    InputOutput(int playerNumber, Card discard, Card pickup, CardGame game, LinkedList<Card> hand) throws IOException {
        this.playerNumber = playerNumber;
        this.discardRight = discard;
        this.pickupLeft = pickup;
        this.game = game;
    }

    // setters and getters:
    // -----------------------------------------------------------------------------

    // METHODS:
    // ---------------------------------------------------------------------------

    // ------------------main methods for writting to the output
    // --------------------
    /*
     * 
     * we need to have a meeting on my code to help get some implemnation with your
     * code as i found it hard to understand
     */

    // method to create files in the correct location

    public InputOutput() {
    }

    public void SetupOutputFile() { // look into then name of the method if that matters
        try {
            File newFile = new File("player" + playerNumber + "_output.txt"); // use this to create code
            newFile.createNewFile();
            System.out.println("File created: " + newFile.getName());
        } catch (IOException e) {
            System.out.println("error occurred while trying to create file");
            e.printStackTrace();
        }
    }

    /**
     * @see please fix the errors
     *      //method to write into new file the inital hand
     *      public void initialHand(){
     *      String initialHand = "";
     *      for (Card card:hand) {
     *      initialHand += " " + card.getHand();//work on this
     *      }
     * 
     *      try{
     *      FileWriter writer = new FileWriter("player" + playerNumber +
     *      "_output.txt", true);
     *      writer.write("player " + playerNumber + " initial hand " + initialHand +
     *      "\n");
     *      writer.close();
     *      //add part of function to gte correct hand -- need more work
     * 
     *      } catch (IOException e) {
     *      System.out.println("error occurred in initalhand method");
     *      e.printStackTrace();
     *      }
     *      }
     * 
     *      public void discardAndPickUp(){
     *      Card card = new Card(decks, players);
     *      int selectedCard = game.getCard(hand);
     *      hand.add(pickupLeft);
     *      hand.remove(selectedCard);
     * 
     *      try {
     * 
     *      FileWriter writer = new FileWriter("player" + playerNumber +
     *      "output.txt", true);
     *      writer.write("player " + playerNumber + " draws a " +
     *      hand.add(pickupLeft.getCard()) + " from deck \n" //
     *      leftCardDeck.getDeckNumber() );
     *      writer.write("\nplayer " + playerNumber + " discards a " +
     *      hand.remove(selectedCard) + " to deck \n" //
     *      rightCardDeck.getDeckNumber() ); // look at sorting this part and get
     *      rifd and think of implmentation of deck
     *      writer.close();
     *      } catch (IOException e) {
     *      System.out.println("An error occurred during writting of draw/discard card");
     *      e.printStackTrace();
     *      }
     * 
     *      }
     * 
     * 
     *      public void currentHand(){
     *      String currentHand = "";
     *      for (Card card:hand) {
     *      currentHand += " " + card.getCard();//work on this
     *      }
     * 
     *      try {
     *      FileWriter writer = new FileWriter("player" + playerNumber +
     *      "output.txt", true);
     *      writer.write("player " + playerNumber + " currents hand " + currentHand
     *      +"\n"); // look at sorting this part and get rifd and think of
     *      implmentation of deck
     *      writer.close();
     *      } catch (IOException e) {
     *      System.out.println("An error occurred during writting of draw/discard card");
     *      e.printStackTrace();
     *      }
     *      }
     * 
     * 
     * 
     *      public void winner(){
     *      try{
     *      FileWriter writer = new FileWriter("player"+ playerNumber
     *      +"_output.txt", true);
     *      writer.write("\nplayer " + playerNumber + " wins\n");
     *      writer.write("player " + playerNumber + " exits\n");
     *      writer.close();
     * 
     * 
     *      } catch (IOException e) {
     *      System.out.println("An error occurred writing this player's win to a file.");
     *      e.printStackTrace();
     * 
     *      }
     *      }
     * 
     * 
     * 
     *      public void finalhand(){
     *      String finalHand = "";
     *      for (Card card:hand) {
     *      finalHand += " " + card.getHand();//work on this
     *      }
     *      try {
     *      FileWriter writer = new FileWriter("player"+ playerNumber
     *      +"_output.txt", true);
     *      writer.write("player " + playerNumber + " final hand " + finalHand +
     *      "\n");
     *      writer.close();
     * 
     *      } catch (IOException e) {
     *      System.out.println("An error occurred writing players final hand");
     *      e.printStackTrace();
     *      }
     *      }
     * 
     * 
     */

    // ------------------ other methods ---------------------

}
