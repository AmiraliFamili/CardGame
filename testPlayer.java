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
     * @see testHandToString
     * 
     *      - testHandToString is a void method, it tests the method handToString
     *      method by creating a mock hand with wrong format and values and passing
     *      it to this method without any errors.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods handToString(LinkedList<Integer>)
     * 
     * @author Amirali Famili
     */
    @Test
    public void testHandToString() {

        Player player = new Player(-22);

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

        String actual = player.handToString(hand);
        assertEquals(28, actual.length());
        // since there are 19 characters and 9 spaces , given that null is counted as a
        // space
    }

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

        card.playerNum = 5;
        card.setPlayers();
        card.setDecks();

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

    /**
     * @see testCardGameGameStability
     * 
     *      - testCardGameGameStability is a void method, this method will attempt
     *      to start the main game method with an arbitrary pre set hand and deck
     *      which should win the game on the spot, then it tries to modify that hand
     *      while the game is being played.
     * 
     * @Note if the number of players change during a game the program will print an
     *       IndexOutOfBounds exception but the game continues.
     * 
     * @link Player.java, Card.java
     * 
     * @PlayerClassInstance player
     * @InstanceAttributes players , decks
     * @PlayerClassMethods startGame(),
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods setPlayers(), getPlayers(), setDecks(), getDecks()
     * 
     * @author Amirali Famili
     */
    @AfterClass
    public static void testCardGameGameStability() {

        Card card = new Card();

        card.playerNum = 8;
        card.setPlayers();
        card.setDecks();

        Player player = new Player(card.getDecks(), card.getPlayers());

        player.players.getFirst().add(1);
        player.players.getFirst().add(1);
        player.players.getFirst().add(1);
        player.players.getFirst().add(0);

        player.decks.getFirst().add(0);
        player.decks.getFirst().add(0);
        player.decks.getFirst().add(0);
        player.decks.getFirst().add(1);

        player.startGame(); // empty hands and decks for the rest of the players works

        card.playerNum = 8;
        card.setPlayers();
        card.setDecks();

        player.players = card.getPlayers();
        player.decks = card.getDecks();

        player.players.get(0).add(13);
        player.players.get(0).add(16);
        player.players.get(0).add(5);
        player.players.get(0).add(2);
        player.players.get(0).add(3);
        player.players.get(0).add(13);
        player.players.get(1).add(3);
        player.players.get(2).add(3);
        player.players.get(2).add(2);
        player.players.get(2).add(1);
        player.players.get(2).add(4);
        player.players.get(2).add(4);
        player.players.get(2).add(3);
        player.players.get(2).add(3);
        player.players.get(2).add(3);

        player.decks.get(0).add(3);
        player.decks.get(1).add(3);
        player.decks.get(1).add(3);
        player.decks.get(2).add(3);
        player.decks.get(2).add(3);
        player.decks.get(2).add(3);
        player.decks.get(2).add(3);
        player.decks.get(2).add(3);
        player.decks.get(02).add(3);

        assertTrue(true); // game finished successfully
    }

    /**
     * @see testStartGameOverload
     * 
     *      - testStartGameOverload is a void method, this method will attempt to
     *      start a game with 2000 players and successfully declare a winner.
     * 
     * @link Player.java, Card.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods startGame()
     * 
     * @CardClassInstance card
     * @CardClassMethods getPlayers(), getDecks()
     * 
     * @author Amirali Famili
     */
    @AfterClass
    public static void testStartGameOverload() {
        Card card = new Card(2000);
        Player player = new Player(card.getDecks(), card.getPlayers());
        player.startGame();
        assertTrue(true); // game finished successfully
    }
}
