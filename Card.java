
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 
 * you can use super() to activate all classes simultaneously and revive the system's state
 * 
 * player and card class should take two nested lists created by the card game and they should have their own instance 
 * of card and player or hand 
 * 
 *  
 */
public class Card {
    final private int timeslice = 90;
    final private int deckNumber;

    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private LinkedList<Integer> pack;
    private LinkedList<Integer> deck;

    public Card(LinkedList<LinkedList<Integer>> decks, LinkedList<LinkedList<Integer>> players) {
        this.decks = decks;
        this.deckNumber = decks.size();
        this.players = players;
    }

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
        String card = scan.nextLine();

        try {
            int num = Integer.parseInt(card);
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
            System.out.println("Entered Value : " + card + "\n");
            return getCard(hand);
        }
    }

    /*
     * maybe we can use left deck and right deck of the player to use multithreading
     * without any conflict
     * 
     * if we use synchronized like this then we aren't using multithreading we are
     * playing sequentially
     * if we don't use them the project will crash instantly because all threads are
     * trying to access the same shared resource
     * 
     * should we separate the players and decks ? how ?
     */

    private Boolean win = false;
/*

public void startGame() {
        ExecutorService executorService = Executors.newFixedThreadPool(players.size());
        executorService.execute(() -> {
            while (!win) {
                for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
                    // Make sure to use synchronization mechanisms to coordinate turns
                    synchronized (players) { // now that it's in synchronized is it running in parallel ?
                        LinkedList<Integer> player = this.players.get(playerIndex % deckNumber);
                        LinkedList<Integer> insertTop = this.decks.get(playerIndex % deckNumber);
                        LinkedList<Integer> discardBottom = this.decks.get((playerIndex + 1) % deckNumber);

                        System.out.println(
                                "player_" + (playerIndex + 1) % deckNumber + " remove one card from Hand : "
                                        + handToString(player) + "\n");
                        int Card = getCard(player);
                        System.out.println("1.Card : " + Card);
                        System.out.println("1.player hand : " + player);
                        System.out.println("1.discardBottom : " + discardBottom);
                        System.out.println("1.insertTop : " + insertTop);

                        int index = player.indexOf(Card);

                        player.remove(index);
                        synchronized (decks) {
                            // with this block all actions to decks are monitored and happen one at a time
                            player.add(insertTop.removeFirst());
                            discardBottom.add(Card);
                        }
                        // we should take the same approach with players list
                        System.out.println("2.player hand :  " + player);
                        System.out.println("2.discardBottom : " + discardBottom);
                        System.out.println("2.insertTop : " + insertTop);

                        // round robin
                        // Threads

                        // writing to files should happen in an synchronized block for each player

                        if (player.get(0) == player.get(1) && player.get(1) == player.get(2)
                                && player.get(2) == player.get(3)) {
                            win = true;
                            System.out.println("player " + (playerIndex + 1) % deckNumber + " Wins !");
                            // notifyAll(); this doesn't work but we have to notify the other players that
                            // player n has won the game
                        }
                    }

                }
            }
        });

        executorService.shutdown();
    }
    */


    public void startGame() { // this code will show that how the players are running in parallel 
        // if we can update the code so that it performs correctly 
        // mainly to understand how can we communicate with the game in way that it's parallel 
        ExecutorService executorService = Executors.newFixedThreadPool(players.size());

        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
            final int currentIndex = playerIndex;

            executorService.execute(() -> {
                while (!win) {
                    LinkedList<Integer> player = players.get(currentIndex % deckNumber);
                    LinkedList<Integer> insertTop = decks.get(currentIndex % deckNumber);
                    LinkedList<Integer> discardBottom = decks.get((currentIndex + 1) % deckNumber);

                    System.out.println(
                            "player_" + (currentIndex + 1) % deckNumber + " remove one card from Hand: "
                                    + handToString(player) + "\n");
                    int card = getCard(player);
                    System.out.println("1.Card: " + card);
                    System.out.println("1.player hand: " + player);
                    System.out.println("1.discardBottom: " + discardBottom);
                    System.out.println("1.insertTop: " + insertTop);

                    int index = player.indexOf(card);
                    

                    synchronized (decks) {
                        player.remove(index);
                        player.add(insertTop.removeFirst());
                        discardBottom.add(card);
                    }

                    System.out.println("2.player hand: " + player);
                    System.out.println("2.discardBottom: " + discardBottom);
                    System.out.println("2.insertTop: " + insertTop);

                    if (player.size() >= 4 && player.get(0).equals(player.get(1)) && player.get(1).equals(player.get(2))
                            && player.get(2).equals(player.get(3))) {
                        win = true;
                        System.out.println("Player " + (currentIndex + 1) % deckNumber + " Wins!");
                    }
                }
            });
        }

        executorService.shutdown();
    }



    

    public void playerWon() {

    }

    /*
     * 
     * Another Approach :
     * 
     * 
     * import java.util.Scanner;
     * 
     * public class CardGame {
     * public static void main(String[] args) {
     * int numPlayers = 4;
     * // Initialize game state, decks, players, etc.
     * 
     * for (int i = 0; i < numPlayers; i++) {
     * int playerIndex = i;
     * Thread playerThread = new Thread(() -> {
     * while (!gameIsOver()) {
     * // Player's turn
     * System.out.println("Player " + playerIndex + ", it's your turn.");
     * // Read player input and perform actions based on game rules
     * // Use synchronization mechanisms to coordinate turns
     * }
     * });
     * playerThread.start();
     * }
     * }
     * 
     * private static boolean gameIsOver() {
     * // Implement the game end condition
     * // Return true when a player wins, signaling the end of the game
     * return false;
     * }
     * }
     * 
     */
}