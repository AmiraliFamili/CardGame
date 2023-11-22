import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

/**
 * @see testPlayer
 * 
 *      - Class testPlayer is used for testing all the methods and objects as
 *      well
 *      as the constructors of the
 *      Player Class, It checks the code for certain exceptions that could
 *      occur.
 * 
 * @Note that most methods within this test class are testing multiple aspects
 *       of both the corresponding method as well as other methods
 *       which are consists of many assertEquals, assertTrue, ...
 * 
 * @author Amirali Famili
 */
public class testPlayer {


    /**
     * @see testHasDuplicates
     * 
     *      - testHasDuplicates is a void method, it tests the method hasDuplicates
     *      by creating 3 mock hands with different problems with their size and
     *      values
     *      and pass each hand to this method, this method will return true if there
     *      is a value repeated within the list even null values.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods hasDuplicates(LinkedList<Integer>)
     * 
     * @author Amirali Famili
     */
    @Test
    public void testHasDuplicates() {
        Player player = new Player(0);

        LinkedList<Integer> hand = new LinkedList<>();
        hand.add(0);
        hand.add(-1);
        hand.add(-1);
        hand.add(-237);
        hand.add(null);
        hand.add(-237);
        hand.add(-237);
        hand.add(0);
        hand.add(1);

        assertTrue(player.hasDuplicates(hand));

        hand = new LinkedList<>();
        hand.add(null);
        hand.add(-1);
        hand.add(-0);
        hand.add(1);

        assertTrue(!player.hasDuplicates(hand));

        hand = new LinkedList<>();
        hand.add(null);
        hand.add(null);
        hand.add(null);
        hand.add(null);

        assertTrue(player.hasDuplicates(hand));
    }

    /**
     * @see testGetCard
     * 
     *      - testGetCard is a void method, this method creates empty players and
     *      decks using the card class (setPlayers & setDecks) and passes
     *      them to the constructor of the player, after that it would create mock
     *      hands substituted as the first player and passes that mock hand to the
     *      getCard method.
     * 
     * @Note getCard method returns 0 if there is a null value in the hand, it
     *       returns the last element if there is no duplicate in the hand ot it
     *       returns the
     *       first occurrence of a digit which has not been repeated throughout the
     *       hand.
     * 
     * @link Player.java, Card.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods getCard(LinkedList<Integer>)
     * 
     * @CardClassInstance card
     * @CardClassMethods setPlayers(), getPlayers()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testGetCard() {

        Card card = new Card();

        card.playerNum = 7;
        card.setPlayers(5);
        card.setDecks(5);

        Player player = new Player(card.getDecks(), card.getPlayers());

        LinkedList<Integer> playerHand = player.players.getFirst();
        playerHand.add(9);
        playerHand.add(-1);
        playerHand.add(null);
        playerHand.add(null);
        playerHand.add(90);
        playerHand.add(90);
        playerHand.add(2);

        // return 0 if one or more elements in a hand are null (reporting a problem
        // within the system)
        assertEquals(0, player.getCard(playerHand));

        playerHand = new LinkedList<>();

        playerHand.add(2);
        playerHand.add(9);
        playerHand.add(-1);
        playerHand.add(90);
        playerHand.add(90);
        playerHand.add(2);

        // return the first element that has no duplicate
        assertEquals(9, player.getCard(playerHand));

        playerHand = new LinkedList<>();

        playerHand.add(-2);
        playerHand.add(-1);
        playerHand.add(0);
        playerHand.add(1);
        playerHand.add(2);

        // if not duplicates then return the last element
        assertEquals(2, player.getCard(playerHand));

    }

    public static void main(String[] args) {
        testPlayer test = new testPlayer();
    }
}
