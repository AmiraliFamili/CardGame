
import java.util.LinkedList;

/**
 * @see Card
 * 
 *      - Class Card is used for managing decks, it receives the initial decks
 *      from CardGame class and communicates to the main game method with it,
 *      it's also responsible for deck related operations such as taking an
 *      element from the left deck and inserting an element to the right deck.
 * 
 * @Note decks live information is only available within this class and can be
 *       easily accessed using a static reference
 * 
 * @author Amirali Famili
 */
public class Card extends Thread {

    private static LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected int deckNumber;

    /**
     * @see Card
     * 
     *      - Card(LinkedList<LinkedList<Integer>>), is the main constructor for
     *      the Card
     *      Class, it takes a nested LinkedList representing decks which
     *      should be their initial deck.
     * 
     * @param decks a nested LinkedList consisting of all the decks of players
     *              within the game
     */
    public Card(LinkedList<LinkedList<Integer>> decks) {
        this.decks = decks;
        this.deckNumber = decks.size();
    }

    public Card() {
        // default constructor
    }

    /**
     * @see getDecks
     * 
     *      - getDecks(LinkedList<LinkedList<Integer>>) is a synchronized method, it
     *      returns the current decks of players.
     * 
     * @return the main decks LinkedList which contains all the player decks
     */
    protected static synchronized LinkedList<LinkedList<Integer>> getDecks() {
        return decks;
    }

    /**
     * @see getLeftDeck
     * 
     *      - getLeftDeck(int) is a synchronized method, it receives an index and
     *      calculates the players left deck and returns it.
     * 
     * @Note player and their left deck have the same index
     * 
     * @param index a positive integer which should always return a deck from decks
     * 
     * @return the leftDeck of the player whom the index is associated with
     */
    protected synchronized LinkedList<Integer> getLeftDeck(int index) {
        if (index < 0) {
            index = 0;
        }
        return decks.get((index % deckNumber));
    }

    /**
     * @see getRightDeck
     * 
     *      - getRightDeck(int) is a synchronized method, it receives an index and
     *      returns the rightDeck of the player whom the index is associated with.
     * 
     * @Note player right deck is one index above player's index.
     * 
     * @param index a positive integer which should always return a deck from decks
     * 
     * @return the rightDeck of the player whom the index is associated with
     */
    protected synchronized LinkedList<Integer> getRightDeck(int index) {
        if (index < 0) {
            index = 0;
        }
        return decks.get((((index + 1) % deckNumber)));
    }

    /**
     * @see getCardFromLeftDeck
     * 
     *      - getCardFromLeftDeck(LinkedList<Integer>) is a synchronized method, it
     *      receives a LinkedList (leftDeck) and removes and returns the first card
     *      (integer) of the LinkedList.
     * 
     * @param leftDeck the left deck of a player which has the same index as theirs
     * 
     * @return the rightDeck of the player whom the index is associated with
     */
    protected synchronized int getCardFromLeftDeck(LinkedList<Integer> leftDeck) {

        try {
            return leftDeck.poll();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @see putCardToRightDeck
     * 
     *      - putCardToRightDeck(LinkedList<Integer>) is a synchronized void method,
     *      it
     *      receives a LinkedList (rightDeck) and a Integer (the card that player
     *      discarded), and it simply adds the discarded card to the rightDeck.
     * 
     * @param rightDeck the right deck of a player
     * 
     * @return the rightDeck of the player whom the index is associated with
     */
    protected synchronized void putCardToRightDeck(int discard, LinkedList<Integer> rightDeck) {
        if (rightDeck != null) {
            rightDeck.add(discard);
        }
    }
}
