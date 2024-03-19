

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import org.junit.BeforeClass;
import org.junit.Test;
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
 * @Note For all the methods inside this test class, java reflection is used to
 *       access CardGame class's private instances.
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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method emptyPackMethod = cardGameClass.getDeclaredMethod("emptyPack");
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");

            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);
            emptyPackMethod.setAccessible(true);

            createPackMethod.invoke(cardGame, 5);
            LinkedList<Integer> pack = (LinkedList<Integer>) getPackMethod.invoke(cardGame);
            assertEquals(5 * 8, pack.size());

            emptyPackMethod.invoke(cardGame);

            assertTrue(((LinkedList<Integer>) getPackMethod.invoke(cardGame)).isEmpty());
        } catch (Exception e) {
            fail("testEmptyPack Failed");
        }
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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");

            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);

            createPackMethod.invoke(cardGame, 34);
            LinkedList<Integer> pack = (LinkedList<Integer>) getPackMethod.invoke(cardGame);

            assertTrue(pack.size() == 34 * 8);
            pack.removeFirst();

            assertTrue(((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size() == ((34 * 8) - 1));
        } catch (Exception e) {
            fail("testGetPack Failed");
        }
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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method emptyPackMethod = cardGameClass.getDeclaredMethod("emptyPack");
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");

            emptyPackMethod.setAccessible(true);
            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);

            emptyPackMethod.invoke(cardGame);
            createPackMethod.invoke(cardGame, 1);
            assertEquals(8, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());

            emptyPackMethod.invoke(cardGame);
            createPackMethod.invoke(cardGame, 0);
            assertEquals(8, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());

            emptyPackMethod.invoke(cardGame);
            createPackMethod.invoke(cardGame, -1);
            assertEquals(8, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());

            emptyPackMethod.invoke(cardGame);

            createPackMethod.invoke(cardGame, -1001);
            assertEquals(8, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());
        } catch (Exception e) {
            fail("testCreatePack Failed");
        }
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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");
            Method get1FromPackMethod = cardGameClass.getDeclaredMethod("get1FromPack");

            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);
            get1FromPackMethod.setAccessible(true);

            createPackMethod.invoke(cardGame, 1);

            int packSize = ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size();
            int expected = ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).getFirst();
            int actual = ((int) get1FromPackMethod.invoke(cardGame));

            assertEquals(expected, actual);
            assertEquals(packSize - 1, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());
        } catch (Exception e) {
            fail("testGet1FromPack Failed");
        }
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
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getPlayers");

            setPlayersMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);

            setPlayersMethod.invoke(cardGame, 6);

            LinkedList<LinkedList<Integer>> actual = (LinkedList<LinkedList<Integer>>) getPlayersMethod
                    .invoke(cardGame);
            LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

            for (int i = 0; i < 6; i++) {
                expected.add(new LinkedList<Integer>());
            }

            assertEquals(6, actual.size());
            assertEquals(expected, actual);

        } catch (Exception e) {
            fail("testSetPlayers Failed");
        }

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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setDecksMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method getDecksMethod = cardGameClass.getDeclaredMethod("getDecks");

            setDecksMethod.setAccessible(true);
            getDecksMethod.setAccessible(true);

            setDecksMethod.invoke(cardGame, 3);

            LinkedList<LinkedList<Integer>> actual = (LinkedList<LinkedList<Integer>>) getDecksMethod
                    .invoke(cardGame);
            LinkedList<LinkedList<Integer>> expected = new LinkedList<LinkedList<Integer>>();

            for (int i = 0; i < 3; i++) {
                expected.add(new LinkedList<Integer>());
            }

            assertEquals(3, actual.size());
            assertEquals(expected, actual);

        } catch (Exception e) {
            fail("testSetDecks Failed");
        }
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
     * @CardGameClassMethods createPack(int), setPlayers(int), getPlayers(),
     *                       dealHands(), getPack()
     */
    @Test
    public void testDealHands() {
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method dealHandsMethod = cardGameClass.getDeclaredMethod("dealHands");
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getPlayers");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");

            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            setPlayersMethod.setAccessible(true);
            dealHandsMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);

            playerNumberField.set(cardGame, 5);
            createPackMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<Integer> pack = (LinkedList<Integer>) getPackMethod.invoke(cardGame);

            int player1Card1 = pack.get(0);
            int player2Card1 = pack.get(1);
            int player3Card1 = pack.get(2);
            int player4Card1 = pack.get(3);
            int player5Card1 = pack.get(4);
            int player1Card2 = pack.get(5);

            setPlayersMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<LinkedList<Integer>> actual = (LinkedList<LinkedList<Integer>>) dealHandsMethod.invoke(cardGame);

            assertTrue(player1Card1 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(0)
                    .get(0));
            assertTrue(player2Card1 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(1)
                    .get(0));
            assertTrue(player3Card1 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(2)
                    .get(0));
            assertTrue(player4Card1 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(3)
                    .get(0));
            assertTrue(player5Card1 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(4)
                    .get(0));
            assertTrue(player1Card2 == ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)).get(0)
                    .get(1));


            assertEquals(4, actual.get(0).size());
            assertEquals(4, actual.get(1).size());
            assertEquals(4, actual.get(2).size());
            assertEquals(4, actual.get(3).size());
            assertEquals(4, actual.get(4).size());


            assertTrue(actual.size() == (int) playerNumberField.get(cardGame));
        } catch (Exception e) {
            fail("testDealHands Failed");
        }
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
     * @InstanceAttributes playerNumber, pack, players
     * @CardGameClassMethods emptyPack(), setPlayers(), dealHands()
     */
    @Test
    public void testDealHandsMockHands() {
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method dealHandsMethod = cardGameClass.getDeclaredMethod("dealHands");
            Method emptyPackMethod = cardGameClass.getDeclaredMethod("emptyPack");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");
            Field packField = cardGameClass.getDeclaredField("pack");
            Field playersField = cardGameClass.getDeclaredField("players");

            playerNumberField.setAccessible(true);
            setPlayersMethod.setAccessible(true);
            dealHandsMethod.setAccessible(true);
            packField.setAccessible(true);
            emptyPackMethod.setAccessible(true);
            playersField.setAccessible(true);

            emptyPackMethod.invoke(cardGame);

            LinkedList<Integer> pack = new LinkedList<>();

            pack.add(9);

            pack.add(23);
            pack.add(34);
            pack.add(5);
            pack.add(0);
            pack.add(-23);
            pack.add(-4);
            pack.add(0);
            pack.add(1);

            packField.set(cardGame, pack);

            playerNumberField.set(cardGame, 4);
            setPlayersMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<LinkedList<Integer>> mockHands = (LinkedList<LinkedList<Integer>>) dealHandsMethod
                    .invoke(cardGame);
            assertTrue(!((LinkedList<LinkedList<Integer>>) playersField.get(cardGame)).getFirst().isEmpty());

            // since the number of elements added to the pack is 9
            boolean expected = (mockHands.get(0).size() + mockHands.get(1).size() + mockHands.get(2).size()
                    + mockHands.get(3).size()) == 9;

            assertTrue(expected);

        } catch (Exception e) {
            fail("testDealHandsMockHands Failed");
        }
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
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method dealHandsMethod = cardGameClass.getDeclaredMethod("dealHands");
            Method dealDecksMethod = cardGameClass.getDeclaredMethod("dealDecks");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");
            Method setDecksMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method getDecksMethod = cardGameClass.getDeclaredMethod("getDecks");

            setDecksMethod.setAccessible(true);
            getDecksMethod.setAccessible(true);
            createPackMethod.setAccessible(true);
            getPackMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            setPlayersMethod.setAccessible(true);
            dealHandsMethod.setAccessible(true);
            dealDecksMethod.setAccessible(true);

            playerNumberField.set(cardGame, 4);
            createPackMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<Integer> pack = (LinkedList<Integer>) getPackMethod.invoke(cardGame);

            setPlayersMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));
            dealHandsMethod.invoke(cardGame);

            setDecksMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<LinkedList<Integer>> actual = (LinkedList<LinkedList<Integer>>) dealDecksMethod.invoke(cardGame);

            assertEquals(4, actual.get(0).size());
            assertTrue(actual.get(1).size() == 4);
            assertTrue(actual.get(2).size() == 4);
            assertTrue(actual.get(3).size() == 4);
            assertTrue(actual.size() == (int) playerNumberField.get(cardGame));
            assertTrue(pack.size() == 0);

        } catch (Exception e) {
            fail("testDealDecks Failed");
        }
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
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setDecksMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method dealDecksMethod = cardGameClass.getDeclaredMethod("dealDecks");
            Method emptyPackMethod = cardGameClass.getDeclaredMethod("emptyPack");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");
            Field packField = cardGameClass.getDeclaredField("pack");
            Field decksField = cardGameClass.getDeclaredField("decks");

            playerNumberField.setAccessible(true);
            setDecksMethod.setAccessible(true);
            dealDecksMethod.setAccessible(true);
            packField.setAccessible(true);
            emptyPackMethod.setAccessible(true);
            decksField.setAccessible(true);

            emptyPackMethod.invoke(cardGame);

            LinkedList<Integer> pack = new LinkedList<>();

            pack.add(3);
            pack.add(213);
            pack.add(400);
            pack.add(5 - 12);
            pack.add(000);
            pack.add(-234454353);
            pack.add(-23843);
            pack.add(-000000000);
            pack.add(134443);

            packField.set(cardGame, pack);

            playerNumberField.set(cardGame, 4);
            setDecksMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            LinkedList<LinkedList<Integer>> mockHands = (LinkedList<LinkedList<Integer>>) dealDecksMethod
                    .invoke(cardGame);
            assertTrue(!((LinkedList<LinkedList<Integer>>) decksField.get(cardGame)).getFirst().isEmpty());
            assertTrue(((LinkedList<Integer>) packField.get(cardGame)).isEmpty());

            // since the number of elements added to the pack is 9
            boolean expected = (mockHands.get(0).size() + mockHands.get(1).size() + mockHands.get(2).size()
                    + mockHands.get(3).size()) == 9;

            assertTrue(expected);

        } catch (Exception e) {
            fail("testDealDecksMockHands Failed");
        }
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
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getPlayers");
            Field playersField = cardGameClass.getDeclaredField("players");

            setPlayersMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);
            playersField.setAccessible(true);

            setPlayersMethod.invoke(cardGame, 5);

            ((LinkedList<LinkedList<Integer>>) playersField.get(cardGame)).get(0).add(90);
            ((LinkedList<LinkedList<Integer>>) playersField.get(cardGame)).getLast().add(91);

            assertTrue(((LinkedList<LinkedList<Integer>>) playersField.get(cardGame)).getFirst().contains(90)
                    && ((LinkedList<LinkedList<Integer>>) playersField.get(cardGame)).getLast().contains(91));
        } catch (Exception e) {
            fail("testGetPlayers Failed");
        }
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
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getDecks");
            Field decksField = cardGameClass.getDeclaredField("decks");

            setPlayersMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);
            decksField.setAccessible(true);

            setPlayersMethod.invoke(cardGame, 7);

            ((LinkedList<LinkedList<Integer>>) decksField.get(cardGame)).get(0).add(100);
            ((LinkedList<LinkedList<Integer>>) decksField.get(cardGame)).getLast().add(101);

            assertTrue(((LinkedList<LinkedList<Integer>>) decksField.get(cardGame)).getFirst().contains(100)
                    && ((LinkedList<LinkedList<Integer>>) decksField.get(cardGame)).getLast().contains(101));
        } catch (Exception e) {
            fail("testGetDecks Failed");
        }
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
     * @CardGameClassMethods createPack(int), getPack()
     */
    @BeforeClass // it requires the cache to be full for this test
    public static void testCreatePackOverload() {
        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");

            createPackMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            getPackMethod.setAccessible(true);

            playerNumberField.set(cardGame, 2147483647); // passing the largest integer possible
            createPackMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));
            assertEquals(0, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size());

            playerNumberField.set(cardGame, 21474839 / 16);// maximum value executable by the method
            // tolerance (or delta) is because of the integer divisions might return a
            // result not as exact
            createPackMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));
            assertEquals(21474839 / 2, ((LinkedList<Integer>) getPackMethod.invoke(cardGame)).size(), 3);

        } catch (Exception e) {
            fail("testCreatePackOverload Failed");
        }
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

    @BeforeClass
    public static void testCardGameConstructor() {

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();

            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            createPackMethod.setAccessible(true);

            LinkedList<Integer> pack = (LinkedList<Integer>) createPackMethod.invoke(cardGame, -74);

            CardGame cardGame1 = new CardGame(-74, pack);

            Class<?> cardGameClass1 = cardGame1.getClass();
            Method getPlayersMethod = cardGameClass1.getDeclaredMethod("getPlayers");
            getPlayersMethod.setAccessible(true);

            assertEquals(1, ((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame1)).size());

            pack = (LinkedList<Integer>) createPackMethod.invoke(cardGame, 0);
            CardGame cardGame2 = new CardGame(0, pack);

            Class<?> cardGameClass2 = cardGame2.getClass();
            Method getPlayersMethod2 = cardGameClass2.getDeclaredMethod("getPlayers");
            getPlayersMethod2.setAccessible(true);

            assertEquals(1, ((LinkedList<LinkedList<Integer>>) getPlayersMethod2.invoke(cardGame2)).size());

            // it even works with 100000 players but due to the system overload for the
            // players and deck files created it would crash the system
            pack = (LinkedList<Integer>) createPackMethod.invoke(cardGame, 1000);
            CardGame cardGame3 = new CardGame(1000, pack);

            Class<?> cardGameClass3 = cardGame3.getClass();
            Method getPlayersMethod3 = cardGameClass3.getDeclaredMethod("getPlayers");
            getPlayersMethod3.setAccessible(true);

            assertEquals(1000, ((LinkedList<LinkedList<Integer>>) getPlayersMethod3.invoke(cardGame3)).size());

            for (int i = 1; i < 11; i++) {
                pack = (LinkedList<Integer>) createPackMethod.invoke(cardGame, i);
                CardGame cardGame4 = new CardGame(i, pack);

                Class<?> cardGameClass4 = cardGame4.getClass();
                Method getDecksMethod = cardGameClass4.getDeclaredMethod("getDecks");
                getDecksMethod.setAccessible(true);

                assertEquals(i, ((LinkedList<LinkedList<Integer>>) getDecksMethod.invoke(cardGame4)).size());
            }

        } catch (Exception e) {
            fail("testCardGameConstructor Failed");
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

        try {
            CardGame cardGame = new CardGame();
            Class<?> cardGameClass = cardGame.getClass();
            Method getPackMethod = cardGameClass.getDeclaredMethod("getPack");
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method setDecksMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getPlayers");
            Method getDecksMethod = cardGameClass.getDeclaredMethod("getDecks");
            Method startGameMethod = cardGameClass.getDeclaredMethod("startGame");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");

            playerNumberField.setAccessible(true);
            getPackMethod.setAccessible(true);
            setDecksMethod.setAccessible(true);
            setPlayersMethod.setAccessible(true);
            getDecksMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);
            startGameMethod.setAccessible(true);

            playerNumberField.set(cardGame, 8);
            setPlayersMethod.invoke(cardGame, 8);
            setDecksMethod.invoke(cardGame, 8);

            Player player = new Player(((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)));
            Card card = new Card((((LinkedList<LinkedList<Integer>>) getDecksMethod.invoke(cardGame))));

            Player.getPlayers().getFirst().add(1);
            Player.getPlayers().getFirst().add(1);
            Player.getPlayers().getFirst().add(1);
            Player.getPlayers().getFirst().add(0);

            Card.getDecks().getFirst().add(0);
            Card.getDecks().getFirst().add(0);
            Card.getDecks().getFirst().add(0);
            Card.getDecks().getFirst().add(1);

            startGameMethod.invoke(cardGame);

            playerNumberField.set(cardGame, 8);
            setPlayersMethod.invoke(cardGame, 8);
            setDecksMethod.invoke(cardGame, 8);

            Player newPlayer = new Player(((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame)));
            Card newCard = new Card((((LinkedList<LinkedList<Integer>>) getDecksMethod.invoke(cardGame))));

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

            assertTrue(true); // game starts successfully

        } catch (Exception e) {
            fail("testCardGameGameStability Failed");
        }
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
            Class<?> cardGameClass = cardGame.getClass();
            Method getPlayersMethod = cardGameClass.getDeclaredMethod("getPlayers");
            Method getDecksMethod = cardGameClass.getDeclaredMethod("getDecks");
            Method createPackMethod = cardGameClass.getDeclaredMethod("createPack", int.class);
            Method setPlayersMethod = cardGameClass.getDeclaredMethod("setPlayers", int.class);
            Method setDecksMethod = cardGameClass.getDeclaredMethod("setDecks", int.class);
            Method dealHandsMethod = cardGameClass.getDeclaredMethod("dealHands");
            Method dealDecksMethod = cardGameClass.getDeclaredMethod("dealDecks");
            Method startGameMethod = cardGameClass.getDeclaredMethod("startGame");
            Field playerNumberField = cardGameClass.getDeclaredField("playerNumber");

            playerNumberField.setAccessible(true);
            getDecksMethod.setAccessible(true);
            getPlayersMethod.setAccessible(true);
            setDecksMethod.setAccessible(true);
            setPlayersMethod.setAccessible(true);
            dealDecksMethod.setAccessible(true);
            dealHandsMethod.setAccessible(true);
            startGameMethod.setAccessible(true);
            createPackMethod.setAccessible(true);

            playerNumberField.set(cardGame, 2000);
            LinkedList<Integer> pack = (LinkedList<Integer>) createPackMethod.invoke(cardGame, 8000);
            setPlayersMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));
            setDecksMethod.invoke(cardGame, (int) playerNumberField.get(cardGame));

            InputOutput output = new InputOutput((LinkedList<LinkedList<Integer>>) dealHandsMethod.invoke(cardGame));
            dealDecksMethod.invoke(cardGame);

            Player player = new Player((LinkedList<LinkedList<Integer>>) getPlayersMethod.invoke(cardGame));
            Card card = new Card((LinkedList<LinkedList<Integer>>) getDecksMethod.invoke(cardGame));

            startGameMethod.invoke(cardGame);
            assertTrue(true); // game starts successfully with no errors

        } catch (Exception e) {
            fail("testStartGameOverload Failed");
        }
    }
}
