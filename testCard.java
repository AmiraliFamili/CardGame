import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import org.junit.Test;

/**
 * @see testCard
 * 
 *      - Class testCard is used for testing all the methods and objects as well
 *      as the constructors of the
 *      Card Class, It checks the code for certain exceptions that could occur.
 * 
 * @Note most test methods within this test class are testing multiple aspects
 *       of the class such as other methods and objects.
 * 
 * @Note getDecks method's functionality is tested within other test methods
 *       thus it doesn't have it's own test method.
 * 
 * @author Amirali Famili
 */
public class testCard {

    /**
     * @see testCardConstructor
     * 
     *      - testCardConstructor is a void method, it tests the constructor of
     *      the Card class to make sure that decks LinkedList and the number of
     *      decks (or players) are set.
     * 
     * @link Card.java
     * 
     * @CardClassInstance card
     * @InstanceAttributes deckNumber
     * @PlayerClassMethods getDecks()
     */
    @Test
    public void testCardConstructor() {
        LinkedList<LinkedList<Integer>> decks = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            decks.add(new LinkedList<>());
            for (int j = 0; j < 2; j++) {
                decks.get(i).add(j);
            }
        }
        Card card = new Card(decks);

        assertEquals(3, Card.getDecks().size());
        assertEquals(2, Card.getDecks().get(0).size());
        assertEquals(2, Card.getDecks().get(1).size());
        assertEquals(2, Card.getDecks().get(2).size());
        assertEquals(3, card.deckNumber);
    }

    /**
     * @see testGetLeftDeck
     * 
     *      - testGetLeftDeck is a void method, it tests the getLeftDeck method from
     *      Card class by creating a mock decks LinkedList and assigning a
     *      playerIndex, by passing the player index to the method we get their left
     *      deck.
     * 
     * @Note player always has the same index as their left deck.
     * 
     * @link Card.java
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNumber
     * @PlayerClassMethods getLeftDeck()
     */
    @Test
    public void testGetLeftDeck() {

        LinkedList<LinkedList<Integer>> decks = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            decks.add(new LinkedList<>());
        }

        decks.get(0).add(null);
        decks.get(0).add(1);
        decks.get(0).add(0);
        decks.get(1).add(null);
        decks.get(1).add(0);
        decks.get(1).add(0);
        decks.get(1).add(0);
        decks.get(1).add(0);

        Card card = new Card(decks);

        int playerIndex = 0;

        assertEquals(3, card.getLeftDeck(playerIndex).size());

        playerIndex++;

        assertEquals(5, card.getLeftDeck(playerIndex).size());

        assertEquals(3, card.getLeftDeck(-1).size());
        assertEquals(3, card.getLeftDeck(-2).size());
        assertEquals(3, card.getLeftDeck(-3).size());
        assertEquals(3, card.getLeftDeck(-4).size());
        assertEquals(3, card.getLeftDeck(-5).size());
    }

    /**
     * @see testGetRightDeck
     * 
     *      - testGetRightDeck is a void method, it tests the getLeftDeck method
     *      from
     *      Card class by creating a mock decks LinkedList and assigning a
     *      playerIndex, by passing the player index to the method we get their
     *      right
     *      deck, since we give it the last player's index it should return their
     *      right deck which is the same as first player's left deck.
     * 
     * @Note players right deck's index is always one index above their left deck.
     * 
     * @link Card.java
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNumber
     * @PlayerClassMethods getRightDeck()
     */
    @Test
    public void testGetRightDeck() {
        LinkedList<LinkedList<Integer>> decks = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            decks.add(new LinkedList<>());
        }

        decks.get(0).add(null);
        decks.get(0).add(1);
        decks.get(0).add(0);
        decks.get(1).add(null);
        decks.get(1).add(0);
        decks.get(1).add(0);
        decks.get(1).add(0);
        decks.get(1).add(0);

        Card card = new Card(decks);

        // here we have 3 players and index 2 should point to the last player
        // since last player's right deck is the same as left deck of the first player
        int playerIndex = 2;
        // this should point to the deck at index 0 since : 2+1 % 3 == 0
        assertEquals(3, card.getRightDeck(playerIndex).size());

        playerIndex++;

        assertEquals(5, card.getRightDeck(playerIndex).size());

        assertEquals(5, card.getRightDeck(-1).size());
        assertEquals(5, card.getRightDeck(-2).size());
        assertEquals(5, card.getRightDeck(-3).size());
        assertEquals(5, card.getRightDeck(-4).size());
        assertEquals(5, card.getRightDeck(-5).size());
    }

    /**
     * @see testGetCardFromLeftDeck
     * 
     *      - testGetCardFromLeftDeck is a void method, it tests the
     *      getCardFromLeftDeck method from Card class, this method uses a poll
     *      request to simply retrieve and remove the first element of it's list,
     *      this method will check the functionality of the method.
     * 
     * @Note when there is a null element method getCardFromLeftDeck will remove it
     *       from the LinkedList and instead it would return -1 so that there
     *       wouldn't be any interruption within the game.
     * 
     * @link Card.java
     * 
     * @CardClassInstance card
     * @PlayerClassMethods getCardFromLeftDeck(LinkedList<Integer>)
     */
    @Test
    public void testGetCardFromLeftDeck() {

        Card card = new Card();

        LinkedList<Integer> leftDeck = new LinkedList<Integer>();
        leftDeck.add(null);
        leftDeck.add(0);
        leftDeck.add(1);
        leftDeck.add(2);
        leftDeck.add(-3748);

        int drawnCard = card.getCardFromLeftDeck(leftDeck);

        assertEquals(-1, drawnCard);

        assertEquals(0, card.getCardFromLeftDeck(leftDeck));

        assertEquals(-1, card.getCardFromLeftDeck(null));

        assertEquals(-1, card.getCardFromLeftDeck(new LinkedList<>()));

    }

    /**
     * @see testPutCardToRightDeck
     * 
     *      - testPutCardToRightDeck is a void method, it tests the
     *      putCardToRightDeck method from Card class, this method will receive an
     *      integer and a list, then it would simple add the integer to the end of
     *      the list, this test method will check the functionality and robustness of the method.
     * 
     * @link Card.java
     * 
     * @CardClassInstance card
     * @PlayerClassMethods getCardFromLeftDeck(LinkedList<Integer>)
     */
    @Test
    public void testPutCardToRightDeck() {

        Card card = new Card();

        LinkedList<Integer> leftDeck = new LinkedList<Integer>();
        leftDeck.add(null);
        leftDeck.add(0);
        leftDeck.add(-1);
        leftDeck.add(2);
        leftDeck.add(-3748);

        card.putCardToRightDeck(348, leftDeck);
        int actual = leftDeck.getLast();
        assertEquals(348, actual);

        card.putCardToRightDeck(5, null);
        int last = leftDeck.getLast();
        assertEquals(348, last);

        leftDeck = new LinkedList<>();
        card.putCardToRightDeck(last, leftDeck);
        assertEquals(1, leftDeck.size());
        assertTrue(leftDeck.contains(348));
    }
}
