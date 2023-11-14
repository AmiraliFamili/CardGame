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
 * @see testCard
 * 
 *      - Class testCard is used for testing all the methods and objects as well
 *      as the constructors of the
 *      Card Class, It checks the code for certain exceptions that could occur.
 * 
 * @Note that most methods within this test class are testing multiple aspects
 *       of both the corresponding method as well as other methods
 *       which are consists of many assertEquals, assertTrue, ...
 * 
 * @author Amirali Famili
 */
public class testCard {

    /**
     * @see testEmptyPack
     * 
     *      - testEmptyPack is a void method, it creates a pack and then checks the
     *      size of the created pack
     *      then, it would empty the pack and check that it actually emptied inside
     *      Card Class.
     * 
     * 
     * @CardClass createPack(int), emptyPack(), getPack() and card instance
     * 
     * @author Amirali Famili
     */
    @Test
    public void testEmptyPack() {
        Card card = new Card();

        card.createPack(5);
        LinkedList<Integer> pack = card.getPack();

        assertEquals(5 * 8, pack.size());

        card.emptyPack();

        assertTrue(card.getPack().isEmpty());
    }

    /**
     * @see testGetPack
     * 
     *      - testGetPack is a void method, it creates a pack and checks for it's
     *      size, then it would remove the
     *      first element of the pack and check with getPack method that it's value
     *      is updated within Card Class.
     * 
     * 
     * @CardClass createPack(int), getPack() and card instance
     * 
     * @author Amirali Famili
     */
    @Test
    public void testGetPack() {
        Card card = new Card();

        card.createPack(34);
        LinkedList<Integer> pack = card.getPack();

        assertTrue(pack.size() == 34 * 8);
        pack.removeFirst();

        assertTrue(card.getPack().size() == ((34 * 8) - 1));
    }

    /**
     * @see testCreatePack
     * 
     *      - testCreatePack is a void method, it creates packs of odd values given
     *      to it and then check it's size, empties the pack
     *      then it would move on to the next odd input for createPack method.
     * 
     * 
     * @CardClass createPack(int), emptyPack(), getPack() and card instance
     * 
     * @author Amirali Famili
     */
    @Test
    public void testCreatePack() {
        Card card = new Card();

        card.createPack(1);
        assertEquals(8, card.getPack().size());

        card.emptyPack();
        card.createPack(0);

        assertEquals(0, card.getPack().size());

        card.emptyPack();
        card.createPack(-1);

        assertEquals(0, card.getPack().size());

        card.emptyPack();

        card.createPack(-1001);
        assertEquals(0, card.getPack().size());

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
     * @CardClassInstance card
     * @CardClassMethods createPack(int), get1FromPack(), getPack()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testGet1FromPack() {
        Card card = new Card();
        card.createPack(1);

        int packSize = card.getPack().size();
        int expected = card.getPack().getFirst();
        int actual = card.get1FromPack();

        assertEquals(expected, actual);
        assertEquals(packSize - 1, card.getPack().size());
    }

    /**
     * @see testSetPlayers
     * 
     *      - testSetPlayers is a void method, it set's the number of players and
     *      then creates that many empty lists within the
     *      nested linked lists called players, then it would retrieve this list
     *      from Card class and after creating a local nested list
     *      of the same length, it would compare to see if they are the same lists.
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods setPlayers(), getPlayers()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testSetPlayers() {
        Card card = new Card();

        card.playerNum = 6;
        card.setPlayers();
        LinkedList<LinkedList<Integer>> actual = card.getPlayers();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < card.playerNum; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(6, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * @see testSetDecks
     * 
     *      - testSetDecks is a void method, similarly to the testSetPlayers method
     *      it would set the decks within the Card Class and retrieves it and then
     *      creates the
     *      expected local list and compares the two lists for Equality and Size.
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods setDecks(), getDecks()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testSetDecks() {

        Card card = new Card();

        card.playerNum = 3;
        card.setDecks();
        LinkedList<LinkedList<Integer>> actual = card.getDecks();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < card.playerNum; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * @see testDealHands
     * 
     *      - testDealHands is a void method, it would create a pack for 5 players
     *      of size 40, then it would retrieve the first 5 cards within the pack, .
     *      then it would use methods setPlayers and dealHands to distribute half of
     *      the pack to the players then it would check that dealer has distributed
     *      the cards in the correct format
     *      giving one card at a time to each player in a round robin fashion, then
     *      it would check that all players have 4 cards in their hands.
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods createPack(int), setPlayers(), getPlayers(), dealHands()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testDealHands() {
        Card card = new Card();

        card.playerNum = 5;
        card.createPack(card.playerNum);
        LinkedList<Integer> pack = card.getPack();

        int player1Card1 = pack.get(0);
        int player2Card1 = pack.get(1);
        int player3Card1 = pack.get(2);
        int player4Card1 = pack.get(3);
        int player5Card1 = pack.get(4);
        int player1Card2 = pack.get(5);

        card.setPlayers();
        LinkedList<LinkedList<Integer>> actual = card.dealHands();

        assertTrue(player1Card1 == card.getPlayers().get(0).get(0));
        assertTrue(player2Card1 == card.getPlayers().get(1).get(0));
        assertTrue(player3Card1 == card.getPlayers().get(2).get(0));
        assertTrue(player4Card1 == card.getPlayers().get(3).get(0));
        assertTrue(player5Card1 == card.getPlayers().get(4).get(0));
        assertTrue(player1Card2 == card.getPlayers().get(0).get(1));

        assertEquals(4, actual.get(0).size());
        assertEquals(4, actual.get(1).size());
        assertEquals(4, actual.get(2).size());
        assertEquals(4, actual.get(3).size());
        assertEquals(4, actual.get(4).size());

        assertTrue(actual.size() == card.playerNum);
    }

    /**
     * @see testDealHandsMockHands
     * 
     *      - testDealHandsMockHands is a void method, it would create a pack with
     *      wrong values and size then
     *      it would checks to see if the hands where created and that cards where
     *      distributed correctly among players.
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum, pack
     * @CardClassMethods emptyPack(), setPlayers(), dealHands()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testDealHandsMockHands() {
        Card card = new Card();

        card.emptyPack();
        card.pack.add(9);
        card.pack.add(23);
        card.pack.add(34);
        card.pack.add(5);
        card.pack.add(0);
        card.pack.add(-23);
        card.pack.add(-4);
        card.pack.add(-0);
        card.pack.add(1);

        card.playerNum = 4;
        card.setPlayers();

        LinkedList<LinkedList<Integer>> mockHands = card.dealHands();
        assertTrue(!card.players.getFirst().isEmpty());

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
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods createPack(int), setPlayers(), dealHands(), dealDecks(),
     *                   seDecks(), getPack()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testDealDecks() {
        Card card = new Card();

        card.playerNum = 4;
        card.createPack(card.playerNum);
        LinkedList<Integer> pack = card.getPack();

        card.setPlayers();
        card.dealHands();
        card.setDecks();
        LinkedList<LinkedList<Integer>> actual = card.dealDecks();

        assertEquals(4, actual.get(0).size());
        assertTrue(actual.get(1).size() == 4);
        assertTrue(actual.get(2).size() == 4);
        assertTrue(actual.get(3).size() == 4);
        assertTrue(actual.size() == card.playerNum);
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
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum, pack
     * @CardClassMethods dealDecks(), seDecks(), emptyPack()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testDealDecksMockHands() {
        Card card = new Card();

        card.emptyPack();
        card.pack.add(3);
        card.pack.add(213);
        card.pack.add(400);
        card.pack.add(5 - 12);
        card.pack.add(000);
        card.pack.add(-234454353);
        card.pack.add(-23843);
        card.pack.add(-000000000);
        card.pack.add(134443);

        card.playerNum = 4;
        card.setDecks();

        LinkedList<LinkedList<Integer>> mockDecks = card.dealDecks();

        assertTrue(!card.decks.getFirst().isEmpty());
        assertTrue(card.pack.isEmpty());

        boolean expected = (mockDecks.get(0).size() + mockDecks.get(1).size() + mockDecks.get(2).size()
                + mockDecks.get(3).size()) == 9;

        assertTrue(expected);
    }

    /**
     * @see testGetPlayers
     * 
     *      - testGetPlayers is a void method, it checks the functionality of
     *      getPlayers by adding some elements to the main players list within Card
     *      Class,
     *      and checking the validity of the players afterwards.
     * 
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum, players
     * @CardClassMethods setPlayers(), getPlayers()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testGetPlayers() {
        Card card = new Card();
        card.playerNum = 5;
        card.setPlayers();
        card.players.get(0).add(90);
        card.players.getLast().add(91);

        assertTrue(card.getPlayers().getFirst().contains(90) && card.getPlayers().getLast().contains(91));
    }

    /**
     * @see testGetDecks
     * 
     *      - testGetDecks is a void method, it checks the functionality of getDecks
     *      by adding some elements to the main decks list within Card Class,
     *      and checking the validity of the decks afterwards.
     * 
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum, decks
     * @CardClassMethods setDecks(), getDecks()
     * 
     * @author Amirali Famili
     */
    @Test
    public void testGetDecks() {
        Card card = new Card();
        card.playerNum = 7;
        card.setDecks();
        card.decks.get(0).add(100);
        card.decks.getLast().add(101);

        assertTrue(card.getDecks().getFirst().contains(100) && card.getDecks().getLast().contains(101));
    }

    /**
     * @see testCreatePackOverload
     * 
     *      - testCreatePackOverload is a void method, this method attempts to
     *      overload the pack created by the system.
     * 
     * 
     * @CardClassInstance card
     * @InstanceAttributes playerNum
     * @CardClassMethods createPack(int)
     * 
     * @author Amirali Famili
     */
    @Test // it requires the memory to be full for this test
    public void testCreatePackOverload() {
        Card card = new Card();

        card.playerNum = 2147483647; // passing the largest integer possible
        card.createPack(card.playerNum);
        assertEquals(0, card.getPack().size());

        card.playerNum = 21474839 / 16; // maximum value executable by the method
        // tolerance (or delta) is because of the integer divisions might return a
        // result not as exact
        card.createPack(card.playerNum);
        assertEquals(21474839 / 2, card.getPack().size(), 3);
    }

    /**
     * @see testCardConstructor
     * 
     *      - testCardConstructor is a void method, this method attempt to test the constructor of the Card class, it would pass wrong values 
     *          as player number and overloads the constructor with 100000 players.
     * 
     * @Note when this constructor is called it would set player numbers, creates a pack, sets both players and decks main list then it would
     *      distribute the cards within that pack among players and decks in a round robin fashion.
     * 
     * 
     * @CardClassInstance card1, card2, card3, card4
     * @CardClassMethods getDecks()
     * 
     * @author Amirali Famili
     */

    @Test 
    public void testCardConstructor() {

        Card card1 = new Card(-74);
        assertEquals(1, card1.getPlayers().size());

        Card card2 = new Card(0);
        assertEquals(1, card2.getPlayers().size());

        Card card3 = new Card(100000);
        assertEquals(100000, card3.getPlayers().size());

        for (int i = 1; i < 11; i++) {
            Card card4 = new Card(i);
            assertEquals(i, card4.getDecks().size());
        }
    }
    public static void main(String[] args) {
        testCard test = new testCard();
        test.testCreatePackOverload();
    }
}
