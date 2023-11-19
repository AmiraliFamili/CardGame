import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @see Card
 * 
 *      - Class Card is used for generating the required data for the main game
 *      to work, it's responsible for generating a pack and passing it's values
 *      to the main instances of this project which are players and decks which
 *      are later used in Player class.
 * 
 * @Note this class's main purpose is to generate hands and decks each in a 4
 *       card format by just receiving an integer.
 * 
 * @author Amirali Famili
 */
public class Card extends Thread {
    private static LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    private int playerNumber;
    private int deckCard;

    Card(int value){
        this.deckCard = value;
    }

    Card(LinkedList<LinkedList<Integer>> decks) {
        this.decks = decks;
        this.playerNumber = decks.size();
    }

    public Card() {
    }

    protected static synchronized LinkedList<LinkedList<Integer>> getDecks(){
        return decks;
    }

    protected synchronized LinkedList<Integer> getLeftDeck(int index) {
        return decks.get((index % playerNumber));
    }

    protected synchronized LinkedList<Integer> getRightDeck(int index) {
        return decks.get((((index + 1) % playerNumber)));
    }
}
