import java.util.LinkedList;
import org.junit.Test;
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
 * @Note getPlayers functionality is already tested within some test methods
 *       thus I didn't test it in it's separate method.
 * 
 * @author Amirali Famili
 */
public class testPlayer {

    /**
     * @see testPlayerConstructor
     * 
     *      - testPlayerConstructor is a void method, it tests the constructor of
     *      the Player class to make sure that players LinkedList and the number of
     *      players are set.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @InstanceAttributes playerNumber
     * @PlayerClassMethods getPlayers(), getPlayer(int)
     */
    @Test
    public void testPlayerConstructor() {
        LinkedList<LinkedList<Integer>> players = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new LinkedList<>());
            for (int j = 0; j < 6; j++) {
                players.get(i).add(j);
            }
        }
        Player player = new Player(players);

        assertEquals(5, Player.getPlayers().size());
        assertEquals(6, player.getPlayer(0).size());
        assertEquals(6, player.getPlayer(1).size());
        assertEquals(6, player.getPlayer(2).size());
        assertEquals(6, player.getPlayer(4).size());
        assertEquals(5, player.playerNumber);
    }

    /**
     * @see testReplaceCard
     * 
     *      - testReplaceCard is a void method, it tests the replaceCard method
     *      inside Players class by passing wrong and null values to it and it
     *      checks the functionality of the method by creating a mock hand and
     *      replacing a random value from it.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods replaceCard(int, int, LinkedList<Integer>)
     */
    @Test
    public void testReplaceCard() {
        Player player = new Player();

        try {
            player.replaceCard(0, 0, null);
            player.replaceCard(-3489, -783, null);
            LinkedList<Integer> hand = new LinkedList<>();
            hand.add(null);
            hand.add(4);
            hand.add(7);
            hand.add(-348);
            hand.add(0);
            hand.add(0);
            player.replaceCard(-8374, 1, hand);
            player.replaceCard(748, 1, hand);
            player.replaceCard(0, 1, hand);

            assertTrue(hand.contains(1)); // 0 has been successfully replaced by 1
        } catch (Exception e) {
            fail("test failed at testReplaceCard, an error occurred whilst testing the method");
        }
    }

    /**
     * @see testGetPlayer
     * 
     *      - testGetPlayer is a void method, it tests the method getPlayer by
     *      creating mock players with wrong values and only initializing a specific
     *      player then it would use getPlayer method to retrieve that specific
     *      player and checks the length of the player's hand.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods getPlayer(int)
     */
    @Test
    public void testGetPlayer() {
        LinkedList<LinkedList<Integer>> players = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new LinkedList<>());
        }

        Player player = new Player(players);
        player.players.get(3).add(null);
        player.players.get(3).add(null);
        player.players.get(3).add(null);
        player.players.get(3).add(null);

        assertEquals(4, player.getPlayer(3).size());
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
     */
    @Test
    public void testHasDuplicates() {
        Player player = new Player();

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
     *      - testGetCard is a void method, this method creates an instance os the
     *      player class, using that instance it would set the player number and set
     *      players list with it and retrieves it with getPlayer method, then it
     *      would create mock hands to test the robustness of the getCard method
     *      which is our subject here.
     * 
     * @Note getCard method returns 0 if there is a null value in the hand, it
     *       returns the last element if there is no duplicate in the hand ot it
     *       returns the
     *       first occurrence of a digit which has not been repeated throughout the
     *       hand.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods getCard(LinkedList<Integer>), getPlayer(int),
     *                     setPlayers(int)
     * 
     */
    @Test
    public void testGetCard() {

        Player player = new Player();

        player.playerNumber = 1;
        player.setPlayers(player.playerNumber);

        LinkedList<Integer> playerHand = player.getPlayer(0);
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
     * @see testSetPlayers
     * 
     *      - testSetPlayers is a void method, it tests the setPlayers method by
     *      passing it any wrong value possible and then by using getPlayers method
     *      it checks the size of the created LinkedLists.
     * 
     * @link Player.java
     * 
     * @PlayerClassInstance player
     * @PlayerClassMethods setPlayers(int), getPlayers()
     */
    @Test
    public void testSetPlayers() {

        Player player = new Player();

        player.setPlayers(-1);
        assertEquals(1, player.getPlayers().size());

        player.setPlayers(0);
        assertEquals(1, Player.getPlayers().size());

        player.setPlayers(1);
        assertEquals(1, player.getPlayers().size());

    }
}
