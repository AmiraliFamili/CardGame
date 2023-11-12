
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 
 *  how can we implement the round robin fashion 
 */
public class Player {
    final private int timeslice = 90;
    final private int playerN;

    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();

    public Player(LinkedList<LinkedList<Integer>> decks, LinkedList<LinkedList<Integer>> players) {
        this.decks = decks;
        this.playerN = decks.size();
        this.players = players;
    }

    public Player(int playerNumber) {
        this.playerN = playerNumber;
    }

    public String handToString(LinkedList<Integer> hand) {
        String sHand = "";
        if (hand.size() > 4) {
            // hand is in the wrong format 
        }
        for (Integer card : hand) {
            if (card != null) {
                sHand = sHand + Integer.toString(card) + " ";
            } else {
                sHand = sHand + " ";
            }
        }
        return sHand;
    }

    // change the names here
    public synchronized boolean hasDuplicates(LinkedList<Integer> hand) {
        Set<Integer> seen = new HashSet<>();

        try {
            for (Integer card : hand) {
                if (!seen.add(card)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // change the names here
    public synchronized int getCard(LinkedList<Integer> hand) {
        if (playerWon(hand)) {

            counter = -1;
            this.win = true;
            lock.notifyAll();
            return 0;
        }
        if (hand.contains(null) || hand.isEmpty()) {
            return 0;
        }

        if (hasDuplicates(hand)) {
            Map<Integer, Integer> cardCount = new HashMap<>();
            for (int c : hand) {
                cardCount.put(c, cardCount.getOrDefault(c, 0) + 1);
            }

            for (int c : hand) {
                if (cardCount.get(c) == 1) {
                    return c;
                }
            }
        } 

        return hand.getLast();
    }

    private volatile Boolean win = false;
    private final Object lock = new Object();
    private int counter = 0;

    private class PlayerThread extends Thread {

        @Override
        public void run() {

            if (!players.get(0).isEmpty() && counter != -1) {
                while (!win) {
                    if (counter == -1) {
                        break;
                    }
                    playTurn();
                }
            } else {
                System.out.println("players have not been set please re run the game");
                System.exit(0);
            }
        }

        private final Object lock2 = new Object();

        public void playTurn() {
            synchronized (lock) { 

                if (counter != -1 || win == false) {

                    // take from left , discard to right 
                    LinkedList<Integer> player = players.get(counter % playerN);
                    LinkedList<Integer> leftDeck = decks.get(counter % playerN);
                    LinkedList<Integer> rightDeck = decks.get((counter + 1) % playerN);

                    while (leftDeck.isEmpty()) {
                        try {
                            if (leftDeck.isEmpty()) {
                                counter++;
                                lock.wait(500);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("round : " + counter + " player  : " + ((counter % playerN) + 1));

                    synchronized (lock2) {

                        int card = getCard(player);
                        if (player.contains(card) && !leftDeck.isEmpty() && card != 0) {// write methode
                            player.remove(player.indexOf(card));
                            int takenCard = leftDeck.removeFirst();
                            player.add(takenCard);
                            rightDeck.add(card);

                            // Player j = new Player(deckNumber, null, null, null, null);

                        } else {
                            try {
                                lock2.wait(500);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        counter++;

                        if (playerWon(player)) {
                            counter = -1;
                            lock.notifyAll();
                        }
                    }
                }
            }
        }

    }

    public boolean playerWon(LinkedList<Integer> player) { // wining strategy
        if (player.size() >= 4 && player.get(0).equals(player.get(1)) && player.get(1).equals(player.get(2))
                && player.get(2).equals(player.get(3))) {
            System.out.println("Player_" + (players.indexOf(player) + 1) + " wins!");
            this.win = true;
            return true;
            // System.exit(0);
        }
        return false;
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
            PlayerThread playerThread = new PlayerThread();
            executorService.execute(playerThread);
        }

        executorService.shutdown();
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

/*
 * EXTRA NOTES FOR IMPLMENTAION
 * implements the Callable interface, which is part of Java's concurrency API
 * and is often used in conjunction with threads.
 * interface similar to Runnable interface, but it allows for a return value and
 * can throw exceptions.
 * In the Player class, the call() method is implemented, which is the method
 * that will be called when a Player object is used with an executor service,
 * such as ExecutorService.submit(Callable<T> task)
 */

/*
 * to get the player output
 * need output file for player an card deck
 * 
 * for each one you need an attribute which is an output file for the objects
 * 
 * in the constructor need to inistilise the outoutfile
 * 
 * where card
 */