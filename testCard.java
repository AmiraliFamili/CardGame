import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;


public class testCard {

    @Test
    public void testEmptyPack() {
        Card card = new Card();

        card.createPack(5);
        LinkedList<Integer> pack = card.getPack();

        assertTrue(pack.size() == 5 * 8);

        card.emptyPack();

        assertTrue(card.getPack().isEmpty());
    }

    @Test
    public void testGetPack() {
        Card card = new Card();

        card.createPack(34);
        LinkedList<Integer> pack = card.getPack();

        assertTrue(pack.size() == 34 * 8);
        pack.removeFirst();

        assertTrue(card.getPack().size() == ((34 * 8) - 1));
    }

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

    @Test
    public void testSetPlayers() {
        Card card = new Card();

        int threads = card.playerNum = 6;
        card.setPlayers();
        LinkedList<LinkedList<Integer>> actual = card.getPlayers();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < threads; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(expected, actual);

        assertEquals(6, actual.size());

    }

    @Test
    public void testSetDecks() {

        Card card = new Card();

        int threads = card.playerNum = 3;
        card.setDecks();
        LinkedList<LinkedList<Integer>> actual = card.getDecks();
        LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

        for (int i = 0; i < threads; i++) {
            expected.add(new LinkedList<Integer>());
        }

        assertEquals(expected, actual);

        assertEquals(3, actual.size());
    }

    @Test
    public void testDealHands() {
        Card card = new Card();

        boolean testPassed = false;
        card.playerNum = 5;
        card.createPack(card.playerNum);
        LinkedList<Integer> pack = card.getPack();

        int player1Card = pack.get(0);
        int player2Card = pack.get(1);
        int player3Card = pack.get(2);
        int player4Card = pack.get(3);
        int player5Card = pack.get(4);

        card.setPlayers();
        LinkedList<LinkedList<Integer>> actual = card.dealHands();

        // checks to see if the hands are distributed in the correct format
        // first player's hand's first card should be the first card of the pack
        // second player's hand's first card should be second card in the pack
        // ... so on for as many players we have
        // Note first player's hand's second card is playerNumber index in pack given
        // that we start from index 0
        if (player1Card == card.getPlayers().get(0).get(0) && player2Card == card.getPlayers().get(1).get(0)
                && player3Card == card.getPlayers().get(2).get(0) && player4Card == card.getPlayers().get(3).get(0)
                && player5Card == card.getPlayers().get(4).get(0)) {
            testPassed = true;
        }

        assertTrue(testPassed);
        assertTrue(actual.get(0).size() == 4);
        assertTrue(actual.get(1).size() == 4);
        assertTrue(actual.get(2).size() == 4);
        assertTrue(actual.get(3).size() == 4);
        assertTrue(actual.get(4).size() == 4);
        assertTrue(actual.size() == card.playerNum);

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

        boolean created = false;
        if (!card.players.getFirst().isEmpty()) {
            created = true;
        }

        assertTrue(created);

        // since the number of elements added to the pack is 9
        boolean expected = (mockHands.get(0).size() + mockHands.get(1).size() + mockHands.get(2).size()
                + mockHands.get(3).size()) == 9;

        assertTrue(expected);
    }

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

        // now we know that we have n decks of size 4 and
        // from the previous test we have n hands of size 4
        // therefore we have total cards of 8*n which is the same
        // as specification.

        assertTrue(pack.size() == 0);

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
        card.setPlayers();
        card.setDecks();

        LinkedList<LinkedList<Integer>> mockDecks = card.dealDecks();

        boolean created = false;
        if (!card.decks.getFirst().isEmpty()) {
            created = true;
        }

        assertTrue(created);

        // since the number of elements added to the pack is 9
        boolean expected = (mockDecks.get(0).size() + mockDecks.get(1).size() + mockDecks.get(2).size()
                + mockDecks.get(3).size()) == 9;

        assertTrue(expected);
    }

    @Test
    public void testGetPlayers() {
        Card card = new Card();
        card.playerNum = 5;
        card.setPlayers();
        card.players.get(0).add(90);
        card.players.getLast().add(91);

        assertTrue(card.getPlayers().getFirst().contains(90) && card.getPlayers().getLast().contains(91));
    }

    @Test
    public void testGetDecks() {
        Card card = new Card();
        card.playerNum = 7;
        card.setDecks();
        card.decks.get(0).add(100);
        card.decks.getLast().add(101);

        assertTrue(card.getDecks().getFirst().contains(100) && card.getDecks().getLast().contains(101));
    }

    @Test
    public void testCreatePackOverload() {
        Card card = new Card();

        card.playerNum = 2147483647; // max integer possible
        card.createPack(card.playerNum);

        assertEquals(0, card.getPack().size());

        card.playerNum = 21474838 / 16; // maximum value executable by the method
        card.createPack(card.playerNum);

        // the actual value has a tolerance of 3 since at large values some of the
        // values are not added
        assertEquals(21474838 / 2, card.getPack().size(), 3);
    }

    @Test // it takes 20 seconds or more to run this , a game with 100000 players
    public void testCardConstructor() {

        Card card = new Card(100000);

        assertEquals(100000, card.getPlayers().size());

        for (int i = 1; i < 11; i++) {
            Card obj = new Card(i);
            assertEquals(i, obj.getDecks().size());
        }
    }


    // write the readme.md file
    // documentation of the test methods

    /**
     * Documentation :
     * commenting example
     * 
     * @see createPostProtected (String handle, String message)
     * 
     *      - method createPostProtected is one of the main method which runs the
     *      social media platform !
     * 
     *      - method creates a post with given handle and message.
     * 
     * @param handle  handle to identify the account.
     * @param message post message.
     * 
     * @throws HandleNotRecognisedException if the handle does not match to any
     *                                      account in the system.
     * @throws InvalidPostException         if the message is empty or has more than
     *                                      100 characters.
     * 
     * @return the {@id} of the created post.
     */
    public static void main(String[] args) {
        testCard test = new testCard();
        test.testCardConstructor();

    }
}
