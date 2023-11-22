
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
    private int playerNumber;
    private int deckCard;

    Card(int value) {
        this.deckCard = value;
    }

    Card(LinkedList<LinkedList<Integer>> decks) {
        this.decks = decks;
        this.playerNumber = decks.size();
    }

    public Card() {
        // default constructor
    }

    protected static synchronized LinkedList<LinkedList<Integer>> getDecks() {
        return decks;
    }

    protected synchronized LinkedList<Integer> getLeftDeck(int index) {
        return decks.get((index % playerNumber));
    }

    protected synchronized LinkedList<Integer> getRightDeck(int index) {
        return decks.get((((index + 1) % playerNumber)));
    }
}
