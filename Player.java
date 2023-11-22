
import java.util.*;

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
    private int playerNumber;
    protected static LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();

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
    public Player(LinkedList<LinkedList<Integer>> players) {
        this.playerNumber = players.size();
        this.players = players;
    }

    public Player(int discard, int draw, LinkedList<Integer> hand) { // should be methods 
        synchronized (this) {
            hand.remove(hand.indexOf(discard));
            hand.add(draw);
        }
    }

    public Player() {
        // default constructor 
    }

    protected synchronized LinkedList<Integer> getPlayer(int index) {
        return this.players.get((index % playerNumber));
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
            return false;
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
        try {
            if (hand.contains(null) || hand.isEmpty()) {
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }
        try {
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
        } catch (Exception e) {
            return 0;
        }

        return hand.getLast(); // it's better if it was in random 
    }

    public synchronized static LinkedList<LinkedList<Integer>> getPlayers() {
        return players;
    }

    public synchronized static void setPlayers(int n) {
        players = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            players.add(new LinkedList<Integer>());
        }
    }

}