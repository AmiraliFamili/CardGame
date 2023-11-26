
import java.util.*;

/**
 * @see Player
 * 
 *      - Player Class is one of the core classes of this project, it receives a
 *      LinkedList representing players from it's constructor and interacts with
 *      the game using players list and operates player related functions to
 *      keep the players list updated.
 * 
 * @Note the length of the nested LinkedList players represents the number of
 *       players currently playing the game..
 * 
 * @author Amirali Famili
 */
public class Player {
    protected int playerNumber;
    protected static LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();

    /**
     * @see Player
     * 
     *      - Player(LinkedList<LinkedList<Integer>>), is the main constructor for
     *      the Player
     *      Class, it takes a nested LinkedList representing player hands which
     *      should be their initial hand.
     * 
     * @param players a nested LinkedList consisting of all the hands of players
     *                within the game
     */
    public Player(LinkedList<LinkedList<Integer>> players) {
        this.playerNumber = players.size();
        this.players = players;
    }

    /**
     * @see replaceCard
     * 
     *      - replaceCard is a void method, it removes the card chosen by the player
     *      to be discarded and inserts the card obtained from their leftDeck to
     *      replace it.
     * 
     * @Note this synchronized method will make sure that the operation of adding
     *       and discarding from hands is atomic.
     * 
     * @param discard is the card that player has chosen to discard
     * @param draw    the card they have drawn from their leftDeck
     * @param hand    represents a player's hand which the operations should be
     *                executed for
     * 
     */
    public synchronized void replaceCard(int discard, int draw, LinkedList<Integer> hand) {
        if (hand != null && discard >= 0 && draw >= 0) {
            if (hand.contains(discard)) {
                hand.remove(hand.indexOf(discard));
                hand.add(draw);
            }
        }
    }

    public Player() {
        // default constructor
    }

    /**
     * @see getPlayer
     * 
     *      - getPlayer will return the current players hand with a given index, the
     *      index represents the round number in cardGame and mod playerNumber we
     *      get the player's index.
     * 
     * 
     * @param index a Positive Integer which should always return a player hand.
     * 
     * @return the player whom the index is associated with
     * 
     */
    protected synchronized LinkedList<Integer> getPlayer(int index) {
        return this.players.get((index % playerNumber));
    }

    /**
     * @see hasDuplicates
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
     * @see getCard
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

    /**
     * @see getPlayers
     * 
     *      - getPlayers is a synchronized method, it returns the current players
     *      list which contains all the hands.
     * 
     * @return the current players of the game (main LinkedList)
     */
    public synchronized static LinkedList<LinkedList<Integer>> getPlayers() {
        return players;
    }

    /**
     * @see setPlayers
     * 
     *      - setPlayers is an unimplemented synchronized void method, it receives an integer n
     *      which represents the number of players and creates a players LinkedList
     *      with n
     *      nested LinkedList inside it.
     * 
     */
    public synchronized static void setPlayers(int n) {
        players = new LinkedList<>();
        if (n <= 0) {
            n = 1;
        }
        for (int i = 0; i < n; i++) {
            players.add(new LinkedList<Integer>());
        }
    }

}