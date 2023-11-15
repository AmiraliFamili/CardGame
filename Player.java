
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @see Player
 * 
 *      - Player Class is the main class of this project, it receives two main
 *      lists one for players and one for decks and it,
 *      Initiates the game with those LinkedLists using a nested inner class
 *      which extends Thread class.
 * 
 * @Note this class's main purpose is to generate hands and decks each in a 4
 *       card format by just receiving an integer.
 * 
 * @author Amirali Famili
 */
public class Player {
    final private int timeSlice = 500;
    final private int playerN;

    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();

    /**
     * @see Player(LinkedList, LinkedList)
     * 
     *      - Player(LinkedList, LinkedList), is the main constructor for the Player
     *      Class, it takes two nested LinkedLists one representing player's hands
     *      and the other their decks and assigns them with the local instances of
     *      this class.
     * 
     * @param decks   a nested LinkedList consisting of all the decks of players
     *                within the game
     * @param players a nested LinkedList consisting of all the hands of players
     *                within the game
     * 
     */
    public Player(LinkedList<LinkedList<Integer>> decks, LinkedList<LinkedList<Integer>> players) {
        this.decks = decks;
        this.playerN = decks.size();
        this.players = players;
    }

    /**
     * @see Player(int)
     * 
     *      - Player(int) is another constructor for the Player class it just
     *      assigns the number of players in the game, mainly used
     *      by the test Classes to manually manipulate the size and structure of the
     *      main lists decks and players.
     * 
     * 
     * @param playerNumber an integer representing the number of players in the
     *                     game.
     * 
     */
    public Player(int playerNumber) {
        this.playerN = playerNumber;
    }

    /**
     * @see handToString(LinkedList)
     * 
     *      - handToString(LinkedList) is an unimplemented method, it is used for
     *      converting LinkedList element to a String,
     *      and then returning the String representation of the values.
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return the string representation of the values within the hand LinkedList
     */
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

    /**
     * @see hasDuplicates(LinkedList)
     * 
     *      - hasDuplicates(LinkedList) is a synchronized method for checking if a
     *      LinkedList of integers had duplicated values within it, .
     *      it returns true if it finds a duplicate and false if there is no element
     *      being repeated inside the LinkedList.
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return true or false
     */
    public synchronized boolean hasDuplicates(LinkedList<Integer> hand) {
        Set<Integer> dup = new HashSet<>();

        try {
            for (Integer card : hand) {
                if (!dup.add(card)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @see getCard(LinkedList)
     * 
     *      - getCard(LinkedList) is a synchronized method, it checks the LinkedList
     *      passed to it as an argument and it returns
     *      the first occurrence of an element that has not been repeated within
     *      hand with the help of hasDuplicate method.
     *      - this method returns the last element of the hand if it fails to find a
     *      duplicate,
     *      if there is a null value it returns 0 which reports a serious problem
     *      within the system.
     * 
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return an integer representing the card to be removed by the player
     */
    public synchronized int getCard(LinkedList<Integer> hand) {
        if (playerWon(hand)) {
            counter = -1;
            this.win = true;
            return 0;
        }
        if (hand.contains(null) || hand.isEmpty()) {
            return 0;
        }

        if (hasDuplicates(hand)) {
            Map<Integer, Integer> cardCount = new HashMap<>();
            for (int card : hand) {
                cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
            }

            for (int card : hand) {
                if (cardCount.get(card) == 1) {
                    return card;
                }
            }
        }
        return hand.getLast();
    }

    private volatile Boolean win = false;
    private final Object lock = new Object();
    private int counter = 0;

    /**
     * @see PlayerThread
     * 
     *      - PlayerThread is a nested class responsible for handling player
     *      threads, it extends Thread and has a run method in which it keeps
     *      executing a player turn until a player wins the game.
     * 
     * @Note playerThread is an inner class of Player class mainly responsible for
     *       multi threading of players and handling the threads to avoid any
     *       problems such as race condition
     */
    private class PlayerThread extends Thread {

        private final Object lock2 = new Object();

        /**
         * @see run
         * 
         *      - run method for threading the players each player will use this run
         *      method to play the game from within their own thread.
         * 
         * @Note this method keeps executing player Turns until a player wins the game.
         */
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

        public void playTurn() {
            // synchronized (lock) {

            if (counter != -1 || win == false) {

                // take from left , discard to right
                LinkedList<Integer> player = players.get(counter % playerN);
                LinkedList<Integer> leftDeck = decks.get(counter % playerN);

                try {
                    decks.get((counter + 1) % playerN);
                } catch (IndexOutOfBoundsException e) {
                    playTurn();
                }

                LinkedList<Integer> rightDeck = decks.get((counter + 1) % playerN);

                System.out.println(
                        "Round : " + counter + "\tPlayer  : " + ((counter % playerN) + 1) + "\t Hand : " + player);

                if (playerWon(player)) {
                    counter = -1;
                    playTurn();
                }
                synchronized (lock2) {

                    if (leftDeck.isEmpty()) {
                        counter++;
                        playTurn();
                    }

                    int card = getCard(player);
                    if (player.contains(card) && !leftDeck.isEmpty() && card != 0) {// write methode
                        try {
                            player.remove(player.indexOf(card));
                            int takenCard = leftDeck.removeFirst();
                            player.add(takenCard);
                            rightDeck.add(card);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (playerWon(player)) {
                            counter = -1;
                            playTurn();
                        }
                    } else {
                        try {
                            lock2.wait(timeSlice);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    counter++;

                }
            }
        }
    }

    // }

    public boolean playerWon(LinkedList<Integer> player) {
        if (player.size() >= 4 && player.get(0).equals(player.get(1)) && player.get(1).equals(player.get(2))
                && player.get(2).equals(player.get(3))) {
            System.out.println("Player_" + (players.indexOf(player) + 1) + " wins!\tfinal Hand : " + player);
            this.win = true;
            System.exit(0);
        }
        return false;
    }

    public void startGame() {
        ExecutorService executorService = Executors.newFixedThreadPool(players.size());

        for (int i = 0; i < players.size(); i++) {
            PlayerThread playerThread = new PlayerThread();
            executorService.execute(playerThread);
        }

        executorService.shutdown();
    }
}