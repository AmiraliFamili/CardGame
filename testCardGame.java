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
 * @see testCardGame
 * 
 *      - Class testCardGame is used for testing all the methods and objects as
 *      well
 *      as the constructors of the
 *      CardGame Class, It checks the code for certain exceptions that could
 *      occur.
 *      It's also testing the main game method.
 * 
 * @Note most test methods within this test class are testing multiple aspects
 *       of the class such as other methods and objects
 * 
 * @author Amirali Famili
 */
public class testCardGame {

    /**
     * @see testEmptyPack
     * 
     *      - testEmptyPack is a void method, it creates a pack and then checks the
     *      size of the created pack
     *      then, it would empty the pack and check that it actually emptied inside
     *      CardGame Class.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods createPack(int), emptyPack(), getPack()
     */
    @Test
    public void testEmptyPack() {
        CardGame cardGame = new CardGame();

        cardGame.createPack(5);
        LinkedList<Integer> pack = cardGame.getPack();

        assertEquals(5 * 8, pack.size());

        cardGame.emptyPack();

        assertTrue(cardGame.getPack().isEmpty());
    }

    /**
     * @see testGetPack
     * 
     *      - testGetPack is a void method, it creates a pack and checks for it's
     *      size, then it would remove the
     *      first element of the pack and check with getPack method that it's value
     *      is updated within CardGame Class.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods createPack(int), getPack()
     */
    @Test
    public void testGetPack() {
        CardGame cardGame = new CardGame();

        cardGame.createPack(34);
        LinkedList<Integer> pack = cardGame.getPack();

        assertTrue(pack.size() == 34 * 8);
        pack.removeFirst();

        assertTrue(cardGame.getPack().size() == ((34 * 8) - 1));
    }

    /**
     * @see testCreatePack
     * 
     *      - testCreatePack is a void method, it creates packs of odd values given
     *      to it and then check it's size, empties the pack
     *      then it would move on to the next odd input for createPack method.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods createPack(int), emptyPack(), getPack()
     */
    @Test
    public void testCreatePack() {
        CardGame cardGame = new CardGame();

        cardGame.createPack(1);
        assertEquals(8, cardGame.getPack().size());

        cardGame.emptyPack();
        cardGame.createPack(0);

        assertEquals(8, cardGame.getPack().size());

        cardGame.emptyPack();
        cardGame.createPack(-1);

        assertEquals(8, cardGame.getPack().size());

        cardGame.emptyPack();

        cardGame.createPack(-1001);
        assertEquals(8, cardGame.getPack().size());

    }

    /**
     * @see testGet1FromPack
     * 
     *      - testGet1FromPack is a void method, it creates a pack and retrieves the
     *      first element of the pack
     *      then it would use get1FromPack method to retrieve and remove the first
     *      element of the pack, after doing so
     *      the code checks to see if the correct card was retrieved from this
     *      method and if the size is reduced by 1.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods createPack(int), get1FromPack(), getPack()
     */
    @Test
    public void testGet1FromPack() {
        CardGame cardGame = new CardGame();
        cardGame.createPack(1);

        int packSize = cardGame.getPack().size();
        int expected = cardGame.getPack().getFirst();
        int actual = cardGame.get1FromPack();

        assertEquals(expected, actual);
        assertEquals(packSize - 1, cardGame.getPack().size());
    }

    /**
     * @see testSetPlayers
     * 
     *      - testSetPlayers is a void method, it creates 6 empty lists within the
     *      nested linked lists called players, then it would retrieve this list
     *      from Card class and after creating a local nested list
     *      of the same length, it would compare to see if they are the same lists.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods setPlayers(), getPlayers()
     */
    @Test
    public void testSetPlayers() {
        CardGame cardGame = new CardGame();

        cardGame.setPlayers(6);
        LinkedList<LinkedList<Integer>> actual = cardGame.getPlayers();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < 6; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(6, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * @see testSetDecks
     * 
     *      - testSetDecks is a void method, similarly to the testSetPlayers method
     *      it would set the decks within the CardGame Class and retrieves it and
     *      then
     *      creates the
     *      expected local list and compares the two lists for Equality and Size.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @CardGameClassMethods setDecks(), getDecks()
     */
    @Test
    public void testSetDecks() {

        CardGame cardGame = new CardGame();

        cardGame.setDecks(3);
        LinkedList<LinkedList<Integer>> actual = cardGame.getDecks();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < 3; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * @see testDealHands
     * 
     *      - testDealHands is a void method, it would create a pack for 5 players
     *      of size 40, then it would retrieve the first 5 cards within the pack,
     *      then it would use methods setPlayers and dealHands to distribute half of
     *      the pack to the players then it would check that dealer has distributed
     *      the cards in the correct format
     *      giving one card at a time to each player in a round robin fashion, then
     *      it would check that all players have 4 cards in their hands.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber
     * @CardGameClassMethods createPack(int), setPlayers(), getPlayers(),
     *                       dealHands()
     */
    @Test
    public void testDealHands() {
        CardGame cardGame = new CardGame();

        cardGame.playerNumber = 5;
        cardGame.createPack(cardGame.playerNumber);
        LinkedList<Integer> pack = cardGame.getPack();

        int player1Card1 = pack.get(0);
        int player2Card1 = pack.get(1);
        int player3Card1 = pack.get(2);
        int player4Card1 = pack.get(3);
        int player5Card1 = pack.get(4);
        int player1Card2 = pack.get(5);

        cardGame.setPlayers(cardGame.playerNumber);
        LinkedList<LinkedList<Integer>> actual = cardGame.dealHands();

        assertTrue(player1Card1 == cardGame.getPlayers().get(0).get(0));
        assertTrue(player2Card1 == cardGame.getPlayers().get(1).get(0));
        assertTrue(player3Card1 == cardGame.getPlayers().get(2).get(0));
        assertTrue(player4Card1 == cardGame.getPlayers().get(3).get(0));
        assertTrue(player5Card1 == cardGame.getPlayers().get(4).get(0));
        assertTrue(player1Card2 == cardGame.getPlayers().get(0).get(1));

        assertEquals(4, actual.get(0).size());
        assertEquals(4, actual.get(1).size());
        assertEquals(4, actual.get(2).size());
        assertEquals(4, actual.get(3).size());
        assertEquals(4, actual.get(4).size());

        assertTrue(actual.size() == cardGame.playerNumber);
    }

    /**
     * @see testDealHandsMockHands
     * 
     *      - testDealHandsMockHands is a void method, it would create a pack with
     *      wrong values and size then
     *      it would checks to see if the hands where created and that cards where
     *      distributed correctly among players.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber, pack
     * @CardGameClassMethods emptyPack(), setPlayers(), dealHands()
     */
    @Test
    public void testDealHandsMockHands() {
        CardGame cardGame = new CardGame();

        cardGame.emptyPack();
        cardGame.pack.add(9);
        cardGame.pack.add(23);
        cardGame.pack.add(34);
        cardGame.pack.add(5);
        cardGame.pack.add(0);
        cardGame.pack.add(-23);
        cardGame.pack.add(-4);
        cardGame.pack.add(-0);
        cardGame.pack.add(1);

        cardGame.playerNumber = 4;
        cardGame.setPlayers(cardGame.playerNumber);

        LinkedList<LinkedList<Integer>> mockHands = cardGame.dealHands();
        assertTrue(!cardGame.players.getFirst().isEmpty());

        // since the number of elements added to the pack is 9
        boolean expected = (mockHands.get(0).size() + mockHands.get(1).size() + mockHands.get(2).size()
                + mockHands.get(3).size()) == 9;

        assertTrue(expected);
    }

    /**
     * @see testDealDecks
     * 
     *      - testDealDecks is a void method, it creates a pack and distributes it's
     *      card to players and decks and it
     *      would check to see if all nested decks have the same size 4 and check to
     *      see if the decks are valid and pack is empty.
     * 
     * @Note on successful run we know that we have n decks of size 4 and from the
     *       previous test we have n hands of size 4
     *       therefore we have total cards of 8 * n which is the same as
     *       specification.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber
     * @CardGameClassMethods createPack(int), setPlayers(), dealHands(),
     *                       dealDecks(),
     *                       seDecks(), getPack()
     */
    @Test
    public void testDealDecks() {
        CardGame cardGame = new CardGame();

        cardGame.playerNumber = 4;
        cardGame.createPack(cardGame.playerNumber);
        LinkedList<Integer> pack = cardGame.getPack();

        cardGame.setPlayers(cardGame.playerNumber);
        cardGame.dealHands();
        cardGame.setDecks(cardGame.playerNumber);
        LinkedList<LinkedList<Integer>> actual = cardGame.dealDecks();

        assertEquals(4, actual.get(0).size());
        assertTrue(actual.get(1).size() == 4);
        assertTrue(actual.get(2).size() == 4);
        assertTrue(actual.get(3).size() == 4);
        assertTrue(actual.size() == cardGame.playerNumber);
        assertTrue(pack.size() == 0);
    }

    /**
     * @see testDealDecksMockHands
     * 
     *      - testDealDecksMockHands is a void method, it creates a pack with wrong
     *      values and just distributes the cards of this pack
     *      between decks then it would check if the pack has been created and if it
     *      contains all the values inside the pack and that the pack is empty.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber, pack
     * @CardGameClassMethods dealDecks(), seDecks(), emptyPack()
     */
    @Test
    public void testDealDecksMockHands() {
        CardGame cardGame = new CardGame();

        cardGame.emptyPack();
        cardGame.pack.add(3);
        cardGame.pack.add(213);
        cardGame.pack.add(400);
        cardGame.pack.add(5 - 12);
        cardGame.pack.add(000);
        cardGame.pack.add(-234454353);
        cardGame.pack.add(-23843);
        cardGame.pack.add(-000000000);
        cardGame.pack.add(134443);

        cardGame.playerNumber = 4;
        cardGame.setDecks(cardGame.playerNumber);

        LinkedList<LinkedList<Integer>> mockDecks = cardGame.dealDecks();

        assertTrue(!cardGame.decks.getFirst().isEmpty());
        assertTrue(cardGame.pack.isEmpty());

        boolean expected = (mockDecks.get(0).size() + mockDecks.get(1).size() + mockDecks.get(2).size()
                + mockDecks.get(3).size()) == 9;

        assertTrue(expected);
    }

    /**
     * @see testGetPlayers
     * 
     *      - testGetPlayers is a void method, it checks the functionality of
     *      getPlayers by adding some elements to the main players list within
     *      CardGame
     *      Class,
     *      and checking the validity of the players afterwards.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes players
     * @CardGameClassMethods setPlayers(), getPlayers()
     */
    @Test
    public void testGetPlayers() {
        CardGame cardGame = new CardGame();

        cardGame.setPlayers(5);
        cardGame.players.get(0).add(90);
        cardGame.players.getLast().add(91);

        assertTrue(cardGame.getPlayers().getFirst().contains(90) && cardGame.getPlayers().getLast().contains(91));
    }

    /**
     * @see testGetDecks
     * 
     *      - testGetDecks is a void method, it checks the functionality of getDecks
     *      by adding some elements to the main decks list within Card Class,
     *      and checking the validity of the decks afterwards.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes decks
     * @CardGameClassMethods setDecks(), getDecks()
     */
    @Test
    public void testGetDecks() {
        CardGame cardGame = new CardGame();
        cardGame.setDecks(7);
        cardGame.decks.get(0).add(100);
        cardGame.decks.getLast().add(101);

        assertTrue(cardGame.getDecks().getFirst().contains(100) && cardGame.getDecks().getLast().contains(101));
    }

    /**
     * @see testCreatePackOverload
     * 
     *      - testCreatePackOverload is a void method, this method attempts to
     *      overload the pack created by the system.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber
     * @CardGameClassMethods createPack(int)
     */
    @After // it requires the memory to be full for this test
    public void testCreatePackOverload() {
        CardGame cardGame = new CardGame();

        cardGame.playerNumber = 2147483647; // passing the largest integer possible
        cardGame.createPack(cardGame.playerNumber);
        assertEquals(0, cardGame.getPack().size());

        cardGame.playerNumber = 21474839 / 16; // maximum value executable by the method
        // tolerance (or delta) is because of the integer divisions might return a
        // result not as exact
        cardGame.createPack(cardGame.playerNumber);
        assertEquals(21474839 / 2, cardGame.getPack().size(), 3);
    }

    /**
     * @see testCardConstructor
     * 
     *      - testCardConstructor is a void method, this method attempt to test the
     *      constructor of the CardGame class, it would pass wrong values
     *      as player number and overloads the constructor with 100000 players.
     * 
     * @Note when this constructor is called it would set player numbers, creates a
     *       pack, sets both players and decks main list then it would
     *       distribute the cards within that pack among players and decks in a
     *       round robin fashion.
     * 
     * @link CardGame.java
     * 
     * @CardGameClassInstance cardGame, cardGame1, cardGame2, cardGame3, cardGame4
     * @CardClassMethods getDecks()
     */

    @Test
    public void testCardGameConstructor() { // this needs supporting pack files to load
        CardGame cardGame = new CardGame();

        LinkedList<Integer> pack = cardGame.createPack(-74);
        CardGame cardGame1 = new CardGame(-74, pack);
        assertEquals(1, cardGame1.getPlayers().size());

        pack = cardGame.createPack(0);
        CardGame cardGame2 = new CardGame(0, pack);
        assertEquals(1, cardGame2.getPlayers().size());

        // it even works with 100000 players but due to the system overload for the
        // players and deck files created it would crash the system
        pack = cardGame.createPack(1000);
        CardGame cardGame3 = new CardGame(1000, pack);
        assertEquals(1000, cardGame3.getPlayers().size());

        for (int i = 1; i < 11; i++) {
            pack = cardGame.createPack(i);
            CardGame cardGame4 = new CardGame(i, pack);
            assertEquals(i, cardGame4.getDecks().size());
        }
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
     * @link Player.java, Card.java, CardGame.java
     * 
     * @CardGameClassInstance cardGame
     * @InstanceAttributes playerNumber
     * @CardGameClassMethods setPlayers(), setDecks(), getPlayers(), getDecks(),
     *                       startGame()
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods Player.getPlayers()
     * 
     * @CardClassInstance card
     * @CardClassMethods Card.getDecks()
     */
    @BeforeClass
    public static void testCardGameGameStability() {

        CardGame cardGame = new CardGame();

        cardGame.playerNumber = 8;
        cardGame.setPlayers(8);
        cardGame.setDecks(8);

        Player player = new Player(cardGame.getPlayers());
        Card card = new Card(cardGame.getDecks());

        Player.getPlayers().getFirst().add(1);
        Player.getPlayers().getFirst().add(1);
        Player.getPlayers().getFirst().add(1);
        Player.getPlayers().getFirst().add(0);

        Card.getDecks().getFirst().add(0);
        Card.getDecks().getFirst().add(0);
        Card.getDecks().getFirst().add(0);
        Card.getDecks().getFirst().add(1);

        cardGame.startGame(); // empty hands and decks for the rest of the players works

        cardGame.playerNumber = 8;
        cardGame.setPlayers(8);
        cardGame.setDecks(8);

        Player newPlayer = new Player(cardGame.getPlayers());
        Card newCards = new Card(cardGame.getDecks());

        Player.getPlayers().get(0).add(13);
        Player.getPlayers().get(0).add(16);
        Player.getPlayers().get(0).add(5);
        Player.getPlayers().get(0).add(2);
        Player.getPlayers().get(0).add(3);
        Player.getPlayers().get(0).add(13);
        Player.getPlayers().get(1).add(3);
        Player.getPlayers().get(2).add(3);
        Player.getPlayers().get(2).add(2);
        Player.getPlayers().get(2).add(1);
        Player.getPlayers().get(2).add(4);
        Player.getPlayers().get(2).add(4);
        Player.getPlayers().get(2).add(3);
        Player.getPlayers().get(2).add(3);
        Player.getPlayers().get(2).add(3);

        Card.getDecks().get(0).add(3);
        Card.getDecks().get(1).add(3);
        Card.getDecks().get(1).add(3);
        Card.getDecks().get(2).add(3);
        Card.getDecks().get(2).add(3);
        Card.getDecks().get(2).add(3);
        Card.getDecks().get(2).add(3);
        Card.getDecks().get(2).add(3);
        Card.getDecks().get(2).add(3);

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
     */
    @BeforeClass
    public static void testStartGameOverload() {
        try {
            CardGame cardGame = new CardGame();
            cardGame.playerNumber = 1000;
            LinkedList<Integer> pack = cardGame.createPack(4000);
            cardGame.setPack(pack);
            cardGame.setPlayers(cardGame.playerNumber);
            cardGame.setDecks(cardGame.playerNumber);
            InputOutput output = new InputOutput(cardGame.dealHands());
            cardGame.dealDecks();
            Player player = new Player(cardGame.getPlayers());
            Card card = new Card(cardGame.getDecks());
            cardGame.startGame();
            assertTrue(true); // game finished successfully with no errors
        } catch (Exception e) {
            fail("testStartGameOverload due to an unknwon error");
        }
    }
}
