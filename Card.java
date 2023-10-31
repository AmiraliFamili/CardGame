
import java.util.*;

/*
 * 
 * you can use super() to activate all classes simultaneously and revive the system's state 
 */
public class Card extends Player {
    final private int timeslice = 90;
    final private int deckNumber;

    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private LinkedList<Integer> pack;

    public Card(int playerNumber) {
        super(playerNumber);
        this.deckNumber = playerNumber;
        createPack(playerNumber);
    }

    public void emptyPack() {
        this.pack = new LinkedList<Integer>();
    }

    public LinkedList<Integer> getPack() {
        return this.pack;
    }

    public LinkedList<Integer> createPack(int n) {
        if (this.pack == null) {
            emptyPack();
        }
        Random random = new Random();
        for (int i = 1; i <= (n * 2); i++) {
            for (int j = 1; j <= 4; j++)
                pack.add(i);
        }
        Collections.shuffle(pack);
        return pack;
    }

    public int get1FromPack() {
        int card = this.pack.poll();
        return card;
    }

    public void setPlayers() {
        for (int i = 0; i < deckNumber; i++) {
            this.players.add(new LinkedList<Integer>());
        }
    }

    public void setDecks() {
        for (int i = 0; i < deckNumber; i++) {
            this.decks.add(new LinkedList<Integer>());
        }
    }

    public LinkedList<LinkedList<Integer>> getPlayers() {
        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealHands(LinkedList<Integer> pack) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < deckNumber; j++) {
                LinkedList<Integer> playerN = this.players.get(j);
                int card = get1FromPack();
                playerN.add(card);
            }
        }

        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealDecks(LinkedList<Integer> pack) {

        int index = 0;
        for (Integer card : pack) {
            LinkedList<Integer> deck = this.decks.get(index);
            deck.add(card);
            index = (index + 1) % deckNumber;
        }
        return decks;
    }

    // try if you can get from the deck n+1 if something wen't wrong take from the
    // first deck

    public String handToString(LinkedList<Integer> hand) {
        String sHand = "";
        if (hand.size() > 4) {
            System.out.println("Wrong value for hand Detected");
        }
        for (Integer card : hand) {
            sHand = sHand + Integer.toString(card) + " ";
        }

        return sHand;
    }

    public int getCard(LinkedList<Integer> hand) {
        Scanner scan = new Scanner(System.in);
        String playerNum = scan.nextLine();

        try {
            int num = Integer.parseInt(playerNum);
            for (Integer cards : hand) {
                if (num == cards) {
                    return num;
                }
            }
            System.out.println("Please enter the card you wish to remove : " + hand);
            System.out.println("Entered Value : " + num + "\n");
            return getCard(hand);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Integer from hand : " + hand);
            System.out.println("Entered Value : " + playerNum + "\n");
            return getCard(hand);
        }
    }

    public boolean validateCard(int card, LinkedList<Integer> hand) {
        for (Integer cards : hand) {
            if (card == cards) {
                return true;
            }
        }
        return false;
    }

    private Boolean win = false;

    public void startGame() {
        int round = 0;
        while (!win) {
            int Card;
            LinkedList<Integer> player = this.players.get(round % deckNumber);
            LinkedList<Integer> insertTop = this.decks.get(round % deckNumber);
            LinkedList<Integer> discardBottom = this.decks.get((round + 1) % deckNumber);


            System.out.println(
                    "player_" + round + 1 % deckNumber + " remove one card from Hand : " + handToString(player));
            Card = getCard(player);
            System.out.println("1.Card : " + Card);
            System.out.println("1.player hand : " + player);
            System.out.println("1.discardBottom : " + discardBottom);
            System.out.println("1.insertTop : " + insertTop);

            int index = player.indexOf(Card);
            // Check if the card is found in the player's hand
            player.remove(index);
            player.add(insertTop.removeFirst());
            discardBottom.add(Card);
        
            System.out.println("2.player hand : " + player);
            System.out.println("2.discardBottom : " + discardBottom);
            System.out.println("2.insertTop : " + insertTop);

            // round robin
            // Threads
            // GUI
            // get input from terminal ?
            // should we write to the file here ?

            if (player.get(0) == player.get(1) && player.get(1) == player.get(2) && player.get(2) == player.get(3)) {
                win = true;
                
            } else {
                round++;
            }

        }

    }

    public void playerWon(){

    }




    /*
     * import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        int numPlayers = 4;
        // Initialize game state, decks, players, etc.

        for (int i = 0; i < numPlayers; i++) {
            int playerIndex = i;
            Thread playerThread = new Thread(() -> {
                while (!gameIsOver()) {
                    // Player's turn
                    System.out.println("Player " + playerIndex + ", it's your turn.");
                    // Read player input and perform actions based on game rules
                    // Use synchronization mechanisms to coordinate turns
                }
            });
            playerThread.start();
        }
    }

    private static boolean gameIsOver() {
        // Implement the game end condition
        // Return true when a player wins, signaling the end of the game
        return false;
    }
}

     */
}