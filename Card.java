
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

    public boolean hasDuplicates(LinkedList<Integer> hand) {
        Set<Integer> seen = new HashSet<>();

        for (Integer card : hand) {
            if (!seen.add(card)) {
                return true;
            }
        }
        return false;
    }

    public int getCard(LinkedList<Integer> hand) { // this method will chose for the players what card should be
                                                   // discarded
        int card = 0;

        if (hasDuplicates(hand)) {
            Map<Integer, Integer> cardCount = new HashMap<>();
            for (int c : hand) {
                cardCount.put(c, cardCount.getOrDefault(c, 0) + 1);
            }

            for (int c : hand) {
                if (cardCount.get(c) == 1) {
                    System.out.println(c + " is not a duplicate in " + hand);
                    card = c;
                    break;
                }
            }
        } else {
            System.out.println(hand + " has no duplicates, returning the first element");
            card = hand.getFirst();
        }

        return card;
    }

    private class PlayerThread extends Thread {
        private LinkedList<Integer> player;
        private LinkedList<Integer> insertTop;
        private LinkedList<Integer> discardBottom;
        int counter = 0;

        public PlayerThread(LinkedList<Integer> player, LinkedList<Integer> insertTop,
                LinkedList<Integer> discardBottom) {
            this.player = player;
            this.insertTop = insertTop;
            this.discardBottom = discardBottom;
        }

        @Override
        public void run() {
            while (!win) {
                playTurn();
            }
        }

        private Boolean win = false;
        private final Object lock = new Object();

        public void playTurn() {

            synchronized (players) { // the problem is with passing the insert top and discard bottom we have to use the shared resources 
                System.out.println(
                        "player_" + (counter + 1) % deckNumber + " remove one card from Hand: " + handToString(player));

                System.out.println("1.player hand: " + player);
                System.out.println("1.discardBottom: " + discardBottom);
                System.out.println("1.insertTop: " + insertTop);

                int card = getCard(player);
                player.remove(player.indexOf(getCard(player)));
                player.add(insertTop.removeFirst());
                discardBottom.add(card);
                System.out.println("2.player hand: " + player);
                System.out.println("2.discardBottom: " + discardBottom);
                System.out.println("2.insertTop: " + insertTop);

            }

            if (player.size() >= 4 && player.get(0).equals(player.get(1)) && player.get(1).equals(player.get(2))
                    && player.get(2).equals(player.get(3))) {
                this.win = true;
                System.out.println("Player" + (players.indexOf(player) + 1) + " wins!");
                System.exit(0);
            }

            counter++;
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

    public void startGame() { // this code will show that how the players are running in parallel
        // if we can update the code so that it performs correctly
        // mainly to understand how can we communicate with the game in way that it's
        // parallel
        ExecutorService executorService = Executors.newFixedThreadPool(players.size());

        for (int i = 0; i < players.size(); i++) {
            LinkedList<Integer> player = players.get(i);
            LinkedList<Integer> insertTop = decks.get(i);
            LinkedList<Integer> discardBottom = decks.get((i + 1) % deckNumber);

            PlayerThread playerThread = new PlayerThread(player, insertTop, discardBottom);
            executorService.execute(playerThread);

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

/*
 * 
 * public void startGame() {
 * ExecutorService executorService =
 * Executors.newFixedThreadPool(players.size());
 * executorService.execute(() -> {
 * while (!win) {
 * for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
 * // Make sure to use synchronization mechanisms to coordinate turns
 * synchronized (players) { // now that it's in synchronized is it running in
 * parallel ?
 * LinkedList<Integer> player = this.players.get(playerIndex % deckNumber);
 * LinkedList<Integer> insertTop = this.decks.get(playerIndex % deckNumber);
 * LinkedList<Integer> discardBottom = this.decks.get((playerIndex + 1) %
 * deckNumber);
 * 
 * System.out.println(
 * "player_" + (playerIndex + 1) % deckNumber + " remove one card from Hand : "
 * + handToString(player) + "\n");
 * int Card = getCard(player);
 * System.out.println("1.Card : " + Card);
 * System.out.println("1.player hand : " + player);
 * System.out.println("1.discardBottom : " + discardBottom);
 * System.out.println("1.insertTop : " + insertTop);
 * 
 * int index = player.indexOf(Card);
 * 
 * player.remove(index);
 * synchronized (decks) {
 * // with this block all actions to decks are monitored and happen one at a
 * time
 * player.add(insertTop.removeFirst());
 * discardBottom.add(Card);
 * }
 * // we should take the same approach with players list
 * System.out.println("2.player hand :  " + player);
 * System.out.println("2.discardBottom : " + discardBottom);
 * System.out.println("2.insertTop : " + insertTop);
 * 
 * // round robin
 * // Threads
 * 
 * // writing to files should happen in an synchronized block for each player
 * 
 * if (player.get(0) == player.get(1) && player.get(1) == player.get(2)
 * && player.get(2) == player.get(3)) {
 * win = true;
 * System.out.println("player " + (playerIndex + 1) % deckNumber + " Wins !");
 * // notifyAll(); this doesn't work but we have to notify the other players
 * that
 * // player n has won the game
 * }
 * }
 * 
 * }
 * }
 * });
 * 
 * executorService.shutdown();
 * }
 */
